package com.besome.blacklogics.logic.editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;

public class JsonUtil {
    public static HashMap<String, Object> readJSON(String str) {
        return new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>(){}.getType());
    }

    public static String writeJSON(HashMap<String, Object> hashMap) {
        return new Gson().toJson(hashMap);
    }
}
