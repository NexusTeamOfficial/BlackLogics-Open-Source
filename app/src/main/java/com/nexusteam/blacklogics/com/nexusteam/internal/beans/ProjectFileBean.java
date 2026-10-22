package com.nexusteam.internal.beans;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

public class ProjectFileBean extends SelectableBean implements Parcelable {
    public static final Parcelable.Creator<ProjectFileBean> CREATOR = new Parcelable.Creator<ProjectFileBean>() {
        public ProjectFileBean createFromParcel(Parcel parcel) {
            return new ProjectFileBean(parcel);
        }

        public ProjectFileBean[] newArray(int i) {
            return new ProjectFileBean[i];
        }
    };
    public static final int KEYBOARD_STATE_HIDDEN = 2;
    public static final int KEYBOARD_STATE_UNSPECIFIED = 0;
    public static final int KEYBOARD_STATE_VISIBLE = 1;
    public static final int OPTION_ACTIVITY_DRAWER = 4;
    public static final int OPTION_ACTIVITY_FAB = 8;
    public static final int OPTION_ACTIVITY_FULLSCREEN = 2;
    public static final int OPTION_ACTIVITY_MASK = 15;
    public static final int OPTION_ACTIVITY_SHIFT = 0;
    public static final int OPTION_ACTIVITY_TOOLBAR = 1;
    public static final int ORIENTATION_BOTH = 2;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 0;
    public static final int PROJECT_FILE_TYPE_ACTIVITY = 0;
    public static final int PROJECT_FILE_TYPE_CUSTOM_VIEW = 1;
    public static final int PROJECT_FILE_TYPE_DRAWER = 2;
    public static final int THEME_DEFAULT = 0;
    public static final int THEME_FULLSCREEN = 2;
    public static final int THEME_NOACTIONBAR = 1;
    public static final int THEME_NONE = -1;
    @Expose
    public String fileName;
    @Expose
    public int fileType;
    @Expose
    public int keyboardSetting;
    @Expose
    public int options = 0;
    @Expose
    public int orientation;
    public String presetName;
    @Expose
    @Deprecated
    public int theme = -1;

    public int describeContents() {
        return 0;
    }

    public void print() {
    }

    public ProjectFileBean(int i, String str) {
        this.fileType = i;
        this.fileName = str;
        if (i == 1) {
            this.presetName = "Basic List Item";
        } else {
            this.presetName = "Basic Drawer";
        }
        if (i == 0) {
            this.options |= 1;
            this.orientation = 0;
        } else {
            this.orientation = 2;
        }
        this.keyboardSetting = 0;
        this.theme = -1;
    }

    public ProjectFileBean(int i, String str, String str2) {
        this.fileType = i;
        this.fileName = str;
        this.presetName = str2;
        if (i == 0) {
            this.options |= 1;
            this.orientation = 0;
        } else {
            this.orientation = 2;
        }
        this.keyboardSetting = 0;
        this.theme = -1;
    }

    public ProjectFileBean(int i, String str, int i2, int i3, int i4) {
        this.fileType = i;
        this.fileName = str;
        this.orientation = i2;
        this.keyboardSetting = i3;
        this.options = i4;
        this.presetName = "Basic Activity";
        this.theme = -1;
    }

    public ProjectFileBean(int i, String str, int i2, int i3, boolean z, boolean z2, boolean z3, boolean z4) {
        this.fileType = i;
        this.fileName = str;
        this.orientation = i2;
        this.keyboardSetting = i3;
        this.presetName = "Basic Activity";
        this.theme = -1;
        if (z) {
            this.options |= 1;
        }
        if (z2) {
            this.options |= 2;
        }
        if (z3) {
            this.options |= 8;
        }
        if (z4) {
            this.options |= 4;
        }
    }

    public ProjectFileBean(int i, String str, String str2, int i2, int i3, boolean z, boolean z2, boolean z3, boolean z4) {
        this.fileType = i;
        this.fileName = str;
        this.orientation = i2;
        this.keyboardSetting = i3;
        this.presetName = str2;
        this.theme = -1;
        if (z) {
            this.options |= 1;
        }
        if (z2) {
            this.options |= 2;
        }
        if (z3) {
            this.options |= 8;
        }
        if (z4) {
            this.options |= 4;
        }
    }

    public void setOptionsByTheme() {
        if (this.theme != -1) {
            this.options = 0;
            if (this.theme == 0) {
                this.options |= 1;
            } else if (this.theme != 1) {
                this.options |= 2;
            }
            this.theme = -1;
        }
    }

    public ProjectFileBean(Parcel parcel) {
        this.fileType = parcel.readInt();
        this.fileName = parcel.readString();
        this.orientation = parcel.readInt();
        this.keyboardSetting = parcel.readInt();
        this.options = parcel.readInt();
        this.presetName = parcel.readString();
    }

    public void copy(ProjectFileBean projectFileBean) {
        this.fileType = projectFileBean.fileType;
        this.fileName = projectFileBean.fileName;
        this.orientation = projectFileBean.orientation;
        this.keyboardSetting = projectFileBean.keyboardSetting;
        this.options = projectFileBean.options;
        this.presetName = projectFileBean.presetName;
        this.theme = -1;
    }

    public boolean hasActivityOption(int i) {
        return ((this.options & 15) & i) == i;
    }

    public int getActivityOptions() {
        return this.options;
    }

    public void setActivityOptions(int i) {
        this.options = i;
    }

    public String getXmlName() {
        return getXmlName(this.fileName);
    }

    public String getActivityName() {
        if (this.fileType != 0) {
            return "";
        }
        return getActivityName(this.fileName);
    }

    public String getJavaName() {
        if (this.fileType != 0) {
            return "";
        }
        return getJavaName(this.fileName);
    }

    public String getDrawerName() {
        if (this.fileType != 0) {
            return "";
        }
        return getDrawerName(this.fileName);
    }

    public String getDrawerXmlName() {
        if (this.fileType != 0) {
            return "";
        }
        return getDrawerName() + ".xml";
    }

    public String getDrawersJavaName() {
        if (this.fileType == 2 && this.fileName.indexOf("_drawer_") >= 0) {
            return getJavaName(this.fileName.substring(8));
        }
        return "";
    }

    public static String getDrawerName(String str) {
        return "_drawer_" + str;
    }

    public static String getXmlName(String str) {
        return str.toLowerCase() + ".xml";
    }

    public static String getActivityName(String str) {
        String lowerCase = str.toLowerCase();
        String str2 = "";
        int i = 0;
        while (i < lowerCase.length()) {
            char charAt = lowerCase.charAt(i);
            if (charAt == '_' && i < lowerCase.length() - 1) {
                int i2 = i + 1;
                char charAt2 = lowerCase.charAt(i2);
                if (Character.isLowerCase(charAt2)) {
                    str2 = str2 + Character.toUpperCase(charAt2);
                    i = i2;
                } else {
                    str2 = str2 + charAt;
                }
            } else if (i == 0) {
                str2 = str2 + Character.toUpperCase(charAt);
            } else {
                str2 = str2 + charAt;
            }
            i++;
        }
        return str2 + "Activity";
    }

    public static String getJavaName(String str) {
        return getActivityName(str) + ".java";
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.fileType);
        parcel.writeString(this.fileName);
        parcel.writeInt(this.orientation);
        parcel.writeInt(this.keyboardSetting);
        parcel.writeInt(this.options);
        parcel.writeString(this.presetName);
    }

    public static Parcelable.Creator<ProjectFileBean> getCreator() {
        return CREATOR;
    }
}
