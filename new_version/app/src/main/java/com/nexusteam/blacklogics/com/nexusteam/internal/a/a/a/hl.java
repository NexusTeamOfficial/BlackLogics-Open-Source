package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import com.google.android.material.textfield.TextInputLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.nexusteam.internal.beans.ColorBean;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.ArrayList;

public class hl extends PopupWindow {
    
    /* renamed from: a  reason: collision with root package name */
    View f266a;
    Activity b;
    /* access modifiers changed from: private */
    public b c;
    private ArrayList<ColorBean> d = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<ColorBean[]> e = new ArrayList<>();
    private LinearLayout f;
    /* access modifiers changed from: private */
    public lp g;
    /* access modifiers changed from: private */
    public EditText h;
    private TextView i;
    private TextView j;
    private HorizontalScrollView k;
    /* access modifiers changed from: private */
    public RecyclerView l;
    /* access modifiers changed from: private */
    public int m;
    /* access modifiers changed from: private */
    public int n;
    /* access modifiers changed from: private */
    public int o = -1;
    /* access modifiers changed from: private */
    public kv p;
    
    public interface b {
        void a(int i);
    }
    
    public hl(View view, Activity activity, int i2, boolean z, boolean z2) {
        super(activity);
        a(view, activity, i2, z, z2);
    }
    
