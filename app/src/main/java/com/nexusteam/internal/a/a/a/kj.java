package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.android.sdklib.repository.remote.RemoteSdk;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class kj {
    public String a(long j, String str) {
        return new SimpleDateFormat(str, Locale.ENGLISH).format(new Date(j));
    }

    public long a(String str, String str2) {
        try {
            return new SimpleDateFormat(str2, Locale.ENGLISH).parse(str).getTime();
        } catch (ParseException unused) {
            return 0;
        }
    }

    public int b(String str, String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2, Locale.ENGLISH);
        Calendar instance = Calendar.getInstance();
        instance.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            instance.setTime(simpleDateFormat.parse(str));
            long timeInMillis = instance.getTimeInMillis();
            instance.setTime(new Date());
            return (int) ((timeInMillis - instance.getTimeInMillis()) / RemoteSdk.DEFAULT_EXPIRATION_PERIOD_MS);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String a(String str) {
        try {
            return new SimpleDateFormat(str).format(new Date());
        } catch (Exception unused) {
            return null;
        }
    }

    public String b(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            return simpleDateFormat.format(new Date());
        } catch (Exception unused) {
            return null;
        }
    }

    public String c(String str, String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2);
        try {
            long time = simpleDateFormat.parse(str).getTime();
            Date date = new Date();
            date.setTime(time + ((long) TimeZone.getDefault().getOffset(time)));
            return simpleDateFormat.format(date);
        } catch (Exception unused) {
            return str;
        }
    }

    public String a(String str, String str2, String str3) {
        try {
            long time = new SimpleDateFormat(str2).parse(str).getTime();
            Date date = new Date();
            date.setTime(time + ((long) TimeZone.getDefault().getOffset(time)));
            return new SimpleDateFormat(str3).format(date);
        } catch (Exception unused) {
            return str;
        }
    }

    public long a() {
        return new Date().getTime();
    }
}
