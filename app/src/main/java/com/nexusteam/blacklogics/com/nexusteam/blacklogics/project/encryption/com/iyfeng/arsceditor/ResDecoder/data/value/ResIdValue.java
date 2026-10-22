
package com.iyfeng.arsceditor.ResDecoder.data.value;

import java.io.IOException;

import com.iyfeng.arsceditor.ResDecoder.ARSCCallBack;
import com.iyfeng.arsceditor.ResDecoder.GetResValues;
import com.iyfeng.arsceditor.ResDecoder.data.ResResource;

public class ResIdValue extends ResValue implements GetResValues {
    @Override
    public void getResValues(ARSCCallBack back, ResResource res) throws IOException {
        back.back(res.getConfig().toString(), res.getResSpec().getType().getName(), res.getResSpec().getName(),
                res.getValue().toString());
    }
}
