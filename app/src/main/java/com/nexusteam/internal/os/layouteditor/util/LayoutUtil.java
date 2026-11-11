package com.nexusteam.internal.os.layouteditor.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LayoutUtil {
    public static View inflate(Context context, int id) {
        return ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(id, null);
    }

    public static View inflate(Context context, ViewGroup root, int id) {
        return ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(id, root, true);
    }

    public static View inflate(Context context, ViewGroup root, int id, boolean attach) {
        return ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(id, root, attach);
    }

    public static float getDip(Context context, float value) {
        return TypedValue.applyDimension(1, value, context.getResources().getDisplayMetrics());
    }
}
