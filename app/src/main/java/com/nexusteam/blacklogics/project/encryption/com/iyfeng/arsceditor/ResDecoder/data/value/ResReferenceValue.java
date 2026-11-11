
package com.iyfeng.arsceditor.ResDecoder.data.value;

import java.io.IOException;

import com.iyfeng.arsceditor.ResDecoder.data.ResPackage;

public class ResReferenceValue extends ResIntValue {
    public ResReferenceValue(ResPackage package_, int value, String rawValue) {
        this(package_, value, rawValue, false);
    }

    public ResReferenceValue(ResPackage package_, int value, String rawValue, boolean theme) {
        super(value, rawValue, "reference");
    }

    @Override
    protected String encodeAsResValue() throws IOException {
        return String.valueOf(mValue);
    }

    public boolean isNull() {
        return mValue == 0;
    }
}
