
package com.nexusteam.blacklogics.model;

import com.nexusteam.blacklogics.bean.PermissionItem;

import java.util.ArrayList;
import java.util.List;

public class PermissionFilterModel {
    private String query;
    private List<PermissionItem> filteredPermissions;
    private List<PermissionItem> allPermissions;

    public PermissionFilterModel(List<PermissionItem> allPermissions) {
        this.allPermissions = allPermissions;
        this.filteredPermissions = new ArrayList<>(allPermissions);
        this.query = "";
    }

    public List<PermissionItem> filterPermissions(String query) {
        this.query = query != null ? query.toLowerCase() : "";
        
        if (this.query.isEmpty()) {
            filteredPermissions = new ArrayList<>(allPermissions);
        } else {
            filteredPermissions = new ArrayList<>();
            for (PermissionItem item : allPermissions) {
                if (item.getPermissionName().toLowerCase().contains(this.query)) {
                    filteredPermissions.add(item);
                }
            }
        }
        
        return filteredPermissions;
    }

    public String getQuery() {
        return query;
    }

    public List<PermissionItem> getFilteredPermissions() {
        return filteredPermissions;
    }

    public List<PermissionItem> getAllPermissions() {
        return allPermissions;
    }

    public void setAllPermissions(List<PermissionItem> allPermissions) {
        this.allPermissions = allPermissions;
        filterPermissions(query); // Re-apply filter with new data
    }
}