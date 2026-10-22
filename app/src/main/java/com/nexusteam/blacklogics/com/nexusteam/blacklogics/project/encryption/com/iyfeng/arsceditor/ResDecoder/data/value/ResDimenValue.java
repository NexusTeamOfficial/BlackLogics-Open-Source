
package com.iyfeng.arsceditor.ResDecoder.data.value;


public class ResDimenValue extends ResIntValue {
    public ResDimenValue(int value, String rawValue) {
        super(value, rawValue, "dimen");
    }

    @Override
    protected String encodeAsResValue() {
        return TypedValue.coerceToString(TypedValue.TYPE_DIMENSION, mValue);
    }
}
