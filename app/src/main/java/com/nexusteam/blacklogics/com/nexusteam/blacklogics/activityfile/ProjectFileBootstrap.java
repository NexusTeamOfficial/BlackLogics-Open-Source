package com.nexusteam.blacklogics.activityfile;

import android.content.Context;
import android.util.Log;

import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.ly;
import com.nexusteam.internal.ma;

public final class ProjectFileBootstrap {
    private static final String TAG = "ProjectFileBootstrap";

    private ProjectFileBootstrap() {}

    public static void onProjectCreated(Context context, String scId, Complex complex) {
        if (scId == null || scId.trim().isEmpty()) return;
        try {
            ly ly = ma.b(scId);
            if (ly != null) {
                boolean hasMain = false;
                for (Object o : ly.a()) {
                    if (o instanceof ProjectFileBean) {
                        ProjectFileBean p = (ProjectFileBean) o;
                        String n = p.fileName != null ? p.fileName : "";
                        if ("main".equalsIgnoreCase(n) || "main.xml".equalsIgnoreCase(n)) {
                            hasMain = true;
                            break;
                        }
                    }
                }
                if (!hasMain) {
                    ProjectFileBean main = new ProjectFileBean(0, "main");
                    try {
                        main.options = ProjectFileBean.OPTION_ACTIVITY_TOOLBAR;
                    } catch (Throwable ignore) {}
                    ly.a(main);
                }
                ly.e();
                try { ly.g(); } catch (Throwable ignore) {}
            }
            ActivitySyncBridge.syncProjectDefaults(context, scId, complex);
        } catch (Throwable t) {
            Log.e(TAG, "onProjectCreated", t);
        }
    }

    public static void onProjectCreated(String scId) {
        onProjectCreated(null, scId, null);
    }
}
