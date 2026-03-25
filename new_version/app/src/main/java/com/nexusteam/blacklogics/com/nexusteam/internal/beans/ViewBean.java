package com.nexusteam.internal.beans;

import com.nexusteam.internal.hc;
import com.nexusteam.internal.jo;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.nexusteam.blacklogics.R;

public class ViewBean extends jo implements Parcelable {
    public static final int CHOICE_MODE_MULTI = 2;
    public static final int CHOICE_MODE_NONE = 0;
    public static final int CHOICE_MODE_SINGLE = 1;
    public static final Parcelable.Creator<ViewBean> CREATOR = new Parcelable.Creator<ViewBean>() {
        public ViewBean createFromParcel(Parcel parcel) {
            return new ViewBean(parcel);
        }
        
        public ViewBean[] newArray(int i) {
            return new ViewBean[i];
        }
    };
    public static final int DEFAULT_MAX = 100;
    public static final int DEFAULT_PROGRESS = 0;
    public static final String PROGRESSBAR_STYLE_CIRCLE = "?android:progressBarStyle";
    public static final String PROGRESSBAR_STYLE_HORIZONTAL = "?android:progressBarStyleHorizontal";
    public static final int SPINNER_MODE_DIALOG = 0;
    public static final int SPINNER_MODE_DROPDOWN = 1;
    public static final int VIEW_TYPE_COUNT = 19;
    public static final int VIEW_TYPE_LAYOUT_HSCROLLVIEW = 2;
    public static final int VIEW_TYPE_LAYOUT_LINEAR = 0;
    public static final int VIEW_TYPE_LAYOUT_RELATIVE = 1;
    public static final int VIEW_TYPE_LAYOUT_VSCROLLVIEW = 12;
    public static final int VIEW_TYPE_WIDGET_ADVIEW = 17;
    public static final int VIEW_TYPE_WIDGET_BUTTON = 3;
    public static final int VIEW_TYPE_WIDGET_CALENDARVIEW = 15;
    public static final int VIEW_TYPE_WIDGET_CHECKBOX = 11;
    public static final int VIEW_TYPE_WIDGET_EDITTEXT = 5;
    public static final int VIEW_TYPE_WIDGET_FAB = 16;
    public static final int VIEW_TYPE_WIDGET_IMAGEVIEW = 6;
    public static final int VIEW_TYPE_WIDGET_LISTVIEW = 9;
    public static final int VIEW_TYPE_WIDGET_MAPVIEW = 18;
    public static final int VIEW_TYPE_WIDGET_PROGRESSBAR = 8;
    public static final int VIEW_TYPE_WIDGET_SEEKBAR = 14;
    public static final int VIEW_TYPE_WIDGET_SPINNER = 10;
    public static final int VIEW_TYPE_WIDGET_SWITCH = 13;
    public static final int VIEW_TYPE_WIDGET_TEXTVIEW = 4;
    public static final int VIEW_TYPE_WIDGET_WEBVIEW = 7;
    @Expose
    public String adSize;
    @Expose
    public String adUnitId;
    @Expose
    public float alpha;
    @Expose
    public int checked;
    @Expose
    public int choiceMode;
    private hc classInfo;
    @Expose
    public int clickable;
    @Expose
    public String customView;
    @Expose
    public int dividerHeight;
    @Expose
    public int enabled;
    @Expose
    public int firstDayOfWeek;
    @Expose
    public String id;
    @Expose
    public ImageBean image;
    @Expose
    public String indeterminate;
    @Expose
    public int index;
    @Expose
    public LayoutBean layout;
    @Expose
    public int max;
    public String name;
    @Expose
    public String parent;
    private hc parentClassInfo;
    @Expose
    public int parentType;
    @Expose
    public String preId;
    @Expose
    public int preIndex;
    @Expose
    public String preParent;
    @Expose
    public int preParentType;
    @Expose
    public int progress;
    @Expose
    public String progressStyle;
    @Expose
    public float scaleX;
    @Expose
    public float scaleY;
    @Expose
    public int spinnerMode;
    @Expose
    public TextBean text;
    @Expose
    public float translationX;
    @Expose
    public float translationY;
    @Expose
    public int type;
    
