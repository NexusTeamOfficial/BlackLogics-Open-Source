package com.nexusteam.blacklogics.dialogfragment;

import a.a.a.*;
import com.nexusteam.blacklogics.generator.source.ResourceManager;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.nexusteam.blacklogics.manager.ProjectSettingsManager;
import com.nexusteam.blacklogics.manager.ActivityManager;
import com.nexusteam.blacklogics.manager.ProjectConfigManager;
import com.nexusteam.blacklogics.manager.GradleConfigManager;
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
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.nexusteam.blacklogics.*;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.project.CreateProjectManager;
import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.dialogfragment.VersionDialog;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;
import com.nexusteam.blacklogics.colorpicker.ColorPickerDialog;
import com.nexusteam.blacklogics.generator.source.model.*;
import com.nexusteam.blacklogics.model.DataModel;

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
	private int selectedColorAccent = Color.parseColor("#FFFF4081");
	private int selectedColorPrimary = Color.parseColor("#FF3F51B5");
	private int selectedColorPrimaryDark = Color.parseColor("#FF303F9F");
	private int selectedColorPrimaryHighlight = Color.parseColor("#FFFF9800");
	private int selectedColorPrimaryNormal = Color.parseColor("#FF2196F3");
	private String selectedIconPath = "";
	private boolean isUpdateMode = false;
	private File originalProjectDir;
	private String sc_id;
	private boolean isAdvancedVisible = false;
	ActivityTypeRegistry registry;
	private EditText appName, packageName, projectName;
	private TextView versionCodeT, versionNameT;
	private CircleImageView appIcon;
	private View view16, view18, view20, view24, view26;
	private LinearLayout advancedContentLayout;
	private LinearLayout advancedSettingsToggle;
	private LinearLayout linear26, linear39, linear40;
	private RadioGroup radioGroupType;
	
	private ResourceManager resourceManager;
	
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
		
		sc_id = String.valueOf(DataModel.DEFAULT_PROJECT_ID + getNextProjectNumber());
		
		registry = ActivityTypeRegistry.getInstance();
		complex = new Complex();
		complex.setC(requireActivity());
		
		if (getArguments() != null) {
			isUpdateMode = getArguments().getBoolean("update", false);
			if (isUpdateMode) {
				originalProjectDir = new File(getArguments().getString("projectDir", ""));
				String dirName = originalProjectDir.getName();
				if (dirName.matches("\\d+")) {
					sc_id = dirName;
				} else if (dirName.startsWith("PROJECT")) {
					sc_id = dirName.substring(7);
				}
				registry.load(requireContext(), sc_id);
			}
		}
		
		complex.setId(sc_id);
	}
	
	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
		LayoutInflater inflater = requireActivity().getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.create_project_dialog, null);
		
		appName = (EditText) dialogView.findViewById(R.id.app_name);
		packageName = (EditText) dialogView.findViewById(R.id.package_name);
		projectName = (EditText) dialogView.findViewById(R.id.project_name);
		versionCodeT = (TextView) dialogView.findViewById(R.id.version_code);
		versionNameT = (TextView) dialogView.findViewById(R.id.version_name);
		appIcon = (CircleImageView) dialogView.findViewById(R.id.app_icon);
		view16 = dialogView.findViewById(R.id.view16);
		view18 = dialogView.findViewById(R.id.view18);
		view20 = dialogView.findViewById(R.id.view20);
		view24 = dialogView.findViewById(R.id.view24);
		view26 = dialogView.findViewById(R.id.view26);
		advancedContentLayout = (LinearLayout) dialogView.findViewById(R.id.linear14);
		linear26 = (LinearLayout) dialogView.findViewById(R.id.linear26);
		linear39 = (LinearLayout) dialogView.findViewById(R.id.linear39);
		linear40 = (LinearLayout) dialogView.findViewById(R.id.linear40);
		advancedSettingsToggle = (LinearLayout) dialogView.findViewById(R.id.linear13);
		final ImageView advancedIcon = (ImageView) dialogView.findViewById(R.id.advanced_icon);
		
		int nameCounter = getNextNameCounter();
		projectName.setText(DataModel.PROJECT_TEMPLATE_NAME + nameCounter);
		packageName.setText(DataModel.PROJECT_TEMPLATE_PACKAGE_NAME + nameCounter);
		
		initializeResourceManager();
		
		if (isUpdateMode && originalProjectDir != null) {
			loadExistingProjectData();
		}
		
		updateColorViews();
		
		setupClickListeners(dialogView, advancedIcon);
		
		builder.setView(dialogView);
		return builder.create();
	}
	
	private void initializeResourceManager() {
		resourceManager = new ResourceManager();
		resourceManager.setAndroidX(false);
		
		updateResourceColors();
		
		resourceManager.setStyle("AppTheme", "@android:style/Theme.Material.Light.NoActionBar");
		resourceManager.setStyle("FullScreen", "@android:style/Theme.Material.Light.NoActionBar.Fullscreen");
		resourceManager.setStyle("NoActionBar", "@android:style/Theme.Material.Light.NoActionBar");
		resourceManager.setStyle("NoStatusBar", "AppTheme");
		
		resourceManager.setDimen("app_margin", "8dp");
		resourceManager.setDimen("button_corner", "4dp");
		resourceManager.setDimen("text_size", "16sp");
		
		resourceManager.save(requireContext(), sc_id);
	}
	
	private void updateResourceColors() {
		if (resourceManager != null) {
			String primary = String.format("#FF%06X", (0xFFFFFF & selectedColorPrimary));
			String primaryDark = String.format("#FF%06X", (0xFFFFFF & selectedColorPrimaryDark));
			String accent = String.format("#FF%06X", (0xFFFFFF & selectedColorAccent));
			String highlight = String.format("#FF%06X", (0xFFFFFF & selectedColorPrimaryHighlight));
			String normal = String.format("#FF%06X", (0xFFFFFF & selectedColorPrimaryNormal));
			
			android.util.Log.d("CreateProject", "Updating colors: primary=" + primary + ", accent=" + accent);
			
			resourceManager.setColor("colorPrimary", primary);
			resourceManager.setColor("colorPrimaryDark", primaryDark);
			resourceManager.setColor("colorAccent", accent);
			resourceManager.setColor("colorControlHighlight", highlight);
			resourceManager.setColor("colorControlNormal", normal);
			resourceManager.setColor("colorBackground", "#FFEEEEEE");
			
			resourceManager.save(requireContext(), sc_id);
			
			ResourceManager verify = new ResourceManager();
			verify.load(requireContext(), sc_id);
			android.util.Log.d("CreateProject", "Verified colors after save: " + verify.getColors().toString());
		}
	}
	
	private void updateColorViews() {
		view16.setBackgroundColor(selectedColorAccent);
		view18.setBackgroundColor(selectedColorPrimary);
		view20.setBackgroundColor(selectedColorPrimaryDark);
		view24.setBackgroundColor(selectedColorPrimaryHighlight);
		view26.setBackgroundColor(selectedColorPrimaryNormal);
		
		updateResourceColors();
	}
	
	private void loadExistingProjectData() {
		try {
			CreateProjectManager manager = new CreateProjectManager(
			requireContext(),
			"blacklogics@2026".toCharArray()
			);
			
			JSONObject config = manager.loadEncryptedConfig(originalProjectDir);
			
			if (config != null) {
				appName.setText(config.optString("appName", ""));
				packageName.setText(config.optString("packageName", ""));
				projectName.setText(config.optString("projectName", ""));
				versionCodeT.setText(config.optString("versionCode", "1"));
				versionNameT.setText(config.optString("versionName", "1.0"));
				
				selectedColorAccent = Color.parseColor(config.optString("colorAccent", "#FFFF4081"));
				selectedColorPrimary = Color.parseColor(config.optString("colorPrimary", "#FF3F51B5"));
				selectedColorPrimaryDark = Color.parseColor(config.optString("colorPrimaryDark", "#FF303F9F"));
				selectedColorPrimaryHighlight = Color.parseColor(config.optString("colorPrimaryHighlight", "#FFFF9800"));
				selectedColorPrimaryNormal = Color.parseColor(config.optString("colorPrimaryNormal", "#FF2196F3"));
				
				File iconFile = new File(originalProjectDir, "icon.png");
				if (iconFile.exists()) {
					selectedIconPath = iconFile.getAbsolutePath();
					Bitmap bitmap = BitmapFactory.decodeFile(selectedIconPath);
					appIcon.setImageBitmap(bitmap);
				}
				
				updateResourceColors();
				updateColorViews();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(
			requireContext(),
			"Failed to load project config",
			Toast.LENGTH_SHORT
			).show();
		}
	}
	
	private void setupClickListeners(View dialogView, final ImageView advancedIcon) {
		advancedSettingsToggle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				isAdvancedVisible = !isAdvancedVisible;
				linear26.setVisibility(isAdvancedVisible ? View.VISIBLE : View.GONE);
				advancedContentLayout.setVisibility(isAdvancedVisible ? View.VISIBLE : View.GONE);
				advancedIcon.setImageResource(isAdvancedVisible ? R.drawable.ic_expand_less : R.drawable.ic_expand_more);
			}
		});
		
		linear39.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				setVersionCodeDialog();
			}
		});
		
		linear40.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				setVersionCodeDialog();
			}
		});
		
		view16.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showColorPicker(
				"Accent Color",
				selectedColorAccent,
				new ColorPickerDialog.OnColorSelectedListener() {
					@Override
					public void onColorSelected(int color) {
						selectedColorAccent = color;
						updateColorViews();
					}
				}
				);
			}
		});
		
		view18.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showColorPicker(
				"Primary Color",
				selectedColorPrimary,
				new ColorPickerDialog.OnColorSelectedListener() {
					@Override
					public void onColorSelected(int color) {
						selectedColorPrimary = color;
						updateColorViews();
					}
				}
				);
			}
		});
		
		view20.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showColorPicker(
				"Primary Dark",
				selectedColorPrimaryDark,
				new ColorPickerDialog.OnColorSelectedListener() {
					@Override
					public void onColorSelected(int color) {
						selectedColorPrimaryDark = color;
						updateColorViews();
					}
				}
				);
			}
		});
		
		view24.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showColorPicker(
				"Primary Highlight",
				selectedColorPrimaryHighlight,
				new ColorPickerDialog.OnColorSelectedListener() {
					@Override
					public void onColorSelected(int color) {
						selectedColorPrimaryHighlight = color;
						updateColorViews();
					}
				}
				);
			}
		});
		
		view26.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showColorPicker(
				"Primary Normal",
				selectedColorPrimaryNormal,
				new ColorPickerDialog.OnColorSelectedListener() {
					@Override
					public void onColorSelected(int color) {
						selectedColorPrimaryNormal = color;
						updateColorViews();
					}
				}
				);
			}
		});
		
		appIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, PICK_IMAGE_REQUEST);
			}
		});
		
		dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (getListener() != null) getListener().onCancel();
				dismiss();
			}
		});
		
		dialogView.findViewById(R.id.btn_create).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				attemptCreateProject();
			}
		});
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
	
	private void createProject(
	String projectName,
	String packageName,
	String appName,
	String versionCode,
	String versionName
	) {
		try {
			if (resourceManager == null) {
				resourceManager = new ResourceManager();
			}
			
			String accent = String.format("#FF%06X", (0xFFFFFF & selectedColorAccent));
			String primary = String.format("#FF%06X", (0xFFFFFF & selectedColorPrimary));
			String primaryDark = String.format("#FF%06X", (0xFFFFFF & selectedColorPrimaryDark));
			String highlight = String.format("#FF%06X", (0xFFFFFF & selectedColorPrimaryHighlight));
			String normal = String.format("#FF%06X", (0xFFFFFF & selectedColorPrimaryNormal));
			
			android.util.Log.d("CreateProject", "Colors being set: " + 
			"primary=" + primary + 
			", accent=" + accent + 
			", dark=" + primaryDark);
			
			resourceManager.setColor("colorPrimary", primary);
			resourceManager.setColor("colorPrimaryDark", primaryDark);
			resourceManager.setColor("colorAccent", accent);
			resourceManager.setColor("colorControlHighlight", highlight);
			resourceManager.setColor("colorControlNormal", normal);
			resourceManager.setColor("colorBackground", "#FFEEEEEE");
			
			resourceManager.setAppName(appName);
			
			resourceManager.save(requireContext(), sc_id);
			
			ResourceManager verifyManager = new ResourceManager();
			verifyManager.load(requireContext(), sc_id);
			Map<String, String> verifyColors = verifyManager.getColors();
			android.util.Log.d("CreateProject", "Verified colors after save: " + verifyColors.toString());
			
			CreateProjectManager manager = new CreateProjectManager(
			requireContext(), 
			"blacklogics@2026".toCharArray()
			);
			
			File Projectdir = new File(TheBlockLogicsUtil.mysc + sc_id);
			manager.setVariables(
			selectedColorAccent, 
			selectedColorPrimary, 
			selectedColorPrimaryDark, 
			selectedColorPrimaryHighlight, 
			selectedColorPrimaryNormal
			);
			
			JSONObject config = new JSONObject();
			config.put("projectName", appName);
			config.put("packageName", packageName);
			config.put("appName", appName);
			config.put("sc_id", sc_id);
			config.put("versionCode", versionCode);
			config.put("versionName", versionName);
			config.put("colorAccent", accent);
			config.put("colorPrimary", primary);
			config.put("colorPrimaryDark", primaryDark);
			config.put("colorPrimaryHighlight", highlight);
			config.put("colorPrimaryNormal", normal);
			
			if (!TextUtils.isEmpty(selectedIconPath)) {
				File iconDir = new File("/storage/emulated/0/.blacklogics/resources/icons/" + sc_id);
				if (!iconDir.exists()) {
					iconDir.mkdirs();
				}
				File iconFile = new File(iconDir, "icon.png");
				try {
					Bitmap bmp = BitmapFactory.decodeFile(selectedIconPath);
					if (bmp != null) {
						FileOutputStream fos = new FileOutputStream(iconFile);
						bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
						fos.flush();
						fos.close();
					} else {
						TheBlockLogicsUtil.copyFile(new File(selectedIconPath), iconFile);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				config.put("iconPath", iconFile.getAbsolutePath());
			} else if (isUpdateMode) {
				File existingIcon = new File("/storage/emulated/0/.blacklogics/resources/icons/" + sc_id, "icon.png");
				config.put("iconPath", existingIcon.exists() ? existingIcon.getAbsolutePath() : "default_icon.png");
			} else {
				config.put("iconPath", "default_icon.png");
			}
			
			ActivityManager activityManager = new ActivityManager()
			.addActivity(DataModel.DEFAULT_ACTIVITY, DataModel.DEFAULT_LAYOUT_NAME);
			activityManager.save(requireContext(), sc_id);
			
			File projectDir = manager.createOrUpdateProject(
			isUpdateMode,
			originalProjectDir,
			sc_id,
			projectName,
			packageName,
			appName,
			Integer.parseInt(versionCode),
			versionName,
			createMainActivityAttributes(),
			config
			);
			
			String resourcesPath = projectDir.getAbsolutePath() + "/app/src/main/res";
			resourceManager.extractResources(sc_id, resourcesPath);
			
			Toast.makeText(getContext(),
			isUpdateMode ? "Project Updated" : "Project Created",
			Toast.LENGTH_SHORT).show();
			
			if (getListener() != null) {
				getListener().onProjectCreated(projectDir.getAbsolutePath(), isUpdateMode);
			}
			
			dismiss();
			
		} catch (Exception e) {
			Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
	
	private Map<String, String> createMainActivityAttributes() {
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("exported", "true");
		attrs.put("launchMode", "singleTop");
		attrs.put("configChanges", "orientation|keyboardHidden|screenSize");
		attrs.put("windowSoftInputMode", "adjustResize");
		return attrs;
	}
	
	private int getMaxExistingProjectId() {
		File projectsDir = new File(TheBlockLogicsUtil.mysc);
		int max = -1;
		if (projectsDir.exists()) {
			File[] files = projectsDir.listFiles();
			if (files != null) {
				for (File f : files) {
					if (!f.isDirectory()) continue;
					try {
						int id = Integer.parseInt(f.getName());
						if (id > max) max = id;
					} catch (NumberFormatException ignored) {
					}
				}
			}
		}
		return max;
	}
	
	private int getNextProjectNumber() {
		int maxId = getMaxExistingProjectId();
		if (maxId < 0) {
			return 0;
		}
		return (maxId - DataModel.DEFAULT_PROJECT_ID) + 1;
	}
	
	private int getNextNameCounter() {
		int maxId = getMaxExistingProjectId();
		if (maxId < 0) {
			return 1;
		}
		return (maxId - DataModel.DEFAULT_PROJECT_ID) + 2;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
			Uri imageUri = data.getData();
			try {
				InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				inputStream.close();
				
				if (bitmap == null) {
					Toast.makeText(requireContext(), "Unsupported image format", Toast.LENGTH_SHORT).show();
					return;
				}
				
				bitmap = cropToSquare(bitmap);
				
				File iconFile = new File(requireContext().getCacheDir(), "temp_icon.png");
				FileOutputStream fos = new FileOutputStream(iconFile);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
				
				selectedIconPath = iconFile.getAbsolutePath();
				appIcon.setImageBitmap(bitmap);
				
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(requireContext(), "Error loading image", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private Bitmap cropToSquare(Bitmap src) {
		int size = Math.min(src.getWidth(), src.getHeight());
		int x = (src.getWidth() - size) / 2;
		int y = (src.getHeight() - size) / 2;
		return Bitmap.createBitmap(src, x, y, size, size);
	}
	
	private void setVersionCodeDialog() {
		int currentMajor = 1;
		int currentMinor = 1;
		int currentPatch = 0;
		
		try {
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
		
		VersionDialog dialog = new VersionDialog(getContext())
		.setInitialValues(currentMajor, currentMinor, currentPatch)
		.setVersionDialogListener(new VersionDialog.VersionDialogListener() {
			@Override
			public void onVersionSelect(String versionName, String versionCode, String minorNumber) {
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
