package com.nexusteam.internal;

import com.nexusteam.internal.hg;
import android.util.Pair;
import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.beans.ComponentBean;
import com.nexusteam.internal.beans.EventBean;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ViewBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class hf {
    private static String b = "\r\n";

    /* renamed from: a  reason: collision with root package name */
    ArrayList<String> f258a = new ArrayList<>();
    private String c;
    private ProjectFileBean d;
    private lw e;
    private hd f;
    private es g;
    private ArrayList<String> h = new ArrayList<>();
    private ArrayList<String> i = new ArrayList<>();
    private ArrayList<String> j = new ArrayList<>();
    private ArrayList<String> k = new ArrayList<>();
    private ArrayList<String> l = new ArrayList<>();
    private ArrayList<String> m = new ArrayList<>();
    private ArrayList<String> n = new ArrayList<>();
    private String o = "";
    private ArrayList<String> p = new ArrayList<>();
    private ArrayList<String> q = new ArrayList<>();
    private ArrayList<String> r = new ArrayList<>();

    public hf(es esVar, ProjectFileBean projectFileBean, lw lwVar) {
        this.c = esVar.f124a;
        this.g = esVar;
        this.d = projectFileBean;
        this.e = lwVar;
    }

    private void a(String str) {
        if (this.f258a.indexOf(str) < 0) {
            this.f258a.add(str);
        }
    }

    private void a(ArrayList<String> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                a(it.next());
            }
        }
    }

    private void b() {
        String str;
        a("android.app.*");
        a("android.os.*");
        a("android.view.*");
        a("android.view.View.*");
        a("android.widget.*");
        a("android.content.*");
        a("android.graphics.*");
        a("android.media.*");
        a("android.net.*");
        a("android.text.*");
        a("android.util.*");
        a("android.webkit.*");
        a("android.animation.*");
        a("android.view.animation.*");
        a("java.util.*");
        a("java.text.*");
        if (this.g.k) {
            a("android.support.v7.app.AppCompatActivity");
        } else {
            a("android.app.Activity");
        }
        if (this.g.k) {
            if (this.d.hasActivityOption(1)) {
                this.i.add("private Toolbar _toolbar;");
                this.m.add("_toolbar = (Toolbar) findViewById(R.id._toolbar);" + b + "setSupportActionBar(_toolbar);" + b + "getSupportActionBar().setDisplayHomeAsUpEnabled(true);" + b + "getSupportActionBar().setHomeButtonEnabled(true);" + b + "_toolbar.setNavigationOnClickListener(new View.OnClickListener() {" + b + "@Override" + b + "public void onClick(View _v) {" + b + "onBackPressed();" + b + "}" + b + "});");
                a("android.support.v7.widget.Toolbar");
            }
            if (this.d.hasActivityOption(8)) {
                this.i.add("private FloatingActionButton _fab;");
                this.m.add("_fab = (FloatingActionButton) findViewById(R.id._fab);" + b);
                a("android.support.design.widget.FloatingActionButton");
            }
            if (this.d.hasActivityOption(4)) {
                this.i.add("private DrawerLayout _drawer;");
                if (this.d.hasActivityOption(1)) {
                    str = "_drawer = (DrawerLayout) findViewById(R.id._drawer);" + "ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(" + this.d.getActivityName() + ".this, _drawer, _toolbar, R.string.app_name, R.string.app_name);" + b;
                } else {
                    str = "_drawer = (DrawerLayout) findViewById(R.id._drawer);" + "ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(" + this.d.getActivityName() + ".this, _drawer, R.string.app_name, R.string.app_name);" + b;
                }
                this.m.add(str + "_drawer.addDrawerListener(_toggle);" + b + "_toggle.syncState();" + b + b + "LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);" + b);
                a("android.support.v4.view.GravityCompat");
                a("android.support.v4.widget.DrawerLayout");
                a("android.support.v7.app.ActionBarDrawerToggle");
                a(ev.d("LinearLayout"));
            }
        }
        this.o = new hb(this.d.getActivityName(), this.g, "onCreate_initializeLogic", this.e.l(this.d.getJavaName(), "onCreate_initializeLogic")).a();
    }

    private void c() {
        this.f = new hd(this.g, this.d, this.e);
        a(this.f.a());
    }

    private void d() {
        String javaName = this.d.getJavaName();
        Iterator<Pair<String, String>> it = this.e.h(javaName).iterator();
        while (it.hasNext()) {
            Pair next = it.next();
            String str = ((String) next.first) + EventBean.SEPARATOR + "moreBlock";
            this.d.hasActivityOption(4);
            this.p.add(hg.d((String) next.first, (String) next.second, new hb(this.d.getActivityName(), this.g, str, this.e.l(javaName, str)).a()));
        }
    }

    private void e() {
        String xmlName = this.d.getXmlName();
        String javaName = this.d.getJavaName();
        ArrayList<ViewBean> a2 = this.e.a(xmlName);
        if (a2.size() > 0) {
            Iterator<ViewBean> it = a2.iterator();
            while (it.hasNext()) {
                this.m.add(c(it.next()));
            }
        }
        if (this.d.hasActivityOption(4)) {
            ArrayList<ViewBean> a3 = this.e.a(this.d.getDrawerXmlName());
            if (a3.size() > 0) {
                Iterator<ViewBean> it2 = a3.iterator();
                while (it2.hasNext()) {
                    this.m.add(d(it2.next()));
                }
            }
        }
        ArrayList<ComponentBean> k2 = this.e.k(javaName);
        if (k2.size() > 0) {
            Iterator<ComponentBean> it3 = k2.iterator();
            while (it3.hasNext()) {
                this.n.add(b(it3.next()));
            }
        }
    }

    private void f() {
        String xmlName = this.d.getXmlName();
        String javaName = this.d.getJavaName();
        ArrayList<Pair<Integer, String>> e2 = this.e.e(javaName);
        if (e2.size() > 0) {
            Iterator<Pair<Integer, String>> it = e2.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                this.i.add(a(((Integer) next.first).intValue(), (String) next.second));
            }
        }
        ArrayList<Pair<Integer, String>> f2 = this.e.f(javaName);
        if (f2.size() > 0) {
            Iterator<Pair<Integer, String>> it2 = f2.iterator();
            while (it2.hasNext()) {
                Pair next2 = it2.next();
                this.j.add(b(((Integer) next2.first).intValue(), (String) next2.second));
            }
        }
        ArrayList<ViewBean> a2 = this.e.a(xmlName);
        if (a2.size() > 0) {
            Iterator<ViewBean> it3 = a2.iterator();
            while (it3.hasNext()) {
                this.k.add(a(it3.next()));
            }
        }
        if (this.d.hasActivityOption(4)) {
            ArrayList<ViewBean> a3 = this.e.a(this.d.getDrawerXmlName());
            if (a3.size() > 0) {
                Iterator<ViewBean> it4 = a3.iterator();
                while (it4.hasNext()) {
                    this.k.add(b(it4.next()));
                }
            }
        }
        ArrayList<ComponentBean> k2 = this.e.k(javaName);
        if (k2.size() > 0) {
            Iterator<ComponentBean> it5 = k2.iterator();
            while (it5.hasNext()) {
                this.l.add(a(it5.next()));
            }
        }
        if (k2.size() > 0) {
            Iterator<ComponentBean> it6 = k2.iterator();
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            while (it6.hasNext()) {
                ComponentBean next3 = it6.next();
                if (next3.type == 5) {
                    z = true;
                }
                if (next3.type == 6) {
                    z2 = true;
                }
                if (next3.type == 14) {
                    z3 = true;
                }
            }
            if (z) {
                this.h.add(hg.a("Timer"));
            }
            if (z2) {
                this.h.add(hg.a("FirebaseDB"));
            }
            if (z3) {
                this.h.add(hg.a("FirebaseStorage"));
            }
        }
    }

    private void g() {
        Iterator<ViewBean> it = this.e.b(this.d.getXmlName()).iterator();
        while (it.hasNext()) {
            ViewBean next = it.next();
            String xmlName = ProjectFileBean.getXmlName(next.customView);
            this.d.getJavaName();
            String str = next.id + EventBean.SEPARATOR + "onBindCustomView";
            this.q.add(hg.a(next.id, next.customView, this.e.a(xmlName), new hb(this.d.getActivityName(), this.g, str, this.e.l(this.d.getJavaName(), str)).a()));
        }
    }

    private void h() {
        Iterator<ComponentBean> it = this.e.k(this.d.getJavaName()).iterator();
        int i2 = 100;
        while (it.hasNext()) {
            ComponentBean next = it.next();
            if (next.type == 15 || next.type == 16) {
                i2++;
                this.r.add(hg.a(next.componentId, i2));
            }
        }
    }

    private void i() {
        for (Map.Entry<String, ArrayList<BlockBean>> value : this.e.l(this.d.getJavaName()).entrySet()) {
            Iterator it = ((ArrayList) value.getValue()).iterator();
            while (it.hasNext()) {
                String str = ((BlockBean) it.next()).opCode;
                char c2 = 65535;
                switch (str.hashCode()) {
                    case -1975568730:
                        if (str.equals("copyToClipboard")) {
                            c2 = 7;
                            break;
                        }
                        break;
                    case -1149848189:
                        if (str.equals("toStringFormat")) {
                            c2 = 1;
                            break;
                        }
                        break;
                    case -733318734:
                        if (str.equals("strToListMap")) {
                            c2 = 3;
                            break;
                        }
                        break;
                    case -208762465:
                        if (str.equals("toStringWithDecimal")) {
                            c2 = 0;
                            break;
                        }
                        break;
                    case 168740282:
                        if (str.equals("mapToStr")) {
                            c2 = 4;
                            break;
                        }
                        break;
                    case 470160234:
                        if (str.equals("fileutilGetLastSegmentPath")) {
                            c2 = 8;
                            break;
                        }
                        break;
                    case 1129709718:
                        if (str.equals("setImageUrl")) {
                            c2 = 9;
                            break;
                        }
                        break;
                    case 1252547704:
                        if (str.equals("listMapToStr")) {
                            c2 = 5;
                            break;
                        }
                        break;
                    case 1313527577:
                        if (str.equals("setTypeface")) {
                            c2 = 6;
                            break;
                        }
                        break;
                    case 1775620400:
                        if (str.equals("strToMap")) {
                            c2 = 2;
                            break;
                        }
                        break;
                }
                switch (c2) {
                    case 0:
                    case 1:
                        a("java.text.DecimalFormat");
                        break;
                    case 2:
                    case 3:
                        a("com.google.gson.Gson");
                        a("com.google.gson.reflect.TypeToken");
                        break;
                    case 4:
                    case 5:
                        a("com.google.gson.Gson");
                        break;
                    case 6:
                        a("android.graphics.Typeface");
                        break;
                    case 7:
                        a("android.content.ClipData");
                        a("android.content.ClipboardManager");
                        break;
                    case 8:
                        a("android.net.Uri");
                        break;
                    case 9:
                        a("com.bumptech.glide.Glide");
                        break;
                }
            }
        }
    }

    private String a(int i2, String str) {
        String b2 = ev.b(i2);
        a(ev.d(b2));
        return hg.a(b2, str, hg.a.a, new String[0]);
    }

    private String b(int i2, String str) {
        String c2 = ev.c(i2);
        a(ev.d(c2));
        return hg.a(c2, str, hg.a.a, new String[0]);
    }

    private String a(ViewBean viewBean) {
        String a2 = viewBean.getClassInfo().a();
        a(ev.d(a2));
        return hg.a(a2, viewBean.id, hg.a.a, new String[0]);
    }

    private String b(ViewBean viewBean) {
        String a2 = viewBean.getClassInfo().a();
        a(ev.d(a2));
        return hg.a(a2, "_drawer_" + viewBean.id, hg.a.a, new String[0]);
    }

    private String a(ComponentBean componentBean) {
        String a2 = ev.a(componentBean.type);
        a(ev.d(a2));
        return hg.a(a2, componentBean.componentId, hg.a.a, componentBean.param1, componentBean.param2, componentBean.param3);
    }

    private String c(ViewBean viewBean) {
        return hg.a(viewBean.getClassInfo().a(), viewBean.id);
    }

    private String d(ViewBean viewBean) {
        return hg.a(viewBean.getClassInfo().a(), viewBean.id, "_nav_view");
    }

    private String b(ComponentBean componentBean) {
        return hg.b(ev.a(componentBean.type), componentBean.componentId, componentBean.param1, componentBean.param2, componentBean.param3);
    }

    public String a() {
        b();
        f();
        e();
        c();
        d();
        g();
        h();
        i();
        StringBuilder sb = new StringBuilder(8192);
        sb.append("package " + this.c + ";");
        sb.append(b);
        sb.append(b);
        Iterator<String> it = this.f258a.iterator();
        while (it.hasNext()) {
            sb.append("import " + it.next() + ";");
            sb.append(b);
        }
        if (this.g.a(this.d.getActivityName()).a()) {
            if (this.g.k) {
                sb.append("import android.support.v4.content.ContextCompat;");
                sb.append(b);
                sb.append("import android.support.v4.app.ActivityCompat;");
                sb.append(b);
            }
            sb.append("import android.Manifest;");
            sb.append(b);
            sb.append("import android.content.pm.PackageManager;");
            sb.append(b);
        }
        sb.append(b);
        if (this.g.k) {
            sb.append("public class " + this.d.getActivityName() + " extends AppCompatActivity {");
            sb.append(b);
        } else {
            sb.append("public class " + this.d.getActivityName() + " extends Activity {");
            sb.append(b);
        }
        Iterator<String> it2 = this.r.iterator();
        while (it2.hasNext()) {
            String next = it2.next();
            if (next.length() > 0) {
                sb.append(b);
                sb.append(next);
            }
        }
        Iterator<String> it3 = this.h.iterator();
        while (it3.hasNext()) {
            String next2 = it3.next();
            if (next2.length() > 0) {
                sb.append(b);
                sb.append(next2);
            }
        }
        if (this.i.size() > 0) {
            sb.append(b);
        }
        Iterator<String> it4 = this.i.iterator();
        while (it4.hasNext()) {
            String next3 = it4.next();
            if (next3.length() > 0) {
                sb.append(b);
                sb.append(next3);
            }
        }
        if (this.j.size() > 0) {
            sb.append(b);
        }
        Iterator<String> it5 = this.j.iterator();
        while (it5.hasNext()) {
            String next4 = it5.next();
            if (next4.length() > 0) {
                sb.append(b);
                sb.append(next4);
            }
        }
        if (this.k.size() > 0) {
            sb.append(b);
        }
        Iterator<String> it6 = this.k.iterator();
        while (it6.hasNext()) {
            String next5 = it6.next();
            if (next5.length() > 0) {
                sb.append(b);
                sb.append(next5);
            }
        }
        if (this.l.size() > 0) {
            sb.append(b);
        }
        Iterator<String> it7 = this.l.iterator();
        while (it7.hasNext()) {
            String next6 = it7.next();
            if (next6.length() > 0) {
                sb.append(b);
                sb.append(next6);
            }
        }
        sb.append(b);
        sb.append("@Override");
        sb.append(b);
        sb.append("protected void onCreate(Bundle _savedInstanceState) {");
        sb.append(b);
        sb.append("super.onCreate(_savedInstanceState);");
        sb.append(b);
        sb.append("setContentView(R.layout." + this.d.fileName + ");");
        sb.append(b);
        sb.append("initialize(_savedInstanceState);");
        sb.append(b);
        if (this.g.a(this.d.getActivityName()).a()) {
            sb.append(hg.a(this.g.k, this.g.a(this.d.getActivityName()).c));
        } else {
            sb.append("initializeLogic();");
            sb.append(b);
        }
        sb.append("}");
        sb.append(b);
        if (this.g.a(this.d.getActivityName()).a()) {
            sb.append("@Override");
            sb.append(b);
            sb.append("public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {");
            sb.append(b);
            sb.append("super.onRequestPermissionsResult(requestCode, permissions, grantResults);");
            sb.append(b);
            sb.append("if (requestCode == 1000) {");
            sb.append(b);
            sb.append("initializeLogic();");
            sb.append(b);
            sb.append("}");
            sb.append(b);
            sb.append("}");
            sb.append(b);
        }
        sb.append(b);
        sb.append("private void initialize(Bundle _savedInstanceState) {");
        sb.append(b);
        Iterator<String> it8 = this.m.iterator();
        while (it8.hasNext()) {
            String next7 = it8.next();
            if (next7.length() > 0) {
                sb.append(b);
                sb.append(next7);
            }
        }
        Iterator<String> it9 = this.n.iterator();
        while (it9.hasNext()) {
            String next8 = it9.next();
            if (next8.length() > 0) {
                sb.append(b);
                sb.append(next8);
            }
        }
        String e2 = this.f.e();
        if (e2.length() > 0) {
            sb.append(b);
            sb.append(b);
            sb.append(e2);
        }
        String f2 = this.f.f();
        if (f2.length() > 0) {
            sb.append(b);
            sb.append(b);
            sb.append(f2);
        }
        String d2 = this.f.d();
        if (d2.length() > 0) {
            sb.append(b);
            sb.append(b);
            sb.append(d2);
        }
        String g2 = this.f.g();
        if (g2.length() > 0) {
            sb.append(b);
            sb.append(b);
            sb.append(g2);
        }
        sb.append(b);
        sb.append("}");
        sb.append(b);
        sb.append("private void initializeLogic() {");
        sb.append(b);
        if (this.o.length() > 0) {
            sb.append(this.o);
            sb.append(b);
        }
        sb.append("}");
        sb.append(b);
        sb.append(b);
        sb.append("@Override");
        sb.append(b);
        sb.append("protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {");
        sb.append(b);
        sb.append("super.onActivityResult(_requestCode, _resultCode, _data);");
        sb.append(b);
        sb.append(b);
        sb.append("switch (_requestCode) {");
        sb.append(b);
        sb.append(this.f.b());
        sb.append(b);
        sb.append("default:");
        sb.append(b);
        sb.append("break;");
        sb.append(b);
        sb.append("}");
        sb.append(b);
        sb.append("}");
        sb.append(b);
        if (this.d.hasActivityOption(4)) {
            this.f.a("onBackPressed", "DrawerLayout", "_drawer");
        }
        ArrayList<ViewBean> a2 = this.e.a(this.d.getXmlName());
        if (a2.size() > 0) {
            Iterator<ViewBean> it10 = a2.iterator();
            while (it10.hasNext()) {
                ViewBean next9 = it10.next();
                if (next9.type == 18) {
                    this.f.a("onStart", "MapView", next9.id);
                    this.f.a("onResume", "MapView", next9.id);
                    this.f.a("onPause", "MapView", next9.id);
                    this.f.a("onStop", "MapView", next9.id);
                    this.f.a("onDestroy", "MapView", next9.id);
                }
            }
        }
        String c2 = this.f.c();
        if (c2.length() > 0) {
            sb.append(b);
            sb.append(c2);
        }
        Iterator<String> it11 = this.p.iterator();
        while (it11.hasNext()) {
            sb.append(b);
            sb.append(it11.next());
            sb.append(b);
        }
        Iterator<String> it12 = this.q.iterator();
        while (it12.hasNext()) {
            sb.append(b);
            sb.append(it12.next());
            sb.append(b);
        }
        sb.append(b);
        a(sb);
        sb.append(b);
        sb.append("}");
        sb.append(b);
        return hg.j(sb.toString());
    }

    private void a(StringBuilder sb) {
        sb.append("@Deprecated" + b + "public void showMessage(String _s) {" + b + "Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();" + b + "}" + b + b + "@Deprecated" + b + "public int getLocationX(View _v) {" + b + "int _location[] = new int[2];" + b + "_v.getLocationInWindow(_location);" + b + "return _location[0];" + b + "}" + b + b + "@Deprecated" + b + "public int getLocationY(View _v) {" + b + "int _location[] = new int[2];" + b + "_v.getLocationInWindow(_location);" + b + "return _location[1];" + b + "}" + b + b + "@Deprecated" + b + "public int getRandom(int _min, int _max) {" + b + "Random random = new Random();" + b + "return random.nextInt(_max - _min + 1) + _min;" + b + "}" + b + b + "@Deprecated" + b + "public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {" + b + "ArrayList<Double> _result = new ArrayList<Double>();" + b + "SparseBooleanArray _arr = _list.getCheckedItemPositions();" + b + "for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {" + b + "if (_arr.valueAt(_iIdx))" + b + "_result.add((double)_arr.keyAt(_iIdx));" + b + "}" + b + "return _result;" + b + "}" + b + b + "@Deprecated" + b + "public float getDip(int _input){" + b + "return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());" + b + "}" + b + b + "@Deprecated" + b + "public int getDisplayWidthPixels(){" + b + "return getResources().getDisplayMetrics().widthPixels;" + b + "}" + b + b + "@Deprecated" + b + "public int getDisplayHeightPixels(){" + b + "return getResources().getDisplayMetrics().heightPixels;" + b + "}" + b);
    }
}
