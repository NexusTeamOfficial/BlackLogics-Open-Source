
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.ToHuman;
import java.io.PrintWriter;

public abstract class EncodedMember implements ToHuman {
    private final int accessFlags;

    public EncodedMember(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public final int getAccessFlags() {
        return accessFlags;
    }

    public abstract CstString getName();

    public abstract void debugPrint(PrintWriter out, boolean verbose);

    public abstract void addContents(mod.agus.jcoderz.dx.dex.file.DexFile file);

    public abstract int encode(DexFile file, AnnotatedOutput out,
                               int lastIndex, int dumpSeq);
}
