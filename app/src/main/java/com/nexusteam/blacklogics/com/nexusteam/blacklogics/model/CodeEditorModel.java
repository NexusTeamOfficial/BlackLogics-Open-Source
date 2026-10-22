package com.nexusteam.blacklogics.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.nexusteam.blacklogics.bean.CodeEditorSettings;

import com.nexusteam.blacklogics.utils.FileUtil;
import com.nexusteam.blacklogics.editor.CodeEditorColorSchemes;
import com.nexusteam.blacklogics.editor.CodeEditorLanguages;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;

public class CodeEditorModel {

    private Context context;
    private String filePath;
    private String fileName;
    private String content;
    private CodeEditorSettings settings;
    private SharedPreferences preferences;

    public CodeEditorModel(Context context, String filePath, String fileName) {
        this.context = context;
        this.filePath = filePath;
        this.fileName = fileName;
        this.settings = new CodeEditorSettings();
        this.preferences = context.getSharedPreferences("hsce", Context.MODE_PRIVATE);
        loadContent();
        loadSettings();
    }

    private void loadContent() {
        if (FileUtil.isExistFile(filePath)) {
            this.content = FileUtil.readFile(filePath);
        } else {
            this.content = "";
        }
    }

    private void loadSettings() {
        settings.setTextSize(preferences.getInt("act_ts", 12));
        settings.setTheme(preferences.getInt("act_theme", 3));
        settings.setWordWrap(preferences.getBoolean("act_ww", false));
        settings.setAutoComplete(preferences.getBoolean("act_ac", true));
        settings.setSymbolPairAutoComplete(preferences.getBoolean("act_acsp", true));
        settings.setLanguage(getLanguageFromFileName());
    }

    public void saveContent(String newContent) {
        FileUtil.writeFile(filePath, newContent);
        this.content = newContent;
    }

    public void saveSettings() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("act_ts", settings.getTextSize());
        editor.putInt("act_theme", settings.getTheme());
        editor.putBoolean("act_ww", settings.isWordWrap());
        editor.putBoolean("act_ac", settings.isAutoComplete());
        editor.putBoolean("act_acsp", settings.isSymbolPairAutoComplete());
        editor.apply();
    }

    public void applyEditorSettings(CodeEditor editor) {
        editor.setTextSize(settings.getTextSize());
        editor.setWordwrap(settings.isWordWrap());
        editor.getProps().symbolPairAutoCompletion = settings.isSymbolPairAutoComplete();
        editor.getComponent(io.github.rosemoe.sora.widget.component.EditorAutoCompletion.class)
              .setEnabled(settings.isAutoComplete());
        
        applyLanguage(editor);
        applyTheme(editor);
    }

    private void applyLanguage(CodeEditor editor) {
    CodeEditorLanguages languages = CodeEditorLanguages.getInstance(context);
    CodeEditorColorSchemes schemes = CodeEditorColorSchemes.getInstance(context);

    String language = getLanguageFromFileName();
    switch (language) {
        case "java":
            editor.setEditorLanguage(languages.getJavaLanguage());
            break;
        case "kotlin":
            editor.setEditorLanguage(languages.getKotlinLanguage());
            editor.setColorScheme(schemes.getDraculaScheme());
            break;
        case "xml":
            editor.setEditorLanguage(languages.getXmlLanguage());
            editor.setColorScheme(schemes.getDraculaScheme());
            break;
        default:

            break;
    }
}


    private void applyTheme(CodeEditor editor) {


    }

    private String getLanguageFromFileName() {
        if (fileName.endsWith(".java")) return "java";
        if (fileName.endsWith(".kt")) return "kotlin";
        if (fileName.endsWith(".xml")) return "xml";
        return "text";
    }


    public String getContent() {
        return content;
    }

    public CodeEditorSettings getSettings() {
        return settings;
    }

    public void setSettings(CodeEditorSettings settings) {
        this.settings = settings;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean hasUnsavedChanges(String currentContent) {
        return !content.equals(currentContent);
    }
}