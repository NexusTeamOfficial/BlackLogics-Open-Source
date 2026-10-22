package com.nexusteam.internal.model;

import java.io.Serializable;

public class ComponentData implements Serializable {
    public int type;
    public String mainData;
    public String extraData;

    public ComponentData(int type, String mainData, String extraData) {
        this.type = type;
        this.mainData = mainData;
        this.extraData = extraData;
    }
}
