package b.b.b;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;

public class xy extends MaterialAlertDialogBuilder {

    private final rs settings;
    private AlertDialog dialog;
    private final Context context;
    

    private TextInputEditText minSdkInput;
    private TextInputEditText targetSdkInput;
    private TextInputEditText appNameInput;
    private MaterialCheckBox cbViewBinding;
    private MaterialCheckBox cbDeprecated;
    private MaterialCheckBox cbMaterial;

    public xy(Context context, rs projectSettings) {
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
        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(context, 20), dp(context, 20), dp(context, 20), dp(context, 20));
        ScrollView scroll = new ScrollView(context);
        scroll.addView(root);

        TextView title = new TextView(context);
        title.setText("Project Settings");
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        title.setPadding(0, 0, 0, dp(context, 12));
        root.addView(title);

        TextInputLayout minSdkLayout = createTextInputLayout(context, "Minimum SDK version");
        minSdkInput = new TextInputEditText(context); // Use instance variable
        minSdkInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        minSdkInput.setText(String.valueOf(settings.getMinSdk()));
        minSdkLayout.addView(minSdkInput);
        root.addView(minSdkLayout);

        TextInputLayout targetSdkLayout = createTextInputLayout(context, "Target SDK version");
        targetSdkInput = new TextInputEditText(context); // Use instance variable
        targetSdkInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        targetSdkInput.setText(String.valueOf(settings.getTargetSdk()));
        targetSdkLayout.addView(targetSdkInput);
        root.addView(targetSdkLayout);

        TextInputLayout appNameLayout = createTextInputLayout(context, "Application class name");
        appNameInput = new TextInputEditText(context); // Use instance variable
        appNameInput.setText(".BlackApplication");
        appNameLayout.addView(appNameInput);
        root.addView(appNameLayout);

        cbViewBinding = createCheckBox(context, "Enable ViewBinding"); // Use instance variable
        cbDeprecated = createCheckBox(context, "Remove old deprecated methods"); // Use instance variable
        cbMaterial = createCheckBox(context, "Use MaterialComponents"); // Use instance variable

        cbViewBinding.setChecked(settings.isEnableLogging());
        cbDeprecated.setChecked(settings.isObfuscateCode());
        cbMaterial.setChecked(settings.isResourceEncrypt());





        LinearLayout btnRow = new LinearLayout(context);
        btnRow.setOrientation(LinearLayout.HORIZONTAL);
        btnRow.setGravity(Gravity.END);
        btnRow.setPadding(0, dp(context, 10), 0, 0);

        MaterialButton btnCancel = new MaterialButton(context, null, com.google.android.material.R.attr.materialButtonOutlinedStyle);
        btnCancel.setText("Cancel");
        MaterialButton btnSave = new MaterialButton(context);
        btnSave.setText("Save");

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
                    int minSdk = Integer.parseInt(minSdkInput.getText().toString());
                    int targetSdk = Integer.parseInt(targetSdkInput.getText().toString());

                    settings
                        .setMinSdk(minSdk)
                        .setTargetSdk(targetSdk)
                        .setEnableLogging(cbViewBinding.isChecked())
                        .setObfuscateCode(cbDeprecated.isChecked())
                        .setResourceEncrypt(cbMaterial.isChecked())
                        .save(context); // Now 'context' is an instance variable

                    dismissDialog();
                } catch (Exception e) {
                    Toast.makeText(context, "Invalid input!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return scroll;
    }

    private TextInputLayout createTextInputLayout(Context context, String hint) {
        TextInputLayout layout = new TextInputLayout(context);
        layout.setHint(hint);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.bottomMargin = dp(context, 10);
        layout.setLayoutParams(params);
        return layout;
    }

    private MaterialCheckBox createCheckBox(Context context, String text) {
        MaterialCheckBox cb = new MaterialCheckBox(context);
        cb.setText(text);
        cb.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        return cb;
    }

    private void dismissDialog() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
    }

    private int dp(Context context, int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
}