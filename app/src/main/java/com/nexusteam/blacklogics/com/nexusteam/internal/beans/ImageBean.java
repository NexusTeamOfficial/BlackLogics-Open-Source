package com.nexusteam.internal.beans;

import com.nexusteam.internal.jo;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import com.google.gson.annotations.Expose;

public class ImageBean extends jo implements Parcelable {
    public static final Parcelable.Creator<ImageBean> CREATOR = new Parcelable.Creator<ImageBean>() {
        public ImageBean createFromParcel(Parcel parcel) {
            return new ImageBean(parcel);
        }

        public ImageBean[] newArray(int i) {
            return new ImageBean[i];
        }
    };
    public static final String SCALE_TYPE_CENTER = ImageView.ScaleType.CENTER.name();
    public static final String SCALE_TYPE_CENTER_CROP = ImageView.ScaleType.CENTER_CROP.name();
    public static final String SCALE_TYPE_CENTER_INSIDE = ImageView.ScaleType.CENTER_INSIDE.name();
    public static final String SCALE_TYPE_FIT_CENTER = ImageView.ScaleType.FIT_CENTER.name();
    public static final String SCALE_TYPE_FIT_END = ImageView.ScaleType.FIT_END.name();
    public static final String SCALE_TYPE_FIT_START = ImageView.ScaleType.FIT_START.name();
    public static final String SCALE_TYPE_FIT_XY = ImageView.ScaleType.FIT_XY.name();
    @Expose
    public String resName;
    @Expose
    public int rotate;
    @Expose
    public String scaleType;

    public int describeContents() {
        return 0;
    }

    public void print() {
    }

    public ImageBean() {
        this.scaleType = ImageView.ScaleType.CENTER.name();
        this.rotate = 0;
    }

    public ImageBean(Parcel parcel) {
        this.resName = parcel.readString();
        this.scaleType = parcel.readString();
        this.rotate = parcel.readInt();
    }

    public void copy(ImageBean imageBean) {
        this.resName = imageBean.resName;
        this.scaleType = imageBean.scaleType;
        this.rotate = imageBean.rotate;
    }

    public boolean isEqual(ImageBean imageBean) {
        if (this.resName != null) {
            if (imageBean.resName == null || !this.resName.equals(imageBean.resName)) {
                return false;
            }
        } else if (imageBean.resName != null) {
            return false;
        }
        if (this.scaleType != null) {
            if (imageBean.scaleType == null || !this.scaleType.equals(imageBean.scaleType)) {
                return false;
            }
        } else if (imageBean.scaleType != null) {
            return false;
        }
        if (this.rotate != imageBean.rotate) {
            return false;
        }
        return true;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.resName);
        parcel.writeString(this.scaleType);
        parcel.writeInt(this.rotate);
    }

    public static Parcelable.Creator<ImageBean> getCreator() {
        return CREATOR;
    }
}
