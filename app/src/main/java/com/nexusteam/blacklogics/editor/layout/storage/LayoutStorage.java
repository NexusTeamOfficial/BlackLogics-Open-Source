package com.nexusteam.blacklogics.editor.layout.storage;

import android.util.Log;
import com.nexusteam.blacklogics.editor.layout.model.LayoutData;

import java.io.*;
import java.util.ArrayList;

public final class LayoutStorage {

    private static final String TAG = "LayoutStorage";
    private static final String STORE_FILE = "layoutstore";
    private static final int STORE_VERSION = 1;
    private static final byte[] MASK_KEY = "NEXUS_LAYOUT_KEY_7734".getBytes();

    private LayoutStorage() {
    }

    private static byte[] applyMask(byte[] data) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ MASK_KEY[i % MASK_KEY.length]);
        }
        return result;
    }

    private static class LayoutEntry {
        String name;
        String xml;
        ArrayList<ViewNode> roots = new ArrayList<ViewNode>();
    }

    private static class ViewNode {
        String tag;
        boolean isPlaceholder;
        boolean isPlaceholderWebView;
        String widgetTag;
        String id;
        ArrayList<String[]> attributes = new ArrayList<String[]>();
        ArrayList<ViewNode> children = new ArrayList<ViewNode>();
    }

    private static File getStoreFile(File baseDir) {
        return new File(baseDir, STORE_FILE);
    }

    public static void save(
            File baseDir,
            String layoutName,
            String xml,
            char[] password
    ) {
        if (baseDir == null || layoutName == null || xml == null) return;
        if (layoutName.trim().isEmpty()) return;

        try {
            if (!baseDir.exists() && !baseDir.mkdirs()) return;

            File file = getStoreFile(baseDir);
            ArrayList<LayoutEntry> entries = readAllEntries(file);

            boolean found = false;
            for (int i = 0; i < entries.size(); i++) {
                LayoutEntry entry = entries.get(i);
                if (layoutName.equals(entry.name)) {
                    entry.xml = xml;
                    found = true;
                    break;
                }
            }
            if (!found) {
                LayoutEntry newEntry = new LayoutEntry();
                newEntry.name = layoutName.trim();
                newEntry.xml = xml;
                entries.add(newEntry);
            }

            writeAllEntries(file, entries);

            Log.d(TAG, "Saved layout: " + layoutName + " to " + STORE_FILE);

        } catch (Exception e) {
            Log.e(TAG, "save failed", e);
        }
    }

    public static LayoutData load(
            File baseDir,
            String layoutName,
            char[] password
    ) {
        if (baseDir == null || layoutName == null) return null;
        if (layoutName.trim().isEmpty()) return null;

        File file = getStoreFile(baseDir);
        if (!file.exists()) {
            File oldFile = new File(baseDir, "layout.bin");
            if (oldFile.exists()) {
                Log.d(TAG, "Falling back to legacy layout.bin");
                return loadLegacy(oldFile, layoutName);
            }
            return null;
        }

        try {
            ArrayList<LayoutEntry> entries = readAllEntries(file);
            for (int i = 0; i < entries.size(); i++) {
                LayoutEntry entry = entries.get(i);
                if (layoutName.equals(entry.name) && entry.xml != null && !entry.xml.isEmpty()) {
                    Log.d(TAG, "Loaded layout: " + layoutName + " from " + STORE_FILE);
                    return new LayoutData(layoutName, entry.xml);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "load failed", e);
        }

        return null;
    }

    private static LayoutData loadLegacy(File oldFile, String layoutName) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(oldFile));
            Object obj = ois.readObject();
            java.util.ArrayList<LayoutData> list;

            if (obj instanceof java.util.ArrayList) {
                list = (java.util.ArrayList<LayoutData>) obj;
            } else {
                list = new java.util.ArrayList<LayoutData>();
            }

            for (LayoutData d : list) {
                if (layoutName.equals(d.name)) {
                    Log.d(TAG, "Loaded from legacy layout.bin");
                    save(new File(oldFile.getParent()), layoutName, d.xml, null);
                    return d;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Legacy load failed", e);
        } finally {
            if (ois != null) {
                try { ois.close(); } catch (IOException ignored) {}
            }
        }
        return null;
    }


    private static ArrayList<LayoutEntry> readAllEntries(File storeFile) {
        ArrayList<LayoutEntry> entries = new ArrayList<LayoutEntry>();
        if (!storeFile.exists()) return entries;

        FileInputStream fis = null;
        DataInputStream in = null;
        try {
            byte[] raw = new byte[(int) storeFile.length()];
            fis = new FileInputStream(storeFile);
            fis.read(raw);

            byte[] plain = applyMask(raw);
            in = new DataInputStream(new ByteArrayInputStream(plain));

            in.readInt();
            int entryCount = in.readInt();
            for (int i = 0; i < entryCount; i++) {
                LayoutEntry entry = new LayoutEntry();
                entry.name = in.readUTF();
                entry.xml = in.readUTF();
                int rootCount = in.readInt();
                for (int r = 0; r < rootCount; r++) {
                    entry.roots.add(readViewNode(in));
                }
                entries.add(entry);
            }
        } catch (Exception e) {
            Log.e(TAG, "readAllEntries failed, starting fresh", e);
            entries = new ArrayList<LayoutEntry>();
        } finally {
            try { if (in != null) in.close(); } catch (Exception ignored) {}
            try { if (fis != null) fis.close(); } catch (Exception ignored) {}
        }
        return entries;
    }

    private static void writeAllEntries(File storeFile, ArrayList<LayoutEntry> entries) throws Exception {
        ByteArrayOutputStream bos = null;
        DataOutputStream out = null;
        FileOutputStream fos = null;
        try {
            bos = new ByteArrayOutputStream();
            out = new DataOutputStream(bos);

            out.writeInt(STORE_VERSION);
            out.writeInt(entries.size());
            for (int i = 0; i < entries.size(); i++) {
                LayoutEntry entry = entries.get(i);
                out.writeUTF(entry.name != null ? entry.name : "");
                out.writeUTF(entry.xml != null ? entry.xml : "");
                out.writeInt(entry.roots.size());
                for (int r = 0; r < entry.roots.size(); r++) {
                    writeViewNode(out, entry.roots.get(r));
                }
            }
            out.flush();

            byte[] masked = applyMask(bos.toByteArray());
            fos = new FileOutputStream(storeFile);
            fos.write(masked);
        } finally {
            try { if (out != null) out.close(); } catch (Exception ignored) {}
            try { if (bos != null) bos.close(); } catch (Exception ignored) {}
            try { if (fos != null) fos.close(); } catch (Exception ignored) {}
        }
    }

    private static void writeViewNode(DataOutputStream out, ViewNode node) throws Exception {
        out.writeUTF(node.tag != null ? node.tag : "View");
        out.writeBoolean(node.isPlaceholder);
        out.writeBoolean(node.isPlaceholderWebView);
        out.writeUTF(node.widgetTag != null ? node.widgetTag : "");
        out.writeUTF(node.id != null ? node.id : "");

        out.writeInt(node.attributes.size());
        for (int i = 0; i < node.attributes.size(); i++) {
            String[] pair = node.attributes.get(i);
            out.writeUTF(pair[0]);
            out.writeUTF(pair[1] != null ? pair[1] : "");
        }

        out.writeInt(node.children.size());
        for (int i = 0; i < node.children.size(); i++) {
            writeViewNode(out, node.children.get(i));
        }
    }

    private static ViewNode readViewNode(DataInputStream in) throws Exception {
        ViewNode node = new ViewNode();
        node.tag = in.readUTF();
        node.isPlaceholder = in.readBoolean();
        node.isPlaceholderWebView = in.readBoolean();
        node.widgetTag = in.readUTF();
        node.id = in.readUTF();

        int attrCount = in.readInt();
        for (int i = 0; i < attrCount; i++) {
            String name = in.readUTF();
            String value = in.readUTF();
            node.attributes.add(new String[]{name, value});
        }

        int childCount = in.readInt();
        for (int i = 0; i < childCount; i++) {
            node.children.add(readViewNode(in));
        }
        return node;
    }
}
