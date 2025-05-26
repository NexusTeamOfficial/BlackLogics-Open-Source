package com.besome.blacklogics.logic.editor;

public class DefineBlock {
	public static final int BLOCK_COLOR_ALL = -16740914;
	public static final int BLOCK_COLOR_ARG_BOOL = 1342177280;
	public static final int BLOCK_COLOR_ARG_FLOAT = -3155748;
	public static final int BLOCK_COLOR_ARG_INT = -657931;
	public static final int BLOCK_COLOR_ARG_SELECTABLE = 805306368;
	public static final int BLOCK_COLOR_ARG_STR = -1;
	public static final int BLOCK_COLOR_CONTROL = -1988310;
	public static final int BLOCK_COLOR_DEFINEDFNC = -13851166;
	public static final int BLOCK_COLOR_FUNCTIONS = -11899692;
	public static final int BLOCK_COLOR_HAT = -3636432;
	public static final int BLOCK_COLOR_LIST = -3384542;
	public static final int BLOCK_COLOR_MOREBLOCKS = -7711273;
	public static final int BLOCK_COLOR_MOREBLOCK_BOOL = -4056997;
	public static final int BLOCK_COLOR_MOREBLOCK_FLOAT = -12232092;
	public static final int BLOCK_COLOR_MOREBLOCK_INT = -16746133;
	public static final int BLOCK_COLOR_MOREBLOCK_STR = -10665929;
	public static final int BLOCK_COLOR_OPERATORS = -10701022;
	public static final int BLOCK_COLOR_VARIABLE = -1147626;
	public static final int BLOCK_COLOR_VARIABLE_BOOL = -1499549;
	public static final int BLOCK_COLOR_VARIABLE_FLOAT = -10457717;
	public static final int BLOCK_COLOR_VARIABLE_INT = -16738680;
	public static final int BLOCK_COLOR_VARIABLE_STR = -8825528;
	public static final int BLOCK_DEPENDENCY_ALL = 0;
	public static final int BLOCK_DEPENDENCY_LOGIC = 2;
	public static final int BLOCK_DEPENDENCY_VIEW = 1;
	public static final String[] BLOCK_FILTERS = new String[]{"V", "L", "C", "O", "F"};
	public static final String BLOCK_HELPER_ACTIVITY_GETEXTRA_S = "5F104";
	public static final String BLOCK_HELPER_AND = "4O05";
	public static final String BLOCK_HELPER_BOOL_VAR = "1V04";
	public static final String BLOCK_HELPER_CAL_ADD_VALUE = "5F231";
	public static final String BLOCK_HELPER_CAL_DIFFERENCE = "5F234";
	public static final String BLOCK_HELPER_CAL_FORMAT = "5F233";
	public static final String BLOCK_HELPER_CAL_GETNOW = "5F230";
	public static final String BLOCK_HELPER_CAL_SET_VALUE = "5F232";
	public static final String BLOCK_HELPER_CHK_GET_CHECKED = "5F504";
	public static final String BLOCK_HELPER_CHK_SET_CHECKED = "5F505";
	public static final String BLOCK_HELPER_DELETE_MEMO = "5F998";
	public static final String BLOCK_HELPER_FALSE = "4O01";
	public static final String BLOCK_HELPER_FILE_GET_DATA = "5F201";
	public static final String BLOCK_HELPER_FILE_REMOVE_DATA = "5F203";
	public static final String BLOCK_HELPER_FILE_SET_DATA = "5F202";
	public static final String BLOCK_HELPER_FINISH_ACTIVITY = "5F106";
	public static final String BLOCK_HELPER_FINISH_MEMO = "5F999";
	public static final String BLOCK_HELPER_FOREVER = "3C02";
	public static final String BLOCK_HELPER_GET_CUR_TIME = "4O921";
	public static final String BLOCK_HELPER_GET_ENABLE = "5F001";
	public static final String BLOCK_HELPER_GET_MEMO_TITLE = "5F995";
	public static final String BLOCK_HELPER_GET_TEXT = "5F003";
	public static final String BLOCK_HELPER_IF_ELSE = "3C05";
	public static final String BLOCK_HELPER_IF_THEN = "3C04";
	public static final String BLOCK_HELPER_INIT_LOGIC = "0W00";
	public static final String BLOCK_HELPER_INTENT_PUTEXTRA = "5F103";
	public static final String BLOCK_HELPER_INTENT_SET_ACTION = "5F100";
	public static final String BLOCK_HELPER_INTENT_SET_DATA = "5F101";
	public static final String BLOCK_HELPER_INTENT_SET_SCREEN = "5F102";
	public static final String BLOCK_HELPER_INT_DECREASE = "1V06";
	public static final String BLOCK_HELPER_INT_INCREASE = "1V05";
	public static final String BLOCK_HELPER_INT_SET_TO = "1V01";
	public static final String BLOCK_HELPER_INT_VAR = "1V00";
	public static final String BLOCK_HELPER_IV_GET_ROTATE = "5F300";
	public static final String BLOCK_HELPER_IV_SET_IMAGE = "5F006";
	public static final String BLOCK_HELPER_IV_SET_ROTATE = "5F301";
	public static final String BLOCK_HELPER_JOIN = "4O15";
	public static final String BLOCK_HELPER_LIST_ADDTO = "2L00";
	public static final String BLOCK_HELPER_LIST_CLEAR = "2L01";
	public static final String BLOCK_HELPER_LIST_CONTAINS = "2L02";
	public static final String BLOCK_HELPER_LIST_DELETE_AT = "2L03";
	public static final String BLOCK_HELPER_LIST_GET_AT_OF = "2L04";
	public static final String BLOCK_HELPER_LIST_INDEX_IN = "2L05";
	public static final String BLOCK_HELPER_LIST_INSERT_AT_TO = "2L06";
	public static final String BLOCK_HELPER_LIST_LENGTH_OF = "2L07";
	public static final String BLOCK_HELPER_LV_GETCHK_COUNT = "5F605";
	public static final String BLOCK_HELPER_LV_GETCHK_POS = "5F603";
	public static final String BLOCK_HELPER_LV_GETCHK_POS_ARR = "5F604";
	public static final String BLOCK_HELPER_LV_REFRESH = "5F601";
	public static final String BLOCK_HELPER_LV_SETDATA = "5F600";
	public static final String BLOCK_HELPER_LV_SETITEM_CHK_POS = "5F602";
	public static final String BLOCK_HELPER_MATH_DIVIDE = "4O11";
	public static final String BLOCK_HELPER_MATH_DIVIDE_REST = "4O12";
	public static final String BLOCK_HELPER_MATH_EQUALS = "4O03";
	public static final String BLOCK_HELPER_MATH_GREAT = "4O04";
	public static final String BLOCK_HELPER_MATH_LESS = "4O02";
	public static final String BLOCK_HELPER_MATH_MINUS = "4O09";
	public static final String BLOCK_HELPER_MATH_MULTI = "4O10";
	public static final String BLOCK_HELPER_MATH_PLUS = "4O08";
	public static final String BLOCK_HELPER_NOT = "4O07";
	public static final String BLOCK_HELPER_OPEN_MEMO = "5F994";
	public static final String BLOCK_HELPER_OR = "4O06";
	public static final String BLOCK_HELPER_PIC_RANDOM = "4O13";
	public static final String BLOCK_HELPER_READ_MEMO = "5F996";
	public static final String BLOCK_HELPER_REPEAT = "3C01";
	public static final String BLOCK_HELPER_SET_BGCOLOR = "5F004";
	public static final String BLOCK_HELPER_SET_ENABLE = "5F000";
	public static final String BLOCK_HELPER_SET_TEXT = "5F002";
	public static final String BLOCK_HELPER_SET_TEXTCOLOR = "5F005";
	public static final String BLOCK_HELPER_SET_VISIBLE = "5F010";
	public static final String BLOCK_HELPER_SHOW_MSG = "5F007";
	public static final String BLOCK_HELPER_SPN_GET_SELECTION = "5F403";
	public static final String BLOCK_HELPER_SPN_REFRESH = "5F401";
	public static final String BLOCK_HELPER_SPN_SET_DATA = "5F400";
	public static final String BLOCK_HELPER_SPN_SET_SELECTION = "5F402";
	public static final String BLOCK_HELPER_START_ACTIVITY = "5F105";
	public static final String BLOCK_HELPER_STOP = "3C03";
	public static final String BLOCK_HELPER_STR_EQUALS = "4O18";
	public static final String BLOCK_HELPER_STR_INDEX_OF = "4O16";
	public static final String BLOCK_HELPER_STR_LENGTH_OF = "4O14";
	public static final String BLOCK_HELPER_STR_SET_TO = "1V03";
	public static final String BLOCK_HELPER_STR_VAR = "1V02";
	public static final String BLOCK_HELPER_SUBSTRING = "4O17";
	public static final String BLOCK_HELPER_TO_NUMBER = "4O19";
	public static final String BLOCK_HELPER_TO_STRING = "4O20";
	public static final String BLOCK_HELPER_TRIM = "4O22";
	public static final String BLOCK_HELPER_TRUE = "4O00";
	public static final String BLOCK_HELPER_VIBRATOR = "5F060";
	public static final String BLOCK_HELPER_WRITE_MEMO = "5F997";
	public static final String BLOCK_OPCODE_ADD_LIST_INT = "addListInt";
	public static final String BLOCK_OPCODE_ADD_LIST_STR = "addListStr";
	public static final String BLOCK_OPCODE_AND = "&&";
	public static final String BLOCK_OPCODE_BREAK = "break";
	public static final String BLOCK_OPCODE_CALENDAR_ADD = "calendarAdd";
	public static final String BLOCK_OPCODE_CALENDAR_DIFF = "calendarDiff";
	public static final String BLOCK_OPCODE_CALENDAR_FORMAT = "calendarFormat";
	public static final String BLOCK_OPCODE_CALENDAR_GET_NOW = "calendarGetNow";
	public static final String BLOCK_OPCODE_CALENDAR_SET = "calendarSet";
	public static final String BLOCK_OPCODE_CLEAR_LIST = "clearList";
	public static final String BLOCK_OPCODE_CONTAIN_LIST_INT = "containListInt";
	public static final String BLOCK_OPCODE_CONTAIN_LIST_STR = "containListStr";
	public static final String BLOCK_OPCODE_DECREASE_INT = "decreaseInt";
	public static final String BLOCK_OPCODE_DEFINED_FUNC = "definedFunc";
	public static final String BLOCK_OPCODE_DELETE_LIST = "deleteList";
	public static final String BLOCK_OPCODE_DIVIDE = "/";
	public static final String BLOCK_OPCODE_DIVIDE_REST = "%";
	public static final String BLOCK_OPCODE_DO_TOAST = "doToast";
	public static final String BLOCK_OPCODE_FALSE = "false";
	public static final String BLOCK_OPCODE_FILE_GET_DATA = "fileGetData";
	public static final String BLOCK_OPCODE_FILE_REMOVE_DATA = "fileRemoveData";
	public static final String BLOCK_OPCODE_FILE_SET_DATA = "fileSetData";
	public static final String BLOCK_OPCODE_FINISH_ACTIVITY = "finishActivity";
	public static final String BLOCK_OPCODE_FOREVER = "forever";
	public static final String BLOCK_OPCODE_GET_ARG = "getArg";
	public static final String BLOCK_OPCODE_GET_AT_LIST_INT = "getAtListInt";
	public static final String BLOCK_OPCODE_GET_AT_LIST_STR = "getAtListStr";
	public static final String BLOCK_OPCODE_GET_CHECKED = "getChecked";
	public static final String BLOCK_OPCODE_GET_ENABLE = "getEnable";
	public static final String BLOCK_OPCODE_GET_ROTATE = "getRotate";
	public static final String BLOCK_OPCODE_GET_TEXT = "getText";
	public static final String BLOCK_OPCODE_GET_VAR = "getVar";
	public static final String BLOCK_OPCODE_IF = "if";
	public static final String BLOCK_OPCODE_IFELSE = "ifElse";
	public static final String BLOCK_OPCODE_INCREASE_INT = "increaseInt";
	public static final String BLOCK_OPCODE_INDEXOF_LIST_INT = "indexListInt";
	public static final String BLOCK_OPCODE_INDEXOF_LIST_STR = "indexListStr";
	public static final String BLOCK_OPCODE_INSERT_LIST_INT = "insertListInt";
	public static final String BLOCK_OPCODE_INSERT_LIST_STR = "insertListStr";
	public static final String BLOCK_OPCODE_INTENT_GET_STRING = "intentGetString";
	public static final String BLOCK_OPCODE_INTENT_PUT_EXTRA = "intentPutExtra";
	public static final String BLOCK_OPCODE_INTENT_SET_ACTION = "intentSetAction";
	public static final String BLOCK_OPCODE_INTENT_SET_DATA = "intentSetData";
	public static final String BLOCK_OPCODE_INTENT_SET_SCREEN = "intentSetScreen";
	public static final String BLOCK_OPCODE_LENGTH_LIST = "lengthList";
	public static final String BLOCK_OPCODE_LIST_GET_CHECK_COUNT = "listGetCheckedCount";
	public static final String BLOCK_OPCODE_LIST_GET_CHECK_POS = "listGetCheckedPosition";
	public static final String BLOCK_OPCODE_LIST_GET_CHECK_POS_ALL = "listGetCheckedPositions";
	public static final String BLOCK_OPCODE_LIST_REFRESH = "listRefresh";
	public static final String BLOCK_OPCODE_LIST_SET_CHECKED = "listSetItemChecked";
	public static final String BLOCK_OPCODE_LIST_SET_DATA = "listSetData";
	public static final String BLOCK_OPCODE_MATH_EQUAL = "=";
	public static final String BLOCK_OPCODE_MATH_GREAT = ">";
	public static final String BLOCK_OPCODE_MATH_LESS = "<";
	public static final String BLOCK_OPCODE_MINUS = "-";
	public static final String BLOCK_OPCODE_MULTIPLY = "*";
	public static final String BLOCK_OPCODE_NOT = "not";
	public static final String BLOCK_OPCODE_OR = "||";
	public static final String BLOCK_OPCODE_PLUS = "+";
	public static final String BLOCK_OPCODE_RANDOM = "random";
	public static final String BLOCK_OPCODE_REPEAT = "repeat";
	public static final String BLOCK_OPCODE_SET_BGCOLOR = "setBgColor";
	public static final String BLOCK_OPCODE_SET_CHECKED = "setChecked";
	public static final String BLOCK_OPCODE_SET_CLICKABLE = "setClickable";
	public static final String BLOCK_OPCODE_SET_ENABLE = "setEnable";
	public static final String BLOCK_OPCODE_SET_IMAGE = "setImage";
	public static final String BLOCK_OPCODE_SET_ROTATE = "setRotate";
	public static final String BLOCK_OPCODE_SET_TEXT = "setText";
	public static final String BLOCK_OPCODE_SET_TEXTCOLOR = "setTextColor";
	public static final String BLOCK_OPCODE_SET_VAR_BOOL = "setVarBoolean";
	public static final String BLOCK_OPCODE_SET_VAR_INT = "setVarInt";
	public static final String BLOCK_OPCODE_SET_VAR_STR = "setVarString";
	public static final String BLOCK_OPCODE_SET_VISIBLE = "setVisible";
	public static final String BLOCK_OPCODE_SPN_GET_SELECTION = "spnGetSelection";
	public static final String BLOCK_OPCODE_SPN_REFRESH = "spnRefresh";
	public static final String BLOCK_OPCODE_SPN_SET_DATA = "spnSetData";
	public static final String BLOCK_OPCODE_SPN_SET_SELECTION = "spnSetSelection";
	public static final String BLOCK_OPCODE_START_ACTIVITY = "startActivity";
	public static final String BLOCK_OPCODE_STR_EQUAL = "stringEquals";
	public static final String BLOCK_OPCODE_STR_INDEX = "stringIndex";
	public static final String BLOCK_OPCODE_STR_JOIN = "stringJoin";
	public static final String BLOCK_OPCODE_STR_LENGTH = "stringLength";
	public static final String BLOCK_OPCODE_STR_SUB = "stringSub";
	public static final String BLOCK_OPCODE_TOLOWERCASE = "toLowerCase";
	public static final String BLOCK_OPCODE_TOUPPERCASE = "toUpperCase";
	public static final String BLOCK_OPCODE_TO_NUMBER = "toNumber";
	public static final String BLOCK_OPCODE_TO_STRING = "toString";
	public static final String BLOCK_OPCODE_ADD_SOURCE_DIRECTLY = "addSourceDirectly";
	public static final String BLOCK_OPCODE_TRIM = "trim";
	public static final String BLOCK_OPCODE_TRUE = "true";
	public static final String BLOCK_OPCODE_VIBRATOR_ACTION = "vibratorAction";
	public static final String BLOCK_TYPE_BOOLEAN = "b";
	public static final String BLOCK_TYPE_COMMAND = " ";
	public static final String BLOCK_TYPE_DROPDOWN = "m";
	public static final String BLOCK_TYPE_FINAL = "f";
	public static final String BLOCK_TYPE_FINALLOOP = "cf";
	public static final String BLOCK_TYPE_FLOAT = "n";
	public static final String BLOCK_TYPE_HAT = "h";
	public static final String BLOCK_TYPE_IFELSE = "e";
	public static final String BLOCK_TYPE_INTEGER = "d";
	public static final String BLOCK_TYPE_LOOP = "c";
	public static final String BLOCK_TYPE_STRING = "s";
	
