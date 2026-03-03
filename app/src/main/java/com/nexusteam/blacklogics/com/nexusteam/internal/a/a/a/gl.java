package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.blacklogics.R;
import java.util.ArrayList;
import java.util.Iterator;

public class gl extends RelativeLayout implements View.OnClickListener {
    /* access modifiers changed from: private */

    /* renamed from: a  reason: collision with root package name */
    public String f193a = "";
    /* access modifiers changed from: private */
    public String b = "";
    private TextView c;
    private TextView d;
    private ImageView e;
    private int f;
    private View g;
    private View h;
    private ArrayList<ProjectFileBean> i;
    /* access modifiers changed from: private */
    public ViewGroup j;
    /* access modifiers changed from: private */
    public gi k;

    public gl(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    private void a(Context context, boolean z) {
        kp.a(getContext(), this, R.layout.property_selector_item);
        this.c = (TextView) findViewById(R.id.tv_name);
        this.d = (TextView) findViewById(R.id.tv_value);
        this.g = findViewById(R.id.property_item);
        this.h = findViewById(R.id.property_menu_item);
        this.e = (ImageView) findViewById(R.id.img_left_icon);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public void setCustomView(ArrayList<ProjectFileBean> arrayList) {
        this.i = arrayList;
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
        this.f193a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.c.setText(kq.a().a(getResources(), identifier));
            this.f = R.drawable.form_48;
            if (this.h.getVisibility() == 0) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(this.f);
                ((TextView) findViewById(R.id.tv_title)).setText(kq.a().a(getContext(), identifier));
                return;
            }
            this.e.setImageResource(this.f);
        }
    }

    public String getKey() {
        return this.f193a;
    }

    public void setValue(String str) {
        if (str == null || str.length() <= 0) {
            str = "none";
        }
        this.b = str;
        this.d.setText(str);
    }

    public String getValue() {
        return this.b;
    }

    public void setOnPropertyValueChangeListener(gi giVar) {
        this.k = giVar;
    }

    public void onClick(View view) {
        if (!ki.a()) {
            String str = this.f193a;
            char c2 = 65535;
            if (str.hashCode() == 1118712953 && str.equals("property_custom_view_listview")) {
                c2 = 0;
            }
            if (c2 == 0) {
                a();
            }
        }
    }

    private void a() {
        final kd kdVar = new kd((Activity) getContext());
        kdVar.a(this.c.getText().toString());
        kdVar.a(this.f);
        View a2 = kp.a(getContext(), R.layout.property_popup_selector_single);
        this.j = (ViewGroup) a2.findViewById(R.id.rg_content);
        this.j.addView(a("none"));
        Iterator<ProjectFileBean> it = this.i.iterator();
        while (it.hasNext()) {
            this.j.addView(a(it.next().fileName));
        }
        int childCount = this.j.getChildCount();
        int i2 = 0;
        ((RadioButton) this.j.getChildAt(0)).setChecked(true);
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            RadioButton radioButton = (RadioButton) this.j.getChildAt(i2);
            if (radioButton.getTag().toString().equals(this.b)) {
                radioButton.setChecked(true);
                break;
            }
            i2++;
        }
        kdVar.a(a2);
        kdVar.a(kq.a().a(getContext(), R.string.common_word_select), new View.OnClickListener() {
            public void onClick(View view) {
                int childCount = gl.this.j.getChildCount();
                int i = 0;
                while (true) {
                    if (i >= childCount) {
                        break;
                    }
                    RadioButton radioButton = (RadioButton) gl.this.j.getChildAt(i);
                    if (radioButton.isChecked()) {
                        gl.this.setValue(radioButton.getTag().toString());
                        break;
                    }
                    i++;
                }
                if (gl.this.k != null) {
                    gl.this.k.a(gl.this.f193a, gl.this.b);
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

    private RadioButton a(String str) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText(str);
        radioButton.setTag(str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int) (kp.a(getContext(), 1.0f) * 40.0f));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }
}
