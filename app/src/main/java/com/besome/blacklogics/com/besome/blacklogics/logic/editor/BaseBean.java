package com.besome.blacklogics.logic.editor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class BaseBean {
    public String toString(BaseBean var1) {
        StringBuffer var2 = new StringBuffer();
        var2.append("[");
        Field[] var4 = var1.getClass().getFields();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field var8 = var4[var6];
            if(!Modifier.isStatic(var8.getModifiers()) && Modifier.isPublic(var8.getModifiers())) {
                try {
                    var2.append(var8.getName()).append("=").append(var8.get(var1)).append(",");
                } catch (Exception var10) {
                    var10.printStackTrace();
                }
            }
        }

        var2.append("]");
        return var2.toString();
    }
}

