package com.nexusteam.blacklogics.sdk.collection;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nexusteam.blacklogics.FileUtil;
import com.nexusteam.internal.beans.ViewBean;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Exact Sketchware-style Widget Collection manager (like class `en`).
 * Storage: {ext}/.blacklogics/widget/list
 * Each line = one CollectionBean JSON.
 * CollectionBean.data = newline-separated ViewBean JSONs.
 */
public class WidgetCollectionManager {

    private static final String TAG = "WidgetCollectionManager";
    private static WidgetCollectionManager instance;

    private final String listPath;
    private final Gson gson;
    private ArrayList<CollectionBean> collections;

    private WidgetCollectionManager() {
        String root = FileUtil.getExternalStorageDir() + "/.blacklogics/widget/";
        FileUtil.makeDir(root);
        listPath = root + "list";
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        load();
    }

    public static synchronized WidgetCollectionManager getInstance() {
        if (instance == null) {
            instance = new WidgetCollectionManager();
        }
        return instance;
    }

    private void load() {
        collections = new ArrayList<>();
        if (!FileUtil.isExistFile(listPath)) return;
        try {
            String content = FileUtil.readFile(listPath);
            BufferedReader reader = new BufferedReader(new StringReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() > 0) {
                    CollectionBean bean = gson.fromJson(line, CollectionBean.class);
                    if (bean != null && bean.name != null) {
                        collections.add(bean);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            Log.e(TAG, "load failed", e);
        }
    }

    private void saveList() {
        try {
            StringBuilder sb = new StringBuilder(1024);
            for (CollectionBean bean : collections) {
                sb.append(gson.toJson(bean)).append("\n");
            }
            FileUtil.writeFile(listPath, sb.toString());
        } catch (Exception e) {
            Log.e(TAG, "saveList failed", e);
        }
    }

    /** All collections as WidgetCollectionBean list. */
    public ArrayList<WidgetCollectionBean> getAll() {
        ArrayList<WidgetCollectionBean> result = new ArrayList<>();
        for (CollectionBean cb : collections) {
            result.add(new WidgetCollectionBean(cb.name, parseWidgets(cb.data)));
        }
        return result;
    }

    public WidgetCollectionBean get(String name) {
        for (CollectionBean cb : collections) {
            if (name.equals(cb.name)) {
                return new WidgetCollectionBean(cb.name, parseWidgets(cb.data));
            }
        }
        return null;
    }

    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        for (CollectionBean cb : collections) {
            names.add(cb.name);
        }
        return names;
    }

    public boolean exists(String name) {
        for (CollectionBean cb : collections) {
            if (cb.name.equals(name)) return true;
        }
        return false;
    }

    /**
     * Add new collection (Sketchware exact).
     * @throws Exception with message "duplicate_name" if name already exists
     */
    public void add(String name, ArrayList<ViewBean> widgets, boolean saveNow) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new Exception("empty_name");
        }
        name = name.trim();
        for (CollectionBean cb : collections) {
            if (cb.name.equals(name)) {
                throw new Exception("duplicate_name");
            }
        }
        StringBuilder data = new StringBuilder();
        if (widgets != null) {
            for (ViewBean vb : widgets) {
                if (vb != null) {
                    data.append(gson.toJson(vb)).append("\n");
                }
            }
        }
        collections.add(new CollectionBean(name, data.toString()));
        if (saveNow) saveList();
    }

    public void rename(String oldName, String newName, boolean saveNow) throws Exception {
        if (newName == null || newName.trim().isEmpty()) {
            throw new Exception("empty_name");
        }
        newName = newName.trim();
        if (!oldName.equals(newName) && exists(newName)) {
            throw new Exception("duplicate_name");
        }
        for (CollectionBean cb : collections) {
            if (cb.name.equals(oldName)) {
                cb.name = newName;
                break;
            }
        }
        if (saveNow) saveList();
    }

    public void delete(String name, boolean saveNow) {
        Iterator<CollectionBean> it = collections.iterator();
        while (it.hasNext()) {
            if (it.next().name.equals(name)) {
                it.remove();
                break;
            }
        }
        if (saveNow) saveList();
    }

    private ArrayList<ViewBean> parseWidgets(String data) {
        ArrayList<ViewBean> list = new ArrayList<>();
        if (data == null || data.isEmpty()) return list;
        try {
            BufferedReader reader = new BufferedReader(new StringReader(data));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() > 0) {
                    ViewBean vb = gson.fromJson(line, ViewBean.class);
                    if (vb != null) list.add(vb);
                }
            }
            reader.close();
        } catch (Exception e) {
            Log.e(TAG, "parseWidgets failed", e);
        }
        return list;
    }

    public void reload() {
        load();
    }

    public void clearCache() {
        if (collections != null) {
            collections.clear();
            collections = null;
        }
        instance = null;
    }
}
