package com.nexusteam.internal.beans;

import android.graphics.Color;

public class ColorBean {
    public int colorCode;
    public String colorName;
    public int displayNameColor;
    public int icon;

    public ColorBean(String str, String str2, String str3, int i) {
        this(Color.parseColor(str), str2, Color.parseColor(str3), i);
    }

    public ColorBean(int i, String str, int i2, int i3) {
        this.colorCode = i;
        this.colorName = str;
        this.displayNameColor = i2;
        this.icon = i3;
    }

    public String getColorCode(boolean z) {
        if (this.colorCode == 0) {
            return "TRANSPARENT";
        }
        if (this.colorCode == 16777215) {
            return "NONE";
        }
        if (z) {
            return String.format("#%08X", new Object[]{Integer.valueOf(this.colorCode)});
        }
        return String.format("#%06X", new Object[]{Integer.valueOf(16777215 & this.colorCode)});
    }
}
