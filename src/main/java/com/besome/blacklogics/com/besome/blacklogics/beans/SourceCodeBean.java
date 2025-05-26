package com.besome.blacklogics.beans;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.besome.blacklogics.DesignActivity;

public class SourceCodeBean implements Parcelable {
	@Expose
	private String activityName;
	@Expose
	private String eventName;
	@Expose
	private String sourceCode;
	private String sc_id;
	
	// Storage path template
	//private static final String STORAGE_PATH = "/storage/emulated/0/.blacklogics/" DesignActivity.getScId() + "/root_logic";
	
	public SourceCodeBean() {
		// Default constructor for Gson
	}
	
	public SourceCodeBean(String activityName, String eventName, String sourceCode, String sc_id) {
		this.activityName = activityName;
		this.eventName = eventName;
		this.sourceCode = sourceCode;
		this.sc_id = sc_id;
	}
	
	private static String getStoragePath(String sc_id) {
		return "/storage/emulated/0/.blacklogics/data/" + sc_id + "/root_logic";
	}
	
	// Getters and Setters
	public String getActivityName() {
		return activityName;
	}
	
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public String getEventName() {
		return eventName;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getSourceCode() {
		return sourceCode;
	}
	
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	public String getScId() {
		return sc_id;
	}
	
	public void setScId(String sc_id) {
		this.sc_id = sc_id;
	}
	
	// Save source code to file
	public void save() throws IOException, JSONException {
		String filePath = getStoragePath(sc_id);
		File file = new File(filePath);
		File parentDir = file.getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		
		JSONArray jsonArray;
		if (file.exists()) {
			// Read existing data
			StringBuilder content = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {
					content.append(line);
				}
			}
			jsonArray = content.length() > 0 ? new JSONArray(content.toString()) : new JSONArray();
		} else {
			jsonArray = new JSONArray();
		}
		
		// Check if entry for this activityName and eventName exists
		boolean updated = false;
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if (jsonObject.getString("activityName").equals(activityName) &&
			jsonObject.getString("eventName").equals(eventName)) {
				// Update existing entry
				jsonObject.put("sourceCode", sourceCode);
				updated = true;
				break;
			}
		}
		
		if (!updated) {
			// Add new entry
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("activityName", activityName);
			jsonObject.put("eventName", eventName);
			jsonObject.put("sourceCode", sourceCode);
			jsonArray.put(jsonObject);
		}
		
		// Write back to file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(jsonArray.toString(2)); // Pretty print with indentation
		}
	}
	
	// Load source code for a specific activityName and eventName
	public static SourceCodeBean load(String activityName, String eventName, String sc_id) throws IOException, JSONException {
		String filePath = getStoragePath(sc_id);
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line);
			}
		}
		
		JSONArray jsonArray = new JSONArray(content.toString());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if (jsonObject.getString("activityName").equals(activityName) &&
			jsonObject.getString("eventName").equals(eventName)) {
				SourceCodeBean bean = new SourceCodeBean();
				bean.setActivityName(jsonObject.getString("activityName"));
				bean.setEventName(jsonObject.getString("eventName"));
				bean.setSourceCode(jsonObject.getString("sourceCode"));
				bean.setScId(sc_id);
				return bean;
			}
		}
		return null;
	}
	
	// Parcelable implementation
	protected SourceCodeBean(Parcel in) {
		activityName = in.readString();
		eventName = in.readString();
		sourceCode = in.readString();
		sc_id = in.readString();
	}
	
	public static final Creator<SourceCodeBean> CREATOR = new Creator<SourceCodeBean>() {
		@Override
		public SourceCodeBean createFromParcel(Parcel in) {
			return new SourceCodeBean(in);
		}
		
		@Override
		public SourceCodeBean[] newArray(int size) {
			return new SourceCodeBean[size];
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(activityName);
		dest.writeString(eventName);
		dest.writeString(sourceCode);
		dest.writeString(sc_id);
	}
}
