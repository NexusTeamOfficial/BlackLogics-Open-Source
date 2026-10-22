
package com.nexusteam.blacklogics.model;

import android.os.Environment;

import java.io.File;

public class ProjectIdGenerator {
    
    public static String getNewScId() {
        int nextNum = getNextProjectNumber();
        int projectId = 601 + nextNum;
        return String.valueOf(projectId);
    }
    
    private static int getNextProjectNumber() {
        File projectsDir = new File("/storage/emulated/0/", ".blacklogics/data/");
        
        if (!projectsDir.exists() || !projectsDir.isDirectory()) {
            return 0;
        }
        
        File[] files = projectsDir.listFiles();
        if (files == null) return 0;
        
        int count = 0;
        for (File file : files) {
            if (file.isDirectory()) {
                String name = file.getName().trim();
                if (name.matches("\\d+")) {
                    try {
                        int id = Integer.parseInt(name);
                        if (id >= 600) {
                            count++;
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
        return count;
    }
}