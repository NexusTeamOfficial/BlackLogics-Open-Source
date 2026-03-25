package com.nexusteam.internal.editor.view;

import com.nexusteam.internal.ek;
import com.nexusteam.internal.en;
import com.nexusteam.internal.er;
import com.nexusteam.internal.fl;
import com.nexusteam.internal.gg;
import com.nexusteam.internal.gh;
import com.nexusteam.internal.gi;
import com.nexusteam.internal.gj;
import com.nexusteam.internal.ha;
import com.nexusteam.internal.jg;
import com.nexusteam.internal.kd;
import com.nexusteam.internal.ke;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kp;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.lf;
import com.nexusteam.internal.ma;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.internal.lib.ui.CustomHorizontalScrollView;
import com.nexusteam.blacklogics.R;
import java.util.ArrayList;
import java.util.Iterator;

public class ViewProperty extends LinearLayout implements gi {
    
    /* renamed from: a  reason: collision with root package name */
    private final String f1405a = "see_all";
    /* access modifiers changed from: private */
    public String b;
    /* access modifiers changed from: private */
    public ProjectFileBean c;
    private Spinner d;
    /* access modifiers changed from: private */
    public ArrayList<ViewBean> e = new ArrayList<>();
    /* access modifiers changed from: private */
    public c f;
    private gh g = null;
    private CustomHorizontalScrollView h;
    private LinearLayout i;
    private LinearLayout j;
    private ha k;
    private b l;
    private View m;
    private ViewEvents n;
    /* access modifiers changed from: private */
    public gg o = null;
    /* access modifiers changed from: private */
    public gj p;
    private LinearLayout q;
    /* access modifiers changed from: private */
    public int r;
    private ImageView s;
    /* access modifiers changed from: private */
    public ObjectAnimator t;
    /* access modifiers changed from: private */
    public ObjectAnimator u;
    /* access modifiers changed from: private */
    public boolean v = true;
    
    public void a(String str, Object obj) {
    }
    
    public ViewProperty(Context context) {
        super(context);
        a(context);
    }
    
    public ViewProperty(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }
    
