package com.nexusteam.internal.os.layouteditor.widget;

import android.graphics.Paint;

public interface WidgetContract {
    void setWidgetId(String id);
    String getWidgetId();

    void setWidgetName(String name);
    String getWidgetName();

    Paint getWidgetPaint();

    void select();
    void unselect();

    String newWidgetId();
}
