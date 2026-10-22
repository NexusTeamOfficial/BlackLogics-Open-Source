
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.util.BitIntSet;
import mod.agus.jcoderz.dx.util.IntSet;
import mod.agus.jcoderz.dx.util.ListIntSet;


public final class SetFactory {

    private static final int DOMFRONT_SET_THRESHOLD_SIZE = 3072;

    private static final int INTERFERENCE_SET_THRESHOLD_SIZE = 3072;

    private static final int LIVENESS_SET_THRESHOLD_SIZE = 3072;


     static IntSet makeDomFrontSet(int szBlocks) {
        return szBlocks <= DOMFRONT_SET_THRESHOLD_SIZE
                ? new BitIntSet(szBlocks)
                : new ListIntSet();
    }

    public static IntSet makeInterferenceSet(int countRegs) {
        return countRegs <= INTERFERENCE_SET_THRESHOLD_SIZE
                ? new BitIntSet(countRegs)
                : new ListIntSet();
    }

     static IntSet makeLivenessSet(int countRegs) {
        return countRegs <= LIVENESS_SET_THRESHOLD_SIZE
                ? new BitIntSet(countRegs)
                : new ListIntSet();
    }
}
