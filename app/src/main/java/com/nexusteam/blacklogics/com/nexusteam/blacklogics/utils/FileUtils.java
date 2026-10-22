package com.besome.blacklogics.util;

import android.content.Context;
import android.os.Environment;
import java.io.*;
import java.util.zip.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.nexusteam.blacklogics.utils.FileUtil;

public class FileUtils {

private static final int DEFAULT_BUFFER_SIZE = 2048;
    
    public static boolean unzip(File zipFile, File destinationDir) {
        try (ZipFile zip = new ZipFile(zipFile)) {
            destinationDir.mkdirs();
            Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();
            
            while (zipFileEntries.hasMoreElements()) {
                ZipEntry entry = zipFileEntries.nextElement();
                String entryName = entry.getName();
                File destFile = new File(destinationDir, entryName);
                File destinationParent = destFile.getParentFile();
                
                if (destinationParent != null && !destinationParent.exists()) {
                    destinationParent.mkdirs();
                }
                
                if (!entry.isDirectory()) {
                    try (BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry))) {
                        try (FileOutputStream fos = new FileOutputStream(destFile)) {
                            try (BufferedOutputStream dest = new BufferedOutputStream(fos, DEFAULT_BUFFER_SIZE)) {
                                byte[] data = new byte[DEFAULT_BUFFER_SIZE];
                                int currentByte;
                                while ((currentByte = is.read(data, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                                    dest.write(data, 0, currentByte);
                                }
                                dest.flush();
                            }
                        }
                    }
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public static void zipFolder(File srcFolder, File destZipFile) throws Exception {
        try (FileOutputStream fileWriter = new FileOutputStream(destZipFile)) {
            try (ZipOutputStream zip = new ZipOutputStream(fileWriter)) {
                addFolderToZip(srcFolder, srcFolder, zip);
                zip.flush();
            }
        }
    }
    
    private static void addFileToZip(File rootPath, File srcFile, ZipOutputStream zip) throws Exception {
        if (srcFile.isDirectory()) {
            addFolderToZip(rootPath, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            try (FileInputStream in = new FileInputStream(srcFile)) {
                String name = srcFile.getPath();
                name = name.replace(rootPath.getPath() + "/", "");
                zip.putNextEntry(new ZipEntry(name));
                int len;
                while ((len = in.read(buf)) > 0) {
                    zip.write(buf, 0, len);
                }
            }
        }
    }
    
    private static void addFolderToZip(File rootPath, File srcFolder, ZipOutputStream zip) throws Exception {
        File[] srcFolderFiles = srcFolder.listFiles();
        if (srcFolderFiles != null) {
            for (File fileName : srcFolderFiles) {
                addFileToZip(rootPath, fileName, zip);
            }
        }
    }
    
    public static void createNomediaFileIn(File dir) {
        FileUtil.writeFile(new File(dir, ".nomedia").getAbsolutePath(), "");
    }
    
    public static void copySafe(File source, File destination) {
        if (!source.exists()) {
            destination.mkdirs();
            createNomediaFileIn(destination);
        } else {
            copy(source, destination);
        }
    }
    
    public static void copy(File source, File destination) {
        if (source.isDirectory()) {
            if (!destination.exists()) destination.mkdirs();
            
            String[] files = source.list();
            if (files != null) {
                for (String file : files) {
                    File srcFile = new File(source, file);
                    File destFile = new File(destination, file);
                    copy(srcFile, destFile);
                }
            }
        } else {
            if (source.getName().equals(".nomedia")) return;
            
            try (InputStream in = new FileInputStream(source);
                 OutputStream out = new FileOutputStream(destination)) {
                
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean zipContainsFile(String zipPath, String fileName) {
        try (ZipInputStream zp = new ZipInputStream(new FileInputStream(new File(zipPath)))) {
            ZipEntry entry;
            while ((entry = zp.getNextEntry()) != null) {
                String name = entry.getName();
                if (name.equals(fileName) || name.startsWith(fileName + File.separator)) {
                    return true;
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }


    public static void extractZipFromAssets(Context context, String assetZipName, String outputDirName) throws IOException {
        InputStream is = context.getAssets().open(assetZipName);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));

        File outputDir = new File("/storage/emulated/0/", outputDirName);
        if (!outputDir.exists()) outputDir.mkdirs();

        ZipEntry entry;
        byte[] buffer = new byte[1024];

        while ((entry = zis.getNextEntry()) != null) {
            File outFile = new File(outputDir, entry.getName());
            if (entry.isDirectory()) {
                outFile.mkdirs();
            } else {
                File parent = outFile.getParentFile();
                if (!parent.exists()) parent.mkdirs();

                FileOutputStream fos = new FileOutputStream(outFile);
                int count;
                while ((count = zis.read(buffer)) != -1) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
            }
            zis.closeEntry();
        }

        zis.close();
        is.close();
    }

    /**
     * Writes a String content into a file.
     *
     * @param file    The file to write into
     * @param content The string content to write
     * @throws IOException
     */
    public static void writeStringToFile(File file, String content) throws IOException {
        if (file == null) return;


        File parent = file.getParentFile();
        if (!parent.exists()) parent.mkdirs();

        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.flush();
        writer.close();
    }
}
