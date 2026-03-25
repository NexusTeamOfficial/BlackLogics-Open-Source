package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.os.Environment;
import com.android.sdklib.repository.RepoConstants;
import java.io.File;

public class fe {
    public static final String A = ("blacklogics" + File.separator + "localization" + File.separator + "strings_provided.xml");
    public static final String B;
    public static final String C = ("blacklogics" + File.separator + "keystore");
    public static final String D = ("blacklogics" + File.separator + "keystore" + File.separator + "release_key.jks");
    public static final String E;
    public static final String F = (".blacklogics" + File.separator + "upload");
    public static final String[] G = {"subs_year_01", "subs_50_year_01", "subs_30_year_01", "subs_20_year_01", "subs_month_06", "subs_month_03", "subs_month_01", "subs_50_month_01", "subs_30_month_01", "subs_20_month_01"};
    public static final long[] H = {32140800000L, 32140800000L, 32140800000L, 32140800000L, 16070400000L, 8035200000L, 2678400000L, 2678400000L, 2678400000L, 2678400000L};
    public static final String[] I = {"subs_month_01", "subs_year_01"};
    public static final String[] J = {"subs_50_month_01", "subs_50_year_01"};
    public static final String[] K = {"subs_30_month_01", "subs_30_year_01"};
    public static final String[] L = {"subs_20_month_01", "subs_20_year_01"};
    public static final String[] M = {"F83085529A75E7A8CEDD64013B1A374B", "90C443DFAB7F23424DE7E079787466CD", "F83085529A75E7A8CEDD64013B1A374B", "C99E5B3F179203AE2749F8F9B5A7493A", "100EFD7391FF1BEE4A1E2F960A1B8AF2"};
    public static final String[] N = {"1486507718310013_1788685811425534", "1486507718310013_1804931006467681", "1486507718310013_1805009746459807", "1486507718310013_1805001526460629", "1486507718310013_1805273579766757", "1486507718310013_1805397669754348", "1486507718310013_1805436593083789", "1486507718310013_1805666736394108", "1486507718310013_1805724186388363", "1486507718310013_1809233042704144"};
    public static final String[] O = {"255022168522663_266931247331755", "255022168522663_268282677196612", "255022168522663_268283823863164", "255022168522663_266575314034015", "255022168522663_279474749410738"};
    public static final String[] P = {"Activity", "CustomView"};

    /* renamed from: a  reason: collision with root package name */
    public static final String f134a = (".blacklogics" + File.separator + RepoConstants.NODE_LIBS);
    public static final String b = (".blacklogics" + File.separator + "mysc");
    public static final String c = (".blacklogics" + File.separator + "mysc" + File.separator + "list");
    public static final String d;
    public static final String e = (".blacklogics" + File.separator + "bak");
    public static final String f = (".blacklogics" + File.separator + RepoConstants.FD_TEMP + File.separator + "images");
    public static final String g = (".blacklogics" + File.separator + RepoConstants.FD_TEMP + File.separator + "sounds");
    public static final String h = (".blacklogics" + File.separator + RepoConstants.FD_TEMP + File.separator + "fonts");
    public static final String i = (".blacklogics" + File.separator + RepoConstants.FD_TEMP + File.separator + "proj");
    public static final String j = (".blacklogics" + File.separator + RepoConstants.FD_TEMP + File.separator + "data");
    public static final String k = (".blacklogics" + File.separator + RepoConstants.FD_TEMP + File.separator + "iconpack");
    public static final String l;
    public static final String m = (".blacklogics" + File.separator + "resources" + File.separator + "icons");
    public static final String n = (".blacklogics" + File.separator + "resources" + File.separator + "images");
    public static final String o = (".blacklogics" + File.separator + "resources" + File.separator + "sounds");
    public static final String p = (".blacklogics" + File.separator + "resources" + File.separator + "fonts");
    public static final String q = "";
    public static final String r = (q + File.separator + "apk");
    public static final String s = (q + File.separator + "data");
    public static final String t = (".blacklogics" + File.separator + "tutorial" + File.separator + "images");
    public static final String u = (".blacklogics" + File.separator + "tutorial" + File.separator + "sounds");
    public static final String v = (".blacklogics" + File.separator + "tutorial" + File.separator + "fonts");
    public static final String w = (".blacklogics" + File.separator + "tutorial" + File.separator + "proj");
    public static final String x;
    public static final String y = ("blacklogics" + File.separator + "localization");
    public static final String z = ("blacklogics" + File.separator + "localization" + File.separator + "strings.xml");

    public static String a() {
        return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxqe7Fu3i3VfnKRSRRljTsMuk7Br0dXaFdGnMNCzMGLQ72PSTEAUo4sXs5+Utmdf9R2s2tZyArdnehk9+Q72F0XzEZGeVfgzfLky7ffuk04yxUye/FlXBun/s0F7g2496+PyfXCP9jIBdncvQ9kaT8Xn6F/j0s2TqS/6xlCD38eYgCVFyp1mld1vYhGCZBlQvXFVJAoKCzqN2QZVO5KarkyTQSGeudvV/UQsJgyHh5zTZKnla1VIVj1Wl3nBb//s2dsmFnAx3500Y/h//XHveLUS7BkP34AGGWPLuoyJruLNvrZ3uUNDnCgnW4+z8Ilaj2SwCTeqQvvw/suZdExs88QIDAQAB";
    }

