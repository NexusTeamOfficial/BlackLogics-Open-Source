/*
 * MIT License (Modified) – Nexus Edition
 *
 * Copyright (c) 2025 NexusTeam & SmartIndiaGaming
 *
 * This file defines the `FloatAttributeMaterialDialog` class.
 * It provides a reusable, Material3-styled slider-based dialog
 * for safely editing float attributes (e.g. textSize, elevation, etc.)
 *
 * - Uses MaterialAlertDialogBuilder (Material3 look)
 * - Auto-detaches parent view to avoid "addViewInner" crash
 * - Includes real-time slider updates with value preview
 *
 * Concept, Engineering & Development by: NexusTeam & SmartIndiaGaming (2025)
 */

package com.nexusteam.ui;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.Slider;

import java.util.Locale;

public class FloatAttributeMaterialDialog {

    /** Callback listener for float selection */
    public interface OnFloatSelected {
        void onSelected(float value);
    }

    /**
     * Shows a Material3 float attribute dialog.
     *
     * @param context  Calling context
     * @param title    Dialog title (e.g., "Set Elevation")
     * @param current  Current float value
     * @param min      Minimum slider value
     * @param max      Maximum slider value
     * @param step     Step size (e.g., 0.5f)
     * @param listener Callback when "Save" clicked
     */
    public static void show(Context context, String title, float current, float min, float max, float step, OnFloatSelected listener) {

        // Root layout
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        int pad = (int) (context.getResources().getDisplayMetrics().density * 24);
        layout.setPadding(pad, pad + 10, pad, pad);

        // Value display
        TextView tvValue = new TextView(context);
        tvValue.setTextSize(16f);
        tvValue.setPadding(0, 0, 0, (int) (8 * context.getResources().getDisplayMetrics().density));
        tvValue.setText(String.format(Locale.getDefault(), "Current: %.1f", current));

        // Material slider
        Slider slider = new Slider(context);
        slider.setValueFrom(min);
        slider.setValueTo(max);
        slider.setStepSize(step);
        slider.setValue(current);

        // Live value update
        slider.addOnChangeListener((s, value, fromUser) ->
                tvValue.setText(String.format(Locale.getDefault(), "Current: %.1f", value)));

        // Add components
        layout.addView(tvValue);
        layout.addView(slider);

        // Prevent parent-attachment crash
        if (layout.getParent() != null) {
            ((ViewGroup) layout.getParent()).removeView(layout);
        }

        // Create dialog
        new MaterialAlertDialogBuilder(context,
                com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
                .setTitle(title)
                .setView(layout)
                .setPositiveButton("Save", (dialog, which) -> {
                    if (listener != null) {
                        listener.onSelected(slider.getValue());
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /** Simplified overload with default range [-100, 100] and step 0.5 */
    public static void show(Context context, String title, float current, OnFloatSelected listener) {
        show(context, title, current, -100f, 100f, 0.5f, listener);
    }
}
