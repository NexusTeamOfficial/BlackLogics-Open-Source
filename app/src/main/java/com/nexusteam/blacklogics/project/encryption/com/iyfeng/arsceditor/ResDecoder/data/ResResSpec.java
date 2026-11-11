
package com.iyfeng.arsceditor.ResDecoder.data;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ResResSpec {
    private final ResID mId;
    private final String mName;
    private final ResPackage mPackage;
    private final Map<ResConfigFlags, ResResource> mResources = new LinkedHashMap<ResConfigFlags, ResResource>();
    private final ResTypeSpec mType;

    public ResResSpec(ResID id, String name, ResPackage pkg, ResTypeSpec type) {
        this.mId = id;
        this.mName = (name.equals("") ? ("APKTOOL_DUMMYVAL_" + id.toString()) : name);
        this.mPackage = pkg;
        this.mType = type;
    }

    public void addResource(ResResource res) throws IOException {
        addResource(res, false);
    }

    public void addResource(ResResource res, boolean overwrite) throws IOException {
        ResConfigFlags flags = res.getConfig().getFlags();
        if (mResources.put(flags, res) != null && !overwrite) {


        }
    }

    public ResResource getDefaultResource() throws IOException {
        return getResource(new ResConfigFlags());
    }

    public String getFullName() {
        return getFullName(false, false);
    }

    public String getFullName(boolean excludePackage, boolean excludeType) {
        return (excludePackage ? "" : getPackage().getName() + ":") + (excludeType ? "" : getType().getName() + "/")
                + getName();
    }

    public String getFullName(ResPackage relativeToPackage, boolean excludeType) {
        return getFullName(getPackage().equals(relativeToPackage), excludeType);
    }

    public ResID getId() {
        return mId;
    }

    public String getName() {
        return mName.replace("\"", "q");
    }

    public ResPackage getPackage() {
        return mPackage;
    }

    public ResResource getResource(ResConfigFlags config) throws IOException {
        ResResource res = mResources.get(config);
        if (res == null) {


        }
        return res;
    }

    public ResResource getResource(ResType config) throws IOException {
        return getResource(config.getFlags());
    }

    public ResTypeSpec getType() {
        return mType;
    }

    public boolean hasDefaultResource() {
        return mResources.containsKey(new ResConfigFlags());
    }

    private boolean hasResource(ResConfigFlags flags) {
        return mResources.containsKey(flags);
    }

    public boolean hasResource(ResType config) {
        return hasResource(config.getFlags());
    }

    public boolean isDummyResSpec() {
        return getName().startsWith("APKTOOL_DUMMY_");
    }

    public Set<ResResource> listResources() {
        return new LinkedHashSet<ResResource>(mResources.values());
    }

    public void removeResource(ResResource res) throws IOException {
        ResConfigFlags flags = res.getConfig().getFlags();
        mResources.remove(flags);
    }

    @Override
    public String toString() {
        return mId.toString() + " " + mType.toString() + "/" + mName;
    }
}
