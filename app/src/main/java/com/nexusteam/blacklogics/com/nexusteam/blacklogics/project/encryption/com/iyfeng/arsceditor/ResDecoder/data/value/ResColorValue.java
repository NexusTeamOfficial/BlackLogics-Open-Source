
package com.iyfeng.arsceditor.ResDecoder.data.value;

public class ResColorValue extends ResIntValue {
    public ResColorValue(int value, String rawValue) {
        super(value, rawValue, "color");
    }

    @Override
    protected String encodeAsResValue() {
        return String.format("#%08x", mValue);
    }
}