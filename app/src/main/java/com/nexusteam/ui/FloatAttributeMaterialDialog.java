
package com.nexusteam.ui;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.Slider;

import java.util.Locale;

public class FloatAttributeMaterialDialog {

    public interface OnFloatSelected {
        void onSelected(float value);
    }

    public static void show(Context context, String title, float current, float min, float max, float step, OnFloatSelected listener) {


        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        int pad = (int) (context.getResources().getDisplayMetrics().density * 24);
        layout.setPadding(pad, pad + 10, pad, pad);


        final TextView tvValue = new TextView(context);
        tvValue.setTextSize(16f);
        tvValue.setPadding(0, 0, 0, (int) (8 * context.getResources().getDisplayMetrics().density));
        tvValue.setText(String.format(Locale.getDefault(), "Current: %.1f", current));


        final Slider slider = new Slider(context);
        slider.setValueFrom(min);
        slider.setValueTo(max);
        slider.setStepSize(step);
        slider.setValue(current);


        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(Slider s, float value, boolean fromUser) {
                tvValue.setText(String.format(Locale.getDefault(), "Current: %.1f", value));
            }
        });


        layout.addView(tvValue);
        layout.addView(slider);


        if (layout.getParent() != null) {
            ((ViewGroup) layout.getParent()).removeView(layout);
        }


        final OnFloatSelected finalListener = listener;
        new MaterialAlertDialogBuilder(context,
        com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
        .setTitle(title)
        .setView(layout)
        .setPositiveButton("Save", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                if (finalListener != null) {
                    finalListener.onSelected(slider.getValue());
                }
            }
        })
        .setNegativeButton("Cancel", null)
        .show();
    }

    public static void show(Context context, String title, float current, OnFloatSelected listener) {
        show(context, title, current, -100f, 100f, 0.5f, listener);
    }
}