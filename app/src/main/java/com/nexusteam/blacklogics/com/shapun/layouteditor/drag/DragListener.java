package com.shapun.layouteditor.drag;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.shapun.layouteditor.ViewEditor;
import com.shapun.layouteditor.Attribute;
import com.shapun.layouteditor.AttributeSet;
import com.nexusteam.blacklogics.editor.ViewEditorDrag;
import com.shapun.layouteditor.utils.ReflectionUtils;
import com.nexusteam.blacklogics.SketchwareUtil;
import com.shapun.layouteditor.utils.UiUtils;
import com.shapun.layouteditor.IdManager;
import com.shapun.layouteditor.utils.DragAndDropUtils;
import java.util.HashMap;

public class DragListener implements View.OnDragListener {
    private ViewEditor viewEditor;
    private View placeHolder;
    private Context context;
    private IdManager idManager;
    private ViewEditorDrag viewEditorDrag;
    
    public DragListener(ViewEditor viewEditor, View placeHolder) {
        this.viewEditor = viewEditor;
        this.placeHolder = placeHolder;
        this.context = viewEditor.getContext();
    }
    
    public void setIdManager(IdManager idManager) {
        this.idManager = idManager;
    }
    
    public void setViewEditorDrag(ViewEditorDrag viewEditorDrag) {
        this.viewEditorDrag = viewEditorDrag;
    }
    
    @Override
    public boolean onDrag(View destinationView, DragEvent event) {
        View draggedView = null;
        if (event.getLocalState() instanceof View) {
            draggedView = (View) event.getLocalState();
        }
        
        try {
            switch (event.getAction()) {
                
                case DragEvent.ACTION_DRAG_STARTED:
                if (viewEditor != null) {
                    viewEditor.log("drag started on " + destinationView);
                }
                if (draggedView != null) {
                    removeView(draggedView);
                }
                return true;
                
                case DragEvent.ACTION_DRAG_ENTERED:
                case DragEvent.ACTION_DRAG_LOCATION:
                if (viewEditor != null) {
                    viewEditor.log("drag entered/location on " + destinationView);
                }
                addView(placeHolder, (ViewGroup) destinationView, event);
                return true;
                
                case DragEvent.ACTION_DRAG_EXITED:
                removeView(placeHolder);
                return true;
                
                case DragEvent.ACTION_DROP:
                if (draggedView == null) {

                    try {
                        HashMap<String, Object> viewData = (HashMap<String, Object>) event.getLocalState();
                        View newView = ReflectionUtils.createView(context, viewData.get("class_path").toString());
                        

                        newView.setMinimumHeight((int) SketchwareUtil.getDip(context, 30));
                        newView.setMinimumWidth((int) SketchwareUtil.getDip(context, 30));
                        
                      //  viewEditor._rearrangeListener(newView);
                        newView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                    viewEditor.showAttributesDialog(v);
                            }
                        });
                        

                        removeView(placeHolder);
                        addView(newView, (ViewGroup) destinationView, event);
                        addInitialAttributes(newView, viewData);
                        

                        if (newView instanceof ViewGroup) {
                            newView.setOnDragListener(this);
                            int dp = (int) SketchwareUtil.getDip(context, 8);
                            newView.setPadding(dp, dp, dp, dp);
                            newView.setBackground(UiUtils.createStrokedBackground(0, 0xFF000000, 1));
                        }
                        

                        if (idManager != null) {
                            String newId = idManager.generateNewId(newView);
                            idManager.addNewId(newView, newId);
                        }
                        
                        AttributeSet attributeSet = new AttributeSet();
                        viewEditor.attributesValueMap.put(newView, attributeSet);
                        String widgetId = (idManager != null && idManager.getId(newView) != null) ? idManager.getId(newView) : "newView";
                        attributeSet.add(new Attribute("android:id", "@+id/" + widgetId));
                        
                        ViewGroup.LayoutParams params = newView.getLayoutParams();
                        if (newView instanceof ViewGroup) {
                            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            attributeSet.add(new Attribute("android:layout_width", "match_parent"));
                            attributeSet.add(new Attribute("android:layout_height", "wrap_content"));
                        } else {
                            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            attributeSet.add(new Attribute("android:layout_width", "wrap_content"));
                            attributeSet.add(new Attribute("android:layout_height", "wrap_content"));
                        }
                        
                        addInitialAttributes(newView, viewData);
                        viewEditor.setDefaultTextSize(newView);
                        
                        if (!viewEditor.isUndoRedoInProgress) {
                            viewEditor.saveStateForUndo("add_widget");
                        }
                        
                        if (viewEditor.onWidgetAddListener != null) {
                            viewEditor.onWidgetAddListener.onWidgetAdded(newView, (ViewGroup) destinationView);
                        }
                        
                    } catch (Throwable t) {
                        SketchwareUtil.showMessage(context, "Create failed: " + t.getMessage());
                    }
                } else {

                    removeView(placeHolder);
                    addView(draggedView, (ViewGroup) destinationView, event);
                    
                    if (!viewEditor.isUndoRedoInProgress) {
                        viewEditor.saveStateForUndo("move_widget");
                    }
                }
                return true;
                
