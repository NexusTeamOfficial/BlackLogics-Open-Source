package com.nexusteam.blacklogics.sdk.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.sdk.managers.WidgetRepository;
import com.nexusteam.blacklogics.sdk.models.WidgetBlueprint;
import com.nexusteam.blacklogics.sdk.utils.DragAndDropUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionWidgetsAdapter extends RecyclerView.Adapter<CollectionWidgetsAdapter.ViewHolder> {

	private static final int THUMB_SIZE_PX = 128;

	private static final Map<String, String> ICON_BY_CLASS = new HashMap<String, String>();
	private static final Map<String, String> ICON_BY_NAME = new HashMap<String, String>();

	private static void register(String name, String classPath, String icon) {
		ICON_BY_NAME.put(name, icon);
		ICON_BY_CLASS.put(classPath, icon);
	}

	static {
		register("LinearLayout", "android.widget.LinearLayout", "ic_palette_linear_layout_horz");
		register("RelativeLayout", "android.widget.RelativeLayout", "ic_palette_relative_layout");
		register("FrameLayout", "android.widget.FrameLayout", "ic_palette_frame_layout");
		register("GridLayout", "android.widget.GridLayout", "ic_palette_grid_layout");
		register("TableLayout", "android.widget.TableLayout", "ic_palette_table_layout");
		register("ConstraintLayout", "androidx.constraintlayout.widget.ConstraintLayout", "ic_palette_constraint_layout");
		register("MotionLayout", "androidx.constraintlayout.motion.widget.MotionLayout", "icon_auto_awesome_motion_round");
		register("CoordinatorLayout", "androidx.coordinatorlayout.widget.CoordinatorLayout", "ic_palette_coordinator_layout");
		register("TextView", "android.widget.TextView", "ic_palette_text_view");
		register("Button", "android.widget.Button", "ic_palette_button");
		register("EditText", "android.widget.EditText", "ic_palette_edit_text");
		register("ImageView", "android.widget.ImageView", "ic_palette_image_view");
		register("ImageButton", "android.widget.ImageButton", "ic_palette_image_button");
		register("CheckBox", "android.widget.CheckBox", "ic_palette_check_box");
		register("RadioButton", "android.widget.RadioButton", "ic_palette_radio_button");
		register("ToggleButton", "android.widget.ToggleButton", "ic_palette_toggle_button");
		register("Switch", "android.widget.Switch", "ic_palette_switch");
		register("SeekBar", "android.widget.SeekBar", "ic_palette_seek_bar");
		register("ProgressBar", "android.widget.ProgressBar", "ic_palette_progress_bar");
		register("RatingBar", "android.widget.RatingBar", "ic_palette_rating_bar");
		register("Spinner", "android.widget.Spinner", "ic_palette_spinner");
		register("ListView", "android.widget.ListView", "ic_palette_list_view");
		register("GridView", "android.widget.GridView", "ic_palette_grid_view");
		register("ExpandableListView", "android.widget.ExpandableListView", "ic_palette_list_view");
		register("WebView", "android.webkit.WebView", "ic_palette_web_view");
		register("WebView2", "com.nexusteam.internal.os.layouteditor.widget.WidgetWebView", "ic_palette_web_view");
		register("VideoView", "android.widget.VideoView", "ic_palette_video_view");
		register("ScrollView", "android.widget.ScrollView", "ic_palette_scroll_view");
		register("HorizontalScrollView", "android.widget.HorizontalScrollView", "ic_palette_horizontal_scroll_view");
		register("NestedScrollView", "androidx.core.widget.NestedScrollView", "ic_palette_nested_scroll_view");
		register("RecyclerView", "androidx.recyclerview.widget.RecyclerView", "ic_palette_recycler_view");
		register("ViewPager", "androidx.viewpager.widget.ViewPager", "ic_palette_view_pager");
		register("ViewPager2", "androidx.viewpager2.widget.ViewPager2", "ic_palette_view_pager");
		register("CardView", "androidx.cardview.widget.CardView", "ic_palette_card_view");
		register("CollapsingToolbarLayout", "com.google.android.material.appbar.CollapsingToolbarLayout", "ic_palette_collapsing_toolbar_layout");
		register("MaterialButton", "com.google.android.material.button.MaterialButton", "ic_palette_button");
		register("TextInputLayout", "com.google.android.material.textfield.TextInputLayout", "ic_palette_text_input_layout");
		register("BottomNavigationView", "com.google.android.material.bottomnavigation.BottomNavigationView", "ic_palette_bottom_navigation_view");
		register("NavigationView", "com.google.android.material.navigation.NavigationView", "ic_palette_navigation_view");
		register("TabLayout", "com.google.android.material.tabs.TabLayout", "ic_palette_tab_layout");
		register("Chip", "com.google.android.material.chip.Chip", "ic_palette_chip");
		register("ChipGroup", "com.google.android.material.chip.ChipGroup", "ic_palette_chip_group");
		register("MaterialCardView", "com.google.android.material.card.MaterialCardView", "ic_palette_card_view");
		register("MaterialSwitch", "com.google.android.material.switchmaterial.SwitchMaterial", "ic_palette_switch");
		register("LottieAnimationView", "com.airbnb.lottie.LottieAnimationView", "icon_animation_round");
		register("CircleImageView", "de.hdodenhof.circleimageview.CircleImageView", "ic_palette_image_view");
	}

	private final Context context;
	private final LayoutInflater inflater;
	private final WidgetRepository repository;
	private final Map<String, Integer> iconResCache = new HashMap<String, Integer>();

	private List<WidgetBlueprint> blueprints;
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
		this.inflater = LayoutInflater.from(context);
		this.repository = WidgetRepository.getInstance(context);
		this.blueprints = blueprints != null ? blueprints : new ArrayList<WidgetBlueprint>();
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
		View view = inflater.inflate(R.layout.list_collection_widget, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
		final WidgetBlueprint blueprint = blueprints.get(position);

		String displayName = blueprint.getDisplayName();
		if (displayName != null && displayName.length() > 0) {
			holder.tvName.setText(displayName);
		} else {
			holder.tvName.setText(blueprint.getWidgetType());
		}

		String type = blueprint.getWidgetType();
		String description = blueprint.getDescription();
		if (description != null && description.length() > 0) {
			holder.tvType.setText(type + " \u2022 " + description);
		} else {
			holder.tvType.setText(type);
		}

		bindThumbnail(blueprint, holder.ivThumbnail);

		holder.ivFavorite.setVisibility(View.GONE);

		holder.lin_main.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int pos = holder.getAdapterPosition();
				if (pos == RecyclerView.NO_POSITION) return;
				if (itemClickListener != null) {
					itemClickListener.onItemClick(blueprints.get(pos), pos);
				}
			}
		});

		holder.lin_main.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				int pos = holder.getAdapterPosition();
				if (pos == RecyclerView.NO_POSITION) return false;
				WidgetBlueprint bp = blueprints.get(pos);

				HashMap<String, Object> dragData = new HashMap<String, Object>();
				dragData.put("type", "collection_widget");
				dragData.put("blueprint", bp);
				dragData.put("widget_class", bp.getWidgetClass());
				dragData.put("widget_name", bp.getDisplayName());
				dragData.put("blueprint_id", bp.getBlueprintId());

				DragAndDropUtils.startDragAndDrop(
						v,
						null,
						new View.DragShadowBuilder(holder.lin_main),
						dragData,
						1);
				return true;
			}
		});

		holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int pos = holder.getAdapterPosition();
				if (pos == RecyclerView.NO_POSITION) return;
				WidgetBlueprint bp = blueprints.get(pos);

				boolean newFavorite = !bp.isFavorite();
				bp.setFavorite(newFavorite);
				repository.toggleFavorite(bp.getBlueprintId());

				if (favoriteClickListener != null) {
					favoriteClickListener.onFavoriteClick(bp, pos, newFavorite);
				}
			}
		});
	}


	private void bindThumbnail(WidgetBlueprint blueprint, ImageView imageView) {
		String path = blueprint.getThumbnailPath();

		if (path != null && path.length() > 0) {
			File file = new File(path);
			if (file.exists()) {
				Bitmap bitmap = decodeSampled(path, THUMB_SIZE_PX, THUMB_SIZE_PX);
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
					return;
				}
			}
		}

		imageView.setImageResource(resolveIconRes(blueprint));
	}

	private int resolveIconRes(WidgetBlueprint blueprint) {
		String iconName = null;

		String widgetClass = blueprint.getWidgetClass();
		if (widgetClass != null) {
			iconName = ICON_BY_CLASS.get(widgetClass);
		}

		if (iconName == null) {
			String widgetType = blueprint.getWidgetType();
			if (widgetType != null) {
				iconName = ICON_BY_NAME.get(widgetType);
			}
		}

		if (iconName == null) {
			return R.drawable.default_image;
		}

		Integer cached = iconResCache.get(iconName);
		if (cached != null) {
			return cached.intValue();
		}

		int resId = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
		if (resId == 0) {
			resId = R.drawable.default_image;
		}
		iconResCache.put(iconName, Integer.valueOf(resId));
		return resId;
	}

	private static Bitmap decodeSampled(String path, int reqWidth, int reqHeight) {
		try {
			BitmapFactory.Options bounds = new BitmapFactory.Options();
			bounds.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, bounds);

			int sample = 1;
			int width = bounds.outWidth;
			int height = bounds.outHeight;
			if (width <= 0 || height <= 0) return null;

			while ((width / (sample * 2)) >= reqWidth && (height / (sample * 2)) >= reqHeight) {
				sample *= 2;
			}

			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = sample;
			return BitmapFactory.decodeFile(path, opts);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} catch (OutOfMemoryError oom) {
			return null;
		}
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
			lin_main = (LinearLayout) itemView.findViewById(R.id.lin_main);
			ivThumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
			tvName = (TextView) itemView.findViewById(R.id.tv_name);
			tvType = (TextView) itemView.findViewById(R.id.tv_type);
			ivFavorite = (ImageView) itemView.findViewById(R.id.iv_favorite);
		}
	}
}