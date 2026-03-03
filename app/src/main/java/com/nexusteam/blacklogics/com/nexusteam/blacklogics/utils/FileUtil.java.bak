
package com.nexusteam.blacklogics.utils;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtil {

    public static boolean renameFile(String sourcePath, String destPath) {
        return new File(sourcePath).renameTo(new File(destPath));
    }

    public static String getFileNameNoExtension(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return "";
        }

        int lastDot = filePath.lastIndexOf('.');
        int lastSeparator = filePath.lastIndexOf(File.separator);

        if (lastSeparator == -1) {
            return (lastDot == -1 ? filePath : filePath.substring(0, lastDot));
        } else if (lastDot == -1 || lastSeparator > lastDot) {
            return filePath.substring(lastSeparator + 1);
        }
        return filePath.substring(lastSeparator + 1, lastDot);
    }

    public static String getFileExtension(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "";
        }

        int lastDot = filePath.lastIndexOf('.');
        int lastSeparator = filePath.lastIndexOf(File.separator);

        if (lastDot == -1 || lastSeparator >= lastDot) {
            return "";
        }
        return filePath.substring(lastDot + 1);
    }

    private static void createNewFile(String path) {
        if (path == null || path.isEmpty()) {
            return;
        }

        int lastSeparator = path.lastIndexOf(File.separator);
        if (lastSeparator > 0) {
            String dirPath = path.substring(0, lastSeparator);
            makeDir(dirPath);
        }

        File file = new File(path);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            Log.e("FileUtil", "Error creating file: " + path, e);
        }
    }

    public static String readFile(String path) {
        if (path == null || path.isEmpty()) {
            return "";
        }

        createNewFile(path);

        StringBuilder content = new StringBuilder();
        try (FileReader reader = new FileReader(path)) {
            char[] buffer = new char[1024];
            int length;

            while ((length = reader.read(buffer)) > 0) {
                content.append(buffer, 0, length);
            }
        } catch (IOException e) {
            Log.e("FileUtil", "Error reading file: " + path, e);
        }

        return content.toString();
    }

    public static void writeFile(String path, String content) {
        if (path == null || path.isEmpty()) {
            return;
        }

        createNewFile(path);

        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(content != null ? content : "");
            writer.flush();
        } catch (IOException e) {
            Log.e("FileUtil", "Error writing to file: " + path, e);
        }
    }

    public static void copyFile(String sourcePath, String destPath) {
        if (!isExistFile(sourcePath)) {
            return;
        }

        createNewFile(destPath);

        try (FileInputStream inputStream = new FileInputStream(sourcePath);
             FileOutputStream outputStream = new FileOutputStream(destPath, false)) {
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            Log.e("FileUtil", "Error copying file from " + sourcePath + " to " + destPath, e);
        }
    }

    public static void copyDirectory(File source, File destination) throws IOException {
        if (!source.isDirectory()) {
            File parent = destination.getParentFile();
            if (parent == null || parent.exists() || parent.mkdirs()) {
                try (FileInputStream input = new FileInputStream(source);
                     FileOutputStream output = new FileOutputStream(destination)) {
                    byte[] buffer = new byte[2048];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) > 0) {
                        output.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                throw new IOException("Cannot create directory: " + parent.getAbsolutePath());
            }
        } else if (destination.exists() || destination.mkdirs()) {
            String[] files = source.list();
            if (files != null) {
                for (String file : files) {
                    copyDirectory(new File(source, file), new File(destination, file));
                }
            }
        } else {
            throw new IOException("Cannot create directory: " + destination.getAbsolutePath());
        }
    }

    public static void extractFileFromZip(InputStream inputStream, File outputFile) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void moveFile(String sourcePath, String destPath) {
        copyFile(sourcePath, destPath);
        deleteFile(sourcePath);
    }

    public static void deleteFile(String path) {
        if (path == null || path.isEmpty()) {
            return;
        }

        File file = new File(path);
        if (!file.exists()) {
            return;
        }

        if (file.isFile()) {
            file.delete();
            return;
        }

        File[] files = file.listFiles();
        if (files != null) {
            for (File subFile : files) {
                if (subFile.isDirectory()) {
                    deleteFile(subFile.getAbsolutePath());
                }
                if (subFile.isFile()) {
                    subFile.delete();
                }
            }
        }

        file.delete();
    }

    public static boolean isExistFile(String path) {
        return path != null && !path.isEmpty() && new File(path).exists();
    }

    public static void makeDir(String path) {
        if (path != null && !path.isEmpty() && !isExistFile(path)) {
            new File(path).mkdirs();
        }
    }

    public static void listDir(String path, List<String> fileList) {
        if (path == null || path.isEmpty() || fileList == null) {
            return;
        }

        File directory = new File(path);
        if (!directory.exists() || directory.isFile()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return;
        }

        fileList.clear();
        for (File file : files) {
            fileList.add(file.getAbsolutePath());
        }
    }

    public static List<String> listFiles(String directory, String extension) {
        List<String> allFiles = new ArrayList<>();
        List<String> filteredFiles = new ArrayList<>();
        
        listDir(directory, allFiles);
        
        for (String filePath : allFiles) {
            if (filePath.endsWith(extension) && isFile(filePath)) {
                filteredFiles.add(filePath);
            }
        }
        
        return filteredFiles;
    }

    public static boolean isDirectory(String path) {
        return isExistFile(path) && new File(path).isDirectory();
    }

    public static boolean isFile(String path) {
        return isExistFile(path) && new File(path).isFile();
    }

    public static long getFileLength(String path) {
        return isExistFile(path) ? new File(path).length() : 0;
    }

    public static String getExternalStorageDir() {
        return "/storage/emulated/0/";
    }

    public static String getPackageDataDir(Context context) {
        return context.getExternalFilesDir(null).getAbsolutePath();
    }

    public static String getPublicDir(String type) {
        return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();
    }

    public static String convertUriToFilePath(Context context, Uri uri) {
        if (context == null || uri == null) {
            return null;
        }

        String path = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    path = "/storage/emulated/0/" + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                String id = DocumentsContract.getDocumentId(uri);

                if (!TextUtils.isEmpty(id)) {
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }
                }

                Uri contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), 
                    Long.parseLong(id)
                );
                path = getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                String selection = "_id=?";
                String[] selectionArgs = new String[]{split[1]};
                path = getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {
            path = getDataColumn(context, uri, null, null);
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }

        if (path != null) {
            try {
                return URLDecoder.decode(path, "UTF-8");
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            Log.e("FileUtil", "Error getting data column", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static void saveBitmap(Bitmap bitmap, String destPath) {
        createNewFile(destPath);

        try (FileOutputStream outputStream = new FileOutputStream(destPath)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (IOException e) {
            Log.e("FileUtil", "Error saving bitmap to " + destPath, e);
        }
    }

    public static Bitmap getScaledBitmap(String imagePath, int maxSize) {
        Bitmap source = BitmapFactory.decodeFile(imagePath);
        if (source == null) {
            return null;
        }

        int width = source.getWidth();
        int height = source.getHeight();
        int newWidth, newHeight;

        if (width > height) {
            newWidth = maxSize;
            newHeight = height * maxSize / width;
        } else {
            newHeight = maxSize;
            newWidth = width * maxSize / height;
        }

        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampleBitmapFromPath(String path, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static void resizeBitmapFileRetainRatio(String sourcePath, String destPath, int maxSize) {
        if (isExistFile(sourcePath)) {
            Bitmap scaled = getScaledBitmap(sourcePath, maxSize);
            if (scaled != null) {
                saveBitmap(scaled, destPath);
            }
        }
    }

    public static void resizeBitmapFileToSquare(String sourcePath, String destPath, int size) {
        if (isExistFile(sourcePath)) {
            Bitmap source = BitmapFactory.decodeFile(sourcePath);
            if (source != null) {
                Bitmap scaled = Bitmap.createScaledBitmap(source, size, size, true);
                saveBitmap(scaled, destPath);
            }
        }
    }

    public static void resizeBitmapFileToCircle(String sourcePath, String destPath) {
        if (!isExistFile(sourcePath)) {
            return;
        }

        Bitmap source = BitmapFactory.decodeFile(sourcePath);
        if (source == null) {
            return;
        }

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());
        
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xFF424242);
        canvas.drawCircle(source.getWidth() / 2f, source.getHeight() / 2f, source.getWidth() / 2f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, rect, rect, paint);
        
        saveBitmap(output, destPath);
    }

    public static void resizeBitmapFileWithRoundedBorder(String sourcePath, String destPath, int radius) {
        if (!isExistFile(sourcePath)) {
            return;
        }

        Bitmap source = BitmapFactory.decodeFile(sourcePath);
        if (source == null) {
            return;
        }

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());
        RectF rectF = new RectF(rect);
        
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xFF424242);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, rect, rect, paint);
        
        saveBitmap(output, destPath);
    }

    public static void cropBitmapFileFromCenter(String sourcePath, String destPath, int width, int height) {
        if (!isExistFile(sourcePath)) {
            return;
        }

        Bitmap source = BitmapFactory.decodeFile(sourcePath);
        if (source == null) {
            return;
        }

        int srcWidth = source.getWidth();
        int srcHeight = source.getHeight();

        if (srcWidth < width && srcHeight < height) {
            return;
        }

        int x = 0;
        int y = 0;

        if (srcWidth > width) {
            x = (srcWidth - width) / 2;
        }

        if (srcHeight > height) {
            y = (srcHeight - height) / 2;
        }

        int cropWidth = Math.min(width, srcWidth);
        int cropHeight = Math.min(height, srcHeight);

        Bitmap cropped = Bitmap.createBitmap(source, x, y, cropWidth, cropHeight);
        saveBitmap(cropped, destPath);
    }

    public static void rotateBitmapFile(String sourcePath, String destPath, float angle) {
        if (!isExistFile(sourcePath)) {
            return;
        }

        Bitmap source = BitmapFactory.decodeFile(sourcePath);
        if (source == null) {
            return;
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap rotated = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        saveBitmap(rotated, destPath);
    }

    public static void scaleBitmapFile(String sourcePath, String destPath, float scaleX, float scaleY) {
        if (!isExistFile(sourcePath)) {
            return;
        }

        Bitmap source = BitmapFactory.decodeFile(sourcePath);
        if (source == null) {
            return;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        Bitmap scaled = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        saveBitmap(scaled, destPath);
    }

    public static void skewBitmapFile(String sourcePath, String destPath, float skewX, float skewY) {
        if (!isExistFile(sourcePath)) {
            return;
        }

        Bitmap source = BitmapFactory.decodeFile(sourcePath);
        if (source == null) {
            return;
        }

        Matrix matrix = new Matrix();
        matrix.postSkew(skewX, skewY);
        Bitmap skewed = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        saveBitmap(skewed, destPath);
    }

    public static void setBitmapFileColorFilter(String sourcePath, String destPath, int color) {
        if (!isExistFile(sourcePath)) {
            return;
        }

        Bitmap source = BitmapFactory.decodeFile(sourcePath);
        if (source == null) {
            return;
        }

        Bitmap output = Bitmap.createBitmap(source, 0, 0, source.getWidth() - 1, source.getHeight() - 1);
        Paint paint = new Paint();
        paint.setColorFilter(new LightingColorFilter(color, 1));
        
        Canvas canvas = new Canvas(output);
        canvas.drawBitmap(output, 0f, 0f, paint);
        
        saveBitmap(output, destPath);
    }

    public static void setBitmapFileBrightness(String sourcePath, String destPath, float brightness) {
        if (!isExistFile(sourcePath)) {
            return;
        }

        Bitmap source = BitmapFactory.decodeFile(sourcePath);
        if (source == null) {
            return;
        }

        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
            1, 0, 0, 0, brightness,
            0, 1, 0, 0, brightness,
            0, 0, 1, 0, brightness,
            0, 0, 0, 1, 0
        });

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), source.getConfig());
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(source, 0, 0, paint);
        
        saveBitmap(output, destPath);
    }

    public static void setBitmapFileContrast(String sourcePath, String destPath, float contrast) {
        if (!isExistFile(sourcePath)) {
            return;
        }

        Bitmap source = BitmapFactory.decodeFile(sourcePath);
        if (source == null) {
            return;
        }

        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
            contrast, 0, 0, 0, 0,
            0, contrast, 0, 0, 0,
            0, 0, contrast, 0, 0,
            0, 0, 0, 1, 0
        });

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), source.getConfig());
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(source, 0, 0, paint);
        
        saveBitmap(output, destPath);
    }

    public static int getJpegRotation(String filePath) {
        try {
            ExifInterface exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            return 0;
        }
    }

    public static File createNewPictureFile(Context context) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String fileName = timeStamp + ".jpg";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        return new File(storageDir, fileName);
    }

    public static byte[] readFromInputStream(InputStream inputStream) {
        int available;
        try {
            available = inputStream.available();
        } catch (IOException e) {
            available = 0;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[available];

        try {
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            return new byte[0];
        }

        return outputStream.toByteArray();
    }

    public static void writeBytes(File target, byte[] data) throws IOException {
        if (!target.exists()) {
            target.getParentFile().mkdirs();
        }
        
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(target))) {
            outputStream.write(data);
            outputStream.flush();
        }
    }

    public static void extractZipTo(ZipInputStream zipInputStream, String outputPath) throws IOException {
        File outputDir = new File(outputPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        ZipEntry entry;
        while ((entry = zipInputStream.getNextEntry()) != null) {
            String entryPath = new File(outputPath, entry.getName()).getAbsolutePath();

            if (!entry.isDirectory()) {
                new File(entryPath).getParentFile().mkdirs();
                writeBytes(new File(entryPath), readFromInputStream(zipInputStream));
            }
            zipInputStream.closeEntry();
        }
        zipInputStream.close();
    }

    public static void requestAllFilesAccessPermission(Context context) {
        if (Build.VERSION.SDK_INT > 29) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Log.e("FileUtil", "Activity to manage all files access permission not found", e);
                }
            }
        } else {
            throw new IllegalStateException("Not on an API level 30 or higher device");
        }
    }

    public static boolean isImageFile(String path) {
        if (path == null || path.isEmpty()) {
            return false;
        }
        
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }
}