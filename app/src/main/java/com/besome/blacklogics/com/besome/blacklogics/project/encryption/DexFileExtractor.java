package com.besome.blacklogics.project.encryption;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DexFileExtractor {

    public static List<String> getDexFileNames(String zipFilePath) {
        List<String> dexFiles = new ArrayList<>();

        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.startsWith("classes") && entryName.endsWith(".dex")) {
                    dexFiles.add(entryName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dexFiles;
    }
}
