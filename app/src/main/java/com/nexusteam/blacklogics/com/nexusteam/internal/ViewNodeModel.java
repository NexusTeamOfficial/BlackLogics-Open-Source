package com.nexusteam.internal;

public class ViewNodeModel {

    private String id;
    private int    type;
    private String widgetType;
    private String customView;
    private String parent;
    private int    parentType;
    private int    index;

    private boolean clickable;
    private boolean enabled;
    private float   alpha;
    private float   scaleX;
    private float   scaleY;
    private float   translationX;
    private float   translationY;

    private int    layoutWidth;
    private int    layoutHeight;
    private String backgroundColor;
    private String borderColor;
    private int    gravity;
    private int    layoutGravity;
    private int    orientation;
    private int    paddingTop;
    private int    paddingBottom;
    private int    paddingLeft;
    private int    paddingRight;
    private int    marginTop;
    private int    marginBottom;
    private int    marginLeft;
    private int    marginRight;
    private float  weight;
    private float  weightSum;

    private String text;
    private String textHint;
    private String textColor;
    private String textHintColor;
    private int    textSize;
    private int    textType;
    private String textFont;
    private int    inputType;
    private int    imeOption;
    private int    lines;
    private boolean singleLine;

    private String imageScaleType;
    private int    imageRotate;

    private int    max;
    private int    progress;
    private boolean indeterminate;
    private String progressStyle;

    private int    choiceMode;
    private int    dividerHeight;
    private int    spinnerMode;

    private boolean checked;

    private String adSize;
    private String adUnitId;

    private int firstDayOfWeek;

    private String inject;


    public String  getId()              { return id; }
    public int     getType()            { return type; }
    public String  getWidgetType()      { return widgetType; }
    public String  getCustomView()      { return customView; }
    public String  getParent()          { return parent; }
    public int     getParentType()      { return parentType; }
    public int     getIndex()           { return index; }
    public boolean isClickable()        { return clickable; }
    public boolean isEnabled()          { return enabled; }
    public float   getAlpha()           { return alpha; }
    public float   getScaleX()          { return scaleX; }
    public float   getScaleY()          { return scaleY; }
    public float   getTranslationX()    { return translationX; }
    public float   getTranslationY()    { return translationY; }
    public int     getLayoutWidth()     { return layoutWidth; }
    public int     getLayoutHeight()    { return layoutHeight; }
    public String  getBackgroundColor() { return backgroundColor; }
    public String  getBorderColor()     { return borderColor; }
    public int     getGravity()         { return gravity; }
    public int     getLayoutGravity()   { return layoutGravity; }
    public int     getOrientation()     { return orientation; }
    public int     getPaddingTop()      { return paddingTop; }
    public int     getPaddingBottom()   { return paddingBottom; }
    public int     getPaddingLeft()     { return paddingLeft; }
    public int     getPaddingRight()    { return paddingRight; }
    public int     getMarginTop()       { return marginTop; }
    public int     getMarginBottom()    { return marginBottom; }
    public int     getMarginLeft()      { return marginLeft; }
    public int     getMarginRight()     { return marginRight; }
    public float   getWeight()          { return weight; }
    public float   getWeightSum()       { return weightSum; }
    public String  getText()            { return text; }
    public String  getTextHint()        { return textHint; }
    public String  getTextColor()       { return textColor; }
    public String  getTextHintColor()   { return textHintColor; }
    public int     getTextSize()        { return textSize; }
    public int     getTextType()        { return textType; }
    public String  getTextFont()        { return textFont; }
    public int     getInputType()       { return inputType; }
    public int     getImeOption()       { return imeOption; }
    public int     getLines()           { return lines; }
    public boolean isSingleLine()       { return singleLine; }
    public String  getImageScaleType()  { return imageScaleType; }
    public int     getImageRotate()     { return imageRotate; }
    public int     getMax()             { return max; }
    public int     getProgress()        { return progress; }
    public boolean isIndeterminate()    { return indeterminate; }
    public String  getProgressStyle()   { return progressStyle; }
    public int     getChoiceMode()      { return choiceMode; }
    public int     getDividerHeight()   { return dividerHeight; }
    public int     getSpinnerMode()     { return spinnerMode; }
    public boolean isChecked()          { return checked; }
    public String  getAdSize()          { return adSize; }
    public String  getAdUnitId()        { return adUnitId; }
    public int     getFirstDayOfWeek()  { return firstDayOfWeek; }
    public String  getInject()          { return inject; }

