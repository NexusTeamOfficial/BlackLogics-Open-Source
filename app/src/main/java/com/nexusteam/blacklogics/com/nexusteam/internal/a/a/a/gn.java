package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class gn extends RelativeLayout implements View.OnClickListener {
    /* access modifiers changed from: private */

    /* renamed from: a  reason: collision with root package name */
    public String f202a = "";
    /* access modifiers changed from: private */
    public int b = -1;
    private TextView c;
    private TextView d;
    private ImageView e;
    private int f;
    private View g;
    private View h;
    /* access modifiers changed from: private */
    public gi i;

    public gn(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    private void a(Context context, boolean z) {
        kp.a(context, this, R.layout.property_selector_item);
        this.c = (TextView) findViewById(R.id.tv_name);
        this.d = (TextView) findViewById(R.id.tv_value);
        this.e = (ImageView) findViewById(R.id.img_left_icon);
        this.g = findViewById(R.id.property_item);
        this.h = findViewById(R.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public void setOrientationItem(int i2) {
        if (i2 == 0) {
            this.g.setVisibility(8);
            this.h.setVisibility(0);
            return;
        }
        this.g.setVisibility(0);
        this.h.setVisibility(8);
    }

    public void setKey(String str) {
        this.f202a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.c.setText(kq.a().a(getResources(), identifier));
            this.f = R.drawable.gravity_96;
            if (this.h.getVisibility() == 0) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(this.f);
                ((TextView) findViewById(R.id.tv_title)).setText(kq.a().a(getContext(), identifier));
                return;
            }
            this.e.setImageResource(this.f);
        }
    }

    public String getKey() {
        return this.f202a;
    }

    public void setValue(int i2) {
        this.b = i2;
        this.d.setText(fa.a(i2));
    }

    public int getValue() {
        return this.b;
    }

    public void setOnPropertyValueChangeListener(gi giVar) {
        this.i = giVar;
    }

    public void onClick(View view) {
        if (!ki.a()) {
            String str = this.f202a;
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -1474767389) {
                if (hashCode == -1244048924 && str.equals("property_gravity")) {
                    c2 = 0;
                }
            } else if (str.equals("property_layout_gravity")) {
                c2 = 1;
            }
            switch (c2) {
                case 0:
                case 1:
                    a();
                    return;
                default:
                    return;
            }
        }
    }

    private void a() {
        final kd kdVar = new kd((Activity) getContext());
        kdVar.a(this.c.getText().toString());
        kdVar.a(this.f);
        View a2 = kp.a(getContext(), R.layout.property_popup_selector_gravity);
        final CheckBox checkBox = (CheckBox) a2.findViewById(R.id.chk_left);
        final CheckBox checkBox2 = (CheckBox) a2.findViewById(R.id.chk_right);
        final CheckBox checkBox3 = (CheckBox) a2.findViewById(R.id.chk_hcenter);
        final CheckBox checkBox4 = (CheckBox) a2.findViewById(R.id.chk_top);
        final CheckBox checkBox5 = (CheckBox) a2.findViewById(R.id.chk_bottom);
        final CheckBox checkBox6 = (CheckBox) a2.findViewById(R.id.chk_vcenter);
        int i2 = this.b & 112;
        int i3 = this.b & 7;
        if (i3 == 1) {
            checkBox3.setChecked(true);
        } else {
            if ((i3 & 3) == 3) {
                checkBox.setChecked(true);
            }
            if ((i3 & 5) == 5) {
                checkBox2.setChecked(true);
            }
        }
        if (i2 == 16) {
            checkBox6.setChecked(true);
        } else {
            if ((i2 & 48) == 48) {
                checkBox4.setChecked(true);
            }
            if ((i2 & 80) == 80) {
                checkBox5.setChecked(true);
            }
        }
        kdVar.a(a2);
        final kd kdVar2 = kdVar;
        kdVar.a(kq.a().a(getContext(), R.string.common_word_select), new View.OnClickListener() {
            public void onClick(View view) {
                int i = checkBox.isChecked() ? 3 : 0;
                if (checkBox2.isChecked()) {
                    i |= 5;
                }
                if (checkBox3.isChecked()) {
                    i |= 1;
                }
                if (checkBox4.isChecked()) {
                    i |= 48;
                }
                if (checkBox5.isChecked()) {
                    i |= 80;
                }
                if (checkBox6.isChecked()) {
                    i |= 16;
                }
                gn.this.setValue(i);
                if (gn.this.i != null) {
                    gn.this.i.a(gn.this.f202a, Integer.valueOf(gn.this.b));
                }
                kdVar2.dismiss();
            }
        });
        kdVar.b(kq.a().a(getContext(), R.string.common_word_cancel), new View.OnClickListener() {
            public void onClick(View view) {
                kdVar.dismiss();
            }
        });
        kdVar.show();
    }
}
