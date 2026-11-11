package com.nexusteam.internal.os.layouteditor.undo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import android.os.Looper;
import com.nexusteam.blacklogics.sdk.layouteditor.IdManager;
import com.nexusteam.blacklogics.sdk.layouteditor.Attribute;
import com.nexusteam.blacklogics.sdk.AttributeSet;
import com.nexusteam.blacklogics.sdk.layouteditor.ViewEditor;
import java.util.HashMap;
import java.util.Map;

public class ViewAction implements UndoableAction {
    
    private final ActionType type;
    private final long timestamp;
    private final String activityName;
    private final Map<String, Object> data;
    
    private final String viewId;
    private final String viewClassName;
    private final String parentId;
    private final AttributeSet oldAttributes;
    private final AttributeSet newAttributes;
    private final int index;
    
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
        
        this.viewId = builder.viewId != null ? builder.viewId : 
                     (builder.view != null ? IdManager.getInstance().getId(builder.view) : null);
        this.viewClassName = builder.view != null ? builder.view.getClass().getName() : builder.viewClassName;
        this.parentId = builder.parent != null ? IdManager.getInstance().getId(builder.parent) : null;
        
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
            resolveLiveRefs();
            
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
        if (view != null && view.getParent() != null) {
            ViewGroup currentParent = (ViewGroup) view.getParent();
            currentParent.removeView(view);
            
            if (viewId != null) {
                IdManager.getInstance().remove(view);
            }
            editor.attributesValueMap.remove(view);
        }
    }
    
    private void undoRemoveView() {
        ViewGroup targetParent = findParent();
        View v = ensureViewInstance();
        if (targetParent != null && v != null) {
            if (v.getParent() instanceof ViewGroup) {
                ((ViewGroup) v.getParent()).removeView(v);
            }
            int targetIndex = Math.min(index < 0 ? targetParent.getChildCount() : index,
                    targetParent.getChildCount());
            targetParent.addView(v, targetIndex);
            if (viewId != null && IdManager.getInstance().getId(v) == null) {
                try { IdManager.getInstance().addNewId(v, viewId); } catch (Exception ignore) {}
            }
            if (oldAttributes != null) {
                editor.attributesValueMap.put(v, oldAttributes);
                applyAttributes(v, oldAttributes);
            }
            editor.setupImportedView(v);
            if (v instanceof ViewGroup) {
                try { v.setOnDragListener(editor.dragListener); } catch (Throwable ignore) {}
            }
        }
    }
    
    private void undoUpdateAttribute() {
        if (view != null && oldAttributes != null) {
            editor.attributesValueMap.put(view, oldAttributes);
            applyAttributes(view, oldAttributes);
            view.requestLayout();
        }
    }
    
    private void undoMoveView() {
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
        if (parent != null && view != null) {
            String originalClass = (String) data.get("originalClassName");
            if (originalClass != null) {
                try {
                    View originalView = createViewFromClassName(originalClass);
                    if (originalView != null) {
                        int currentIndex = parent.indexOfChild(view);
                        parent.removeView(view);
                        
                        parent.addView(originalView, currentIndex);
                        
                        if (viewId != null) {
                            IdManager.getInstance().addNewId(originalView, viewId);
                        }
                        
                        if (oldAttributes != null) {
                            editor.attributesValueMap.put(originalView, oldAttributes);
                            applyAttributes(originalView, oldAttributes);
                        }
                        
                        editor.setupImportedView(originalView);
                        if (originalView instanceof ViewGroup) {
                            originalView.setOnDragListener(editor.dragListener);
                        }
                        
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
            resolveLiveRefs();
            
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
        ViewGroup targetParent = findParent();
        View v = ensureViewInstance();
        if (targetParent != null && v != null) {
            if (v.getParent() instanceof ViewGroup) {
                ((ViewGroup) v.getParent()).removeView(v);
            }
            int targetIndex = Math.min(index < 0 ? targetParent.getChildCount() : index,
                    targetParent.getChildCount());
            targetParent.addView(v, targetIndex);
            if (viewId != null && IdManager.getInstance().getId(v) == null) {
                try { IdManager.getInstance().addNewId(v, viewId); } catch (Exception ignore) {}
            }
            if (newAttributes != null) {
                editor.attributesValueMap.put(v, newAttributes);
                applyAttributes(v, newAttributes);
            }
            editor.setupImportedView(v);
            if (v instanceof ViewGroup) {
                try { v.setOnDragListener(editor.dragListener); } catch (Throwable ignore) {}
            }
        }
    }
    
    private void redoRemoveView() {
        if (view != null && view.getParent() != null) {
            ViewGroup currentParent = (ViewGroup) view.getParent();
            currentParent.removeView(view);
            
            if (viewId != null) {
                IdManager.getInstance().remove(view);
            }
            editor.attributesValueMap.remove(view);
        }
    }
    
    private void redoUpdateAttribute() {
        if (view != null && newAttributes != null) {
            editor.attributesValueMap.put(view, newAttributes);
            applyAttributes(view, newAttributes);
            view.requestLayout();
        }
    }
    
    private void redoMoveView() {
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
        if (parent != null && view != null) {
            String newClass = (String) data.get("newClassName");
            if (newClass != null) {
                try {
                    View newView = createViewFromClassName(newClass);
                    if (newView != null) {
                        int currentIndex = parent.indexOfChild(view);
                        parent.removeView(view);
                        
                        parent.addView(newView, currentIndex);
                        
                        if (viewId != null) {
                            IdManager.getInstance().addNewId(newView, viewId);
                        }
                        
                        if (newAttributes != null) {
                            editor.attributesValueMap.put(newView, newAttributes);
                            applyAttributes(newView, newAttributes);
                        }
                        
                        editor.setupImportedView(newView);
                        if (newView instanceof ViewGroup) {
                            newView.setOnDragListener(editor.dragListener);
                        }
                        
                        this.view = newView;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void resolveLiveRefs() {
        IdManager ids = IdManager.getInstance();
        if (viewId != null) {
            View found = ids.getView(viewId);
            if (found != null) view = found;
        }
        if (parentId != null) {
            View p = ids.getView(parentId);
            if (p instanceof ViewGroup) parent = (ViewGroup) p;
        }
    }

    private View ensureViewInstance() {
        if (view != null) return view;
        if (viewClassName == null || editor == null) return null;
        try {
            View created = null;
            try {
                created = com.nexusteam.blacklogics.sdk.utils.DynamicWidgetFactory
                        .resolve(editor.getContext(), viewClassName, null);
            } catch (Throwable ignore) {}
            if (created == null) {
                created = createViewFromClassName(viewClassName);
            }
            if (created == null) return null;
            if (viewId != null) {
                try {
                    IdManager.getInstance().addNewId(created, viewId);
                } catch (IllegalArgumentException dup) {
                    View existing = IdManager.getInstance().getView(viewId);
                    if (existing != null) {
                        view = existing;
                        return view;
                    }
                }
            }
            AttributeSet attrs = newAttributes != null ? newAttributes : oldAttributes;
            if (attrs != null) {
                editor.attributesValueMap.put(created, attrs);
                applyAttributes(created, attrs);
            }
            editor.setupImportedView(created);
            if (created instanceof ViewGroup) {
                try {
                    created.setOnDragListener(editor.dragListener);
                } catch (Throwable ignore) {}
            }
            view = created;
            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ViewGroup findParent() {
        if (parent != null && parent.isAttachedToWindow()) return parent;
        if (parentId != null) {
            View p = IdManager.getInstance().getView(parentId);
            if (p instanceof ViewGroup) {
                parent = (ViewGroup) p;
                return parent;
            }
        }
        if (editor != null) {
            try {
                if (editor.editorLayout != null) return editor.editorLayout;
            } catch (Throwable ignore) {}
        }
        return parent;
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
                Class<?> clazz = Class.forName("android.widget." + className);
                return (View) clazz.getConstructor(Context.class).newInstance(editor.getContext());
            } catch (Exception e2) {
                try {
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