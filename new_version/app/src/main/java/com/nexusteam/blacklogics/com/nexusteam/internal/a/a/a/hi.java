package com.nexusteam.internal;

import java.util.ArrayList;
import java.util.Iterator;

public class hi {

    /* renamed from: a  reason: collision with root package name */
    private String f262a;
    private int b;
    private String c;
    private boolean d;
    private ArrayList<a> e;
    private ArrayList<hi> f;

    public hi(String str) {
        this(str, false);
    }

    public hi(String str, boolean z) {
        this.d = z;
        this.f262a = str;
        this.b = 0;
        this.e = new ArrayList<>();
        this.f = new ArrayList<>();
    }

    public void a(int i, String str, String str2, String str3) {
        this.e.add(i, new a(str, str2, str3));
    }

    public void a(String str, String str2, String str3) {
        this.e.add(new a(str, str2, str3));
    }

    public void a(hi hiVar) {
        hiVar.a(this.b + 1);
        this.f.add(hiVar);
    }

    public void a(int i) {
        this.b = i;
        if (this.f != null) {
            Iterator<hi> it = this.f.iterator();
            while (it.hasNext()) {
                it.next().a(i + 1);
            }
        }
    }

    private String b() {
        return b(0);
    }

    private String b(int i) {
        String str = "";
        for (int i2 = 0; i2 < this.b + i; i2++) {
            str = str + "\t";
        }
        return str;
    }

    public void a(String str) {
        this.c = str;
    }

    public String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(b());
        sb.append("<");
        sb.append(this.f262a);
        Iterator<a> it = this.e.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (this.e.size() <= 1 || this.d) {
                sb.append("\t");
            } else {
                sb.append("\r\n");
                sb.append(b(1));
            }
            sb.append(next.a());
        }
        if (this.f.size() > 0) {
            sb.append(">");
            sb.append("\r\n");
            Iterator<hi> it2 = this.f.iterator();
            while (it2.hasNext()) {
                sb.append(it2.next().a());
            }
            sb.append(b());
            sb.append("</");
            sb.append(this.f262a);
            sb.append(">");
            sb.append("\r\n");
        } else if (this.c == null || this.c.length() <= 0) {
            sb.append("/>");
            sb.append("\r\n");
        } else {
            sb.append(">");
            sb.append(this.c);
            sb.append("</");
            sb.append(this.f262a);
            sb.append(">");
            sb.append("\r\n");
        }
        return sb.toString();
    }

    class a {
        private String b;
        private String c;
        private String d;

        public a(String str, String str2, String str3) {
            this.b = str;
            this.c = str2;
            this.d = str3;
        }

        public String a() {
            if (this.b == null || this.b.length() <= 0) {
                return this.c + "=" + "\"" + this.d + "\"";
            }
            return this.b + ":" + this.c + "=" + "\"" + this.d + "\"";
        }
    }
}
