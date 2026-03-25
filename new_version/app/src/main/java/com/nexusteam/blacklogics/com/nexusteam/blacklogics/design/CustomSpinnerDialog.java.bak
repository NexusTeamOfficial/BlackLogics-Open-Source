package com.nexusteam.blacklogics.design;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.DesignActivity;
import com.besome.blacklogics.development.Complex;
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
        
        final TextInputEditText editText =
            dialog.findViewById(R.id.edittext3);
        MaterialButton cancelButton =
            dialog.findViewById(R.id.button5);
        MaterialButton addButton =
            dialog.findViewById(R.id.button6);
        
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
                
                String viewName = editText.getText()
                    .toString()
                    .trim();
                
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
                
                // FIXED: Only add to custom views, not to normal views
                // Remove the line that sets custom view name which also adds to xName/acName
                // complex.setCustomViewName(viewName);
                
                // Instead, directly add to custom views array only
                addToCustomViewsOnly(viewName);
                
                dialog.dismiss();
                toast("Custom view '" + viewName + "' created successfully");
            }
        });
        
        dialog.show();
    }
    
    /**
     * Add view only to custom views array, not to normal views
     */
    private void addToCustomViewsOnly(String viewName) {
        try {
            org.json.JSONObject jsonData = complex.jsonData;
            org.json.JSONObject temp = new org.json.JSONObject(jsonData.toString());
            org.json.JSONObject metaData = temp.optJSONObject("meta");
            if (metaData == null) metaData = new org.json.JSONObject();
            
            // Only add to customViews array, NOT to xName/acName
            org.json.JSONArray customViewArray = metaData.optJSONArray("customViews");
            if (customViewArray == null) customViewArray = new org.json.JSONArray();
            
            String encodedViewName = complex.encodeData(viewName);
            if (!arrayContains(customViewArray, encodedViewName)) {
                customViewArray.put(encodedViewName);
            }
            
            metaData.put("customViews", customViewArray);
            
            // Create default XML file for the custom view (optional)
            String xmlFileName = viewName.toLowerCase() + ".xml";
            String defaultXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<merge xmlns:android=\"http://schemas.android.com/apk/res/android\">\n\n" +
                "</merge>";
            
            // Save XML to a separate location or just store in the custom view data
            // We're not adding to xName array, so we need to store it elsewhere
            org.json.JSONObject customViewData = metaData.optJSONObject("customViewData");
            if (customViewData == null) customViewData = new org.json.JSONObject();
            customViewData.put(encodedViewName, complex.encodeData(defaultXml));
            metaData.put("customViewData", customViewData);
            
            temp.put("meta", metaData);
            complex.jsonData = temp;
            complex.saveJson();
            
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Helper method to check if JSONArray contains a value
     */
    private boolean arrayContains(org.json.JSONArray array, String value) {
        try {
            for (int i = 0; i < array.length(); i++) {
                if (array.getString(i).equals(value)) {
                    return true;
                }
            }
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private void toast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}