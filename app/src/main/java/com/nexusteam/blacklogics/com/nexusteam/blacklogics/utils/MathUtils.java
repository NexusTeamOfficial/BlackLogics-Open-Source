
package com.nexusteam.blacklogics.utils;

import java.util.Random;

public class MathUtils {

    public static int randomInt(int min, int max) {
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }
        
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static float randomFloat(float min, float max) {
        if (min > max) {
            float temp = min;
            min = max;
            max = temp;
        }
        
        Random random = new Random();
        return min + random.nextFloat() * (max - min);
    }

    public static double randomDouble(double min, double max) {
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }
        
        Random random = new Random();
        return min + random.nextDouble() * (max - min);
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}