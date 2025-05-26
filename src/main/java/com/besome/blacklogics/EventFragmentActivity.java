package com.besome.blacklogics;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
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
import androidx.recyclerview.*;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.viewpager.*;
import androidx.viewpager2.*;
import com.besome.sketch.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.material.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import com.besome.blacklogics.logic.editor.LogicEditorActivity;
import com.besome.blacklogics.util.FileHandler;
import android.util.Base64;
import com.google.gson.Gson;
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

public class EventFragmentActivity extends Fragment {
	
	private FloatingActionButton _fab;
	private String s = "";
	private boolean bbnb = false;
	private HashMap<String, Object> map = new HashMap<>();
	private String sc_id = "";
	private String event_name = "";
	public DesignActivity designAC;
	public static String myData = "";
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear4;
	private RecyclerView rvSelectedEvents;
	private ImageView imageview1;
	private LinearLayout linear3;
	private TextView textview2;
	private TextView textview1;
	
	private Intent i = new Intent();
	private Intent intent = new Intent();
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.event_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		_fab = _view.findViewById(R.id._fab);
		linear1 = _view.findViewById(R.id.linear1);
		linear2 = _view.findViewById(R.id.linear2);
		linear4 = _view.findViewById(R.id.linear4);
		rvSelectedEvents = _view.findViewById(R.id.rvSelectedEvents);
		imageview1 = _view.findViewById(R.id.imageview1);
		linear3 = _view.findViewById(R.id.linear3);
		textview2 = _view.findViewById(R.id.textview2);
		textview1 = _view.findViewById(R.id.textview1);
		
		linear2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.putExtra("id", "onCreate");
				intent.putExtra("event", "initializeLogic");
				intent.putExtra("event_text", "On activity create");
				intent.putExtra("filename", myData.concat("_".concat("onCreate")));
				intent.putExtra("sc_id", sc_id);
				intent.putExtra("activityName", DesignActivity.currentActivityBean.getActivityName());
				intent.putExtra("widgetid", "");
				intent.setClass(getContext().getApplicationContext(), LogicEditorActivity.class);
				startActivity(intent);
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				try {
					        String[] widgets = ViewEditorFragmentActivity.getAllWidgetsIdString(ViewEditorFragmentActivity.ll).split(",");
					
					        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
					        builder.setTitle("Select Widget");
					
					        builder.setItems(widgets, (dialog, which) -> {
						            String selectedWidgetId = widgets[which];
						
						            String path = FileHandler.codeSavePath + "/" + sc_id + "/events/events.json";
						            File file = new File(path);
						            JSONObject allEvents = new JSONObject();
						
						            try {
							                if (file.exists()) {
								                    String json = FileHandler.readFile(path);
								                    String decoded = new String(Base64.decode(json, Base64.DEFAULT));
								                    allEvents = new JSONObject(decoded);
								                }
							
							                // Only create if not already exists
							                if (!allEvents.has(selectedWidgetId)) {
								                    JSONObject emptyEvent = new JSONObject();
								                    emptyEvent.put("name", selectedWidgetId);
								                    emptyEvent.put("event_name", ViewEditorFragmentActivity.activityName); // <- Added line
								                    emptyEvent.put("blocks", new JSONArray());
								
								                    allEvents.put(selectedWidgetId, emptyEvent);
								
								                    String encoded = Base64.encodeToString(allEvents.toString().getBytes(), Base64.NO_WRAP);
								                    FileHandler.saveFile(path, encoded);
								
								                    SketchwareUtil.showMessage(getContext(), "Event created for " + selectedWidgetId);
								                } else {
								                    SketchwareUtil.showMessage(getContext(), "Event already exists for " + selectedWidgetId);
								                }
							
							            } catch (Exception e) {
							                SketchwareUtil.showMessage(getContext(), "Error: " + e.toString());
							            }
						        });
					
					        builder.show();
					    } catch (Exception e) {
					        SketchwareUtil.showMessage(getContext(), e.toString());
					    }
			}
		});
	}
	
	private void initializeLogic() {
		com.besome.blacklogics.logic.editor.DesignDataManager.initialize(requireActivity(), "");
		 // Replace this:
		    // designAC = new DesignActivity();
		    
		    // With this:
		    designAC = (DesignActivity) requireActivity();
		sc_id = requireActivity().getIntent().getStringExtra("sc_id");
		        event_name = ViewEditorFragmentActivity.activityName;
		File dir = new File(FileHandler.codeSavePath + "/" + sc_id);
		        if (!dir.exists()) {
			            dir.mkdirs();
			        }
		displaySelectedEvents();
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
			        DesignActivity.complex.storeWidgetEventSelections(activityName, selectedWidgets, eventType);
			
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
	private void displaySelectedEvents() {
		    rvSelectedEvents.setLayoutManager(new LinearLayoutManager(getContext()));
		    String activityName = ViewEditorFragmentActivity.activityName;
		    Map<String, String> selections = DesignActivity.complex.getWidgetEventSelections(activityName);
		    List<Map.Entry<String, String>> selectionList = new ArrayList<>(selections.entrySet());
		    SelectedEventAdapter adapter = new SelectedEventAdapter(selectionList);
		    rvSelectedEvents.setAdapter(adapter);
		    adapter.notifyDataSetChanged(); // Ensure adapter updates
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
	{
	}
	
}