package com.nexusteam.blacklogics.utils;

import android.content.res.AssetManager;
import android.util.Log;

import org.eclipse.tm4e.core.registry.IGrammarSource;
import org.eclipse.tm4e.core.registry.IThemeSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class AssetLoaderUtils {

    private static final String TAG = "AssetLoaderUtils";

    /* ---------------- GRAMMAR ---------------- */

    public static IGrammarSource loadGrammarFromAssets(
            final AssetManager assets,
            final String grammarPath
    ) {
        return new IGrammarSource() {

            @Override
            public String getFilePath() {
                return grammarPath;
            }

            @Override
            public Reader getReader() throws IOException {
                InputStream is = assets.open(grammarPath);
                return new InputStreamReader(is);
            }
        };
    }

    /* ---------------- THEME ---------------- */

    public static IThemeSource loadThemeFromAssets(
            final AssetManager assets,
            final String themePath
    ) {
        return new IThemeSource() {

            @Override
            public String getFilePath() {
                return themePath;
            }

            @Override
            public Reader getReader() throws IOException {
                InputStream is = assets.open(themePath);
                return new InputStreamReader(is);
            }
        };
    }

    /* ---------------- LIST GRAMMARS ---------------- */

    public static List<String> getAvailableGrammars(AssetManager assets) {
        List<String> grammars = new ArrayList<String>();
        try {
            String[] files = assets.list("textmate");
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    String file = files[i];
                    if (file.endsWith(".tmLanguage")
                            || file.endsWith(".tmLanguage.json")) {
                        grammars.add("textmate/" + file);
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed to list grammar files", e);
        }
        return grammars;
    }

    /* ---------------- LIST THEMES ---------------- */

    public static List<String> getAvailableThemes(AssetManager assets) {
        List<String> themes = new ArrayList<String>();
        try {
            String[] files = assets.list("textmate/themes");
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    String file = files[i];
                    if (file.endsWith(".json")
                            || file.endsWith(".tmTheme")) {
                        themes.add("textmate/themes/" + file);
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed to list theme files", e);
        }
        return themes;
    }
}
