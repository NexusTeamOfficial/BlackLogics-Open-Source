package com.nexusteam.internal.beans;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class WidgetCollectionRepository {

    private static final String TAG = "WidgetCollectionRepo";
    private static final String DIR_NAME = "collections";
    private static final String FILE_NAME = "collections.json";

    private static volatile WidgetCollectionRepository instance;

    private final Context appContext;
    private final Gson gson;
    private final File storeFile;

    private final ArrayList<WidgetCollectionBean> roots = new ArrayList<WidgetCollectionBean>();

    private final CopyOnWriteArrayList<ChangeListener> listeners =
            new CopyOnWriteArrayList<ChangeListener>();

    public interface ChangeListener {
        void onCollectionsChanged();
        void onCollectionAdded(WidgetCollectionBean collection);
        void onCollectionRemoved(String id);
        void onCollectionUpdated(WidgetCollectionBean collection);
    }

    public static abstract class SimpleChangeListener implements ChangeListener {
        @Override public void onCollectionAdded(WidgetCollectionBean c) {}
        @Override public void onCollectionRemoved(String id) {}
        @Override public void onCollectionUpdated(WidgetCollectionBean c) {}
    }

    private WidgetCollectionRepository(Context context) {
        this.appContext = context.getApplicationContext();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        File dir = new File(appContext.getFilesDir(), DIR_NAME);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        this.storeFile = new File(dir, FILE_NAME);
        loadFromDisk();
    }

    public static WidgetCollectionRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (WidgetCollectionRepository.class) {
                if (instance == null) {
                    instance = new WidgetCollectionRepository(context);
                }
            }
        }
        return instance;
    }


    public void addChangeListener(ChangeListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyChanged() {
        for (ChangeListener l : listeners) {
            try {
                l.onCollectionsChanged();
            } catch (Exception e) {
                Log.w(TAG, "listener error", e);
            }
        }
    }

    private void notifyAdded(WidgetCollectionBean c) {
        for (ChangeListener l : listeners) {
            try {
                l.onCollectionAdded(c);
            } catch (Exception e) {
                Log.w(TAG, "listener error", e);
            }
        }
        notifyChanged();
    }

    private void notifyRemoved(String id) {
        for (ChangeListener l : listeners) {
            try {
                l.onCollectionRemoved(id);
            } catch (Exception e) {
                Log.w(TAG, "listener error", e);
            }
        }
        notifyChanged();
    }

    private void notifyUpdated(WidgetCollectionBean c) {
        for (ChangeListener l : listeners) {
            try {
                l.onCollectionUpdated(c);
            } catch (Exception e) {
                Log.w(TAG, "listener error", e);
            }
        }
        notifyChanged();
    }


    public List<WidgetCollectionBean> getRoots() {
        synchronized (roots) {
            return new ArrayList<WidgetCollectionBean>(roots);
        }
    }

    public List<WidgetCollectionBean> getAll() {
        synchronized (roots) {
            return WidgetCollectionBean.flattenForest(roots);
        }
    }

    public WidgetCollectionBean findById(String id) {
        if (id == null) return null;
        synchronized (roots) {
            for (WidgetCollectionBean root : roots) {
                WidgetCollectionBean found = root.findById(id);
                if (found != null) return found;
            }
        }
        return null;
    }

    public List<WidgetCollectionBean> getFavorites() {
        List<WidgetCollectionBean> favs = new ArrayList<WidgetCollectionBean>();
        for (WidgetCollectionBean c : getAll()) {
            if (c != null && c.isFavorite()) favs.add(c);
        }
        return favs;
    }

    public List<WidgetCollectionBean> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAll();
        }
        String q = query.trim().toLowerCase();
        List<WidgetCollectionBean> hits = new ArrayList<WidgetCollectionBean>();
        for (WidgetCollectionBean c : getAll()) {
            if (c == null) continue;
            if (matches(c, q)) hits.add(c);
        }
        return hits;
    }

    private static boolean matches(WidgetCollectionBean c, String q) {
        if (c.getDisplayName().toLowerCase().contains(q)) return true;
        if (c.getDescription().toLowerCase().contains(q)) return true;
        for (String tag : c.getTags()) {
            if (tag != null && tag.toLowerCase().contains(q)) return true;
        }
        return false;
    }


    public void add(WidgetCollectionBean collection) {
        if (collection == null) return;
        synchronized (roots) {
            String parentId = collection.getParentId();
            if (parentId != null && !parentId.isEmpty()) {
                WidgetCollectionBean parent = findByIdLocked(parentId);
                if (parent != null) {
                    parent.addChild(collection);
                    persistLocked();
                    notifyAdded(collection);
                    return;
                }
                collection.setParentId(null);
            }
            roots.add(collection);
            persistLocked();
        }
        notifyAdded(collection);
    }

    public WidgetCollectionBean createFolder(String name, String parentId) {
        WidgetCollectionBean folder = WidgetCollectionBean.builder()
                .name(name != null ? name : "New Folder")
                .parentId(parentId)
                .build();
        add(folder);
        return folder;
    }

    public WidgetCollectionBean createCollection(String name,
            ArrayList<ViewBean> widgets, String parentId) {
        WidgetCollectionBean collection = WidgetCollectionBean.builder()
                .name(name != null ? name : "New Collection")
                .widgets(widgets)
                .parentId(parentId)
                .build();
        add(collection);
        return collection;
    }

    public boolean update(WidgetCollectionBean collection) {
        if (collection == null || collection.getId() == null) return false;
        synchronized (roots) {
            WidgetCollectionBean existing = findByIdLocked(collection.getId());
            if (existing == null) return false;
            collection.touch();
            persistLocked();
        }
        notifyUpdated(collection);
        return true;
    }

    public boolean rename(String id, String newName) {
        WidgetCollectionBean c = findById(id);
        if (c == null) return false;
        c.setName(newName);
        synchronized (roots) {
            persistLocked();
        }
        notifyUpdated(c);
        return true;
    }

    public boolean setFavorite(String id, boolean favorite) {
        WidgetCollectionBean c = findById(id);
        if (c == null) return false;
        c.setFavorite(favorite);
        synchronized (roots) {
            persistLocked();
        }
        notifyUpdated(c);
        return true;
    }

    public boolean remove(String id) {
        if (id == null) return false;
        synchronized (roots) {
            for (int i = 0; i < roots.size(); i++) {
                WidgetCollectionBean r = roots.get(i);
                if (r != null && id.equals(r.getId())) {
                    roots.remove(i);
                    persistLocked();
                    notifyRemoved(id);
                    return true;
                }
            }
            for (WidgetCollectionBean root : roots) {
                if (removeFromSubtree(root, id)) {
                    persistLocked();
                    notifyRemoved(id);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean removeFromSubtree(WidgetCollectionBean parent, String id) {
        if (parent == null) return false;
        if (parent.removeChildById(id)) return true;
        for (WidgetCollectionBean child : parent.getChildrenRaw()) {
            if (removeFromSubtree(child, id)) return true;
        }
        return false;
    }

    public boolean move(String id, String newParentId) {
        if (id == null) return false;
        if (id.equals(newParentId)) return false;

        synchronized (roots) {
            WidgetCollectionBean target = findByIdLocked(id);
            if (target == null) return false;

            if (newParentId != null && target.findById(newParentId) != null) {
                return false;
            }

            detachLocked(id);

            if (newParentId == null || newParentId.isEmpty()) {
                target.setParentId(null);
                roots.add(target);
            } else {
                WidgetCollectionBean parent = findByIdLocked(newParentId);
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

    public boolean reorderRoot(int fromIndex, int toIndex) {
        synchronized (roots) {
            int size = roots.size();
            if (fromIndex < 0 || fromIndex >= size || toIndex < 0 || toIndex >= size) {
                return false;
            }
            if (fromIndex == toIndex) return true;
            WidgetCollectionBean item = roots.remove(fromIndex);
            roots.add(toIndex, item);
            persistLocked();
        }
        notifyChanged();
        return true;
    }

    public void sortRootsByName(final boolean ascending) {
        synchronized (roots) {
            Collections.sort(roots, new Comparator<WidgetCollectionBean>() {
                @Override
                public int compare(WidgetCollectionBean a, WidgetCollectionBean b) {
                    String na = a != null ? a.getDisplayName() : "";
                    String nb = b != null ? b.getDisplayName() : "";
                    int cmp = na.compareToIgnoreCase(nb);
                    return ascending ? cmp : -cmp;
                }
            });
            persistLocked();
        }
        notifyChanged();
    }

    public void sortRootsByUpdated(final boolean newestFirst) {
        synchronized (roots) {
            Collections.sort(roots, new Comparator<WidgetCollectionBean>() {
                @Override
                public int compare(WidgetCollectionBean a, WidgetCollectionBean b) {
                    long ta = a != null ? a.getUpdatedAt() : 0L;
                    long tb = b != null ? b.getUpdatedAt() : 0L;
                    if (ta == tb) return 0;
                    int cmp = ta < tb ? -1 : 1;
                    return newestFirst ? -cmp : cmp;
                }
            });
            persistLocked();
        }
        notifyChanged();
    }


    private void loadFromDisk() {
        if (!storeFile.exists()) {
            roots.clear();
            return;
        }
        try {
            FileInputStream fis = new FileInputStream(storeFile);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            Type type = new TypeToken<ArrayList<WidgetCollectionBean>>() {}.getType();
            ArrayList<WidgetCollectionBean> flat = gson.fromJson(reader, type);
            reader.close();
            fis.close();

            roots.clear();
            if (flat != null && !flat.isEmpty()) {
                roots.addAll(WidgetCollectionBean.buildTree(flat));
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to load collections", e);
            roots.clear();
        }
    }

    private void persistLocked() {
        try {
            ArrayList<WidgetCollectionBean> flat =
                    WidgetCollectionBean.flattenForest(roots);
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
            Log.e(TAG, "Failed to persist collections", e);
        }
    }

    public void reload() {
        synchronized (roots) {
            loadFromDisk();
        }
        notifyChanged();
    }

    public void save() {
        synchronized (roots) {
            persistLocked();
        }
    }


    private WidgetCollectionBean findByIdLocked(String id) {
        if (id == null) return null;
        for (WidgetCollectionBean root : roots) {
            WidgetCollectionBean found = root.findById(id);
            if (found != null) return found;
        }
        return null;
    }

    private void detachLocked(String id) {
        for (int i = 0; i < roots.size(); i++) {
            WidgetCollectionBean r = roots.get(i);
            if (r != null && id.equals(r.getId())) {
                roots.remove(i);
                return;
            }
        }
        for (WidgetCollectionBean root : roots) {
            if (removeFromSubtree(root, id)) return;
        }
    }
}
