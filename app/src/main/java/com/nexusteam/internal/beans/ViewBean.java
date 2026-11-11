package com.nexusteam.internal.beans;

import com.nexusteam.internal.hc;
import com.nexusteam.internal.jo;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
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
    public static final int VIEW_TYPE_WIDGET_RADIOBUTTON = 19;
    public static final int VIEW_TYPE_WIDGET_RADIOGROUP = 20;
    public static final int VIEW_TYPE_WIDGET_TOGGLEBUTTON = 21;
    public static final int VIEW_TYPE_WIDGET_RATINGBAR = 22;
    public static final int VIEW_TYPE_WIDGET_NUMBERPICKER = 23;
    public static final int VIEW_TYPE_WIDGET_VIDEOVIEW = 24;
    public static final int VIEW_TYPE_WIDGET_GRIDVIEW = 25;
    public static final int VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW = 26;
    public static final int VIEW_TYPE_WIDGET_DATEPICKER = 27;
    public static final int VIEW_TYPE_WIDGET_TIMEPICKER = 28;
    public static final int VIEW_TYPE_WIDGET_MATERIALBUTTON = 29;
    public static final int VIEW_TYPE_WIDGET_MATERIALCARDVIEW = 30;
    public static final int VIEW_TYPE_WIDGET_TEXTINPUTLAYOUT = 31;
    public static final int VIEW_TYPE_WIDGET_TEXTINPUTEDITTEXT = 32;
    public static final int VIEW_TYPE_WIDGET_CHIP = 33;
    public static final int VIEW_TYPE_WIDGET_CHIPGROUP = 34;
    public static final int VIEW_TYPE_WIDGET_BOTTOMNAVIGATIONVIEW = 35;
    public static final int VIEW_TYPE_WIDGET_TABLAYOUT = 36;
    public static final int VIEW_TYPE_WIDGET_SWITCHMATERIAL = 37;
    public static final int VIEW_TYPE_WIDGET_SLIDER = 38;

    public static final int VIEW_TYPE_WIDGET_RECYCLERVIEW = 39;
    public static final int VIEW_TYPE_WIDGET_VIEWPAGER = 40;
    public static final int VIEW_TYPE_WIDGET_VIEWPAGER2 = 41;
    public static final int VIEW_TYPE_WIDGET_CARDVIEW = 42;
    public static final int VIEW_TYPE_WIDGET_CONSTRAINTLAYOUT = 43;
    public static final int VIEW_TYPE_WIDGET_COORDINATORLAYOUT = 44;
    public static final int VIEW_TYPE_WIDGET_NESTEDSCROLLVIEW = 45;
    public static final int VIEW_TYPE_WIDGET_LOTTIE = 46;
    public static final int VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW = 47;
    public static final int VIEW_TYPE_WIDGET_NAVIGATIONVIEW = 48;
    public static final int VIEW_TYPE_WIDGET_SEARCHVIEW = 49;
    public static final int VIEW_TYPE_LAYOUT_FRAME = 50;
    public static final int VIEW_TYPE_LAYOUT_GRID = 51;
    public static final int VIEW_TYPE_LAYOUT_TABLE = 52;
    public static final int VIEW_TYPE_WIDGET_IMAGEBUTTON = 53;
    public static final int VIEW_TYPE_WIDGET_EXPANDABLELISTVIEW = 54;
    public static final int VIEW_TYPE_WIDGET_COLLAPSINGTOOLBAR = 55;
    public static final int VIEW_TYPE_WIDGET_MOTIONLAYOUT = 56;
    public static final int VIEW_TYPE_WIDGET_WEBVIEW2 = 57;

