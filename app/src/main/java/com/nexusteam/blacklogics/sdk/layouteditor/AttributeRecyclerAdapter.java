
package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nexusteam.blacklogics.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttributeRecyclerAdapter
extends RecyclerView.Adapter<AttributeRecyclerAdapter.ViewHolder> {
	
	
	private List<HashMap<String, Object>> data;
	private final Context context;
	private OnItemClickListener listener;
	
	
	public interface OnItemClickListener {
		void onItemClick(int position);
	}
	
	
	public AttributeRecyclerAdapter(Context context,
	ArrayList<HashMap<String, Object>> data) {
		this.context = context;
		this.data    = new ArrayList<>(data);
		setHasStableIds(false);
	}
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
	}
	
	
	public void updateData(final List<HashMap<String, Object>> newData) {
		final List<HashMap<String, Object>> oldData = this.data;
		final List<HashMap<String, Object>> newCopy = new ArrayList<>(newData);
		
		DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
			
			@Override
			public int getOldListSize() {
				return oldData.size();
			}
			
			@Override
			public int getNewListSize() {
				return newCopy.size();
			}
			
			@Override
			public boolean areItemsTheSame(int oldPos, int newPos) {
				Object oldName = oldData.get(oldPos).get("attribute_name");
				Object newName = newCopy.get(newPos).get("attribute_name");
				if (oldName == null || newName == null) return false;
				return oldName.equals(newName);
			}
			
			@Override
			public boolean areContentsTheSame(int oldPos, int newPos) {
				HashMap<String, Object> oldItem = oldData.get(oldPos);
				HashMap<String, Object> newItem = newCopy.get(newPos);
				
				Object oldName = oldItem.get("name");
				Object newName = newItem.get("name");
				if (oldName == null ? newName != null : !oldName.equals(newName)) return false;
				
				Object oldType = oldItem.get("argument_type");
				Object newType = newItem.get("argument_type");
				return oldType == null ? newType == null : oldType.equals(newType);
			}
		});
		
		this.data = newCopy;
		diffResult.dispatchUpdatesTo(this);
	}
	
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context)
		.inflate(R.layout.attribute_view, parent, false);
		return new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
		HashMap<String, Object> item = data.get(position);
		
		String attributeName = localizeAttributeName(item);
		String argumentType  = item.containsKey("argument_type")
		? item.get("argument_type").toString() : "";
		
		holder.tvName.setText(attributeName);
		
		Object rawName = item.get("name");
		String iconKey = rawName != null ? rawName.toString() : attributeName;
		bindIcon(holder.ivIcon, iconKey, argumentType);
		
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					int adapterPos = holder.getBindingAdapterPosition();
					if (adapterPos != RecyclerView.NO_POSITION) {
						listener.onItemClick(adapterPos);
					}
				}
			}
		});
	}
	
	private String localizeAttributeName(HashMap<String, Object> item) {
		if (item == null) return "";
		
		Object raw = item.get("name");
		if (raw == null) return "";
		
		String englishName = raw.toString().trim();
		if (englishName.isEmpty()) return "";
		
		String key = "attr_" + englishName
				.toLowerCase()
				.replace(" ", "_")
				.replace("-", "_")
				.replace(":", "_")
				.replace(".", "_");
		
		int resId = context.getResources().getIdentifier(key, "string", context.getPackageName());
		if (resId != 0) {
			try {
				return context.getString(resId);
			} catch (Exception ignored) {
			}
		}
		
		return englishName;
	}
	
	private void bindIcon(ImageView ivIcon, String attributeName, String argumentType) {
		if (ivIcon == null) return;
		String n = attributeName == null ? "" : attributeName.toLowerCase();
		String t = argumentType == null ? "" : argumentType.toLowerCase();

		int resId = resolveAttrIcon(n, t);
		ivIcon.setVisibility(View.VISIBLE);
		try {
			ivIcon.setImageResource(resId);
		} catch (Exception e) {
			try {
				ivIcon.setImageResource(R.drawable.ic_attr_generic);
			} catch (Exception ignored) {
				ivIcon.setVisibility(View.GONE);
			}
		}
	}

	private int resolveAttrIcon(String n, String t) {
		if (n.contains("width") && !n.contains("min")) return safeRes("ic_attr_width", "width_96");
		if (n.contains("height") && !n.contains("min")) return safeRes("ic_attr_height", "height_96");
		if (n.contains("min width") || n.contains("minheight") || n.contains("min height") || n.contains("minwidth"))
			return safeRes("ic_attr_size", "ic_attr_generic");

		if (n.contains("text size") || n.contains("textsize") || n.equals("text size"))
			return safeRes("ic_attr_size", "abc_96");
		if (n.contains("text color") || n.contains("hint color") || n.contains("textcolor"))
			return safeRes("ic_attr_color", "color_palette_48");
		if (n.contains("hint") && !n.contains("color"))
			return safeRes("ic_attr_hint", "abc_96");
		if (n.contains("text") || n.contains("title") || n.contains("all caps") || n.contains("letter spacing")
				|| n.contains("ellipsize") || n.contains("single line"))
			return safeRes("ic_attr_text", "abc_96");
		if (n.contains("lines") || n.contains("max lines") || n.contains("min lines"))
			return safeRes("ic_attr_lines", "abc_96");

		if (n.contains("gravity")) return safeRes("ic_attr_gravity", "gravity_96");
		if (n.contains("orientation")) return safeRes("ic_attr_orientation", "ic_attr_generic");
		if (n.contains("weight")) return safeRes("ic_attr_weight", "one_to_many_48");
		if (n.contains("padding")) return safeRes("ic_attr_padding", "ic_attr_generic");
		if (n.contains("margin")) return safeRes("ic_attr_margin", "ic_attr_generic");

		if (n.contains("translation") || n.contains("transition"))
			return safeRes("ic_attr_translate", "swipe_right_48");
		if (n.contains("scale")) return safeRes("ic_attr_scale", "ic_attr_generic");
		if (n.contains("rotation") || n.contains("rotate"))
			return safeRes("ic_attr_rotation", "ic_attr_generic");
		if (n.contains("elevation")) return safeRes("ic_attr_elevation", "ic_attr_generic");
		if (n.contains("alpha")) return safeRes("ic_attr_alpha", "ic_attr_generic");

		if (t.equals("color") || n.contains("color") || n.contains("background") || n.contains("tint"))
			return safeRes("ic_attr_color", "color_palette_48");

		if (n.contains("image") || n.contains("src") || n.contains("drawable") || n.contains("scale type")
				|| n.contains("adjust view"))
			return safeRes("ic_attr_image", "ic_attr_generic");

		if (n.contains("visibility") || n.contains("visible"))
			return safeRes("ic_attr_visibility", "ic_attr_generic");
		if (n.contains("enabled") || n.contains("enable"))
			return safeRes("ic_attr_enabled", "ic_attr_generic");
		if (n.contains("clickable") || n.contains("focusable") || n.contains("select all"))
			return safeRes("ic_attr_clickable", "ic_attr_generic");
		if (n.contains("checked") || n.contains("check"))
			return safeRes("ic_attr_checked", "ic_attr_generic");

		if (n.contains("convert") || n.contains("custom view") || n.contains("customview"))
			return safeRes("ic_attr_convert", "ic_attr_generic");

		if (t.equals("string")) return safeRes("ic_attr_text", "abc_96");
		if (t.equals("size")) return safeRes("ic_attr_size", "ic_attr_generic");
		if (t.equals("boolean")) return safeRes("ic_attr_checked", "ic_attr_generic");
		if (t.equals("float") || t.equals("int")) return safeRes("ic_attr_size", "ic_attr_generic");
		if (t.equals("flag") || t.equals("enum")) return safeRes("ic_attr_gravity", "gravity_96");

		return safeRes("ic_attr_generic", "ic_attr_generic");
	}

	private int safeRes(String primary, String fallback) {
		int id = context.getResources().getIdentifier(primary, "drawable", context.getPackageName());
		if (id != 0) return id;
		id = context.getResources().getIdentifier(fallback, "drawable", context.getPackageName());
		if (id != 0) return id;
		return android.R.drawable.ic_menu_edit;
	}
	
	@Override
	public int getItemCount() {
		return data.size();
	}
	
	
	public static class ViewHolder extends RecyclerView.ViewHolder {
		final TextView  tvName;
		final ImageView ivIcon;
		
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			tvName = itemView.findViewById(R.id.tv_name);
			ivIcon = itemView.findViewById(R.id.iv_icon);
		}
	}
}
