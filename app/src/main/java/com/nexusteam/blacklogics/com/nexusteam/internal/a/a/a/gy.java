package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;

public class gy extends LinearLayout implements View.OnClickListener {

    /* renamed from: a  reason: collision with root package name */
    private String f245a = "";
    private boolean b = false;
    private TextView c;
    private Switch d;
    private ImageView e;
    private int f;
    private View g;
    private View h;
    private gi i;

    public gy(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    private void a(Context context, boolean z) {
        kp.a(context, this, R.layout.property_switch_item_singleline);
        this.c = (TextView) findViewById(R.id.tv_name);
        this.d = (Switch) findViewById(R.id.switch_value);
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
        ki.a(this);
        this.f245a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.c.setText(kq.a().a(getResources(), identifier));
            String str2 = this.f245a;
            char c2 = 65535;
            int hashCode = str2.hashCode();
            if (hashCode != -782258371) {
                if (hashCode != -56658399) {
                    if (hashCode != 1160800983) {
                        if (hashCode == 1800186104 && str2.equals("property_clickable")) {
                            c2 = 2;
                        }
                    } else if (str2.equals("property_enabled")) {
                        c2 = 1;
                    }
                } else if (str2.equals("property_single_line")) {
                    c2 = 0;
                }
            } else if (str2.equals("property_checked")) {
                c2 = 3;
            }
            switch (c2) {
                case 0:
                    this.f = R.drawable.horizontal_line_48;
                    break;
                case 1:
                    this.f = R.drawable.light_on_48;
                    break;
                case 2:
                    this.f = R.drawable.natural_user_interface2_48;
                    break;
                case 3:
                    this.f = R.drawable.ok_48;
                    break;
            }
            if (this.h.getVisibility() == 0) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(this.f);
                ((TextView) findViewById(R.id.tv_title)).setText(kq.a().a(getContext(), identifier));
                return;
            }
            this.e.setImageResource(this.f);
        }
    }

    public String getKey() {
        return this.f245a;
    }

    public void setValue(boolean z) {
        this.b = z;
        this.d.setChecked(z);
    }

    public boolean getValue() {
        return this.b;
    }

    public void setOnPropertyValueChangeListener(gi giVar) {
        this.i = giVar;
    }

    public void onClick(View view) {
        setValue(!this.b);
        if (this.i != null) {
            this.i.a(this.f245a, Boolean.valueOf(this.b));
        }
    }
}
