package com.besome.blacklogics.project.encryption;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;

public class EncryptResourceTask {
    private String inputPath;
    private String outputPath;

    public EncryptResourceTask(String inputPath, String outputPath) throws Exception {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        System.out.println("Parsing APK...");

        ZipFile zipFile = new ZipFile(inputPath);
        ZipOut zipOut = new ZipOut(outputPath).setInput(zipFile);
        zipOut.removeFile("resources.arsc");

        ArscObfuser arscObfuser = new ArscObfuser(getZipInputStream(zipFile, "resources.arsc"));
        zipOut.addFile("resources.arsc", arscObfuser.getData());

        HashMap<String, String> map = arscObfuser.getMap();
        int i = 0;

        for (String key : map.keySet()) {
            zipOut.removeFile(key);
            zipOut.addFile(map.get(key), toByteArray(getZipInputStream(zipFile, key)));
            System.out.println(key);
            i++;
        }

        System.out.println("Saving file...");
        zipOut.save();
        System.out.println("File output to: " + outputPath);
    }

    private InputStream getZipInputStream(ZipFile zipFile, String entryName) throws IOException {
        ZipEntry entry = zipFile.getEntry(entryName);
        if (entry == null) {
            throw new IOException("Entry " + entryName + " not found in the zip file.");
        }
        return zipFile.getInputStream(entry);
    }

    private byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = input.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
}
