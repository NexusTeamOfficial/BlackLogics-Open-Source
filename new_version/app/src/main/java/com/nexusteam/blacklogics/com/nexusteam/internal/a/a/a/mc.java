package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class mc {

    /* renamed from: a  reason: collision with root package name */
    private static kv f374a;

    public static void a(Context context, boolean z) {
        if (f374a == null) {
            f374a = new kv(context, "P15");
        }
    }

    public static void a() {
        for (String next : f374a.a().keySet()) {
            a(next, f374a.c(next));
        }
        f374a.c();
    }

    public static ArrayList<HashMap<String, Object>> b() {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        kk kkVar = new kk();
        File[] listFiles = new File(fe.d()).listFiles();
        if (listFiles == null) {
            return arrayList;
        }
        for (File file : listFiles) {
            try {
                if (new File(file, "project").exists()) {
                    HashMap<String, Object> a2 = ko.a(kkVar.b(kkVar.f(file.getAbsolutePath() + File.separator + "project")));
                    if (kr.c(a2, "sc_id").equals(file.getName())) {
                        arrayList.add(a2);
                    }
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            }
        }
        return arrayList;
    }

    public static String c() {
        int parseInt = Integer.parseInt("600") + 1;
        Iterator<HashMap<String, Object>> it = b().iterator();
        while (it.hasNext()) {
            parseInt = Math.max(parseInt, Integer.parseInt(kr.c(it.next(), "sc_id")) + 1);
        }
        return String.valueOf(parseInt);
    }

    public static String d() {
        ArrayList<HashMap<String, Object>> b = b();
        ArrayList arrayList = new ArrayList();
        Iterator<HashMap<String, Object>> it = b.iterator();
        while (it.hasNext()) {
            String c = kr.c(it.next(), "my_ws_name");
            if (c.equals("NewProject")) {
                arrayList.add(1);
            } else if (c.indexOf("NewProject") == 0) {
                try {
                    arrayList.add(Integer.valueOf(Integer.parseInt(c.substring(10))));
                } catch (Exception unused) {
                }
            }
        }
        Collections.sort(arrayList, new a());
        int i = 0;
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            int intValue = ((Integer) it2.next()).intValue();
            int i2 = i + 1;
            if (intValue != i2) {
                if (intValue != i) {
                    break;
                }
            } else {
                i = i2;
            }
        }
        if (i == 0) {
            return "NewProject";
        }
        return "NewProject" + (i + 1);
    }

    public static HashMap<String, Object> a(String str) {
        kk kkVar = new kk();
        HashMap<String, Object> hashMap = null;
        try {
            String b = fe.b(str);
            if (!new File(b).exists()) {
                return null;
            }
            HashMap<String, Object> a2 = ko.a(kkVar.b(kkVar.f(b + File.separator + "project")));
            try {
                if (!kr.c(a2, "sc_id").equals(str)) {
                    return null;
                }
                return a2;
            } catch (Exception e) {

                hashMap = a2;
                Log.e("ERROR", e.getMessage(), e);
                return hashMap;
            }
        } catch (Exception e2) {

            Log.e("ERROR", e2.getMessage(), e2);
            return hashMap;
        }
    }

    public static HashMap<String, Object> b(String str) {
        Iterator<HashMap<String, Object>> it = b().iterator();
        while (it.hasNext()) {
            HashMap<String, Object> next = it.next();
            if (kr.c(next, "my_sc_pkg_name").equals(str) && kr.a(next, "proj_type") == 1) {
                return next;
            }
        }
        return null;
    }

    public static void a(String str, HashMap<String, Object> hashMap) {
        File file = new File(fe.d());
        if (!file.exists()) {
            file.mkdirs();
        }
        String str2 = fe.b(str) + File.separator + "project";
        String a2 = ko.a(hashMap);
        kk kkVar = new kk();
        try {
            kkVar.a(str2, kkVar.g(a2));
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }
    }

    public static void b(String str, HashMap<String, Object> hashMap) {
        File file = new File(fe.b(str));
        if (file.exists()) {
            String str2 = file + File.separator + "project";
            kk kkVar = new kk();
            try {
                HashMap<String, Object> a2 = ko.a(kkVar.b(kkVar.f(str2)));
                if (kr.c(a2, "sc_id").equals(str)) {
                    if (hashMap.containsKey("custom_icon")) {
                        a2.put("custom_icon", hashMap.get("custom_icon"));
                    }
                    if (hashMap.containsKey("proj_type")) {
                        a2.put("proj_type", hashMap.get("proj_type"));
                    }
                    if (hashMap.containsKey("published_dt")) {
                        a2.put("published_dt", hashMap.get("published_dt"));
                    }
                    a2.put("my_sc_pkg_name", hashMap.get("my_sc_pkg_name"));
                    a2.put("my_ws_name", hashMap.get("my_ws_name"));
                    a2.put("my_app_name", hashMap.get("my_app_name"));
                    a2.put("sc_ver_code", hashMap.get("sc_ver_code"));
                    a2.put("sc_ver_name", hashMap.get("sc_ver_name"));
                    a2.put("sketchware_ver", hashMap.get("sketchware_ver"));
                    a2.put("color_accent", hashMap.get("color_accent"));
                    a2.put("color_primary", hashMap.get("color_primary"));
                    a2.put("color_primary_dark", hashMap.get("color_primary_dark"));
                    a2.put("color_control_highlight", hashMap.get("color_control_highlight"));
                    a2.put("color_control_normal", hashMap.get("color_control_normal"));
                    kkVar.a(str2, kkVar.g(ko.a(a2)));
                }
            } catch (Exception e) {
                Log.e("DEBUG", e.getMessage(), e);
            }
        }
    }

    public static void a(Context context, String str) {
        File file = new File(fe.b(str));
        if (file.exists()) {
            kk kkVar = new kk();
            kkVar.a(file);
            kkVar.d(fe.a(str));
            kkVar.d(fe.r() + File.separator + str);
            kkVar.d(fe.s() + File.separator + str);
            kkVar.d(fe.t() + File.separator + str);
            kkVar.d(fe.q() + File.separator + str);
            kkVar.d(fe.d(str));
            kkVar.d(fe.e(str));
            new kv(context, "D01_" + str).c();
            new kv(context, "D02_" + str).c();
            new kv(context, "D03_" + str).c();
            new kv(context, "D04_" + str).c();
        }
    }

    static class a implements Comparator<Integer> {
        a() {
        }

        /* renamed from: a */
        public int compare(Integer num, Integer num2) {
            return num.compareTo(num2);
        }
    }
}
