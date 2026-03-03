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

public class gv extends RelativeLayout implements View.OnClickListener {
    /* access modifiers changed from: private */
    public String f240a = "";
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
    
    public gv(Context context, boolean z) {
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
        this.f240a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.c.setText(kq.a().a(getResources(), identifier));
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -1623888455) {
                if (hashCode != -10402863) {
                    if (hashCode == 1618119219 && str.equals("property_ad_size")) {
                        c2 = 0;
                    }
                } else if (str.equals("property_indeterminate")) {
                    c2 = 2;
                }
            } else if (str.equals("property_scale_type")) {
                c2 = 1;
            }
            switch (c2) {
                case 0:
                    this.f = R.drawable.widget_admob;
                    break;
                case 1:
                    this.f = R.drawable.enlarge_48;
                    break;
                case 2:
                    this.f = R.drawable.event_on_accuracy_changed_48dp;
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
        return this.f240a;
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
        kd dialog = new kd((Activity) getContext());
        dialog.a(this.c.getText().toString());
        dialog.a(this.f);
        
        View layout = kp.a(getContext(), R.layout.property_popup_selector_single);
        this.i = (ViewGroup) layout.findViewById(R.id.rg_content);
        
        String property = this.f240a;
        int type = -1;
        int hash = property.hashCode();
        
        if (hash == -1623888455 && property.equals("property_scale_type")) {
            type = 0;
        } else if (hash == 1618119219 && property.equals("property_ad_size")) {
            type = 1;
        } else if (hash == -10402863 && property.equals("property_indeterminate")) {
            type = 2;
        }
        
        String[] options;
        switch (type) {
            case 0:
                options = fa.j;
                break;
            case 1:
                options = fa.k;
                break;
            case 2:
                options = fa.l;
                break;
            default:
                options = new String[0];
                break;
        }
        
        for (String option : options) {
            RadioButton button = a(option);
            this.i.addView(button);
        }
        
        int count = this.i.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton btn = (RadioButton) this.i.getChildAt(i);
            String tag = btn.getTag().toString();
            if (tag.equals(this.b)) {
                btn.setChecked(true);
                break;
            }
        }
        
        dialog.a(layout);
        
        String selectText = kq.a().a(getContext(), R.string.common_word_select);
        dialog.a(selectText, new gv$1(this, dialog));
        
        String cancelText = kq.a().a(getContext(), R.string.common_word_cancel);
        dialog.b(cancelText, new gv$2(this, dialog));
        
        dialog.show();
    }
    
    class gv$1 implements View.OnClickListener {
        final /* synthetic */ kd a;
        final /* synthetic */ gv b;
        
        gv$1(gv gvVar, kd dialog) {
            this.b = gvVar;
            this.a = dialog;
        }
        
        @Override
        public void onClick(View v) {
            ViewGroup group = gv.a(this.b);
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                RadioButton button = (RadioButton) group.getChildAt(i);
                if (button.isChecked()) {
                    this.b.setValue(button.getTag().toString());
                    break;
                }
            }
            if (gv.b(this.b) != null) {
                gv.b(this.b).a(gv.c(this.b), gv.d(this.b));
            }
            this.a.dismiss();
        }
    }
    
    class gv$2 implements View.OnClickListener {
        final /* synthetic */ kd a;
        final /* synthetic */ gv b;
        
        gv$2(gv gvVar, kd dialog) {
            this.b = gvVar;
            this.a = dialog;
        }
        
        @Override
        public void onClick(View v) {
            this.a.dismiss();
        }
    }
    
    private RadioButton a(String str) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setTextSize(2, 12.0f);
        radioButton.setText(str);
        radioButton.setTag(str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = (int) kp.a(getContext(), 4.0f);
        layoutParams.bottomMargin = (int) kp.a(getContext(), 4.0f);
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }
    
    static ViewGroup a(gv gvVar) {
        return gvVar.i;
    }
    
    static gi b(gv gvVar) {
        return gvVar.j;
    }
    
    static String c(gv gvVar) {
        return gvVar.f240a;
    }
    
    static String d(gv gvVar) {
        return gvVar.b;
    }
}
