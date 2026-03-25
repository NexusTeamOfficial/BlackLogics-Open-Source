package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class kp {
    public static View a(Context context, int i) {
        return ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(i, (ViewGroup) null);
    }

    public static View a(Context context, ViewGroup viewGroup, int i) {
        return ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(i, viewGroup, true);
    }

    public static View a(Context context, ViewGroup viewGroup, int i, boolean z) {
        return ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(i, viewGroup, z);
    }

    public static float a(Context context, float f) {
        return TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }
}
