package com.nexusteam.blacklogics;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import androidx.cardview.widget.CardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.besome.blacklogics.model.ComponentData;
import com.besome.blacklogics.util.ComponentList;
import com.nexusteam.internal.beans.ComponentBean;

import java.util.ArrayList;
import java.util.List;

public class AddComponentDialog {
	
	private Context context;
	private OnComponentSelectedListener listener;
	private AlertDialog dialog;
	private List<ComponentData> allComponents;
	private List<ComponentData> filteredComponents;
	private ComponentGridAdapter adapter;
	private EditText searchEditText;
	private GridView gridView;
	private TextView emptyView;
	private ImageView clearSearch;
	
	public interface OnComponentSelectedListener {
		void onComponentSelected(ComponentData component);
	}
	
	public AddComponentDialog(Context context, OnComponentSelectedListener listener) {
		this.context = context;
		this.listener = listener;
		this.allComponents = ComponentList.getComponents();
		this.filteredComponents = new ArrayList<ComponentData>(allComponents);
	}
	
	public void show() {
		AlertDialog.Builder builder = new MaterialAlertDialogBuilder(context, com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered);
		
		// Inflate custom layout
		View view = View.inflate(context, R.layout.component_add_dialog, null);
		builder.setView(view);
		
		// Initialize views
		initializeViews(view);
		setupGridView();
		setupSearch(view);
		setupCloseButton(view);
		
		// Create and show dialog
		dialog = builder.create();
		dialog.show();
		
		// Set dialog window properties
		Window window = dialog.getWindow();
		if (window != null) {
			window.setLayout(
			WindowManager.LayoutParams.MATCH_PARENT,
			WindowManager.LayoutParams.WRAP_CONTENT
			);
			window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		}
	}
	
	private void initializeViews(View view) {
		searchEditText = (EditText) view.findViewById(R.id.searchEditText);
		gridView = (GridView) view.findViewById(R.id.component_grid);
		emptyView = (TextView) view.findViewById(R.id.emptyView);
		clearSearch = (ImageView) view.findViewById(R.id.clearSearch);
	}
	
