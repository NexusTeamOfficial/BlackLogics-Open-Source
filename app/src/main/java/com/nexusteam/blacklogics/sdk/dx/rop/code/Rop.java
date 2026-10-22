
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.Hex;

public final class Rop {
    public static final int BRANCH_MIN = 1;

    public static final int BRANCH_NONE = 1;

    public static final int BRANCH_RETURN = 2;

    public static final int BRANCH_GOTO = 3;

    public static final int BRANCH_IF = 4;

    public static final int BRANCH_SWITCH = 5;

    public static final int BRANCH_THROW = 6;

    public static final int BRANCH_MAX = 6;

    private final int opcode;

    private final Type result;

    private final TypeList sources;

    private final TypeList exceptions;

    private final int branchingness;

    private final boolean isCallLike;

    private final String nickname;

    public Rop(int opcode, Type result, TypeList sources,
               TypeList exceptions, int branchingness, boolean isCallLike,
               String nickname) {
        if (result == null) {
            throw new NullPointerException("result == null");
        }

        if (sources == null) {
            throw new NullPointerException("sources == null");
        }

        if (exceptions == null) {
            throw new NullPointerException("exceptions == null");
        }

        if ((branchingness < BRANCH_MIN) || (branchingness > BRANCH_MAX)) {
            throw new IllegalArgumentException("invalid branchingness: " + branchingness);
        }

        if ((exceptions.size() != 0) && (branchingness != BRANCH_THROW)) {
            throw new IllegalArgumentException("exceptions / branchingness " +
                                               "mismatch");
        }

        this.opcode = opcode;
        this.result = result;
        this.sources = sources;
        this.exceptions = exceptions;
        this.branchingness = branchingness;
        this.isCallLike = isCallLike;
        this.nickname = nickname;
    }

    public Rop(int opcode, Type result, TypeList sources,
               TypeList exceptions, int branchingness, String nickname) {
        this(opcode, result, sources, exceptions, branchingness, false,
             nickname);
    }

    public Rop(int opcode, Type result, TypeList sources, int branchingness,
               String nickname) {
        this(opcode, result, sources, StdTypeList.EMPTY, branchingness, false,
             nickname);
    }

    public Rop(int opcode, Type result, TypeList sources, String nickname) {
        this(opcode, result, sources, StdTypeList.EMPTY, Rop.BRANCH_NONE,
             false, nickname);
    }

    public Rop(int opcode, Type result, TypeList sources, TypeList exceptions,
               String nickname) {
        this(opcode, result, sources, exceptions, Rop.BRANCH_THROW, false,
             nickname);
    }

    public Rop(int opcode, TypeList sources, TypeList exceptions) {
        this(opcode, Type.VOID, sources, exceptions, Rop.BRANCH_THROW, true,
             null);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {

            return true;
        }

        if (!(other instanceof Rop)) {
            return false;
        }

        Rop rop = (Rop) other;

        return (opcode == rop.opcode) &&
            (branchingness == rop.branchingness) &&
            (result == rop.result) &&
            sources.equals(rop.sources) &&
            exceptions.equals(rop.exceptions);
    }

    @Override
    public int hashCode() {
        int h = (opcode * 31) + branchingness;
        h = (h * 31) + result.hashCode();
        h = (h * 31) + sources.hashCode();
        h = (h * 31) + exceptions.hashCode();

        return h;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(40);

        sb.append("Rop{");

        sb.append(RegOps.opName(opcode));

        if (result != Type.VOID) {
            sb.append(" ");
            sb.append(result);
        } else {
            sb.append(" .");
        }

        sb.append(" <-");

        int sz = sources.size();
        if (sz == 0) {
            sb.append(" .");
        } else {
            for (int i = 0; i < sz; i++) {
                sb.append(' ');
                sb.append(sources.getType(i));
            }
        }

        if (isCallLike) {
            sb.append(" call");
        }

        sz = exceptions.size();
        if (sz != 0) {
            sb.append(" throws");
            for (int i = 0; i < sz; i++) {
                sb.append(' ');
                Type one = exceptions.getType(i);
                if (one == Type.THROWABLE) {
                    sb.append("<any>");
                } else {
                    sb.append(exceptions.getType(i));
                }
            }
        } else {
            switch (branchingness) {
                case BRANCH_NONE:   sb.append(" flows"); break;
                case BRANCH_RETURN: sb.append(" returns"); break;
                case BRANCH_GOTO:   sb.append(" gotos"); break;
                case BRANCH_IF:     sb.append(" ifs"); break;
                case BRANCH_SWITCH: sb.append(" switches"); break;
                default: sb.append(" " + Hex.u1(branchingness)); break;
            }
        }

        sb.append('}');

        return sb.toString();
    }

    public int getOpcode() {
        return opcode;
    }

    public Type getResult() {
        return result;
    }

    public TypeList getSources() {
        return sources;
    }

    public TypeList getExceptions() {
        return exceptions;
    }

    public int getBranchingness() {
        return branchingness;
    }

    public boolean isCallLike() {
        return isCallLike;
    }


    public boolean isCommutative() {
        switch (opcode) {
            case RegOps.AND:
            case RegOps.OR:
            case RegOps.XOR:
            case RegOps.ADD:
            case RegOps.MUL:
                return true;
            default:
                return false;
        }
    }

    public String getNickname() {
        if (nickname != null) {
            return nickname;
        }

        return toString();
    }

    public final boolean canThrow() {
        return (exceptions.size() != 0);
    }
}
