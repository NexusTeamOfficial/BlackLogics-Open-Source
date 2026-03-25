package com.nexusteam.blacklogics.utils;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.nexusteam.blacklogics.bean.EditorTheme;
import com.nexusteam.blacklogics.utils.BlackLogicsUtil;
import com.nexusteam.blacklogics.editor.CodeEditorColorSchemes;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;
import io.github.rosemoe.sora.widget.schemes.SchemeEclipse;
import io.github.rosemoe.sora.widget.schemes.SchemeGitHub;
import io.github.rosemoe.sora.widget.schemes.SchemeNotepadXX;
import io.github.rosemoe.sora.widget.schemes.SchemeVS2019;

public class CodeEditorUtils {

    public static void saveEditorSettings(Context context, CodeEditor editor, String prefix) {
        SharedPreferences pref = context.getSharedPreferences("hsce", Context.MODE_PRIVATE);
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        
        pref.edit()
            .putInt(prefix + "_ts", (int) (editor.getTextSizePx() / scaledDensity))
            .apply();
    }

    public static void loadEditorSettings(Context context, CodeEditor editor, String prefix) {
        SharedPreferences pref = context.getSharedPreferences("hsce", Context.MODE_PRIVATE);
        
        int textSize = pref.getInt(prefix + "_ts", 12);
        int theme = pref.getInt(prefix + "_theme", 3);
        boolean wordWrap = pref.getBoolean(prefix + "_ww", false);
        boolean autoComplete = pref.getBoolean(prefix + "_ac", true);
        boolean symbolPairAutoComplete = pref.getBoolean(prefix + "_acsp", true);
        
        applyTheme(editor, theme, context);
        editor.setTextSize(textSize);
        editor.setWordwrap(wordWrap);
        editor.getProps().symbolPairAutoCompletion = symbolPairAutoComplete;
        editor.getComponent(io.github.rosemoe.sora.widget.component.EditorAutoCompletion.class)
              .setEnabled(autoComplete);
    }

    public static void applyTheme(CodeEditor editor, int themeId, Context context) {
        EditorColorScheme scheme = getThemeScheme(editor, themeId, context);
        editor.setColorScheme(scheme);
    }

    private static EditorColorScheme getThemeScheme(CodeEditor editor, int themeId, Context context) {
    CodeEditorColorSchemes schemes = CodeEditorColorSchemes.getInstance(context);

    if (editor.getColorScheme() instanceof io.github.rosemoe.sora.langs.textmate.TextMateColorScheme) {
        switch (themeId) {
            case 1:
                return schemes.getGitHubScheme();
            case 3:
            default:
                return schemes.getDraculaScheme();
        }
    } else {
        switch (themeId) {
            default:
            case 0:
                return new EditorColorScheme();
            case 1:
                return new SchemeGitHub();
            case 2:
                return new SchemeEclipse();
            case 3:
                return new SchemeDarcula();
            case 4:
                return new SchemeVS2019();
            case 5:
                return new SchemeNotepadXX();
        }
    }
}


    public static String prettifyXml(String xml, int indentAmount, Intent extras) {
        try {
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))));
            

            document.normalize();
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", 
                                        String.valueOf(indentAmount));
            
            if (extras != null && extras.hasExtra("disableHeader")) {
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            }
            
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
            return stringWriter.toString();
            
        } catch (Exception e) {
            BlackLogicsUtil.toastError("Failed to format XML: " + e.getMessage());
            return null;
        }
    }

    public static String getClipboardText(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        
        if (clipboard != null && clipboard.hasPrimaryClip()) {
            ClipDescription desc = clipboard.getPrimaryClipDescription();
            ClipData data = clipboard.getPrimaryClip();
            
            if (data != null && desc != null && desc.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                CharSequence text = data.getItemAt(0).getText();
                return text != null ? text.toString() : "";
            }
        }
        return "";
    }

    public static List<EditorTheme> getAvailableThemes() {
        List<EditorTheme> themes = new ArrayList<>();
        themes.add(new EditorTheme("Default", 0, 0));
        themes.add(new EditorTheme("GitHub", 1, 0));
        themes.add(new EditorTheme("Eclipse", 2, 0));
        themes.add(new EditorTheme("Dracula", 3, 0));
        themes.add(new EditorTheme("VS2019", 4, 0));
        themes.add(new EditorTheme("NotepadXX", 5, 0));
        return themes;
    }
}