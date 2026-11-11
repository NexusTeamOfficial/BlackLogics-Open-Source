
package com.iyfeng.arsceditor.ResDecoder.data.value;


public class ResFractionValue extends ResIntValue {
    public ResFractionValue(int value, String rawValue) {
        super(value, rawValue, "fraction");
    }

    @Override
    protected String encodeAsResValue() {
        return TypedValue.coerceToString(TypedValue.TYPE_FRACTION, mValue);
    }
}
