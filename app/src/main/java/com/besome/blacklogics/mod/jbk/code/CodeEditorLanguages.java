package mod.jbk.code;

import android.content.Context;
import android.content.res.AssetManager;

import com.besome.sketch.SketchApplication;

import org.eclipse.tm4e.core.registry.IGrammarSource;
import org.eclipse.tm4e.core.registry.IThemeSource;

import io.github.rosemoe.sora.lang.EmptyLanguage;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import mod.jbk.util.LogUtil;

public class CodeEditorLanguages {

    private static final String TAG = "CodeEditorLanguages";

    private static Language KOTLIN;
    private static Language XML;

    public static Language getKotlinLanguage(Context context) {
        if (KOTLIN == null) {
            initializeKotlinLanguage(context);
        }
        return KOTLIN;
    }

    public static Language getXmlLanguage(Context context) {
        if (XML == null) {
            initializeXmlLanguage(context);
        }
        return XML;
    }

    private static void initializeKotlinLanguage(Context context) {
        AssetManager assets = context.getAssets();

        try {
            KOTLIN = TextMateLanguage.create(
                    IGrammarSource.fromInputStream(
                            assets.open("textmate/kotlin.tmLanguage"),
                            "kotlin.tmLanguage",
                            null
                    ),
                    IThemeSource.fromInputStream(
                            assets.open("textmate/themes/dracula.json"),
                            "dracula.json",
                            null
                    )
            );
        } catch (Exception | NoSuchMethodError e) {
            LogUtil.e(TAG, "Failed to create Kotlin TextMate language, using empty one as default Kotlin language", e);
            KOTLIN = new EmptyLanguage();
        }
    }

    private static void initializeXmlLanguage(Context context) {
        AssetManager assets = context.getAssets();

        try {
            XML = TextMateLanguage.create(
                    IGrammarSource.fromInputStream(
                            assets.open("textmate/xml.tmLanguage.json"),
                            "xml.tmLanguage.json",
                            null
                    ),
                    IThemeSource.fromInputStream(
                            assets.open("textmate/themes/dracula.json"),
                            "dracula.json",
                            null
                    )
            );
        } catch (Exception | NoSuchMethodError e) {
            LogUtil.e(TAG, "Failed to create XML TextMate language, using empty one as default XML language", e);
            XML = new EmptyLanguage();
        }
    }
}