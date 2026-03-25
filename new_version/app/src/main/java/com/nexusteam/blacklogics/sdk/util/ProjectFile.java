package com.nexusteam.sdk.util;

import a.a.a.lC;
import a.a.a.yB;
import android.graphics.Color;

public class ProjectFile {
    public static int getColor(String sc_id, String color) {
        return yB.a(lC.b(sc_id), color, getDefaultColor(color));
    }

    private static int getDefaultColor(String color) {
        switch (color) {
            case "color_primary_dark":
                return Color.parseColor("#ff0084c2");

            case "color_control_highlight":
                return Color.parseColor("#20008dcd");

            case "color_control_normal":
                return Color.parseColor("#ff57beee");

            default:
                return Color.parseColor("#ff008dcd");
        }
    }
}