                case DragEvent.ACTION_DRAG_ENDED:
                if (viewEditor != null) {
                    viewEditor.log("drag ended on " + destinationView);
                }
                removeView(placeHolder);
                
                if (event.getResult() && viewEditor != null && viewEditor.vib != null) {
                    viewEditor.vib.vibrate(100);
                } else {
                    if (draggedView != null && idManager != null) {
                        idManager.remove(draggedView);
                        viewEditor.attributesValueMap.remove(draggedView);
                    }
                }
                draggedView = null;
                return true;
                
                default:
                break;
            }
        } catch (Exception e) {
            if (viewEditor != null) {
                viewEditor.showMessage(e.toString());
            }
        }
        
        return true;
    }
    
    private int getIndexForNewChildOfLinearLayout(LinearLayout linear, DragEvent dragEvent) {
        int orientation = linear.getOrientation();
        if (orientation == LinearLayout.VERTICAL) {
            int posY = (int) dragEvent.getY();
            int index = 0;
            for (int i = 0; i < linear.getChildCount(); i++) {
                View child = linear.getChildAt(i);
                if (child == placeHolder) continue;
                if (child.getTop() < posY) index++;
            }
            return index;
        } else if (orientation == LinearLayout.HORIZONTAL) {
            int posX = (int) dragEvent.getX();
            int index = 0;
            for (int i = 0; i < linear.getChildCount(); i++) {
                View child = linear.getChildAt(i);
                if (child == placeHolder) continue;
                if (child.getRight() < posX) index++;
            }
            return index;
        }
        return -1;
    }
    
    private int getGravityForNewChildOfFrameLayout(FrameLayout frameLayout, DragEvent event) {
        int gravity = 0;
        int posX = (int) event.getX();
        int posY = (int) event.getY();
        int height = frameLayout.getHeight();
        int width = frameLayout.getWidth();
        int childHeight = placeHolder.getLayoutParams().height;
        int childWidth = placeHolder.getLayoutParams().width;
        
        if (posX > width / 2 - childWidth && posX < width / 2 + childWidth) {
            gravity |= Gravity.CENTER_HORIZONTAL;
        }
        if (posY > height / 2 - childHeight && posY < height / 2 + childHeight) {
            gravity |= Gravity.CENTER_VERTICAL;
        }
        if (posX > width - childWidth) {
            gravity |= Gravity.RIGHT;
        }
        if (posY > height - childHeight) {
            gravity |= Gravity.BOTTOM;
        }
        return gravity;
    }
    
    private void addView(View view, ViewGroup destination, DragEvent event) {
        try {
            if (destination instanceof LinearLayout) {
                int index = getIndexForNewChildOfLinearLayout((LinearLayout) destination, event);
                addViewWithIndex(view, destination, index);
                return;
            }
            
            if (destination instanceof FrameLayout) {
                addViewWithIndex(view, destination, -1);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                params.gravity = getGravityForNewChildOfFrameLayout((FrameLayout) destination, event);
                return;
            }
            
            addViewWithIndex(view, destination, -1);
        } catch (Exception e) {
            SketchwareUtil.showMessage(context, "Add failed: " + e.getMessage());
        }
    }
    
    private void addViewWithIndex(View view, ViewGroup parent, int index) {
        if (index >= 0 && index < parent.getChildCount()) {
            parent.addView(view, index);
        } else {
            parent.addView(view);
        }
    }
    
    private void removeView(View view) {
        if (view.getParent() instanceof ViewGroup) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }
    
    private void addInitialAttributes(View view, HashMap<String, Object> map) {
        if (viewEditor != null && map != null && map.containsKey("initial_attributes")) {
            HashMap<String, String> initial_attributes = (HashMap<String, String>) map.get("initial_attributes");
            for (String key : initial_attributes.keySet()) {
                Attribute attr = new Attribute(key, initial_attributes.get(key));
                viewEditor.applyAttribute(view, attr);
                if (viewEditor.attributesValueMap.containsKey(view)) {
                    viewEditor.attributesValueMap.get(view).add(attr);
                }
            }
        }
    }
}
