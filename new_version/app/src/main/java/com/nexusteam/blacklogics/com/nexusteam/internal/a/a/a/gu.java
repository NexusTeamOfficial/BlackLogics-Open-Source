package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.app.Activity;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class gu extends RelativeLayout implements View.OnClickListener {
    /* access modifiers changed from: private */

    /* renamed from: a  reason: collision with root package name */
    public String f237a = "";
    /* access modifiers changed from: private */
    public String b = "";
    private TextView c;
    private TextView d;
    private ImageView e;
    private int f;
    private View g;
    private View h;
    /* access modifiers changed from: private */
    public ViewGroup i;
    /* access modifiers changed from: private */
    public gi j;

    public gu(Context context, boolean z) {
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
        this.f237a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.c.setText(kq.a().a(getResources(), identifier));
            char c2 = 65535;
            if (str.hashCode() == -78143730 && str.equals("property_progressbar_style")) {
                c2 = 0;
            }
            if (c2 == 0) {
                this.f = R.drawable.style_48dp;
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
        return this.f237a;
    }

    public void setValue(String str) {
        this.b = str;
        this.d.setText(str);
    }

    public String getValue() {
        return this.b;
    }

    public void setOnPropertyValueChangeListener(gi giVar) {
        this.j = giVar;
    }

    public void onClick(View view) {
        if (!ki.a()) {
            a();
        }
    }

    private void a() {
        final kd kdVar = new kd((Activity) getContext());
        kdVar.a(this.c.getText().toString());
        kdVar.a(this.f);
        View a2 = kp.a(getContext(), R.layout.property_popup_selector_single);
        this.i = (ViewGroup) a2.findViewById(R.id.rg_content);
        int i2 = 0;
        for (Pair a3 : fa.b(this.f237a)) {
            this.i.addView(a((Pair<String, String>) a3));
        }
        int childCount = this.i.getChildCount();
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            RadioButton radioButton = (RadioButton) this.i.getChildAt(i2);
            if (radioButton.getTag().toString().equals(this.b)) {
                radioButton.setChecked(true);
                break;
            }
            i2++;
        }
        kdVar.a(a2);
        kdVar.a(kq.a().a(getContext(), R.string.common_word_select), new View.OnClickListener() {
            public void onClick(View view) {
                int childCount = gu.this.i.getChildCount();
                int i = 0;
                while (true) {
                    if (i >= childCount) {
                        break;
                    }
                    RadioButton radioButton = (RadioButton) gu.this.i.getChildAt(i);
                    if (radioButton.isChecked()) {
                        gu.this.setValue(radioButton.getTag().toString());
                        break;
                    }
                    i++;
                }
                if (gu.this.j != null) {
                    gu.this.j.a(gu.this.f237a, gu.this.b);
                }
                kdVar.dismiss();
            }
        });
        kdVar.b(kq.a().a(getContext(), R.string.common_word_cancel), new View.OnClickListener() {
            public void onClick(View view) {
                kdVar.dismiss();
            }
        });
        kdVar.show();
    }

    private RadioButton a(Pair<String, String> pair) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText((CharSequence) pair.second);
        radioButton.setTag(pair.first);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int) (kp.a(getContext(), 1.0f) * 40.0f));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }
}
