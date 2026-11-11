package com.nexusteam.blacklogics.activityfile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.ly;
import com.nexusteam.internal.ma;

import java.util.ArrayList;

public class ActivityFileController {

    public static final int REQ_ADD_ACTIVITY = 264;
    public static final int REQ_ADD_CUSTOM_VIEW = 266;
    public static final int REQ_MANAGE_VIEW = 270;

    private static final String TAG = "ActivityFileController";

    private final String scId;
    private final Complex complex;
    private final Context appContext;
    private OnProjectFileChangeListener changeListener;

    public ActivityFileController(Context context, String scId, Complex complex) {
        this.appContext = context.getApplicationContext();
        this.scId = scId;
        this.complex = complex;
    }

    public ActivityFileController(String scId, Complex complex) {
        this.appContext = null;
        this.scId = scId;
        this.complex = complex;
    }

    public void setOnProjectFileChangeListener(OnProjectFileChangeListener l) {
        this.changeListener = l;
    }

    public OnProjectFileChangeListener getChangeListener() {
        return changeListener;
    }

    public ly files() {
        return ma.b(scId);
    }

    public void ensureProjectFileListBootstrapped() {
        ProjectFileBootstrap.onProjectCreated(scId);
    }

    public void reload() {
        try {
            ly ly = ma.b(scId, true);
            if (ly != null) {
                try {
                    ly.i();
                } catch (Throwable t) {
                    try { ly.d(); } catch (Throwable ignore) {}
                }
                Log.i(TAG, "reload activities=" + ly.a().size());
            }
        } catch (Throwable t) {
            Log.e(TAG, "reload", t);
        }
    }

    public void syncFromComplex() {
        if (complex == null) return;
        ensureProjectFileListBootstrapped();
        ly ly = files();
        if (ly == null) return;
        try {
            String joined = null;
            try {
                joined = complex.getAllJavaActivity();
            } catch (Throwable ignore) {}
            if (joined == null || joined.trim().isEmpty()) {
                try {
                    java.util.List names = complex.getAllJavaAndXmlNames();
                    if (names != null) {
                        StringBuilder sb = new StringBuilder();
                        for (Object o : names) {
                            String s = String.valueOf(o);
                            if (s.endsWith(".java")) {
                                if (sb.length() > 0) sb.append(',');
                                sb.append(s.substring(0, s.length() - 5));
                            }
                        }
                        joined = sb.toString();
                    }
                } catch (Throwable ignore) {}
            }
            if (joined == null || joined.trim().isEmpty()) return;

            for (String part : joined.split(",")) {
                if (part == null) continue;
                String act = part.trim();
                if (act.isEmpty()) continue;
                if (act.endsWith(".java")) act = act.substring(0, act.length() - 5);
                String layout = ActivitySyncBridge.toJavaName(act).equals(act)
                        ? ActivitySyncBridge.stripXml(act.replace("Activity", "").toLowerCase())
                        : ActivitySyncBridge.stripXml(
                                Character.toLowerCase(act.charAt(0))
                                        + (act.endsWith("Activity") && act.length() > 8
                                        ? act.substring(1, act.length() - 8)
                                        : act.substring(1)));
                layout = act.endsWith("Activity") && act.length() > 8
                        ? Character.toLowerCase(act.charAt(0)) + act.substring(1, act.length() - 8)
                        : act.toLowerCase();
                if ("mainactivity".equalsIgnoreCase(act) || "MainActivity".equals(act)) {
                    layout = "main";
                }
                boolean exists = false;
                for (ProjectFileBean p : ly.a()) {
                    if (p != null && layout.equals(p.fileName) && p.fileType == 0) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    ly.a(new ProjectFileBean(ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY, layout));
                }
            }
            ly.e();
        } catch (Throwable t) {
            Log.w(TAG, "syncFromComplex", t);
        }
    }

    public boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null) return false;
        if (requestCode != REQ_ADD_ACTIVITY && requestCode != REQ_ADD_CUSTOM_VIEW
                && requestCode != REQ_MANAGE_VIEW) {
            return false;
        }
        ProjectFileBean bean = null;
        try {
            bean = data.getParcelableExtra("project_file");
        } catch (Throwable ignore) {}
        if (bean == null) return false;

        Context ctx = appContext;
        ActivitySyncBridge.syncNewOrUpdatedActivity(ctx, scId, complex, bean);

        if (changeListener != null) {
            changeListener.onProjectFileSelected(0, bean);
            changeListener.onProjectFileSelected(1, bean);
        }
        return true;
    }

    public void openManageView(Activity host) {
        Intent i = new Intent(host, com.nexusteam.blacklogics.manage.view.ManageViewActivity.class);
        i.putExtra("sc_id", scId);
        i.putExtra("compatUseYn", "Y");
        host.startActivityForResult(i, REQ_MANAGE_VIEW);
    }

    public void openAddActivity(Activity host) {
        Intent i = new Intent(host, com.nexusteam.blacklogics.manage.view.AddViewActivity.class);
        i.putExtra("sc_id", scId);
        host.startActivityForResult(i, REQ_ADD_ACTIVITY);
    }

    public void openAddCustomView(Activity host) {
        Intent i = new Intent(host, com.nexusteam.blacklogics.manage.view.AddCustomViewActivity.class);
        i.putExtra("sc_id", scId);
        host.startActivityForResult(i, REQ_ADD_CUSTOM_VIEW);
    }

    public ArrayList<ProjectFileBean> getActivities() {
        try {
            return files() != null ? files().a() : new ArrayList<ProjectFileBean>();
        } catch (Throwable t) {
            return new ArrayList<>();
        }
    }
}
