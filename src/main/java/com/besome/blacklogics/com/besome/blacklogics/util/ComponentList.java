package com.besome.blacklogics.util;

import com.besome.blacklogics.model.ComponentData;
import java.util.ArrayList;

public class ComponentList {
	public static ArrayList<ComponentData> getComponents() {
		ArrayList<ComponentData> components = new ArrayList<>();
		components.add(new ComponentData("Intent", "Used to switch between activities or start services.", "https://developer.android.com/reference/android/content/Intent"));
		components.add(new ComponentData("Dialog", "Displays a dialog with customizable content.", "https://developer.android.com/guide/topics/ui/dialogs"));
		components.add(new ComponentData("ObjectAnimator", "Animates object properties over time.", "https://developer.android.com/reference/android/animation/ObjectAnimator"));
        components.add(new ComponentData("Calendar", "Calendar is used to calculate time and date", "https://developer.android.com/reference/java/util/Calendar"));
		// components.add(new ComponentData("Toast", "Shows a quick, temporary message.", "https://developer.android.com/guide/topics/ui/notifiers/toasts"));
		components.add(new ComponentData("Notification", "Displays a notification in the status bar.", "https://developer.android.com/guide/topics/ui/notifiers/notifications"));
		components.add(new ComponentData("SharedPreferences", "Stores key-value pairs persistently.", "https://developer.android.com/reference/android/content/SharedPreferences"));
		components.add(new ComponentData("AsyncTask", "Performs background operations and publishes results on the UI thread.", "https://developer.android.com/reference/android/os/AsyncTask"));
		components.add(new ComponentData("Handler", "Schedules messages and runnables to execute on a thread.", "https://developer.android.com/reference/android/os/Handler"));
		components.add(new ComponentData("Service", "Runs background operations without a UI.", "https://developer.android.com/guide/components/services"));
		components.add(new ComponentData("BroadcastReceiver", "Receives system-wide broadcast events.", "https://developer.android.com/reference/android/content/BroadcastReceiver"));
		components.add(new ComponentData("ContentProvider", "Manages access to a structured set of data.", "https://developer.android.com/guide/topics/providers/content-providers"));
		/// components.add(new ComponentData("Fragment", "Represents a portion of a UI or behavior in an activity.", "https://developer.android.com/guide/fragments"));
		components.add(new ComponentData("ViewModel", "Manages UI-related data in a lifecycle-conscious way.", "https://developer.android.com/topic/libraries/architecture/viewmodel"));
		components.add(new ComponentData("LiveData", "Holds data that can be observed for changes.", "https://developer.android.com/topic/libraries/architecture/livedata"));
		components.add(new ComponentData("Room", "Provides an abstraction layer over SQLite.", "https://developer.android.com/jetpack/androidx/releases/room"));
		components.add(new ComponentData("WorkManager", "Schedules deferrable, asynchronous tasks.", "https://developer.android.com/topic/libraries/architecture/workmanager"));
		//components.add(new ComponentData("RecyclerView", "Displays large sets of data efficiently.", "https://developer.android.com/guide/topics/ui/layout/recyclerview"));
		//  components.add(new ComponentData("ViewPager", "Allows swiping between pages of data.", "https://developer.android.com/reference/androidx/viewpager/widget/ViewPager"));
		components.add(new ComponentData("MediaPlayer", "Plays audio and video files.", "https://developer.android.com/reference/android/media/MediaPlayer"));
		components.add(new ComponentData("Camera", "Captures photos and videos.", "https://developer.android.com/guide/topics/media/camera"));
		components.add(new ComponentData("LocationManager", "Provides access to location services.", "https://developer.android.com/reference/android/location/LocationManager"));
		components.add(new ComponentData("SensorManager", "Accesses device sensors like accelerometer.", "https://developer.android.com/reference/android/hardware/SensorManager"));
		components.add(new ComponentData("BluetoothAdapter", "Manages Bluetooth functionality.", "https://developer.android.com/reference/android/bluetooth/BluetoothAdapter"));
		components.add(new ComponentData("AlarmManager", "Schedules your application to run at a specific time.", "https://developer.android.com/reference/android/app/AlarmManager"));
		components.add(new ComponentData("JobScheduler", "Manages scheduled tasks for background work.", "https://developer.android.com/reference/android/app/job/JobScheduler"));
		components.add(new ComponentData("ConnectivityManager", "Manages network connections.", "https://developer.android.com/reference/android/net/ConnectivityManager"));
		components.add(new ComponentData("PowerManager", "Controls power-related features like wake locks.", "https://developer.android.com/reference/android/os/PowerManager"));
		components.add(new ComponentData("ClipboardManager", "Manages clipboard operations.", "https://developer.android.com/reference/android/content/ClipboardManager"));
		components.add(new ComponentData("PackageManager", "Retrieves information about installed apps and system features.", "https://developer.android.com/reference/android/content/pm/PackageManager"));
		components.add(new ComponentData("DownloadManager", "Handles long-running HTTP downloads.", "https://developer.android.com/reference/android/app/DownloadManager"));
		components.add(new ComponentData("Vibrator", "Accesses the device's vibration service.", "https://developer.android.com/reference/android/os/Vibrator"));
		components.add(new ComponentData("AudioManager", "Manages audio settings and routing.", "https://developer.android.com/reference/android/media/AudioManager"));
		components.add(new ComponentData("AccountManager", "Manages user accounts and credentials.", "https://developer.android.com/reference/android/accounts/AccountManager"));
		components.add(new ComponentData("TelephonyManager", "Provides access to information about the telephony services.", "https://developer.android.com/reference/android/telephony/TelephonyManager"));
		components.add(new ComponentData("InputMethodManager", "Manages input methods like the soft keyboard.", "https://developer.android.com/reference/android/view/inputmethod/InputMethodManager"));
		components.add(new ComponentData("WindowManager", "Manages windows and their layout.", "https://developer.android.com/reference/android/view/WindowManager"));
		components.add(new ComponentData("ActivityManager", "Provides information about running app processes.", "https://developer.android.com/reference/android/app/ActivityManager"));
		components.add(new ComponentData("AppOpsManager", "Allows access to app operations and permission enforcement.", "https://developer.android.com/reference/android/app/AppOpsManager"));
		components.add(new ComponentData("AlertDialog.Builder", "Used to build and display alert dialogs.", "https://developer.android.com/reference/android/app/AlertDialog.Builder"));
	//	components.add(new ComponentData("Toast", "Displays a small message at the bottom of the screen.", "https://developer.android.com/guide/topics/ui/notifiers/toasts"));
	//	components.add(new ComponentData("Snackbar", "Provides lightweight feedback about an operation.", "https://developer.android.com/reference/com/google/android/material/snackbar/Snackbar"));
		components.add(new ComponentData("BottomSheetDialog", "Displays content in a bottom sheet.", "https://developer.android.com/reference/com/google/android/material/bottomsheet/BottomSheetDialog"));
		components.add(new ComponentData("ProgressDialog", "Displays a progress wheel or bar (deprecated, but still used in legacy apps).", "https://developer.android.com/reference/android/app/ProgressDialog"));
		components.add(new ComponentData("DatePickerDialog", "Dialog for picking a date.", "https://developer.android.com/reference/android/app/DatePickerDialog"));
		components.add(new ComponentData("TimePickerDialog", "Dialog for picking a time.", "https://developer.android.com/reference/android/app/TimePickerDialog"));
		components.add(new ComponentData("ArrayAdapter", "Bridges data and UI for items in a list.", "https://developer.android.com/reference/android/widget/ArrayAdapter"));
		components.add(new ComponentData("AlertDialog", "A modal dialog with multiple actions.", "https://developer.android.com/reference/android/app/AlertDialog"));
		components.add(new ComponentData("DialogFragment", "Displays a Dialog inside a Fragment.", "https://developer.android.com/reference/android/app/DialogFragment"));
		
		return components;
	}
}
