
package mod.agus.jcoderz.dx.dex.cf;

import mod.agus.jcoderz.dx.dex.code.PositionList;
import java.io.PrintStream;

public class CfOptions {
    public int positionInfo = PositionList.LINES;

    public boolean localInfo = false;

    public boolean strictNameCheck = true;

    public boolean optimize = false;

    public String optimizeListFile = null;

    public String dontOptimizeListFile = null;

    public boolean statistics;

    public PrintStream warn = System.err;
}
