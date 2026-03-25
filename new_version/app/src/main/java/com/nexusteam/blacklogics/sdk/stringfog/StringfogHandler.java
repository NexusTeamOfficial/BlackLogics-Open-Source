package com.nexusteam.sdk.project.stringfog;

import com.google.gson.Gson;
import com.nexusteam.sdk.lib.FileUtil;
import com.nexusteam.sdk.project.proguard.ProguardHandler;
import java.util.HashMap;

public class StringfogHandler {
    private final String config_path;

    public StringfogHandler(String sc_id) {
        config_path =
            FileUtil.getExternalStorageDir().concat("/.blacklogics/data/" + sc_id + "/stringfog");

        if (!FileUtil.isExistFile(config_path))
            FileUtil.writeFile(config_path, getDefaultConfig());
    }

    private static String getDefaultConfig() {
        HashMap<String, String> config = new HashMap<>();
        config.put("enabled", "false");

        return new Gson().toJson(config);
    }

    public boolean isStringfogEnabled() {
        boolean enabled;

        if (FileUtil.isExistFile(config_path)) {
            HashMap<String, String> config = null;

            try {
                config = new Gson().fromJson(
                    FileUtil.readFile(config_path), ProguardHandler.hashMapStringStringType);
            } finally {
                Object enabledValue;

                enabled = (config != null) && (enabledValue = config.get("enabled")) != null
                    && enabledValue.equals("true");
            }

            return enabled;
        }

        return false;
    }

    public void setStringfogEnabled(boolean enabled) {
        HashMap<String, String> config = new Gson().fromJson(
            FileUtil.readFile(config_path), ProguardHandler.hashMapStringStringType);
        config.put("enabled", Boolean.valueOf(enabled).toString());

        FileUtil.writeFile(config_path, new Gson().toJson(config));
    }
}
