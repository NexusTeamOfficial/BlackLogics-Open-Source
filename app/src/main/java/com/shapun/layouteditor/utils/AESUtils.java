package com.shapun.layouteditor.utils;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";


    private static final String SECRET_KEY = "X9a8B7c6D5e4F3g2H1i0J9k8L7m6N5o4";

    private static SecretKeySpec getKey() throws Exception {
        byte[] key = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(key, ALGORITHM);
    }

    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        byte[] encVal = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeToString(encVal, Base64.NO_WRAP);
    }

    public static String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        byte[] decoded = Base64.decode(encryptedData, Base64.NO_WRAP);
        byte[] decValue = cipher.doFinal(decoded);
        return new String(decValue, StandardCharsets.UTF_8);
    }
}
