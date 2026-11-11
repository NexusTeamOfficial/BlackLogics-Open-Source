
package com.iyfeng.arsceditor.ResDecoder.data.value;

import java.io.IOException;

import com.iyfeng.arsceditor.ResDecoder.ARSCCallBack;
import com.iyfeng.arsceditor.ResDecoder.GetResValues;
import com.iyfeng.arsceditor.ResDecoder.data.ResResource;

public abstract class ResScalarValue extends ResIntBasedValue implements GetResValues {
    protected final String mRawValue;
    protected final String mType;

    protected ResScalarValue(String type, int rawIntValue, String rawValue) {
        super(rawIntValue);
        mType = type;
        mRawValue = rawValue;
    }

    protected abstract String encodeAsResValue() throws IOException;

    public String encodeAsResXmlItemValue() throws IOException {
        return encodeResValue();
    }

    public String encodeResValue() throws IOException {
        if (mRawValue != null) {
            return mRawValue;
        }
        return encodeAsResValue();
    }

    @Override
    public void getResValues(ARSCCallBack back, ResResource res) throws IOException {
        String type = res.getResSpec().getType().getName();

        String body = encodeAsResValue();
        back.back(res.getConfig().toString(), type, res.getResSpec().getName(), body);
    }

    public String getType() {
        return mType;
    }
}
