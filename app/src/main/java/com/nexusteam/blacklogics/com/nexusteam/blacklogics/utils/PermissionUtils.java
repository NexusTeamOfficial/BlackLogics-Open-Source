
package com.nexusteam.blacklogics.utils;

import android.util.Log;

import com.nexusteam.blacklogics.bean.PermissionItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {

    public static List<PermissionItem> getAllSystemPermissions() {
        List<PermissionItem> permissions = new ArrayList<>();
        try {
            for (Field permission : Class.forName("android.Manifest$permission").getDeclaredFields()) {
                if (permission.get(null) instanceof String) {
                    String permName = (String) permission.get(null);
                    permissions.add(new PermissionItem(permName));
                }
            }
        } catch (ClassNotFoundException e) {
            Log.e("PermissionUtils", "Couldn't find class android.Manifest.permission!");
        } catch (IllegalAccessException ignored) {
        }
        return permissions;
    }

    public static List<String> getPermissionNames(List<PermissionItem> permissionItems) {
        List<String> names = new ArrayList<>();
        for (PermissionItem item : permissionItems) {
            names.add(item.getPermissionName());
        }
        return names;
    }

    public static List<PermissionItem> convertToPermissionItems(List<String> permissionNames, List<String> selectedPermissions) {
        List<PermissionItem> items = new ArrayList<>();
        for (String name : permissionNames) {
            PermissionItem item = new PermissionItem(name);
            item.setSelected(selectedPermissions.contains(name));
            items.add(item);
        }
        return items;
    }
}