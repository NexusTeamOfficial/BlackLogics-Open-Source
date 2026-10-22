
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.util.BitIntSet;
import mod.agus.jcoderz.dx.util.IntSet;
import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.ssa.back.InterferenceGraph;

public class InterferenceRegisterMapper extends BasicRegisterMapper {
    private final ArrayList<BitIntSet> newRegInterference;

    private final mod.agus.jcoderz.dx.ssa.back.InterferenceGraph oldRegInterference;

    public InterferenceRegisterMapper(InterferenceGraph oldRegInterference,
                                      int countOldRegisters) {
        super(countOldRegisters);

        newRegInterference = new ArrayList<BitIntSet>();
        this.oldRegInterference = oldRegInterference;
    }

    @Override
    public void addMapping(int oldReg, int newReg, int category) {
        super.addMapping(oldReg, newReg, category);

        addInterfence(newReg, oldReg);

        if (category == 2) {
            addInterfence(newReg + 1, oldReg);
        }
    }

    public boolean interferes(int oldReg, int newReg, int category) {
        if (newReg >= newRegInterference.size()) {
            return false;
        } else {
            IntSet existing = newRegInterference.get(newReg);

            if (existing == null) {
                return false;
            } else if (category == 1) {
                return existing.has(oldReg);
            } else {
                return existing.has(oldReg)
                        || (interferes(oldReg, newReg+1, category-1));
            }
        }
    }

    public boolean interferes(mod.agus.jcoderz.dx.rop.code.RegisterSpec oldSpec, int newReg) {
        return interferes(oldSpec.getReg(), newReg, oldSpec.getCategory());
    }

    private void addInterfence(int newReg, int oldReg) {
        newRegInterference.ensureCapacity(newReg + 1);

        while (newReg >= newRegInterference.size()) {
            newRegInterference.add(new BitIntSet(newReg +1));
        }

        oldRegInterference.mergeInterferenceSet(
                oldReg, newRegInterference.get(newReg));
    }

    public boolean areAnyPinned(RegisterSpecList oldSpecs,
                                int newReg, int targetCategory) {
        int sz = oldSpecs.size();

        for (int i = 0; i < sz; i++) {
            RegisterSpec oldSpec = oldSpecs.get(i);
            int r = oldToNew(oldSpec.getReg());

            if (r == newReg
                || (oldSpec.getCategory() == 2 && (r + 1) == newReg)
                || (targetCategory == 2 && (r == newReg + 1))) {
                return true;
            }
        }

        return false;
    }
}
