
package mod.agus.jcoderz.dx.rop.type;

import mod.agus.jcoderz.dx.util.ToHuman;

public interface TypeBearer
        extends ToHuman {
    public Type getType();

    public TypeBearer getFrameType();

    public int getBasicType();

    public int getBasicFrameType();

    public boolean isConstant();
}
