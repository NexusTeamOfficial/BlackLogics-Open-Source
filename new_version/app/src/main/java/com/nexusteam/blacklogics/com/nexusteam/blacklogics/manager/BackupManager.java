
package com.nexusteam.blacklogics.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.bean.BackupConfig;
import com.nexusteam.blacklogics.bean.BackupResult;
import com.nexusteam.blacklogics.model.ProjectIdGenerator;
import com.nexusteam.blacklogics.utils.BackupDialogHelper;
import com.nexusteam.blacklogics.utils.BackupFactory;

import java.io.File;
import java.lang.ref.WeakReference;

import com.nexusteam.blacklogics.utils.BlackLogicsUtil;
import com.nexusteam.blacklogics.utils.Helper;

public class BackupManager {
    
    private final Activity activity;
    
    public BackupManager(Activity activity) {
        this.activity = activity;
    }
    
    public void backup(String sc_id, String project_name) {
        BackupConfig config = new BackupConfig(sc_id, project_name);
        showBackupOptionsDialog(config);
    }
    
    private void showBackupOptionsDialog(final BackupConfig config) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(activity)
                .setIcon(R.drawable.ic_backup)
                .setTitle("Backup Options");
        
        View optionsView = BackupDialogHelper.createBackupOptionsView(activity, config);
        
        dialogBuilder.setView(optionsView)
                .setPositiveButton("Back up", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        executeBackup(config);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        
        dialogBuilder.show();
    }
    
    private void executeBackup(BackupConfig config) {
        new BackupAsyncTask(new WeakReference<Activity>(activity), config).execute();
    }
    
    public void restore() {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = 0;
        properties.selection_type = 0;
        properties.root = new File("/storage/emulated/0/");
        properties.error_dir = new File("/storage/emulated/0/");
        properties.offset = new File(BackupFactory.getBackupDir());
        properties.extensions = new String[]{BackupFactory.EXTENSION};
        
        FilePickerDialog fpd = new FilePickerDialog(activity, properties);
        fpd.setTitle("Select a backup file (" + BackupFactory.EXTENSION + ")");
        fpd.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                final String file = files[0];
                final boolean local_libs = BackupFactory.zipContainsFile(file, "local_libs");
                
                if (local_libs) {
                    new MaterialAlertDialogBuilder(activity)
                            .setTitle("Warning")
                            .setMessage("Looks like the backup file you selected contains some local libraries. Do you want to copy them to your local_libs directory (if they do not already exist)?")
                            .setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    executeRestore(file, true);
                                }
                            })
                            .setNegativeButton("Don't copy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    executeRestore(file, false);
                                }
                            })
                            .setNeutralButton("CANCEL", null)
                            .show();
                } else {
                    executeRestore(file, false);
                }
            }
        });
        
        fpd.show();
    }
    
    private void executeRestore(String file, boolean restoreLocalLibs) {
        BackupConfig config = new BackupConfig();
        config.setRestoreLocalLibs(restoreLocalLibs);
        new RestoreAsyncTask(new WeakReference<Activity>(activity), file, config).execute();
    }
    
    private static class BackupAsyncTask extends AsyncTask<Void, Void, BackupResult> {
        
        private final WeakReference<Activity> activityRef;
        private final BackupConfig config;
        private ProgressDialog progressDialog;
        private BackupFactory backupFactory;
        
        BackupAsyncTask(WeakReference<Activity> activityRef, BackupConfig config) {
            this.activityRef = activityRef;
            this.config = config;
        }
        
        @Override
        protected void onPreExecute() {
            Activity activity = activityRef.get();
            if (activity != null) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Creating backup...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        }
        
        @Override
        protected BackupResult doInBackground(Void... voids) {
            backupFactory = new BackupFactory(config.getProjectId());
            backupFactory.setBackupLocalLibs(config.isIncludeLocalLibraries());
            backupFactory.setBackupCustomBlocks(config.isIncludeCustomBlocks());
            
            return backupFactory.backup(config.getProjectName());
        }
        
        @Override
        protected void onPostExecute(BackupResult result) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            
            Activity activity = activityRef.get();
            if (activity != null) {
                if (result.isSuccess()) {
                    BlackLogicsUtil.toast("Successfully created backup to: " + 
                            result.getOutputFile().getAbsolutePath());
                } else {
                    BlackLogicsUtil.toastError("Error: " + result.getError(), Toast.LENGTH_LONG);
                }
            }
        }
    }
    
    private static class RestoreAsyncTask extends AsyncTask<Void, Void, BackupResult> {
        
        private final WeakReference<Activity> activityRef;
        private final String filePath;
        private final BackupConfig config;
        private ProgressDialog progressDialog;
        private BackupFactory backupFactory;
        
        RestoreAsyncTask(WeakReference<Activity> activityRef, String filePath, BackupConfig config) {
            this.activityRef = activityRef;
            this.filePath = filePath;
            this.config = config;
        }
        
        @Override
        protected void onPreExecute() {
            Activity activity = activityRef.get();
            if (activity != null) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Restoring...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        }
        
        @Override
        protected BackupResult doInBackground(Void... voids) {
            String newScId = ProjectIdGenerator.getNewScId();
            backupFactory = new BackupFactory(newScId);
            backupFactory.setBackupLocalLibs(config.isRestoreLocalLibs());
            
            try {
                return backupFactory.restore(new File(filePath));
            } catch (Exception e) {
                BackupResult result = new BackupResult();
                result.setSuccess(false);
                result.setError(e.getMessage());
                return result;
            }
        }
        
        @Override
        protected void onPostExecute(BackupResult result) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            
            Activity activity = activityRef.get();
            if (activity != null) {
                if (result.isSuccess()) {
                    BlackLogicsUtil.toast("Restored successfully");
                } else {
                    BlackLogicsUtil.toastError("Couldn't restore: " + result.getError(), Toast.LENGTH_LONG);
                }
            }
        }
    }
}