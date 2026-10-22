
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.util.IntList;
import mod.agus.jcoderz.dx.util.MutabilityControl;

public final class SwitchList extends MutabilityControl {
    private final mod.agus.jcoderz.dx.util.IntList values;

    private final mod.agus.jcoderz.dx.util.IntList targets;

    private int size;

    public SwitchList(int size) {
        super(true);
        this.values = new mod.agus.jcoderz.dx.util.IntList(size);
        this.targets = new mod.agus.jcoderz.dx.util.IntList(size + 1);
        this.size = size;
    }

    @Override
    public void setImmutable() {
        values.setImmutable();
        targets.setImmutable();
        super.setImmutable();
    }

    public int size() {
        return size;
    }

    public int getValue(int n) {
        return values.get(n);
    }

    public int getTarget(int n) {
        return targets.get(n);
    }

    public int getDefaultTarget() {
        return targets.get(size);
    }

    public mod.agus.jcoderz.dx.util.IntList getTargets() {
        return targets;
    }

    public IntList getValues() {
        return values;
    }

    public void setDefaultTarget(int target) {
        throwIfImmutable();

        if (target < 0) {
            throw new IllegalArgumentException("target < 0");
        }

        if (targets.size() != size) {
            throw new RuntimeException("non-default elements not all set");
        }

        targets.add(target);
    }

    public void add(int value, int target) {
        throwIfImmutable();

        if (target < 0) {
            throw new IllegalArgumentException("target < 0");
        }

        values.add(value);
        targets.add(target);
    }

    public void removeSuperfluousDefaults() {
        throwIfImmutable();

        int sz = size;

        if (sz != (targets.size() - 1)) {
            throw new IllegalArgumentException("incomplete instance");
        }

        int defaultTarget = targets.get(sz);
        int at = 0;

        for (int i = 0; i < sz; i++) {
            int target = targets.get(i);
            if (target != defaultTarget) {
                if (i != at) {
                    targets.set(at, target);
                    values.set(at, values.get(i));
                }
                at++;
            }
        }

        if (at != sz) {
            values.shrink(at);
            targets.set(at, defaultTarget);
            targets.shrink(at + 1);
            size = at;
        }
    }
}
