package com.nexusteam.blacklogics.design;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.DesignActivity;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.internal.beans.WidgetCollectionBean;
import com.nexusteam.internal.editor.view.ViewEditor;
import com.nexusteam.internal.lv;
import com.nexusteam.internal.ma;

import java.util.ArrayList;

public class GraphicEditorFragment extends Fragment {

    private static final String TAG = "GraphicEditorFragment";
    public static GraphicEditorFragment instance;

    public ViewEditor viewEditor;
    public String scId;
    public String activityName = "MainActivity";
    public String layoutName = "main";

    private ProjectFileBean currentFile;
    private boolean isLoaded = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        if (getArguments() != null) {
            scId = getArguments().getString("sc_id", "");
            activityName = getArguments().getString("activityName", "MainActivity");
            layoutName = getArguments().getString("layoutName", "main");
        } else if (getActivity() != null && getActivity().getIntent() != null) {
            scId = getActivity().getIntent().getStringExtra("sc_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fr_graphic_editor, container, false);
        viewEditor = root.findViewById(R.id.view_editor);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (scId != null && !scId.isEmpty() && currentFile != null) {
            loadProjectFile(scId, currentFile);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (instance == this) instance = null;
    }


    public void loadProjectFile(String scId, ProjectFileBean file) {
        this.scId = scId;
        this.currentFile = file;
        if (viewEditor == null || file == null) return;

        try {
            viewEditor.a(scId, file);

            if (file.fileType == 0) {
                String java = file.getJavaName();
                if (java != null && java.endsWith(".java")) {
                    java = java.substring(0, java.length() - 5);
                }
                activityName = java != null ? java : "MainActivity";
            }

            String xml = file.getXmlName();
            if (xml != null && xml.endsWith(".xml")) {
                xml = xml.substring(0, xml.length() - 4);
            }
            layoutName = xml != null ? xml : "main";
            isLoaded = true;
        } catch (Throwable t) {
            Log.e(TAG, "loadProjectFile failed", t);
        }
    }

    public void loadLayout(String scId, String activityName, String layoutName) {
        this.scId = scId;
        this.activityName = activityName;
        this.layoutName = layoutName;

        ProjectFileBean bean = new ProjectFileBean(
                ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY,
                layoutName
        );
        loadProjectFile(scId, bean);
    }

    public boolean saveLayout() {
        if (viewEditor == null || scId == null || currentFile == null) return false;
        try {
            ArrayList<ViewBean> beans = getCurrentViewBeans();
            if (beans != null) {
                com.nexusteam.blacklogics.bridge.ViewBeanBridge.saveToLayoutStore(
                        scId,
                        currentFile.getXmlName(),
                        beans
                );
            }
            return true;
        } catch (Throwable t) {
            Log.e(TAG, "saveLayout failed", t);
            return false;
        }
    }

    @Nullable
    public ArrayList<ViewBean> getCurrentViewBeans() {
        try {
            if (scId == null || currentFile == null) return null;
            return ma.a(scId).a(currentFile.getXmlName());
        } catch (Throwable t) {
            Log.w(TAG, "getCurrentViewBeans", t);
            return null;
        }
    }

    public void undo() {
        try {
            if (scId == null) return;
            Log.d(TAG, "undo requested — wire to lv history if needed");
        } catch (Throwable t) {
            Log.e(TAG, "undo", t);
        }
    }

    public void redo() {
        try {
            Log.d(TAG, "redo requested — wire to lv history if needed");
        } catch (Throwable t) {
            Log.e(TAG, "redo", t);
        }
    }

    public void setFavoriteData(ArrayList<WidgetCollectionBean> list) {
        if (viewEditor != null && list != null) {
            viewEditor.setFavoriteData(list);
        }
    }

    public ViewEditor getViewEditor() {
        return viewEditor;
    }

    public ProjectFileBean getCurrentFile() {
        return currentFile;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void onActivityOrLayoutChanged(String activityName, String layoutName) {
        this.activityName = activityName;
        this.layoutName = layoutName;
        if (scId != null && !scId.isEmpty()) {
            saveLayout();
            loadLayout(scId, activityName, layoutName);
        }
    }
}
