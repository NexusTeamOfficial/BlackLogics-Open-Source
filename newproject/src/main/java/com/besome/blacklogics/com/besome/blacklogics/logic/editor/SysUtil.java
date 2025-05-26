package com.besome.blacklogics.logic.editor;

import android.content.Context;

public class SysUtil {
    public static int getStatusBarHeight(Context var0) {
        int var1 = var0.getResources().getIdentifier("status_bar_height", "dimen", "android");
        
        int var2 = 0;
        if(var1 > 0) {
            var2 = var0.getResources().getDimensionPixelSize(var1);
        }
        
        //lol

        return var2;
    }
}
