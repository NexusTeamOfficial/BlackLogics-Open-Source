package com.nexusteam.blacklogics.editor;

import android.content.Context;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.nexusteam.blacklogics.*;
import com.nexusteam.blacklogics.interfaces.WidgetAddListener;
import com.nexusteam.blacklogics.interfaces.WidgetOnClick;
import com.nexusteam.blacklogics.interfaces.WidgetOnAttribute;
import com.nexusteam.blacklogics.manager.AddWidgetCommand;
import com.nexusteam.blacklogics.manager.BlacklogicsUndoRedoManager;
import com.nexusteam.blacklogics.manager.MoveWidgetCommand;
import com.nexusteam.blacklogics.utils.*;
import com.shapun.layouteditor.*;
import com.shapun.layouteditor.managers.*;
import com.shapun.layouteditor.utils.*;

import java.util.HashMap;
import java.util.Map;

public class ViewEditorDrag implements View.OnDragListener {
    
    private final Context context;
    
    private int[] parentLocation = new int[2];
    private View lastTargetView;
    
    private final View placeHolder;
    private final View.OnDragListener self = this;
    

    private final HashMap<View, AttributeSet> attributesValueMap = new HashMap<>();
    

    private IdManager idManager;
    private final BlacklogicsUndoRedoManager undoRedoManager = new BlacklogicsUndoRedoManager();
    private WidgetAddListener widgetAddListener;
    private WidgetOnAttribute widgetOnAttribute;
    private WidgetOnClick widgetOnClick;
    
    private boolean isUndoRedoInProgress = false;
    
    public ViewEditorDrag(Context context, View placeHolder) {
        this.context = context;
        this.placeHolder = placeHolder;
    }
    
    /* ---------------- DRAG ---------------- */
    
    @Override
    public boolean onDrag(View destinationView, DragEvent event) {
        
        View draggedView = null;
        if (event.getLocalState() instanceof View) {
            draggedView = (View) event.getLocalState();
        }
        

        destinationView.getLocationOnScreen(parentLocation);
        

        float screenX = event.getX() + parentLocation[0];
        float screenY = event.getY() + parentLocation[1];
        
        try {
            switch (event.getAction()) {
                
                case DragEvent.ACTION_DRAG_STARTED:
                if (draggedView != null) {
                    draggedView.setAlpha(0.3f); 
                    ViewGroupUtils.removeView(draggedView);
                }
                lastTargetView = null;
                return true;
                
                case DragEvent.ACTION_DRAG_LOCATION:
                
                if (lastTargetView != destinationView) {
                    lastTargetView = destinationView;
                }
                

                addPlaceHolder((ViewGroup) destinationView, event);
                return true;
                
                case DragEvent.ACTION_DRAG_EXITED:
                ViewGroupUtils.removeView(placeHolder);
                lastTargetView = null;
                return true;
                
                case DragEvent.ACTION_DROP:
                
                ViewGroupUtils.removeView(placeHolder);
                
                if (draggedView == null) {
                    View newWidget = createNewWidget(destinationView, event);
                    AddWidgetCommand cmd = new AddWidgetCommand(newWidget, (ViewGroup) destinationView, this);
                    undoRedoManager.executeCommand(cmd);
                    
                } else {
                    draggedView.setAlpha(1f); 
                    ViewGroup oldParent = (ViewGroup) draggedView.getParent();
                    int oldIndex = oldParent.indexOfChild(draggedView);
                    
                    int newIndex = getIndexForLinear((LinearLayout) destinationView, event);
                    
                    MoveWidgetCommand cmd = new MoveWidgetCommand(
                    draggedView,
                    oldParent,
                    oldIndex,
                    (ViewGroup) destinationView,
                    newIndex
                    );
                    
                    undoRedoManager.executeCommand(cmd);
                    
                    
                    if (!isUndoRedoInProgress) {
                        
                    }
                }
                return true;
                
                case DragEvent.ACTION_DRAG_ENDED:
                ViewGroupUtils.removeView(placeHolder);
                if (draggedView != null) {
                    draggedView.setAlpha(1f);
                }
                lastTargetView = null;
                return true;
            }
        } catch (Throwable e) {
            SketchwareUtil.showMessage(context, e.toString());
        }
        
        return true;
    }
    
    
    /* ---------------- CREATE ---------------- */
    
