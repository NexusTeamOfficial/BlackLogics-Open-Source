package com.nexusteam.blacklogics.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nexusteam.blacklogics.callback.OnWidgetDragListener;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class WidgetPaletteAdapter
        extends RecyclerView.Adapter<WidgetPaletteAdapter.WidgetViewHolder> {

    private final Context context;
    private final ArrayList<HashMap<String, Object>> widgetList;
    private final OnWidgetDragListener dragListener;

    private final int padding;

    public WidgetPaletteAdapter(
            Context context,
            ArrayList<HashMap<String, Object>> widgetList,
            OnWidgetDragListener dragListener
    ) {
        this.context = context;
        this.widgetList = widgetList;
        this.dragListener = dragListener;
        this.padding = TheBlockLogicsUtil.getDip(context, 2);
    }

    @NonNull
    @Override
    public WidgetViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_widget, parent, false);
        return new WidgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull final WidgetViewHolder holder,
            final int position
    ) {

        final HashMap<String, Object> item = widgetList.get(position);

        holder.container.setGravity(Gravity.CENTER_VERTICAL);
        holder.container.setPadding(padding, padding, padding, padding);
        holder.container.setElevation(2f);

        holder.title.setText(
                item.containsKey("name")
                        ? String.valueOf(item.get("name"))
                        : "LinearLayout"
        );
        holder.title.setTextColor(Color.parseColor("#555555"));
        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        holder.title.setSingleLine(true);

        if (item.containsKey("icon")) {
            int resId = context.getResources().getIdentifier(
                    String.valueOf(item.get("icon")),
                    "drawable",
                    context.getPackageName()
            );
            if (resId != 0) {
                holder.icon.setImageResource(resId);
                holder.icon.setVisibility(View.VISIBLE);
            } else {
                holder.icon.setVisibility(View.GONE);
            }
        } else {
            holder.icon.setVisibility(View.GONE);
        }

        holder.container.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (dragListener != null) {
                            dragListener.onWidgetLongPressed(
                                    holder.container,
                                    item,
                                    position
                            );
                        }
                        return true;
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return widgetList.size();
    }

    static class WidgetViewHolder extends RecyclerView.ViewHolder {

        final LinearLayout container;
        final TextView title;
        final ImageView icon;

        WidgetViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.lin_main);
            title = itemView.findViewById(R.id.title);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}
