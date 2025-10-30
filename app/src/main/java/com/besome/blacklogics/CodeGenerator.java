package com.besome.blacklogics;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.besome.blacklogics.beans.ActivityBean;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.besome.blacklogics.util.FileHandler;
import com.besome.blacklogics.development.Complex;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.nexusteam.internal.os.layouteditor.util.WidgetStorageManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Base64;
import java.lang.reflect.Type;

public class CodeGenerator {
	private static final String TAG = "CodeGenerator";
	private final Context context;
	private final String scId;
	private final String pkgName;
	private final Complex complex;
	private ActivityBean activityBean;
	private ProjectActivityBean currentActivityBean;
	
	public CodeGenerator(Context context, String scId, String pkgName, Complex complex, ActivityBean activityBean, ProjectActivityBean currentActivityBean) {
		this.context = context;
		this.scId = scId;
		this.pkgName = pkgName;
		this.complex = complex;
		this.activityBean = activityBean;
		this.currentActivityBean = currentActivityBean;
	}
	
	public void setActivityBean(ActivityBean activityBean) {
		this.activityBean = activityBean;
	}
	
	public void setCurrentActivityBean(ProjectActivityBean currentActivityBean) {
		this.currentActivityBean = currentActivityBean;
	}
	
	public void generateJavaCode() {
		if (activityBean == null || currentActivityBean == null) {
		//	showToast("Error: No activity selected for Java code generation");
			Log.e(TAG, "activityBean or currentActivityBean is null");
			return;
		}
		
		StringBuilder javaCode = new StringBuilder();
		javaCode.append("package ").append(pkgName).append(";\n\n");
		
		// Import statements
		javaCode.append(generateImports());
		
		// Class declaration
		javaCode.append("\npublic class ").append(activityBean.getActivityName()).append(" extends ");
		javaCode.append(complex.getAndroidXEnable() ? "AppCompatActivity" : "Activity").append(" {\n\n");
		
		// Declare widget fields
		declareWidgetFields(activityBean.getActivityName(), javaCode);
		
		// Declare component fields
		boolean timerDeclared = false;
		List<HashMap<String, String>> components = loadComponentLogic(activityBean.getActivityName());
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			String fieldName = component.get("fieldName");
			if ("Timer".equals(componentName)) {
				if (!timerDeclared) {
					javaCode.append("    private Timer _timer = new Timer();\n");
					timerDeclared = true;
				}
				javaCode.append("    private TimerTask ").append(fieldName).append(";\n");
			} else {
				javaCode.append("    private ").append(componentName).append(" ").append(fieldName).append(";\n");
			}
		}
		
		// Declare variable fields
		List<HashMap<String, String>> variables = loadVariableLogic(activityBean.getActivityName());
		for (HashMap<String, String> variable : variables) {
			String varType = variable.get("varTypeName");
			String varName = variable.get("varName");
			javaCode.append("    private ").append(varType).append(" ").append(varName).append(";\n");
		}
		
		// onCreate method
		javaCode.append("\n    @Override\n");
		javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
		javaCode.append("        super.onCreate(savedInstanceState);\n");
		javaCode.append("        setContentView(R.layout.").append(currentActivityBean.getLayoutName()).append(");\n");
		javaCode.append("        initialize(savedInstanceState);\n");
		javaCode.append("        initializeLogic();\n");
		javaCode.append("    }\n");
		
		// Initialize method
		javaCode.append("\n    private void initialize(Bundle _savedInstanceState) {\n");
		initializeWidgetFields(activityBean.getActivityName(), javaCode);
		
