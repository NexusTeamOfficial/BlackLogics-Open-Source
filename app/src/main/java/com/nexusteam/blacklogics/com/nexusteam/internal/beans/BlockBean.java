package com.nexusteam.internal.beans;

import com.nexusteam.internal.ev;
import com.nexusteam.internal.hc;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;

public class BlockBean extends SelectableBean implements Parcelable {
    public static final Parcelable.Creator<BlockBean> CREATOR = new Parcelable.Creator<BlockBean>() {
        public BlockBean createFromParcel(Parcel parcel) {
            return new BlockBean(parcel);
        }
        
        public BlockBean[] newArray(int i) {
            return new BlockBean[i];
        }
    };
    private hc classInfo;
    @Expose
    public int color;
    @Expose
    public String id;
    @Expose
    public int nextBlock;
    @Expose
    public String opCode;
    private ArrayList<hc> paramClassInfo;
    @Expose
    public ArrayList<String> parameters;
    @Expose
    public String spec;
    @Expose
    public int subStack1;
    @Expose
    public int subStack2;
    @Expose
    public String type;
    @Expose
    public String typeName;
    
    public int describeContents() {
        return 0;
    }
    
    public void print() {
    }
    
    public BlockBean() {
        this.parameters = new ArrayList<>();
        this.subStack1 = -1;
        this.subStack2 = -1;
        this.nextBlock = -1;
    }
    
    public BlockBean(String str, String str2, String str3, String str4) {
        this(str, str2, str3, "", str4);
    }
    
    public BlockBean(String str, String str2, String str3, String str4, String str5) {
        this.id = str;
        this.spec = str2;
        this.type = str3;
        this.typeName = str4;
        this.opCode = str5;
        this.parameters = new ArrayList<>();
        this.subStack1 = -1;
        this.subStack2 = -1;
        this.nextBlock = -1;
        buildClassInfo();
    }
    
    public BlockBean(Parcel parcel) {
        this.id = parcel.readString();
        this.spec = parcel.readString();
        this.type = parcel.readString();
        this.typeName = parcel.readString();
        this.opCode = parcel.readString();
        this.color = parcel.readInt();
        this.parameters = (ArrayList) parcel.readSerializable();
        this.subStack1 = parcel.readInt();
        this.subStack2 = parcel.readInt();
        this.nextBlock = parcel.readInt();
        buildClassInfo();
    }
    
    public boolean isEqual(BlockBean blockBean) {
        if (blockBean == null) {
            return false;
        }
        if (this.id != null && !this.id.equals(blockBean.id)) {
            return false;
        }
        if ((this.spec != null && !this.spec.equals(blockBean.spec)) || !this.type.equals(blockBean.type)) {
            return false;
        }
        if ((this.typeName != null && !this.typeName.equals(blockBean.typeName)) || !this.opCode.equals(blockBean.opCode) || this.color != blockBean.color || this.subStack1 != blockBean.subStack1 || this.subStack2 != blockBean.subStack2 || this.nextBlock != blockBean.nextBlock) {
            return false;
        }
        if (this.parameters != null && this.parameters.size() != blockBean.parameters.size()) {
            return false;
        }
        for (int i = 0; i < this.parameters.size(); i++) {
            String str = this.parameters.get(i);
            String str2 = blockBean.parameters.get(i);
            if (str != null && !str.equals(str2)) {
                return false;
            }
        }
        return true;
    }
    
    public void writeToStream(java.io.DataOutputStream out) throws java.io.IOException {
        out.writeUTF(this.id == null ? "" : this.id);
        out.writeUTF(this.spec == null ? "" : this.spec);
        out.writeUTF(this.type == null ? "" : this.type);
        out.writeUTF(this.typeName == null ? "" : this.typeName);
        out.writeUTF(this.opCode == null ? "" : this.opCode);
        out.writeInt(this.color);
        out.writeInt(this.subStack1);
        out.writeInt(this.subStack2);
        out.writeInt(this.nextBlock);
        

        if (this.parameters == null) {
            out.writeInt(0);
        } else {
            out.writeInt(this.parameters.size());
            for (String param : this.parameters) {
                out.writeUTF(param == null ? "" : param);
            }
        }
    }
    

    public void readFromStream(java.io.DataInputStream in) throws java.io.IOException {
        this.id = in.readUTF();
        this.spec = in.readUTF();
        this.type = in.readUTF();
        this.typeName = in.readUTF();
        this.opCode = in.readUTF();
        this.color = in.readInt();
        this.subStack1 = in.readInt();
        this.subStack2 = in.readInt();
        this.nextBlock = in.readInt();
        

        int size = in.readInt();
        this.parameters = new java.util.ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.parameters.add(in.readUTF());
        }
        

        buildClassInfo();
    }
    
    public hc getClassInfo() {
        if (this.classInfo == null) {
            buildClassInfo();
        }
        return this.classInfo;
    }
    
    public ArrayList<hc> getParamClassInfo() {
        if (this.paramClassInfo == null) {
            buildClassInfo();
        }
        return this.paramClassInfo;
    }
    
    private void buildClassInfo() {
        this.classInfo = ev.a(this.type, this.typeName);
        this.paramClassInfo = ev.b(this.spec);
    }
    
    public void copy(BlockBean blockBean) {
        this.id = blockBean.id;
        this.spec = blockBean.spec;
        this.type = blockBean.type;
        this.typeName = blockBean.typeName;
        this.opCode = blockBean.opCode;
        this.color = blockBean.color;
        this.parameters = new ArrayList<>(blockBean.parameters);
        this.subStack1 = blockBean.subStack1;
        this.subStack2 = blockBean.subStack2;
        this.nextBlock = blockBean.nextBlock;
        buildClassInfo();
    }
    
    public BlockBean clone() {
        BlockBean blockBean = new BlockBean();
        blockBean.copy(this);
        return blockBean;
    }
    
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.spec);
        parcel.writeString(this.type);
        parcel.writeString(this.typeName);
        parcel.writeString(this.opCode);
        parcel.writeInt(this.color);
        parcel.writeSerializable(this.parameters);
        parcel.writeInt(this.subStack1);
        parcel.writeInt(this.subStack2);
        parcel.writeInt(this.nextBlock);
    }
    
    public static Parcelable.Creator<BlockBean> getCreator() {
        return CREATOR;
    }
}
