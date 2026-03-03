package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.hl;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class gk extends RelativeLayout implements View.OnClickListener {

    /* renamed from: a  reason: collision with root package name */
    private Context f191a;
    /* access modifiers changed from: private */
    public String b;
    /* access modifiers changed from: private */
    public int c;
    private TextView d;
    private TextView e;
    private View f;
    private ImageView g;
    private int h;
    private View i;
    private View j;
    /* access modifiers changed from: private */
    public gi k;

    public gk(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    private void a(Context context, boolean z) {
        this.f191a = context;
        kp.a(context, this, R.layout.property_color_item);
        this.d = (TextView) findViewById(R.id.tv_name);
        this.e = (TextView) findViewById(R.id.tv_value);
        this.f = findViewById(R.id.view_color);
        this.g = (ImageView) findViewById(R.id.img_left_icon);
        this.i = findViewById(R.id.property_item);
        this.j = findViewById(R.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public void setOrientationItem(int i2) {
        if (i2 == 0) {
            this.i.setVisibility(8);
            this.j.setVisibility(0);
            return;
        }
        this.i.setVisibility(0);
        this.j.setVisibility(8);
    }

    public void setKey(String str) {
        this.b = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.d.setText(kq.a().a(getResources(), identifier));
            this.h = R.drawable.color_palette_48;
            if (this.j.getVisibility() == 0) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(this.h);
                ((TextView) findViewById(R.id.tv_title)).setText(kq.a().a(getContext(), identifier));
                return;
            }
            this.g.setImageResource(this.h);
        }
    }

    public String getKey() {
        return this.b;
    }

    public void setValue(int i2) {
        this.c = i2;
        if (i2 == 0) {
            this.e.setText("TRANSPARENT");
            this.f.setBackgroundColor(i2);
        } else if (i2 == 16777215) {
            this.e.setText("NONE");
            this.f.setBackgroundColor(i2);
        } else {
            this.e.setText(String.format("#%08X", new Object[]{Integer.valueOf(i2 & -1)}));
            this.f.setBackgroundColor(i2);
        }
    }

    public int getValue() {
        return this.c;
    }

    public void setOnPropertyValueChangeListener(gi giVar) {
        this.k = giVar;
    }

    public void onClick(View view) {
        if (!ki.a()) {
            a();
        }
    }

    private void a() {
        boolean z;
        boolean z2;
        View a2 = kp.a(this.f191a, R.layout.color_picker);
        a2.setAnimation(AnimationUtils.loadAnimation(this.f191a, R.anim.abc_fade_in));
        if (this.b == "property_background_color") {
            z2 = true;
            z = true;
        } else {
            z2 = false;
            z = false;
        }
        hl hlVar = new hl(a2, (Activity) this.f191a, this.c, z2, z);
        hlVar.a((hl.b) new hl.b() {
            public void a(int i) {
                gk.this.setValue(i);
                if (gk.this.k != null) {
                    gk.this.k.a(gk.this.b, Integer.valueOf(gk.this.c));
                }
            }
        });
        hlVar.setAnimationStyle(R.anim.abc_fade_in);
        hlVar.showAtLocation(a2, 17, 0, 0);
    }
}