		// Initialize components with event listeners
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			String fieldName = component.get("fieldName");
			String logic = getBlockLogic(fieldName);
			String eventLogic = getBlockLogicForEvent(fieldName);
			switch (componentName) {
				case "Intent":
				javaCode.append("        ").append(fieldName).append(" = new Intent();\n");
				break;
				case "Dialog":
				javaCode.append("        ").append(fieldName).append(" = new Dialog(this);\n");
				javaCode.append("        ").append(fieldName).append(".setOnShowListener(new DialogInterface.OnShowListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onShow(DialogInterface dialog) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				javaCode.append("        ").append(fieldName).append(".setOnDismissListener(new DialogInterface.OnDismissListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onDismiss(DialogInterface dialog) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "ObjectAnimator":
				javaCode.append("        ").append(fieldName).append(" = new ObjectAnimator();\n");
				javaCode.append("        ").append(fieldName).append(".addListener(new Animator.AnimatorListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationStart(Animator animation) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationEnd(Animator animation) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationCancel(Animator animation) {}\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationRepeat(Animator animation) {}\n");
				javaCode.append("        });\n");
				break;
				case "SharedPreferences":
				javaCode.append("        ").append(fieldName).append(" = getSharedPreferences(\"prefs\", MODE_PRIVATE);\n");
				javaCode.append("        ").append(fieldName).append(".registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onSharedPreferenceChanged(SharedPreferences sp, String key) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "AsyncTask":
				javaCode.append("        // AsyncTask should be subclassed\n");
				break;
				case "Handler":
				javaCode.append("        ").append(fieldName).append(" = new Handler(Looper.getMainLooper());\n");
				break;
				case "Service":
				javaCode.append("        // Service is typically started\n");
				break;
				case "BroadcastReceiver":
				javaCode.append("        ").append(fieldName).append(" = new BroadcastReceiver() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onReceive(Context context, Intent intent) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        };\n");
				javaCode.append("        registerReceiver(").append(fieldName).append(", new IntentFilter());\n");
				break;
				case "ContentProvider":
				javaCode.append("        // ContentProvider initialized by system\n");
				break;
				case "Fragment":
				javaCode.append("        // Fragment lifecycle handled separately\n");
				break;
				case "ViewModel":
				javaCode.append("        ").append(fieldName).append(" = new ViewModelProvider(this).get(ViewModel.class);\n");
				break;
				case "LiveData":
				javaCode.append("        // LiveData observed with Observer\n");
				break;
				case "Room":
				javaCode.append("        // Room database initialized separately\n");
				break;
				case "WorkManager":
				javaCode.append("        // WorkManager initialized with WorkRequest\n");
				break;
				case "RecyclerView":
				javaCode.append("        ").append(fieldName).append(" = findViewById(R.id.").append(fieldName).append(");\n");
				javaCode.append("        ").append(fieldName).append(".addOnScrollListener(new RecyclerView.OnScrollListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "ViewPager":
				javaCode.append("        ").append(fieldName).append(" = findViewById(R.id.").append(fieldName).append(");\n");
				javaCode.append("        ").append(fieldName).append(".addOnPageChangeListener(new ViewPager.OnPageChangeListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageSelected(int position) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageScrollStateChanged(int state) {}\n");
				javaCode.append("        });\n");
				break;
				case "MediaPlayer":
				javaCode.append("        ").append(fieldName).append(" = new MediaPlayer();\n");
				javaCode.append("        ").append(fieldName).append(".setOnCompletionListener(new MediaPlayer.OnCompletionListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onCompletion(MediaPlayer mp) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "Camera":
				javaCode.append("        // Legacy Camera API initialization\n");
				break;
				case "LocationManager":
				javaCode.append("        ").append(fieldName).append(" = (LocationManager) getSystemService(Context.LOCATION_SERVICE);\n");
				break;
				case "SensorManager":
				javaCode.append("        ").append(fieldName).append(" = (SensorManager) getSystemService(Context.SENSOR_SERVICE);\n");
				break;
				case "BluetoothAdapter":
				javaCode.append("        ").append(fieldName).append(" = BluetoothAdapter.getDefaultAdapter();\n");
				break;
				case "CameraX":
				javaCode.append("        // CameraX initialization\n");
				break;
				case "FilePicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_OPEN_DOCUMENT);\n");
				break;
				case "ImagePicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);\n");
				break;
				case "VideoPicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);\n");
				break;
				case "AudioRecorder":
				javaCode.append("        ").append(fieldName).append(" = new MediaRecorder();\n");
				break;
				case "SpeechRecognizer":
				javaCode.append("        ").append(fieldName).append(" = SpeechRecognizer.createSpeechRecognizer(this);\n");
				break;
				case "QRScanner":
				javaCode.append("        // QRScanner handled via Activity result\n");
				break;
				case "DocumentPicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_OPEN_DOCUMENT);\n");
				break;
				case "BiometricAuth":
				javaCode.append("        ").append(fieldName).append(" = new BiometricPrompt(this, ContextCompat.getMainExecutor(this), new BiometricPrompt.AuthenticationCallback() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {\n");
				javaCode.append("                ").append(logic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "Timer":
				javaCode.append("        ").append(fieldName).append(" = new TimerTask() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void run() {\n");
				javaCode.append("                ").append(logic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        };\n");
				javaCode.append("        _timer.schedule(").append(fieldName).append(", 0, 1000);\n");
				break;
			}
		}
		
		// Add widget event listeners
		try {
			WidgetStorageManager storageManager = new WidgetStorageManager(context, scId, activityBean.getActivityName());
			JSONArray widgetsArray = storageManager.loadAllWidgets();
			for (int i = 0; i < widgetsArray.length(); i++) {
				JSONObject widgetObj = widgetsArray.getJSONObject(i);
				String widgetIdChild = widgetObj.getString("widget_id");
				String widgetType = widgetObj.getString("class");
				String logic = getBlockLogic(widgetIdChild);
				if (widgetIdChild != null && !widgetIdChild.isEmpty()) {
					switch (widgetType) {
						case "CheckBox":
						case "Switch":
						javaCode.append("\n        ").append(widgetIdChild).append(".setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {\n");
						javaCode.append("                ").append(logic).append("\n");
						javaCode.append("            }\n");
						javaCode.append("        });\n");
						break;
						case "EditText":
						javaCode.append("\n        ").append(widgetIdChild).append(".addTextChangedListener(new TextWatcher() {\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onTextChanged(CharSequence s, int start, int before, int count) {\n");
						javaCode.append("                ").append(logic).append("\n");
						javaCode.append("            }\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void afterTextChanged(Editable s) {}\n");
						javaCode.append("        });\n");
						break;
						case "SeekBar":
						javaCode.append("\n        ").append(widgetIdChild).append(".setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {\n");
						javaCode.append("                ").append(logic).append("\n");
						javaCode.append("            }\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onStartTrackingTouch(SeekBar seekBar) {}\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onStopTrackingTouch(SeekBar seekBar) {}\n");
						javaCode.append("        });\n");
						break;
						case "Spinner":
						javaCode.append("\n        ").append(widgetIdChild).append(".setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {\n");
						javaCode.append("                ").append(logic).append("\n");
						javaCode.append("            }\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onNothingSelected(AdapterView<?> parent) {}\n");
						javaCode.append("        });\n");
						break;
						default:
						javaCode.append("\n        ").append(widgetIdChild).append(".setOnClickListener(new View.OnClickListener() {\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onClick(View v) {\n");
						javaCode.append("                ").append(logic).append("\n");
						javaCode.append("            }\n");
						javaCode.append("        });\n");
						break;
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Error processing widgets: " + e.getMessage());
			showToast("Error processing widgets");
		}
		
		javaCode.append("\n    }\n");
		
		// Initialize logic
		String logic = getBlockLogics(activityBean.getActivityName());
		javaCode.append("    private void initializeLogic() {\n");
		if (logic != null && !logic.isEmpty()) {
			String[] lines = logic.split("\n");
			for (String line : lines) {
				javaCode.append("        ").append(line.strip()).append("\n");
			}
		}
		javaCode.append("    }\n");
		
		// Lifecycle events
		try {
			String path = FileHandler.codeSavePath + "/" + scId + "/events/lifecycle_events.json";
			File file = new File(path);
			JSONObject allEvents = new JSONObject();
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				allEvents = new JSONObject(decoded);
			}
			
			String activityName = activityBean.getActivityName();
			JSONArray existingArray = allEvents.optJSONArray(activityName);
			if (existingArray != null) {
				for (int i = 0; i < existingArray.length(); i++) {
					String event = existingArray.optString(i);
					String eventLogic = getBlockLogicForEvent(event);
					switch (event) {
						case "onStart":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onStart() {\n");
						javaCode.append("        super.onStart();\n");
						if (eventLogic != null && !eventLogic.isEmpty()) {
							javaCode.append("        ").append(eventLogic.replace("\n", "\n        ")).append("\n");
						}
						javaCode.append("    }\n");
						break;
						case "onResume":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onResume() {\n");
						javaCode.append("        super.onResume();\n");
						if (eventLogic != null && !eventLogic.isEmpty()) {
							javaCode.append("        ").append(eventLogic.replace("\n", "\n        ")).append("\n");
						}
						javaCode.append("    }\n");
						break;
						case "onPause":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onPause() {\n");
						javaCode.append("        super.onPause();\n");
						if (eventLogic != null && !eventLogic.isEmpty()) {
							javaCode.append("        ").append(eventLogic.replace("\n", "\n        ")).append("\n");
						}
						javaCode.append("    }\n");
						break;
						case "onBackPressed":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onBackPressed() {\n");
						javaCode.append("        super.onBackPressed();\n");
						if (eventLogic != null && !eventLogic.isEmpty()) {
							javaCode.append("        ").append(eventLogic.replace("\n", "\n        ")).append("\n");
						}
						javaCode.append("    }\n");
						break;
						case "onDestroy":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onDestroy() {\n");
						javaCode.append("        super.onDestroy();\n");
						if (eventLogic != null && !eventLogic.isEmpty()) {
							javaCode.append("        ").append(eventLogic.replace("\n", "\n        ")).append("\n");
						}
						javaCode.append("    }\n");
						break;
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Error processing lifecycle events: " + e.getMessage());
			showToast("Error processing lifecycle events");
		}
		
		// Utility methods
		javaCode.append("\n    public void showMessage(String message) {\n");
		javaCode.append("        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();\n");
		javaCode.append("    }\n");
		
		javaCode.append("}\n");
		
		// Save the generated Java code
		complex.setJavaCode(activityBean.getActivityName(), javaCode.toString());
		Log.d(TAG, "Generated Java code for " + activityBean.getActivityName() + ":\n" + prettyPrintCode(javaCode.toString()));
	}
	
	public String getJavaCode() {
		if (activityBean == null || currentActivityBean == null) {
		//	showToast("Error: No activity selected for Java code generation");
			Log.e(TAG, "activityBean or currentActivityBean is null");
			return "";
		}
		
		StringBuilder javaCode = new StringBuilder();
		javaCode.append("package ").append(pkgName).append(";\n\n");
		
		// Import statements
		javaCode.append(generateImports());
		
		// Class declaration
		javaCode.append("\npublic class ").append(activityBean.getActivityName()).append(" extends ");
		javaCode.append(complex.getAndroidXEnable() ? "AppCompatActivity" : "Activity").append(" {\n\n");
		
		// Declare widget fields
		declareWidgetFields(activityBean.getActivityName(), javaCode);
		
		// Declare component fields
		boolean timerDeclared = false;
		List<HashMap<String, String>> components = loadComponentLogic(activityBean.getActivityName());
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			String fieldName = component.get("fieldName");
			if ("Timer".equals(componentName)) {
				if (!timerDeclared) {
					javaCode.append("    private Timer _timer = new Timer();\n");
					timerDeclared = true;
				}
				javaCode.append("    private TimerTask ").append(fieldName).append(";\n");
			} else {
				javaCode.append("    private ").append(componentName).append(" ").append(fieldName).append(";\n");
			}
		}
		
		// Declare variable fields
		List<HashMap<String, String>> variables = loadVariableLogic(activityBean.getActivityName());
		for (HashMap<String, String> variable : variables) {
			String varType = variable.get("varTypeName");
			String varName = variable.get("varName");
			javaCode.append("    private ").append(varType).append(" ").append(varName).append(";\n");
		}
		
		// onCreate method
		javaCode.append("\n    @Override\n");
		javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
		javaCode.append("        super.onCreate(savedInstanceState);\n");
		javaCode.append("        setContentView(R.layout.").append(currentActivityBean.getLayoutName()).append(");\n");
		javaCode.append("        initialize(savedInstanceState);\n");
		javaCode.append("        initializeLogic();\n");
		javaCode.append("    }\n");
		
		// Initialize method
		javaCode.append("\n    private void initialize(Bundle _savedInstanceState) {\n");
		initializeWidgetFields(activityBean.getActivityName(), javaCode);
		
		// Initialize components with event listeners
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			String fieldName = component.get("fieldName");
			String logic = getBlockLogic(fieldName);
			String eventLogic = getBlockLogicForEvent(fieldName);
			switch (componentName) {
				case "Intent":
				javaCode.append("        ").append(fieldName).append(" = new Intent();\n");
				break;
				case "Dialog":
				javaCode.append("        ").append(fieldName).append(" = new Dialog(this);\n");
				javaCode.append("        ").append(fieldName).append(".setOnShowListener(new DialogInterface.OnShowListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onShow(DialogInterface dialog) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				javaCode.append("        ").append(fieldName).append(".setOnDismissListener(new DialogInterface.OnDismissListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onDismiss(DialogInterface dialog) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "ObjectAnimator":
				javaCode.append("        ").append(fieldName).append(" = new ObjectAnimator();\n");
				javaCode.append("        ").append(fieldName).append(".addListener(new Animator.AnimatorListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationStart(Animator animation) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationEnd(Animator animation) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationCancel(Animator animation) {}\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationRepeat(Animator animation) {}\n");
				javaCode.append("        });\n");
				break;
				case "SharedPreferences":
				javaCode.append("        ").append(fieldName).append(" = getSharedPreferences(\"prefs\", MODE_PRIVATE);\n");
				javaCode.append("        ").append(fieldName).append(".registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onSharedPreferenceChanged(SharedPreferences sp, String key) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "AsyncTask":
				javaCode.append("        // AsyncTask should be subclassed\n");
				break;
				case "Handler":
				javaCode.append("        ").append(fieldName).append(" = new Handler(Looper.getMainLooper());\n");
				break;
				case "Service":
				javaCode.append("        // Service is typically started\n");
				break;
				case "BroadcastReceiver":
				javaCode.append("        ").append(fieldName).append(" = new BroadcastReceiver() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onReceive(Context context, Intent intent) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        };\n");
				javaCode.append("        registerReceiver(").append(fieldName).append(", new IntentFilter());\n");
				break;
				case "ContentProvider":
				javaCode.append("        // ContentProvider initialized by system\n");
				break;
				case "Fragment":
				javaCode.append("        // Fragment lifecycle handled separately\n");
				break;
				case "ViewModel":
				javaCode.append("        ").append(fieldName).append(" = new ViewModelProvider(this).get(ViewModel.class);\n");
				break;
				case "LiveData":
				javaCode.append("        // LiveData observed with Observer\n");
				break;
				case "Room":
				javaCode.append("        // Room database initialized separately\n");
				break;
				case "WorkManager":
				javaCode.append("        // WorkManager initialized with WorkRequest\n");
				break;
				case "RecyclerView":
				javaCode.append("        ").append(fieldName).append(" = findViewById(R.id.").append(fieldName).append(");\n");
				javaCode.append("        ").append(fieldName).append(".addOnScrollListener(new RecyclerView.OnScrollListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "ViewPager":
				javaCode.append("        ").append(fieldName).append(" = findViewById(R.id.").append(fieldName).append(");\n");
				javaCode.append("        ").append(fieldName).append(".addOnPageChangeListener(new ViewPager.OnPageChangeListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageSelected(int position) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageScrollStateChanged(int state) {}\n");
				javaCode.append("        });\n");
				break;
				case "MediaPlayer":
				javaCode.append("        ").append(fieldName).append(" = new MediaPlayer();\n");
				javaCode.append("        ").append(fieldName).append(".setOnCompletionListener(new MediaPlayer.OnCompletionListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onCompletion(MediaPlayer mp) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "Camera":
				javaCode.append("        // Legacy Camera API initialization\n");
				break;
				case "LocationManager":
				javaCode.append("        ").append(fieldName).append(" = (LocationManager) getSystemService(Context.LOCATION_SERVICE);\n");
				break;
				case "SensorManager":
				javaCode.append("        ").append(fieldName).append(" = (SensorManager) getSystemService(Context.SENSOR_SERVICE);\n");
				break;
				case "BluetoothAdapter":
				javaCode.append("        ").append(fieldName).append(" = BluetoothAdapter.getDefaultAdapter();\n");
				break;
				case "CameraX":
				javaCode.append("        // CameraX initialization\n");
				break;
				case "FilePicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_OPEN_DOCUMENT);\n");
				break;
				case "ImagePicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);\n");
				break;
				case "VideoPicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);\n");
				break;
				case "AudioRecorder":
				javaCode.append("        ").append(fieldName).append(" = new MediaRecorder();\n");
				break;
				case "SpeechRecognizer":
				javaCode.append("        ").append(fieldName).append(" = SpeechRecognizer.createSpeechRecognizer(this);\n");
				break;
				case "QRScanner":
				javaCode.append("        // QRScanner handled via Activity result\n");
				break;
				case "DocumentPicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_OPEN_DOCUMENT);\n");
				break;
				case "BiometricAuth":
				javaCode.append("        ").append(fieldName).append(" = new BiometricPrompt(this, ContextCompat.getMainExecutor(this), new BiometricPrompt.AuthenticationCallback() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {\n");
				javaCode.append("                ").append(logic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "Timer":
				javaCode.append("        ").append(fieldName).append(" = new TimerTask() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void run() {\n");
				javaCode.append("                ").append(logic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        };\n");
				javaCode.append("        _timer.schedule(").append(fieldName).append(", 0, 1000);\n");
				break;
			}
		}
		
		// Add widget event listeners
		try {
			WidgetStorageManager storageManager = new WidgetStorageManager(context, scId, activityBean.getActivityName());
			JSONArray widgetsArray = storageManager.loadAllWidgets();
			for (int i = 0; i < widgetsArray.length(); i++) {
				JSONObject widgetObj = widgetsArray.getJSONObject(i);
				String widgetIdChild = widgetObj.getString("widget_id");
				String widgetType = widgetObj.getString("class");
				String logic = getBlockLogic(widgetIdChild);
				if (widgetIdChild != null && !widgetIdChild.isEmpty()) {
					switch (widgetType) {
						case "CheckBox":
						case "Switch":
						javaCode.append("\n        ").append(widgetIdChild).append(".setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {\n");
						javaCode.append("                ").append(logic).append("\n");
						javaCode.append("            }\n");
						javaCode.append("        });\n");
						break;
						case "EditText":
						javaCode.append("\n        ").append(widgetIdChild).append(".addTextChangedListener(new TextWatcher() {\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onTextChanged(CharSequence s, int start, int before, int count) {\n");
						javaCode.append("                ").append(logic).append("\n");
						javaCode.append("            }\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void afterTextChanged(Editable s) {}\n");
						javaCode.append("        });\n");
						break;
						case "SeekBar":
						javaCode.append("\n        ").append(widgetIdChild).append(".setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {\n");
						javaCode.append("                ").append(logic).append("\n");
						javaCode.append("            }\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onStartTrackingTouch(SeekBar seekBar) {}\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onStopTrackingTouch(SeekBar seekBar) {}\n");
						javaCode.append("        });\n");
						break;
						case "Spinner":
						javaCode.append("\n        ").append(widgetIdChild).append(".setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {\n");
						javaCode.append("                ").append(logic).append("\n");
						javaCode.append("            }\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onNothingSelected(AdapterView<?> parent) {}\n");
						javaCode.append("        });\n");
						break;
						default:
						javaCode.append("\n        ").append(widgetIdChild).append(".setOnClickListener(new View.OnClickListener() {\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onClick(View v) {\n");
						javaCode.append("                ").append(logic).append("\n");
						javaCode.append("            }\n");
						javaCode.append("        });\n");
						break;
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Error processing widgets: " + e.getMessage());
			showToast("Error processing widgets");
		}
		
		javaCode.append("\n    }\n");
		
		// Initialize logic
		String logic = getBlockLogics(activityBean.getActivityName());
		javaCode.append("    private void initializeLogic() {\n");
		if (logic != null && !logic.isEmpty()) {
			String[] lines = logic.split("\n");
			for (String line : lines) {
				javaCode.append("        ").append(line.strip()).append("\n");
			}
		}
		javaCode.append("    }\n");
		
		// Lifecycle events
		try {
			String path = FileHandler.codeSavePath + "/" + scId + "/events/lifecycle_events.json";
			File file = new File(path);
			JSONObject allEvents = new JSONObject();
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				allEvents = new JSONObject(decoded);
			}
			
			String activityName = activityBean.getActivityName();
			JSONArray existingArray = allEvents.optJSONArray(activityName);
			if (existingArray != null) {
				for (int i = 0; i < existingArray.length(); i++) {
					String event = existingArray.optString(i);
					String eventLogic = getBlockLogicForEvent(event);
					switch (event) {
						case "onStart":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onStart() {\n");
						javaCode.append("        super.onStart();\n");
						if (eventLogic != null && !eventLogic.isEmpty()) {
							javaCode.append("        ").append(eventLogic.replace("\n", "\n        ")).append("\n");
						}
						javaCode.append("    }\n");
						break;
						case "onResume":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onResume() {\n");
						javaCode.append("        super.onResume();\n");
						if (eventLogic != null && !eventLogic.isEmpty()) {
							javaCode.append("        ").append(eventLogic.replace("\n", "\n        ")).append("\n");
						}
						javaCode.append("    }\n");
						break;
						case "onPause":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onPause() {\n");
						javaCode.append("        super.onPause();\n");
						if (eventLogic != null && !eventLogic.isEmpty()) {
							javaCode.append("        ").append(eventLogic.replace("\n", "\n        ")).append("\n");
						}
						javaCode.append("    }\n");
						break;
						case "onBackPressed":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onBackPressed() {\n");
						javaCode.append("        super.onBackPressed();\n");
						if (eventLogic != null && !eventLogic.isEmpty()) {
							javaCode.append("        ").append(eventLogic.replace("\n", "\n        ")).append("\n");
						}
						javaCode.append("    }\n");
						break;
						case "onDestroy":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onDestroy() {\n");
						javaCode.append("        super.onDestroy();\n");
						if (eventLogic != null && !eventLogic.isEmpty()) {
							javaCode.append("        ").append(eventLogic.replace("\n", "\n        ")).append("\n");
						}
						javaCode.append("    }\n");
						break;
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Error processing lifecycle events: " + e.getMessage());
			showToast("Error processing lifecycle events");
		}
		
		// Utility methods
		javaCode.append("\n    public void showMessage(String message) {\n");
		javaCode.append("        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();\n");
		javaCode.append("    }\n");
		
		javaCode.append("}\n");
		
		// Save the generated Java code
		complex.setJavaCode(activityBean.getActivityName(), javaCode.toString());
		Log.d(TAG, "Generated Java code for " + activityBean.getActivityName() + ":\n" + prettyPrintCode(javaCode.toString()));
        
        return javaCode.toString();
	}
	
	public void generateXmlLayout() {
		if (activityBean == null || currentActivityBean == null) {
			showToast("Error: No activity selected for XML layout generation");
			Log.e(TAG, "activityBean or currentActivityBean is null");
			return;
		}
		
		StringBuilder xmlCode = new StringBuilder();
		xmlCode.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		xmlCode.append("<androidx.constraintlayout.widget.ConstraintLayout\n");
		xmlCode.append("    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n");
		xmlCode.append("    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n");
		xmlCode.append("    android:layout_width=\"match_parent\"\n");
		xmlCode.append("    android:layout_height=\"match_parent\">\n\n");
		
		try {
			WidgetStorageManager storageManager = new WidgetStorageManager(context, scId, activityBean.getActivityName());
			JSONArray widgetsArray = storageManager.loadAllWidgets();
			for (int i = 0; i < widgetsArray.length(); i++) {
				JSONObject widgetObj = widgetsArray.getJSONObject(i);
				String widgetType = widgetObj.getString("class");
				String widgetId = widgetObj.getString("widget_id");
				String width = widgetObj.optString("layout_width", "wrap_content");
				String height = widgetObj.optString("layout_height", "wrap_content");
				String xPos = widgetObj.optString("x_position", "0");
				String yPos = widgetObj.optString("y_position", "0");
				String text = widgetObj.optString("text", "");
				String otherAttrs = widgetObj.optString("other_attributes", "");
				
				xmlCode.append("    <").append(widgetType).append("\n");
				xmlCode.append("        android:id=\"@+id/").append(widgetId).append("\"\n");
				xmlCode.append("        android:layout_width=\"").append(width).append("\"\n");
				xmlCode.append("        android:layout_height=\"").append(height).append("\"\n");
				xmlCode.append("        app:layout_constraintStart_toStartOf=\"parent\"\n");
				xmlCode.append("        app:layout_constraintTop_toTopOf=\"parent\"\n");
				xmlCode.append("        android:layout_marginStart=\"").append(xPos).append("dp\"\n");
				xmlCode.append("        android:layout_marginTop=\"").append(yPos).append("dp\"\n");
				
				if (!text.isEmpty()) {
					xmlCode.append("        android:text=\"").append(text).append("\"\n");
				}
				if (!otherAttrs.isEmpty()) {
					String[] attrs = otherAttrs.split(";");
					for (String attr : attrs) {
						if (!attr.trim().isEmpty()) {
							xmlCode.append("        ").append(attr.trim()).append("\n");
						}
					}
				}
				
				xmlCode.append("    />\n\n");
			}
		} catch (Exception e) {
			Log.e(TAG, "Error generating XML layout: " + e.getMessage());
			showToast("Error generating XML layout");
		}
		
		xmlCode.append("</androidx.constraintlayout.widget.ConstraintLayout>\n");
		
		// Save the generated XML code
		complex.setXmlCode(currentActivityBean.getLayoutName(), xmlCode.toString());
		Log.d(TAG, "Generated XML layout for " + currentActivityBean.getLayoutName() + ":\n" + prettyPrintCode(xmlCode.toString()));
	}
	
	private String generateImports() {
		StringBuilder imports = new StringBuilder();
		if (complex.getAndroidXEnable()) {
			imports.append("import androidx.appcompat.app.AppCompatActivity;\n");
			imports.append("import androidx.fragment.app.Fragment;\n");
			imports.append("import androidx.fragment.app.FragmentManager;\n");
			imports.append("import androidx.fragment.app.DialogFragment;\n");
			imports.append("import com.google.android.material.*;\n");
		} else {
			imports.append("import android.app.Activity;\n");
		}
		imports.append("import android.app.*;\n");
		imports.append("import android.os.*;\n");
		imports.append("import android.os.Bundle;\n");
		imports.append("import android.widget.*;\n");
		imports.append("import android.text.*;\n");
		imports.append("import android.net.*;\n");
		imports.append("import android.util.*;\n");
		imports.append("import android.view.*;\n");
		imports.append("import android.graphics.*;\n");
		imports.append("import android.content.*;\n");
		imports.append("import android.widget.Toast;\n");
		imports.append("import android.webkit.*;\n");
		imports.append("import android.view.animation.*;\n");
		
		List<HashMap<String, String>> components = loadComponentLogic(activityBean.getActivityName());
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			switch (componentName) {
				case "Intent":
				imports.append("import android.content.Intent;\n");
				break;
				case "Dialog":
				imports.append("import android.app.Dialog;\n");
				break;
				case "ObjectAnimator":
				imports.append("import android.animation.ObjectAnimator;\n");
				break;
				case "SharedPreferences":
				imports.append("import android.content.SharedPreferences;\n");
				break;
				case "AsyncTask":
				imports.append("import android.os.AsyncTask;\n");
				break;
				case "Handler":
				imports.append("import android.os.Handler;\n");
				imports.append("import android.os.Looper;\n");
				break;
				case "Service":
				imports.append("import android.app.Service;\n");
				break;
				case "BroadcastReceiver":
				imports.append("import android.content.BroadcastReceiver;\n");
				imports.append("import android.content.IntentFilter;\n");
				break;
				case "ContentProvider":
				imports.append("import android.content.ContentProvider;\n");
				break;
				case "Fragment":
				imports.append("import androidx.fragment.app.Fragment;\n");
				break;
				case "ViewModel":
				imports.append("import androidx.lifecycle.ViewModel;\n");
				imports.append("import androidx.lifecycle.ViewModelProvider;\n");
				break;
				case "LiveData":
				imports.append("import androidx.lifecycle.LiveData;\n");
				imports.append("import androidx.lifecycle.Observer;\n");
				break;
				case "Room":
				imports.append("import androidx.room.*;\n");
				break;
				case "WorkManager":
				imports.append("import androidx.work.*;\n");
				break;
				case "RecyclerView":
				imports.append("import androidx.recyclerview.widget.RecyclerView;\n");
				break;
				case "ViewPager":
				imports.append("import androidx.viewpager.widget.ViewPager;\n");
				break;
				case "MediaPlayer":
				imports.append("import android.media.MediaPlayer;\n");
				break;
				case "Camera":
				imports.append("import android.hardware.Camera;\n");
				break;
				case "LocationManager":
				imports.append("import android.location.LocationManager;\n");
				imports.append("import android.location.LocationListener;\n");
				imports.append("import android.location.Location;\n");
				break;
				case "SensorManager":
				imports.append("import android.hardware.SensorManager;\n");
				imports.append("import android.hardware.Sensor;\n");
				imports.append("import android.hardware.SensorEvent;\n");
				imports.append("import android.hardware.SensorEventListener;\n");
				break;
				case "BluetoothAdapter":
				imports.append("import android.bluetooth.BluetoothAdapter;\n");
				break;
				case "Timer":
				imports.append("import java.util.Timer;\n");
				imports.append("import java.util.TimerTask;\n");
				break;
				case "CameraX":
				imports.append("import androidx.camera.core.*;\n");
				imports.append("import androidx.camera.lifecycle.ProcessCameraProvider;\n");
				break;
				case "FilePicker":
				imports.append("import android.content.Intent;\n");
				imports.append("import android.provider.DocumentsContract;\n");
				break;
				case "ImagePicker":
				imports.append("import android.provider.MediaStore;\n");
				break;
				case "VideoPicker":
				imports.append("import android.provider.MediaStore;\n");
				break;
				case "AudioRecorder":
				imports.append("import android.media.MediaRecorder;\n");
				break;
				case "SpeechRecognizer":
				imports.append("import android.speech.SpeechRecognizer;\n");
				imports.append("import android.speech.RecognitionListener;\n");
				imports.append("import android.os.Bundle;\n");
				break;
				case "QRScanner":
				imports.append("import com.google.zxing.integration.android.IntentIntegrator;\n");
				imports.append("import com.google.zxing.integration.android.IntentResult;\n");
				break;
				case "DocumentPicker":
				imports.append("import android.content.Intent;\n");
				imports.append("import android.provider.OpenableColumns;\n");
				break;
				case "BiometricAuth":
				imports.append("import androidx.biometric.BiometricPrompt;\n");
				imports.append("import androidx.core.content.ContextCompat;\n");
				break;
			}
		}
		
		List<HashMap<String, String>> variables = loadVariableLogic(activityBean.getActivityName());
		for (HashMap<String, String> variable : variables) {
			String varType = variable.get("varTypeName");
			switch (varType) {
				case "ArrayList<String>":
				case "ArrayList<Double>":
				imports.append("import java.util.ArrayList;\n");
				break;
				case "HashMap":
				imports.append("import java.util.HashMap;\n");
				break;
			}
		}
		
		return imports.toString();
	}
	
	private void declareWidgetFields(String activityName, StringBuilder javaCode) {
		try {
			WidgetStorageManager storageManager = new WidgetStorageManager(context, scId, activityName);
			JSONArray widgetsArray = storageManager.loadAllWidgets();
			for (int i = 0; i < widgetsArray.length(); i++) {
				JSONObject widgetObj = widgetsArray.getJSONObject(i);
				String widgetType = widgetObj.getString("class");
				String widgetId = widgetObj.getString("widget_id");
				javaCode.append("    private ").append(widgetType).append(" ").append(widgetId).append(";\n");
			}
		} catch (Exception e) {
			Log.e(TAG, "Error declaring widget fields: " + e.getMessage());
			showToast("Error declaring widget fields");
		}
	}
	
	private void initializeWidgetFields(String activityName, StringBuilder javaCode) {
		try {
			WidgetStorageManager storageManager = new WidgetStorageManager(context, scId, activityName);
			JSONArray widgetsArray = storageManager.loadAllWidgets();
			for (int i = 0; i < widgetsArray.length(); i++) {
				JSONObject widgetObj = widgetsArray.getJSONObject(i);
				String widgetId = widgetObj.getString("widget_id");
				javaCode.append("        ").append(widgetId).append(" = findViewById(R.id.").append(widgetId).append(");\n");
			}
		} catch (Exception e) {
			Log.e(TAG, "Error initializing widget fields: " + e.getMessage());
			showToast("Error initializing widget fields");
		}
	}
	
	private List<HashMap<String, String>> loadVariableLogic(String activityName) {
		List<HashMap<String, String>> variables = new ArrayList<>();
		try {
			String path = FileHandler.codeSavePath + "/" + scId + "/variables/" + activityName + ".json";
			File file = new File(path);
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
				variables = gson.fromJson(decoded, listType);
			}
		} catch (Exception e) {
			Log.e(TAG, "Error loading variable logic: " + e.getMessage());
			showToast("Error loading variables");
		}
		return variables != null ? variables : new ArrayList<>();
	}
	
	private List<HashMap<String, String>> loadComponentLogic(String activityName) {
		List<HashMap<String, String>> components = new ArrayList<>();
		try {
			String path = FileHandler.codeSavePath + "/" + scId + "/components/" + activityName + ".json";
			File file = new File(path);
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
				components = gson.fromJson(decoded, listType);
			}
		} catch (Exception e) {
			Log.e(TAG, "Error loading component logic: " + e.getMessage());
			showToast("Error loading components");
		}
		return components != null ? components : new ArrayList<>();
	}
	
	private String getBlockLogic(String blockId) {
		try {
			String path = FileHandler.codeSavePath + "/" + scId + "/blocks/" + activityBean.getActivityName() + "/" + blockId + ".json";
			File file = new File(path);
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				JSONObject blockObj = new JSONObject(decoded);
				return blockObj.optString("logic", "");
			}
		} catch (Exception e) {
			Log.e(TAG, "Error getting block logic for " + blockId + ": " + e.getMessage());
			showToast("Error getting block logic");
		}
		return "";
	}
	
	private String getBlockLogicForEvent(String event) {
		try {
			String path = FileHandler.codeSavePath + "/" + scId + "/events/" + activityBean.getActivityName() + "/" + event + ".json";
			File file = new File(path);
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				JSONObject eventObj = new JSONObject(decoded);
				return eventObj.optString("logic", "");
			}
		} catch (Exception e) {
			Log.e(TAG, "Error getting event logic for " + event + ": " + e.getMessage());
			showToast("Error getting event logic");
		}
		return "";
	}
	
	private String getBlockLogics(String activityName) {
		try {
			String path = FileHandler.codeSavePath + "/" + scId + "/logic/" + activityName + ".json";
			File file = new File(path);
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				JSONObject logicObj = new JSONObject(decoded);
				return logicObj.optString("logic", "");
			}
		} catch (Exception e) {
			Log.e(TAG, "Error getting block logics for " + activityName + ": " + e.getMessage());
			showToast("Error getting block logics");
		}
		return "";
	}
	
	private String prettyPrintCode(String code) {
		// Simple pretty print (can be enhanced with a proper formatter)
		String[] lines = code.split("\n");
		StringBuilder formatted = new StringBuilder();
		int indentLevel = 0;
		for (String line : lines) {
			String trimmed = line.trim();
			if (trimmed.startsWith("}") || trimmed.startsWith("]")) {
				indentLevel--;
			}
			for (int i = 0; i < indentLevel; i++) {
				formatted.append("    ");
			}
			formatted.append(trimmed).append("\n");
			if (trimmed.endsWith("{") || trimmed.endsWith("[")) {
				indentLevel++;
			}
		}
		return formatted.toString();
	}
	
	private void showToast(String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
