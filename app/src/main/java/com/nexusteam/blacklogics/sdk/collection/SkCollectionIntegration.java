package com.nexusteam.blacklogics.sdk.collection;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nexusteam.blacklogics.sdk.adapter.SkCollectionListAdapter;
import com.nexusteam.blacklogics.sdk.layouteditor.AttributeStore;
import com.nexusteam.blacklogics.sdk.layouteditor.IdManager;
import com.nexusteam.blacklogics.sdk.layouteditor.ViewBeanRegistry;
import com.nexusteam.blacklogics.sdk.ui.SkAddToCollectionDialog;
import com.nexusteam.blacklogics.sdk.utils.DragAndDropUtils;

import java.util.HashMap;

/**
 * Sketchware-style collection integration for ViewEditor.
 *
 * - Simple drag & drop only (no rename/delete/preview option dialogs)
 * - Collection delete via same bottom delete-zone (drag collection onto deleteImg)
 * - Uses com.nexusteam.internal.beans.ViewBean throughout
 */
public final class SkCollectionIntegration {

    private SkCollectionIntegration() {}

    public interface EditorHost extends CollectionInflater.Host {
        void showMessage(String msg);
        void addViewWithUndo(View view, ViewGroup parent, int index);
        void notifyWidgetAdded(View widget, ViewGroup parent);
        void refreshUndoRedoButtons();
        ViewGroup getEditorRoot();
        /** Optional: show the same delete image used for canvas widgets */
        void showDeleteZone();
        void hideDeleteZone();
        /** Optional: the delete ImageView so we can attach a drag listener */
        View getDeleteZoneView();
    }

    // -------------------------------------------------------------------------
    // List setup — pure drag & drop, no option dialogs
    // -------------------------------------------------------------------------

    public static SkCollectionListAdapter setupList(
            final Context context,
            RecyclerView recyclerView,
            final EditorHost editorHost) {

        final SkCollectionListAdapter adapter = new SkCollectionListAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        // Wire delete-zone so dropping a collection onto it deletes the collection
        wireDeleteZoneForCollections(editorHost, adapter);

        adapter.setListener(new SkCollectionListAdapter.Listener() {
            @Override
            public void onClick(WidgetCollectionBean item) {
                // No preview dialog — pure drag & drop UX
            }

            @Override
            public void onLongClick(WidgetCollectionBean item, View anchor) {
                // No menu — start drag immediately
                startCollectionDrag(item, anchor, editorHost);
            }

            @Override
            public void onDragStart(WidgetCollectionBean item, View anchor) {
                startCollectionDrag(item, anchor, editorHost);
            }
        });

        adapter.reload();
        return adapter;
    }

    private static void startCollectionDrag(
            WidgetCollectionBean item,
            View anchor,
            EditorHost editorHost) {
        if (item == null || item.name == null) return;

        HashMap<String, Object> dragData = new HashMap<String, Object>();
        dragData.put("type", "sk_collection");
        dragData.put("collection_name", item.name);

        try {
            if (editorHost != null) {
                editorHost.showDeleteZone();
            }
            DragAndDropUtils.startDragAndDrop(
                    anchor,
                    null,
                    new View.DragShadowBuilder(anchor),
                    dragData,
                    1
            );
        } catch (Exception e) {
            // Drag optional on older APIs
        }
    }

