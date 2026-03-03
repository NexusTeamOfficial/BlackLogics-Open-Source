package com.nexusteam.blacklogics.lib.language;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.nexusteam.blacklogics.utils.LocaleHelper;

public class LanguageManager {

    private static final String PREF_NAME = "LANG";
    private static final String KEY_LANG = "lang";

    // 🔹 Save language (en / hi)
    public static void setLanguage(Context context, String lang) {
        SharedPreferences sp =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_LANG, lang).apply();
    }

    // 🔹 Get saved language
    public static String getLanguage(Context context) {
        SharedPreferences sp =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(KEY_LANG, "en"); // default English
    }

    // 🔹 Apply locale (used by ApplicationLoader)
    public static Context apply(Context context) {
        String lang = getLanguage(context);
        return LocaleHelper.setLocale(context, lang);
    }

    // 🔹 Change language + restart app safely
    public static void changeLanguage(Context context, String lang) {
        setLanguage(context, lang);

        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());

        if (intent != null) {
            intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NEW_TASK
            );
            context.startActivity(intent);
        }

        if (context instanceof android.app.Activity) {
            ((android.app.Activity) context).finish();
        }
    }
}