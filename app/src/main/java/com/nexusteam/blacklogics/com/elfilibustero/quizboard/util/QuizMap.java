package com.elfilibustero.quizboard.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class QuizMap {

    public String toString(QuizMap quizMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        

        for (Field field : quizMap.getClass().getFields()) {
            int modifiers = field.getModifiers();

            if (!Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
                try {
                    sb.append(field.getName());
                    sb.append("=");
                    sb.append(field.get(quizMap));
                    sb.append(",");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        
        sb.append("]");
        return sb.toString();
    }
}
