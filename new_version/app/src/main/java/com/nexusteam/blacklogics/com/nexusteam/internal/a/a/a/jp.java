package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.nexusteam.internal.beans.ProjectLibraryBean;
import java.util.HashMap;

public class jp {

    /* renamed from: a  reason: collision with root package name */
    private kv f307a;

    public jp(Context context) {
        this.f307a = new kv(context, "P2");
    }

    public void a(HashMap<String, Object> hashMap) {
        this.f307a.a("P2I1", Integer.valueOf(kr.a(hashMap, "user_id")), false);
        this.f307a.a("P2I2", kr.c(hashMap, "login_id"), false);
        this.f307a.a("P2I3", kr.c(hashMap, "login_pwd"), false);
        this.f307a.a("P2I4", kr.c(hashMap, "is_sns_user"), false);
        this.f307a.a("P2I5", kr.c(hashMap, "sns_kind"), false);
        this.f307a.a("P2I6", kr.c(hashMap, "alias"), false);
        this.f307a.a("P2I7", Integer.valueOf(kr.a(hashMap, "level")), false);
        this.f307a.a("P2I8", Integer.valueOf(kr.a(hashMap, "max_share_cnt")), false);
        this.f307a.a("P2I9", Integer.valueOf(kr.a(hashMap, "promotion_id")), false);
        this.f307a.a("P2I10", Integer.valueOf(kr.a(hashMap, "reward_type")), false);
        this.f307a.a("P2I11", kr.c(hashMap, "reward_code"), false);
        this.f307a.a("P2I12", kr.c(hashMap, "expire_dt"), false);
        this.f307a.a("P2I13", kr.c(hashMap, "device_id"), false);
        this.f307a.a("P2I14", kr.c(hashMap, "session_id"), false);
        this.f307a.a("P2I15", Integer.valueOf(kr.a(hashMap, "grade_point")), false);
        this.f307a.a("P2I16", Integer.valueOf(kr.a(hashMap, "grade_level")), false);
        this.f307a.b();
    }

    public void a() {
        this.f307a.a("P2I1");
        this.f307a.a("P2I2");
        this.f307a.a("P2I3");
        this.f307a.a("P2I4");
        this.f307a.a("P2I6");
        this.f307a.a("P2I7");
        this.f307a.a("P2I8");
        this.f307a.a("P2I14");
        this.f307a.a("P2I15");
        this.f307a.a("P2I16");
    }

    public boolean b() {
        return h() > 0;
    }

    public boolean c() {
        return this.f307a.a("P2I7", 0) == 8;
    }

    public boolean d() {
        return this.f307a.a("P2I7", 0) == 9;
    }

    public boolean e() {
        return c() || d();
    }

    public boolean f() {
        return this.f307a.a("P2I7", 0) == 1;
    }

    public String g() {
        return this.f307a.a("P2I6", "");
    }

    public void a(String str) {
        this.f307a.a("P2I6", (Object) str);
    }

    public int h() {
        return this.f307a.a("P2I1", -1);
    }

    public String i() {
        return this.f307a.a("P2I2", "");
    }

    public String j() {
        return this.f307a.d("P2I13");
    }

    public int k() {
        return this.f307a.a("P2I7", -1);
    }

    public boolean l() {
        return ProjectLibraryBean.LIB_USE_Y.equals(this.f307a.a("P2I4", ProjectLibraryBean.LIB_USE_N));
    }

    public boolean m() {
        return "facebook".equals(this.f307a.a("P2I5", ""));
    }

    public boolean n() {
        return "google".equals(this.f307a.a("P2I5", ""));
    }

    public String o() {
        return this.f307a.a("P2I14", "");
    }

    public int p() {
        return this.f307a.a("P2I15", 0);
    }

    public int q() {
        return this.f307a.a("P2I16", 0);
    }
}
