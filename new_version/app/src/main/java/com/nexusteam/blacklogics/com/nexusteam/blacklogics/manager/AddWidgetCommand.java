package com.nexusteam.blacklogics.manager;

import android.view.View;
import android.view.ViewGroup;

import com.nexusteam.blacklogics.editor.ViewEditorDrag;
import com.nexusteam.blacklogics.interfaces.Command;

public class AddWidgetCommand implements Command {
    private final View widget;
    private final ViewGroup parent;
    private final ViewEditorDrag editor;

    public AddWidgetCommand(View widget, ViewGroup parent, ViewEditorDrag editor) {
        this.widget = widget;
        this.parent = parent;
        this.editor = editor;
    }

    @Override
    public void execute() {
        parent.addView(widget);
        editor.bindAttributes(widget);
    }
 
    @Override
    public void undo() {
        parent.removeView(widget);
    }
}
