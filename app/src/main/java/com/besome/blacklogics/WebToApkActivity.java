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
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.besome.sketch.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.*;
import com.google.android.material.button.*;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.*;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import de.hdodenhof.circleimageview.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import android.content.Context;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.net.URISyntaxException;
import java.net.URI;
import java.io.File;
import com.apk.builder.SystemLogPrinter;
import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.apk.builder.FileUtil;
import com.apk.builder.logger.*;
import com.apk.builder.compiler.compiler2.CompilerAsyncTask;
import androidx.appcompat.app.AppCompatDelegate;
import mod.hey.studios.util.Helper;
import com.besome.blacklogics.development.Complex;
import com.besome.blacklogics.util.FileUtils;
import com.besome.blacklogics.util.*;
import java.io.*;

public class WebToApkActivity extends AppCompatActivity {
	
	private String asset = "";
	private String library = "";
	public File mainDir;
	File projectDir;
	
	private ScrollView vscroll2;
	private LinearLayout linear1;
	private TextInputLayout textinputlayout8;
	private TextInputLayout textinputlayout9;
	private TextInputLayout textinputlayout10;
	private TextInputLayout textinputlayout11;
	private TextInputLayout textinputlayout12;
	private TextView textview2;
	private SwitchMaterial switch_splash;
	private TextInputLayout textinputlayout13;
	private TextInputLayout textinputlayout14;
	private TextView textview3;
	private SwitchMaterial switch_debuggable;
	private SwitchMaterial switch_proguard;
	private SwitchMaterial switch_cleartext;
	private RecyclerView recyclerview1;
	private MaterialButton btn_build_apk;
	private TextInputEditText appName;
	private TextInputEditText pkgName;
	private TextInputEditText edittext3;
	private TextInputEditText edittext4;
	private TextInputEditText edittext5;
	private TextInputEditText minSdkInput;
	private TextInputEditText targetSdkInput;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.web_to_apk);
		initialize(_savedInstanceState);
		
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll2 = findViewById(R.id.vscroll2);
		linear1 = findViewById(R.id.linear1);
		textinputlayout8 = findViewById(R.id.textinputlayout8);
		textinputlayout9 = findViewById(R.id.textinputlayout9);
		textinputlayout10 = findViewById(R.id.textinputlayout10);
		textinputlayout11 = findViewById(R.id.textinputlayout11);
		textinputlayout12 = findViewById(R.id.textinputlayout12);
		textview2 = findViewById(R.id.textview2);
		switch_splash = findViewById(R.id.switch_splash);
		textinputlayout13 = findViewById(R.id.textinputlayout13);
		textinputlayout14 = findViewById(R.id.textinputlayout14);
		textview3 = findViewById(R.id.textview3);
		switch_debuggable = findViewById(R.id.switch_debuggable);
		switch_proguard = findViewById(R.id.switch_proguard);
		switch_cleartext = findViewById(R.id.switch_cleartext);
		recyclerview1 = findViewById(R.id.recyclerview1);
		btn_build_apk = findViewById(R.id.btn_build_apk);
		appName = findViewById(R.id.appName);
		pkgName = findViewById(R.id.pkgName);
		edittext3 = findViewById(R.id.edittext3);
		edittext4 = findViewById(R.id.edittext4);
		edittext5 = findViewById(R.id.edittext5);
		minSdkInput = findViewById(R.id.minSdkInput);
		targetSdkInput = findViewById(R.id.targetSdkInput);
		
		btn_build_apk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				try {
					FileUtils.extractZipFromAssets(WebToApkActivity.this, "webviewtemplate.zip", ".blacklogics/temp");
					
					String packageName = pkgName.getText().toString().trim();
					String appLabel = appName.getText().toString().trim();
					String webUrl = edittext5.getText().toString().trim();
					String versionCodeStr = edittext3.getText().toString().trim();
					String versionNameStr = edittext4.getText().toString().trim();
					String minSdkStr = minSdkInput.getText().toString().trim();
					String targetSdkStr = targetSdkInput.getText().toString().trim();
					
					if (packageName.isEmpty()) { Toast.makeText(WebToApkActivity.this, "Enter package name!", Toast.LENGTH_SHORT).show(); pkgName.requestFocus(); return; }
					if (appLabel.isEmpty()) { Toast.makeText(WebToApkActivity.this, "Enter app name!", Toast.LENGTH_SHORT).show(); appName.requestFocus(); return; }
					if (webUrl.isEmpty()) { Toast.makeText(WebToApkActivity.this, "Enter Web URL!", Toast.LENGTH_SHORT).show(); edittext5.requestFocus(); return; }
					if (versionCodeStr.isEmpty()) { Toast.makeText(WebToApkActivity.this, "Enter version code!", Toast.LENGTH_SHORT).show(); edittext3.requestFocus(); return; }
					if (versionNameStr.isEmpty()) { Toast.makeText(WebToApkActivity.this, "Enter version name!", Toast.LENGTH_SHORT).show(); edittext4.requestFocus(); return; }
					if (minSdkStr.isEmpty()) { Toast.makeText(WebToApkActivity.this, "Enter minimum SDK!", Toast.LENGTH_SHORT).show(); minSdkInput.requestFocus(); return; }
					if (targetSdkStr.isEmpty()) { Toast.makeText(WebToApkActivity.this, "Enter target SDK!", Toast.LENGTH_SHORT).show(); targetSdkInput.requestFocus(); return; }
					
					int versionCode = Integer.parseInt(versionCodeStr);
					int minSdk = Integer.parseInt(minSdkStr);
					int targetSdk = Integer.parseInt(targetSdkStr);
					
					String packagePath = packageName.replace(".", "/");
					File projectDir = new File(Environment.getExternalStorageDirectory(), ".blacklogics/temp");
					
					File javaDir = new File(projectDir, "app/src/main/java/" + packagePath);
					File mainDir = new File(projectDir, "app/src/main");
					File resValuesDir = new File(projectDir, "app/src/main/res/values");
					File outPut = new File(projectDir, "bin");
					if (!javaDir.exists()) javaDir.mkdirs();
					if (!mainDir.exists()) mainDir.mkdirs();
					if (!resValuesDir.exists()) resValuesDir.mkdirs();
					if (!outPut.exists()) outPut.mkdirs();
					
					MainActivityGenerator gen = new MainActivityGenerator()
					.setPackageName(packageName)
					.setLayoutName("main")
					.setURL(webUrl);
					File mainActivityFile = new File(javaDir, "MainActivity.java");
					FileUtils.writeStringToFile(mainActivityFile, gen.generateMainActivity());
					
					_debugActivityGenerate();
					
					String manifestContent = AndroidManifestGenerator.generateManifest(packageName, appLabel, ".MainActivity");
					File manifestFile = new File(mainDir, "AndroidManifest.xml");
					FileUtils.writeStringToFile(manifestFile, manifestContent);
					
					String stringsXml = "<resources>\n<string name=\"app_name\" translatable=\"false\">" + appLabel + "</string>\n</resources>";
					File stringsFile = new File(resValuesDir, "strings.xml");
					FileUtils.writeStringToFile(stringsFile, stringsXml);
					
					Toast.makeText(WebToApkActivity.this, "Project generated at:\n" + projectDir.getAbsolutePath(), Toast.LENGTH_LONG).show();
					
					SystemLogPrinter.start(mLogger);
					
					Project project = new Project();
					project.setLibraries(Library.fromFile(new File(library)));
					
					File workingDir = new File(projectDir, "build_temp");
					if (!workingDir.exists() && !workingDir.mkdirs()) throw new RuntimeException("Failed to create working directory");
					project.setWorkingDirectory(workingDir);
					
					List<File> resourceDirs = new ArrayList<>();
					resourceDirs.add(resValuesDir.getParentFile());
					project.setResourcesFiles(resourceDirs);
					project.setJavaFile(javaDir);
					project.setManifestFile(manifestFile);
					project.setOutputFile(outPut);
					
					project.setVersionName(versionNameStr);
					project.setVersionCode(versionCode);
					
					if (!TextUtils.isEmpty(asset)) project.setAssetsFile(new File(asset));
					
					project.setLogger(mLogger);
					project.setMinSdk(minSdk);
					project.setTargetSdk(targetSdk);
					
					CompilerAsyncTask task = new CompilerAsyncTask(WebToApkActivity.this);
					task.execute(project);
					
				} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(WebToApkActivity.this, "Build failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
				}
				
			}
		});
	}
	
	private void initializeLogic() {
		ImageView back = findViewById(R.id.ig_toolbar_back);
		TextView title = findViewById(R.id.tx_toolbar_title);
		ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);
		
		Helper.applyRippleToToolbarView(back);
		back.setOnClickListener(Helper.getBackPressedClickListener(this));
		title.setText("Web to Apk");
		loadFile.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		loadFile.setImageResource(R.drawable.ic_more_vert_black);
		loadFile.setVisibility(View.GONE);
		Helper.applyRippleToToolbarView(loadFile);
		mLogger = new Logger();
		mLogger.attach(recyclerview1);
	}
	private Logger mLogger;
	{
	}
	
	public void _debugActivityGenerate() {
		try {
			// 1. Extract template zip to main temp folder
			FileUtils.extractZipFromAssets(WebToApkActivity.this, "webviewtemplate.zip", ".blacklogics/temp");
			
			// 2. Get package name
			String packageName = pkgName.getText().toString().trim();
			if (packageName.isEmpty()) {
				Toast.makeText(WebToApkActivity.this, "Enter package name!", Toast.LENGTH_SHORT).show();
				return;
			}
			
			// 3. Get app name
			String appLabel = appName.getText().toString().trim();
			if (appLabel.isEmpty()) appLabel = "WebView";
			
			// 4. Get URL from EditText
			String webUrl = edittext5.getText().toString().trim(); 
			if (webUrl.isEmpty()) webUrl = "https://example.com";
			
			// 5. Convert package name to folder path
			String packagePath = packageName.replace(".", "/");
			
			// 6. Define temp project folder
			File projectDir = new File(Environment.getExternalStorageDirectory(), ".blacklogics/temp");
			
			// 7. Define Java, main, and resource folders inside temp
			File javaDir = new File(projectDir, "app/src/main/java/" + packagePath);
			File mainDir = new File(projectDir, "app/src/main");
			File resValuesDir = new File(projectDir, "app/src/main/res/values");
			File outPut = new File(projectDir, "bin"); // output
			
			// 8. Create folders if not exist
			if (!javaDir.exists()) javaDir.mkdirs();
			if (!mainDir.exists()) mainDir.mkdirs();
			if (!resValuesDir.exists()) resValuesDir.mkdirs();
			if (!outPut.exists()) outPut.mkdirs();
			
			// ===== Generate DebugActivity.java =====
			StringBuilder debugActivity = new StringBuilder();
			debugActivity.append("package ").append(packageName).append(";\n\n");
			debugActivity.append("import android.app.Activity;\n");
			debugActivity.append("import android.content.Intent;\n");
			debugActivity.append("import android.os.Bundle;\n");
			debugActivity.append("import android.text.SpannableStringBuilder;\n");
			debugActivity.append("import android.widget.HorizontalScrollView;\n");
			debugActivity.append("import android.widget.ScrollView;\n");
			debugActivity.append("import android.widget.TextView;\n");
			debugActivity.append("import java.util.HashMap;\n");
			debugActivity.append("import java.util.Map;\n\n");
			debugActivity.append("public class DebugActivity extends Activity {\n\n");
			debugActivity.append("    private static final Map<String, String> exceptionMap = new HashMap<String, String>() {{\n");
			debugActivity.append("        put(\"StringIndexOutOfBoundsException\", \"Invalid string operation\\\\n\");\n");
			debugActivity.append("        put(\"IndexOutOfBoundsException\", \"Invalid list operation\\\\n\");\n");
			debugActivity.append("        put(\"ArithmeticException\", \"Invalid arithmetical operation\\\\n\");\n");
			debugActivity.append("        put(\"NumberFormatException\", \"Invalid toNumber block operation\\\\n\");\n");
			debugActivity.append("        put(\"ActivityNotFoundException\", \"Invalid intent operation\\\\n\");\n");
			debugActivity.append("    }};\n\n");
			debugActivity.append("    @Override\n");
			debugActivity.append("    protected void onCreate(Bundle savedInstanceState) {\n");
			debugActivity.append("        super.onCreate(savedInstanceState);\n\n");
			debugActivity.append("        SpannableStringBuilder formattedMessage = new SpannableStringBuilder();\n");
			debugActivity.append("        Intent intent = getIntent();\n");
			debugActivity.append("        String errorMessage = \"\";\n\n");
			debugActivity.append("        if (intent != null) {\n");
			debugActivity.append("            errorMessage = intent.getStringExtra(\"error\");\n");
			debugActivity.append("        }\n\n");
			debugActivity.append("        if (!errorMessage.isEmpty()) {\n");
			debugActivity.append("            String[] split = errorMessage.split(\"\\\\n\");\n");
			debugActivity.append("            String exceptionType = split[0];\n");
			debugActivity.append("            String message = exceptionMap.containsKey(exceptionType) ? exceptionMap.get(exceptionType) : \"\";\n\n");
			debugActivity.append("            if (!message.isEmpty()) formattedMessage.append(message);\n");
			debugActivity.append("            for (int i = 1; i < split.length; i++) {\n");
			debugActivity.append("                formattedMessage.append(split[i]);\n");
			debugActivity.append("                formattedMessage.append(\"\\\\n\");\n");
			debugActivity.append("            }\n");
			debugActivity.append("        } else {\n");
			debugActivity.append("            formattedMessage.append(\"No error message available.\");\n");
			debugActivity.append("        }\n\n");
			debugActivity.append("        setTitle(getTitle() + \" Crashed\");\n\n");
			debugActivity.append("        TextView errorView = new TextView(this);\n");
			debugActivity.append("        errorView.setText(formattedMessage);\n");
			debugActivity.append("        errorView.setTextIsSelectable(true);\n\n");
			debugActivity.append("        HorizontalScrollView hscroll = new HorizontalScrollView(this);\n");
			debugActivity.append("        ScrollView vscroll = new ScrollView(this);\n");
			debugActivity.append("        hscroll.addView(vscroll);\n");
			debugActivity.append("        vscroll.addView(errorView);\n\n");
			debugActivity.append("        setContentView(hscroll);\n");
			debugActivity.append("    }\n");
			debugActivity.append("}\n");
			
			File debugActivityFile = new File(javaDir, "DebugActivity.java");
			FileUtils.writeStringToFile(debugActivityFile, debugActivity.toString());
			
			// ===== Generate BlackLogger.java =====
			StringBuilder blackLogger = new StringBuilder();
			blackLogger.append("package ").append(packageName).append(";\n\n");
			blackLogger.append("import android.content.Context;\n");
			blackLogger.append("import android.content.Intent;\n");
			blackLogger.append("import android.util.Log;\n");
			blackLogger.append("import java.io.BufferedReader;\n");
			blackLogger.append("import java.io.IOException;\n");
			blackLogger.append("import java.io.InputStreamReader;\n\n");
			blackLogger.append("import ").append(packageName).append(".BlackApplication;\n\n");
			blackLogger.append("public class BlackLogger {\n\n");
			blackLogger.append("    private static volatile boolean isRunning = false;\n");
			blackLogger.append("    private static Thread loggerThread = new Thread() {\n");
			blackLogger.append("        @Override\n");
			blackLogger.append("        public void run() {\n");
			blackLogger.append("            isRunning = true;\n");
			blackLogger.append("            try {\n");
			blackLogger.append("                Runtime.getRuntime().exec(\"logcat -c\");\n");
			blackLogger.append("                Process process = Runtime.getRuntime().exec(\"logcat\");\n\n");
			blackLogger.append("                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {\n");
			blackLogger.append("                    String logTxt = bufferedReader.readLine();\n");
			blackLogger.append("                    do {\n");
			blackLogger.append("                        broadcastLog(logTxt);\n");
			blackLogger.append("                    } while (isRunning && (logTxt = bufferedReader.readLine()) != null);\n\n");
			blackLogger.append("                    if (isRunning) {\n");
			blackLogger.append("                        broadcastLog(\"Logger got killed. Restarting.\");\n");
			blackLogger.append("                        startLogging();\n");
			blackLogger.append("                    } else {\n");
			blackLogger.append("                        broadcastLog(\"Logger stopped.\");\n");
			blackLogger.append("                    }\n");
			blackLogger.append("                }\n");
			blackLogger.append("            } catch (IOException e) {\n");
			blackLogger.append("                broadcastLog(e.getMessage());\n");
			blackLogger.append("            }\n");
			blackLogger.append("        }\n");
			blackLogger.append("    };\n\n");
			blackLogger.append("    public static synchronized void startLogging() {\n");
			blackLogger.append("        if (!isRunning) {\n");
			blackLogger.append("            loggerThread.start();\n");
			blackLogger.append("        } else {\n");
			blackLogger.append("            broadcastLog(\"Logger already running\");\n");
			blackLogger.append("        }\n");
			blackLogger.append("    }\n\n");
			blackLogger.append("    public static synchronized void stopLogging() {\n");
			blackLogger.append("        if (isRunning) {\n");
			blackLogger.append("            isRunning = false;\n");
			blackLogger.append("            broadcastLog(\"Stopping logger by user request.\");\n");
			blackLogger.append("        } else {\n");
			blackLogger.append("            broadcastLog(\"Logger not running\");\n");
			blackLogger.append("        }\n");
			blackLogger.append("    }\n\n");
			blackLogger.append("    public static void broadcastLog(String log) {\n");
			blackLogger.append("        Context context = BlackApplication.getContext();\n");
			blackLogger.append("        Intent intent = new Intent();\n");
			blackLogger.append("        intent.setAction(\"pro.sketchware.ACTION_NEW_DEBUG_LOG\");\n");
			blackLogger.append("        intent.putExtra(\"log\", log);\n");
			blackLogger.append("        intent.putExtra(\"packageName\", context.getPackageName());\n");
			blackLogger.append("        context.sendBroadcast(intent);\n");
			blackLogger.append("    }\n");
			blackLogger.append("}\n");
			
			File blackLoggerFile = new File(javaDir, "BlackLogger.java");
			FileUtils.writeStringToFile(blackLoggerFile, blackLogger.toString());
			
			// ===== Generate BlackApplication.java =====
			StringBuilder blackApp = new StringBuilder();
			blackApp.append("package ").append(packageName).append(";\n\n");
			blackApp.append("import android.app.Application;\n");
			blackApp.append("import android.content.Context;\n");
			blackApp.append("import android.content.Intent;\n");
			blackApp.append("import android.os.Process;\n");
			blackApp.append("import android.util.Log;\n\n");
			blackApp.append("public class BlackApplication extends Application {\n\n");
			blackApp.append("    private static Context mApplicationContext;\n\n");
			blackApp.append("    public static Context getContext() {\n");
			blackApp.append("        return mApplicationContext;\n");
			blackApp.append("    }\n\n");
			blackApp.append("    @Override\n");
			blackApp.append("    public void onCreate() {\n");
			blackApp.append("        mApplicationContext = getApplicationContext();\n\n");
			blackApp.append("        Thread.setDefaultUncaughtExceptionHandler(\n");
			blackApp.append("            new Thread.UncaughtExceptionHandler() {\n");
			blackApp.append("                @Override\n");
			blackApp.append("                public void uncaughtException(Thread thread, Throwable throwable) {\n");
			blackApp.append("                    Intent intent = new Intent(getApplicationContext(), DebugActivity.class);\n");
			blackApp.append("                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);\n");
			blackApp.append("                    intent.putExtra(\"error\", Log.getStackTraceString(throwable));\n");
			blackApp.append("                    startActivity(intent);\n\n");
			blackApp.append("                    BlackLogger.broadcastLog(Log.getStackTraceString(throwable));\n");
			blackApp.append("                    Process.killProcess(Process.myPid());\n");
			blackApp.append("                    System.exit(1);\n");
			blackApp.append("                }\n");
			blackApp.append("            }\n");
			blackApp.append("        );\n\n");
			blackApp.append("        BlackLogger.startLogging();\n");
			blackApp.append("        super.onCreate();\n");
			blackApp.append("    }\n");
			blackApp.append("}\n");
			
			File blackAppFile = new File(javaDir, "BlackApplication.java");
			FileUtils.writeStringToFile(blackAppFile, blackApp.toString());
			
			// Toast.makeText(WebToApkActivity.this, "DebugActivity.java generated!", Toast.LENGTH_SHORT).show();
			
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(WebToApkActivity.this, "Failed to generate DebugActivity: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
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