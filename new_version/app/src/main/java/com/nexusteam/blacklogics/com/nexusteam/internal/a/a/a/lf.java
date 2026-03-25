package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import com.nexusteam.blacklogics.R;
import android.text.Spanned;
import java.util.ArrayList;

public class lf extends le {

    /* renamed from: a  reason: collision with root package name */
    private ArrayList<String> f347a;
    private String g;

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return null;
    }

    public lf(Context context, TextInputLayout textInputLayout, ArrayList<String> arrayList) {
        super(context, textInputLayout);
        this.f347a = arrayList;
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String charSequence2 = charSequence.toString();
        if (this.g != null && this.g.length() > 0 && charSequence2.equals(this.g)) {
            this.c.setErrorEnabled(false);
            this.e = true;
        } else if (this.f347a.indexOf(charSequence2) >= 0) {
            this.c.setErrorEnabled(true);
            this.c.setError(kq.a().a(this.b, R.string.common_message_name_unavailable));
            this.e = false;
        } else {
            this.c.setErrorEnabled(false);
            this.e = true;
        }
    }
}
