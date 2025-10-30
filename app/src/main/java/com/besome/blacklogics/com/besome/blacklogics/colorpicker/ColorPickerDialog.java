package com.besome.blacklogics.colorpicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;
import com.besome.blacklogics.*;

public class ColorPickerDialog extends Dialog {
    
    public interface OnColorSelectedListener {
        void onColorSelected(int color);
    }
    
    private String title;
    private int initialColor;
    private OnColorSelectedListener listener;
    
    public ColorPickerDialog(Context context, int initialColor) {
        super(context);
        this.initialColor = initialColor;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setOnColorSelectedListener(OnColorSelectedListener listener) {
        this.listener = listener;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_color_picker);
        
        if (title != null) {
            setTitle(title);
        }
        
        final ColorPicker picker = findViewById(R.id.picker);
        SVBar svBar = findViewById(R.id.svbar);
        OpacityBar opacityBar = findViewById(R.id.opacitybar);
        SaturationBar saturationBar = findViewById(R.id.saturationbar);
        ValueBar valueBar = findViewById(R.id.valuebar);
        final EditText hexValue = findViewById(R.id.hexValue);
        Button okButton = findViewById(R.id.okButton);
        Button cancelButton = findViewById(R.id.cancelButton);
        
        // Setup color picker with bars
        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);
        
        // Set initial color
        picker.setColor(initialColor);
        hexValue.setText(String.format("#%08X", initialColor));
        
        // Update hex value when color changes
        picker.setOnColorChangedListener(color -> {
            hexValue.setText(String.format("#%08X", color));
        });
        
        // Try to parse hex value when edited
        hexValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                try {
                    String hex = hexValue.getText().toString();
                    if (hex.startsWith("#")) {
                        picker.setColor(Color.parseColor(hex));
                    } else {
                        picker.setColor(Color.parseColor("#" + hex));
                    }
                } catch (IllegalArgumentException e) {
                    // Invalid color, ignore
                }
            }
        });
        
        okButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onColorSelected(picker.getColor());
            }
            dismiss();
        });
        
        cancelButton.setOnClickListener(v -> dismiss());
    }
}