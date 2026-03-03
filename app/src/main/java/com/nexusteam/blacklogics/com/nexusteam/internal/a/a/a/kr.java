package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import java.util.Map;

public class kr {
    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0003, code lost:
        r0 = r0.get(r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object a(java.util.Map<java.lang.String, java.lang.Object> r0, java.lang.String r1, java.lang.Object r2) {
        /*
            if (r0 != 0) goto L_0x0003
            return r2
        L_0x0003:
            java.lang.Object r0 = r0.get(r1)
            if (r0 != 0) goto L_0x000a
            return r2
        L_0x000a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: a.a.a.kr.a(java.util.Map, java.lang.String, java.lang.Object):java.lang.Object");
    }

    public static int a(Map<String, Object> map, String str, int i) {
        Object a2 = a(map, str, (Object) Integer.valueOf(i));
        if (a2 instanceof Integer) {
            return ((Integer) a(map, str, (Object) Integer.valueOf(i))).intValue();
        }
        return a2 instanceof Double ? ((Double) a(map, str, (Object) Integer.valueOf(i))).intValue() : i;
    }

    public static int a(Map<String, Object> map, String str) {
        return a(map, str, -1);
    }

    public static boolean b(Map<String, Object> map, String str) {
        return ((Boolean) a(map, str, (Object) false)).booleanValue();
    }

    public static boolean a(Map<String, Object> map, String str, boolean z) {
        return ((Boolean) a(map, str, (Object) Boolean.valueOf(z))).booleanValue();
    }

    public static String c(Map<String, Object> map, String str) {
        return (String) a(map, str, (Object) "");
    }
}
