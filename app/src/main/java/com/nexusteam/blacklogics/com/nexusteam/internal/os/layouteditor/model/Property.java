package com.nexusteam.internal.os.layouteditor.model;

public class Property {
    public static final int TYPE_HEIGHT = 1;
    public static final int TYPE_ID = 3;
    public static final int TYPE_TEXT = 2;
    public static final int TYPE_WIDTH = 0;
    private int mType;

    public Property(int i) {
        this.mType = i;
    }

    public int getType() {
        return this.mType;
    }
}

