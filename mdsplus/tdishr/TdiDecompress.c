/*	Tdi1Decompress.C
	Decompress using standard (MdsXpand) or user routine in an image.
	Compressed data has pointers to
	1	descriptor of image logical name.
	2	descriptor of routine name.
	3	descriptor of shape. Signality and units.
	4	descriptor of compressed data. Signality and units.
	TDI:	result = image->routine(shape, compressed)

	See MDS$COMPRESS.C and MdsCmprs.C for more info on expansion routine arguments.
	Fortran image:	routine(nitems, %descr(packed), %descr(result), bit)

	Ken Klare, LANL	P-4	(c)1989,1990,1992
*/
#include <STATICdef.h>
#include <stdlib.h>
#include "tdirefstandard.h"
#include "tdirefcat.h"
#include <tdimessages.h>
#include <mdsshr.h>

STATIC_CONSTANT char *cvsrev = "@(#)$RCSfile: TdiDecompress.c,v $ $Revision: 1.6 $ $Date: 2003/11/17 21:21:21 $";

extern int TdiGetArgs();
extern int Tdi2Vector();
extern int TdiFindImageSymbol();
extern int TdiMasterData();

TdiRefStandard(Tdi1Decompress)
struct descriptor_xd	sig[4], uni[4], dat[4];
struct TdiCatStruct		cats[5];
int	cmode = -1, j, (*symbol)();
int	bit = 0;

	status = TdiGetArgs(opcode, narg, list, sig, uni, dat, cats);
	if (status & 1) status = Tdi2Vector(narg-2, &uni[2], &dat[2], &cats[2]);
	if (status & 1) {
	struct descriptor_a *pa = (struct descriptor_a *)dat[2].pointer;
	int	nitems;
        if (pa->length <= 0)
        {
          switch (pa->dtype)
          {
            case DTYPE_B:
            case DTYPE_BU: pa->length = 1; break;
            case DTYPE_W:
            case DTYPE_WU: pa->length = 2; break;
            case DTYPE_L:
            case DTYPE_LU:
            case DTYPE_F:
            case DTYPE_FS: pa->length = 4; break;
            case DTYPE_D:
            case DTYPE_G:
            case DTYPE_FT:
            case DTYPE_Q:
            case DTYPE_QU: pa->length = 8; break;
            case DTYPE_O:
            case DTYPE_OU: pa->length = 16; break;
            default: return TdiINVDTYDSC;
          }
        }
        nitems = (int)pa->arsize / (int)pa->length;

		if (cats[1].in_dtype == DTYPE_MISSING) symbol = MdsXpand;
		else status = TdiFindImageSymbol(dat[0].pointer, dat[1].pointer, &symbol);
		if (status & 1) status = MdsGet1DxA(pa, &pa->length, &pa->dtype, out_ptr);
		if (status & 1) {
			out_ptr->pointer->class = CLASS_A;
			status = (*symbol)(&nitems, dat[3].pointer, out_ptr->pointer, &bit);
		}
	}
	status = TdiMasterData(narg-2, &sig[2], &uni[2], &cmode, out_ptr);
	for (j = narg; --j >= 0;) {
		if (sig[j].pointer) MdsFree1Dx(&sig[j], NULL);
		if (uni[j].pointer) MdsFree1Dx(&uni[j], NULL);
		if (dat[j].pointer) MdsFree1Dx(&dat[j], NULL);
	}
	return status;
}
