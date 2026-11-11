package com.nexusteam.blacklogics.manager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.bean.BackupConfig;
import com.nexusteam.blacklogics.bean.BackupResult;
import com.nexusteam.blacklogics.model.ProjectIdGenerator;
import com.nexusteam.blacklogics.utils.BackupDialogHelper;
import com.nexusteam.blacklogics.utils.BackupFactory;
import com.nexusteam.blacklogics.utils.BlackLogicsUtil;
import com.nexusteam.blacklogics.utils.SketchwareImporter;
import com.nexusteam.internal.ProjectModel;

import com.nexusteam.filepicker.elitefileselector.EliteFilePicker;
import com.nexusteam.filepicker.elitefileselector.FileItem;
import com.nexusteam.filepicker.elitefileselector.callback.FilePickerCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class BackupManager implements FilePickerCallback {

    public static final int REQUEST_RESTORE_BLB = 1001;
    public static final int REQUEST_IMPORT_SWB  = 1002;

    private static final int BUFFER = 4096;

    private final Activity activity;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final EliteFilePicker filePicker;

    private int pendingRequestCode = -1;

    public interface OnImportListener {
        void onImportSuccess(String newScId, String projectName);
        void onImportError(String reason);
    }

    private OnImportListener importListener;

    public BackupManager(Activity activity) {
        this.activity = activity;
        this.filePicker = EliteFilePicker.getInstance();
    }

    public void setOnImportListener(OnImportListener listener) {
        this.importListener = listener;
    }


    public void backup(String sc_id, String project_name) {
        BackupConfig config = new BackupConfig(sc_id, project_name);
        showBackupOptionsDialog(config);
    }

    private void showBackupOptionsDialog(final BackupConfig config) {
        android.view.View optionsView =
                BackupDialogHelper.createBackupOptionsView(activity, config);

        new MaterialAlertDialogBuilder(activity)
                .setIcon(R.drawable.ic_backup)
                .setTitle("Backup Options")
                .setView(optionsView)
                .setPositiveButton("Back up", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        executeBackup(config);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void executeBackup(final BackupConfig config) {
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Creating backup...");
        pd.setCancelable(false);
        pd.show();

        new Thread(new Runnable() {
            public void run() {
                final BackupFactory factory = new BackupFactory(config.getProjectId());
                factory.setBackupLocalLibs(config.isIncludeLocalLibraries());
                factory.setBackupCustomBlocks(config.isIncludeCustomBlocks());
                final BackupResult result = factory.backup(config.getProjectName());

                mainHandler.post(new Runnable() {
                    public void run() {
                        if (pd.isShowing()) pd.dismiss();
                        if (result.isSuccess()) {
                            BlackLogicsUtil.toast("Backup saved: "
                                    + result.getOutputFile().getAbsolutePath());
                        } else {
                            BlackLogicsUtil.toastError("Backup failed: "
                                    + result.getError(), Toast.LENGTH_LONG);
                        }
                    }
                });
            }
        }).start();
    }


    public void restore() {
        openFilePicker(REQUEST_RESTORE_BLB, BackupFactory.EXTENSION);
    }

    private void executeRestore(final File sourceFile, final boolean restoreLocalLibs) {
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Restoring...");
        pd.setCancelable(false);
        pd.show();

        new Thread(new Runnable() {
            public void run() {
                try {
                    File tempFile = copyFileToTemp(sourceFile, "restore_temp.blb");

                    String newScId = ProjectIdGenerator.getNewScId();
                    BackupFactory factory = new BackupFactory(newScId);
                    factory.setBackupLocalLibs(restoreLocalLibs);
                    final BackupResult result = factory.restore(tempFile);
                    tempFile.delete();

                    mainHandler.post(new Runnable() {
                        public void run() {
                            if (pd.isShowing()) pd.dismiss();
                            if (result.isSuccess()) {
                                BlackLogicsUtil.toast("Restored successfully");
                                if (importListener != null) {
                                    importListener.onImportSuccess(null, null);
                                }
                            } else {
                                BlackLogicsUtil.toastError("Restore failed: "
                                        + result.getError(), Toast.LENGTH_LONG);
                            }
                        }
                    });
                } catch (final Exception e) {
                    mainHandler.post(new Runnable() {
                        public void run() {
                            if (pd.isShowing()) pd.dismiss();
                            BlackLogicsUtil.toastError("Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG);
                        }
                    });
                }
            }
        }).start();
    }


    public void importFromSketchware() {
        new MaterialAlertDialogBuilder(activity)
                .setTitle("Import Project")
                .setMessage("Choose backup type to import:")
                .setPositiveButton("Sketchware (.swb)", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        openFilePicker(REQUEST_IMPORT_SWB, "swb");
                    }
                })
                .setNegativeButton("BlackLogics (.blb)", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        restore();
                    }
                })
                .setNeutralButton("Cancel", null)
                .show();
    }

    private void executeSketchwareImport(final File sourceFile) {
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Importing from Sketchware...");
        pd.setCancelable(false);
        pd.show();

        new Thread(new Runnable() {
            public void run() {
                try {
                    final File tempSwb = copyFileToTemp(sourceFile, "import_temp.swb");
                    final String newScId = ProjectIdGenerator.getNewScId();

                    final ProjectModel model =
                            SketchwareImporter.importSwbSync(activity, tempSwb, newScId);

                    tempSwb.delete();

                    mainHandler.post(new Runnable() {
                        public void run() {
                            if (pd.isShowing()) pd.dismiss();
                            String msg = "Imported: " + model.getAppName()
                                    + " (ID: " + newScId + ")";
                            BlackLogicsUtil.toast(msg);
                            if (importListener != null) {
                                importListener.onImportSuccess(newScId,
                                        model.getProjectName());
                            }
                        }
                    });

                } catch (final Exception e) {
                    mainHandler.post(new Runnable() {
                        public void run() {
                            if (pd.isShowing()) pd.dismiss();
                            BlackLogicsUtil.toastError(
                                    "Import failed: " + e.getMessage(),
                                    Toast.LENGTH_LONG);
                            if (importListener != null) {
                                importListener.onImportError(e.getMessage());
                            }
                        }
                    });
                }
            }
        }).start();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }


    private void handlePickedFile(int requestCode, final File picked) {
        if (picked == null || !picked.exists()) {
            BlackLogicsUtil.toastError("File not found", Toast.LENGTH_LONG);
            return;
        }

        if (requestCode == REQUEST_RESTORE_BLB) {
            final boolean hasLocalLibs = BackupFactory.zipContainsFile(
                    picked.getAbsolutePath(), "local_libs");

            if (hasLocalLibs) {
                new MaterialAlertDialogBuilder(activity)
                        .setTitle("Warning")
                        .setMessage("This backup contains local libraries. Copy them?")
                        .setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                executeRestore(picked, true);
                            }
                        })
                        .setNegativeButton("Don't copy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                executeRestore(picked, false);
                            }
                        })
                        .setNeutralButton("Cancel", null)
                        .show();
            } else {
                executeRestore(picked, false);
            }

        } else if (requestCode == REQUEST_IMPORT_SWB) {
            executeSketchwareImport(picked);
        }
    }


    private void openFilePicker(int requestCode, String extension) {
        pendingRequestCode = requestCode;

        filePicker.init(activity, this);

        new EliteFilePicker.Builder(activity)
                .setInitialPath(Environment.getExternalStorageDirectory().getAbsolutePath())
                .setSelectionMode("single")
                .setAllowedExtensions(extension)
                .setMaxSelectionCount(1)
                .setShowHiddenFiles(false)
                .launch();
    }


    @Override
    public void onSuccess(final List<FileItem> files) {
        if (files == null || files.isEmpty()) {
            mainHandler.post(new Runnable() {
                public void run() {
                    BlackLogicsUtil.toastError("No file selected", Toast.LENGTH_SHORT);
                }
            });
            return;
        }
        final File picked = new File(files.get(0).getPath());
        final int requestCode = pendingRequestCode;
        pendingRequestCode = -1;

        mainHandler.post(new Runnable() {
            public void run() {
                handlePickedFile(requestCode, picked);
            }
        });
    }

    @Override
    public void onError(final Exception error) {
        mainHandler.post(new Runnable() {
            public void run() {
                BlackLogicsUtil.toastError("File picker error: " + error.getMessage(),
                        Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void onCancelled() {
    }

    @Override
    public void onPermissionDenied(final String permission) {
        mainHandler.post(new Runnable() {
            public void run() {
                BlackLogicsUtil.toastError("Permission required: " + permission,
                        Toast.LENGTH_LONG);
            }
        });
    }


    private File copyFileToTemp(File source, String tempName) throws Exception {
        File tempFile = new File(activity.getCacheDir(), tempName);
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is  = new FileInputStream(source);
            fos = new FileOutputStream(tempFile);
            byte[] buf = new byte[BUFFER];
            int len;
            while ((len = is.read(buf)) > 0) {
                fos.write(buf, 0, len);
            }
        } finally {
            if (is  != null) try { is.close();  } catch (Exception ignored) {}
            if (fos != null) try { fos.close(); } catch (Exception ignored) {}
        }
        return tempFile;
    }
}