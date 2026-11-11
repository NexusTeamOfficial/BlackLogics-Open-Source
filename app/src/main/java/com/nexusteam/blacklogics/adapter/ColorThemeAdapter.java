package com.nexusteam.blacklogics.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.model.ColorTheme;

import java.util.List;

public class ColorThemeAdapter extends RecyclerView.Adapter<ColorThemeAdapter.ViewHolder> {

    public interface OnThemeSelectedListener {
        void onThemeSelected(ColorTheme theme, int position);
    }

    private Context context;
    private List<ColorTheme> items;
    private int selectedPosition = 0;
    private OnThemeSelectedListener listener;

    public ColorThemeAdapter(Context context, List<ColorTheme> items) {
        this.context = context;
        this.items = items;
    }

    public void setOnThemeSelectedListener(OnThemeSelectedListener listener) {
        this.listener = listener;
    }

    public void setSelectedPosition(int position) {
        int old = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(old);
        notifyItemChanged(selectedPosition);
    }

    public ColorTheme getSelected() {
        if (items == null || items.isEmpty()) return null;
        return items.get(selectedPosition);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_color_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ColorTheme theme = items.get(position);

        holder.tvName.setText(theme.getName());
        setCircleColor(holder.colorPrimary, theme.getPrimary());
        setCircleColor(holder.colorPrimaryDark, theme.getPrimaryDark());
        setCircleColor(holder.colorAccent, theme.getAccent());
        setCircleColor(holder.colorHighlight, theme.getHighlight());

        boolean selected = (position == selectedPosition);
        holder.ivSelected.setVisibility(selected ? View.VISIBLE : View.GONE);

        if (selected) {
            holder.card.setStrokeColor(theme.getPrimary());
            holder.card.setStrokeWidth(dpToPx(2.5f));
        } else {
            holder.card.setStrokeColor(context.getResources().getColor(R.color.colorOutlineVariant));
            holder.card.setStrokeWidth(dpToPx(1.5f));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int old = selectedPosition;
                selectedPosition = position;
                notifyItemChanged(old);
                notifyItemChanged(selectedPosition);
                if (listener != null) {
                    listener.onThemeSelected(theme, position);
                }
            }
        });
    }

    private void setCircleColor(View view, int color) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(color);
        view.setBackground(gd);
    }

    private int dpToPx(float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView card;
        View colorPrimary;
        View colorPrimaryDark;
        View colorAccent;
        View colorHighlight;
        TextView tvName;
        ImageView ivSelected;

        ViewHolder(View itemView) {
            super(itemView);
            card = (MaterialCardView) itemView.findViewById(R.id.card_color);
            colorPrimary = itemView.findViewById(R.id.color_primary);
            colorPrimaryDark = itemView.findViewById(R.id.color_primary_dark);
            colorAccent = itemView.findViewById(R.id.color_accent);
            colorHighlight = itemView.findViewById(R.id.color_highlight);
            tvName = (TextView) itemView.findViewById(R.id.tv_theme_name);
            ivSelected = (ImageView) itemView.findViewById(R.id.iv_selected);
        }
    }
}
