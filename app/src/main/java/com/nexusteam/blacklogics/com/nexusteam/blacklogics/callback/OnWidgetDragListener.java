package com.nexusteam.blacklogics.callback;

import android.view.View;
import java.util.HashMap;

public interface OnWidgetDragListener {

    void onWidgetLongPressed(
            View dragView,
            HashMap<String, Object> widgetData,
            int position
    );
}
