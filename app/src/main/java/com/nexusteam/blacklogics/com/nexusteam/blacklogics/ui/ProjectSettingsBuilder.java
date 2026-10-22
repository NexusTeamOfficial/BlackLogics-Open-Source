package com.nexusteam.blacklogics.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.nexusteam.blacklogics.manager.ProjectSettingsManager;

public class ProjectSettingsBuilder extends MaterialAlertDialogBuilder {

    private final ProjectSettingsManager settings;
    private final Context context;
    private AlertDialog dialog;

    private TextInputEditText minSdkInput;
    private TextInputEditText targetSdkInput;
    private TextInputEditText appNameInput;
    private MaterialCheckBox cbViewBinding;
    private MaterialCheckBox cbDeprecated;
    private MaterialCheckBox cbMaterial;

    public ProjectSettingsBuilder(Context context, ProjectSettingsManager projectSettings) {
        super(context);
        this.settings = projectSettings;
        this.context = context;
        setView(createDialogView(context));
        dialog = this.create();
    }

    @Override
    public AlertDialog show() {
        dialog.show();
        return dialog;
    }

    private View createDialogView(final Context context) {

        ScrollView scroll = new ScrollView(context);
        scroll.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        scroll.setClipToPadding(false);

        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        root.setPadding(
                dp(context, 24), dp(context, 20),
                dp(context, 24), dp(context, 12));
        scroll.addView(root);

        TextInputLayout minSdkLayout = createTextInputLayout(context, "Minimum SDK version");
        minSdkInput = new TextInputEditText(context);
        minSdkInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        minSdkInput.setText(String.valueOf(settings.getMinSdk()));
        minSdkLayout.addView(minSdkInput);
        root.addView(minSdkLayout);

        TextInputLayout targetSdkLayout = createTextInputLayout(context, "Target SDK version");
        targetSdkInput = new TextInputEditText(context);
        targetSdkInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        targetSdkInput.setText(String.valueOf(settings.getTargetSdk()));
        targetSdkLayout.addView(targetSdkInput);
        root.addView(targetSdkLayout);

        TextInputLayout appNameLayout = createTextInputLayout(context, "Application class name");
        appNameInput = new TextInputEditText(context);
        appNameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        appNameInput.setText(settings.getApplicationClassName());
        appNameLayout.addView(appNameInput);
        root.addView(appNameLayout);

        cbViewBinding = createCheckBox(context, "Enable ViewBinding");
        cbDeprecated = createCheckBox(context, "Remove old deprecated methods");
        cbMaterial = createCheckBox(context, "Use MaterialComponents");

        cbViewBinding.setChecked(settings.isEnableLogging());
        cbDeprecated.setChecked(settings.isObfuscateCode());
        cbMaterial.setChecked(settings.isResourceEncrypt());

        root.addView(cbViewBinding);
        root.addView(cbDeprecated);
        root.addView(cbMaterial);

        LinearLayout btnRow = new LinearLayout(context);
        btnRow.setOrientation(LinearLayout.HORIZONTAL);
        btnRow.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams btnRowParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        btnRowParams.topMargin = dp(context, 16);
        btnRow.setLayoutParams(btnRowParams);

        MaterialButton btnCancel = new MaterialButton(
                context, null,
                com.google.android.material.R.attr.materialButtonOutlinedStyle);
        btnCancel.setText("Cancel");

        MaterialButton btnSave = new MaterialButton(context);
        btnSave.setText("Save");

        LinearLayout.LayoutParams cancelParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        cancelParams.setMarginEnd(dp(context, 8));
        btnCancel.setLayoutParams(cancelParams);

        btnRow.addView(btnCancel);
        btnRow.addView(btnSave);
        root.addView(btnRow);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String minStr = minSdkInput.getText() != null
                            ? minSdkInput.getText().toString().trim() : "";
                    String targetStr = targetSdkInput.getText() != null
                            ? targetSdkInput.getText().toString().trim() : "";

                    if (minStr.isEmpty() || targetStr.isEmpty()) {
                        Toast.makeText(context,
                                "SDK values can't be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int minSdk = Integer.parseInt(minStr);
                    int targetSdk = Integer.parseInt(targetStr);
                    String appClassName = appNameInput.getText() != null
                            ? appNameInput.getText().toString().trim() : "";

                    settings
                            .setMinSdk(minSdk)
                            .setTargetSdk(targetSdk)
                            .setApplicationClassName(appClassName)
                            .setEnableLogging(cbViewBinding.isChecked())
                            .setObfuscateCode(cbDeprecated.isChecked())
                            .setResourceEncrypt(cbMaterial.isChecked())
                            .save(context);

                    dismissDialog();
                } catch (NumberFormatException e) {
                    Toast.makeText(context,
                            "SDK values must be valid numbers", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(context,
                            "Invalid input!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return scroll;
    }

    private TextInputLayout createTextInputLayout(Context context, String hint) {
        TextInputLayout layout = new TextInputLayout(context);
        layout.setHint(hint);
        layout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        layout.setBoxCornerRadii(
                dp(context, 12), dp(context, 12),
                dp(context, 12), dp(context, 12));

        int primaryColor = MaterialColors.getColor(
                layout, com.google.android.material.R.attr.colorPrimary);
        int outlineColor = MaterialColors.getColor(
                layout, com.google.android.material.R.attr.colorOutline);

        ColorStateList strokeColors = new ColorStateList(
                new int[][] {
                        new int[] { android.R.attr.state_focused },
                        new int[] {}
                },
                new int[] { primaryColor, outlineColor });

        layout.setBoxStrokeColorStateList(strokeColors);
        layout.setHintTextColor(strokeColors);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.bottomMargin = dp(context, 12);
        layout.setLayoutParams(params);

        return layout;
    }

    private MaterialCheckBox createCheckBox(Context context, String text) {
        MaterialCheckBox cb = new MaterialCheckBox(context);
        cb.setText(text);
        cb.setMinHeight(dp(context, 48));
        cb.setPadding(
                dp(context, 8), dp(context, 12),
                dp(context, 8), dp(context, 12));
        cb.setTextColor(MaterialColors.getColor(
                cb, com.google.android.material.R.attr.colorOnSurface));
        cb.setButtonTintList(ColorStateList.valueOf(
                MaterialColors.getColor(
                        cb, com.google.android.material.R.attr.colorPrimary)));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.topMargin = dp(context, 4);
        cb.setLayoutParams(params);

        return cb;
    }

    private void dismissDialog() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
    }

    private int dp(Context context, int value) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value,
                context.getResources().getDisplayMetrics());
    }
}