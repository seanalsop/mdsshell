#include <mdsdescrip.h>
#include <mds_gendevice.h>
#include <mitdevices_msg.h>
#include <mds_stdarg.h>

#include <treeshr.h>
#include <string.h>
#include "mit_pulse_gen.h"
#include "decoder.h"
#define min(a,b) ((a) < (b) ? (a) : (b))
#define max(a,b) ((a) > (b) ? (a) : (b))

typedef struct descriptor *Dptr;

extern int mit_pulse___get_setup(Dptr,InGet_setupStruct *);
extern int mit_decoder__get_event(int *,EventMask *);
extern int TdiCompile();
extern int GenDeviceFree();

static int GetSetup(Dptr niddsc_ptr, Dptr method, DecoderSetup *setup, EventMask *event_mask, Dptr *output, int gate);

int mit_pulse__get_setup(Dptr niddsc_ptr, Dptr method, DecoderSetup *setup, EventMask *event_mask, Dptr *output)
{
  return GetSetup(niddsc_ptr, method, setup, event_mask, output, 0);
}

int mit_gate__get_setup(Dptr niddsc_ptr, Dptr method, DecoderSetup *setup, EventMask *event_mask, Dptr *output)
{
  return GetSetup(niddsc_ptr, method, setup, event_mask, output, 1);
}

static int GetSetup(Dptr niddsc_ptr, Dptr method, DecoderSetup *setup, EventMask *event_mask, Dptr *output, int gate)
{
  int status;
  InGet_setupStruct s;
  status = mit_pulse___get_setup(niddsc_ptr,&s);
  if (status & 1)
  {
    float delay = s.pulse_time - s.trigger;
    float max_period = max(delay, s.duration);
    float period;
    static float gate_start;
    static float gate_end;
    static int trigger_nid;
    static int output_nid;
    int clock_source;
    static EMPTYXD(out);
    DESCRIPTOR_NID(trigger_nid_dsc,(char *)&trigger_nid);
    static DESCRIPTOR_NID(output_nid_dsc,(char *)0);
    DESCRIPTOR_FLOAT(gate_start_dsc,&gate_start);
    DESCRIPTOR_FLOAT(gate_end_dsc,&gate_end);
    trigger_nid = s.head_nid + MIT_PULSE_N_TRIGGER;
    output_nid = s.head_nid + MIT_PULSE_N_OUTPUT;
    output_nid_dsc.pointer = (char *)&output_nid;
    for (clock_source = EXT_1MHZ, period = 1E-6;
         period * 65534 < max_period && clock_source <= EXT_100HZ;
         clock_source++, period *= 10);
    if (clock_source > EXT_100HZ)
      return TIMING$_INVDELDUR;
    switch (s.output_mode)
    {
      case PO_HIGH_PULSES:         setup->output_control = HIGH_PULSES; setup->start_high = 0; break;
      case PO_LOW_PULSES:          setup->output_control = LOW_PULSES;  setup->start_high = 1; break;
      case PO_TOGGLE_INITIAL_HIGH: setup->output_control = TOGGLE;      setup->start_high = 1; break;
      case PO_TOGGLE_INITIAL_LOW:  setup->output_control = TOGGLE;      setup->start_high = 0; break;
      default: return TIMING$_INVOUTCTR;
    }
    setup->count_up = 0;
    setup->bcd_count = 0;
    setup->repeat_count = 0;
    setup->double_load = s.duration > 0;
    setup->special_gate = 0;
    setup->clock_source = clock_source;
    setup->falling_edge = 0;
    switch (s.trigger_mode)
    {
      case TM_EVENT_TRIGGER:
      case TM_RISING_TRIGGER:   setup->gating = GATE_RISING; break;
      case TM_FALLING_TRIGGER:  setup->gating = GATE_FALLING; break;
      case TM_SOFTWARE_TRIGGER: setup->gating = GATE_NONE; break;
      default:                  status = TIMING$_INVTRGMOD; goto error;
    }
    setup->load = max(delay/period + .499999,3);
    gate_start = setup->load * period;
    setup->hold = max(s.duration/period + .499999,3);
    gate_end = setup->hold * period + gate_start;
    if (s.trigger_mode == TM_EVENT_TRIGGER) 
    {
      int event_nid = s.head_nid + MIT_PULSE_N_EVENT;
      status = mit_decoder__get_event(&event_nid,event_mask);
      if (!(status & 1)) goto error;
    }
    else
      memset(event_mask,0,sizeof(EventMask));
    if (gate)
    {
      static DESCRIPTOR(out_expression,"$1 + [$2,$3]");
      TdiCompile(&out_expression,&trigger_nid_dsc,&gate_start_dsc,&gate_end_dsc, &out MDS_END_ARG);
    }
    else
    {
      static DESCRIPTOR(out_expression,"$1 + $2");
      TdiCompile(&out_expression,&trigger_nid_dsc,&gate_start_dsc, &out MDS_END_ARG);
    }
    status = TreePutRecord(output_nid,(struct descriptor *)&out,0);
    *output = &output_nid_dsc;
    GenDeviceFree(&s);
  }
  return status;
error:
  GenDeviceFree(&s);
  return status;
}
