
package mod.agus.jcoderz.dx.rop.code;

public interface TranslationAdvice {
    public boolean hasConstantOperation(mod.agus.jcoderz.dx.rop.code.Rop opcode,
                                        mod.agus.jcoderz.dx.rop.code.RegisterSpec sourceA, RegisterSpec sourceB);

    public boolean requiresSourcesInOrder(Rop opcode, RegisterSpecList sources);

    public int getMaxOptimalRegisterCount();
}