    @SuppressWarnings("unchecked")
    private View createNewWidget(View destinationView, DragEvent event) {
        try {
            Object localState = event.getLocalState();
            if (!(localState instanceof HashMap)) {
                SketchwareUtil.showMessage(context, "Invalid drag data");
                return null;
            }
            
            HashMap<String, Object> viewData = (HashMap<String, Object>) localState;
            
            View newView = ReflectionUtils.createView(
            context,
            viewData.get("class_path").toString()
            );
            
            newView.setMinimumHeight(dp(30));
            newView.setMinimumWidth(dp(30));
            
            if (widgetOnClick != null) {

            }
            
            addView(newView, (ViewGroup) destinationView, event);
            
            if (newView instanceof ViewGroup) {
                newView.setOnDragListener(this);
                int p = dp(8);
                newView.setPadding(p, p, p, p);
            }
            
            if (idManager != null) {
                String id = idManager.generate(newView);
                idManager.bind(newView, id);
            }
            
            AttributeSet set = new AttributeSet();
            attributesValueMap.put(newView, set);
            
            if (idManager != null) {
                set.add(new Attribute("android:id", "@+id/" + idManager.generate(newView)));
            }
            
            applyLayoutParams(newView, set);
            addInitialAttributes(newView, viewData);
            
            if (widgetAddListener != null) {
                widgetAddListener.onWidgetAdded(newView, (ViewGroup) destinationView);
            }
            
            return newView;
            
        } catch (Throwable t) {
            SketchwareUtil.showMessage(context, "Create failed: " + t.getMessage());
            return null;
        }
    }
    
    public void bindAttributes(View widget) {
        attributesValueMap.put(widget, new AttributeSet());
    }
    
    public void applyAttribute(View widget, Attribute attr) {
        if (widgetOnAttribute != null) {
            widgetOnAttribute.onWidgetAttributes(widget, attr);
        }
    }
    
    
    /* ---------------- ADD VIEW ---------------- */
    
    private void addView(View view, ViewGroup destination, DragEvent event) {
        
        if (destination instanceof LinearLayout) {
            int index = getIndexForLinear((LinearLayout) destination, event);
            ViewGroupUtils.addView(view, destination, index);
            return;
        }
        
        if (destination instanceof FrameLayout) {
            ViewGroupUtils.addView(view, destination);
            FrameLayout.LayoutParams lp =
            (FrameLayout.LayoutParams) view.getLayoutParams();
            lp.gravity = getGravityForFrame((FrameLayout) destination, event);
            return;
        }
        
        ViewGroupUtils.addView(view, destination);
    }
    
    /* ---------------- INDEX ---------------- */
    
    private int getIndexForLinear(LinearLayout linear, DragEvent event) {
        
        if (linear.getOrientation() == LinearLayout.VERTICAL) {
            int y = (int) event.getY();
            int index = 0;
            for (int i = 0; i < linear.getChildCount(); i++) {
                View c = linear.getChildAt(i);
                if (c == placeHolder) continue;
                if (c.getTop() < y) index++;
            }
            return index;
        }
        
        int x = (int) event.getX();
        int index = 0;
        for (int i = 0; i < linear.getChildCount(); i++) {
            View c = linear.getChildAt(i);
            if (c == placeHolder) continue;
            if (c.getRight() < x) index++;
        }
        return index;
    }
    
    private int getGravityForFrame(FrameLayout frame, DragEvent event) {
        
        int g = 0;
        int x = (int) event.getX();
        int y = (int) event.getY();
        
        if (x > frame.getWidth() / 2) g |= Gravity.RIGHT;
        if (y > frame.getHeight() / 2) g |= Gravity.BOTTOM;
        
        return g;
    }
    
    /* ---------------- PLACEHOLDER ---------------- */
    
    private void addPlaceHolder(ViewGroup parent, DragEvent event) {
        ViewGroupUtils.removeView(placeHolder);
        addView(placeHolder, parent, event);
    }
    
    /* ---------------- ATTRIBUTES ---------------- */
    
    @SuppressWarnings("unchecked")
    private void addInitialAttributes(View view, HashMap<String, Object> map) {
        
        if (!map.containsKey("initial_attributes")) return;
        
        Map<String, String> attrs =
        (Map<String, String>) map.get("initial_attributes");
        
        for (String key : attrs.keySet()) {
            Attribute attr = new Attribute(key, attrs.get(key));
            if (widgetOnAttribute != null) {
                widgetOnAttribute.onWidgetAttributes(view, attr);
            }
            attributesValueMap.get(view).add(attr);
        }
    }
    
    
    private void applyLayoutParams(View view, AttributeSet set) {
        
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        
        if (view instanceof ViewGroup) {
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            set.add(new Attribute("android:layout_width", "match_parent"));
            set.add(new Attribute("android:layout_height", "wrap_content"));
        } else {
            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            set.add(new Attribute("android:layout_width", "wrap_content"));
            set.add(new Attribute("android:layout_height", "wrap_content"));
        }
    }
    
    private int dp(int v) {
        return (int) SketchwareUtil.getDip(context, v);
    }
    
}
