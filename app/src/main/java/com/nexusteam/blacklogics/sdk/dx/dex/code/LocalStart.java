
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;

public final class LocalStart extends ZeroSizeInsn {
    private final mod.agus.jcoderz.dx.rop.code.RegisterSpec local;

    public static String localString(mod.agus.jcoderz.dx.rop.code.RegisterSpec spec) {
        return spec.regString() + ' ' + spec.getLocalItem().toString() + ": " +
            spec.getTypeBearer().toHuman();
    }

    public LocalStart(SourcePosition position, mod.agus.jcoderz.dx.rop.code.RegisterSpec local) {
        super(position);

        if (local == null) {
            throw new NullPointerException("local == null");
        }

        this.local = local;
    }

    @Override
    public mod.agus.jcoderz.dx.dex.code.DalvInsn withRegisterOffset(int delta) {
        return new LocalStart(getPosition(), local.withOffset(delta));
    }

    @Override
    public mod.agus.jcoderz.dx.dex.code.DalvInsn withRegisters(RegisterSpecList registers) {
        return new LocalStart(getPosition(), local);
    }

    public RegisterSpec getLocal() {
        return local;
    }

    @Override
    protected String argString() {
        return local.toString();
    }

    @Override
    protected String listingString0(boolean noteIndices) {
        return "local-start " + localString(local);
    }

    @Override
    public DalvInsn withMapper(RegisterMapper mapper) {
      return new LocalStart(getPosition(), mapper.map(local));
    }
}
