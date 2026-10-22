// CollectionWidgetsAdapter.java
package com.shapun.layouteditor.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;
import com.nexusteam.blacklogics.R;
import com.shapun.layouteditor.managers.WidgetRepository;
import com.shapun.layouteditor.models.WidgetBlueprint;
import com.shapun.layouteditor.utils.DragAndDropUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CollectionWidgetsAdapter extends RecyclerView.Adapter<CollectionWidgetsAdapter.ViewHolder> {
	
	private Context context;
	private List<WidgetBlueprint> blueprints;
	private WidgetRepository repository;
	private OnItemClickListener itemClickListener;
	private OnFavoriteClickListener favoriteClickListener;
	
	public interface OnItemClickListener {
		void onItemClick(WidgetBlueprint blueprint, int position);
	}
	
	public interface OnFavoriteClickListener {
		void onFavoriteClick(WidgetBlueprint blueprint, int position, boolean isFavorite);
	}
	
	public CollectionWidgetsAdapter(Context context, List<WidgetBlueprint> blueprints) {
		this.context = context;
		this.blueprints = blueprints != null ? blueprints : new ArrayList<WidgetBlueprint>();
		this.repository = WidgetRepository.getInstance(context);
	}
	
	public void setBlueprints(List<WidgetBlueprint> blueprints) {
		this.blueprints = blueprints != null ? blueprints : new ArrayList<WidgetBlueprint>();
		notifyDataSetChanged();
	}
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.itemClickListener = listener;
	}
	
	public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
		this.favoriteClickListener = listener;
	}
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.list_collection_widget, parent, false);
		return new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
		final WidgetBlueprint blueprint = blueprints.get(position);
		
		// Calculate dimensions
		int paddingSmall = TheBlockLogicsUtil.getDip(context, 4);
		int cornerRadius = TheBlockLogicsUtil.getDip(context, 8);
		
		// Setup background with corner radius
		GradientDrawable background = new GradientDrawable();
		background.setColor(Color.WHITE);
		background.setCornerRadius(cornerRadius);
		background.setStroke(1, Color.parseColor("#E0E0E0"));
		
		holder.lin_main.setBackground(background);
		holder.lin_main.setElevation(2f);
		holder.lin_main.setGravity(Gravity.CENTER_VERTICAL);
		holder.lin_main.setPadding(paddingSmall, paddingSmall, paddingSmall, paddingSmall);
		
		// Load thumbnail if exists
		loadThumbnail(blueprint, holder.ivThumbnail);
		
		// Set widget name
		String displayName = blueprint.getDisplayName();
		if (displayName != null && !displayName.isEmpty()) {
			holder.tvName.setText(displayName);
		} else {
			holder.tvName.setText(blueprint.getWidgetType());
		}
		
		// Set widget type and description
		String description = blueprint.getDescription();
		String type = blueprint.getWidgetType();
		
		if (description != null && !description.isEmpty()) {
			holder.tvType.setText(type + " • " + description);
		} else {
			holder.tvType.setText(type);
		}
		
	/*	// Set favorite icon
		if (blueprint.isFavorite()) {
			holder.ivFavorite.setImageResource(R.drawable.ic_star_filled);
			holder.ivFavorite.setVisibility(View.VISIBLE);
		} else {
			holder.ivFavorite.setImageResource(R.drawable.ic_star_border);
			holder.ivFavorite.setVisibility(View.GONE);
		}*/
		
		// Click listener for normal click (show attributes)
		holder.lin_main.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (itemClickListener != null) {
					itemClickListener.onItemClick(blueprint, position);
				}
			}
		});
		
		// Long click listener for drag and drop
		holder.lin_main.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// Create drag data - we'll pass the blueprint
				HashMap<String, Object> dragData = new HashMap<>();
				dragData.put("type", "collection_widget");
				dragData.put("blueprint", blueprint);
				dragData.put("widget_class", blueprint.getWidgetClass());
				dragData.put("widget_name", blueprint.getDisplayName());
				dragData.put("blueprint_id", blueprint.getBlueprintId());
				
				// Start drag and drop
				DragAndDropUtils.startDragAndDrop(
				v,
				null,
				new View.DragShadowBuilder(holder.lin_main),
				dragData,
				1
				);
				
				return true;
			}
		});
		
		// Favorite click listener
		holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean newFavorite = !blueprint.isFavorite();
				blueprint.setFavorite(newFavorite);
				
				// Update repository
				repository.toggleFavorite(blueprint.getBlueprintId());
				
				// Update icon
				/* if (newFavorite) {
holder.ivFavorite.setImageResource(R.drawable.ic_star_filled);
} else {
holder.ivFavorite.setImageResource(R.drawable.ic_star_border);
}*/				
				
				// Notify listener
				if (favoriteClickListener != null) {
					favoriteClickListener.onFavoriteClick(blueprint, position, newFavorite);
				}
			}
		});
	}
	
	private void loadThumbnail(WidgetBlueprint blueprint, ImageView imageView) {
		String thumbnailPath = blueprint.getThumbnailPath();
		
		if (thumbnailPath != null && !thumbnailPath.isEmpty()) {
			File thumbnailFile = new File(thumbnailPath);
			if (thumbnailFile.exists()) {
				try {
					Bitmap bitmap = BitmapFactory.decodeFile(thumbnailPath);
					if (bitmap != null) {
						imageView.setImageBitmap(bitmap);
						imageView.setVisibility(View.VISIBLE);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		// Default thumbnail based on widget type
		setDefaultThumbnail(blueprint.getWidgetType(), imageView);
	}
	
	private void setDefaultThumbnail(String widgetType, ImageView imageView) {
		int iconRes = R.drawable.default_image; // Default icon
		/* 
if (widgetType != null) {
String lowerType = widgetType.toLowerCase();
if (lowerType.contains("button")) {
iconRes = R.drawable.ic_button;
} else if (lowerType.contains("textview") || lowerType.contains("text")) {
iconRes = R.drawable.ic_textview;
} else if (lowerType.contains("image")) {
iconRes = R.drawable.ic_imageview;
} else if (lowerType.contains("edittext")) {
iconRes = R.drawable.ic_edittext;
} else if (lowerType.contains("checkbox")) {
iconRes = R.drawable.ic_checkbox;
} else if (lowerType.contains("radio")) {
iconRes = R.drawable.ic_radio;
} else if (lowerType.contains("switch")) {
iconRes = R.drawable.ic_switch;
} else if (lowerType.contains("seekbar")) {
iconRes = R.drawable.ic_seekbar;
} else if (lowerType.contains("progress")) {
iconRes = R.drawable.ic_progress;
} else if (lowerType.contains("layout") || lowerType.contains("container")) {
iconRes = R.drawable.ic_layout;
}
}*/		
		
		imageView.setImageResource(iconRes);
		imageView.setVisibility(View.VISIBLE);
	}
	
	@Override
	public int getItemCount() {
		return blueprints.size();
	}
	
	public static class ViewHolder extends RecyclerView.ViewHolder {
		final LinearLayout lin_main;
		final ImageView ivThumbnail;
		final TextView tvName;
		final TextView tvType;
		final ImageView ivFavorite;
		
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			lin_main = itemView.findViewById(R.id.lin_main);
			ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
			tvName = itemView.findViewById(R.id.tv_name);
			tvType = itemView.findViewById(R.id.tv_type);
			ivFavorite = itemView.findViewById(R.id.iv_favorite);
		}
	}
}
