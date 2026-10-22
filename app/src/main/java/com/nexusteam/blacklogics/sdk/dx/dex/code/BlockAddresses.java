
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;

public final class BlockAddresses {
    private final mod.agus.jcoderz.dx.dex.code.CodeAddress[] starts;

    private final mod.agus.jcoderz.dx.dex.code.CodeAddress[] lasts;

    private final mod.agus.jcoderz.dx.dex.code.CodeAddress[] ends;

    public BlockAddresses(mod.agus.jcoderz.dx.rop.code.RopMethod method) {
        mod.agus.jcoderz.dx.rop.code.BasicBlockList blocks = method.getBlocks();
        int maxLabel = blocks.getMaxLabel();

        this.starts = new mod.agus.jcoderz.dx.dex.code.CodeAddress[maxLabel];
        this.lasts = new mod.agus.jcoderz.dx.dex.code.CodeAddress[maxLabel];
        this.ends = new mod.agus.jcoderz.dx.dex.code.CodeAddress[maxLabel];

        setupArrays(method);
    }

    public mod.agus.jcoderz.dx.dex.code.CodeAddress getStart(mod.agus.jcoderz.dx.rop.code.BasicBlock block) {
        return starts[block.getLabel()];
    }

    public mod.agus.jcoderz.dx.dex.code.CodeAddress getStart(int label) {
        return starts[label];
    }

    public mod.agus.jcoderz.dx.dex.code.CodeAddress getLast(mod.agus.jcoderz.dx.rop.code.BasicBlock block) {
        return lasts[block.getLabel()];
    }

    public mod.agus.jcoderz.dx.dex.code.CodeAddress getLast(int label) {
        return lasts[label];
    }

    public mod.agus.jcoderz.dx.dex.code.CodeAddress getEnd(mod.agus.jcoderz.dx.rop.code.BasicBlock block) {
        return ends[block.getLabel()];
    }

    public mod.agus.jcoderz.dx.dex.code.CodeAddress getEnd(int label) {
        return ends[label];
    }

    private void setupArrays(RopMethod method) {
        BasicBlockList blocks = method.getBlocks();
        int sz = blocks.size();

        for (int i = 0; i < sz; i++) {
            BasicBlock one = blocks.get(i);
            int label = one.getLabel();
            Insn insn = one.getInsns().get(0);

            starts[label] = new mod.agus.jcoderz.dx.dex.code.CodeAddress(insn.getPosition());

            SourcePosition pos = one.getLastInsn().getPosition();

            lasts[label] = new mod.agus.jcoderz.dx.dex.code.CodeAddress(pos);
            ends[label] = new CodeAddress(pos);
        }
    }
}
