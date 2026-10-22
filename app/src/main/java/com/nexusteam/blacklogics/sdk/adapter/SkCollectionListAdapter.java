package com.nexusteam.blacklogics.sdk.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.RippleDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nexusteam.blacklogics.sdk.collection.WidgetCollectionBean;
import com.nexusteam.blacklogics.sdk.collection.WidgetCollectionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Material 3 collection list — name only (no icon, no subtitle).
 */
public class SkCollectionListAdapter extends RecyclerView.Adapter<SkCollectionListAdapter.Holder> {

    public interface Listener {
        void onClick(WidgetCollectionBean item);
        void onLongClick(WidgetCollectionBean item, View anchor);
        void onDragStart(WidgetCollectionBean item, View anchor);
    }

    private final Context context;
    private final List<WidgetCollectionBean> items = new ArrayList<>();
    private Listener listener;
    private String query = "";

    public SkCollectionListAdapter(Context context) {
        this.context = context;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setFilter(String q) {
        this.query = q != null ? q.trim().toLowerCase() : "";
        reload();
    }

    public void reload() {
        items.clear();
        ArrayList<WidgetCollectionBean> all = WidgetCollectionManager.getInstance().getAll();
        if (query == null || query.isEmpty()) {
            items.addAll(all);
        } else {
            for (WidgetCollectionBean b : all) {
                if (b.name != null && b.name.toLowerCase().contains(query)) {
                    items.add(b);
                }
            }
        }
        notifyDataSetChanged();
    }

    private int resolveColor(int attr, int fallback) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(attr, tv, true)) {
            if (tv.resourceId != 0) {
                return context.getResources().getColor(tv.resourceId);
            }
            return tv.data;
        }
        return fallback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        float d = context.getResources().getDisplayMetrics().density;
        int hPad = (int) (16 * d);
        int vPad = (int) (14 * d);

        TextView name = new TextView(context);
        name.setId(android.R.id.text1);
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        name.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        name.setMaxLines(1);
        name.setEllipsize(android.text.TextUtils.TruncateAt.END);
        name.setGravity(Gravity.CENTER_VERTICAL);
        name.setPadding(hPad, vPad, hPad, vPad);
        name.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        int onSurface = resolveColor(android.R.attr.textColorPrimary, 0xFF1C1B1F);
        int ripple = resolveColor(android.R.attr.colorControlHighlight, 0x33FFFFFF);
        name.setTextColor(onSurface);

        // Material 3 style ripple on transparent background
        name.setBackground(new RippleDrawable(
                ColorStateList.valueOf(ripple),
                null,
                null
        ));
        name.setClickable(true);
        name.setFocusable(true);

        return new Holder(name);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final WidgetCollectionBean item = items.get(position);
        holder.name.setText(item.name != null ? item.name : "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onClick(item);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onLongClick(item, v);
                    listener.onDragStart(item, v);
                }
                return true;
            }
        });

        // Also allow drag from touch move after long press is handled by parent
        holder.itemView.setOnTouchListener(null);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        final TextView name;

        Holder(TextView itemView) {
            super(itemView);
            this.name = itemView;
        }
    }
}
