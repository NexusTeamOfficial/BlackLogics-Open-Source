package com.nexusteam.blacklogics.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.model.ProjectTemplate;

import java.util.List;

public class ProjectTemplateAdapter extends RecyclerView.Adapter<ProjectTemplateAdapter.ViewHolder> {

    public interface OnTemplateSelectedListener {
        void onTemplateSelected(ProjectTemplate template, int position);
    }

    private Context context;
    private List<ProjectTemplate> items;
    private int selectedPosition = 0;
    private OnTemplateSelectedListener listener;

    public ProjectTemplateAdapter(Context context, List<ProjectTemplate> items) {
        this.context = context;
        this.items = items;
    }

    public void setOnTemplateSelectedListener(OnTemplateSelectedListener listener) {
        this.listener = listener;
    }

    public void setSelectedPosition(int position) {
        int old = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(old);
        notifyItemChanged(selectedPosition);
    }

    public ProjectTemplate getSelected() {
        if (items == null || items.isEmpty()) return null;
        return items.get(selectedPosition);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ProjectTemplate item = items.get(position);
        holder.tvName.setText(item.getName());
        holder.tvDesc.setText(item.getDescription());
        holder.ivPreview.setImageResource(item.getPreviewDrawable());

        boolean selected = (position == selectedPosition);
        holder.ivSelected.setVisibility(selected ? View.VISIBLE : View.GONE);

        if (selected) {
            holder.card.setStrokeColor(context.getResources().getColor(R.color.colorPrimary));
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
                    listener.onTemplateSelected(item, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    private int dpToPx(float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView card;
        ImageView ivPreview;
        ImageView ivSelected;
        TextView tvName;
        TextView tvDesc;

        ViewHolder(View itemView) {
            super(itemView);
            card = (MaterialCardView) itemView.findViewById(R.id.card_template);
            ivPreview = (ImageView) itemView.findViewById(R.id.iv_template_preview);
            ivSelected = (ImageView) itemView.findViewById(R.id.iv_selected);
            tvName = (TextView) itemView.findViewById(R.id.tv_template_name);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_template_desc);
        }
    }
}
