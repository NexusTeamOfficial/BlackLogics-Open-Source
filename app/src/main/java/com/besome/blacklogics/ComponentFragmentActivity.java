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
import android.widget.LinearLayout;
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
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import com.besome.blacklogics.model.ComponentData;
import com.besome.blacklogics.util.ComponentList;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import androidx.appcompat.app.AlertDialog;

public class ComponentFragmentActivity extends Fragment {
	
	private FloatingActionButton _fab;
	public ComponentFragmentActivity.BaseComponentRecyclerAdapter adapter;
	private boolean t = false;
	public ArrayList<HashMap<String, Object>> componentData = new ArrayList();
	private boolean isDeleteMode = false;
	public ComponentFragmentActivity activity;
	public static ComponentFragmentActivity componentFragmentActivity;
	private String componentName = "";
	private String fieldName = "";
	
	private LinearLayout baseComponentLayout;
	private RecyclerView baseComponentRecycler;
	
	private Intent intent = new Intent();
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.component_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		componentFragmentActivity = this;
		_fab = _view.findViewById(R.id._fab);
		baseComponentLayout = _view.findViewById(R.id.baseComponentLayout);
		baseComponentRecycler = _view.findViewById(R.id.baseComponentRecycler);
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				showComponentDialog();
			}
		});
	}
	
	private void initializeLogic() {
		this.c();
	}
	
	public void _a() {
	}
	public void c() {
			List<HashMap<String, String>> components = DesignActivity.loadComponentLogic(DesignActivity.currentActivityBean.getActivityName());
			componentData.clear();
			for (HashMap<String, String> component : components) {
					HashMap<String, Object> data = new HashMap<>();
					data.put("componentName", component.get("componentName"));
					data.put("fieldName", component.get("fieldName"));
					componentData.add(data);
			}
			
			baseComponentRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
			adapter = new BaseComponentRecyclerAdapter(componentData);
			baseComponentRecycler.setAdapter(adapter);
	}
	
	private void showComponentDialog() {
			android.view.LayoutInflater inflater = getActivity().getLayoutInflater();
			android.view.View dialogView = inflater.inflate(R.layout.component_selection_dialog, null);
		
			android.widget.TextView title = dialogView.findViewById(R.id.dialog_title);
			android.widget.GridView componentGrid = dialogView.findViewById(R.id.component_grid);
			title.setText("Select Component");
		
			java.util.ArrayList<String> componentNames = new java.util.ArrayList<>();
			java.util.ArrayList<ComponentData> components = ComponentList.getComponents();
			for (ComponentData component : components) {
					componentNames.add(component.getName());
			}
		
			android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(
				getActivity(), android.R.layout.simple_list_item_1, componentNames);
			componentGrid.setAdapter(adapter);
		
			com.google.android.material.dialog.MaterialAlertDialogBuilder builder =
				new com.google.android.material.dialog.MaterialAlertDialogBuilder(getActivity());
			builder.setView(dialogView);
			androidx.appcompat.app.AlertDialog dialog = builder.create();
		
			componentGrid.setOnItemClickListener((parent, view, position, id) -> {
					dialog.dismiss();
					showComponentDetailsDialog(components.get(position));
			});
		
			dialog.show();
	}
	
	
	private void showComponentDetailsDialog(ComponentData component) {
			android.view.LayoutInflater inflater = getActivity().getLayoutInflater();
			android.view.View dialogView = inflater.inflate(R.layout.component_details_dialog, null);
		
			android.widget.TextView name = dialogView.findViewById(R.id.dialog_title);
			android.widget.TextView description = dialogView.findViewById(R.id.dialog_description);
			android.widget.EditText fieldNameInput = dialogView.findViewById(R.id.field_name_input);
			android.widget.Button saveButton = dialogView.findViewById(R.id.add_button);
			android.widget.Button docButton = dialogView.findViewById(R.id.doc_button);
		
			fieldNameInput.setFocusable(true);
			fieldNameInput.setFocusableInTouchMode(true);
			fieldNameInput.setClickable(true);
		
			name.setText(component.getName());
			description.setText(component.getDescription());
		
			com.google.android.material.dialog.MaterialAlertDialogBuilder builder =
				new com.google.android.material.dialog.MaterialAlertDialogBuilder(getActivity());
			builder.setView(dialogView);
			androidx.appcompat.app.AlertDialog dialog = builder.create();
		
			saveButton.setOnClickListener(v -> {
					String fieldName = fieldNameInput.getText().toString().trim();
					if (fieldName.isEmpty()) {
							TheBlockLogicsUtil.showToast(getActivity(), "Please enter a field name");
					} else if (!fieldName.matches("^[a-z][a-zA-Z0-9]*$")) {
							TheBlockLogicsUtil.showToast(getActivity(), "Field name must start with a lowercase letter and contain only letters and numbers");
					} else {
							DesignActivity.saveComponentLogic(
								DesignActivity.currentActivityBean.getActivityName(),
								component.getName(),
								fieldName
							);
							java.util.HashMap<String, Object> data = new java.util.HashMap<>();
							data.put("componentName", component.getName());
							data.put("fieldName", fieldName);
							componentData.add(data);
							adapter.notifyDataSetChanged();
							dialog.dismiss();
							TheBlockLogicsUtil.showToast(getActivity(), "Component added successfully");
					}
			});
		
			docButton.setOnClickListener(v -> {
					android.content.Intent intent = new android.content.Intent(
						android.content.Intent.ACTION_VIEW,
						android.net.Uri.parse(component.getDocumentationUrl()));
					startActivity(intent);
			});
		
			dialog.show();
	}
	
	private List<String> getComponentEvents(String componentName) {
			List<String> events = new ArrayList<>();
			switch (componentName) {
					case "Dialog":
					events.add("show");
					events.add("onDismiss");
					events.add("onCancel");
					break;
					case "Intent":
					events.add("onActivityResult");
					break;
					case "ObjectAnimator":
					events.add("onAnimationStart");
					events.add("onAnimationEnd");
					events.add("onAnimationCancel");
					events.add("onAnimationRepeat");
					break;
					case "Timer":
					events.add("onTick");
					events.add("onFinish");
					break;
					case "Notification":
					events.add("onNotificationPosted");
					events.add("onNotificationRemoved");
					break;
					case "AsyncTask":
					events.add("onPreExecute");
					events.add("onProgressUpdate");
					events.add("onPostExecute");
					events.add("onCancelled");
					break;
					case "Handler":
					events.add("handleMessage");
					break;
					case "Service":
					events.add("onCreate");
					events.add("onStartCommand");
					events.add("onBind");
					events.add("onDestroy");
					break;
					case "BroadcastReceiver":
					events.add("onReceive");
					break;
					case "ViewModel":
					events.add("onCleared");
					break;
					case "LiveData":
					events.add("onActive");
					events.add("onInactive");
					break;
					case "WorkManager":
					events.add("onWorkStateChanged");
					break;
					case "MediaPlayer":
					events.add("onPrepared");
					events.add("onCompletion");
					events.add("onError");
					break;
					case "Camera":
					case "Camera2 API":
					events.add("onPictureTaken");
					events.add("onVideoRecorded");
					break;
					case "FilePicker":
					case "Storage Access Framework":
					events.add("onFileSelected");
					break;
					case "LocationManager":
					events.add("onLocationChanged");
					events.add("onProviderEnabled");
					events.add("onProviderDisabled");
					break;
					case "SensorManager":
					events.add("onSensorChanged");
					events.add("onAccuracyChanged");
					break;
					case "BluetoothAdapter":
					events.add("onBluetoothStateChanged");
					events.add("onDeviceFound");
					break;
					case "AlarmManager":
					events.add("onAlarmTriggered");
					break;
					case "JobScheduler":
					events.add("onJobFinished");
					break;
					default:
					events.add("onClick"); // Generic event for UI-related components
					break;
			}
			return events;
	}
	
	// Placeholder method to handle event selection
	private void handleEventSelection(String componentName, String fieldName, String eventName) {
			// Implement your logic here, e.g., add the event to a code editor or logic block
			TheBlockLogicsUtil.showToast(getActivity(), "Handling " + eventName + " for " + componentName + " (" + fieldName + ")");
			// Example: Add to a code editor or logic system
			// DesignActivity.addEventLogic(DesignActivity.currentActivityBean.getActivityName(), componentName, fieldName, eventName);
	}
	
	{
	}
	
	public class BaseComponentRecyclerAdapter extends RecyclerView.Adapter<BaseComponentRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public BaseComponentRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.component_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout base = _view.findViewById(R.id.base);
			final LinearLayout baseOptions = _view.findViewById(R.id.baseOptions);
			final LinearLayout baseComponentDeleteUi = _view.findViewById(R.id.baseComponentDeleteUi);
			final LinearLayout baseIconUi = _view.findViewById(R.id.baseIconUi);
			final LinearLayout baseLayout = _view.findViewById(R.id.baseLayout);
			final ImageView baseComponentMoreOptions = _view.findViewById(R.id.baseComponentMoreOptions);
			final ImageView baseComponentIcon = _view.findViewById(R.id.baseComponentIcon);
			final TextView baseComponentName = _view.findViewById(R.id.baseComponentName);
			final TextView baseComponentSubName = _view.findViewById(R.id.baseComponentSubName);
			final ImageView baseDeleteIcon = _view.findViewById(R.id.baseDeleteIcon);
			final TextView baseDeleteText = _view.findViewById(R.id.baseDeleteText);
			
			ArrayList<HashMap<String, Object>> data = _data;
			int position= _position;
			HashMap<String, Object> component = data.get(position);
			componentName = component.get("componentName").toString();
			fieldName = component.get("fieldName").toString();
			baseComponentName.setText(component.get("componentName").toString());
			baseComponentSubName.setText(component.get("fieldName").toString());
			
			baseComponentDeleteUi.setVisibility(isDeleteMode ? View.VISIBLE : View.GONE);
			baseComponentIcon.setImageResource(isDeleteMode ? 2131231644 : 2131231642);
			
			baseComponentDeleteUi.setOnClickListener(v -> {
					new android.app.AlertDialog.Builder(getActivity())
					.setTitle("Remove Component")
					.setMessage("Are you sure you want to remove " + component.get("componentName") + " (" + component.get("fieldName") + ")?")
					.setPositiveButton("Remove", (dialog, which) -> {
							String activityName = DesignActivity.currentActivityBean.getActivityName();
							String componentName = component.get("componentName").toString();
							String fieldName = component.get("fieldName").toString();
							DesignActivity.removeComponentLogic(activityName, componentName, fieldName);
							data.remove(position);
							notifyItemRemoved(position);
							notifyItemRangeChanged(position, data.size());
							TheBlockLogicsUtil.showToast(getActivity(), "Component removed successfully");
					})
					.setNegativeButton("Cancel", null)
					.show();
			});
			
			baseComponentMoreOptions.setOnClickListener(v -> {
					isDeleteMode = !isDeleteMode;
					baseComponentDeleteUi.setVisibility(isDeleteMode ? View.VISIBLE : View.GONE);
					baseComponentIcon.setImageResource(isDeleteMode ? 2131231644 : 2131231642);
					notifyDataSetChanged(); // Refresh to update visibility for all items
			});
			
			//baseOptions.removeViewsInLayout(2, baseOptions.getChildCount() - 2); // Assuming first two children are existing UI elements
			baseOptions.removeAllViews(); 
			// Get the list of events for the component
			List<String> events = getComponentEvents(componentName);
			
			// Programmatically add a button for each event
			for (String event : events) {
					Button eventButton = new Button(getActivity());
					eventButton.setText(event);
					eventButton.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
					));
					// Style the button (customize as needed)
					eventButton.setBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(android.R.color.holo_blue_light)));
					eventButton.setTextColor(getActivity().getResources().getColor(android.R.color.white));
					eventButton.setTextSize(12); // Smaller text size for better fit
					eventButton.setPadding(8, 4, 8, 4); // Add padding for better appearance
					eventButton.setTag("eventButton_" + event); // Unique tag for each event button
					
					// Set click listener for the event button
					eventButton.setOnClickListener(v -> {
							handleEventSelection(componentName, fieldName, event);
					});
					
					// Add the button to the baseLayout
					baseOptions.addView(eventButton);
			}
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