package com.besome.blacklogics.lib.base;

import b.b.b.rs;
import b.b.b.Qf;
import b.b.b.Qm;
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

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.apk.builder.FileUtil;
import com.besome.blacklogics.*;
import com.besome.blacklogics.project.ProjectDataHelper;
import com.besome.blacklogics.beans.ProjectBean;
import com.besome.blacklogics.development.Complex;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;

import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;
import com.shapun.layouteditor.*;
import java.lang.ref.WeakReference;

public abstract class BaseActivity extends AppCompatActivity {
	
	private static final String TAG = "BaseActivity";
	
	protected String sc_id = "";
	protected String pkgName = "";
	protected String scName = "";
	protected String projectPath = "";
	
	protected String currentActivityName = "MainActivity";
	protected String currentLayoutName = "main";
	
	protected ProjectBean projectBean;
	protected Complex complex;
	protected rs settings;
    public Qm activitySession;
    protected static final String SESSION_KEY = "base_activity_session";
	
	private final Handler mainHandler = new Handler(Looper.getMainLooper());
	private boolean isActivityDestroyed = false;
	private long activityCreateTime = 0;
	protected ViewEditor editor;
	private WeakReference<BaseActivity> activityReference;
	
	protected TabAdapterFragmentAdapter baseAdapter;
	protected ViewPager baseViewPager;
	protected TabLayout baseTabLayout;
	protected int savedTabPosition = 0;
	protected int savedXmlPosition = 0;
	protected int savedJavaPosition = 0;
	
	
	protected boolean autoGenerateCode = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		activityCreateTime = System.currentTimeMillis();
		activityReference = new WeakReference<>(this);
		isActivityDestroyed = false;
		
		initializeCommonData();
		initializeSession();
		
		log("Activity created: " + getClass().getSimpleName());
		
