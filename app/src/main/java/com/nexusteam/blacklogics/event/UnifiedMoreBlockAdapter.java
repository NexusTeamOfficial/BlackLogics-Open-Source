package com.nexusteam.blacklogics.event;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.editor.LogicEditorActivity;
import com.nexusteam.blacklogics.utils.EventUtils;
import java.util.List;

public class UnifiedMoreBlockAdapter extends RecyclerView.Adapter<UnifiedMoreBlockAdapter.ViewHolder> {
    
    private Context context;
    private List<UnifiedEventManager.MoreBlockInfo> functions;
    private String sc_id;
    private String activityName;
    private com.nexusteam.blacklogics.EventFragmentActivity e;
    
    public UnifiedMoreBlockAdapter(Context context, List<UnifiedEventManager.MoreBlockInfo> functions, 
                                   String sc_id, String activityName, com.nexusteam.blacklogics.EventFragmentActivity e) {
        this.context = context;
        this.functions = functions;
        this.sc_id = sc_id;
        this.activityName = activityName;
        this.e = e;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
            .inflate(R.layout.cus_item_functions, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final UnifiedEventManager.MoreBlockInfo function = functions.get(position);
        
        holder.functionName.setText(function.name);
        
        holder.linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LogicEditorActivity.class);
                intent.putExtra("id", function.name);
                intent.putExtra("event", "moreBlock");
                intent.putExtra("event_text", function.spec);
                intent.putExtra("filename", activityName);
                intent.putExtra("sc_id", sc_id);
                intent.putExtra("activityName", activityName);
                intent.putExtra("project_file", e.getFileBean());
                intent.putExtra("type", function.type);
                context.startActivity(intent);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return functions == null ? 0 : functions.size();
    }
    
    public void updateData(List<UnifiedEventManager.MoreBlockInfo> newData) {
        this.functions = newData;
        notifyDataSetChanged();
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linear1;
        TextView functionName;
        
        public ViewHolder(View v) {
            super(v);
            linear1 = v.findViewById(R.id.linear1);
            functionName = v.findViewById(R.id.functionName);
        }
    }
}