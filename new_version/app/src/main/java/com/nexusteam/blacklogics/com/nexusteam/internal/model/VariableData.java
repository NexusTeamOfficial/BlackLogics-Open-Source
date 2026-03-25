package com.nexusteam.internal.model;

import java.io.Serializable;

public class VariableData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public int type;        // 0=boolean, 1=int, 2=string, 3=map, 4=listInt, 5=listStr, 6=listMap
    public String name;
    public String defaultValue;
    
    public VariableData(int type, String name, String defaultValue) {
        this.type = type;
        this.name = name;
        this.defaultValue = defaultValue;
    }
}