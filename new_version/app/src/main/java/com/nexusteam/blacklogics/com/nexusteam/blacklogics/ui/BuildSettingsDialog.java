
package com.nexusteam.blacklogics.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.bean.BuildSettingsConfig;
import com.nexusteam.blacklogics.model.BuildSettings;
import com.nexusteam.blacklogics.utils.BuildSettingsDialogHelper;

import com.nexusteam.blacklogics.utils.BlackLogicsUtil;
import com.nexusteam.blacklogics.utils.Helper;

public class BuildSettingsDialog {
    
    private final Activity activity;
    private final BuildSettings settings;
    private BuildSettingsConfig config;
    
    public BuildSettingsDialog(Activity activity, String sc_id) {
        this.activity = activity;
        this.settings = new BuildSettings(sc_id);
        this.config = settings.loadConfig();
    }
    
    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        
        View inflate = activity.getLayoutInflater().inflate(R.layout.project_config_layout, null);
        
        ImageView icon = inflate.findViewById(R.id.project_config_icon);
        TextView title = inflate.findViewById(R.id.project_config_title);
        LinearLayout contentView = inflate.findViewById(R.id.project_config_pref_layout);
        TextView cancel = inflate.findViewById(R.id.text_cancel);
        TextView save = inflate.findViewById(R.id.text_save);
        
        icon.setImageResource(R.drawable.side_menu_setting_icon_over);
        title.setText("Build Settings");
        
        final View[] viewArr = new View[7];
        

        viewArr[0] = BuildSettingsDialogHelper.addInputPref(
            activity, 
            BuildSettings.SETTING_ANDROID_JAR_PATH, 
            "", 
            "Custom android.jar", 
            android.view.inputmethod.EditorInfo.TYPE_CLASS_TEXT, 
            contentView, 
            config
        );
        
        viewArr[1] = BuildSettingsDialogHelper.addInputPref(
            activity, 
            BuildSettings.SETTING_CLASSPATH, 
            "", 
            "Classpath (separated by :)", 
            android.view.inputmethod.EditorInfo.TYPE_CLASS_TEXT, 
            contentView, 
            config
        );
        
        viewArr[2] = BuildSettingsDialogHelper.addSingleChoicePref(
            activity, 
            BuildSettings.SETTING_DEXER, 
            new String[]{"Dx", "D8", "R8"}, 
            "Dx", 
            "Dexer", 
            contentView, 
            config
        );
        
        viewArr[3] = BuildSettingsDialogHelper.addSingleChoicePref(
            activity, 
            BuildSettings.SETTING_JAVA_VERSION, 
            new String[]{"1.7", "1.8"}, 
            "1.7", 
            "Java version", 
            contentView, 
            config
        );
        
        viewArr[4] = BuildSettingsDialogHelper.addTogglePref(
            activity, 
            BuildSettings.SETTING_NO_WARNINGS, 
            true, 
            "Hide warnings in error log", 
            12, 
            contentView, 
            config
        );
        
        viewArr[5] = BuildSettingsDialogHelper.addTogglePref(
            activity, 
            BuildSettings.SETTING_NO_HTTP_LEGACY, 
            false, 
            "Don't include http-legacy-28.dex", 
            12, 
            contentView, 
            config
        );
        
        viewArr[6] = BuildSettingsDialogHelper.addTogglePref(
            activity, 
            BuildSettings.SETTING_ENABLE_LOGCAT, 
            true, 
            "Enable debug logcat logs viewable in Logcat Reader. Not enabled in exported AABs/APKs.", 
            12, 
            contentView, 
            config
        );
        
        builder.setView(inflate);
        
        final AlertDialog buildSettingsDialog = builder.create();
        buildSettingsDialog.show();
        
        cancel.setOnClickListener(Helper.getDialogDismissListener(buildSettingsDialog));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BuildSettingsConfig newConfig = BuildSettingsDialogHelper.collectConfigFromViews(viewArr, config);
                

                settings.saveConfig(newConfig);
                
                BlackLogicsUtil.toast("Build settings saved");
                buildSettingsDialog.dismiss();
            }
        });
    }
}