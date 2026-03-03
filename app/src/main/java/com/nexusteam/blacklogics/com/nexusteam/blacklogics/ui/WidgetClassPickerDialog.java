package com.nexusteam.blacklogics.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import com.nexusteam.blacklogics.interfaces.OnWidgetClassSelectedListener;
import com.nexusteam.blacklogics.provider.WidgetClassSuggestionProvider;
import com.nexusteam.blacklogics.R;

public class WidgetClassPickerDialog {

    private Context context;
    private OnWidgetClassSelectedListener callback;

    public WidgetClassPickerDialog(
            Context context,
            OnWidgetClassSelectedListener callback
    ) {
        this.context = context;
        this.callback = callback;
    }

    public void show() {

        LinearLayout rootLayout = new LinearLayout(context);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        int padding = (int) (20 * context.getResources()
                .getDisplayMetrics().density);
        rootLayout.setPadding(padding, padding, padding, padding);

        TextInputLayout inputLayout = new TextInputLayout(
                context,
                null,
                com.google.android.material.R.attr.textInputOutlinedStyle
        );
        inputLayout.setHint("Widget class path");
        inputLayout.setBoxBackgroundMode(
                TextInputLayout.BOX_BACKGROUND_OUTLINE
        );
        inputLayout.setBoxCornerRadii(14, 14, 14, 14);

        final MaterialAutoCompleteTextView autoComplete =
                new MaterialAutoCompleteTextView(context);
        autoComplete.setHint("e.g. android.widget.Button");

        ArrayList<String> suggestions =
                WidgetClassSuggestionProvider.provide();

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        context,
                        R.layout.material_list_item,
                        R.id.item_text,
                        suggestions
                );

        autoComplete.setThreshold(1);
        autoComplete.setAdapter(adapter);

        inputLayout.addView(autoComplete);

        rootLayout.addView(
                inputLayout,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        new MaterialAlertDialogBuilder(context)
                .setTitle("Add Custom Widget")
                .setView(rootLayout)
                .setPositiveButton(
                        "Add",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which
                            ) {

                                String classPath =
                                        autoComplete.getText()
                                                .toString()
                                                .trim();

                                if (classPath.length() == 0) return;

                                try {
                                    Class clazz =
                                            Class.forName(classPath);

                                    callback.onWidgetClassSelected(
                                            clazz.getSimpleName(),
                                            classPath
                                    );
                                } catch (ClassNotFoundException e) {

                                }
                            }
                        }
                )
                .setNegativeButton("Cancel", null)
                .show();
    }
}
