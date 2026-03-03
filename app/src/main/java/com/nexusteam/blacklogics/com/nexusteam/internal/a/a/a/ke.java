package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ke {
    public static Toast a(Context context, CharSequence charSequence, int i, int i2, float f, float f2) {
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.custom_toast, (ViewGroup) null);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.custom_toast_container);
        ((TextView) inflate.findViewById(R.id.tv_stoast)).setText(charSequence.toString());
        Toast toast = new Toast(context);
        toast.setDuration(i);
        toast.setGravity(i2, (int) f, (int) f2);
        toast.setView(inflate);
        return toast;
    }

    public static Toast a(Context context, CharSequence charSequence, int i, int i2, float f, float f2, int i3) {
        try {
            View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.custom_toast, (ViewGroup) null);
            LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.custom_toast_container);
            TextView textView = (TextView) inflate.findViewById(R.id.tv_stoast);
            if (i3 != 1) {
                linearLayout.setBackgroundResource(R.drawable.bg_toast_normal);
                textView.setTextColor(context.getResources().getColor(R.color.scolor_black_01));
            } else {
                linearLayout.setBackgroundResource(R.drawable.bg_toast_warning);
                textView.setTextColor(context.getResources().getColor(R.color.scolor_red_02));
            }
            textView.setText(charSequence.toString());
            Toast toast = new Toast(context);
            toast.setDuration(i);
            toast.setGravity(i2, (int) kp.a(context, f), (int) kp.a(context, f2));
            toast.setView(inflate);
            return toast;
        } catch (Exception unused) {
            return Toast.makeText(context, charSequence, i);
        }
    }

    public static Toast a(Context context, int i, int i2) {
        return a(context, kq.a().a(context, i), i2, 48, 0.0f, 64.0f, 0);
    }

    public static Toast a(Context context, CharSequence charSequence, int i) {
        return a(context, charSequence, i, 48, 0.0f, 64.0f, 0);
    }

    public static Toast b(Context context, int i, int i2) {
        return a(context, kq.a().a(context, i), i2, 48, 0.0f, 64.0f, 1);
    }

    public static Toast b(Context context, CharSequence charSequence, int i) {
        return a(context, charSequence, i, 48, 0.0f, 64.0f, 1);
    }
}
