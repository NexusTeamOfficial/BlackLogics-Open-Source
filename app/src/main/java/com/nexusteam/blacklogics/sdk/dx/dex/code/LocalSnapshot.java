
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecSet;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;

public final class LocalSnapshot extends ZeroSizeInsn {
    private final mod.agus.jcoderz.dx.rop.code.RegisterSpecSet locals;

    public LocalSnapshot(SourcePosition position, mod.agus.jcoderz.dx.rop.code.RegisterSpecSet locals) {
        super(position);

        if (locals == null) {
            throw new NullPointerException("locals == null");
        }

        this.locals = locals;
    }

    @Override
    public mod.agus.jcoderz.dx.dex.code.DalvInsn withRegisterOffset(int delta) {
        return new LocalSnapshot(getPosition(), locals.withOffset(delta));
    }

    @Override
    public mod.agus.jcoderz.dx.dex.code.DalvInsn withRegisters(RegisterSpecList registers) {
        return new LocalSnapshot(getPosition(), locals);
    }

    public RegisterSpecSet getLocals() {
        return locals;
    }

    @Override
    protected String argString() {
        return locals.toString();
    }

    @Override
    protected String listingString0(boolean noteIndices) {
        int sz = locals.size();
        int max = locals.getMaxSize();
        StringBuilder sb = new StringBuilder(100 + sz * 40);

        sb.append("local-snapshot");

        for (int i = 0; i < max; i++) {
            RegisterSpec spec = locals.get(i);
            if (spec != null) {
                sb.append("\n  ");
                sb.append(LocalStart.localString(spec));
            }
        }

        return sb.toString();
    }

    @Override
    public DalvInsn withMapper(RegisterMapper mapper) {
      return new LocalSnapshot(getPosition(), mapper.map(locals));
    }
}
