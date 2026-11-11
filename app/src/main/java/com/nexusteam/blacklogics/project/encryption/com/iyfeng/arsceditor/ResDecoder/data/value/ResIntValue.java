
package com.iyfeng.arsceditor.ResDecoder.data.value;

import java.io.IOException;

public class ResIntValue extends ResScalarValue {
    protected final int mValue;
    private int type;

    public ResIntValue(int value, String rawValue, int type) {
        this(value, rawValue, "integer");
        this.type = type;
    }

    public ResIntValue(int value, String rawValue, String type) {
        super(type, value, rawValue);
        this.mValue = value;
    }

    @Override
    protected String encodeAsResValue() throws IOException {
        return TypedValue.coerceToString(type, mValue);
    }

    public int getValue() {
        return mValue;
    }
}