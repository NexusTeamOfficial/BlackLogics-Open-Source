package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import java.util.ArrayList;

public class jg extends Exception {

    /* renamed from: a  reason: collision with root package name */
    private ArrayList<String> f300a;

    public jg(String str) {
        super(str);
    }

    public void a(ArrayList<String> arrayList) {
        this.f300a = arrayList;
    }

    public ArrayList<String> a() {
        return this.f300a;
    }
}
