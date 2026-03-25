package com.nexusteam.internal.os.layouteditor.undo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import android.os.Looper;
import com.shapun.layouteditor.IdManager;
import com.shapun.layouteditor.Attribute;
import com.shapun.layouteditor.AttributeSet;
import com.shapun.layouteditor.ViewEditor;
import java.util.HashMap;
import java.util.Map;

public class ViewAction implements UndoableAction {
    
    private final ActionType type;
    private final long timestamp;
    private final String activityName;
    private final Map<String, Object> data;
    
    // Store IDs and class info for restoration
    private final String viewId;
    private final String viewClassName;
    private final String parentId;
    private final AttributeSet oldAttributes;
    private final AttributeSet newAttributes;
    private final int index;
    
    // Transient references (only valid during execution)
    private transient View view;
    private transient ViewGroup parent;
    private transient ViewEditor editor;
    
    private ViewAction(Builder builder) {
        this.type = builder.type;
        this.timestamp = System.currentTimeMillis();
        this.activityName = builder.activityName;
        this.view = builder.view;
        this.parent = builder.parent;
        this.editor = builder.editor;
        this.oldAttributes = builder.oldAttributes;
        this.newAttributes = builder.newAttributes;
        this.index = builder.index;
        
        // Store IDs and class info
        this.viewId = builder.viewId != null ? builder.viewId : 
                     (builder.view != null ? IdManager.getInstance().getId(builder.view) : null);
        this.viewClassName = builder.view != null ? builder.view.getClass().getName() : builder.viewClassName;
        this.parentId = builder.parent != null ? IdManager.getInstance().getId(builder.parent) : null;
        
        // Data map for additional info
        this.data = new HashMap<>();
        this.data.put("index", builder.index);
        this.data.put("viewId", viewId);
        this.data.put("className", viewClassName);
        this.data.put("parentId", parentId);
        this.data.put("parentClassName", builder.parent != null ? builder.parent.getClass().getName() : "");
        
        if (builder.originalClassName != null) {
            this.data.put("originalClassName", builder.originalClassName);
        }
        if (builder.newClassName != null) {
            this.data.put("newClassName", builder.newClassName);
        }
    }
    
    @Override
    public void undo() {
        if (editor == null || editor.isUndoRedoInProgress) return;
        
        try {
            editor.isUndoRedoInProgress = true;
            
            switch (type) {
                case ADD_VIEW:
                    undoAddView();
                    break;
                    
                case REMOVE_VIEW:
                    undoRemoveView();
                    break;
                    
                case UPDATE_ATTRIBUTE:
                    undoUpdateAttribute();
                    break;
                    
                case MOVE_VIEW:
                    undoMoveView();
                    break;
                    
                case CONVERT_WIDGET:
                    undoConvertWidget();
                    break;
                    
                case BULK_CHANGE:
                    // Not handled at individual level
                    break;
            }
            
            notifyListeners();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            editor.isUndoRedoInProgress = false;
        }
    }
    
    private void undoAddView() {
        // ADD_VIEW ka undo = view remove karo
        if (view != null && view.getParent() != null) {
            ViewGroup currentParent = (ViewGroup) view.getParent();
            currentParent.removeView(view);
            
            // ID and attributes remove karo
            if (viewId != null) {
                IdManager.getInstance().remove(view);
            }
            editor.attributesValueMap.remove(view);
        }
    }
    
    private void undoRemoveView() {
        // REMOVE_VIEW ka undo = view wapas add karo
        ViewGroup targetParent = findParent();
        if (targetParent != null && view != null) {
            int targetIndex = Math.min(index, targetParent.getChildCount());
            targetParent.addView(view, targetIndex);
            
            // ID restore karo
            if (viewId != null) {
                IdManager.getInstance().addNewId(view, viewId);
            }
            
            // Attributes restore karo
            if (oldAttributes != null) {
                editor.attributesValueMap.put(view, oldAttributes);
                applyAttributes(view, oldAttributes);
            }
            
            // Setup listeners
            editor.setupImportedView(view);
            if (view instanceof ViewGroup) {
                view.setOnDragListener(editor.dragListener);
            }
        }
    }
    
    private void undoUpdateAttribute() {
        // UPDATE_ATTRIBUTE ka undo = purani attributes restore karo
        if (view != null && oldAttributes != null) {
            editor.attributesValueMap.put(view, oldAttributes);
            applyAttributes(view, oldAttributes);
            view.requestLayout();
        }
    }
    
