
package com.iyfeng.arsceditor.ResDecoder.data.value;

import java.io.IOException;
import java.util.Arrays;

import com.iyfeng.arsceditor.ResDecoder.ARSCCallBack;
import com.iyfeng.arsceditor.ResDecoder.GetResValues;
import com.iyfeng.arsceditor.ResDecoder.IO.Duo;
import com.iyfeng.arsceditor.ResDecoder.data.ResResource;

public class ResArrayValue extends ResBagValue implements GetResValues {
    public static final int BAG_KEY_ARRAY_START = 0x02000000;

    private final String AllowedArrayTypes[] = { "string", "integer" };

    private final ResScalarValue[] mItems;

    ResArrayValue(ResReferenceValue parent, Duo<Integer, ResScalarValue>[] items) {
        super(parent);

        mItems = new ResScalarValue[items.length];
        for (int i = 0; i < items.length; i++) {
            mItems[i] = items[i].m2;
        }
    }

    public ResArrayValue(ResReferenceValue parent, ResScalarValue[] items) {
        super(parent);
        mItems = items;
    }

    @Override
    public void getResValues(ARSCCallBack back, ResResource res) throws IOException {
        String type = getType();
        type = (type == null ? "" : type + "-") + "array";

        for (int i = 0; i < mItems.length; i++) {
            back.back(res.getConfig().toString(), type, res.getResSpec().getName(), mItems[i].encodeAsResValue());
        }
    }

    public String getType() throws IOException {
        if (mItems.length == 0) {
            return null;
        }
        String type = mItems[0].getType();
        for (int i = 0; i < mItems.length; i++) {
            if (mItems[i].encodeAsResXmlItemValue().startsWith("@string")) {
                return "string";
            } else if (mItems[i].encodeAsResXmlItemValue().startsWith("@drawable")) {
                return null;
            } else if (mItems[i].encodeAsResXmlItemValue().startsWith("@integer")) {
                return "integer";
            } else if (!"string".equals(type) && !"integer".equals(type)) {
                return null;
            } else if (!type.equals(mItems[i].getType())) {
                return null;
            }
        }
        if (!Arrays.asList(AllowedArrayTypes).contains(type)) {
            return "string";
        }
        return type;
    }
}
