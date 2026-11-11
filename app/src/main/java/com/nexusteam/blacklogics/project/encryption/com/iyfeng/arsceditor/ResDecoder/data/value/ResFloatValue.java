
package com.iyfeng.arsceditor.ResDecoder.data.value;

public class ResFloatValue extends ResScalarValue {
    private final float mValue;

    public ResFloatValue(float value, int rawIntValue, String rawValue) {
        super("float", rawIntValue, rawValue);
        this.mValue = value;
    }

    @Override
    protected String encodeAsResValue() {
        return String.valueOf(mValue);
    }

    public float getValue() {
        return mValue;
    }
}
