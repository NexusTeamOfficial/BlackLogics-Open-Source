package com.nexusteam.blacklogics.models;

import android.content.Context;
import android.content.res.AssetManager;

import com.nexusteam.blacklogics.bean.TextMateGrammar;
import com.nexusteam.blacklogics.utils.AssetLoaderUtils;

import org.eclipse.tm4e.core.registry.IGrammarSource;
import org.eclipse.tm4e.core.registry.IThemeSource;

import java.util.ArrayList;
import java.util.List;

import io.github.rosemoe.sora.lang.EmptyLanguage;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;

public class CodeEditorLanguageModel {

    private Context context;
    private Language currentLanguage;
    private List<TextMateGrammar> availableGrammars;
    private ThemeRegistry themeRegistry;

    public CodeEditorLanguageModel(Context context) {
        this.context = context;
        this.availableGrammars = new ArrayList<>();
        loadAvailableGrammars();
        initializeThemeRegistry();
    }

    private void loadAvailableGrammars() {

        availableGrammars.add(new TextMateGrammar("Kotlin", "kotlin.tmLanguage", "source.kotlin", ".kt"));
        availableGrammars.add(new TextMateGrammar("XML", "xml.tmLanguage.json", "text.xml", ".xml"));
        availableGrammars.add(new TextMateGrammar("Java", "java.tmLanguage.json", "source.java", ".java"));
        availableGrammars.add(new TextMateGrammar("JavaScript", "javascript.tmLanguage.json", "source.js", ".js"));
        availableGrammars.add(new TextMateGrammar("HTML", "html.tmLanguage.json", "text.html.basic", ".html"));
        availableGrammars.add(new TextMateGrammar("CSS", "css.tmLanguage.json", "source.css", ".css"));
    }

    private void initializeThemeRegistry() {
        themeRegistry = ThemeRegistry.getInstance();
    }

    public Language getLanguage(String languageName) {
        TextMateGrammar grammar = findGrammar(languageName);
        if (grammar == null) {
            return new EmptyLanguage();
        }

        try {
            AssetManager assets = context.getAssets();
            
            IGrammarSource grammarSource = AssetLoaderUtils.loadGrammarFromAssets(
                assets, "textmate/" + grammar.getFileName()
            );
            
            IThemeSource themeSource = AssetLoaderUtils.loadThemeFromAssets(
                assets, "textmate/themes/dracula.json"
            );

            if (grammarSource != null && themeSource != null) {
                currentLanguage = TextMateLanguage.create(grammarSource, themeSource);
                return currentLanguage;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new EmptyLanguage();
    }

    public Language getKotlinLanguage() {
        return getLanguage("kotlin");
    }

    public Language getXmlLanguage() {
        return getLanguage("xml");
    }

    public Language getJavaLanguage() {
        return getLanguage("java");
    }

    private TextMateGrammar findGrammar(String languageName) {
        for (TextMateGrammar grammar : availableGrammars) {
            if (grammar.getName().equalsIgnoreCase(languageName)) {
                return grammar;
            }
        }
        return null;
    }

    public List<String> getAvailableLanguageNames() {
        List<String> names = new ArrayList<>();
        for (TextMateGrammar grammar : availableGrammars) {
            names.add(grammar.getName());
        }
        return names;
    }

    public ThemeRegistry getThemeRegistry() {
        return themeRegistry;
    }

    public void clearCache() {
        currentLanguage = null;
    }
}