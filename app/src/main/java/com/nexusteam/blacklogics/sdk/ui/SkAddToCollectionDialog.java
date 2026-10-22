package com.nexusteam.blacklogics.sdk.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexusteam.blacklogics.sdk.collection.CollectionViewBeanHelper;
import com.nexusteam.blacklogics.sdk.collection.WidgetCollectionManager;
import com.nexusteam.blacklogics.sdk.layouteditor.AttributeStore;
import com.nexusteam.blacklogics.sdk.layouteditor.IdManager;
import com.nexusteam.blacklogics.sdk.layouteditor.ViewBeanRegistry;
import com.nexusteam.internal.beans.ViewBean;

import java.util.ArrayList;

/**
 * Professional Sketchware-style Add to Collection dialog.
 */
public final class SkAddToCollectionDialog {

    public interface Callback {
        void onSaved(String collectionName);
        void onCancelled();
    }

    private SkAddToCollectionDialog() {}

    public static void show(
            final Context context,
            final View targetView,
            final AttributeStore store,
            final IdManager idManager,
            final ViewBeanRegistry registry,
            final Callback callback) {

        if (context == null || targetView == null) {
            if (callback != null) callback.onCancelled();
            return;
        }

        float d = context.getResources().getDisplayMetrics().density;
        int pad = (int) (16 * d);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(pad, pad, pad, pad / 2);

        TextView hint = new TextView(context);
        hint.setText("Selected widget + children will be saved as a reusable collection (Sketchware style).");
        hint.setTextSize(12);
        hint.setPadding(0, 0, 0, pad / 2);
        layout.addView(hint);

        final EditText nameInput = new EditText(context);
        nameInput.setHint("Collection name");
        nameInput.setSingleLine(true);
        layout.addView(nameInput);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add to Collection");
        builder.setView(layout);
        builder.setPositiveButton("Save", null);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) callback.onCancelled();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = nameInput.getText().toString().trim();
                        if (TextUtils.isEmpty(name)) {
                            Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ArrayList<ViewBean> widgets = CollectionViewBeanHelper.collectFromView(
                                targetView, store, idManager, registry);
                        if (widgets == null || widgets.isEmpty()) {
                            Toast.makeText(context, "No widgets to save", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            WidgetCollectionManager.getInstance().add(name, widgets, true);
                            Toast.makeText(context, "Saved: " + name + " (" + widgets.size() + " views)", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            if (callback != null) callback.onSaved(name);
                        } catch (Exception e) {
                            String msg = "duplicate_name".equals(e.getMessage())
                                    ? "Name already exists" : "Failed to save";
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dialog.show();
    }
}