		if (autoGenerateCode) {
			executeBackgroundTask(this::generateJavaCode, () -> log("Java code generated."));
			executeBackgroundTask(this::generateXmlCode, () -> log("XML code generated."));
		}
	}
	
	@Override
	protected void onDestroy() {
		baseAdapter = null;
		baseViewPager = null;
		baseTabLayout = null;
        Qf.resetCache();
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
			// Save tab states
			if (baseTabLayout != null) {
				outState.putInt("savedTabPosition", baseTabLayout.getSelectedTabPosition());
			}
			
			outState.putString("sc_id", sc_id);
			outState.putString("pkgName", pkgName);
			outState.putString("scName", scName);
			outState.putString("current_activity_name", currentActivityName);
			outState.putString("current_layout_name", currentLayoutName);
			
			if (activitySession != null) {
				outState.putString("activity_session", activitySession.toJson());
			}
			
			saveSession();
			super.onSaveInstanceState(outState);
		} catch (Exception e) {
			logError("onSaveInstanceState", e);
		}
	}
	
	@Override
	protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
		try {
			// Restore tab states
			savedTabPosition = savedInstanceState.getInt("savedTabPosition", 0);
			
			sc_id = savedInstanceState.getString("sc_id", "");
			pkgName = savedInstanceState.getString("pkgName", "");
			scName = savedInstanceState.getString("scName", "");
			
			currentActivityName = savedInstanceState.getString("current_activity_name", "MainActivity");
			currentLayoutName = savedInstanceState.getString("current_layout_name", "main");
			
			// Restore Qm session
			String sessionJson = savedInstanceState.getString("activity_session");
			if (sessionJson != null) {
				activitySession = Qm.fromJson(sessionJson);
				log("Session restored - Activity: " + currentActivityName + ", Layout: " + currentLayoutName);
			}
			ProjectDataHelper.setActivityName(this, currentActivityName);
			super.onRestoreInstanceState(savedInstanceState);
			
			// Delayed restoration of UI
			restoreBaseUI();
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
			Qf.setPath(projectPath);
			settings = new rs(sc_id);
			projectBean = new ProjectBean();
			complex = new Complex();
			editor = new ViewEditor(this);
			editor.setScId(sc_id);
			editor.setPath(projectPath);
			complex.setC(this);
			complex.setId(sc_id);
			ProjectDataHelper.setScId(this, sc_id);
            ProjectDataHelper.setActivityName(this, currentActivityName);
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
			}
		}).start();
	}
	
	protected void restoreBaseUI() {
		if (baseTabLayout != null && savedTabPosition < baseTabLayout.getTabCount()) {
			runOnUiThreadSafe(() -> {
				if (isActivityValid() && baseTabLayout != null) {
					TabLayout.Tab tab = baseTabLayout.getTabAt(savedTabPosition);
					if (tab != null) {
						tab.select();
					}
				}
			});
		}
	}
	
	protected boolean isAdapterReady() {
		return baseAdapter != null && baseAdapter.getCount() > 0;
	}
	
	protected void setupBaseTabs(ViewPager viewPager, TabLayout tabLayout, int tabCount) {
		this.baseViewPager = viewPager;
		this.baseTabLayout = tabLayout;
		
		if (baseAdapter == null) {
			baseAdapter = new TabAdapterFragmentAdapter(this, getSupportFragmentManager(), tabCount);
		}
		
		viewPager.setAdapter(baseAdapter);
		tabLayout.setupWithViewPager(viewPager);
		
		// Restore previous tab position
		if (savedTabPosition > 0 && savedTabPosition < tabLayout.getTabCount()) {
			new Handler().postDelayed(() -> {
				if (isActivityValid() && tabLayout != null) {
					tabLayout.getTabAt(savedTabPosition).select();
				}
			}, 100);
		}
	}
	
	protected Fragment getBaseFragment(int position) {
		if (baseAdapter != null && position < baseAdapter.getCount()) {
			try {
				return baseAdapter.getFragment(position);
			} catch (Exception e) {
				logError("getBaseFragment", e);
			}
		}
		return null;
	}
	
	// ==================== LOGGING ====================
	
	protected void log(String message) {
		Log.d(TAG, message);
	}
	
	protected void logError(String operation, Exception e) {
		Log.e(TAG, "Error in " + operation, e);
	}
	
	public int getMinSdk() {
		return settings.getMinSdk();
	}
	
	public int getTargetSdk() {
		return settings.getTargetSdk(); 
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
	
	protected void saveSession() {
		if (activitySession != null) {
			// Ensure session has latest activity/layout names
			activitySession.setActivityName(currentActivityName);
			activitySession.setLayoutName(currentLayoutName);
			
			try {
				getPreferences(MODE_PRIVATE)
				.edit()
				.putString(SESSION_KEY, activitySession.toJson())
				.apply();
				log("Session saved - Activity: " + currentActivityName + ", Layout: " + currentLayoutName);
			} catch (Exception e) {
				logError("saveSession", e);
			}
		}
	}
	protected void initializeSession() {
		if (activitySession == null) {
			activitySession = new Qm(
			currentActivityName,  
			sc_id,                // Project ID
			currentLayoutName,    // Layout name  
			"Session Started"     // Additional info
			);
		} else {
			// Update existing session with current values
			activitySession.setActivityName(currentActivityName);
			activitySession.setLayoutName(currentLayoutName);
		}
		
		// Restore from saved state if available
		String savedSession = getPreferences(MODE_PRIVATE).getString(SESSION_KEY, null);
		if (savedSession != null) {
			try {
				Qm restoredSession = Qm.fromJson(savedSession);
				
				// IMPORTANT: Restore activity and layout names
				currentActivityName = restoredSession.getActivityName();
				currentLayoutName = restoredSession.getLayoutName();
				
				activitySession = restoredSession;
				log("Session restored - Activity: " + currentActivityName + ", Layout: " + currentLayoutName);
			} catch (Exception e) {
				logError("initializeSession", e);
			}
		}
	}
	
	protected void setCurrentActivityName(String activityName) {
		this.currentActivityName = activityName;
		if (activitySession != null) {
			activitySession.setActivityName(activityName);
			saveSession();
		}
	}
	
	protected void setCurrentLayoutName(String layoutName) {
		this.currentLayoutName = layoutName;
		if (activitySession != null) {
			activitySession.setLayoutName(layoutName);
			saveSession();
		}
	}
	
	protected String getCurrentActivityName() {
		return activitySession != null ? activitySession.getActivityName() : currentActivityName;
	}
	
	protected String getCurrentLayoutName() {
		return activitySession != null ? activitySession.getLayoutName() : currentLayoutName;
	}
	
	protected void updateSessionInfo(String info) {
		if (activitySession != null) {
			activitySession.setInfo(info);
		//	activitySession.setTimestamp(System.currentTimeMillis());
			saveSession();
		}
	}
}