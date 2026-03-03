package com.nexusteam.internal.beans;

import com.nexusteam.internal.jo;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.core.view.ViewCompat;
import com.google.gson.annotations.Expose;

public class TextBean extends jo implements Parcelable {
    public static final Parcelable.Creator<TextBean> CREATOR = new Parcelable.Creator<TextBean>() {
        public TextBean createFromParcel(Parcel parcel) {
            return new TextBean(parcel);
        }

        public TextBean[] newArray(int i) {
            return new TextBean[i];
        }
    };
    public static int IME_OPTION_DONE = 6;
    public static int IME_OPTION_GO = 2;
    public static int IME_OPTION_NEXT = 5;
    public static int IME_OPTION_NONE = 1;
    public static int IME_OPTION_NORMAL = 0;
    public static int IME_OPTION_SEARCH = 3;
    public static int IME_OPTION_SEND = 4;
    public static int INPUT_TYPE_NUMBER_DECIMAL = 8194;
    public static int INPUT_TYPE_NUMBER_SIGNED = 4098;
    public static int INPUT_TYPE_NUMBER_SIGNED_DECIMAL = 12290;
    public static int INPUT_TYPE_PASSWORD = 129;
    public static int INPUT_TYPE_PHONE = 3;
    public static int INPUT_TYPE_TEXT = 1;
    public static String TEXT_FONT = "default_font";
    public static int TEXT_TYPE_BOLD = 1;
    public static int TEXT_TYPE_BOLDITALIC = 3;
    public static int TEXT_TYPE_ITALIC = 2;
    public static int TEXT_TYPE_NORMAL;
    @Expose
    public String hint;
    @Expose
    public int hintColor;
    @Expose
    public int imeOption;
    @Expose
    public int inputType;
    @Expose
    public int line;
    @Expose
    public int singleLine;
    @Expose
    public String text;
    @Expose
    public int textColor;
    @Expose
    public String textFont;
    @Expose
    public int textSize;
    @Expose
    public int textType;

    public int describeContents() {
        return 0;
    }

    public void print() {
    }

    public TextBean() {
        this.text = "";
        this.textSize = 12;
        this.textType = TEXT_TYPE_NORMAL;
        this.textColor = ViewCompat.MEASURED_STATE_MASK;
        this.hint = "";
        this.hintColor = -10453621;
        this.singleLine = 0;
        this.line = 0;
        this.inputType = INPUT_TYPE_TEXT;
        this.imeOption = IME_OPTION_NORMAL;
        this.textFont = TEXT_FONT;
    }

    public TextBean(Parcel parcel) {
        this.text = parcel.readString();
        this.textSize = parcel.readInt();
        this.textColor = parcel.readInt();
        this.textType = parcel.readInt();
        this.textFont = parcel.readString();
        this.hint = parcel.readString();
        this.hintColor = parcel.readInt();
        this.singleLine = parcel.readInt();
        this.line = parcel.readInt();
        this.inputType = parcel.readInt();
        this.imeOption = parcel.readInt();
    }

    public void copy(TextBean textBean) {
        this.text = textBean.text;
        this.textSize = textBean.textSize;
        this.textColor = textBean.textColor;
        this.textType = textBean.textType;
        this.textFont = textBean.textFont;
        this.hint = textBean.hint;
        this.hintColor = textBean.hintColor;
        this.singleLine = textBean.singleLine;
        this.line = textBean.line;
        this.inputType = textBean.inputType;
        this.imeOption = textBean.imeOption;
    }

    public boolean isEqual(TextBean textBean) {
        if (this.text != null) {
            if (textBean.text == null || !this.text.equals(textBean.text)) {
                return false;
            }
        } else if (textBean.text != null) {
            return false;
        }
        if (this.textSize != textBean.textSize || this.textColor != textBean.textColor || this.textType != textBean.textType) {
            return false;
        }
        if (this.textFont != null) {
            if (textBean.textFont == null || !this.textFont.equals(textBean.textFont)) {
                return false;
            }
        } else if (textBean.textFont != null) {
            return false;
        }
        if (this.hint != null) {
            if (textBean.hint == null || !this.hint.equals(textBean.hint)) {
                return false;
            }
        } else if (textBean.hint != null) {
            return false;
        }
        if (this.hintColor == textBean.hintColor && this.singleLine == textBean.singleLine && this.line == textBean.line && this.inputType == textBean.inputType && this.imeOption == textBean.imeOption) {
            return true;
        }
        return false;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.text);
        parcel.writeInt(this.textSize);
        parcel.writeInt(this.textColor);
        parcel.writeInt(this.textType);
        parcel.writeString(this.textFont);
        parcel.writeString(this.hint);
        parcel.writeInt(this.hintColor);
        parcel.writeInt(this.singleLine);
        parcel.writeInt(this.line);
        parcel.writeInt(this.inputType);
        parcel.writeInt(this.imeOption);
    }

    public static Parcelable.Creator<TextBean> getCreator() {
        return CREATOR;
    }
}
