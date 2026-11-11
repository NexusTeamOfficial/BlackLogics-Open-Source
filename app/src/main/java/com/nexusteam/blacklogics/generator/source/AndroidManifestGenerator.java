package com.nexusteam.blacklogics.generator.source;

import android.content.Context;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Base64;
import com.nexusteam.internal.BlockStorage;
import com.nexusteam.internal.model.ComponentData;
import com.nexusteam.internal.beans.ComponentBean;

import com.apk.builder.FileUtil;
import com.nexusteam.blacklogics.manager.ProjectSettingsManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AndroidManifestGenerator {
	
	private String packageName;
	private String appName;
	private String appIcon;
	private String themeColor;
	private String applicationClassName = ".BlackApplication";
	private boolean useAndroidX;
	
	private String launcherActivity;
	private String launcherActivityPath;
	
	private List<Map<String, Object>> activities;
	private List<Map<String, Object>> services;
	private List<Map<String, Object>> receivers;
	private List<String> permissions;
	private Map<String, String> attributes;
	
	private String customAppComponentsXml;
	
	private boolean needsFileProvider = false;
	private Context loadedContext = null;
	
	private String sc_id;
	
	public AndroidManifestGenerator() {
		activities = new ArrayList<Map<String, Object>>();
		services = new ArrayList<Map<String, Object>>();
		receivers = new ArrayList<Map<String, Object>>();
		permissions = new ArrayList<String>();
		attributes = new HashMap<String, String>();
		launcherActivityPath = null;
	}
	
	
	public AndroidManifestGenerator setLauncherActivityPath(String path) {
		this.launcherActivityPath = path;
		return this;
	}
	
	public String getLauncherActivityFromPath() {
		if (launcherActivityPath == null) {
			return null;
		}
		
		try {
			File file = new File(launcherActivityPath);
			
			if (!file.exists()) {
				return null;
			}
			
			String content = new String(
			Files.readAllBytes(file.toPath()),
			StandardCharsets.UTF_8
			);
			
			if (content == null || content.trim().isEmpty()) {
				return null;
			}
			
			return content.trim();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public AndroidManifestGenerator setPackageName(String name) {
		this.packageName = name;
		return this;
	}
	
	public AndroidManifestGenerator setAppName(String name) {
		this.appName = name;
		return this;
	}
	
	public AndroidManifestGenerator setAppIcon(String icon) {
		this.appIcon = icon;
		return this;
	}
	
	public AndroidManifestGenerator setThemeColor(String color) {
		this.themeColor = color;
		return this;
	}
	
	public AndroidManifestGenerator setApplicationClassName(String name) {
		if (name != null && !name.trim().isEmpty()) {
			this.applicationClassName = name.trim();
		}
		return this;
	}
	
	public AndroidManifestGenerator setAndroidX(boolean enable) {
		this.useAndroidX = enable;
		return this;
	}
	
	public boolean isUseAndroidX() {
		return this.useAndroidX;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	
	public AndroidManifestGenerator setLauncherActivity(String name) {
		if (name == null || name.trim().isEmpty()) {
			return this;
		}
		
		String resolved = resolveActivityName(name.trim());
		
		boolean found = false;
		
		for (Map<String, Object> act : activities) {
			if (resolved.equals(act.get("name"))) {
				
				found = true;
				
				Map<String, String> attrs =
				(Map<String, String>) act.get("attrs");
				
				if (attrs == null) {
					attrs = new HashMap<String, String>();
					act.put("attrs", attrs);
				}
				
				attrs.put("exported", "true");
				attrs.put(
				"configChanges",
				"orientation|keyboardHidden|screenSize"
				);
				attrs.put(
				"windowSoftInputMode",
				"adjustResize"
				);
				
				break;
			}
		}
		
		if (found) {
			launcherActivity = resolved;
		}
		
		return this;
	}
	
	public String getLauncherActivity() {
		return launcherActivity;
	}
	
	private String resolveActivityName(String raw) {
		
		if (raw == null || raw.trim().isEmpty()) {
			return ".MainActivity";
		}
		
		raw = raw.trim();
		
		if (raw.startsWith(".")) {
			return raw;
		}
		
		if (raw.contains(".")) {
			return raw;
		}
		
		StringBuilder sb = new StringBuilder();
		boolean capitalizeNext = true;
		
		for (char c : raw.toCharArray()) {
			
			if (c == '_') {
				capitalizeNext = true;
				
			} else if (capitalizeNext) {
				sb.append(Character.toUpperCase(c));
				capitalizeNext = false;
				
			} else {
				sb.append(c);
			}
		}
		
		String camel = sb.toString();
		
		if (!camel.endsWith("Activity")) {
			camel = camel + "Activity";
		}
		
		return "." + camel;
	}
	
	
	public AndroidManifestGenerator addActivity(
	String name,
	Map<String, String> attrs
	) {
		
		Map<String, Object> existing = find(activities, name);
		
		if (existing != null) {
			existing.put(
			"attrs",
			attrs != null
			? attrs
			: new HashMap<String, String>()
			);
			return this;
		}
		
		Map<String, Object> map =
		new HashMap<String, Object>();
		
		map.put("name", name);
		
		map.put(
		"attrs",
		attrs != null
		? attrs
		: new HashMap<String, String>()
		);
		
		activities.add(map);
		
		return this;
	}
	
	public AndroidManifestGenerator removeActivity(String name) {
		if (name == null || name.trim().isEmpty()) {
			return this;
		}
		name = name.trim();
		String alt = name.startsWith(".") ? name.substring(1) : "." + name;
		for (int i = activities.size() - 1; i >= 0; i--) {
			Object n = activities.get(i).get("name");
			if (n == null) continue;
			String sn = String.valueOf(n);
			if (name.equals(sn) || alt.equals(sn)) {
				activities.remove(i);
			}
		}
		if (launcherActivity != null
		&& (name.equals(launcherActivity) || alt.equals(launcherActivity))) {
			launcherActivity = null;
		}
		return this;
	}
	
	public AndroidManifestGenerator addService(
	String name,
	Map<String, String> attrs
	) {
		
		if (!exists(services, name)) {
			
			Map<String, Object> map =
			new HashMap<String, Object>();
			
			map.put("name", name);
			
			map.put(
			"attrs",
			attrs != null
			? attrs
			: new HashMap<String, String>()
			);
			
			services.add(map);
		}
		
		return this;
	}
	
	public AndroidManifestGenerator addReceiver(
	String name,
	Map<String, String> attrs
	) {
		
		if (!exists(receivers, name)) {
			
			Map<String, Object> map =
			new HashMap<String, Object>();
			
			map.put("name", name);
			
			map.put(
			"attrs",
			attrs != null
			? attrs
			: new HashMap<String, String>()
			);
			
			receivers.add(map);
		}
		
		return this;
	}
	
	public AndroidManifestGenerator addPermission(String name) {
		
		if (name != null && !name.trim().isEmpty()) {
			
			name = name.trim();
			
			if (!permissions.contains(name)) {
				permissions.add(name);
			}
		}
		
		return this;
	}
	
	public AndroidManifestGenerator setAttribute(
	String key,
	String value
	) {
		
		if (key != null && value != null) {
			attributes.put(key, value);
		}
		
		return this;
	}
	
	
	public AndroidManifestGenerator autoRegisterActivities(
	String sc_id
	) {
		
		try {
			
			Set<String> existingSimple =
			new HashSet<String>();
			
			for (Map<String, Object> activity : activities) {
				Object name = activity.get("name");
				if (name != null) {
					String s = simpleComponentName(String.valueOf(name));
					if (s.length() > 0) existingSimple.add(s);
				}
			}
			
			List<String> list = new ArrayList<String>();
			
			collectActivityNamesFromJsonFile(
			new File(
			FileUtil.getExternalStorageDir()
			+ "/.blacklogics/data/"
			+ sc_id
			+ "/java"
			),
			list
			);
			
			File javaDir = new File(
			FileUtil.getExternalStorageDir()
			+ "/.blacklogics/data/"
			+ sc_id
			+ "/files/java"
			);
			if (javaDir.exists() && javaDir.isDirectory()) {
				File[] kids = javaDir.listFiles();
				if (kids != null) {
					for (File f : kids) {
						if (f == null || !f.isFile()) continue;
						String n = f.getName();
						if (n.endsWith(".java")) {
							list.add(n.substring(0, n.length() - 5));
						}
					}
				}
			}
			
			collectActivityNamesFromActivitiesConfig(sc_id, list);
			
			for (String fullName : list) {
				
				if (fullName == null) {
					continue;
				}
				
				fullName = fullName.trim();
				
				if (fullName.isEmpty()) {
					continue;
				}
				
				String className;
				
				int lastDot = fullName.lastIndexOf('.');
				
				if (lastDot >= 0) {
					className =
					fullName.substring(lastDot + 1);
				} else {
					className = fullName;
				}
				
				if (className.isEmpty()) {
					continue;
				}
				
				
				String manifestName =
				"." + className;
				
				if (existingSimple.contains(className)
				|| find(activities, manifestName) != null) {
					continue;
				}
				
				Map<String, String> attrs =
				new HashMap<String, String>();
				
				attrs.put("exported", "true");
				attrs.put(
				"configChanges",
				"orientation|keyboardHidden|screenSize"
				);
				attrs.put(
				"windowSoftInputMode",
				"adjustResize"
				);
				
				Map<String, Object> activity =
				new HashMap<String, Object>();
				
				activity.put(
				"name",
				manifestName
				);
				
				activity.put(
				"attrs",
				attrs
				);
				
				activities.add(activity);
				existingSimple.add(className);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	private void collectActivityNamesFromJsonFile(File file, List<String> out) {
		try {
			if (file == null || !file.exists() || !file.isFile()) return;
			String json = new String(
			Files.readAllBytes(file.toPath()),
			StandardCharsets.UTF_8
			);
			if (json.trim().isEmpty()) return;
			List<String> list = new Gson().fromJson(
			json,
			new TypeToken<List<String>>() {}.getType()
			);
			if (list == null) return;
			for (String s : list) {
				if (s != null && !s.trim().isEmpty()) out.add(s.trim());
			}
		} catch (Exception ignored) {}
	}
	
	@SuppressWarnings("unchecked")
	private void collectActivityNamesFromActivitiesConfig(String sc_id, List<String> out) {
		try {
			File logic = new File(
			FileUtil.getExternalStorageDir()
			+ "/.blacklogics/data/"
			+ sc_id
			+ "/logic"
			);
			if (!logic.exists() || !logic.isFile()) return;
			String raw = new String(
			Files.readAllBytes(logic.toPath()),
			StandardCharsets.UTF_8
			);
			if (raw.trim().isEmpty()) return;
			JSONObject root = new JSONObject(raw);
			if (!root.has("activities_config")) return;
			String encoded = root.optString("activities_config", "");
			if (encoded == null || encoded.trim().isEmpty()) return;
			String decoded = new String(
			Base64.getDecoder().decode(encoded.trim()),
			StandardCharsets.UTF_8
			);
			Map<String, Object> map = new Gson().fromJson(
			decoded,
			new TypeToken<Map<String, Object>>() {}.getType()
			);
			if (map == null) return;
			for (String key : map.keySet()) {
				if (key == null || key.trim().isEmpty()) continue;
				String k = key.trim();
				if (!k.endsWith("Activity") && !k.equalsIgnoreCase("Main")
				&& !k.equalsIgnoreCase("Debug")) {
					if (k.equalsIgnoreCase("Main")) {
						out.add("MainActivity");
					} else if (Character.isUpperCase(k.charAt(0))) {
						out.add(k.endsWith("Activity") ? k : k + "Activity");
					} else {
						out.add(k);
					}
				} else {
					out.add(k);
				}
			}
		} catch (Exception ignored) {}
	}
	
	
	private static final String[] PERM_STORAGE = {
		"android.permission.READ_EXTERNAL_STORAGE",
		"android.permission.WRITE_EXTERNAL_STORAGE",
		"android.permission.READ_MEDIA_IMAGES",
		"android.permission.READ_MEDIA_VIDEO",
		"android.permission.READ_MEDIA_AUDIO"
	};
	
	private static final String[] PERM_NET = {
		"android.permission.INTERNET",
		"android.permission.ACCESS_NETWORK_STATE"
	};
	
	private static final Map<String, String[]> COMPONENT_PERMISSIONS =
	new HashMap<String, String[]>();
	
	static {
		COMPONENT_PERMISSIONS.put("FilePicker", concat(PERM_STORAGE, new String[] {"android.permission.MANAGE_EXTERNAL_STORAGE"}));
		COMPONENT_PERMISSIONS.put("Camera", concat(new String[] {"android.permission.CAMERA"}, PERM_STORAGE));
		COMPONENT_PERMISSIONS.put("Camera2 API", new String[] {"android.permission.CAMERA"});
		COMPONENT_PERMISSIONS.put("FirebaseDB", PERM_NET);
		COMPONENT_PERMISSIONS.put("FirebaseAuth", PERM_NET);
		COMPONENT_PERMISSIONS.put("FirebaseStorage", concat(PERM_NET, PERM_STORAGE));
		COMPONENT_PERMISSIONS.put("InterstitialAd", PERM_NET);
		COMPONENT_PERMISSIONS.put("DownloadManager", concat(PERM_NET, PERM_STORAGE));
		COMPONENT_PERMISSIONS.put("ConnectivityManager", new String[] {"android.permission.ACCESS_NETWORK_STATE"});
		COMPONENT_PERMISSIONS.put("LocationManager", new String[] {
			"android.permission.ACCESS_FINE_LOCATION",
			"android.permission.ACCESS_COARSE_LOCATION"});
		COMPONENT_PERMISSIONS.put("Vibrator", new String[] {"android.permission.VIBRATE"});
		COMPONENT_PERMISSIONS.put("PowerManager", new String[] {"android.permission.WAKE_LOCK"});
		COMPONENT_PERMISSIONS.put("TelephonyManager", new String[] {"android.permission.READ_PHONE_STATE"});
		COMPONENT_PERMISSIONS.put("AccountManager", new String[] {"android.permission.GET_ACCOUNTS"});
		COMPONENT_PERMISSIONS.put("Notification", new String[] {"android.permission.POST_NOTIFICATIONS"});
		COMPONENT_PERMISSIONS.put("RequestNetwork", PERM_NET);
		COMPONENT_PERMISSIONS.put("SpeechToText", new String[] {
			"android.permission.RECORD_AUDIO",
			"android.permission.INTERNET"});
		COMPONENT_PERMISSIONS.put("BluetoothConnect", new String[] {
			"android.permission.BLUETOOTH",
			"android.permission.BLUETOOTH_ADMIN",
			"android.permission.BLUETOOTH_CONNECT",
			"android.permission.BLUETOOTH_SCAN"});
		COMPONENT_PERMISSIONS.put("BluetoothAdapter", new String[] {
			"android.permission.BLUETOOTH",
			"android.permission.BLUETOOTH_ADMIN",
			"android.permission.BLUETOOTH_CONNECT",
			"android.permission.BLUETOOTH_SCAN"});
	}
	
	private static String[] concat(String[] a, String[] b) {
		String[] r = new String[a.length + b.length];
		System.arraycopy(a, 0, r, 0, a.length);
		System.arraycopy(b, 0, r, a.length, b.length);
		return r;
	}
	
	private void applyProjectSettings() {
		try {
			if (this.sc_id == null || this.sc_id.trim().isEmpty()) return;
			File settingsFile = new File("/storage/emulated/0/.blacklogics/data/"
			+ this.sc_id.trim() + "/settings");
			if (!settingsFile.exists()) return;
			
			ProjectSettingsManager ps = new ProjectSettingsManager(this.sc_id.trim());
			ps.load(null);
			String cls = ps.getApplicationClassName();
			if (cls != null && !cls.trim().isEmpty()) {
				this.applicationClassName = cls.trim();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	private void collectTypesFromProjectLogic(String sc_id, Set<String> out, String tag) {
		try {
			File f = new File("/storage/emulated/0/.blacklogics/data/" + sc_id + "/project_logic.bin");
			if (!f.exists() || f.length() == 0) {
				android.util.Log.d(tag, "project_logic.bin nahi mila: " + f.getAbsolutePath());
				return;
			}
			byte[] decoded = android.util.Base64.decode(
			java.nio.file.Files.readAllBytes(f.toPath()), android.util.Base64.DEFAULT);
			com.google.gson.JsonElement rootEl = new com.google.gson.JsonParser()
			.parse(new String(decoded, java.nio.charset.StandardCharsets.UTF_8));
			if (rootEl == null || !rootEl.isJsonObject()) return;
			com.google.gson.JsonElement comps = rootEl.getAsJsonObject().get("components");
			if (comps == null || !comps.isJsonObject()) return;
			for (Map.Entry<String, com.google.gson.JsonElement> act : comps.getAsJsonObject().entrySet()) {
				if (act.getValue() == null || !act.getValue().isJsonArray()) continue;
				for (com.google.gson.JsonElement item : act.getValue().getAsJsonArray()) {
					if (item == null || !item.isJsonObject()) continue;
					com.google.gson.JsonElement n = item.getAsJsonObject().get("componentName");
					if (n != null && n.isJsonPrimitive()) out.add(n.getAsString());
				}
			}
		} catch (Throwable t) {
			android.util.Log.d(tag, "project_logic read failed: " + t);
		}
	}
	
	private void collectTypesFromBlockStorage(String sc_id, Set<String> out, String tag) {
		try {
			BlockStorage.setScId(sc_id);
			Object[] all = BlockStorage.readAll();
			if (all == null || all.length < 3 || !(all[2] instanceof Map)) return;
			for (Object listObj : ((Map<?, ?>) all[2]).values()) {
				if (!(listObj instanceof List)) continue;
				for (Object o : (List<?>) listObj) {
					if (!(o instanceof ComponentData)) continue;
					ComponentData d = (ComponentData) o;
					if (d.extraData != null && d.extraData.length() > 0) out.add(d.extraData);
					try {
						String byType = ComponentBean.getComponentTypeName(d.type);
						if (byType != null && byType.length() > 0) out.add(byType);
					} catch (Throwable ignored) {}
				}
			}
		} catch (Throwable t) {
			android.util.Log.d(tag, "BlockStorage read failed: " + t);
		}
	}
	
	public AndroidManifestGenerator autoRegisterComponentPermissions(String sc_id) {
		needsFileProvider = false;
		if (sc_id == null || sc_id.trim().isEmpty()) return this;
		sc_id = sc_id.trim();
		final String TAG = "ManifestPerm";
		try {
			Set<String> types = new HashSet<String>();
			collectTypesFromProjectLogic(sc_id, types, TAG);
			collectTypesFromBlockStorage(sc_id, types, TAG);
			android.util.Log.d(TAG, "sc_id=" + sc_id + " component types=" + types);
			
			for (String typeName : types) {
				if ("Camera".equals(typeName)) needsFileProvider = true;
				String[] perms = COMPONENT_PERMISSIONS.get(typeName);
				if (perms == null) continue;
				for (String perm : perms) addPermission(perm);
			}
		} catch (Throwable t) {
			android.util.Log.d(TAG, "failed: " + t);
			t.printStackTrace();
		}
		return this;
	}
	
	
	public AndroidManifestGenerator autoRegisterPermissions(
	String sc_id
	) {
		
		try {
			
			File file = new File(
			FileUtil.getExternalStorageDir()
			+ "/.blacklogics/data/"
			+ sc_id
			+ "/permission"
			);
			
			if (!file.exists() || !file.isFile()) {
				return this;
			}
			
			String json = new String(
			Files.readAllBytes(file.toPath()),
			StandardCharsets.UTF_8
			);
			
			if (json.trim().isEmpty()) {
				return this;
			}
			
			List<String> list =
			new Gson().fromJson(
			json,
			new TypeToken<List<String>>() {}.getType()
			);
			
			if (list == null) {
				return this;
			}
			
			for (String permission : list) {
				
				if (permission == null) {
					continue;
				}
				
				permission = permission.trim();
				
				if (permission.isEmpty()) {
					continue;
				}
				
				addPermission(permission);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	
	public AndroidManifestGenerator autoRegisterServices(
	String sc_id
	) {
		
		try {
			
			File file = new File(
			FileUtil.getExternalStorageDir()
			+ "/.blacklogics/data/"
			+ sc_id
			+ "/service"
			);
			
			if (!file.exists() || !file.isFile()) {
				return this;
			}
			
			String json = new String(
			Files.readAllBytes(file.toPath()),
			StandardCharsets.UTF_8
			);
			
			if (json.trim().isEmpty()) {
				return this;
			}
			
			List<String> list =
			new Gson().fromJson(
			json,
			new TypeToken<List<String>>() {}.getType()
			);
			
			if (list == null) {
				return this;
			}
			
			Set<String> existingNames =
			new HashSet<String>();
			
			for (Map<String, Object> service : services) {
				
				String name =
				(String) service.get("name");
				
				if (name != null) {
					existingNames.add(name);
				}
			}
			
			for (String fullName : list) {
				
				if (fullName == null) {
					continue;
				}
				
				fullName = fullName.trim();
				
				if (fullName.isEmpty()) {
					continue;
				}
				
				String className;
				
				int lastDot = fullName.lastIndexOf('.');
				
				if (lastDot >= 0) {
					className =
					fullName.substring(lastDot + 1);
				} else {
					className = fullName;
				}
				
				if (className.isEmpty()) {
					continue;
				}
				
				String manifestName =
				"." + className;
				
				if (existingNames.contains(manifestName)) {
					continue;
				}
				
				Map<String, String> attrs =
				new HashMap<String, String>();
				
				Map<String, Object> service =
				new HashMap<String, Object>();
				
				service.put(
				"name",
				manifestName
				);
				
				service.put(
				"attrs",
				attrs
				);
				
				services.add(service);
				existingNames.add(manifestName);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	
	public AndroidManifestGenerator registerDefaultDebugActivity() {
		
		String debugName = ".DebugActivity";
		
		if (find(activities, debugName) != null) {
			return this;
		}
		
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("screenOrientation", "portrait");
		attrs.put("theme", "@style/AppTheme.DebugActivity");
		attrs.put("exported", "false");
		
		Map<String, Object> activity = new HashMap<String, Object>();
		activity.put("name", debugName);
		activity.put("attrs", attrs);
		
		activities.add(activity);
		
		return this;
	}
	
	
	public AndroidManifestGenerator applyCustomAppComponents(String sc_id) {
		
		try {
			
			File file = new File(
			FileUtil.getExternalStorageDir()
			+ "/.blacklogics/data/"
			+ sc_id
			+ "/Injection/androidmanifest/app_components.txt"
			);
			
			if (!file.exists() || !file.isFile()) {
				customAppComponentsXml = null;
				return this;
			}
			
			String content = new String(
			Files.readAllBytes(file.toPath()),
			StandardCharsets.UTF_8
			);
			
			if (content == null || content.trim().isEmpty()) {
				customAppComponentsXml = null;
				return this;
			}
			
			customAppComponentsXml = content.trim();
			
		} catch (Exception e) {
			e.printStackTrace();
			customAppComponentsXml = null;
		}
		
		return this;
	}
	
	
	public String generate() {
		
		applyProjectSettings();
		autoRegisterComponentPermissions(this.sc_id);
		
		StringBuilder xml =
		new StringBuilder();
		
		xml.append(
		"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
		);
		
		xml.append("<manifest\n");
		xml.append(
		"    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
		);
		
		xml.append(
		"    package=\""
		).append(
		safe(packageName)
		).append(
		"\">\n\n"
		);
		
		
		if (!permissions.isEmpty()) {
			
			for (String permission : permissions) {
				
				xml.append(
				"    <uses-permission android:name=\""
				);
				
				xml.append(
				safe(permission)
				);
				
				xml.append(
				"\" />\n"
				);
			}
			
			xml.append("\n");
		}
		
		
		xml.append(
		"    <application\n"
		);
		
		xml.append(
		"        android:allowBackup=\"true\"\n"
		);
		
		xml.append(
		"        android:icon=\""
		).append(
		safe(appIcon)
		).append(
		"\"\n"
		);
		
		xml.append(
		"        android:label=\"@string/app_name\"\n"
		);
		
		xml.append(
		"        android:name=\""
		).append(
		safe(applicationClassName)
		).append(
		"\"\n"
		);
		
		xml.append(
		"        android:theme=\"@style/"
		).append(
		safe(themeColor)
		).append(
		"\""
		);
		
		if (!attributes.isEmpty()) {
			
			xml.append("\n");
			
			int i = 0;
			
			for (Map.Entry<String, String> entry
			: attributes.entrySet()) {
				
				xml.append(
				"        android:"
				);
				
				xml.append(
				entry.getKey()
				);
				
				xml.append(
				"=\""
				);
				
				xml.append(
				safe(entry.getValue())
				);
				
				xml.append(
				"\""
				);
				
				if (i < attributes.size() - 1) {
					xml.append("\n");
				}
				
				i++;
			}
		}
		
		xml.append(">\n\n");
		
		
		String finalLauncher = null;
		
		String launcherFromPath =
		getLauncherActivityFromPath();
		
		if (launcherFromPath != null
		&& !launcherFromPath.trim().isEmpty()) {
			
			String resolved =
			resolveActivityName(
			launcherFromPath.trim()
			);
			
			if (activityExists(resolved)) {
				finalLauncher = resolved;
			}
		}
		
		if (finalLauncher == null
		&& launcherActivity != null
		&& !launcherActivity.trim().isEmpty()) {
			
			if (activityExists(launcherActivity)) {
				finalLauncher = launcherActivity;
			}
		}
		
		
		for (Map<String, Object> activity
		: activities) {
			
			String name =
			(String) activity.get("name");
			
			Map<String, String> attrs =
			(Map<String, String>)
			activity.get("attrs");
			
			boolean isLauncher =
			finalLauncher != null
			&& name != null
			&& name.equals(finalLauncher);
			
			if (isLauncher) {
				attrs = (attrs != null)
				? new HashMap<String, String>(attrs)
				: new HashMap<String, String>();
				attrs.put("exported", "true");
			}
			
			xml.append(
			"        <activity\n"
			);
			
			xml.append(
			"            android:name=\""
			);
			
			xml.append(
			safe(name)
			);
			
			xml.append("\"");
			
			if (attrs != null && !attrs.isEmpty()) {
				
				for (Map.Entry<String, String> entry
				: attrs.entrySet()) {
					
					xml.append(
					"\n            android:"
					);
					
					xml.append(
					entry.getKey()
					);
					
					xml.append(
					"=\""
					);
					
					xml.append(
					safe(entry.getValue())
					);
					
					xml.append(
					"\""
					);
				}
			}
			
			if (isLauncher) {
				
				xml.append(">\n");
				
				xml.append(
				"            <intent-filter>\n"
				);
				
				xml.append(
				"                <action android:name=\"android.intent.action.MAIN\" />\n"
				);
				
				xml.append(
				"                <category android:name=\"android.intent.category.LAUNCHER\" />\n"
				);
				
				xml.append(
				"            </intent-filter>\n"
				);
				
				xml.append(
				"        </activity>\n"
				);
				
			} else {
				
				xml.append(
				" />\n"
				);
			}
			
			xml.append("\n");
		}
		
		
		for (Map<String, Object> service
		: services) {
			
			String name =
			(String) service.get("name");
			
			Map<String, String> attrs =
			(Map<String, String>)
			service.get("attrs");
			
			xml.append(
			"        <service\n"
			);
			
			xml.append(
			"            android:name=\""
			);
			
			xml.append(
			safe(name)
			);
			
			xml.append("\"");
			
			if (attrs != null && !attrs.isEmpty()) {
				
				for (Map.Entry<String, String> entry
				: attrs.entrySet()) {
					
					xml.append(
					"\n            android:"
					);
					
					xml.append(
					entry.getKey()
					);
					
					xml.append(
					"=\""
					);
					
					xml.append(
					safe(entry.getValue())
					);
					
					xml.append(
					"\""
					);
				}
			}
			
			xml.append(
			" />\n\n"
			);
		}
		
		
		for (Map<String, Object> receiver
		: receivers) {
			
			String name =
			(String) receiver.get("name");
			
			Map<String, String> attrs =
			(Map<String, String>)
			receiver.get("attrs");
			
			xml.append(
			"        <receiver\n"
			);
			
			xml.append(
			"            android:name=\""
			);
			
			xml.append(
			safe(name)
			);
			
			xml.append("\"");
			
			if (attrs != null && !attrs.isEmpty()) {
				
				for (Map.Entry<String, String> entry
				: attrs.entrySet()) {
					
					xml.append(
					"\n            android:"
					);
					
					xml.append(
					entry.getKey()
					);
					
					xml.append(
					"=\""
					);
					
					xml.append(
					safe(entry.getValue())
					);
					
					xml.append(
					"\""
					);
				}
			}
			
			xml.append(
			" />\n\n"
			);
		}
		
		
		if (needsFileProvider
		&& useAndroidX
		&& (customAppComponentsXml == null
		|| !customAppComponentsXml.contains("FileProvider"))) {
			
			String fpClass = "androidx.core.content.FileProvider";
			
			xml.append("        <provider\n");
			xml.append("            android:name=\"").append(fpClass).append("\"\n");
			xml.append("            android:authorities=\"").append(safe(packageName)).append(".provider\"\n");
			xml.append("            android:exported=\"false\"\n");
			xml.append("            android:grantUriPermissions=\"true\">\n");
			xml.append("            <meta-data\n");
			xml.append("                android:name=\"android.support.FILE_PROVIDER_PATHS\"\n");
			xml.append("                android:resource=\"@xml/provider_paths\" />\n");
			xml.append("        </provider>\n\n");
		}
		
		
		if (customAppComponentsXml != null
		&& !customAppComponentsXml.trim().isEmpty()) {
			
			xml.append(customAppComponentsXml);
			xml.append("\n\n");
		}
		
		
		xml.append(
		"    </application>\n"
		);
		
		xml.append(
		"</manifest>\n"
		);
		
		return xml.toString();
	}
	
	
	public void load(
	Context context,
	String sc_id
	) {
		
		this.sc_id = sc_id;
		if (context != null) this.loadedContext = context;
		
		try {
			
			File dir = new File(
			FileUtil.getExternalStorageDir() + "/.blacklogics/data/"
			+ sc_id
			);
			
			File file =
			new File(dir, "logic");
			
			if (!file.exists()) {
				return;
			}
			
			JSONObject json =
			new JSONObject(
			new String(
			Files.readAllBytes(
			file.toPath()
			),
			StandardCharsets.UTF_8
			)
			);
			
			activities.clear();
			services.clear();
			receivers.clear();
			permissions.clear();
			attributes.clear();
			
			launcherActivity = null;
			launcherActivityPath = null;
			
			if (json.has("manifest_data")) {
				
				String decoded =
				new String(
				Base64.getDecoder().decode(
				json.getString(
				"manifest_data"
				)
				),
				StandardCharsets.UTF_8
				);
				
				if (decoded != null
				&& !decoded.trim().isEmpty()) {
					
					Map<String, Object> data =
					new Gson().fromJson(
					decoded,
					new TypeToken<Map<String, Object>>() {}
					.getType()
					);
					
					Map<String, Object> manifest =
					(Map<String, Object>)
					data.get("manifest");
					
					if (manifest != null) {
						
						if (manifest.get("package") != null) {
							packageName =
							(String)
							manifest.get("package");
						}
						
						if (manifest.get("appName") != null) {
							appName =
							(String)
							manifest.get("appName");
						}
						
						if (manifest.get("appIcon") != null) {
							appIcon =
							(String)
							manifest.get("appIcon");
						}
						
						if (manifest.get("themeColor") != null) {
							themeColor =
							(String)
							manifest.get("themeColor");
						}
						
						if (manifest.get("applicationClassName") != null) {
							Object rawAppClass =
							manifest.get("applicationClassName");
							
							if (rawAppClass instanceof String
							&& !((String) rawAppClass).trim().isEmpty()) {
								applicationClassName =
								((String) rawAppClass).trim();
							}
						}
						
						if (manifest.get("launcher") != null) {
							launcherActivity =
							(String)
							manifest.get("launcher");
						}
						
						if (manifest.get("launcherPath") != null) {
							launcherActivityPath =
							(String)
							manifest.get("launcherPath");
						}
						
						Object ax =
						manifest.get("useAndroidX");
						
						useAndroidX =
						ax != null
						&& (Boolean) ax;
						
						Object activitiesObj =
						manifest.get("activities");
						
						if (activitiesObj instanceof List) {
							activities =
							(List<Map<String, Object>>)
							activitiesObj;
						}
						
						Object servicesObj =
						manifest.get("services");
						
						if (servicesObj instanceof List) {
							services =
							(List<Map<String, Object>>)
							servicesObj;
						}
						
						Object receiversObj =
						manifest.get("receivers");
						
						if (receiversObj instanceof List) {
							receivers =
							(List<Map<String, Object>>)
							receiversObj;
						}
						
						Object permissionsObj =
						manifest.get("permissions");
						
						if (permissionsObj instanceof List) {
							permissions =
							(List<String>)
							permissionsObj;
						}
						
						Object attributesObj =
						manifest.get("attributes");
						
						if (attributesObj instanceof Map) {
							attributes =
							(Map<String, String>)
							attributesObj;
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public AndroidManifestGenerator prepareFullManifest(String sc_id) {
		if (sc_id == null || sc_id.trim().isEmpty()) {
			return this;
		}
		this.sc_id = sc_id.trim();
		
		load(null, this.sc_id);
		
		autoRegisterActivities(this.sc_id);
		applyProjectSettings();
		autoRegisterPermissions(this.sc_id);
		autoRegisterComponentPermissions(this.sc_id);
		autoRegisterServices(this.sc_id);
		registerDefaultDebugActivity();
		applyCustomAppComponents(this.sc_id);
		
		if (packageName == null || packageName.trim().isEmpty()) {
			packageName = "com.my.app";
		}
		if (appIcon == null || appIcon.trim().isEmpty()) {
			appIcon = "@mipmap/ic_launcher";
		}
		if (themeColor == null || themeColor.trim().isEmpty()) {
			themeColor = "AppTheme";
		}
		if (applicationClassName == null || applicationClassName.trim().isEmpty()) {
			applicationClassName = ".BlackApplication";
		}
		
		return this;
	}
	
	public String generateFull(String sc_id) {
		prepareFullManifest(sc_id);
		if (launcherActivityPath != null) {
			setLauncherActivityPath(launcherActivityPath);
		}
		return generate();
	}
	
	
	public void extractManifest(
	String sc_id,
	String outputPath
	) {
		
		try {
			
			prepareFullManifest(sc_id);
			
			String xml = generate();
			
			File out =
			new File(
			outputPath
			+ "/AndroidManifest.xml"
			);
			
			File parent =
			out.getParentFile();
			
			if (parent != null
			&& !parent.exists()) {
				
				parent.mkdirs();
			}
			
			FileOutputStream fos =
			new FileOutputStream(out);
			
			try {
				
				fos.write(
				xml.getBytes(
				StandardCharsets.UTF_8
				)
				);
				
			} finally {
				fos.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private String simpleComponentName(String name) {
		if (name == null) return "";
		String n = name.trim();
		if (n.isEmpty()) return "";
		int lastDot = n.lastIndexOf('.');
		if (lastDot >= 0 && lastDot < n.length() - 1) {
			n = n.substring(lastDot + 1);
		}
		if (n.startsWith(".")) {
			n = n.substring(1);
		}
		return n;
	}
	
	private boolean sameComponentName(String a, String b) {
		if (a == null || b == null) return false;
		if (a.equals(b)) return true;
		String sa = simpleComponentName(a);
		String sb = simpleComponentName(b);
		return sa.length() > 0 && sa.equals(sb);
	}
	
	private boolean exists(
	List<Map<String, Object>> list,
	String name
	) {
		return find(list, name) != null;
	}
	
	private Map<String, Object> find(
	List<Map<String, Object>> list,
	String name
	) {
		
		if (name == null || list == null) {
			return null;
		}
		
		for (Map<String, Object> item : list) {
			Object n = item.get("name");
			if (n == null) continue;
			if (sameComponentName(name, String.valueOf(n))) {
				return item;
			}
		}
		
		return null;
	}
	public void save(Context context, String sc_id) {
		try {
			File dir = new File(
			FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id
			);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			File file = new File(dir, "logic");
			
			JSONObject output = new JSONObject();
			if (file.exists()) {
				try {
					String existing = new String(
					Files.readAllBytes(file.toPath()),
					StandardCharsets.UTF_8
					);
					if (existing != null && !existing.trim().isEmpty()) {
						output = new JSONObject(existing);
					}
				} catch (Exception ignored) {}
			}
			
			Map<String, Object> manifest = new HashMap<String, Object>();
			manifest.put("package", packageName);
			manifest.put("appName", appName);
			manifest.put("appIcon", appIcon);
			manifest.put("themeColor", themeColor);
			manifest.put("applicationClassName", applicationClassName);
			manifest.put("launcher", launcherActivity);
			manifest.put("launcherPath", launcherActivityPath);
			manifest.put("useAndroidX", useAndroidX);
			manifest.put("activities", activities);
			manifest.put("services", services);
			manifest.put("receivers", receivers);
			manifest.put("permissions", permissions);
			manifest.put("attributes", attributes);
			
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("manifest", manifest);
			
			String json = new Gson().toJson(root);
			String encoded = Base64.getEncoder().encodeToString(
			json.getBytes(StandardCharsets.UTF_8)
			);
			
			output.put("manifest_data", encoded);
			
			FileOutputStream fos = new FileOutputStream(file);
			try {
				fos.write(output.toString(2).getBytes(StandardCharsets.UTF_8));
			} finally {
				fos.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public AndroidManifestGenerator addLauncherActivity(String name) {
		if (name == null || name.trim().isEmpty()) {
			return this;
		}
		
		String resolved = resolveActivityName(name.trim());
		
		for (Map<String, Object> activity : activities) {
			
			String activityName =
			(String) activity.get("name");
			
			if (resolved.equals(activityName)) {
				
				launcherActivity = resolved;
				
				Map<String, String> attrs =
				(Map<String, String>)
				activity.get("attrs");
				
				if (attrs == null) {
					attrs = new HashMap<String, String>();
					activity.put("attrs", attrs);
				}
				
				attrs.put("exported", "true");
				attrs.put(
				"configChanges",
				"orientation|keyboardHidden|screenSize"
				);
				attrs.put(
				"windowSoftInputMode",
				"adjustResize"
				);
				
				return this;
			}
		}
		
		Map<String, String> attrs =
		new HashMap<String, String>();
		
		attrs.put("exported", "true");
		attrs.put(
		"configChanges",
		"orientation|keyboardHidden|screenSize"
		);
		attrs.put(
		"windowSoftInputMode",
		"adjustResize"
		);
		
		addActivity(resolved, attrs);
		
		launcherActivity = resolved;
		
		return this;
	}
	
	private boolean activityExists(
	String name
	) {
		
		if (name == null) {
			return false;
		}
		
		for (Map<String, Object> activity
		: activities) {
			
			if (name.equals(
			activity.get("name")
			)) {
				return true;
			}
		}
		
		return false;
	}
	
	private String safe(String value) {
		
		if (value == null) {
			return "";
		}
		
		return value
		.replace("&", "&amp;")
		.replace("\"", "&quot;")
		.replace("<", "&lt;")
		.replace(">", "&gt;");
	}
}
