
package mod.agus.jcoderz.dx.io.instructions;

public interface CodeOutput extends CodeCursor {
    public void write(short codeUnit);

    public void write(short u0, short u1);

    public void write(short u0, short u1, short u2);

    public void write(short u0, short u1, short u2, short u3);

    public void write(short u0, short u1, short u2, short u3, short u4);

    public void writeInt(int value);

    public void writeLong(long value);

    public void write(byte[] data);

    public void write(short[] data);

    public void write(int[] data);

    public void write(long[] data);
}
