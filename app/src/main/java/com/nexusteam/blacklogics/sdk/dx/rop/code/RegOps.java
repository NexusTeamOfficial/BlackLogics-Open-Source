
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.util.Hex;

public final class RegOps {
    public static final int NOP = 1;

    public static final int MOVE = 2;

    public static final int MOVE_PARAM = 3;

    public static final int MOVE_EXCEPTION = 4;

    public static final int CONST = 5;

    public static final int GOTO = 6;

    public static final int IF_EQ = 7;

    public static final int IF_NE = 8;

    public static final int IF_LT = 9;

    public static final int IF_GE = 10;

    public static final int IF_LE = 11;

    public static final int IF_GT = 12;

    public static final int SWITCH = 13;

    public static final int ADD = 14;

    public static final int SUB = 15;

    public static final int MUL = 16;

    public static final int DIV = 17;

    public static final int REM = 18;

    public static final int NEG = 19;

    public static final int AND = 20;

    public static final int OR = 21;

    public static final int XOR = 22;

    public static final int SHL = 23;

    public static final int SHR = 24;

    public static final int USHR = 25;

    public static final int NOT = 26;

    public static final int CMPL = 27;

    public static final int CMPG = 28;

    public static final int CONV = 29;

    public static final int TO_BYTE = 30;

    public static final int TO_CHAR = 31;

    public static final int TO_SHORT = 32;

    public static final int RETURN = 33;

    public static final int ARRAY_LENGTH = 34;

    public static final int THROW = 35;

    public static final int MONITOR_ENTER = 36;

    public static final int MONITOR_EXIT = 37;

    public static final int AGET = 38;

    public static final int APUT = 39;

    public static final int NEW_INSTANCE = 40;

    public static final int NEW_ARRAY = 41;

    public static final int FILLED_NEW_ARRAY = 42;

    public static final int CHECK_CAST = 43;

    public static final int INSTANCE_OF = 44;

    public static final int GET_FIELD = 45;

    public static final int GET_STATIC = 46;

    public static final int PUT_FIELD = 47;

    public static final int PUT_STATIC = 48;

    public static final int INVOKE_STATIC = 49;

    public static final int INVOKE_VIRTUAL = 50;

    public static final int INVOKE_SUPER = 51;

    public static final int INVOKE_DIRECT = 52;

    public static final int INVOKE_INTERFACE = 53;

    public static final int MARK_LOCAL = 54;

    public static final int MOVE_RESULT = 55;

    public static final int MOVE_RESULT_PSEUDO = 56;

    public static final int FILL_ARRAY_DATA = 57;

    public static final int INVOKE_POLYMORPHIC = 58;

    public static final int INVOKE_CUSTOM = 59;

    private RegOps() {

    }

    public static String opName(int opcode) {
        switch (opcode) {
            case NOP: return "nop";
            case MOVE: return "move";
            case MOVE_PARAM: return "move-param";
            case MOVE_EXCEPTION: return "move-exception";
            case CONST: return "const";
            case GOTO: return "goto";
            case IF_EQ: return "if-eq";
            case IF_NE: return "if-ne";
            case IF_LT: return "if-lt";
            case IF_GE: return "if-ge";
            case IF_LE: return "if-le";
            case IF_GT: return "if-gt";
            case SWITCH: return "switch";
            case ADD: return "add";
            case SUB: return "sub";
            case MUL: return "mul";
            case DIV: return "div";
            case REM: return "rem";
            case NEG: return "neg";
            case AND: return "and";
            case OR: return "or";
            case XOR: return "xor";
            case SHL: return "shl";
            case SHR: return "shr";
            case USHR: return "ushr";
            case NOT: return "not";
            case CMPL: return "cmpl";
            case CMPG: return "cmpg";
            case CONV: return "conv";
            case TO_BYTE: return "to-byte";
            case TO_CHAR: return "to-char";
            case TO_SHORT: return "to-short";
            case RETURN: return "return";
            case ARRAY_LENGTH: return "array-length";
            case THROW: return "throw";
            case MONITOR_ENTER: return "monitor-enter";
            case MONITOR_EXIT: return "monitor-exit";
            case AGET: return "aget";
            case APUT: return "aput";
            case NEW_INSTANCE: return "new-instance";
            case NEW_ARRAY: return "new-array";
            case FILLED_NEW_ARRAY: return "filled-new-array";
            case CHECK_CAST: return "check-cast";
            case INSTANCE_OF: return "instance-of";
            case GET_FIELD: return "get-field";
            case GET_STATIC: return "get-static";
            case PUT_FIELD: return "put-field";
            case PUT_STATIC: return "put-static";
            case INVOKE_STATIC: return "invoke-static";
            case INVOKE_VIRTUAL: return "invoke-virtual";
            case INVOKE_SUPER: return "invoke-super";
            case INVOKE_DIRECT: return "invoke-direct";
            case INVOKE_INTERFACE: return "invoke-interface";
            case MOVE_RESULT: return "move-result";
            case MOVE_RESULT_PSEUDO: return "move-result-pseudo";
            case FILL_ARRAY_DATA: return "fill-array-data";
            case INVOKE_POLYMORPHIC: return "invoke-polymorphic";
            case INVOKE_CUSTOM: return "invoke-custom";
        }

        return "unknown-" + Hex.u1(opcode);
    }

    public static int flippedIfOpcode(final int opcode) {
        switch (opcode) {
            case RegOps.IF_EQ:
            case RegOps.IF_NE:
                return opcode;
            case RegOps.IF_LT:
                return RegOps.IF_GT;
            case RegOps.IF_GE:
                return RegOps.IF_LE;
            case RegOps.IF_LE:
                return RegOps.IF_GE;
            case RegOps.IF_GT:
                return RegOps.IF_LT;
            default:
                throw new RuntimeException("Unrecognized IF regop: " + opcode);
        }
    }
}
