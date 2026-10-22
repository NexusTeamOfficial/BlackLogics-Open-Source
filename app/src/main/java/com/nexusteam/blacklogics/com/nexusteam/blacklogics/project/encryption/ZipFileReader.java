package com.nexusteam.blacklogics.project.encryption;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileReader {

    private String zipFilePath;

    public ZipFileReader(String zipFilePath) {
        this.zipFilePath = zipFilePath;
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

   
}
