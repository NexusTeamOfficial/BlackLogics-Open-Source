package com.nexusteam.blacklogics;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.*;
import androidx.constraintlayout.widget.*;
import androidx.core.*;
import androidx.core.widget.NestedScrollView;
import androidx.customview.*;
import androidx.draganddrop.*;
import androidx.dynamicanimation.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.*;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.airbnb.viewmodeladapter.*;
import com.besome.blacklogics.custom.SideBarVertical;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.flexbox.*;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.*;
import com.google.android.material.card.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import com.nexusteam.blacklogics.widget.AdLayout;
import com.squareup.leakcanary.*;
import de.hdodenhof.circleimageview.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import b.b.b.Qm;
import com.nexusteam.internal.editor.LogicEditorActivity;
import com.besome.blacklogics.util.FileHandler;
import com.besome.blacklogics.util.ProjectActivityManager;
import android.util.Base64;
import androidx.appcompat.app.AppCompatDelegate;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.besome.blacklogics.project.ProjectDataHelper;
import com.nexusteam.blacklogics.logic.editor.AddBlockPopup;
import com.nexusteam.blacklogics.logic.ProjectLogicRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import android.view.LayoutInflater;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.nexusteam.blacklogics.utils.EventUtils;
import android.util.Base64;

import com.nexusteam.internal.editor.makeblock.MakeBlockActivity;
import com.nexusteam.internal.beans.BlockCollectionBean;
import android.util.Pair;
import com.nexusteam.internal.BlockStorage;
import com.nexusteam.internal.beans.BlockBean;
import android.content.DialogInterface;

import com.nexusteam.internal.beans.MoreBlockCollectionBean;
import b.b.b.Qf;

import com.nexusteam.internal.BlockStorage;
import com.nexusteam.internal.FullStorage;
import com.nexusteam.blacklogics.editor.layout.parser.ViewParser;
import com.nexusteam.internal.manager.ComponentManager;
import com.nexusteam.internal.ma;
import android.util.Pair;

public class EventFragmentActivity extends Fragment {
	
	private FloatingActionButton _fab;
	private String s = "";
	private boolean bbnb = false;
	private HashMap<String, Object> map = new HashMap<>();
	private String sc_id = "";
	private String event_name = "";
	public DesignActivity designAC;
	public static String myData = "";
	public static EventFragmentActivity eventFragmentActivity;
	private HashMap<String, Object> widget_add_map = new HashMap<>();
	public ProjectActivityBean currentActivityBean;
	public static String activityName = "";
	private Qm activitySession;
	private static final String[] LIFECYCLE_EVENTS = {"onCreate", "onPostCreate", "onStart", "onResume", "onPostResume", "onPause", "onStop", "onRestart", "onDestroy", "onBackPressed", "onSaveInstanceState", "onRestoreInstanceState", "onActivityResult", "onRequestPermissionsResult", "onUserLeaveHint", "onTrimMemory", "onLowMemory", "onNewIntent", "onConfigurationChanged", "onOptionsItemSelected", "onCreateOptionsMenu"};
	public int currentSidebarPosition = 0;
	private AddBlockPopup addBlockPopup;
	private String filename = "";
	private boolean isTipsVisible = false;
	private HashMap<String, ArrayList<BlockBean>> moreBlocksMap;
	private ArrayList<MoreBlockCollectionBean> moreBlockCollections;
	
	private ArrayList<HashMap<String, Object>> list_widget_map = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout mainContentLayout;
	private SideBarVertical sidebar;
	private NestedScrollView contentScrollView;
	private LinearLayout linear11;
	private LinearLayout lifecycleEvents;
	private LinearLayout viewLifeCycle;
	private LinearLayout moreBlockLifeCycle;
	private AdLayout adLayout;
	private MaterialCardView initializeLogicCard;
	private TextView selectedEventsTitle;
	private RecyclerView rvSelectedEvents;
	private LinearLayout noEventsLayout;
	private LinearLayout learnMoreSection;
	private LinearLayout linear5;
	private LinearLayout linear2;
	private LinearLayout actionLayout;
	private ImageView imageview1;
	private LinearLayout linear3;
	private ImageView arrowIcon;
	private TextView textview2;
	private TextView textview1;
	private LinearLayout removeLayout;
	private LinearLayout deleteLayout;
	private ImageView imageview10;
	private TextView textview10;
	private ImageView imageview11;
	private TextView textview11;
	private ShapeableImageView noEventsIcon;
	private TextView noEventsText;
	private TextView noEventsHint;
	private TextView learnMoreBtn;
	private MaterialCardView tipsCard;
	private LinearLayout tipsLayout;
	private TextView tipsTitle;
	private TextView tip1;
	private TextView tip2;
	private TextView tip3;
	private TextView tip4;
	private TextView viewsTitle;
	private RecyclerView viewsList;
	private LinearLayout noViewsLayout;
	private ShapeableImageView noViewsIcon;
	private TextView noViewsText;
	private TextView noViewsDescription;
	private TextView functionsTitle;
	private LinearLayout linear12;
	private RecyclerView functionsList;
	private LinearLayout noFunctionsLayout;
	private ShapeableImageView noFunctionsIcon;
	private TextView noFunctionsText;
	private TextView noFunctionsDescription;
	
