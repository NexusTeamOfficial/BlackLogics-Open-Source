package com.elfilibustero.quizboard.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * A class that provides a string representation of an object's public non-static fields.
 */
public class QuizMap {

    /**
     * Returns a string representation of an object's public non-static fields.
     * 
     * @param quizMap the object to convert to a string
     * @return a string representation of the object's public non-static fields
     */
    public String toString(QuizMap quizMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        // Loop through all the fields of the object
        for (Field field : quizMap.getClass().getFields()) {
            int modifiers = field.getModifiers();
            // Only include public non-static fields
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
