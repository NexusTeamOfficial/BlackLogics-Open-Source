package com.nexusteam.internal.editor.view;
import com.nexusteam.blacklogics.R;


import com.nexusteam.internal.ek;
import com.nexusteam.internal.en;
import com.nexusteam.internal.fe;
import com.nexusteam.internal.gg;
import com.nexusteam.internal.hm;
import com.nexusteam.internal.hn;
import com.nexusteam.internal.hp;
import com.nexusteam.internal.hq;
import com.nexusteam.internal.hy;
import com.nexusteam.internal.ii;
import com.nexusteam.internal.ik;
import com.nexusteam.internal.il;
import com.nexusteam.internal.iq;
import com.nexusteam.internal.is;
import com.nexusteam.internal.it;
import com.nexusteam.internal.iv;
import com.nexusteam.internal.kd;
import com.nexusteam.internal.ke;
import com.nexusteam.internal.kk;
import com.nexusteam.internal.kp;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.kv;
import com.nexusteam.internal.ky;
import com.nexusteam.internal.lv;
import com.nexusteam.internal.ma;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ProjectResourceBean;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.internal.beans.WidgetCollectionBean;
import com.nexusteam.internal.editor.view.palette.PaletteFavorite;
import com.nexusteam.internal.editor.view.palette.PaletteWidget;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ViewEditor extends RelativeLayout implements View.OnClickListener, View.OnTouchListener {
    private ViewDummy A;
    private ImageView B;
    private ObjectAnimator C;
    private ObjectAnimator D;
    private boolean E = false;
    private boolean F = false;
    private boolean G = false;
    private boolean H = true;
    private FrameLayout I;
    private int[] J = new int[2];
    private hq K;
    private int L = 50;
    private int M = 30;
    private boolean N;
    private hp O;
    private gg P;
    private hm Q;
    private hn R;
    private Tracker S;
    private ProjectFileBean T;
    private boolean U = true;
    private boolean V = false;
    private LinearLayout W;
    
    /* renamed from: a  reason: collision with root package name */
    public ViewPane f1388a;
    /* access modifiers changed from: private */
    public b aa;
    /* access modifiers changed from: private */
    public b ab;
    private LinearLayout ac;
    private Runnable ad = new Runnable() {
        public void run() {
            ViewEditor.this.l();
        }
    };
    public boolean b = true;
    private String c;
    private String d;
    private final int e = 99;
    private int f = 99;
    private int[] g = new int[19];
    private float h = 0.0f;
    private int i;
    private int j;
    private int k;
    private int l;
    private PaletteWidget m;
    private PaletteFavorite n;
    private LinearLayout o;
    private TextView p;
    private ImageView q;
    private LinearLayout r;
    private TextView s;
    private Vibrator t;
    private View u = null;
    private final Handler v = new Handler();
    private boolean w = false;
    private float x = 0.0f;
    private float y = 0.0f;
    private int z = 0;
    
    public enum PaletteGroupType {
        ePaletteGroup_basic,
        ePaletteGroup_favorite
    }
    
    public ViewEditor(Context context) {
        super(context);
        a(context);
    }
    
    public ViewEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }
    
    private void a(Context context) {
        kp.a(context, this, com.nexusteam.blacklogics.R.layout.view_editor);



        this.m = (PaletteWidget) findViewById(com.nexusteam.blacklogics.R.id.palette_widget);
        this.n = (PaletteFavorite) findViewById(com.nexusteam.blacklogics.R.id.palette_favorite);
        this.A = (ViewDummy) findViewById(com.nexusteam.blacklogics.R.id.dummy);
        this.B = (ImageView) findViewById(com.nexusteam.blacklogics.R.id.icon_delete);
        this.I = (FrameLayout) findViewById(com.nexusteam.blacklogics.R.id.shape);
        this.W = (LinearLayout) findViewById(com.nexusteam.blacklogics.R.id.palette_group);
        h();
        findViewById(com.nexusteam.blacklogics.R.id.btn_editproperties).setOnClickListener(this);
        findViewById(com.nexusteam.blacklogics.R.id.img_close).setOnClickListener(this);
        this.h = kp.a(context, 1.0f);
        this.L = (int) (((float) this.L) * this.h);
        this.M = (int) (((float) this.M) * this.h);
        this.k = getResources().getDisplayMetrics().widthPixels;
        this.l = getResources().getDisplayMetrics().heightPixels;
        this.ac = new LinearLayout(context);
        this.ac.setOrientation(1);
        this.ac.setGravity(17);
        this.ac.setLayoutParams(new FrameLayout.LayoutParams(this.k, this.l));
        this.I.addView(this.ac);
        this.o = new LinearLayout(context);
        this.o.setBackgroundColor(-16743230);
        this.o.setOrientation(0);
        this.o.setGravity(16);
        this.o.setLayoutParams(new FrameLayout.LayoutParams(this.k, (int) (this.h * 25.0f)));
        this.p = new TextView(context);
        this.p.setTextColor(-1);
        this.p.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.p.setPadding((int) (this.h * 8.0f), 0, 0, 0);
        this.p.setGravity(16);
        this.o.addView(this.p);
        this.q = new ImageView(context);
        this.q.setImageResource(com.nexusteam.blacklogics.R.drawable.phone_bg_top);
        this.q.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.q.setScaleType(ImageView.ScaleType.FIT_END);
        this.o.addView(this.q);
        this.I.addView(this.o);
        this.r = new LinearLayout(context);
        this.r.setBackgroundColor(-16740915);
        this.r.setOrientation(0);
        this.r.setGravity(16);
        this.r.setLayoutParams(new FrameLayout.LayoutParams(this.k, (int) (this.h * 48.0f)));
        this.s = new TextView(context);
        this.s.setTextColor(-1);
        this.s.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.s.setPadding((int) (this.h * 16.0f), 0, 0, 0);
        this.s.setGravity(16);
        this.s.setTextSize(15.0f);
        this.s.setText("Toolbar");
        this.s.setTypeface((Typeface) null, 1);
        this.r.addView(this.s);
        this.I.addView(this.r);
        this.f1388a = new ViewPane(getContext());
        this.f1388a.setLayoutParams(new FrameLayout.LayoutParams(this.k, this.l));
        this.I.addView(this.f1388a);
        this.f1388a.setOnTouchListener(this);
        this.t = (Vibrator) context.getSystemService("vibrator");
        this.N = new kv(context, "P12").a("P12I0", true);
        this.z = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    
    private void h() {
        this.aa = new b(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1);
        layoutParams.weight = 1.0f;
        this.aa.setLayoutParams(layoutParams);
        this.aa.a(PaletteGroupType.ePaletteGroup_basic);
        this.aa.setSelected(true);
        this.aa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ViewEditor.this.i();
                ViewEditor.this.aa.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).start();
                ViewEditor.this.ab.animate().scaleX(0.9f).scaleY(0.9f).alpha(0.6f).start();
                ViewEditor.this.aa.setSelected(true);
                ViewEditor.this.ab.setSelected(false);
            }
        });
        this.ab = new b(getContext());
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, -1);
        layoutParams2.weight = 1.0f;
        this.ab.setLayoutParams(layoutParams2);
        this.ab.a(PaletteGroupType.ePaletteGroup_favorite);
        this.ab.setSelected(false);
        this.ab.animate().scaleX(0.9f).scaleY(0.9f).alpha(0.6f).start();
        this.ab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ViewEditor.this.j();
                ViewEditor.this.aa.animate().scaleX(0.9f).scaleY(0.9f).alpha(0.6f).start();
                ViewEditor.this.ab.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).start();
                ViewEditor.this.aa.setSelected(false);
                ViewEditor.this.ab.setSelected(true);
            }
        });
        this.W.addView(this.aa);
        this.W.addView(this.ab);
    }
    
    /* access modifiers changed from: private */
    public void i() {
        this.m.setVisibility(0);
        this.n.setVisibility(8);
    }
    
    /* access modifiers changed from: private */
    public void j() {
        this.m.setVisibility(8);
        this.n.setVisibility(0);
    }
    
    public void a(String str) {
        hq a2 = this.f1388a.a(str);
        if (a2 != null && this.K != a2) {
            if (this.K != null) {
                this.K.setSelection(false);
            }
            a2.setSelection(true);
            this.K = a2;
        }
    }
    
    public void a() {
        boolean z2;
        if (this.U) {
            this.r.setVisibility(0);
        } else {
            this.r.setVisibility(8);
        }
        if (this.V) {
            this.o.setVisibility(8);
        } else {
            this.o.setVisibility(0);
        }
        this.f1388a.setVisibility(0);
        this.k = getResources().getDisplayMetrics().widthPixels;
        this.l = getResources().getDisplayMetrics().heightPixels;
        int i2 = this.k - ((int) (this.h * 120.0f));
        int i3 = (((this.l - ((int) (this.h * 25.0f))) - ((int) (this.h * 44.0f))) - ((int) (this.h * 48.0f))) - ((int) (this.h * 48.0f));
        float f2 = ((float) i2) / ((float) this.k);
        float min = Math.min(f2, ((float) i3) / ((float) this.l));
        if (min == f2) {
            this.ac.setBackgroundResource(com.nexusteam.blacklogics.R.drawable.new_view_pane_background_port);
            this.i = (int) (this.h * 8.0f);
            this.j = (int) (this.h * 24.0f);
            z2 = false;
        } else {
            this.ac.setBackgroundResource(com.nexusteam.blacklogics.R.drawable.new_view_pane_background_land);
            this.i = (int) (this.h * 24.0f);
            this.j = (int) (this.h * 8.0f);
            z2 = true;
        }
        this.ac.setLayoutParams(new FrameLayout.LayoutParams(this.k, this.l));
        this.ac.setScaleX(min);
        this.ac.setScaleY(min);
        this.ac.setX((float) (-((int) ((((float) this.k) - (((float) this.k) * min)) / 2.0f))));
        this.ac.setY((float) (-((int) ((((float) this.l) - (((float) this.l) * min)) / 2.0f))));
        float min2 = Math.min(((float) (i2 - (this.i * 2))) / ((float) this.k), ((float) (i3 - (this.j * 2))) / ((float) this.l));
        int h2 = ky.h(getContext());
        int g2 = ky.g(getContext());
        if (z2) {
            this.k -= this.i * 2;
        }
        int i4 = this.i - ((int) ((((float) this.k) - (((float) this.k) * min2)) / 2.0f));
        int i5 = this.j;
        if (this.o.getVisibility() == 0) {
            this.o.setLayoutParams(new FrameLayout.LayoutParams(this.k, h2));
            this.o.setScaleX(min2);
            this.o.setScaleY(min2);
            float f3 = (float) h2;
            float f4 = f3 * min2;
            int i6 = this.j - ((int) ((f3 - f4) / 2.0f));
            this.o.setX((float) i4);
            this.o.setY((float) i6);
            i5 += (int) f4;
        }
        if (this.r.getVisibility() == 0) {
            this.r.setLayoutParams(new FrameLayout.LayoutParams(this.k, g2));
            this.r.setScaleX(min2);
            this.r.setScaleY(min2);
            float f5 = (float) g2;
            float f6 = f5 * min2;
            this.r.setX((float) i4);
            this.r.setY((float) (i5 - ((int) ((f5 - f6) / 2.0f))));
            i5 += (int) f6;
        }
        int i7 = this.l;
        if (this.o.getVisibility() == 0) {
            i7 -= h2;
        }
        if (this.r.getVisibility() == 0) {
            i7 -= g2;
        }
        if (!z2) {
            i7 -= this.j * 2;
        }
        this.f1388a.setLayoutParams(new FrameLayout.LayoutParams(this.k, i7));
        this.f1388a.setScaleX(min2);
        this.f1388a.setScaleY(min2);
        float f7 = (float) i7;
        this.f1388a.setX((float) (this.i - ((int) ((((float) this.k) - (((float) this.k) * min2)) / 2.0f))));
        this.f1388a.setY((float) (i5 - ((int) ((f7 - (min2 * f7)) / 2.0f))));
        this.b = false;
    }
    
    public void b() {
        this.g = new int[19];
    }
    
    public void a(String str, ProjectFileBean projectFileBean) {
        this.c = str;
        this.T = projectFileBean;
        this.d = projectFileBean.getXmlName();
        if (projectFileBean.fileType == 2) {
            this.p.setText(projectFileBean.fileName.substring(1));
        } else {
            this.p.setText(projectFileBean.getXmlName());
        }
        e();
        if (projectFileBean.fileType == 0) {
            this.U = projectFileBean.hasActivityOption(1);
            this.V = projectFileBean.hasActivityOption(2);
            if (projectFileBean.hasActivityOption(8)) {
                d(ma.a(str).d(projectFileBean.getXmlName()));
            }
        } else {
            this.U = false;
            this.V = false;
        }
        this.b = true;
    }
    
    public ProjectFileBean getProjectFile() {
        return this.T;
    }
    
    public void a(PaletteWidget.a aVar, String str) {
        View a2 = this.m.a(aVar, str);
        a2.setClickable(true);
        a2.setOnTouchListener(this);
    }
    
    public void c() {
        this.m.a();
        this.m.b();
    }
    
    public void a(PaletteWidget.b bVar, String str, String str2, String str3) {
        View a2 = this.m.a(bVar, str, str2, str3);
        a2.setClickable(true);
        a2.setOnTouchListener(this);
    }
    
    public boolean a(View view) {
        return view instanceof il;
    }
    
    private void k() {
        this.n.a();
    }
    
    private void a(String str, ArrayList<ViewBean> arrayList) {
        View a2 = this.n.a(str, arrayList);
        a2.setClickable(true);
        a2.setOnTouchListener(this);
    }
    
    public void setFavoriteData(ArrayList<WidgetCollectionBean> arrayList) {
        k();
        Iterator<WidgetCollectionBean> it = arrayList.iterator();
        while (it.hasNext()) {
            WidgetCollectionBean next = it.next();
            a(next.name, next.widgets);
        }
    }
    
    public void setPaletteLayoutVisible(int i2) {
        this.m.setLayoutVisible(i2);
    }
    
    private String a(int i2) {
        String a2 = fe.a(i2);
        StringBuilder sb = new StringBuilder();
        sb.append(a2);
        int[] iArr = this.g;
        int i3 = iArr[i2] + 1;
        iArr[i2] = i3;
        sb.append(i3);
        String sb2 = sb.toString();
        ArrayList<ViewBean> a3 = ma.a(this.c).a(this.d);
        while (true) {
            boolean z2 = false;
            Iterator<ViewBean> it = a3.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (sb2.equals(it.next().id)) {
                        z2 = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!z2) {
                return sb2;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a2);
            int[] iArr2 = this.g;
            int i4 = iArr2[i2] + 1;
            iArr2[i2] = i4;
            sb3.append(i4);
            sb2 = sb3.toString();
        }
    }
    
    public void onClick(View view) {
        int id = view.getId();
        if (id == com.nexusteam.blacklogics.R.id.btn_editproperties) {
            o();
        } else if (id == com.nexusteam.blacklogics.R.id.img_close) {
        }
    }
    
    public hq a(ArrayList<ViewBean> arrayList, boolean z2) {
        if (z2) {
            lv.a(this.c).a(this.T.getXmlName(), arrayList);
            if (this.R != null) {
                this.R.a();
            }
        }
        hq hqVar = null;
        Iterator<ViewBean> it = arrayList.iterator();
        while (it.hasNext()) {
            ViewBean next = it.next();
            if (arrayList.indexOf(next) == 0) {
                hqVar = a(next);
            } else {
                a(next);
            }
        }
        return hqVar;
    }
    
    public void b(ArrayList<ViewBean> arrayList, boolean z2) {
        if (z2) {
            lv.a(this.c).b(this.T.getXmlName(), arrayList);
            if (this.R != null) {
                this.R.a();
            }
        }
        int size = arrayList.size();
        while (true) {
            size--;
            if (size >= 0) {
                b(arrayList.get(size));
            } else {
                return;
            }
        }
    }
    
    public hq a(ViewBean viewBean, boolean z2) {
        if (z2) {
            lv.a(this.c).a(this.T.getXmlName(), viewBean);
            if (this.R != null) {
                this.R.a();
            }
        }
        return a(viewBean);
    }
    
    public hq b(ViewBean viewBean, boolean z2) {
        if (z2) {
            lv.a(this.c).b(this.T.getXmlName(), viewBean);
            if (this.R != null) {
                this.R.a();
            }
        }
        return this.f1388a.f(viewBean);
    }
    
    private boolean e(ViewBean viewBean) {
        if (this.T.fileType != 1) {
            return (this.T.fileType == 2 && (viewBean.type == 0 || viewBean.type == 12 || viewBean.type == 2 || viewBean.type == 4 || viewBean.type == 5 || viewBean.type == 3 || viewBean.type == 6 || viewBean.type == 11 || viewBean.type == 13 || viewBean.type == 14 || viewBean.type == 8)) ? false : true;
        }
        if (viewBean.type == 0 || viewBean.type == 4 || viewBean.type == 5 || viewBean.type == 3 || viewBean.type == 6 || viewBean.type == 11 || viewBean.type == 13 || viewBean.type == 14 || viewBean.type == 8) {
            return false;
        }
        return true;
    }
    
    public hq a(ViewBean viewBean) {
        View d2 = this.f1388a.d(viewBean);
        this.f1388a.a(d2);
        String a2 = fe.a(viewBean.type);
        if (viewBean.id.indexOf(a2) == 0 && viewBean.id.length() > a2.length()) {
            try {
                int intValue = Integer.valueOf(viewBean.id.substring(a2.length())).intValue();
                if (this.g[viewBean.type] < intValue) {
                    this.g[viewBean.type] = intValue;
                }
            } catch (NumberFormatException unused) {
            }
        }
        d2.setOnTouchListener(this);
        return (hq) d2;
    }
    
    public void b(ViewBean viewBean) {
        this.f1388a.e(viewBean);



        
    }
    
    public hq c(ViewBean viewBean) {
        hq g2 = this.f1388a.g(viewBean);
        this.O.a();
        this.O.a(viewBean.id);
        return g2;
    }
    
    public void d() {
        this.f1388a.c();
        b();
        f();
    }
    
    public void a(ArrayList<ViewBean> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<ViewBean> it = arrayList.iterator();
            while (it.hasNext()) {
                a(it.next());
            }
        }
    }
    
    public void e() {
        this.f1388a.a();
    }
    
    public void d(ViewBean viewBean) {
        this.f1388a.a(viewBean).setOnTouchListener(this);
    }
    
    public void f() {
        if (this.K != null) {
            this.K.setSelection(false);
            this.K = null;
        }
        if (this.O != null) {
            this.O.a(false, "");
        }
        /*if (getContext() instanceof GraficEditorActivity) {
            ((GraficEditorActivity) getContext()).loadViewBeans();
        }*/
    }
    
    public void a(hq hqVar, boolean z2) {
        if (this.K != null) {
            this.K.setSelection(false);
        }
        this.K = hqVar;
        this.K.setSelection(true);
        if (this.O != null) {
            this.O.a(z2, this.K.getBean().id);
        }
    }
    
    /* access modifiers changed from: private */
    public void l() {
        boolean z2;
        boolean z3;
        if (this.u != null) {
            if (a(this.u)) {
                if (this.u instanceof iq) {
                    iq iqVar = (iq) this.u;
                    Iterator<ViewBean> it = iqVar.getData().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next().type == 17) {
                                z2 = true;
                                break;
                            }
                        } else {
                            z2 = false;
                            break;
                        }
                    }
                    if (!z2 || this.Q.c()) {
                        Iterator<ViewBean> it2 = iqVar.getData().iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                if (it2.next().type == 18) {
                                    z3 = true;
                                    break;
                                }
                            } else {
                                z3 = false;
                                break;
                            }
                        }
                        if (z3 && !this.Q.d()) {
                            ke.b(getContext(), (CharSequence) kq.a().a(getContext(), com.nexusteam.blacklogics.R.string.design_library_guide_setup_first), 0).show();
                            return;
                        }
                    } else {
                        ke.b(getContext(), (CharSequence) kq.a().a(getContext(), com.nexusteam.blacklogics.R.string.design_library_guide_setup_first), 0).show();
                        return;
                    }
                } else if ((this.u instanceof ik) && !this.Q.c()) {
                    ke.b(getContext(), (CharSequence) kq.a().a(getContext(), com.nexusteam.blacklogics.R.string.design_library_guide_setup_first), 0).show();
                    return;
                } else if ((this.u instanceof iv) && !this.Q.d()) {
                    ke.b(getContext(), (CharSequence) kq.a().a(getContext(), com.nexusteam.blacklogics.R.string.design_library_guide_setup_first), 0).show();
                    return;
                }
            }
            this.m.setScrollEnabled(false);
            this.n.setScrollEnabled(false);
            if (this.Q != null) {
                this.Q.a();
            }
            if (this.N) {
                this.t.vibrate(100);
            }
            this.w = true;
            this.A.a(this.u);
            this.A.bringToFront();
            f();
            this.A.a(this.u, this.x, this.y, this.x, this.y);
            this.A.a(this.J);
            if (!a(this.u)) {
                this.u.setVisibility(8);
                a(true);
                this.f1388a.b(((hq) this.u).getBean());
            } else if (this.u instanceof iq) {
                a(true);
                this.f1388a.b((ViewBean) null);
            } else {
                a(false);
                this.f1388a.b((ViewBean) null);
            }
            if (a(this.x, this.y)) {
                this.A.setAllow(true);
                int i2 = this.L;
                int i3 = this.M;
                if (!a(this.u)) {
                    i2 = this.u.getWidth();
                    i3 = this.u.getHeight();
                } else {
                    if (this.u instanceof is) {
                        i2 = -1;
                    }
                    if (this.u instanceof it) {
                        i3 = -1;
                    }
                }
                this.f1388a.a((int) this.x, (int) this.y, i2, i3);
                return;
            }
            this.A.setAllow(false);
            this.f1388a.a(true);
        }
    }
    
    private boolean b(View view) {
        ViewParent parent = view.getParent();
        while (parent != null && parent != this) {
            if ((parent instanceof ii) || (parent instanceof hy)) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }
    
    
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (motionEvent.getPointerId(motionEvent.getActionIndex()) > 0) {
            return true;
        }
        if (view == this.f1388a) {
            if (actionMasked == 0) {
                f();
                this.u = null;
            }
            return true;
        }
        if (actionMasked != 8) {
            switch (actionMasked) {
                case 0:
                this.w = false;
                this.x = motionEvent.getRawX();
                this.y = motionEvent.getRawY();
                this.u = view;
                if ((view instanceof hq) && ((hq) view).getFixed()) {
                    return true;
                }
                if (b(view) && this.Q != null) {
                    this.Q.a();
                }
                this.v.postDelayed(this.ad, (long) (ViewConfiguration.getLongPressTimeout() / 2));
                return true;
                case 1:
                if (!this.w) {
                    if (this.u instanceof hq) {
                        a((hq) this.u, true);
                    }
                    if (this.Q != null) {
                        this.Q.b();
                    }
                    this.A.setDummyVisibility(8);
                    this.u = null;
                    this.f1388a.d();
                    this.v.removeCallbacks(this.ad);
                    return true;
                }
                if (this.A.getAllow()) {
                    if (this.G && (this.u instanceof hq)) {
                        ArrayList<ViewBean> a2 = ma.a(this.c).a(this.d, ((hq) this.u).getBean());
                        for (int size = a2.size() - 1; size >= 0; size--) {
                            ma.a(this.c).a(this.T, a2.get(size));
                        }
                        b(a2, true);
                    } else if (!this.G || !(this.u instanceof iq)) {
                        this.f1388a.a(false);
                        if (this.u instanceof iq) {
                            iq iqVar = (iq) this.u;
                            ArrayList arrayList = new ArrayList();
                            kk kkVar = new kk();
                            boolean z2 = false;
                            for (int i2 = 0; i2 < iqVar.getData().size(); i2++) {
                                ViewBean viewBean = iqVar.getData().get(i2);
                                if (e(viewBean)) {
                                    arrayList.add(viewBean.clone());
                                    String str = viewBean.layout.backgroundResource;
                                    String str2 = viewBean.image.resName;
                                    if (!ma.c(this.c).h(str) && ek.f().a(str)) {
                                        ProjectResourceBean b2 = ek.f().b(str);
                                        try {
                                            kkVar.a(fe.w() + File.separator + "image" + File.separator + "data" + File.separator + b2.resFullName, fe.r() + File.separator + this.c + File.separator + b2.resFullName);
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                        ma.c(this.c).f373a.add(b2);
                                        z2 = true;
                                    }
                                    if (!ma.c(this.c).h(str2) && ek.f().a(str2)) {
                                        ProjectResourceBean b3 = ek.f().b(str2);
                                        try {
                                            kkVar.a(fe.w() + File.separator + "image" + File.separator + "data" + File.separator + b3.resFullName, fe.r() + File.separator + this.c + File.separator + b3.resFullName);
                                        } catch (Exception e3) {
                                            e3.printStackTrace();
                                        }
                                        ma.c(this.c).f373a.add(b3);
                                        z2 = true;
                                    }
                                }
                            }
                            if (z2) {
                                ke.a(getContext(), (CharSequence) kq.a().a(getContext(), com.nexusteam.blacklogics.R.string.view_widget_favorites_image_auto_added), 0).show();
                            }
                            if (arrayList.size() > 0) {
                                HashMap hashMap = new HashMap();
                                this.f1388a.a((ViewBean) arrayList.get(0), (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                                Iterator it = arrayList.iterator();
                                while (it.hasNext()) {
                                    ViewBean viewBean2 = (ViewBean) it.next();
                                    if (ma.a(this.c).n(this.T.getXmlName(), viewBean2.id)) {
                                        hashMap.put(viewBean2.id, a(viewBean2.type));
                                    } else {
                                        hashMap.put(viewBean2.id, viewBean2.id);
                                    }
                                    viewBean2.id = (String) hashMap.get(viewBean2.id);
                                    if (!(arrayList.indexOf(viewBean2) == 0 || viewBean2.parent == null || viewBean2.parent.length() <= 0)) {
                                        viewBean2.parent = (String) hashMap.get(viewBean2.parent);
                                    }
                                    ma.a(this.c).b(this.d, viewBean2);
                                }
                                a(a((ArrayList<ViewBean>) arrayList, true), true);
                            }
                        } else if (this.u instanceof il) {
                            ViewBean bean = ((il) this.u).getBean();
                            bean.id = a(bean.type);
                            this.f1388a.a(bean, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                            ma.a(this.c).b(this.d, bean);
                            /*HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder();
eventBuilder.setCategory("editor");
eventBuilder.setAction("widget");
eventBuilder.setLabel("Custom");
this.S.send(eventBuilder.build());*/                            
                            if (bean.type == 3 && this.T.fileType == 0) {
                                ma.a(this.c).a(this.T.getJavaName(), 1, bean.type, bean.id, "onClick");
                            }
                            a(a(bean, true), true);
                        } else if (this.u instanceof hq) {
                            ViewBean bean2 = ((hq) this.u).getBean();
                            this.f1388a.a(bean2, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                            a(b(bean2, true), true);
                        }
                    } else {
                        b(((iq) this.u).getName());
                    }
                } else if (this.u instanceof hq) {
                    ViewBean bean2 = ((hq) this.u).getBean();
                    a((hq) this.u, true);
                }
                
                this.m.setScrollEnabled(true);
                this.n.setScrollEnabled(true);
                if (this.Q != null) {
                    this.Q.b();
                }
                a(false);
                this.A.setDummyVisibility(8);
                this.u = null;
                this.f1388a.d();
                this.v.removeCallbacks(this.ad);
                this.w = false;
                this.A.setAllow(false);
                return true;
                case 2:
                if (this.w) {
                    this.v.removeCallbacks(this.ad);
                    this.A.a(view, motionEvent.getRawX(), motionEvent.getRawY(), this.x, this.y);
                    if (b(motionEvent.getRawX(), motionEvent.getRawY())) {
                        this.A.setAllow(true);
                        b(true);
                        return true;
                    }
                    if (this.G) {
                        b(false);
                    }
                    if (a(motionEvent.getRawX(), motionEvent.getRawY())) {
                        this.A.setAllow(true);
                        int i3 = this.L;
                        int i4 = this.M;
                        if (!a(this.u)) {
                            i3 = this.u.getWidth();
                            i4 = this.u.getHeight();
                        } else {
                            if (this.u instanceof is) {
                                i3 = -1;
                            }
                            if (this.u instanceof it) {
                                i4 = -1;
                            }
                        }
                        this.f1388a.a((int) motionEvent.getRawX(), (int) motionEvent.getRawY(), i3, i4);
                    } else {
                        this.A.setAllow(false);
                        this.f1388a.a(true);
                    }
                    return true;
                } else if (Math.abs(this.x - motionEvent.getRawX()) < ((float) this.z) && Math.abs(this.y - motionEvent.getRawY()) < ((float) this.z)) {
                    return true;
                } else {
                    this.u = null;
                    this.v.removeCallbacks(this.ad);
                    return true;
                }
                case 3:
                break;
                default:
                return true;
            }
        }
        this.m.setScrollEnabled(true);
        this.n.setScrollEnabled(true);
        if (this.Q != null) {
            this.Q.b();
        }
        a(false);
        this.A.setDummyVisibility(8);
        this.f1388a.d();
        this.v.removeCallbacks(this.ad);
        this.w = false;
        return true;
    }
    
    private boolean a(float f2, float f3) {
        int[] iArr = new int[2];
        this.f1388a.getLocationOnScreen(iArr);
        if (f2 <= ((float) iArr[0]) || f2 >= ((float) iArr[0]) + (((float) this.f1388a.getWidth()) * this.f1388a.getScaleX()) || f3 <= ((float) iArr[1]) || f3 >= ((float) iArr[1]) + (((float) this.f1388a.getHeight()) * this.f1388a.getScaleY())) {
            return false;
        }
        return true;
    }
    
    private void b(final String str) {
        final kd kdVar = new kd((Activity) getContext());
        kdVar.a(kq.a().a(getContext(), com.nexusteam.blacklogics.R.string.view_widget_favorites_delete_title));
        kdVar.a(com.nexusteam.blacklogics.R.drawable.high_priority_96_red);
        kdVar.b(kq.a().a(getContext(), com.nexusteam.blacklogics.R.string.view_widget_favorites_delete_message));
        kdVar.a(kq.a().a(getContext(), com.nexusteam.blacklogics.R.string.common_word_delete), new View.OnClickListener() {
            public void onClick(View view) {
                en.f().a(str, true);
                ViewEditor.this.setFavoriteData(en.f().g());
                kdVar.dismiss();
            }
        });
        kdVar.b(kq.a().a(getContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new View.OnClickListener() {
            public void onClick(View view) {
                kdVar.dismiss();
            }
        });
        kdVar.show();
    }
    
    private void m() {
        this.C = ObjectAnimator.ofFloat(this.B, "TranslationY", new float[]{0.0f});
        this.C.setDuration(500);
        this.C.setInterpolator(new DecelerateInterpolator());
        this.D = ObjectAnimator.ofFloat(this.B, "TranslationY", new float[]{(float) this.B.getHeight()});
        this.D.setDuration(300);
        this.D.setInterpolator(new DecelerateInterpolator());
        this.E = true;
    }
    
    private void n() {
        if (this.C.isRunning()) {
            this.C.cancel();
        }
        if (this.D.isRunning()) {
            this.D.cancel();
        }
    }
    
    private void a(boolean z2) {
        this.B.bringToFront();
        if (!this.E) {
            m();
        }
        if (this.F != z2) {
            this.F = z2;
            n();
            if (z2) {
                this.C.start();
            } else {
                this.D.start();
            }
        }
    }
    
    private boolean b(float f2, float f3) {
        int[] iArr = new int[2];
        this.B.getLocationOnScreen(iArr);
        if (f2 <= ((float) iArr[0]) || f2 >= ((float) (iArr[0] + this.B.getWidth())) || f3 <= ((float) iArr[1]) || f3 >= ((float) (iArr[1] + this.B.getHeight()))) {
            return false;
        }
        return true;
    }
    
    private void b(boolean z2) {
        if (this.G != z2) {
            this.G = z2;
            if (this.G) {
                this.B.setImageResource(com.nexusteam.blacklogics.R.drawable.icon_delete_active);
            } else {
                this.B.setImageResource(com.nexusteam.blacklogics.R.drawable.icon_delete);
            }
        }
    }
    
    public void setOnPropertyClickListener(gg ggVar) {
        this.P = ggVar;
    }
    
    public void setOnWidgetSelectedListener(hp hpVar) {
        this.O = hpVar;
    }
    
    public void setOnDraggingListener(hm hmVar) {
        this.Q = hmVar;
    }
    
    public void setOnHistoryChangeListener(hn hnVar) {
        this.R = hnVar;
    }
    
    private void o() {
        if (this.P != null) {
            this.P.a(this.d, this.K.getBean());
        }
    }
    
    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.b) {
            a();
        }
    }
    
    public void g() {
        this.f1388a.setResourceManager(ma.c(this.c));
    }
    
    class b extends LinearLayout implements View.OnClickListener {
        
        /* renamed from: a  reason: collision with root package name */
        PaletteGroupType f1395a;
        View b;
        ImageView c;
        
        public void onClick(View view) {
        }
        
        public b(Context context) {
            super(context);
            a(context);
        }
        
        private void a(Context context) {
            kp.a(context, this, com.nexusteam.blacklogics.R.layout.palette_group_item);
            this.b = findViewById(com.nexusteam.blacklogics.R.id.group_item);
            this.c = (ImageView) findViewById(com.nexusteam.blacklogics.R.id.img_group);
        }
        
        public void a(PaletteGroupType aVar) {
            this.f1395a = aVar;
            if (aVar == PaletteGroupType.ePaletteGroup_basic) {
                this.c.setImageResource(com.nexusteam.blacklogics.R.drawable.selector_palette_tab_ic_sketchware);
            } else {
                this.c.setImageResource(com.nexusteam.blacklogics.R.drawable.selector_palette_tab_ic_bookmark);
            }
            setOnClickListener(this);
        }
    }
}
