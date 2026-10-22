
package com.nexusteam.blacklogics.sdk.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.util.Map;

public class DynamicWidgetFactory {
	
	private static final String TAG = "DynamicWidgetFactory";
	
	@NonNull
	private static Context canvasContext(@NonNull Context context) {
		try {
			int themeId = 0;
			String pkg = context.getPackageName();
			String[] themeNames = {
				"Theme.MaterialComponents.Light.NoActionBar",
				"Theme_MaterialComponents_Light_NoActionBar",
				"Theme.MaterialComponents.Light",
				"Theme_MaterialComponents_Light",
				"Theme.Material3.Light.NoActionBar",
				"Theme_Material3_Light_NoActionBar",
				"Theme.AppCompat.Light.NoActionBar",
				"Theme_AppCompat_Light_NoActionBar",
				"Theme.AppCompat.Light",
				"Theme_AppCompat_Light",
			};
			for (String name : themeNames) {
				themeId = context.getResources().getIdentifier(name, "style", pkg);
				if (themeId == 0) {
					themeId = context.getResources().getIdentifier(
					name.replace('_', '.'), "style", "com.google.android.material");
				}
				if (themeId == 0) {
					themeId = context.getResources().getIdentifier(
					name.replace('_', '.'), "style", "androidx.appcompat");
				}
				if (themeId != 0) break;
			}
			if (themeId == 0) {
				themeId = android.R.style.Theme_Material_Light;
			}
			return new ContextThemeWrapper(context, themeId);
		} catch (Throwable t) {
			Log.w(TAG, "canvasContext fallback: " + t.getMessage());
			return context;
		}
	}
	
	@NonNull
	public static View resolve(
	@NonNull Context context,
	@NonNull String xmlTag,
	@Nullable Map<String, String> attrs) {
		
		Context lightCtx = canvasContext(context);
		
		View view = tryCreate(lightCtx, xmlTag);
		
		if (view == null && !xmlTag.contains(".")) {
			view = tryResolveShortName(lightCtx, xmlTag);
		}
		
		if (view == null && lightCtx != context) {
			view = tryCreate(context, xmlTag);
			if (view == null && !xmlTag.contains(".")) {
				view = tryResolveShortName(context, xmlTag);
			}
		}
		
		if (view == null) {
			Log.w(TAG, "Could not resolve: " + xmlTag + " → showing placeholder");
			view = buildPlaceholder(lightCtx, xmlTag);
		}
		
		populatePreviewItemsIfNeeded(lightCtx, view);
		
		if (attrs != null) {
			applyBasicAttrs(lightCtx, view, attrs);
		}
		
		ensureCanvasReadableDefaults(view);
		
		makeEditorPreviewSafe(view);
		
		return view;
	}
	
	private static void ensureCanvasReadableDefaults(@NonNull View view) {
		try {
			if (view instanceof TextView) {
				TextView tv = (TextView) view;
				int c = tv.getCurrentTextColor();
				int a = (c >>> 24) & 0xFF;
				int r = (c >> 16) & 0xFF;
				int g = (c >> 8) & 0xFF;
				int b = c & 0xFF;
				int avg = (r + g + b) / 3;
				if (a < 40 || avg > 180) {
					tv.setTextColor(0xDE000000);
				}
				try {
					int hc = tv.getCurrentHintTextColor();
					int ha = (hc >>> 24) & 0xFF;
					int hAvg = (((hc >> 16) & 0xFF) + ((hc >> 8) & 0xFF) + (hc & 0xFF)) / 3;
					if (ha < 40 || hAvg > 200 || hAvg < 40) {
						tv.setHintTextColor(0x99000000);
					}
				} catch (Throwable ignore) {}
			}

			if (view instanceof android.widget.CompoundButton) {
				try {
					android.widget.CompoundButton cb = (android.widget.CompoundButton) view;
					int c = cb.getCurrentTextColor();
					int avg = (((c >> 16) & 0xFF) + ((c >> 8) & 0xFF) + (c & 0xFF)) / 3;
					if (avg > 180) cb.setTextColor(0xDE000000);
				} catch (Throwable ignore) {}
			}

			if (view.getBackground() == null) {
				String cn = view.getClass().getName();
				if (cn.contains("MaterialButton") || cn.contains("Button")
						|| cn.contains("Chip")) {
				} else if (cn.contains("CardView") || cn.contains("MaterialCard")) {
				}
			}

			if (view instanceof ViewGroup) {
				ViewGroup vg = (ViewGroup) view;
				int n = Math.min(vg.getChildCount(), 16);
				for (int i = 0; i < n; i++) {
					View child = vg.getChildAt(i);
					if (child != null) ensureCanvasReadableDefaults(child);
				}
			}
		} catch (Throwable ignore) {}
	}

	public static void applyCanvasLightDefaults(@NonNull View view) {
		ensureCanvasReadableDefaults(view);
	}
	
