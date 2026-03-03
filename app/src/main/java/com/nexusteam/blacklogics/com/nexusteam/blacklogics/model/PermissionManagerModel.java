
package com.nexusteam.blacklogics.model;

import com.nexusteam.blacklogics.bean.PermissionItem;

import java.util.ArrayList;
import java.util.List;

public class PermissionManagerModel {
    private String projectId;
    private List<PermissionItem> allPermissions;
    private FileResConfig fileResConfig;
    
    public PermissionManagerModel(String projectId) {
        this.projectId = projectId;
        this.allPermissions = new ArrayList<>();
        this.fileResConfig = new FileResConfig(projectId);
    }
    
    public String getProjectId() {
        return projectId;
    }
    
    public void setProjectId(String projectId) {
        this.projectId = projectId;
        this.fileResConfig.setProjectId(projectId);
    }
    
    public List<PermissionItem> getAllPermissions() {
        return allPermissions;
    }
    
    public void setAllPermissions(List<PermissionItem> allPermissions) {
        this.allPermissions = allPermissions;

        syncFromFileResConfig();
    }
    
    public List<String> getSelectedPermissions() {
        return fileResConfig.getPermissions();
    }
    
    public void setSelectedPermissions(List<String> selectedPermissions) {
        fileResConfig.setPermissions(selectedPermissions);
        syncToAllPermissions();
    }
    
    public FileResConfig getFileResConfig() {
        return fileResConfig;
    }
    
    public void addPermission(String permission) {

        fileResConfig.addPermission(permission);
        

        for (PermissionItem item : allPermissions) {
            if (item.getPermissionName().equals(permission)) {
                item.setSelected(true);
                break;
            }
        }
    }
    
    public void removePermission(String permission) {

        fileResConfig.removePermission(permission);
        

        for (PermissionItem item : allPermissions) {
            if (item.getPermissionName().equals(permission)) {
                item.setSelected(false);
                break;
            }
        }
    }
    
    public boolean isPermissionSelected(String permission) {
        return fileResConfig.hasPermission(permission);
    }
    
    public void resetPermissions() {
        fileResConfig.clearPermissions();

        for (PermissionItem item : allPermissions) {
            item.setSelected(false);
        }
    }
    
    public void savePermissions() {
        fileResConfig.savePermissions();
    }
    
    /**
     * Sync FROM file TO UI (allPermissions)
     */
    public void syncFromFileResConfig() {
        List<String> selectedInFile = fileResConfig.getPermissions();
        for (PermissionItem item : allPermissions) {
            item.setSelected(selectedInFile.contains(item.getPermissionName()));
        }
    }
    
    /**
     * Sync FROM UI (allPermissions) TO file
     */
    public void syncToAllPermissions() {
        List<String> selected = new ArrayList<>();
        for (PermissionItem item : allPermissions) {
            if (item.isSelected()) {
                selected.add(item.getPermissionName());
            }
        }
        fileResConfig.setPermissions(selected);
    }
    
    public void setFileResConfig(FileResConfig fileResConfig) {
        this.fileResConfig = fileResConfig;
        syncFromFileResConfig();
    }
    
    public void loadFromFile() {

        fileResConfig = new FileResConfig(projectId);
        syncFromFileResConfig();
    }
}