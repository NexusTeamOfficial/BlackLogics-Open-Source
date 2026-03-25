package com.nexusteam.blacklogics.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.nexusteam.blacklogics.bean.TextMateGrammar;

import org.eclipse.tm4e.core.registry.IGrammarSource;
import org.eclipse.tm4e.core.registry.IThemeSource;

import java.util.HashMap;
import java.util.Map;

import io.github.rosemoe.sora.lang.EmptyLanguage;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;

public class LanguageManager {

    private static final Map<String, Language> languageCache = new HashMap<>();
    private static ThemeRegistry themeRegistry;

    public static Language getLanguage(Context context, String languageName) {
        if (languageCache.containsKey(languageName)) {
            return languageCache.get(languageName);
        }

        Language language = createLanguage(context, languageName);
        languageCache.put(languageName, language);
        return language;
    }

    private static Language createLanguage(Context context, String languageName) {
        AssetManager assets = context.getAssets();
        
        TextMateGrammar grammar = getGrammarForLanguage(languageName);
        if (grammar == null) {
            return new EmptyLanguage();
        }

        try {
            IGrammarSource grammarSource = AssetLoaderUtils.loadGrammarFromAssets(
                assets, "textmate/" + grammar.getFileName()
            );
            
            IThemeSource themeSource = AssetLoaderUtils.loadThemeFromAssets(
                assets, "textmate/themes/dracula.json"
            );

            if (grammarSource != null && themeSource != null) {
                return TextMateLanguage.create(grammarSource, themeSource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new EmptyLanguage();
    }

    private static TextMateGrammar getGrammarForLanguage(String languageName) {
        switch (languageName.toLowerCase()) {
            case "kotlin":
                return new TextMateGrammar("Kotlin", "kotlin.tmLanguage", "source.kotlin", ".kt");
            case "xml":
                return new TextMateGrammar("XML", "xml.tmLanguage.json", "text.xml", ".xml");
            case "java":
                return new TextMateGrammar("Java", "java.tmLanguage.json", "source.java", ".java");
            case "javascript":
                return new TextMateGrammar("JavaScript", "javascript.tmLanguage.json", "source.js", ".js");
            case "html":
                return new TextMateGrammar("HTML", "html.tmLanguage.json", "text.html.basic", ".html");
            case "css":
                return new TextMateGrammar("CSS", "css.tmLanguage.json", "source.css", ".css");
            default:
                return null;
        }
    }

    public static void clearCache() {
        languageCache.clear();
    }
}