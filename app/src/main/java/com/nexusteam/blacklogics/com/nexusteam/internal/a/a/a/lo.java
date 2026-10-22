package com.nexusteam.internal;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Spanned;
import com.nexusteam.blacklogics.R;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class lo extends le {

    Pattern f356a;
    private String[] g;
    private ArrayList<String> h;
    private String i;

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return null;
    }

    public lo(Context context, TextInputLayout textInputLayout, String[] strArr, ArrayList<String> arrayList) {
        super(context, textInputLayout);
        this.f356a = Pattern.compile("^[a-z][a-z0-9_]*");
        this.g = strArr;
        this.h = arrayList;
    }

    public lo(Context context, TextInputLayout textInputLayout, String[] strArr, ArrayList<String> arrayList, String str) {
        super(context, textInputLayout);
        this.f356a = Pattern.compile("^[a-z][a-z0-9_]*");
        this.g = strArr;
        this.h = arrayList;
        this.i = str;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        boolean z;
        String strTrim = charSequence.toString().trim();
        if (strTrim.length() < 3) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_min_lenth, 3));
            this.e = false;
            return;
        }
        if (strTrim.length() > 70) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_max_lenth, 70));
            this.e = false;
            return;
        }
        if (strTrim.equals("default_image") || "NONE".toLowerCase().equals(strTrim.toLowerCase())) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.common_message_name_unavailable));
            this.e = false;
            return;
        }
        if (!strTrim.equals(this.i) && this.h.indexOf(strTrim) >= 0) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.common_message_name_unavailable));
            this.e = false;
            return;
        }
        String[] strArr = this.g;
        int length = strArr.length;
        int i4 = 0;
        while (true) {
            if (i4 >= length) {
                z = false;
                break;
            } else {
                if (charSequence.toString().equals(strArr[i4])) {
                    z = true;
                    break;
                }
                i4++;
            }
        }
        if (z) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.logic_editor_message_reserved_keywords));
            this.e = false;
        } else if (!Character.isLetter(charSequence.charAt(0))) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.logic_editor_message_variable_name_must_start_letter));
            this.e = false;
        } else if (this.f356a.matcher(charSequence.toString()).matches()) {
            this.c.setErrorEnabled(false);
            this.e = true;
        } else {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_rule_4));
            this.e = false;
        }
    }
}
