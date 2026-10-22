
package com.iyfeng.arsceditor.ResDecoder.data.value;

public class ResIntBasedValue extends ResValue {
    private int mRawIntValue;

    protected ResIntBasedValue(int rawIntValue) {
        mRawIntValue = rawIntValue;
    }

    public int getRawIntValue() {
        return mRawIntValue;
    }
}
