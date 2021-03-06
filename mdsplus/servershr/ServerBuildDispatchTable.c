/*------------------------------------------------------------------------------

		Name:   ServerBuildDispatchTable   

		Type:   C function

     		Author:	TOM FREDIAN

		Date:   17-APR-1992

    		Purpose: Build dispatch table 

------------------------------------------------------------------------------

	Call sequence: 

int ServerBuildDispatchTable( )

------------------------------------------------------------------------------
   Copyright (c) 1992
   Property of Massachusetts Institute of Technology, Cambridge MA 02139.
   This program cannot be copied or distributed in any form for non-MIT
   use without specific written approval of MIT Plasma Fusion Center
   Management.
---------------------------------------------------------------------------

 	Description:


------------------------------------------------------------------------------*/

#include <mdsdescrip.h>
#include <ipdesc.h>
#include <usagedef.h>
#include <dbidef.h>
#include <stdio.h>
#include <stdlib.h>
#include <strroutines.h>
#include <string.h>
#include <treeshr.h>
#include <mdsshr.h>
#include <libroutines.h>
#include <servershr.h>
#include <mds_stdarg.h>
#include "servershrp.h"
extern int TdiDeallocate();
extern int TdiDispatchOf();
extern int TdiExecute();
extern int TdiGetLong();
extern int TdiData();
extern int TdiGetNci();

static int num_actions;
static ActionInfo *actions;

static int CompareActions(ActionInfo *a, ActionInfo *b)
{
  return a->on == b->on ? (a->phase == b->phase ? a->sequence - b->sequence : a->phase - b->phase) : b->on - a->on;
}

#define MAX_ACTIONS 10000

static int AddReference(int idx, int *nid)
{
  int i;
  int j;
  for (i=0; i<num_actions; i++)
  {
    if (actions[i].nid == *nid)
    {
      if (actions[i].num_references == 0)
        actions[i].referenced_by = (int *)malloc(sizeof(int));
      else
      {
        for (j=0;j<actions[i].num_references;j++)
          if (actions[i].referenced_by[j] == idx)
            return 1;
        actions[i].referenced_by = (int *)realloc((char *)actions[i].referenced_by,sizeof(int) * (actions[i].num_references+1));
      }
      actions[i].referenced_by[actions[i].num_references] = idx;
      actions[i].num_references++;
      return 1;
    }
  }
  return 0;
}

static int fixup_nid(int *nid, int idx, struct descriptor *path_out)
{
  static DESCRIPTOR(dtype_str,"DTYPE");
  static int dtype;
  static DESCRIPTOR_LONG(dtype_dsc,&dtype);
  DESCRIPTOR_NID(niddsc,0);
  int status;
  niddsc.pointer = (char *)nid;
  status = TdiGetNci(&niddsc,&dtype_str,&dtype_dsc MDS_END_ARG);
  if (status & 1 && dtype == DTYPE_ACTION && AddReference(idx,nid))
  {
    char ident[64];
    char tmp[64];
    struct descriptor ident_dsc = {0, DTYPE_T, CLASS_S, 0};
    EMPTYXD(xd);
    sprintf(ident,"_ACTION_%08X",*nid);
    ident_dsc.length = strlen(ident);
    ident_dsc.pointer = ident;
    StrCopyDx(path_out,&ident_dsc);
    strcpy(tmp,ident);
    strcpy(ident,"public ");
    strcat(ident,tmp);
    strcat(ident,"=compile('_$_$_$_UNDEFINED')");
    ident_dsc.length = strlen(ident);
    TdiExecute(&ident_dsc,&xd MDS_END_ARG);
    MdsFree1Dx(&xd,NULL);
    return 1;
  }
  return 0;
}

static int fixup_path(struct descriptor *path_in, int idx, struct descriptor *path_out)
{
  char *path = strncpy((char *)malloc(path_in->length+1),path_in->pointer,path_in->length);
  int nid;
  int status = 0;
  path[path_in->length] = 0;
  if (TreeFindNode(path,&nid) & 1)
    status = fixup_nid(&nid, idx, path_out);
  free(path);
  return status;
}

