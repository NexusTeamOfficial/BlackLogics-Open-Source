package com.nexusteam.blacklogics.activities;

import android.app.AlertDialog;

import android.content.DialogInterface;
import com.apk.builder.ApplicationLoader; // Ya jo bhi correct package ho
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.models.CodeEditorModel;
import com.nexusteam.blacklogics.utils.CodeEditorUtils;
import com.nexusteam.blacklogics.utils.FileTypeDetector;

import com.nexusteam.blacklogics.utils.BlackLogicsUtil;
import com.nexusteam.sdk.code.ResHelper;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;

public class SrcCodeEditor extends AppCompatActivity {
    
    private LinearLayout toolbar;
    private CodeEditor editor;
    private CodeEditorModel editorModel;
    private ImageView menu_view_undo, menu_view_redo, save, more;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_editor_hs);
        
        initializeViews();
        initializeEditor();
        setupListeners();
    }
    
    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        editor = findViewById(R.id.editor);
        menu_view_undo = findViewById(R.id.menu_view_undo);
        menu_view_redo = findViewById(R.id.menu_view_redo);
        more = findViewById(R.id.more);
        save = findViewById(R.id.save);
    }
    
    private void initializeEditor() {
        toolbar.setVisibility(View.VISIBLE);
        
        String title = getIntent().getStringExtra("title");
        String contentPath = getIntent().getStringExtra("content");
        
        setTitle(title);
        

        editorModel = new CodeEditorModel(this, contentPath, title);
        

        editor.setTypefaceText(android.graphics.Typeface.MONOSPACE);
        editor.setText(editorModel.getContent());
        

        editorModel.applyEditorSettings(editor);
        

        CodeEditorUtils.loadEditorSettings(this, editor, "act");
    }
    
    private void setupListeners() {
        menu_view_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.undo();
            }
        });
        
        menu_view_redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.redo();
            }
        });
        
        
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                saveFile();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }
    
    private void showPopupMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        Menu menu = popupMenu.getMenu();
        

        menu.add(0, 0, 0, "Find & Replace");
        menu.add(0, 1, 1, "Switch Theme");
        menu.add(0, 2, 2, "Pretty Print");
        menu.add(0, 3, 3, "Word Wrap");
        menu.add(0, 4, 4, "Auto Complete");
        menu.add(0, 5, 5, "Auto Complete Symbol Pair");
        
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                handlePopupMenuItemClick(item);
                return true;
            }
        });
        
        
        popupMenu.show();
    }
    
    private void handlePopupMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case 0: // Find & Replace
            editor.getSearcher().stopSearch();
            editor.beginSearchMode();
            break;
            
            case 1: // Switch Theme
            showThemeSelector();
            break;
            
            case 2: // Pretty Print
            performPrettyPrint();
            break;
            
            case 3: // Word Wrap
            toggleWordWrap();
            break;
            
            case 4: // Auto Complete
            toggleAutoComplete();
            break;
            
            case 5: // Auto Complete Symbol Pair
            toggleSymbolPairAutoComplete();
            break;
        }
    }
    
    private void showThemeSelector() {
        String[] themes = new String[]{"Default", "GitHub", "Eclipse", "Dracula", "VS2019", "NotepadXX"};
        
        new AlertDialog.Builder(this)
        .setTitle("Switch Theme")
        .setSingleChoiceItems(themes, editorModel.getSettings().getTheme(),
        new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CodeEditorUtils.applyTheme(editor, which, ApplicationLoader.getContext());
                editorModel.getSettings().setTheme(which);
                editorModel.saveSettings();
                dialog.dismiss();
            }
        })
        .setNegativeButton(R.string.common_word_cancel, null)
        .show();
        
    }
    
    private void performPrettyPrint() {
        String fileName = editorModel.getFileName();
        if (FileTypeDetector.isXmlFile(fileName)) {
            String formatted = CodeEditorUtils.prettifyXml(
            editor.getText().toString(), 4, getIntent()
            );
            if (formatted != null) {
                editor.setText(formatted);
                BlackLogicsUtil.toast("XML formatted successfully");
            }
        } else if (FileTypeDetector.isJavaFile(fileName)) {
            BlackLogicsUtil.toast("Java pretty print not implemented yet");
        } else {
            BlackLogicsUtil.toast("Only Java and XML files can be formatted");
        }
    }
    
    private void toggleWordWrap() {
        boolean newWordWrap = !editor.isWordwrap();
        editor.setWordwrap(newWordWrap);
        editorModel.getSettings().setWordWrap(newWordWrap);
        editorModel.saveSettings();
        BlackLogicsUtil.toast("Word wrap " + (newWordWrap ? "enabled" : "disabled"));
    }
    
    private void toggleAutoComplete() {
        boolean newAutoComplete = !editor.getComponent(EditorAutoCompletion.class).isEnabled();
        editor.getComponent(EditorAutoCompletion.class).setEnabled(newAutoComplete);
        editorModel.getSettings().setAutoComplete(newAutoComplete);
        editorModel.saveSettings();
        BlackLogicsUtil.toast("Auto complete " + (newAutoComplete ? "enabled" : "disabled"));
    }
    
    private void toggleSymbolPairAutoComplete() {
        boolean newSymbolPair = !editor.getProps().symbolPairAutoCompletion;
        editor.getProps().symbolPairAutoCompletion = newSymbolPair;
        editorModel.getSettings().setSymbolPairAutoComplete(newSymbolPair);
        editorModel.saveSettings();
        BlackLogicsUtil.toast("Symbol pair auto complete " + (newSymbolPair ? "enabled" : "disabled"));
    }
    
    private void saveFile() {
        String currentContent = editor.getText().toString();
        editorModel.saveContent(currentContent);
        BlackLogicsUtil.toast("Saved");
    }
    
    @Override
    public void onBackPressed() {
        if (editorModel.hasUnsavedChanges(editor.getText().toString())) {
            showUnsavedChangesDialog();
        } else {
            super.onBackPressed();
        }
    }
    
    private void showUnsavedChangesDialog() {
        new AlertDialog.Builder(this)
        .setTitle("Warning")
        .setMessage("You have unsaved changes. Are you sure you want to exit?")
        .setPositiveButton(R.string.common_word_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
        
        .setNegativeButton(R.string.common_word_cancel, null)
        .create()
        .show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.clear();
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Undo")
        .setIcon(getDrawable(R.drawable.ic_undo_white_48dp))
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Redo")
        .setIcon(getDrawable(R.drawable.ic_redo_white_48dp))
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Save")
        .setIcon(getDrawable(R.drawable.save_white_48))
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        switch (title) {
            case "Undo":
            editor.undo();
            break;
            case "Redo":
            editor.redo();
            break;
            case "Save":
            saveFile();
            break;
        }
        return true;
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        CodeEditorUtils.saveEditorSettings(this, editor, "act");
    }
}