    private void a(Context context) {
        kp.a(context, this, R.layout.view_property);
        this.q = (LinearLayout) findViewById(R.id.layout_property_group);
        this.q.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.h = (CustomHorizontalScrollView) findViewById(R.id.hcv_property);
        this.m = findViewById(R.id.property_layout);
        this.i = (LinearLayout) findViewById(R.id.property_contents);
        this.j = (LinearLayout) findViewById(R.id.layout_property_see_all);
        this.n = (ViewEvents) findViewById(R.id.view_event);
        this.h.setOnScrollChangedListener(new CustomHorizontalScrollView.a() {
            public void a(int i, int i2, int i3, int i4) {
                if (Math.abs(i - i3) <= 5) {
                    return;
                }
                if (i > i3) {
                    if (ViewProperty.this.v) {
                        boolean unused = ViewProperty.this.v = false;
                        ViewProperty.this.d();
                        ViewProperty.this.u.start();
                    }
                } else if (!ViewProperty.this.v) {
                    boolean unused2 = ViewProperty.this.v = true;
                    ViewProperty.this.d();
                    ViewProperty.this.t.start();
                }
            }
        });
        this.s = (ImageView) findViewById(R.id.img_save);
        this.s.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!ki.a()) {
                    ViewProperty.this.f();
                }
            }
        });
        this.d = (Spinner) findViewById(R.id.spn_widget);
        this.f = new c(context, this.e);
        this.d.setAdapter(this.f);
        this.d.setSelection(0);
        this.d.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
            
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                ViewProperty.this.f.a(i);
                ViewProperty.this.b((ViewBean) ViewProperty.this.e.get(i));
            }
        });
        e();
        c();
        this.k = new ha(getContext());
        this.k.setOrientation(0);
        this.i.addView(this.k);
    }
    
    private void c() {
        if (this.t == null) {
            this.t = ObjectAnimator.ofFloat(this.j, View.TRANSLATION_Y, new float[]{0.0f});
            this.t.setDuration(400);
            this.t.setInterpolator(new DecelerateInterpolator());
        }
        if (this.u == null) {
            this.u = ObjectAnimator.ofFloat(this.j, View.TRANSLATION_Y, new float[]{kp.a(getContext(), 84.0f)});
            this.u.setDuration(200);
            this.u.setInterpolator(new DecelerateInterpolator());
        }
    }
    
    /* access modifiers changed from: private */
    public void d() {
        if (this.t.isRunning()) {
            this.t.cancel();
        }
        if (this.u.isRunning()) {
            this.u.cancel();
        }
    }
    
    public void setOnPropertyValueChangedListener(gj gjVar) {
        this.p = gjVar;
        this.k.setOnPropertyValueChangedListener(new gj() {
            public void a(ViewBean viewBean) {
                if (ViewProperty.this.p != null) {
                    ViewProperty.this.p.a(viewBean);
                }
            }
        });
    }
    
    public void a(String str, ProjectFileBean projectFileBean) {
        this.b = str;
        this.c = projectFileBean;
    }
    
    public void setOnPropertyListener(gg ggVar) {
        this.o = ggVar;
    }
    
    public void setOnEventClickListener(fl flVar) {
        this.n.setOnEventClickListener(flVar);
    }
    
    public void a(String str) {
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            if (this.e.get(i2).id.equals(str)) {
                this.d.setSelection(i2);
                return;
            }
        }
    }
    
    public void a(ArrayList<ViewBean> arrayList, ViewBean viewBean) {
        this.e.clear();
        Iterator<ViewBean> it = arrayList.iterator();
        while (it.hasNext()) {
            this.e.add(it.next());
        }
        if (viewBean != null) {
            this.e.add(0, viewBean);
        }
        this.f.notifyDataSetChanged();
    }
    
    public void setOnPropertyTargetChangeListener(gh ghVar) {
        this.g = ghVar;
    }
    
    private void e() {
        a(0, R.string.property_group_basic);
        a(1, R.string.property_group_event);
        a(2, R.string.property_group_recent);
    }
    
    private void a(int i2, int i3) {
        a aVar = new a(getContext());
        aVar.a(i2, i3);
        aVar.setTag(Integer.valueOf(i2));
        this.q.addView(aVar);
    }
    
    public void a() {
        if (this.k != null) {
            this.k.a();
        }
    }
    
    /* access modifiers changed from: private */
    public void b(ViewBean viewBean) {
        if (this.g != null) {
            this.g.a(viewBean.id);
        }
        if ("_fab".equals(viewBean.id)) {
            this.s.setVisibility(8);
        } else {
            this.s.setVisibility(0);
        }
        this.k.setProjectFileBean(this.c);
        b();
    }
    
    public void b() {
        for (int i2 = 0; i2 < this.q.getChildCount(); i2++) {
            a aVar = (a) this.q.getChildAt(i2);
            if (this.r == ((Integer) aVar.getTag()).intValue()) {
                aVar.setSelected(true);
                aVar.c.setTextColor(-1);
                aVar.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).start();
            } else {
                aVar.setSelected(false);
                aVar.c.setTextColor(-14868183);
                aVar.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f).start();
            }
        }
        if (this.f.a() < this.e.size()) {
            ViewBean viewBean = this.e.get(this.f.a());
            if (this.r == 0) {
                this.m.setVisibility(0);
                this.j.setVisibility(0);
                this.k.a(this.b, viewBean);
                a(viewBean);
                this.n.setVisibility(8);
            } else if (this.r == 1) {
                this.m.setVisibility(0);
                this.k.a(viewBean);
                this.j.setVisibility(8);
            } else if (this.r == 2) {
                this.m.setVisibility(8);
                this.n.setVisibility(0);
                this.n.a(this.b, this.c, viewBean);
            }
        }
    }
    
    /* access modifiers changed from: protected */
    public void a(ViewBean viewBean) {
        if (this.l == null) {
            this.l = new b(getContext());
            this.l.a("see_all", R.drawable.color_more_96, R.string.common_word_see_all);
            this.l.a(viewBean);
            this.j.addView(this.l);
            return;
        }
        this.l.a(viewBean);
    }
    
    /* access modifiers changed from: private */
    public void f() {
        Context context = getContext();
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        } else if (getRootView().getContext() instanceof Activity) {
            activity = (Activity) getRootView().getContext();
        }
        
        if (activity == null) {
            ke.b(getContext(), "Cannot open save dialog — invalid context", 0).show();
            return;
        }
        
        final kd kdVar = new kd(activity);
        kdVar.a(kq.a().a(getContext(), R.string.view_widget_favorites_save_title));
        kdVar.a(R.drawable.ic_bookmark_red_48dp);
        View a2 = kp.a(getContext(), R.layout.property_popup_save_to_favorite);
        ((TextView) a2.findViewById(R.id.tv_favorites_guide)).setText(kq.a().a(getContext(), R.string.view_widget_favorites_save_guide_new));
        final EditText editText = (EditText) a2.findViewById(R.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(524289);
        editText.setImeOptions(6);
        final lf lfVar = new lf(getContext(), (TextInputLayout) a2.findViewById(R.id.ti_input), en.f().h());
        kdVar.a(a2);
        kdVar.a(kq.a().a(getContext(), R.string.common_word_save), new View.OnClickListener() {
            public void onClick(View view) {
                if (!ki.a() && lfVar.a()) {
                    String obj = editText.getText().toString();
                    ArrayList<ViewBean> a2 = ma.a(ViewProperty.this.b).a(ViewProperty.this.c.getXmlName(), (ViewBean) ViewProperty.this.e.get(ViewProperty.this.f.a()));
                    Iterator<ViewBean> it = a2.iterator();
                    while (it.hasNext()) {
                        ViewBean next = it.next();
                        String str = next.layout.backgroundResource;
                        String str2 = next.image.resName;
                        if (str != null && !str.equals("NONE") && ma.c(ViewProperty.this.b).h(str) && !ek.f().a(str)) {
                            try {
                                ek.f().a(ViewProperty.this.b, ma.c(ViewProperty.this.b).k(str));
                            } catch (Exception e) {
                                e.printStackTrace();
                                ke.b(ViewProperty.this.getContext(), (CharSequence) e.getMessage(), 0).show();
                            }
                        }
                        if (str2 != null && !str2.equals("default_image") && !str2.equals("NONE") && ma.c(ViewProperty.this.b).h(str2) && !ek.f().a(str2)) {
                            try {
                                ek.f().a(ViewProperty.this.b, ma.c(ViewProperty.this.b).k(str2));
                            } catch (Exception e2) {
                                ke.b(ViewProperty.this.getContext(), (CharSequence) e2.getMessage(), 0).show();
                            }
                        }
                    }
                    try {
                        en.f().a(obj, a2, true);
                        if (ViewProperty.this.o != null) {
                            ViewProperty.this.o.a();
                        }
                        ke.a(ViewProperty.this.getContext(), (CharSequence) kq.a().a(ViewProperty.this.getContext(), R.string.common_message_complete_save), 0).show();
                        kdVar.dismiss();
                    } catch (Exception unused) {
                        ke.a(ViewProperty.this.getContext(), (CharSequence) "duplicated name!!", 0).show();
                    }
                }
            }
        });
        kdVar.b(kq.a().a(getContext(), R.string.common_word_cancel), new View.OnClickListener() {
            public void onClick(View view) {
                kdVar.dismiss();
            }
        });
        kdVar.show();
    }
    
    class c extends BaseAdapter {
        
        /* renamed from: a  reason: collision with root package name */
        Context f1415a;
        int b;
        ArrayList<ViewBean> c;
        
        public long getItemId(int i) {
            return (long) i;
        }
        
        public c(Context context, ArrayList<ViewBean> arrayList) {
            this.f1415a = context;
            this.c = arrayList;
        }
        
        public void a(int i) {
            this.b = i;
        }
        
        public int a() {
            return this.b;
        }
        
        public int getCount() {
            if (this.c == null) {
                return 0;
            }
            return this.c.size();
        }
        
        public Object getItem(int i) {
            return this.c.get(i);
        }
        
        private er a(int i, View view, ViewGroup viewGroup, boolean z, boolean z2) {
            er erVar;
            if (view != null) {
                erVar = (er) view;
            } else {
                erVar = new er(this.f1415a);
                erVar.setTextSize(R.dimen.text_size_body_small);
            }
            erVar.setDropDown(z2);
            ViewBean viewBean = this.c.get(i);
            erVar.a(ViewBean.getViewTypeResId(viewBean.type), viewBean.id, z);
            return erVar;
        }
        
        public View getView(int i, View view, ViewGroup viewGroup) {
            return a(i, view, viewGroup, false, false);
        }
        
        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return a(i, view, viewGroup, this.b == i, true);
        }
    }
    
    class b extends LinearLayout implements View.OnClickListener {
        
        /* renamed from: a  reason: collision with root package name */
        String f1414a;
        View b;
        ImageView c;
        TextView d;
        TextView e;
        ViewBean f;
        
        public b(Context context) {
            super(context);
            a(context);
        }
        
        private void a(Context context) {
            kp.a(context, this, R.layout.property_grid_item);
            this.b = findViewById(R.id.property_menu_item);
            this.c = (ImageView) findViewById(R.id.img_icon);
            this.d = (TextView) findViewById(R.id.tv_title);
            this.e = (TextView) findViewById(R.id.tv_sub_title);
        }
        
        public void a(String str, int i, int i2) {
            this.b.setVisibility(0);
            this.f1414a = str;
            this.c.setImageResource(i);
            this.d.setText(kq.a().a(getContext(), i2));
            this.d.setTextColor(-27365);
            setOnClickListener(this);
        }
        
        public void a(ViewBean viewBean) {
            this.f = viewBean;
        }
        
        public void onClick(View view) {
            if (ViewProperty.this.o != null) {
                ViewProperty.this.o.a(ViewProperty.this.c.getXmlName(), this.f);
            }
        }
    }
    
    class a extends LinearLayout implements View.OnClickListener {
        
        /* renamed from: a  reason: collision with root package name */
        int f1413a;
        View b;
        TextView c;
        
        public a(Context context) {
            super(context);
            a(context);
        }
        
        private void a(Context context) {
            kp.a(context, this, R.layout.property_group_item);
            this.b = findViewById(R.id.property_group_item);
            this.c = (TextView) findViewById(R.id.tv_title);
        }
        
        public void a(int i, int i2) {
            this.f1413a = i;
            setTag(Integer.valueOf(i));
            this.c.setText(kq.a().a(getContext(), i2));
            setOnClickListener(this);
        }
        
        public void onClick(View view) {
            int unused = ViewProperty.this.r = ((Integer) view.getTag()).intValue();
            ViewProperty.this.b();
        }
    }
}
