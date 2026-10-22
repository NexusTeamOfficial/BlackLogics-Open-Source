
package mod.agus.jcoderz.dx.ssa.back;

import mod.agus.jcoderz.dx.ssa.SetFactory;
import mod.agus.jcoderz.dx.util.IntSet;
import java.util.ArrayList;

public class InterferenceGraph {
    private final ArrayList<IntSet> interference;

    public InterferenceGraph(int countRegs) {
        interference = new ArrayList<IntSet>(countRegs);

        for (int i = 0; i < countRegs; i++) {
            interference.add(SetFactory.makeInterferenceSet(countRegs));
        }
    }

    public void add(int regV, int regW) {
        ensureCapacity(Math.max(regV, regW) + 1);

        interference.get(regV).add(regW);
        interference.get(regW).add(regV);
    }

    public void dumpToStdout() {
        int oldRegCount = interference.size();

        for (int i = 0; i < oldRegCount; i++) {
            StringBuilder sb = new StringBuilder();

            sb.append("Reg " + i + ":" + interference.get(i).toString());

            System.out.println(sb.toString());
        }
    }

    public void mergeInterferenceSet(int reg, IntSet set) {
        if (reg < interference.size()) {
            set.merge(interference.get(reg));
        }
    }

    private void ensureCapacity(int size) {
        int countRegs = interference.size();

        interference.ensureCapacity(size);

        for (int i = countRegs; i < size; i++) {
            interference.add(SetFactory.makeInterferenceSet(size));
        }
    }
}
