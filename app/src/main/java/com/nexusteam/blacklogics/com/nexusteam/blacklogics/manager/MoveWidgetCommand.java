package com.nexusteam.blacklogics.manager;

import android.view.View;
import android.view.ViewGroup;

import com.nexusteam.blacklogics.interfaces.Command;

public class MoveWidgetCommand implements Command {
    private final View widget;
    private final ViewGroup oldParent;
    private final int oldIndex;
    private final ViewGroup newParent;
    private final int newIndex;

    public MoveWidgetCommand(View widget, ViewGroup oldParent, int oldIndex,
                             ViewGroup newParent, int newIndex) {
        this.widget = widget;
        this.oldParent = oldParent;
        this.oldIndex = oldIndex;
        this.newParent = newParent;
        this.newIndex = newIndex;
    }

    @Override
    public void execute() {
        oldParent.removeView(widget);
        newParent.addView(widget, newIndex);
    }

    @Override
    public void undo() {
        newParent.removeView(widget);
        oldParent.addView(widget, oldIndex);
    }
}