	public static void makeEditorPreviewSafe(final View view) {
		if (view == null) return;
		try {
			freezeOne(view);
			if (view instanceof ViewGroup) {
				final ViewGroup vg = (ViewGroup) view;
				vg.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
				blockAllDescendants(vg);
				view.post(new Runnable() {
					@Override public void run() {
						try { blockAllDescendants(vg); } catch (Throwable ignore) {}
					}
				});
			}
		} catch (Throwable t) {
			Log.w(TAG, "makeEditorPreviewSafe: " + t.getMessage());
		}
	}
	
	private static void freezeOne(View view) {
		if (view == null) return;
		
		if (view instanceof android.widget.EditText) {
			android.widget.EditText et = (android.widget.EditText) view;
			et.setFocusable(false);
			et.setFocusableInTouchMode(false);
			et.setCursorVisible(false);
			et.setLongClickable(false);
			et.setKeyListener(null);
		}
		
		if (view instanceof android.widget.CompoundButton) {
			android.widget.CompoundButton cb = (android.widget.CompoundButton) view;
			cb.setClickable(false);
			cb.setFocusable(false);
			cb.setOnCheckedChangeListener(null);
		}
		
		if (view instanceof android.widget.SeekBar) {
			((android.widget.SeekBar) view).setOnSeekBarChangeListener(null);
		}
		
		if (view instanceof android.widget.RatingBar) {
			view.setFocusable(false);
		}
		
		if (view instanceof android.widget.Spinner) {
			view.setClickable(false);
			view.setFocusable(false);
		}
		
		if (view instanceof android.widget.AdapterView) {
			android.widget.AdapterView av = (android.widget.AdapterView) view;
			av.setOnItemClickListener(null);
			av.setOnItemSelectedListener(null);
			av.setOnItemLongClickListener(null);
			av.setFocusable(false);
		}
		
		if (view instanceof androidx.recyclerview.widget.RecyclerView) {
			androidx.recyclerview.widget.RecyclerView rv =
			(androidx.recyclerview.widget.RecyclerView) view;
			rv.setOnClickListener(null);
			rv.setNestedScrollingEnabled(false);
		}
		
		if (view instanceof android.webkit.WebView) {
			try {
				((android.webkit.WebView) view).getSettings().setJavaScriptEnabled(false);
			} catch (Throwable ignore) {}
		}
		
		if (view instanceof com.google.android.material.bottomnavigation.BottomNavigationView) {
			com.google.android.material.bottomnavigation.BottomNavigationView bnv =
			(com.google.android.material.bottomnavigation.BottomNavigationView) view;
			try {
				bnv.setOnItemSelectedListener(
				new com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(android.view.MenuItem item) {
						return false;
					}
				});
			} catch (Throwable ignore) {}
			try {
				bnv.setOnItemReselectedListener(
				new com.google.android.material.navigation.NavigationBarView.OnItemReselectedListener() {
					@Override
					public void onNavigationItemReselected(android.view.MenuItem item) {}
				});
			} catch (Throwable ignore) {}
		}
		
		if (view instanceof com.google.android.material.navigation.NavigationView) {
			try {
				((com.google.android.material.navigation.NavigationView) view)
				.setNavigationItemSelectedListener(null);
			} catch (Throwable ignore) {}
		}
		
		if (view instanceof com.google.android.material.tabs.TabLayout) {
			try {
				((com.google.android.material.tabs.TabLayout) view).clearOnTabSelectedListeners();
			} catch (Throwable ignore) {}
		}
		
		if (view instanceof androidx.viewpager2.widget.ViewPager2) {
			try {
				((androidx.viewpager2.widget.ViewPager2) view).setUserInputEnabled(false);
			} catch (Throwable ignore) {}
		}
		
