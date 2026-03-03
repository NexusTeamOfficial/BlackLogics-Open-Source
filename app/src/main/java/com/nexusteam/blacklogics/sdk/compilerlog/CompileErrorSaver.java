package com.nexusteam.sdk.compilerlog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import com.besome.blacklogics.tools.CompileLogActivity;
import com.nexusteam.sdk.lib.FilePathUtil;
import com.nexusteam.sdk.lib.FileUtil;

public class CompileErrorSaver {
    private static final String MESSAGE_NO_COMPILE_ERRORS_SAVED =
        "No compile errors have been saved yet.";

    public String sc_id;
    public FilePathUtil filePathUtil = new FilePathUtil();
    public String path;

    public CompileErrorSaver(String sc_id) {
        this.sc_id = sc_id;
        path = FilePathUtil.getLastCompileLogPath(sc_id);
    }

    public void writeLogsToFile(String errorText) {
        if (logFileExists())
            FileUtil.deleteFile(path);
        FileUtil.writeFile(path, errorText);
    }

    public void showLastErrors(Context context) {
        Intent intent = new Intent(context, CompileLogActivity.class);
        intent.putExtra("error", getLogsFromFile());
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("showingLastError", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public void deleteSavedLogs() {
        FileUtil.deleteFile(path);
    }

    public String getLogsFromFile() {
        if (!logFileExists())
            return MESSAGE_NO_COMPILE_ERRORS_SAVED;
        return FileUtil.readFile(path);
    }

    public boolean logFileExists() {
        return FileUtil.isExistFile(path);
    }
}
