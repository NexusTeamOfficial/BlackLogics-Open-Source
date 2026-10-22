package com.nexusteam.blacklogics.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.nexusteam.blacklogics.manager.ActivityManager;

public class ActivityDebugger {
    
    private static final String TAG = "ActivityDebugger";
    
    public static void debugActivities(Context context, String scId) {
        try {
            Log.d(TAG, "========================================");
            Log.d(TAG, "🔍 DEBUGGING ACTIVITIES FOR sc_id: " + scId);
            Log.d(TAG, "========================================");
            
            String xqPath = "/storage/emulated/0/.blacklogics/data/" + scId + "/xq";
            File xqFile = new File(xqPath);
            Log.d(TAG, "📁 xq file exists: " + xqFile.exists());
            Log.d(TAG, "📁 xq file path: " + xqPath);
            
            if (xqFile.exists()) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(xqFile);
                    byte[] data = new byte[(int) xqFile.length()];
                    fis.read(data);
                    String content = new String(data, "UTF-8");
                    Log.d(TAG, "📄 xq content:\n" + content);
                } finally {
                    if (fis != null) {
                        try { fis.close(); } catch (Exception e) {}
                    }
                }
            }
            
            ActivityManager manager = new ActivityManager();
            manager.load(context, scId);
            
            List<ActivityManager.ViewItem> items = manager.getActivities();
            Log.d(TAG, "📋 Activities from xq manager: " + (items != null ? items.size() : 0));
            
            if (items != null && items.size() > 0) {
                Log.d(TAG, "📋 Activity list:");
                for (int i = 0; i < items.size(); i++) {
                    ActivityManager.ViewItem item = items.get(i);
                    Log.d(TAG, "  [" + i + "] xmlName: " + item.xmlName + 
                              ", javaName: " + item.javaName);
                }
            } else {
                Log.d(TAG, "⚠️ No activities found in xq manager!");
            }
            
            Log.d(TAG, "========================================");
            Log.d(TAG, "✅ DEBUG COMPLETE");
            Log.d(TAG, "========================================");
            
        } catch (Exception e) {
            Log.e(TAG, "❌ Debug failed", e);
        }
    }
}