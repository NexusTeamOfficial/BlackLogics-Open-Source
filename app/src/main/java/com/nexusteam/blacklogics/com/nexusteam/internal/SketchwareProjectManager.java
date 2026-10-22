package com.nexusteam.internal;

import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SketchwareProjectManager {

    private static final String SKETCHWARE_PATH =
            "/storage/emulated/0/.sketchware/mysc/list/";


    public interface Callback {
        void onLoaded(List<ProjectModel> projects);
        void onError(Exception e);
    }


    private static final Handler mainHandler = new Handler(Looper.getMainLooper());


    public static void loadAll(final Callback callback) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    final List<ProjectModel> result = loadAllSync();
                    mainHandler.post(new Runnable() {
                        public void run() {
                            callback.onLoaded(result);
                        }
                    });
                } catch (final Exception e) {
                    mainHandler.post(new Runnable() {
                        public void run() {
                            callback.onError(e);
                        }
                    });
                }
            }
        }).start();
    }

    public static void loadById(final String scId, final Callback callback) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    final ProjectModel model = loadByIdSync(scId);
                    final List<ProjectModel> result = new ArrayList<ProjectModel>();
                    if (model != null) result.add(model);
                    mainHandler.post(new Runnable() {
                        public void run() {
                            callback.onLoaded(result);
                        }
                    });
                } catch (final Exception e) {
                    mainHandler.post(new Runnable() {
                        public void run() {
                            callback.onError(e);
                        }
                    });
                }
            }
        }).start();
    }


    public static List<ProjectModel> loadAllSync() {
        List<ProjectModel> list = new ArrayList<ProjectModel>();
        File root = new File(SKETCHWARE_PATH);

        if (!root.exists() || !root.isDirectory()) return list;

        File[] projectDirs = root.listFiles();
        if (projectDirs == null) return list;

        for (int i = 0; i < projectDirs.length; i++) {
            File dir = projectDirs[i];
            File projectFile = new File(dir, "project");
            if (!projectFile.exists()) continue;
            try {
                ProjectModel model = ProjectFileParser.parse(projectFile.getAbsolutePath());
                if (model != null) {
                    list.add(model);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public static ProjectModel loadByIdSync(String scId) throws Exception {
        String path = SKETCHWARE_PATH + scId + "/project";
        return ProjectFileParser.parse(path);
    }
}
