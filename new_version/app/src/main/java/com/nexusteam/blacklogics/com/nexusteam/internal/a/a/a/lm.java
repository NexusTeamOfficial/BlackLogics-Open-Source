package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Spanned;
import java.util.regex.Pattern;

public class lm extends le {

    /* renamed from: a  reason: collision with root package name */
    Pattern f354a = Pattern.compile("([a-zA-Z][a-zA-Z\\d]*\\.)*[a-zA-Z][a-zA-Z\\d]*");

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return null;
    }

    public lm(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence.toString().trim().length() > 50) {
            this.c.setErrorEnabled(true);
            if (this.f == 0) {
                this.c.setError(kq.a().a(this.b, R.string.invalid_value_max_lenth, 50));
            } else {
                this.c.setError(kq.a().a(this.b, this.f, 50));
            }
            this.e = false;
            return;
        }
        this.c.setErrorEnabled(false);
        this.e = true;
        if (!this.f354a.matcher(charSequence.toString()).matches()) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_rule_2));
            this.e = false;
        } else if (charSequence.toString().indexOf(".") < 0) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.myprojects_settings_message_contain_dot));
            this.e = false;
            return;
        } else {
            this.c.setErrorEnabled(false);
            this.e = true;
        }
        boolean z = false;
        for (String str : charSequence.toString().split("\\.")) {
            String[] strArr = fc.b;
            int length = strArr.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    break;
                } else if (strArr[i4].equals(str)) {
                    z = true;
                    break;
                } else {
                    i4++;
                }
            }
        }
        if (z) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.logic_editor_message_reserved_keywords));
            this.e = false;
        }
    }
}