		if (view instanceof com.google.android.material.chip.Chip) {
			com.google.android.material.chip.Chip chip =
			(com.google.android.material.chip.Chip) view;
			chip.setClickable(false);
			chip.setCheckable(false);
			chip.setFocusable(false);
		}
	}
	
	private static void blockAllDescendants(View root) {
		if (!(root instanceof ViewGroup)) return;
		ViewGroup vg = (ViewGroup) root;
		for (int i = 0; i < vg.getChildCount(); i++) {
			View c = vg.getChildAt(i);
			try {
				c.setClickable(false);
				c.setLongClickable(false);
				c.setFocusable(false);
				c.setFocusableInTouchMode(false);
				c.setOnClickListener(null);
				c.setOnLongClickListener(null);
				c.setOnTouchListener(null);
			} catch (Throwable ignore) {}
			if (c instanceof ViewGroup) {
				((ViewGroup) c).setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
				blockAllDescendants(c);
			}
		}
	}
	
	private static void disableDescendantClicks(View root) {
		blockAllDescendants(root);
	}
	
	
	private static void populatePreviewItemsIfNeeded(@NonNull Context context, @NonNull final View view) {
		if (view instanceof android.widget.ExpandableListView) {
			android.widget.ExpandableListView expandableListView =
			(android.widget.ExpandableListView) view;
			expandableListView.setAdapter(new PreviewExpandableAdapter());
			expandableListView.expandGroup(0);
			
		} else if (view instanceof android.widget.GridView) {
			android.widget.GridView gridView = (android.widget.GridView) view;
			if (gridView.getNumColumns() <= 0) {
				gridView.setNumColumns(3);
			}
			String[] items = new String[6];
			for (int i = 0; i < items.length; i++) {
				items[i] = "Grid " + (i + 1);
			}
			android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(
			context, android.R.layout.simple_list_item_1, items);
			gridView.setAdapter(adapter);
			
		} else if (view instanceof android.widget.ListView) {
			android.widget.ListView listView = (android.widget.ListView) view;
			String[] items = { "List item 1", "List item 2", "List item 3" };
			android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(
			context, android.R.layout.simple_list_item_1, items);
			listView.setAdapter(adapter);
			
		} else if (view instanceof androidx.recyclerview.widget.RecyclerView) {
			final androidx.recyclerview.widget.RecyclerView recyclerView =
			(androidx.recyclerview.widget.RecyclerView) view;
			recyclerView.post(new Runnable() {
				@Override
				public void run() {
					if (recyclerView.getLayoutManager() == null) {
						recyclerView.setLayoutManager(
						new androidx.recyclerview.widget.LinearLayoutManager(recyclerView.getContext()));
					}
					if (recyclerView.getAdapter() == null) {
						String[] items = { "Recycler item 1", "Recycler item 2", "Recycler item 3" };
						recyclerView.setAdapter(new PreviewRecyclerAdapter(items));
					}
				}
			});
			
		} else if (view instanceof com.google.android.material.tabs.TabLayout) {
			final com.google.android.material.tabs.TabLayout tabLayout =
			(com.google.android.material.tabs.TabLayout) view;
			String[] tabs = { "TAB 1", "TAB 2", "TAB 3" };
			for (String t : tabs) {
				tabLayout.addTab(tabLayout.newTab().setText(t));
			}
			tabLayout.addOnTabSelectedListener(
			new com.google.android.material.tabs.TabLayout.OnTabSelectedListener() {
				@Override
				public void onTabSelected(com.google.android.material.tabs.TabLayout.Tab tab) {
					if (tab.getPosition() != 0) {
						tabLayout.selectTab(tabLayout.getTabAt(0), false);
					}
				}
				
				@Override
				public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab tab) {
				}
				
				@Override
				public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab tab) {
				}
			});
			
		} else if (view instanceof com.google.android.material.bottomnavigation.BottomNavigationView) {
			final com.google.android.material.bottomnavigation.BottomNavigationView bottomNav =
			(com.google.android.material.bottomnavigation.BottomNavigationView) view;
			android.view.Menu menu = bottomNav.getMenu();
			menu.add(0, 1, 0, "Home").setIcon(PreviewIcons.home(context));
			menu.add(0, 2, 1, "Chat").setIcon(PreviewIcons.chat(context));
			menu.add(0, 3, 2, "Profile").setIcon(PreviewIcons.profile(context));
			bottomNav.setSelectedItemId(1);
			bottomNav.setOnNavigationItemSelectedListener(
			new com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener() {
				@Override
				public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
					return false;
				}
			});
			int minH = dp(context, 73);
			bottomNav.setMinimumHeight(minH);
			ViewGroup.LayoutParams lp = bottomNav.getLayoutParams();
			if (lp == null) {
				lp = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, minH);
				bottomNav.setLayoutParams(lp);
			} else {
				if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT
				|| lp.height == ViewGroup.LayoutParams.MATCH_PARENT
				|| lp.height < minH) {
					lp.height = minH;
					bottomNav.setLayoutParams(lp);
				}
			}
			
		} else if (view instanceof android.webkit.WebView) {
			android.webkit.WebView webView = (android.webkit.WebView) view;
			loadCenteredLabel(webView, "WebView");
			
		} else if (isSwipeRefreshLayout(view)) {
			loadRefreshLabelIntoSwipeRefresh(context, (ViewGroup) view);
			
		} else if (view instanceof android.widget.Spinner) {
			final android.widget.Spinner spinner = (android.widget.Spinner) view;
			String[] items = { "Spinner" };
			android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(
			context, android.R.layout.simple_spinner_item, items);
			spinner.setAdapter(adapter);
			spinner.setClickable(false);
			
		} else if (view instanceof android.widget.ProgressBar) {
			android.widget.ProgressBar progressBar = (android.widget.ProgressBar) view;
			if (!progressBar.isIndeterminate()) {
				progressBar.setMax(100);
				progressBar.setProgress(60);
			}
			
		} else if (view instanceof android.widget.RatingBar) {
			final android.widget.RatingBar ratingBar = (android.widget.RatingBar) view;
			ratingBar.setRating(3f);
			ratingBar.setIsIndicator(true);
			
		} else if (view instanceof androidx.viewpager.widget.ViewPager) {
			final androidx.viewpager.widget.ViewPager viewPager =
			(androidx.viewpager.widget.ViewPager) view;
			viewPager.setAdapter(new PreviewPagerAdapter(context, "Page 1", "Page 2", "Page 3"));
			
		} else if (view instanceof androidx.viewpager2.widget.ViewPager2) {
			final androidx.viewpager2.widget.ViewPager2 viewPager2 =
			(androidx.viewpager2.widget.ViewPager2) view;
			viewPager2.post(new Runnable() {
				@Override
				public void run() {
					viewPager2.setAdapter(new PreviewPager2Adapter(
					new String[] { "Page 1", "Page 2", "Page 3" }));
				}
			});
			
		} else if (view instanceof com.google.android.material.chip.ChipGroup) {
			com.google.android.material.chip.ChipGroup chipGroup =
			(com.google.android.material.chip.ChipGroup) view;
			String[] labels = { "Chip 1", "Chip 2", "Chip 3" };
			for (String label : labels) {
				com.google.android.material.chip.Chip chip =
				new com.google.android.material.chip.Chip(context);
				chip.setText(label);
				chip.setClickable(false);
				chip.setCheckable(false);
				chipGroup.addView(chip);
			}
			
		} else if (view instanceof com.google.android.material.navigation.NavigationView) {
			com.google.android.material.navigation.NavigationView navigationView =
			(com.google.android.material.navigation.NavigationView) view;
			android.view.Menu menu = navigationView.getMenu();
			menu.add(0, 1, 0, "Nav item 1");
			menu.add(0, 2, 1, "Nav item 2");
			menu.add(0, 3, 2, "Nav item 3");
		}
	}
	
	
	private static void loadCenteredLabel(@NonNull android.webkit.WebView webView, @NonNull String label) {
		String html = "<html><body style=\"display:flex;align-items:center;"
		+ "justify-content:center;height:100%;margin:0;"
		+ "font-family:sans-serif;color:#616161;\">"
		+ "<span>" + label + "</span></body></html>";
		webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
	}
	
	
	private static boolean isSwipeRefreshLayout(@NonNull View view) {
		return view.getClass().getName()
		.equals("androidx.swiperefreshlayout.widget.SwipeRefreshLayout");
	}
	
	private static void loadRefreshLabelIntoSwipeRefresh(@NonNull Context context, @NonNull ViewGroup swipeRefresh) {
		if (swipeRefresh.getChildCount() > 0) return;
		TextView label = new TextView(context);
		label.setText("Refresh view");
		label.setTextColor(Color.parseColor("#616161"));
		label.setGravity(Gravity.CENTER);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
		ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		swipeRefresh.addView(label, lp);
	}
	
	private static class PreviewRecyclerAdapter
	extends androidx.recyclerview.widget.RecyclerView.Adapter<PreviewRecyclerAdapter.Holder> {
		
		private final String[] items;
		
		PreviewRecyclerAdapter(String[] items) {
			this.items = items;
		}
		
		@NonNull
		@Override
		public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			TextView tv = new TextView(parent.getContext());
			tv.setPadding(dp(parent.getContext(), 16), dp(parent.getContext(), 12),
			dp(parent.getContext(), 16), dp(parent.getContext(), 12));
			tv.setLayoutParams(new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			return new Holder(tv);
		}
		
		@Override
		public void onBindViewHolder(@NonNull Holder holder, int position) {
			((TextView) holder.itemView).setText(items[position]);
		}
		
		@Override
		public int getItemCount() {
			return items.length;
		}
		
		static class Holder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
			Holder(@NonNull View itemView) {
				super(itemView);
			}
		}
	}
	
	private static class PreviewExpandableAdapter extends android.widget.BaseExpandableListAdapter {
		
		private final String[] groups = { "Group 1", "Group 2" };
		private final String[][] children = {
			{ "Child 1.1", "Child 1.2" },
			{ "Child 2.1", "Child 2.2" }
		};
		
		@Override
		public int getGroupCount() {
			return groups.length;
		}
		
		@Override
		public int getChildrenCount(int groupPosition) {
			return children[groupPosition].length;
		}
		
		@Override
		public Object getGroup(int groupPosition) {
			return groups[groupPosition];
		}
		
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return children[groupPosition][childPosition];
		}
		
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}
		
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}
		
		@Override
		public boolean hasStableIds() {
			return true;
		}
		
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
		ViewGroup parent) {
			TextView tv = makeRow(parent.getContext());
			tv.setTypeface(Typeface.DEFAULT_BOLD);
			tv.setText((String) getGroup(groupPosition));
			return tv;
		}
		
		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
		View convertView, ViewGroup parent) {
			TextView tv = makeRow(parent.getContext());
			tv.setPadding(dp(parent.getContext(), 32), dp(parent.getContext(), 12),
			dp(parent.getContext(), 16), dp(parent.getContext(), 12));
			tv.setText((String) getChild(groupPosition, childPosition));
			return tv;
		}
		
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}
		
		private TextView makeRow(Context context) {
			TextView tv = new TextView(context);
			tv.setPadding(dp(context, 16), dp(context, 12), dp(context, 16), dp(context, 12));
			tv.setTextColor(Color.parseColor("#424242"));
			tv.setLayoutParams(new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			return tv;
		}
	}
	
	
	private static class PreviewPagerAdapter extends androidx.viewpager.widget.PagerAdapter {
		private final Context context;
		private final String[] pages;
		
		PreviewPagerAdapter(Context context, String... pages) {
			this.context = context;
			this.pages = pages;
		}
		
		@Override
		public int getCount() {
			return pages.length;
		}
		
		@Override
		public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
			return view == object;
		}
		
		@NonNull
		@Override
		public Object instantiateItem(@NonNull ViewGroup container, int position) {
			TextView tv = new TextView(context);
			tv.setText(pages[position]);
			tv.setGravity(Gravity.CENTER);
			tv.setTextColor(Color.parseColor("#616161"));
			tv.setTextSize(18f);
			tv.setTypeface(Typeface.DEFAULT_BOLD);
			container.addView(tv, new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			return tv;
		}
		
		@Override
		public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
			container.removeView((View) object);
		}
	}
	
	
	private static class PreviewPager2Adapter
	extends androidx.recyclerview.widget.RecyclerView.Adapter<PreviewPager2Adapter.Holder> {
		
		private final String[] pages;
		
		PreviewPager2Adapter(String[] pages) {
			this.pages = pages;
		}
		
		@NonNull
		@Override
		public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			TextView tv = new TextView(parent.getContext());
			tv.setGravity(Gravity.CENTER);
			tv.setTextColor(Color.parseColor("#616161"));
			tv.setTextSize(18f);
			tv.setTypeface(Typeface.DEFAULT_BOLD);
			tv.setLayoutParams(new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			return new Holder(tv);
		}
		
		@Override
		public void onBindViewHolder(@NonNull Holder holder, int position) {
			((TextView) holder.itemView).setText(pages[position]);
		}
		
		@Override
		public int getItemCount() {
			return pages.length;
		}
		
		static class Holder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
			Holder(@NonNull View itemView) {
				super(itemView);
			}
		}
	}
	
	
	private static class PreviewIcons {
		
		static android.graphics.drawable.Drawable home(Context context) {
			android.graphics.drawable.Drawable real = fromResourceName(context, "ic_nav_home");
			if (real != null) return real;
			return draw(context, new IconPainter() {
				@Override
				public void paint(android.graphics.Canvas c, android.graphics.Paint p, float s) {
					android.graphics.Path path = new android.graphics.Path();
					path.moveTo(s * 0.5f, s * 0.12f);
					path.lineTo(s * 0.90f, s * 0.45f);
					path.lineTo(s * 0.78f, s * 0.45f);
					path.lineTo(s * 0.78f, s * 0.88f);
					path.lineTo(s * 0.58f, s * 0.88f);
					path.lineTo(s * 0.58f, s * 0.62f);
					path.lineTo(s * 0.42f, s * 0.62f);
					path.lineTo(s * 0.42f, s * 0.88f);
					path.lineTo(s * 0.22f, s * 0.88f);
					path.lineTo(s * 0.22f, s * 0.45f);
					path.lineTo(s * 0.10f, s * 0.45f);
					path.close();
					c.drawPath(path, p);
				}
			});
		}
		
		static android.graphics.drawable.Drawable chat(Context context) {
			android.graphics.drawable.Drawable real = fromResourceName(context, "ic_nav_chat");
			if (real != null) return real;
			return draw(context, new IconPainter() {
				@Override
				public void paint(android.graphics.Canvas c, android.graphics.Paint p, float s) {
					android.graphics.RectF bubble = new android.graphics.RectF(
					s * 0.12f, s * 0.16f, s * 0.88f, s * 0.68f);
					c.drawRoundRect(bubble, s * 0.08f, s * 0.08f, p);
					
					android.graphics.Path tail = new android.graphics.Path();
					tail.moveTo(s * 0.30f, s * 0.68f);
					tail.lineTo(s * 0.30f, s * 0.88f);
					tail.lineTo(s * 0.50f, s * 0.68f);
					tail.close();
					c.drawPath(tail, p);
					
					android.graphics.Paint erase = new android.graphics.Paint(p);
					erase.setColor(Color.WHITE);
					erase.setXfermode(new android.graphics.PorterDuffXfermode(
					android.graphics.PorterDuff.Mode.CLEAR));
					for (int i = 0; i < 3; i++) {
						float y = s * (0.32f + i * 0.11f);
						c.drawRect(s * 0.24f, y, s * 0.76f, y + s * 0.05f, erase);
					}
				}
			});
		}
		
		static android.graphics.drawable.Drawable profile(Context context) {
			android.graphics.drawable.Drawable real = fromResourceName(context, "ic_nav_profile");
			if (real != null) return real;
			return draw(context, new IconPainter() {
				@Override
				public void paint(android.graphics.Canvas c, android.graphics.Paint p, float s) {
					c.drawCircle(s * 0.5f, s * 0.30f, s * 0.18f, p);
					android.graphics.RectF body = new android.graphics.RectF(
					s * 0.20f, s * 0.55f, s * 0.80f, s * 0.90f);
					c.drawArc(body, 180, 180, true, p);
				}
			});
		}
		
		private static android.graphics.drawable.Drawable fromResourceName(Context context, String name) {
			try {
				int resId = context.getResources().getIdentifier(
				name, "drawable", context.getPackageName());
				if (resId == 0) return null;
				android.graphics.drawable.Drawable d = context.getResources().getDrawable(resId, context.getTheme());
				if (d != null) {
					int size = dp(context, 24);
					d.setBounds(0, 0, size, size);
				}
				return d;
			} catch (Exception e) {
				return null;
			}
		}
		
		private interface IconPainter {
			void paint(android.graphics.Canvas canvas, android.graphics.Paint paint, float sizePx);
		}
		
		private static android.graphics.drawable.Drawable draw(Context context, IconPainter painter) {
			int size = dp(context, 24);
			android.graphics.Bitmap bitmap = android.graphics.Bitmap.createBitmap(
			size, size, android.graphics.Bitmap.Config.ARGB_8888);
			android.graphics.Canvas canvas = new android.graphics.Canvas(bitmap);
			android.graphics.Paint paint = new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG);
			paint.setStyle(android.graphics.Paint.Style.FILL);
			paint.setColor(Color.parseColor("#616161"));
			painter.paint(canvas, paint, size);
			android.graphics.drawable.BitmapDrawable drawable =
			new android.graphics.drawable.BitmapDrawable(context.getResources(), bitmap);
			drawable.setBounds(0, 0, size, size);
			return drawable;
		}
	}
	
	
	@Nullable
	private static View tryCreate(@NonNull Context context, @NonNull String className) {
		try {
			ClassLoader cl = context.getClassLoader();
			Class<?> cls = cl != null
			? Class.forName(className, true, cl)
			: Class.forName(className);
			if (!View.class.isAssignableFrom(cls)) return null;
			View v = instantiate(context, cls);
			if (v == null) {
				Log.w(TAG, "instantiate returned null for " + className);
			}
			return v;
		} catch (ClassNotFoundException ignored) {
		} catch (Throwable e) {
			Log.w(TAG, "tryCreate failed for " + className + ": " + e);
		}
		return null;
	}
	
	
	private static final java.util.Map<String, String> KNOWN_FQCN =
	new java.util.HashMap<String, String>();
	static {
		KNOWN_FQCN.put("MaterialButton", "com.google.android.material.button.MaterialButton");
		KNOWN_FQCN.put("MaterialButtonToggleGroup", "com.google.android.material.button.MaterialButtonToggleGroup");
		KNOWN_FQCN.put("MaterialCardView", "com.google.android.material.card.MaterialCardView");
		KNOWN_FQCN.put("MaterialTextView", "com.google.android.material.textview.MaterialTextView");
		KNOWN_FQCN.put("TextInputLayout", "com.google.android.material.textfield.TextInputLayout");
		KNOWN_FQCN.put("TextInputEditText", "com.google.android.material.textfield.TextInputEditText");
		KNOWN_FQCN.put("MaterialAutoCompleteTextView", "com.google.android.material.textfield.MaterialAutoCompleteTextView");
		KNOWN_FQCN.put("BottomNavigationView", "com.google.android.material.bottomnavigation.BottomNavigationView");
		KNOWN_FQCN.put("NavigationView", "com.google.android.material.navigation.NavigationView");
		KNOWN_FQCN.put("NavigationRailView", "com.google.android.material.navigationrail.NavigationRailView");
		KNOWN_FQCN.put("TabLayout", "com.google.android.material.tabs.TabLayout");
		KNOWN_FQCN.put("TabItem", "com.google.android.material.tabs.TabItem");
		KNOWN_FQCN.put("Chip", "com.google.android.material.chip.Chip");
		KNOWN_FQCN.put("ChipGroup", "com.google.android.material.chip.ChipGroup");
		KNOWN_FQCN.put("SwitchMaterial", "com.google.android.material.switchmaterial.SwitchMaterial");
		KNOWN_FQCN.put("MaterialSwitch", "com.google.android.material.materialswitch.MaterialSwitch");
		KNOWN_FQCN.put("MaterialCheckBox", "com.google.android.material.checkbox.MaterialCheckBox");
		KNOWN_FQCN.put("MaterialRadioButton", "com.google.android.material.radiobutton.MaterialRadioButton");
		KNOWN_FQCN.put("AppBarLayout", "com.google.android.material.appbar.AppBarLayout");
		KNOWN_FQCN.put("CollapsingToolbarLayout", "com.google.android.material.appbar.CollapsingToolbarLayout");
		KNOWN_FQCN.put("MaterialToolbar", "com.google.android.material.appbar.MaterialToolbar");
		KNOWN_FQCN.put("FloatingActionButton", "com.google.android.material.floatingactionbutton.FloatingActionButton");
		KNOWN_FQCN.put("ExtendedFloatingActionButton", "com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton");
		KNOWN_FQCN.put("CircularProgressIndicator", "com.google.android.material.progressindicator.CircularProgressIndicator");
		KNOWN_FQCN.put("LinearProgressIndicator", "com.google.android.material.progressindicator.LinearProgressIndicator");
		KNOWN_FQCN.put("Slider", "com.google.android.material.slider.Slider");
		KNOWN_FQCN.put("RangeSlider", "com.google.android.material.slider.RangeSlider");
		KNOWN_FQCN.put("BottomAppBar", "com.google.android.material.bottomappbar.BottomAppBar");
		KNOWN_FQCN.put("BottomSheet", "com.google.android.material.bottomsheet.BottomSheetBehavior");
		KNOWN_FQCN.put("RecyclerView", "androidx.recyclerview.widget.RecyclerView");
		KNOWN_FQCN.put("CardView", "androidx.cardview.widget.CardView");
		KNOWN_FQCN.put("NestedScrollView", "androidx.core.widget.NestedScrollView");
		KNOWN_FQCN.put("ViewPager", "androidx.viewpager.widget.ViewPager");
		KNOWN_FQCN.put("ViewPager2", "androidx.viewpager2.widget.ViewPager2");
		KNOWN_FQCN.put("CoordinatorLayout", "androidx.coordinatorlayout.widget.CoordinatorLayout");
		KNOWN_FQCN.put("DrawerLayout", "androidx.drawerlayout.widget.DrawerLayout");
		KNOWN_FQCN.put("SwipeRefreshLayout", "androidx.swiperefreshlayout.widget.SwipeRefreshLayout");
		KNOWN_FQCN.put("ConstraintLayout", "androidx.constraintlayout.widget.ConstraintLayout");
		KNOWN_FQCN.put("MotionLayout", "androidx.constraintlayout.motion.widget.MotionLayout");
		KNOWN_FQCN.put("Toolbar", "androidx.appcompat.widget.Toolbar");
		KNOWN_FQCN.put("LottieAnimationView", "com.airbnb.lottie.LottieAnimationView");
		KNOWN_FQCN.put("CircleImageView", "de.hdodenhof.circleimageview.CircleImageView");
	}
	
	@Nullable
	private static View tryResolveShortName(@NonNull Context context, @NonNull String shortName) {
		
		String known = KNOWN_FQCN.get(shortName);
		if (known != null) {
			View v = tryCreate(context, known);
			if (v != null) {
				Log.d(TAG, "Resolved via map: " + shortName + " → " + known);
				return v;
			}
			if ("MaterialSwitch".equals(shortName)) {
				v = tryCreate(context, "com.google.android.material.switchmaterial.SwitchMaterial");
				if (v != null) return v;
			}
			if ("SwitchMaterial".equals(shortName)) {
				v = tryCreate(context, "com.google.android.material.materialswitch.MaterialSwitch");
				if (v != null) return v;
			}
		}
		
		String[] runtimePackages = {
			"android.widget",
			"android.view",
			"android.webkit",
			"androidx.appcompat.widget",
			"androidx.recyclerview.widget",
			"androidx.constraintlayout.widget",
			"androidx.constraintlayout.motion.widget",
			"androidx.cardview.widget",
			"androidx.coordinatorlayout.widget",
			"androidx.drawerlayout.widget",
			"androidx.swiperefreshlayout.widget",
			"androidx.viewpager2.widget",
			"androidx.viewpager.widget",
			"androidx.core.widget",
			"com.google.android.material.button",
			"com.google.android.material.card",
			"com.google.android.material.textfield",
			"com.google.android.material.textview",
			"com.google.android.material.tabs",
			"com.google.android.material.bottomnavigation",
			"com.google.android.material.navigation",
			"com.google.android.material.navigationrail",
			"com.google.android.material.chip",
			"com.google.android.material.floatingactionbutton",
			"com.google.android.material.appbar",
			"com.google.android.material.switchmaterial",
			"com.google.android.material.materialswitch",
			"com.google.android.material.checkbox",
			"com.google.android.material.radiobutton",
			"com.google.android.material.slider",
			"com.google.android.material.progressindicator",
			"com.google.android.material.bottomappbar",
			"com.google.android.material.bottomsheet",
			"com.google.android.material",
			"com.google.android.flexbox",
			"com.airbnb.lottie",
			"de.hdodenhof.circleimageview",
			"com.nexusteam.internal.os.layouteditor.widget",
			"com.nexusteam.blacklogics.custom",
			"com.nexusteam.blacklogics.sdk.widgets",
			"com.nexusteam.blacklogics",
		};
		
		for (String pkg : runtimePackages) {
			View v = tryCreate(context, pkg + "." + shortName);
			if (v != null) {
				Log.d(TAG, "Resolved short name: " + shortName + " → " + pkg + "." + shortName);
				return v;
			}
		}
		
		return null;
	}
	
	
	@NonNull
	private static View buildPlaceholder(@NonNull Context context, @NonNull String tagName) {
		String shortName = tagName.contains(".")
		? tagName.substring(tagName.lastIndexOf('.') + 1)
		: tagName;
		String fqcn = KNOWN_FQCN.containsKey(shortName) ? KNOWN_FQCN.get(shortName) : tagName;
		if (fqcn == null || fqcn.isEmpty()) fqcn = tagName;
		
		try {
			com.nexusteam.blacklogics.sdk.widgets.PlaceholderWidget ph =
			new com.nexusteam.blacklogics.sdk.widgets.PlaceholderWidget(context, fqcn);
			ph.setMinimumHeight(dp(context, 48));
			ph.setMinimumWidth(dp(context, 80));
			return ph;
		} catch (Throwable t) {
			Log.w(TAG, "PlaceholderWidget create failed, fallback LinearLayout", t);
		}
		
		LinearLayout container = new LinearLayout(context);
		container.setOrientation(LinearLayout.VERTICAL);
		container.setGravity(Gravity.CENTER);
		container.setMinimumHeight(dp(context, 48));
		container.setMinimumWidth(dp(context, 80));
		container.setTag(fqcn);
		
		android.graphics.drawable.GradientDrawable border = new android.graphics.drawable.GradientDrawable();
		border.setColor(Color.parseColor("#F5F5F5"));
		border.setStroke(dp(context, 1), Color.parseColor("#BDBDBD"));
		border.setCornerRadius(dp(context, 4));
		container.setBackground(border);
		
		TextView label = new TextView(context);
		label.setText(shortName);
		label.setTextColor(Color.parseColor("#616161"));
		label.setTextSize(11f);
		label.setTypeface(Typeface.MONOSPACE);
		label.setGravity(Gravity.CENTER);
		label.setPadding(dp(context, 8), dp(context, 4), dp(context, 8), dp(context, 4));
		container.addView(label);
		
		return container;
	}
	
	
	@Nullable
	private static View instantiate(@NonNull Context context, @NonNull Class<?> cls) {
		Throwable last = null;
		try {
			return (View) cls.getConstructor(Context.class).newInstance(context);
		} catch (Throwable t) { last = t; }
		
		try {
			return (View) cls.getConstructor(Context.class, android.util.AttributeSet.class)
			.newInstance(context, null);
		} catch (Throwable t) { last = t; }
		
		try {
			return (View) cls.getConstructor(Context.class, android.util.AttributeSet.class, int.class)
			.newInstance(context, null, 0);
		} catch (Throwable t) { last = t; }
		
		try {
			return (View) cls.getConstructor(Context.class, android.util.AttributeSet.class, int.class, int.class)
			.newInstance(context, null, 0, 0);
		} catch (Throwable t) { last = t; }
		
		if (last != null) {
			Throwable cause = last.getCause() != null ? last.getCause() : last;
			Log.w(TAG, "instantiate failed " + cls.getName() + ": " + cause);
		}
		return null;
	}
	
	
	private static void applyBasicAttrs(
	@NonNull Context context,
	@NonNull View view,
	@NonNull Map<String, String> attrs) {
		
		int w = parseSize(context, attrs.get("android:layout_width"),  ViewGroup.LayoutParams.WRAP_CONTENT);
		int h = parseSize(context, attrs.get("android:layout_height"), ViewGroup.LayoutParams.WRAP_CONTENT);
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		if (lp == null) lp = new ViewGroup.LayoutParams(w, h);
		else { lp.width = w; lp.height = h; }
		view.setLayoutParams(lp);
		
		int padAll = parseDp(context, attrs.get("android:padding"), 0);
		int l = parseDp(context, attrs.get("android:paddingLeft"),   padAll);
		int t = parseDp(context, attrs.get("android:paddingTop"),    padAll);
		int r = parseDp(context, attrs.get("android:paddingRight"),  padAll);
		int b = parseDp(context, attrs.get("android:paddingBottom"), padAll);
		view.setPadding(l, t, r, b);
		
		String bg = attrs.get("android:background");
		if (bg != null && bg.startsWith("#")) {
			try { view.setBackgroundColor(Color.parseColor(bg)); }
			catch (Exception ignored) {}
		}
	}
	
	
	private static int dp(@NonNull Context ctx, int val) {
		return Math.round(val * ctx.getResources().getDisplayMetrics().density);
	}
	
	private static int parseSize(@NonNull Context ctx, @Nullable String val, int def) {
		if (val == null) return def;
		switch (val.trim()) {
			case "match_parent": case "fill_parent": return ViewGroup.LayoutParams.MATCH_PARENT;
			case "wrap_content": return ViewGroup.LayoutParams.WRAP_CONTENT;
			default:
			try {
				if (val.endsWith("dp")) return dp(ctx, Integer.parseInt(val.replace("dp","").trim()));
				if (val.endsWith("px")) return Integer.parseInt(val.replace("px","").trim());
			} catch (Exception ignored) {}
			return def;
		}
	}
	
	private static int parseDp(@NonNull Context ctx, @Nullable String val, int def) {
		if (val == null) return def;
		try {
			if (val.endsWith("dp")) return dp(ctx, Integer.parseInt(val.replace("dp","").trim()));
			if (val.endsWith("px")) return Integer.parseInt(val.replace("px","").trim());
		} catch (Exception ignored) {}
		return def;
	}
}
