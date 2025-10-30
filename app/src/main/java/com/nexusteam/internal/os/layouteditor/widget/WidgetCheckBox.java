package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import com.besome.blacklogics.R;
import com.besome.blacklogics.*;

import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetCheckBox extends Widget {
    private CheckBox mCheckBox;

    public WidgetCheckBox(Context context) {
        super(context);
        mCheckBox = new CheckBox(context);
        mCheckBox.setText("CheckBox");
        addView(mCheckBox);
    }

    //@Override
    public void setLayoutParams(LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
        mCheckBox.setLayoutParams(layoutParams);
    }

    public static String newWidgetId() {
        int i = 1;
        while (WidgetUtil.isWidgetIdExist("checkbox" + i)) {
            i++;
        }
        return "checkbox" + i;
    }
}
