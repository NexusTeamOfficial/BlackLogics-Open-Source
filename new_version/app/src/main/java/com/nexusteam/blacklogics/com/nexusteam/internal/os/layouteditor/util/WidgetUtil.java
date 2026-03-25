package com.nexusteam.internal.os.layouteditor.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nexusteam.internal.os.layouteditor.widget.WidgetButton;
import com.nexusteam.internal.os.layouteditor.widget.WidgetTextView;
import java.util.ArrayList;
import com.nexusteam.internal.os.layouteditor.*;
import com.nexusteam.blacklogics.R;
import com.besome.blacklogics.*;
import com.nexusteam.blacklogics.*;

import com.nexusteam.internal.os.layouteditor.widget.*;
import com.nexusteam.internal.os.layouteditor.model.*;

public class WidgetUtil {
    public static final String WIDGET_TYPE_BUTTON = "Button";
    public static final String WIDGET_TYPE_SOURCE = "Source";
    public static final String WIDGET_TYPE_TEXT_VIEW = "TextView";
    public static final String WIDGET_TY0E_IMAGE_VIEW = "ImageView";
    public static final String WIDGET_TYPE_LINEAR_LAYOUT = "LinearLayout";
    public static final String WIDGET_TYPE_WEB_VIEW = "WebView";
    public static final String WIDGET_TYPE_LIST_VIEW = "ListView";
    public static final String WIDGET_TYPE_CODE_VIEWER = "CodeViewer";

    public static int setTextSize(View selectedWidget, String toString)
    {
        return 0;
    }
    
    public static int getWidgetIconId(String str) {
        if (WIDGET_TYPE_TEXT_VIEW.equals(str)) {
            return 0;
        }
        if (WIDGET_TYPE_BUTTON.equals(str)) {
            return 1;
        }
        if (WIDGET_TY0E_IMAGE_VIEW.equals(str)) {
            return 2;
        }
        if (WIDGET_TYPE_LINEAR_LAYOUT.equals(str)) {
            return 3;
        }
        if (WIDGET_TYPE_WEB_VIEW.equals(str)) {
            return 4;
        }
        if (WIDGET_TYPE_LIST_VIEW.equals(str)) {
            return 5;
        }
        if (WIDGET_TYPE_CODE_VIEWER.equals(str)) {
            return 6;
        }
        return 0;
    }

    public static boolean isWidgetIdExist(String str) {
        for (int i = 0; i < ViewEditorFragmentActivity.ll.getChildCount(); i++) {
            View childAt = ViewEditorFragmentActivity.ll.getChildAt(i);
            if (childAt != ViewEditorFragmentActivity.view_location && str.equals(getWidgetId(((ViewGroup) childAt).getChildAt(0)))) {
                return true;
            }
        }
        return false;
    }

    public static String getWidgetId(View view) {
        if (view instanceof WidgetTextView) {
            return ((WidgetTextView) view).getWidgetId();
        }
        if (view instanceof WidgetButton) {
            return ((WidgetButton) view).getWidgetId();
        }
        if (view instanceof WidgetImageView) {
            return ((WidgetImageView) view).getWidgetId();
        }
        if (view instanceof WidgetWebView) {
            return ((WidgetWebView) view).getWidgetId();
        }
        if (view instanceof WidgetListView) {
            return ((WidgetListView) view).getWidgetId();
        }
        if (view instanceof WidgetCodeViewer) {
            return ((WidgetCodeViewer) view).getWidgetId();
        }
        return (String) null;
    }
    
    public static String getTextStyle(int i) {
        int i2 = i;
        if (3 == i2) {
            return "bold|italic";
        }
        if (1 == i2) {
            return "bold";
        }
        if (2 == i2) {
            return "italic";
        }
        return "normal";
    }
    
    public static String getWidgetType(View view) {
        if (view instanceof WidgetTextView) {
            return WIDGET_TYPE_TEXT_VIEW;
        }
        if (view instanceof WidgetButton) {
            return WIDGET_TYPE_BUTTON;
        }
        if (view instanceof WidgetImageView) {
            return WIDGET_TY0E_IMAGE_VIEW;
        }
        if (view instanceof WidgetWebView) {
            return WIDGET_TYPE_WEB_VIEW;
        }
        if (view instanceof WidgetListView) {
            return WIDGET_TYPE_LIST_VIEW;
        }
        if (view instanceof WidgetCodeViewer) {
            return WIDGET_TYPE_CODE_VIEWER;
        }
        return (String) null;
    }

    public static TextView getTextViewOfWidget(View view) {
        if (view instanceof WidgetTextView) {
            return ((WidgetTextView) view).getTextView();
        }
        if (view instanceof WidgetButton) {
            return ((WidgetButton) view).getTextView();
        }
        return (TextView) null;
    }
    
    public static void setWidgetId(View view, String str) {
        if (view instanceof WidgetTextView) {
            ((WidgetTextView) view).setWidgetId(str);
        }
        if (view instanceof WidgetButton) {
            ((WidgetButton) view).setWidgetId(str);
        }
        if (view instanceof WidgetImageView) {
            ((WidgetImageView) view).setWidgetId(str);
        }
        if (view instanceof WidgetLinear) {
            ((WidgetLinear) view).setWidgetId(str);
        }
        if (view instanceof WidgetWebView) {
            ((WidgetWebView) view).setWidgetId(str);
        }
        if (view instanceof WidgetListView) {
            ((WidgetListView) view).setWidgetId(str);
        }
        if (view instanceof WidgetCodeViewer) {
            ((WidgetCodeViewer) view).setWidgetId(str);
        }
    }
    
    public static boolean containsWidgetInPhone(int i) {
        for (int i2 = 0; i2 < ViewEditorFragmentActivity.ll.getChildCount(); i2++) {
            View childAt = ((ViewGroup) ViewEditorFragmentActivity.ll.getChildAt(i2)).getChildAt(0);
            if (i == 0 && (childAt instanceof WidgetTextView)) {
                return true;
            }
            if (i == 1 && (childAt instanceof WidgetButton)) {
                return true;
            }
            if (i == 2 && (childAt instanceof WidgetImageView)) {
                return true;
            }
            if (i == 3 && (childAt instanceof WidgetLinear)) {
                return true;
            }
            if (i == 4 && (childAt instanceof WidgetWebView)) {
                return true;
            }
            if (i == 5 && (childAt instanceof WidgetListView)) {
                return true;
            }
            if (i == 6 && (childAt instanceof WidgetCodeViewer)) {
                return true;
            }
        }
        return false;
    }
    
    public static String getImagePath(View widget) {
        if (widget instanceof WidgetImageView) {
           return ((WidgetImageView) widget).getImagePath();
        }
         return "";
    }

    public static void setImagePath(View widget, String imagePath) {
        if (widget instanceof WidgetImageView) {
           ((WidgetImageView) widget).setImagePath(imagePath);
        }
    }
}