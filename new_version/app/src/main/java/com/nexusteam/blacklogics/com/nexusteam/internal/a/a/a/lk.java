package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import com.nexusteam.blacklogics.R;
import android.text.InputFilter;
import android.text.Spanned;

public class lk extends le {

    /* renamed from: a  reason: collision with root package name */
    private int f352a;
    private int g;

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return null;
    }

    public lk(Context context, TextInputLayout textInputLayout, int i, int i2) {
        super(context, textInputLayout);
        this.f352a = i;
        this.g = i2;
        this.d = textInputLayout.getEditText();
        this.d.setFilters(new InputFilter[]{this});
        this.d.addTextChangedListener(this);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence.toString().trim().length() < this.f352a) {
            this.c.setErrorEnabled(true);
            if (this.f == 0) {
                this.c.setError(kq.a().a(this.b, R.string.invalid_value_min_lenth, Integer.valueOf(this.f352a)));
            } else {
                this.c.setError(kq.a().a(this.b, this.f, Integer.valueOf(this.f352a)));
            }
            this.e = false;
        } else if (charSequence.toString().trim().length() > this.g) {
            this.c.setErrorEnabled(true);
            if (this.f == 0) {
                this.c.setError(kq.a().a(this.b, R.string.invalid_value_max_lenth, Integer.valueOf(this.g)));
            } else {
                this.c.setError(kq.a().a(this.b, this.f, Integer.valueOf(this.g)));
            }
            this.e = false;
        } else {
            this.c.setErrorEnabled(false);
            this.e = true;
        }
    }
}
