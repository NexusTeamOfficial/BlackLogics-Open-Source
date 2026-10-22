package com.nexusteam.internal;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.nexusteam.internal.beans.ProjectResourceBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResourceManager {

    private static ResourceManager instance;
    private ExecutorService threadPool = Executors.newFixedThreadPool(3);
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    private static final String BASE_PATH = "/storage/emulated/0/.blacklogics/resources/";

    public interface SaveListener {
        void onSuccess(int count);
        void onFailure(String error);
    }

    public interface LoadListener {
        void onSuccess(ArrayList<ProjectResourceBean> resources);
        void onFailure(String error);
    }

    public interface ProgressListener {
        void onProgress(int current, int total, String fileName);
    }

    private ResourceManager() {}

    public static synchronized ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }


    public void saveImages(String projectId, SaveListener listener) {
        String imagePath = BASE_PATH + "images/" + projectId + "/";
        saveResourcesFromPath(imagePath, projectId, listener);
    }

    public void saveImagesWithProgress(String projectId, ProgressListener progress, SaveListener listener) {
        String imagePath = BASE_PATH + "images/" + projectId + "/";
        saveResourcesFromPathWithProgress(imagePath, projectId, progress, listener);
    }

    public void loadImages(String projectId, LoadListener listener) {
        threadPool.execute(new LoadRunnable(projectId, listener));
    }

    private void saveResourcesFromPath(String path, String projectId, SaveListener listener) {
        new SaveTask(path, projectId, listener).executeOnExecutor(threadPool);
    }

    private void saveResourcesFromPathWithProgress(String path, String projectId,
                                                    ProgressListener progress, SaveListener listener) {
        new SaveTask(path, projectId, listener, progress).executeOnExecutor(threadPool);
    }


    public void registerSingleImage(String projectId, String resName, String fullFileName, SaveListener listener) {
        threadPool.execute(new RegisterSingleRunnable(projectId, resName, fullFileName, listener));
    }

    private class RegisterSingleRunnable implements Runnable {
        private String projectId;
        private String resName;
        private String fullFileName;
        private SaveListener callback;

        RegisterSingleRunnable(String projectId, String resName, String fullFileName, SaveListener callback) {
            this.projectId = projectId;
            this.resName = resName;
            this.fullFileName = fullFileName;
            this.callback = callback;
        }

        @Override
        public void run() {
            try {
                mb manager = ma.c(projectId);
                if (manager == null) {
                    postFailure("Manager is null");
                    return;
                }

                if (manager.f373a != null) {
                    for (ProjectResourceBean existing : manager.f373a) {
                        if (existing.resFullName.equals(fullFileName) || existing.resName.equals(resName)) {
                            postSuccess(0);
                            return;
                        }
                    }
                }

                ProjectResourceBean bean = new ProjectResourceBean(
                        ProjectResourceBean.PROJECT_RES_TYPE_FILE, resName, fullFileName);

                manager.f373a.add(bean);
                manager.h();

                postSuccess(1);

            } catch (Exception e) {
                postFailure(e.getMessage());
            }
        }

        private void postSuccess(final int count) {
            if (callback == null) return;
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onSuccess(count);
                }
            });
        }

        private void postFailure(final String error) {
            if (callback == null) return;
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onFailure(error);
                }
            });
        }
    }

    private class SaveTask extends AsyncTask<Void, Object, SaveTask.Result> {
        private String folderPath;
        private String projectId;
        private SaveListener callback;
        private ProgressListener progressCallback;
        private boolean cancelled;

        class Result {
            boolean success;
            int count;
            String message;

            Result(boolean success, int count, String message) {
                this.success = success;
                this.count = count;
                this.message = message;
            }
        }

        SaveTask(String folderPath, String projectId, SaveListener callback) {
            this(folderPath, projectId, callback, null);
        }

        SaveTask(String folderPath, String projectId, SaveListener callback, ProgressListener progress) {
            this.folderPath = folderPath;
            this.projectId = projectId;
            this.callback = callback;
            this.progressCallback = progress;
        }

        @Override
        protected Result doInBackground(Void... params) {
            try {
                if (cancelled) {
                    return new Result(false, 0, "Cancelled");
                }

                File folder = new File(folderPath);
                if (!folder.exists() || !folder.isDirectory()) {
                    return new Result(false, 0, "Folder not found: " + folderPath);
                }

                File[] files = folder.listFiles();
                if (files == null || files.length == 0) {
                    return new Result(false, 0, "No files found");
                }

                mb manager = ma.c(projectId);
                if (manager == null) {
                    return new Result(false, 0, "Manager is null");
                }

                if (manager.f373a == null) {
                    manager.f373a = new ArrayList<ProjectResourceBean>();
                }

                int total = files.length;
                int validFiles = 0;
                int newlyAdded = 0;

                for (int i = 0; i < total; i++) {
                    if (cancelled) {
                        return new Result(false, 0, "Cancelled");
                    }

                    File file = files[i];

                    if (!file.isFile()) {
                        continue;
                    }

                    String name = file.getName().toLowerCase();
                    if (name.endsWith(".png") || name.endsWith(".jpg") ||
                        name.endsWith(".jpeg") || name.endsWith(".gif")) {

                        String fullName = file.getName();
                        String resName = fullName;
                        int dotIndex = fullName.lastIndexOf(".");
                        if (dotIndex > 0) {
                            resName = fullName.substring(0, dotIndex);
                        }

                        validFiles++;

                        boolean alreadyExists = false;
                        Iterator<ProjectResourceBean> it = manager.f373a.iterator();
                        while (it.hasNext()) {
                            if (it.next().resFullName.equals(fullName)) {
                                alreadyExists = true;
                                break;
                            }
                        }

                        if (!alreadyExists) {
                            ProjectResourceBean bean = new ProjectResourceBean(1, resName, fullName);
                            manager.f373a.add(bean);
                            newlyAdded++;
                        }

                        if (progressCallback != null) {
                            publishProgress(validFiles, total, fullName);
                        }
                    }
                }

                if (validFiles == 0) {
                    return new Result(false, 0, "No valid image files");
                }

                if (newlyAdded > 0) {
                    manager.h();
                }

                return new Result(true, newlyAdded, "Success");

            } catch (Exception e) {
                return new Result(false, 0, e.getMessage());
            }
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            if (progressCallback != null && values.length >= 3) {
                int current = (Integer) values[0];
                int total = (Integer) values[1];
                String fileName = (String) values[2];
                progressCallback.onProgress(current, total, fileName);
            }
        }

        @Override
        protected void onPostExecute(Result result) {
            if (callback == null) return;

            if (result.success) {
                callback.onSuccess(result.count);
            } else {
                callback.onFailure(result.message);
            }
        }

        void cancel() {
            cancelled = true;
            cancel(true);
        }
    }

    private class LoadRunnable implements Runnable {
        private String projectId;
        private LoadListener listener;

        LoadRunnable(String projectId, LoadListener listener) {
            this.projectId = projectId;
            this.listener = listener;
        }

        @Override
        public void run() {
            try {
                mb manager = ma.c(projectId);

                if (manager == null) {
                    postError("Manager is null");
                    return;
                }

                manager.l();

                final ArrayList<ProjectResourceBean> resources = new ArrayList<ProjectResourceBean>();

                if (manager.f373a != null) {
                    resources.addAll(manager.f373a);
                }

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(resources);
                    }
                });

            } catch (Exception e) {
                postError(e.getMessage());
            }
        }

        private void postError(final String error) {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onFailure(error);
                }
            });
        }
    }
}