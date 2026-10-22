
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.MutabilityControl;
import java.util.HashMap;

public final class LocalVariableInfo
        extends MutabilityControl {
    private final int regCount;

    private final mod.agus.jcoderz.dx.rop.code.RegisterSpecSet emptySet;

    private final mod.agus.jcoderz.dx.rop.code.RegisterSpecSet[] blockStarts;

    private final HashMap<mod.agus.jcoderz.dx.rop.code.Insn, RegisterSpec> insnAssignments;

    public LocalVariableInfo(RopMethod method) {
        if (method == null) {
            throw new NullPointerException("method == null");
        }

        BasicBlockList blocks = method.getBlocks();
        int maxLabel = blocks.getMaxLabel();

        this.regCount = blocks.getRegCount();
        this.emptySet = new mod.agus.jcoderz.dx.rop.code.RegisterSpecSet(regCount);
        this.blockStarts = new mod.agus.jcoderz.dx.rop.code.RegisterSpecSet[maxLabel];
        this.insnAssignments =
            new HashMap<mod.agus.jcoderz.dx.rop.code.Insn, RegisterSpec>(blocks.getInstructionCount());

        emptySet.setImmutable();
    }

    public void setStarts(int label, mod.agus.jcoderz.dx.rop.code.RegisterSpecSet specs) {
        throwIfImmutable();

        if (specs == null) {
            throw new NullPointerException("specs == null");
        }

        try {
            blockStarts[label] = specs;
        } catch (ArrayIndexOutOfBoundsException ex) {

            throw new IllegalArgumentException("bogus label");
        }
    }

    public boolean mergeStarts(int label, mod.agus.jcoderz.dx.rop.code.RegisterSpecSet specs) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecSet start = getStarts0(label);
        boolean changed = false;

        if (start == null) {
            setStarts(label, specs);
            return true;
        }

        mod.agus.jcoderz.dx.rop.code.RegisterSpecSet newStart = start.mutableCopy();
        if (start.size() != 0) {
            newStart.intersect(specs, true);
        } else {
            newStart = specs.mutableCopy();
        }

        if (start.equals(newStart)) {
            return false;
        }

        newStart.setImmutable();
        setStarts(label, newStart);

        return true;
    }

    public mod.agus.jcoderz.dx.rop.code.RegisterSpecSet getStarts(int label) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecSet result = getStarts0(label);

        return (result != null) ? result : emptySet;
    }

    public mod.agus.jcoderz.dx.rop.code.RegisterSpecSet getStarts(BasicBlock block) {
        return getStarts(block.getLabel());
    }

    public mod.agus.jcoderz.dx.rop.code.RegisterSpecSet mutableCopyOfStarts(int label) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecSet result = getStarts0(label);

        return (result != null) ?
            result.mutableCopy() : new mod.agus.jcoderz.dx.rop.code.RegisterSpecSet(regCount);
    }

    public void addAssignment(mod.agus.jcoderz.dx.rop.code.Insn insn, RegisterSpec spec) {
        throwIfImmutable();

        if (insn == null) {
            throw new NullPointerException("insn == null");
        }

        if (spec == null) {
            throw new NullPointerException("spec == null");
        }

        insnAssignments.put(insn, spec);
    }

    public RegisterSpec getAssignment(Insn insn) {
        return insnAssignments.get(insn);
    }

    public int getAssignmentCount() {
        return insnAssignments.size();
    }

    public void debugDump() {
        for (int label = 0 ; label < blockStarts.length; label++) {
            if (blockStarts[label] == null) {
                continue;
            }

            if (blockStarts[label] == emptySet) {
                System.out.printf("%04x: empty set\n", label);
            } else {
                System.out.printf("%04x: %s\n", label, blockStarts[label]);
            }
        }
    }

    private RegisterSpecSet getStarts0(int label) {
        try {
            return blockStarts[label];
        } catch (ArrayIndexOutOfBoundsException ex) {

            throw new IllegalArgumentException("bogus label");
        }
    }
}