	private Intent i = new Intent();
	private Intent intent = new Intent();
	private SharedPreferences prefs;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.event_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		eventFragmentActivity = this;
		_fab = _view.findViewById(R.id._fab);
		linear1 = _view.findViewById(R.id.linear1);
		mainContentLayout = _view.findViewById(R.id.mainContentLayout);
		sidebar = _view.findViewById(R.id.sidebar);
		contentScrollView = _view.findViewById(R.id.contentScrollView);
		linear11 = _view.findViewById(R.id.linear11);
		lifecycleEvents = _view.findViewById(R.id.lifecycleEvents);
		viewLifeCycle = _view.findViewById(R.id.viewLifeCycle);
		moreBlockLifeCycle = _view.findViewById(R.id.moreBlockLifeCycle);
		adLayout = _view.findViewById(R.id.adLayout);
		initializeLogicCard = _view.findViewById(R.id.initializeLogicCard);
		selectedEventsTitle = _view.findViewById(R.id.selectedEventsTitle);
		rvSelectedEvents = _view.findViewById(R.id.rvSelectedEvents);
		noEventsLayout = _view.findViewById(R.id.noEventsLayout);
		learnMoreSection = _view.findViewById(R.id.learnMoreSection);
		linear5 = _view.findViewById(R.id.linear5);
		linear2 = _view.findViewById(R.id.linear2);
		actionLayout = _view.findViewById(R.id.actionLayout);
		imageview1 = _view.findViewById(R.id.imageview1);
		linear3 = _view.findViewById(R.id.linear3);
		arrowIcon = _view.findViewById(R.id.arrowIcon);
		textview2 = _view.findViewById(R.id.textview2);
		textview1 = _view.findViewById(R.id.textview1);
		removeLayout = _view.findViewById(R.id.removeLayout);
		deleteLayout = _view.findViewById(R.id.deleteLayout);
		imageview10 = _view.findViewById(R.id.imageview10);
		textview10 = _view.findViewById(R.id.textview10);
		imageview11 = _view.findViewById(R.id.imageview11);
		textview11 = _view.findViewById(R.id.textview11);
		noEventsIcon = _view.findViewById(R.id.noEventsIcon);
		noEventsText = _view.findViewById(R.id.noEventsText);
		noEventsHint = _view.findViewById(R.id.noEventsHint);
		learnMoreBtn = _view.findViewById(R.id.learnMoreBtn);
		tipsCard = _view.findViewById(R.id.tipsCard);
		tipsLayout = _view.findViewById(R.id.tipsLayout);
		tipsTitle = _view.findViewById(R.id.tipsTitle);
		tip1 = _view.findViewById(R.id.tip1);
		tip2 = _view.findViewById(R.id.tip2);
		tip3 = _view.findViewById(R.id.tip3);
		tip4 = _view.findViewById(R.id.tip4);
		viewsTitle = _view.findViewById(R.id.viewsTitle);
		viewsList = _view.findViewById(R.id.viewsList);
		noViewsLayout = _view.findViewById(R.id.noViewsLayout);
		noViewsIcon = _view.findViewById(R.id.noViewsIcon);
		noViewsText = _view.findViewById(R.id.noViewsText);
		noViewsDescription = _view.findViewById(R.id.noViewsDescription);
		functionsTitle = _view.findViewById(R.id.functionsTitle);
		linear12 = _view.findViewById(R.id.linear12);
		functionsList = _view.findViewById(R.id.functionsList);
		noFunctionsLayout = _view.findViewById(R.id.noFunctionsLayout);
		noFunctionsIcon = _view.findViewById(R.id.noFunctionsIcon);
		noFunctionsText = _view.findViewById(R.id.noFunctionsText);
		noFunctionsDescription = _view.findViewById(R.id.noFunctionsDescription);
		prefs = getContext().getSharedPreferences("AppSettings", Activity.MODE_PRIVATE);
		
