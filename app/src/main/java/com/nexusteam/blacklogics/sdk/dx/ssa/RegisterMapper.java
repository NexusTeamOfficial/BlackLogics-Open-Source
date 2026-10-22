
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecSet;

public abstract class RegisterMapper {
    public abstract int getNewRegisterCount();

    public abstract mod.agus.jcoderz.dx.rop.code.RegisterSpec map(mod.agus.jcoderz.dx.rop.code.RegisterSpec registerSpec);

    public final mod.agus.jcoderz.dx.rop.code.RegisterSpecList map(mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources) {
        int sz = sources.size();
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList newSources = new RegisterSpecList(sz);

        for (int i = 0; i < sz; i++) {
            newSources.set(i, map(sources.get(i)));
        }

        newSources.setImmutable();


        return newSources.equals(sources) ? sources : newSources;
    }

    public final mod.agus.jcoderz.dx.rop.code.RegisterSpecSet map(mod.agus.jcoderz.dx.rop.code.RegisterSpecSet sources) {
        int sz = sources.getMaxSize();
        mod.agus.jcoderz.dx.rop.code.RegisterSpecSet newSources = new RegisterSpecSet(getNewRegisterCount());

        for (int i = 0; i < sz; i++) {
            RegisterSpec registerSpec = sources.get(i);
            if (registerSpec != null) {
                newSources.put(map(registerSpec));
            }
        }

        newSources.setImmutable();


        return newSources.equals(sources) ? sources : newSources;
    }
}
