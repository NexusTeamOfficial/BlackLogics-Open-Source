package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class gz {

    /* renamed from: a  reason: collision with root package name */
    private static gz f246a = null;
    private static int c = 10;
    private HashMap<String, ArrayList<String>> b;
    private kv d;

    private gz() {
    }

    public static gz a() {
        if (f246a == null) {
            synchronized (kq.class) {
                if (f246a == null) {
                    f246a = new gz();
                }
            }
        }
        return f246a;
    }

    public void a(Context context) {
        if (this.b == null) {
            this.b = new HashMap<>();
        }
        this.b.clear();
        if (this.d == null) {
            this.d = new kv(context, "P26");
        }
    }

    public ArrayList<String> a(String str) {
        return this.b.get(str);
    }

    public void b(String str) {
        if (this.b.get(str) == null) {
            String[] split = this.d.d(str).split(",");
            int length = split.length;
            while (true) {
                length--;
                if (length < 0) {
                    return;
                }
                if (!split[length].isEmpty()) {
                    a(str, split[length]);
                }
            }
        }
    }

    public void a(String str, String str2) {
        ArrayList arrayList = this.b.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.b.put(str, arrayList);
        }
        if (arrayList.contains(str2)) {
            arrayList.remove(str2);
        }
        arrayList.add(0, str2);
        if (arrayList.size() > c) {
            arrayList.remove(arrayList.size() - 1);
        }
    }

    public void b() {
        String str = "";
        for (String next : this.b.keySet()) {
            Iterator it = this.b.get(next).iterator();
            while (it.hasNext()) {
                str = str + ((String) it.next()) + ",";
            }
            this.d.a(next, (Object) str);
        }
    }
}
