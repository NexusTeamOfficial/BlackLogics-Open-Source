// AddToCollectionDialog.java
package com.shapun.layouteditor.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.nexusteam.blacklogics.R;
import com.shapun.layouteditor.managers.WidgetRepository;
import com.shapun.layouteditor.AttributeSet;

import java.util.ArrayList;
import java.util.List;

public class AddToCollectionDialog {
    
    public interface Callback {
        void onWidgetSaved(String blueprintId);
    }
    
    public static void show(final Context context, 
                            final android.view.View widgetView,
                            final AttributeSet attributeSet,
                            final Callback callback) {
        
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Save to Widget Repository");
        
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 30, 50, 30);
        
        // Widget name input
        TextView nameLabel = new TextView(context);
        nameLabel.setText("Widget Name");
        nameLabel.setTextSize(14);
        nameLabel.setTextColor(0xFF333333);
        
        final EditText nameInput = new EditText(context);
        nameInput.setHint("e.g., My Custom Button");
        nameInput.setSingleLine(true);
        
        // Description input
        TextView descLabel = new TextView(context);
        descLabel.setText("Description (Optional)");
        descLabel.setTextSize(14);
        descLabel.setTextColor(0xFF333333);
        descLabel.setPadding(0, 20, 0, 0);
        
        final EditText descInput = new EditText(context);
        descInput.setHint("What does this widget do?");
        descInput.setSingleLine(true);
        
        // Tags
        TextView tagsLabel = new TextView(context);
        tagsLabel.setText("Tags (Optional)");
        tagsLabel.setTextSize(14);
        tagsLabel.setTextColor(0xFF333333);
        tagsLabel.setPadding(0, 20, 0, 10);
        
        final EditText tagsInput = new EditText(context);
        tagsInput.setHint("button, custom, material");
        tagsInput.setSingleLine(true);
        
        // Preview note
        TextView previewNote = new TextView(context);
        previewNote.setText("Note: A thumbnail will be automatically generated");
        previewNote.setTextSize(12);
        previewNote.setTextColor(0xFF888888);
        previewNote.setPadding(0, 20, 0, 0);
        
        // Add all views
        layout.addView(nameLabel);
        layout.addView(nameInput);
        layout.addView(descLabel);
        layout.addView(descInput);
        layout.addView(tagsLabel);
        layout.addView(tagsInput);
        layout.addView(previewNote);
        
        builder.setView(layout);
        
        builder.setPositiveButton("Save", null);
        builder.setNegativeButton("Cancel", null);
        
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String widgetName = nameInput.getText().toString().trim();
                        
                        if (TextUtils.isEmpty(widgetName)) {
                            Toast.makeText(context, "Please enter a widget name", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        
                        // Parse tags
                        List<String> tags = new ArrayList<String>();
                        String tagsText = tagsInput.getText().toString().trim();
                        if (!TextUtils.isEmpty(tagsText)) {
                            String[] tagArray = tagsText.split(",");
                            for (String tag : tagArray) {
                                String trimmed = tag.trim();
                                if (!trimmed.isEmpty()) {
                                    tags.add(trimmed);
                                }
                            }
                        }
                        
                        // Save to repository
                        String blueprintId = WidgetRepository.getInstance(context)
                            .saveWidgetToRepository(
                                widgetName,
                                widgetView,
                                attributeSet,
                                descInput.getText().toString().trim(),
                                tags
                            );
                        
                        if (blueprintId != null) {
                            Toast.makeText(context, "Widget saved successfully!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            if (callback != null) {
                                callback.onWidgetSaved(blueprintId);
                            }
                        } else {
                            Toast.makeText(context, "Failed to save widget", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        
        dialog.show();
    }
}