package com.besome.blacklogics;

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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.*;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.besome.blacklogics.custom.SideBarVertical;
import com.besome.sketch.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
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
import com.besome.blacklogics.logic.editor.LogicEditorActivity;
import com.besome.blacklogics.util.FileHandler;
import com.besome.blacklogics.util.ProjectActivityManager;
import android.util.Base64;
import androidx.appcompat.app.AppCompatDelegate;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.besome.blacklogics.project.ProjectDataHelper;
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
	
	private ArrayList<HashMap<String, Object>> list_widget_map = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear4;
	private LinearLayout linear9;
	private SideBarVertical sidebar;
	private LinearLayout linear11;
	private LinearLayout lifecycleEvents;
	private LinearLayout viewLifeCycle;
	private LinearLayout moreBlockLifeCycle;
	private LinearLayout linear2;
	private RecyclerView rvSelectedEvents;
	private ImageView imageview1;
	private LinearLayout linear3;
	private TextView textview2;
	private TextView textview1;
	private RecyclerView viewsList;
	
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
		linear4 = _view.findViewById(R.id.linear4);
		linear9 = _view.findViewById(R.id.linear9);
		sidebar = _view.findViewById(R.id.sidebar);
		linear11 = _view.findViewById(R.id.linear11);
		lifecycleEvents = _view.findViewById(R.id.lifecycleEvents);
		viewLifeCycle = _view.findViewById(R.id.viewLifeCycle);
		moreBlockLifeCycle = _view.findViewById(R.id.moreBlockLifeCycle);
		linear2 = _view.findViewById(R.id.linear2);
		rvSelectedEvents = _view.findViewById(R.id.rvSelectedEvents);
		imageview1 = _view.findViewById(R.id.imageview1);
		linear3 = _view.findViewById(R.id.linear3);
		textview2 = _view.findViewById(R.id.textview2);
		textview1 = _view.findViewById(R.id.textview1);
		viewsList = _view.findViewById(R.id.viewsList);
		prefs = getContext().getSharedPreferences("AppSettings", Activity.MODE_PRIVATE);
		
		linear2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (myData == null) myData = "";
				if (getActivity() instanceof DesignActivity) {
					DesignActivity designActivity = (DesignActivity) getActivity();
					
					intent.putExtra("id", "onCreate");
					intent.putExtra("event", "initializeLogic");
					intent.putExtra("event_text", "On activity create");
					intent.putExtra("filename", "initializeLogic");
					intent.putExtra("sc_id", sc_id);
					intent.putExtra("activityName", ProjectDataHelper.getActivityName(getContext().getApplicationContext()));
					intent.putExtra("widgetid", "");
					intent.putExtra("type", "");
					intent.setClass(getContext().getApplicationContext(), LogicEditorActivity.class);
					startActivity(intent);
				}
				
			}
		});
	}
	
	private void initializeLogic() {
		com.besome.blacklogics.logic.editor.DesignDataManager.initialize(requireActivity(), "");
		if (getActivity() instanceof DesignActivity) {
			DesignActivity designActivity = (DesignActivity) getActivity();
		}
		activityName = ProjectDataHelper.getActivityName(getActivity());
		designAC = (DesignActivity) requireActivity();
		sc_id = requireActivity().getIntent().getStringExtra("sc_id");
		currentActivityBean = new ProjectActivityBean(
		designAC.defaultAcName, // activityName
		"",         // layoutName
		"", // packageName
		true,           // isMainActivity
		sc_id,
		""     // projectName
		);
		event_name = ViewEditorFragmentActivity.activityName;
		
		File dir = new File(FileHandler.codeSavePath + "/" + sc_id);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		ProjectActivityManager.getInstance(requireActivity(), sc_id).loadFromFile(requireActivity(), sc_id);
		displaySelectedEvents();
		_fab.setOnClickListener(v -> showLifecycleEventDialog());
		sidebar.addItem(new SideBarVertical.SidebarItem(R.drawable.icon_recycling_round, "Activity", "View recent activity"));
		sidebar.addItem(new SideBarVertical.SidebarItem(R.drawable.icon_view_column_round, "View", "Switch views"));
		//sidebar.addItem(new SideBarVertical.SidebarItem(android.R.drawable.ic_menu_manage, "Component", "Manage components"));
		//sidebar.addItem(new SideBarVertical.SidebarItem(android.R.drawable.ic_menu_sort_by_size, "Drawer", "Open drawer"));
		sidebar.addItem(new SideBarVertical.SidebarItem(R.drawable.block, "Moreblock", "More options"));
		
		// Set click listener
		sidebar.setOnItemClickListener((item, position) -> {
			//Toast.makeText(MainActivity.this, "Clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
			if (position == 0) {
				lifecycleEvents.setVisibility(View.VISIBLE);
				viewLifeCycle.setVisibility(View.GONE);
				moreBlockLifeCycle.setVisibility(View.GONE);
			} else {
				if (position == 1) {
					lifecycleEvents.setVisibility(View.GONE);
					viewLifeCycle.setVisibility(View.VISIBLE);
					moreBlockLifeCycle.setVisibility(View.GONE);
				} else {
					if (position == 2) {
						lifecycleEvents.setVisibility(View.GONE);
						viewLifeCycle.setVisibility(View.GONE);
						moreBlockLifeCycle.setVisibility(View.VISIBLE);
					}
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
			getFilteredWidgets(currentActivityBean.getActivityName());
			
			viewsList.setAdapter(new ViewsListAdapter(filtered));
		}
		
	}
	
	public void _a() {
	}
	public static void saveEventData(String sc_id, String eventName, JSONObject eventData) {
		try {
			String path = FileHandler.codeSavePath + "/" + sc_id + "/events/events.json";
			File file = new File(path);
			JSONObject allEvents = new JSONObject();
			
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				allEvents = new JSONObject(decoded);
			}
			
			allEvents.put(eventName, eventData);
			String encoded = Base64.encodeToString(allEvents.toString().getBytes(), Base64.NO_WRAP);
			FileHandler.saveFile(path, encoded);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadEventsList() {
		try {
			String path = FileHandler.codeSavePath + "/" + sc_id + "/events/events.json";
			File file = new File(path);
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				JSONObject obj = new JSONObject(decoded);
				ArrayList<String> keys = new ArrayList<>();
				Iterator<String> iter = obj.keys();
				while (iter.hasNext()) {
					keys.add(iter.next());
				}
				
				for (String eventKey : keys) {
					JSONObject eventObj = obj.optJSONObject(eventKey);
					if (eventObj != null) {
						String activityName = eventObj.optString("event_name", "UnknownActivity");
						
						TextView tv = new TextView(requireContext());
						tv.setText(eventKey + " (" + activityName + ")");
						tv.setPadding(20, 10, 20, 10);
						tv.setTextSize(16);
						
						tv.setOnClickListener(v -> {
							Intent i = new Intent(getContext(), LogicEditorActivity.class);
							i.putExtra("event_name", eventKey);
							i.putExtra("sc_id", sc_id);
							i.putExtra("is_new_event", false);
							i.putExtra("activity_name", activityName);
							startActivity(i);
						});
						
						linear4.addView(tv);
					}
				}
			}
		} catch (Exception e) {
			SketchwareUtil.showMessage(getContext(), e.toString());
		}
	}
	
	/**
 * Shows a dialog to select multiple widgets and assign an event type (onClick, onLongClick, onTouch).
 * Stores selections in Complex class JSON and displays them in a new RecyclerView.
 */
	public void showEventSelectionDialog() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View dialogView = inflater.inflate(R.layout.dialog_event_selection, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setView(dialogView);
		
		RecyclerView rvWidgets = dialogView.findViewById(R.id.rv_widgets);
		Spinner spinnerEventType = dialogView.findViewById(R.id.spinner_event_type);
		Button btnSelect = dialogView.findViewById(R.id.btn_select);
		
		// Get all widgets from the current layout
		List<String> widgetIds = new ArrayList<>();
		for (int i = 0; i < ViewEditorFragmentActivity.ll.getChildCount(); i++) {
			View view = ViewEditorFragmentActivity.ll.getChildAt(i);
			if (view != null && view.getId() != View.NO_ID) {
				String id = getResources().getResourceEntryName(view.getId());
				widgetIds.add(id);
			}
		}
		
		// Set up RecyclerView for widgets with checkboxes
		rvWidgets.setLayoutManager(new LinearLayoutManager(getContext()));
		WidgetAdapter adapter = new WidgetAdapter(widgetIds);
		rvWidgets.setAdapter(adapter);
		
		AlertDialog dialog = builder.create();
		
		btnSelect.setOnClickListener(v -> {
			List<String> selectedWidgets = adapter.getSelectedWidgets();
			String eventType = spinnerEventType.getSelectedItem().toString();
			
			if (selectedWidgets.isEmpty()) {
				Toast.makeText(getContext(), "Please select at least one widget", Toast.LENGTH_SHORT).show();
				return;
			}
			
			// Store selections in Complex class
			String activityName = ViewEditorFragmentActivity.activityName;
			// DesignActivity.complex.storeWidgetEventSelections(activityName, selectedWidgets, eventType);
			
			// Add or update RecyclerView to display selected events
			//displaySelectedEvents();
			
			Toast.makeText(getContext(), "Selections saved", Toast.LENGTH_SHORT).show();
			dialog.dismiss();
		});
		
		dialog.show();
	}
	
	/**
 * Adapter for RecyclerView to display widget IDs with checkboxes for multiple selection.
 */
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
			String widgetId = widgetIds.get(position);
			holder.tvWidgetId.setText(widgetId);
			holder.cbWidget.setChecked(selectedWidgets.contains(widgetId));
			holder.cbWidget.setOnCheckedChangeListener((buttonView, isChecked) -> {
				if (isChecked) {
					if (!selectedWidgets.contains(widgetId)) {
						selectedWidgets.add(widgetId);
					}
				} else {
					selectedWidgets.remove(widgetId);
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
		if (getActivity() instanceof DesignActivity) {
			DesignActivity designActivity = (DesignActivity) getActivity();
			activityName = designActivity.activityName;
			activitySession = designActivity.activitySession;
		}
		
		rvSelectedEvents.setLayoutManager(new LinearLayoutManager(getContext()));
		List<String> selectionList = new ArrayList<>();
		
		try {
			String path = FileHandler.codeSavePath + "/" + sc_id + "/events/lifecycle_events.json";
			File file = new File(path);
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				JSONObject obj = new JSONObject(decoded);
				//String activityName = currentActivityBean.getActivityName();
				JSONArray events = obj.optJSONArray(activityName);
				
				if (events != null) {
					for (int i = 0; i < events.length(); i++) {
						selectionList.add(events.getString(i));
					}
				}
			}
		} catch (Exception e) {
			SketchwareUtil.showMessage(getContext(), e.toString());
		}
		//ProjectActivityBean bean = ProjectActivityManager.getInstance(requireActivity(), sc_id).getActivity(sc_id);
		LifecycleEventAdapter adapter = new LifecycleEventAdapter(getContext(), selectionList, myData, sc_id, currentActivityBean, activitySession);
		rvSelectedEvents.setAdapter(adapter);
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
	private void saveSelectedLifecycleEvents(List<String> events) {
		try {
			String path = FileHandler.codeSavePath + "/" + sc_id + "/events/lifecycle_events.json";
			File file = new File(path);
			JSONObject allEvents = new JSONObject();
			
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				allEvents = new JSONObject(decoded);
			}
			
			// String activityName = activityName;
			JSONArray existingArray = allEvents.optJSONArray(activityName);
			if (existingArray == null) {
				existingArray = new JSONArray();
			}
			
			// Duplicate avoid
			for (String event : events) {
				if (!arrayContains(existingArray, event)) {
					existingArray.put(event);
				}
			}
			
			allEvents.put(activityName, existingArray);
			
			String encoded = Base64.encodeToString(allEvents.toString().getBytes(), Base64.NO_WRAP);
			FileHandler.saveFile(path, encoded);
			
		} catch (Exception e) {
			SketchwareUtil.showMessage(getContext(), "Error: " + e.toString());
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
	
	private void showLifecycleEventDialog() {
		com.google.android.material.dialog.MaterialAlertDialogBuilder builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext());
		builder.setTitle("Select Lifecycle Events");
		
		boolean[] checkedItems = new boolean[LIFECYCLE_EVENTS.length];
		List<String> selectedEvents = new ArrayList<>();
		
		builder.setMultiChoiceItems(LIFECYCLE_EVENTS, checkedItems, (dialog, which, isChecked) -> {
			if (isChecked) {
				if (!selectedEvents.contains(LIFECYCLE_EVENTS[which])) {
					selectedEvents.add(LIFECYCLE_EVENTS[which]);
				}
			} else {
				selectedEvents.remove(LIFECYCLE_EVENTS[which]);
			}
		});
		
		builder.setPositiveButton("Add", (dialog, which) -> {
			if (!selectedEvents.isEmpty()) {
				saveSelectedLifecycleEvents(selectedEvents);
				displaySelectedEvents(); // refresh RecyclerView
			} else {
				// Optional: show a message if no event selected
				// Toast.makeText(getContext(), "No event selected", Toast.LENGTH_SHORT).show();
			}
		});
		
		builder.setNegativeButton("Cancel", null);
		builder.show();
	}
	
	private static class LifecycleEventAdapter extends RecyclerView.Adapter<LifecycleEventAdapter.ViewHolder> {
		private List<String> events;
		private Context context;
		private String myData;
		private String sc_id;
		private ProjectActivityBean bean;
		private Qm session;
		
		public LifecycleEventAdapter(Context context, List<String> events, String myData, String sc_id, ProjectActivityBean bean, Qm session) {
			this.context = context;
			this.events = events;
			this.myData = myData;
			this.sc_id = sc_id;
			this.bean = bean;
			this.session = session;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.mathod_ui, parent, false);
			return new ViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			String eventName = events.get(position);
			holder.eventName.setText(eventName);
			
			// Short description for each lifecycle / common event
			holder.eventDescription.setText(getEventDescription(eventName));
			
			// Click listener to open LogicEditorActivity
			holder.itemView.setOnClickListener(v -> {
				String eventExtra = getEventPlaceholder(eventName);
				
				Intent intent = new Intent(context, LogicEditorActivity.class);
				intent.putExtra("id", eventName);
				intent.putExtra("event", eventExtra);
				intent.putExtra("event_text", holder.eventDescription.getText().toString());
				intent.putExtra("filename", activityName + eventName + sc_id);
				intent.putExtra("sc_id", sc_id);
				intent.putExtra("activityName", activityName);
				intent.putExtra("widgetid", "");
				intent.putExtra("type", eventName);
				if (session != null) {
					intent.putExtra("qm_session", session); 
				}
				context.startActivity(intent);
			});
		}
		
		@Override
		public int getItemCount() {
			return events.size();
		}
		
		static class ViewHolder extends RecyclerView.ViewHolder {
			TextView eventName;
			TextView eventDescription;
			
			ViewHolder(View itemView) {
				super(itemView);
				eventName = itemView.findViewById(R.id.eventName);
				eventDescription = itemView.findViewById(R.id.eventDescription);
			}
		}
		
		// Helper: Get description for each event
		private String getEventDescription(String eventName) {
			switch (eventName) {
				case "onCreate": return "On activity create";
				case "onPostCreate": return "After onCreate called";
				case "onStart": return "Activity becoming visible";
				case "onResume": return "Activity in foreground";
				case "onPostResume": return "After onResume called";
				case "onPause": return "Activity partially hidden";
				case "onStop": return "Activity stopped";
				case "onRestart": return "Activity restarted";
				case "onDestroy": return "On activity destroyed";
				case "onBackPressed": return "On back button press";
				case "onSaveInstanceState": return "Save activity state";
				case "onRestoreInstanceState": return "Restore activity state";
				case "onActivityResult": return "Activity result received";
				case "onRequestPermissionsResult": return "Permissions result received";
				case "onUserLeaveHint": return "User leaving activity";
				case "onTrimMemory": return "System memory trim";
				case "onLowMemory": return "Low memory warning";
				case "onNewIntent": return "New intent received";
				case "onConfigurationChanged": return "Configuration changed";
				default: return "";
			}
		}
		
		// Helper: Get placeholder for each event
		private String getEventPlaceholder(String eventName) {
			switch (eventName) {
				case "onActivityResult": return "%d.requestcode %d.resultcode %m.intent";
				case "onClick": return "%m.view";
				case "onFileSelected": return "%s.path";
				case "onLocationChanged": return "%d.latitude %d.longitude %d.altitude";
				case "onSensorChanged": return "%s.sensorname %d.x %d.y %d.z";
				case "onReceive": return "%m.intent";
				case "onNotificationPosted": return "%s.package %s.title %s.text";
				case "onTick": return "%d.remainingtime";
				case "onPrepared":
				case "onCompletion":
				case "onError": return "%m.mediaplayer";
				case "onOptionsItemSelected": return "%d.id %s.title";
				case "onPictureTaken": return "%s.imagepath";
				case "onVideoRecorded": return "%s.videopath";
				case "onJobFinished": return "%d.jobid %b.needsreschedule";
				case "onStartCommand": return "%m.intent %d.flags %d.startid";
				default: return ""; // No extra
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
	
	private String getValue(Map<String, Object> data, String key, String defaultValue) {
		return data.containsKey(key) ? String.valueOf(data.get(key)) : defaultValue;
	}
	
	// Utility method for mapping widget type → drawable
	private int getWidgetIcon(String widgetType) {
		switch (widgetType) {
			case "TextView": return R.drawable.widget_text_view;
			case "CheckBox": return R.drawable.widget_check_box;
			case "Switch": return R.drawable.widget_switch;
			case "LinearLayout": return R.drawable.widget_linear_horizontal;
			case "ScrollView": return R.drawable.widget_scrollview;
			case "HorizontalScrollView": return R.drawable.widget_horizontalscrollview;
			case "RadioButton": return R.drawable.widget_radio_button;
			case "RelativeLayout": return R.drawable.ic_palette_relative_layout;
			case "MotionLayout": return R.drawable.icon_auto_awesome_motion_round;
			case "Button": return R.drawable.widget_button;
			case "EditText": return R.drawable.widget_edit_text;
			case "ImageView": return R.drawable.widget_image_view;
			case "ImageButton": return R.drawable.widget_image_view;
			case "ToggleButton": return R.drawable.widget_button;
			case "SeekBar": return R.drawable.widget_seek_bar;
			case "ProgressBar": return R.drawable.widget_progress_bar;
			case "RatingBar": return R.drawable.ic_palette_rating_bar;
			case "Spinner": return R.drawable.icon_format_list_bulleted_round;
			case "WebView": return R.drawable.widget_web_view;
			case "VideoView": return R.drawable.widget_video_view;
			case "NestedScrollView": return R.drawable.widget_scrollview;
			default: return R.drawable.icon_question_mark_round;
		}
	}
	{
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
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView widget_type_icon = _view.findViewById(R.id.widget_type_icon);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final TextView widget_classname = _view.findViewById(R.id.widget_classname);
			final TextView widget_id = _view.findViewById(R.id.widget_id);
			final TextView widget_listener = _view.findViewById(R.id.widget_listener);
			
			// Usage inside adapter
			Map<String, Object> item = _data.get((int) _position);
			
			// Set listener name
			widget_listener.setText(getValue(item, "widget_listener_name", "onClick"));
			
			// Set widget id
			widget_id.setText(getValue(item, "widget_id", "textview1"));
			
			// Set widget classname
			String widgetType = getValue(item, "widget_type", "TextView");
			widget_classname.setText(widgetType);
			
			// Set widget icon
			widget_type_icon.setImageResource(getWidgetIcon(widgetType));
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