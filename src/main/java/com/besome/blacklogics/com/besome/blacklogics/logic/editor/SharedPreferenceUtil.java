package com.besome.blacklogics.logic.editor;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;
import java.util.HashMap;

public class SharedPreferenceUtil {
    Context context;
    Editor editor;// = this.sharedPref.edit();
    SharedPreferences sharedPref;

    public SharedPreferenceUtil(Context context, String str) {
        this.context = context;
        this.sharedPref = context.getSharedPreferences(str, 0);
        this.editor = this.sharedPref.edit();
    }

    public boolean clear(String str) {
        this.editor.remove(str);
        return this.editor.commit();
    }

    public boolean clearAll() {
        this.editor.clear();
        return this.editor.commit();
    }

    public boolean commit() {
        return this.editor.commit();
    }

    public boolean containKey(String str) {
        return this.sharedPref.contains(str);
    }

    public boolean getBoolean(String str) {
        return this.sharedPref.getBoolean(str, false);
    }

    public boolean getBoolean(String str, boolean z) {
        return this.sharedPref.getBoolean(str, z);
    }

    public int getInt(String str) {
        return getInt(str, 0);
    }

    public int getInt(String str, int i) {
        return this.sharedPref.getInt(str, i);
    }

    public long getLong(String str) {
        return this.sharedPref.getLong(str, 0);
    }

    public String getString(String str) {
        return getString(str, "");
    }

    public String getString(String str, String str2) {
        return this.sharedPref.getString(str, str2);
    }

    public void putObject(String str, Object obj) {
        putObject(str, obj, true);
    }

    public void putObject(String str, Object obj, boolean z) {
        if (obj instanceof String) {
            this.editor.putString(str, (String) obj);
        } else if (obj instanceof Integer) {
            this.editor.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            this.editor.putLong(str, ((Long) obj).longValue());
        } else if (obj instanceof Boolean) {
            this.editor.putBoolean(str, ((Boolean) obj).booleanValue());
        }
        if (z) {
            this.editor.commit();
        }
    }

    public HashMap<String, Object> readJSON(String str) {
        String string = getString(str);
        return string.isEmpty() ? new HashMap() : JsonUtil.readJSON(string);
    }

    public HashMap<String, Object> readState() {
        try {
            return (HashMap) this.sharedPref.getAll();
        } catch (Throwable e) {
            Log.e(getClass().getSimpleName(), e.getMessage(), e);
            return new HashMap();
        }
    }

    public void setListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.sharedPref.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public void writeJSON(String str, HashMap<String, Object> hashMap) {
        putObject(str, JsonUtil.writeJSON(hashMap));
    }

    public boolean writeState(HashMap<String, Object> hashMap) {
        try {
            for (String str : hashMap.keySet()) {
                putObject(str, hashMap.get(str));
            }
            this.editor.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            this.editor.clear();
            return false;
        }
    }
}
