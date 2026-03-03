
package com.nexusteam.blacklogics.utils;

import android.os.Environment;

import com.google.gson.Gson;
import com.nexusteam.blacklogics.bean.BackupResult;
import com.nexusteam.blacklogics.model.BackupSettings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.nexusteam.blacklogics.utils.FileUtil;
import com.besome.blacklogics.util.FileUtils;

public class BackupFactory {
    
    private final String projectId;
    private boolean backupLocalLibs;
    private boolean backupCustomBlocks;
    
    public BackupFactory(String projectId) {
        this.projectId = projectId;
    }
    
    public BackupResult backup(String projectName) {
        BackupResult result = new BackupResult();
        
        try {
            createBackupsFolder();
            
            File outFolder = new File(BackupSettings.getBackupDir(), projectName);
            File outZip = new File(BackupSettings.getBackupDir(), 
            BackupSettings.generateBackupFileName(projectName));
            
            if (outFolder.exists() || outZip.exists()) {
                return backup(projectName + "_d");
            }
            
            FileUtil.makeDir(outFolder.getAbsolutePath());
            

            File dataF = new File(outFolder, "data");
            FileUtil.makeDir(dataF.getAbsolutePath());
            FileUtils.copySafe(getDataDir(), dataF);
            

            File resF = new File(outFolder, "resources");
            FileUtil.makeDir(resF.getAbsolutePath());
            
            for (String subfolder : BackupSettings.getResourceSubfolders()) {
                File resSubf = new File(resF, subfolder);
                FileUtil.makeDir(resSubf.getAbsolutePath());
                FileUtils.copySafe(getResDir(subfolder), resSubf);
                
                if (!subfolder.equals("icons")) {
                    FileUtils.createNomediaFileIn(resSubf);
                }
            }
            
            File sourceConfigEnc = new File(getProjectPath(), "config.enc");
            if (!sourceConfigEnc.exists()) {
                throw new RuntimeException("config.enc not found in project");
            }
            
            File backupConfigEnc = new File(outFolder, "config.enc");
            FileUtils.copy(sourceConfigEnc, backupConfigEnc);
            
            

            if (backupLocalLibs) {
                File localLibs = getLocalLibsPath();
                if (localLibs.exists()) {
                    try {
                        JSONArray ja = new JSONArray(FileUtil.readFile(localLibs.getAbsolutePath()));
                        File libsF = new File(outFolder, "local_libs");
                        libsF.mkdirs();
                        
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            File f = new File(jo.getString("dexPath")).getParentFile();
                            FileUtils.copy(f, new File(libsF, f.getName()));
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
            

            FileUtils.zipFolder(outFolder, outZip);
            

            FileUtil.deleteFile(outFolder.getAbsolutePath());
            
            result.setSuccess(true);
            result.setMessage("Backup created successfully");
            result.setOutputFile(outZip);
            
        } catch (Exception e) {
            result.setSuccess(false);
            result.setError(e.getMessage());
        }
        
        return result;
    }
    
    public BackupResult restore(File swbPath) {
        BackupResult result = new BackupResult();
        
        try {
            createBackupsFolder();
            
            String name = swbPath.getName();
            if (name.contains(".")) {
                name = name.substring(0, name.lastIndexOf("."));
            }
            
            return restoreInternal(swbPath, name, result);
            
        } catch (Exception e) {
            result.setSuccess(false);
            result.setError("Restoration failed: " + e.getMessage());
            return result;
        }
    }
    
    private BackupResult restoreInternal(File swbPath, String name, BackupResult result) {
        File outFolder = new File(BackupSettings.getBackupDir(), name);
        
        if (outFolder.exists()) {
            return restoreInternal(swbPath, name + "_d", result);
        }
        
        if (!FileUtils.unzip(swbPath, outFolder)) {
            result.setSuccess(false);
            result.setError("Couldn't unzip the backup");
            return result;
        }
        

        File data = new File(outFolder, "data");
        File res = new File(outFolder, "resources");
        

        FileUtils.copy(data, getDataDir());
        

        for (String subfolder : BackupSettings.getResourceSubfolders()) {
            File subf = new File(res, subfolder);
            FileUtils.copySafe(subf, getResDir(subfolder));
        }
        

        


        File backupConfigEnc = new File(outFolder, "config.enc");
        
        if (!backupConfigEnc.exists()) {
            result.setSuccess(false);
            result.setError("config.enc not found in backup");
            return result;
        }
        

        File projectDir = getProjectPath();
        if (!projectDir.exists()) {
            projectDir.mkdirs();
        }
        
        File targetConfigEnc = new File(projectDir, "config.enc");
        

        if (targetConfigEnc.exists()) {
            FileUtil.deleteFile(targetConfigEnc.getAbsolutePath());
        }
        

        FileUtils.copy(backupConfigEnc, targetConfigEnc);
        
        
        

        if (backupLocalLibs) {
            File localLibs = new File(outFolder, "local_libs");
            if (localLibs.exists()) {
                File[] localLibsContent = localLibs.listFiles();
                if (localLibsContent != null) {
                    for (File localLib : localLibsContent) {
                        File localLibRealPath = new File(BackupSettings.getAllLocalLibsDir(), localLib.getName());
                        if (!localLibRealPath.exists()) {
                            localLibRealPath.mkdirs();
                            FileUtils.copy(localLib, localLibRealPath);
                        }
                    }
                }
            }
        }
        

        FileUtil.deleteFile(outFolder.getAbsolutePath());
        
        result.setSuccess(true);
        result.setMessage("Restored successfully");
        return result;
    }
    
    public void setBackupLocalLibs(boolean backupLocalLibs) {
        this.backupLocalLibs = backupLocalLibs;
    }
    
    public void setBackupCustomBlocks(boolean backupCustomBlocks) {
        this.backupCustomBlocks = backupCustomBlocks;
    }
    
    private void createBackupsFolder() {
        FileUtil.makeDir(BackupSettings.getBackupDir());
    }
    
    private File getDataDir() {
        return new File("/storage/emulated/0/",
        ".blacklogics/data/" + projectId);
    }
    
    private File getResDir(String subfolder) {
        return new File("/storage/emulated/0/",
        ".blacklogics/resources/" + subfolder + "/" + projectId);
    }
    
    private File getProjectPath() {
        return new File("/storage/emulated/0/",
        ".blacklogics/mysc/list/" + projectId);
    }
    
    private File getLocalLibsPath() {
        return new File("/storage/emulated/0/",
        ".blacklogics/data/" + projectId + "/local_library");
    }
    

    public static HashMap<String, Object> getProject(File file) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] key = "blacklogicssecure".getBytes();
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(key));
            
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            byte[] encrypted = new byte[(int) raf.length()];
            raf.readFully(encrypted);
            raf.close();
            
            byte[] decrypted = cipher.doFinal(encrypted);
            String decryptedString = new String(decrypted);
            
            return new Gson().fromJson(decryptedString.trim(), Helper.TYPE_MAP);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static boolean writeEncrypted(File file, String string) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] key = "blacklogicssecure".getBytes();
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(key));
            
            byte[] encrypted = cipher.doFinal(string.trim().getBytes());
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.setLength(0);
            raf.write(encrypted);
            raf.close();
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static String getBackupDir() {
        return BackupSettings.getBackupDir();
    }
    
    public static boolean zipContainsFile(String zipPath, String fileName) {
        return FileUtils.zipContainsFile(zipPath, fileName);
    }
    
    public static final String EXTENSION = BackupSettings.EXTENSION;
}