    public static String getViewTypeName(int i) {
        switch (i) {
            case 0:
            return "LinearLayout";
            case 2:
            return "HScrollView";
            case 3:
            return "Button";
            case 4:
            return "TextView";
            case 5:
            return "EditText";
            case 6:
            return "ImageView";
            case 7:
            return "WebView";
            case 8:
            return "ProgressBar";
            case 9:
            return "ListView";
            case 10:
            return "Spinner";
            case 11:
            return "CheckBox";
            case 12:
            return "ScrollView";
            case 13:
            return "Switch";
            case 14:
            return "SeekBar";
            case 15:
            return "CalendarView";
            case 17:
            return "AdView";
            case 18:
            return "MapView";
            default:
            return "";
        }
    }
    
    public static int getViewTypeResId(int i) {
        switch (i) {
            case 0:
            return R.drawable.widget_linear_horizontal;
            case 1:
            return R.drawable.widget_relative_layout;
            case 2:
            return R.drawable.widget_horizon_scrollview;
            case 3:
            return R.drawable.widget_button;
            case 4:
            return R.drawable.widget_text_view;
            case 5:
            return R.drawable.widget_edit_text;
            case 6:
            return R.drawable.widget_image_view;
            case 7:
            return R.drawable.widget_web_view;
            case 8:
            return R.drawable.widget_progress_bar;
            case 9:
            return R.drawable.widget_list_view;
            case 10:
            return R.drawable.widget_spinner;
            case 11:
            return R.drawable.widget_check_box;
            case 12:
            return R.drawable.widget_scrollview;
            case 13:
            return R.drawable.widget_switch;
            case 14:
            return R.drawable.widget_seek_bar;
            case 15:
            return R.drawable.widget_calendarview;
            case 16:
            return R.drawable.widget_fab;
            case 17:
            return R.drawable.widget_admob;
            case 18:
            return R.drawable.widget_google_map;
            default:
            return R.drawable.widget_module;
        }
    }
    
    public int describeContents() {
        return 0;
    }
    
    public ViewBean() {
        this.parent = null;
        this.parentType = -1;
        this.enabled = 1;
        this.clickable = 1;
        this.spinnerMode = 1;
        this.dividerHeight = 1;
        this.choiceMode = 0;
        this.customView = "";
        this.checked = 0;
        this.alpha = 1.0f;
        this.translationX = 0.0f;
        this.translationY = 0.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.max = 100;
        this.progress = 0;
        this.firstDayOfWeek = 1;
        this.adSize = "";
        this.adUnitId = "";
        this.layout = new LayoutBean();
        this.text = new TextBean();
        this.image = new ImageBean();
        this.indeterminate = "false";
        this.progressStyle = PROGRESSBAR_STYLE_CIRCLE;
    }
    
    public ViewBean(String str, int i) {
        this();
        this.id = str;
        this.name = str;
        this.type = i;
        this.parent = null;
    }
    
    public ViewBean(Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.type = parcel.readInt();
        this.parent = parcel.readString();
        this.parentType = parcel.readInt();
        this.index = parcel.readInt();
        this.enabled = parcel.readInt();
        this.clickable = parcel.readInt();
        this.spinnerMode = parcel.readInt();
        this.dividerHeight = parcel.readInt();
        this.choiceMode = parcel.readInt();
        this.customView = parcel.readString();
        this.checked = parcel.readInt();
        this.alpha = parcel.readFloat();
        this.translationX = parcel.readFloat();
        this.translationY = parcel.readFloat();
        this.scaleX = parcel.readFloat();
        this.scaleY = parcel.readFloat();
        this.max = parcel.readInt();
        this.progress = parcel.readInt();
        this.firstDayOfWeek = parcel.readInt();
        this.adSize = parcel.readString();
        this.adUnitId = parcel.readString();
        this.preParent = parcel.readString();
        this.preParentType = parcel.readInt();
        this.preIndex = parcel.readInt();
        this.preId = parcel.readString();
        this.layout = (LayoutBean) parcel.readParcelable(LayoutBean.class.getClassLoader());
        this.text = (TextBean) parcel.readParcelable(TextBean.class.getClassLoader());
        this.image = (ImageBean) parcel.readParcelable(ImageBean.class.getClassLoader());
        this.indeterminate = parcel.readString();
        this.progressStyle = parcel.readString();
    }
    
