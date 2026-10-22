package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.util.Log;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class LayoutBackupManager {

	public interface RestoreCallback {
		void loadFromXmlString(String xml);
		void saveLayoutAsJson(String layoutName) throws Exception;
		void showMessage(String message);
	}

	private final Context context;
	private final String scId;
	private final String savePath;
	private final XmlGenerator xmlGenerator;
	private final RestoreCallback callback;

	public LayoutBackupManager(Context context, String scId, String savePath,
	XmlGenerator xmlGenerator, RestoreCallback callback) {
		this.context = context;
		this.scId = scId;
		this.savePath = savePath;
		this.xmlGenerator = xmlGenerator;
		this.callback = callback;
	}

	private File getBackupDir() {
		File preferred = context.getExternalFilesDir(null);
		if (preferred != null) {
			return new File(preferred, ".blacklogics/bak/" + scId);
		}
		return new File("/storage/emulated/0/.blacklogics/bak/" + scId);
	}

	public void saveBackup(String layoutName, String xml) {
		try {
			File dir = getBackupDir();
			if (!dir.exists()) dir.mkdirs();
			File backupFile = new File(dir, layoutName + ".xml");
			FileOutputStream fos = new FileOutputStream(backupFile);
			fos.write(xml.getBytes("UTF-8"));
			fos.close();
		} catch (Exception e) {
			Log.w("LayoutBackupManager", "saveBackup failed (non-critical): " + e.getMessage());
		}
	}

	public void checkAndRestoreBackup(final String layoutName) {
		if (layoutName == null || layoutName.trim().isEmpty()) return;
		final File backupFile = new File(getBackupDir(), layoutName + ".xml");
		if (!backupFile.exists()) return;
		File mainFile = new File(savePath);
		boolean isNewer = backupFile.lastModified() > mainFile.lastModified() + 2000L;
		if (!isNewer) { backupFile.delete(); return; }
		try {
			new MaterialAlertDialogBuilder(context,
			com.google.android.material.R.style
			.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
			.setTitle("Crash Recovery")
			.setMessage("App pichli baar crash hone se pehle \""
			+ layoutName + "\" ka backup mila hai.\n"
			+ "Kya aap is backup se restore karna chahte hain?")
			.setCancelable(false)
			.setPositiveButton("Restore Karo",
			new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(android.content.DialogInterface d, int w) {
					restoreFromBackup(layoutName, backupFile);
				}
			})
			.setNegativeButton("Ignore Karo",
			new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(android.content.DialogInterface d, int w) {
					backupFile.delete();
				}
			})
			.show();
		} catch (Exception e) {
			Log.e("LayoutBackupManager", "checkAndRestoreBackup dialog failed", e);
		}
	}

	private void restoreFromBackup(String layoutName, File backupFile) {
		try {
			byte[] bytes = new byte[(int) backupFile.length()];
			FileInputStream fis = new FileInputStream(backupFile);
			fis.read(bytes);
			fis.close();
			String xml = new String(bytes, "UTF-8");
			if (xml.trim().isEmpty() || !xmlGenerator.validateXML(xml)) {
				callback.showMessage("Backup corrupt hai, restore nahi hua.");
				backupFile.delete();
				return;
			}
			callback.loadFromXmlString(xml);
			try { callback.saveLayoutAsJson(layoutName); } catch (Exception ignored) {}
			backupFile.delete();
			callback.showMessage("Backup se restore ho gaya!");
		} catch (Exception e) {
			callback.showMessage("Restore failed: " + e.getMessage());
			Log.e("LayoutBackupManager", "restoreFromBackup failed", e);
		}
	}
}
