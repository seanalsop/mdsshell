#define L6810B_K_CONG_NODES 36
#define L6810B_N_HEAD 0
#define L6810B_N_NAME 1
#define L6810B_N_COMMENT 2
#define L6810B_N_FREQ 3
#define L6810B_N_EXT_CLOCK_IN 4
#define L6810B_N_STOP_TRIG 5
#define L6810B_N_MEMORIES 6
#define L6810B_N_SEGMENTS 7
#define L6810B_N_ACTIVE_MEM 8
#define L6810B_N_ACTIVE_CHANS 9
#define L6810B_N_INPUT_01 10
#define L6810B_N_INPUT_01_STARTIDX 11
#define L6810B_N_INPUT_01_ENDIDX 12
#define L6810B_N_INPUT_01_FULL_SCALE 13
#define L6810B_N_INPUT_01_SRC_CPLING 14
#define L6810B_N_INPUT_01_OFFSET 15
#define L6810B_N_INPUT_02 16
#define L6810B_N_INPUT_02_STARTIDX 17
#define L6810B_N_INPUT_02_ENDIDX 18
#define L6810B_N_INPUT_02_FULL_SCALE 19
#define L6810B_N_INPUT_02_SRC_CPLING 20
#define L6810B_N_INPUT_02_OFFSET 21
#define L6810B_N_INPUT_03 22
#define L6810B_N_INPUT_03_STARTIDX 23
#define L6810B_N_INPUT_03_ENDIDX 24
#define L6810B_N_INPUT_03_FULL_SCALE 25
#define L6810B_N_INPUT_03_SRC_CPLING 26
#define L6810B_N_INPUT_03_OFFSET 27
#define L6810B_N_INPUT_04 28
#define L6810B_N_INPUT_04_STARTIDX 29
#define L6810B_N_INPUT_04_ENDIDX 30
#define L6810B_N_INPUT_04_FULL_SCALE 31
#define L6810B_N_INPUT_04_SRC_CPLING 32
#define L6810B_N_INPUT_04_OFFSET 33
#define L6810B_N_INIT_ACTION 34
#define L6810B_N_STORE_ACTION 35
typedef struct {
	struct descriptor_xd *__xds;
	int __num_xds;
	int head_nid;
	char *name;
	int memories;
	int segments;
	int active_mem;
	int active_mem_convert;
	int active_chans;
	int freq;
	int freq_convert;
	float input_01_full_scale;
	int input_01_full_scale_convert;
	float input_02_full_scale;
	int input_02_full_scale_convert;
	float input_03_full_scale;
	int input_03_full_scale_convert;
	float input_04_full_scale;
	int input_04_full_scale_convert;
	int input_01_src_cpling;
	int input_02_src_cpling;
	int input_03_src_cpling;
	int input_04_src_cpling;
	int input_01_offset;
	int input_02_offset;
	int input_03_offset;
	int input_04_offset;
	struct descriptor *ext_clock_in;
} InInitStruct;
typedef struct {
	struct descriptor_xd *__xds;
	int __num_xds;
	int head_nid;
	char *name;
} InTriggerStruct;
typedef struct {
	struct descriptor_xd *__xds;
	int __num_xds;
	int head_nid;
	char *name;
} InStoreStruct;
