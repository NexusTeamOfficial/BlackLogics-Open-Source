package com.apk.builder.model;

import java.io.File;
import java.io.IOException;

/**
 * Interface for handling fast file copying operations to add internal resources
 * in the compilation process.
 */
public interface ResourceFileCopier {
    
    /**
     * Copies a source file or directory to the destination path.
     *
     * @param source      The source file or directory to copy.
     * @param destination The destination file or directory path.
     * @throws IOException If an I/O error occurs during copying.
     */
    void copyResource(File source, File destination) throws IOException;
    
    /**
     * Copies multiple resource files or directories to the specified destination.
     *
     * @param sources     Array of source files or directories to copy.
     * @param destination The destination directory path.
     * @throws IOException If an I/O error occurs during copying.
     */
    void copyMultipleResources(File[] sources, File destination) throws IOException;
    
    /**
     * Checks if the source file or directory exists and is valid for copying.
     *
     * @param source The source file or directory to check.
     * @return True if the source is valid, false otherwise.
     */
    boolean isValidResource(File source);
}