package com.besome.blacklogics;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
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
import androidx.viewpager.*;
import androidx.viewpager2.*;
import com.besome.sketch.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.material.*;
import com.google.android.material.button.*;
import com.google.android.material.textfield.*;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import com.besome.blacklogics.development.Complex;
import com.besome.blacklogics.colorpicker.*;
import com.besome.blacklogics.adapter.ProjectAdapter;
import a.a.a.Lx;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class CreateProjectActivity extends AppCompatActivity {
	
	private int selectedColorAccent = Color.parseColor("#FF4081");
	private int selectedColorPrimary = Color.parseColor("#3F51B5");
	private int selectedColorPrimaryDark = Color.parseColor("#303F9F");
	private int selectedColorPrimaryHighlight = Color.parseColor("#FF9800");
	private int selectedColorPrimaryNormal = Color.parseColor("#2196F3");
	private static final int PICK_IMAGE_REQUEST = 1;
	private String selectedIconPath = "";  
	private boolean isUpdateMode = false;
	private ProjectAdapter.ProjectItem projectToUpdate = null;
	private String originalProjectDir = "";
	public Complex complex;
	private HashMap<String, Object> sq = new HashMap<>();
	private HashMap<String, Object> pathMap = new HashMap<>();
	private String sc_id = "";
	
	private LinearLayout linear4;
	private MaterialTextView textview9;
	private FrameLayout view5;
	private MaterialTextView textview10;
	private MaterialTextView textview17;
	private TextInputLayout appNameInputLayout;
	private MaterialTextView textview12;
	private TextInputLayout pkgNameInputLayout;
	private MaterialTextView textview13;
	private TextInputLayout projectNameInputLayout;
	private HorizontalScrollView hscroll1;
	private View linear20;
	private LinearLayout linear21;
	private LinearLayout linear6;
	private ImageView scIcon;
	private TextInputEditText srcName;
	private TextInputEditText prgPkgName;
	private TextInputEditText projectName;
	private LinearLayout linear11;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout linear12;
	private LinearLayout linear14;
	private LinearLayout linear18;
	private LinearLayout linear16;
	private FrameLayout colorAccent;
	private MaterialTextView textview18;
	private FrameLayout cP;
	private MaterialTextView textview19;
	private FrameLayout cAD;
	private MaterialTextView textview20;
	private FrameLayout cPH;
	private MaterialTextView textview22;
	private FrameLayout cPN;
	private MaterialTextView textview21;
	private TextInputLayout textinputlayout1;
	private TextInputLayout textinputlayout3;
	private TextInputEditText versionCode;
	private TextInputEditText versionName;
	private MaterialButton cancelProject;
	private MaterialButton createProject;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.create_project);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		if (getIntent().hasExtra("update") && getIntent().getBooleanExtra("update", false)) {
				        isUpdateMode = true;
				        projectToUpdate = (ProjectAdapter.ProjectItem) getIntent().getSerializableExtra("project");
				        originalProjectDir = projectToUpdate.projectDir.getAbsolutePath();
				    }
		linear4 = findViewById(R.id.linear4);
		textview9 = findViewById(R.id.textview9);
		view5 = findViewById(R.id.view5);
		textview10 = findViewById(R.id.textview10);
		textview17 = findViewById(R.id.textview17);
		appNameInputLayout = findViewById(R.id.appNameInputLayout);
		textview12 = findViewById(R.id.textview12);
		pkgNameInputLayout = findViewById(R.id.pkgNameInputLayout);
		textview13 = findViewById(R.id.textview13);
		projectNameInputLayout = findViewById(R.id.projectNameInputLayout);
		hscroll1 = findViewById(R.id.hscroll1);
		linear20 = findViewById(R.id.linear20);
		linear21 = findViewById(R.id.linear21);
		linear6 = findViewById(R.id.linear6);
		scIcon = findViewById(R.id.scIcon);
		srcName = findViewById(R.id.srcName);
		prgPkgName = findViewById(R.id.prgPkgName);
		projectName = findViewById(R.id.projectName);
		linear11 = findViewById(R.id.linear11);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		linear12 = findViewById(R.id.linear12);
		linear14 = findViewById(R.id.linear14);
		linear18 = findViewById(R.id.linear18);
		linear16 = findViewById(R.id.linear16);
		colorAccent = findViewById(R.id.colorAccent);
		textview18 = findViewById(R.id.textview18);
		cP = findViewById(R.id.cP);
		textview19 = findViewById(R.id.textview19);
		cAD = findViewById(R.id.cAD);
		textview20 = findViewById(R.id.textview20);
		cPH = findViewById(R.id.cPH);
		textview22 = findViewById(R.id.textview22);
		cPN = findViewById(R.id.cPN);
		textview21 = findViewById(R.id.textview21);
		textinputlayout1 = findViewById(R.id.textinputlayout1);
		textinputlayout3 = findViewById(R.id.textinputlayout3);
		versionCode = findViewById(R.id.versionCode);
		versionName = findViewById(R.id.versionName);
		cancelProject = findViewById(R.id.cancelProject);
		createProject = findViewById(R.id.createProject);
	}
	
	private void initializeLogic() {
		int projectId = 601 + getNextProjectNumber();
		complex = new Complex();
		complex.setId(String.valueOf(projectId));
		sc_id = String.valueOf(projectId);
		int nameCounter = getNextNameCounter();
		projectName.setText("NewProject" + nameCounter);
		    prgPkgName.setText("com.example.project" + nameCounter);
		
		versionCode.setText("1");
		        versionName.setText("1.0");
		        
		        // Set initial colors
		        updateColorViews();
		        
		        // Set click listeners for color selection
		        colorAccent.setOnClickListener(v -> showColorPicker("Accent Color", selectedColorAccent, color -> {
			            selectedColorAccent = color;
			            updateColorViews();
			        }));
		        
		        cP.setOnClickListener(v -> showColorPicker("Primary Color", selectedColorPrimary, color -> {
			            selectedColorPrimary = color;
			            updateColorViews();
			        }));
		        
		        cAD.setOnClickListener(v -> showColorPicker("Primary Dark", selectedColorPrimaryDark, color -> {
			            selectedColorPrimaryDark = color;
			            updateColorViews();
			        }));
		        
		        cPH.setOnClickListener(v -> showColorPicker("Primary Highlight", selectedColorPrimaryHighlight, color -> {
			            selectedColorPrimaryHighlight = color;
			            updateColorViews();
			        }));
		        
		        cPN.setOnClickListener(v -> showColorPicker("Primary Normal", selectedColorPrimaryNormal, color -> {
			            selectedColorPrimaryNormal = color;
			            updateColorViews();
			        }));
		        
		        // Set click listeners for buttons
		        cancelProject.setOnClickListener(v -> finish());
		        
		        createProject.setOnClickListener(v -> attemptCreateProject());
		scIcon.setOnClickListener(v -> {
			    // Open gallery to select an image
			    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			    intent.setType("image/*");
			    startActivityForResult(intent, PICK_IMAGE_REQUEST);
		});
		if (isUpdateMode && projectToUpdate != null) {
			        // Set title to indicate update mode
			        textview9.setText("Update Project");
			        
			        // Fill fields with existing project data
			        projectName.setText(projectToUpdate.projectName);
			        prgPkgName.setText(projectToUpdate.packageName);
			        versionCode.setText(projectToUpdate.versionCode);
			        versionName.setText(projectToUpdate.versionName);
			        
			        // Try to load the project icon
			        File iconFile = new File(projectToUpdate.projectDir, "icon.png");
			        if (iconFile.exists()) {
				            selectedIconPath = iconFile.getAbsolutePath();
				            scIcon.setImageDrawable(Drawable.createFromPath(selectedIconPath));
				        }
			        
			        // Load colors from config
			        try {
				            File configFile = new File(projectToUpdate.projectDir, "config");
				            if (configFile.exists()) {
					                JSONObject config = TheBlockLogicsUtil.getProjectConfig(configFile);
					                if (config != null) {
						                    selectedColorAccent = Color.parseColor(config.optString("colorAccent", "#FF4081"));
						                    selectedColorPrimary = Color.parseColor(config.optString("colorPrimary", "#3F51B5"));
						                    selectedColorPrimaryDark = Color.parseColor(config.optString("colorPrimaryDark", "#303F9F"));
						                    selectedColorPrimaryHighlight = Color.parseColor(config.optString("colorPrimaryHighlight", "#FF9800"));
						                    selectedColorPrimaryNormal = Color.parseColor(config.optString("colorPrimaryNormal", "#2196F3"));
						                }
					            }
				        } catch (Exception e) {
				            e.printStackTrace();
				        }
			        
			        // Update button text
			        createProject.setText("Update Project");
			    } else {
			        // New project mode
			        int projectIdq = 601 + getNextProjectNumber();
			        complex = new Complex();
			        complex.setId(String.valueOf(projectIdq));
			        versionCode.setText("1");
			        versionName.setText("1.0");
			    }
		    
		    // Set initial colors
		    updateColorViews();
		
		
	}
	
	private void updateColorViews() {
			colorAccent.setBackgroundColor(selectedColorAccent);
			cP.setBackgroundColor(selectedColorPrimary);
			cAD.setBackgroundColor(selectedColorPrimaryDark);
			cPH.setBackgroundColor(selectedColorPrimaryHighlight);
			cPN.setBackgroundColor(selectedColorPrimaryNormal);
	}
	
	private void showColorPicker(String title, int initialColor, ColorPickerDialog.OnColorSelectedListener listener) {
			ColorPickerDialog dialog = new ColorPickerDialog(this, initialColor);
			dialog.setTitle(title);
			dialog.setOnColorSelectedListener(listener);
			dialog.show();
	}
	
	private void attemptCreateProject() {
			// Reset errors
			projectName.setError(null);
			prgPkgName.setError(null);
			srcName.setError(null);
			versionCode.setError(null);
			versionName.setError(null);
			
			// Get values
			String pName = projectName.getText().toString().trim();
			String pkgName = prgPkgName.getText().toString().trim();
			String srcDir = srcName.getText().toString().trim();
			String vCode = versionCode.getText().toString().trim();
			String vName = versionName.getText().toString().trim();
			
			boolean cancel = false;
			View focusView = null;
			
			// Validate project name
			if (TextUtils.isEmpty(pName)) {
					projectName.setError("Project name is required");
					focusView = projectName;
					cancel = true;
			}
			
			// Validate package name
			if (TextUtils.isEmpty(pkgName)) {
					prgPkgName.setError("Package name is required");
					focusView = prgPkgName;
					cancel = true;
			} else if (!isValidPackageName(pkgName)) {
					prgPkgName.setError("Invalid package name");
					focusView = prgPkgName;
					cancel = true;
			}
			
			// Validate source directory
			if (TextUtils.isEmpty(srcDir)) {
					srcName.setError("Source directory is required");
					focusView = srcName;
					cancel = true;
			} else if (!srcDir.matches("[a-zA-Z0-9_ ]+")) {
					srcName.setError("Only letters, numbers, underscore and spaces allowed");
					focusView = srcName;
					cancel = true;
			}
			
			// Validate version code
			if (TextUtils.isEmpty(vCode)) {
					versionCode.setError("Version code is required");
					focusView = versionCode;
					cancel = true;
			} else if (!vCode.matches("\\d+")) {
					versionCode.setError("Must be a number");
					focusView = versionCode;
					cancel = true;
			}
			
			// Validate version name
			if (TextUtils.isEmpty(vName)) {
					versionName.setError("Version name is required");
					focusView = versionName;
					cancel = true;
			} else if (!vName.matches("[0-9]+(\\.[0-9]+)*")) {
					versionName.setError("Invalid version format (e.g. 1.0.0)");
					focusView = versionName;
					cancel = true;
			}
			
			if (cancel) {
					focusView.requestFocus();
			} else {
					createProject(pName, pkgName, srcDir, vCode, vName);
			}
	}
	
	private boolean isValidPackageName(String packageName) {
			return packageName.matches("^([a-zA-Z_][a-zA-Z0-9_]*\\.)+([a-zA-Z_][a-zA-Z0-9_]*)$");
	}
	
	private void createProject(String projectName, String packageName, String srcDir, 
	String versionCode, String versionName) {
			try {
					String projectDirPath;
					String projectId;
					
					if (isUpdateMode && projectToUpdate != null) {
							// Update existing project
							projectDirPath = originalProjectDir;
							projectId = projectToUpdate.scId;
				            complex.updateProjectName(projectName);
				            complex.updatePackageName(packageName);
					} else {
							// Create new project
							int newProjectId = 601 + getNextProjectNumber();
				            int nameCounter = getNextNameCounter();
							projectId = String.valueOf(newProjectId);
							projectDirPath = TheBlockLogicsUtil.projects + projectId;
							
							// Create project directory structure
							File projectDir = new File(projectDirPath);
							if (!projectDir.exists()) {
									projectDir.mkdirs();
							}
							
							_createProjectJson(packageName);
							
							// Create subdirectories if they don't exist
							/* new File(projectDir, "/files/resource/java").mkdirs();
			new File(projectDir, "/files/resource/assets").mkdirs();
			new File(projectDir, "/files/resource/res").mkdirs();
			*/
							complex.setPkgName(packageName);
							complex.setProjectName(projectName);
							complex.setGradleBuildConfig(null);
							complex.setProGuardRules(null);
							complex.setGradleBuild(null);
							/*complex.setAcName("MainActivity");
			complex.setXName("main");*/
							complex.setManifest(null);
							complex.setStringResources(null);
							complex.setColorResources(null);
							complex.setStyleResources(null);
							complex.setDimensionResources(null);
							complex.setValuesV21Resources(null);
							complex.setJavaCode("FileUtil", Lx.e(packageName));
							complex.setJavaCode("BlackLogicsUtil", Lx.i(packageName));
				            complex.addDebugActivity();
				            complex.addBlackApplication();
				            complex.addBlackLogger();
				            complex.addActivityToManifest("DebugActivity");
					}
					
					File projectDir = new File(projectDirPath);
					
					// Create/update config file
					JSONObject config = new JSONObject();
					config.put("projectName", projectName);
					config.put("packageName", packageName);
					config.put("srcDir", srcDir);
					config.put("sc_id", projectId);
					config.put("versionCode", versionCode);
					config.put("versionName", versionName);
					config.put("colorAccent", String.format("#%06X", (0xFFFFFF & selectedColorAccent)));
					config.put("colorPrimary", String.format("#%06X", (0xFFFFFF & selectedColorPrimary)));
					config.put("colorPrimaryDark", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryDark)));
					config.put("colorPrimaryHighlight", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryHighlight)));
					config.put("colorPrimaryNormal", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryNormal)));
					
					// Handle icon
					if (!TextUtils.isEmpty(selectedIconPath)) {
							File iconFile = new File(projectDir, "icon.png");
							TheBlockLogicsUtil.copyFile(new File(selectedIconPath), iconFile);
							config.put("iconPath", "icon.png");
					} else if (isUpdateMode) {
							// Keep existing icon if no new one was selected
							File existingIcon = new File(projectDir, "icon.png");
							if (existingIcon.exists()) {
									config.put("iconPath", "icon.png");
							} else {
									config.put("iconPath", "default_icon.png");
							}
					} else {
							// New project with no icon selected
							config.put("iconPath", "default_icon.png");
					}
					
					// Save config
					TheBlockLogicsUtil.writeFile(projectDir.getAbsolutePath() + "/config", config.toString());
					
					// Update complex object
					complex.setPkgName(packageName);
					complex.setProjectName(projectName);
					complex.setGradleBuildConfig(null);
					complex.setProGuardRules(null);
					complex.setGradleBuild(null);
					complex.setAcName("MainActivity");
			        complex.setLauncherActivity("MainActivity");
			        complex.enableToolBar("MainActivity", false, true);
			        complex.setLogic("//TUDO : AUTOMATICALLY ADDED", "MainActivity");
					complex.setXName("main");
					complex.setManifest(null);
					complex.setStringResources(null);
					complex.setColorResources(null);
					complex.setStyleResources(null);
					complex.setDimensionResources(null);
					complex.setValuesV21Resources(null);
					complex.setJavaCode("FileUtil", Lx.e(packageName));
					complex.setJavaCode("BlackLogicsUtil", Lx.i(packageName));
			        if (FileUtil.isExistFile(projectDir.getAbsolutePath() + "/icon.png")) {
				            complex.setManifestIcon(FileUtil.readFile(projectDir.getAbsolutePath() + "/icon.png"));
				        } else {
				            complex.setManifestIcon("");
				        }
					// Show success message
					TheBlockLogicsUtil.showToast(this, isUpdateMode ? "Project updated successfully!" : "Project created successfully!");
					
					// Return to previous activity with result
					Intent resultIntent = new Intent();
					resultIntent.putExtra("projectPath", projectDir.getAbsolutePath());
					resultIntent.putExtra("isUpdate", isUpdateMode);
					setResult(Activity.RESULT_OK, resultIntent);
					finish();
			} catch (JSONException e) {
					TheBlockLogicsUtil.showToast(this, "Error: " + e.getMessage());
			} catch (Exception e) {
					TheBlockLogicsUtil.showToast(this, "Error: " + e.getMessage());
			}
	}
	
	private int getNextProjectNumber() {
			File projectsDir = new File(TheBlockLogicsUtil.projects);
			if (!projectsDir.exists()) {
					return 0;
			}
			
			File[] projectDirs = projectsDir.listFiles();
			if (projectDirs == null || projectDirs.length == 0) {
					return 0;
			}
			
			return projectDirs.length;
	}
	
	private int getNextNameCounter() {
		    File projectsDir = new File(TheBlockLogicsUtil.projects);
		    if (!projectsDir.exists()) {
			        return 1; // Start with 1 if no projects exist
			    }
		
		    File[] projectDirs = projectsDir.listFiles();
		    if (projectDirs == null || projectDirs.length == 0) {
			        return 1; // Start with 1 if no projects exist
			    }
		
		    return projectDirs.length + 1; // Start from 1 and increment based on existing projects
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			
			if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
					Uri imageUri = data.getData();
					
					try {
							// Create a temporary file for the icon
							File iconFile = new File(getCacheDir(), "temp_icon.png");
							selectedIconPath = iconFile.getAbsolutePath();
							
							// Copy the selected image to our temporary file
							InputStream inputStream = getContentResolver().openInputStream(imageUri);
							OutputStream outputStream = new FileOutputStream(iconFile);
							
							byte[] buffer = new byte[1024];
							int length;
							while ((length = inputStream.read(buffer)) > 0) {
									outputStream.write(buffer, 0, length);
							}
							
							inputStream.close();
							outputStream.close();
							
							// Display the selected icon
							Bitmap bitmap = BitmapFactory.decodeFile(selectedIconPath);
							scIcon.setImageBitmap(bitmap);
							
					} catch (IOException e) {
							e.printStackTrace();
							TheBlockLogicsUtil.showToast(this, "Error loading image");
					}
			}
	}
	
	public void _createProjectJson(String pkgName) {
		    Map<String, Object> pathMap = new HashMap<>();
		    String base = FileUtil.getExternalStorageDir();
		
		    // Resource files as plain strings
		    List<String> resourceFiles = new ArrayList<>();
		    resourceFiles.add(base + "/.blacklogics/mysc/" + sc_id + "/app/src/main/res/");
		    resourceFiles.add(base + "/.blacklogics/data/" + sc_id + "/files/resource/");
		    resourceFiles.add(base + "/.blacklogics/resources/images/" + sc_id + "/");
		    pathMap.put("resourceFiles", resourceFiles);
		
		    // Java files as plain strings
		    List<String> javaFiles = new ArrayList<>();
		    javaFiles.add(base + "/.blacklogics/mysc/" + sc_id + "/app/src/main/java/" + pkgName.replace(".", "/") + "/");
		    pathMap.put("javaFiles", javaFiles);
		
		    // Manifest file as object with "path"
		    Map<String, String> manifestMap = new HashMap<>();
		    manifestMap.put("path", base + "/.blacklogics/mysc/" + sc_id + "/app/src/main/AndroidManifest.xml");
		    pathMap.put("manifestFile", manifestMap);
		
		    // Assets file as string path
		    pathMap.put("assetsFile", base + "/.blacklogics/data/" + sc_id + "/files/assets/");
		
		    // Proguard file as string path
		    pathMap.put("proguardFile", base + "/.blacklogics/data/" + sc_id + "/files/proguard-rules.pro");
		
		    // Write JSON to file
		    String json = new Gson().toJson(pathMap);
		    FileUtil.writeFile(base + "/.blacklogics/data/" + sc_id + "/project.json", json);
	}
	
	// ... (keep all other existing methods)
	{
			// Add this ColorPickerDialog class in the same package
			
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}