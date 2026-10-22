
package mod.agus.jcoderz.dx.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public final class ByteArray {
    private final byte[] bytes;

    private final int start;

    private final int size;

    public ByteArray(byte[] bytes, int start, int end) {
        if (bytes == null) {
            throw new NullPointerException("bytes == null");
        }

        if (start < 0) {
            throw new IllegalArgumentException("start < 0");
        }

        if (end < start) {
            throw new IllegalArgumentException("end < start");
        }

        if (end > bytes.length) {
            throw new IllegalArgumentException("end > bytes.length");
        }

        this.bytes = bytes;
        this.start = start;
        this.size = end - start;
    }

    public ByteArray(byte[] bytes) {
        this(bytes, 0, bytes.length);
    }

    public int size() {
        return size;
    }

    public ByteArray slice(int start, int end) {
        checkOffsets(start, end);
        byte[] slicedOut = Arrays.copyOfRange(bytes, start, end);
        return new ByteArray(slicedOut);
    }

    public int underlyingOffset(int offset) {
        return start + offset;
    }

    public int getByte(int off) {
        checkOffsets(off, off + 1);
        return getByte0(off);
    }

    public int getShort(int off) {
        checkOffsets(off, off + 2);
        return (getByte0(off) << 8) | getUnsignedByte0(off + 1);
    }

    public int getInt(int off) {
        checkOffsets(off, off + 4);
        return (getByte0(off) << 24) |
            (getUnsignedByte0(off + 1) << 16) |
            (getUnsignedByte0(off + 2) << 8) |
            getUnsignedByte0(off + 3);
    }

    public long getLong(int off) {
        checkOffsets(off, off + 8);
        int part1 = (getByte0(off) << 24) |
            (getUnsignedByte0(off + 1) << 16) |
            (getUnsignedByte0(off + 2) << 8) |
            getUnsignedByte0(off + 3);
        int part2 = (getByte0(off + 4) << 24) |
            (getUnsignedByte0(off + 5) << 16) |
            (getUnsignedByte0(off + 6) << 8) |
            getUnsignedByte0(off + 7);

        return (part2 & 0xffffffffL) | ((long) part1) << 32;
    }

    public int getUnsignedByte(int off) {
        checkOffsets(off, off + 1);
        return getUnsignedByte0(off);
    }

    public int getUnsignedShort(int off) {
        checkOffsets(off, off + 2);
        return (getUnsignedByte0(off) << 8) | getUnsignedByte0(off + 1);
    }

    public void getBytes(byte[] out, int offset) {
        if ((out.length - offset) < size) {
            throw new IndexOutOfBoundsException("(out.length - offset) < " +
                                                "size()");
        }

        System.arraycopy(bytes, start, out, offset, size);
    }

    private void checkOffsets(int s, int e) {
        if ((s < 0) || (e < s) || (e > size)) {
            throw new IllegalArgumentException("bad range: " + s + ".." + e +
                                               "; actual size " + size);
        }
    }

    private int getByte0(int off) {
        return bytes[start + off];
    }

    private int getUnsignedByte0(int off) {
        return bytes[start + off] & 0xff;
    }

    public MyDataInputStream makeDataInputStream() {
        return new MyDataInputStream(makeInputStream());
    }

    public MyInputStream makeInputStream() {
        return new MyInputStream();
    }

    public interface GetCursor {
        int getCursor();
    }

    public class MyInputStream extends InputStream {
        private int cursor;

        private int mark;

        public MyInputStream() {
            cursor = 0;
            mark = 0;
        }

        @Override
        public int read() throws IOException {
            if (cursor >= size) {
                return -1;
            }

            int result = getUnsignedByte0(cursor);
            cursor++;
            return result;
        }

        @Override
        public int read(byte[] arr, int offset, int length) {
            if ((offset + length) > arr.length) {
                length = arr.length - offset;
            }

            int maxLength = size - cursor;
            if (length > maxLength) {
                length = maxLength;
            }

            System.arraycopy(bytes, cursor + start, arr, offset, length);
            cursor += length;
            return length;
        }

        @Override
        public int available() {
            return size - cursor;
        }

        @Override
        public void mark(int reserve) {
            mark = cursor;
        }

        @Override
        public void reset() {
            cursor = mark;
        }

        @Override
        public boolean markSupported() {
            return true;
        }
    }

    public static class MyDataInputStream extends DataInputStream {
        private final MyInputStream wrapped;

        public MyDataInputStream(MyInputStream wrapped) {
            super(wrapped);

            this.wrapped = wrapped;
        }
    }
}
