package com.besome.blacklogics.file;
 
import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileExtractor {
    
    private Context context;
    private String targetPackageName;
    
    public FileExtractor(Context context, String targetPackageName) {
        this.context = context;
        this.targetPackageName = targetPackageName;
    }
    
    public void extractAndReplace(String outputDirPath) {
        try {

            String assetsPath = "debug";
            AssetManager assetManager = context.getAssets();
            

            String[] files = assetManager.list(assetsPath);
            
            if (files != null && files.length > 0) {
                for (String fileName : files) {
                    if (fileName.endsWith(".java")) {
                        extractFile(assetsPath + "/" + fileName, outputDirPath, fileName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void extractFile(String assetFilePath, String outputDirPath, String fileName) {
        try {

            InputStream inputStream = context.getAssets().open(assetFilePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            inputStream.close();
            

            String modifiedContent = content.toString().replace("%PACKAGE_NAME%", targetPackageName);
            

            File outputDir = new File(outputDirPath);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            

            File outputFile = new File(outputDir, fileName);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(modifiedContent.getBytes());
            outputStream.close();
            
            System.out.println("File created: " + outputFile.getAbsolutePath());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}