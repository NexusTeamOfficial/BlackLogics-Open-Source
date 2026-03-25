package com.nexusteam.blacklogics.project.encryption;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {

    public void extractFileFromZip(String zipFilePath, String fileName, String targetDirectoryPath) throws IOException {
        File zipFile = new File(zipFilePath);
        File targetDirectory = new File(targetDirectoryPath);

        try (ZipInputStream zipInputStream = new ZipInputStream(zipFile.toURI().toURL().openStream())) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().equals(fileName)) {
                    File file = new File(targetDirectory, entry.getName());
                    file.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                    zipInputStream.closeEntry();
                    break;  // Stop once we've extracted the specific file
                }
                zipInputStream.closeEntry();
            }
        }
    }
}