		linear2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				try {
					
					Qm qm = getSession();
					
					String activity = "";
					String layout = "";
					
					if (qm == null) {
						android.util.Log.e("RedirectDebug", "Session (qm) is NULL");
						android.widget.Toast.makeText(getContext().getApplicationContext(), "Session is null", android.widget.Toast.LENGTH_LONG).show();
						return;
					}
					Intent i = new Intent(getContext().getApplicationContext(),
					com.nexusteam.internal.editor.LogicEditorActivity.class);
					
					i.putExtra("event", "initializeLogic");
					i.putExtra("sc_id", sc_id);
					i.putExtra("id", "onCreate");
					i.putExtra("event_text", "On activity create");
					i.putExtra("filename", activityName);
					i.putExtra("activityName", activityName);
					i.putExtra("project_file", getFileBean());
					startActivity(i);
				} catch (Exception e) {
					
					android.util.Log.e("RedirectDebug", "Unknown error: " + e);
					android.widget.Toast.makeText(getContext().getApplicationContext(), e.toString(), android.widget.Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		removeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				ProjectLogicRepository.removeBlockLogic(activityName);
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_setUpFAB();
			}
		});
	}
	
	private void initializeLogic() {
		Qm qm = getSession();
		if (qm != null) {
			String activity = qm.getActivityName();
			String layout = qm.getLayoutName();
			activityName = activity;
		}
		
		rvSelectedEvents.setNestedScrollingEnabled(false);
		rvSelectedEvents.setLayoutManager(new LinearLayoutManager(getContext()));
		designAC = (DesignActivity) requireActivity();
		sc_id = requireActivity().getIntent().getStringExtra("sc_id");
		currentActivityBean = new ProjectActivityBean(
		activityName, // activityName
		"",         // layoutName
		"", // packageName
		true,           // isMainActivity
		sc_id,
		""     // projectName
		);
		event_name = activityName;
		
		BlockStorage.setScId(sc_id);
		//addBlockPopup = new AddBlockPopup(requireActivity(), activityName, activityName);
		com.besome.blacklogics.logic.editor.DesignDataManager.initialize(requireActivity(), sc_id);
		File dir = new File(FileHandler.codeSavePath + "/" + sc_id);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		ProjectActivityManager.getInstance(requireActivity(), sc_id).loadFromFile(requireActivity(), sc_id);
		displaySelectedEvents();
		sidebar.addItem(new SideBarVertical.SidebarItem(R.drawable.icon_recycling_round, "Activity", "View recent activity"));
		sidebar.addItem(new SideBarVertical.SidebarItem(R.drawable.icon_view_column_round, "View", "Switch views"));
		// sidebar.addItem(new SideBarVertical.SidebarItem(android.R.drawable.ic_menu_manage, "Component", "Manage components"));
		// sidebar.addItem(new SideBarVertical.SidebarItem(android.R.drawable.ic_menu_sort_by_size, "Drawer", "Open drawer"));
		sidebar.addItem(new SideBarVertical.SidebarItem(R.drawable.block, "Moreblock", "More options"));
		
		sidebar.setOnItemClickListener(new SideBarVertical.OnItemClickListener() {
			@Override
			public void onItemClick(SideBarVertical.SidebarItem item, int position) {
				
				lifecycleEvents.setVisibility(View.GONE);
				viewLifeCycle.setVisibility(View.GONE);
				moreBlockLifeCycle.setVisibility(View.GONE);
				
				currentSidebarPosition = position;
				
				switch (position) {
					case 0:
					lifecycleEvents.setVisibility(View.VISIBLE);
					break;
					
					case 1:
					viewLifeCycle.setVisibility(View.VISIBLE);
					break;
					
					case 2:
					moreBlockLifeCycle.setVisibility(View.VISIBLE);
					break;
				}
			}
		});
		
		viewsList.setLayoutManager(new LinearLayoutManager(getContext()));
		
		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir()
		.concat("/.blacklogics/data/".concat(sc_id.concat("/basedata"))))) {
			
			list_widget_map = new Gson().fromJson(
			FileUtil.readFile(FileUtil.getExternalStorageDir()
			.concat("/.blacklogics/data/".concat(sc_id.concat("/basedata")))),
			new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType()
			);
			
			ArrayList<HashMap<String, Object>> filtered = 
			getFilteredWidgets(activityName);
			
			viewsList.setAdapter(new ViewsListAdapter(filtered));
		}
		
		learnMoreBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toggleTipsCard();
			}
		});
		adLayout.loadBannerAd();
		_addFunctions();
		_setupToggle();
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		_addFunctions();
	}
	public void _a() {
	}
	public static void saveEventData(String sc_id, String eventName, JSONObject eventData) {
		EventUtils.saveEventData(sc_id, eventName, eventData);
	}
	private void loadEventsList() {
		HashMap<String, JSONObject> allEvents = EventUtils.loadAllEvents(sc_id);
		
		for (Map.Entry<String, JSONObject> entry : allEvents.entrySet()) {
			final String eventKey = entry.getKey();
			JSONObject eventObj = entry.getValue();
			
			if (eventObj != null) {
				final String activityName = eventObj.optString("event_name", "UnknownActivity");
				
				TextView tv = new TextView(requireContext());
				tv.setText(eventKey + " (" + activityName + ")");
				tv.setPadding(20, 10, 20, 10);
				tv.setTextSize(16);
				
				tv.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(getContext(), LogicEditorActivity.class);
						i.putExtra("event_name", eventKey);
						i.putExtra("sc_id", sc_id);
						i.putExtra("is_new_event", false);
						i.putExtra("activity_name", activityName);
						startActivity(i);
					}
				});
				
				// Add to your layout
				// linear4.addView(tv);
			}
		}
	}
	public void showEventSelectionDialog() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View dialogView = inflater.inflate(R.layout.dialog_event_selection, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setView(dialogView);
		
		RecyclerView rvWidgets = dialogView.findViewById(R.id.rv_widgets);
		Spinner spinnerEventType = dialogView.findViewById(R.id.spinner_event_type);
		Button btnSelect = dialogView.findViewById(R.id.btn_select);
		
		List<String> widgetIds = new ArrayList<>();
		for (int i = 0; i < ViewEditorFragmentActivity.ll.getChildCount(); i++) {
			View view = ViewEditorFragmentActivity.ll.getChildAt(i);
			if (view != null && view.getId() != View.NO_ID) {
				String id = getResources().getResourceEntryName(view.getId());
				widgetIds.add(id);
			}
		}
		
		rvWidgets.setLayoutManager(new LinearLayoutManager(getContext()));
		final WidgetAdapter adapter = new WidgetAdapter(widgetIds);
		rvWidgets.setAdapter(adapter);
		
		final AlertDialog dialog = builder.create();
		
		btnSelect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		
		dialog.show();
	}
	
	private static class WidgetAdapter extends RecyclerView.Adapter<WidgetAdapter.ViewHolder> {
		private List<String> widgetIds;
		private List<String> selectedWidgets;
		
		public WidgetAdapter(List<String> widgetIds) {
			this.widgetIds = widgetIds;
			this.selectedWidgets = new ArrayList<>();
		}
		
		public List<String> getSelectedWidgets() {
			return new ArrayList<>(selectedWidgets);
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.item_widget, parent, false);
			return new ViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			final String widgetId = widgetIds.get(position);
			holder.tvWidgetId.setText(widgetId);
			holder.cbWidget.setChecked(selectedWidgets.contains(widgetId));
			
			holder.cbWidget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						if (!selectedWidgets.contains(widgetId)) {
							selectedWidgets.add(widgetId);
						}
					} else {
						selectedWidgets.remove(widgetId);
					}
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return widgetIds.size();
		}
		
		static class ViewHolder extends RecyclerView.ViewHolder {
			TextView tvWidgetId;
			CheckBox cbWidget;
			
			ViewHolder(View itemView) {
				super(itemView);
				tvWidgetId = itemView.findViewById(R.id.tv_widget_id);
				cbWidget = itemView.findViewById(R.id.cb_widget);
			}
		}
	}
	
	public void displaySelectedEvents() {
		if (getActivity() == null) {
			return;
		}
		
		// Get session and activity name
		Qm qm = getSession();
		if (qm != null) {
			activityName = qm.getActivityName();
		}
		
		List<String> selectionList = EventUtils.loadLifecycleEvents(sc_id, activityName);
		
		// Create adapter
		LifecycleEventAdapter adapter = new LifecycleEventAdapter(
		getContext(), 
		selectionList, 
		myData, 
		sc_id, 
		currentActivityBean, 
		qm, 
		this
		);
		
		rvSelectedEvents.setAdapter(adapter);
		updateEmptyStates();
	}
	/**
 * Adapter for RecyclerView to display selected widgets and their assigned events.
 */
	private static class SelectedEventAdapter extends RecyclerView.Adapter<SelectedEventAdapter.ViewHolder> {
		private List<Map.Entry<String, String>> selections;
		
		public SelectedEventAdapter(List<Map.Entry<String, String>> selections) {
			this.selections = selections;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.item_selected_event, parent, false);
			return new ViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			Map.Entry<String, String> selection = selections.get(position);
			holder.tvWidget.setText(selection.getKey());
			holder.tvEvent.setText(selection.getValue());
		}
		
		@Override
		public int getItemCount() {
			return selections.size();
		}
		
		static class ViewHolder extends RecyclerView.ViewHolder {
			TextView tvWidget;
			TextView tvEvent;
			
			ViewHolder(View itemView) {
				super(itemView);
				tvWidget = itemView.findViewById(R.id.tv_selected_widget);
				tvEvent = itemView.findViewById(R.id.tv_selected_event);
			}
		}
	}
	private boolean arrayContains(JSONArray array, String value) {
		for (int i = 0; i < array.length(); i++) {
			if (array.optString(i).equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	private void saveSelectedLifecycleEvents(List<String> events) {
		boolean success = EventUtils.saveLifecycleEvents(sc_id, activityName, events);
		if (!success) {
			SketchwareUtil.showMessage(getContext(), "Error saving events");
		}
	}
	private void showLifecycleEventDialog() {
		
		// Already saved events — inhe list mein nahi dikhana
		final List<String> alreadyAdded = EventUtils.loadLifecycleEvents(sc_id, activityName);
		
		// Filter karo — sirf wo events jo abhi tak add nahi hue
		final List<String> filteredEvents = new ArrayList<String>();
		for (String ev : EventUtils.getLifecycleEvents()) {
			if (!alreadyAdded.contains(ev)) {
				filteredEvents.add(ev);
			}
		}
		
		if (filteredEvents.isEmpty()) {
			android.widget.Toast.makeText(getContext(),
			"All events already added", android.widget.Toast.LENGTH_SHORT).show();
			return;
		}
		
		// Selected events track karne ke liye
		final List<String> selectedEvents = new ArrayList<String>();
		
		// Dialog view — RecyclerView + buttons
		final LinearLayout root = new LinearLayout(getContext());
		root.setOrientation(LinearLayout.VERTICAL);
		
		// Title
		TextView tvTitle = new TextView(getContext());
		tvTitle.setText("Activity");
		tvTitle.setTextSize(18f);
		tvTitle.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
		tvTitle.setTextColor(0xFF4CAF50);
		final int p16 = (int)(16 * getResources().getDisplayMetrics().density);
		final int p12 = (int)(12 * getResources().getDisplayMetrics().density);
		final int p8  = (int)(8  * getResources().getDisplayMetrics().density);
		tvTitle.setPadding(p16, p12, p16, p8);
		root.addView(tvTitle);
		
		// RecyclerView
		final RecyclerView rv = new RecyclerView(getContext());
		rv.setLayoutManager(new LinearLayoutManager(getContext()));
		rv.setLayoutParams(new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.MATCH_PARENT,
		(int)(420 * getResources().getDisplayMetrics().density)));
		
		final RecyclerView.Adapter dialogAdapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
			
			// ViewHolder — programmatic row
			class VH extends RecyclerView.ViewHolder {
				TextView tvName, tvDesc;
				android.widget.CheckBox checkBox;
				
				VH(LinearLayout row) {
					super(row);
					// icon placeholder
					TextView icon = new TextView(row.getContext());
					icon.setText("<>");
					icon.setTextColor(0xFF888888);
					icon.setTypeface(android.graphics.Typeface.MONOSPACE);
					icon.setTextSize(12f);
					int s = (int)(36 * getResources().getDisplayMetrics().density);
					LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(s, s);
					icon.setLayoutParams(iconLp);
					icon.setGravity(android.view.Gravity.CENTER);
					row.addView(icon);
					
					// text column
					LinearLayout col = new LinearLayout(row.getContext());
					col.setOrientation(LinearLayout.VERTICAL);
					LinearLayout.LayoutParams colLp =
					new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
					colLp.setMargins(p8, 0, 0, 0);
					col.setLayoutParams(colLp);
					
					tvName = new TextView(row.getContext());
					tvName.setTextSize(14f);
					tvName.setTextColor(0xFFFFFFFF);
					tvName.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
					col.addView(tvName);
					
					tvDesc = new TextView(row.getContext());
					tvDesc.setTextSize(12f);
					tvDesc.setTextColor(0xFF888888);
					col.addView(tvDesc);
					
					row.addView(col);
					
					// checkbox
					checkBox = new android.widget.CheckBox(row.getContext());
					checkBox.setClickable(false);
					checkBox.setFocusable(false);
					row.addView(checkBox);
				}
			}
			
			@Override
			public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				LinearLayout row = new LinearLayout(parent.getContext());
				row.setOrientation(LinearLayout.HORIZONTAL);
				row.setGravity(android.view.Gravity.CENTER_VERTICAL);
				row.setPadding(p16, p12, p16, p12);
				row.setLayoutParams(new RecyclerView.LayoutParams(
				RecyclerView.LayoutParams.MATCH_PARENT,
				RecyclerView.LayoutParams.WRAP_CONTENT));
				return new VH(row);
			}
			
			@Override
			public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
				final VH holder = (VH) vh;
				final String eventName = filteredEvents.get(position);
				
				// "Activity: eventName" prefix — var field se
				String prefix = "Activity";
				EventUtils.SystemEvent sysEv = EventUtils.findSystemEventByName(eventName);
				if (sysEv != null && !sysEv.var.isEmpty()) {
					prefix = sysEv.var;
				}
				holder.tvName.setText(prefix + ": " + eventName);
				holder.tvDesc.setText(EventUtils.getEventDescription(eventName));
				holder.checkBox.setChecked(selectedEvents.contains(eventName));
				
				holder.itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int pos = holder.getAdapterPosition();
						if (pos < 0) return;
						String ev = filteredEvents.get(pos);
						if (selectedEvents.contains(ev)) {
							selectedEvents.remove(ev);
						} else {
							selectedEvents.add(ev);
						}
						notifyItemChanged(pos);
					}
				});
			}
			
			@Override
			public int getItemCount() { return filteredEvents.size(); }
		};
		
		rv.setAdapter(dialogAdapter);
		root.addView(rv);
		
		// Divider
		View divider = new View(getContext());
		divider.setBackgroundColor(0x33FFFFFF);
		divider.setLayoutParams(new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.MATCH_PARENT, 1));
		root.addView(divider);
		
		// Button row
		LinearLayout btnRow = new LinearLayout(getContext());
		btnRow.setOrientation(LinearLayout.HORIZONTAL);
		btnRow.setPadding(p16, p8, p16, p8);
		
		android.widget.Button btnCancel = new android.widget.Button(getContext());
		btnCancel.setText("Cancel");
		LinearLayout.LayoutParams lp =
		new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
		lp.setMargins(0, 0, p8, 0);
		btnCancel.setLayoutParams(lp);
		
		android.widget.Button btnAdd = new android.widget.Button(getContext());
		btnAdd.setText("Add");
		btnAdd.setBackgroundColor(0xFFB9F6CA);
		btnAdd.setTextColor(0xFF000000);
		btnAdd.setLayoutParams(
		new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
		
		btnRow.addView(btnCancel);
		btnRow.addView(btnAdd);
		root.addView(btnRow);
		
		// Show dialog
		final android.app.AlertDialog dialog =
		new android.app.AlertDialog.Builder(requireContext())
		.setView(root)
		.create();
		
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { dialog.dismiss(); }
		});
		
		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!selectedEvents.isEmpty()) {
					saveSelectedLifecycleEvents(selectedEvents);
					displaySelectedEvents();
				}
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	private static class LifecycleEventAdapter extends RecyclerView.Adapter<LifecycleEventAdapter.ViewHolder> {
		
		private List<String> events;
		private Context context;
		private String myData;
		private String sc_id;
		private ProjectActivityBean bean;
		private Qm session;
		private EventFragmentActivity e;
		
		// Add this to track expanded states
		private boolean[] expandedStates;
		
		public LifecycleEventAdapter(Context context, List<String> events, String myData, String sc_id, ProjectActivityBean bean, Qm session, EventFragmentActivity e) {
			this.context = context;
			this.events = events;
			this.myData = myData;
			this.sc_id = sc_id;
			this.bean = bean;
			this.session = session;
			this.e = e;
			
			// Initialize all as collapsed
			this.expandedStates = new boolean[events.size()];
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.mathod_ui, parent, false); // Make sure to use parent for proper layout params
			return new ViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(final ViewHolder holder, final int position) {
			
			final String eventName = events.get(position);
			
			holder.eventName.setText(eventName);
			holder.eventDescription.setText(EventUtils.getEventDescription(eventName));
			
			// Set initial visibility based on expanded state
			if (expandedStates[position]) {
				holder.actionLayout.setVisibility(View.VISIBLE);
				holder.arrowIcon.setRotation(180f);
			} else {
				holder.actionLayout.setVisibility(View.GONE);
				holder.arrowIcon.setRotation(0f);
			}
			
			// header click toggle
			holder.arrowIcon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					if (holder.actionLayout.getVisibility() == View.GONE) {
						
						// Update expanded state
						expandedStates[position] = true;
						
						holder.actionLayout.setVisibility(View.VISIBLE);
						
						holder.arrowIcon.animate()
						.rotation(180f)
						.setDuration(200)
						.start();
						
					} else {
						
						// Update expanded state
						expandedStates[position] = false;
						
						holder.actionLayout.setVisibility(View.GONE);
						
						holder.arrowIcon.animate()
						.rotation(0f)
						.setDuration(200)
						.start();
					}
				}
			});
			
			// open logic editor
			holder.headerLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					Intent intent = EventUtils.createEventIntent(
					context,
					eventName,
					holder.eventDescription.getText().toString(),
					activityName,
					sc_id,
					session,
					e.getFileBean()
					);
					
					context.startActivity(intent);
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return events.size();
		}
		
		static class ViewHolder extends RecyclerView.ViewHolder {
			
			TextView eventName;
			TextView eventDescription;
			
			LinearLayout headerLayout;
			LinearLayout actionLayout;
			
			ImageView arrowIcon;
			
			ViewHolder(View itemView) {
				super(itemView);
				
				eventName = itemView.findViewById(R.id.textview2);
				eventDescription = itemView.findViewById(R.id.textview1);
				
				headerLayout = itemView.findViewById(R.id.linear2);
				actionLayout = itemView.findViewById(R.id.actionLayout);
				
				arrowIcon = itemView.findViewById(R.id.arrowIcon);
			}
		}
	}
	private ArrayList<HashMap<String, Object>> getFilteredWidgets(String activityName) {
		ArrayList<HashMap<String, Object>> filtered = new ArrayList<>();
		if (list_widget_map != null) {
			for (HashMap<String, Object> item : list_widget_map) {
				if (activityName.equals(item.get("activityName"))) {
					filtered.add(item);
				}
			}
		}
		return filtered;
	}
	
	private Qm getSession() {
		if (getActivity() instanceof com.besome.blacklogics.lib.base.BaseActivity) {
			return ((com.besome.blacklogics.lib.base.BaseActivity) getActivity()).activitySession;
		}
		return null;
	}
	private String getProjectDir() {
		if (getActivity() instanceof com.besome.blacklogics.lib.base.BaseActivity) {
			return ((com.besome.blacklogics.lib.base.BaseActivity) getActivity()).getProjectPath();
		}
		return null;
	}
	private com.nexusteam.internal.beans.ProjectFileBean getFileBean() {
		if (getActivity() instanceof com.besome.blacklogics.lib.base.BaseActivity) {
			return ((com.besome.blacklogics.lib.base.BaseActivity) getActivity()).fileBean;
		}
		return null;
	}
	private void toggleTipsCard() {
		if (isTipsVisible) {
			// Hide tips card with animation
			collapseView(tipsCard);
			learnMoreBtn.setText("Learn more about events");
			learnMoreBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more, 0);
		} else {
			// Show tips card with animation
			expandView(tipsCard);
			learnMoreBtn.setText("Show less");
			learnMoreBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_expand_less, 0);
		}
		isTipsVisible = !isTipsVisible;
	}
	
	// Animation methods
	private void expandView(final View v) {
		v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		final int targetHeight = v.getMeasuredHeight();
		
		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		
		ValueAnimator animator = ValueAnimator.ofInt(0, targetHeight);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				v.getLayoutParams().height = (int) animation.getAnimatedValue();
				v.requestLayout();
			}
		});
		
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				v.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
			}
		});
		
		animator.setDuration(300);
		animator.start();
	}
	
	private void collapseView(final View v) {
		final int initialHeight = v.getMeasuredHeight();
		
		ValueAnimator animator = ValueAnimator.ofInt(initialHeight, 0);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				v.getLayoutParams().height = (int) animation.getAnimatedValue();
				v.requestLayout();
			}
		});
		
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				v.setVisibility(View.GONE);
			}
		});
		
		animator.setDuration(300);
		animator.start();
	}
	
	// Add this method to update empty states visibility
	private void updateEmptyStates() {
		// For lifecycle events
		if (rvSelectedEvents.getAdapter() == null || rvSelectedEvents.getAdapter().getItemCount() == 0) {
			rvSelectedEvents.setVisibility(View.GONE);
			noEventsLayout.setVisibility(View.VISIBLE);
		} else {
			rvSelectedEvents.setVisibility(View.VISIBLE);
			noEventsLayout.setVisibility(View.GONE);
		}
		
		// For views
		if (viewsList.getAdapter() == null || viewsList.getAdapter().getItemCount() == 0) {
			viewsList.setVisibility(View.GONE);
			noViewsLayout.setVisibility(View.VISIBLE);
		} else {
			viewsList.setVisibility(View.VISIBLE);
			noViewsLayout.setVisibility(View.GONE);
		}
		
		// For functions
		if (functionsList.getAdapter() == null || functionsList.getAdapter().getItemCount() == 0) {
			functionsList.setVisibility(View.GONE);
			noFunctionsLayout.setVisibility(View.VISIBLE);
		} else {
			functionsList.setVisibility(View.VISIBLE);
			noFunctionsLayout.setVisibility(View.GONE);
		}
	}
	
	private void showMoreBlockCreationDialog() {
		try {
			Intent intent = new Intent(getContext(), MakeBlockActivity.class);
			intent.putExtra("sc_id", sc_id);
			intent.putExtra("activityName", activityName + ".java");
			intent.putExtra("project_file", getFileBean());
			startActivityForResult(intent, 222);
		} catch (Exception e) {
			Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String var4 = data.getStringExtra("block_name");
		String var5 = data.getStringExtra("block_spec");
		if (requestCode == 222 && resultCode == Activity.RESULT_OK) {
			ma.a(sc_id).c(activityName + ".java", var4, var5);
			ArrayList<Pair<String, String>> list =
			BlockStorage.loadFunctions(activityName + ".java");
			
			list.add(new Pair<>(var4, var5));
			
			BlockStorage.saveFunctions(activityName + ".java", list);
			loadMoreBlockCollections();
			setupMoreBlockList();
		}
	}
	private void loadMoreBlockCollections() {
		setupMoreBlockList();
	}
	
	public class MoreBlockAdapter extends RecyclerView.Adapter<MoreBlockAdapter.ViewHolder> {
		
		private ArrayList<MoreBlockCollectionBean> data;
		
		public MoreBlockAdapter(ArrayList<MoreBlockCollectionBean> arr) {
			this.data = arr != null ? arr : new ArrayList<MoreBlockCollectionBean>();
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = getActivity().getLayoutInflater().inflate(R.layout.cus_item_functions, parent, false);
			return new ViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder holder, final int position) {
			final MoreBlockCollectionBean item = data.get(position);
			
			holder.functionName.setText(item.name);
			
			holder.linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getContext(), LogicEditorActivity.class);
					intent.putExtra("id", item.name);
					intent.putExtra("event", "moreBlock");
					intent.putExtra("event_text", item.spec);
					intent.putExtra("filename", activityName);
					intent.putExtra("sc_id", sc_id);
					intent.putExtra("activityName", activityName);
					intent.putExtra("type", "definedFunc");
					startActivity(intent);
				}
			});
			
			holder.linear1.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					showDeleteDialog(item, position);
					return true;
				}
			});
		}
		
		private void showDeleteDialog(final MoreBlockCollectionBean item, final int position) {
			new AlertDialog.Builder(getContext())
			.setTitle("Delete Moreblock")
			.setMessage("Delete '" + item.name + "'?")
			.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					deleteMoreBlock(item, position);
				}
			})
			.setNegativeButton("Cancel", null)
			.show();
		}
		
		private void deleteMoreBlock(MoreBlockCollectionBean item, int position) {
			// Load existing functions
			ArrayList<Pair<String, String>> functions = BlockStorage.loadFunctions(activityName);
			ArrayList<Pair<String, String>> newFunctions = new ArrayList<>();
			
			// Remove the function
			for (Pair<String, String> func : functions) {
				if (!func.first.equals(item.name)) {
					newFunctions.add(func);
				}
			}
			
			// Save updated functions
			BlockStorage.saveFunctions(activityName, newFunctions);
			
			// Clear blocks for this function
			ArrayList<BlockBean> emptyBlocks = new ArrayList<>();
			BlockStorage.save(activityName + "_" + item.name, emptyBlocks);
			
			// Remove from local data
			data.remove(position);
			notifyItemRemoved(position);
			
			Toast.makeText(getContext(), "Deleted: " + item.name, Toast.LENGTH_SHORT).show();
			
			// Reload list
			loadMoreBlockCollections();
			setupMoreBlockList();
		}
		
		@Override
		public int getItemCount() {
			return data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			LinearLayout linear1;
			TextView functionName;
			
			public ViewHolder(View v) {
				super(v);
				linear1 = v.findViewById(R.id.linear1);
				functionName = v.findViewById(R.id.functionName);
			}
		}
	}
	private void setupMoreBlockList() {
		if (functionsList == null) return;
		
		// Directly load functions from BlockStorage
		ArrayList<Pair<String, String>> functions = BlockStorage.loadFunctions(activityName + ".java");
		
		// Use simple adapter
		functionsList.setLayoutManager(new LinearLayoutManager(getContext()));
		functionsList.setAdapter(new SimpleMoreBlockAdapter(functions));
		updateEmptyStates();
	}
	
	// Simple adapter class
	public class SimpleMoreBlockAdapter extends RecyclerView.Adapter<SimpleMoreBlockAdapter.ViewHolder> {
		
		private ArrayList<Pair<String, String>> functions;
		
		public SimpleMoreBlockAdapter(ArrayList<Pair<String, String>> functions) {
			this.functions = functions != null 
			? functions 
			: new ArrayList<Pair<String, String>>();
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = getActivity().getLayoutInflater().inflate(R.layout.cus_item_functions, parent, false);
			return new ViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder holder, final int position) {
			final Pair<String, String> function = functions.get(position);
			
			holder.functionName.setText(function.first);
			
			holder.linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getContext(), LogicEditorActivity.class);
					intent.putExtra("id", function.first);
					intent.putExtra("event", "moreBlock");
					intent.putExtra("event_text", function.second);
					intent.putExtra("filename", activityName);
					intent.putExtra("sc_id", sc_id);
					intent.putExtra("activityName", activityName);
					intent.putExtra("project_file", getFileBean());
					intent.putExtra("type", function.first);
					startActivity(intent);
				}
			});
			
			holder.linear1.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					showDeleteDialog(function, position);
					return true;
				}
			});
		}
		
		private void showDeleteDialog(final Pair<String, String> function, final int position) {
			new AlertDialog.Builder(getContext())
			.setTitle("Delete Moreblock")
			.setMessage("Delete '" + function.first + "'?")
			.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					deleteMoreBlock(function, position);
				}
			})
			.setNegativeButton("Cancel", null)
			.show();
		}
		
		private void deleteMoreBlock(Pair<String, String> function, int position) {
			// Load and update functions
			ArrayList<Pair<String, String>> currentFunctions = BlockStorage.loadFunctions(activityName + ".java");
			ArrayList<Pair<String, String>> newFunctions = new ArrayList<>();
			
			for (Pair<String, String> func : currentFunctions) {
				if (!func.first.equals(function.first)) {
					newFunctions.add(func);
				}
			}
			
			// Save updated functions
			BlockStorage.saveFunctions(activityName + ".java", newFunctions);
			
			// Clear blocks
			//  BlockStorage.save(activityName + "_" + function.first, new ArrayList<>());
			
			// Update UI
			functions.remove(position);
			notifyItemRemoved(position);
			
			Toast.makeText(getContext(), "Deleted: " + function.first, Toast.LENGTH_SHORT).show();
			
			// Refresh if needed
			if (functions.isEmpty()) {
				setupMoreBlockList();
			}
		}
		
		@Override
		public int getItemCount() {
			return functions.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			LinearLayout linear1;
			TextView functionName;
			
			public ViewHolder(View v) {
				super(v);
				linear1 = v.findViewById(R.id.linear1);
				functionName = v.findViewById(R.id.functionName);
			}
		}
	}
	{
	}
	
	
	public void _setUpFAB() {
		switch((int)currentSidebarPosition) {
			case ((int)0): {
				showLifecycleEventDialog();
				break;
			}
			case ((int)1): {
				 
				break;
			}
			case ((int)2): {
				showMoreBlockCreationDialog();
				break;
			}
		}
	}
	
	
	public void _addFunctions() {
		setupMoreBlockList();
	}
	
	
	public void _setupToggle() {
		final ViewGroup parent = (ViewGroup) actionLayout.getParent();
		arrowIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				android.transition.AutoTransition transition = new android.transition.AutoTransition();
				transition.setDuration(200);
				
				android.transition.TransitionManager.beginDelayedTransition(parent, transition);
				
				if (actionLayout.getVisibility() == View.GONE) {
					actionLayout.setVisibility(View.VISIBLE);
					arrowIcon.animate().rotation(180f).setDuration(200).start();
				} else {
					actionLayout.setVisibility(View.GONE);
					arrowIcon.animate().rotation(0f).setDuration(200).start();
				}
				
			}
		});
	}
	
	public class ViewsListAdapter extends RecyclerView.Adapter<ViewsListAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public ViewsListAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.views_click_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final com.google.android.material.card.MaterialCardView cardview2 = _view.findViewById(R.id.cardview2);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView widget_type_icon = _view.findViewById(R.id.widget_type_icon);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final ImageView imageview8 = _view.findViewById(R.id.imageview8);
			final TextView widget_classname = _view.findViewById(R.id.widget_classname);
			final TextView widget_id = _view.findViewById(R.id.widget_id);
			final TextView widget_listener = _view.findViewById(R.id.widget_listener);
			
			Map<String, Object> item = _data.get(_position);
			
			// Use EventUtils for safe value extraction
			widget_listener.setText(EventUtils.getValue(item, "widget_listener_name", "onClick"));
			widget_id.setText(EventUtils.getValue(item, "widget_id", "textview1"));
			
			String widgetType = EventUtils.getValue(item, "widget_type", "TextView");
			widget_classname.setText(widgetType);
			
			// Use EventUtils for widget icon
			widget_type_icon.setImageResource(EventUtils.getWidgetIcon(widgetType));
			
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class FunctionsListAdapter extends RecyclerView.Adapter<FunctionsListAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public FunctionsListAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.cus_item_functions, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final TextView functionName = _view.findViewById(R.id.functionName);
			
			HashMap<String, Object> item = _data.get(_position);
			
			final String name = item.get("name").toString();
			final String spec = item.get("spec").toString();
			
			functionName.setText(name);
			
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Qm qm = getSession();
					String activity = "";
					String layout = "";
					if (qm != null) {
						activity = qm.getActivityName();
						layout = qm.getLayoutName();
						activityName = activity;
					}
					
					Intent i = new Intent(getContext(), LogicEditorActivity.class);
					i.putExtra("id", name);
					i.putExtra("event", spec);
					i.putExtra("event_text", "moreBlock");
					i.putExtra("filename", activityName);
					i.putExtra("sc_id", sc_id);
					i.putExtra("activityName", activityName);
					
					if (qm != null) {
						i.putExtra("qm_session", qm);
					}
					
					//i.putExtra("widgetid", "");
					i.putExtra("type", "definedFunc");
					startActivity(i);
				}
			});
			
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
}