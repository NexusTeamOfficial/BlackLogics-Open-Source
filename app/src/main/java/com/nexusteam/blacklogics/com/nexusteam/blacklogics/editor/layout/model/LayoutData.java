package com.nexusteam.blacklogics.editor.layout.model;

import java.io.Serializable;

public class LayoutData implements Serializable {

    private static final long serialVersionUID = 1L;

    public String name;
    public String xml;

    public LayoutData(String name, String xml) {
        this.name = name;
        this.xml = xml;
    }
}
