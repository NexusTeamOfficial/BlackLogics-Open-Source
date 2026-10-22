
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.util.IntList;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;

public class BasicRegisterMapper extends RegisterMapper {
    private final IntList oldToNew;

    private int runningCountNewRegisters;

    public BasicRegisterMapper(int countOldRegisters) {
        oldToNew = new IntList(countOldRegisters);
    }

    @Override
    public int getNewRegisterCount() {
        return runningCountNewRegisters;
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.RegisterSpec map(RegisterSpec registerSpec) {
        if (registerSpec == null) {
            return null;
        }

        int newReg;
        try {
            newReg = oldToNew.get(registerSpec.getReg());
        } catch (IndexOutOfBoundsException ex) {
            newReg = -1;
        }

        if (newReg < 0) {
            throw new RuntimeException("no mapping specified for register");
        }

        return registerSpec.withReg(newReg);
    }

    public int oldToNew(int oldReg) {
        if (oldReg >= oldToNew.size()) {
            return -1;
        }

        return oldToNew.get(oldReg);
    }

    public String toHuman() {
        StringBuilder sb = new StringBuilder();

        sb.append("Old\tNew\n");
        int sz = oldToNew.size();

        for (int i = 0; i < sz; i++) {
            sb.append(i);
            sb.append('\t');
            sb.append(oldToNew.get(i));
            sb.append('\n');
        }

        sb.append("new reg count:");

        sb.append(runningCountNewRegisters);
        sb.append('\n');

        return sb.toString();
    }

    public void addMapping(int oldReg, int newReg, int category) {
        if (oldReg >= oldToNew.size()) {

            for (int i = oldReg - oldToNew.size(); i >= 0; i--) {
                oldToNew.add(-1);
            }
        }

        oldToNew.set(oldReg, newReg);

        if (runningCountNewRegisters < (newReg + category)) {
            runningCountNewRegisters = newReg + category;
        }
    }
}
