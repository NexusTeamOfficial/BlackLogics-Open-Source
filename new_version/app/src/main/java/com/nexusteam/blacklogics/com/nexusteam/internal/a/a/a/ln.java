package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Spanned;
import java.util.regex.Pattern;

public class ln extends le {

    /* renamed from: a  reason: collision with root package name */
    Pattern f355a = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*");

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return null;
    }

    public ln(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence.toString().trim().length() <= 0) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_min_lenth, 1));
            this.e = false;
        } else if (charSequence.toString().trim().length() > 20) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_max_lenth, 20));
            this.e = false;
        } else if (!Character.isLetter(charSequence.charAt(0))) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.logic_editor_message_variable_name_must_start_letter));
            this.e = false;
        } else if (this.f355a.matcher(charSequence.toString()).matches()) {
            this.c.setErrorEnabled(false);
            this.e = true;
        } else {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_rule_3));
            this.e = false;
        }
    }
}
