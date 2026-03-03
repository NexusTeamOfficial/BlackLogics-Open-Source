package com.besome.blacklogics.logic.editor;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LayoutUtil {
    public static float getDip(Context var0, float var1) {
        return TypedValue.applyDimension(1, var1, var0.getResources().getDisplayMetrics());
    }

    public static View inflate(Context var0, int var1) {
        return ((LayoutInflater)var0.getSystemService("layout_inflater")).inflate(var1, (ViewGroup)null);
    }

    public static View inflate(Context var0, ViewGroup var1, int var2) {
        return ((LayoutInflater)var0.getSystemService("layout_inflater")).inflate(var2, var1, true);
    }

    public static View inflate(Context var0, ViewGroup var1, int var2, boolean var3) {
        return ((LayoutInflater)var0.getSystemService("layout_inflater")).inflate(var2, var1, var3);
    }
}

