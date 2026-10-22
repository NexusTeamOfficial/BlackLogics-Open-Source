
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.util.ByteArray;

public final class RawAttribute extends BaseAttribute {
    private final mod.agus.jcoderz.dx.util.ByteArray data;

    private final mod.agus.jcoderz.dx.rop.cst.ConstantPool pool;

    public RawAttribute(String name, mod.agus.jcoderz.dx.util.ByteArray data, mod.agus.jcoderz.dx.rop.cst.ConstantPool pool) {
        super(name);

        if (data == null) {
            throw new NullPointerException("data == null");
        }

        this.data = data;
        this.pool = pool;
    }

    public RawAttribute(String name, mod.agus.jcoderz.dx.util.ByteArray data, int offset,
                        int length, mod.agus.jcoderz.dx.rop.cst.ConstantPool pool) {
        this(name, data.slice(offset, offset + length), pool);
    }

    public ByteArray getData() {
        return data;
    }

    @Override
    public int byteLength() {
        return data.size() + 6;
    }

    public ConstantPool getPool() {
        return pool;
    }
}
