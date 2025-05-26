package com.besome.blacklogics.logic.editor;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
//import com.besome.sketch.lib.base.BaseBean;
import java.util.ArrayList;

public class BlockBean extends BaseBean implements Parcelable {
    public static final Creator<BlockBean> CREATOR = new Creator<BlockBean>() {

        public BlockBean createFromParcel(Parcel parcel) {
            return new BlockBean(parcel);
        }

        public BlockBean[] newArray(int i) {
            return new BlockBean[i];
        }
    };
    public int color;
    public String id;
    public int nextBlock;
    public String opCode;
    public ArrayList<String> paramTypes;
    public ArrayList<String> parameters;
    public String spec;
    public int subStack1;
    public int subStack2;
    public String type;

    public BlockBean() {
        this.parameters = new ArrayList();
        this.paramTypes = new ArrayList();
        this.subStack1 = -1;
        this.subStack2 = -1;
        this.nextBlock = -1;
    }

    public BlockBean(Parcel parcel) {
        this.id = parcel.readString();
        this.spec = parcel.readString();
        this.type = parcel.readString();
        this.opCode = parcel.readString();
        this.color = parcel.readInt();
        this.parameters = (ArrayList) parcel.readSerializable();
        this.paramTypes = (ArrayList) parcel.readSerializable();
        this.subStack1 = parcel.readInt();
        this.subStack2 = parcel.readInt();
        this.nextBlock = parcel.readInt();
    }

    public BlockBean(String str, String str2, String str3, String str4) {
        this.id = str;
        this.spec = str2;
        this.type = str3;
        this.opCode = str4;
        this.parameters = new ArrayList();
        this.paramTypes = new ArrayList();
        this.subStack1 = -1;
        this.subStack2 = -1;
        this.nextBlock = -1;
    }

    public static Creator<BlockBean> getCreator() {
        return CREATOR;
    }

    public void copy(BlockBean blockBean) {
        this.id = blockBean.id;
        this.spec = blockBean.spec;
        this.type = blockBean.type;
        this.opCode = blockBean.opCode;
        this.color = blockBean.color;
        this.parameters = new ArrayList(blockBean.parameters);
        this.paramTypes = new ArrayList(blockBean.paramTypes);
        this.subStack1 = blockBean.subStack1;
        this.subStack2 = blockBean.subStack2;
        this.nextBlock = blockBean.nextBlock;
    }

    public int describeContents() {
        return 0;
    }

    public void print() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.spec);
        parcel.writeString(this.type);
        parcel.writeString(this.opCode);
        parcel.writeInt(this.color);
        parcel.writeSerializable(this.parameters);
        parcel.writeSerializable(this.paramTypes);
        parcel.writeInt(this.subStack1);
        parcel.writeInt(this.subStack2);
        parcel.writeInt(this.nextBlock);
    }
}
