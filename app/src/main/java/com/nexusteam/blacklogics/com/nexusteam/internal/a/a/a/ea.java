package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import org.json.JSONObject;

public class ea {
    
    /* renamed from: a  reason: collision with root package name */
    String f108a;
    String b;
    String c;
    String d;
    long e;
    int f;
    String g;
    String h;
    String i;
    String j;
    
    public ea(String str, String str2, String str3) {
        try {
            this.f108a = str;
            this.i = str2;
            JSONObject jSONObject = new JSONObject(this.i);
            this.b = jSONObject.optString("orderId");
            this.c = jSONObject.optString("packageName");
            this.d = jSONObject.optString("productId");
            this.e = jSONObject.optLong("purchaseTime");
            this.f = jSONObject.optInt("purchaseState");
            this.g = jSONObject.optString("developerPayload");
            this.h = jSONObject.optString("token", jSONObject.optString("purchaseToken"));
            this.j = str3;
        } catch(Exception e) {
            
        }
    }
    
    public String a() {
        return this.f108a;
    }
    
    public String b() {
        return this.b;
    }
    
    public String c() {
        return this.d;
    }
    
    public long d() {
        return this.e;
    }
    
    public int e() {
        return this.f;
    }
    
    public String f() {
        return this.g;
    }
    
    public String g() {
        return this.h;
    }
    
    public String toString() {
        return "PurchaseInfo(type:" + this.f108a + "):" + this.i;
    }
}
