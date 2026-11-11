package com.nexusteam.internal;

import com.nexusteam.blacklogics.R;

public class ma {

    private static lw f372a;
    private static ly b;
    private static mb c;
    private static lz d;

    public static void a() {
        f372a = null;
        b = null;
        c = null;
        d = null;
    }

    public static void b() {
        try {
            if (f372a != null) {
                f372a.b();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            f372a = null;
        }
    }

    public static void c() {
        try {
            if (b != null) {
                b.c();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            b = null;
        }
    }

    public static void d() {
        try {
            if (c != null) {
                c.a();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            c = null;
        }
    }

    public static void e() {
        try {
            if (d != null) {
                d.a();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            d = null;
        }
    }

    public static synchronized lw a(String str) {
        return a(str, true);
    }

    public static synchronized lw a(String str, boolean z) {
        synchronized (ma.class) {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            if (f372a != null && f372a.f364a != null && !str.equals(f372a.f364a)) {
                b();
            }
            if (f372a == null) {
                f372a = new lw(str);
                try {
                    if (!z) {
                        f372a.j();
                        f372a.k();
                    } else {
                        if (f372a.f()) {
                            f372a.h();
                        } else {
                            f372a.j();
                        }
                        if (f372a.g()) {
                            f372a.i();
                        } else {
                            f372a.k();
                        }
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            return f372a;
        }
    }

    public static synchronized ly b(String str) {
        return b(str, true);
    }

    public static synchronized ly b(String str, boolean z) {
        synchronized (ma.class) {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            if (b != null && b.f369a != null && !str.equals(b.f369a)) {
                c();
            }
            if (b == null) {
                b = new ly(str);
                try {
                    if (!z) {
                        b.d();
                    } else if (b.h()) {
                        b.i();
                    } else {
                        b.d();
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            return b;
        }
    }

    public static synchronized mb c(String str) {
        return c(str, true);
    }

    public static synchronized mb c(String str, boolean z) {
        synchronized (ma.class) {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            if (c != null && c.g != null && !str.equals(c.g)) {
                d();
            }
            if (c == null) {
                c = new mb(str);
                try {
                    if (!z) {
                        c.g();
                    } else if (c.k()) {
                        c.l();
                    } else {
                        c.g();
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            return c;
        }
    }

    public static synchronized lz d(String str) {
        return d(str, true);
    }

    public static synchronized lz d(String str, boolean z) {
        synchronized (ma.class) {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            if (d != null && d.f370a != null && !str.equals(d.f370a)) {
                e();
            }
            if (d == null) {
                d = new lz(str);
                try {
                    if (!z) {
                        d.b();
                    } else if (d.k()) {
                        d.j();
                    } else {
                        d.b();
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            return d;
        }
    }
}
