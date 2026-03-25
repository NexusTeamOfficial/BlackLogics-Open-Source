package com.nexusteam.blacklogics.security.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

/**
 * FileEncryptionUtil - Encryption utility (AES, PBKDF2). Produces binary output: SALT(16) || IV(16) || CIPHERTEXT.
 */
public class FileEncryptionUtil {

    private static final int SALT_LEN = 16;
    private static final int IV_LEN = 16;
    private static final int KEY_LEN_BITS = 128; // 128-bit AES key
    private static final int PBKDF2_ITER = 65536;
    private static final String KDF_ALGO = "PBKDF2WithHmacSHA256";
    private static final String CIPHER_ALGO = "AES/CBC/PKCS5Padding";


    public static SecretKey deriveKey(char[] password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(KDF_ALGO);
        KeySpec spec = new PBEKeySpec(password, salt, PBKDF2_ITER, KEY_LEN_BITS);
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }


    public static void encryptToFile(File outFile, byte[] plainBytes, char[] password) throws Exception {
        SecureRandom sr = new SecureRandom();


        byte[] salt = new byte[SALT_LEN];
        sr.nextBytes(salt);


        SecretKey key = deriveKey(password, salt);


        byte[] iv = new byte[IV_LEN];
        sr.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);


        Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] cipherBytes = cipher.doFinal(plainBytes);


        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            fos.write(salt);
            fos.write(iv);
            fos.write(cipherBytes);
            fos.flush();
        }
    }


    public static byte[] decryptFromFile(File inFile, char[] password) throws Exception {
        byte[] all = Files.readAllBytes(inFile.toPath());
        if (all.length < SALT_LEN + IV_LEN + 1) {
            throw new IllegalArgumentException("File too short or corrupted");
        }

        byte[] salt = new byte[SALT_LEN];
        byte[] iv = new byte[IV_LEN];
        System.arraycopy(all, 0, salt, 0, SALT_LEN);
        System.arraycopy(all, SALT_LEN, iv, 0, IV_LEN);

        int cipherStart = SALT_LEN + IV_LEN;
        int cipherLen = all.length - cipherStart;
        byte[] cipherBytes = new byte[cipherLen];
        System.arraycopy(all, cipherStart, cipherBytes, 0, cipherLen);

        SecretKey key = deriveKey(password, salt);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        return cipher.doFinal(cipherBytes);
    }
}
