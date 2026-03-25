package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;

public class kv {

    SharedPreferences f342a;
    SharedPreferences.Editor b;

    public kv(Context context, String str) {
        this.f342a = context.getSharedPreferences(str, 0);
        this.b = this.f342a.edit(); // ✅ initialize here (after f342a is ready)
    }

    public HashMap<String, Object> a() {
        try {
            return (HashMap) this.f342a.getAll();
        } catch (Exception unused) {
            return new HashMap<>();
        }
    }

    public boolean b() {
        return this.b.commit();
    }

    public boolean c() {
        this.b.clear();
        return this.b.commit();
    }

    public boolean a(String str) {
        this.b.remove(str);
        return this.b.commit();
    }

    public boolean b(String str) {
        return this.f342a.contains(str);
    }

    public void a(String str, HashMap<String, Object> hashMap) {
        a(str, (Object) ko.a(hashMap));
    }

    public HashMap<String, Object> c(String str) {
        String d = d(str);
        if (d.isEmpty()) {
            return new HashMap<>();
        }
        return ko.a(d);
    }

    public void a(String str, Object obj, boolean z) {
        if (obj instanceof String) {
            this.b.putString(str, (String) obj);
        } else if (obj instanceof Integer) {
            this.b.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            this.b.putLong(str, ((Long) obj).longValue());
        } else if (obj instanceof Boolean) {
            this.b.putBoolean(str, ((Boolean) obj).booleanValue());
        }
        if (z) {
            this.b.commit();
        }
    }

    public void a(String str, Object obj) {
        a(str, obj, true);
    }

    public String d(String str) {
        return a(str, "");
    }

    public String a(String str, String str2) {
        return this.f342a.getString(str, str2);
    }

    public int e(String str) {
        return a(str, 0);
    }

    public int a(String str, int i) {
        return this.f342a.getInt(str, i);
    }

    public long f(String str) {
        return this.f342a.getLong(str, 0);
    }

    public boolean g(String str) {
        return this.f342a.getBoolean(str, false);
    }

    public boolean a(String str, boolean z) {
        return this.f342a.getBoolean(str, z);
    }
}
