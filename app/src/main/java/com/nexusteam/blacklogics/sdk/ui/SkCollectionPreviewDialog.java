package com.nexusteam.blacklogics.sdk.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nexusteam.blacklogics.sdk.collection.WidgetCollectionBean;
import com.nexusteam.internal.beans.ViewBean;

/**
 * Professional preview for Sketchware-style collection (structure list + add).
 */
public final class SkCollectionPreviewDialog {

    public interface Callback {
        void onAddToEditor(WidgetCollectionBean collection);
    }

    private SkCollectionPreviewDialog() {}

    public static void show(final Context context, final WidgetCollectionBean collection, final Callback callback) {
        if (context == null || collection == null) return;

        float d = context.getResources().getDisplayMetrics().density;
        int pad = (int) (12 * d);

        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(pad, pad, pad, pad);

        int n = collection.widgets != null ? collection.widgets.size() : 0;
        TextView info = new TextView(context);
        info.setText(n + " view(s)  •  hierarchy below");
        info.setTextColor(Color.parseColor("#616161"));
        info.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        info.setPadding(0, 0, 0, pad);
        root.addView(info);

        ScrollView scroll = new ScrollView(context);
        LinearLayout list = new LinearLayout(context);
        list.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(list);

        if (collection.widgets != null) {
            for (int i = 0; i < collection.widgets.size(); i++) {
                ViewBean vb = collection.widgets.get(i);
                if (vb == null) continue;

                LinearLayout row = new LinearLayout(context);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setGravity(Gravity.CENTER_VERTICAL);
                int rp = (int) (8 * d);
                row.setPadding(rp, rp, rp, rp);
                GradientDrawable bg = new GradientDrawable();
                bg.setColor(Color.parseColor("#F5F5F5"));
                bg.setCornerRadius(6 * d);
                row.setBackground(bg);
                LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                rlp.bottomMargin = (int) (4 * d);
                row.setLayoutParams(rlp);

                TextView idx = new TextView(context);
                idx.setText(String.valueOf(i + 1));
                idx.setTypeface(Typeface.DEFAULT_BOLD);
                idx.setTextColor(Color.parseColor("#1565C0"));
                idx.setPadding(0, 0, (int) (10 * d), 0);
                row.addView(idx);

                LinearLayout col = new LinearLayout(context);
                col.setOrientation(LinearLayout.VERTICAL);
                col.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                TextView title = new TextView(context);
                String id = vb.id != null ? vb.id : "(no id)";
                title.setText(id + "  ·  type " + vb.type);
                title.setTypeface(Typeface.DEFAULT_BOLD);
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                title.setTextColor(Color.parseColor("#212121"));
                col.addView(title);

                TextView sub = new TextView(context);
                String parent = vb.parent != null ? vb.parent : "root";
                sub.setText("parent: " + parent);
                sub.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
                sub.setTextColor(Color.parseColor("#757575"));
                col.addView(sub);

                row.addView(col);
                list.addView(row);
            }
        }

        root.addView(scroll, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, (int) (240 * d)));

        new AlertDialog.Builder(context)
                .setTitle(collection.name != null ? collection.name : "Collection")
                .setView(root)
                .setPositiveButton("Add to Editor", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callback != null) callback.onAddToEditor(collection);
                    }
                })
                .setNegativeButton("Close", null)
                .show();
    }
}
