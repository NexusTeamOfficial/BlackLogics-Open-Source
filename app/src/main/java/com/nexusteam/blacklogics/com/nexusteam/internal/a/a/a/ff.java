package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

public class ff {
    public static boolean a(String str) {
        return true;
    }

    public static boolean b(String str) {
        try {
            if (Integer.valueOf(str).intValue() < Integer.valueOf("200").intValue() || Integer.valueOf(str).intValue() >= Integer.valueOf("600").intValue()) {
                return false;
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}
