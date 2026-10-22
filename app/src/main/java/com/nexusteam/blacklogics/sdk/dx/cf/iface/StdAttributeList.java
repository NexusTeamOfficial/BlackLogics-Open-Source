
package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class StdAttributeList extends FixedSizeList
        implements AttributeList {
    public StdAttributeList(int size) {
        super(size);
    }

    @Override
    public mod.agus.jcoderz.dx.cf.iface.Attribute get(int n) {
        return (mod.agus.jcoderz.dx.cf.iface.Attribute) get0(n);
    }

    @Override
    public int byteLength() {
        int sz = size();
        int result = 2;

        for (int i = 0; i < sz; i++) {
            result += get(i).byteLength();
        }

        return result;
    }

    @Override
    public mod.agus.jcoderz.dx.cf.iface.Attribute findFirst(String name) {
        int sz = size();

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.cf.iface.Attribute att = get(i);
            if (att.getName().equals(name)) {
                return att;
            }
        }

        return null;
    }

    @Override
    public mod.agus.jcoderz.dx.cf.iface.Attribute findNext(mod.agus.jcoderz.dx.cf.iface.Attribute attrib) {
        int sz = size();
        int at;

        outer: {
            for (at = 0; at < sz; at++) {
                mod.agus.jcoderz.dx.cf.iface.Attribute att = get(at);
                if (att == attrib) {
                    break outer;
                }
            }

            return null;
        }

        String name = attrib.getName();

        for (at++; at < sz; at++) {
            mod.agus.jcoderz.dx.cf.iface.Attribute att = get(at);
            if (att.getName().equals(name)) {
                return att;
            }
        }

        return null;
    }

    public void set(int n, Attribute attribute) {
        set0(n, attribute);
    }
}
