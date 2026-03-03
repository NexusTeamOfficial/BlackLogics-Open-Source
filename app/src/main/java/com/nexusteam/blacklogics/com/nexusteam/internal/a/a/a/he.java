package com.nexusteam.internal;

import com.nexusteam.internal.beans.ProjectFileBean;
import java.util.ArrayList;
import java.util.Iterator;

public class he {

    /* renamed from: a  reason: collision with root package name */
    private hi f257a = new hi("manifest");
    private ArrayList<ProjectFileBean> b;
    private es c;

    public he(es esVar, ArrayList<ProjectFileBean> arrayList) {
        this.c = esVar;
        this.b = arrayList;
        this.f257a.a("xmlns", "android", "http://schemas.android.com/apk/res/android");
    }

    private void a(hi hiVar, String str) {
        hi hiVar2 = new hi("uses-permission");
        hiVar2.a("android", "name", str);
        hiVar.a(hiVar2);
    }

    public String a() {
        this.f257a.a("", "package", this.c.f124a);
        if (!this.c.a()) {
            if (this.c.b(1)) {
                a(this.f257a, "android.permission.CALL_PHONE");
            }
            if (this.c.b(2)) {
                a(this.f257a, "android.permission.INTERNET");
            }
            if (this.c.b(4)) {
                a(this.f257a, "android.permission.VIBRATE");
            }
            if (this.c.b(8)) {
                a(this.f257a, "android.permission.ACCESS_NETWORK_STATE");
            }
            if (this.c.b(16)) {
                a(this.f257a, "android.permission.CAMERA");
            }
            if (this.c.b(32)) {
                a(this.f257a, "android.permission.READ_EXTERNAL_STORAGE");
            }
            if (this.c.b(64)) {
                a(this.f257a, "android.permission.WRITE_EXTERNAL_STORAGE");
            }
            if (this.c.b(128)) {
                a(this.f257a, "android.permission.RECORD_AUDIO");
            }
            if (this.c.b(256)) {
                a(this.f257a, "android.permission.BLUETOOTH");
            }
            if (this.c.b(512)) {
                a(this.f257a, "android.permission.BLUETOOTH_ADMIN");
            }
            if (this.c.b(1024)) {
                a(this.f257a, "android.permission.ACCESS_FINE_LOCATION");
            }
        }
        hi hiVar = new hi("application");
        hiVar.a("android", "allowBackup", "true");
        hiVar.a("android", "label", this.c.b);
        hiVar.a("android", "icon", "@drawable/app_icon");
        hiVar.a("android", "largeHeap", "true");
        if (this.c.f) {
            hiVar.a("android", "name", ".SketchApplication");
        }
        hiVar.a("android", "theme", "@style/AppTheme");
        Iterator<ProjectFileBean> it = this.b.iterator();
        while (it.hasNext()) {
            ProjectFileBean next = it.next();
            hi hiVar2 = new hi("activity");
            String javaName = next.getJavaName();
            hiVar2.a("android", "name", "." + javaName.substring(0, javaName.indexOf("java")));
            hiVar2.a("android", "configChanges", "orientation|screenSize");
            if (this.c.k) {
                if (next.hasActivityOption(2)) {
                    hiVar2.a("android", "theme", "@style/AppTheme.FullScreen");
                }
            } else if (next.hasActivityOption(2)) {
                if (next.hasActivityOption(1)) {
                    hiVar2.a("android", "theme", "@style/NoStatusBar");
                } else {
                    hiVar2.a("android", "theme", "@style/FullScreen");
                }
            } else if (!next.hasActivityOption(1)) {
                hiVar2.a("android", "theme", "@style/NoActionBar");
            }
            if (next.orientation == 0) {
                hiVar2.a("android", "screenOrientation", "portrait");
            } else if (next.orientation == 1) {
                hiVar2.a("android", "screenOrientation", "landscape");
            }
            String a2 = fd.a(next.keyboardSetting);
            if (a2.length() > 0) {
                hiVar2.a("android", "windowSoftInputMode", a2);
            }
            if (next.fileName.equals("main")) {
                hi hiVar3 = new hi("intent-filter");
                hi hiVar4 = new hi("action");
                hiVar4.a("android", "name", "android.intent.action.MAIN");
                hiVar3.a(hiVar4);
                hi hiVar5 = new hi("category");
                hiVar5.a("android", "name", "android.intent.category.LAUNCHER");
                hiVar3.a(hiVar5);
                hiVar2.a(hiVar3);
            }
            hiVar.a(hiVar2);
        }
        if (this.c.f) {
            hi hiVar6 = new hi("activity");
            hiVar6.a("android", "name", ".DebugActivity");
            hiVar6.a("android", "screenOrientation", "portrait");
            hiVar.a(hiVar6);
        }
        if (this.c.m) {
            hi hiVar7 = new hi("activity");
           hiVar7.a("android", "name", "com.google.android.gms.ads.AdActivity");
            hiVar7.a("android", "configChanges", "keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize");
            hiVar7.a("android", "theme", "@android:style/Theme.Translucent");
            hiVar.a(hiVar7);
        }
        if (this.c.h) {
            a(hiVar);
        }
        if (this.c.l) {
            c(hiVar);
        }
        if (this.c.t) {
            d(hiVar);
        }
        if (this.c.o) {
            b(hiVar);
        }
        this.f257a.a(hiVar);
        return this.f257a.a();
    }

    private hi a(hi hiVar) {
        hi hiVar2 = new hi("meta-data");
        hiVar2.a("android", "name", "com.google.android.gms.version");
        hiVar2.a("android", "value", "@integer/google_play_services_version");
        hiVar.a(hiVar2);
        return hiVar2;
    }

    private hi b(hi hiVar) {
        hi hiVar2 = new hi("meta-data");
        hiVar2.a("android", "name", "com.google.android.geo.API_KEY");
        hiVar2.a("android", "value", "@string/google_maps_key");
        hiVar.a(hiVar2);
        return hiVar2;
    }

    private hi c(hi hiVar) {
        hi hiVar2 = new hi("provider");
        hiVar2.a("android", "name", "com.google.firebase.provider.FirebaseInitProvider");
        hiVar2.a("android", "authorities", this.c.f124a + ".firebaseinitprovider");
        hiVar2.a("android", "exported", "false");
        hiVar2.a("android", "initOrder", "100");
        hiVar.a(hiVar2);
        return hiVar2;
    }

    private hi d(hi hiVar) {
        hi hiVar2 = new hi("provider");
        hiVar2.a("android", "authorities", this.c.f124a + ".provider");
        hiVar2.a("android", "name", "android.support.v4.content.FileProvider");
        hiVar2.a("android", "exported", "false");
        hiVar2.a("android", "grantUriPermissions", "true");
        hi hiVar3 = new hi("meta-data");
        hiVar3.a("android", "name", "android.support.FILE_PROVIDER_PATHS");
        hiVar3.a("android", "resource", "@xml/provider_paths");
        hiVar2.a(hiVar3);
        hiVar.a(hiVar2);
        return hiVar2;
    }
}
