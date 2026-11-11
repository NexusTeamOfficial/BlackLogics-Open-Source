package com.nexusteam.internal;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Spanned;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.EventBean;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class lh extends le {

    Pattern f349a;
    private String[] g;
    private ArrayList<String> h;
    private String i;
    private int j;

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return null;
    }

    public lh(Context context, TextInputLayout textInputLayout, String[] strArr, ArrayList<String> arrayList) {
        super(context, textInputLayout);
        this.f349a = Pattern.compile("^[a-z][a-z0-9_]*");
        this.g = strArr;
        this.h = arrayList;
        this.j = 1;
    }

    public lh(Context context, TextInputLayout textInputLayout, String[] strArr, ArrayList<String> arrayList, String str) {
        super(context, textInputLayout);
        this.f349a = Pattern.compile("^[a-z][a-z0-9_]*");
        this.g = strArr;
        this.h = arrayList;
        this.i = str;
        this.j = 1;
    }

    public void a(int i) {
        this.j = i;
        if (b().length() > 0) {
            b(b());
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        b(charSequence.toString().trim());
    }

    private void b(String str) {
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
        if (this.j == 1) {
            if (!str.equals(this.i) && this.h.indexOf(str) >= 0) {
                this.c.setErrorEnabled(true);
                this.c.setError(kq.a().a(this.b, R.string.common_message_name_unavailable));
                this.e = false;
                return;
            }
        } else {
            ArrayList<String> arrayList = new ArrayList();
            for (int i = 1; i <= this.j; i++) {
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
        } else if (this.f349a.matcher(str.toString()).matches()) {
            this.c.setErrorEnabled(false);
            this.e = true;
        } else {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.invalid_value_rule_4));
            this.e = false;
        }
    }
}
