
package mod.agus.jcoderz.dx.dex.code;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.dex.DexOptions;

public final class OutputCollector {
    private final mod.agus.jcoderz.dx.dex.code.OutputFinisher finisher;

    private ArrayList<mod.agus.jcoderz.dx.dex.code.DalvInsn> suffix;

    public OutputCollector(DexOptions dexOptions, int initialCapacity, int suffixInitialCapacity,
                           int regCount, int paramSize) {
        this.finisher = new mod.agus.jcoderz.dx.dex.code.OutputFinisher(dexOptions, initialCapacity, regCount, paramSize);
        this.suffix = new ArrayList<mod.agus.jcoderz.dx.dex.code.DalvInsn>(suffixInitialCapacity);
    }

    public void add(mod.agus.jcoderz.dx.dex.code.DalvInsn insn) {
        finisher.add(insn);
    }

    public mod.agus.jcoderz.dx.dex.code.DalvInsn get(int at) {
        if (at >= finisher.size() || at < 0) {
            return null;
        } else {
            return finisher.get(at);
        }
    }

    public int size() {
        return finisher.size();
    }

    public void reverseBranch(int which, CodeAddress newTarget) {
        finisher.reverseBranch(which, newTarget);
    }

    public void addSuffix(DalvInsn insn) {
        suffix.add(insn);
    }

    public OutputFinisher getFinisher() {
        if (suffix == null) {
            throw new UnsupportedOperationException("already processed");
        }

        appendSuffixToOutput();
        return finisher;
    }

    private void appendSuffixToOutput() {
        int size = suffix.size();

        for (int i = 0; i < size; i++) {
            finisher.add(suffix.get(i));
        }

        suffix = null;
    }
}
