package com.nexusteam.blacklogics.manager;

import com.nexusteam.blacklogics.interfaces.Command;
import java.util.Stack;

public class BlacklogicsUndoRedoManager {

    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();
    private boolean isUndoRedoInProgress = false;

    public boolean isUndoRedoInProgress() {
        return isUndoRedoInProgress;
    }

    public void executeCommand(Command command) {
        if (isUndoRedoInProgress) return;
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        if (undoStack.isEmpty()) return;
        isUndoRedoInProgress = true;
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
        isUndoRedoInProgress = false;
    }

    public void redo() {
        if (redoStack.isEmpty()) return;
        isUndoRedoInProgress = true;
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
        isUndoRedoInProgress = false;
    }

    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}