    private void undoMoveView() {
        // MOVE_VIEW ka undo = purani position pe le jao
        if (view != null && view.getParent() != null) {
            ViewGroup oldParent = (ViewGroup) view.getParent();
            oldParent.removeView(view);
            
            ViewGroup targetParent = findParent();
            if (targetParent != null) {
                int targetIndex = Math.min(index, targetParent.getChildCount());
                targetParent.addView(view, targetIndex);
            }
        }
    }
    
    private void undoConvertWidget() {
        // CONVERT_WIDGET ka undo = original widget restore karo
        if (parent != null && view != null) {
            String originalClass = (String) data.get("originalClassName");
            if (originalClass != null) {
                try {
                    View originalView = createViewFromClassName(originalClass);
                    if (originalView != null) {
                        int currentIndex = parent.indexOfChild(view);
                        parent.removeView(view);
                        
                        // Original view add karo
                        parent.addView(originalView, currentIndex);
                        
                        // ID restore karo
                        if (viewId != null) {
                            IdManager.getInstance().addNewId(originalView, viewId);
                        }
                        
                        // Attributes restore karo
                        if (oldAttributes != null) {
                            editor.attributesValueMap.put(originalView, oldAttributes);
                            applyAttributes(originalView, oldAttributes);
                        }
                        
                        // Setup listeners
                        editor.setupImportedView(originalView);
                        if (originalView instanceof ViewGroup) {
                            originalView.setOnDragListener(editor.dragListener);
                        }
                        
                        // Update reference
                        this.view = originalView;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public void redo() {
        if (editor == null || editor.isUndoRedoInProgress) return;
        
        try {
            editor.isUndoRedoInProgress = true;
            
            switch (type) {
                case ADD_VIEW:
                    redoAddView();
                    break;
                    
                case REMOVE_VIEW:
                    redoRemoveView();
                    break;
                    
                case UPDATE_ATTRIBUTE:
                    redoUpdateAttribute();
                    break;
                    
                case MOVE_VIEW:
                    redoMoveView();
                    break;
                    
                case CONVERT_WIDGET:
                    redoConvertWidget();
                    break;
                    
                case BULK_CHANGE:
                    // Not handled at individual level
                    break;
            }
            
            notifyListeners();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            editor.isUndoRedoInProgress = false;
        }
    }
    
    private void redoAddView() {
        // ADD_VIEW ka redo = view wapas add karo
        ViewGroup targetParent = findParent();
        if (targetParent != null && view != null) {
            int targetIndex = Math.min(index, targetParent.getChildCount());
            targetParent.addView(view, targetIndex);
            
            // ID restore karo
            if (viewId != null) {
                IdManager.getInstance().addNewId(view, viewId);
            }
            
            // Attributes restore karo
            if (newAttributes != null) {
                editor.attributesValueMap.put(view, newAttributes);
                applyAttributes(view, newAttributes);
            }
            
            // Setup listeners
            editor.setupImportedView(view);
            if (view instanceof ViewGroup) {
                view.setOnDragListener(editor.dragListener);
            }
        }
    }
    
    private void redoRemoveView() {
        // REMOVE_VIEW ka redo = view remove karo
        if (view != null && view.getParent() != null) {
            ViewGroup currentParent = (ViewGroup) view.getParent();
            currentParent.removeView(view);
            
            // ID and attributes remove karo
            if (viewId != null) {
                IdManager.getInstance().remove(view);
            }
            editor.attributesValueMap.remove(view);
        }
    }
    
    private void redoUpdateAttribute() {
        // UPDATE_ATTRIBUTE ka redo = naye attributes apply karo
        if (view != null && newAttributes != null) {
            editor.attributesValueMap.put(view, newAttributes);
            applyAttributes(view, newAttributes);
            view.requestLayout();
        }
    }
    
    private void redoMoveView() {
        // MOVE_VIEW ka redo = nayi position pe le jao
        if (view != null && view.getParent() != null && parent != null) {
            ViewGroup currentParent = (ViewGroup) view.getParent();
            if (currentParent != parent) {
                currentParent.removeView(view);
                int targetIndex = Math.min(index, parent.getChildCount());
                parent.addView(view, targetIndex);
            }
        }
    }
    
    private void redoConvertWidget() {
        // CONVERT_WIDGET ka redo = naya widget apply karo
        if (parent != null && view != null) {
            String newClass = (String) data.get("newClassName");
            if (newClass != null) {
                try {
                    View newView = createViewFromClassName(newClass);
                    if (newView != null) {
                        int currentIndex = parent.indexOfChild(view);
                        parent.removeView(view);
                        
                        // New view add karo
                        parent.addView(newView, currentIndex);
                        
                        // ID restore karo
                        if (viewId != null) {
                            IdManager.getInstance().addNewId(newView, viewId);
                        }
                        
                        // Attributes restore karo
                        if (newAttributes != null) {
                            editor.attributesValueMap.put(newView, newAttributes);
                            applyAttributes(newView, newAttributes);
                        }
                        
                        // Setup listeners
                        editor.setupImportedView(newView);
                        if (newView instanceof ViewGroup) {
                            newView.setOnDragListener(editor.dragListener);
                        }
                        
                        // Update reference
                        this.view = newView;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private ViewGroup findParent() {
        if (parent != null) return parent;
        if (parentId != null && editor != null) {
            // Try to find parent by ID
            return (ViewGroup) IdManager.getInstance().getView(parentId);
        }
        return null;
    }
    
    private void applyAttributes(View view, AttributeSet attributes) {
        if (editor == null || attributes == null) return;
        
        for (Attribute attr : attributes.getAttributes()) {
            try {
                editor.applyAttribute(view, attr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private View createViewFromClassName(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return (View) clazz.getConstructor(Context.class).newInstance(editor.getContext());
        } catch (Exception e) {
            try {
                // Try with android.widget prefix
                Class<?> clazz = Class.forName("android.widget." + className);
                return (View) clazz.getConstructor(Context.class).newInstance(editor.getContext());
            } catch (Exception e2) {
                try {
                    // Try with androidx prefix
                    Class<?> clazz = Class.forName("androidx.appcompat.widget." + className);
                    return (View) clazz.getConstructor(Context.class).newInstance(editor.getContext());
                } catch (Exception e3) {
                    return null;
                }
            }
        }
    }
    
    private void notifyListeners() {
        if (editor != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    // Update UI if needed
                    editor.invalidate();
                }
            });
        }
    }
    
    @Override
    public String getDescription() {
        switch (type) {
            case ADD_VIEW: 
                return "Add " + getSimpleName(viewClassName);
            case REMOVE_VIEW: 
                return "Remove " + getSimpleName(viewClassName);
            case UPDATE_ATTRIBUTE: 
                return "Change attribute";
            case MOVE_VIEW: 
                return "Move widget";
            case CONVERT_WIDGET: 
                return "Convert widget";
            case BULK_CHANGE:
                return "Multiple changes";
            default: 
                return "Unknown action";
        }
    }
    
    private String getSimpleName(String fullClassName) {
        if (fullClassName == null) return "widget";
        int lastDot = fullClassName.lastIndexOf('.');
        return lastDot > 0 ? fullClassName.substring(lastDot + 1) : fullClassName;
    }
    
    @Override
    public long getTimestamp() {
        return timestamp;
    }
    
    @Override
    public ActionType getType() {
        return type;
    }
    
    @Override
    public Map<String, Object> getData() {
        return data;
    }
    
    @Override
    public String getActivityName() {
        return activityName;
    }
    
    // Builder Class
    public static class Builder {
        private ActionType type;
        private View view;
        private ViewGroup parent;
        private int index = -1;
        private String viewId;
        private String viewClassName;
        private ViewEditor editor;
        private AttributeSet oldAttributes;
        private AttributeSet newAttributes;
        private String activityName;
        private String originalClassName;
        private String newClassName;
        
        public Builder setType(ActionType type) {
            this.type = type;
            return this;
        }
        
        public Builder setView(View view) {
            this.view = view;
            if (view != null) {
                this.viewClassName = view.getClass().getName();
                if (viewId == null) {
                    this.viewId = IdManager.getInstance().getId(view);
                }
            }
            return this;
        }
        
        public Builder setParent(ViewGroup parent) {
            this.parent = parent;
            return this;
        }
        
        public Builder setIndex(int index) {
            this.index = index;
            return this;
        }
        
        public Builder setViewId(String viewId) {
            this.viewId = viewId;
            return this;
        }
        
        public Builder setViewClassName(String className) {
            this.viewClassName = className;
            return this;
        }
        
        public Builder setEditor(ViewEditor editor) {
            this.editor = editor;
            return this;
        }
        
        public Builder setOldAttributes(AttributeSet oldAttributes) {
            this.oldAttributes = oldAttributes;
            return this;
        }
        
        public Builder setNewAttributes(AttributeSet newAttributes) {
            this.newAttributes = newAttributes;
            return this;
        }
        
        public Builder setActivityName(String activityName) {
            this.activityName = activityName;
            return this;
        }
        
        public Builder setOriginalClassName(String originalClassName) {
            this.originalClassName = originalClassName;
            return this;
        }
        
        public Builder setNewClassName(String newClassName) {
            this.newClassName = newClassName;
            return this;
        }
        
        public ViewAction build() {
            return new ViewAction(this);
        }
    }
}