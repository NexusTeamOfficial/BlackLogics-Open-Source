
package com.iyfeng.arsceditor.ResDecoder.data;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class ResTypeSpec {
    private final int mEntryCount;
    private final byte mId;

    private final String mName;
    private final Map<String, ResResSpec> mResSpecs = new LinkedHashMap<String, ResResSpec>();

    public ResTypeSpec(String name, ResTable resTable, ResPackage package_, byte id, int entryCount) {
        this.mName = name;
        this.mId = id;
        this.mEntryCount = entryCount;
    }

    public void addResSpec(ResResSpec spec) throws IOException {
        if (mResSpecs.put(spec.getName(), spec) != null) {


        }
    }

    public int getEntryCount() {
        return mEntryCount;
    }

    public byte getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public ResResSpec getResSpec(String name) throws IOException {
        ResResSpec spec = mResSpecs.get(name);
        if (spec == null) {


        }
        return spec;
    }

    public boolean isString() {
        return mName.equalsIgnoreCase("string");
    }

    public Set<ResResSpec> listResSpecs() {
        return new LinkedHashSet<ResResSpec>(mResSpecs.values());
    }

    public void removeResSpec(ResResSpec spec) throws IOException {
        mResSpecs.remove(spec.getName());
    }

    @Override
    public String toString() {
        return mName;
    }
}
