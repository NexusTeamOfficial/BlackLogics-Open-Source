package com.nexusteam.blacklogics.project.encryption;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class EncryptResourceTask {
    private String inputPath;
    private String outputPath;

    public EncryptResourceTask(String inputPath, String outputPath) throws Exception {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        System.out.println("Parsing APK...");

        String writePath = outputPath;
        boolean samePath = inputPath.equals(outputPath);
        if (samePath) {
            writePath = outputPath + ".tmp_enc";
        }

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(inputPath);
            ZipOut zipOut = new ZipOut(writePath).setInput(zipFile);
            zipOut.removeFile("resources.arsc");

            ArscObfuser arscObfuser = new ArscObfuser(getZipInputStream(zipFile, "resources.arsc"));
            zipOut.addFile("resources.arsc", arscObfuser.getData());

            HashMap<String, String> map = arscObfuser.getMap();
            for (String key : map.keySet()) {
                zipOut.removeFile(key);
                zipOut.addFile(map.get(key), toByteArray(getZipInputStream(zipFile, key)));
                System.out.println(key);
            }

            System.out.println("Saving file...");
            zipOut.save();
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException ignored) {
                }
            }
        }

        if (samePath) {
            java.io.File tmp = new java.io.File(writePath);
            java.io.File dest = new java.io.File(outputPath);
            if (dest.exists()) dest.delete();
            if (!tmp.renameTo(dest)) {
                java.nio.file.Files.copy(tmp.toPath(), dest.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                tmp.delete();
            }
        }

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
        input.close();
        return buffer.toByteArray();
    }
}
