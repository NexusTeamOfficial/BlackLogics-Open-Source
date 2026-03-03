package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.app.Activity;
import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class gq extends RelativeLayout implements View.OnClickListener {
    /* access modifiers changed from: private */

    /* renamed from: a  reason: collision with root package name */
    public String f219a = "";
    /* access modifiers changed from: private */
    public int b = -1;
    private TextView c;
    private TextView d;
    private ImageView e;
    private View f;
    private View g;
    /* access modifiers changed from: private */
    public gi h;
    private boolean i = true;
    private boolean j = true;
    private boolean k = true;
    private int l;

    public gq(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    public void setItemEnabled(int i2) {
        if ((i2 & 1) == 1) {
            this.i = true;
        } else {
            this.i = false;
        }
        if ((i2 & 2) == 2) {
            this.j = true;
        } else {
            this.j = false;
        }
        if ((i2 & 4) == 4) {
            this.k = true;
        } else {
            this.k = false;
        }
    }

    private void a(Context context, boolean z) {
        kp.a(context, this, R.layout.property_selector_item);
        this.c = (TextView) findViewById(R.id.tv_name);
        this.d = (TextView) findViewById(R.id.tv_value);
        this.e = (ImageView) findViewById(R.id.img_left_icon);
        this.f = findViewById(R.id.property_item);
        this.g = findViewById(R.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public void setOrientationItem(int i2) {
        if (i2 == 0) {
            this.f.setVisibility(8);
            this.g.setVisibility(0);
            return;
        }
        this.f.setVisibility(0);
        this.g.setVisibility(8);
    }

    public void setKey(String str) {
        this.f219a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.c.setText(kq.a().a(getResources(), identifier));
            if (this.g.getVisibility() == 0) {
                setIcon((ImageView) findViewById(R.id.img_icon));
                ((TextView) findViewById(R.id.tv_title)).setText(kq.a().a(getContext(), identifier));
                return;
            }
            setIcon(this.e);
        }
    }

    private void setIcon(ImageView imageView) {
        if ("property_layout_width".equals(this.f219a)) {
            this.l = R.drawable.width_96;
        } else if ("property_layout_height".equals(this.f219a)) {
            this.l = R.drawable.height_96;
        }
        imageView.setImageResource(this.l);
    }

    public String getKey() {
        return this.f219a;
    }

    public void setValue(int i2) {
        this.b = i2;
        if (!this.j && i2 == -2) {
            this.d.setText(fa.a(this.f219a, -1));
        } else if (this.k || i2 < 0) {
            this.d.setText(fa.a(this.f219a, i2));
        } else {
            this.d.setText(fa.a(this.f219a, -2));
        }
    }

    public int getValue() {
        return this.b;
    }

    public void setOnPropertyValueChangeListener(gi giVar) {
        this.h = giVar;
    }

    public void onClick(View view) {
        if (!ki.a()) {
            a();
        }
    }

    private void a() {
        final kd kdVar = new kd((Activity) getContext());
        kdVar.a(this.c.getText().toString());
        kdVar.a(this.l);
        View a2 = kp.a(getContext(), R.layout.property_popup_measurement);
        final EditText editText = (EditText) a2.findViewById(R.id.ed_input);
        final RadioGroup radioGroup = (RadioGroup) a2.findViewById(R.id.rg_width_height);
        final ll llVar = new ll(getContext(), (TextInputLayout) a2.findViewById(R.id.ti_input), 0, 999);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_directinput) {
                    editText.setEnabled(true);
                    llVar.a(editText.getText().toString());
                    return;
                }
                editText.setEnabled(false);
            }
        });
        editText.setEnabled(false);
        radioGroup.clearCheck();
        if (this.b >= 0) {
            if (this.k) {
                radioGroup.check(R.id.rb_directinput);
                editText.setEnabled(true);
                llVar.a(String.valueOf(this.b));
            } else {
                radioGroup.check(R.id.rb_wrapcontent);
            }
        } else if (this.b == -1) {
            radioGroup.check(R.id.rb_matchparent);
        } else if (this.j) {
            radioGroup.check(R.id.rb_wrapcontent);
        } else {
            radioGroup.check(R.id.rb_matchparent);
        }
        final RadioButton radioButton = (RadioButton) a2.findViewById(R.id.rb_matchparent);
        a2.findViewById(R.id.tv_matchparent).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                radioButton.setChecked(true);
            }
        });
        final RadioButton radioButton2 = (RadioButton) a2.findViewById(R.id.rb_wrapcontent);
        View findViewById = a2.findViewById(R.id.tv_wrapcontent);
        if (this.j) {
            radioButton2.setEnabled(true);
            findViewById.setClickable(true);
            ((TextView) findViewById).setTextColor(-9079435);
            findViewById.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    radioButton2.setChecked(true);
                }
            });
        } else {
            radioButton2.setEnabled(false);
            findViewById.setClickable(false);
            ((TextView) findViewById).setTextColor(-2236963);
        }
        final RadioButton radioButton3 = (RadioButton) a2.findViewById(R.id.rb_directinput);
        View findViewById2 = a2.findViewById(R.id.direct_input);
        if (this.k) {
            radioButton3.setEnabled(true);
            findViewById2.setClickable(true);
            ((TextView) a2.findViewById(R.id.tv_input_dp)).setTextColor(-9079435);
            findViewById2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    radioButton3.setChecked(true);
                }
            });
        } else {
            radioButton3.setEnabled(false);
            findViewById2.setClickable(false);
            ((TextView) a2.findViewById(R.id.tv_input_dp)).setTextColor(-2236963);
        }
        kdVar.a(a2);
        final kd kdVar2 = kdVar;
        kdVar.a(kq.a().a(getContext(), R.string.common_word_select), new View.OnClickListener() {
            public void onClick(View view) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_matchparent) {
                    gq.this.setValue(-1);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_wrapcontent) {
                    gq.this.setValue(-2);
                } else if (llVar.a()) {
                    gq.this.setValue(Integer.valueOf(editText.getText().toString()).intValue());
                } else {
                    return;
                }
                if (gq.this.h != null) {
                    gq.this.h.a(gq.this.f219a, Integer.valueOf(gq.this.b));
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
