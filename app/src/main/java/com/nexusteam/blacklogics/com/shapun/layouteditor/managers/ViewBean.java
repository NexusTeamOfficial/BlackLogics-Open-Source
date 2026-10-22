package com.shapun.layouteditor.managers;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewBean implements Parcelable {
    

    @Expose
    @SerializedName("id")
    private String id;
    
    @Expose
    @SerializedName("type")
    private String type;
    
    @Expose
    @SerializedName("className")
    private String className;
    
    @Expose
    @SerializedName("parentId")
    private String parentId;
    
    @Expose
    @SerializedName("index")
    private int index;
    
    @Expose
    @SerializedName("attributes")
    private HashMap<String, String> attributes;
    
    @Expose
    @SerializedName("children")
    private ArrayList<ViewBean> children;
    
    @Expose
    @SerializedName("isAddedToLayout")
    private boolean isAddedToLayout;
    
    @Expose
    @SerializedName("timestamp")
    private long timestamp;
    

    private transient View view;
    private transient ViewGroup parent;


    public ViewBean() {
        this.attributes = new HashMap<>();
        this.children = new ArrayList<>();
        this.timestamp = System.currentTimeMillis();
        this.isAddedToLayout = false;
    }
    
    public ViewBean(String type, String id) {
        this();
        this.type = type;
        this.id = id;
        this.className = "android.widget." + type;
    }
    
    public ViewBean(String className, String type, String id) {
        this();
        this.className = className;
        this.type = type;
        this.id = id;
    }


    protected ViewBean(Parcel in) {
        id = in.readString();
        type = in.readString();
        className = in.readString();
        parentId = in.readString();
        index = in.readInt();
        attributes = in.readHashMap(HashMap.class.getClassLoader());
        children = in.createTypedArrayList(ViewBean.CREATOR);
        isAddedToLayout = in.readByte() != 0;
        timestamp = in.readLong();
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }

    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }

    public HashMap<String, String> getAttributes() { return attributes; }
    public void setAttributes(HashMap<String, String> attributes) { this.attributes = attributes; }

    public ArrayList<ViewBean> getChildren() { return children; }
    public void setChildren(ArrayList<ViewBean> children) { this.children = children; }

    public boolean isAddedToLayout() { return isAddedToLayout; }
    public void setAddedToLayout(boolean addedToLayout) { isAddedToLayout = addedToLayout; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public View getView() { return view; }
    public void setView(View view) { this.view = view; }

    public ViewGroup getParent() { return parent; }
    public void setParent(ViewGroup parent) { this.parent = parent; }


    public void addAttribute(String key, String value) {
        if (attributes == null) attributes = new HashMap<>();
        attributes.put(key, value);
    }
    
    public String getAttribute(String key) {
        return attributes != null ? attributes.get(key) : null;
    }
    
    public void removeAttribute(String key) {
        if (attributes != null) attributes.remove(key);
    }
    
    public void addChild(ViewBean child) {
        if (children == null) children = new ArrayList<>();
        children.add(child);
        child.setParentId(this.id);
    }
    
    public void removeChild(ViewBean child) {
        if (children != null) children.remove(child);
    }
    
    public boolean isValid() {
        return id != null && !id.isEmpty() && type != null && !type.isEmpty();
    }
    
    public void markAsAdded(ViewGroup parent, int index) {
        this.parent = parent;
        this.index = index;
        this.isAddedToLayout = true;
        if (parent != null) {
            this.parentId = getParentIdFromView(parent);
        }
    }
    
    private String getParentIdFromView(ViewGroup parent) {

        return "parent_" + parent.hashCode(); // Temporary implementation
    }
    
    public void removeFromLayout() {
        if (parent != null && view != null) {
            parent.removeView(view);
        }
        this.parent = null;
        this.isAddedToLayout = false;
        this.parentId = null;
    }
    

    public ViewBean deepClone() {
        ViewBean clone = new ViewBean();
        clone.id = this.id;
        clone.type = this.type;
        clone.className = this.className;
        clone.parentId = this.parentId;
        clone.index = this.index;
        clone.isAddedToLayout = this.isAddedToLayout;
        clone.timestamp = this.timestamp;
        
        if (this.attributes != null) {
            clone.attributes = new HashMap<>(this.attributes);
        }
        
        if (this.children != null) {
            clone.children = new ArrayList<>();
            for (ViewBean child : this.children) {
                clone.children.add(child.deepClone());
            }
        }
        
        return clone;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(className);
        dest.writeString(parentId);
        dest.writeInt(index);
        dest.writeMap(attributes);
        dest.writeTypedList(children);
        dest.writeByte((byte) (isAddedToLayout ? 1 : 0));
        dest.writeLong(timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ViewBean> CREATOR = new Creator<ViewBean>() {
        @Override
        public ViewBean createFromParcel(Parcel in) {
            return new ViewBean(in);
        }

        @Override
        public ViewBean[] newArray(int size) {
            return new ViewBean[size];
        }
    };

    @Override
    public String toString() {
        return "ViewBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", className='" + className + '\'' +
                ", parentId='" + parentId + '\'' +
                ", index=" + index +
                ", attributes=" + attributes +
                ", children=" + children +
                ", isAddedToLayout=" + isAddedToLayout +
                ", timestamp=" + timestamp +
                '}';
    }
}