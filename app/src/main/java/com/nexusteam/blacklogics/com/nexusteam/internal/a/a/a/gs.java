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
import com.nexusteam.blacklogics.R;

public class gs extends RelativeLayout implements View.OnClickListener {
    /* access modifiers changed from: private */

    /* renamed from: a  reason: collision with root package name */
    public String f231a = "";
    /* access modifiers changed from: private */
    public int b = -1;
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

    public gs(Context context, boolean z) {
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
        this.f231a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.c.setText(kq.a().a(getResources(), identifier));
            String str2 = this.f231a;
            char c2 = 65535;
            switch (str2.hashCode()) {
                case -1567696407:
                    if (str2.equals("property_text_size")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case -1353621303:
                    if (str2.equals("property_text_style")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -522792099:
                    if (str2.equals("property_ime_option")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case -512158157:
                    if (str2.equals("property_spinner_mode")) {
                        c2 = 5;
                        break;
                    }
                    break;
                case 235805286:
                    if (str2.equals("property_orientation")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 1096920256:
                    if (str2.equals("property_first_day_of_week")) {
                        c2 = 7;
                        break;
                    }
                    break;
                case 1106908695:
                    if (str2.equals("property_choice_mode")) {
                        c2 = 6;
                        break;
                    }
                    break;
                case 2133471033:
                    if (str2.equals("property_input_type")) {
                        c2 = 4;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    this.f = R.drawable.grid_3_48;
                    break;
                case 1:
                    this.f = R.drawable.abc_96_color;
                    break;
                case 2:
                    this.f = R.drawable.text_width_96;
                    break;
                case 3:
                    this.f = R.drawable.keyboard_48;
                    break;
                case 4:
                    this.f = R.drawable.keyboard_48;
                    break;
                case 5:
                    this.f = R.drawable.pull_down_48;
                    break;
                case 6:
                    this.f = R.drawable.multiple_choice_48;
                    break;
                case 7:
                    this.f = R.drawable.monday_48;
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
        return this.f231a;
    }

    public void setValue(int i2) {
        this.b = i2;
        this.d.setText(fa.a(this.f231a, i2));
    }

    public int getValue() {
        return this.b;
    }

    public void setOnPropertyValueChangeListener(gi giVar) {
        this.j = giVar;
    }

    public void onClick(View view) {
        if (!ki.a()) {
            String str = this.f231a;
            char c2 = 65535;
            switch (str.hashCode()) {
                case -1567696407:
                    if (str.equals("property_text_size")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case -1353621303:
                    if (str.equals("property_text_style")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -522792099:
                    if (str.equals("property_ime_option")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case -512158157:
                    if (str.equals("property_spinner_mode")) {
                        c2 = 5;
                        break;
                    }
                    break;
                case 235805286:
                    if (str.equals("property_orientation")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 1096920256:
                    if (str.equals("property_first_day_of_week")) {
                        c2 = 7;
                        break;
                    }
                    break;
                case 1106908695:
                    if (str.equals("property_choice_mode")) {
                        c2 = 6;
                        break;
                    }
                    break;
                case 2133471033:
                    if (str.equals("property_input_type")) {
                        c2 = 4;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
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
        View a2 = kp.a(getContext(), R.layout.property_popup_selector_single);
        this.i = (ViewGroup) a2.findViewById(R.id.rg_content);
        TextView textView = (TextView) a2.findViewById(R.id.desc);
        int i2 = 0;
        if (this.f231a == "property_ime_option") {
            textView.setText(kq.a().a(getContext(), R.string.property_description_edittext_ime_options));
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        for (Pair a3 : fa.a(this.f231a)) {
            this.i.addView(a((Pair<Integer, String>) a3));
        }
        int childCount = this.i.getChildCount();
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            RadioButton radioButton = (RadioButton) this.i.getChildAt(i2);
            if (Integer.valueOf(radioButton.getTag().toString()).intValue() == this.b) {
                radioButton.setChecked(true);
                break;
            }
            i2++;
        }
        kdVar.a(a2);
        kdVar.a(kq.a().a(getContext(), R.string.common_word_select), new View.OnClickListener() {
            public void onClick(View view) {
                int childCount = gs.this.i.getChildCount();
                int i = 0;
                while (true) {
                    if (i >= childCount) {
                        break;
                    }
                    RadioButton radioButton = (RadioButton) gs.this.i.getChildAt(i);
                    if (radioButton.isChecked()) {
                        gs.this.setValue(Integer.valueOf(radioButton.getTag().toString()).intValue());
                        break;
                    }
                    i++;
                }
                if (gs.this.j != null) {
                    gs.this.j.a(gs.this.f231a, Integer.valueOf(gs.this.b));
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

    private RadioButton a(Pair<Integer, String> pair) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText((CharSequence) pair.second);
        radioButton.setTag(pair.first);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int) (kp.a(getContext(), 1.0f) * 40.0f));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }
}