    public void print() {
        this.layout.print();
        this.text.print();
        this.image.print();
    }
    
    public void copy(ViewBean viewBean) {
        viewBean.print();
        this.id = viewBean.id;
        this.name = viewBean.name;
        this.type = viewBean.type;
        this.parent = viewBean.parent;
        this.parentType = viewBean.parentType;
        this.index = viewBean.index;
        this.enabled = viewBean.enabled;
        this.clickable = viewBean.clickable;
        this.spinnerMode = viewBean.spinnerMode;
        this.dividerHeight = viewBean.dividerHeight;
        this.choiceMode = viewBean.choiceMode;
        this.customView = viewBean.customView;
        this.checked = viewBean.checked;
        this.alpha = viewBean.alpha;
        this.translationX = viewBean.translationX;
        this.translationY = viewBean.translationY;
        this.scaleX = viewBean.scaleX;
        this.scaleY = viewBean.scaleY;
        this.max = viewBean.max;
        this.progress = viewBean.progress;
        this.firstDayOfWeek = viewBean.firstDayOfWeek;
        this.adSize = viewBean.adSize;
        this.adUnitId = viewBean.adUnitId;
        this.preParent = viewBean.preParent;
        this.preParentType = viewBean.preParentType;
        this.preIndex = viewBean.preIndex;
        this.preId = viewBean.preId;
        this.layout.copy(viewBean.layout);
        this.text.copy(viewBean.text);
        this.image.copy(viewBean.image);
        this.indeterminate = viewBean.indeterminate;
        this.progressStyle = viewBean.progressStyle;
    }
    
    public ViewBean clone() {
        ViewBean viewBean = new ViewBean();
        viewBean.copy(this);
        return viewBean;
    }
    
