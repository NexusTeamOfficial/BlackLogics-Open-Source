package com.nexusteam.blacklogics.sdk.managers;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.blacklogics.sdk.models.CollectionFolder;
import com.nexusteam.blacklogics.sdk.models.WidgetBlueprint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class CollectionFolderRepository {

    private static final String TAG = "CollectionFolderRepo";
    private static final String DIR_NAME = "collection_folders";
    private static final String FILE_NAME = "folders.json";

    private static volatile CollectionFolderRepository instance;

    private final Context appContext;
    private final Gson gson;
    private final File storeFile;
    private final ArrayList<CollectionFolder> roots = new ArrayList<CollectionFolder>();
    private final CopyOnWriteArrayList<ChangeListener> listeners =
            new CopyOnWriteArrayList<ChangeListener>();

    public interface ChangeListener {
        void onFoldersChanged();
    }

    private CollectionFolderRepository(Context context) {
        this.appContext = context.getApplicationContext();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        File dir = new File(appContext.getFilesDir(), DIR_NAME);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        this.storeFile = new File(dir, FILE_NAME);
        load();
    }

    public static CollectionFolderRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (CollectionFolderRepository.class) {
                if (instance == null) {
                    instance = new CollectionFolderRepository(context);
                }
            }
        }
        return instance;
    }

    public void addChangeListener(ChangeListener l) {
        if (l != null && !listeners.contains(l)) listeners.add(l);
    }

    public void removeChangeListener(ChangeListener l) {
        listeners.remove(l);
    }

    private void notifyChanged() {
        for (ChangeListener l : listeners) {
            try { l.onFoldersChanged(); } catch (Exception e) {
                Log.w(TAG, "listener", e);
            }
        }
    }


    public List<CollectionFolder> getRoots() {
        synchronized (roots) {
            return new ArrayList<CollectionFolder>(roots);
        }
    }

    public List<CollectionFolder> getAll() {
        synchronized (roots) {
            return CollectionFolder.flattenForest(roots);
        }
    }

    public CollectionFolder findById(String id) {
        if (id == null) return null;
        synchronized (roots) {
            for (CollectionFolder r : roots) {
                CollectionFolder f = r.findById(id);
                if (f != null) return f;
            }
        }
        return null;
    }

    public CollectionFolder findFolderContaining(String blueprintId) {
        if (blueprintId == null) return null;
        synchronized (roots) {
            for (CollectionFolder r : roots) {
                CollectionFolder f = r.findFolderContaining(blueprintId);
                if (f != null) return f;
            }
        }
        return null;
    }

    public List<WidgetBlueprint> getUncategorized(WidgetRepository widgetRepo) {
        List<WidgetBlueprint> all = widgetRepo.getAllWidgets();
        List<WidgetBlueprint> orphans = new ArrayList<WidgetBlueprint>();
        synchronized (roots) {
            for (WidgetBlueprint bp : all) {
                if (bp == null) continue;
                if (findFolderContainingLocked(bp.getBlueprintId()) == null) {
                    orphans.add(bp);
                }
            }
        }
        return orphans;
    }


    public CollectionFolder createFolder(String name, String parentId) {
        CollectionFolder folder = CollectionFolder.builder()
                .name(name != null ? name : "New Folder")
                .parentId(parentId)
                .build();
        add(folder);
        return folder;
    }

    public void add(CollectionFolder folder) {
        if (folder == null) return;
        synchronized (roots) {
            String pid = folder.getParentId();
            if (pid != null && !pid.isEmpty()) {
                CollectionFolder parent = findByIdLocked(pid);
                if (parent != null) {
                    parent.addChild(folder);
                    persistLocked();
                    notifyChanged();
                    return;
                }
                folder.setParentId(null);
            }
            roots.add(folder);
            persistLocked();
        }
        notifyChanged();
    }

    public boolean rename(String id, String newName) {
        CollectionFolder f = findById(id);
        if (f == null) return false;
        f.setName(newName);
        synchronized (roots) { persistLocked(); }
        notifyChanged();
        return true;
    }

    public boolean remove(String id) {
        if (id == null) return false;
        synchronized (roots) {
            for (int i = 0; i < roots.size(); i++) {
                CollectionFolder r = roots.get(i);
                if (r != null && id.equals(r.getId())) {
                    roots.remove(i);
                    persistLocked();
                    notifyChanged();
                    return true;
                }
            }
            for (CollectionFolder root : roots) {
                if (removeFromSubtree(root, id)) {
                    persistLocked();
                    notifyChanged();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean removeFromSubtree(CollectionFolder parent, String id) {
        if (parent == null) return false;
        if (parent.removeChildById(id)) return true;
        for (CollectionFolder child : parent.getChildrenRaw()) {
            if (removeFromSubtree(child, id)) return true;
        }
        return false;
    }

    public boolean moveFolder(String id, String newParentId) {
        if (id == null || id.equals(newParentId)) return false;
        synchronized (roots) {
            CollectionFolder target = findByIdLocked(id);
            if (target == null) return false;
            if (newParentId != null && target.findById(newParentId) != null) {
                return false;
            }
            detachLocked(id);
            if (newParentId == null || newParentId.isEmpty()) {
                target.setParentId(null);
                roots.add(target);
            } else {
                CollectionFolder parent = findByIdLocked(newParentId);
                if (parent == null) {
                    target.setParentId(null);
                    roots.add(target);
                } else {
                    parent.addChild(target);
                }
            }
            persistLocked();
        }
        notifyChanged();
        return true;
    }

    public boolean assignBlueprint(String blueprintId, String folderId) {
        if (blueprintId == null) return false;
        synchronized (roots) {
            for (CollectionFolder f : CollectionFolder.flattenForest(roots)) {
                f.removeBlueprintId(blueprintId);
            }
            if (folderId != null) {
                CollectionFolder folder = findByIdLocked(folderId);
                if (folder == null) return false;
                folder.addBlueprintId(blueprintId);
            }
            persistLocked();
        }
        notifyChanged();
        return true;
    }

    public boolean unassignBlueprint(String blueprintId) {
        return assignBlueprint(blueprintId, null);
    }

    public void save() {
        synchronized (roots) { persistLocked(); }
    }

    public void reload() {
        synchronized (roots) { load(); }
        notifyChanged();
    }


    private void load() {
        roots.clear();
        if (!storeFile.exists()) return;
        try {
            FileInputStream fis = new FileInputStream(storeFile);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            Type type = new TypeToken<ArrayList<CollectionFolder>>() {}.getType();
            ArrayList<CollectionFolder> flat = gson.fromJson(reader, type);
            reader.close();
            fis.close();
            if (flat != null && !flat.isEmpty()) {
                roots.addAll(CollectionFolder.buildTree(flat));
            }
        } catch (Exception e) {
            Log.e(TAG, "load failed", e);
            roots.clear();
        }
    }

    private void persistLocked() {
        try {
            ArrayList<CollectionFolder> flat = CollectionFolder.flattenForest(roots);
            File tmp = new File(storeFile.getAbsolutePath() + ".tmp");
            FileOutputStream fos = new FileOutputStream(tmp);
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            gson.toJson(flat, writer);
            writer.flush();
            writer.close();
            fos.close();
            if (storeFile.exists()) {
                storeFile.delete();
            }
            tmp.renameTo(storeFile);
        } catch (Exception e) {
            Log.e(TAG, "persist failed", e);
        }
    }

    private CollectionFolder findByIdLocked(String id) {
        if (id == null) return null;
        for (CollectionFolder r : roots) {
            CollectionFolder f = r.findById(id);
            if (f != null) return f;
        }
        return null;
    }

    private CollectionFolder findFolderContainingLocked(String blueprintId) {
        for (CollectionFolder r : roots) {
            CollectionFolder f = r.findFolderContaining(blueprintId);
            if (f != null) return f;
        }
        return null;
    }

    private void detachLocked(String id) {
        for (int i = 0; i < roots.size(); i++) {
            CollectionFolder r = roots.get(i);
            if (r != null && id.equals(r.getId())) {
                roots.remove(i);
                return;
            }
        }
        for (CollectionFolder root : roots) {
            if (removeFromSubtree(root, id)) return;
        }
    }
}
