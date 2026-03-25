package com.nexusteam.blacklogics.editor;

import android.content.Context;

import com.nexusteam.blacklogics.models.CodeEditorLanguageModel;

import io.github.rosemoe.sora.lang.Language;

public class CodeEditorLanguages {

    private static CodeEditorLanguages instance;
    private CodeEditorLanguageModel languageModel;

    private CodeEditorLanguages(Context context) {
        languageModel = new CodeEditorLanguageModel(context.getApplicationContext());
    }

    public static synchronized CodeEditorLanguages getInstance(Context context) {
        if (instance == null) {
            instance = new CodeEditorLanguages(context);
        }
        return instance;
    }

    public Language getKotlinLanguage() {
        return languageModel.getKotlinLanguage();
    }

    public Language getXmlLanguage() {
        return languageModel.getXmlLanguage();
    }

    public Language getJavaLanguage() {
        return languageModel.getJavaLanguage();
    }

    public Language getLanguage(String languageName) {
        return languageModel.getLanguage(languageName);
    }

    public Language getLanguageForFile(String fileName) {
        if (fileName.endsWith(".kt")) {
            return getKotlinLanguage();
        } else if (fileName.endsWith(".xml")) {
            return getXmlLanguage();
        } else if (fileName.endsWith(".java")) {
            return getJavaLanguage();
        } else if (fileName.endsWith(".js")) {
            return getLanguage("javascript");
        } else if (fileName.endsWith(".html")) {
            return getLanguage("html");
        } else if (fileName.endsWith(".css")) {
            return getLanguage("css");
        } else {
            return languageModel.getLanguage("text");
        }
    }

    public void clearCache() {
        languageModel.clearCache();
    }
}