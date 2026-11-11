package com.nexusteam.blacklogics.lib.base;

import com.nexusteam.blacklogics.manager.ProjectSettingsManager;
import com.nexusteam.blacklogics.manager.ProjectLogicManager;
import com.nexusteam.blacklogics.beans.ActivityFileInfo;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import com.nexusteam.blacklogics.lib.language.LanguageManager;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.apk.builder.FileUtil;
import com.nexusteam.blacklogics.*;
import com.nexusteam.blacklogics.*;
import com.nexusteam.blacklogics.project.ProjectDataHelper;
import com.nexusteam.blacklogics.beans.ProjectBean;
import com.nexusteam.blacklogics.development.Complex;

import com.nexusteam.blacklogics.beans.ProjectInfoBean;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.BlockStorage;

import com.nexusteam.internal.helper.ProjectFileBeanFactory;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;

import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;
import com.nexusteam.blacklogics.sdk.*;
import com.nexusteam.blacklogics.sdk.layouteditor.*;
import java.lang.ref.WeakReference;

public abstract class BaseActivity extends AppCompatActivity {
	
	private static final String TAG = "BaseActivity";
	
	protected ProjectInfoBean projectInfo;
	
	public static ProjectFileBean fileBean;
	
	private boolean tabsReady = false;
	
	protected String sc_id = "";
	protected String pkgName = "";
	protected String scName = "";
	protected String projectPath = "";
	
	public String currentActivityName = "MainActivity";
	protected String currentLayoutName = "main";
	
	protected ProjectBean projectBean;
	protected Complex complex;
	protected ProjectSettingsManager settings;
	public ActivityFileInfo activitySession;
	protected static final String SESSION_KEY = "base_activity_session";
	
	private final Handler mainHandler = new Handler(Looper.getMainLooper());
	private boolean isActivityDestroyed = false;
	private long activityCreateTime = 0;
	protected ViewEditor editor;
	private WeakReference<BaseActivity> activityReference;
	
