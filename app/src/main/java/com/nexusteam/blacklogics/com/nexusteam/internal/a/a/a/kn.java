package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import androidx.core.view.ViewCompat;
import java.util.Hashtable;

public class kn {
    
    /* renamed from: a  reason: collision with root package name */
    public static int[] f338a = {ViewCompat.MEASURED_STATE_MASK, -1, -16777088, -8355712, -16744448, -16755457, -16017703, ViewCompat.MEASURED_STATE_MASK};
    private static final String[] j = {"Activity", "View", "EditText", "onCreate", "onClick", "LinearLayout", "TextView", "ImageView", "Button", "ArrayList", "String", "Intent", "SharedPreferences", "Calendar"};
    protected Hashtable<String, Integer> b = null;
    protected Hashtable<String, Integer> c = null;
    protected StringBuffer d = new StringBuffer();
    protected String e;
    protected int f;
    protected int g;
    protected int h;
    protected boolean i = false;
    
    public kn(Context context) {
        b();
    }
    
    public final int a() {
        return this.f - this.h;
    }
    
    /* access modifiers changed from: package-private */
    public void b() {
        this.b = new Hashtable<>();
        for (String put : fc.b) {
            this.b.put(put, 2);
        }
        this.c = new Hashtable<>();
        for (String put2 : j) {
            this.c.put(put2, 6);
        }
    }
    
    public final int c() {
        return this.h;
    }
    
    public int d() {
        int e2;
        int e3;
        int e4;
        this.h = this.f;
        int e5 = e();
        if (e5 == -1) {
            return -1;
        }
        if (e5 == 34) {
            while (true) {
                int e6 = e();
                if (e6 == -1) {
                    a(e6);
                    return 4;
                } else if (e6 == 34) {
                    return 4;
                } else {
                    if (e6 == 92) {
                        e();
                    }
                }
            }
        } else if (e5 != 39) {
            switch (e5) {
                case 47:
                int e7 = e();
                if (e7 == 47) {
                    do {
                        e2 = e();
                    } while (e2 != -1 && e2 != 10);
                    a(e2);
                    return 3;
                }
                a(e7);
                return 7;
                
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                break;
                default:
                char c2 = (char) e5;
                if (Character.isWhitespace(c2)) {
                    do {
                        e4 = e();
                    } while (Character.isWhitespace((char) e4));
                    a(e4);
                    return 1;
                } else if (!Character.isJavaIdentifierStart(c2)) {
                    return 7;
                } else {
                    this.d.setLength(0);
                    do {
                        this.d.append((char) e5);
                        e5 = e();
                    } while (Character.isJavaIdentifierPart((char) e5));
                    a(e5);
                    Integer num = this.b.get(this.d.toString());
                    if (num != null) {
                        return num.intValue();
                    }
                    Integer num2 = this.c.get(this.d.toString());
                    if (num2 != null) {
                        return num2.intValue();
                    }
                    return 0;
                }
            }
            do {
                e3 = e();
            } while (Character.isDigit((char) e3));
            a(e3);
            return 5;
        } else {
            while (true) {
                int e8 = e();
                if (e8 == -1) {
                    a(e8);
                    return 4;
                } else if (e8 == 39) {
                    return 4;
                } else {
                    if (e8 == 92) {
                        e();
                    }
                }
            }
        }
    }
    
    /* access modifiers changed from: protected */
    public int e() {
        if (this.f > this.g) {
            return -1;
        }
        String str = this.e;
        int i2 = this.f;
        this.f = i2 + 1;
        return str.charAt(i2);
    }
    
    public void a(String str) {
        this.e = str;
        this.f = 0;
        this.g = this.e.length() - 1;
    }
    
    /* access modifiers changed from: protected */
    public void a(int i2) {
        if (i2 != -1) {
            this.f--;
        }
    }
}
