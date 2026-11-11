package com.nexusteam.blacklogics.design;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nexusteam.blacklogics.manager.ActivityManager;

import androidx.appcompat.app.AlertDialog;

import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.DesignActivity;
import com.nexusteam.blacklogics.development.Complex;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateActivityDialog {
    
    private final DesignActivity a;
    private final String sc_id;
    private final Complex complex;
    
    public CreateActivityDialog(DesignActivity activity, String sc_id, Complex complex) {
        this.a = activity;
        this.sc_id = sc_id;
        this.complex = complex;
    }
    
    public void show(boolean isUpdateMode, String existingActivityName) {
        
        View v = LayoutInflater.from(a)
        .inflate(R.layout.create_activity_dialog, null);
        
        final AlertDialog dialog = new MaterialAlertDialogBuilder(a)
        .setView(v)
        .create();
        

        final ImageView preview = v.findViewById(R.id.preview_image);
        final TextInputEditText nameEt = v.findViewById(R.id.edittext_name);
        
        final CheckBox cbStatus = v.findViewById(R.id.checkbox_status_bar);
        final CheckBox cbToolbar = v.findViewById(R.id.checkbox_toolbar);
        final CheckBox cbDrawer = v.findViewById(R.id.checkbox_drawer);
        final CheckBox cbFab = v.findViewById(R.id.checkbox_fab);
        
        final RadioGroup rgType = v.findViewById(R.id.radio_group_type);
        final RadioGroup rgOrientation = v.findViewById(R.id.radio_group_orientation);
        
        MaterialButton btnCancel = v.findViewById(R.id.button_cancel);
        MaterialButton btnSave = v.findViewById(R.id.button_save);
        

        final boolean finalIsUpdateMode = isUpdateMode;
        final String finalExistingActivityName = existingActivityName;
        
        
        if (isUpdateMode && existingActivityName != null) {
            try {
                JSONObject o = complex.getActivityData(existingActivityName);
                if (o != null) {
                    nameEt.setText(existingActivityName);
                    cbStatus.setChecked(o.optBoolean("statusBar", true));
                    cbToolbar.setChecked(o.optBoolean("toolbar", true));
                    cbDrawer.setChecked(o.optBoolean("drawer", false));
                    cbFab.setChecked(o.optBoolean("fab", false));
                    
                    rgType.check(
                    "Fragment".equals(o.optString("type")) ?
                    R.id.radio_fragment :
                    "DialogFragment".equals(o.optString("type")) ?
                    R.id.radio_dialog_fragment :
                    R.id.radio_activity
                    );
                    
                    rgOrientation.check(
                    "Portrait".equals(o.optString("orientation")) ?
                    R.id.radio_portrait :
                    "Landscape".equals(o.optString("orientation")) ?
                    R.id.radio_landscape :
                    R.id.radio_both
                    );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        
        

        View.OnClickListener previewUpdater = new View.OnClickListener() {
            @Override
            public void onClick(View x) {
                int m = 0;
                if (cbStatus.isChecked()) m |= 8;
                if (cbToolbar.isChecked()) m |= 4;
                if (cbDrawer.isChecked()) m |= 2;
                if (cbFab.isChecked()) m |= 1;
                
                Map<Integer, Integer> map = new HashMap<>();
                map.put(15, R.drawable.activity_1101);
                map.put(14, R.drawable.activity_0101);
                map.put(13, R.drawable.activity_1001);
                map.put(12, R.drawable.activity_0001);
                map.put(11, R.drawable.activity_1100);
                map.put(10, R.drawable.activity_0100);
                map.put(9, R.drawable.activity_1000);
                map.put(8, R.drawable.activity_0000);
                map.put(7, R.drawable.activity_1111);
                map.put(6, R.drawable.activity_1011);
                map.put(5, R.drawable.activity_0111);
                map.put(4, R.drawable.activity_preset_1);
                map.put(3, R.drawable.activity_1110);
                map.put(2, R.drawable.activity_0110);
                map.put(1, R.drawable.activity_1010);
                map.put(0, R.drawable.activity_0010);
                
                preview.setImageResource(map.get(m));
            }
        };
        
        cbStatus.setOnClickListener(previewUpdater);
        cbToolbar.setOnClickListener(previewUpdater);
        cbDrawer.setOnClickListener(previewUpdater);
        cbFab.setOnClickListener(previewUpdater);
        previewUpdater.onClick(null);
        
        

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {
                dialog.dismiss();
            }
        });
        

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {
                
                String raw = nameEt.getText().toString().trim();
                if (raw.isEmpty()) {
                    toast("Activity name cannot be empty");
                    return;
                }
                
                String actName = formatToCamelCase(raw);
                String xmlName = raw.toLowerCase().replaceAll("[^a-z0-9]", "_");
                
                List<String> names = complex.getAllJavaAndXmlNames();
                if (!finalIsUpdateMode || !actName.equals(finalExistingActivityName)) {
                    if (names.contains(actName + ".java") || names.contains(xmlName)) {
                        toast("Name already exists");
                        return;
                    }
                }
                
                String type = getType(rgType);
                String orientation = getOrientation(rgOrientation);
                
                boolean status = cbStatus.isChecked();
                boolean toolbar = cbToolbar.isChecked();
                boolean drawer = cbDrawer.isChecked();
                boolean fab = cbFab.isChecked();
                
                try {
                    
                    if (finalIsUpdateMode) {
                        
                        complex.updateActivity(
                        finalExistingActivityName,
                        actName,
                        xmlName,
                        fab,
                        toolbar,
                        complex.getAndroidXEnable(),
                        drawer,
                        type,
                        orientation,
                        status
                        );
                        
                    } else {
                        
                        

                        
                        complex.setAcName(actName);
                        complex.setXName(xmlName);
                        complex.addActivityToManifest(actName);
                        complex.enableFab(actName, fab);
                        complex.enableToolBar(
                        actName,
                        complex.getAndroidXEnable(),
                        toolbar
                        );
                        try {
                            complex.addActivity(actName, xmlName, fab, false, drawer);
                        } catch (Throwable ignore) {}
                        
                        ActivityManager activityManager = new ActivityManager();
                        activityManager.load(a, sc_id);
                        activityManager.addActivity(actName, xmlName);
                        activityManager.save(a, sc_id);
                        
                        AndroidManifestGenerator manifest = new AndroidManifestGenerator();
                        manifest.load(a, sc_id);
                        manifest.addActivity(
                        "." + actName,
                        createMainActivityAttributes(
                        resolveTheme(toolbar, status)
                        )
                        );
                        manifest.setAttribute("supportsRtl", "true");
                        manifest.save(a, sc_id);
                        
                    }

                    try {
                        com.nexusteam.blacklogics.sdk.util.ActivityConfig ac =
                                new com.nexusteam.blacklogics.sdk.util.ActivityConfig();
                        ac.init(a, sc_id);
                        ac.setActivityConfig(actName, status, toolbar, drawer, fab, type, orientation, xmlName);
                        ac.save(a, sc_id);
                    } catch (Throwable ignore) {}
                    try {
                        if (com.nexusteam.blacklogics.ViewBuilderFragmentActivity.instance != null) {
                            com.nexusteam.blacklogics.ViewBuilderFragmentActivity.instance.activityConfig.init(a, sc_id);
                            com.nexusteam.blacklogics.ViewBuilderFragmentActivity.instance.u();
                            com.nexusteam.blacklogics.ViewBuilderFragmentActivity.instance.refreshUI();
                        }
                    } catch (Throwable ignore) {}
                    
                    dialog.dismiss();
                    
                } catch (Exception e) {
                    toast(e.getMessage());
                }
            }
        });
        
        dialog.show();
    }
    
    
    private String getType(RadioGroup g) {
        if (g.getCheckedRadioButtonId() == R.id.radio_fragment) return "Fragment";
        if (g.getCheckedRadioButtonId() == R.id.radio_dialog_fragment) return "DialogFragment";
        return "Activity";
    }
    
    private String getOrientation(RadioGroup g) {
        if (g.getCheckedRadioButtonId() == R.id.radio_portrait) return "Portrait";
        if (g.getCheckedRadioButtonId() == R.id.radio_landscape) return "Landscape";
        return "Both";
    }
    
    private boolean isValidInput(String input) {
        return input.matches("^[a-z_][a-z0-9_]*$");
    }
    
    private String formatToCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        String[] parts = input.split("_");
        for (String part : parts) {
            if (!part.isEmpty()) {
                result.append(part.substring(0, 1).toUpperCase())
                .append(part.substring(1));
            }
        }
        return result.toString();
    }
    
    private Map<String, String> createMainActivityAttributes(String themeName) {
        Map<String, String> attrs = new HashMap<>();
        attrs.put("exported", "true");
        attrs.put("launchMode", "singleTop");
        attrs.put("configChanges", "orientation|keyboardHidden|screenSize");
        attrs.put("windowSoftInputMode", "adjustResize");
        
        attrs.put("theme", "@style/" + themeName);
        
        return attrs;
    }
    
    private String resolveTheme(boolean toolbar, boolean status) {
        if (toolbar && status) return "AppTheme";
        if (toolbar) return "NoStatusBar";
        if (status) return "NoActionBar";
        return "FullScreen";
    }
    
    private void toast(String s) {
        Toast.makeText(a, s, Toast.LENGTH_SHORT).show();
    }
}