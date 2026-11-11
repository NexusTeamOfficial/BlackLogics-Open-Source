package com.nexusteam.blacklogics.generator.source;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import android.util.Base64;

import com.nexusteam.blacklogics.logic.ProjectLogicRepository;
import com.nexusteam.blacklogics.generator.source.model.ActivityType;
import com.nexusteam.blacklogics.generator.source.model.ActivityTypeRegistry;
import com.nexusteam.blacklogics.editor.layout.LayoutLoader;

public class JavaCodeImporter {
	
	private static boolean isAndroidXEnabled(String projectPath) {
		try {
			if (projectPath == null) return true;
			String p = projectPath.replace('\\', '/');
			int idx = p.lastIndexOf('/');
			if (idx < 0 || idx >= p.length() - 1) return true;
			com.nexusteam.blacklogics.development.Complex c =
					new com.nexusteam.blacklogics.development.Complex();
			c.setId(p.substring(idx + 1));
			return c.getAndroidXEnable();
		} catch (Throwable t) {
			return true;
		}
	}
	
	public static void addRequiredImports(StringBuilder javaCode, String activityName, String projectPath) {
		
		ActivityTypeRegistry registry = ActivityTypeRegistry.getInstance();
		com.nexusteam.blacklogics.generator.source.model.ActivityConfig config = registry.getActivityConfig(activityName);
		ActivityType activityType = (config != null) ? config.getType() : ActivityType.ACTIVITY;
		
		
		javaCode.append("import android.app.Activity;\n");
		javaCode.append("import android.os.Bundle;\n");
		javaCode.append("import android.view.*;\n");
		javaCode.append("import android.widget.*;\n");
		javaCode.append("import android.content.*;\n");
		javaCode.append("import android.util.*;\n");
		javaCode.append("import android.webkit.*;\n");
		javaCode.append("import android.media.*;\n");
		javaCode.append("import android.hardware.*;\n");
		javaCode.append("import android.location.*;\n");
		javaCode.append("import java.util.*;\n");
		javaCode.append("import android.net.*;\n");
		javaCode.append("import android.animation.*;\n");
		javaCode.append("import android.app.job.*;\n");
		javaCode.append("import android.content.pm.*;\n");
		javaCode.append("import android.app.*;\n");
		javaCode.append("import android.os.*;\n");
		javaCode.append("import android.database.*;\n");
		javaCode.append("import android.provider.*;\n");
		javaCode.append("import android.bluetooth.*;\n");

		try {
			String scId = null;
			if (projectPath != null) {
				String p = projectPath.replace('\\', '/');
				int idx = p.lastIndexOf('/');
				if (idx >= 0 && idx < p.length() - 1) scId = p.substring(idx + 1);
			}
			if (scId != null && activityName != null) {
				com.nexusteam.blacklogics.development.Complex c =
						new com.nexusteam.blacklogics.development.Complex();
				c.setId(scId);
				String name = activityName;
				if (name.endsWith(".java")) name = name.substring(0, name.length() - 5);
				if (c.getEnableFabBoolean(name)) {
					if (!javaCode.toString().contains("floatingactionbutton.FloatingActionButton")) {
						javaCode.append("import com.google.android.material.floatingactionbutton.FloatingActionButton;\n");
					}
				}
				if (c.isDrawerEnabled(name)) {
					if (!javaCode.toString().contains("drawerlayout.widget.DrawerLayout")) {
						javaCode.append("import androidx.drawerlayout.widget.DrawerLayout;\n");
					}
					if (!javaCode.toString().contains("ActionBarDrawerToggle")) {
						javaCode.append("import androidx.appcompat.app.ActionBarDrawerToggle;\n");
					}
					if (!javaCode.toString().contains("core.view.GravityCompat")) {
						javaCode.append("import androidx.core.view.GravityCompat;\n");
					}
				}
			}
		} catch (Throwable ignore) {}
		
		
		if (activityType != null) {
			if (activityType.isFragment()) {
				
				javaCode.append("import androidx.fragment.app.Fragment;\n");
				
				
				if (!javaCode.toString().contains("import android.view.LayoutInflater")) {
					javaCode.append("import android.view.LayoutInflater;\n");
				}
				
				
				if (!javaCode.toString().contains("import android.view.ViewGroup")) {
					javaCode.append("import android.view.ViewGroup;\n");
				}
			}
			
			
			if (activityType.isDialogFragment()) {
				if (activityType == ActivityType.DIALOG_FRAGMENT) {
					javaCode.append("import androidx.fragment.app.DialogFragment;\n");
				} else if (activityType == ActivityType.APP_COMPAT_DIALOG_FRAGMENT) {
					javaCode.append("import androidx.appcompat.app.AppCompatDialogFragment;\n");
				} else if (activityType == ActivityType.BOTTOM_SHEET_DIALOG_FRAGMENT) {
					javaCode.append("import com.google.android.material.bottomsheet.BottomSheetDialogFragment;\n");
				}
				
				
				javaCode.append("import android.app.Dialog;\n");
				javaCode.append("import android.app.AlertDialog;\n");
			}
			
			
			if (activityType == ActivityType.LIST_FRAGMENT) {
				javaCode.append("import androidx.fragment.app.ListFragment;\n");
			} else if (activityType == ActivityType.PREFERENCE_FRAGMENT) {
				javaCode.append("import androidx.preference.PreferenceFragmentCompat;\n");
			}
			
			
			if (!activityType.isFragment() && !activityType.getImportPath().isEmpty()) {
				javaCode.append("import ").append(activityType.getImportPath()).append(";\n");
			}
		}
		
		
		Set<String> requiredImports = new HashSet<>();
		List<String> activityEvents = getLifecycleEventsForActivity(activityName, projectPath);
		
		for (String event : activityEvents) {
			if ("onConfigurationChanged".equals(event)) {
				requiredImports.add("import android.content.res.Configuration;\n");
			} else if ("onProvideKeyboardShortcuts".equals(event)) {
				requiredImports.add("import android.view.KeyboardShortcutGroup;\n");
			} else if ("onCreateContextMenu".equals(event) || "onContextItemSelected".equals(event)) {
				requiredImports.add("import android.view.ContextMenu;\n");
				requiredImports.add("import android.view.ContextMenu.ContextMenuInfo;\n");
			} else if ("onTouchEvent".equals(event) || "onTrackballEvent".equals(event) || "onGenericMotionEvent".equals(event)) {
				requiredImports.add("import android.view.MotionEvent;\n");
			} else if ("onKeyDown".equals(event) || "onKeyUp".equals(event) || "onKeyLongPress".equals(event) ||
			"onKeyShortcut".equals(event) || "onKeyMultiple".equals(event)) {
				requiredImports.add("import android.view.KeyEvent;\n");
			} else if ("onSensorChanged".equals(event) || "onAccuracyChanged".equals(event)) {
				requiredImports.add("import android.hardware.Sensor;\n");
				requiredImports.add("import android.hardware.SensorEvent;\n");
				requiredImports.add("import android.hardware.SensorEventListener;\n");
				requiredImports.add("import android.hardware.SensorManager;\n");
			} else if ("onLocationChanged".equals(event) || "onStatusChanged".equals(event) ||
			"onProviderEnabled".equals(event) || "onProviderDisabled".equals(event)) {
				requiredImports.add("import android.location.Location;\n");
				requiredImports.add("import android.location.LocationListener;\n");
				requiredImports.add("import android.location.LocationManager;\n");
			} else if ("onCreateOptionsMenu".equals(event) || "onPrepareOptionsMenu".equals(event) ||
			"onOptionsItemSelected".equals(event) || "onOptionsMenuClosed".equals(event)) {
				requiredImports.add("import android.view.Menu;\n");
				requiredImports.add("import android.view.MenuItem;\n");
			}
			
			
			if (activityType != null && activityType.isFragment()) {
				if ("onCreateView".equals(event) || "onViewCreated".equals(event)) {
					
				} else if ("onAttach".equals(event) || "onDetach".equals(event)) {
					requiredImports.add("import android.content.Context;\n");
				} else if ("onCreateDialog".equals(event) && activityType.isDialogFragment()) {
					
				}
			}
		}
		
		for (String imp : requiredImports) {
			if (!javaCode.toString().contains(imp.trim())) {
				javaCode.append(imp);
			}
		}
		
		
		List<HashMap<String, String>> variables = ProjectLogicRepository.loadVariableLogic(activityName);
		for (HashMap<String, String> var : variables) {
			String type = var.get("varTypeName");
			if (type != null) {
				if (type.contains("ArrayList") && !javaCode.toString().contains("import java.util.ArrayList")) {
					javaCode.append("import java.util.ArrayList;\n");
				}
				if (type.contains("HashMap") && !javaCode.toString().contains("import java.util.HashMap")) {
					javaCode.append("import java.util.HashMap;\n");
				}
				if (type.contains("HashSet") && !javaCode.toString().contains("import java.util.HashSet")) {
					javaCode.append("import java.util.HashSet;\n");
				}
				if (type.contains("List") && !javaCode.toString().contains("import java.util.List")) {
					javaCode.append("import java.util.List;\n");
				}
				if (type.contains("Map") && !javaCode.toString().contains("import java.util.Map")) {
					javaCode.append("import java.util.Map;\n");
				}
				if (type.contains("Set") && !javaCode.toString().contains("import java.util.Set")) {
					javaCode.append("import java.util.Set;\n");
				}
			}
		}
		
		
		List<HashMap<String, String>> components = ProjectLogicRepository.loadComponentLogic(activityName);
		for (HashMap<String, String> comp : components) {
			String type = comp.get("componentName");
			if (type != null) {
				switch (type) {
					case "Intent":
					if (!javaCode.toString().contains("import android.content.Intent")) {
						javaCode.append("import android.content.Intent;\n");
					}
					break;
					
					case "Dialog":
					if (!javaCode.toString().contains("import android.app.Dialog")) {
						javaCode.append("import android.app.Dialog;\n");
					}
					break;
					
					case "ObjectAnimator":
					if (!javaCode.toString().contains("import android.animation.ObjectAnimator")) {
						javaCode.append("import android.animation.ObjectAnimator;\n");
					}
					if (!javaCode.toString().contains("import android.animation.Animator;")) {
						javaCode.append("import android.animation.Animator;\n");
					}
					break;
					
					case "Calendar":
					if (!javaCode.toString().contains("import java.util.Calendar")) {
						javaCode.append("import java.util.Calendar;\n");
					}
					break;
					
					case "Timer":
					if (!javaCode.toString().contains("import java.util.Timer")) {
						javaCode.append("import java.util.Timer;\n");
						javaCode.append("import java.util.TimerTask;\n");
					}
					break;
					
					case "Notification":
					if (!javaCode.toString().contains("import android.app.Notification")) {
						javaCode.append("import android.app.Notification;\n");
						javaCode.append("import android.app.NotificationChannel;\n");
						javaCode.append("import android.app.NotificationManager;\n");
					}
					break;
					
					case "SharedPreferences":
					if (!javaCode.toString().contains("import android.content.SharedPreferences")) {
						javaCode.append("import android.content.SharedPreferences;\n");
					}
					if (!javaCode.toString().contains("import android.content.Context")) {
						javaCode.append("import android.content.Context;\n");
					}
					break;
					
					case "AsyncTask":
					if (!javaCode.toString().contains("import android.os.AsyncTask")) {
						javaCode.append("import android.os.AsyncTask;\n");
					}
					break;
					
					case "Handler":
					if (!javaCode.toString().contains("import android.os.Handler")) {
						javaCode.append("import android.os.Handler;\n");
						javaCode.append("import android.os.Looper;\n");
					}
					break;
					
					case "Service":
					if (!javaCode.toString().contains("import android.app.Service")) {
						javaCode.append("import android.app.Service;\n");
					}
					break;
					
					case "BroadcastReceiver":
					if (!javaCode.toString().contains("import android.content.BroadcastReceiver")) {
						javaCode.append("import android.content.BroadcastReceiver;\n");
					}
					break;
					
					case "ContentProvider":
					if (!javaCode.toString().contains("import android.content.ContentProvider")) {
						javaCode.append("import android.content.ContentProvider;\n");
						javaCode.append("import android.content.ContentResolver;\n");
					}
					break;
					
					case "ViewModel":
					if (!javaCode.toString().contains("import androidx.lifecycle.ViewModel")) {
						javaCode.append("import androidx.lifecycle.ViewModel;\n");
						javaCode.append("import androidx.lifecycle.ViewModelProvider;\n");
					}
					break;
					
					case "LiveData":
					if (!javaCode.toString().contains("import androidx.lifecycle.LiveData")) {
						javaCode.append("import androidx.lifecycle.LiveData;\n");
						javaCode.append("import androidx.lifecycle.MutableLiveData;\n");
					}
					break;
					
					case "Room":
					if (!javaCode.toString().contains("import androidx.room.Room")) {
						javaCode.append("import androidx.room.Room;\n");
						javaCode.append("import androidx.room.Database;\n");
					}
					break;
					
					case "WorkManager":
					if (!javaCode.toString().contains("import androidx.work.WorkManager")) {
						javaCode.append("import androidx.work.WorkManager;\n");
					}
					break;
					
					case "MediaPlayer":
					if (!javaCode.toString().contains("import android.media.MediaPlayer")) {
						javaCode.append("import android.media.MediaPlayer;\n");
					}
					break;
					
					case "Camera":
					if (!javaCode.toString().contains("import android.provider.MediaStore")) {
						javaCode.append("import android.provider.MediaStore;\n");
					}
					if (isAndroidXEnabled(projectPath)
							&& !javaCode.toString().contains("import androidx.core.content.FileProvider")) {
						javaCode.append("import androidx.core.content.FileProvider;\n");
					}
					if (!javaCode.toString().contains("import android.net.Uri")) {
						javaCode.append("import android.net.Uri;\n");
					}
					if (!javaCode.toString().contains("import java.io.File")) {
						javaCode.append("import java.io.File;\n");
					}
					if (!javaCode.toString().contains("import android.Manifest")) {
						javaCode.append("import android.Manifest;\n");
					}
					if (!javaCode.toString().contains("import android.content.pm.PackageManager")) {
						javaCode.append("import android.content.pm.PackageManager;\n");
					}
					break;
					
					case "Camera2 API":
					if (!javaCode.toString().contains("import android.hardware.camera2")) {
						javaCode.append("import android.hardware.camera2.CameraManager;\n");
						javaCode.append("import android.hardware.camera2.CameraDevice;\n");
						javaCode.append("import android.hardware.camera2.CameraCaptureSession;\n");
						javaCode.append("import android.hardware.camera2.CaptureRequest;\n");
					}
					break;
					
					case "InterstitialAd":
					if (!javaCode.toString().contains("import com.google.android.gms.ads.InterstitialAd")) {
						javaCode.append("import com.google.android.gms.ads.InterstitialAd;\n");
						javaCode.append("import com.google.android.gms.ads.AdListener;\n");
						javaCode.append("import com.google.android.gms.ads.AdRequest;\n");
					}
					break;
					
					case "FirebaseDB":
					if (!javaCode.toString().contains("import com.google.firebase.database.DatabaseReference")) {
						javaCode.append("import com.google.firebase.database.DatabaseReference;\n");
						javaCode.append("import com.google.firebase.database.FirebaseDatabase;\n");
						javaCode.append("import com.google.firebase.database.ChildEventListener;\n");
						javaCode.append("import com.google.firebase.database.DataSnapshot;\n");
						javaCode.append("import com.google.firebase.database.DatabaseError;\n");
					}
					break;
					
					case "FirebaseAuth":
					if (!javaCode.toString().contains("import com.google.firebase.auth.FirebaseAuth")) {
						javaCode.append("import com.google.firebase.auth.FirebaseAuth;\n");
						javaCode.append("import com.google.firebase.auth.AuthResult;\n");
						javaCode.append("import com.google.android.gms.tasks.OnCompleteListener;\n");
						javaCode.append("import com.google.android.gms.tasks.Task;\n");
					}
					break;
					
					case "Gyroscope":
					if (!javaCode.toString().contains("import android.hardware.SensorEventListener")) {
						javaCode.append("import android.hardware.Sensor;\n");
						javaCode.append("import android.hardware.SensorEvent;\n");
						javaCode.append("import android.hardware.SensorEventListener;\n");
						javaCode.append("import android.hardware.SensorManager;\n");
					}
					break;
					
					case "FirebaseStorage":
					if (!javaCode.toString().contains("import com.google.firebase.storage.StorageReference")) {
						javaCode.append("import com.google.firebase.storage.FirebaseStorage;\n");
						javaCode.append("import com.google.firebase.storage.StorageReference;\n");
						javaCode.append("import com.google.firebase.storage.UploadTask;\n");
						javaCode.append("import com.google.firebase.storage.FileDownloadTask;\n");
						javaCode.append("import com.google.android.gms.tasks.OnSuccessListener;\n");
						javaCode.append("import com.google.android.gms.tasks.OnProgressListener;\n");
						javaCode.append("import com.google.android.gms.tasks.OnFailureListener;\n");
					}
					break;
					
					case "FilePicker":
					if (!javaCode.toString().contains("import android.content.Intent")) {
						javaCode.append("import android.content.Intent;\n");
					}
					if (!javaCode.toString().contains("import android.net.Uri")) {
						javaCode.append("import android.net.Uri;\n");
					}
					if (!javaCode.toString().contains("import android.content.ClipData")) {
						javaCode.append("import android.content.ClipData;\n");
					}
					if (!javaCode.toString().contains("import java.util.ArrayList")) {
						javaCode.append("import java.util.ArrayList;\n");
					}
					if (!javaCode.toString().contains("import android.Manifest")) {
						javaCode.append("import android.Manifest;\n");
					}
					if (!javaCode.toString().contains("import android.content.pm.PackageManager")) {
						javaCode.append("import android.content.pm.PackageManager;\n");
					}
					if (!javaCode.toString().contains("import android.os.Build")) {
						javaCode.append("import android.os.Build;\n");
					}
					if (!javaCode.toString().contains("import android.app.Activity")) {
						javaCode.append("import android.app.Activity;\n");
					}
					break;
					
					case "Storage Access Framework":
					if (!javaCode.toString().contains("import android.content.Intent")) {
						javaCode.append("import android.content.Intent;\n");
					}
					if (!javaCode.toString().contains("import android.net.Uri")) {
						javaCode.append("import android.net.Uri;\n");
					}
					if (!javaCode.toString().contains("import android.provider.DocumentsContract")) {
						javaCode.append("import android.provider.DocumentsContract;\n");
					}
					break;
					
					case "LocationManager":
					if (!javaCode.toString().contains("import android.location.LocationManager")) {
						javaCode.append("import android.location.LocationManager;\n");
						javaCode.append("import android.location.LocationListener;\n");
						javaCode.append("import android.location.Location;\n");
					}
					break;
					
					case "SensorManager":
					if (!javaCode.toString().contains("import android.hardware.SensorManager")) {
						javaCode.append("import android.hardware.SensorManager;\n");
						javaCode.append("import android.hardware.Sensor;\n");
						javaCode.append("import android.hardware.SensorEvent;\n");
						javaCode.append("import android.hardware.SensorEventListener;\n");
					}
					break;
					
					case "BluetoothAdapter":
					if (!javaCode.toString().contains("import android.bluetooth.BluetoothAdapter")) {
						javaCode.append("import android.bluetooth.BluetoothAdapter;\n");
						javaCode.append("import android.bluetooth.BluetoothDevice;\n");
					}
					break;
					
					case "AlarmManager":
					if (!javaCode.toString().contains("import android.app.AlarmManager")) {
						javaCode.append("import android.app.AlarmManager;\n");
					}
					break;
					
					case "JobScheduler":
					if (!javaCode.toString().contains("import android.app.job.JobScheduler")) {
						javaCode.append("import android.app.job.JobScheduler;\n");
						javaCode.append("import android.app.job.JobInfo;\n");
						javaCode.append("import android.app.job.JobService;\n");
					}
					break;
					
					case "ConnectivityManager":
					if (!javaCode.toString().contains("import android.net.ConnectivityManager")) {
						javaCode.append("import android.net.ConnectivityManager;\n");
						javaCode.append("import android.net.NetworkInfo;\n");
					}
					break;
					
					case "PowerManager":
					if (!javaCode.toString().contains("import android.os.PowerManager")) {
						javaCode.append("import android.os.PowerManager;\n");
					}
					break;
					
					case "ClipboardManager":
					if (!javaCode.toString().contains("import android.content.ClipboardManager")) {
						javaCode.append("import android.content.ClipboardManager;\n");
						javaCode.append("import android.content.ClipData;\n");
					}
					break;
					
					case "PackageManager":
					if (!javaCode.toString().contains("import android.content.pm.PackageManager")) {
						javaCode.append("import android.content.pm.PackageManager;\n");
					}
					break;
					
					case "DownloadManager":
					if (!javaCode.toString().contains("import android.app.DownloadManager")) {
						javaCode.append("import android.app.DownloadManager;\n");
					}
					break;
					
					case "Vibrator":
					if (!javaCode.toString().contains("import android.os.Vibrator")) {
						javaCode.append("import android.os.Vibrator;\n");
					}
					break;
					
					case "AudioManager":
					if (!javaCode.toString().contains("import android.media.AudioManager")) {
						javaCode.append("import android.media.AudioManager;\n");
					}
					break;
					
					case "AccountManager":
					if (!javaCode.toString().contains("import android.accounts.AccountManager")) {
						javaCode.append("import android.accounts.AccountManager;\n");
					}
					break;
					
					case "TelephonyManager":
					if (!javaCode.toString().contains("import android.telephony.TelephonyManager")) {
						javaCode.append("import android.telephony.TelephonyManager;\n");
					}
					break;
					
					case "InputMethodManager":
					if (!javaCode.toString().contains("import android.view.inputmethod.InputMethodManager")) {
						javaCode.append("import android.view.inputmethod.InputMethodManager;\n");
					}
					break;
					
					case "WindowManager":
					if (!javaCode.toString().contains("import android.view.WindowManager")) {
						javaCode.append("import android.view.WindowManager;\n");
					}
					break;
					
					case "ActivityManager":
					if (!javaCode.toString().contains("import android.app.ActivityManager")) {
						javaCode.append("import android.app.ActivityManager;\n");
					}
					break;
					
					case "AppOpsManager":
					if (!javaCode.toString().contains("import android.app.AppOpsManager")) {
						javaCode.append("import android.app.AppOpsManager;\n");
					}
					break;
					
					case "AlertDialog.Builder":
					case "AlertDialog":
					if (!javaCode.toString().contains("import android.app.AlertDialog")) {
						javaCode.append("import android.app.AlertDialog;\n");
					}
					if (!javaCode.toString().contains("import android.content.DialogInterface")) {
						javaCode.append("import android.content.DialogInterface;\n");
					}
					break;
					
					case "BottomSheetDialog":
					if (!javaCode.toString().contains("import com.google.android.material.bottomsheet.BottomSheetDialog")) {
						javaCode.append("import com.google.android.material.bottomsheet.BottomSheetDialog;\n");
					}
					break;
					
					case "ProgressDialog":
					if (!javaCode.toString().contains("import android.app.ProgressDialog")) {
						javaCode.append("import android.app.ProgressDialog;\n");
					}
					break;
					
					case "DatePickerDialog":
					if (!javaCode.toString().contains("import android.app.DatePickerDialog")) {
						javaCode.append("import android.app.DatePickerDialog;\n");
					}
					if (!javaCode.toString().contains("import android.widget.DatePicker")) {
						javaCode.append("import android.widget.DatePicker;\n");
					}
					break;
					
					case "TimePickerDialog":
					if (!javaCode.toString().contains("import android.app.TimePickerDialog")) {
						javaCode.append("import android.app.TimePickerDialog;\n");
					}
					if (!javaCode.toString().contains("import android.widget.TimePicker")) {
						javaCode.append("import android.widget.TimePicker;\n");
					}
					break;
					
					case "ArrayAdapter":
					if (!javaCode.toString().contains("import android.widget.ArrayAdapter")) {
						javaCode.append("import android.widget.ArrayAdapter;\n");
					}
					break;
					case "FragmentAdapter":
					if (!javaCode.toString().contains("import androidx.fragment.app.FragmentManager")) {
						javaCode.append("import androidx.fragment.app.FragmentManager;\n");
					}
					if (!javaCode.toString().contains("import androidx.fragment.app.Fragment")) {
						javaCode.append("import androidx.fragment.app.Fragment;\n");
					}
					if (!javaCode.toString().contains("import androidx.fragment.app.FragmentStatePagerAdapter")) {
						javaCode.append("import androidx.fragment.app.FragmentStatePagerAdapter;\n");
					}
					break;    
				}
			}
			
		}
		
		Map<String, String> widgets = LayoutLoader.extractWidgetsFromLayout(activityName, projectPath);
		
		if (widgets != null && !widgets.isEmpty()) {
			for (Map.Entry<String, String> widget : widgets.entrySet()) {
				String widgetType = widget.getValue();
				if (widgetType == null) continue;
				
				switch (widgetType) {
					case "MaterialButton":
					addImportIfNotExists(javaCode, "import com.google.android.material.button.MaterialButton;\n");
					break;
					
					case "MaterialCardView":
					addImportIfNotExists(javaCode, "import com.google.android.material.card.MaterialCardView;\n");
					break;
					
					case "MaterialTextView":
					addImportIfNotExists(javaCode, "import com.google.android.material.textview.MaterialTextView;\n");
					break;
					
					case "MaterialEditText":
					addImportIfNotExists(javaCode, "import com.google.android.material.textfield.MaterialEditText;\n");
					break;
					
					case "TextInputLayout":
					addImportIfNotExists(javaCode, "import com.google.android.material.textfield.TextInputLayout;\n");
					break;
					
					case "TextInputEditText":
					addImportIfNotExists(javaCode, "import com.google.android.material.textfield.TextInputEditText;\n");
					break;
					
					case "FloatingActionButton":
					addImportIfNotExists(javaCode, "import com.google.android.material.floatingactionbutton.FloatingActionButton;\n");
					break;
					
					case "TabLayout":
					addImportIfNotExists(javaCode, "import com.google.android.material.tabs.TabLayout;\n");
					break;
					
					case "BottomNavigationView":
					addImportIfNotExists(javaCode, "import com.google.android.material.bottomnavigation.BottomNavigationView;\n");
					break;
					
					case "NavigationView":
					addImportIfNotExists(javaCode, "import com.google.android.material.navigation.NavigationView;\n");
					break;
					
					case "AppBarLayout":
					addImportIfNotExists(javaCode, "import com.google.android.material.appbar.AppBarLayout;\n");
					break;
					
					case "CollapsingToolbarLayout":
					addImportIfNotExists(javaCode, "import com.google.android.material.appbar.CollapsingToolbarLayout;\n");
					break;
					
					case "Chip":
					addImportIfNotExists(javaCode, "import com.google.android.material.chip.Chip;\n");
					break;
					
					case "ChipGroup":
					addImportIfNotExists(javaCode, "import com.google.android.material.chip.ChipGroup;\n");
					break;
					
					case "BottomSheetDialog":
					addImportIfNotExists(javaCode, "import com.google.android.material.bottomsheet.BottomSheetDialog;\n");
					break;
					
					case "BottomSheetDialogFragment":
					addImportIfNotExists(javaCode, "import com.google.android.material.bottomsheet.BottomSheetDialogFragment;\n");
					break;
					
					case "RecyclerView":
					addImportIfNotExists(javaCode, "import androidx.recyclerview.widget.RecyclerView;\n");
					addImportIfNotExists(javaCode, "import androidx.recyclerview.widget.LinearLayoutManager;\n");
					break;
					
					case "ViewPager2":
					addImportIfNotExists(javaCode, "import androidx.viewpager2.widget.ViewPager2;\n");
					break;
					
					case "ConstraintLayout":
					addImportIfNotExists(javaCode, "import androidx.constraintlayout.widget.ConstraintLayout;\n");
					break;
					
					case "CoordinatorLayout":
					addImportIfNotExists(javaCode, "import androidx.coordinatorlayout.widget.CoordinatorLayout;\n");
					break;
					
					case "Toolbar":
					addImportIfNotExists(javaCode, "import androidx.appcompat.widget.Toolbar;\n");
					break;
					
					case "CardView":
					addImportIfNotExists(javaCode, "import androidx.cardview.widget.CardView;\n");
					break;
					
					case "SwipeRefreshLayout":
					addImportIfNotExists(javaCode, "import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;\n");
					break;
					
					case "NestedScrollView":
					addImportIfNotExists(javaCode, "import androidx.core.widget.NestedScrollView;\n");
					break;
				}
			}
		}
		
		boolean needsMaterial = false;
		try {
			String layoutPath = "/storage/emulated/0/.blacklogics/" + extractProjectIdFromPath(projectPath);
			Map<String, String> widgets1 = LayoutLoader.extractWidgetsFromLayout(activityName, layoutPath);
			
			if (widgets1 != null) {
				for (Map.Entry<String, String> widget : widgets1.entrySet()) {
					String widgetType = widget.getValue();
					if (widgetType != null && (
					widgetType.startsWith("Material") ||
					widgetType.equals("BottomSheetDialog") ||
					widgetType.equals("BottomNavigationView") ||
					widgetType.equals("NavigationView") ||
					widgetType.equals("FloatingActionButton") ||
					widgetType.equals("TabLayout") ||
					widgetType.equals("TextInputLayout") ||
					widgetType.equals("TextInputEditText") ||
					widgetType.equals("Chip") ||
					widgetType.equals("ChipGroup") ||
					widgetType.equals("MaterialButton") ||
					widgetType.equals("MaterialCardView") ||
					widgetType.equals("AppBarLayout") ||
					widgetType.equals("CollapsingToolbarLayout") ||
					widgetType.equals("CoordinatorLayout") ||
					widgetType.equals("ConstraintLayout") ||
					widgetType.equals("RecyclerView")
					)) {
						needsMaterial = true;
						break;
					}
				}
			}
			
			if (components != null) {
				for (HashMap<String, String> comp : components) {
					String type = comp.get("componentName");
					if (type != null && type.equals("BottomSheetDialog")) {
						needsMaterial = true;
						break;
					}
				}
			}
			
			if (activityType == ActivityType.BOTTOM_SHEET_DIALOG_FRAGMENT) {
				needsMaterial = true;
			}
			
		} catch (Exception e) {
		}
		
		if (needsMaterial) {
			if (!javaCode.toString().contains("import com.google.android.material")) {
				javaCode.append("import com.google.android.material.appbar.AppBarLayout;\n");
				javaCode.append("import com.google.android.material.appbar.CollapsingToolbarLayout;\n");
				javaCode.append("import com.google.android.material.bottomsheet.BottomSheetDialog;\n");
				javaCode.append("import com.google.android.material.button.MaterialButton;\n");
				javaCode.append("import com.google.android.material.card.MaterialCardView;\n");
				javaCode.append("import com.google.android.material.chip.Chip;\n");
				javaCode.append("import com.google.android.material.chip.ChipGroup;\n");
				javaCode.append("import com.google.android.material.floatingactionbutton.FloatingActionButton;\n");
				javaCode.append("import com.google.android.material.navigation.NavigationView;\n");
				javaCode.append("import com.google.android.material.tabs.TabLayout;\n");
				javaCode.append("import com.google.android.material.textfield.TextInputEditText;\n");
				javaCode.append("import com.google.android.material.textfield.TextInputLayout;\n");
				javaCode.append("import com.google.android.material.bottomnavigation.BottomNavigationView;\n");
				javaCode.append("import androidx.coordinatorlayout.widget.CoordinatorLayout;\n");
				javaCode.append("import androidx.constraintlayout.widget.ConstraintLayout;\n");
				javaCode.append("import androidx.appcompat.widget.Toolbar;\n");
				javaCode.append("import androidx.recyclerview.widget.RecyclerView;\n");
				javaCode.append("import androidx.recyclerview.widget.LinearLayoutManager;\n");
			}
		}
		
		if (activityType != null && activityType.isFragment()) {
			if (!javaCode.toString().contains("import android.content.Context")) {
				javaCode.append("import android.content.Context;\n");
			}
		}
		
		javaCode.append("\n");
	}
	
