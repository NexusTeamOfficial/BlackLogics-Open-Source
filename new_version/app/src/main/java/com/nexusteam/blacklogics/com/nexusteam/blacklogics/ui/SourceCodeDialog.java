package com.nexusteam.blacklogics.ui;

import android.content.Context;
import android.content.DialogInterface;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.bean.SourceCodeData;
import com.nexusteam.blacklogics.model.SourceCodeConfig;
import com.nexusteam.blacklogics.utils.SourceCodeHelper;

import io.github.rosemoe.sora.widget.CodeEditor;

import static com.nexusteam.blacklogics.utils.BlackLogicsUtil.getDip;

public class SourceCodeDialog {

    public static void show(Context context, String code) {
        SourceCodeData data = SourceCodeHelper.createDefaultSourceCodeData(code);
        SourceCodeConfig config = SourceCodeHelper.getDefaultConfig();
        show(context, data, config);
    }

    public static void show(Context context, String code, String language) {
        SourceCodeData data = SourceCodeHelper.createSourceCodeData(code, language);
        SourceCodeConfig config = SourceCodeHelper.getDefaultConfig();
        show(context, data, config);
    }

    public static void show(Context context, SourceCodeData data) {
        SourceCodeConfig config = SourceCodeHelper.getDefaultConfig();
        show(context, data, config);
    }

    public static void show(Context context, SourceCodeData data, SourceCodeConfig config) {
        CodeEditor codeEditor = SourceCodeHelper.createCodeEditor(context, data, config);

        // Use MaterialAlertDialogBuilder for automatic dark/light support
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog);
        builder.setTitle(data.getTitle())
                .setIcon(R.drawable.code_icon)
                .setPositiveButton(R.string.common_word_close, null)
                .setView(codeEditor,
                        (int) getDip(context, 24),
                        (int) getDip(context, 8),
                        (int) getDip(context, 24),
                        (int) getDip(context, 8));

        builder.show();
    }

    public static void showWithTitle(Context context, String code, String title) {
        SourceCodeData data = SourceCodeHelper.createDefaultSourceCodeData(code);
        data.setTitle(title);
        SourceCodeConfig config = SourceCodeHelper.getDefaultConfig();
        show(context, data, config);
    }

    public static void showEditable(Context context, String code,
                                    final EditableCallback callback) {
        SourceCodeData data = SourceCodeHelper.createDefaultSourceCodeData(code);
        data.setEditable(true);
        data.setTitle("Edit Code");
        SourceCodeConfig config = SourceCodeHelper.getDefaultConfig();
        showEditableDialog(context, data, config, callback);
    }

    private static void showEditableDialog(final Context context, final SourceCodeData data,
                                           final SourceCodeConfig config,
                                           final EditableCallback callback) {
        final CodeEditor codeEditor = SourceCodeHelper.createCodeEditor(context, data, config);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog);
        builder.setTitle(data.getTitle())
                .setIcon(R.drawable.code_icon)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {
                        if (callback != null) {
                            callback.onSave(codeEditor.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .setView(codeEditor,
                        (int) getDip(context, 24),
                        (int) getDip(context, 8),
                        (int) getDip(context, 24),
                        (int) getDip(context, 8));

        builder.show();
    }

    public interface EditableCallback {
        void onSave(String modifiedCode);
    }
}