    public static String a(int i2) {
        switch (i2) {
            case 0:
                return "linear";
            case 2:
                return "hscroll";
            case 3:
                return "button";
            case 4:
                return "textview";
            case 5:
                return "edittext";
            case 6:
                return "imageview";
            case 7:
                return "webview";
            case 8:
                return "progressbar";
            case 9:
                return "listview";
            case 10:
                return "spinner";
            case 11:
                return "checkbox";
            case 12:
                return "vscroll";
            case 13:
                return "switch";
            case 14:
                return "seekbar";
            case 15:
                return "calendarview";
            case 17:
                return "adview";
            case 18:
                return "mapview";
            default:
                return "widget";
        }
    }

    public static String b(int i2) {
        switch (i2) {
            case 1:
                return "SL-01";
            case 2:
                return "SL-02";
            case 3:
                return "SL-03";
            case 4:
                return "SL-04";
            default:
                return "SL-01";
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(".blacklogics");
        sb.append(File.separator);
        sb.append("data");
        d = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(".blacklogics");
        sb2.append(File.separator);
        sb2.append("resources");
        l = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(".blacklogics");
        sb3.append(File.separator);
        sb3.append("download");

        StringBuilder sb4 = new StringBuilder();
        sb4.append(".blacklogics");
        sb4.append(File.separator);
        sb4.append("collection");
        x = sb4.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append("blacklogics");
        sb5.append(File.separator);
        sb5.append("signed_apk");
        B = sb5.toString();
        StringBuilder sb6 = new StringBuilder();
        sb6.append("blacklogics");
        sb6.append(File.separator);
        sb6.append("service_account");
        E = sb6.toString();
    }

    public static String b() {
        File externalStorageDirectory = new File("/storage/emulated/0/");
        return new File(externalStorageDirectory, ".blacklogics" + File.separator).getAbsolutePath();
    }

    public static String c() {
        return new File("/storage/emulated/0/", f134a).getAbsolutePath();
    }

    public static String a(String str) {
        return new File("/storage/emulated/0/", b + File.separator + str).getAbsolutePath();
    }

    public static String d() {
        return new File("/storage/emulated/0/", c).getAbsolutePath();
    }

    public static String b(String str) {
        return new File("/storage/emulated/0/", c + File.separator + str).getAbsolutePath();
    }

    public static final String e() {
        return new File("/storage/emulated/0/", "blacklogics").getAbsolutePath();
    }

    public static final String f() {
        return new File("/storage/emulated/0/", y).getAbsolutePath();
    }

    public static final String g() {
        return new File("/storage/emulated/0/", z).getAbsolutePath();
    }

    public static final String h() {
        return new File("/storage/emulated/0/", A).getAbsolutePath();
    }

    public static final String i() {
        return new File("/storage/emulated/0/", B).getAbsolutePath();
    }

    @Deprecated
    public static final String j() {
        return new File("/storage/emulated/0/", C).getAbsolutePath();
    }

    public static final String k() {
        return new File("/storage/emulated/0/", D).getAbsolutePath();
    }

    public static final String l() {
        return new File("/storage/emulated/0/", E).getAbsolutePath();
    }

    public static String m() {
        return new File("/storage/emulated/0/", f).getAbsolutePath();
    }

    public static String n() {
        return new File("/storage/emulated/0/", g).getAbsolutePath();
    }

    public static String o() {
        return new File("/storage/emulated/0/", h).getAbsolutePath();
    }

    public static String p() {
        return new File("/storage/emulated/0/", k).getAbsolutePath();
    }

    public static String q() {
        return new File("/storage/emulated/0/", m).getAbsolutePath();
    }

    public static String r() {
        return new File("/storage/emulated/0/", n).getAbsolutePath();
    }

    public static String s() {
        return new File("/storage/emulated/0/", o).getAbsolutePath();
    }

    public static String t() {
        return new File("/storage/emulated/0/", p).getAbsolutePath();
    }

    public static String u() {
        return new File("/storage/emulated/0/", q).getAbsolutePath();
    }

    public static String v() {
        return new File("/storage/emulated/0/", s).getAbsolutePath();
    }

    public static String w() {
        return new File("/storage/emulated/0/", x).getAbsolutePath();
    }

    public static String c(String str) {
        return "resource" + File.separator + str + File.separator + "res.zip";
    }

    public static String d(String str) {
        return new File("/storage/emulated/0/", d + File.separator + str).getAbsolutePath();
    }

    public static String e(String str) {
        return new File("/storage/emulated/0/", e + File.separator + str).getAbsolutePath();
    }

    public static String x() {
        return new File("/storage/emulated/0/", F).getAbsolutePath();
    }

    public static void a(Context context, String str) {
        kv kvVar = new kv(context, "P17_" + str);
        kv kvVar2 = new kv(context, "P18_" + str);
        kv kvVar3 = new kv(context, "P13_" + str);
        kv kvVar4 = new kv(context, "P14_" + str);
        kvVar.c();
        kvVar2.c();
        kvVar3.c();
        kvVar4.c();
        kv kvVar5 = new kv(context, "D03_" + str);
        kv kvVar6 = new kv(context, "D04_" + str);
        kv kvVar7 = new kv(context, "D01_" + str);
        kv kvVar8 = new kv(context, "D02_" + str);
        kvVar5.c();
        kvVar6.c();
        kvVar7.c();
        kvVar8.c();
    }
}
