package com.nexusteam.internal.editor.blockparser.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsedBlock {

    public static final int TYPE_BUILT_IN = 1;
    public static final int TYPE_CUSTOM = 2;
    public static final int TYPE_SOURCE = 3;
    public static final int TYPE_CONTAINER = 4;
    public static final int TYPE_UNKNOWN = 0;

    private String id;
    private String opcode;
    private String displayName;
    private int type;
    private String originalCode;
    private Map parameters;
    private List children;
    private List nextBlocks;
    private int startLine;
    private int endLine;
    private boolean matched;

    public ParsedBlock() {
        this.parameters = new HashMap();
        this.children = new ArrayList();
        this.nextBlocks = new ArrayList();
        this.type = TYPE_UNKNOWN;
        this.matched = false;
        this.startLine = -1;
        this.endLine = -1;
    }

    public ParsedBlock(String opcode, int type) {
        this();
        this.opcode = opcode;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOriginalCode() {
        return originalCode;
    }

    public void setOriginalCode(String originalCode) {
        this.originalCode = originalCode;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        if (parameters == null) {
            this.parameters = new HashMap();
        } else {
            this.parameters = parameters;
        }
    }

    public void addParameter(String key, String value) {
        if (key != null) {
            this.parameters.put(key, value == null ? "" : value);
        }
    }

    public String getParameter(String key) {
        Object val = this.parameters.get(key);
        return val == null ? null : val.toString();
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        if (children == null) {
            this.children = new ArrayList();
        } else {
            this.children = children;
        }
    }

    public void addChild(ParsedBlock child) {
        if (child != null) {
            this.children.add(child);
        }
    }

    public List getNextBlocks() {
        return nextBlocks;
    }

    public void setNextBlocks(List nextBlocks) {
        if (nextBlocks == null) {
            this.nextBlocks = new ArrayList();
        } else {
            this.nextBlocks = nextBlocks;
        }
    }

    public void addNextBlock(ParsedBlock block) {
        if (block != null) {
            this.nextBlocks.add(block);
        }
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public boolean isSourceBlock() {
        return type == TYPE_SOURCE;
    }

    public boolean isContainer() {
        return type == TYPE_CONTAINER;
    }

    public boolean isBuiltIn() {
        return type == TYPE_BUILT_IN;
    }

    public boolean isCustom() {
        return type == TYPE_CUSTOM;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ParsedBlock{opcode=");
        sb.append(opcode);
        sb.append(", type=");
        sb.append(type);
        sb.append(", matched=");
        sb.append(matched);
        sb.append(", params=");
        sb.append(parameters.size());
        sb.append(", children=");
        sb.append(children.size());
        sb.append("}");
        return sb.toString();
    }
}