	private void setupGridView() {
		// Calculate number of columns based on screen width
		int columns = 3; // Default to 3 columns
		gridView.setNumColumns(columns);
		
		adapter = new ComponentGridAdapter(context, filteredComponents);
		gridView.setAdapter(adapter);
		
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (listener != null && position < filteredComponents.size()) {
					listener.onComponentSelected(filteredComponents.get(position));
				}
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
			}
		});
	}
	
	private void setupSearch(View view) {
		searchEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				filterComponents(s.toString());
				
				// Show/hide clear button
				if (clearSearch != null) {
					if (s.length() > 0) {
						clearSearch.setVisibility(View.VISIBLE);
					} else {
						clearSearch.setVisibility(View.GONE);
					}
				}
			}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		
		// Clear search button
		if (clearSearch != null) {
			clearSearch.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					searchEditText.setText("");
				}
			});
		}
	}
	
	private void setupCloseButton(View view) {
		ImageView closeButton = (ImageView) view.findViewById(R.id.closeButton);
		if (closeButton != null) {
			closeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (dialog != null && dialog.isShowing()) {
						dialog.dismiss();
					}
				}
			});
		}
	}
	
	private void filterComponents(String query) {
		filteredComponents.clear();
		
		if (query == null || query.trim().isEmpty()) {
			filteredComponents.addAll(allComponents);
		} else {
			String lowerQuery = query.toLowerCase().trim();
			for (ComponentData component : allComponents) {
				if (component.getName().toLowerCase().contains(lowerQuery) ||
				component.getDescription().toLowerCase().contains(lowerQuery)) {
					filteredComponents.add(component);
				}
			}
		}
		
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
		
		// Show empty view if no results
		if (filteredComponents.isEmpty()) {
			if (emptyView != null) {
				emptyView.setVisibility(View.VISIBLE);
			}
			if (gridView != null) {
				gridView.setVisibility(View.GONE);
			}
		} else {
			if (emptyView != null) {
				emptyView.setVisibility(View.GONE);
			}
			if (gridView != null) {
				gridView.setVisibility(View.VISIBLE);
			}
		}
	}
	
	// Custom Grid Adapter
	private class ComponentGridAdapter extends ArrayAdapter<ComponentData> {
		
		private Context context;
		private List<ComponentData> components;
		private LayoutInflater inflater;
		
		public ComponentGridAdapter(Context context, List<ComponentData> components) {
			super(context, 0, components);
			this.context = context;
			this.components = components;
			this.inflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return components.size();
		}
		
		@Override
		public ComponentData getItem(int position) {
			return components.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_component_grid, parent, false);
				holder = new ViewHolder();
				holder.cardView = (CardView) convertView;
				holder.iconView = (ImageView) convertView.findViewById(R.id.componentIcon);
				holder.nameView = (TextView) convertView.findViewById(R.id.componentName);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			ComponentData component = getItem(position);
			if (component != null) {
				holder.nameView.setText(component.getName());
				
				// Set icon based on component type
				int iconRes = getIconForComponent(component.getName());
				if (iconRes != 0) {
					holder.iconView.setImageResource(iconRes);
				} else {
					holder.iconView.setImageResource(R.drawable.widget_module);
				}
				
				// Set background color based on component type
				int colorRes = getColorForComponent(component.getName());
				if (colorRes != 0) {
					holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, colorRes));
				} else {
					holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.component_default_bg));
				}
			}
			
			return convertView;
		}
		
		private class ViewHolder {
			CardView cardView;
			ImageView iconView;
			TextView nameView;
		}
		
		private int getIconForComponent(String componentName) {
			if (componentName == null) return 0;
			
			switch (componentName) {
				case "Intent":
				return R.drawable.widget_intent;
				case "SharedPreferences":
				return R.drawable.widget_shared_preference;
				case "Calendar":
				return R.drawable.widget_calendar;
				case "Vibrator":
				return R.drawable.widget_vibrator;
				case "Timer":
				return R.drawable.widget_timer;
				case "Firebase DB":
				return R.drawable.widget_firebase;
				case "Dialog":
				return R.drawable.widget_alertdialog;
				case "MediaPlayer":
				return R.drawable.widget_mediaplayer;
				case "SoundPool":
				return R.drawable.widget_soundpool;
				case "ObjectAnimator":
				return R.drawable.widget_objectanimator;
				case "Gyroscope":
				return R.drawable.widget_gyroscope;
				case "Firebase Auth":
				return R.drawable.widget_firebase;
				case "Interstitial Ad":
				return R.drawable.widget_admob;
				case "Firebase Storage":
				return R.drawable.widget_firebase;
				case "Camera":
				return R.drawable.widget_camera;
				case "FilePicker":
				return R.drawable.widget_file;
				case "RequestNetwork":
				return R.drawable.widget_network_request;
				case "TextToSpeech":
				return R.drawable.widget_text_to_speech;
				case "SpeechToText":
				return R.drawable.widget_speech_to_text;
				case "BluetoothConnect":
				return R.drawable.widget_bluetooth;
				case "LocationManager":
				return R.drawable.widget_location;
				default:
				return R.drawable.widget_module;
			}
		}
		
		private int getColorForComponent(String componentName) {
			if (componentName == null) return 0;
			
			if (componentName.equals("Intent")) {
				return R.color.component_intent;
			} else if (componentName.equals("SharedPreferences")) {
				return R.color.component_file;
			} else if (componentName.equals("Calendar")) {
				return R.color.component_calendar;
			} else if (componentName.equals("Vibrator")) {
				return R.color.component_vibrator;
			} else if (componentName.equals("Timer")) {
				return R.color.component_timer;
			} else if (componentName.equals("Firebase DB") || 
			componentName.equals("Firebase Auth") || 
			componentName.equals("Firebase Storage")) {
				return R.color.component_firebase;
			} else if (componentName.equals("Dialog")) {
				return R.color.component_dialog;
			} else if (componentName.equals("MediaPlayer")) {
				return R.color.component_mediaplayer;
			} else if (componentName.equals("SoundPool")) {
				return R.color.component_soundpool;
			} else if (componentName.equals("ObjectAnimator")) {
				return R.color.component_animator;
			} else if (componentName.equals("Gyroscope")) {
				return R.color.component_gyroscope;
			} else if (componentName.equals("Interstitial Ad")) {
				return R.color.component_ad;
			} else if (componentName.equals("Camera")) {
				return R.color.component_camera;
			} else if (componentName.equals("FilePicker")) {
				return R.color.component_filepicker;
			} else if (componentName.equals("RequestNetwork")) {
				return R.color.component_network;
			} else if (componentName.equals("TextToSpeech") || 
			componentName.equals("SpeechToText")) {
				return R.color.component_speech;
			} else if (componentName.equals("BluetoothConnect")) {
				return R.color.component_bluetooth;
			} else if (componentName.equals("LocationManager")) {
				return R.color.component_location;
			} else {
				return R.color.component_default;
			}
		}
	}
}