static int make_idents(struct descriptor *path_in, int idx, struct descriptor *path_out)
{
  if (path_in && path_in->pointer && path_in->pointer[0] == '_')
    path_in->dtype = DTYPE_IDENT;
  return 0;
}

static void LinkConditions()
{
  int i;
  for (i=0; i<num_actions; i++)
  {
    if (actions[i].condition)
    {
      EMPTYXD(xd);
      MdsCopyDxXdZ(actions[i].condition,&xd,0,fixup_nid,(void *)i,fixup_path,(void *)i);
      MdsCopyDxXdZ((struct descriptor *)&xd,(struct descriptor_xd *)actions[i].condition,0,0,(void *)0,make_idents,(void *)i);
      MdsFree1Dx(&xd,0);
    }
  }
  return;
}

int ServerBuildDispatchTable( char *wildcard, char *monitor_name, void **table)
{
  DispatchTable **table_ptr = (DispatchTable **)table;
  void *ctx = 0;
  static int nid;
  int i;
  static char *allnodes = "***";
  static DESCRIPTOR(varnames,"_ACTION_*");
  static struct descriptor varnames_d = {0, DTYPE_T, CLASS_D, 0};
  static int relcount;
  static DESCRIPTOR_LONG(relcount_d,&relcount);
  char *nodespec = wildcard ? wildcard : allnodes;
  int mask = 1 << TreeUSAGE_ACTION;
  int status=1;
  int *nids = (int *)malloc(MAX_ACTIONS * sizeof(int));
  int *nidptr;
  char tree[12];
  int shot;
  char *cptr;
  static DBI_ITM itmlst[] = {{sizeof(tree),DbiNAME,0,0},
                          {sizeof(shot),DbiSHOTID,0,0},
                          {0,0,0,0}};
  itmlst[0].pointer=&tree;
  itmlst[1].pointer=&shot;
  TreeGetDbi(itmlst);
  for (i=0, cptr = tree;i<12;i++) 
    if (cptr[i] == (char)32) 
      break;
    else
      tree[i] = cptr[i];
  tree[i] = 0;
  if (shot == -1)
    return ServerINVSHOT;
  num_actions=0;
  if (!varnames_d.length)
    StrCopyDx(&varnames_d,&varnames);
  while ((TreeFindNodeWild(nodespec,&nids[num_actions],&ctx,mask) & 1) && (num_actions < MAX_ACTIONS))
    num_actions++;
  TreeFindNodeEnd(&ctx);
  if (num_actions)
  {
    static struct descriptor node = {0, DTYPE_T, CLASS_D, 0};
    static struct descriptor object = {0, DTYPE_T, CLASS_D, 0};
    static zero=0;
    int table_size = sizeof(DispatchTable) + (num_actions-1) * sizeof(ActionInfo);
    *table_ptr = (DispatchTable *)malloc(table_size);
    memset(*table_ptr,0,table_size);
    actions = (*table_ptr)->actions;
    (*table_ptr)->shot = shot;
    strcpy((*table_ptr)->tree,tree);
    for (i=0, nidptr = nids; i<num_actions; nidptr++, i++)
    {
      struct descriptor event_name = {0, DTYPE_T, CLASS_D, 0};
      struct descriptor niddsc = {4, DTYPE_NID, CLASS_S, 0};
      static EMPTYXD(xd);
      niddsc.pointer = (char *)nidptr;
      actions[i].nid = *nidptr;
      actions[i].on = TreeIsOn(*nidptr) & 1;
      actions[i].num_references = 0;
      actions[i].referenced_by = 0;
      actions[i].status = 0;
      actions[i].dispatched = 0;
      actions[i].closed = 0;
      actions[i].condition = 0;
      if (actions[i].on)
      {
        if (TdiDispatchOf(&niddsc, &xd MDS_END_ARG) & 1)
        {
          struct descriptor_dispatch *dispatch = (struct descriptor_dispatch *)xd.pointer;
          if (dispatch->pointer[0] == TreeSCHED_SEQ)
          {
            struct descriptor server = {sizeof(actions->server),DTYPE_T,CLASS_S,0};
            static DESCRIPTOR(phase_lookup,"PHASE_NUMBER_LOOKUP($)");
            struct descriptor phase_d = {sizeof(int), DTYPE_L, CLASS_S, 0};
            server.pointer = actions[i].server;
            phase_d.pointer = (char *)&actions[i].phase;
            if (!(TdiExecute(&phase_lookup,dispatch->phase, &phase_d MDS_END_ARG) & 1))
              actions[i].phase = 0x10000001;
            if (!(TdiGetLong(dispatch->when, &actions[i].sequence) & 1))
            {
              static EMPTYXD(emptyxd);
              actions[i].sequence = 0;
              actions[i].condition = (struct descriptor *)memcpy(malloc(sizeof(emptyxd)),&emptyxd,sizeof(emptyxd));
              MdsCopyDxXd((struct descriptor *)dispatch->when,(struct descriptor_xd *)actions[i].condition);
	      actions[i].path = TreeGetMinimumPath(&zero,actions[i].nid);
            }
            else if (actions[i].sequence <= 0)
              actions[i].phase = 0x10000002;
	    else
	      actions[i].path = TreeGetMinimumPath(&zero,actions[i].nid);
            if (!(TdiData(dispatch->ident,&server MDS_END_ARG)&1))
              actions[i].phase = 0x10000003;
          }
          else
            actions[i].phase =   0x10000004;
	  if (TdiData(dispatch->completion,&event_name MDS_END_ARG) & 1 && event_name.length)
	  {
	    actions[i].event = strncpy((char *)malloc(event_name.length+1),event_name.pointer,event_name.length);
	    actions[i].event[event_name.length] = 0;
	    StrFree1Dx(&event_name);
	  }
        }
        else
          actions[i].phase =     0x10000005;
      }
    }
    qsort(actions, num_actions, sizeof(ActionInfo), (int (*)(const void *,const void *))CompareActions);
    for (i=0;i<num_actions && actions[i].on;i++);
    (*table_ptr)->num = i;
    LinkConditions();
    if (monitor_name)
    {
      char tree[13];
      char *cptr;
      for (i=0, cptr = (*table_ptr)->tree;i<12;i++) 
        if (cptr[i] == (char)32) 
          break;
        else
          tree[i] = cptr[i];
      tree[i] = 0;
      for (i=0; i<num_actions; /* i++ */ i += num_actions-1)
      {
        struct descrip p1,p2,p3,p4,p5,p6,p7,p8;
        int mode;
        int on = actions[i].on;
        char *server = "";
        mode = i ? (i == (num_actions - 1) ? MonitorBuildEnd : MonitorBuild) : MonitorBuildBegin;
        if (!(ServerSendMessage(0, monitor_name, SrvMonitor, 0, 0, 0, 0, 0, 8, 
                      MakeDescrip(&p1,DTYPE_CSTRING,0,0,tree), 
                      MakeDescrip(&p2,DTYPE_LONG,0,0,&(*table_ptr)->shot),
                      MakeDescrip(&p3,DTYPE_LONG,0,0,&actions[i].phase),
                      MakeDescrip(&p4,DTYPE_LONG,0,0,&actions[i].nid),
                      MakeDescrip(&p5,DTYPE_LONG,0,0,&on),
                      MakeDescrip(&p6,DTYPE_LONG,0,0,&mode),
                      MakeDescrip(&p7,DTYPE_CSTRING,0,0,server),
                      MakeDescrip(&p8,DTYPE_LONG,0,0,&actions[i].status))
                      & 1))
          break;
      }
    }
  }
  else
    status = TreeNNF;
  free(nids);
  return status;
}
