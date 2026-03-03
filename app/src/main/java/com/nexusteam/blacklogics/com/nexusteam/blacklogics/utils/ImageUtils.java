
package com.nexusteam.blacklogics.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class ImageUtils {
    
    public static Bitmap rotateBitmap(Bitmap source, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    
    public static Bitmap scaleBitmap(Bitmap source, float scaleX, float scaleY) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    
    public static Bitmap decodeSampledBitmap(String path, int reqWidth, int reqHeight) {
        return FileUtil.decodeSampleBitmapFromPath(path, reqWidth, reqHeight);
    }
}