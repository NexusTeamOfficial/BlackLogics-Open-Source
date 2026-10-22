package com.nexusteam.blacklogics.design;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.DesignActivity;
import com.nexusteam.blacklogics.development.Complex;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CreateCustomViewDialog {
    
    private final DesignActivity activity;
    private final Complex complex;
    
    public CreateCustomViewDialog(DesignActivity activity, Complex complex) {
        this.activity = activity;
        this.complex = complex;
    }
    
    public void show() {
        
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.create_custom_view);
        
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT)
            );
            dialog.getWindow().setDimAmount(0.5f);
        }
        
        final TextInputEditText editText = dialog.findViewById(R.id.edittext3);
        MaterialButton cancelButton = dialog.findViewById(R.id.button5);
        MaterialButton addButton = dialog.findViewById(R.id.button6);
        
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String viewName = editText.getText().toString().trim();
                
                if (viewName.isEmpty()) {
                    toast("Please enter a view name");
                    return;
                }
                
                if (!viewName.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
                    toast("View name must start with a letter and contain only letters and numbers");
                    return;
                }
                
                if (complex.hasCustomView(viewName)) {
                    toast("View name already exists");
                    return;
                }
                
                complex.setCustomViewName(viewName);
                
                String defaultXml = 
                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                    "    android:layout_width=\"match_parent\"\n" +
                    "    android:layout_height=\"wrap_content\"\n" +
                    "    android:orientation=\"vertical\">\n\n" +
                    "</LinearLayout>";
                
                complex.registerCustomViewInRootLayout(viewName, defaultXml);
                
                dialog.dismiss();
                toast("Custom view '" + viewName + "' created successfully");
            }
        });
        
        dialog.show();
    }
    
    private void toast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}