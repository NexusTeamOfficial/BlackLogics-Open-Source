package com.nexusteam.sdk.jbk.code;

import android.content.Context;
import android.content.res.AssetManager;
import com.nexusteam.sdk.jbk.util.LogUtil;
import io.github.rosemoe.sora.lang.EmptyLanguage;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.eclipse.tm4e.core.registry.IGrammarSource;
import org.eclipse.tm4e.core.registry.IThemeSource;

public class CodeEditorLanguages {
    private static final String TAG = "CodeEditorLanguages";

    private static Language KOTLIN;
    private static Language XML;

    public static Language getKotlinLanguage(Context context) {
        if (KOTLIN == null) {
            getXmlLanguage(context);
        }
        return KOTLIN;
    }

    public static Language getXmlLanguage(Context context) {
        if (XML == null) {
            initializeXmlLanguage(context);
        }
        return XML;
    }

    private static void initializeXmlLanguage(final Context context) {
        final AssetManager assets = context.getAssets();

        try {
            IGrammarSource grammarSource = new IGrammarSource() {
                @Override
                public String getFilePath() {
                    return "textmate/xml.tmLanguage.json";
                }

                @Override
                public Reader getReader() {
                    try {
                        InputStream is = assets.open("textmate/xml.tmLanguage.json");
                        return new InputStreamReader(is);
                    } catch (Exception e) {
                        return null;
                    }
                }
            };

            IThemeSource themeSource = new IThemeSource() {
                @Override
                public String getFilePath() {
                    return "textmate/themes/dracula.json";
                }

                @Override
                public Reader getReader() {
                    try {
                        InputStream is = assets.open("textmate/themes/dracula.json");
                        return new InputStreamReader(is);
                    } catch (Exception e) {
                        return null;
                    }
                }
            };

            XML = TextMateLanguage.create(grammarSource, themeSource);

        } catch (Exception e) {
            LogUtil.e(TAG, "Failed to create XML TextMate language, using empty one", e);
            XML = new EmptyLanguage();
        }
    }
}
