package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.DisplayMetrics;
import com.google.android.gms.common.GoogleApiAvailability;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Locale;

public class ky {
    public static String a(Context context) {
        return "";
    }

    public static long a() {
        try {
            return new StatFs("/storage/emulated/0/").getFreeBytes() / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
        } catch (Exception unused) {
            return -1;
        }
    }

    public static void a(Context context, int i) {
        String className = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", i);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", className);
        context.sendBroadcast(intent);
    }

    public static String b() {
        Field[] fields = Build.VERSION_CODES.class.getFields();
        int length = fields.length;
        int i = 0;
        while (i < length) {
            Field field = fields[i];
            String name = field.getName();
            try {
                if (field.getInt(new Object()) == Build.VERSION.SDK_INT) {
                    return name;
                }
                i++;
            } catch (Exception unused) {
                return "";
            }
        }
        return "";
    }

    public static int b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String c(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean d(Context context) {
        int[] iArr = {0, 1};
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            for (int i : iArr) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.getType() == i) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean a(Activity activity) {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(activity);
        if (isGooglePlayServicesAvailable == 0) {
            return true;
        }
        instance.isUserResolvableError(isGooglePlayServicesAvailable);
        return false;
    }

    public static ArrayList<String> e(Context context) {
        ArrayList<String> arrayList = new ArrayList<>();
        Account[] accountsByType = AccountManager.get(context).getAccountsByType("com.google");
        for (int i = 0; i < accountsByType.length; i++) {
            if (accountsByType[i].type.equals("com.google")) {
                arrayList.add(accountsByType[i].name);
            }
        }
        return arrayList;
    }

    public static float[] b(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return new float[]{displayMetrics.xdpi, displayMetrics.ydpi};
    }

    public static int[] c(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

    public static Locale f(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.getResources().getConfiguration().getLocales().get(0);
        }
        return context.getResources().getConfiguration().locale;
    }

    public static String c() {
        if (Build.VERSION.SDK_INT < 21) {
            return Build.CPU_ABI;
        }
        String[] strArr = Build.SUPPORTED_ABIS;
        if (strArr != null) {
            return strArr[0];
        }
        return null;
    }

    public static int g(Context context) {
        return (int) kp.a(context, 48.0f);
    }

    public static int h(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static boolean b(Context context, int i) {
        return ((SensorManager) context.getSystemService("sensor")).getDefaultSensor(i) != null;
    }
}
