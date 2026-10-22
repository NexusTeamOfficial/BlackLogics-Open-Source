
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;

public final class CodeAddress extends ZeroSizeInsn {
    private final boolean bindsClosely;

    public CodeAddress(mod.agus.jcoderz.dx.rop.code.SourcePosition position) {
        this(position, false);
    }

    public CodeAddress(SourcePosition position, boolean bindsClosely) {
        super(position);
        this.bindsClosely = bindsClosely;
    }

    @Override
    public final DalvInsn withRegisters(RegisterSpecList registers) {
        return new CodeAddress(getPosition());
    }

    @Override
    protected String argString() {
        return null;
    }

    @Override
    protected String listingString0(boolean noteIndices) {
        return "code-address";
    }

    public boolean getBindsClosely() {
        return bindsClosely;
    }
}