	protected TabAdapterFragmentAdapter baseAdapter;
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
	}
	
	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(LanguageManager.apply(newBase));
	}
	
	@Override
	protected void onDestroy() {
		ProjectLogicManager.resetCache();
		super.onDestroy();
		isActivityDestroyed = true;
		mainHandler.removeCallbacksAndMessages(null);
		
		if (complex != null) complex.cleanup();
		
		log("Activity destroyed: " + getClass().getSimpleName());
	}
	
	@Override
	public void applyOverrideConfiguration(Configuration overrideConfiguration) {
		if (overrideConfiguration != null) {
			int uiMode = overrideConfiguration.uiMode;
			overrideConfiguration.setTo(getBaseContext().getResources().getConfiguration());
			overrideConfiguration.uiMode = uiMode;
		}
		super.applyOverrideConfiguration(overrideConfiguration);
	}
	
	
	@Override
	public void onConfigurationChanged(@NonNull Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		log("Configuration changed");
	}
	
	
	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		try {
			
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
			
			
			sc_id = savedInstanceState.getString("sc_id", "");
			pkgName = savedInstanceState.getString("pkgName", "");
			scName = savedInstanceState.getString("scName", "");
			
			currentActivityName = savedInstanceState.getString("current_activity_name", "MainActivity");
			currentLayoutName = savedInstanceState.getString("current_layout_name", "main");
			
			
			String sessionJson = savedInstanceState.getString("activity_session");
			if (sessionJson != null) {
				activitySession = ActivityFileInfo.fromJson(sessionJson);
				log("Session restored - Activity: " + currentActivityName + ", Layout: " + currentLayoutName);
			}
			ProjectDataHelper.setActivityName(this, currentActivityName);
			super.onRestoreInstanceState(savedInstanceState);
			
			
			restoreBaseUI();
		} catch (Exception e) {
			logError("onRestoreInstanceState", e);
			initializeCommonData();
		}
	}
	
	
	
	protected void generateJavaCode() {
		log("Default generateJavaCode() called. Override in derived activity.");
	}
	
	protected void generateXmlCode() {
		log("Default generateXmlCode() called. Override in derived activity.");
	}
	
	
	
	protected void initializeCommonData() {
		try {
			sc_id = getIntent().getStringExtra("sc_id");
			pkgName = getIntent().getStringExtra("pkgName");
			scName = getIntent().getStringExtra("scName");
			projectPath = getIntent().getStringExtra("projectPath");
			
			com.nexusteam.blacklogics.logic.ProjectLogicRepository.setPath(projectPath);
			
			if (sc_id == null || sc_id.isEmpty()) {
				log("sc_id missing, finishing activity");
				safeFinish();
				return;
			}
			fileBean = ProjectFileBeanFactory.createActivity(
			currentLayoutName,
			0,         
			false,      
			false,     
			true,       
			false      
			);
			projectInfo = new ProjectInfoBean(
			sc_id,
			currentActivityName,
			currentLayoutName
			);
			BlockStorage.setScId(sc_id);
			ProjectLogicManager.setPath(projectPath);
			com.nexusteam.blacklogics.logic.ProjectLogicRepository.setPath(projectPath);
			settings = new ProjectSettingsManager(sc_id);
			settings.load(this);
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
	
	public String getProjectPath() {
		return projectPath;
	}
	
	
	
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
	
	
	
	protected boolean isActivityValid() {
		return !isActivityDestroyed && !isFinishing() && !isDestroyed();
	}
	
	protected void safeFinish() {
		if (isActivityValid()) {
			runOnUiThreadSafe(new Runnable() {
				@Override
				public void run() {
					finish();
				}
			});
		}
	}
	
	protected void showToastSafe(final String message) {
		runOnUiThreadSafe(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(
				BaseActivity.this,
				message,
				Toast.LENGTH_SHORT
				).show();
			}
		});
	}
	
	protected void runOnUiThreadSafe(final Runnable action) {
		if (isActivityValid() && mainHandler != null) {
			mainHandler.post(new Runnable() {
				@Override
				public void run() {
					if (isActivityValid()) {
						try {
							if (action != null) {
								action.run();
							}
						} catch (Exception e) {
							logError("runOnUiThreadSafe", e);
						}
					}
				}
			});
		}
	}
	
	protected void executeBackgroundTask(
	final Runnable backgroundTask,
	final Runnable uiCallback
	) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (backgroundTask != null) {
						backgroundTask.run();
					}
					if (uiCallback != null && isActivityValid()) {
						runOnUiThreadSafe(uiCallback);
					}
				} catch (Exception e) {
					logError("executeBackgroundTask", e);
				}
			}
		}).start();
	}
	
	
	protected void restoreBaseUI() {
		restoreTabSafely();
	}
	
	private void restoreTabSafely() {
	}
	
	
	
	protected boolean isAdapterReady() {
		return true;
	}
	
	protected void setupBaseTabs(ViewPager viewPager, TabLayout tabLayout, int tabCount) {
	}
	
	
	protected Fragment getBaseFragment(int position) {
		return null;
	}
	
	
	
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
		if (activitySession != null && sc_id != null && !sc_id.isEmpty()) {
			
			activitySession.setActivityName(currentActivityName);
			activitySession.setLayoutName(currentLayoutName);
			
			try {
				getSharedPreferences(SESSION_KEY + "_" + sc_id, MODE_PRIVATE)
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
			activitySession = new ActivityFileInfo(
			currentActivityName,  
			sc_id,
			currentLayoutName,
			"Session Started"
			);
		} else {
			
			activitySession.setActivityName(currentActivityName);
			activitySession.setLayoutName(currentLayoutName);
		}
		
		if (sc_id == null || sc_id.isEmpty()) {
			return;
		}
		
		String savedSession = getSharedPreferences(SESSION_KEY + "_" + sc_id, MODE_PRIVATE).getString(SESSION_KEY, null);
		if (savedSession != null) {
			try {
				ActivityFileInfo restoredSession = ActivityFileInfo.fromJson(savedSession);
				
				
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
		
		if (projectInfo != null) {
			projectInfo.setActivityName(activityName);
		}
		
		if (activitySession != null) {
			activitySession.setActivityName(activityName);
			saveSession();
		}
	}
	
	protected void setCurrentLayoutName(String layoutName) {
		this.currentLayoutName = layoutName;
		
		if (projectInfo != null) {
			projectInfo.setLayoutName(layoutName);
		}
		
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
	
	protected String getProjectScId() {
		return activitySession != null ? activitySession.getScId() : sc_id;
	}
	
	protected void updateSessionInfo(String info) {
		if (activitySession != null) {
			activitySession.setInfo(info);
			
			saveSession();
		}
	}
}
