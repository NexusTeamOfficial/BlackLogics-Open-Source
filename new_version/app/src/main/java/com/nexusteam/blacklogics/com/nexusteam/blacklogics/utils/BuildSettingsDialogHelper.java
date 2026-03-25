
package com.nexusteam.blacklogics.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.nexusteam.blacklogics.bean.BuildSettingsConfig;
import com.nexusteam.blacklogics.model.BuildSettings;

import com.nexusteam.blacklogics.utils.BlackLogicsUtil;

import static com.nexusteam.blacklogics.utils.BlackLogicsUtil.getDip;

public class BuildSettingsDialogHelper {
    
    public static EditText addInputPref(Activity activity, String key, String defaultValue, 
                                        String hint, int inputType, LinearLayout addTo, 
                                        BuildSettingsConfig config) {
        TextInputLayout textInputLayout = new TextInputLayout(activity);
        
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(
                0,
                (int) getDip(activity, 12),
                0,
                0
        );
        
        textInputLayout.setLayoutParams(layoutParams);
        addTo.addView(textInputLayout);
        
        EditText editText = new EditText(activity);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setPadding(
                (int) getDip(activity, 4),
                (int) getDip(activity, 8),
                (int) getDip(activity, 8),
                (int) getDip(activity, 8)
        );
        editText.setTextSize(16f);
        editText.setTextColor(0xff000000);
        editText.setHint(hint);
        editText.setHintTextColor(0xff607d8b);
        editText.setTag(key);
        editText.setInputType(inputType);
        

        switch (key) {
            case BuildSettings.SETTING_ANDROID_JAR_PATH:
                editText.setText(config.getAndroidJarPath());
                break;
            case BuildSettings.SETTING_CLASSPATH:
                editText.setText(config.getClasspath());
                break;
        }
        
        textInputLayout.addView(editText);
        return editText;
    }
    
