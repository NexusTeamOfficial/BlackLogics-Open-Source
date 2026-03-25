package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.app.Activity;
import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.view.InputDeviceCompat;
import androidx.appcompat.widget.ActivityChooserView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nexusteam.internal.beans.ProjectFileBean;

public class gp extends RelativeLayout implements View.OnClickListener {
    
    /* renamed from: a  reason: collision with root package name */
    private Context f210a;
    /* access modifiers changed from: private */
    public String b = "";
    /* access modifiers changed from: private */
    public String c = "";
    private ImageView d;
    private int e;
    private TextView f;
    private TextView g;
    private View h;
    private View i;
    private String j;
    private ProjectFileBean k;
    /* access modifiers changed from: private */
    public gi l;
    
    public gp(Context context, boolean z) {
        super(context);
        a(context, z);
    }
    
    private void a(Context context, boolean z) {
        this.f210a = context;
        kp.a(context, this, R.layout.property_input_item);
        this.f = (TextView) findViewById(R.id.tv_name);
        this.g = (TextView) findViewById(R.id.tv_value);
        this.d = (ImageView) findViewById(R.id.img_left_icon);
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
    
    public void a(String str, ProjectFileBean projectFileBean) {
        this.j = str;
        this.k = projectFileBean;
    }
    
    public void setKey(String str) {
        this.b = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.f.setText(kq.a().a(getResources(), identifier));
            if (this.i.getVisibility() == 0) {
                setIcon((ImageView) findViewById(R.id.img_icon));
                ((TextView) findViewById(R.id.tv_title)).setText(kq.a().a(getContext(), identifier));
                return;
            }
            setIcon(this.d);
        }
    }
    
    private void setIcon(ImageView imageView) {
        String key = this.b;
        int iconResId = 0;
        
        if (key == null) {

            imageView.setImageResource(R.drawable.abc_96);
            return;
        }
        
        switch (key) {
            case "property_id":
            iconResId = R.drawable.rename_96_blue;
            break;
            case "property_text":
            iconResId = R.drawable.abc_96;
            break;
            case "property_hint":
            iconResId = R.drawable.help_96_blue;
            break;
            case "property_weight":
            case "property_weight_sum":
            iconResId = R.drawable.one_to_many_48;
            break;
            case "property_rotate":
            iconResId = R.drawable.ic_reset_color_32dp;
            break;
            case "property_lines":
            case "property_max":
            case "property_progress":
            iconResId = R.drawable.numbers_48;
            break;
            case "property_alpha":
            iconResId = R.drawable.opacity_48;
            break;
            case "property_translation_x":
            iconResId = R.drawable.swipe_right_48;
            break;
            case "property_translation_y":
            iconResId = R.drawable.swipe_down_48;
            break;
            case "property_scale_x":
            case "property_scale_y":
            iconResId = R.drawable.resize_48;
            break;
            

            case "property_background_color":
            iconResId = R.drawable.color_palette_48;
            break;
            case "property_background_resource":
            iconResId = R.drawable.full_image_48;
            break;
            case "property_text_color":
            iconResId = R.drawable.color_palette_48;
            break;
            case "property_image":
            iconResId = R.drawable.full_image_48;
            break;
            
            default:

            iconResId = R.drawable.abc_96;
            break;
        }
        
        if (iconResId == 0) {
            iconResId = R.drawable.abc_96;
        }
        
        this.e = iconResId;
        imageView.setImageResource(iconResId);
    }
    
    
    public String getKey() {
        return this.b;
    }
    
    public void setValue(String str) {
        this.c = str;
        this.g.setText(str);
    }
    
    public String getValue() {
        return this.c;
    }
    
    public void setOnPropertyValueChangeListener(gi giVar) {
        this.l = giVar;
    }
    
    public void onClick(View view) {
        if (!ki.a()) {
            String str = this.b;
            char c2 = 65535;
            switch (str.hashCode()) {
                case -1553367436:
                if (str.equals("property_alpha")) {
                    c2 = 9;
                    break;
                }
                break;
                case -1543300075:
                if (str.equals("property_lines")) {
                    c2 = 6;
                    break;
                }
                break;
                case -1019734351:
                if (str.equals("property_hint")) {
                    c2 = 2;
                    break;
                }
                break;
                case -1019380393:
                if (str.equals("property_text")) {
                    c2 = 1;
                    break;
                }
                break;
                case -1018178217:
                if (str.equals("property_progress")) {
                    c2 = 8;
                    break;
                }
                break;
                case -864174086:
                if (str.equals("property_max")) {
                    c2 = 7;
                    break;
                }
                break;
                case -710204242:
                if (str.equals("property_weight_sum")) {
                    c2 = 4;
                    break;
                }
                break;
                case -420171003:
                if (str.equals("property_rotate")) {
                    c2 = 5;
                    break;
                }
                break;
                case -286582750:
                if (str.equals("property_weight")) {
                    c2 = 3;
                    break;
                }
                break;
                case 20737408:
                if (str.equals("property_translation_x")) {
                    c2 = 10;
                    break;
                }
                break;
                case 20737409:
                if (str.equals("property_translation_y")) {
                    c2 = 11;
                    break;
                }
                break;
                case 386320985:
                if (str.equals("property_scale_x")) {
                    c2 = 12;
                    break;
                }
                break;
                case 386320986:
                if (str.equals("property_scale_y")) {
                    c2 = 13;
                    break;
                }
                break;
                case 1357596613:
                if (str.equals("property_id")) {
                    c2 = 0;
                    break;
                }
                break;
            }
            switch (c2) {
                case 0:
                a();
                return;
                case 1:
                case 2:
                a(0, 255);
                return;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                b();
                return;
                case 9:
                b(0, 1);
                return;
                case 10:
                case 11:
                b(-9999, 9999);
                return;
                case 12:
                case 13:
                b(0, 99);
                return;
                default:
                return;
            }
        }
    }
    
