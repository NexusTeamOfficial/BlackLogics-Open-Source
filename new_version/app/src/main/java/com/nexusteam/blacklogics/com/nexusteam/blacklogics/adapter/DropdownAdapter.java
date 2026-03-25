package com.besome.blacklogics.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.nexusteam.blacklogics.R;

public class DropdownAdapter extends RecyclerView.Adapter<DropdownAdapter.ViewHolder> {
    private List<String> items;
    private OnItemSelectedCallback callback;
    
    public interface OnItemSelectedCallback {
        void onItemSelected(String item);
    }
    
    public DropdownAdapter(List<String> items, OnItemSelectedCallback callback) {
        this.items = items;
        this.callback = callback;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.dropdown_item, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String item = items.get(position);
        holder.textView.setText(item);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                callback.onItemSelected(item);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return items.size();
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        
        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview2);
        }
    }
}
