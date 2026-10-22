
package mod.agus.jcoderz.dx.dex.code;

import java.util.HashSet;

import mod.agus.jcoderz.dx.rop.type.Type;

public interface CatchBuilder {
    public CatchTable build();

    public boolean hasAnyCatches();

    public HashSet<Type> getCatchTypes();
}
