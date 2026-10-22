package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class le implements InputFilter, TextWatcher {
    protected Context b;
    protected TextInputLayout c;
    protected EditText d;
    protected boolean e;
    protected int f;

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public le(Context context, TextInputLayout textInputLayout) {
        this.b = context;
        this.c = textInputLayout;
        this.d = textInputLayout.getEditText();
        this.d.setFilters(new InputFilter[]{this});
        this.d.addTextChangedListener(this);
    }

    public boolean a() {
        if (!this.e) {
            this.d.requestFocus();
        }
        return this.e;
    }

    public void a(String str) {
        this.e = true;
        this.d.setText(str);
    }

    public String b() {
        return this.d.getText().toString();
    }

    public void afterTextChanged(Editable editable) {
        if (editable.toString().isEmpty()) {
            this.c.setErrorEnabled(false);
        }
    }
}
