package com.nexusteam.blacklogics.manager;

import android.view.View;

import com.shapun.layouteditor.Attribute;
import com.nexusteam.blacklogics.editor.ViewEditorDrag;
import com.nexusteam.blacklogics.interfaces.Command;

public class ChangeAttributeCommand implements Command {

    private final View widget;
    private final Attribute oldAttr;
    private final Attribute newAttr;
    private final ViewEditorDrag editor;

    public ChangeAttributeCommand(View widget, Attribute oldAttr, Attribute newAttr, ViewEditorDrag editor) {
        this.widget = widget;
        this.oldAttr = oldAttr;
        this.newAttr = newAttr;
        this.editor = editor;
    }

    @Override
    public void execute() {
        editor.applyAttribute(widget, newAttr);
    }

    @Override
    public void undo() {
        editor.applyAttribute(widget, oldAttr);
    }
}
