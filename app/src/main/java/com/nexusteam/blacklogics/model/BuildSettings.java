
package com.nexusteam.blacklogics.model;

import com.nexusteam.blacklogics.bean.BuildSettingsConfig;

import com.nexusteam.blacklogics.utils.FileUtil;

public class BuildSettings extends ProjectSettings {
    
    public static final String SETTING_ANDROID_JAR_PATH = "android_jar";
    public static final String SETTING_CLASSPATH = "classpath";
    public static final String SETTING_DEXER = "dexer";
    public static final String SETTING_INCREMENTAL_BUILD_ACTIVE = "incremental_build";
    public static final String SETTING_JAVA_VERSION = "java_ver";
    public static final String SETTING_NO_HTTP_LEGACY = "no_http_legacy";
    public static final String SETTING_NO_WARNINGS = "no_warn";
    public static final String SETTING_ENABLE_LOGCAT = "enable_logcat";
    
    public static final String SETTING_DEXER_D8 = "D8";
    public static final String SETTING_DEXER_R8 = "R8";
    public static final String SETTING_DEXER_DX = "Dx";
    public static final String SETTING_JAVA_VERSION_1_7 = "1.7";
    public static final String SETTING_JAVA_VERSION_1_8 = "1.8";

    public BuildSettings(String sc_id) {
        super(sc_id);
    }

    @Override
    public String getPath() {
        return FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/build_config";
    }
    
    public BuildSettingsConfig loadConfig() {
    BuildSettingsConfig config = new BuildSettingsConfig();

    config.setAndroidJarPath(getValue(SETTING_ANDROID_JAR_PATH, ""));
    config.setClasspath(getValue(SETTING_CLASSPATH, ""));
    config.setDexer(getValue(SETTING_DEXER, SETTING_DEXER_DX));
    config.setJavaVersion(getValue(SETTING_JAVA_VERSION, SETTING_JAVA_VERSION_1_7));
    config.setNoWarnings(getValue(SETTING_NO_WARNINGS, true));
    config.setNoHttpLegacy(getValue(SETTING_NO_HTTP_LEGACY, false));
    config.setEnableLogcat(getValue(SETTING_ENABLE_LOGCAT, true));
    config.setIncrementalBuildActive(getValue(SETTING_INCREMENTAL_BUILD_ACTIVE, false));

    return config;
}
    public void saveConfig(BuildSettingsConfig config) {
        setValue(SETTING_ANDROID_JAR_PATH, config.getAndroidJarPath());
        setValue(SETTING_CLASSPATH, config.getClasspath());
        setValue(SETTING_DEXER, config.getDexer());
        setValue(SETTING_JAVA_VERSION, config.getJavaVersion());
        setValue(SETTING_NO_WARNINGS, String.valueOf(config.isNoWarnings()));
        setValue(SETTING_NO_HTTP_LEGACY, String.valueOf(config.isNoHttpLegacy()));
        setValue(SETTING_ENABLE_LOGCAT, String.valueOf(config.isEnableLogcat()));
        setValue(SETTING_INCREMENTAL_BUILD_ACTIVE, String.valueOf(config.isIncrementalBuildActive()));
    }
}