    private void a() {
        final kd kdVar = new kd((Activity) getContext());
        kdVar.a(this.f.getText().toString());
        kdVar.a(this.e);
        View a2 = kp.a(getContext(), R.layout.property_popup_input_text);
        final EditText editText = (EditText) a2.findViewById(R.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(524289);
        editText.setImeOptions(6);
        final ls lsVar = new ls(this.f210a, (TextInputLayout) a2.findViewById(R.id.ti_input), fc.b, fc.c(), ma.a(this.j).b(this.k), this.c);
        lsVar.a(this.c);
        kdVar.a(a2);
        kdVar.a(kq.a().a(getContext(), R.string.common_word_save), new View.OnClickListener() {
            public void onClick(View view) {
                if (lsVar.a()) {
                    gp.this.setValue(editText.getText().toString());
                    if (gp.this.l != null) {
                        gp.this.l.a(gp.this.b, gp.this.c);
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
    
    private void a(int i2, int i3) {
        final kd kdVar = new kd((Activity) getContext());
        kdVar.a(this.f.getText().toString());
        kdVar.a(this.e);
        View a2 = kp.a(getContext(), R.layout.property_popup_input_text);
        final EditText editText = (EditText) a2.findViewById(R.id.ed_input);
        final lk lkVar = new lk(this.f210a, (TextInputLayout) a2.findViewById(R.id.ti_input), i2, i3);
        lkVar.a(this.c);
        kdVar.a(a2);
        kdVar.a(kq.a().a(getContext(), R.string.common_word_save), new View.OnClickListener() {
            public void onClick(View view) {
                if (lkVar.a()) {
                    gp.this.setValue(editText.getText().toString());
                    if (gp.this.l != null) {
                        gp.this.l.a(gp.this.b, gp.this.c);
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
    
    private void b() {
        final kd kdVar = new kd((Activity) getContext());
        kdVar.a(this.f.getText().toString());
        kdVar.a(this.e);
        View a2 = kp.a(getContext(), R.layout.property_popup_input_text);
        final EditText editText = (EditText) a2.findViewById(R.id.ed_input);
        editText.setInputType(InputDeviceCompat.SOURCE_TOUCHSCREEN);
        editText.setText(this.c);
        final ll llVar = new ll(
        this.f210a,
        (TextInputLayout) a2.findViewById(R.id.ti_input),
        0,
        ("property_max".equals(this.b) || "property_progress".equals(this.b))
        ? Integer.MAX_VALUE
        : 999
        );
        
        kdVar.a(a2);
        kdVar.a(kq.a().a(getContext(), R.string.common_word_save), new View.OnClickListener() {
            public void onClick(View view) {
                if (llVar.a()) {
                    gp.this.setValue(editText.getText().toString());
                    if (gp.this.l != null) {
                        gp.this.l.a(gp.this.b, gp.this.c);
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
    
    private void b(int i2, int i3) {
        final kd kdVar = new kd((Activity) getContext());
        kdVar.a(this.f.getText().toString());
        kdVar.a(this.e);
        View a2 = kp.a(getContext(), R.layout.property_popup_input_text);
        final EditText editText = (EditText) a2.findViewById(R.id.ed_input);
        editText.setInputType(i2 < 0 ? 12290 : 8194);
        editText.setText(this.c);
        final lg lgVar = new lg(this.f210a, (TextInputLayout) a2.findViewById(R.id.ti_input), i2, i3);
        kdVar.a(a2);
        kdVar.a(kq.a().a(getContext(), R.string.common_word_save), new View.OnClickListener() {
            public void onClick(View view) {
                if (lgVar.a()) {
                    gp.this.setValue(editText.getText().toString());
                    if (gp.this.l != null) {
                        gp.this.l.a(gp.this.b, gp.this.c);
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
