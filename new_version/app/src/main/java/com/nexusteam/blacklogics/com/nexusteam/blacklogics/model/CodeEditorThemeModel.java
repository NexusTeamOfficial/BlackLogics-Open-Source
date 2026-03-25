package com.nexusteam.blacklogics.models;

import android.content.Context;
import android.content.res.AssetManager;

import com.nexusteam.blacklogics.bean.EditorThemeConfig;
import com.nexusteam.blacklogics.utils.AssetLoaderUtils;

import org.eclipse.tm4e.core.registry.IThemeSource;

import java.util.ArrayList;
import java.util.List;

import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

public class CodeEditorThemeModel {

    private Context context;
    private ThemeRegistry themeRegistry;
    private List<EditorThemeConfig> availableThemes;
    private EditorColorScheme currentTheme;

    public CodeEditorThemeModel(Context context) {
        this.context = context;
        this.themeRegistry = ThemeRegistry.getInstance();
        this.availableThemes = new ArrayList<>();
        loadAvailableThemes();
    }

    private void loadAvailableThemes() {

        availableThemes.add(new EditorThemeConfig("dracula", "dracula.json", "Dracula", true));
        availableThemes.add(new EditorThemeConfig("github", "GitHub.tmTheme", "GitHub", false));
        availableThemes.add(new EditorThemeConfig("monokai", "monokai.json", "Monokai", true));
        availableThemes.add(new EditorThemeConfig("solarized", "solarized.json", "Solarized", false));
    }

    public EditorColorScheme getTheme(String themeName) {
        EditorThemeConfig themeConfig = findTheme(themeName);
        if (themeConfig == null) {
            return new EditorColorScheme();
        }

        try {
            AssetManager assets = context.getAssets();
            IThemeSource themeSource = AssetLoaderUtils.loadThemeFromAssets(
                assets, "textmate/themes/" + themeConfig.getThemeFile()
            );

            if (themeSource != null) {
                themeRegistry.loadTheme(themeSource);
                currentTheme = TextMateColorScheme.create(themeRegistry);
                return currentTheme;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new EditorColorScheme();
    }

    public EditorColorScheme getDraculaScheme() {
        return getTheme("dracula");
    }

    public EditorColorScheme getGitHubScheme() {
        return getTheme("github");
    }

    public EditorColorScheme getMonokaiScheme() {
        return getTheme("monokai");
    }

    public EditorColorScheme getSolarizedScheme() {
        return getTheme("solarized");
    }

    private EditorThemeConfig findTheme(String themeName) {
        for (EditorThemeConfig theme : availableThemes) {
            if (theme.getThemeName().equalsIgnoreCase(themeName) || 
                theme.getDisplayName().equalsIgnoreCase(themeName)) {
                return theme;
            }
        }
        return null;
    }

    public List<String> getAvailableThemeNames() {
        List<String> names = new ArrayList<>();
        for (EditorThemeConfig theme : availableThemes) {
            names.add(theme.getDisplayName());
        }
        return names;
    }

    public List<EditorThemeConfig> getAvailableThemes() {
        return new ArrayList<>(availableThemes);
    }

    public EditorColorScheme getCurrentTheme() {
        return currentTheme != null ? currentTheme : getDraculaScheme();
    }

    public void clearCache() {
        currentTheme = null;
    }
}