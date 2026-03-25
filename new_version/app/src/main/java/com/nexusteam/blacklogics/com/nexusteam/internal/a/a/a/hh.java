package com.nexusteam.internal;

import java.util.HashMap;

public class hh {

    /* renamed from: a  reason: collision with root package name */
    HashMap<String, hi> f261a = new HashMap<>();
    private hi b = new hi("resources");

    public void a(String str, int i) {
        hi hiVar = new hi("integer", true);
        hiVar.a("", "name", str);
        hiVar.a(String.valueOf(i));
        this.b.a(hiVar);
    }

    public void a(String str, String str2) {
        a(str, str2, false);
    }

    public void a(String str, String str2, boolean z) {
        hi hiVar = new hi("string", true);
        hiVar.a("", "name", str);
        if (!z) {
            hiVar.a("", "translatable", "false");
        }
        String trim = str2.trim();
        if (trim.length() > 0) {
            if (trim.charAt(0) == '\"') {
                trim = trim.substring(1);
            }
            if (trim.charAt(trim.length() - 1) == '\"') {
                trim = trim.substring(0, trim.length() - 1);
            }
        }
        hiVar.a(trim);
        this.b.a(hiVar);
    }

    public void b(String str, String str2) {
        hi hiVar = new hi("color", true);
        hiVar.a("", "name", str);
        hiVar.a(str2);
        this.b.a(hiVar);
    }

    public void c(String str, String str2) {
        hi hiVar = new hi("style", true);
        hiVar.a("", "name", str);
        if (str2.length() > 0) {
            hiVar.a("", "parent", str2);
        }
        this.b.a(hiVar);
        this.f261a.put(str, hiVar);
    }

    public void a(String str, String str2, String str3) {
        hi hiVar = this.f261a.get(str);
        if (hiVar != null) {
            hi hiVar2 = new hi("item", true);
            hiVar2.a("", "name", str2);
            hiVar2.a(str3);
            hiVar.a(hiVar2);
        }
    }

    public String a() {
        return this.b.a();
    }
}