	public static void addAndroidXImports(StringBuilder javaCode, com.nexusteam.blacklogics.development.Complex complex) {
		
		boolean needsAppCompat = false;
		
		if (complex != null && complex.getAndroidXEnable()) {
			
			if (!javaCode.toString().contains("import androidx.appcompat.app.AppCompatActivity")) {
				javaCode.append("import androidx.appcompat.app.AppCompatActivity;\n");
			}
			if (!javaCode.toString().contains("import androidx.annotation.NonNull")) {
				javaCode.append("import androidx.annotation.NonNull;\n");
			}
			if (!javaCode.toString().contains("import androidx.annotation.Nullable")) {
				javaCode.append("import androidx.annotation.Nullable;\n");
			}
			if (!javaCode.toString().contains("import androidx.core.content.ContextCompat")) {
				javaCode.append("import androidx.core.content.ContextCompat;\n");
			}
			if (!javaCode.toString().contains("import androidx.core.app.ActivityCompat")) {
				javaCode.append("import androidx.core.app.ActivityCompat;\n");
			}
			
			
			if (!javaCode.toString().contains("import androidx.fragment.app.Fragment")) {
				
			}
		} else {
			
			if (!javaCode.toString().contains("import android.app.Activity")) {
				javaCode.append("import android.app.Activity;\n");
			}
		}
	}
	