	public static int getBlockColor(String var0, String var1) {
		if(var1.equals("h")) {
			return -3636432;
		} else if (var1.equals("s.inputOnly")) {
			return BLOCK_COLOR_ARG_STR; // -1, same as 's'
		} else {
			byte var2 = -1;
			switch(var0.hashCode()) {
				case -2020761366:
				if(var0.equals("fileRemoveData")) {
					var2 = 81;
				}
				break;
				case -1998407506:
				if(var0.equals("listSetData")) {
					var2 = 62;
				}
				break;
				case -1920517885:
				if(var0.equals("setVarBoolean")) {
					var2 = 2;
				}
				break;
				case -1919300188:
				if(var0.equals("toNumber")) {
					var2 = 44;
				}
				break;
				case -1776922004:
				if(var0.equals("toString")) {
					var2 = 45;
				}
				break;
				case -1684072208:
				if(var0.equals("intentSetData")) {
					var2 = 73;
				}
				break;
				case -1573371685:
				if(var0.equals("stringJoin")) {
					var2 = 40;
				}
				break;
				case -1530840255:
				if(var0.equals("stringIndex")) {
					var2 = 41;
				}
				break;
				case -1528850031:
				if(var0.equals("startActivity")) {
					var2 = 76;
				}
				break;
				case -1526161572:
				if(var0.equals("setBgColor")) {
					var2 = 55;
				}
				break;
				case -1512519571:
				if(var0.equals("definedFunc")) {
					var2 = 89;
				}
				break;
				case -1440042085:
				if(var0.equals("spnSetSelection")) {
					var2 = 70;
				}
				break;
				case -1384861688:
				if(var0.equals("getAtListInt")) {
					var2 = 10;
				}
				break;
				case -1384851894:
				if(var0.equals("getAtListStr")) {
					var2 = 17;
				}
				break;
				case -1377080719:
				if(var0.equals("decreaseInt")) {
					var2 = 5;
				}
				break;
				case -1271141237:
				if(var0.equals("clearList")) {
					var2 = 14;
				}
				break;
				case -1249367264:
				if(var0.equals("getArg")) {
					var2 = 0;
				}
				break;
				case -1249347599:
				if(var0.equals("getVar")) {
					var2 = 1;
				}
				break;
				case -1192544266:
				if(var0.equals("ifElse")) {
					var2 = 24;
				}
				break;
				case -1137582698:
				if(var0.equals("toLowerCase")) {
					var2 = 48;
				}
				break;
				case -938285885:
				if(var0.equals("random")) {
					var2 = 38;
				}
				break;
				case -934531685:
				if(var0.equals("repeat")) {
					var2 = 20;
				}
				break;
				case -918173448:
				if(var0.equals("listGetCheckedPosition")) {
					var2 = 65;
				}
				break;
				case -869293886:
				if(var0.equals("finishActivity")) {
					var2 = 78;
				}
				break;
				case -854558288:
				if(var0.equals("setVisible")) {
					var2 = 51;
				}
				break;
				case -677662361:
				if(var0.equals("forever")) {
					var2 = 21;
				}
				break;
				case -578987803:
				if(var0.equals("setChecked")) {
					var2 = 60;
				}
				break;
				case -509946902:
				if(var0.equals("spnRefresh")) {
					var2 = 69;
				}
				break;
				case -425293664:
				if(var0.equals("setClickable")) {
					var2 = 52;
				}
				break;
				case -411705840:
				if(var0.equals("fileSetData")) {
					var2 = 80;
				}
				break;
				case -399551817:
				if(var0.equals("toUpperCase")) {
					var2 = 47;
				}
				break;
				case -353129373:
				if(var0.equals("calendarDiff")) {
					var2 = 86;
				}
				break;
				case -329562760:
				if(var0.equals("insertListInt")) {
					var2 = 8;
				}
				break;
				case -329552966:
				if(var0.equals("insertListStr")) {
					var2 = 16;
				}
				break;
				case -322651344:
				if(var0.equals("stringEquals")) {
					var2 = 43;
				}
				break;
				case -283328259:
				if(var0.equals("intentPutExtra")) {
					var2 = 75;
				}
				break;
				case -189292433:
				if(var0.equals("stringSub")) {
					var2 = 42;
				}
				break;
				case -133532073:
				if(var0.equals("stringLength")) {
					var2 = 39;
				}
				break;
				case -96313603:
				if(var0.equals("containListInt")) {
					var2 = 13;
				}
				break;
				case -96303809:
				if(var0.equals("containListStr")) {
					var2 = 19;
				}
				break;
				case -75125341:
				if(var0.equals("getText")) {
					var2 = 54;
				}
				break;
				case -60494417:
				if(var0.equals("vibratorAction")) {
					var2 = 87;
				}
				break;
				case 37:
				if(var0.equals("%")) {
					var2 = 37;
				}
				break;
				case 42:
				if(var0.equals("*")) {
					var2 = 35;
				}
				break;
				case 43:
				if(var0.equals("+")) {
					var2 = 33;
				}
				break;
				case 45:
				if(var0.equals("-")) {
					var2 = 34;
				}
				break;
				case 47:
				if(var0.equals("/")) {
					var2 = 36;
				}
				break;
				case 60:
				if(var0.equals("<")) {
					var2 = 27;
				}
				break;
				case 61:
				if(var0.equals("=")) {
					var2 = 28;
				}
				break;
				case 62:
				if(var0.equals(">")) {
					var2 = 29;
				}
				break;
				case 1216:
				if(var0.equals("&&")) {
					var2 = 30;
				}
				break;
				case 3357:
				if(var0.equals("if")) {
					var2 = 23;
				}
				break;
				case 3968:
				if(var0.equals("||")) {
					var2 = 31;
				}
				break;
				case 109267:
				if(var0.equals("not")) {
					var2 = 32;
				}
				break;
				case 3568674:
				if(var0.equals("trim")) {
					var2 = 46;
				}
				break;
				case 3569038:
				if(var0.equals("true")) {
					var2 = 25;
				}
				break;
				case 8255701:
				if(var0.equals("calendarFormat")) {
					var2 = 85;
				}
				break;
				case 27679870:
				if(var0.equals("calendarGetNow")) {
					var2 = 82;
				}
				break;
				case 94001407:
				if(var0.equals("break")) {
					var2 = 22;
				}
				break;
				case 97196323:
				if(var0.equals("false")) {
					var2 = 26;
				}
				break;
				case 182549637:
				if(var0.equals("setEnable")) {
					var2 = 49;
				}
				break;
				case 389111867:
				if(var0.equals("spnSetData")) {
					var2 = 68;
				}
				break;
				case 397166713:
				if(var0.equals("getEnable")) {
					var2 = 50;
				}
				break;
				case 404247683:
				if(var0.equals("calendarAdd")) {
					var2 = 83;
				}
				break;
				case 404265028:
				if(var0.equals("calendarSet")) {
					var2 = 84;
				}
				break;
				case 475815924:
				if(var0.equals("setTextColor")) {
					var2 = 56;
				}
				break;
				case 556217437:
				if(var0.equals("setRotate")) {
					var2 = 58;
				}
				break;
				case 573295520:
				if(var0.equals("listGetCheckedCount")) {
					var2 = 67;
				}
				break;
				case 657721930:
				if(var0.equals("setVarInt")) {
					var2 = 3;
				}
				break;
				case 725249532:
				if(var0.equals("intentSetAction")) {
					var2 = 72;
				}
				break;
				case 754442829:
				if(var0.equals("increaseInt")) {
					var2 = 4;
				}
				break;
				case 762282303:
				if(var0.equals("indexListInt")) {
					var2 = 11;
				}
				break;
				case 762292097:
				if(var0.equals("indexListStr")) {
					var2 = 18;
				}
				break;
				case 770834513:
				if(var0.equals("getRotate")) {
					var2 = 59;
				}
				break;
				case 845089750:
				if(var0.equals("setVarString")) {
					var2 = 6;
				}
				break;
				case 1160674468:
				if(var0.equals("lengthList")) {
					var2 = 12;
				}
				break;
				case 1240510514:
				if(var0.equals("intentSetScreen")) {
					var2 = 74;
				}
				break;
				case 1305932583:
				if(var0.equals("spnGetSelection")) {
					var2 = 71;
				}
				break;
				case 1343794064:
				if(var0.equals("listSetItemChecked")) {
					var2 = 64;
				}
				break;
				case 1395026457:
				if(var0.equals("setImage")) {
					var2 = 57;
				}
				break;
				case 1397501021:
				if(var0.equals("listRefresh")) {
					var2 = 63;
				}
				break;
				case 1470831563:
				if(var0.equals("intentGetString")) {
					var2 = 77;
				}
				break;
				case 1601394299:
				if(var0.equals("listGetCheckedPositions")) {
					var2 = 66;
				}
				break;
				case 1764351209:
				if(var0.equals("deleteList")) {
					var2 = 9;
				}
				break;
				case 1779174257:
				if(var0.equals("getChecked")) {
					var2 = 61;
				}
				break;
				case 1814870108:
				if(var0.equals("doToast")) {
					var2 = 88;
				}
				break;
				case 1823151876:
				if(var0.equals("fileGetData")) {
					var2 = 79;
				}
				break;
				case 1984984239:
				if(var0.equals("setText")) {
					var2 = 53;
				}
				break;
				case 2090179216:
				if(var0.equals("addListInt")) {
					var2 = 7;
				}
				break;
				case 2090189010:
				if(var0.equals("addListStr")) {
					var2 = 15;
				}
			}
			
			int var3;
			switch(var2) {
				case 0:
				var3 = -7711273;
				break;
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				var3 = -1147626;
				break;
				case 7:
				case 8:
				case 9:
				case 10:
				case 11:
				case 12:
				case 13:
				case 14:
				case 15:
				case 16:
				case 17:
				case 18:
				case 19:
				var3 = -3384542;
				break;
				case 20:
				case 21:
				case 22:
				case 23:
				case 24:
				var3 = -1988310;
				break;
				case 25:
				case 26:
				case 27:
				case 28:
				case 29:
				case 30:
				case 31:
				case 32:
				case 33:
				case 34:
				case 35:
				case 36:
				case 37:
				case 38:
				case 39:
				case 40:
				case 41:
				case 42:
				case 43:
				case 44:
				case 45:
				case 46:
				case 47:
				case 48:
				var3 = -10701022;
				break;
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
				case 56:
				case 57:
				case 58:
				case 59:
				case 60:
				case 61:
				case 62:
				case 63:
				case 64:
				case 65:
				case 66:
				case 67:
				case 68:
				case 69:
				case 70:
				case 71:
				case 72:
				case 73:
				case 74:
				case 75:
				case 76:
				case 77:
				case 78:
				case 79:
				case 80:
				case 81:
				case 82:
				case 83:
				case 84:
				case 85:
				case 86:
				case 87:
				var3 = -11899692;
				break;
				case 88:
				var3 = -13851166;
				break;
				case 89:
				var3 = -7711273;
				break;
				default:
				var3 = -13851166;
			}
			
			return var3;
		}
	}
	
