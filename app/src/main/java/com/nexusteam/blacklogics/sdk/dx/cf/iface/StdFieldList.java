
package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class StdFieldList extends FixedSizeList implements FieldList {
    public StdFieldList(int size) {
        super(size);
    }

    @Override
    public mod.agus.jcoderz.dx.cf.iface.Field get(int n) {
        return (mod.agus.jcoderz.dx.cf.iface.Field) get0(n);
    }

    public void set(int n, Field field) {
        set0(n, field);
    }
}
