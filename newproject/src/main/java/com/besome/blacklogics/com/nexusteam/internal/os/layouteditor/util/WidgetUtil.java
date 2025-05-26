package com.nexusteam.internal.os.layouteditor.util;

import android.view.View;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.BitmapFactory;
import com.nexusteam.internal.os.layouteditor.widget.*;
import com.besome.blacklogics.ViewEditorFragmentActivity;
import java.io.File;

public class WidgetUtil {
    public static final String WIDGET_TYPE_BUTTON = "Button";
    public static final String WIDGET_TYPE_SOURCE = "Source";
    public static final String WIDGET_TYPE_TEXT_VIEW = "TextView";
    public static final String WIDGET_TYPE_EDIT_TEXT = "EditText";
    public static final String WIDGET_TYPE_IMAGE_VIEW = "ImageView";
    public static final String WIDGET_TYPE_LINEAR_LAYOUT = "LinearLayout";
    public static final String WIDGET_TYPE_WEB_VIEW = "WebView";
    public static final String WIDGET_TYPE_LIST_VIEW = "ListView";
    public static final String WIDGET_TYPE_CODE_VIEWER = "CodeViewer";
    public static final String WIDGET_TYPE_VIEW_PAGER = "ViewPager";
    public static final String WIDGET_TYPE_CHECK_BOX = "CheckBox";
    public static final String WIDGET_TYPE_SWITCH = "Switch";
    public static final String WIDGET_TYPE_VIDEO_VIEW = "VideoView";
    public static final String WIDGET_TYPE_SEEK_BAR = "SeekBar";
    public static final String WIDGET_TYPE_PROGRESS_BAR = "ProgressBar";
    public static final String WIDGET_TYPE_RADIO_BUTTON = "RadioButton";
    public static final String WIDGET_TYPE_FRAME_LAYOUT = "FrameLayout";
    public static final String WIDGET_TYPE_SEARCH_VIEW = "SearchView";
    public static final String WIDGET_TYPE_RATING_VIEW = "RatingView";
    public static final String WIDGET_TYPE_CIRCLE_IMAGE_VIEW = "CircleImageView";
    public static final String WIDGET_TYPE_DIGITAL_CLOCK = "DigitalClock";
    public static final String WIDGET_TYPE_TIME_PICKER = "TimePicker";
    public static final String WIDGET_TYPE_SCROLL_VIEW = "ScrollView";
    public static final String WIDGET_TYPE_HORIZONTAL_SCROLL_VIEW = "HorizontalScrollView";
    public static final String WIDGET_TYPE_GRID_VIEW = "GridView";

    public static int setTextSize(View selectedWidget, String toString) {
        return 0;
    }

