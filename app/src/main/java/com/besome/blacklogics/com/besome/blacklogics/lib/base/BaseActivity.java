package com.besome.blacklogics.lib.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apk.builder.FileUtil;
import com.besome.blacklogics.beans.ProjectBean;
import com.besome.blacklogics.development.Complex;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

import java.lang.ref.WeakReference;

/**
* BaseActivity — Professional Sketchware-style base activity
* that includes lifecycle safety, navigation utilities,
* project data management, and enforced Java/XML code generation.
*
* Any activity extending BaseActivity must override
* {@link #generateJavaCode()} and {@link #generateXmlCode()},
* unless relying on default implementation.
*
* Added features:
* - Lifecycle hooks
* - Safe UI updates
* - Built-in navigation utilities
* - Project data management
* - Custom library hooks
*
* Author: NexusTeam
*/
public abstract class BaseActivity extends AppCompatActivity {
	
	private static final String TAG = "BaseActivity";
	
	protected String sc_id = "";
	protected String pkgName = "";
	protected String scName = "";
	protected String projectPath = "";
	
	protected ProjectBean projectBean;
	protected Complex complex;
	
	private final Handler mainHandler = new Handler(Looper.getMainLooper());
	private boolean isActivityDestroyed = false;
	private long activityCreateTime = 0;
	
	private WeakReference<BaseActivity> activityReference;
	
	// ==================== LIFECYCLE HOOKS ====================
	protected boolean autoGenerateCode = true; // Sketchware-level toggle
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		activityCreateTime = System.currentTimeMillis();
		activityReference = new WeakReference<>(this);
		isActivityDestroyed = false;
		
		initializeCommonData();
		
		log("Activity created: " + getClass().getSimpleName());
		
		if (autoGenerateCode) {
			executeBackgroundTask(this::generateJavaCode, () -> log("Java code generated."));
			executeBackgroundTask(this::generateXmlCode, () -> log("XML code generated."));
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		isActivityDestroyed = true;
		mainHandler.removeCallbacksAndMessages(null);
		
		if (complex != null) complex.cleanup();
		
		log("Activity destroyed: " + getClass().getSimpleName());
	}
	
	
	@Override
	public void onConfigurationChanged(@NonNull Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		log("Configuration changed");
	}
	
	// Change method signature to match DesignActivity
	protected abstract void generateXmlCode(String layoutName);
	
	
	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		try {
			outState.putString("sc_id", sc_id);
			outState.putString("pkgName", pkgName);
			outState.putString("scName", scName);
			super.onSaveInstanceState(outState);
		} catch (Exception e) {
			logError("onSaveInstanceState", e);
		}
	}
	
	@Override
	protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
		try {
			sc_id = savedInstanceState.getString("sc_id", "");
			pkgName = savedInstanceState.getString("pkgName", "");
			scName = savedInstanceState.getString("scName", "");
			super.onRestoreInstanceState(savedInstanceState);
		} catch (Exception e) {
			logError("onRestoreInstanceState", e);
			initializeCommonData();
		}
	}
	
	// ==================== ABSTRACT METHODS ====================
	
	/**
* Generates Java code for the activity.
* Must be overridden if specific behavior is required.
* By default, logs the call.
*/	
	protected void generateJavaCode() {
		log("Default generateJavaCode() called. Override in derived activity.");
	}
	
	/**
* Generates XML layout code for the activity.
* Must be overridden if specific behavior is required.
* By default, logs the call.
*/	
	protected void generateXmlCode() {
		log("Default generateXmlCode() called. Override in derived activity.");
	}
	
	// ==================== COMMON INITIALIZATION ====================
	
	protected void initializeCommonData() {
		try {
			sc_id = getIntent().getStringExtra("sc_id");
			pkgName = getIntent().getStringExtra("pkgName");
			scName = getIntent().getStringExtra("scName");
			projectPath = getIntent().getStringExtra("projectPath");
			
			if (sc_id == null || sc_id.isEmpty()) {
				log("sc_id missing, finishing activity");
				safeFinish();
				return;
			}
			
			projectBean = new ProjectBean();
			complex = new Complex();
			complex.setC(this);
			complex.setId(sc_id);
            complex.removeDuplicateActivities();
			
		} catch (Exception e) {
			logError("initializeCommonData", e);
			showToastSafe("Initialization error");
			safeFinish();
		}
	}
	
	// ==================== NAVIGATION ====================
	
	protected void navigateToClassSafe(Class<?> targetClass) {
		if (!isActivityValid()) return;
		
		try {
			Intent intent = new Intent(this, targetClass);
			intent.putExtra("sc_id", sc_id);
			intent.putExtra("pkgName", pkgName);
			intent.putExtra("scName", scName);
			intent.putExtra("projectPath", projectPath);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
		} catch (Exception e) {
			logError("navigateToClassSafe", e);
			showToastSafe("Navigation error");
		}
	}
	
	// ==================== SAFETY UTILITIES ====================
	
	protected boolean isActivityValid() {
		return !isActivityDestroyed && !isFinishing() && !isDestroyed();
	}
	
	protected void safeFinish() {
		if (isActivityValid()) {
			runOnUiThreadSafe(this::finish);
		}
	}
	
	protected void showToastSafe(String message) {
		runOnUiThreadSafe(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
	}
	
	protected void runOnUiThreadSafe(Runnable action) {
		if (isActivityValid() && mainHandler != null) {
			mainHandler.post(() -> {
				if (isActivityValid()) {
					try {
						action.run();
					} catch (Exception e) {
						logError("runOnUiThreadSafe", e);
					}
				}
			});
		}
	}
	
	protected void executeBackgroundTask(Runnable backgroundTask, Runnable uiCallback) {
		new Thread(() -> {
			try {
				if (backgroundTask != null) backgroundTask.run();
				if (uiCallback != null && isActivityValid()) runOnUiThreadSafe(uiCallback);
			} catch (Exception e) {
				logError("executeBackgroundTask", e);
				//showToastSafe("Operation failed");
			}
		}).start();
	}
	
	// ==================== LOGGING ====================
	
	protected void log(String message) {
		Log.d(TAG, message);
	}
	
	protected void logError(String operation, Exception e) {
		Log.e(TAG, "Error in " + operation, e);
	}
	
	// ==================== PATH UTILITIES ====================
	
	protected String getBasePath() {
		return FileUtil.getExternalStorageDir().concat("/.blacklogics/data/".concat(sc_id));
	}
	
	protected String getProjectRoot() {
		return FileUtil.getExternalStorageDir().concat("/.blacklogics/mysc/".concat(sc_id));
	}
	
	protected String getApkPath() {
		return FileUtil.getExternalStorageDir().concat("/.blacklogics/mysc/".concat(sc_id.concat("/bin/".concat(scName + ".apk"))));
	}
}