    public void setId(String v)              { this.id = v; }
    public void setType(int v)               { this.type = v; }
    public void setWidgetType(String v)      { this.widgetType = v; }
    public void setCustomView(String v)      { this.customView = v; }
    public void setParent(String v)          { this.parent = v; }
    public void setParentType(int v)         { this.parentType = v; }
    public void setIndex(int v)              { this.index = v; }
    public void setClickable(boolean v)      { this.clickable = v; }
    public void setEnabled(boolean v)        { this.enabled = v; }
    public void setAlpha(float v)            { this.alpha = v; }
    public void setScaleX(float v)           { this.scaleX = v; }
    public void setScaleY(float v)           { this.scaleY = v; }
    public void setTranslationX(float v)     { this.translationX = v; }
    public void setTranslationY(float v)     { this.translationY = v; }
    public void setLayoutWidth(int v)        { this.layoutWidth = v; }
    public void setLayoutHeight(int v)       { this.layoutHeight = v; }
    public void setBackgroundColor(String v) { this.backgroundColor = v; }
    public void setBorderColor(String v)     { this.borderColor = v; }
    public void setGravity(int v)            { this.gravity = v; }
    public void setLayoutGravity(int v)      { this.layoutGravity = v; }
    public void setOrientation(int v)        { this.orientation = v; }
    public void setPaddingTop(int v)         { this.paddingTop = v; }
    public void setPaddingBottom(int v)      { this.paddingBottom = v; }
    public void setPaddingLeft(int v)        { this.paddingLeft = v; }
    public void setPaddingRight(int v)       { this.paddingRight = v; }
    public void setMarginTop(int v)          { this.marginTop = v; }
    public void setMarginBottom(int v)       { this.marginBottom = v; }
    public void setMarginLeft(int v)         { this.marginLeft = v; }
    public void setMarginRight(int v)        { this.marginRight = v; }
    public void setWeight(float v)           { this.weight = v; }
    public void setWeightSum(float v)        { this.weightSum = v; }
    public void setText(String v)            { this.text = v; }
    public void setTextHint(String v)        { this.textHint = v; }
    public void setTextColor(String v)       { this.textColor = v; }
    public void setTextHintColor(String v)   { this.textHintColor = v; }
    public void setTextSize(int v)           { this.textSize = v; }
    public void setTextType(int v)           { this.textType = v; }
    public void setTextFont(String v)        { this.textFont = v; }
    public void setInputType(int v)          { this.inputType = v; }
    public void setImeOption(int v)          { this.imeOption = v; }
    public void setLines(int v)              { this.lines = v; }
    public void setSingleLine(boolean v)     { this.singleLine = v; }
    public void setImageScaleType(String v)  { this.imageScaleType = v; }
    public void setImageRotate(int v)        { this.imageRotate = v; }
    public void setMax(int v)                { this.max = v; }
    public void setProgress(int v)           { this.progress = v; }
    public void setIndeterminate(boolean v)  { this.indeterminate = v; }
    public void setProgressStyle(String v)   { this.progressStyle = v; }
    public void setChoiceMode(int v)         { this.choiceMode = v; }
    public void setDividerHeight(int v)      { this.dividerHeight = v; }
    public void setSpinnerMode(int v)        { this.spinnerMode = v; }
    public void setChecked(boolean v)        { this.checked = v; }
    public void setAdSize(String v)          { this.adSize = v; }
    public void setAdUnitId(String v)        { this.adUnitId = v; }
    public void setFirstDayOfWeek(int v)     { this.firstDayOfWeek = v; }
    public void setInject(String v)          { this.inject = v; }

    public static String typeToName(int type) {
        switch (type) {
            case 0:  return "LinearLayout";
            case 1:  return "ScrollView";
            case 2:  return "Button";
            case 3:  return "TextView";
            case 4:  return "TextView";
            case 5:  return "EditText";
            case 6:  return "ImageView";
            case 7:  return "WebView";
            case 8:  return "ProgressBar";
            case 9:  return "ListView";
            case 10: return "Spinner";
            case 11: return "CheckBox";
            case 12: return "Switch";
            case 13: return "SeekBar";
            case 14: return "CalendarView";
            case 15: return "AdView";
            case 16: return "FloatingActionButton";
            case 17: return "MapView";
            case 18: return "HorizontalScrollView";
            default: return "View";
        }
    }

    @Override
    public String toString() {
        return "ViewNodeModel{id='" + id + "', widgetType='" + widgetType
                + "', parent='" + parent + "', index=" + index + "}";
    }
}