    public static RadioGroup addSingleChoicePref(final Activity activity, final String key, 
                                                 final String[] choices, final String defaultValue, 
                                                 final String title, LinearLayout addTo, 
                                                 final BuildSettingsConfig config) {
        TextView textView = new TextView(activity);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(title);
        textView.setTextSize(14f);
        textView.setTextColor(0xff008DCD);
        textView.setPadding(
                0,
                (int) getDip(activity, 12),
                0,
                (int) getDip(activity, 12)
        );
        
        addTo.addView(textView);
        
        RadioGroup radioGroup = new RadioGroup(activity);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.setTag(key);
        radioGroup.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 
                ViewGroup.LayoutParams.WRAP_CONTENT));
        
        addTo.addView(radioGroup);
        
        final String currentValue;
        if (key.equals(BuildSettings.SETTING_DEXER)) {
            currentValue = config.getDexer();
        } else if (key.equals(BuildSettings.SETTING_JAVA_VERSION)) {
            currentValue = config.getJavaVersion();
        } else {
            currentValue = "";
        }
        
        for (final String choice : choices) {
            RadioButton radioButton = new RadioButton(activity);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setId(View.generateViewId());
            radioButton.setText(choice);
            radioButton.setTextColor(0xff000000);
            radioButton.setTextSize(16f);
            
            if (currentValue.equals(choice) || 
                (currentValue.isEmpty() && choice.equals(defaultValue))) {
                radioButton.setChecked(true);
            }
            
            radioGroup.addView(radioButton);
            
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) return;
                    
                    if (key.equals(BuildSettings.SETTING_JAVA_VERSION) && 
                        choice.equals(BuildSettings.SETTING_JAVA_VERSION_1_8)) {
                        BlackLogicsUtil.toast("Don't forget to enable D8 to be able to compile Java 8 code");
                    } else if (key.equals(BuildSettings.SETTING_DEXER) && 
                               choice.equals(BuildSettings.SETTING_DEXER_D8) && 
                               Build.VERSION.SDK_INT < 26) {
                        BlackLogicsUtil.toast("Your Android version isn't compatible with D8 (requires Android 8+).\nIf you proceed to use it, compilation will fail");
                    } else if (key.equals(BuildSettings.SETTING_DEXER) && 
                               choice.equals(BuildSettings.SETTING_DEXER_R8) && 
                               Build.VERSION.SDK_INT < 26) {
                        BlackLogicsUtil.toast("Your Android version isn't compatible with R8 (requires Android 8+).\nIf you proceed to use it, compilation will fail");
                    }
                }
            });
        }
        return radioGroup;
    }
    
    public static CheckBox addTogglePref(final Activity activity, final String key, 
                                         boolean defaultValue, String label, 
                                         int leftMargin, LinearLayout addTo, 
                                         final BuildSettingsConfig config) {
        CheckBox checkBox = new CheckBox(activity);
        
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(
                0,
                (int) getDip(activity, leftMargin),
                0,
                0
        );
        checkBox.setLayoutParams(layoutParams);
        addTo.addView(checkBox);
        
        boolean isChecked = false;
        switch (key) {
            case BuildSettings.SETTING_NO_WARNINGS:
                isChecked = config.isNoWarnings();
                break;
            case BuildSettings.SETTING_NO_HTTP_LEGACY:
                isChecked = config.isNoHttpLegacy();
                break;
            case BuildSettings.SETTING_ENABLE_LOGCAT:
                isChecked = config.isEnableLogcat();
                break;
        }
        
        checkBox.setText(label);
        checkBox.setChecked(isChecked);
        checkBox.setTextColor(0xff000000);
        checkBox.setPadding(
                (int) getDip(activity, 4),
                (int) getDip(activity, 8),
                (int) getDip(activity, 8),
                (int) getDip(activity, 8)
        );
        checkBox.setTag(key);
        
        if (key.equals(BuildSettings.SETTING_NO_HTTP_LEGACY)) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked1) {
                    if (isChecked1) {
                        BlackLogicsUtil.toast("Note that this option may cause issues if RequestNetwork component is used");
                    }
                }
            });
        }
        
        return checkBox;
    }
    
    public static BuildSettingsConfig collectConfigFromViews(View[] views, BuildSettingsConfig originalConfig) {
        BuildSettingsConfig config = new BuildSettingsConfig();
        

        config.setAndroidJarPath(originalConfig.getAndroidJarPath());
        config.setClasspath(originalConfig.getClasspath());
        config.setDexer(originalConfig.getDexer());
        config.setJavaVersion(originalConfig.getJavaVersion());
        config.setNoWarnings(originalConfig.isNoWarnings());
        config.setNoHttpLegacy(originalConfig.isNoHttpLegacy());
        config.setEnableLogcat(originalConfig.isEnableLogcat());
        config.setIncrementalBuildActive(originalConfig.isIncrementalBuildActive());
        
        for (View view : views) {
            Object tag = view.getTag();
            if (tag == null) continue;
            
            String key = tag.toString();
            
            if (view instanceof EditText) {
                String value = ((EditText) view).getText().toString();
                if (key.equals(BuildSettings.SETTING_ANDROID_JAR_PATH)) {
                    config.setAndroidJarPath(value);
                } else if (key.equals(BuildSettings.SETTING_CLASSPATH)) {
                    config.setClasspath(value);
                }
            } else if (view instanceof RadioGroup) {
                RadioGroup radioGroup = (RadioGroup) view;
                int checkedId = radioGroup.getCheckedRadioButtonId();
                if (checkedId != -1) {
                    RadioButton radioButton = radioGroup.findViewById(checkedId);
                    String value = radioButton.getText().toString();
                    
                    if (key.equals(BuildSettings.SETTING_DEXER)) {
                        config.setDexer(value);
                    } else if (key.equals(BuildSettings.SETTING_JAVA_VERSION)) {
                        config.setJavaVersion(value);
                    }
                }
            } else if (view instanceof CheckBox) {
                boolean isChecked = ((CheckBox) view).isChecked();
                
                if (key.equals(BuildSettings.SETTING_NO_WARNINGS)) {
                    config.setNoWarnings(isChecked);
                } else if (key.equals(BuildSettings.SETTING_NO_HTTP_LEGACY)) {
                    config.setNoHttpLegacy(isChecked);
                } else if (key.equals(BuildSettings.SETTING_ENABLE_LOGCAT)) {
                    config.setEnableLogcat(isChecked);
                }
            }
        }
        
        return config;
    }
}