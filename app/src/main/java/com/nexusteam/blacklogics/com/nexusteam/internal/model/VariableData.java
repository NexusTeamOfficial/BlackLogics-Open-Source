package com.nexusteam.internal.model;

import java.io.Serializable;

public class VariableData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public int type;
    public String name;
    public String defaultValue;
    
    public VariableData(int type, String name, String defaultValue) {
        this.type = type;
        this.name = name;
        this.defaultValue = defaultValue;
    }
}