
package com.nexusteam.blacklogics.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.apk.builder.ApplicationLoader;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlackLogicsUtil {
    
    public static final int TOP = 1;
    public static final int CENTER = 2;
    public static final int BOTTOM = 3;
    
    private BlackLogicsUtil() {

    }
    
    public static void showCustomToast(String message, int textColor, int textSize, 
    int bgColor, int radius, int gravity) {
        if (message == null || message.isEmpty()) {
            return;
        }
        
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        View view = toast.getView();
        
        if (view != null) {
            TextView textView = view.findViewById(android.R.id.message);
            if (textView != null) {
                textView.setTextSize(textSize);
                textView.setTextColor(textColor);
                textView.setGravity(Gravity.CENTER);
            }
            
            GradientDrawable background = new GradientDrawable();
            background.setColor(bgColor);
            background.setCornerRadius(radius);
            view.setBackground(background);
            view.setPadding(15, 10, 15, 10);
            
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                view.setElevation(10);
            }
        }
        
        switch (gravity) {
            case TOP:
            toast.setGravity(Gravity.TOP, 0, 150);
            break;
            case CENTER:
            toast.setGravity(Gravity.CENTER, 0, 0);
            break;
            case BOTTOM:
            toast.setGravity(Gravity.BOTTOM, 0, 150);
            break;
        }
        
        toast.show();
    }
    
    public static void sortListMap(final List<Map<String, Object>> listMap, final String key, 
    final boolean isNumber, final boolean ascending) {
        if (listMap == null || listMap.isEmpty() || key == null) {
            return;
        }
        
        Collections.sort(listMap, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                Object value1 = map1.get(key);
                Object value2 = map2.get(key);
                
                if (value1 == null || value2 == null) {
                    return 0;
                }
                
                if (isNumber) {
                    try {
                        double num1 = Double.parseDouble(value1.toString());
                        double num2 = Double.parseDouble(value2.toString());
                        if (ascending) {
                            return Double.compare(num1, num2);
                        } else {
                            return Double.compare(num2, num1);
                        }
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                } else {
                    String str1 = value1.toString();
                    String str2 = value2.toString();
                    if (ascending) {
                        return str1.compareTo(str2);
                    } else {
                        return str2.compareTo(str1);
                    }
                }
            }
        });
    }
    
    public static boolean isNetworkConnected() {
        Context context = getApplicationContext();
        if (context == null) {
            return false;
        }
        
        ConnectivityManager connectivityManager = 
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if (connectivityManager == null) {
            return false;
        }
        
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
    
    public static String readInputStreamToString(InputStream inputStream) {
        if (inputStream == null) {
            return "";
        }
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            return outputStream.toString("UTF-8");
        } catch (IOException e) {
            Log.e("BlackLogicsUtil", "Error reading input stream", e);
            return "";
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                Log.e("BlackLogicsUtil", "Error closing streams", e);
            }
        }
    }
    
    public static void hideKeyboard(View view) {
        if (view == null) {
            return;
        }
        
        Context context = getApplicationContext();
        InputMethodManager inputMethodManager = 
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    
    public static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        
        View view = activity.getCurrentFocus();
        if (view != null) {
            hideKeyboard(view);
        }
    }
    
    public static void showKeyboard(View view) {
        if (view == null) {
            return;
        }
        
        Context context = getApplicationContext();
        InputMethodManager inputMethodManager = 
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
    
    public static void showMessage(Context context, String message) {
        if (context == null || message == null || message.isEmpty()) {
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    
    public static void showToast(Context context, String message) {
        showMessage(context, message);
    }
    
    public static int getRandomNumber(int min, int max) {
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }
        
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
    
    public static List<Integer> getCheckedItemPositions(ListView listView) {
        List<Integer> checkedPositions = new ArrayList<>();
        
        if (listView == null) {
            return checkedPositions;
        }
        
        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
        for (int i = 0; i < checkedItems.size(); i++) {
            if (checkedItems.valueAt(i)) {
                checkedPositions.add(checkedItems.keyAt(i));
            }
        }
        
        return checkedPositions;
    }
    
    public static float dipToPixels(float dipValue) {
        Context context = getApplicationContext();
        return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 
        dipValue, 
        context.getResources().getDisplayMetrics()
        );
    }
    
    public static int dipToPixelsInt(float dipValue) {
        return (int) dipToPixels(dipValue);
    }
    
    public static int getScreenWidth() {
        Context context = getApplicationContext();
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    
    public static int getScreenHeight() {
        Context context = getApplicationContext();
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    
    public static List<String> getMapKeys(Map<String, Object> map) {
        List<String> keys = new ArrayList<>();
        
        if (map == null || map.isEmpty()) {
            return keys;
        }
        
        keys.addAll(map.keySet());
        return keys;
    }
    
    public static void showToastMessage(String message, int duration) {
        Context context = getApplicationContext();
        if (context != null && message != null && !message.isEmpty()) {
            Toast.makeText(context, message, duration).show();
        }
    }
    
    public static void showToastMessage(String message) {
        showToastMessage(message, Toast.LENGTH_SHORT);
    }
    
    public static void showErrorToast(String message, int d) {
        Context context = getApplicationContext();
        if (context == null || message == null || message.isEmpty()) return;
        
        Toast toast = Toast.makeText(context, message, d);
        View view = toast.getView();
        
        if (view != null) {
            TextView text = view.findViewById(android.R.id.message);
            if (text != null) {
                text.setTextColor(0xFFFFFFFF);
                text.setTextSize(14);
                text.setGravity(Gravity.CENTER);
                text.setPadding(
                dipToPixelsInt(16),
                dipToPixelsInt(10),
                dipToPixelsInt(16),
                dipToPixelsInt(10)
                );
            }
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(0xFF2A2A2A);
            bg.setCornerRadius(dipToPixelsInt(16));
            bg.setStroke(dipToPixelsInt(1.5f), 0xFFFF4444); // red border
            
            view.setBackground(bg);
        }
        
        toast.setGravity(Gravity.BOTTOM, 0, dipToPixelsInt(72));
        toast.show();
    }
    
    
    public static void toast(String message, int d) {
        Context context = getApplicationContext();
        if (context == null || message == null || message.isEmpty()) return;
        
        Toast toast = Toast.makeText(context, message, d);
        View view = toast.getView();
        
        if (view != null) {
            TextView text = view.findViewById(android.R.id.message);
            if (text != null) {
                text.setTextColor(0xFFFFFFFF);
                text.setTextSize(14);
                text.setGravity(Gravity.CENTER);
                text.setPadding(
                dipToPixelsInt(16),
                dipToPixelsInt(10),
                dipToPixelsInt(16),
                dipToPixelsInt(10)
                );
            }
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(0xFF323232); // material dark
            bg.setCornerRadius(dipToPixelsInt(16));
            
            view.setBackground(bg);
        }
        
        toast.setGravity(Gravity.BOTTOM, 0, dipToPixelsInt(72));
        toast.show();
    }
    
    
    public static void toast(String message) {
        toast(message, Toast.LENGTH_SHORT);
    }
    
    public static void showErrorToast(String message) {
        showErrorToast(message, Toast.LENGTH_SHORT);
    }
    
    public static void toastError(String message) {
        showErrorToast(message, Toast.LENGTH_SHORT);
    }    
    
    public static void toastError(String message, int duration) {
        showErrorToast(message, duration);
    }    
    
    public static int dipToPixel(float dp) {
        return dipToPixelsInt(dp);
    }
    
    public static int dipToSp(float dp) {
        float pixels = dipToPixels(dp);
        Context context = getApplicationContext();
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pixels / scaledDensity);
    }
    
    public static String getSafDocumentDisplayName(Uri uri) {
        return executeSingleStringQuery(uri, DocumentsContract.Document.COLUMN_DISPLAY_NAME);
    }
    
    public static String executeSingleStringQuery(Uri uri, String columnName) {
        if (uri == null || columnName == null) {
            return null;
        }
        
        Context context = getApplicationContext();
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                uri, new String[]{columnName}, null, null, null);
            
            if (cursor != null && cursor.moveToFirst() && !cursor.isNull(0)) {
                return cursor.getString(0);
            }
        } catch (Exception e) {
            Log.e("BlackLogicsUtil", "Failed to execute query for Uri: " + uri, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        
        return null;
    }
    
    public static void copySafDocumentToTempFile(final Uri documentUri, final Activity activity, 
    final String fileExtension, 
    final DocumentCopyCallback callback) {
        if (documentUri == null || activity == null || callback == null) {
            return;
        }
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                ParcelFileDescriptor fileDescriptor = null;
                FileInputStream inputStream = null;
                FileOutputStream outputStream = null;
                BufferedOutputStream bufferedStream = null;
                
                try {
                    fileDescriptor = activity.getContentResolver().openFileDescriptor(documentUri, "r");
                    
                    if (fileDescriptor == null) {
                        throw new IOException("Failed to open file descriptor");
                    }
                    
                    inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
                    
                    File tempFile = File.createTempFile("document", "." + fileExtension);
                    
                    outputStream = new FileOutputStream(tempFile);
                    bufferedStream = new BufferedOutputStream(outputStream);
                    
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) > 0) {
                        bufferedStream.write(buffer, 0, bytesRead);
                    }
                    
                    final File finalTempFile = tempFile;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(finalTempFile);
                        }
                    });
                    
                } catch (final IOException e) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(e);
                        }
                    });
                } finally {
                    try {
                        if (bufferedStream != null) bufferedStream.close();
                        if (outputStream != null) outputStream.close();
                        if (inputStream != null) inputStream.close();
                        if (fileDescriptor != null) fileDescriptor.close();
                    } catch (IOException e) {
                        Log.e("BlackLogicsUtil", "Error closing streams", e);
                    }
                }
            }
        }).start();
    }
    
    public static int dpToPx(Activity activity, float dp) {
        if (activity == null) {
            return 0;
        }
        
        return (int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        activity.getResources().getDisplayMetrics()
        );
    }
    
    public static void showNotification(Context context, 
    String title, 
    String message) {
        
        if (context == null) return;
        
        String CHANNEL_ID = "blacklogics_channel";
        
        NotificationManager manager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
            CHANNEL_ID,
            "BlackLogics Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("BlackLogics app notifications");
            manager.createNotificationChannel(channel);
        }
        
        Notification notification =
        new NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle(title)
        .setContentText(message)
        .setAutoCancel(true)
        .build();
        
        manager.notify((int) System.currentTimeMillis(), notification);
    }
    
    public static boolean openUrl(Context context, String url) {
        if (context == null || url == null || url.isEmpty()) {
            return false;
        }
        
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            Log.e("BlackLogicsUtil", "No activity found to handle URL: " + url, e);
            return false;
        }
    }
    
    public static void hideKeyboard() {
        Activity activity = (Activity) getApplicationContext();
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    
    public static float getDip(int input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, input, getApplicationContext().getResources().getDisplayMetrics());
    }
    
    public static int getDip(Context context, int dipValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * density);
    }
    
    private static Context getApplicationContext() {
        return ApplicationLoader.getContext(); //
    }
    
    public interface DocumentCopyCallback {
        void onSuccess(File tempFile);
        void onError(IOException exception);
    }
}