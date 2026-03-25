package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;

public class lg extends le {

    /* renamed from: a  reason: collision with root package name */
    private int f348a;
    private int g;

    public void afterTextChanged(Editable editable) {
    }

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return null;
    }

    public lg(Context context, TextInputLayout textInputLayout, int i, int i2) {
        super(context, textInputLayout);
        this.f348a = i;
        this.g = i2;
        this.d = textInputLayout.getEditText();
        this.d.setFilters(new InputFilter[]{this});
        this.d.addTextChangedListener(this);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String charSequence2 = charSequence.toString();
        if (charSequence2.isEmpty()) {
            this.c.setErrorEnabled(true);
            this.c.setError(String.format("%d ~ %d", new Object[]{Integer.valueOf(this.f348a), Integer.valueOf(this.g)}));
            this.e = false;
            return;
        }
        try {
            float parseFloat = Float.parseFloat(charSequence2);
            if (parseFloat < ((float) this.f348a) || parseFloat > ((float) this.g)) {
                this.c.setErrorEnabled(true);
                this.c.setError(String.format("%d ~ %d", new Object[]{Integer.valueOf(this.f348a), Integer.valueOf(this.g)}));
                this.e = false;
                return;
            }
            this.c.setErrorEnabled(false);
            this.e = true;
        } catch (NumberFormatException unused) {
            this.c.setErrorEnabled(true);
            this.c.setError(String.format("%d ~ %d", new Object[]{Integer.valueOf(this.f348a), Integer.valueOf(this.g)}));
            this.e = false;
        }
    }
}
