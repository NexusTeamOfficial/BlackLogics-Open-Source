package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.sdk.utils.DragAndDropUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class WidgetPaletteAdapter
extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	public interface UnselectCallback {
		void unselectSelectedWidget();
	}

	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM = 1;

	private static final String PAYLOAD_HEADER_STATE = "header_state";
	private static final String PREFS = "widget_palette_prefs";
	private static final String KEY_COLLAPSED = "collapsed_sections";

	private static final boolean TINT_ICONS = true;

	private static final Pattern CAMEL_BOUNDARY = Pattern.compile("(?<=[a-z0-9])(?=[A-Z])");

	private static final int DARK_ITEM_FILL      = 0xFF2A2A3D;
	private static final int DARK_ITEM_FILL_ALT  = 0xFF32324A;
	private static final int DARK_TEXT           = 0xFFE8E8F0;
	private static final int DARK_TEXT_MUTED     = 0xFF9A9AB0;
	private static final int DARK_RIPPLE         = 0x44FFFFFF;
	private static final int DARK_BADGE          = 0xFF3D3D55;

	private final Context context;
	private final ArrayList<HashMap<String, Object>> data;
	private final EditorUiHelpers uiHelpers;
	private final UnselectCallback unselectCallback;

	private final ArrayList<HashMap<String, Object>> rows =
	new ArrayList<HashMap<String, Object>>();
	private final HashMap<String, Integer> sectionCounts = new HashMap<String, Integer>();
	private final HashSet<String> collapsed = new HashSet<String>();
	private final HashMap<String, Integer> iconCache = new HashMap<String, Integer>();

	private final boolean isDarkMode;
	private final int colorText;
	private final int colorTextMuted;
	private final int colorItemFill;
	private final int colorRipple;
	private final int colorBadge;
	private final float cornerRadiusPx;

	private boolean tapHintShown = false;

	public WidgetPaletteAdapter(Context context,
	ArrayList<HashMap<String, Object>> data,
	EditorUiHelpers uiHelpers,
	UnselectCallback unselectCallback) {
		this.context = context;
		this.data = data;
		this.uiHelpers = uiHelpers;
		this.unselectCallback = unselectCallback;

		int nightMode = context.getResources().getConfiguration().uiMode
				& Configuration.UI_MODE_NIGHT_MASK;
		this.isDarkMode = (nightMode == Configuration.UI_MODE_NIGHT_YES);

		if (isDarkMode) {
			this.colorText      = DARK_TEXT;
			this.colorTextMuted = DARK_TEXT_MUTED;
			this.colorItemFill  = DARK_ITEM_FILL;
			this.colorRipple    = DARK_RIPPLE;
			this.colorBadge     = DARK_BADGE;
		} else {
			int textPrimary = uiHelpers.resolveThemeColor(
					android.R.attr.textColorPrimary, 0xFF212121);
			int textSecondary = uiHelpers.resolveThemeColor(
					android.R.attr.textColorSecondary, 0xFF757575);
			this.colorText      = textPrimary;
			this.colorTextMuted = textSecondary;
			this.colorItemFill  = withAlpha(textPrimary, 0x14);
			this.colorRipple    = withAlpha(textPrimary, 0x33);
			this.colorBadge     = withAlpha(textPrimary, 0x26);
		}

		this.cornerRadiusPx = dp(10);

		loadCollapsed();
		rebuildRows();
	}


	public void refresh() {
		rebuildRows();
		notifyDataSetChanged();
	}

	public boolean isDarkMode() {
		return isDarkMode;
	}


	@Override
	public int getItemViewType(int position) {
		return isHeader(rows.get(position)) ? TYPE_HEADER : TYPE_ITEM;
	}

	@Override
	public int getItemCount() { return rows.size(); }

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(context);

		if (viewType == TYPE_HEADER) {
			View v = inflater.inflate(R.layout.item_palette_header, parent, false);
			final HeaderHolder holder = new HeaderHolder(v);
			holder.title.setTextColor(colorTextMuted);
			holder.count.setTextColor(colorTextMuted);
			holder.chevron.setColorFilter(colorTextMuted, PorterDuff.Mode.SRC_IN);
			v.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int pos = holder.getAdapterPosition();
					if (pos == RecyclerView.NO_POSITION || pos >= rows.size()) return;
					if (!isHeader(rows.get(pos))) return;
					toggleSection(pos);
				}
			});
			return holder;
		}

		View v = inflater.inflate(R.layout.item_palette_widget, parent, false);
		final ItemHolder holder = new ItemHolder(v);

		GradientDrawable fill = new GradientDrawable();
		fill.setColor(colorItemFill);
		fill.setCornerRadius(cornerRadiusPx);

		if (isDarkMode) {
			fill.setStroke(dp(1), 0x22FFFFFF);
		}

		GradientDrawable mask = new GradientDrawable();
		mask.setColor(Color.BLACK);
		mask.setCornerRadius(cornerRadiusPx);

		holder.linMain.setBackground(new RippleDrawable(
				ColorStateList.valueOf(colorRipple), fill, mask));

		holder.tvName.setTextColor(colorText);

		GradientDrawable badge = new GradientDrawable();
		badge.setShape(GradientDrawable.OVAL);
		badge.setColor(colorBadge);
		holder.tvIconFallback.setBackground(badge);
		holder.tvIconFallback.setTextColor(colorText);

		if (TINT_ICONS) {
			holder.ivIcon.setColorFilter(colorTextMuted, PorterDuff.Mode.SRC_IN);
		}

		holder.linMain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (tapHintShown) return;
				tapHintShown = true;
				Toast.makeText(context,
						"Widget ko canvas par drag karne ke liye long-press karo",
						Toast.LENGTH_SHORT).show();
			}
		});
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		HashMap<String, Object> row = rows.get(position);
		if (holder instanceof HeaderHolder) {
			HeaderHolder h = (HeaderHolder) holder;
			h.title.setText(localizeViewName(nameOf(row, "")));
			bindHeaderState(h, row, false);
		} else {
			bindItem((ItemHolder) holder, row);
		}
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position,
	@NonNull List<Object> payloads) {
		if (!payloads.isEmpty() && holder instanceof HeaderHolder) {
			bindHeaderState((HeaderHolder) holder, rows.get(position), true);
			return;
		}
		super.onBindViewHolder(holder, position, payloads);
	}


	private void bindHeaderState(HeaderHolder h, HashMap<String, Object> header, boolean animate) {
		String name = nameOf(header, "");
		boolean isCollapsed = collapsed.contains(name);
		float target = isCollapsed ? -90f : 0f;

		h.chevron.animate().cancel();
		if (animate) {
			h.chevron.animate().rotation(target).setDuration(150).start();
		} else {
			h.chevron.setRotation(target);
		}

		Integer c = sectionCounts.get(name);
		if (isCollapsed && c != null) {
			h.count.setText(String.valueOf(c));
			h.count.setVisibility(View.VISIBLE);
		} else {
			h.count.setVisibility(View.GONE);
		}
		h.itemView.setContentDescription(name + (isCollapsed ? ", collapsed" : ", expanded"));
	}

	private void bindItem(final ItemHolder h, final HashMap<String, Object> item) {
		String name = nameOf(item, "LinearLayout");
String display = localizeViewName(name);
h.tvName.setText(CAMEL_BOUNDARY.matcher(display).replaceAll("\u200B"));
h.linMain.setContentDescription(display);

		h.tvName.setTextColor(colorText);
		if (TINT_ICONS) {
			h.ivIcon.setColorFilter(colorTextMuted, PorterDuff.Mode.SRC_IN);
		}

		int resId = resolveIcon(item);
		if (resId != 0) {
			h.ivIcon.setImageResource(resId);
			h.ivIcon.setVisibility(View.VISIBLE);
			h.tvIconFallback.setVisibility(View.GONE);
		} else {
			h.ivIcon.setVisibility(View.GONE);
			h.tvIconFallback.setText(name.length() > 0
					? name.substring(0, 1).toUpperCase() : "?");
			h.tvIconFallback.setVisibility(View.VISIBLE);
			h.tvIconFallback.setTextColor(colorText);
		}

		h.linMain.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				unselectCallback.unselectSelectedWidget();
				DragAndDropUtils.startDragAndDrop(
						v, null,
						new View.DragShadowBuilder(h.linMain),
						item, 1);
				return true;
			}
		});
	}

	private int resolveIcon(HashMap<String, Object> item) {
		Object icon = item.get("icon");
		if (icon == null) return 0;
		String iconName = icon.toString();
		Integer cached = iconCache.get(iconName);
		if (cached != null) return cached;
		int resId = context.getResources().getIdentifier(
				iconName, "drawable", context.getPackageName());
		iconCache.put(iconName, resId);
		return resId;
	}


	private void rebuildRows() {
		rows.clear();
		sectionCounts.clear();
		String current = null;
		for (HashMap<String, Object> item : data) {
			if (item == null) continue;
			if (isHeader(item)) {
				current = nameOf(item, "");
				sectionCounts.put(current, 0);
				rows.add(item);
			} else {
				if (current != null) {
					Integer c = sectionCounts.get(current);
					sectionCounts.put(current, c == null ? 1 : c + 1);
				}
				if (current == null || !collapsed.contains(current)) {
					rows.add(item);
				}
			}
		}
	}

	private void toggleSection(int headerPos) {
		HashMap<String, Object> header = rows.get(headerPos);
		String name = nameOf(header, "");

		if (collapsed.contains(name)) {
			collapsed.remove(name);
			ArrayList<HashMap<String, Object>> items = sectionItems(name);
			rows.addAll(headerPos + 1, items);
			notifyItemRangeInserted(headerPos + 1, items.size());
		} else {
			collapsed.add(name);
			int count = 0;
			for (int i = headerPos + 1; i < rows.size() && !isHeader(rows.get(i)); i++) {
				count++;
			}
			for (int i = 0; i < count; i++) {
				rows.remove(headerPos + 1);
			}
			notifyItemRangeRemoved(headerPos + 1, count);
		}
		notifyItemChanged(headerPos, PAYLOAD_HEADER_STATE);
		persistCollapsed();
	}

	private ArrayList<HashMap<String, Object>> sectionItems(String headerName) {
		ArrayList<HashMap<String, Object>> out = new ArrayList<HashMap<String, Object>>();
		boolean inSection = false;
		for (HashMap<String, Object> item : data) {
			if (item == null) continue;
			if (isHeader(item)) {
				if (inSection) break;
				inSection = headerName.equals(nameOf(item, ""));
			} else if (inSection) {
				out.add(item);
			}
		}
		return out;
	}

	private void loadCollapsed() {
		try {
			Set<String> saved = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
					.getStringSet(KEY_COLLAPSED, null);
			if (saved != null) collapsed.addAll(saved);
		} catch (Exception ignore) {}
	}

	private void persistCollapsed() {
		try {
			context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
					.putStringSet(KEY_COLLAPSED, new HashSet<String>(collapsed)).apply();
		} catch (Exception ignore) {}
	}


	private static boolean isHeader(HashMap<String, Object> item) {
		return item != null && "header".equals(String.valueOf(item.get("type")));
	}

	private static String nameOf(HashMap<String, Object> item, String fallback) {
		Object n = item.get("name");
		return n != null ? n.toString() : fallback;
	}

	private static int withAlpha(int color, int alpha) {
		return (color & 0x00FFFFFF) | (alpha << 24);
	}

	private int dp(int value) {
		return (int) (value * context.getResources().getDisplayMetrics().density + 0.5f);
	}
	
	private String localizeViewName(String english) {
	if (english == null || english.isEmpty()) return "";
	String key = "view_" + english
			.replaceAll("([a-z0-9])([A-Z])", "$1_$2")
			.toLowerCase()
			.replace(" ", "_");
	int resId = context.getResources().getIdentifier(
			key, "string", context.getPackageName());
	if (resId != 0) {
		try { return context.getString(resId); }
		catch (Exception ignored) {}
	}
	return english;
}


	public class ItemHolder extends RecyclerView.ViewHolder {
		final LinearLayout linMain;
		final TextView tvName;
		final ImageView ivIcon;
		final TextView tvIconFallback;
		public ItemHolder(@NonNull View itemView) {
			super(itemView);
			linMain = itemView.findViewById(R.id.lin_main);
			tvName = itemView.findViewById(R.id.title);
			ivIcon = itemView.findViewById(R.id.icon);
			tvIconFallback = itemView.findViewById(R.id.icon_fallback);
		}
	}

	public class HeaderHolder extends RecyclerView.ViewHolder {
		final TextView title;
		final TextView count;
		final ImageView chevron;
		public HeaderHolder(@NonNull View itemView) {
			super(itemView);
			title = itemView.findViewById(R.id.palette_header_title);
			count = itemView.findViewById(R.id.palette_header_count);
			chevron = itemView.findViewById(R.id.palette_header_chevron);
		}
	}
}
