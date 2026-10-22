
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public final class CstString extends TypedConstant {
    public static final CstString EMPTY_STRING = new CstString("");

    private final String string;

    private final ByteArray bytes;

    public static byte[] stringToUtf8Bytes(String string) {
        int len = string.length();
        byte[] bytes = new byte[len * 3];
        int outAt = 0;

        for (int i = 0; i < len; i++) {
            char c = string.charAt(i);
            if ((c != 0) && (c < 0x80)) {
                bytes[outAt] = (byte) c;
                outAt++;
            } else if (c < 0x800) {
                bytes[outAt] = (byte) (((c >> 6) & 0x1f) | 0xc0);
                bytes[outAt + 1] = (byte) ((c & 0x3f) | 0x80);
                outAt += 2;
            } else {
                bytes[outAt] = (byte) (((c >> 12) & 0x0f) | 0xe0);
                bytes[outAt + 1] = (byte) (((c >> 6) & 0x3f) | 0x80);
                bytes[outAt + 2] = (byte) ((c & 0x3f) | 0x80);
                outAt += 3;
            }
        }

        byte[] result = new byte[outAt];
        System.arraycopy(bytes, 0, result, 0, outAt);
        return result;
    }

    public static String utf8BytesToString(ByteArray bytes) {
        int length = bytes.size();
        char[] chars = new char[length];
        int outAt = 0;

        for (int at = 0; length > 0; ) {
            int v0 = bytes.getUnsignedByte(at);
            char out;
            switch (v0 >> 4) {
                case 0x00: case 0x01: case 0x02: case 0x03:
                case 0x04: case 0x05: case 0x06: case 0x07: {

                    length--;
                    if (v0 == 0) {

                        return throwBadUtf8(v0, at);
                    }
                    out = (char) v0;
                    at++;
                    break;
                }
                case 0x0c: case 0x0d: {

                    length -= 2;
                    if (length < 0) {
                        return throwBadUtf8(v0, at);
                    }
                    int v1 = bytes.getUnsignedByte(at + 1);
                    if ((v1 & 0xc0) != 0x80) {
                        return throwBadUtf8(v1, at + 1);
                    }
                    int value = ((v0 & 0x1f) << 6) | (v1 & 0x3f);
                    if ((value != 0) && (value < 0x80)) {
                        return throwBadUtf8(v1, at + 1);
                    }
                    out = (char) value;
                    at += 2;
                    break;
                }
                case 0x0e: {

                    length -= 3;
                    if (length < 0) {
                        return throwBadUtf8(v0, at);
                    }
                    int v1 = bytes.getUnsignedByte(at + 1);
                    if ((v1 & 0xc0) != 0x80) {
                        return throwBadUtf8(v1, at + 1);
                    }
                    int v2 = bytes.getUnsignedByte(at + 2);
                    if ((v1 & 0xc0) != 0x80) {
                        return throwBadUtf8(v2, at + 2);
                    }
                    int value = ((v0 & 0x0f) << 12) | ((v1 & 0x3f) << 6) |
                        (v2 & 0x3f);
                    if (value < 0x800) {
                        return throwBadUtf8(v2, at + 2);
                    }
                    out = (char) value;
                    at += 3;
                    break;
                }
                default: {

                    return throwBadUtf8(v0, at);
                }
            }
            chars[outAt] = out;
            outAt++;
        }

        return new String(chars, 0, outAt);
    }

    private static String throwBadUtf8(int value, int offset) {
        throw new IllegalArgumentException("bad utf-8 byte " + Hex.u1(value) +
                                           " at offset " + Hex.u4(offset));
    }

    public CstString(String string) {
        if (string == null) {
            throw new NullPointerException("string == null");
        }

        this.string = string.intern();
        this.bytes = new ByteArray(stringToUtf8Bytes(string));
    }

    public CstString(ByteArray bytes) {
        if (bytes == null) {
            throw new NullPointerException("bytes == null");
        }

        this.bytes = bytes;
        this.string = utf8BytesToString(bytes).intern();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CstString)) {
            return false;
        }

        return string.equals(((CstString) other).string);
    }

    @Override
    public int hashCode() {
        return string.hashCode();
    }

    @Override
    protected int compareTo0(Constant other) {
        return string.compareTo(((CstString) other).string);
    }

    @Override
    public String toString() {
        return "string{\"" + toHuman() + "\"}";
    }

    @Override
    public String typeName() {
        return "utf8";
    }

    @Override
    public boolean isCategory2() {
        return false;
    }

    @Override
    public String toHuman() {
        int len = string.length();
        StringBuilder sb = new StringBuilder(len * 3 / 2);

        for (int i = 0; i < len; i++) {
            char c = string.charAt(i);
            if ((c >= ' ') && (c < 0x7f)) {
                if ((c == '\'') || (c == '\"') || (c == '\\')) {
                    sb.append('\\');
                }
                sb.append(c);
            } else if (c <= 0x7f) {
                switch (c) {
                    case '\n': sb.append("\\n"); break;
                    case '\r': sb.append("\\r"); break;
                    case '\t': sb.append("\\t"); break;
                    default: {
                        char nextChar =
                            (i < (len - 1)) ? string.charAt(i + 1) : 0;
                        boolean displayZero =
                            (nextChar >= '0') && (nextChar <= '7');
                        sb.append('\\');
                        for (int shift = 6; shift >= 0; shift -= 3) {
                            char outChar = (char) (((c >> shift) & 7) + '0');
                            if ((outChar != '0') || displayZero) {
                                sb.append(outChar);
                                displayZero = true;
                            }
                        }
                        if (! displayZero) {

                            sb.append('0');
                        }
                        break;
                    }
                }
            } else {
                sb.append("\\u");
                sb.append(Character.forDigit(c >> 12, 16));
                sb.append(Character.forDigit((c >> 8) & 0x0f, 16));
                sb.append(Character.forDigit((c >> 4) & 0x0f, 16));
                sb.append(Character.forDigit(c & 0x0f, 16));
            }
        }

        return sb.toString();
    }

    public String toQuoted() {
        return '\"' + toHuman() + '\"';
    }

    public String toQuoted(int maxLength) {
        String string = toHuman();
        int length = string.length();
        String ellipses;

        if (length <= (maxLength - 2)) {
            ellipses = "";
        } else {
            string = string.substring(0, maxLength - 5);
            ellipses = "...";
        }

        return '\"' + string + ellipses + '\"';
    }

    public String getString() {
        return string;
    }

    public ByteArray getBytes() {
        return bytes;
    }

    public int getUtf8Size() {
        return bytes.size();
    }

    public int getUtf16Size() {
        return string.length();
    }

    @Override
    public Type getType() {
        return Type.STRING;
    }
}
