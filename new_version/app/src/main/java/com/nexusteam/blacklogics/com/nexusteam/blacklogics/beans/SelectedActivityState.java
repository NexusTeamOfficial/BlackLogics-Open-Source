package com.nexusteam.blacklogics.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Stores currently selected activity/layout state.
 * Use this to restore spinner selection after ViewPager page change.
 */
public class SelectedActivityState implements Parcelable {

    private String xmlName;      // e.g. "activity_main"
    private String xmlFileName;  // e.g. "activity_main.xml"
    private String javaName;     // e.g. "MainActivity" (empty for custom views)
    private String javaFileName; // e.g. "MainActivity.java" (empty for custom views)
    private int xmlSpinnerPosition;
    private int javaSpinnerPosition;
    private boolean isCustomView;

    public SelectedActivityState() {}

    public SelectedActivityState(
        String xmlName,
        String xmlFileName,
        String javaName,
        String javaFileName,
        int xmlSpinnerPosition,
        int javaSpinnerPosition
    ) {
        this.xmlName = xmlName;
        this.xmlFileName = xmlFileName;
        this.javaName = javaName;
        this.javaFileName = javaFileName;
        this.xmlSpinnerPosition = xmlSpinnerPosition;
        this.javaSpinnerPosition = javaSpinnerPosition;
        this.isCustomView = (javaName == null || javaName.isEmpty());
    }

    // ---- Getters ----

    public String getXmlName()           { return xmlName != null ? xmlName : ""; }
    public String getXmlFileName()       { return xmlFileName != null ? xmlFileName : ""; }
    public String getJavaName()          { return javaName != null ? javaName : ""; }
    public String getJavaFileName()      { return javaFileName != null ? javaFileName : ""; }
    public int getXmlSpinnerPosition()   { return xmlSpinnerPosition; }
    public int getJavaSpinnerPosition()  { return javaSpinnerPosition; }
    public boolean isCustomView()        { return isCustomView; }

    // ---- Setters ----

    public void setXmlName(String xmlName)                       { this.xmlName = xmlName; }
    public void setXmlFileName(String xmlFileName)               { this.xmlFileName = xmlFileName; }
    public void setJavaName(String javaName)                     { this.javaName = javaName; this.isCustomView = (javaName == null || javaName.isEmpty()); }
    public void setJavaFileName(String javaFileName)             { this.javaFileName = javaFileName; }
    public void setXmlSpinnerPosition(int xmlSpinnerPosition)    { this.xmlSpinnerPosition = xmlSpinnerPosition; }
    public void setJavaSpinnerPosition(int javaSpinnerPosition)  { this.javaSpinnerPosition = javaSpinnerPosition; }

    // ---- Parcelable ----

    protected SelectedActivityState(Parcel in) {
        xmlName             = in.readString();
        xmlFileName         = in.readString();
        javaName            = in.readString();
        javaFileName        = in.readString();
        xmlSpinnerPosition  = in.readInt();
        javaSpinnerPosition = in.readInt();
        isCustomView        = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(xmlName);
        dest.writeString(xmlFileName);
        dest.writeString(javaName);
        dest.writeString(javaFileName);
        dest.writeInt(xmlSpinnerPosition);
        dest.writeInt(javaSpinnerPosition);
        dest.writeByte((byte) (isCustomView ? 1 : 0));
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<SelectedActivityState> CREATOR =
        new Creator<SelectedActivityState>() {
            @Override
            public SelectedActivityState createFromParcel(Parcel in) {
                return new SelectedActivityState(in);
            }
            @Override
            public SelectedActivityState[] newArray(int size) {
                return new SelectedActivityState[size];
            }
        };
}