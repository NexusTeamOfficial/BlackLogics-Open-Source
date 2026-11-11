package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class jo {
    public String toString(jo joVar) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (Field field : joVar.getClass().getFields()) {
            if (!Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers())) {
                try {
                    stringBuffer.append(field.getName());
                    stringBuffer.append("=");
                    stringBuffer.append(field.get(joVar));
                    stringBuffer.append(",");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
