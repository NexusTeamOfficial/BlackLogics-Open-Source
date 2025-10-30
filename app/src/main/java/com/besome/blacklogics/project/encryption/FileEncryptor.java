package com.besome.blacklogics.project.encryption;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileEncryptor {
    private String key;

    public FileEncryptor(String key) {
        this.key = key;
    }

    public void encryptFile(String inputFilePath, String outputFilePath) {
        byte[] inputData = readFile(inputFilePath);
        if (inputData != null) {
            byte[] encryptedData = ByteEncoder.Encrypt(inputData, key);
            if (encryptedData != null) {
                writeFile(outputFilePath, encryptedData);
            } else {
                System.out.println("Encryption failed.");
            }
        } else {
            System.out.println("Failed to read input file.");
        }
    }

    public String decryptFile(String inputFilePath) {
        byte[] encryptedData = readFile(inputFilePath);
        if (encryptedData != null) {
            byte[] decryptedData = ByteEncoder.Decrypt(encryptedData, key);
            if (decryptedData != null) {
                return new String(decryptedData);
            } else {
                return "Decryption failed.";
            }
        } else {
            return "Failed to read input file.";
        }
    }

    private byte[] readFile(String filePath) {
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void writeFile(String filePath, byte[] data) {
        try {
            Files.write(Paths.get(filePath), data, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
