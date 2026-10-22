
package mod.agus.jcoderz.dx.util;

import mod.agus.jcoderz.dex.util.ByteOutput;

public interface Output extends ByteOutput {
    public int getCursor();

    public void assertCursor(int expectedCursor);

    @Override
    public void writeByte(int value);

    public void writeShort(int value);

    public void writeInt(int value);

    public void writeLong(long value);

    public int writeUleb128(int value);

    public int writeSleb128(int value);

    public void write(ByteArray bytes);

    public void write(byte[] bytes, int offset, int length);

    public void write(byte[] bytes);

    public void writeZeroes(int count);

    public void alignTo(int alignment);
}
