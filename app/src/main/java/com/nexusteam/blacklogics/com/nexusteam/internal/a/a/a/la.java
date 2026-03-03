package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import androidx.core.view.ViewCompat;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;

public class la {

    /* renamed from: a  reason: collision with root package name */
    public static int[] f345a = {ViewCompat.MEASURED_STATE_MASK, -1, -16777088, -9725696, -16744448, -10088838, ViewCompat.MEASURED_STATE_MASK};
    private static final String[] i = {"android"};
    protected Hashtable<String, Integer> b = null;
    protected StringBuffer c = new StringBuffer();
    protected String d;
    protected int e;
    protected int f;
    protected int g;
    protected boolean h;

    public la(Context context) {
        this.h = false;
        this.b = new Hashtable<>();
        for (String put : i) {
            this.b.put(put, 5);
        }
    }

    public final int a() {
        return this.e - this.g;
    }

    public final int b() {
        return this.g;
    }

    public int c() {
        int d2;
        int d3;
        int d4;
        this.g = this.e;
        int d5 = d();
        if (d5 == -1) {
            return -1;
        }
        if (d5 == 34) {
            while (true) {
                int d6 = d();
                if (d6 == -1) {
                    a(d6);
                    return 4;
                } else if (d6 == 34) {
                    return 4;
                } else {
                    if (d6 == 92) {
                        d();
                    }
                }
            }
        } else if (d5 == 39) {
            while (true) {
                int d7 = d();
                if (d7 == -1) {
                    a(d7);
                    return 4;
                } else if (d7 == 39) {
                    return 4;
                } else {
                    if (d7 == 92) {
                        d();
                    }
                }
            }
        } else if (d5 == 60) {
            do {
                d2 = d();
                if (Character.isWhitespace((char) d2) || d2 == 10) {
                    a(d2);
                }
            } while (d2 != -1);
            a(d2);
            return 2;
        } else if (d5 == 62) {
            return 2;
        } else {
            char c2 = (char) d5;
            if (Character.isWhitespace(c2)) {
                do {
                    d4 = d();
                } while (Character.isWhitespace((char) d4));
                a(d4);
                return 1;
            } else if (Character.isLetter(c2)) {
                this.c.setLength(0);
                do {
                    this.c.append((char) d5);
                    d5 = d();
                } while (Character.isLetter((char) d5));
                a(d5);
                Integer num = this.b.get(this.c.toString());
                if (num != null) {
                    return num.intValue();
                }
                return 0;
            } else if (!Character.isLetter(c2)) {
                return 6;
            } else {
                do {
                    d3 = d();
                    if ('=' == ((char) d3)) {
                        break;
                    }
                } while (d3 != 10);
                a(d3);
                return 0;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int d() {
        if (this.e > this.f) {
            return -1;
        }
        String str = this.d;
        int i2 = this.e;
        this.e = i2 + 1;
        return str.charAt(i2);
    }

    public void a(String str) {
        this.d = str;
        this.e = 0;
        this.f = this.d.length() - 1;
    }

    /* access modifiers changed from: protected */
    public void a(int i2) {
        if (i2 != -1) {
            this.e--;
        }
    }

    public ArrayList<int[]> b(String str) {
        int i2;
        ArrayList<int[]> arrayList = new ArrayList<>();
        StringReader stringReader = new StringReader(str);
        int[] iArr = new int[2];
        boolean z = false;
        int i3 = 0;
        boolean z2 = false;
        while (!z) {
            try {
                int read = stringReader.read();
                if (read == -1) {
                    if (z2) {
                        iArr[1] = i3;
                        arrayList.add(iArr);
                    }
                    z = true;
                } else if (read == 45) {
                    if (z2) {
                        stringReader.read();
                        i3 = i3 + 1 + 1;
                        if (stringReader.read() == 62) {
                            i3++;
                            iArr[1] = i3;
                            arrayList.add(iArr);
                            z2 = false;
                        }
                    }
                    i3++;
                } else if (read != 60) {
                    i3++;
                } else {
                    if (stringReader.read() != 33 || z2) {
                        i2 = i3 + 1;
                    } else {
                        int[] iArr2 = new int[2];
                        iArr2[0] = i3 > 0 ? i3 - 1 : i3;
                        i2 = i3 + 1;
                        iArr = iArr2;
                        z2 = true;
                    }
                    i3 = i2 + 1;
                }
            } catch (IOException unused) {
            }
        }
        return arrayList;
    }
}
