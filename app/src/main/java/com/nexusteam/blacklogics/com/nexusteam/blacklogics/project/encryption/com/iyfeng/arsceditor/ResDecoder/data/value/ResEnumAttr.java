
package com.iyfeng.arsceditor.ResDecoder.data.value;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.iyfeng.arsceditor.ResDecoder.ARSCCallBack;
import com.iyfeng.arsceditor.ResDecoder.IO.Duo;
import com.iyfeng.arsceditor.ResDecoder.data.ResResource;

public class ResEnumAttr extends ResAttr {
    private final Duo<ResReferenceValue, ResIntValue>[] mItems;

    private final Map<Integer, String> mItemsCache = new HashMap<Integer, String>();

    ResEnumAttr(ResReferenceValue parent, int type, Integer min, Integer max, Boolean l10n,
            Duo<ResReferenceValue, ResIntValue>[] items) {
        super(parent, type, min, max, l10n);
        mItems = items;
    }

    @Override
    public String convertToResXmlFormat(ResScalarValue value) throws IOException {
        if (value instanceof ResIntValue) {
            String ret = String.valueOf(value);
            if (ret != null) {
                return ret;
            }
        }
        return super.convertToResXmlFormat(value);
    }

    @Override
    protected void serializeBody(ARSCCallBack back, ResResource res) throws IOException, IOException {
        for (Duo<ResReferenceValue, ResIntValue> duo : mItems) {
            int intVal = duo.m2.getValue();
            back.back(res.getConfig().toString(), "enum", res.getResSpec().getName(), String.valueOf(intVal));
        }
    }
}