	private static List<String> getLifecycleEventsForActivity(String activityName, String projectPath) {
		List<String> events = new ArrayList<String>();
		try {
			String path = projectPath + "/events/lifecycle_events.json";
			File file = new File(path);
			if (file.exists()) {
				String json = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT), StandardCharsets.UTF_8);
				JSONObject allEvents = new JSONObject(decoded);
				JSONArray activityEvents = allEvents.optJSONArray(activityName);
				if (activityEvents != null) {
					for (int i = 0; i < activityEvents.length(); i++) {
						String event = activityEvents.optString(i);
						events.add(event);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return events;
	}
	
	private static void addImportIfNotExists(StringBuilder javaCode, String importStatement) {
		if (!javaCode.toString().contains(importStatement.trim())) {
			javaCode.append(importStatement);
		}
	}
	
	private static String extractProjectIdFromPath(String projectPath) {
		if (projectPath == null || projectPath.isEmpty()) {
			return null;
		}
		String[] parts = projectPath.split("/");
		for (int i = 0; i < parts.length; i++) {
			if ("data".equals(parts[i]) && i + 1 < parts.length) {
				return parts[i + 1];
			}
		}
		return null;
	}
	
	private static boolean isFragment(String activityName) {
		ActivityTypeRegistry registry = ActivityTypeRegistry.getInstance();
		com.nexusteam.blacklogics.generator.source.model.ActivityConfig config = registry.getActivityConfig(activityName);
		return (config != null && config.getType() != null && config.getType().isFragment());
	}
	
	private static boolean isDialogFragment(String activityName) {
		ActivityTypeRegistry registry = ActivityTypeRegistry.getInstance();
		com.nexusteam.blacklogics.generator.source.model.ActivityConfig config = registry.getActivityConfig(activityName);
		return (config != null && config.getType() != null && config.getType().isDialogFragment());
	}
}
