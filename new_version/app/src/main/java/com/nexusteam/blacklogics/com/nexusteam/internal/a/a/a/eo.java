package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

public class eo {

    /* renamed from: a  reason: collision with root package name */
    public static boolean f120a;
    public static long[] b = new long[10];

    public static boolean a(int i) {
        if (b == null) {
            b = new long[10];
        }
        return b[i] == 0 || b(i) > 30000;
    }

    public static long b(int i) {
        if (b == null) {
            b = new long[10];
        }
        return System.currentTimeMillis() - b[i];
    }
}
