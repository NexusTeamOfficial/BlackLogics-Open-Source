package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.app.Activity;
import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class gt extends RelativeLayout implements View.OnClickListener {

    /* renamed from: a  reason: collision with root package name */
    Context f234a;
    /* access modifiers changed from: private */
    public String b = "";
    /* access modifiers changed from: private */
    public int c = 1;
    private TextView d;
    private TextView e;
    private ImageView f;
    private int g;
    private View h;
    private View i;
    /* access modifiers changed from: private */
    public gi j;

    public gt(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    private void a(Context context, boolean z) {
        this.f234a = context;
        kp.a(context, this, R.layout.property_input_item);
        this.d = (TextView) findViewById(R.id.tv_name);
        this.e = (TextView) findViewById(R.id.tv_value);
        this.f = (ImageView) findViewById(R.id.img_left_icon);
        this.h = findViewById(R.id.property_item);
        this.i = findViewById(R.id.property_menu_item);
        if (z) {
            setSoundEffectsEnabled(true);
            setOnClickListener(this);
        }
    }

    public void setOrientationItem(int i2) {
        if (i2 == 0) {
            this.h.setVisibility(8);
            this.i.setVisibility(0);
            return;
        }
        this.h.setVisibility(0);
        this.i.setVisibility(8);
    }

    public void setKey(String str) {
        this.b = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.d.setText(kq.a().a(getResources(), identifier));
            this.g = R.drawable.expand_48;
            if (this.i.getVisibility() == 0) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(this.g);
                ((TextView) findViewById(R.id.tv_title)).setText(kq.a().a(getContext(), identifier));
                return;
            }
            this.f.setImageResource(this.g);
        }
    }

    public String getKey() {
        return this.b;
    }

    public void setValue(int i2) {
        this.c = i2;
        TextView textView = this.e;
        textView.setText(this.c + " dp");
    }

    public int getValue() {
        return this.c;
    }

    public void setOnPropertyValueChangeListener(gi giVar) {
        this.j = giVar;
    }

    public void onClick(View view) {
        if (!ki.a()) {
            String str = this.b;
            char c2 = 65535;
            if (str.hashCode() == -1919612745 && str.equals("property_divider_height")) {
                c2 = 0;
            }
            if (c2 == 0) {
                a();
            }
        }
    }

    private void a() {
        final kd kdVar = new kd((Activity) getContext());
        kdVar.a(this.d.getText().toString());
        kdVar.a(this.g);
        View a2 = kp.a(getContext(), R.layout.property_popup_input_size);
        final EditText editText = (EditText) a2.findViewById(R.id.et_input);
        final ll llVar = new ll(this.f234a, (TextInputLayout) a2.findViewById(R.id.ti_input), 0, 999);
        llVar.a(String.valueOf(this.c));
        kdVar.a(a2);
        kdVar.a(kq.a().a(getContext(), R.string.common_word_save), new View.OnClickListener() {
            public void onClick(View view) {
                if (llVar.a()) {
                    gt.this.setValue(Integer.valueOf(editText.getText().toString()).intValue());
                    if (gt.this.j != null) {
                        gt.this.j.a(gt.this.b, Integer.valueOf(gt.this.c));
                    }
                    kdVar.dismiss();
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
}
