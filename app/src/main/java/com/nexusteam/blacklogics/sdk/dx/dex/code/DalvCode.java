
package mod.agus.jcoderz.dx.dex.code;

import java.util.HashSet;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.type.Type;

public final class DalvCode {
    private final int positionInfo;

    private mod.agus.jcoderz.dx.dex.code.OutputFinisher unprocessedInsns;

    private CatchBuilder unprocessedCatches;

    private CatchTable catches;

    private mod.agus.jcoderz.dx.dex.code.PositionList positions;

    private mod.agus.jcoderz.dx.dex.code.LocalList locals;

    private mod.agus.jcoderz.dx.dex.code.DalvInsnList insns;

    public DalvCode(int positionInfo, OutputFinisher unprocessedInsns,
            CatchBuilder unprocessedCatches) {
        if (unprocessedInsns == null) {
            throw new NullPointerException("unprocessedInsns == null");
        }

        if (unprocessedCatches == null) {
            throw new NullPointerException("unprocessedCatches == null");
        }

        this.positionInfo = positionInfo;
        this.unprocessedInsns = unprocessedInsns;
        this.unprocessedCatches = unprocessedCatches;
        this.catches = null;
        this.positions = null;
        this.locals = null;
        this.insns = null;
    }

    private void finishProcessingIfNecessary() {
        if (insns != null) {
            return;
        }

        insns = unprocessedInsns.finishProcessingAndGetList();
        positions = mod.agus.jcoderz.dx.dex.code.PositionList.make(insns, positionInfo);
        locals = mod.agus.jcoderz.dx.dex.code.LocalList.make(insns);
        catches = unprocessedCatches.build();


        unprocessedInsns = null;
        unprocessedCatches = null;
    }

    public void assignIndices(AssignIndicesCallback callback) {
        unprocessedInsns.assignIndices(callback);
    }

    public boolean hasPositions() {
        return (positionInfo != mod.agus.jcoderz.dx.dex.code.PositionList.NONE)
            && unprocessedInsns.hasAnyPositionInfo();
    }

    public boolean hasLocals() {
        return unprocessedInsns.hasAnyLocalInfo();
    }

    public boolean hasAnyCatches() {
        return unprocessedCatches.hasAnyCatches();
    }

    public HashSet<Type> getCatchTypes() {
        return unprocessedCatches.getCatchTypes();
    }

    public HashSet<mod.agus.jcoderz.dx.rop.cst.Constant> getInsnConstants() {
        return unprocessedInsns.getAllConstants();
    }

    public DalvInsnList getInsns() {
        finishProcessingIfNecessary();
        return insns;
    }

    public CatchTable getCatches() {
        finishProcessingIfNecessary();
        return catches;
    }

    public PositionList getPositions() {
        finishProcessingIfNecessary();
        return positions;
    }

    public LocalList getLocals() {
        finishProcessingIfNecessary();
        return locals;
    }

    public static interface AssignIndicesCallback {
        public int getIndex(Constant cst);
    }
}
