package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;
import java.io.File;
import java.io.IOException;

public class WidgetImageView extends ImageView implements WidgetContract {
    private String mWidgetId;
    private String mWidgetName;
    private boolean isSelected = false;
    private Paint widgetPaint = new Paint();
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

   // @Override
    public void init() {
        //super.init();
        setPadding(8, 8, 8, 8);
        setScaleType(ImageView.ScaleType.CENTER_CROP);
        setImageResource(R.drawable.default_image);
        setEnabled(false);
    }

    @Override
    public void setWidgetId(String id) {
        this.mWidgetId = id;
    }

    @Override
    public String getWidgetId() {
        return mWidgetId;
    }

    @Override
    public void setWidgetName(String name) {
        this.mWidgetName = name;
    }

    @Override
    public String getWidgetName() {
        return mWidgetName;
    }

    @Override
    public Paint getWidgetPaint() {
        return widgetPaint;
    }

    @Override
    public void select() {
        isSelected = true;
        widgetPaint.setColor(getResources().getColor(R.color.widget_selection_color));
        invalidate();
    }

    @Override
    public void unselect() {
        isSelected = false;
        widgetPaint.setColor(0);
        invalidate();
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        if (isSelected) {
            canvas.drawRect(0, 0, getWidth(), getHeight(), widgetPaint);
        }
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

    @Override
    public void setImageResource(int resourceId) {
        super.setImageResource(resourceId);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            super.setImageBitmap(bitmap);
        }
    }

    public Drawable getDrawable() {
        return super.getDrawable();
    }

    public void clearImage() {
        setImageDrawable(null);
    }

    @Override
    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        super.setScaleType(scaleType);
    }

    @Override
    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    private void loadImage(String path) {
        new Thread(() -> {
            try {
                File imageFile = new File(path);
                if (!imageFile.exists()) {
                    throw new IOException("File not found");
                }

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, options);

                int reqWidth = getWidth() > 0 ? getWidth() : 1000;
                int reqHeight = getHeight() > 0 ? getHeight() : 1000;
                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

                options.inJustDecodeBounds = false;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeFile(path, options);

                bitmap = rotateBitmapIfRequired(bitmap, path);

                final Bitmap finalBitmap = bitmap;
                post(() -> {
                    if (finalBitmap != null) {
                        setImageBitmap(finalBitmap);
                    } else {
                        setImageResource(R.drawable.ic_broken_image);
                    }
                });
            } catch (Exception e) {
                post(() -> setImageResource(R.drawable.ic_broken_image));
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
    public String newWidgetId() {
        int i = 1;
        while (WidgetUtil.isWidgetIdExist("imageview" + i)) {
            i++;
        }
        return "imageview" + i;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }
}