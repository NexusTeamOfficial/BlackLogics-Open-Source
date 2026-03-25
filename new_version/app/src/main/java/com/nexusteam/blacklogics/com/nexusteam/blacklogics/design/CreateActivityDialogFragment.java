package com.nexusteam.blacklogics.design;

import b.b.b.*;
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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.DesignActivity;
import com.besome.blacklogics.development.Complex;
import com.nexusteam.blacklogics.generator.source.model.*;
import com.nexusteam.blacklogics.model.DataModel;
import com.nexusteam.blacklogics.sdk.util.ActivityConfig;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import b.b.b.rs;
import com.nexusteam.blacklogics.generator.source.model.ActivityStructureRegistry;
import b.b.b.pm;

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
	
	// 1️⃣ YAHAN PAR ActivityConfig DECLARE KARO
	private ActivityConfig activityConfig;
	
	public CreateActivityDialogFragment(DesignActivity a, String sc_id, Complex complex,
	boolean isUpdateMode, String existingActivityName) {
		registry = ActivityTypeRegistry.getInstance();
		this.a = a;
		this.sc_id = sc_id;
		this.complex = complex;
		this.isUpdateMode = isUpdateMode;
		this.existingActivityName = existingActivityName;
		
		// 2️⃣ CONSTRUCTOR MEIN INITIALIZE KARO
		this.activityConfig = new ActivityConfig();
	}
	
	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		registry.load(context, sc_id);
		
		// 3️⃣ onAttach MEIN ActivityConfig KO INIT KARO (LOAD DATA)
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
		
		nameEt.requestFocus();
		
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
		
		/* ================= UPDATE MODE ================= */
		if (isUpdateMode && existingActivityName != null) {
			try {
				JSONObject o = complex.getActivityData(existingActivityName);
				if (o != null) {
					nameEt.setText(existingActivityName);
					cbStatus.setChecked(o.optBoolean("statusBar", true));
					cbToolbar.setChecked(o.optBoolean("toolbar", true));
					cbDrawer.setChecked(o.optBoolean("drawer", false));
					cbFab.setChecked(o.optBoolean("fab", false));
					
					rgType.check(
					"Fragment".equals(o.optString("type")) ? R.id.radio_fragment :
					"DialogFragment".equals(o.optString("type")) ? R.id.radio_dialog_fragment :
					R.id.radio_activity
					);
					
					rgOrientation.check(
					"Portrait".equals(o.optString("orientation")) ? R.id.radio_portrait :
					"Landscape".equals(o.optString("orientation")) ? R.id.radio_landscape :
					R.id.radio_both
					);
					
					// 4️⃣ UPDATE MODE MEIN ActivityConfig SE DATA CHECK KAR SAKTE HO
					// Example: Check if activity has toolbar from config
					boolean hasToolbar = activityConfig.hasToolbar(existingActivityName);
					boolean hasFab = activityConfig.hasFab(existingActivityName);
					// etc.
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		/* ================= PREVIEW ================= */
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
		
		/* ================= BUTTONS ================= */
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		btnSave.setOnClickListener(new View.OnClickListener() {
	@Override
	public void onClick(View v) {
		// 🔥 SAVE BUTTON CLICK PE TOAST DALDO
		if (isUpdateMode) {
			toast("Updating activity...");
		} else {
			toast("Creating new activity...");
		}
		
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
				
				// 5️⃣ UPDATE MODE MEIN ActivityConfig MEIN DATA UPDATE KARO
				activityConfig.setActivityConfig(
					actName, status, toolbar, drawer, fab, type, orientation, xmlName
				);
				
				// 6️⃣ AGAR TOOLBAR/STATUS BAR CHANGE HUA TOH THEME UPDATE KARO
				activityConfig.updateActivityTheme(actName);
				
				// ✅ UPDATE SUCCESS TOAST
				toast("Activity updated successfully!");
				
			} else {
				complex.addActivityToManifest(actName);
				complex.enableFab(actName, fab);
				complex.enableToolBar(actName, complex.getAndroidXEnable(), toolbar);
				
				setupCheck(radioGroupType, actName, xmlName, status, toolbar);
				
				// 7️⃣ NEW ACTIVITY KE LIYE ActivityConfig MEIN DATA SAVE KARO
				activityConfig.setActivityConfig(
					actName, status, toolbar, drawer, fab, type, orientation, xmlName
				);
				
				// 8️⃣ AGAR YEH PEHLI ACTIVITY HAI TOH LAUNCHER SET KARO
				if (activityConfig.getActivityCount() == 0) { // Pehli activity
					activityConfig.setLauncherActivity(actName);
				}
				
				// ✅ CREATE SUCCESS TOAST
				toast("Activity created successfully!");
			}
			
			// 9️⃣ SAB KUCH SAVE KARO - ACTIVITY CONFIG, MANIFEST, GRADLE
			activityConfig.save(a, sc_id);
			
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
	
	/* ================= HELPERS ================= */
	
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
		Map<String, String> attrs = new HashMap<String, String>(); // Java 7: <> nahi
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
		if (status) return "NoActionBar";
		return "FullScreen";
	}
	
	private void setupCheck(RadioGroup g, String activityName, String layoutName, boolean status, boolean toolbar) {
		if (g.getCheckedRadioButtonId() == R.id.radio_fragment) {
			registry.registerActivity(activityName, ActivityType.FRAGMENT_ACTIVITY, layoutName);
			registry.save(a, sc_id);
			xq activityManager = new xq();
			activityManager.load(a, sc_id);
			activityManager.addActivity(activityName + DataModel.ADD_FRAGMENT, layoutName);
			activityManager.save(a, sc_id);
			complex.setAcName(activityName + DataModel.ADD_FRAGMENT);
			complex.setXName(layoutName);
		} else if (g.getCheckedRadioButtonId() == R.id.radio_dialog_fragment) {
			registry.registerActivity(activityName, ActivityType.DIALOG_FRAGMENT, layoutName);
			registry.save(a, sc_id);
			xq activityManager = new xq();
			activityManager.load(a, sc_id);
			activityManager.addActivity(activityName + DataModel.ADD_DIALOG_FRAGMENT, layoutName);
			activityManager.save(a, sc_id);
			complex.setAcName(activityName + DataModel.ADD_DIALOG_FRAGMENT);
			complex.setXName(layoutName);
		} else if (g.getCheckedRadioButtonId() == R.id.radio_activity) {
			registry.registerActivity(activityName, ActivityType.ACTIVITY, layoutName);
			registry.save(a, sc_id);
			aq manifest = new aq();
			manifest.load(a, sc_id);
			manifest.addActivity(
			"." + activityName + DataModel.ADD_ACTIVITY,
			createMainActivityAttributes(resolveTheme(toolbar, status))
			);
			manifest.setAttribute("supportsRtl", "true");
			manifest.save(a, sc_id);
            
            AndroidManifestGenerator Manifest = new AndroidManifestGenerator();
            Manifest.load(a, sc_id);
            Manifest.addActivity("." + activityName + DataModel.ADD_ACTIVITY, createMainActivityAttributes(resolveTheme(toolbar, status)));
           Manifest.save(a, sc_id);
			
			xq activityManager = new xq();
			activityManager.load(a, sc_id);
			activityManager.addActivity(activityName + DataModel.ADD_ACTIVITY, layoutName);
			activityManager.save(a, sc_id);
			complex.setAcName(activityName + DataModel.ADD_ACTIVITY);
			complex.setXName(layoutName);
		}
	}
	
	private void toast(String s) {
		Toast.makeText(a, s, Toast.LENGTH_SHORT).show();
	}
}
