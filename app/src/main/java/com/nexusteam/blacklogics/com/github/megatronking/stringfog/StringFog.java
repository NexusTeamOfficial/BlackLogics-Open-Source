package com.github.megatronking.stringfog;

public final class StringFog {

    private static final IStringFog IMPL =
            new com.github.megatronking.stringfog.xor.StringFogImpl();

    private StringFog() {}

    public static String decrypt(byte[] data, byte[] key) {
        if (data == null) return "";
        if (key == null || key.length == 0) return new String(data);
        return IMPL.decrypt(data, key);
    }

    public static byte[] encrypt(String data, byte[] key) {
        if (data == null) return new byte[0];
        return IMPL.encrypt(data, key);
    }
}
