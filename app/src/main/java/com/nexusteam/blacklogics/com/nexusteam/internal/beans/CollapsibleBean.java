package com.nexusteam.internal.beans;

import com.nexusteam.internal.jo;

public class CollapsibleBean extends jo {
    public int buttonPressed = -1;
    public boolean isCollapsed = true;
    public boolean isConfirmation = false;
    public boolean isSelected = false;

    public void initValue() {
        this.isCollapsed = true;
        this.isConfirmation = false;
        this.isSelected = false;
        this.buttonPressed = -1;
    }
}
