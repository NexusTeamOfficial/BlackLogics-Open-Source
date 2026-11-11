package com.shapun.layouteditor.utils;

import android.view.View;
import android.view.ViewGroup;

public class ViewGroupUtils {

    public static ViewGroup getParent(View view) {
        if (view == null) return null;
        return (ViewGroup) view.getParent();
    }

    public static void removeView(View view) {
        if (view == null) return; // null-safe check
        ViewGroup parent = getParent(view);
        if (parent != null) {
            parent.removeView(view);
        }
    }

    public static void replaceView(View view1, View view2) {
        if (view1 == null || view2 == null) return;

        removeView(view2);

        ViewGroup parent = (ViewGroup) view1.getParent();
        if (parent != null) {
            int index = parent.indexOfChild(view1);
            parent.removeView(view1);
            parent.addView(view2, index);
        }
    }

    public static void addView(View view, ViewGroup parent) {
        if (parent == null || view == null) return;
        removeView(view);
        parent.addView(view);
    }

    public static void addView(View view, ViewGroup newParent, int pos) {
        if (view == null || newParent == null) return;

        ViewGroup parent = (ViewGroup) view.getParent();


        if (parent == newParent && parent.indexOfChild(view) == pos) {
            return;
        }

        removeView(view);
        if (pos < 0 || pos > newParent.getChildCount()) {
            newParent.addView(view); // fallback if position invalid
        } else {
            newParent.addView(view, pos);
        }
    }
}
