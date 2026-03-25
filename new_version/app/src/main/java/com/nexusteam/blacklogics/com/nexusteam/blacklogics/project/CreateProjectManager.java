package com.nexusteam.blacklogics.project;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;

import com.besome.blacklogics.development.Complex;
import com.nexusteam.blacklogics.security.crypto.FileEncryptionUtil;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

import org.json.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import a.a.a.*;
import b.b.b.ar;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import b.b.b.rs;
import com.nexusteam.blacklogics.generator.source.model.ActivityStructureRegistry;
import b.b.b.pm;
import b.b.b.gq;

public class CreateProjectManager {

    private final Context context;
    private final char[] cryptoPassword;
    private int selectedColorAccent = Color.parseColor("#FF4081");
    private int selectedColorPrimary = Color.parseColor("#3F51B5");
    private int selectedColorPrimaryDark = Color.parseColor("#303F9F");
    private int selectedColorPrimaryHighlight = Color.parseColor("#FF9800");
    private int selectedColorPrimaryNormal = Color.parseColor("#2196F3");
    private ar resourceManager;

    public CreateProjectManager(Context context, char[] cryptoPassword) {
        this.context = context;
        this.cryptoPassword = cryptoPassword;
        initializeResourceManager();
    }
    
    public void setVariables(int selectedColorAccent, int selectedColorPrimary, int selectedColorPrimaryDark, int selectedColorPrimaryHighlight, int selectedColorPrimaryNormal) {
        this.selectedColorAccent = selectedColorAccent;
        this.selectedColorPrimary = selectedColorPrimary;
        this.selectedColorPrimaryDark = selectedColorPrimaryDark;
        this.selectedColorPrimaryHighlight = selectedColorPrimaryHighlight;
        this.selectedColorPrimaryNormal = selectedColorPrimaryNormal;
    }

    
    public File createOrUpdateProject(
            boolean isUpdate,
            File originalProjectDir,
            String scId,
            String projectName,
            String packageName,
            String appName,
            int versionCode,
            String versionName,
            Map<String, String> mainActivityAttrs,
            JSONObject extraConfig
    ) throws Exception {

        File projectDir = isUpdate
                ? originalProjectDir
                : new File(TheBlockLogicsUtil.mysc + scId);

        if (!isUpdate && !projectDir.exists()) {
            projectDir.mkdirs();
        }

        pm projectManager = new pm()
                .setProjectId(scId)
                .setPackageName(packageName)
                .setProjectName(projectName)
                .setScName(projectName)
                .setMinSdk(21)
                .setTargetSdk(34)
                .setVersionCode(versionCode)
                .setVersionName(versionName);

        if (isUpdate) {
            projectManager.load(context, scId);
        }
        projectManager.save(context);

        AndroidManifestGenerator manifest = new AndroidManifestGenerator();
        if (isUpdate) {
            manifest.load(context, scId);
        }
        
        if (resourceManager != null) {
           resourceManager.save(context, scId);
        }

        manifest.setPackageName(packageName)
                .setAppName(appName)
                .setThemeColor("AppTheme")
                .setAppIcon("@mipmap/icon")
                   .setAndroidX(false)
                .addActivity(".MainActivity", mainActivityAttrs)
                .save(context, scId);


        if (!isUpdate) {
            new ActivityStructureRegistry()
                    .addActivity("MainActivity", "main")
                    .save(context, scId);
        }

        new rs(scId)
                .setMinSdk(21)
                .setTargetSdk(34)
                .setResourceEncrypt(false)
                .save(context);

        
        new gq()
                .setApplicationId(packageName)
                .setMinSdk(21)
                .setTargetSdk(34)
                .setCompileSdk(34)
                .setVersionCode(versionCode)
                .setVersionName(versionName)
                .save(context, scId);

        Complex complex = new Complex();
        complex.setC(context);
        complex.setId(scId);
        complex.setAcName("MainActivity");
        complex.setXName("main");
        complex.setPkgName(packageName);
        complex.setProjectName(projectName);
        complex.enableToolBar("MainActivity", false, true);

        saveEncryptedConfig(projectDir, extraConfig);

        return projectDir;
    }
    
    private void initializeResourceManager() {
        resourceManager = new ar().setAndroidX(false);
        

        updateResourceColors();
        

        resourceManager.setStyle("AppTheme", "@android:style/Theme.Material.Light");
        resourceManager.setStyle("FullScreen", "@android:style/Theme.Material.Light.NoActionBar.Fullscreen");
        resourceManager.setStyle("NoActionBar", "@android:style/Theme.Material.Light.NoActionBar");
        resourceManager.setStyle("NoStatusBar", "AppTheme");
        
        resourceManager.setDimen("app_margin", "16dp");
        resourceManager.setDimen("button_corner", "8dp");
        resourceManager.setDimen("text_size", "14sp");
    }
    
    private void updateResourceColors() {
        if (resourceManager != null) {
            resourceManager.setColor("colorPrimary", String.format("#%06X", (0xFFFFFF & selectedColorPrimary)));
            resourceManager.setColor("colorPrimaryDark", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryDark)));
            resourceManager.setColor("colorAccent", String.format("#%06X", (0xFFFFFF & selectedColorAccent)));
            resourceManager.setColor("colorControlHighlight", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryHighlight)));
            resourceManager.setColor("colorControlNormal", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryNormal)));
            resourceManager.setColor("colorBackground", "#F5F5F5");
        }
    }

    private void saveEncryptedConfig(File projectDir, JSONObject json) throws Exception {
    if (json == null) return;

    File outFile = new File(projectDir, "config.enc");

    java.io.FileOutputStream fos = new java.io.FileOutputStream(outFile);
    fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
    fos.flush();
    fos.close();
}
public JSONObject loadEncryptedConfig(File projectDir) throws Exception {
    File file = new File(projectDir, "config.enc");
    if (!file.exists()) return null;

    java.io.FileInputStream fis = new java.io.FileInputStream(file);
    byte[] data = new byte[(int) file.length()];
    fis.read(data);
    fis.close();

    return new JSONObject(new String(data, StandardCharsets.UTF_8));
}

}