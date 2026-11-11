package com.nexusteam.blacklogics.ui;

import android.app.Activity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.textfield.TextInputEditText;
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
        preferenceScroller.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        preferenceScroller.setClipToPadding(false);
        preferenceScroller.setPadding(
                dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(12));

        LinearLayout preferenceContainer = new LinearLayout(dialog.getContext());
        preferenceContainer.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        preferenceContainer.setOrientation(LinearLayout.VERTICAL);
        preferenceContainer.setPadding(
                dpToPx(16), dpToPx(8), dpToPx(16), dpToPx(8));

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

        final MaterialCheckBox removeOldMethods = addTogglePref(
                ProjectSettings.SETTING_DISABLE_OLD_METHODS,
                false,
                "Remove old deprecated methods in files, like showMessage, getDip, etc.",
                preferenceContainer);

        final MaterialCheckBox useNewMaterialComponentsAppTheme = addTogglePref(
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

        dialog.a(xB.b().a(activity, R.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.b(xB.b().a(activity, R.string.common_word_save), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setValues(preferences);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @NonNull
    private MaterialCheckBox addTogglePref(
            String key, boolean defaultState, String hint, LinearLayout layout) {

        MaterialCheckBox checkBox = new MaterialCheckBox(activity);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, dpToPx(8), 0, dpToPx(4));
        checkBox.setLayoutParams(layoutParams);

        checkBox.setMinHeight(dpToPx(48));
        checkBox.setPadding(dpToPx(8), dpToPx(12), dpToPx(8), dpToPx(12));

        String value = settings.getValue(key, defaultState ? "true" : "false");
        checkBox.setText(hint);
        checkBox.setChecked("true".equals(value));

        checkBox.setTextColor(MaterialColors.getColor(
                checkBox, com.google.android.material.R.attr.colorOnSurface));
        checkBox.setButtonTintList(android.content.res.ColorStateList.valueOf(
                MaterialColors.getColor(
                        checkBox, com.google.android.material.R.attr.colorPrimary)));

        checkBox.setTag(key);
        layout.addView(checkBox);
        return checkBox;
    }

    @NonNull
    private EditText addInputPref(
            String key, String defaultValue, String hint, int inputType, LinearLayout layout) {

        TextInputLayout textInputLayout = new TextInputLayout(activity);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, dpToPx(8), 0, dpToPx(4));
        textInputLayout.setLayoutParams(layoutParams);

        textInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        textInputLayout.setHint(hint);
        textInputLayout.setBoxCornerRadii(
                dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12));

        int primaryColor = MaterialColors.getColor(
                textInputLayout, com.google.android.material.R.attr.colorPrimary);
        int outlineColor = MaterialColors.getColor(
                textInputLayout, com.google.android.material.R.attr.colorOutline);
        textInputLayout.setBoxStrokeColorStateList(new android.content.res.ColorStateList(
                new int[][] {
                        new int[] { android.R.attr.state_focused },
                        new int[] {}
                },
                new int[] { primaryColor, outlineColor }
        ));
        textInputLayout.setHintTextColor(new android.content.res.ColorStateList(
                new int[][] {
                        new int[] { android.R.attr.state_focused },
                        new int[] {}
                },
                new int[] { primaryColor, outlineColor }
        ));

        TextInputEditText editText = new TextInputEditText(activity);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        editText.setTextSize(16);
        editText.setTextColor(MaterialColors.getColor(
                editText, com.google.android.material.R.attr.colorOnSurface));
        editText.setHintTextColor(MaterialColors.getColor(
                editText, com.google.android.material.R.attr.colorOnSurfaceVariant));

        editText.setText(settings.getValue(key, defaultValue));
        editText.setTag(key);
        editText.setInputType(inputType);
        editText.setSingleLine(true);

        textInputLayout.addView(editText);
        layout.addView(textInputLayout);

        return editText;
    }

    private int dpToPx(float dp) {
        return (int) (dp * activity.getResources().getDisplayMetrics().density);
    }
}