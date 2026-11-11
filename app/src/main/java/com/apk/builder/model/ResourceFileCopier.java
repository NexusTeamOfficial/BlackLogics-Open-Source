package com.apk.builder.model;

import java.io.File;
import java.io.IOException;

public interface ResourceFileCopier {
    
    void copyResource(File source, File destination) throws IOException;
    
    void copyMultipleResources(File[] sources, File destination) throws IOException;
    
    boolean isValidResource(File source);
}