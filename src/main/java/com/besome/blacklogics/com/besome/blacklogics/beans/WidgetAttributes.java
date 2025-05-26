package com.besome.blacklogics.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.HashMap;
import java.util.Map;

// Parcelable class to save custom attributes
public class WidgetAttributes implements Parcelable {
    public Map<String, Map<String, String>> attributes;
    
    public WidgetAttributes(Map<String, Map<String, String>> attributes) {
        this.attributes = attributes;
    }

    protected WidgetAttributes(Parcel in) {
        attributes = new HashMap<>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String widgetId = in.readString();
            Map<String, String> attrMap = new HashMap<>();
            int attrSize = in.readInt();
            for (int j = 0; j < attrSize; j++) {
                String key = in.readString();
                String value = in.readString();
                attrMap.put(key, value);
            }
            attributes.put(widgetId, attrMap);
        }
    }

    public static final Creator<WidgetAttributes> CREATOR = new Creator<WidgetAttributes>() {
        @Override
        public WidgetAttributes createFromParcel(Parcel in) {
            return new WidgetAttributes(in);
        }

        @Override
        public WidgetAttributes[] newArray(int size) {
            return new WidgetAttributes[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(attributes.size());
        for (Map.Entry<String, Map<String, String>> entry : attributes.entrySet()) {
            dest.writeString(entry.getKey());
            Map<String, String> attrMap = entry.getValue();
            dest.writeInt(attrMap.size());
            for (Map.Entry<String, String> attr : attrMap.entrySet()) {
                dest.writeString(attr.getKey());
                dest.writeString(attr.getValue());
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Map<String, Map<String, String>> getAttributes() {
        return attributes;
    }
}