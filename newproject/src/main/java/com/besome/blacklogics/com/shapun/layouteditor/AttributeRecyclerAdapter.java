package com.shapun.layouteditor;

import android.content.Context;
import com.besome.blacklogics.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;

public class AttributeRecyclerAdapter extends RecyclerView.Adapter<AttributeRecyclerAdapter.ViewHolder> {

    private ArrayList<HashMap<String, Object>> data;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public AttributeRecyclerAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
        this.context = context;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attribute_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, Object> item = data.get(position);
        String attributeName = item.get("name").toString();
        holder.tvName.setText(attributeName);

        // Set icon based on attribute type
        String argumentType = item.get("argument_type").toString();
        if (argumentType.equals("String") && attributeName.toLowerCase().contains("text")) {
            holder.ivIcon.setVisibility(View.VISIBLE);
            holder.ivIcon.setImageResource(R.drawable.abc_96);
        } else if (argumentType.equals("Size") && attributeName.toLowerCase().contains("width")) {
            holder.ivIcon.setVisibility(View.VISIBLE);
            holder.ivIcon.setImageResource(R.drawable.width_96);
        } else if (argumentType.equals("Size") && attributeName.toLowerCase().contains("height")) {
            holder.ivIcon.setVisibility(View.VISIBLE);
            holder.ivIcon.setImageResource(R.drawable.height_96);
        } else if (argumentType.equals("flag") && attributeName.toLowerCase().contains("gravity")) {
            holder.ivIcon.setVisibility(View.VISIBLE);
            holder.ivIcon.setImageResource(R.drawable.gravity_96);
        } else if (argumentType.equals("Color") && attributeName.toLowerCase().contains("Text Color")) {
            holder.ivIcon.setVisibility(View.VISIBLE);
            holder.ivIcon.setImageResource(R.drawable.color_palette_48);
        } else if (argumentType.equals("float") && attributeName.toLowerCase().contains("Translation Y")) {
            holder.ivIcon.setVisibility(View.VISIBLE);
            holder.ivIcon.setImageResource(R.drawable.swipe_down_48);
        } else if (argumentType.equals("float") && attributeName.toLowerCase().contains("Translation X")) {
            holder.ivIcon.setVisibility(View.VISIBLE);
            holder.ivIcon.setImageResource(R.drawable.swipe_right_48);
        } else if (argumentType.equals("int") && attributeName.toLowerCase().contains("Weight")) {
            holder.ivIcon.setVisibility(View.VISIBLE);
            holder.ivIcon.setImageResource(R.drawable.one_to_many_48);
        } else {
            holder.ivIcon.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}