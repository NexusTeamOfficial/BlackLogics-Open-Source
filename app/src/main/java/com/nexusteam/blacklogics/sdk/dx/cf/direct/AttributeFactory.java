
package mod.agus.jcoderz.dx.cf.direct;

import mod.agus.jcoderz.dx.cf.attrib.RawAttribute;
import mod.agus.jcoderz.dx.cf.iface.Attribute;
import mod.agus.jcoderz.dx.cf.iface.ParseException;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;
import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public class AttributeFactory {
    public static final int CTX_CLASS = 0;

    public static final int CTX_FIELD = 1;

    public static final int CTX_METHOD = 2;

    public static final int CTX_CODE = 3;

    public static final int CTX_COUNT = 4;

    public AttributeFactory() {

    }

    public final mod.agus.jcoderz.dx.cf.iface.Attribute parse(DirectClassFile cf, int context, int offset,
                                                              mod.agus.jcoderz.dx.cf.iface.ParseObserver observer) {
        if (cf == null) {
            throw new NullPointerException("cf == null");
        }

        if ((context < 0) || (context >= CTX_COUNT)) {
            throw new IllegalArgumentException("bad context");
        }

        mod.agus.jcoderz.dx.rop.cst.CstString name = null;

        try {
            mod.agus.jcoderz.dx.util.ByteArray bytes = cf.getBytes();
            mod.agus.jcoderz.dx.rop.cst.ConstantPool pool = cf.getConstantPool();
            int nameIdx = bytes.getUnsignedShort(offset);
            int length = bytes.getInt(offset + 2);

            name = (CstString) pool.get(nameIdx);

            if (observer != null) {
                observer.parsed(bytes, offset, 2,
                                "name: " + name.toHuman());
                observer.parsed(bytes, offset + 2, 4,
                                "length: " + mod.agus.jcoderz.dx.util.Hex.u4(length));
            }

            return parse0(cf, context, name.getString(), offset + 6, length,
                          observer);
        } catch (ParseException ex) {
            ex.addContext("...while parsing " +
                    ((name != null) ? (name.toHuman() + " ") : "") +
                    "attribute at offset " + Hex.u4(offset));
            throw ex;
        }
    }

    protected mod.agus.jcoderz.dx.cf.iface.Attribute parse0(DirectClassFile cf, int context, String name,
                                                            int offset, int length,
                                                            ParseObserver observer) {
        ByteArray bytes = cf.getBytes();
        ConstantPool pool = cf.getConstantPool();
        Attribute result = new RawAttribute(name, bytes, offset, length, pool);

        if (observer != null) {
            observer.parsed(bytes, offset, length, "attribute data");
        }

        return result;
    }
}
