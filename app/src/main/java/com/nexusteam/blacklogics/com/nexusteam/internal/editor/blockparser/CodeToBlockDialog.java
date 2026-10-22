package com.nexusteam.internal.editor.blockparser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.editor.blockparser.result.BlockConversionResult;

import java.util.List;

public class CodeToBlockDialog {

    public interface Callback {
        void onBlocksReady(List blockBeans);
    }

    public static void show(final Activity activity, final Callback callback) {
        if (activity == null) {
            return;
        }
        CodeToBlockManager.init(activity);

        LinearLayout root = new LinearLayout(activity);
        root.setOrientation(LinearLayout.VERTICAL);
        int pad = dp(activity, 16);
        root.setPadding(pad, pad, pad, pad);

        TextView titleHint = new TextView(activity);
        titleHint.setText("Paste Java code below.\nMatched code → visual blocks\nUnmatched → Add Source Directly");
        titleHint.setTextSize(13);
        titleHint.setPadding(0, 0, 0, dp(activity, 8));
        root.addView(titleHint);

        final EditText input = new EditText(activity);
        input.setHint("Paste Java code here...");
        input.setMinLines(8);
        input.setMaxLines(18);
        input.setGravity(android.view.Gravity.TOP | android.view.Gravity.START);
        input.setTypeface(Typeface.MONOSPACE);
        input.setTextSize(12);
        input.setBackgroundColor(0xFF263238);
        input.setTextColor(0xFFEEFFFF);
        input.setHintTextColor(0xFF90A4AE);
        input.setPadding(dp(activity, 12), dp(activity, 12), dp(activity, 12), dp(activity, 12));

        ScrollView scroll = new ScrollView(activity);
        scroll.addView(input);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(activity, 220));
        root.addView(scroll, lp);

        TextView info = new TextView(activity);
        info.setText("Patterns: " + CodeToBlockManager.getRegisteredCount() + " | kq opcodes: " + CodeToBlockManager.getKqOpcodeCount());
        info.setTextSize(11);
        info.setTextColor(0xFF78909C);
        info.setPadding(0, dp(activity, 8), 0, 0);
        root.addView(info);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Code to Block");
        builder.setView(root);
        builder.setPositiveButton("Convert", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                final String code = input.getText() == null ? "" : input.getText().toString();
                if (code.trim().length() == 0) {
                    Toast.makeText(activity, "Please paste some Java code", Toast.LENGTH_SHORT).show();
                    return;
                }
                convertAndDeliver(activity, code, callback);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setNeutralButton("Source Only", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String code = input.getText() == null ? "" : input.getText().toString();
                if (code.trim().length() == 0) {
                    Toast.makeText(activity, "No code", Toast.LENGTH_SHORT).show();
                    return;
                }
                List list = new java.util.ArrayList();
                list.add(CodeToBlockManager.createSourceBlock(code));
                if (callback != null) {
                    callback.onBlocksReady(list);
                }
            }
        });
        builder.show();
    }

    private static void convertAndDeliver(final Activity activity, final String code, final Callback callback) {
        new Thread(new Runnable() {
            public void run() {
                final BlockConversionResult result = CodeToBlockManager.convert(code);
                final List beans = CodeToBlockManager.convertToBlockBeans(code);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        if (beans == null || beans.size() == 0) {
                            Toast.makeText(activity,
                                    "Could not convert code: " + result.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            return;
                        }

                        String msg = "Converted: " + result.getMatchedBlocks()
                                + " matched, " + result.getSourceBlocks() + " source blocks";
                        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();

                        if (callback != null) {
                            callback.onBlocksReady(beans);
                        }
                    }
                });
            }
        }).start();
    }

    private static int dp(Activity activity, int value) {
        float density = activity.getResources().getDisplayMetrics().density;
        return (int) (value * density + 0.5f);
    }
}
