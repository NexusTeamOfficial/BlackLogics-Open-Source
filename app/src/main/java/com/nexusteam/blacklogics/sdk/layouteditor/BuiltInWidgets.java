package com.nexusteam.blacklogics.sdk.layouteditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class BuiltInWidgets {

	private BuiltInWidgets() {}

	public static ArrayList<HashMap<String, Object>> getAll() {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		add(list, "LinearLayout", "android.widget.LinearLayout", "ic_palette_linear_layout_horz", null,
				events());
		add(list, "RelativeLayout", "android.widget.RelativeLayout", "ic_palette_relative_layout", null,
				events());
		add(list, "FrameLayout", "android.widget.FrameLayout", "ic_palette_frame_layout", null,
				events());
		add(list, "GridLayout", "android.widget.GridLayout", "ic_palette_grid_layout", null,
				events());
		add(list, "TableLayout", "android.widget.TableLayout", "ic_palette_table_layout", null,
				events());
		add(list, "ConstraintLayout", "androidx.constraintlayout.widget.ConstraintLayout", "ic_palette_constraint_layout", null,
				events());
		add(list, "MotionLayout", "androidx.constraintlayout.motion.widget.MotionLayout", "icon_auto_awesome_motion_round", null,
				events());
		add(list, "CoordinatorLayout", "androidx.coordinatorlayout.widget.CoordinatorLayout", "ic_palette_coordinator_layout", null,
				events());
		add(list, "TextView", "android.widget.TextView", "ic_palette_text_view", attrs("android:text", "TextView"),
				events(ev("onClick", "View.OnClickListener"),
				ev("onLongClick", "View.OnLongClickListener"),
				ev("onTextChanged", "TextWatcher")));
		add(list, "Button", "android.widget.Button", "ic_palette_button", attrs("android:text", "Button"),
				events(ev("onClick", "View.OnClickListener"),
				ev("onLongClick", "View.OnLongClickListener")));
		add(list, "EditText", "android.widget.EditText", "ic_palette_edit_text", attrs("android:hint", "Enter text"),
				events(ev("onClick", "View.OnClickListener"),
				ev("onFocusChange", "View.OnFocusChangeListener"),
				ev("onTextChanged", "TextWatcher")));
		add(list, "ImageView", "android.widget.ImageView", "ic_palette_image_view", attrs("android:src", "@drawable/default_image"),
				events(ev("onClick", "View.OnClickListener"),
				ev("onLongClick", "View.OnLongClickListener")));
		add(list, "ImageButton", "android.widget.ImageButton", "ic_palette_image_button", null,
				events(ev("onClick", "View.OnClickListener"),
				ev("onLongClick", "View.OnLongClickListener")));
		add(list, "CheckBox", "android.widget.CheckBox", "ic_palette_check_box", attrs("android:text", "CheckBox"),
				events(ev("onCheckedChanged", "CompoundButton.OnCheckedChangeListener")));
		add(list, "RadioButton", "android.widget.RadioButton", "ic_palette_radio_button", attrs("android:text", "RadioButton"),
				events(ev("onCheckedChanged", "CompoundButton.OnCheckedChangeListener")));
		add(list, "ToggleButton", "android.widget.ToggleButton", "ic_palette_toggle_button", null,
				events(ev("onCheckedChanged", "CompoundButton.OnCheckedChangeListener")));
		add(list, "Switch", "android.widget.Switch", "ic_palette_switch", attrs("android:text", "Switch"),
				events(ev("onCheckedChanged", "CompoundButton.OnCheckedChangeListener")));
		add(list, "SeekBar", "android.widget.SeekBar", "ic_palette_seek_bar", null,
				events(ev("onProgressChanged", "SeekBar.OnSeekBarChangeListener"),
				ev("onStartTrackingTouch", "SeekBar.OnSeekBarChangeListener"),
				ev("onStopTrackingTouch", "SeekBar.OnSeekBarChangeListener")));
		add(list, "ProgressBar", "android.widget.ProgressBar", "ic_palette_progress_bar", null,
				events());
		add(list, "RatingBar", "android.widget.RatingBar", "ic_palette_rating_bar", null,
				events(ev("onRatingChanged", "RatingBar.OnRatingBarChangeListener")));
		add(list, "Spinner", "android.widget.Spinner", "ic_palette_spinner", null,
				events(ev("onItemSelected", "AdapterView.OnItemSelectedListener"),
				ev("onNothingSelected", "AdapterView.OnItemSelectedListener")));
		add(list, "ListView", "android.widget.ListView", "ic_palette_list_view", null,
				events(ev("onItemClick", "AdapterView.OnItemClickListener"),
				ev("onItemLongClick", "AdapterView.OnItemLongClickListener"),
				ev("onItemSelected", "AdapterView.OnItemSelectedListener"),
				evCustom("onCustomListBind", "CustomView.OnBindListener")));
		add(list, "GridView", "android.widget.GridView", "ic_palette_grid_view", null,
				events(ev("onItemClick", "AdapterView.OnItemClickListener"),
				ev("onItemLongClick", "AdapterView.OnItemLongClickListener"),
				ev("onItemSelected", "AdapterView.OnItemSelectedListener"),
				evCustom("onCustomListBind", "CustomView.OnBindListener")));
		add(list, "ExpandableListView", "android.widget.ExpandableListView", "ic_palette_list_view", null,
				events(ev("onGroupClick", "ExpandableListView.OnGroupClickListener"),
				ev("onChildClick", "ExpandableListView.OnChildClickListener"),
				ev("onGroupExpand", "ExpandableListView.OnGroupExpandListener"),
				ev("onGroupCollapse", "ExpandableListView.OnGroupCollapseListener"),
				evCustom("onCustomListBind", "CustomView.OnBindListener")));
		add(list, "WebView", "android.webkit.WebView", "ic_palette_web_view", null,
				events(ev("onPageStarted", "WebViewClient"),
				ev("onPageFinished", "WebViewClient")));
		add(list, "WebView2", "com.nexusteam.internal.os.layouteditor.widget.WidgetWebView", "ic_palette_web_view", null,
				events(ev("onPageStarted", "WebViewClient"),
				ev("onPageFinished", "WebViewClient")));
		add(list, "VideoView", "android.widget.VideoView", "ic_palette_video_view", null,
				events(ev("onPrepared", "MediaPlayer.OnPreparedListener"),
				ev("onCompletion", "MediaPlayer.OnCompletionListener"),
				ev("onError", "MediaPlayer.OnErrorListener")));
		add(list, "ScrollView", "android.widget.ScrollView", "ic_palette_scroll_view", null,
				events(ev("onScrollChanged", "View.OnScrollChangeListener")));
		add(list, "HorizontalScrollView", "android.widget.HorizontalScrollView", "ic_palette_horizontal_scroll_view", null,
				events(ev("onScrollChanged", "View.OnScrollChangeListener")));
		add(list, "NestedScrollView", "androidx.core.widget.NestedScrollView", "ic_palette_nested_scroll_view", null,
				events(ev("onScrollChanged", "View.OnScrollChangeListener")));
		add(list, "RecyclerView", "androidx.recyclerview.widget.RecyclerView", "ic_palette_recycler_view", null,
				events(ev("onItemClick", "AdapterView.OnItemClickListener"),
				ev("onItemLongClick", "AdapterView.OnItemLongClickListener"),
				evCustom("onCustomListBind", "CustomView.OnBindListener")));
		add(list, "ViewPager", "androidx.viewpager.widget.ViewPager", "ic_palette_view_pager", null,
				events(ev("onPageSelected", "ViewPager.OnPageChangeListener"),
				ev("onPageScrolled", "ViewPager.OnPageChangeListener"),
				ev("onPageScrollStateChanged", "ViewPager.OnPageChangeListener")));
		add(list, "ViewPager2", "androidx.viewpager2.widget.ViewPager2", "ic_palette_view_pager", null,
				events(ev("onPageSelected", "ViewPager2.OnPageChangeCallback"),
				ev("onPageScrolled", "ViewPager2.OnPageChangeCallback"),
				ev("onPageScrollStateChanged", "ViewPager2.OnPageChangeCallback")));
		add(list, "CardView", "androidx.cardview.widget.CardView", "ic_palette_card_view", null,
				events(ev("onClick", "View.OnClickListener"),
				ev("onLongClick", "View.OnLongClickListener")));
		add(list, "CollapsingToolbarLayout", "com.google.android.material.appbar.CollapsingToolbarLayout", "ic_palette_collapsing_toolbar_layout", null,
				events());
		add(list, "MaterialButton", "com.google.android.material.button.MaterialButton", "ic_palette_button", attrs("android:text", "Material Button"),
				events(ev("onClick", "View.OnClickListener"),
				ev("onLongClick", "View.OnLongClickListener")));
		add(list, "TextInputLayout", "com.google.android.material.textfield.TextInputLayout", "ic_palette_text_input_layout", attrs("hint", "TextInputLayout"),
				events(ev("onTextChanged", "TextWatcher")));
		add(list, "BottomNavigationView", "com.google.android.material.bottomnavigation.BottomNavigationView", "ic_palette_bottom_navigation_view", null,
				events(ev("onNavItemSelected", "BottomNavigationView.OnNavigationItemSelectedListener")));
		add(list, "NavigationView", "com.google.android.material.navigation.NavigationView", "ic_palette_navigation_view", null,
				events(ev("onNavigationItemSelected", "NavigationView.OnNavigationItemSelectedListener")));
		add(list, "TabLayout", "com.google.android.material.tabs.TabLayout", "ic_palette_tab_layout", null,
				events(ev("onTabSelected", "TabLayout.OnTabSelectedListener"),
				ev("onTabUnselected", "TabLayout.OnTabSelectedListener"),
				ev("onTabReselected", "TabLayout.OnTabSelectedListener")));
		add(list, "Chip", "com.google.android.material.chip.Chip", "ic_palette_chip", attrs("android:text", "Chip"),
				events(ev("onClick", "View.OnClickListener"),
				ev("onCheckedChanged", "CompoundButton.OnCheckedChangeListener"),
				ev("onCloseIconClick", "View.OnClickListener")));
		add(list, "ChipGroup", "com.google.android.material.chip.ChipGroup", "ic_palette_chip_group", null,
				events(ev("onChipCheckedChanged", "ChipGroup.OnCheckedChangeListener")));
		add(list, "MaterialCardView", "com.google.android.material.card.MaterialCardView", "ic_palette_card_view", null,
				events(ev("onClick", "View.OnClickListener"),
				ev("onLongClick", "View.OnLongClickListener"),
				ev("onCheckedChanged", "MaterialCardView.OnCheckedChangeListener")));
		add(list, "MaterialSwitch", "com.google.android.material.switchmaterial.SwitchMaterial", "ic_palette_switch", null,
				events(ev("onCheckedChanged", "CompoundButton.OnCheckedChangeListener")));
		add(list, "LottieAnimationView", "com.airbnb.lottie.LottieAnimationView", "icon_animation_round", null,
				events(ev("onClick", "View.OnClickListener"),
				ev("onAnimationEnd", "Animator.AnimatorListener")));
		add(list, "CircleImageView", "de.hdodenhof.circleimageview.CircleImageView", "ic_palette_image_view", attrs("android:src", "@drawable/default_image"),
				events(ev("onClick", "View.OnClickListener"),
				ev("onLongClick", "View.OnLongClickListener")));
		return list;
	}


	private static void add(List<HashMap<String, Object>> list, String name, String classPath,
			String icon, Map<String, String> initialAttributes,
			List<Map<String, Object>> events) {
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("name", name);
		m.put("class_path", classPath);
		m.put("icon", icon);
		if (initialAttributes != null) m.put("initial_attributes", initialAttributes);
		m.put("events", events);
		list.add(m);
	}

	private static Map<String, String> attrs(String... kv) {
		LinkedHashMap<String, String> m = new LinkedHashMap<String, String>();
		for (int i = 0; i + 1 < kv.length; i += 2) m.put(kv[i], kv[i + 1]);
		return m;
	}

	private static Map<String, Object> ev(String name, String type) {
		LinkedHashMap<String, Object> m = new LinkedHashMap<String, Object>();
		m.put("name", name);
		m.put("type", type);
		return m;
	}

	private static Map<String, Object> evCustom(String name, String type) {
		Map<String, Object> m = ev(name, type);
		m.put("requiresCustomView", Boolean.TRUE);
		return m;
	}

	@SafeVarargs
	private static List<Map<String, Object>> events(Map<String, Object>... evs) {
		ArrayList<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> e : evs) l.add(e);
		return l;
	}
}
