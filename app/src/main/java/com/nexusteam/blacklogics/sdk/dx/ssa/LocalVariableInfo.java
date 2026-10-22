
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.util.MutabilityControl;
import java.util.HashMap;
import java.util.List;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecSet;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;

public class LocalVariableInfo         extends MutabilityControl {
    private final int regCount;

    private final mod.agus.jcoderz.dx.rop.code.RegisterSpecSet emptySet;

    private final mod.agus.jcoderz.dx.rop.code.RegisterSpecSet[] blockStarts;

    private final HashMap<mod.agus.jcoderz.dx.ssa.SsaInsn, mod.agus.jcoderz.dx.rop.code.RegisterSpec> insnAssignments;

    public LocalVariableInfo(SsaMethod method) {
        if (method == null) {
            throw new NullPointerException("method == null");
        }

        List<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> blocks = method.getBlocks();

        this.regCount = method.getRegCount();
        this.emptySet = new mod.agus.jcoderz.dx.rop.code.RegisterSpecSet(regCount);
        this.blockStarts = new mod.agus.jcoderz.dx.rop.code.RegisterSpecSet[blocks.size()];
        this.insnAssignments =
            new HashMap<mod.agus.jcoderz.dx.ssa.SsaInsn, mod.agus.jcoderz.dx.rop.code.RegisterSpec>();

        emptySet.setImmutable();
    }

    public void setStarts(int index, mod.agus.jcoderz.dx.rop.code.RegisterSpecSet specs) {
        throwIfImmutable();

        if (specs == null) {
            throw new NullPointerException("specs == null");
        }

        try {
            blockStarts[index] = specs;
        } catch (ArrayIndexOutOfBoundsException ex) {

            throw new IllegalArgumentException("bogus index");
        }
    }

    public boolean mergeStarts(int index, mod.agus.jcoderz.dx.rop.code.RegisterSpecSet specs) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecSet start = getStarts0(index);
        boolean changed = false;

        if (start == null) {
            setStarts(index, specs);
            return true;
        }

        mod.agus.jcoderz.dx.rop.code.RegisterSpecSet newStart = start.mutableCopy();
        newStart.intersect(specs, true);

        if (start.equals(newStart)) {
            return false;
        }

        newStart.setImmutable();
        setStarts(index, newStart);

        return true;
    }

    public mod.agus.jcoderz.dx.rop.code.RegisterSpecSet getStarts(int index) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecSet result = getStarts0(index);

        return (result != null) ? result : emptySet;
    }

    public mod.agus.jcoderz.dx.rop.code.RegisterSpecSet getStarts(SsaBasicBlock block) {
        return getStarts(block.getIndex());
    }

    public mod.agus.jcoderz.dx.rop.code.RegisterSpecSet mutableCopyOfStarts(int index) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecSet result = getStarts0(index);

        return (result != null) ?
            result.mutableCopy() : new mod.agus.jcoderz.dx.rop.code.RegisterSpecSet(regCount);
    }

    public void addAssignment(mod.agus.jcoderz.dx.ssa.SsaInsn insn, mod.agus.jcoderz.dx.rop.code.RegisterSpec spec) {
        throwIfImmutable();

        if (insn == null) {
            throw new NullPointerException("insn == null");
        }

        if (spec == null) {
            throw new NullPointerException("spec == null");
        }

        insnAssignments.put(insn, spec);
    }

    public RegisterSpec getAssignment(SsaInsn insn) {
        return insnAssignments.get(insn);
    }

    public int getAssignmentCount() {
        return insnAssignments.size();
    }

    public void debugDump() {
        for (int index = 0 ; index < blockStarts.length; index++) {
            if (blockStarts[index] == null) {
                continue;
            }

            if (blockStarts[index] == emptySet) {
                System.out.printf("%04x: empty set\n", index);
            } else {
                System.out.printf("%04x: %s\n", index, blockStarts[index]);
            }
        }
    }

    private RegisterSpecSet getStarts0(int index) {
        try {
            return blockStarts[index];
        } catch (ArrayIndexOutOfBoundsException ex) {

            throw new IllegalArgumentException("bogus index");
        }
    }
}
