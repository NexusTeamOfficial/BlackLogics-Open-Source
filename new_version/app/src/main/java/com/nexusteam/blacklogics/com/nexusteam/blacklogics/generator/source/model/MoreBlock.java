package com.nexusteam.blacklogics.generator.source.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MoreBlock implements Serializable {
    private String blockName;
    private String blockSpec;
    private List<BlockParameter> parameters;
    private String blockLogic;
    private long createdAt;
    
    public MoreBlock() {
        this.parameters = new ArrayList<BlockParameter>();
        this.createdAt = System.currentTimeMillis();
    }
    
    public MoreBlock(String blockName, String blockSpec) {
        this.blockName = blockName;
        this.blockSpec = blockSpec;
        this.parameters = new ArrayList<BlockParameter>();
        this.createdAt = System.currentTimeMillis();
    }
    

    public String getBlockName() {
        return blockName;
    }
    
    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }
    
    public String getBlockSpec() {
        return blockSpec;
    }
    
    public void setBlockSpec(String blockSpec) {
        this.blockSpec = blockSpec;
    }
    
    public List<BlockParameter> getParameters() {
        return parameters;
    }
    
    public void setParameters(List<BlockParameter> parameters) {
        this.parameters = parameters;
    }
    
    public String getBlockLogic() {
        return blockLogic;
    }
    
    public void setBlockLogic(String blockLogic) {
        this.blockLogic = blockLogic;
    }
    
    public long getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}