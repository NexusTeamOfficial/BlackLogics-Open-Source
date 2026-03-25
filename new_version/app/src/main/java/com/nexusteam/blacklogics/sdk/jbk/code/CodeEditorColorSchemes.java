package com.nexusteam.sdk.jbk.code;

import android.content.Context;
import android.content.res.AssetManager;
import com.nexusteam.sdk.jbk.util.LogUtil;
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.eclipse.tm4e.core.registry.IThemeSource;

public class CodeEditorColorSchemes {
    private static final String TAG = "CodeEditorColorSchemes";

    private static EditorColorScheme DRACULA;
    private static EditorColorScheme GITHUB;

    public static EditorColorScheme getDraculaScheme(Context context) {
        if (DRACULA == null) {
            loadGitHub(context);
        }
        return DRACULA;
    }

    public static EditorColorScheme getGitHubScheme(Context context) {
        if (GITHUB == null) {
            loadGitHub(context);
        }
        return GITHUB;
    }

    private static void loadGitHub(final Context context) {
        final AssetManager assets = context.getAssets();
        ThemeRegistry registry = ThemeRegistry.getInstance();

        try {
            IThemeSource source = new IThemeSource() {
                @Override
                public String getFilePath() {
                    return "textmate/themes/GitHub.tmTheme";
                }

                @Override
                public Reader getReader() {
                    try {
                        InputStream is = assets.open("textmate/themes/GitHub.tmTheme");
                        return new InputStreamReader(is);
                    } catch (Exception e) {
                        return null;
                    }
                }
            };

            registry.loadTheme(source);
            GITHUB = TextMateColorScheme.create(registry);

        } catch (Exception e) {
            LogUtil.e(TAG, "GitHub theme load failed", e);
            GITHUB = new EditorColorScheme();
        }
    }
}
