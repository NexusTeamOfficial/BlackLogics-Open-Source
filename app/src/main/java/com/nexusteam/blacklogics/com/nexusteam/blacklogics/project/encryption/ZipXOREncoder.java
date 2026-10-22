package com.nexusteam.blacklogics.project.encryption;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.nio.file.Files;

public class ZipXOREncoder {

    private String zipFilePath;

    public ZipXOREncoder(String zipFilePath) {
        this.zipFilePath = zipFilePath;
    }

    public static byte[] xorEncode(byte[] data, String key) {
        byte[] keyBytes = key.getBytes();
        byte[] resultBytes = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            resultBytes[i] = (byte) (data[i] ^ keyBytes[i % keyBytes.length]);
        }
        return resultBytes;
    }

    public byte[] readFileFromZip(String fileName) throws IOException {
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            ZipEntry zipEntry = zipFile.getEntry(fileName);
            if (zipEntry == null) {
                throw new IOException("File not found in the ZIP: " + fileName);
            }
            try (InputStream inputStream = zipFile.getInputStream(zipEntry);
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static void writeFile(File file, byte[] data) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        }
    }

    public void encodeFileFromZip(String fileName, File outputFile, String key) throws IOException {
        byte[] fileData = readFileFromZip(fileName);
        byte[] encodedData = xorEncode(fileData, key);
        writeFile(outputFile, encodedData);
    }

    
}
