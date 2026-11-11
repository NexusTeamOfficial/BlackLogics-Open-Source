package com.nexusteam.blacklogics.project.encryption;

import java.util.Base64;

public class Base64EncoderUtil {

    public static String encodeBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String getMultiEncodedString(String input, int times) {
        String encoded = input;
        for (int i = 0; i < times; i++) {
            encoded = encodeBase64(encoded);
        }
        return encoded;
    }

    public static String generateDecodeCode(String encoded) {
        char[] charArray = encoded.toCharArray();
        StringBuilder code = new StringBuilder();
        code.append("new String(android.util.Base64.decode(");
        code.append("new String(android.util.Base64.decode(");
        code.append("new String(android.util.Base64.decode(");
        code.append("new String(android.util.Base64.decode(");
        code.append("new String(new char[]{");

        for (int i = 0; i < charArray.length; i++) {
            code.append("(char)").append((int) charArray[i]);
            if (i < charArray.length - 1) {
                code.append(",");
            }
        }

        code.append("}).getBytes(), android.util.Base64.DEFAULT)).getBytes(), android.util.Base64.DEFAULT)).getBytes(), android.util.Base64.DEFAULT)).getBytes(), android.util.Base64.DEFAULT))");

        return code.toString();
    }
}
