package com.nexusteam.blacklogics.ui;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.bean.SourceCodeData;
import com.nexusteam.blacklogics.model.SourceCodeConfig;
import com.nexusteam.blacklogics.utils.SourceCodeHelper;

import io.github.rosemoe.sora.widget.CodeEditor;

import static com.nexusteam.blacklogics.utils.BlackLogicsUtil.getDip;

public class SourceCodeDialog {
	
	private static MaterialAlertDialogBuilder createDialogBuilder(Context context) {
		return new MaterialAlertDialogBuilder(
		context,
		com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog);
	}
	
	public static void show(@NonNull Context context, String code) {
		SourceCodeData data = SourceCodeHelper.createDefaultSourceCodeData(code);
		SourceCodeConfig config = SourceCodeHelper.getDefaultConfig();
		show(context, data, config);
	}
	
	public static void show(@NonNull Context context, String code, String language) {
		SourceCodeData data = SourceCodeHelper.createSourceCodeData(code, language);
		SourceCodeConfig config = SourceCodeHelper.getDefaultConfig();
		show(context, data, config);
	}
	
	public static void show(@NonNull Context context, SourceCodeData data) {
		SourceCodeConfig config = SourceCodeHelper.getDefaultConfig();
		show(context, data, config);
	}
	
	public static void show(@NonNull Context context, SourceCodeData data,
	@NonNull SourceCodeConfig config) {
		CodeEditor codeEditor = SourceCodeHelper.createCodeEditor(context, data, config);
		
		MaterialAlertDialogBuilder builder = createDialogBuilder(context);
		
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
	
	public static void showWithTitle(@NonNull Context context, String code, String title) {
		SourceCodeData data = SourceCodeHelper.createDefaultSourceCodeData(code);
		data.setTitle(title);
		SourceCodeConfig config = SourceCodeHelper.getDefaultConfig();
		show(context, data, config);
	}
	
	public static void showEditable(@NonNull Context context, String code,
	final EditableCallback callback) {
		SourceCodeData data = SourceCodeHelper.createDefaultSourceCodeData(code);
		data.setEditable(true);
		data.setTitle("Edit Code");
		SourceCodeConfig config = SourceCodeHelper.getDefaultConfig();
		showEditableDialog(context, data, config, callback);
	}
	
	private static void showEditableDialog(@NonNull final Context context,
	@NonNull final SourceCodeData data,
	@NonNull final SourceCodeConfig config,
	final EditableCallback callback) {
		final CodeEditor codeEditor = SourceCodeHelper.createCodeEditor(context, data, config);
		
		MaterialAlertDialogBuilder builder = createDialogBuilder(context);
		
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
