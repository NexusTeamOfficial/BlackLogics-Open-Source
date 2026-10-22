
package mod.agus.jcoderz.dx.dex;

import mod.agus.jcoderz.dex.DexFormat;
import mod.agus.jcoderz.dx.dex.code.DalvInsnList;
import java.io.PrintStream;

public final class DexOptions {

    public static final boolean ALIGN_64BIT_REGS_SUPPORT = true;

    public boolean ALIGN_64BIT_REGS_IN_OUTPUT_FINISHER = ALIGN_64BIT_REGS_SUPPORT;

    public int minSdkVersion = DexFormat.API_NO_EXTENDED_OPCODES;

    public boolean forceJumbo = false;

    public boolean allowAllInterfaceMethodInvokes = false;

    public final PrintStream err;

    public DexOptions() {
        err = System.err;
    }

    public DexOptions(PrintStream stream) {
        err = stream;
    }

    public String getMagic() {
        return DexFormat.apiToMagic(minSdkVersion);
    }

    public boolean apiIsSupported(int apiLevel) {


        return minSdkVersion >= apiLevel;
    }
}
