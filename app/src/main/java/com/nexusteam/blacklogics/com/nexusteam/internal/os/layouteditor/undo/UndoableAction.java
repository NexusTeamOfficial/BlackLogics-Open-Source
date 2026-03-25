package com.nexusteam.internal.os.layouteditor.undo;

import android.view.View;
import java.util.Map;

public interface UndoableAction {
    void undo();
    void redo();
    String getDescription();
    long getTimestamp();
    ActionType getType();
    Map<String, Object> getData();
    String getActivityName();
}