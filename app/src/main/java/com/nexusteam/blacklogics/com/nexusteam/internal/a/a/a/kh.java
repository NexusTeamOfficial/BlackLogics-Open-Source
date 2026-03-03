package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class kh {
    public static Bitmap a(String str, int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = i;
        return BitmapFactory.decodeFile(str, options);
    }
    
    public static Bitmap a(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }
    
    public static Bitmap a(Bitmap bitmap, int i, int i2, int i3) {
        Matrix matrix = new Matrix();
        matrix.setScale((float) i2, (float) i3);
        matrix.postRotate((float) i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }
    
    public static int a(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outWidth;
        int i4 = options.outHeight;
        int i5 = 1;
        if (i4 > i2 || i3 > i) {
            int i6 = i4 / 2;
            int i7 = i3 / 2;
            while (i6 / i5 >= i2 && i7 / i5 >= i) {
                i5 *= 2;
            }
        }
        return i5;
    }
    
    public static Bitmap a(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = a(options, i, i2);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(str, options);
    }
    
    public static int a(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt("Orientation", -1);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt != 6) {
                return attributeInt != 8 ? 0 : 270;
            }
            return 90;
        } catch(Exception e) { return 0;}
    }
    
    public static void a(String inputPath, String outputPath, int w, int h, int cornerRadius) {

        Bitmap bitmap = a(inputPath, 512, 512);
        

        int rotation = 0;
        try {
            rotation = a(inputPath); // possibly reads EXIF orientation
        } catch (Exception e) {
            e.printStackTrace();
            rotation = 0;
        }
        

        if (rotation > 0) {
            bitmap = a(bitmap, rotation);
        }
        

        Bitmap result = a(bitmap, w, h, cornerRadius);
        
        File file = new File(outputPath);
        FileOutputStream out = null;
        
        try {
            out = new FileOutputStream(file);
            result.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            result.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception ignored) {
                }
            }
        }
    }
    
}
