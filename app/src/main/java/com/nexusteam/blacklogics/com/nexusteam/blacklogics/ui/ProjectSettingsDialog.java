
package com.nexusteam.blacklogics.ui;

import android.app.Activity;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.material.textfield.TextInputLayout;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.model.ProjectSettings;
import com.nexusteam.blacklogics.utils.Helper;

import a.a.a.aB;
import a.a.a.xB;

public class ProjectSettingsDialog {

    private final Activity activity;
    private final ProjectSettings settings;

    public ProjectSettingsDialog(Activity activity, String sc_id) {
        this.activity = activity;
        this.settings = new ProjectSettings(sc_id);
    }

    public void show() {
        final aB dialog = new aB(activity);
        dialog.a(R.drawable.services_48);
        dialog.b("Project Configuration");

        ScrollView preferenceScroller = new ScrollView(dialog.getContext());
        {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            preferenceScroller.setLayoutParams(layoutParams);
        }

        LinearLayout preferenceContainer = new LinearLayout(dialog.getContext());
        {
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            preferenceContainer.setLayoutParams(layoutParams);
            preferenceContainer.setOrientation(LinearLayout.VERTICAL);
        }

        final EditText minimumSdkVersion = addInputPref(
                ProjectSettings.SETTING_MINIMUM_SDK_VERSION,
                "21",
                "Minimum SDK version",
                InputType.TYPE_CLASS_NUMBER,
                preferenceContainer);

        final EditText targetSdkVersion = addInputPref(
                ProjectSettings.SETTING_TARGET_SDK_VERSION,
                "28",
                "Target SDK version",
                InputType.TYPE_CLASS_NUMBER,
                preferenceContainer);

        final EditText applicationClassName = addInputPref(
                ProjectSettings.SETTING_APPLICATION_CLASS,
                ".BlackApplication",
                "Application class name",
                InputType.TYPE_CLASS_TEXT,
                preferenceContainer);

        final CheckBox removeOldMethods = addTogglePref(
                ProjectSettings.SETTING_DISABLE_OLD_METHODS,
                false,
                "Remove old deprecated methods in files, like showMessage, getDip, etc.",
                preferenceContainer);

        final CheckBox useNewMaterialComponentsAppTheme = addTogglePref(
                ProjectSettings.SETTING_ENABLE_BRIDGELESS_THEMES,
                false,
                "Use new MaterialComponents AppTheme (will replace e.g. Button with MaterialButton, be careful!)",
                preferenceContainer);

        preferenceScroller.addView(preferenceContainer);
        dialog.a(preferenceScroller);

        final View[] preferences = {
                minimumSdkVersion,
                targetSdkVersion,
                applicationClassName,
                removeOldMethods,
                useNewMaterialComponentsAppTheme
        };

        dialog.a(xB.b().a(activity, R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.b(xB.b().a(activity, R.string.common_word_save), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setValues(preferences);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private CheckBox addTogglePref(String key, boolean defaultState, String hint, LinearLayout layout) {
        CheckBox checkBox = new CheckBox(activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, dpToPx(10), 0, 0);
        checkBox.setLayoutParams(layoutParams);
        layout.addView(checkBox);

        String value = settings.getValue(key, defaultState ? "true" : "false");

        checkBox.setText(hint);
        checkBox.setChecked("true".equals(value));
        checkBox.setTextColor(Color.BLACK);
        checkBox.setPadding(dpToPx(4), dpToPx(8), dpToPx(8), dpToPx(8));

        checkBox.setTag(key);

        return checkBox;
    }

    private EditText addInputPref(String key, String defaultValue, String hint, int inputType, LinearLayout layout) {
        TextInputLayout textInputLayout = new TextInputLayout(activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, dpToPx(10), 0, 0);
        textInputLayout.setLayoutParams(layoutParams);
        layout.addView(textInputLayout);

        EditText editText = new EditText(activity);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setPadding(dpToPx(4), dpToPx(8), dpToPx(8), dpToPx(8));
        editText.setTextSize(16);
        editText.setTextColor(Color.BLACK);
        editText.setHint(hint);
        editText.setHintTextColor(0xff607d8b);
        editText.setText(settings.getValue(key, defaultValue));
        editText.setTag(key);
        editText.setInputType(inputType);
        textInputLayout.addView(editText);

        return editText;
    }

    private int dpToPx(float dp) {
        return (int) (dp * activity.getResources().getDisplayMetrics().density);
    }
}