	public static String getSpecStringId(String var0, String var1) {
		byte var2 = -1;
		switch(var0.hashCode()) {
			case -2020761366:
			if(var0.equals("fileRemoveData")) {
				var2 = 79;
			}
			break;
			case -1998407506:
			if(var0.equals("listSetData")) {
				var2 = 60;
			}
			break;
			case -1920517885:
			if(var0.equals("setVarBoolean")) {
				var2 = 0;
			}
			break;
			case -1919300188:
			if(var0.equals("toNumber")) {
				var2 = 42;
			}
			break;
			case -1776922004:
			if(var0.equals("toString")) {
				var2 = 43;
			}
			break;
			case -1684072208:
			if(var0.equals("intentSetData")) {
				var2 = 71;
			}
			break;
			case -1573371685:
			if(var0.equals("stringJoin")) {
				var2 = 38;
			}
			break;
			case -1530840255:
			if(var0.equals("stringIndex")) {
				var2 = 39;
			}
			break;
			case -1528850031:
			if(var0.equals("startActivity")) {
				var2 = 74;
			}
			break;
			case -1526161572:
			if(var0.equals("setBgColor")) {
				var2 = 53;
			}
			break;
			case -1440042085:
			if(var0.equals("spnSetSelection")) {
				var2 = 68;
			}
			break;
			case -1384861688:
			if(var0.equals("getAtListInt")) {
				var2 = 8;
			}
			break;
			case -1384851894:
			if(var0.equals("getAtListStr")) {
				var2 = 15;
			}
			break;
			case -1377080719:
			if(var0.equals("decreaseInt")) {
				var2 = 3;
			}
			break;
			case -1271141237:
			if(var0.equals("clearList")) {
				var2 = 12;
			}
			break;
			case -1192544266:
			if(var0.equals("ifElse")) {
				var2 = 22;
			}
			break;
			case -1137582698:
			if(var0.equals("toLowerCase")) {
				var2 = 46;
			}
			break;
			case -938285885:
			if(var0.equals("random")) {
				var2 = 36;
			}
			break;
			case -934531685:
			if(var0.equals("repeat")) {
				var2 = 18;
			}
			break;
			case -918173448:
			if(var0.equals("listGetCheckedPosition")) {
				var2 = 63;
			}
			break;
			case -869293886:
			if(var0.equals("finishActivity")) {
				var2 = 76;
			}
			break;
			case -854558288:
			if(var0.equals("setVisible")) {
				var2 = 49;
			}
			break;
			case -677662361:
			if(var0.equals("forever")) {
				var2 = 19;
			}
			break;
			case -578987803:
			if(var0.equals("setChecked")) {
				var2 = 58;
			}
			break;
			case -509946902:
			if(var0.equals("spnRefresh")) {
				var2 = 67;
			}
			break;
			case -425293664:
			if(var0.equals("setClickable")) {
				var2 = 50;
			}
			break;
			case -411705840:
			if(var0.equals("fileSetData")) {
				var2 = 78;
			}
			break;
			case -399551817:
			if(var0.equals("toUpperCase")) {
				var2 = 45;
			}
			break;
			case -353129373:
			if(var0.equals("calendarDiff")) {
				var2 = 84;
			}
			break;
			case -329562760:
			if(var0.equals("insertListInt")) {
				var2 = 6;
			}
			break;
			case -329552966:
			if(var0.equals("insertListStr")) {
				var2 = 14;
			}
			break;
			case -322651344:
			if(var0.equals("stringEquals")) {
				var2 = 41;
			}
			break;
			case -283328259:
			if(var0.equals("intentPutExtra")) {
				var2 = 73;
			}
			break;
			case -189292433:
			if(var0.equals("stringSub")) {
				var2 = 40;
			}
			break;
			case -133532073:
			if(var0.equals("stringLength")) {
				var2 = 37;
			}
			break;
			case -96313603:
			if(var0.equals("containListInt")) {
				var2 = 11;
			}
			break;
			case -96303809:
			if(var0.equals("containListStr")) {
				var2 = 17;
			}
			break;
			case -75125341:
			if(var0.equals("getText")) {
				var2 = 52;
			}
			break;
			case -60494417:
			if(var0.equals("vibratorAction")) {
				var2 = 85;
			}
			break;
			case 37:
			if(var0.equals("%")) {
				var2 = 35;
			}
			break;
			case 42:
			if(var0.equals("*")) {
				var2 = 33;
			}
			break;
			case 43:
			if(var0.equals("+")) {
				var2 = 31;
			}
			break;
			case 45:
			if(var0.equals("-")) {
				var2 = 32;
			}
			break;
			case 47:
			if(var0.equals("/")) {
				var2 = 34;
			}
			break;
			case 60:
			if(var0.equals("<")) {
				var2 = 25;
			}
			break;
			case 61:
			if(var0.equals("=")) {
				var2 = 26;
			}
			break;
			case 62:
			if(var0.equals(">")) {
				var2 = 27;
			}
			break;
			case 1216:
			if(var0.equals("&&")) {
				var2 = 28;
			}
			break;
			case 3357:
			if(var0.equals("if")) {
				var2 = 21;
			}
			break;
			case 3968:
			if(var0.equals("||")) {
				var2 = 29;
			}
			break;
			case 109267:
			if(var0.equals("not")) {
				var2 = 30;
			}
			break;
			case 3568674:
			if(var0.equals("trim")) {
				var2 = 44;
			}
			break;
			case 3569038:
			if(var0.equals("true")) {
				var2 = 23;
			}
			break;
			case 8255701:
			if(var0.equals("calendarFormat")) {
				var2 = 83;
			}
			break;
			case 27679870:
			if(var0.equals("calendarGetNow")) {
				var2 = 80;
			}
			break;
			case 94001407:
			if(var0.equals("break")) {
				var2 = 20;
			}
			break;
			case 97196323:
			if(var0.equals("false")) {
				var2 = 24;
			}
			break;
			case 182549637:
			if(var0.equals("setEnable")) {
				var2 = 47;
			}
			break;
			case 389111867:
			if(var0.equals("spnSetData")) {
				var2 = 66;
			}
			break;
			case 397166713:
			if(var0.equals("getEnable")) {
				var2 = 48;
			}
			break;
			case 404247683:
			if(var0.equals("calendarAdd")) {
				var2 = 81;
			}
			break;
			case 404265028:
			if(var0.equals("calendarSet")) {
				var2 = 82;
			}
			break;
			case 475815924:
			if(var0.equals("setTextColor")) {
				var2 = 54;
			}
			break;
			case 556217437:
			if(var0.equals("setRotate")) {
				var2 = 56;
			}
			break;
			case 573295520:
			if(var0.equals("listGetCheckedCount")) {
				var2 = 65;
			}
			break;
			case 657721930:
			if(var0.equals("setVarInt")) {
				var2 = 1;
			}
			break;
			case 725249532:
			if(var0.equals("intentSetAction")) {
				var2 = 70;
			}
			break;
			case 754442829:
			if(var0.equals("increaseInt")) {
				var2 = 2;
			}
			break;
			case 762282303:
			if(var0.equals("indexListInt")) {
				var2 = 9;
			}
			break;
			case 762292097:
			if(var0.equals("indexListStr")) {
				var2 = 16;
			}
			break;
			case 770834513:
			if(var0.equals("getRotate")) {
				var2 = 57;
			}
			break;
			case 845089750:
			if(var0.equals("setVarString")) {
				var2 = 4;
			}
			break;
			case 1160674468:
			if(var0.equals("lengthList")) {
				var2 = 10;
			}
			break;
			case 1240510514:
			if(var0.equals("intentSetScreen")) {
				var2 = 72;
			}
			break;
			case 1305932583:
			if(var0.equals("spnGetSelection")) {
				var2 = 69;
			}
			break;
			case 1343794064:
			if(var0.equals("listSetItemChecked")) {
				var2 = 62;
			}
			break;
			case 1395026457:
			if(var0.equals("setImage")) {
				var2 = 55;
			}
			break;
			case 1397501021:
			if(var0.equals("listRefresh")) {
				var2 = 61;
			}
			break;
			case 1470831563:
			if(var0.equals("intentGetString")) {
				var2 = 75;
			}
			break;
			case 1601394299:
			if(var0.equals("listGetCheckedPositions")) {
				var2 = 64;
			}
			break;
			case 1764351209:
			if(var0.equals("deleteList")) {
				var2 = 7;
			}
			break;
			case 1779174257:
			if(var0.equals("getChecked")) {
				var2 = 59;
			}
			break;
			case 1814870108:
			if(var0.equals("doToast")) {
				var2 = 86;
			}
			break;
			case 1823151876:
			if(var0.equals("fileGetData")) {
				var2 = 77;
			}
			break;
			case 1984984239:
			if(var0.equals("setText")) {
				var2 = 51;
			}
			break;
			case 2090179216:
			if(var0.equals("addListInt")) {
				var2 = 5;
			}
			break;
			case 2090189010:
			if(var0.equals("addListStr")) {
				var2 = 13;
			}
		}
		
		switch(var2) {
			case 0:
			return "set_var_bool";
			case 1:
			return "set_var_int";
			case 2:
			return "increase_int";
			case 3:
			return "decrease_int";
			case 4:
			return "set_var_str";
			case 5:
			return "add_list_int";
			case 6:
			return "insert_list_int";
			case 7:
			return "delete_list";
			case 8:
			return "get_at_list_int";
			case 9:
			return "indexof_list_int";
			case 10:
			return "length_list";
			case 11:
			return "contain_list_int";
			case 12:
			return "clear_list";
			case 13:
			return "add_list_str";
			case 14:
			return "insert_list_str";
			case 15:
			return "get_at_list_str";
			case 16:
			return "indexof_list_str";
			case 17:
			return "contain_list_str";
			case 18:
			return "repeat";
			case 19:
			return "forever";
			case 20:
			return "break";
			case 21:
			return "if";
			case 22:
			return "if_else";
			case 23:
			return "true";
			case 24:
			return "false";
			case 25:
			return "smaller";
			case 26:
			return "equal";
			case 27:
			return "bigger";
			case 28:
			return "and";
			case 29:
			return "or";
			case 30:
			return "not";
			case 31:
			return "plus";
			case 32:
			return "minus";
			case 33:
			return "times";
			case 34:
			return "divide";
			case 35:
			return "rest";
			case 36:
			return "random";
			case 37:
			return "string_length";
			case 38:
			return "string_join";
			case 39:
			return "string_index";
			case 40:
			return "string_sub";
			case 41:
			return "string_equals";
			case 42:
			return "to_number";
			case 43:
			return "to_string";
			case 44:
			return "trim";
			case 45:
			return "to_upper_case";
			case 46:
			return "to_lower_case";
			case 47:
			return "set_enable";
			case 48:
			return "get_enable";
			case 49:
			return "set_visible";
			case 50:
			return "set_clickable";
			case 51:
			return "set_text";
			case 52:
			return "get_text";
			case 53:
			return "set_bg_color";
			case 54:
			return "set_text_color";
			case 55:
			return "set_image";
			case 56:
			return "set_rotate";
			case 57:
			return "get_rotate";
			case 58:
			return "set_checked";
			case 59:
			return "get_checked";
			case 60:
			return "list_set_data";
			case 61:
			return "list_refresh";
			case 62:
			return "list_set_item_checked";
			case 63:
			return "list_get_checked_position";
			case 64:
			return "list_get_checked_positions";
			case 65:
			return "list_get_checked_count";
			case 66:
			return "spn_set_data";
			case 67:
			return "spn_refresh";
			case 68:
			return "spn_set_selection";
			case 69:
			return "spn_get_selection";
			case 70:
			return "intent_set_action";
			case 71:
			return "intent_set_data";
			case 72:
			return "intent_set_screen";
			case 73:
			return "intent_put_extra";
			case 74:
			return "start_activity";
			case 75:
			return "intent_get_string";
			case 76:
			return "finish_activity";
			case 77:
			return "file_get_data";
			case 78:
			return "file_set_data";
			case 79:
			return "file_remove_data";
			case 80:
			return "calendar_get_now";
			case 81:
			return "calendar_add";
			case 82:
			return "calendar_set";
			case 83:
			return "calendar_format";
			case 84:
			return "calendar_diff";
			case 85:
			return "vibrator_action";
			case 86:
			return "do_toast";
			default:
			return "";
		}
	}
}
