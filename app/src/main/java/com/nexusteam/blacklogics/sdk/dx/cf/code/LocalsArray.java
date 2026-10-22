
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.MutabilityControl;
import mod.agus.jcoderz.dx.util.ToHuman;

public abstract class LocalsArray extends MutabilityControl implements ToHuman {

    protected LocalsArray(boolean mutable) {
        super(mutable);
    }

    public abstract LocalsArray copy();

    public abstract void annotate(ExceptionWithContext ex);

    public abstract void makeInitialized(Type type);

    public abstract int getMaxLocals();

    public abstract void set(int idx, mod.agus.jcoderz.dx.rop.type.TypeBearer type);

    public abstract void set(RegisterSpec spec);

    public abstract void invalidate(int idx);

    public abstract mod.agus.jcoderz.dx.rop.type.TypeBearer getOrNull(int idx);

    public abstract mod.agus.jcoderz.dx.rop.type.TypeBearer get(int idx);

    public abstract mod.agus.jcoderz.dx.rop.type.TypeBearer getCategory1(int idx);

    public abstract TypeBearer getCategory2(int idx);

    public abstract LocalsArray merge(LocalsArray other);

    public abstract LocalsArraySet mergeWithSubroutineCaller
            (LocalsArray other, int predLabel);

    protected abstract OneLocalsArray getPrimary();

}
