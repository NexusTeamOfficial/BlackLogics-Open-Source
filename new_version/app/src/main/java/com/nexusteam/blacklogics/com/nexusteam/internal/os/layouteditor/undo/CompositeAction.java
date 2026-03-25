package com.nexusteam.internal.os.layouteditor.undo;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CompositeAction implements UndoableAction {
    
    private final List<UndoableAction> actions;
    private final String description;
    private final long timestamp;
    private final String activityName;
    private final Map<String, Object> data;
    
    public CompositeAction(List<UndoableAction> actions, String description, String activityName) {
        this.actions = actions;
        this.description = description;
        this.timestamp = System.currentTimeMillis();
        this.activityName = activityName;
        this.data = new HashMap<>();
        this.data.put("actionCount", actions.size());
    }
    
    @Override
    public void undo() {
        // Undo in reverse order
        for (int i = actions.size() - 1; i >= 0; i--) {
            actions.get(i).undo();
        }
    }
    
    @Override
    public void redo() {
        // Redo in original order
        for (UndoableAction action : actions) {
            action.redo();
        }
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public long getTimestamp() {
        return timestamp;
    }
    
    @Override
    public ActionType getType() {
        return ActionType.BULK_CHANGE;
    }
    
    @Override
    public Map<String, Object> getData() {
        return data;
    }
    
    @Override
    public String getActivityName() {
        return activityName;
    }
}