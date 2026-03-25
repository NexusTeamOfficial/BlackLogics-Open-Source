package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Spanned;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class lq extends le {

    /* renamed from: a  reason: collision with root package name */
    Pattern f358a = Pattern.compile("^[a-z][a-z0-9_]*");
    private String[] g;
    private ArrayList<String> h;
    private String i;

    public CharSequence filter(CharSequence charSequence, int i2, int i3, Spanned spanned, int i4, int i5) {
        return null;
    }

    public lq(Context context, TextInputLayout textInputLayout, String[] strArr, ArrayList<String> arrayList) {
        super(context, textInputLayout);
        this.g = strArr;
        this.h = arrayList;
    }

    public lq(Context context, TextInputLayout textInputLayout, String[] strArr, ArrayList<String> arrayList, String str) {
        super(context, textInputLayout);
        this.g = strArr;
        this.h = arrayList;
        this.i = str;
    }

    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        boolean z;
        if (charSequence.toString().trim().length() < 3) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_min_lenth, 3));
            this.e = false;
        } else if (charSequence.toString().trim().length() > 20) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_max_lenth, 20));
            this.e = false;
        } else if (this.i == null || this.i.length() <= 0 || !charSequence.toString().equals(this.i)) {
            String[] strArr = this.g;
            int length = strArr.length;
            int i5 = 0;
            while (true) {
                if (i5 >= length) {
                    z = false;
                    break;
                }
                if (charSequence.toString().equals(strArr[i5])) {
                    z = true;
                    break;
                }
                i5++;
            }
            if (z) {
                this.c.setErrorEnabled(true);
                this.c.setError(kq.a().a(this.b, R.string.logic_editor_message_reserved_keywords));
                this.e = false;
            } else if ("main".equals(charSequence.toString()) || this.h.indexOf(charSequence.toString()) >= 0) {
                this.c.setErrorEnabled(true);
                this.c.setError(kq.a().a(this.b, R.string.common_message_name_unavailable));
                this.e = false;
            } else if (!Character.isLetter(charSequence.charAt(0))) {
                this.c.setErrorEnabled(true);
                this.c.setError(kq.a().a(this.b, R.string.logic_editor_message_variable_name_must_start_letter));
                this.e = false;
            } else if (this.f358a.matcher(charSequence.toString()).matches()) {
                this.c.setErrorEnabled(false);
                this.e = true;
            } else {
                this.c.setErrorEnabled(true);
                this.c.setError(kq.a().a(this.b, R.string.invalid_value_rule_4));
                this.e = false;
            }
        } else {
            this.c.setErrorEnabled(false);
            this.e = true;
        }
    }
}
