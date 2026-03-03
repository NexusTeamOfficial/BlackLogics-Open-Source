package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Color;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Spanned;
import android.view.View;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.nexusteam.blacklogics.R;
import java.util.regex.Pattern;

public class lp extends le {

    /* renamed from: a  reason: collision with root package name */
    Pattern f357a = Pattern.compile("[A-Fa-f0-9]*");
    View g;

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return null;
    }

    public lp(Context context, TextInputLayout textInputLayout, View view) {
        super(context, textInputLayout);
        this.g = view;
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String trim = charSequence.toString().trim();
        if (trim.length() > 8) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_max_lenth, 8));
            this.e = false;
        } else if (this.f357a.matcher(trim).matches()) {
            try {
                this.g.setBackgroundColor(Color.parseColor(String.format("#%8s", new Object[]{trim}).replaceAll(" ", "F")));
            } catch (Exception unused) {
                this.c.setErrorEnabled(true);
                this.c.setError(kq.a().a(this.b, R.string.invalid_value_format));
                this.e = false;
                this.g.setBackgroundColor(-592138);
            }
            this.c.setErrorEnabled(false);
            this.e = true;
        } else {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_format));
            this.g.setBackgroundColor(-592138);
            this.e = false;
        }
    }
}
