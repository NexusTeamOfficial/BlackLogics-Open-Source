package com.besome.blacklogics.dialogfragment;

import a.a.a.*;
import b.b.b.ar;
import b.b.b.aq;
import b.b.b.rs;
import b.b.b.xq;
import b.b.b.pm;
import b.b.b.gq;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.besome.blacklogics.*;
import com.besome.blacklogics.R;
import com.besome.blacklogics.development.Complex;
import com.besome.blacklogics.dialogfragment.VersionDialog;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;
import com.besome.blacklogics.colorpicker.ColorPickerDialog;

import de.hdodenhof.circleimageview.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 

public class CreateProjectDialogFragment extends DialogFragment {
	
	private static final int PICK_IMAGE_REQUEST = 1;
	private Complex complex;
	private int selectedColorAccent = Color.parseColor("#FF4081");
	private int selectedColorPrimary = Color.parseColor("#3F51B5");
	private int selectedColorPrimaryDark = Color.parseColor("#303F9F");
	private int selectedColorPrimaryHighlight = Color.parseColor("#FF9800");
	private int selectedColorPrimaryNormal = Color.parseColor("#2196F3");
	private String selectedIconPath = "";
	private boolean isUpdateMode = false;
	private File originalProjectDir;
	private String sc_id;
	private boolean isAdvancedVisible = false;
	
	private EditText appName, packageName, projectName;
	private TextView versionCodeT, versionNameT;
	private CircleImageView appIcon;
	private View view16, view18, view20, view24, view26;
	private LinearLayout advancedContentLayout;
	private LinearLayout advancedSettingsToggle;
	private LinearLayout linear26, linear39, linear40;
	
	private ar resourceManager;
	
	public interface OnProjectCreatedListener {
		void onProjectCreated(String projectPath, boolean isUpdate);
		void onCancel();
	}
	
