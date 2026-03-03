
package com.nexusteam.blacklogics.utils;

import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.nexusteam.blacklogics.bean.BackupConfig;

import java.util.HashMap;

public class BackupDialogHelper {
    
    public static View createBackupOptionsView(Activity activity, final BackupConfig config) {
        LinearLayout checkboxContainer = new LinearLayout(activity);
        checkboxContainer.setOrientation(LinearLayout.VERTICAL);
        checkboxContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        
        int dip = (int) BlackLogicsUtil.getDip(activity, 8);
        checkboxContainer.setPadding(dip, dip, dip, dip);
        
        final HashMap<Integer, Boolean> dialogStates = new HashMap<>();
        dialogStates.put(0, false);
        dialogStates.put(1, false);
        config.setDialogStates(dialogStates);
        
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int index = -1;
                Object tag = buttonView.getTag();
                if (tag instanceof String) {
                    String tagStr = (String) tag;
                    switch (tagStr) {
                        case "local libraries":
                            index = 0;
                            break;
                        case "Custom Blocks":
                            index = 1;
                            break;
                    }
                    if (index != -1) {
                        dialogStates.put(index, isChecked);
                        config.setDialogStates(dialogStates);
                        
                        if (index == 0) {
                            config.setIncludeLocalLibraries(isChecked);
                        } else if (index == 1) {
                            config.setIncludeCustomBlocks(isChecked);
                        }
                    }
                }
            }
        };
        
        MaterialCheckBox includeLocalLibraries = new MaterialCheckBox(activity);
        includeLocalLibraries.setTag("local libraries");
        includeLocalLibraries.setText("Include used Local libraries");
        includeLocalLibraries.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        includeLocalLibraries.setOnCheckedChangeListener(listener);
        checkboxContainer.addView(includeLocalLibraries);
        
        MaterialCheckBox includeUsedCustomBlocks = new MaterialCheckBox(activity);
        includeUsedCustomBlocks.setTag("Custom Blocks");
        includeUsedCustomBlocks.setText("Include used Custom Blocks");
        includeUsedCustomBlocks.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        includeUsedCustomBlocks.setOnCheckedChangeListener(listener);
        checkboxContainer.addView(includeUsedCustomBlocks);
        
        return checkboxContainer;
    }
}