
package com.iyfeng.arsceditor.ResDecoder;

import java.io.IOException;

import com.iyfeng.arsceditor.ResDecoder.data.ResResource;


public interface GetResValues {
    public void getResValues(ARSCCallBack back, ResResource res) throws IOException;
}
