package com.besome.blacklogics.custom;

import com.besome.blacklogics.*;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;

public class CustomActivityDialog {

    private final Context context;
    private OnSaveClickListener listener;

    public interface OnSaveClickListener {
        void onSave(String formattedName, String unformattedName);
    }

    public CustomActivityDialog(Context context) {
        this.context = context;
    }

    public void setOnSaveClickListener(OnSaveClickListener listener) {
        this.listener = listener;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialogTheme);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_custom_activity, null);
        builder.setView(dialogView);

        TextInputLayout textInputLayout = dialogView.findViewById(R.id.text_input_layout);
        EditText editText = textInputLayout.getEditText();
        
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setClickable(true);
        
        // Apply gradient programmatically if needed
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textInputLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.edittext_gradient));
        }*/

        setupEditTextValidation(editText);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);

        dialogView.findViewById(R.id.btn_save).setOnClickListener(v -> {
            String input = editText.getText().toString().trim();
            if (isValidInput(input)) {
                String formattedName = formatToCamelCase(input);
                if (listener != null) listener.onSave(formattedName, input);
                dialog.dismiss();
            } else {
                textInputLayout.setError("Only lowercase a-z, numbers (not first), and _");
            }
        });

        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
    // Force lowercase and block invalid chars in real-time
    private void setupEditTextValidation(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String currentText = s.toString();
                if (!currentText.matches("[a-z0-9_]*")) { // Only allow a-z, 0-9, _
                    String filtered = currentText.replaceAll("[^a-z0-9_]", "");
                    editText.setText(filtered);
                    editText.setSelection(editText.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Validate: No leading numbers, only a-z, 0-9, _
    private boolean isValidInput(String input) {
        return input.matches("^[a-z_][a-z0-9_]*$");
    }

    // Convert hello_world → HelloWorld
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
}