package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class kx {
    public static boolean a(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static String a(int i) {
        if (i < 0) {
            return "0";
        }
        float f = (float) i;
        if (f >= 1024.0f && f < 1048576.0f) {
            return new DecimalFormat("#.#KB").format((double) (f / 1024.0f));
        }
        if (f >= 1048576.0f && f < 1.07374182E9f) {
            return new DecimalFormat("#.#MB").format((double) (f / 1048576.0f));
        }
        if (f >= 1.07374182E9f && f < 1.09951163E12f) {
            return new DecimalFormat("#.#GB").format((double) (f / 1.07374182E9f));
        }
        return String.valueOf(i) + "B";
    }

    public static String b(int i) {
        float f = (float) i;
        if (f >= 1000.0f && f < 1000000.0f) {
            return new DecimalFormat("#.#K").format((double) (f / 1000.0f));
        }
        if (f >= 1000000.0f && f < 1.0E9f) {
            return new DecimalFormat("#.#M").format((double) (f / 1000000.0f));
        }
        if (f < 1.0E9f || f >= 1.0E12f) {
            return String.valueOf(i);
        }
        return new DecimalFormat("#.#G").format((double) (f / 1.0E9f));
    }

    public static void a(Context context, String str, String str2) {
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(str, str2));
    }

    public static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            if ((bArr[i] & 255) < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Long.toString((long) (bArr[i] & 255), 16));
        }
        return stringBuffer.toString();
    }

    public static byte[] b(String str) {
        int length = str.length();
        byte[] bArr = new byte[((length + 1) / 2)];
        int i = 0;
        int i2 = 1;
        if (length % 2 == 1) {
            bArr[0] = (byte) a(str.charAt(0));
            i = 1;
        } else {
            i2 = 0;
        }
        while (i < length) {
            int i3 = i + 1;
            bArr[i2] = (byte) ((a(str.charAt(i)) << 4) | a(str.charAt(i3)));
            i2++;
            i = i3 + 1;
        }
        return bArr;
    }

    private static int a(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return (c - 'A') + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return (c - 'a') + 10;
        }
        throw new IllegalArgumentException("invalid hex digit '" + c + "'");
    }

    public static String a() {
        Random random = new Random();
        int nextInt = random.nextInt(100000);
        while (true) {
            if (nextInt >= 10000 && nextInt <= 99999) {
                return String.valueOf(nextInt);
            }
            nextInt = random.nextInt(100000);
        }
    }

    public static String c(String str) {
        String str2 = "";
        int i = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt == '\\') {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                i++;
                sb.append(str.charAt(i));
                str2 = sb.toString();
            } else {
                str2 = str2 + charAt;
            }
            i++;
        }
        return str2;
    }

    public static ArrayList<String> d(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        a aVar = new a(str);
        while (!aVar.a()) {
            String c = aVar.c();
            if (c.length() > 0) {
                arrayList.add(c);
            }
        }
        return arrayList;
    }

    public static class a {

        /* renamed from: a  reason: collision with root package name */
        private String f344a;
        private int b = 0;

        public a(String str) {
            this.f344a = str;
        }

        public boolean a() {
            return this.b >= this.f344a.length();
        }

        public void b() {
            while (this.b < this.f344a.length() && this.f344a.charAt(this.b) == ' ') {
                this.b++;
            }
        }

        public String c() {
            b();
            if (a()) {
                return "";
            }
            String str = "";
            boolean z = false;
            int i = this.b;
            while (this.b < this.f344a.length() && this.f344a.charAt(this.b) != ' ') {
                char charAt = this.f344a.charAt(this.b);
                if (charAt != '\\') {
                    if (charAt == '%') {
                        if (this.b > i) {
                            break;
                        }
                        z = true;
                    }
                    if (z && (charAt == '?' || charAt == '-')) {
                        break;
                    }
                    str = str + charAt;
                    this.b++;
                } else {
                    str = str + (charAt + this.f344a.charAt(this.b + 1));
                    this.b += 2;
                }
            }
            return str;
        }
    }

    public static String c(int i) {
        return new DecimalFormat("#,###").format((long) i);
    }
}
