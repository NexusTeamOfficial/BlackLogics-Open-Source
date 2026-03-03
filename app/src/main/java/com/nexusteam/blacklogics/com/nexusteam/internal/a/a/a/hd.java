package com.nexusteam.internal;

import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.beans.ComponentBean;
import com.nexusteam.internal.beans.EventBean;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ViewBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class hd {

    /* renamed from: a  reason: collision with root package name */
    private ProjectFileBean f252a;
    private es b;
    private ArrayList<c> c = new ArrayList<>();
    private ArrayList<c> d = new ArrayList<>();
    private ArrayList<a> e = new ArrayList<>();
    private ArrayList<c> f = new ArrayList<>();
    private ArrayList<c> g = new ArrayList<>();
    private ArrayList<b> h = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<String> i = new ArrayList<>();
    private HashMap<String, String> j = new HashMap<>();

    public hd(es esVar, ProjectFileBean projectFileBean, lw lwVar) {
        this.b = esVar;
        this.f252a = projectFileBean;
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(lwVar.a(projectFileBean.getXmlName()));
        if (projectFileBean.hasActivityOption(8)) {
            arrayList.add(lwVar.d(projectFileBean.getXmlName()));
        }
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ViewBean viewBean = (ViewBean) it.next();
                this.c.add(new c(viewBean.id, viewBean.getClassInfo()));
            }
        }
        ArrayList<ComponentBean> k = lwVar.k(projectFileBean.getJavaName());
        if (k.size() > 0) {
            Iterator<ComponentBean> it2 = k.iterator();
            while (it2.hasNext()) {
                ComponentBean next = it2.next();
                if (next.type == 12 || next.type == 13) {
                    this.f.add(new c(next.componentId, next.getClassInfo()));
                } else {
                    this.d.add(new c(next.componentId, next.getClassInfo()));
                }
            }
        }
        if (projectFileBean.hasActivityOption(4)) {
            ArrayList<ViewBean> a2 = lwVar.a(projectFileBean.getDrawerXmlName());
            if (a2.size() > 0) {
                Iterator<ViewBean> it3 = a2.iterator();
                while (it3.hasNext()) {
                    ViewBean next2 = it3.next();
                    ArrayList<c> arrayList2 = this.g;
                    arrayList2.add(new c("_drawer_" + next2.id, next2.getClassInfo()));
                }
            }
        }
        a(lwVar.j(projectFileBean.getJavaName()), lwVar.l(projectFileBean.getJavaName()));
    }

    private void a(ArrayList<EventBean> arrayList, HashMap<String, ArrayList<BlockBean>> hashMap) {
        Iterator<EventBean> it = arrayList.iterator();
        while (it.hasNext()) {
            EventBean next = it.next();
            ArrayList arrayList2 = hashMap.get(next.targetId + EventBean.SEPARATOR + next.eventName);
            String str = "";
            if (arrayList2 != null && arrayList2.size() > 0) {
                str = new hb(this.f252a.getActivityName(), this.b, next.eventName, arrayList2).a();
            }
            switch (next.eventType) {
                case 1:
                    b(next.targetId, next.eventName, str);
                    break;
                case 2:
                    if (next.targetType != 12 && next.targetType != 13) {
                        if (next.targetType != 15 && next.targetType != 16) {
                            d(next.targetId, next.eventName, str);
                            break;
                        } else {
                            a(next.targetType, next.targetId, next.eventName, str);
                            break;
                        }
                    } else {
                        e(next.targetId, next.eventName, str);
                        break;
                    }
                case 3:
                    a(next.eventName, str);
                    break;
                case 4:
                    c(next.targetId, next.eventName, str);
                    break;
            }
        }
    }

    public ArrayList<String> a() {
        return this.i;
    }

    public void a(String str, String str2, String str3) {
        if (!this.j.containsKey(str)) {
            this.j.put(str, hg.c(str, str2, str3));
        }
    }

    private void a(String str, String str2) {
        Iterator<a> it = this.e.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next.f253a.equals(str)) {
                next.a(str2);
                return;
            }
        }
        a aVar = new a(str);
        aVar.a(str2);
        this.e.add(aVar);
    }

    private void a(int i2, String str, String str2, String str3) {
        Iterator<b> it = this.h.iterator();
        boolean z = false;
        while (it.hasNext()) {
            b next = it.next();
            if (next.b.equals(str)) {
                if (str2.equals("onPictureTaken") || str2.equals("onFilesPicked")) {
                    next.a(str3);
                    return;
                } else if (str2.equals("onPictureTakenCancel") || str2.equals("onFilesPickedCancel")) {
                    next.b(str3);
                    return;
                } else {
                    z = true;
                }
            }
        }
        if (!z) {
            b bVar = new b(i2, str);
            if (str2.equals("onPictureTaken") || str2.equals("onFilesPicked")) {
                bVar.a(str3);
            } else if (str2.equals("onPictureTakenCancel") || str2.equals("onFilesPickedCancel")) {
                bVar.b(str3);
            }
            this.h.add(bVar);
        }
    }

    public String b() {
        StringBuilder sb = new StringBuilder(4096);
        Iterator<b> it = this.h.iterator();
        while (it.hasNext()) {
            String a2 = it.next().a();
            if (sb.length() > 0 && a2.length() > 0) {
                sb.append("\r\n");
                sb.append("\r\n");
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    public String c() {
        StringBuilder sb = new StringBuilder(4096);
        for (Map.Entry next : this.j.entrySet()) {
            String str = (String) next.getKey();
            String str2 = (String) next.getValue();
            boolean z = false;
            Iterator<a> it = this.e.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().f253a.equals(str)) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!z) {
                a aVar = new a(str);
                aVar.a(str2);
                this.e.add(aVar);
            }
        }
        Iterator<a> it2 = this.e.iterator();
        while (it2.hasNext()) {
            String a2 = it2.next().a();
            if (sb.length() > 0 && a2.length() > 0) {
                sb.append("\r\n");
                sb.append("\r\n");
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    private void b(String str, String str2, String str3) {
        Iterator<c> it = this.c.iterator();
        while (it.hasNext()) {
            c next = it.next();
            if (next.f255a.equals(str)) {
                next.a(str2, str3);
                return;
            }
        }
    }

    private void c(String str, String str2, String str3) {
        String str4 = "_drawer_" + str;
        Iterator<c> it = this.g.iterator();
        while (it.hasNext()) {
            c next = it.next();
            if (next.f255a.equals(str4)) {
                next.a(str2, str3);
                return;
            }
        }
    }

    public String d() {
        StringBuilder sb = new StringBuilder(4096);
        Iterator<c> it = this.g.iterator();
        while (it.hasNext()) {
            String a2 = it.next().a();
            if (sb.length() > 0 && a2.length() > 0) {
                sb.append("\r\n");
                sb.append("\r\n");
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    public String e() {
        StringBuilder sb = new StringBuilder(4096);
        Iterator<c> it = this.c.iterator();
        while (it.hasNext()) {
            String a2 = it.next().a();
            if (sb.length() > 0 && a2.length() > 0) {
                sb.append("\r\n");
                sb.append("\r\n");
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    private void d(String str, String str2, String str3) {
        Iterator<c> it = this.d.iterator();
        while (it.hasNext()) {
            c next = it.next();
            if (next.f255a.equals(str)) {
                next.a(str2, str3);
                return;
            }
        }
    }

    public String f() {
        StringBuilder sb = new StringBuilder(4096);
        Iterator<c> it = this.d.iterator();
        while (it.hasNext()) {
            String a2 = it.next().a();
            if (sb.length() > 0 && a2.length() > 0) {
                sb.append("\r\n");
                sb.append("\r\n");
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    private void e(String str, String str2, String str3) {
        Iterator<c> it = this.f.iterator();
        while (it.hasNext()) {
            c next = it.next();
            if (next.f255a.equals(str)) {
                next.a(str2, str3);
                return;
            }
        }
    }

    public String g() {
        StringBuilder sb = new StringBuilder(4096);
        Iterator<c> it = this.f.iterator();
        while (it.hasNext()) {
            c next = it.next();
            next.b();
            String a2 = next.a();
            if (sb.length() > 0 && a2.length() > 0) {
                sb.append("\r\n");
                sb.append("\r\n");
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    class c {

        /* renamed from: a  reason: collision with root package name */
        String f255a;
        hc b;
        ArrayList<d> c = new ArrayList<>();

        public void b() {
        }

        public c(String str, hc hcVar) {
            this.f255a = str;
            this.b = hcVar;
            String[] c2 = ex.c(hcVar);
            if (c2.length > 0) {
                for (String str2 : c2) {
                    if (this.c.indexOf(str2) < 0) {
                        this.c.add(new d(str2));
                    }
                }
                if (hcVar.a().equals("FirebaseDB") || hcVar.a().equals("FirebaseStorage") || hcVar.a().equals("FirebaseAuth") || hcVar.a().equals("Gyroscope") || hcVar.a().equals("WebView") || hcVar.a().equals("InterstitialAd") || hcVar.a().equals("RequestNetwork") || hcVar.a().equals("BluetoothConnect")) {
                    Iterator<d> it = this.c.iterator();
                    while (it.hasNext()) {
                        it.next().b = true;
                    }
                }
            }
        }

        public void a(String str, String str2) {
            if (this.c.size() > 0) {
                Iterator<d> it = this.c.iterator();
                while (it.hasNext()) {
                    d next = it.next();
                    if (next.c.size() > 0) {
                        Iterator<a> it2 = next.c.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            a next2 = it2.next();
                            if (next2.f253a.equals(str)) {
                                next2.a(str2);
                                next.b = true;
                                break;
                            }
                        }
                        if (next.b) {
                            hd.this.i.addAll(ev.e(next.f256a));
                        }
                    }
                }
            }
        }

        public String a() {
            StringBuilder sb = new StringBuilder(4096);
            Iterator<d> it = this.c.iterator();
            while (it.hasNext()) {
                String a2 = it.next().a(this.f255a);
                if (sb.length() > 0 && a2.length() > 0) {
                    sb.append("\r\n");
                    sb.append("\r\n");
                }
                sb.append(a2);
            }
            return sb.toString();
        }
    }

    class d {

        /* renamed from: a  reason: collision with root package name */
        String f256a;
        boolean b = false;
        ArrayList<a> c;

        public d(String str) {
            this.f256a = str;
            this.c = new ArrayList<>();
            String[] a2 = ex.a(str);
            if (a2.length > 0) {
                for (String aVar : a2) {
                    this.c.add(new a(aVar));
                }
            }
        }

        public String a(String str) {
            if (!this.b) {
                return "";
            }
            StringBuilder sb = new StringBuilder(4096);
            Iterator<a> it = this.c.iterator();
            while (it.hasNext()) {
                String a2 = it.next().a();
                if (sb.length() > 0 && a2.length() > 0) {
                    sb.append("\r\n");
                    sb.append("\r\n");
                }
                sb.append(a2);
            }
            return hg.b(this.f256a, str, sb.toString());
        }
    }

    class a {

        /* renamed from: a  reason: collision with root package name */
        String f253a = "";
        String b = "";

        public a(String str) {
            this.f253a = str;
        }

        public void a(String str) {
            this.b = str;
        }

        public String a() {
            return hg.b(this.f253a, this.b);
        }
    }

    class b {

        /* renamed from: a  reason: collision with root package name */
        int f254a = 0;
        String b = "";
        String c = "";
        String d = "";

        public b(int i, String str) {
            this.f254a = i;
            this.b = str;
        }

        public void a(String str) {
            this.c = str;
        }

        public void b(String str) {
            this.d = str;
        }

        public String a() {
            return hg.a(this.f254a, this.b, this.c, this.d);
        }
    }
}
