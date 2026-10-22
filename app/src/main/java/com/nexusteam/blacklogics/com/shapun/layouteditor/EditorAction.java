package com.shapun.layouteditor;

import android.view.View;
import android.view.ViewGroup;
import com.shapun.layouteditor.AttributeSet;
import com.shapun.layouteditor.IdManager;

public class EditorAction {
    public static final int ACTION_ADD_VIEW = 1;
    public static final int ACTION_REMOVE_VIEW = 2;
    public static final int ACTION_UPDATE_ATTR = 3;
    
    public int actionType;
    public View view;
    public ViewGroup parent;
    public int index;
    public AttributeSet oldAttributes;
    public AttributeSet newAttributes;
    public String viewId;
    public String activityName;
    public String xmlSnapshot;
    
    private IdManager idManager;
    
    public EditorAction(int actionType, View view, ViewGroup parent, int index,
                       AttributeSet oldAttributes, AttributeSet newAttributes, 
                       String activityName, String xmlSnapshot, IdManager idManager) {
        this.actionType = actionType;
        this.view = view;
        this.parent = parent;
        this.index = index;
        this.oldAttributes = oldAttributes;
        this.newAttributes = newAttributes;
        this.viewId = (view != null && idManager != null) ? idManager.getId(view) : null;
        this.activityName = activityName;
        this.xmlSnapshot = xmlSnapshot;
        this.idManager = idManager;
    }
    
    public EditorAction(int actionType, View view, ViewGroup parent, int index,
                       AttributeSet oldAttributes, AttributeSet newAttributes, 
                       String activityName, String xmlSnapshot) {
        this(actionType, view, parent, index, oldAttributes, newAttributes, activityName, xmlSnapshot, null);
    }
    

    public int getActionType() { return actionType; }
    public View getView() { return view; }
    public ViewGroup getParent() { return parent; }
    public int getIndex() { return index; }
    public AttributeSet getOldAttributes() { return oldAttributes; }
    public AttributeSet getNewAttributes() { return newAttributes; }
    public String getViewId() { return viewId; }
    public String getActivityName() { return activityName; }
    public String getXmlSnapshot() { return xmlSnapshot; }
    

    public void setIdManager(IdManager idManager) { 
        this.idManager = idManager;
        if (view != null && idManager != null) {
            this.viewId = idManager.getId(view);
        }
    }
}