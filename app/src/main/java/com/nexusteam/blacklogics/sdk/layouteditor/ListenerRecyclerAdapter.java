package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nexusteam.blacklogics.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListenerRecyclerAdapter
extends RecyclerView.Adapter<ListenerRecyclerAdapter.ViewHolder> {

	private final ArrayList<HashMap<String, Object>> data;
	private final Context context;
	private AttributeRecyclerAdapter.OnItemClickListener listener;

	public ListenerRecyclerAdapter(Context context,
	ArrayList<HashMap<String, Object>> data) {
		this.context = context;
		this.data = data;
	}

	public void setOnItemClickListener(AttributeRecyclerAdapter.OnItemClickListener l) {
		this.listener = l;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(context)
		.inflate(R.layout.attribute_view, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
		holder.tvName.setText(data.get(position).get("name").toString());
		holder.ivIcon.setVisibility(View.GONE);
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) listener.onItemClick(position);
			}
		});
	}

	@Override
	public int getItemCount() { return data.size(); }

	public class ViewHolder extends RecyclerView.ViewHolder {
		final TextView tvName;
		final ImageView ivIcon;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			tvName = itemView.findViewById(R.id.tv_name);
			ivIcon = itemView.findViewById(R.id.iv_icon);
		}
	}
}
