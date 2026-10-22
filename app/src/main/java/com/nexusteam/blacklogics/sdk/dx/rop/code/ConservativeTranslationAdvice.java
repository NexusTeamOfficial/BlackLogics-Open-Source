
package mod.agus.jcoderz.dx.rop.code;

public final class ConservativeTranslationAdvice
        implements TranslationAdvice {
    public static final ConservativeTranslationAdvice THE_ONE =
        new ConservativeTranslationAdvice();

    private ConservativeTranslationAdvice() {

    }

    @Override
    public boolean hasConstantOperation(mod.agus.jcoderz.dx.rop.code.Rop opcode,
                                        RegisterSpec sourceA, RegisterSpec sourceB) {
        return false;
    }

    @Override
    public boolean requiresSourcesInOrder(Rop opcode,
                                          RegisterSpecList sources) {
        return false;
    }

    @Override
    public int getMaxOptimalRegisterCount() {
        return Integer.MAX_VALUE;
    }
}
