
package mod.agus.jcoderz.dex;

import mod.agus.jcoderz.dex.util.ByteInput;
import mod.agus.jcoderz.dex.util.ByteOutput;

public final class EncodedValueCodec {
    private EncodedValueCodec() {
    }

    public static void writeSignedIntegralValue(ByteOutput out, int type, long value) {
        int requiredBits = 65 - Long.numberOfLeadingZeros(value ^ (value >> 63));


        int requiredBytes = (requiredBits + 0x07) >> 3;

        out.writeByte(type | ((requiredBytes - 1) << 5));


        while (requiredBytes > 0) {
            out.writeByte((byte) value);
            value >>= 8;
            requiredBytes--;
        }
    }

    public static void writeUnsignedIntegralValue(ByteOutput out, int type, long value) {

        int requiredBits = 64 - Long.numberOfLeadingZeros(value);
        if (requiredBits == 0) {
            requiredBits = 1;
        }


        int requiredBytes = (requiredBits + 0x07) >> 3;

        out.writeByte(type | ((requiredBytes - 1) << 5));


        while (requiredBytes > 0) {
            out.writeByte((byte) value);
            value >>= 8;
            requiredBytes--;
        }
    }

    public static void writeRightZeroExtendedValue(ByteOutput out, int type, long value) {

        int requiredBits = 64 - Long.numberOfTrailingZeros(value);
        if (requiredBits == 0) {
            requiredBits = 1;
        }


        int requiredBytes = (requiredBits + 0x07) >> 3;


        value >>= 64 - (requiredBytes * 8);

        out.writeByte(type | ((requiredBytes - 1) << 5));


        while (requiredBytes > 0) {
            out.writeByte((byte) value);
            value >>= 8;
            requiredBytes--;
        }
    }

    public static int readSignedInt(ByteInput in, int zwidth) {
        int result = 0;
        for (int i = zwidth; i >= 0; i--) {
            result = (result >>> 8) | ((in.readByte() & 0xff) << 24);
        }
        result >>= (3 - zwidth) * 8;
        return result;
    }

    public static int readUnsignedInt(ByteInput in, int zwidth, boolean fillOnRight) {
        int result = 0;
        if (!fillOnRight) {
            for (int i = zwidth; i >= 0; i--) {
                result = (result >>> 8) | ((in.readByte() & 0xff) << 24);
            }
            result >>>= (3 - zwidth) * 8;
        } else {
            for (int i = zwidth; i >= 0; i--) {
                result = (result >>> 8) | ((in.readByte() & 0xff) << 24);
            }
        }
        return result;
    }

    public static long readSignedLong(ByteInput in, int zwidth) {
        long result = 0;
        for (int i = zwidth; i >= 0; i--) {
            result = (result >>> 8) | ((in.readByte() & 0xffL) << 56);
        }
        result >>= (7 - zwidth) * 8;
        return result;
    }

    public static long readUnsignedLong(ByteInput in, int zwidth, boolean fillOnRight) {
        long result = 0;
        if (!fillOnRight) {
            for (int i = zwidth; i >= 0; i--) {
                result = (result >>> 8) | ((in.readByte() & 0xffL) << 56);
            }
            result >>>= (7 - zwidth) * 8;
        } else {
            for (int i = zwidth; i >= 0; i--) {
                result = (result >>> 8) | ((in.readByte() & 0xffL) << 56);
            }
        }
        return result;
    }
}
