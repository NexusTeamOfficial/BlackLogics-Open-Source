package com.nexusteam.blacklogics.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.text.style.ForegroundColorSpan;
import android.graphics.Typeface;
import android.graphics.Color;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class CrashLogUpdateNotice {

    private static final String PREF_NAME = "crashlog_notice_pref";
    private static final String KEY_NOTICE_SHOWN = "notice_shown";

    public static void showIfNeeded(final Context context) {

        final SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        if (prefs.getBoolean(KEY_NOTICE_SHOWN, false)) {
            return;
        }

        String text =
                "CrashLog v0.0.7 - alpha7 (16584)\n\n" +

                "⚠ WARNING:\n" +
                "Some latest Android devices may block the installation of the BlackLogics app and show a virus detection warning.\n" +
                "This is caused by Android security policies or false detection and is NOT an issue with the app itself.\n\n" +

                "AdMob support has been added in this version to help support " +
                "development and maintenance of the project.\n\n" +
                "This project remains open-source and your support helps us " +
                "continue improving it.\n\n" +
                "If you encounter any issue you can report it to the developer.";

        SpannableString message = new SpannableString(text);

        message.setSpan(
                new StyleSpan(Typeface.BOLD),
                0,
                28,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        message.setSpan(
                new UnderlineSpan(),
                0,
                28,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        int warningStart = text.indexOf("⚠ WARNING:");
        int warningEnd = warningStart + 120;

        message.setSpan(
                new ForegroundColorSpan(Color.RED),
                warningStart,
                warningEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        message.setSpan(
                new StyleSpan(Typeface.BOLD),
                warningStart,
                warningEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        new MaterialAlertDialogBuilder(context)
                .setTitle("CrashLog Notice")
                .setMessage(message)

                .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        prefs.edit()
                                .putBoolean(KEY_NOTICE_SHOWN, true)
                                .apply();

                        dialog.dismiss();
                    }
                })

                .setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .setNeutralButton("Report Developer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:supportnexusteam@gmail.com"));
                        intent.putExtra(Intent.EXTRA_SUBJECT,
                                "CrashLog Feedback / Issue Report");

                        context.startActivity(intent);
                    }
                })

                .setCancelable(false)
                .show();
    }
}