    public boolean isEqual(ViewBean viewBean) {
        if (this.type != viewBean.type || this.parentType != viewBean.parentType || this.index != viewBean.index || this.enabled != viewBean.enabled || this.clickable != viewBean.clickable || this.alpha != viewBean.alpha || this.translationX != viewBean.translationX || this.translationY != viewBean.translationY || this.scaleX != viewBean.scaleX || this.scaleY != viewBean.scaleY || this.spinnerMode != viewBean.spinnerMode || this.dividerHeight != viewBean.dividerHeight || this.choiceMode != viewBean.choiceMode || this.checked != viewBean.checked || this.max != viewBean.max || this.progress != viewBean.progress || this.firstDayOfWeek != viewBean.firstDayOfWeek || !this.adSize.equals(viewBean.adSize) || !this.adUnitId.equals(viewBean.adUnitId) || !this.text.isEqual(viewBean.text) || !this.layout.isEqual(viewBean.layout) || !this.image.isEqual(viewBean.image) || !this.indeterminate.equals(viewBean.indeterminate) || !this.progressStyle.equals(viewBean.progressStyle)) {
            return false;
        }
        if (this.id != null) {
            if (viewBean.id == null || !this.id.equals(viewBean.id)) {
                return false;
            }
        } else if (viewBean.id != null) {
            return false;
        }
        if (this.parent != null) {
            if (viewBean.parent == null || !this.parent.equals(viewBean.parent)) {
                return false;
            }
        } else if (viewBean.parent != null) {
            return false;
        }
        if (this.customView != null) {
            if (viewBean.customView == null || !this.customView.equals(viewBean.customView)) {
                return false;
            }
            return true;
        } else if (viewBean.customView != null) {
            return false;
        } else {
            return true;
        }
    }
    
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeInt(this.type);
        parcel.writeString(this.parent);
        parcel.writeInt(this.parentType);
        parcel.writeInt(this.index);
        parcel.writeInt(this.enabled);
        parcel.writeInt(this.clickable);
        parcel.writeInt(this.spinnerMode);
        parcel.writeInt(this.dividerHeight);
        parcel.writeInt(this.choiceMode);
        parcel.writeString(this.customView);
        parcel.writeInt(this.checked);
        parcel.writeFloat(this.alpha);
        parcel.writeFloat(this.translationX);
        parcel.writeFloat(this.translationY);
        parcel.writeFloat(this.scaleX);
        parcel.writeFloat(this.scaleY);
        parcel.writeInt(this.max);
        parcel.writeInt(this.progress);
        parcel.writeInt(this.firstDayOfWeek);
        parcel.writeString(this.adSize);
        parcel.writeString(this.adUnitId);
        parcel.writeString(this.preParent);
        parcel.writeInt(this.preParentType);
        parcel.writeInt(this.preIndex);
        parcel.writeString(this.preId);
        parcel.writeParcelable(this.layout, i);
        parcel.writeParcelable(this.text, i);
        parcel.writeParcelable(this.image, i);
        parcel.writeString(this.indeterminate);
        parcel.writeString(this.progressStyle);
    }
    
    public static int getViewTypeByTypeName(String typeName) {
        if (typeName == null) return -1;
        
        switch (typeName) {
            case "Button":
            return 3;
            case "TextView":
            return 4;
            case "CheckBox":
            return 11;
            case "EditText":
            return 5;
            case "ImageView":
            return 6;
            case "ListView":
            return 9;
            case "ProgressBar":
            return 8;
            case "SeekBar":
            return 14;
            case "Spinner":
            return 10;
            case "Switch":
            return 13;
            case "WebView":
            return 7;
            case "LinearLayout":
            return 0;
            case "ScrollView":
            return 12;
            case "HScrollView":
            return 2;
            case "CalendarView":
            return 15;
            case "AdView":
            return 17;
            case "MapView":
            return 18;
            default:
            return -1;
        }
    }
    
    
    public void clearClassInfo() {
        this.classInfo = null;
    }
    
    /* access modifiers changed from: protected */
    public hc buildClassInfo(int i) {
        String str = "";
        if (i != 0) {
            switch (i) {
                case 2:
                str = "HorizontalScrollView";
                break;
                case 3:
                str = "Button";
                break;
                case 4:
                str = "TextView";
                break;
                case 5:
                str = "EditText";
                break;
                case 6:
                str = "ImageView";
                break;
                case 7:
                str = "WebView";
                break;
                case 8:
                str = "ProgressBar";
                break;
                case 9:
                str = "ListView";
                break;
                case 10:
                str = "Spinner";
                break;
                case 11:
                str = "CheckBox";
                break;
                case 12:
                str = "ScrollView";
                break;
                case 13:
                str = "Switch";
                break;
                case 14:
                str = "SeekBar";
                break;
                case 15:
                str = "CalendarView";
                break;
                case 16:
                str = "FloatingActionButton";
                break;
                case 17:
                str = "AdView";
                break;
                case 18:
                str = "MapView";
                break;
            }
        } else {
            str = "LinearLayout";
        }
        return new hc(str);
    }
    
    public hc getClassInfo() {
        if (this.classInfo == null) {
            this.classInfo = buildClassInfo(this.type);
        }
        return this.classInfo;
    }
    
    public hc getParentClassInfo() {
        if (this.parentType == -1) {
            return null;
        }
        if (this.parentClassInfo == null) {
            this.parentClassInfo = buildClassInfo(this.parentType);
        }
        return this.parentClassInfo;
    }
    
    public static Parcelable.Creator<ViewBean> getCreator() {
        return CREATOR;
    }
}
