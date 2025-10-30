package com.nexusteam.ui;

import android.content.Context;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Material Design Dialog for selecting text size (8sp–100sp)
 * Created by NexusTeam & SmartIndiaGaming
 */
public class TextSizePickerDialog {

    public interface OnTextSizeSelected {
        void onSelected(String selectedValue);
    }

    private final Context context;
    private final OnTextSizeSelected listener;
    private final String currentValue;

    public TextSizePickerDialog(Context context, String currentValue, OnTextSizeSelected listener) {
        this.context = context;
        this.listener = listener;
        this.currentValue = currentValue;
    }

    public void show() {
        List<String> sizeList = new ArrayList<>();
        for (int i = 8; i <= 100; i++) {
            sizeList.add(i + "sp");
        }

        CharSequence[] items = sizeList.toArray(new CharSequence[0]);
        int preSelect = -1;

        // Pre-select current value if already set
        if (currentValue != null && currentValue.endsWith("sp")) {
            try {
                float val = Float.parseFloat(currentValue.replace("sp", "").trim());
                preSelect = (int) (val - 8);
            } catch (Exception ignored) {}
        }

        new MaterialAlertDialogBuilder(context)
                .setTitle("Select Text Size")
                .setSingleChoiceItems(items, preSelect, (dialog, which) -> {
                    String selected = items[which].toString();
                    if (listener != null) listener.onSelected(selected);
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
