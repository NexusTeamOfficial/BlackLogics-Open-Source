package com.nexusteam.blacklogics.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;

import com.nexusteam.blacklogics.utils.BlackLogicsUtil;
import com.nexusteam.blacklogics.utils.FileUtil;
import com.nexusteam.blacklogics.activities.SrcCodeEditor;

public class AssetHelper {

    public static void openFile(Context context, String filePath, String fileName) {
        if (filePath.endsWith(".json") || filePath.endsWith(".txt")) {
            Intent launchIntent = new Intent();
            launchIntent.setClass(context, SrcCodeEditor.class);
            launchIntent.putExtra("title", fileName);
            launchIntent.putExtra("content", filePath);
            context.startActivity(launchIntent);
        } else {
            Intent viewIntent = new Intent(Intent.ACTION_VIEW);
            viewIntent.setDataAndType(Uri.fromFile(new File(filePath)), "*/*");
            viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(viewIntent);
        }
    }

    public static void showDeleteDialog(Context context, final String fileName, final boolean isFolder, 
                                        final Runnable onDelete) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(fileName)
                .setMessage("Are you sure you want to delete this " + 
                           (isFolder ? "folder" : "file") + "? " +
                           "This action cannot be reversed!")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDelete.run();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public static void createFile(String currentPath, String fileName) {
        FileUtil.writeFile(new File(currentPath, fileName).getAbsolutePath(), "");
        BlackLogicsUtil.toast("File was created successfully");
    }

    public static void createFolder(String currentPath, String folderName) {
        FileUtil.makeDir(new File(currentPath, folderName).getAbsolutePath());
        BlackLogicsUtil.toast("Folder was created successfully");
    }

    public static void renameFile(String oldPath, String newPath) {
        FileUtil.renameFile(oldPath, newPath);
        BlackLogicsUtil.toast("Renamed successfully");
    }
}