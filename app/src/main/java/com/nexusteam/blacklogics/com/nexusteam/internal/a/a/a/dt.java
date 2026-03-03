package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;

public class dt {

    /* renamed from: a  reason: collision with root package name */
    kv f96a;

    public dt(Context context) {
        this.f96a = new kv(context, "P4");
    }

    public String a() {
        return this.f96a.d("P4I2");
    }

    public String b() {
        return this.f96a.d("P4I1");
    }

    public long c() {
        return this.f96a.f("P4I4");
    }

    public String d() {
        return this.f96a.d("P4I0");
    }

    public String e() {
        return this.f96a.d("P4I3");
    }

    public String f() {
        return this.f96a.d("P4I6");
    }

    public String g() {
        return this.f96a.d("P4I7");
    }

    public boolean h() {
        String a2 = a();
        if (a2.equals("subs_subscribe_02")) {
            return true;
        }
        long c = c();
        boolean z = false;
        for (String equals : fe.G) {
            if (equals.equals(a2) && c > 0) {
                z = true;
            }
        }
        return z;
    }

    public long a(String str, long j) {
        for (int i = 0; i < fe.G.length; i++) {
            if (fe.G[i].equals(str)) {
                return j + fe.H[i];
            }
        }
        return 0;
    }

    public long a(ea eaVar) {
        return a(eaVar.c(), eaVar.d());
    }

    public void a(ea eaVar, String str, String str2) {
        long d = eaVar.d();
        this.f96a.a("P4I2", eaVar.c(), false);
        this.f96a.a("P4I7", eaVar.g(), false);
        this.f96a.a("P4I6", eaVar.a(), false);
        this.f96a.a("P4I4", Long.valueOf(d), false);
        this.f96a.a("P4I3", str, false);
        this.f96a.a("P4I0", eaVar.b(), false);
        this.f96a.a("P4I1", str2, false);
        this.f96a.b();
    }

    public void i() {
        this.f96a.c();
    }
}
