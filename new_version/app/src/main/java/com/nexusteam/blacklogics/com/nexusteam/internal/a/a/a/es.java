package com.nexusteam.internal;

import com.nexusteam.internal.beans.AdTestDeviceBean;
import com.nexusteam.internal.beans.ProjectLibraryBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class es {

    /* renamed from: a  reason: collision with root package name */
    public String f124a = "";
    public String b;
    public String c;
    public String d;
    public boolean e = false;
    public boolean f = false;
    public boolean g = false;
    public boolean h = false;
    public boolean i = false;
    public boolean j = false;
    public boolean k = false;
    public boolean l = false;
    public boolean m = false;
    public boolean n = false;
    public boolean o = false;
    public int p = 0;
    public String q = "ca-app-pub-3940256099942544/6300978111";
    public String r = "ca-app-pub-3940256099942544/1033173712";
    public ArrayList<String> s = new ArrayList<>();
    public boolean t = false;
    public String u = "";
    HashMap<String, a> v = new HashMap<>();

    public void b() {
    }

    public class a {

        /* renamed from: a  reason: collision with root package name */
        public boolean f125a = false;
        public boolean b = false;
        public int c = 0;

        public a() {
        }

        public void a(int i) {
            this.c = i | this.c;
        }

        public boolean b(int i) {
            return (this.c & i) == i;
        }

        public boolean a() {
            if (!b(1) && !b(32) && !b(64) && !b(16) && !b(128) && !b(1024)) {
                return false;
            }
            return true;
        }
    }

    public boolean a() {
        return this.p == 0;
    }

    public void a(int i2) {
        this.p = i2 | this.p;
    }

    public boolean b(int i2) {
        return (this.p & i2) == i2;
    }

    public void a(String str, int i2) {
        if (!this.v.containsKey(str)) {
            this.v.put(str, new a());
        }
        this.v.get(str).a(i2);
        a(i2);
    }

    public a a(String str) {
        if (!this.v.containsKey(str)) {
            this.v.put(str, new a());
        }
        return this.v.get(str);
    }

    public void a(ProjectLibraryBean projectLibraryBean) {
        if (!(projectLibraryBean == null || projectLibraryBean.testDevices == null || projectLibraryBean.testDevices.size() <= 0)) {
            Iterator<AdTestDeviceBean> it = projectLibraryBean.testDevices.iterator();
            while (it.hasNext()) {
                this.s.add(it.next().deviceId);
            }
        }
        if (!(projectLibraryBean == null || projectLibraryBean.reserved1 == null || projectLibraryBean.reserved1.isEmpty())) {
            this.q = projectLibraryBean.reserved1.substring(projectLibraryBean.reserved1.lastIndexOf(" : ") + 3);
        }
        if (projectLibraryBean != null && projectLibraryBean.reserved2 != null && !projectLibraryBean.reserved2.isEmpty()) {
            this.r = projectLibraryBean.reserved2.substring(projectLibraryBean.reserved2.lastIndexOf(" : ") + 3);
        }
    }

    public void b(ProjectLibraryBean projectLibraryBean) {
        if (projectLibraryBean != null && projectLibraryBean.data != null) {
            this.u = projectLibraryBean.data;
        }
    }
}
