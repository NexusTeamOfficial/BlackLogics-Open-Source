package com.nexusteam.internal.lib.ui;

import com.nexusteam.internal.kp;
import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.nexusteam.blacklogics.R;

public class EasyDeleteEditText extends RelativeLayout {

    private Context f1598a;
    private ImageView b;
    private EditText c;
    private TextInputLayout d;

    public EasyDeleteEditText(Context context) {
        super(context);
        a(context);
    }

    public EasyDeleteEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public EasyDeleteEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        this.f1598a = context;
        kp.a(context, this, R.layout.easy_delete_edittext);
        this.b = (ImageView) findViewById(R.id.img_delete);
        this.c = (EditText) findViewById(R.id.easy_ed_input);
        this.d = (TextInputLayout) findViewById(R.id.easy_ti_input);
        this.b.setVisibility(8);
        this.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyDeleteEditText.this.c.setText("");
            }
        });
        this.c.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                EasyDeleteEditText.this.a();
            }
        });
        this.c.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean z) {
                EasyDeleteEditText.this.a();
            }
        });
        a();
    }

    public void a() {
        if (this.c.isEnabled() && this.c.hasFocus() && this.c.length() > 0) {
            this.b.setVisibility(0);
        } else {
            this.b.setVisibility(8);
        }
    }

    public EditText getEditText() {
        return this.c;
    }

    public TextInputLayout getTextInputLayout() {
        return this.d;
    }

    public void setHint(String str) {
        this.d.setHint(str);
    }
}
