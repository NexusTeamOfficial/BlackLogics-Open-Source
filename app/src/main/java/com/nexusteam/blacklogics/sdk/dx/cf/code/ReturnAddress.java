
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.Hex;

public final class ReturnAddress implements mod.agus.jcoderz.dx.rop.type.TypeBearer {
    private final int subroutineAddress;

    public ReturnAddress(int subroutineAddress) {
        if (subroutineAddress < 0) {
            throw new IllegalArgumentException("subroutineAddress < 0");
        }

        this.subroutineAddress = subroutineAddress;
    }

    @Override
    public String toString() {
        return ("<addr:" + Hex.u2(subroutineAddress) + ">");
    }

    @Override
    public String toHuman() {
        return toString();
    }

    @Override
    public mod.agus.jcoderz.dx.rop.type.Type getType() {
        return mod.agus.jcoderz.dx.rop.type.Type.RETURN_ADDRESS;
    }

    @Override
    public TypeBearer getFrameType() {
        return this;
    }

    @Override
    public int getBasicType() {
        return mod.agus.jcoderz.dx.rop.type.Type.RETURN_ADDRESS.getBasicType();
    }

    @Override
    public int getBasicFrameType() {
        return Type.RETURN_ADDRESS.getBasicFrameType();
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ReturnAddress)) {
            return false;
        }

        return subroutineAddress == ((ReturnAddress) other).subroutineAddress;
    }

    @Override
    public int hashCode() {
        return subroutineAddress;
    }

    public int getSubroutineAddress() {
        return subroutineAddress;
    }
}
