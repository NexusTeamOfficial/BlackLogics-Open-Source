package com.nexusteam.blacklogics.models;

import java.util.ArrayList;
import java.util.List;

import com.nexusteam.blacklogics.bean.AssetItem;
import com.nexusteam.blacklogics.utils.AssetFileUtils;

public class AssetManagerModel {

    private String currentPath;
    private String scId;
    private List<AssetItem> assetItems;

    public AssetManagerModel(String scId, String initialPath) {
        this.scId = scId;
        this.currentPath = initialPath;
        this.assetItems = new ArrayList<>();
    }

    public void loadAssets() {
        assetItems.clear();
        ArrayList<String> filePaths = AssetFileUtils.listFiles(currentPath);
        
        for (String path : filePaths) {
            String name = AssetFileUtils.getFileNameFromPath(path);
            boolean isFolder = AssetFileUtils.isDirectory(path);
            boolean isImage = !isFolder && AssetFileUtils.isImageFile(path);
            
            assetItems.add(new AssetItem(path, name, isFolder, isImage));
        }
    }

    public boolean navigateUp() {
        if (!isRootDirectory()) {
            currentPath = currentPath.substring(0, currentPath.lastIndexOf("/"));
            return true;
        }
        return false;
    }

    public void navigateToFolder(String folderPath) {
        currentPath = folderPath;
    }

    public boolean isRootDirectory() {

        return currentPath.endsWith("/assets");
    }


    public String getCurrentPath() {
        return currentPath;
    }

    public List<AssetItem> getAssetItems() {
        return assetItems;
    }

    public int getItemCount() {
        return assetItems.size();
    }

    public AssetItem getItem(int position) {
        if (position >= 0 && position < assetItems.size()) {
            return assetItems.get(position);
        }
        return null;
    }
}