	public static CreateProjectDialogFragment newInstance(boolean isUpdate, File projectDir) {
		CreateProjectDialogFragment fragment = new CreateProjectDialogFragment();
		Bundle args = new Bundle();
		args.putBoolean("update", isUpdate);
		if (isUpdate && projectDir != null) {
			args.putString("projectDir", projectDir.getAbsolutePath());
		}
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			isUpdateMode = getArguments().getBoolean("update", false);
			if (isUpdateMode) {
				originalProjectDir = new File(getArguments().getString("projectDir", ""));
			}
		}
		complex = new Complex();
		complex.setC(requireActivity());
		sc_id = String.valueOf(601 + getNextProjectNumber());
		complex.setId(sc_id);
	}
	
	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
		LayoutInflater inflater = requireActivity().getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.create_project_dialog, null);
		
		// Initialize views
		appName = dialogView.findViewById(R.id.app_name);
		packageName = dialogView.findViewById(R.id.package_name);
		projectName = dialogView.findViewById(R.id.project_name);
		versionCodeT = dialogView.findViewById(R.id.version_code);
		versionNameT = dialogView.findViewById(R.id.version_name);
		appIcon = dialogView.findViewById(R.id.app_icon);
		view16 = dialogView.findViewById(R.id.view16);
		view18 = dialogView.findViewById(R.id.view18);
		view20 = dialogView.findViewById(R.id.view20);
		view24 = dialogView.findViewById(R.id.view24);
		view26 = dialogView.findViewById(R.id.view26);
		advancedContentLayout = dialogView.findViewById(R.id.linear14);
		linear26 = dialogView.findViewById(R.id.linear26);
		linear39 = dialogView.findViewById(R.id.linear39);
		linear40 = dialogView.findViewById(R.id.linear40);
		advancedSettingsToggle = dialogView.findViewById(R.id.linear13);
		ImageView advancedIcon = dialogView.findViewById(R.id.advanced_icon);
		
		// Set initial values
		int nameCounter = getNextNameCounter();
		projectName.setText("NewProject" + nameCounter);
		packageName.setText("com.my.newproject" + nameCounter);
		updateColorViews();
		
		// Advanced settings toggle
		advancedSettingsToggle.setOnClickListener(v -> {
			isAdvancedVisible = !isAdvancedVisible;
			linear26.setVisibility(isAdvancedVisible ? View.VISIBLE : View.GONE);
			advancedContentLayout.setVisibility(isAdvancedVisible ? View.VISIBLE : View.GONE);
			advancedIcon.setImageResource(isAdvancedVisible ? R.drawable.ic_expand_less : R.drawable.ic_expand_more);
		});
		
		// Color pickers
		view16.setOnClickListener(v -> showColorPicker("ColorAccent", selectedColorAccent, color -> {
			selectedColorAccent = color;
			updateColorViews();
		}));
		view18.setOnClickListener(v -> showColorPicker("ColorPrimary", selectedColorPrimary, color -> {
			selectedColorPrimary = color;
			updateColorViews();
		}));
		view20.setOnClickListener(v -> showColorPicker("ColorPrimaryDark", selectedColorPrimaryDark, color -> {
			selectedColorPrimaryDark = color;
			updateColorViews();
		}));
		view24.setOnClickListener(v -> showColorPicker("ColorPrimaryHighlight", selectedColorPrimaryHighlight, color -> {
			selectedColorPrimaryHighlight = color;
			updateColorViews();
		}));
		view26.setOnClickListener(v -> showColorPicker("ColorPrimaryNormal", selectedColorPrimaryNormal, color -> {
			selectedColorPrimaryNormal = color;
			updateColorViews();
		}));
		linear39.setOnClickListener(v -> setVersionCodeDialog());
		linear40.setOnClickListener(v -> setVersionCodeDialog());
		
		
		// Image selection
		appIcon.setOnClickListener(v -> {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, PICK_IMAGE_REQUEST);
		});
		
		// Buttons
		dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
			if (getListener() != null) getListener().onCancel();
			dismiss();
		});
		
		dialogView.findViewById(R.id.btn_create).setOnClickListener(v -> attemptCreateProject());
		
		if (isUpdateMode && originalProjectDir != null) {
			try {
				JSONObject config = TheBlockLogicsUtil.getProjectConfig(new File(originalProjectDir, "config"));
				if (config != null) {
					appName.setText(config.optString("appName", ""));
					packageName.setText(config.optString("packageName", ""));
					projectName.setText(config.optString("projectName", ""));
					versionCodeT.setText(config.optString("versionCode", "1"));
					versionNameT.setText(config.optString("versionName", "1.0"));
					selectedColorAccent = Color.parseColor(config.optString("colorAccent", "#FF4081"));
					selectedColorPrimary = Color.parseColor(config.optString("colorPrimary", "#3F51B5"));
					selectedColorPrimaryDark = Color.parseColor(config.optString("colorPrimaryDark", "#303F9F"));
					selectedColorPrimaryHighlight = Color.parseColor(config.optString("colorPrimaryHighlight", "#FF9800"));
					selectedColorPrimaryNormal = Color.parseColor(config.optString("colorPrimaryNormal", "#2196F3"));
					File iconFile = new File(originalProjectDir, "icon.png");
					if (iconFile.exists()) {
						selectedIconPath = iconFile.getAbsolutePath();
						Bitmap bitmap = BitmapFactory.decodeFile(selectedIconPath);
						appIcon.setImageBitmap(bitmap);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		updateColorViews();
		initializeResourceManager();
		builder.setView(dialogView);
		return builder.create();
	}
	
	private void updateColorViews() {
		view16.setBackgroundColor(selectedColorAccent);
		view18.setBackgroundColor(selectedColorPrimary);
		view20.setBackgroundColor(selectedColorPrimaryDark);
		view24.setBackgroundColor(selectedColorPrimaryHighlight);
		view26.setBackgroundColor(selectedColorPrimaryNormal);
	}
	
	private void showColorPicker(String title, int initialColor, ColorPickerDialog.OnColorSelectedListener listener) {
		ColorPickerDialog dialog = new ColorPickerDialog(requireContext(), initialColor);
		dialog.setTitle(title);
		dialog.setOnColorSelectedListener(listener);
		dialog.show();
	}
	
	private void attemptCreateProject() {
		String appNameText = appName.getText().toString().trim();
		String pkgName = packageName.getText().toString().trim();
		String projectNameText = projectName.getText().toString().trim();
		String vCode = versionCodeT.getText().toString().trim();
		String vName = versionNameT.getText().toString().trim();
		
		boolean cancel = false;
		View focusView = null;
		
		if (TextUtils.isEmpty(appNameText)) {
			appName.setError("Application name is required");
			focusView = appName;
			cancel = true;
		}
		if (TextUtils.isEmpty(pkgName)) {
			packageName.setError("Package name is required");
			focusView = packageName;
			cancel = true;
		} else if (!isValidPackageName(pkgName)) {
			packageName.setError("Invalid package name");
			focusView = packageName;
			cancel = true;
		}
		if (TextUtils.isEmpty(projectNameText)) {
			projectName.setError("Project name is required");
			focusView = projectName;
			cancel = true;
		}
		if (TextUtils.isEmpty(vCode)) {
			versionCodeT.setError("Version code is required");
			focusView = versionCodeT;
			cancel = true;
		} else if (!vCode.matches("\\d+")) {
			versionCodeT.setError("Must be a number");
			focusView = versionCodeT;
			cancel = true;
		}
		if (TextUtils.isEmpty(vName)) {
			versionNameT.setError("Version name is required");
			focusView = versionNameT;
			cancel = true;
		} else if (!vName.matches("[0-9]+(\\.[0-9]+)*")) {
			versionNameT.setError("Invalid version format (e.g. 1.0.0)");
			focusView = versionNameT;
			cancel = true;
		}
		
		if (cancel) {
			focusView.requestFocus();
		} else {
			createProject(projectNameText, pkgName, appNameText, vCode, vName);
		}
	}
	
	private boolean isValidPackageName(String packageName) {
		return packageName.matches("^([a-zA-Z_][a-zA-Z0-9_]*\\.)+([a-zA-Z_][a-zA-Z0-9_]*)$");
	}
	
	private void createProject(String projectName, String packageName, String appName, String versionCode, String versionName) {
		try {
			String projectDirPath = TheBlockLogicsUtil.projects + sc_id;
			File projectDir = new File(projectDirPath);
			
			if (!isUpdateMode) {
				if (!projectDir.exists()) {
					projectDir.mkdirs();
				}
				
				// Create project structure using pm (Project Manager)
				pm projectManager = new pm()
				.setPackageName(packageName)
				.setProjectName(projectName)
				.setScName(projectName)
				.setProjectId(sc_id)
				.setMinSdk(21)
				.setTargetSdk(34)
				.setVersionCode(Integer.parseInt(versionCode))
				.setVersionName(versionName);
				
				// Save project configuration
				projectManager.save(requireContext());
				
				// Create manifest using aq (Android Manifest)
				aq manifestBuilder = new aq()
				.setPackageName(packageName)
				.setAppName(appName)
				.setAppIcon("@mipmap/icon")
				.setThemeColor("AppTheme")
				.setAndroidX(false)
				.addActivity(".MainActivity", createMainActivityAttributes())
				.setAttribute("supportsRtl", "true");
				
				// Save manifest configuration
				manifestBuilder.save(requireContext(), sc_id);
				
				// Create activity manager using xq
				xq activityManager = new xq()
				.addActivity("MainActivity", "main");
				
				// Save activity configuration
				activityManager.save(requireContext(), sc_id);
				
				rs settings = new rs(sc_id);
				settings.setResourceEncrypt(false)
				.setObfuscateCode(false)
				.setTargetSdk(34)
				.setMinSdk(21)
				.save(requireContext());
				
				gq gradleManager = new gq()
				.setApplicationId(packageName)
				.setMinSdk(21)
				.setTargetSdk(34)
				.setCompileSdk(34)
				.setVersionCode(Integer.parseInt(versionCode))
				.setVersionName(versionName)
				.addDependency("implementation 'androidx.appcompat:appcompat:1.6.1'")
				.addDependency("implementation 'com.google.android.material:material:1.9.0'");
				
				gradleManager.save(requireContext(), sc_id);
				
				if (resourceManager != null) {
					resourceManager.save(requireContext(), sc_id);
				}
				
				complex.setAcName("MainActivity");
				complex.setXName("main");
                
                complex.enableToolBar("MainActivity", false, true);
                complex.enableFab("MainActivity", true);
				
				complex.setProjectName(projectName);
				complex.setScName(projectName);
				complex.setPkgName(packageName);
				
			} else {
				projectDir = originalProjectDir;
				
				// Update existing project
				pm projectManager = new pm()
				.setProjectId(sc_id);
				projectManager.load(requireContext(), sc_id);
				projectManager.setPackageName(packageName)
				.setProjectName(projectName)
				.setVersionCode(Integer.parseInt(versionCode))
				.setVersionName(versionName)
				.save(requireContext());
				
				// Update manifest
				aq manifestBuilder = new aq();
				manifestBuilder.load(requireContext(), sc_id);
				manifestBuilder.setPackageName(packageName)
				.setAppName(appName)
				.save(requireContext(), sc_id);
			}
			
			// Save project config (your existing code)
			JSONObject config = new JSONObject();
			config.put("projectName", appName);
			config.put("packageName", packageName);
			config.put("appName", appName);
			config.put("sc_id", sc_id);
			config.put("versionCode", versionCode);
			config.put("versionName", versionName);
			config.put("colorAccent", String.format("#%06X", (0xFFFFFF & selectedColorAccent)));
			config.put("colorPrimary", String.format("#%06X", (0xFFFFFF & selectedColorPrimary)));
			config.put("colorPrimaryDark", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryDark)));
			config.put("colorPrimaryHighlight", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryHighlight)));
			config.put("colorPrimaryNormal", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryNormal)));
			
			if (!TextUtils.isEmpty(selectedIconPath)) {
				File iconFile = new File(projectDir, "icon.png");
				TheBlockLogicsUtil.copyFile(new File(selectedIconPath), iconFile);
				config.put("iconPath", "icon.png");
			} else if (isUpdateMode) {
				File existingIcon = new File(projectDir, "icon.png");
				config.put("iconPath", existingIcon.exists() ? "icon.png" : "default_icon.png");
			} else {
				config.put("iconPath", "default_icon.png");
			}
			
			TheBlockLogicsUtil.writeFile(projectDir.getAbsolutePath() + "/config", config.toString());
			Toast.makeText(requireContext(), isUpdateMode ? "Project updated successfully!" : "Project created successfully!", Toast.LENGTH_SHORT).show();
			
			if (getListener() != null) getListener().onProjectCreated(projectDir.getAbsolutePath(), isUpdateMode);
			dismiss();
		} catch (JSONException e) {
			Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	// Helper method to create main activity attributes
	private Map<String, String> createMainActivityAttributes() {
		Map<String, String> attrs = new HashMap<>();
		attrs.put("exported", "true");
		attrs.put("launchMode", "singleTop");
		attrs.put("configChanges", "orientation|keyboardHidden|screenSize");
		attrs.put("windowSoftInputMode", "adjustResize");
		return attrs;
	}
	
	
	private int getNextProjectNumber() {
		File projectsDir = new File(TheBlockLogicsUtil.projects);
		return projectsDir.exists() ? projectsDir.listFiles() != null ? projectsDir.listFiles().length : 0 : 0;
	}
	
	private int getNextNameCounter() {
		File projectsDir = new File(TheBlockLogicsUtil.projects);
		return projectsDir.exists() ? projectsDir.listFiles() != null ? projectsDir.listFiles().length + 1 : 1 : 1;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
			Uri imageUri = data.getData();
			try {
				File iconFile = new File(requireContext().getCacheDir(), "temp_icon.png");
				selectedIconPath = iconFile.getAbsolutePath();
				InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
				OutputStream outputStream = new FileOutputStream(iconFile);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, length);
				}
				inputStream.close();
				outputStream.close();
				Bitmap bitmap = BitmapFactory.decodeFile(selectedIconPath);
				appIcon.setImageBitmap(bitmap);
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(requireContext(), "Error loading image", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void initializeResourceManager() {
		resourceManager = new ar().setAndroidX(false);
		
		// Set initial colors
		updateResourceColors();
		
		// Set styles and dimensions
		resourceManager.setStyle("AppTheme", "@android:style/Theme.Material.Light");
		resourceManager.setStyle("FullScreen", "@android:style/Theme.Material.Light.NoActionBar.Fullscreen");
		resourceManager.setStyle("NoActionBar", "@android:style/Theme.Material.Light.NoActionBar");
		resourceManager.setStyle("NoStatusBar", "AppTheme");
		
		resourceManager.setDimen("app_margin", "16dp");
		resourceManager.setDimen("button_corner", "8dp");
		resourceManager.setDimen("text_size", "14sp");
	}
	
	private void updateResourceColors() {
		if (resourceManager != null) {
			resourceManager.setColor("colorPrimary", String.format("#%06X", (0xFFFFFF & selectedColorPrimary)));
			resourceManager.setColor("colorPrimaryDark", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryDark)));
			resourceManager.setColor("colorAccent", String.format("#%06X", (0xFFFFFF & selectedColorAccent)));
			resourceManager.setColor("colorControlHighlight", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryHighlight)));
			resourceManager.setColor("colorControlNormal", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryNormal)));
			resourceManager.setColor("colorBackground", "#F5F5F5");
		}
	}
	
	private void _createProjectJson(String pkgName) {
		JSONObject json = new JSONObject();
		try {
			json.put("javaFiles", TheBlockLogicsUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/app/src/main/java/" + pkgName.replace(".", "/") + "/");
			json.put("manifestFile", TheBlockLogicsUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/app/src/main/AndroidManifest.xml");
			TheBlockLogicsUtil.writeFile(TheBlockLogicsUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/project.json", json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void setVersionCodeDialog() {
		// Get current version values from TextViews
		int currentMajor = 1;
		int currentMinor = 1;
		int currentPatch = 0;
		
		try {
			// Extract numbers safely
			if (versionCodeT.getText() != null && !versionCodeT.getText().toString().isEmpty()) {
				currentMajor = Integer.parseInt(versionCodeT.getText().toString());
			}
			
			if (versionNameT.getText() != null && !versionNameT.getText().toString().isEmpty()) {
				String[] parts = versionNameT.getText().toString().split("\\.");
				if (parts.length >= 2) {
					currentMinor = Integer.parseInt(parts[0]);
					currentPatch = Integer.parseInt(parts[1]);
				}
			}
		} catch (Exception ignored) {}
		
		// Now open dialog with pre-filled values
		VersionDialog dialog = new VersionDialog(getContext())
		.setInitialValues(currentMajor, currentMinor, currentPatch)
		.setVersionDialogListener(new VersionDialog.VersionDialogListener() {
			@Override
			public void onVersionSelect(String versionName, String versionCode, String minorNumber) {
				// Save selected values back to TextViews
				versionCodeT.setText(versionCode);
				versionNameT.setText(versionName);
			}
		});
		
		dialog.show();
	}
	
	
	private OnProjectCreatedListener getListener() {
		return (OnProjectCreatedListener) getActivity();
	}
}
