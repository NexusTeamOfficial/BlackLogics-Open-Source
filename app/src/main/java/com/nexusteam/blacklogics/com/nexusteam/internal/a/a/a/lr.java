package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Spanned;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class lr extends le {

    /* renamed from: a  reason: collision with root package name */
    Pattern f359a = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*");
    private String[] g;
    private String[] h;
    private ArrayList<String> i;
    private String j;

    public CharSequence filter(CharSequence charSequence, int i2, int i3, Spanned spanned, int i4, int i5) {
        return null;
    }

    public lr(Context context, TextInputLayout textInputLayout, String[] strArr, String[] strArr2, ArrayList<String> arrayList) {
        super(context, textInputLayout);
        this.g = strArr;
        this.h = strArr2;
        this.i = arrayList;
    }

    public void a(String[] strArr) {
        this.h = strArr;
    }

    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        boolean z;
        boolean z2;
        if (charSequence.toString().trim().length() < 1) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_min_lenth, 1));
            this.e = false;
        } else if (charSequence.toString().trim().length() > 20) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_max_lenth, 20));
            this.e = false;
        } else if (this.j != null && this.j.length() > 0 && charSequence.toString().equals(this.j)) {
            this.c.setErrorEnabled(false);
            this.e = true;
        } else if (this.i.indexOf(charSequence.toString()) >= 0) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.common_message_name_unavailable));
            this.e = false;
        } else {
            String[] strArr = this.h;
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
                this.c.setError(kq.a().a(this.b, R.string.common_message_name_unavailable));
                this.e = false;
                return;
            }
            String[] strArr2 = this.g;
            int length2 = strArr2.length;
            int i6 = 0;
            while (true) {
                if (i6 >= length2) {
                    z2 = false;
                    break;
                }
                if (charSequence.toString().equals(strArr2[i6])) {
                    z2 = true;
                    break;
                }
                i6++;
            }
            if (z2) {
                this.c.setErrorEnabled(true);
                this.c.setError(kq.a().a(this.b, R.string.logic_editor_message_reserved_keywords));
                this.e = false;
            } else if (!Character.isLetter(charSequence.charAt(0))) {
                this.c.setErrorEnabled(true);
                this.c.setError(kq.a().a(this.b, R.string.logic_editor_message_variable_name_must_start_letter));
                this.e = false;
            } else {
                if (this.f359a.matcher(charSequence.toString()).matches()) {
                    this.c.setErrorEnabled(false);
                    this.e = true;
                } else {
                    this.c.setErrorEnabled(true);
                    this.c.setError(kq.a().a(this.b, R.string.invalid_value_rule_3));
                    this.e = false;
                }
                if (charSequence.toString().trim().length() < 1) {
                    this.c.setErrorEnabled(true);
                    this.c.setError(kq.a().a(this.b, R.string.invalid_value_min_lenth, 1));
                    this.e = false;
                }
            }
        }
    }
}