    public static int getWidgetIconId(String str) {
        if (WIDGET_TYPE_TEXT_VIEW.equals(str)) {
            return 0;
        }
        if (WIDGET_TYPE_BUTTON.equals(str)) {
            return 1;
        }
        if (WIDGET_TYPE_IMAGE_VIEW.equals(str)) {
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
        if (WIDGET_TYPE_EDIT_TEXT.equals(str)) {
            return 7;
        }
        if (WIDGET_TYPE_VIEW_PAGER.equals(str)) {
            return 8;
        }
        if (WIDGET_TYPE_CHECK_BOX.equals(str)) {
            return 9;
        }
        if (WIDGET_TYPE_SWITCH.equals(str)) {
            return 10;
        }
        if (WIDGET_TYPE_VIDEO_VIEW.equals(str)) {
            return 11;
        }
        if (WIDGET_TYPE_SEEK_BAR.equals(str)) {
            return 12;
        }
        if (WIDGET_TYPE_PROGRESS_BAR.equals(str)) {
            return 13;
        }
        if (WIDGET_TYPE_RADIO_BUTTON.equals(str)) {
            return 14;
        }
        if (WIDGET_TYPE_FRAME_LAYOUT.equals(str)) {
            return 15;
        }
        if (WIDGET_TYPE_SEARCH_VIEW.equals(str)) {
            return 16;
        }
        if (WIDGET_TYPE_RATING_VIEW.equals(str)) {
            return 17;
        }
        if (WIDGET_TYPE_CIRCLE_IMAGE_VIEW.equals(str)) {
            return 18;
        }
        if (WIDGET_TYPE_DIGITAL_CLOCK.equals(str)) {
            return 19;
        }
        if (WIDGET_TYPE_TIME_PICKER.equals(str)) {
            return 20;
        }
        if (WIDGET_TYPE_SCROLL_VIEW.equals(str)) {
            return 21;
        }
        if (WIDGET_TYPE_HORIZONTAL_SCROLL_VIEW.equals(str)) {
            return 22;
        }
        if (WIDGET_TYPE_GRID_VIEW.equals(str)) {
            return 23;
        }
        return 0;
    }

    public static boolean isWidgetIdExist(String str) {
        for (int i = 0; i < ViewEditorFragmentActivity.ll.getChildCount(); i++) {
            View childAt = ViewEditorFragmentActivity.ll.getChildAt(i);
            if (childAt != ViewEditorFragmentActivity.view_location && str.equals(getWidgetId(childAt))) {
                return true;
            }
        }
        return false;
    }

    public static String getWidgetId(View view) {
        if (view instanceof Widget) {
            return ((WidgetContract) view).getWidgetId();
        }
        return null;
    }

    public static void setWidgetId(View view, String str) {
        if (view instanceof Widget) {
            ((WidgetContract) view).setWidgetId(str);
        }
    }

    public static String getWidgetType(View view) {
        if (view instanceof WidgetTextView) {
            return WIDGET_TYPE_TEXT_VIEW;
        }
        if (view instanceof WidgetButton) {
            return WIDGET_TYPE_BUTTON;
        }
        if (view instanceof WidgetImageView) {
            return WIDGET_TYPE_IMAGE_VIEW;
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
        if (view instanceof WidgetEditText) {
            return WIDGET_TYPE_EDIT_TEXT;
        }
        if (view instanceof WidgetViewPager) {
            return WIDGET_TYPE_VIEW_PAGER;
        }
        if (view instanceof WidgetCheckBox) {
            return WIDGET_TYPE_CHECK_BOX;
        }
        if (view instanceof WidgetSwitch) {
            return WIDGET_TYPE_SWITCH;
        }
        if (view instanceof WidgetVideoView) {
            return WIDGET_TYPE_VIDEO_VIEW;
        }
        if (view instanceof WidgetSeekBar) {
            return WIDGET_TYPE_SEEK_BAR;
        }
        if (view instanceof WidgetProgressBar) {
            return WIDGET_TYPE_PROGRESS_BAR;
        }
        if (view instanceof WidgetRadioButton) {
            return WIDGET_TYPE_RADIO_BUTTON;
        }
        if (view instanceof WidgetFrameLayout) {
            return WIDGET_TYPE_FRAME_LAYOUT;
        }
        if (view instanceof WidgetLinearLayout) {
            return WIDGET_TYPE_LINEAR_LAYOUT;
        }
        if (view instanceof WidgetSearchView) {
            return WIDGET_TYPE_SEARCH_VIEW;
        }
        if (view instanceof WidgetRatingView) {
            return WIDGET_TYPE_RATING_VIEW;
        }
        if (view instanceof WidgetCircleImageView) {
            return WIDGET_TYPE_CIRCLE_IMAGE_VIEW;
        }
        if (view instanceof WidgetDigitalClock) {
            return WIDGET_TYPE_DIGITAL_CLOCK;
        }
        if (view instanceof WidgetTimePicker) {
            return WIDGET_TYPE_TIME_PICKER;
        }
        if (view instanceof WidgetScrollView) {
            return WIDGET_TYPE_SCROLL_VIEW;
        }
        if (view instanceof WidgetHorizontalScrollView) {
            return WIDGET_TYPE_HORIZONTAL_SCROLL_VIEW;
        }
        if (view instanceof WidgetGridView) {
            return WIDGET_TYPE_GRID_VIEW;
        }
        return null;
    }

    public static TextView getTextViewOfWidget(View view) {
        if (view instanceof WidgetEditText) {
            return (WidgetEditText) view;
        }
        if (view instanceof WidgetButton) {
            return (WidgetButton) view;
        }
        if (view instanceof WidgetCheckBox) {
            return (WidgetCheckBox) view;
        }
        if (view instanceof WidgetRadioButton) {
            return (WidgetRadioButton) view;
        }
        return null;
    }

    public static boolean containsWidgetInPhone(int i) {
        for (int i2 = 0; i2 < ViewEditorFragmentActivity.ll.getChildCount(); i2++) {
            View childAt = ViewEditorFragmentActivity.ll.getChildAt(i2);
            if (i == 0 && (childAt instanceof WidgetTextView)) {
                return true;
            }
            if (i == 1 && (childAt instanceof WidgetButton)) {
                return true;
            }
            if (i == 2 && (childAt instanceof WidgetImageView)) {
                return true;
            }
            if (i == 3 && (childAt instanceof WidgetLinearLayout)) {
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
            if (i == 7 && (childAt instanceof WidgetEditText)) {
                return true;
            }
            if (i == 8 && (childAt instanceof WidgetViewPager)) {
                return true;
            }
            if (i == 9 && (childAt instanceof WidgetCheckBox)) {
                return true;
            }
            if (i == 10 && (childAt instanceof WidgetSwitch)) {
                return true;
            }
            if (i == 11 && (childAt instanceof WidgetVideoView)) {
                return true;
            }
            if (i == 12 && (childAt instanceof WidgetSeekBar)) {
                return true;
            }
            if (i == 13 && (childAt instanceof WidgetProgressBar)) {
                return true;
            }
            if (i == 14 && (childAt instanceof WidgetRadioButton)) {
                return true;
            }
            if (i == 15 && (childAt instanceof WidgetFrameLayout)) {
                return true;
            }
            if (i == 16 && (childAt instanceof WidgetSearchView)) {
                return true;
            }
            if (i == 17 && (childAt instanceof WidgetRatingView)) {
                return true;
            }
            if (i == 18 && (childAt instanceof WidgetCircleImageView)) {
                return true;
            }
            if (i == 19 && (childAt instanceof WidgetDigitalClock)) {
                return true;
            }
            if (i == 20 && (childAt instanceof WidgetTimePicker)) {
                return true;
            }
            if (i == 21 && (childAt instanceof WidgetScrollView)) {
                return true;
            }
            if (i == 22 && (childAt instanceof WidgetHorizontalScrollView)) {
                return true;
            }
            if (i == 23 && (childAt instanceof WidgetGridView)) {
                return true;
            }
        }
        return false;
    }

    public static String getImagePath(View widget) {
        if (widget instanceof WidgetImageView) {
            return ((WidgetImageView) widget).getImagePath();
        }
        if (widget instanceof WidgetCircleImageView) {
            return ((WidgetCircleImageView) widget).getImagePath();
        }
        return "";
    }

    public static void setImagePath(View widget, String imagePath) {
        if (widget instanceof WidgetImageView) {
            ((WidgetImageView) widget).setImagePath(imagePath);
        }
        if (widget instanceof WidgetCircleImageView) {
            ((WidgetCircleImageView) widget).setImagePath(imagePath);
        }
    }

    public static Drawable loadImageFromPath(String path) {
        if (path == null || path.isEmpty()) return null;
        File file = new File(path);
        if (!file.exists()) return null;
        return new BitmapDrawable(BitmapFactory.decodeFile(path));
    }

    public static String getTextStyle(int i) {
        if (i == 3) {
            return "bold|italic";
        }
        if (i == 1) {
            return "bold";
        }
        if (i == 2) {
            return "italic";
        }
        return "normal";
    }
}