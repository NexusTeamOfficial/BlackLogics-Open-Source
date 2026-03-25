package com.nexusteam.internal.os.layouteditor.undo;

import android.util.Log;
import com.shapun.layouteditor.ViewEditor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class UndoManager {
    
    private static final int MAX_STACK_SIZE = 30;
    private static UndoManager instance;
    
    private final Map<String, Stack<UndoableAction>> undoStacks = new HashMap<>();
    private final Map<String, Stack<UndoableAction>> redoStacks = new HashMap<>();
    private final Map<String, List<UndoListener>> listeners = new HashMap<>();
    
    private boolean isBatchMode = false;
    private List<UndoableAction> batchActions = new ArrayList<>();
    private String currentBatchActivity;
    
    public interface UndoListener {
        void onStackChanged(String activityName, boolean canUndo, boolean canRedo);
    }
    
    private UndoManager() {}
    
    public static synchronized UndoManager getInstance() {
        if (instance == null) {
            instance = new UndoManager();
        }
        return instance;
    }
    
    // Push action to undo stack
    public void pushAction(UndoableAction action) {
        if (action == null) return;
        
        String activityName = action.getActivityName();
        if (activityName == null) return;
        
        if (isBatchMode && currentBatchActivity != null && 
            currentBatchActivity.equals(activityName)) {
            batchActions.add(action);
            return;
        }
        
        getUndoStack(activityName).push(action);
        getRedoStack(activityName).clear();
        
        // Limit stack size
        Stack<UndoableAction> stack = getUndoStack(activityName);
        while (stack.size() > MAX_STACK_SIZE) {
            stack.remove(0);
        }
        
        notifyListeners(activityName);
    }
    
    // Start batch operation (multiple actions as one)
    public void beginBatch(String activityName) {
        this.isBatchMode = true;
        this.currentBatchActivity = activityName;
        this.batchActions.clear();
    }
    
    // End batch and push combined action
    public void endBatch(String description) {
        if (!isBatchMode || batchActions.isEmpty()) {
            isBatchMode = false;
            return;
        }
        
        // Create composite action
        CompositeAction composite = new CompositeAction(
            batchActions, 
            description, 
            currentBatchActivity
        );
        
        pushAction(composite);
        
        isBatchMode = false;
        currentBatchActivity = null;
        batchActions.clear();
    }
    
    // Undo
    public boolean undo(String activityName) {
        Stack<UndoableAction> undoStack = getUndoStack(activityName);
        if (undoStack.isEmpty()) {
            return false;
        }
        
        UndoableAction action = undoStack.pop();
        Stack<UndoableAction> redoStack = getRedoStack(activityName);
        
        try {
            action.undo();
            redoStack.push(action);
            notifyListeners(activityName);
            return true;
        } catch (Exception e) {
            Log.e("UndoManager", "Undo failed", e);
            return false;
        }
    }
    
    // Redo
    public boolean redo(String activityName) {
        Stack<UndoableAction> redoStack = getRedoStack(activityName);
        if (redoStack.isEmpty()) {
            return false;
        }
        
        UndoableAction action = redoStack.pop();
        Stack<UndoableAction> undoStack = getUndoStack(activityName);
        
        try {
            action.redo();
            undoStack.push(action);
            notifyListeners(activityName);
            return true;
        } catch (Exception e) {
            Log.e("UndoManager", "Redo failed", e);
            return false;
        }
    }
    
    // Clear history for activity
    public void clearHistory(String activityName) {
        getUndoStack(activityName).clear();
        getRedoStack(activityName).clear();
        notifyListeners(activityName);
    }
    
    // Get descriptions for UI
    public String getUndoDescription(String activityName) {
        Stack<UndoableAction> stack = getUndoStack(activityName);
        return stack.isEmpty() ? null : stack.peek().getDescription();
    }
    
    public String getRedoDescription(String activityName) {
        Stack<UndoableAction> stack = getRedoStack(activityName);
        return stack.isEmpty() ? null : stack.peek().getDescription();
    }
    
    // Check states
    public boolean canUndo(String activityName) {
        return !getUndoStack(activityName).isEmpty();
    }
    
    public boolean canRedo(String activityName) {
        return !getRedoStack(activityName).isEmpty();
    }
    
    // Stack management - Fix generic types
    @SuppressWarnings("unchecked")
    private Stack<UndoableAction> getUndoStack(String activityName) {
        if (!undoStacks.containsKey(activityName)) {
            undoStacks.put(activityName, new Stack<UndoableAction>());
        }
        return undoStacks.get(activityName);
    }
    
    @SuppressWarnings("unchecked")
    private Stack<UndoableAction> getRedoStack(String activityName) {
        if (!redoStacks.containsKey(activityName)) {
            redoStacks.put(activityName, new Stack<UndoableAction>());
        }
        return redoStacks.get(activityName);
    }
    
    // Listeners - Fix generic types
    @SuppressWarnings("unchecked")
    public void addListener(String activityName, UndoListener listener) {
        if (!listeners.containsKey(activityName)) {
            listeners.put(activityName, new ArrayList<UndoListener>());
        }
        listeners.get(activityName).add(listener);
    }
    
    public void removeListener(String activityName, UndoListener listener) {
        List<UndoListener> list = listeners.get(activityName);
        if (list != null) {
            list.remove(listener);
        }
    }
    
    private void notifyListeners(String activityName) {
        List<UndoListener> list = listeners.get(activityName);
        if (list != null) {
            boolean canUndo = canUndo(activityName);
            boolean canRedo = canRedo(activityName);
            for (UndoListener listener : list) {
                listener.onStackChanged(activityName, canUndo, canRedo);
            }
        }
    }
}