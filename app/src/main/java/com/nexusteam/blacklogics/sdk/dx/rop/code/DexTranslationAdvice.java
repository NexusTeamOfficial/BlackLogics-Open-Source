
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;

public final class DexTranslationAdvice
        implements TranslationAdvice {
    public static final DexTranslationAdvice THE_ONE =
        new DexTranslationAdvice();

    public static final DexTranslationAdvice NO_SOURCES_IN_ORDER =
        new DexTranslationAdvice(true);

    private static final int MIN_INVOKE_IN_ORDER = 6;

    private final boolean disableSourcesInOrder;

    private DexTranslationAdvice() {
        disableSourcesInOrder = false;
    }

    private DexTranslationAdvice(boolean disableInvokeRange) {
        this.disableSourcesInOrder = disableInvokeRange;
    }

    @Override
    public boolean hasConstantOperation(mod.agus.jcoderz.dx.rop.code.Rop opcode,
                                        RegisterSpec sourceA, RegisterSpec sourceB) {
        if (sourceA.getType() != Type.INT) {
            return false;
        }


        if (! (sourceB.getTypeBearer() instanceof mod.agus.jcoderz.dx.rop.cst.CstInteger)) {

            if (sourceA.getTypeBearer() instanceof mod.agus.jcoderz.dx.rop.cst.CstInteger &&
                    opcode.getOpcode() == RegOps.SUB) {
                mod.agus.jcoderz.dx.rop.cst.CstInteger cst = (mod.agus.jcoderz.dx.rop.cst.CstInteger) sourceA.getTypeBearer();
                return cst.fitsIn16Bits();
            } else {
                return false;
            }
        }

        mod.agus.jcoderz.dx.rop.cst.CstInteger cst = (mod.agus.jcoderz.dx.rop.cst.CstInteger) sourceB.getTypeBearer();

        switch (opcode.getOpcode()) {

            case RegOps.REM:
            case RegOps.ADD:
            case RegOps.MUL:
            case RegOps.DIV:
            case RegOps.AND:
            case RegOps.OR:
            case RegOps.XOR:
                return cst.fitsIn16Bits();

            case RegOps.SHL:
            case RegOps.SHR:
            case RegOps.USHR:
                return cst.fitsIn8Bits();

            case RegOps.SUB:
                mod.agus.jcoderz.dx.rop.cst.CstInteger cst2 = CstInteger.make(-cst.getValue());
                return cst2.fitsIn16Bits();
            default:
                return false;
        }
    }

    @Override
    public boolean requiresSourcesInOrder(Rop opcode,
                                          mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources) {

        return !disableSourcesInOrder && opcode.isCallLike()
                && totalRopWidth(sources) >= MIN_INVOKE_IN_ORDER;
    }

    private int totalRopWidth(RegisterSpecList sources) {
        int sz = sources.size();
        int total = 0;

        for (int i = 0; i < sz; i++) {
            total += sources.get(i).getCategory();
        }

        return total;
    }

    @Override
    public int getMaxOptimalRegisterCount() {
        return 16;
    }
}
