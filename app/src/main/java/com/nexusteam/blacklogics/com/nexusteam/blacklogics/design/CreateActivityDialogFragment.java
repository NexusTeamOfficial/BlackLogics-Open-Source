package com.nexusteam.blacklogics.design;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.*;
import android.text.TextWatcher;
import android.text.Editable;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.nexusteam.blacklogics.manager.ActivityManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.DesignActivity;
import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.generator.source.model.*;
import com.nexusteam.blacklogics.model.DataModel;
import com.nexusteam.blacklogics.sdk.util.ActivityConfig;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.nexusteam.blacklogics.manager.ProjectSettingsManager;
import com.nexusteam.blacklogics.generator.source.model.ActivityStructureRegistry;
import com.nexusteam.blacklogics.manager.ProjectConfigManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateActivityDialogFragment extends DialogFragment {
	
	private final DesignActivity a;
	private final String sc_id;
	private final Complex complex;
	private RadioGroup radioGroupType;
	ActivityTypeRegistry registry;
	
	private boolean isUpdateMode;
	private String existingActivityName;
	
	private ActivityConfig activityConfig;
	
	public interface OnActivitySavedListener {
		void onActivitySaved();
	}
	
	private OnActivitySavedListener onActivitySavedListener;
	
	public void setOnActivitySavedListener(OnActivitySavedListener listener) {
		this.onActivitySavedListener = listener;
	}
	
	public CreateActivityDialogFragment(DesignActivity a, String sc_id, Complex complex,
	boolean isUpdateMode, String existingActivityName) {
		registry = ActivityTypeRegistry.getInstance();
		this.a = a;
		this.sc_id = sc_id;
		this.complex = complex;
		this.isUpdateMode = isUpdateMode;
		this.existingActivityName = existingActivityName;
		
		this.activityConfig = new ActivityConfig();
	}
	
	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		registry.load(context, sc_id);
		
		activityConfig.init(context, sc_id);
	}
	
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	@Nullable ViewGroup container,
	@Nullable Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.create_activity_dialog, container, false);
		
		final ImageView preview = view.findViewById(R.id.preview_image);
		final TextInputEditText nameEt = view.findViewById(R.id.edittext_name);
		
		if (!isUpdateMode) {
			nameEt.requestFocus();
		}
		
		nameEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				String input = s.toString().trim();
				
				if (input.isEmpty()) {
					nameEt.setError("Layout name required");
					return;
				}
				
				if (!input.matches("^[a-z][a-z0-9_]*$")) {
					nameEt.setError("Use lowercase letters, numbers and underscore only.\nMust start with a letter.");
					return;
				}
				
				if (input.contains("__")) {
					nameEt.setError("Double underscore not allowed");
					return;
				}
				
				if (input.endsWith("_")) {
					nameEt.setError("Cannot end with underscore");
					return;
				}
				
				
				for (String blocked : DataModel.BLOCKED_VALIDATOR_NAME()) {
					if (blocked.equals(input)) {
						nameEt.setError("This name is not allowed");
						return;
					}
				}
				
				
				nameEt.setError(null);
			}
		});
		
		
		final CheckBox cbStatus = view.findViewById(R.id.checkbox_status_bar);
		final CheckBox cbToolbar = view.findViewById(R.id.checkbox_toolbar);
		final CheckBox cbDrawer = view.findViewById(R.id.checkbox_drawer);
		final CheckBox cbFab = view.findViewById(R.id.checkbox_fab);
		
		final RadioGroup rgType = view.findViewById(R.id.radio_group_type);
		final RadioGroup rgOrientation = view.findViewById(R.id.radio_group_orientation);
		
		MaterialButton btnCancel = view.findViewById(R.id.button_cancel);
		MaterialButton btnSave = view.findViewById(R.id.button_save);
		radioGroupType = view.findViewById(R.id.radio_group_type);
		
		radioGroupType.check(R.id.radio_activity);
		
		if (isUpdateMode && existingActivityName != null) {
			try {
				JSONObject o = complex.getActivityData(existingActivityName);
				if (o != null) {
					nameEt.setText(existingActivityName);
					
					nameEt.setEnabled(false);
					nameEt.setFocusable(false);
					nameEt.setError(null);
					
					cbStatus.setChecked(o.optBoolean("statusBar", true));
					cbToolbar.setChecked(o.optBoolean("toolbar", true));
					cbDrawer.setChecked(o.optBoolean("drawer", false));
					cbFab.setChecked(o.optBoolean("fab", false));
					
					rgType.check(
					"Fragment".equals(o.optString("type")) ? R.id.radio_fragment :
					"DialogFragment".equals(o.optString("type")) ? R.id.radio_dialog_fragment :
					R.id.radio_activity
					);
					
					rgType.setEnabled(false);
					for (int i = 0; i < rgType.getChildCount(); i++) {
						rgType.getChildAt(i).setEnabled(false);
					}
					
					rgOrientation.check(
					"Portrait".equals(o.optString("orientation")) ? R.id.radio_portrait :
					"Landscape".equals(o.optString("orientation")) ? R.id.radio_landscape :
					R.id.radio_both
					);
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		View.OnClickListener previewUpdater = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int m = 0;
				if (cbStatus.isChecked()) m |= 8;
				if (cbToolbar.isChecked()) m |= 4;
				if (cbDrawer.isChecked()) m |= 2;
				if (cbFab.isChecked()) m |= 1;
				
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				map.put(15, R.drawable.activity_1101);
				map.put(14, R.drawable.activity_0101);
				map.put(13, R.drawable.activity_1001);
				map.put(12, R.drawable.activity_0001);
				map.put(11, R.drawable.activity_1100);
				map.put(10, R.drawable.activity_0100);
				map.put(9, R.drawable.activity_1000);
				map.put(8, R.drawable.activity_0000);
				map.put(7, R.drawable.activity_1111);
				map.put(6, R.drawable.activity_1011);
				map.put(5, R.drawable.activity_0111);
				map.put(4, R.drawable.activity_preset_1);
				map.put(3, R.drawable.activity_1110);
				map.put(2, R.drawable.activity_0110);
				map.put(1, R.drawable.activity_1010);
				map.put(0, R.drawable.activity_0010);
				
				Integer resId = map.get(m);
				if (resId != null) {
					preview.setImageResource(resId);
				}
			}
		};
		
		
		cbStatus.setOnClickListener(previewUpdater);
		cbToolbar.setOnClickListener(previewUpdater);
		cbDrawer.setOnClickListener(previewUpdater);
		cbFab.setOnClickListener(previewUpdater);
		previewUpdater.onClick(null);
		
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String raw = nameEt.getText().toString().trim();
				if (raw.isEmpty()) {
					toast("Activity name cannot be empty");
					return;
				}
				
				String actName = formatToCamelCase(raw);
				String xmlName = raw.toLowerCase().replaceAll("[^a-z0-9]", "_");
				
				List<String> names = complex.getAllJavaAndXmlNames();
				if (!isUpdateMode || !actName.equals(existingActivityName)) {
					if (names.contains(actName + ".java") || names.contains(xmlName)) {
						toast("Name already exists");
						return;
					}
				}
				
				String type = getType(rgType);
				String orientation = getOrientation(rgOrientation);
				
				boolean status = cbStatus.isChecked();
				boolean toolbar = cbToolbar.isChecked();
				boolean drawer = cbDrawer.isChecked();
				boolean fab = cbFab.isChecked();
				
				String suffix = "Fragment".equals(type) ? DataModel.ADD_FRAGMENT
				: "DialogFragment".equals(type) ? DataModel.ADD_DIALOG_FRAGMENT
				: DataModel.ADD_ACTIVITY;
				
				String fullActName = actName.toLowerCase().endsWith(suffix.toLowerCase())
				? actName
				: actName + suffix;
				
				try {
					if (isUpdateMode) {
						complex.updateActivity(
						existingActivityName,
						actName,
						xmlName,
						fab,
						toolbar,
						complex.getAndroidXEnable(),
						drawer,
						type,
						orientation,
						status
						);
						
						if (!fullActName.equals(existingActivityName)) {
							activityConfig.removeActivity(existingActivityName);
						}
						
						activityConfig.setActivityConfig(
						fullActName, status, toolbar, drawer, fab, type, orientation, xmlName
						);
						
						activityConfig.updateActivityTheme(fullActName);
						
						if (!fullActName.equals(existingActivityName)) {
							registry.removeActivity(existingActivityName);
						}
						ActivityType regType = "Fragment".equals(type) ? ActivityType.FRAGMENT_ACTIVITY
						: "DialogFragment".equals(type) ? ActivityType.DIALOG_FRAGMENT
						: ActivityType.ACTIVITY;
						registry.registerActivity(fullActName, regType, xmlName);
						registry.save(a, sc_id);
						
					} else {
						complex.addActivityToManifest(actName);
						complex.enableFab(actName, fab);
						complex.enableToolBar(actName, complex.getAndroidXEnable(), toolbar);
						
						setupCheck(radioGroupType, actName, xmlName, status, toolbar);
						
						boolean isFirstActivity = activityConfig.getActivityCount() == 0;
						
						activityConfig.setActivityConfig(
						fullActName, status, toolbar, drawer, fab, type, orientation, xmlName
						);
						
					}
					
					activityConfig.save(a, sc_id);
					
					if (onActivitySavedListener != null) {
						onActivitySavedListener.onActivitySaved();
					}
					
					dismiss();
				} catch (Exception e) {
					toast("Error: " + e.getMessage());
				}
			}
		});
		
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		if (getDialog() != null && getDialog().getWindow() != null) {
			getDialog().getWindow().setLayout(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.WRAP_CONTENT
			);
			getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		}
	}
	
	
	private String getType(RadioGroup g) {
		if (g.getCheckedRadioButtonId() == R.id.radio_fragment) return "Fragment";
		if (g.getCheckedRadioButtonId() == R.id.radio_dialog_fragment) return "DialogFragment";
		return "Activity";
	}
	
	private String getOrientation(RadioGroup g) {
		if (g.getCheckedRadioButtonId() == R.id.radio_portrait) return "Portrait";
		if (g.getCheckedRadioButtonId() == R.id.radio_landscape) return "Landscape";
		return "Both";
	}
	
	private String formatToCamelCase(String input) {
		StringBuilder result = new StringBuilder();
		for (String part : input.split("_")) {
			if (!part.isEmpty()) {
				result.append(part.substring(0, 1).toUpperCase())
				.append(part.substring(1));
			}
		}
		return result.toString();
	}
	
	private Map<String, String> createMainActivityAttributes(String themeName) {
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("exported", "true");
		attrs.put("launchMode", "singleTop");
		attrs.put("configChanges", "orientation|keyboardHidden|screenSize");
		attrs.put("windowSoftInputMode", "adjustResize");
		attrs.put("theme", "@style/" + themeName);
		return attrs;
	}
	
	private String resolveTheme(boolean toolbar, boolean status) {
		if (toolbar && status) return "AppTheme";
		if (toolbar) return "NoStatusBar";
		if (status) return "AppTheme.NoActionBar";
		return "FullScreen";
	}
	private void setupCheck(RadioGroup g, String activityName, String layoutName, boolean status, boolean toolbar) {
		if (g.getCheckedRadioButtonId() == R.id.radio_fragment) {
			String suffixedName = activityName.toLowerCase().endsWith(DataModel.ADD_FRAGMENT.toLowerCase())
			? activityName : activityName + DataModel.ADD_FRAGMENT;
			
			registry.registerActivity(suffixedName, ActivityType.FRAGMENT_ACTIVITY, layoutName);
			registry.save(a, sc_id);
			ActivityManager activityManager = new ActivityManager();
			activityManager.load(a, sc_id);
			activityManager.addActivity(suffixedName, layoutName);
			activityManager.save(a, sc_id);
			
			ActivityStructureRegistry structureRegistry = new ActivityStructureRegistry();
			structureRegistry.load(a, sc_id);
			structureRegistry.addActivity(suffixedName, layoutName);
			structureRegistry.save(a, sc_id);
			
			complex.setAcName(suffixedName);
			complex.setXName(layoutName);
		} else if (g.getCheckedRadioButtonId() == R.id.radio_dialog_fragment) {
			String suffixedName = activityName.toLowerCase().endsWith(DataModel.ADD_DIALOG_FRAGMENT.toLowerCase())
			? activityName : activityName + DataModel.ADD_DIALOG_FRAGMENT;
			
			registry.registerActivity(suffixedName, ActivityType.DIALOG_FRAGMENT, layoutName);
			registry.save(a, sc_id);
			ActivityManager activityManager = new ActivityManager();
			activityManager.load(a, sc_id);
			activityManager.addActivity(suffixedName, layoutName);
			activityManager.save(a, sc_id);
			
			ActivityStructureRegistry structureRegistry = new ActivityStructureRegistry();
			structureRegistry.load(a, sc_id);
			structureRegistry.addActivity(suffixedName, layoutName);
			structureRegistry.save(a, sc_id);
			
			complex.setAcName(suffixedName);
			complex.setXName(layoutName);
		} else if (g.getCheckedRadioButtonId() == R.id.radio_activity) {
			String suffixedName = activityName.toLowerCase().endsWith(DataModel.ADD_ACTIVITY.toLowerCase())
			? activityName : activityName + DataModel.ADD_ACTIVITY;
			
			registry.registerActivity(suffixedName, ActivityType.ACTIVITY, layoutName);
			registry.save(a, sc_id);
			
			AndroidManifestGenerator manifest = new AndroidManifestGenerator();
			manifest.load(a, sc_id);
			manifest.addActivity(
			"." + suffixedName,
			createMainActivityAttributes(resolveTheme(toolbar, status))
			);
			manifest.setAttribute("supportsRtl", "true");
			manifest.save(a, sc_id);
			
			ActivityManager activityManager = new ActivityManager();
			activityManager.load(a, sc_id);
			activityManager.addActivity(suffixedName, layoutName);
			activityManager.save(a, sc_id);
			
			ActivityStructureRegistry structureRegistry = new ActivityStructureRegistry();
			structureRegistry.load(a, sc_id);
			structureRegistry.addActivity(suffixedName, layoutName);
			structureRegistry.save(a, sc_id);
			
			complex.setAcName(suffixedName);
			complex.setXName(layoutName);
		}
	}
	
	private void toast(String s) {
		Toast.makeText(a, s, Toast.LENGTH_SHORT).show();
	}
}
