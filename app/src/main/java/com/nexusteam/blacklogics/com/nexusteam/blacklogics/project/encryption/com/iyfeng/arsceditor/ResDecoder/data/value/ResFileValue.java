
package com.iyfeng.arsceditor.ResDecoder.data.value;

import java.io.IOException;

import com.iyfeng.arsceditor.ResDecoder.ARSCCallBack;
import com.iyfeng.arsceditor.ResDecoder.GetResValues;
import com.iyfeng.arsceditor.ResDecoder.data.ResResource;

public class ResFileValue extends ResIntBasedValue implements GetResValues {
    private final String mPath;

    public ResFileValue(String path, int rawIntValue) {
        super(rawIntValue);
        this.mPath = path;
    }

    public String getPath() {
        return mPath;
    }

    @Override
    public void getResValues(ARSCCallBack back, ResResource res) throws IOException {
        back.back(res.getConfig().toString(), res.getResSpec().getType().getName(), res.getResSpec().getName(),
                getStrippedPath());
    }

    public String getStrippedPath() throws IOException {
        if (!mPath.startsWith("res/")) {
            throw new IOException("File path does not start with \"res/\": " + mPath);
        }
        return mPath;
    }

    @Override
    public String toString() {
        return mPath;
    }
}
