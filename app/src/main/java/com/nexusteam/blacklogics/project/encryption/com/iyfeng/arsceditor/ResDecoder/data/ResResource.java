
package com.iyfeng.arsceditor.ResDecoder.data;

import java.io.IOException;

import com.iyfeng.arsceditor.ResDecoder.data.value.ResValue;

public class ResResource {
    private final ResType mConfig;
    private final ResResSpec mResSpec;
    private final ResValue mValue;

    public ResResource(ResType config, ResResSpec spec, ResValue value) {
        this.mConfig = config;
        this.mResSpec = spec;
        this.mValue = value;
    }

    public ResType getConfig() {
        return mConfig;
    }

    public String getFilePath() {
        return mResSpec.getType().getName() + mConfig.getFlags().getQualifiers() + "/" + mResSpec.getName();
    }

    public ResResSpec getResSpec() {
        return mResSpec;
    }

    public ResValue getValue() {
        return mValue;
    }

    public void replace(ResValue value) throws IOException {
        ResResource res = new ResResource(mConfig, mResSpec, value);
        mConfig.addResource(res, true);
        mResSpec.addResource(res, true);
    }

    @Override
    public String toString() {
        return getFilePath();
    }
}
