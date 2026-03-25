package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;

public class ko {
    public static String a(HashMap<String, Object> hashMap) {
        return new Gson().toJson(hashMap);
    }

    public static HashMap<String, Object> a(String str) {
        return (HashMap) new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>() {
        }.getType());
    }
}