    /**
     * Attach drag listener on the shared delete ImageView so that
     * dropping a collection there deletes it (same UX as widget delete).
     */
    private static void wireDeleteZoneForCollections(
            final EditorHost editorHost,
            final SkCollectionListAdapter adapter) {
        if (editorHost == null) return;
        final View deleteZone = editorHost.getDeleteZoneView();
        if (deleteZone == null) return;

        deleteZone.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                Object local = event.getLocalState();
                if (!(local instanceof HashMap)) return false;
                @SuppressWarnings("unchecked")
                HashMap<String, Object> data = (HashMap<String, Object>) local;
                Object type = data.get("type");
                if (type == null || !"sk_collection".equals(String.valueOf(type))) {
                    return false;
                }

                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        v.setAlpha(0.7f);
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        v.setAlpha(1f);
                        return true;
                    case DragEvent.ACTION_DROP: {
                        v.setAlpha(1f);
                        Object nameObj = data.get("collection_name");
                        if (nameObj != null) {
                            String name = String.valueOf(nameObj);
                            try {
                                WidgetCollectionManager.getInstance().delete(name, true);
                                if (adapter != null) adapter.reload();
                                if (editorHost != null) {
                                    editorHost.showMessage("Deleted: " + name);
                                }
                            } catch (Exception e) {
                                if (editorHost != null) {
                                    editorHost.showMessage("Delete failed");
                                }
                            }
                        }
                        return true;
                    }
                    case DragEvent.ACTION_DRAG_ENDED:
                        v.setAlpha(1f);
                        if (editorHost != null) editorHost.hideDeleteZone();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public static void refresh(SkCollectionListAdapter adapter) {
        if (adapter != null) adapter.reload();
    }

    // -------------------------------------------------------------------------
    // Add to collection (from selected view) — uses full ViewBean tree
    // -------------------------------------------------------------------------

    public static void showAddDialog(
            Context context,
            View targetView,
            AttributeStore store,
            IdManager idManager,
            ViewBeanRegistry registry,
            SkAddToCollectionDialog.Callback callback) {

        SkAddToCollectionDialog.show(context, targetView, store, idManager, registry, callback);
    }

    // -------------------------------------------------------------------------
    // Insert collection into editor
    // -------------------------------------------------------------------------

    public static void addCollectionToEditor(EditorHost host, WidgetCollectionBean collection) {
        if (host == null || collection == null || collection.widgets == null) return;
        ViewGroup dest = host.getEditorRoot();
        if (dest == null) return;
        try {
            View root = CollectionInflater.inflate(host, collection.widgets, dest, -1);
            if (root != null) {
                host.notifyWidgetAdded(root, dest);
                host.refreshUndoRedoButtons();
                host.showMessage("Added: " + collection.name);
            } else {
                host.showMessage("Failed to inflate collection");
            }
        } catch (Exception e) {
            host.showMessage("Error adding collection");
        }
    }

    public static void addCollectionToEditor(EditorHost host, String collectionName) {
        WidgetCollectionBean c = WidgetCollectionManager.getInstance().get(collectionName);
        if (c == null) {
            if (host != null) host.showMessage("Collection not found: " + collectionName);
            return;
        }
        addCollectionToEditor(host, c);
    }

    /**
     * Call from ViewEditor / WidgetDropHandler when type == "sk_collection".
     * Returns true if handled.
     */
    public static boolean handleDrop(
            Object widgetData,
            ViewGroup destination,
            EditorHost host) {
        if (!(widgetData instanceof HashMap) || host == null) return false;
        @SuppressWarnings("unchecked")
        HashMap<String, Object> data = (HashMap<String, Object>) widgetData;
        Object type = data.get("type");
        if (type == null || !"sk_collection".equals(String.valueOf(type))) return false;

        Object nameObj = data.get("collection_name");
        if (nameObj == null) return false;
        String name = String.valueOf(nameObj);

        WidgetCollectionBean collection = WidgetCollectionManager.getInstance().get(name);
        if (collection == null || collection.widgets == null || collection.widgets.isEmpty()) {
            host.showMessage("Collection empty or not found");
            return true;
        }

        ViewGroup dest = destination != null ? destination : host.getEditorRoot();
        if (dest == null) dest = host.getEditorRoot();
        try {
            View root = CollectionInflater.inflate(host, collection.widgets, dest, -1);
            if (root != null) {
                host.notifyWidgetAdded(root, dest);
                host.refreshUndoRedoButtons();
                host.showMessage("Added: " + name);
            } else {
                host.showMessage("Failed to inflate: " + name);
            }
        } catch (Exception e) {
            host.showMessage("Drop failed");
        }
        return true;
    }
}
