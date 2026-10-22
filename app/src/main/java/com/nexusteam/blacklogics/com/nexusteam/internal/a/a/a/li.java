package com.nexusteam.internal;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Spanned;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.EventBean;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class li extends le {

    Pattern f350a;
    private String[] g;
    private ArrayList<String> h;
    private ArrayList<String> i;
    private String j;
    private int k;

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return null;
    }

    public li(Context context, TextInputLayout textInputLayout, String[] strArr, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        super(context, textInputLayout);
        this.f350a = Pattern.compile("^[a-z][a-z0-9_]*");
        this.g = strArr;
        this.h = arrayList;
        this.i = arrayList2;
        this.k = 1;
    }

    public void a(ArrayList<String> arrayList) {
        this.i = arrayList;
    }

    public void a(int i) {
        this.k = i;
        c(b());
    }

    public void b(String str) {
        this.j = str;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        c(charSequence.toString().trim());
    }

    private void c(String str) {
        boolean z;
        if (str.length() < 3) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_min_lenth, 3));
            this.e = false;
            return;
        }
        if (str.length() > 70) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_max_lenth, 70));
            this.e = false;
            return;
        }
        if (str.equals("default_image") || "NONE".toLowerCase().equals(str.toLowerCase())) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.common_message_name_unavailable));
            this.e = false;
            return;
        }
        if (this.k == 1) {
            if (!str.equals(this.j) && this.h.indexOf(str) >= 0) {
                this.c.setErrorEnabled(true);
                this.c.setError(kq.a().a(this.b, R.string.common_message_name_unavailable));
                this.e = false;
                return;
            } else if (!str.equals(this.j) && this.i.indexOf(str) >= 0) {
                this.c.setErrorEnabled(true);
                this.c.setError(kq.a().a(this.b, R.string.common_message_name_unavailable));
                this.e = false;
                return;
            }
        } else {
            ArrayList<String> arrayList = new ArrayList();
            for (int i = 1; i <= this.k; i++) {
                arrayList.add(str + EventBean.SEPARATOR + i);
            }
            ArrayList<String> arrayList2 = new ArrayList();
            for (String str2 : arrayList) {
                if (this.h.indexOf(str2) >= 0) {
                    arrayList2.add(str2);
                }
            }
            if (arrayList2.size() > 0) {
                this.c.setErrorEnabled(true);
                String strA = kq.a().a(this.b, R.string.common_message_name_unavailable);
                String str3 = "";
                for (String str4 : arrayList2) {
                    if (str3.length() > 0) {
                        str3 = str3 + ", ";
                    }
                    str3 = str3 + str4;
                }
                this.c.setError(strA + "\n[" + str3 + "]");
                this.e = false;
                return;
            }
        }
        String[] strArr = this.g;
        int length = strArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                z = false;
                break;
            } else {
                if (str.equals(strArr[i2])) {
                    z = true;
                    break;
                }
                i2++;
            }
        }
        if (z) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.logic_editor_message_reserved_keywords));
            this.e = false;
        } else if (!Character.isLetter(str.charAt(0))) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.logic_editor_message_variable_name_must_start_letter));
            this.e = false;
        } else if (this.f350a.matcher(str.toString()).matches()) {
            this.c.setErrorEnabled(false);
            this.e = true;
        } else {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_rule_4));
            this.e = false;
        }
    }
}
