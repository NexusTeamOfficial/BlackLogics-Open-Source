package com.nexusteam.internal;

import org.json.JSONObject;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ProjectFileParser {

    private static final String AES_KEY   = "sketchwaresecure";
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String CHARSET   = "UTF-8";

    private static final String R_SC_ID       = "sc_id";
    private static final String R_WS_NAME     = "my_ws_name";
    private static final String R_APP_NAME    = "my_app_name";
    private static final String R_PKG_NAME    = "my_sc_pkg_name";
    private static final String R_VER_CODE    = "sc_ver_code";
    private static final String R_VER_NAME    = "sc_ver_name";
    private static final String R_CUSTOM_ICON = "custom_icon";
    private static final String R_C_ACCENT    = "color_accent";
    private static final String R_C_PRIMARY   = "color_primary";
    private static final String R_C_PRI_DARK  = "color_primary_dark";
    private static final String R_C_HIGHLIGHT = "color_control_highlight";
    private static final String R_C_NORMAL    = "color_control_normal";


    public static ProjectModel parse(String projectFilePath) throws Exception {
        byte[] encrypted = readFile(projectFilePath);
        String jsonString = decrypt(encrypted);
        JSONObject raw = new JSONObject(jsonString);
        return mapToModel(raw);
    }

    public static void save(ProjectModel model, String projectFilePath) throws Exception {
        JSONObject raw = mapToRaw(model);
        byte[] encrypted = encrypt(raw.toString());
        writeFile(projectFilePath, encrypted);
    }


    private static ProjectModel mapToModel(JSONObject raw) throws Exception {
        ProjectModel model = new ProjectModel();
        String scId = raw.optString(R_SC_ID, "");

        model.setScId(scId);
        model.setProjectName(raw.optString(R_WS_NAME, ""));
        model.setAppName(raw.optString(R_APP_NAME, ""));
        model.setPackageName(raw.optString(R_PKG_NAME, ""));
        model.setVersionCode(raw.optString(R_VER_CODE, "1"));
        model.setVersionName(raw.optString(R_VER_NAME, "1.0"));
        model.setColorAccent(intToHex(raw.optDouble(R_C_ACCENT, -16777216)));
        model.setColorPrimary(intToHex(raw.optDouble(R_C_PRIMARY, -16777216)));
        model.setColorPrimaryDark(intToHex(raw.optDouble(R_C_PRI_DARK, -16777216)));
        model.setColorPrimaryHighlight(intToHex(raw.optDouble(R_C_HIGHLIGHT, -16777216)));
        model.setColorPrimaryNormal(intToHex(raw.optDouble(R_C_NORMAL, -16777216)));

        boolean customIcon = raw.optBoolean(R_CUSTOM_ICON, false);
        if (customIcon && scId.length() > 0) {
            model.setIconPath("/storage/emulated/0/.sketchware/mysc/list/" + scId + "/icon");
        } else {
            model.setIconPath("default_icon.png");
        }

        return model;
    }

    private static JSONObject mapToRaw(ProjectModel model) throws Exception {
        JSONObject raw = new JSONObject();
        raw.put(R_SC_ID,       model.getScId());
        raw.put(R_WS_NAME,     model.getProjectName());
        raw.put(R_APP_NAME,    model.getAppName());
        raw.put(R_PKG_NAME,    model.getPackageName());
        raw.put(R_VER_CODE,    model.getVersionCode());
        raw.put(R_VER_NAME,    model.getVersionName());
        raw.put(R_C_ACCENT,    hexToInt(model.getColorAccent()));
        raw.put(R_C_PRIMARY,   hexToInt(model.getColorPrimary()));
        raw.put(R_C_PRI_DARK,  hexToInt(model.getColorPrimaryDark()));
        raw.put(R_C_HIGHLIGHT, hexToInt(model.getColorPrimaryHighlight()));
        raw.put(R_C_NORMAL,    hexToInt(model.getColorPrimaryNormal()));
        raw.put(R_CUSTOM_ICON, model.hasCustomIcon());
        return raw;
    }


    public static String intToHex(double colorDouble) {
        int color = (int) colorDouble;
        return String.format("#%08X", color);
    }

    public static int hexToInt(String hex) {
        try {
            if (hex == null) return 0xFF000000;
            if (hex.startsWith("#")) hex = hex.substring(1);
            if (hex.length() == 6) hex = "FF" + hex;
            return (int) Long.parseLong(hex, 16);
        } catch (Exception e) {
            return 0xFF000000;
        }
    }


    private static String decrypt(byte[] encrypted) throws Exception {
        byte[] keyBytes = AES_KEY.getBytes(CHARSET);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,
                new SecretKeySpec(keyBytes, "AES"),
                new IvParameterSpec(keyBytes));
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, CHARSET);
    }

    private static byte[] encrypt(String text) throws Exception {
        byte[] keyBytes = AES_KEY.getBytes(CHARSET);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,
                new SecretKeySpec(keyBytes, "AES"),
                new IvParameterSpec(keyBytes));
        return cipher.doFinal(text.getBytes(CHARSET));
    }


    private static byte[] readFile(String path) throws Exception {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }

    private static void writeFile(String path, byte[] data) throws Exception {
        File file = new File(path);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.flush();
        fos.close();
    }
}
