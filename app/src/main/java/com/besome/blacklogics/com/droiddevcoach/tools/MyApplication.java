package com.droiddevcoach.tools;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.util.TypedValue;

import com.besome.blacklogics.*;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class MyApplication extends Application {
	
	private static final String TAG = "MyApplication";
	private static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// Initialize the static context
		context = getApplicationContext();
		
		// Initialize logging and services
		initLogging();
		initSomeService();
		
		// Set up the crash handler
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable throwable) {
				// Log the error
				Log.e(TAG, "Uncaught exception", throwable);
				
				// Start DebugActivity with the error message
				Intent intent = new Intent(context, DebugActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.putExtra("error_message", getStackTraceString(throwable));
				context.startActivity(intent);
				
				// Terminate the application
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(1);
			}
		});
	}
	
	private void initLogging() {
		Log.d(TAG, "Logging initialized");
	}
	
	private void initSomeService() {
		Log.d(TAG, "SomeService initialized");
	}
	
	public void doGlobalAction() {
		Log.d(TAG, "Global action performed");
	}
	
	public void handleError(Exception e) {
		Log.e(TAG, "Error occurred: " + e.getMessage());
	}
	
	private String getStackTraceString(Throwable throwable) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement element : throwable.getStackTrace()) {
			sb.append(element.toString()).append("\n");
		}
		return sb.toString();
	}
	
	public static Context getContext() {
		return context;
	}
	
	public static void showMessage(String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	// Static method to convert dp to pixels
	public static float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, context.getResources().getDisplayMetrics());
	}
	
	public static void clearInputs(ViewGroup viewGroup) {
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View view = viewGroup.getChildAt(i);
			
			if (view instanceof EditText) {
				((EditText) view).setText("");
			} else if (view instanceof CheckBox) {
				((CheckBox) view).setChecked(false);
			} else if (view instanceof RadioGroup) {
				((RadioGroup) view).clearCheck();
			} else if (view instanceof Spinner) {
				((Spinner) view).setSelection(0);
			} else if (view instanceof SeekBar) {
				((SeekBar) view).setProgress(0);
			} else if (view instanceof ViewGroup) {
				// Recursive call for nested ViewGroups
				clearInputs((ViewGroup) view);
			}
		}
	}
	
	
}
