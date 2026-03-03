package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Spanned;
import java.util.regex.Pattern;

public class ld extends le {

    /* renamed from: a  reason: collision with root package name */
    Pattern f346a = Pattern.compile(".*[&\"'<>].*");

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return null;
    }

    public ld(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence.toString().trim().length() <= 0) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_min_lenth, 1));
            this.e = false;
        } else if (charSequence.toString().trim().length() > 50) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_max_lenth, 50));
            this.e = false;
        } else if (this.f346a.matcher(charSequence.toString()).matches()) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_rule_5));
            this.e = false;
        } else {
            this.c.setErrorEnabled(false);
            this.e = true;
        }
    }
}