    /* access modifiers changed from: protected */
    public void a(View view, final Activity activity, int i2, boolean z, boolean z2) {
        this.b = activity;
        this.f266a = view;
        this.p = new kv(activity, "P24");
        a(z, z2);
        for (int i3 = 0; i3 < this.e.size(); i3++) {
            ColorBean[] colorBeanArr = this.e.get(i3);
            int i4 = 0;
            while (true) {
                if (i4 >= colorBeanArr.length) {
                    break;
                } else if (colorBeanArr[i4].colorCode == i2) {
                    this.m = i3;
                    this.n = i3;
                    this.o = i4;
                    break;
                } else {
                    i4++;
                }
            }
        }
        super.setFocusable(true);
        super.setOutsideTouchable(true);
        super.setContentView(view);
        int[] c2 = ky.c(activity);
        super.setWidth(c2[0]);
        super.setHeight(c2[1]);
        this.k = (HorizontalScrollView) view.findViewById(R.id.layout_hsv_color);
        this.f = (LinearLayout) view.findViewById(R.id.layout_color_title);
        this.l = (RecyclerView) view.findViewById(R.id.color_list);
        this.l.setHasFixedSize(true);
        this.l.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        this.l.setAdapter(new a());
        this.l.setItemAnimator(new DefaultItemAnimator());
        this.h = (EditText) view.findViewById(R.id.et_custom_color);
        ((TextInputLayout) view.findViewById(R.id.ti_custom_color)).setHint(kq.a().a((Context) activity, R.string.picker_color_hint_enter_hex_color_code));
        this.i = (TextView) view.findViewById(R.id.tv_custom_color);
        this.g = new lp(activity, (TextInputLayout) view.findViewById(R.id.ti_custom_color), this.i);
        this.h.setPrivateImeOptions("defaultInputmode=english;");
        this.j = (TextView) view.findViewById(R.id.tv_add_color);
        this.j.setText(kq.a().a((Context) activity, R.string.common_word_add).toUpperCase());
        this.j.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (hl.this.g.a()) {
                    hl.this.b(String.format("#%8s", new Object[]{hl.this.h.getText().toString()}).replaceAll(" ", "F").toUpperCase());
                    hl.this.d();
                }
            }
        });
        this.l.getAdapter().notifyItemChanged(this.o);
        this.f.removeAllViews();
        for (int ii5 = 0; ii5 < this.d.size(); ii5++) {
            final int index = ii5; // ← final copy for inner classes
            
            hk hkVar = new hk(activity);
            ColorBean colorBean = this.d.get(ii5);
            
            hkVar.b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    hl.this.n = index;
                    if (index == 0 && ((ColorBean[]) hl.this.e.get(index)).length == 0) {
                        ke.b((Context) activity, (CharSequence) kq.a().a((Context) activity, R.string.picker_color_custom_color_not_found), 1).show();
                    }
                    hl.this.l.getAdapter().notifyDataSetChanged();
                }
            });
            
            hkVar.b.setText(colorBean.colorName);
            hkVar.b.setTextColor(colorBean.displayNameColor);
            hkVar.b.setBackgroundColor(colorBean.colorCode);
            this.f.addView(hkVar);
            
            if (ii5 == this.m) {
                hkVar.c.setImageResource(colorBean.icon);
                hkVar.c.setVisibility(0);
            } else {
                hkVar.c.setVisibility(8);
            }
            
            hkVar.b.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View view) {
                    if (index != 0) {
                        return false;
                    }
                    hl.this.c();
                    return false;
                }
            });
        }
        
        Animation animation = view.getAnimation();
        if (animation != null) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }
                
                public void onAnimationStart(Animation animation) {
                }
                
                public void onAnimationEnd(Animation animation) {
                    hl.this.a();
                }
            });
        }
    }
    
    /* access modifiers changed from: private */
    public void a() {
        if (this.m < this.f.getChildCount()) {
            this.k.smoothScrollTo((int) this.f.getChildAt(this.m).getX(), 0);
            this.l.scrollToPosition(this.o);
        }
    }
    
    /* access modifiers changed from: protected */
    public void a(boolean z, boolean z2) {
        this.d.add(new ColorBean("#FFF6F6F6", "CUSTOM", "#212121", R.drawable.checked_grey_32));
        this.d.add(fa.p[0]);
        this.d.add(fa.q[0]);
        this.d.add(fa.r[0]);
        this.d.add(fa.s[0]);
        this.d.add(fa.t[0]);
        this.d.add(fa.u[0]);
        this.d.add(fa.v[0]);
        this.d.add(fa.w[0]);
        this.d.add(fa.x[0]);
        this.d.add(fa.y[0]);
        this.d.add(fa.z[0]);
        this.d.add(fa.A[0]);
        this.d.add(fa.B[0]);
        this.d.add(fa.C[0]);
        this.d.add(fa.D[0]);
        this.d.add(fa.E[0]);
        this.d.add(fa.F[0]);
        this.d.add(fa.G[0]);
        this.d.add(fa.H[0]);
        this.d.add(fa.I[0]);
        this.d.add(fa.J[0]);
        this.e.add(b());
        this.e.add(fa.p);
        this.e.add(fa.q);
        this.e.add(fa.r);
        this.e.add(fa.s);
        this.e.add(fa.t);
        this.e.add(fa.u);
        this.e.add(fa.v);
        this.e.add(fa.w);
        this.e.add(fa.x);
        this.e.add(fa.y);
        this.e.add(fa.z);
        this.e.add(fa.A);
        this.e.add(fa.B);
        this.e.add(fa.C);
        this.e.add(fa.D);
        this.e.add(fa.E);
        this.e.add(fa.F);
        this.e.add(fa.G);
        this.e.add(fa.H);
        this.e.add(fa.I);
        this.e.add(fa.J);
        if (z) {
            this.d.add(fa.K[0]);
            this.e.add(fa.K);
        }
        if (z2) {
            this.d.add(fa.L[0]);
            this.e.add(fa.L);
        }
    }
    
    /* access modifiers changed from: private */
    public ColorBean[] b() {
        String d2 = this.p.d("P24I1");
        if (d2.isEmpty()) {
            return new ColorBean[0];
        }
        String[] split = d2.split(",");
        ColorBean[] colorBeanArr = new ColorBean[split.length];
        for (int i2 = 0; i2 < split.length; i2++) {
            try {
                int parseColor = Color.parseColor(split[i2]);
                int red = Color.red(parseColor);
                int green = Color.green(parseColor);
                int blue = Color.blue(parseColor);
                int i3 = red > 240 ? 1 : 0;
                if (green > 240) {
                    i3++;
                }
                if (blue > 240) {
                    i3++;
                }
                if (i3 >= 2) {
                    colorBeanArr[i2] = new ColorBean(split[i2], "CUSTOM", "#212121", R.drawable.checked_grey_32);
                } else {
                    colorBeanArr[i2] = new ColorBean(split[i2], "CUSTOM", "#ffffff", R.drawable.checked_white_32);
                }
            } catch (Exception unused) {
                this.p.c();
                colorBeanArr = new ColorBean[0];
            }
        }
        return colorBeanArr;
    }
    
    /* access modifiers changed from: private */
    public void a(String str) {
        String d2 = this.p.d("P24I1");
        if (d2.contains(str)) {
            this.p.a("P24I1", (Object) d2.replaceAll(str + ",", ""));
            this.e.set(0, b());
            d();
        }
    }
    
    /* access modifiers changed from: private */
    public void b(String str) {
        String d2 = this.p.d("P24I1");
        if (d2.contains(str)) {
            ke.b((Context) this.b, (CharSequence) kq.a().a((Context) this.b, R.string.picker_color_already_exist), 0).show();
            return;
        }
        this.p.a("P24I1", (Object) str + "," + d2);
        this.e.set(0, b());
        d();
        this.m = 0;
        a();
    }
    
    /* access modifiers changed from: private */
    public void c() {
        final kd kdVar = new kd(this.b);
        kdVar.a(R.drawable.delete_96);
        kdVar.a(kq.a().a((Context) this.b, R.string.picker_color_title_delete_all_custom_color));
        kdVar.b(kq.a().a((Context) this.b, R.string.picker_color_message_delete_all_custom_color));
        kdVar.a(kq.a().a((Context) this.b, R.string.common_word_delete), new View.OnClickListener() {
            public void onClick(View view) {
                hl.this.p.c();
                hl.this.e.set(0, hl.this.b());
                hl.this.d();
                kdVar.dismiss();
            }
        });
        kdVar.b(kq.a().a((Context) this.b, R.string.common_word_cancel), new View.OnClickListener() {
            public void onClick(View view) {
                kdVar.dismiss();
            }
        });
        kdVar.show();
    }
    
    /* access modifiers changed from: private */
    public void c(final String str) {
        final kd kdVar = new kd(this.b);
        kdVar.a(R.drawable.delete_96);
        kdVar.a(kq.a().a((Context) this.b, R.string.picker_color_title_delete_custom_color));
        kdVar.b(kq.a().a((Context) this.b, R.string.picker_color_message_delete_custom_color));
        kdVar.a(kq.a().a((Context) this.b, R.string.common_word_delete), new View.OnClickListener() {
            public void onClick(View view) {
                hl.this.a(str);
                hl.this.d();
                kdVar.dismiss();
            }
        });
        kdVar.b(kq.a().a((Context) this.b, R.string.common_word_cancel), new View.OnClickListener() {
            public void onClick(View view) {
                kdVar.dismiss();
            }
        });
        kdVar.show();
    }
    
    /* access modifiers changed from: private */
    public void d() {
        this.n = 0;
        this.m = 0;
        this.o = 0;
        this.l.getAdapter().notifyDataSetChanged();
    }
    
    public void a(b bVar) {
        this.c = bVar;
    }
    
    public class a extends RecyclerView.Adapter<a.C0005a> {
        
        public a() {}
        
        @Override
        public C0005a onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.color_picker_item, parent, false);
            return new C0005a(v);
        }
        
        @Override
        public void onBindViewHolder(C0005a holder, int position) {
            ColorBean bean = ((ColorBean[]) hl.this.e.get(hl.this.n))[position];
            holder.b.setText(bean.getColorCode(hl.this.n == 0));
            holder.c.setText(position == 0 ? bean.colorName : "");
            holder.b.setTextColor(bean.displayNameColor);
            holder.c.setTextColor(bean.displayNameColor);
            holder.f276a.setBackgroundColor(bean.colorCode);
            if (position == hl.this.o && hl.this.n == hl.this.m) {
                holder.d.setImageResource(bean.icon);
                holder.d.setVisibility(View.VISIBLE);
            } else {
                holder.d.setVisibility(View.GONE);
            }
        }
        
        @Override
        public int getItemCount() {
            return ((ColorBean[]) hl.this.e.get(hl.this.n)).length;
        }
        
        public class C0005a extends RecyclerView.ViewHolder {
            public View f276a;
            public TextView b;
            public TextView c;
            public ImageView d;
            
            public C0005a(View itemView) {
                super(itemView);
                f276a = itemView.findViewById(R.id.layout_color_item);
                b = itemView.findViewById(R.id.tv_color_code);
                c = itemView.findViewById(R.id.tv_color_name);
                d = itemView.findViewById(R.id.img_selector);
                
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (hl.this.c != null) {
                            String code = b.getText().toString();
                            if ("TRANSPARENT".equals(code)) {
                                hl.this.c.a(0);
                            } else if ("NONE".equals(code)) {
                                hl.this.c.a(0xFFFFFF);
                            } else {
                                hl.this.c.a(Color.parseColor(code));
                            }
                        }
                        hl.this.dismiss();
                    }
                });
                
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (hl.this.n != 0) return false;
                        hl.this.c(b.getText().toString());
                        return true;
                    }
                });
            }
        }
    }
    
}
