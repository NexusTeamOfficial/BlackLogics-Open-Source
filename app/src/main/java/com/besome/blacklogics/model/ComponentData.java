package com.besome.blacklogics.model;

public class ComponentData {
    private String name;
    private String description;
    private String documentationUrl;

    public ComponentData(String name, String description, String documentationUrl) {
        this.name = name;
        this.description = description;
        this.documentationUrl = documentationUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }
}