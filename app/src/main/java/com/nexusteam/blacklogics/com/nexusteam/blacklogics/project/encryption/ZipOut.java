package com.nexusteam.blacklogics.project.encryption;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipOut {

    private ZipFile inputZipFile;
    private final String outPath;
    private ZipOutputStream zos;
    private final Set<String> saveList = new HashSet<>();
    private final Set<String> removeList = new HashSet<>();
    private final List<String> entries = new ArrayList<>();
    private boolean inputOwnedByUs = false;
    private boolean closed = false;

    public ZipOut(String outPath) throws Exception {
        this.outPath = outPath;
        File out = new File(outPath);
        if (out.exists()) {
            out.delete();
        }
        File parent = out.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        this.zos = new ZipOutputStream(new FileOutputStream(out));
    }

    public ZipOut setInput(ZipFile zipFile) throws Exception {
        this.inputZipFile = zipFile;
        this.inputOwnedByUs = false;
        readZip(inputZipFile);
        return this;
    }

    public ZipOut setInput(String apkPath) throws Exception {
        this.inputZipFile = new ZipFile(apkPath);
        this.inputOwnedByUs = true;
        readZip(inputZipFile);
        return this;
    }

    public void addFile(String entry, byte[] data) throws IOException {
        if (closed) throw new IOException("ZipOut already closed");
        ZipEntry ze = new ZipEntry(entry);
        if (shouldStore(entry)) {
            writeStored(ze, data);
        } else {
            ze.setMethod(ZipEntry.DEFLATED);
            zos.putNextEntry(ze);
            zos.write(data);
            zos.closeEntry();
        }
        saveList.add(entry);
    }

    public void removeFile(String entry) {
        removeList.add(entry);
    }

    public void save() throws Exception {
        if (closed) return;
        if (inputZipFile == null) {
            throw new IOException("input ZipFile not set");
        }

        try {
            for (String key : entries) {
                if (removeList.contains(key) || saveList.contains(key)) {
                    continue;
                }
                ZipEntry inEntry = inputZipFile.getEntry(key);
                if (inEntry == null) continue;

                byte[] data;
                try (InputStream is = inputZipFile.getInputStream(inEntry)) {
                    data = toByteArray(is);
                }

                ZipEntry outEntry = new ZipEntry(key);

                boolean store = shouldStore(key) || inEntry.getMethod() == ZipEntry.STORED;

                if (store) {
                    writeStored(outEntry, data);
                } else {
                    outEntry.setMethod(ZipEntry.DEFLATED);
                    zos.putNextEntry(outEntry);
                    zos.write(data);
                    zos.closeEntry();
                }
            }
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                    zos = null;
                }
            } catch (IOException ignored) {
            }

            if (inputOwnedByUs && inputZipFile != null) {
                try {
                    inputZipFile.close();
                } catch (IOException ignored) {
                }
                inputZipFile = null;
            }
            closed = true;
        }
    }

    private void writeStored(ZipEntry ze, byte[] data) throws IOException {
        CRC32 crc = new CRC32();
        crc.update(data);
        ze.setMethod(ZipEntry.STORED);
        ze.setSize(data.length);
        ze.setCompressedSize(data.length);
        ze.setCrc(crc.getValue());
        zos.putNextEntry(ze);
        zos.write(data);
        zos.closeEntry();
    }

    private static boolean shouldStore(String name) {
        if (name == null) return false;
        String n = name.toLowerCase();
        return n.equals("resources.arsc")
                || n.endsWith(".so")
                || n.endsWith(".arsc");
    }

    private void readZip(ZipFile zip) throws Exception {
        entries.clear();
        Enumeration<? extends ZipEntry> enums = zip.entries();
        while (enums.hasMoreElements()) {
            entries.add(enums.nextElement().getName());
        }
    }

    private static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) != -1) {
            out.write(buf, 0, n);
        }
        return out.toByteArray();
    }
}