// ── Extended types (AndroidX + Material + 3rd-party) ──
public static final int VIEW_TYPE_WIDGET_MATERIALTOOLBAR = 58;
public static final int VIEW_TYPE_WIDGET_SWIPEREFRESHLAYOUT = 59;
public static final int VIEW_TYPE_WIDGET_SHAPEABLEIMAGEVIEW = 60;
public static final int VIEW_TYPE_WIDGET_CIRCULARPROGRESS = 61;
public static final int VIEW_TYPE_WIDGET_LINEARPROGRESS = 62;
public static final int VIEW_TYPE_WIDGET_INPUTCHIP = 63;
public static final int VIEW_TYPE_WIDGET_FILTERCHIP = 64;
public static final int VIEW_TYPE_WIDGET_SUGGESTIONCHIP = 65;
public static final int VIEW_TYPE_WIDGET_BOTTOMAPPBAR = 66;
public static final int VIEW_TYPE_WIDGET_MATERIALSLIDER = 67;
public static final int VIEW_TYPE_WIDGET_PATTERNCLOCKVIEW = 68;
public static final int VIEW_TYPE_WIDGET_CODEVIEW = 69;
public static final int VIEW_TYPE_WIDGET_OTPVIEW = 70;
public static final int VIEW_TYPE_WIDGET_BUBBLELAYOUT = 71;
public static final int VIEW_TYPE_WIDGET_BADGEVIEW = 72;
public static final int VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW = 73;
public static final int VIEW_TYPE_WIDGET_EXOPLAYERVIEW = 74;
public static final int VIEW_TYPE_WIDGET_NATIVEADVIEW = 75;
public static final int VIEW_TYPE_WIDGET_PAGERTABSTRIP = 76;
public static final int VIEW_TYPE_WIDGET_TABITEM = 77;
public static final int VIEW_TYPE_WIDGET_MATERIALCALENDARVIEW = 78;
public static final int VIEW_TYPE_WIDGET_MATERIALTIMEPICKER = 79;
public static final int VIEW_TYPE_WIDGET_MATERIALDATEPICKER = 80;
public static final int VIEW_TYPE_WIDGET_MATERIALSPINNER = 81;
public static final int VIEW_TYPE_WIDGET_MAPFRAGMENT = 82;
public static final int VIEW_TYPE_WIDGET_SUPPORTMAPFRAGMENT = 83;
public static final int VIEW_TYPE_WIDGET_MAPVIEW_GOOGLE = 84;
public static final int VIEW_TYPE_WIDGET_MAPVIEW_OSM = 85;
public static final int VIEW_TYPE_WIDGET_PLAYERVIEW = 86;
public static final int VIEW_TYPE_WIDGET_ANIMATEDVECTOR = 87;
public static final int VIEW_TYPE_WIDGET_STATELISTANIMATOR = 88;
public static final int VIEW_TYPE_WIDGET_ANIMATEDSTATELIST = 89;
public static final int VIEW_TYPE_WIDGET_SPINNER_MATERIAL = 90;
public static final int VIEW_TYPE_WIDGET_RADIOGROUP_MATERIAL = 91;
public static final int VIEW_TYPE_WIDGET_CHECKBOX_MATERIAL = 92;
public static final int VIEW_TYPE_WIDGET_SWITCHCOMPAT = 93;
public static final int VIEW_TYPE_WIDGET_TEXTINPUTEDITMATERIAL = 94;
public static final int VIEW_TYPE_LAYOUT_COORDINATOR_MATERIAL = 95;
public static final int VIEW_TYPE_LAYOUT_FRAME_ANDROIDX = 96;
public static final int VIEW_TYPE_LAYOUT_CONSTRAINT_BARRIER = 97;
public static final int VIEW_TYPE_LAYOUT_FLOW = 98;
public static final int VIEW_TYPE_COUNT = 99;
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
    @Expose
    public float rating;
    @Expose
    public int numStars;
    @Expose
    public int pickerMinValue;
    @Expose
    public int pickerMaxValue;
    @Expose
    public String videoPath;
    @Expose
    public float cardCornerRadius;
    @Expose
    public float cardElevation;
    @Expose
    public float sliderValueFrom;
    @Expose
    public float sliderValueTo;
    @Expose
    public float sliderValue;
    @Expose
    public Map<String, String> attrs;
    
    public static String getViewTypeName(int i) {
    switch (i) {
        case 0: return "LinearLayout";
        case 2: return "HScrollView";
        case 3: return "Button";
        case 4: return "TextView";
        case 5: return "EditText";
        case 6: return "ImageView";
        case 7: return "WebView";
        case 8: return "ProgressBar";
        case 9: return "ListView";
        case 10: return "Spinner";
        case 11: return "CheckBox";
        case 12: return "ScrollView";
        case 13: return "Switch";
        case 14: return "SeekBar";
        case 15: return "CalendarView";
        case 16: return "FloatingActionButton";
        case 17: return "AdView";
        case 18: return "MapView";
        case 19: return "RadioButton";
        case 20: return "RadioGroup";
        case 21: return "ToggleButton";
        case 22: return "RatingBar";
        case 23: return "NumberPicker";
        case 24: return "VideoView";
        case 25: return "GridView";
        case 26: return "AutoCompleteTextView";
        case 27: return "DatePicker";
        case 28: return "TimePicker";
        case 29: return "MaterialButton";
        case 30: return "MaterialCardView";
        case 31: return "TextInputLayout";
        case 32: return "TextInputEditText";
        case 33: return "Chip";
        case 34: return "ChipGroup";
        case 35: return "BottomNavigationView";
        case 36: return "TabLayout";
        case 37: return "SwitchMaterial";
        case 38: return "Slider";
        case 39: return "RecyclerView";
        case 40: return "ViewPager";
        case 41: return "ViewPager2";
        case 42: return "CardView";
        case 43: return "ConstraintLayout";
        case 44: return "CoordinatorLayout";
        case 45: return "NestedScrollView";
        case 46: return "LottieAnimationView";
        case 47: return "CircleImageView";
        case 48: return "NavigationView";
        case 49: return "SearchView";
        case 50: return "FrameLayout";
        case 51: return "GridLayout";
        case 52: return "TableLayout";
        case 53: return "ImageButton";
        case 54: return "ExpandableListView";
        case 55: return "CollapsingToolbarLayout";
        case 56: return "MotionLayout";
        case 57: return "WebView2";

        // ── Extended ──
        case 58: return "MaterialToolbar";
        case 59: return "SwipeRefreshLayout";
        case 60: return "ShapeableImageView";
        case 61: return "CircularProgressIndicator";
        case 62: return "LinearProgressIndicator";
        case 63: return "InputChip";
        case 64: return "FilterChip";
        case 65: return "SuggestionChip";
        case 66: return "BottomAppBar";
        case 67: return "MaterialSlider";
        case 68: return "PatternLockView";
        case 69: return "CodeView";
        case 70: return "OTPView";
        case 71: return "BubbleLayout";
        case 72: return "BadgeView";
        case 73: return "YouTubePlayerView";
        case 74: return "ExoPlayerView";
        case 75: return "NativeAdView";
        case 76: return "PagerTabStrip";
        case 77: return "TabItem";
        case 78: return "MaterialCalendarView";
        case 79: return "MaterialTimePicker";
        case 80: return "MaterialDatePicker";
        case 81: return "MaterialSpinner";
        case 82: return "MapFragment";
        case 83: return "SupportMapFragment";
        case 84: return "GoogleMapView";
        case 85: return "OpenStreetMapView";
        case 86: return "PlayerView";
        case 87: return "AnimatedVectorDrawable";
        case 88: return "StateListAnimator";
        case 89: return "AnimatedStateListDrawable";
        case 90: return "MaterialSpinner";
        case 91: return "MaterialRadioGroup";
        case 92: return "MaterialCheckBox";
        case 93: return "SwitchCompat";
        case 94: return "TextInputEditTextMaterial";
        case 95: return "CoordinatorLayoutMaterial";
        case 96: return "FrameLayoutAndroidX";
        case 97: return "ConstraintLayoutBarrier";
        case 98: return "ConstraintLayoutFlow";

        case 1: return "RelativeLayout";
        default: return "";
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
            case 19:
            return R.drawable.widget_radio_button;
            case 20:
            return R.drawable.widget_radio_group;
            case 21:
            return R.drawable.widget_toggle_button;
            case 22:
            return R.drawable.widget_rating_bar;
            case 23:
            return R.drawable.widget_number_picker;
            case 24:
            return R.drawable.widget_video_view;
            case 25:
            return R.drawable.widget_grid_view;
            case 26:
            return R.drawable.widget_autocomplete_textview;
            case 27:
            return R.drawable.widget_date_picker;
            case 28:
            return R.drawable.widget_time_picker;
            case 29:
            return R.drawable.widget_material_button;
            case 30:
            return R.drawable.widget_material_cardview;
            case 31:
            return R.drawable.widget_text_input_layout;
            case 32:
            return R.drawable.widget_text_input_edittext;
            case 33:
            return R.drawable.widget_chip;
            case 34:
            return R.drawable.widget_chip_group;
            case 35:
            return R.drawable.widget_bottom_navigation;
            case 36:
            return R.drawable.widget_tab_layout;
            case 37:
            return R.drawable.widget_switch_material;
            case 38:
            return R.drawable.widget_slider;
            case 39:
            return R.drawable.widget_module;
            case 40:
            case 41:
            return R.drawable.widget_module;
            case 42:
            return R.drawable.widget_material_cardview;
            case 43:
            case 44:
            case 45:
            return R.drawable.widget_relative_layout;
            case 46:
            case 47:
            return R.drawable.widget_image_view;
            case 48:
            return R.drawable.widget_module;
            case 49:
            return R.drawable.widget_module;
            case 50:
            case 51:
            case 52:
            return R.drawable.widget_relative_layout;
            case 53:
            return R.drawable.widget_image_view;
            case 54:
            return R.drawable.widget_list_view;
            case 55:
            case 56:
            return R.drawable.widget_relative_layout;
            case 57:
            return R.drawable.widget_web_view;
            default:
            case 58:
case 59:
case 60:
case 61:
case 62:
case 63:
case 64:
case 65:
case 66:
case 67:
case 68:
case 69:
case 70:
case 71:
case 72:
case 73:
case 74:
case 75:
case 76:
case 77:
case 78:
case 79:
case 80:
case 81:
case 82:
case 83:
case 84:
case 85:
case 86:
case 87:
case 88:
case 89:
case 90:
case 91:
case 92:
case 93:
case 94:
case 95:
case 96:
case 97:
case 98:
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
        this.rating = 0.0f;
        this.numStars = 5;
        this.pickerMinValue = 0;
        this.pickerMaxValue = 100;
        this.videoPath = "";
        this.cardCornerRadius = 4.0f;
        this.cardElevation = 2.0f;
        this.sliderValueFrom = 0.0f;
        this.sliderValueTo = 100.0f;
        this.sliderValue = 0.0f;
        this.attrs = new LinkedHashMap<String, String>();
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
        this.rating = parcel.readFloat();
        this.numStars = parcel.readInt();
        this.pickerMinValue = parcel.readInt();
        this.pickerMaxValue = parcel.readInt();
        this.videoPath = parcel.readString();
        this.cardCornerRadius = parcel.readFloat();
        this.cardElevation = parcel.readFloat();
        this.sliderValueFrom = parcel.readFloat();
        this.sliderValueTo = parcel.readFloat();
        this.sliderValue = parcel.readFloat();
        this.preParent = parcel.readString();
        this.preParentType = parcel.readInt();
        this.preIndex = parcel.readInt();
        this.preId = parcel.readString();
        this.layout = (LayoutBean) parcel.readParcelable(LayoutBean.class.getClassLoader());
        this.text = (TextBean) parcel.readParcelable(TextBean.class.getClassLoader());
        this.image = (ImageBean) parcel.readParcelable(ImageBean.class.getClassLoader());
        this.indeterminate = parcel.readString();
        this.progressStyle = parcel.readString();
        // attrs map (appended for backward compatibility with older parcels that omit it)
        this.attrs = new LinkedHashMap<String, String>();
        try {
            int attrCount = parcel.dataAvail() > 0 ? parcel.readInt() : 0;
            for (int i = 0; i < attrCount; i++) {
                String k = parcel.readString();
                String v = parcel.readString();
                if (k != null) {
                    this.attrs.put(k, v != null ? v : "");
                }
            }
        } catch (Exception ignored) {
            if (this.attrs == null) this.attrs = new LinkedHashMap<String, String>();
        }
    }
    
    public Map<String, String> getAttrs() {
        if (this.attrs == null) {
            this.attrs = new LinkedHashMap<String, String>();
        }
        return this.attrs;
    }

    public void print() {
        this.layout.print();
        this.text.print();
        this.image.print();
    }
    
    public void copy(ViewBean viewBean) {
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
        this.rating = viewBean.rating;
        this.numStars = viewBean.numStars;
        this.pickerMinValue = viewBean.pickerMinValue;
        this.pickerMaxValue = viewBean.pickerMaxValue;
        this.videoPath = viewBean.videoPath;
        this.cardCornerRadius = viewBean.cardCornerRadius;
        this.cardElevation = viewBean.cardElevation;
        this.sliderValueFrom = viewBean.sliderValueFrom;
        this.sliderValueTo = viewBean.sliderValueTo;
        this.sliderValue = viewBean.sliderValue;
        this.preParent = viewBean.preParent;
        this.preParentType = viewBean.preParentType;
        this.preIndex = viewBean.preIndex;
        this.preId = viewBean.preId;
        this.layout.copy(viewBean.layout);
        this.text.copy(viewBean.text);
        this.image.copy(viewBean.image);
        this.indeterminate = viewBean.indeterminate;
        this.progressStyle = viewBean.progressStyle;
        this.attrs = new LinkedHashMap<String, String>();
        if (viewBean.attrs != null) {
            this.attrs.putAll(viewBean.attrs);
        }
    }
    
    public ViewBean clone() {
        ViewBean viewBean = new ViewBean();
        viewBean.copy(this);
        return viewBean;
    }
    
    public boolean isEqual(ViewBean viewBean) {
        if (viewBean == null) return false;
        if (this.type != viewBean.type || this.parentType != viewBean.parentType || this.index != viewBean.index
                || this.enabled != viewBean.enabled || this.clickable != viewBean.clickable
                || this.alpha != viewBean.alpha || this.translationX != viewBean.translationX
                || this.translationY != viewBean.translationY || this.scaleX != viewBean.scaleX
                || this.scaleY != viewBean.scaleY || this.spinnerMode != viewBean.spinnerMode
                || this.dividerHeight != viewBean.dividerHeight || this.choiceMode != viewBean.choiceMode
                || this.checked != viewBean.checked || this.max != viewBean.max
                || this.progress != viewBean.progress || this.firstDayOfWeek != viewBean.firstDayOfWeek
                || this.rating != viewBean.rating || this.numStars != viewBean.numStars
                || this.pickerMinValue != viewBean.pickerMinValue || this.pickerMaxValue != viewBean.pickerMaxValue
                || this.cardCornerRadius != viewBean.cardCornerRadius || this.cardElevation != viewBean.cardElevation
                || this.sliderValueFrom != viewBean.sliderValueFrom || this.sliderValueTo != viewBean.sliderValueTo
                || this.sliderValue != viewBean.sliderValue) {
            return false;
        }
        if (!safeEquals(this.adSize, viewBean.adSize)) return false;
        if (!safeEquals(this.adUnitId, viewBean.adUnitId)) return false;
        if (!safeEquals(this.indeterminate, viewBean.indeterminate)) return false;
        if (!safeEquals(this.progressStyle, viewBean.progressStyle)) return false;
        if (!safeEquals(this.id, viewBean.id)) return false;
        if (!safeEquals(this.parent, viewBean.parent)) return false;
        if (!safeEquals(this.customView, viewBean.customView)) return false;
        if (!safeEquals(this.videoPath, viewBean.videoPath)) return false;
        if (this.text != null) {
            if (viewBean.text == null || !this.text.isEqual(viewBean.text)) return false;
        } else if (viewBean.text != null) {
            return false;
        }
        if (this.layout != null) {
            if (viewBean.layout == null || !this.layout.isEqual(viewBean.layout)) return false;
        } else if (viewBean.layout != null) {
            return false;
        }
        if (this.image != null) {
            if (viewBean.image == null || !this.image.isEqual(viewBean.image)) return false;
        } else if (viewBean.image != null) {
            return false;
        }
        Map<String, String> a1 = this.getAttrs();
        Map<String, String> a2 = viewBean.getAttrs();
        if (a1.size() != a2.size()) return false;
        Iterator<Map.Entry<String, String>> it = a1.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> e = it.next();
            if (!safeEquals(e.getValue(), a2.get(e.getKey()))) return false;
        }
        return true;
    }

    private static boolean safeEquals(String a, String b) {
        if (a == null) return b == null;
        return a.equals(b);
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
        parcel.writeFloat(this.rating);
        parcel.writeInt(this.numStars);
        parcel.writeInt(this.pickerMinValue);
        parcel.writeInt(this.pickerMaxValue);
        parcel.writeString(this.videoPath);
        parcel.writeFloat(this.cardCornerRadius);
        parcel.writeFloat(this.cardElevation);
        parcel.writeFloat(this.sliderValueFrom);
        parcel.writeFloat(this.sliderValueTo);
        parcel.writeFloat(this.sliderValue);
        parcel.writeString(this.preParent);
        parcel.writeInt(this.preParentType);
        parcel.writeInt(this.preIndex);
        parcel.writeString(this.preId);
        parcel.writeParcelable(this.layout, i);
        parcel.writeParcelable(this.text, i);
        parcel.writeParcelable(this.image, i);
        parcel.writeString(this.indeterminate);
        parcel.writeString(this.progressStyle);
        Map<String, String> map = this.getAttrs();
        parcel.writeInt(map.size());
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> e = it.next();
            parcel.writeString(e.getKey());
            parcel.writeString(e.getValue() != null ? e.getValue() : "");
        }
    }
    
    public static int getViewTypeByTypeNameExtended(String typeName) {
        if (typeName == null) return -1;
        switch (typeName) {
            case "RecyclerView": return VIEW_TYPE_WIDGET_RECYCLERVIEW;
            case "ViewPager": return VIEW_TYPE_WIDGET_VIEWPAGER;
            case "ViewPager2": return VIEW_TYPE_WIDGET_VIEWPAGER2;
            case "CardView": return VIEW_TYPE_WIDGET_CARDVIEW;
            case "ConstraintLayout": return VIEW_TYPE_WIDGET_CONSTRAINTLAYOUT;
            case "CoordinatorLayout": return VIEW_TYPE_WIDGET_COORDINATORLAYOUT;
            case "NestedScrollView": return VIEW_TYPE_WIDGET_NESTEDSCROLLVIEW;
            case "LottieAnimationView": return VIEW_TYPE_WIDGET_LOTTIE;
            case "CircleImageView": return VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW;
            case "NavigationView": return VIEW_TYPE_WIDGET_NAVIGATIONVIEW;
            case "SearchView": return VIEW_TYPE_WIDGET_SEARCHVIEW;
            case "FrameLayout": return VIEW_TYPE_LAYOUT_FRAME;
            case "GridLayout": return VIEW_TYPE_LAYOUT_GRID;
            case "TableLayout": return VIEW_TYPE_LAYOUT_TABLE;
            case "ImageButton": return VIEW_TYPE_WIDGET_IMAGEBUTTON;
            case "ExpandableListView": return VIEW_TYPE_WIDGET_EXPANDABLELISTVIEW;
            case "CollapsingToolbarLayout": return VIEW_TYPE_WIDGET_COLLAPSINGTOOLBAR;
            case "MotionLayout": return VIEW_TYPE_WIDGET_MOTIONLAYOUT;
            case "WebView2": return VIEW_TYPE_WIDGET_WEBVIEW2;
            default: return getViewTypeByTypeName(typeName);
        }
    }

    public static int getViewTypeByTypeName(String typeName) {
        if (typeName == null) return -1;
        
        String original = typeName;
    if (typeName.contains(".")) {
        String simple = typeName.substring(typeName.lastIndexOf('.') + 1);
        if (!simple.equals(typeName)) {
            int mapped = ViewBean.getViewTypeByTypeName(simple);
            if (mapped != -1) return mapped;
            // fall through with simple
            typeName = simple;
        }
    }
        
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
            case "FloatingActionButton":
            return 16;
            case "RadioButton":
            return 19;
            case "RadioGroup":
            return 20;
            case "ToggleButton":
            return 21;
            case "RatingBar":
            return 22;
            case "NumberPicker":
            return 23;
            case "VideoView":
            return 24;
            case "GridView":
            return 25;
            case "AutoCompleteTextView":
            return 26;
            case "DatePicker":
            return 27;
            case "TimePicker":
            return 28;
            case "MaterialButton":
            return 29;
            case "MaterialCardView":
            return 30;
            case "TextInputLayout":
            return 31;
            case "TextInputEditText":
            return 32;
            case "Chip":
            return 33;
            case "ChipGroup":
            return 34;
            case "BottomNavigationView":
            return 35;
            case "TabLayout":
            return 36;
            case "SwitchMaterial":
            return 37;
            case "Slider":
            return 38;
            case "RecyclerView":
            return 39;
            case "ViewPager":
            return 40;
            case "ViewPager2":
            return 41;
            case "CardView":
            return 42;
            case "ConstraintLayout":
            return 43;
            case "CoordinatorLayout":
            return 44;
            case "NestedScrollView":
            return 45;
            case "LottieAnimationView":
            case "Lottie":
            return 46;
            case "CircleImageView":
            return 47;
            case "NavigationView":
            return 48;
            case "SearchView":
            return VIEW_TYPE_WIDGET_SEARCHVIEW;
            case "FrameLayout":
            return VIEW_TYPE_LAYOUT_FRAME;
            case "GridLayout":
            return VIEW_TYPE_LAYOUT_GRID;
            case "TableLayout":
            return VIEW_TYPE_LAYOUT_TABLE;
            case "ImageButton":
            return VIEW_TYPE_WIDGET_IMAGEBUTTON;
            case "ExpandableListView":
            return VIEW_TYPE_WIDGET_EXPANDABLELISTVIEW;
            case "CollapsingToolbarLayout":
            return VIEW_TYPE_WIDGET_COLLAPSINGTOOLBAR;
            case "MotionLayout":
            return VIEW_TYPE_WIDGET_MOTIONLAYOUT;
            case "WebView2":
            return VIEW_TYPE_WIDGET_WEBVIEW2;
            // ── Extended ──
case "MaterialToolbar": return 58;
case "SwipeRefreshLayout": return 59;
case "ShapeableImageView": return 60;
case "CircularProgressIndicator": return 61;
case "LinearProgressIndicator": return 62;
case "InputChip": return 63;
case "FilterChip": return 64;
case "SuggestionChip": return 65;
case "BottomAppBar": return 66;
case "MaterialSlider": return 67;
case "PatternLockView": return 68;
case "CodeView": return 69;
case "OTPView": return 70;
case "BubbleLayout": return 71;
case "BadgeView": return 72;
case "YouTubePlayerView": return 73;
case "YoutubePlayerView": return 73;
case "ExoPlayerView": return 74;
case "NativeAdView": return 75;
case "PagerTabStrip": return 76;
case "TabItem": return 77;
case "MaterialCalendarView": return 78;
case "MaterialTimePicker": return 79;
case "MaterialDatePicker": return 80;
case "MaterialSpinner": return 81;
case "MapFragment": return 82;
case "SupportMapFragment": return 83;
case "GoogleMapView": return 84;
case "OpenStreetMapView": return 85;
case "PlayerView": return 86;
case "AnimatedVectorDrawable": return 87;
case "StateListAnimator": return 88;
case "AnimatedStateListDrawable": return 89;
case "MaterialRadioGroup": return 91;
case "MaterialCheckBox": return 92;
case "SwitchCompat": return 93;
case "TextInputEditTextMaterial": return 94;
case "CoordinatorLayoutMaterial": return 95;
case "FrameLayoutAndroidX": return 96;
case "ConstraintLayoutBarrier": return 97;
case "ConstraintLayoutFlow": return 98;
            case "MaterialSwitch":
            return VIEW_TYPE_WIDGET_SWITCHMATERIAL;
            case "RelativeLayout":
            return VIEW_TYPE_LAYOUT_RELATIVE;
            case "HorizontalScrollView":
            return VIEW_TYPE_LAYOUT_HSCROLLVIEW;
            default:
            return -1;
        }
    }
    
    
    public void clearClassInfo() {
        this.classInfo = null;
    }
    
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
                case 19:
                str = "RadioButton";
                break;
                case 20:
                str = "RadioGroup";
                break;
                case 21:
                str = "ToggleButton";
                break;
                case 22:
                str = "RatingBar";
                break;
                case 23:
                str = "NumberPicker";
                break;
                case 24:
                str = "VideoView";
                break;
                case 25:
                str = "GridView";
                break;
                case 26:
                str = "AutoCompleteTextView";
                break;
                case 27:
                str = "DatePicker";
                break;
                case 28:
                str = "TimePicker";
                break;
                case 29:
                str = "MaterialButton";
                break;
                case 30:
                str = "MaterialCardView";
                break;
                case 31:
                str = "TextInputLayout";
                break;
                case 32:
                str = "TextInputEditText";
                break;
                case 33:
                str = "Chip";
                break;
                case 34:
                str = "ChipGroup";
                break;
                case 35:
                str = "BottomNavigationView";
                break;
                case 36:
                str = "TabLayout";
                break;
                case 37:
                str = "SwitchMaterial";
                break;
                case 38:
                str = "Slider";
                break;
                case 39: str = "RecyclerView"; break;
                case 40: str = "ViewPager"; break;
                case 41: str = "ViewPager2"; break;
                case 42: str = "CardView"; break;
                case 43: str = "ConstraintLayout"; break;
                case 44: str = "CoordinatorLayout"; break;
                case 45: str = "NestedScrollView"; break;
                case 46: str = "LottieAnimationView"; break;
                case 47: str = "CircleImageView"; break;
                case 48: str = "NavigationView"; break;
                case 49: str = "SearchView"; break;
                case 50: str = "FrameLayout"; break;
                case 51: str = "GridLayout"; break;
                case 52: str = "TableLayout"; break;
                case 53: str = "ImageButton"; break;
                case 54: str = "ExpandableListView"; break;
                case 55: str = "CollapsingToolbarLayout"; break;
                case 56: str = "MotionLayout"; break;
                case 57: str = "WebView2"; break;
                case 1: str = "RelativeLayout"; break;
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
