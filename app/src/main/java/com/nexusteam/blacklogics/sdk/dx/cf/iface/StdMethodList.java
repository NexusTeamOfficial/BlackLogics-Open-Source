
package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class StdMethodList extends FixedSizeList implements MethodList {
    public StdMethodList(int size) {
        super(size);
    }

    @Override
    public mod.agus.jcoderz.dx.cf.iface.Method get(int n) {
        return (mod.agus.jcoderz.dx.cf.iface.Method) get0(n);
    }

    public void set(int n, Method method) {
        set0(n, method);
    }
}
