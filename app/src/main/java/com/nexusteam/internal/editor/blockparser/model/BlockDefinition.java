package com.nexusteam.internal.editor.blockparser.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BlockDefinition {

    private String opcode;
    private String name;
    private String category;
    private boolean custom;
    private boolean container;
    private List codePatterns;
    private List parameterNames;
    private String description;
    private String color;
    private List compiledPatterns;

    public BlockDefinition() {
        this.codePatterns = new ArrayList();
        this.parameterNames = new ArrayList();
        this.compiledPatterns = new ArrayList();
        this.custom = false;
        this.container = false;
    }

    public BlockDefinition(String opcode, String name, String category) {
        this();
        this.opcode = opcode;
        this.name = name;
        this.category = category;
    }

    public void addCodePattern(String pattern) {
        if (pattern == null || pattern.length() == 0) {
            return;
        }
        this.codePatterns.add(pattern);
        try {
            Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
            this.compiledPatterns.add(p);
        } catch (Exception e) {
        }
    }

    public void addParameter(String paramName) {
        if (paramName != null && paramName.length() > 0) {
            this.parameterNames.add(paramName);
        }
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public boolean isContainer() {
        return container;
    }

    public void setContainer(boolean container) {
        this.container = container;
    }

    public List getCodePatterns() {
        return codePatterns;
    }

    public void setCodePatterns(List codePatterns) {
        this.codePatterns = codePatterns == null ? new ArrayList() : codePatterns;
        this.compiledPatterns.clear();
        for (int i = 0; i < this.codePatterns.size(); i++) {
            String pat = (String) this.codePatterns.get(i);
            try {
                this.compiledPatterns.add(Pattern.compile(pat, Pattern.DOTALL));
            } catch (Exception e) {
            }
        }
    }

    public List getCompiledPatterns() {
        return compiledPatterns;
    }

    public List getParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(List parameterNames) {
        this.parameterNames = parameterNames == null ? new ArrayList() : parameterNames;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString() {
        return "BlockDefinition{opcode=" + opcode + ", name=" + name + ", category=" + category + ", custom=" + custom + "}";
    }
}
