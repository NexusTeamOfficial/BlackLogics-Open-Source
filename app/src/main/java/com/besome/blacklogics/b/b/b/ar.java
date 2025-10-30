package b.b.b;

import android.content.Context;
import android.os.Environment;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.Base64;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ar {
	
	private boolean useAndroidX;
	private Map<String, String> colors = new HashMap<>();
	private Map<String, String> styles = new HashMap<>();
	private Map<String, String> dimens = new HashMap<>();
	private Map<String, String> attributes = new HashMap<>();
	
	public ar setAndroidX(boolean enable) {
		this.useAndroidX = enable;
		colors.clear();
		styles.clear();
		dimens.clear();
		
		if (enable) {
			setColor("colorPrimary", "#2196F3");
			setColor("colorPrimaryDark", "#1976D2");
			setColor("colorAccent", "#FF4081");
			setColor("colorBackground", "#F5F5F5");
			setColor("colorControlHighlight", "#40C4FF");
			setColor("colorControlNormal", "#B0BEC5");
			
			setStyle("AppTheme", "Theme.MaterialComponents.DayNight");
			setStyle("FullScreen", "Theme.MaterialComponents.Light.NoActionBar");
			setStyle("NoActionBar", "Theme.MaterialComponents.Light.NoActionBar");
			setStyle("NoStatusBar", "AppTheme");
			setStyle("AppTheme.DebugActivity", "AppTheme");
			
			setDimen("app_margin", "16dp");
			setDimen("button_corner", "8dp");
			setDimen("text_size", "14sp");
			
		} else {
			setColor("colorPrimary", "#3F51B5");
			setColor("colorPrimaryDark", "#303F9F");
			setColor("colorAccent", "#FF9800");
			setColor("colorBackground", "#EEEEEE");
			setColor("colorControlHighlight", "#FFCC80");
			setColor("colorControlNormal", "#BDBDBD");
			
			setStyle("AppTheme", "@android:style/Theme.Material.Light.NoActionBar");
			setStyle("FullScreen", "@android:style/Theme.Material.Light.NoActionBar.Fullscreen");
			setStyle("NoActionBar", "@android:style/Theme.Material.Light.NoActionBar");
			setStyle("NoStatusBar", "AppTheme");
			setStyle("AppTheme.DebugActivity", "AppTheme");
			
			setDimen("app_margin", "8dp");
			setDimen("button_corner", "4dp");
			setDimen("text_size", "16sp");
		}
		
		return this;
	}
	
	public boolean getAndroidX() {
		return useAndroidX;
	}
	
	public ar setColor(String name, String value) {
		colors.put(name, value);
		return this;
	}
	
	public ar setStyle(String name, String parent) {
		styles.put(name, parent);
		return this;
	}
	
	public ar setDimen(String name, String value) {
		dimens.put(name, value);
		return this;
	}
	
	public ar setAttribute(String key, String value) {
		attributes.put(key, value);
		return this;
	}
	
	public String generateColors() {
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
		for (Map.Entry<String, String> color : colors.entrySet()) {
			xml.append("    <color name=\"").append(color.getKey())
			.append("\">").append(color.getValue()).append("</color>\n");
		}
		xml.append("</resources>");
		return xml.toString();
	}
	
	public String generateStyles() {
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
		for (Map.Entry<String, String> style : styles.entrySet()) {
			xml.append("    <style name=\"").append(style.getKey())
			.append("\" parent=\"").append(style.getValue()).append("\">\n");
			if (style.getKey().equals("AppTheme") || style.getKey().equals("FullScreen") || style.getKey().equals("NoActionBar")) {
				xml.append("        <item name=\"android:colorPrimary\">@color/colorPrimary</item>\n");
				xml.append("        <item name=\"android:colorPrimaryDark\">@color/colorPrimaryDark</item>\n");
				xml.append("        <item name=\"android:colorAccent\">@color/colorAccent</item>\n");
				xml.append("        <item name=\"android:colorControlHighlight\">@color/colorControlHighlight</item>\n");
				xml.append("        <item name=\"android:colorControlNormal\">@color/colorControlNormal</item>\n");
			}
			if (style.getKey().equals("NoStatusBar")) {
				xml.append("        <item name=\"android:windowFullscreen\">true</item>\n");
			}
			xml.append("    </style>\n");
		}
		xml.append("</resources>");
		return xml.toString();
	}
	
	public String generateDimens() {
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
		for (Map.Entry<String, String> dimen : dimens.entrySet()) {
			xml.append("    <dimen name=\"").append(dimen.getKey())
			.append("\">").append(dimen.getValue()).append("</dimen>\n");
		}
		xml.append("    <dimen name=\"small_margin\">8dp</dimen>\n");
		xml.append("    <dimen name=\"medium_margin\">16dp</dimen>\n");
		xml.append("    <dimen name=\"large_margin\">24dp</dimen>\n");
		xml.append("    <dimen name=\"button_height\">48dp</dimen>\n");
		xml.append("</resources>");
		return xml.toString();
	}
	
	public void save(Context context, String sc_id) {
		try {
			File dir = new File(Environment.getExternalStorageDirectory() + "/.blacklogics/data/" + sc_id);
			if (!dir.exists()) dir.mkdirs();
			File file = new File(dir, "logic");
			JSONObject json = file.exists() ? new JSONObject(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8)) : new JSONObject();
			Map<String, Object> data = new HashMap<>();
			if (json.has("resource_data")) {
				String decoded = new String(Base64.getDecoder().decode(json.getString("resource_data")), StandardCharsets.UTF_8);
				data = new Gson().fromJson(decoded, new TypeToken<Map<String, Object>>(){}.getType());
			}
			Map<String, Object> newResource = new HashMap<>();
			newResource.put("useAndroidX", useAndroidX);
			newResource.put("colors", colors);
			newResource.put("styles", styles);
			newResource.put("dimens", dimens);
			newResource.put("attributes", attributes);
			data.put("resources", newResource);
			String base64 = Base64.getEncoder().encodeToString(new Gson().toJson(data).getBytes(StandardCharsets.UTF_8));
			json.put("resource_data", base64);
			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
			}
			//saveResourceFile(dir, "colors.xml", generateColors());
			//saveResourceFile(dir, "styles.xml", generateStyles());
			//saveResourceFile(dir, "dimens.xml", generateDimens());
		} catch (Exception e) {}
	}
	
	private void saveResourceFile(File dir, String fileName, String content) {
		try {
			File resourceFile = new File(dir, fileName);
			try (FileOutputStream fos = new FileOutputStream(resourceFile)) {
				fos.write(content.getBytes(StandardCharsets.UTF_8));
			}
		} catch (Exception e) {}
	}
	
	public void load(Context context, String sc_id) {
		try {
			File dir = new File(Environment.getExternalStorageDirectory() + "/.blacklogics/data/" + sc_id);
			File file = new File(dir, "logic");
			if (file.exists()) {
				JSONObject json = new JSONObject(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8));
				if (json.has("resource_data")) {
					String decoded = new String(Base64.getDecoder().decode(json.getString("resource_data")), StandardCharsets.UTF_8);
					Map<String, Object> data = new Gson().fromJson(decoded, new TypeToken<Map<String, Object>>(){}.getType());
					Map<String, Object> r = (Map<String, Object>) data.get("resources");
					if (r != null) {
						useAndroidX = (boolean) r.get("useAndroidX");
						colors = (Map<String, String>) r.get("colors");
						styles = (Map<String, String>) r.get("styles");
						dimens = (Map<String, String>) r.get("dimens");
						attributes = (Map<String, String>) r.get("attributes");
					}
				}
			}
		} catch (Exception e) {}
	}
	
	public void extractResources(String sc_id, String outputPath) {
		try {
			load(null, sc_id);
			File colorsDir = new File(outputPath + "/values");
			if (!colorsDir.exists()) colorsDir.mkdirs();
			saveResourceFile(colorsDir, "colors.xml", generateColors());
			saveResourceFile(colorsDir, "styles.xml", generateStyles());
			saveResourceFile(colorsDir, "dimens.xml", generateDimens());
		} catch (Exception e) {}
	}
}
