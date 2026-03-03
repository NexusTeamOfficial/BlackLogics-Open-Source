package com.nexusteam.blacklogics.editor;

import android.content.Context;

import com.nexusteam.blacklogics.models.CodeEditorThemeModel;

import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

public class CodeEditorColorSchemes {

    private static CodeEditorColorSchemes instance;
    private CodeEditorThemeModel themeModel;

    private CodeEditorColorSchemes(Context context) {
        themeModel = new CodeEditorThemeModel(context.getApplicationContext());
    }

    public static synchronized CodeEditorColorSchemes getInstance(Context context) {
        if (instance == null) {
            instance = new CodeEditorColorSchemes(context);
        }
        return instance;
    }

    public EditorColorScheme getDraculaScheme() {
        return themeModel.getDraculaScheme();
    }

    public EditorColorScheme getGitHubScheme() {
        return themeModel.getGitHubScheme();
    }

    public EditorColorScheme getMonokaiScheme() {
        return themeModel.getMonokaiScheme();
    }

    public EditorColorScheme getSolarizedScheme() {
        return themeModel.getSolarizedScheme();
    }

    public EditorColorScheme getTheme(String themeName) {
        return themeModel.getTheme(themeName);
    }

    public EditorColorScheme getCurrentTheme() {
        return themeModel.getCurrentTheme();
    }

    public String[] getAvailableThemeNames() {
        return themeModel.getAvailableThemeNames().toArray(new String[0]);
    }

    public void clearCache() {
        themeModel.clearCache();
    }
}