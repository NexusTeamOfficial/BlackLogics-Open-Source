package com.nexusteam.internal.os.layouteditor.undo;

public enum ActionType {
    ADD_VIEW(1),
    REMOVE_VIEW(2),
    UPDATE_ATTRIBUTE(3),
    MOVE_VIEW(4),
    CONVERT_WIDGET(5),
    BULK_CHANGE(6); // Add missing constant
    
    private final int value;
    
    ActionType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    public static ActionType fromValue(int value) {
        for (ActionType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        return UPDATE_ATTRIBUTE;
    }
}