package com.nexusteam.internal;

import android.content.Context;
import java.util.Random;

public class ds {

    /* renamed from: a  reason: collision with root package name */
    private kv f95a;
    private int b = this.f95a.e("U1I0");
    private int c;

    public ds(Context context) {
        this.f95a = new kv(context, "U1");
        this.c = (this.b <= 3 ? this.b : 3) * 20;
    }

    public boolean a() {
        return new Random().nextInt(100) < this.c;
    }
}
