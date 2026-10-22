package com.shapun.layouteditor.managers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shapun.layouteditor.managers.ViewBean;
import com.nexusteam.blacklogics.FileUtil;
import com.nexusteam.blacklogics.SketchwareUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewBeanManager {
    private static final String TAG = "ViewBeanManager";
    private static ViewBeanManager instance;
    
    private Context context;
    private String scId;
    private String activityName;
    

    private HashMap<String, ViewBean> viewBeans = new HashMap<>(); // id -> ViewBean
    private HashMap<View, ViewBean> viewToBeanMap = new HashMap<>(); // View -> ViewBean
    private ArrayList<ViewBean> pendingBeans = new ArrayList<>(); // Not yet added to layout
    

    private Gson gson;
    
    private ViewBeanManager() {

        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }
    
    public static ViewBeanManager getInstance() {
        if (instance == null) {
            instance = new ViewBeanManager();
        }
        return instance;
    }
    
    public void initialize(Context context, String scId, String activityName) {
        this.context = context.getApplicationContext();
        this.scId = scId;
        this.activityName = activityName;
        loadViewBeans();
    }
    

    public ViewBean createViewBean(String type, String id, String className) {
        ViewBean bean = new ViewBean(className, type, id);
        viewBeans.put(id, bean);
        saveViewBeans();
        return bean;
    }
    

    public void registerView(View view, ViewBean bean) {
        if (view != null && bean != null) {
            bean.setView(view);
            viewToBeanMap.put(view, bean);
            viewBeans.put(bean.getId(), bean);
        }
    }
    

    public void markAsAddedToLayout(ViewBean bean, ViewGroup parent, int index) {
        if (bean != null) {
            bean.markAsAdded(parent, index);
            pendingBeans.remove(bean);
            saveViewBeans();
            Log.d(TAG, "ViewBean added to layout: " + bean.getId());
        }
    }
    

    public void addPendingBean(ViewBean bean) {
        if (bean != null && !pendingBeans.contains(bean)) {
            pendingBeans.add(bean);
            viewBeans.put(bean.getId(), bean);
        }
    }
    

    public void removeViewBean(String id) {
        ViewBean bean = viewBeans.get(id);
        if (bean != null) {

            viewBeans.remove(id);
            pendingBeans.remove(bean);
            if (bean.getView() != null) {
                viewToBeanMap.remove(bean.getView());
            }
            

            if (bean.getParentId() != null) {
                ViewBean parent = viewBeans.get(bean.getParentId());
                if (parent != null) {
                    parent.removeChild(bean);
                }
            }
            
            saveViewBeans();
            Log.d(TAG, "ViewBean removed: " + id);
        }
    }
    

    public ViewBean getViewBean(String id) {
        return viewBeans.get(id);
    }
    

    public ViewBean getViewBean(View view) {
        return viewToBeanMap.get(view);
    }
    

    public List<ViewBean> getAllViewBeans() {
        return new ArrayList<>(viewBeans.values());
    }
    

    public List<ViewBean> getPendingBeans() {
        return new ArrayList<>(pendingBeans);
    }
    

    public void cleanupOrphanedBeans() {
        List<String> orphanedIds = new ArrayList<>();
        
        for (ViewBean bean : viewBeans.values()) {
            if (!bean.isAddedToLayout() && !pendingBeans.contains(bean)) {

                orphanedIds.add(bean.getId());
            }
        }
        
        for (String id : orphanedIds) {
            removeViewBean(id);
        }
        
        if (!orphanedIds.isEmpty()) {
            Log.i(TAG, "Cleaned up " + orphanedIds.size() + " orphaned ViewBeans");
            SketchwareUtil.showMessage(context, "Cleaned " + orphanedIds.size() + " orphaned widgets");
        }
    }
    

    public void saveViewBeans() {
        try {
            String filePath = getStorageFilePath();
            String json = gson.toJson(viewBeans);
            FileUtil.writeFile(filePath, json);
            Log.d(TAG, "ViewBeans saved: " + viewBeans.size() + " beans");
        } catch (Exception e) {
            Log.e(TAG, "Failed to save ViewBeans: " + e.getMessage(), e);
        }
    }
    

    public void loadViewBeans() {
        try {
            String filePath = getStorageFilePath();
            if (FileUtil.isExistFile(filePath)) {
                String json = FileUtil.readFile(filePath);
                Type type = new TypeToken<HashMap<String, ViewBean>>(){}.getType();
                HashMap<String, ViewBean> loadedBeans = gson.fromJson(json, type);
                
                if (loadedBeans != null) {
                    viewBeans.clear();
                    viewBeans.putAll(loadedBeans);
                    

                    pendingBeans.clear();
                    for (ViewBean bean : viewBeans.values()) {
                        if (!bean.isAddedToLayout()) {
                            pendingBeans.add(bean);
                        }
                    }
                    
                    Log.d(TAG, "ViewBeans loaded: " + viewBeans.size() + " beans");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to load ViewBeans: " + e.getMessage(), e);

            viewBeans = new HashMap<>();
            pendingBeans = new ArrayList<>();
        }
    }
    

    private String getStorageFilePath() {
        return FileUtil.getExternalStorageDir() + 
               "/.blacklogics/data/" + scId + 
               "/viewbeans/" + activityName + "_viewbeans.json";
    }
    

    public void clear() {
        viewBeans.clear();
        viewToBeanMap.clear();
        pendingBeans.clear();
    }
    

    public String getStats() {
        return "Total: " + viewBeans.size() + 
               ", Added: " + (viewBeans.size() - pendingBeans.size()) + 
               ", Pending: " + pendingBeans.size();
    }
    

    public void recoverPendingBeans() {
        if (!pendingBeans.isEmpty()) {
            Log.i(TAG, "Attempting to recover " + pendingBeans.size() + " pending beans");

            for (ViewBean bean : new ArrayList<>(pendingBeans)) {


                Log.w(TAG, "Pending bean (needs recovery): " + bean.getId());
            }
        }
    }
}