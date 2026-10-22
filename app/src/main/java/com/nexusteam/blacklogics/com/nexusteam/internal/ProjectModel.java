package com.nexusteam.internal;

public class ProjectModel {
	
	private String sc_id;
	private String projectName;
	private String appName;
	private String packageName;
	private String versionCode;
	private String versionName;
	private String colorAccent;
	private String colorPrimary;
	private String colorPrimaryDark;
	private String colorPrimaryHighlight;
	private String colorPrimaryNormal;
	private String iconPath;
	private String layoutPath = "";
	
	
	public ProjectModel() {}
	
	
	public String getLayoutPath() { return layoutPath; }
	public void setLayoutPath(String p) { this.layoutPath = p; }
	public String getScId()                  { return sc_id; }
	public String getProjectName()           { return projectName; }
	public String getAppName()               { return appName; }
	public String getPackageName()           { return packageName; }
	public String getVersionCode()           { return versionCode; }
	public String getVersionName()           { return versionName; }
	public String getColorAccent()           { return colorAccent; }
	public String getColorPrimary()          { return colorPrimary; }
	public String getColorPrimaryDark()      { return colorPrimaryDark; }
	public String getColorPrimaryHighlight() { return colorPrimaryHighlight; }
	public String getColorPrimaryNormal()    { return colorPrimaryNormal; }
	public String getIconPath()              { return iconPath; }
	
	
	public void setScId(String sc_id)                           { this.sc_id = sc_id; }
	public void setProjectName(String projectName)              { this.projectName = projectName; }
	public void setAppName(String appName)                      { this.appName = appName; }
	public void setPackageName(String packageName)              { this.packageName = packageName; }
	public void setVersionCode(String versionCode)              { this.versionCode = versionCode; }
	public void setVersionName(String versionName)              { this.versionName = versionName; }
	public void setColorAccent(String colorAccent)              { this.colorAccent = colorAccent; }
	public void setColorPrimary(String colorPrimary)            { this.colorPrimary = colorPrimary; }
	public void setColorPrimaryDark(String colorPrimaryDark)    { this.colorPrimaryDark = colorPrimaryDark; }
	public void setColorPrimaryHighlight(String c)              { this.colorPrimaryHighlight = c; }
	public void setColorPrimaryNormal(String c)                 { this.colorPrimaryNormal = c; }
	public void setIconPath(String iconPath)                    { this.iconPath = iconPath; }
	
	
	public boolean hasCustomIcon() {
		return iconPath != null && !iconPath.equals("default_icon.png");
	}
	
	public int getColorAccentInt()           { return parseColor(colorAccent); }
	public int getColorPrimaryInt()          { return parseColor(colorPrimary); }
	public int getColorPrimaryDarkInt()      { return parseColor(colorPrimaryDark); }
	public int getColorHighlightInt()        { return parseColor(colorPrimaryHighlight); }
	public int getColorNormalInt()           { return parseColor(colorPrimaryNormal); }
	
	private int parseColor(String hex) {
		try {
			if (hex == null) return 0xFF000000;
			if (hex.startsWith("#")) hex = hex.substring(1);
			if (hex.length() == 6) hex = "FF" + hex;
			return (int) Long.parseLong(hex, 16);
		} catch (Exception e) {
			return 0xFF000000;
		}
	}
	
	@Override
	public String toString() {
		return "ProjectModel{" +
		"sc_id='" + sc_id + '\'' +
		", projectName='" + projectName + '\'' +
		", appName='" + appName + '\'' +
		", packageName='" + packageName + '\'' +
		", versionCode='" + versionCode + '\'' +
		", versionName='" + versionName + '\'' +
		", colorAccent='" + colorAccent + '\'' +
		", colorPrimary='" + colorPrimary + '\'' +
		", iconPath='" + iconPath + '\'' +
		'}';
	}
}
