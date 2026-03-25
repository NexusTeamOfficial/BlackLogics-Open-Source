package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.app.Activity;
import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexusteam.blacklogics.R;

public class go extends RelativeLayout implements View.OnClickListener {
    
    /* renamed from: a  reason: collision with root package name */
    Context f205a;
    public int b;
    public int c;
    public int d;
    public int e;
    /* access modifiers changed from: private */
    public String f = "";
    private String g = "";
    private View h;
    private View i;
    private ImageView j;
    private int k;
    private TextView l;
    private TextView m;
    /* access modifiers changed from: private */
    public gi n;
    
    public go(Context context, boolean z) {
        super(context);
        a(context, z);
    }
    
    private void a(Context context, boolean z) {
        this.f205a = context;
        kp.a(context, this, R.layout.property_input_item);
        this.l = (TextView) findViewById(R.id.tv_name);
        this.m = (TextView) findViewById(R.id.tv_value);
        this.j = (ImageView) findViewById(R.id.img_left_icon);
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
        this.f = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.l.setText(kq.a().a(getResources(), identifier));
            String str2 = this.f;
            char c2 = 65535;
            int hashCode = str2.hashCode();
            if (hashCode != -576300200) {
                if (hashCode == 1964055463 && str2.equals("property_padding")) {
                    c2 = 1;
                }
            } else if (str2.equals("property_margin")) {
                c2 = 0;
            }
            switch (c2) {
                case 0:
                this.k = R.drawable.insert_white_space_48;
                break;
                case 1:
                this.k = R.drawable.collect_48;
                break;
            }
            if (this.i.getVisibility() == 0) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(this.k);
                ((TextView) findViewById(R.id.tv_title)).setText(kq.a().a(getContext(), identifier));
                return;
            }
            this.j.setImageResource(this.k);
        }
    }
    
    public String getKey() {
        return this.f;
    }
    
    public void a(int i2, int i3, int i4, int i5) {
        this.b = i2;
        this.c = i3;
        this.d = i4;
        this.e = i5;
        TextView textView = this.m;
        textView.setText("left: " + this.b + ", top: " + this.c + ", right: " + this.d + ", bottom: " + this.e);
    }
    
    public String getValue() {
        return this.g;
    }
    
    public void setOnPropertyValueChangeListener(gi giVar) {
        this.n = giVar;
    }
    
    public void onClick(View view) {
        if (!ki.a()) {
            String str = this.f;
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -576300200) {
                if (hashCode == 1964055463 && str.equals("property_padding")) {
                    c2 = 1;
                }
            } else if (str.equals("property_margin")) {
                c2 = 0;
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
        kd kdVar = new kd((Activity) getContext());
        kdVar.a(this.l.getText().toString());
        kdVar.a(this.k);
        View a2 = kp.a(getContext(), R.layout.property_popup_input_indent);
        CheckBox checkBox = (CheckBox) a2.findViewById(R.id.chk_pty_all);
        checkBox.setText(kq.a().a(getContext(), R.string.common_word_all));
        EditText editText = (EditText) a2.findViewById(R.id.et_all);
        EditText editText2 = (EditText) a2.findViewById(R.id.et_left);
        EditText editText3 = (EditText) a2.findViewById(R.id.et_top);
        EditText editText4 = (EditText) a2.findViewById(R.id.et_right);
        final EditText editText5 = (EditText) a2.findViewById(R.id.et_bottom);
        ll llVar = new ll(this.f205a, (TextInputLayout) a2.findViewById(R.id.ti_all), 0, 999);
        ll llVar2 = new ll(this.f205a, (TextInputLayout) a2.findViewById(R.id.ti_left), 0, 999);
        ll llVar3 = new ll(this.f205a, (TextInputLayout) a2.findViewById(R.id.ti_right), 0, 999);
        kd kdVar2 = kdVar;
        ll llVar4 = new ll(this.f205a, (TextInputLayout) a2.findViewById(R.id.ti_top), 0, 999);
        View view = a2;
        ll llVar5 = new ll(this.f205a, (TextInputLayout) a2.findViewById(R.id.ti_bottom), 0, 999);
        llVar2.a(String.valueOf(this.b));
        llVar4.a(String.valueOf(this.c));
        llVar3.a(String.valueOf(this.d));
        llVar5.a(String.valueOf(this.e));
        if (this.b == this.c && this.c == this.d && this.d == this.e) {
            llVar.a(String.valueOf(this.b));
            checkBox.setChecked(true);
            editText.setEnabled(true);
            editText2.clearFocus();
            editText3.clearFocus();
            editText4.clearFocus();
            editText5.clearFocus();
            editText2.setEnabled(false);
            editText3.setEnabled(false);
            editText4.setEnabled(false);
            editText5.setEnabled(false);
        } else {
            editText.clearFocus();
            editText.setEnabled(false);
            editText2.setEnabled(true);
            editText3.setEnabled(true);
            editText4.setEnabled(true);
            editText5.setEnabled(true);
        }
        ll llVar6 = llVar4;
        ll llVar7 = llVar5;
        final CheckBox checkBox2 = checkBox;
        final EditText editText6 = editText;
        ll llVar8 = llVar3;
        final EditText editText7 = editText2;
        ll llVar9 = llVar2;
        final EditText editText8 = editText3;
        ll llVar10 = llVar;
        final EditText editText9 = editText4;
        EditText editText10 = editText5;
        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (checkBox2.isChecked()) {
                    editText6.setEnabled(true);
                    editText7.clearFocus();
                    editText8.clearFocus();
                    editText9.clearFocus();
                    editText5.clearFocus();
                    editText7.setEnabled(false);
                    editText8.setEnabled(false);
                    editText9.setEnabled(false);
                    editText5.setEnabled(false);
                    return;
                }
                editText6.clearFocus();
                editText6.setEnabled(false);
                editText7.setEnabled(true);
                editText8.setEnabled(true);
                editText9.setEnabled(true);
                editText5.setEnabled(true);
            }
        });
        final ll llVar11 = llVar9;
        final ll llVar12 = llVar6;
        final ll llVar13 = llVar8;
        final ll llVar14 = llVar7;
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            
            public void afterTextChanged(Editable editable) {
                llVar11.a(editText6.getText().toString());
                llVar12.a(editText6.getText().toString());
                llVar13.a(editText6.getText().toString());
                llVar14.a(editText6.getText().toString());
            }
        });
        kd kdVar3 = kdVar2;
        kdVar3.a(view);
        final CheckBox checkBox3 = checkBox;
        final ll llVar15 = llVar10;
        final ll llVar16 = llVar9;
        final ll llVar17 = llVar6;
        final ll llVar18 = llVar7;
        String saveText = kq.a().a(getContext(), R.string.common_word_save);
        final EditText editText11 = editText2;
        final EditText editText12 = editText3;
        final EditText editText13 = editText4;
        final EditText editText14 = editText10;
        final kd kdVar4 = kdVar3;
        
        View.OnClickListener saveClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox3.isChecked()) {
                    if (llVar15.a() && llVar16.a() && llVar13.a() && llVar17.a() && llVar18.a()) {
                        int left = Integer.parseInt(editText11.getText().toString());
                        int top = Integer.parseInt(editText12.getText().toString());
                        int right = Integer.parseInt(editText13.getText().toString());
                        int bottom = Integer.parseInt(editText14.getText().toString());
                        go.this.a(left, top, right, bottom);
                        if (go.this.n != null) {
                            go.this.n.a(go.this.f, new int[]{left, top, right, bottom});
                        }
                        kdVar4.dismiss();
                    }
                } else if (llVar16.a() && llVar13.a() && llVar17.a() && llVar18.a()) {
                    int left = Integer.parseInt(editText11.getText().toString());
                    int top = Integer.parseInt(editText12.getText().toString());
                    int right = Integer.parseInt(editText13.getText().toString());
                    int bottom = Integer.parseInt(editText14.getText().toString());
                    go.this.a(left, top, right, bottom);
                    if (go.this.n != null) {
                        go.this.n.a(go.this.f, new int[]{left, top, right, bottom});
                    }
                    kdVar4.dismiss();
                }
            }
        };
        
        final kd kdVar5 = kdVar4;
        kdVar5.a(saveText, saveClickListener);
        kdVar5.b(kq.a().a(this.f205a, R.string.common_word_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kdVar5.dismiss();
            }
        });
        kdVar5.show();
        
    }
}
