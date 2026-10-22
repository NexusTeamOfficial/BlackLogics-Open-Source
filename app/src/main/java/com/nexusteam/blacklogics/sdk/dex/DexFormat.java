
package mod.agus.jcoderz.dex;

public final class DexFormat {
    private DexFormat() {}

    public static final int API_SPACES_IN_SIMPLE_NAME = 10000;

    public static final int API_CONST_METHOD_HANDLE = 28;

    public static final int API_METHOD_HANDLES = 26;

    public static final int API_DEFINE_INTERFACE_METHODS = 24;

    public static final int API_INVOKE_INTERFACE_METHODS = 24;

    public static final int API_INVOKE_STATIC_INTERFACE_METHODS = 21;

    public static final int API_NO_EXTENDED_OPCODES = 13;

    public static final int API_CURRENT = API_CONST_METHOD_HANDLE;

    public static final String VERSION_FOR_API_10000 = "040";

    public static final String VERSION_FOR_API_28 = "039";

    public static final String VERSION_FOR_API_26 = "038";

    public static final String VERSION_FOR_API_24 = "037";

    public static final String VERSION_FOR_API_13 = "035";

    public static final String VERSION_CURRENT = VERSION_FOR_API_28;

    public static final String DEX_IN_JAR_NAME = "classes.dex";

    public static final String MAGIC_PREFIX = "dex\n";

    public static final String MAGIC_SUFFIX = "\0";

    public static final int ENDIAN_TAG = 0x12345678;

    public static final int MAX_MEMBER_IDX = 0xFFFF;

    public static final int MAX_TYPE_IDX = 0xFFFF;

    public static int magicToApi(byte[] magic) {
        if (magic.length != 8) {
            return -1;
        }

        if ((magic[0] != 'd') || (magic[1] != 'e') || (magic[2] != 'x') || (magic[3] != '\n') ||
                (magic[7] != '\0')) {
            return -1;
        }

        String version = "" + ((char) magic[4]) + ((char) magic[5]) +((char) magic[6]);

        if (version.equals(VERSION_FOR_API_13)) {
            return API_NO_EXTENDED_OPCODES;
        } else if (version.equals(VERSION_FOR_API_24)) {
            return API_DEFINE_INTERFACE_METHODS;
        } else if (version.equals(VERSION_FOR_API_26)) {
            return API_METHOD_HANDLES;
        } else if (version.equals(VERSION_FOR_API_28)) {
            return API_CONST_METHOD_HANDLE;
        } else if (version.equals(VERSION_FOR_API_10000)) {
            return API_SPACES_IN_SIMPLE_NAME;
        } else if (version.equals(VERSION_CURRENT)) {
            return API_CURRENT;
        }

        return -1;
    }

    public static String apiToMagic(int targetApiLevel) {
        String version;

        if (targetApiLevel >= API_CURRENT) {
            version = VERSION_CURRENT;
        } else if (targetApiLevel >= API_SPACES_IN_SIMPLE_NAME) {
            version = VERSION_FOR_API_10000;
        } else if (targetApiLevel >= API_CONST_METHOD_HANDLE) {
            version = VERSION_FOR_API_28;
        } else if (targetApiLevel >= API_METHOD_HANDLES) {
            version = VERSION_FOR_API_26;
        } else if (targetApiLevel >= API_DEFINE_INTERFACE_METHODS) {
            version = VERSION_FOR_API_24;
        } else {
            version = VERSION_FOR_API_13;
        }

        return MAGIC_PREFIX + version + MAGIC_SUFFIX;
    }

    public static boolean isSupportedDexMagic(byte[] magic) {
        int api = magicToApi(magic);
        return api > 0;
    }
}
