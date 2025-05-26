package mod.jbk.code;

import android.content.Context;
import android.content.res.AssetManager;

import com.besome.sketch.SketchApplication;

import org.eclipse.tm4e.core.registry.IThemeSource;

import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import mod.jbk.util.LogUtil;

public class CodeEditorColorSchemes {

    private static final String TAG = "CodeEditorColorSchemes";

    private static EditorColorScheme DRACULA;
    private static EditorColorScheme GITHUB;

    public static EditorColorScheme getDraculaScheme(Context context) {
        if (DRACULA == null) {
            initializeDraculaScheme(context);
        }
        return DRACULA;
    }

    public static EditorColorScheme getGitHubScheme(Context context) {
        if (GITHUB == null) {
            initializeGitHubScheme(context);
        }
        return GITHUB;
    }

    private static void initializeDraculaScheme(Context context) {
        AssetManager assets = context.getAssets();
        ThemeRegistry themeRegistry = ThemeRegistry.getInstance();

        try {
            IThemeSource draculaTheme = IThemeSource.fromInputStream(
                    assets.open("textmate/themes/dracula.json"),
                    "dracula.json",
                    null
            );
            themeRegistry.loadTheme(draculaTheme);
            DRACULA = TextMateColorScheme.create(themeRegistry);
        } catch (Exception e) {
            LogUtil.e(TAG, "Failed to load Dracula theme, falling back to default scheme", e);
            DRACULA = new EditorColorScheme();
        }
    }

    private static void initializeGitHubScheme(Context context) {
        AssetManager assets = context.getAssets();
        ThemeRegistry themeRegistry = ThemeRegistry.getInstance();

        try {
            IThemeSource gitHubTheme = IThemeSource.fromInputStream(
                    assets.open("textmate/themes/GitHub.tmTheme"),
                    "GitHub.tmTheme",
                    null
            );
            themeRegistry.loadTheme(gitHubTheme);
            GITHUB = TextMateColorScheme.create(themeRegistry);
        } catch (Exception e) {
            LogUtil.e(TAG, "Failed to load GitHub theme, falling back to default scheme", e);
            GITHUB = new EditorColorScheme();
        }
    }
}