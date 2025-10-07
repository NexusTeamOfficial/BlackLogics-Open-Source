package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.besome.blacklogics.R;
import com.besome.blacklogics.*;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;
import java.io.File;
import java.io.IOException;

public class WidgetImageView extends Widget {
    private ImageView mImageView;
    private String mImagePath;
    private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;

    public WidgetImageView(Context context) {
        this(context, null);
    }

    public WidgetImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.mImageView = new ImageView(getContext());
        this.mImageView.setPadding(8, 8, 8, 8);
        this.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.mImageView.setImageResource(R.drawable.default_image);
        addView(this.mImageView, new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        //addView(this.mImageView);
    }

    public void setImagePath(String path) {
        if (path == null || path.equals(mImagePath)) {
            return;
        }
        
        this.mImagePath = path;
        loadImage(path);
    }

    public String getImagePath() {
        return mImagePath;
    }
    
    public void setImageBitmap(Bitmap bitmap) {
        if (mImageView != null && bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        }
    }

    public Drawable getDrawable() {
        return mImageView != null ? mImageView.getDrawable() : null;
    }

    public void clearImage() {
        if (mImageView != null) {
            mImageView.setImageDrawable(null);
        }
    }
    
    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        mImageView.setScaleType(scaleType);
    }
    

    private void loadImage(String path) {
        new Thread(() -> {
            try {
                File imageFile = new File(path);
                if (!imageFile.exists()) {
                    throw new IOException("File not found");
                }

                // First decode with bounds to check dimensions
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, options);

                // Calculate sample size
                int reqWidth = getWidth() > 0 ? getWidth() : 1000;
                int reqHeight = getHeight() > 0 ? getHeight() : 1000;
                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

                // Decode actual bitmap
                options.inJustDecodeBounds = false;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeFile(path, options);

                // Correct orientation
                bitmap = rotateBitmapIfRequired(bitmap, path);

                final Bitmap finalBitmap = bitmap;
                post(() -> {
                    if (finalBitmap != null) {
                        mImageView.setImageBitmap(finalBitmap);
                    } else {
                        mImageView.setImageResource(R.drawable.ic_broken_image);
                    }
                });
            } catch (Exception e) {
                post(() -> mImageView.setImageResource(R.drawable.ic_broken_image));
            }
        }).start();
    }

    private Bitmap rotateBitmapIfRequired(Bitmap bitmap, String path) throws IOException {
        ExifInterface exif = new ExifInterface(path);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateBitmap(bitmap, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateBitmap(bitmap, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateBitmap(bitmap, 270);
            default:
                return bitmap;
        }
    }

    private Bitmap rotateBitmap(Bitmap bitmap, int degree) {
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
        if (mImageView != null) {
            mImageView.setLayoutParams(layoutParams);
        }
    }

    public static String newWidgetId() {
        int i = 1;
        while (WidgetUtil.isWidgetIdExist("imageview" + i)) {
            i++;
        }
        return "imageview" + i;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mImageView != null) {
            mImageView.setImageDrawable(null);
        }
    }
}