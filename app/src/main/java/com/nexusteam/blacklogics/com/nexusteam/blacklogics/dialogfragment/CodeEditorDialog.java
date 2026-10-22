package com.nexusteam.blacklogics.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.models.CodeEditorModel;
import com.nexusteam.blacklogics.utils.CodeEditorUtils;

import io.github.rosemoe.sora.widget.CodeEditor;
import com.nexusteam.blacklogics.utils.BlackLogicsUtil;

public class CodeEditorDialog {
    
    private Context context;
    private AlertDialog dialog;
    private CodeEditor editor;
    private CodeEditorModel editorModel;
    
    public CodeEditorDialog(Context context) {
        this.context = context;
    }
    
    public void show(String title, String filePath) {
        View view = LayoutInflater.from(context).inflate(R.layout.code_editor_hs, null);
        editor = view.findViewById(R.id.editor);
        

        editorModel = new CodeEditorModel(context, filePath, title);
        

        editor.setTypefaceText(android.graphics.Typeface.MONOSPACE);
        editor.setText(editorModel.getContent());
        editorModel.applyEditorSettings(editor);
        CodeEditorUtils.loadEditorSettings(context, editor, "dlg");
        

        view.findViewById(R.id.menu_view_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.undo();
            }
        });
        
        view.findViewById(R.id.menu_view_redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.redo();
            }
        });
        
        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile();
            }
        });
        
        view.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
        

        dialog = new AlertDialog.Builder(context)
        .setTitle(title)
        .setView(view)
        .setNegativeButton("Close", null)
        .create();
        
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                CodeEditorUtils.saveEditorSettings(context, editor, "dlg");
            }
        });
        
        dialog.show();
    }
    
    private void showMenu(View anchor) {

    }
    
    private void saveFile() {
        String currentContent = editor.getText().toString();
        editorModel.saveContent(currentContent);
        BlackLogicsUtil.toast("Saved");
    }
}
