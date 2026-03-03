
package com.nexusteam.blacklogics.utils;

import java.io.File;

public class PathUtils {
    
    public static String getParentDirectory(String path) {
        if (path == null || path.isEmpty()) {
            return "";
        }
        
        int lastSeparator = path.lastIndexOf(File.separator);
        if (lastSeparator > 0) {
            return path.substring(0, lastSeparator);
        }
        return "";
    }
    
    public static String joinPaths(String... paths) {
        if (paths == null || paths.length == 0) {
            return "";
        }
        
        StringBuilder result = new StringBuilder(paths[0]);
        for (int i = 1; i < paths.length; i++) {
            if (!result.toString().endsWith(File.separator) && !paths[i].startsWith(File.separator)) {
                result.append(File.separator);
            }
            result.append(paths[i]);
        }
        
        return result.toString();
    }
}