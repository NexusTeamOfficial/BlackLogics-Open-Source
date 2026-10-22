
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.util.Hex;
import java.util.ArrayList;
import java.util.List;

import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;

public final class PhiInsn extends mod.agus.jcoderz.dx.ssa.SsaInsn {
    private final int ropResultReg;

    private final ArrayList<Operand> operands = new ArrayList<Operand>();

    private mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources;

    public PhiInsn(mod.agus.jcoderz.dx.rop.code.RegisterSpec resultReg, mod.agus.jcoderz.dx.ssa.SsaBasicBlock block) {
        super(resultReg, block);
        ropResultReg = resultReg.getReg();
    }

    public PhiInsn(final int resultReg, final mod.agus.jcoderz.dx.ssa.SsaBasicBlock block) {
        super(mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(resultReg, Type.VOID), block);
        ropResultReg = resultReg;
    }

    @Override
    public PhiInsn clone() {
        throw new UnsupportedOperationException("can't clone phi");
    }

    public void updateSourcesToDefinitions(mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth) {
        for (Operand o : operands) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec def
                = ssaMeth.getDefinitionForRegister(
                    o.regSpec.getReg()).getResult();

            o.regSpec = o.regSpec.withType(def.getType());
        }

        sources = null;
    }

    public void changeResultType(TypeBearer type, LocalItem local) {
        setResult(mod.agus.jcoderz.dx.rop.code.RegisterSpec.makeLocalOptional(
                          getResult().getReg(), type, local));
    }

    public int getRopResultReg() {
        return ropResultReg;
    }

    public void addPhiOperand(mod.agus.jcoderz.dx.rop.code.RegisterSpec registerSpec,
                              mod.agus.jcoderz.dx.ssa.SsaBasicBlock predBlock) {
        operands.add(new Operand(registerSpec, predBlock.getIndex(),
                predBlock.getRopLabel()));


        sources = null;
    }

    public void removePhiRegister(mod.agus.jcoderz.dx.rop.code.RegisterSpec registerSpec) {
        ArrayList<Operand> operandsToRemove = new ArrayList<Operand>();
        for (Operand o : operands) {
            if (o.regSpec.getReg() == registerSpec.getReg()) {
                operandsToRemove.add(o);
            }
        }

        operands.removeAll(operandsToRemove);


        sources = null;
    }

    public int predBlockIndexForSourcesIndex(int sourcesIndex) {
        return operands.get(sourcesIndex).blockIndex;
    }

    @Override
    public Rop getOpcode() {
        return null;
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.Insn getOriginalRopInsn() {
        return null;
    }

    @Override
    public boolean canThrow() {
        return false;
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.RegisterSpecList getSources() {
        if (sources != null) {
            return sources;
        }

        if (operands.size() == 0) {

            return mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY;
        }

        int szSources = operands.size();
        sources = new RegisterSpecList(szSources);

        for (int i = 0; i < szSources; i++) {
            Operand o = operands.get(i);

            sources.set(i, o.regSpec);
        }

        sources.setImmutable();
        return sources;
    }

    @Override
    public boolean isRegASource(int reg) {

        for (Operand o : operands) {
            if (o.regSpec.getReg() == reg) {
                return true;
            }
        }

        return false;
    }

    public boolean areAllOperandsEqual() {
        if (operands.size() == 0 ) {

            return true;
        }

        int firstReg = operands.get(0).regSpec.getReg();
        for (Operand o : operands) {
            if (firstReg != o.regSpec.getReg()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public final void mapSourceRegisters(RegisterMapper mapper) {
        for (Operand o : operands) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec old = o.regSpec;
            o.regSpec = mapper.map(old);
            if (old != o.regSpec) {
                getBlock().getParent().onSourceChanged(this, old, o.regSpec);
            }
        }
        sources = null;
    }

    @Override
    public Insn toRopInsn() {
        throw new IllegalArgumentException(
                "Cannot convert phi insns to rop form");
    }

    public List<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> predBlocksForReg(int reg, SsaMethod ssaMeth) {
        ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> ret = new ArrayList<SsaBasicBlock>();

        for (Operand o : operands) {
            if (o.regSpec.getReg() == reg) {
                ret.add(ssaMeth.getBlocks().get(o.blockIndex));
            }
        }

        return ret;
    }

    @Override
    public boolean isPhiOrMove() {
        return true;
    }

    @Override
    public boolean hasSideEffect() {
        return Optimizer.getPreserveLocals() && getLocalAssignment() != null;
    }

    @Override
    public void accept(SsaInsn.Visitor v) {
        v.visitPhiInsn(this);
    }

    @Override
    public String toHuman() {
        return toHumanWithInline(null);
    }

    protected final String toHumanWithInline(String extra) {
        StringBuilder sb = new StringBuilder(80);

        sb.append(SourcePosition.NO_INFO);
        sb.append(": phi");

        if (extra != null) {
            sb.append("(");
            sb.append(extra);
            sb.append(")");
        }

        mod.agus.jcoderz.dx.rop.code.RegisterSpec result = getResult();

        if (result == null) {
            sb.append(" .");
        } else {
            sb.append(" ");
            sb.append(result.toHuman());
        }

        sb.append(" <-");

        int sz = getSources().size();
        if (sz == 0) {
            sb.append(" .");
        } else {
            for (int i = 0; i < sz; i++) {
                sb.append(" ");
                sb.append(sources.get(i).toHuman()
                        + "[b="
                        + Hex.u2(operands.get(i).ropLabel)  + "]");
            }
        }

        return sb.toString();
    }

    private static class Operand {
        public mod.agus.jcoderz.dx.rop.code.RegisterSpec regSpec;
        public final int blockIndex;
        public final int ropLabel;

        public Operand(RegisterSpec regSpec, int blockIndex, int ropLabel) {
            this.regSpec = regSpec;
            this.blockIndex = blockIndex;
            this.ropLabel = ropLabel;
        }
    }

    public static interface Visitor {
        public void visitPhiInsn(PhiInsn insn);
    }
}
