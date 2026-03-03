package com.besome.blacklogics.development;

import b.b.b.ar;
import b.b.b.gq;
import b.b.b.pm;
import android.content.Context;
import com.nexusteam.blacklogics.FileUtil;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.nexusteam.blacklogics.generator.source.SourceCodeGenerator;
import com.besome.blacklogics.file.AssetCopyUtil;
import com.besome.blacklogics.file.FileExtractor;
import java.io.File;

public class ProjectBuilder {
    private ar resourceManager;
    private AndroidManifestGenerator manifestManager;
    private gq gradleManager;
    private pm projectManager;
    private SourceCodeGenerator dataManager;
    private Complex complex;
    private String sc_id;
    private String basePath;
    private String projectPath;
    private String packageName;
    private Context context;
    
    public ProjectBuilder(Context context, Complex complex, String basePath, String sc_id) {
        this.context = context;
        this.complex = complex;
        this.basePath = basePath;
        this.sc_id = sc_id;
        
        this.resourceManager = new ar();
        this.resourceManager.load(context, sc_id);
        
        this.manifestManager = new AndroidManifestGenerator();
        this.manifestManager.load(context, sc_id);
        
        this.gradleManager = new gq();
        this.gradleManager.load(context, sc_id);
        

        this.projectManager = new pm();
        this.projectManager.load(context, sc_id);
        
        this.packageName = projectManager.getPackageName();
        String packagePath = packageName.replace('.', '/');
        
        this.projectPath = basePath + "/.blacklogics/mysc/" + sc_id + "/";
        

        this.dataManager = new SourceCodeGenerator(context, sc_id);
        this.dataManager.setXmlOutPut(projectPath + "app/src/main/res/layout/");
        this.dataManager.setOutPut(projectPath + "app/src/main/java/" + packagePath + "/");
    }
    
    public void setContext(Context context) {
        this.context = context;
    }
    
    public void buildProject() {
        createDirectories();
        extractManifest();
        generateDebug();
        dataManager.extractAllJavaCodes();
        extractJavaFiles();
        dataManager.extractAndSaveAllXmlCodes();
        extractGradleFiles();
        extractProGuardRules();
        extractResources();
    }
    
    private void createDirectories() {
        String packagePath = packageName.replace('.', '/');
        
        String[] directories = {
            "app/",
            "app/src/",
            "app/src/main/",
            "app/src/main/java/" + packagePath + "/",
            "app/src/main/res/",
            "app/src/main/res/drawable/",
            "app/src/main/res/layout/",
            "app/src/main/res/values/",
            "app/src/main/res/values-v21/",
            "app/src/main/res/mipmap/",
            "app/src/main/assets/",
            "app/libs/",
            "gradle/"
        };
        
        for (String dir : directories) {
            File directory = new File(projectPath + dir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }
    }
    
    private void extractManifest() {
        manifestManager.extractManifest(sc_id, projectPath + "app/src/main/");
    }
    
    private void extractJavaFiles() {
        try {
            String packagePath = packageName.replace('.', File.separatorChar);
            
            File logicFile = new File(basePath + "/.blacklogics/data/" + sc_id + "/root_logic");
            if (logicFile.isFile() && logicFile.length() > 0) {
                complex.extractAllLogicsFromJson(logicFile.getAbsolutePath());
            }
            
            String javaOutputPath = projectPath + "app/src/main/java/" + packagePath + File.separator;
            complex.extractAllJavaCodes(javaOutputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void extractXmlFiles() {
        complex.extractAllXmlCodes(projectPath + "app/src/main/res/layout/");
    }
    
    private void extractGradleFiles() {

        gradleManager.extractGradleFiles(sc_id, projectPath);
        complex.extractGradleBuildConfig(projectPath + "app/");
        complex.extractGradleSettings(projectPath);
    }
    
    private void extractProGuardRules() {
        complex.extractProGuardRules(projectPath + "app/");
    }
    
    private void extractResources() {
        complex.extractAllResources(projectPath + "app/src/main/res/");
        copyAssets();
        resourceManager.extractResources(sc_id, projectPath + "app/src/main/res/");
        if ("include ':app'".equals(complex.getSettingsGradle())) {
            complex.settingsGradle(null);
        }
    }
    
    private void copyAssets() {
        if (!FileUtil.isExistFile(basePath + "/.blacklogics/mysc/" + sc_id + "/app/src/main/res/mipmap/icon")) {
            AssetCopyUtil.copyAssetsToExternalStorage(context, "icon", ".blacklogics/mysc/" + sc_id + "/app/src/main/res/mipmap/");
        }
        AssetCopyUtil.copyAssetsToExternalStorage(context, "default_image", ".blacklogics/mysc/" + sc_id + "/app/src/main/res/mipmap/");
    }
    
    public void generateDebug() {
        String packagePath = packageName.replace('.', '/');
        String outputPath = projectPath + "app/src/main/java/" + packagePath + File.separator;
        
        FileExtractor extractor = new FileExtractor(context, packageName);
        extractor.extractAndReplace(outputPath);
    }
    
}
