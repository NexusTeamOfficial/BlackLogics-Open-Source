package com.nexusteam.internal.os.layouteditor.model;

public class FileItem {
    private final String name;
    private final String path;
    
    public FileItem(String name, String path) {
        this.name = name;
        this.path = path;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPath() {
        return path;
    }
}