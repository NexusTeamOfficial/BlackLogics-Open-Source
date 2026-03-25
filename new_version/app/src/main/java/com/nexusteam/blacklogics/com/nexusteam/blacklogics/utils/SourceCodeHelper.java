package com.nexusteam.blacklogics.utils;

import android.content.Context;
import android.graphics.Typeface;

import io.github.rosemoe.sora.langs.java.JavaLanguage;


import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.Magnifier;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

import com.nexusteam.blacklogics.bean.SourceCodeData;
import com.nexusteam.blacklogics.model.SourceCodeConfig;

public class SourceCodeHelper {
    
    public static CodeEditor createCodeEditor(Context context, SourceCodeData data, 
    SourceCodeConfig config) {
        CodeEditor codeEditor = new CodeEditor(context);
        

        codeEditor.setColorScheme(new EditorColorScheme());
        

        codeEditor.setEditable(data.isEditable());
        codeEditor.setTextSize(data.getTextSize());
        codeEditor.setTypefaceText(Typeface.MONOSPACE);
        codeEditor.setWordwrap(data.isWordWrap());
        

        String language = data.getLanguage();

        codeEditor.setEditorLanguage(new JavaLanguage());





        

        codeEditor.setText(data.getCode());
        

        Magnifier magnifier = codeEditor.getComponent(Magnifier.class);
        if (magnifier != null) {
            magnifier.setWithinEditorForcibly(config.isEnableMagnifier());
        }
        
        return codeEditor;
    }
    
    public static SourceCodeData createDefaultSourceCodeData(String code) {
        SourceCodeData data = new SourceCodeData();
        data.setCode(code);
        data.setTitle("Source Code");
        data.setLanguage(SourceCodeConfig.LANGUAGE_JAVA);
        data.setEditable(false);
        data.setWordWrap(false);
        data.setTextSize(12);
        return data;
    }
    
    public static SourceCodeData createSourceCodeData(String code, String language) {
        SourceCodeData data = new SourceCodeData(code);
        data.setLanguage(language);
        return data;
    }
    
    public static SourceCodeConfig getDefaultConfig() {
        SourceCodeConfig config = new SourceCodeConfig();
        config.setDefaultTextSize(12);
        config.setDefaultEditable(false);
        config.setDefaultWordWrap(false);
        config.setEnableMagnifier(true);
        return config;
    }
}
