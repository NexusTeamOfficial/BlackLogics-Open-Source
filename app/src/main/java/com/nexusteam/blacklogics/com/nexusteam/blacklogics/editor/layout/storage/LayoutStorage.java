package com.nexusteam.blacklogics.editor.layout.storage;

import android.util.Log;
import com.nexusteam.blacklogics.editor.layout.model.LayoutData;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public final class LayoutStorage {

    private static final String TAG = "LayoutStorage";
    private static final String FILE_NAME = "layout.bin";

    private LayoutStorage() {
    }

    /* ---------------- SAVE ---------------- */
    public static void save(
            File baseDir,
            String layoutName,
            String xml,
            char[] password // ignored, only for backward compatibility
    ) {
        if (baseDir == null || layoutName == null || xml == null) return;
        if (layoutName.trim().isEmpty()) return;

        try {
            if (!baseDir.exists() && !baseDir.mkdirs()) return;

            File file = new File(baseDir, FILE_NAME);
            ArrayList<LayoutData> list;


            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    Object obj = ois.readObject();
                    @SuppressWarnings("unchecked")
                    ArrayList<LayoutData> tempList = (obj instanceof ArrayList) ? (ArrayList<LayoutData>) obj : new ArrayList<LayoutData>();
                    list = tempList;
                }
            } else {
                list = new ArrayList<>();
            }


            Iterator<LayoutData> it = list.iterator();
            while (it.hasNext()) {
                if (layoutName.equalsIgnoreCase(it.next().name)) {
                    it.remove();
                }
            }

            list.add(new LayoutData(layoutName.trim(), xml));


            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(list);
            }

        } catch (Exception e) {
            Log.e(TAG, "save failed", e);
        }
    }

    /* ---------------- LOAD ---------------- */
    public static LayoutData load(
            File baseDir,
            String layoutName,
            char[] password // ignored, only for backward compatibility
    ) {
        if (baseDir == null || layoutName == null) return null;
        if (layoutName.trim().isEmpty()) return null;

        File file = new File(baseDir, FILE_NAME);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            @SuppressWarnings("unchecked")
            ArrayList<LayoutData> list = (obj instanceof ArrayList) ? (ArrayList<LayoutData>) obj : new ArrayList<LayoutData>();

            for (LayoutData d : list) {
                if (layoutName.equals(d.name)) {
                    return d;
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "load failed", e);
        }

        return null;
    }
}
