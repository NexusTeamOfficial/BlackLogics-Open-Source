package com.nexusteam.sdk.util;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * AdvancedRefactor.java - Professional Refactoring Tool like Android Studio
 * Handles package renaming, import updates, directory restructuring
 */
public class AdvancedRefactor {
    
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private boolean dryRun;
    private List<String> processedFiles;
    private Map<String, RefactorStats> stats;
    
    public AdvancedRefactor() {
        this(false);
    }
    
    public AdvancedRefactor(boolean dryRun) {
        this.dryRun = dryRun;
        this.processedFiles = new ArrayList<>();
        this.stats = new HashMap<>();
    }
    
    /**
     * Rename package completely - updates all files and directory structure
     * Like Android Studio's Refactor -> Rename Package
     */
    public PackageRenameResult renamePackage(String projectRoot, String oldPackage, String newPackage) {
        System.out.println("Renaming package: " + oldPackage + " -> " + newPackage);
        PackageRenameResult result = new PackageRenameResult(oldPackage, newPackage);
        
        File root = new File(projectRoot);
        if (!root.exists()) {
            result.addError("Project root does not exist: " + projectRoot);
            return result;
        }
        
        // Convert package names to paths
        String oldPath = oldPackage.replace('.', File.separatorChar);
        String newPath = newPackage.replace('.', File.separatorChar);
        
        // Find all Java/Kotlin files
        List<File> allFiles = findAllFiles(root, new String[]{".java", ".kt"});
        
        // First pass: Update package declarations and imports in all files
        for (File file : allFiles) {
            try {
                updatePackageInFile(file, oldPackage, newPackage, result);
            } catch (IOException e) {
                result.addError("Error processing " + file.getPath() + ": " + e.getMessage());
            }
        }
        
        // Second pass: Move directories to new package structure
        movePackageDirectories(root, oldPath, newPath, result);
        
        // Third pass: Update all import statements that might reference the old package
        for (File file : findAllFiles(root, new String[]{".java", ".kt", ".xml", ".gradle"})) {
            try {
                updateImportsInFile(file, oldPackage, newPackage, result);
            } catch (IOException e) {
                result.addError("Error updating imports in " + file.getPath() + ": " + e.getMessage());
            }
        }
        
        return result;
    }
    
    /**
     * Update package declaration in a single file
     */
    private void updatePackageInFile(File file, String oldPackage, String newPackage, PackageRenameResult result) 
            throws IOException {
        String content = readFile(file);
        String original = content;
        
        // Pattern for package declaration
        Pattern packagePattern = Pattern.compile(
            "^\\s*package\\s+" + Pattern.quote(oldPackage) + "\\s*;?\\s*$",
            Pattern.MULTILINE
        );
        
        Matcher matcher = packagePattern.matcher(content);
        if (matcher.find()) {
            String newPackageDecl = "package " + newPackage + 
                                   (content.contains(";") ? ";" : "");
            content = matcher.replaceFirst(newPackageDecl);
            
            if (!content.equals(original)) {
                if (!dryRun) {
                    writeFile(file, content);
                }
                result.addUpdatedFile(file.getPath(), "package");
                processedFiles.add(file.getPath());
            }
        }
    }
    
    /**
     * Update import statements in a file
     */
    private void updateImportsInFile(File file, String oldPackage, String newPackage, PackageRenameResult result) 
            throws IOException {
        String content = readFile(file);
        String original = content;
        
        // Update import statements
        String oldPackagePattern = Pattern.quote(oldPackage);
        
        // Handle regular imports
        Pattern importPattern = Pattern.compile(
            "import\\s+(" + oldPackagePattern + "\\.[^;]+);"
        );
        Matcher matcher = importPattern.matcher(content);
        StringBuffer sb = new StringBuffer();
        
        while (matcher.find()) {
            String fullImport = matcher.group(1);
            String newImport = fullImport.replaceFirst(oldPackagePattern, newPackage);
            matcher.appendReplacement(sb, "import " + newImport + ";");
            result.incrementImportUpdates();
        }
        matcher.appendTail(sb);
        content = sb.toString();
        
        // Handle static imports
        Pattern staticImportPattern = Pattern.compile(
            "import\\s+static\\s+(" + oldPackagePattern + "\\.[^;]+);"
        );
        matcher = staticImportPattern.matcher(content);
        sb = new StringBuffer();
        
        while (matcher.find()) {
            String fullImport = matcher.group(1);
            String newImport = fullImport.replaceFirst(oldPackagePattern, newPackage);
            matcher.appendReplacement(sb, "import static " + newImport + ";");
            result.incrementImportUpdates();
        }
        matcher.appendTail(sb);
        content = sb.toString();
        
        // Handle wildcard imports
        Pattern wildcardPattern = Pattern.compile(
            "import\\s+" + oldPackagePattern + "\\.\\*;"
        );
        content = wildcardPattern.matcher(content).replaceAll("import " + newPackage + ".*;");
        
        // Handle imports without semicolon (Kotlin)
        Pattern ktImportPattern = Pattern.compile(
            "import\\s+(" + oldPackagePattern + "\\.[^\n]+)"
        );
        matcher = ktImportPattern.matcher(content);
        sb = new StringBuffer();
        
        while (matcher.find()) {
            String fullImport = matcher.group(1);
            String newImport = fullImport.replaceFirst(oldPackagePattern, newPackage);
            matcher.appendReplacement(sb, "import " + newImport);
            result.incrementImportUpdates();
        }
        matcher.appendTail(sb);
        content = sb.toString();
        
        if (!content.equals(original)) {
            if (!dryRun) {
                writeFile(file, content);
            }
            result.addUpdatedFile(file.getPath(), "imports");
        }
    }
    
    /**
     * Move directories to reflect new package structure
     */
    private void movePackageDirectories(File root, String oldPath, String newPath, PackageRenameResult result) {
        // Find all directories that contain the old package path
        List<File> packageDirs = findDirectoriesContaining(root, oldPath);
        
        for (File dir : packageDirs) {
            String dirPath = dir.getAbsolutePath();
            if (dirPath.contains(oldPath)) {
                String newDirPath = dirPath.replace(oldPath, newPath);
                File newDir = new File(newDirPath);
                
                // Create parent directories if needed
                if (!dryRun) {
                    newDir.getParentFile().mkdirs();
                    if (dir.renameTo(newDir)) {
                        result.addMovedDirectory(dirPath, newDirPath);
                    } else {
                        result.addError("Failed to move directory: " + dirPath);
                    }
                } else {
                    result.addMovedDirectory(dirPath, newDirPath);
                }
            }
        }
    }
    
    /**
     * Update import statements across the entire project
     */
    public ImportUpdateResult updateImports(String projectRoot, String oldImport, String newImport) {
        ImportUpdateResult result = new ImportUpdateResult(oldImport, newImport);
        
        List<File> files = findAllFiles(new File(projectRoot), 
            new String[]{".java", ".kt", ".xml", ".gradle"});
        
        for (File file : files) {
            try {
                String content = readFile(file);
                String original = content;
                
                // Update import statements
                String importPattern = "import\\s+" + Pattern.quote(oldImport) + "\\s*;?";
                content = content.replaceAll(importPattern, "import " + newImport + 
                                            (original.contains(";") ? ";" : ""));
                
                // Update fully qualified class names
                String fqnPattern = "\\b" + Pattern.quote(oldImport) + "\\.[A-Z]";
                // This is simplified - full implementation would need AST parsing
                
                if (!content.equals(original)) {
                    if (!dryRun) {
                        writeFile(file, content);
                    }
                    result.addUpdatedFile(file.getPath());
                    processedFiles.add(file.getPath());
                    
                    // Count replacements
                    int diff = countDifferences(original, content);
                    result.addReplacements(diff);
                }
            } catch (IOException e) {
                result.addError("Error processing " + file.getPath() + ": " + e.getMessage());
            }
        }
        
        return result;
    }
    
    /**
     * Rename class with full refactoring - updates all references
     */
    public ClassRenameResult renameClass(String projectRoot, String oldClassName, String newClassName) {
        ClassRenameResult result = new ClassRenameResult(oldClassName, newClassName);
        
        List<File> files = findAllFiles(new File(projectRoot), 
            new String[]{".java", ".kt", ".xml", ".gradle"});
        
        // First pass: Find and update the class file itself
        File classFile = findClassFile(projectRoot, oldClassName);
        if (classFile != null) {
            try {
                // Update class declaration in the file
                String content = readFile(classFile);
                String original = content;
                
                // Update class declaration
                String classPattern = "(class|interface|enum)\\s+" + 
                                     Pattern.quote(oldClassName) + "\\b";
                content = content.replaceAll(classPattern, "$1 " + newClassName);
                
                // Update constructor names
                String constructorPattern = "\\b" + Pattern.quote(oldClassName) + "\\s*\\(";
                content = content.replaceAll(constructorPattern, newClassName + "(");
                
                if (!content.equals(original)) {
                    if (!dryRun) {
                        writeFile(classFile, content);
                    }
                    result.setClassFileUpdated(true);
                }
                
                // Rename the file
                File newFile = new File(classFile.getParent(), 
                    newClassName + getFileExtension(classFile.getName()));
                if (!dryRun) {
                    if (classFile.renameTo(newFile)) {
                        result.setNewFilePath(newFile.getAbsolutePath());
                    }
                } else {
                    result.setNewFilePath(newFile.getAbsolutePath());
                }
                
            } catch (IOException e) {
                result.addError("Error updating class file: " + e.getMessage());
            }
        }
        
        // Second pass: Update all references to this class
        for (File file : files) {
            if (file.equals(classFile)) continue;
            
            try {
                String content = readFile(file);
                String original = content;
                
                // Update class references
                String refPattern = "\\b" + Pattern.quote(oldClassName) + "\\b";
                content = content.replaceAll(refPattern, newClassName);
                
                // Update import statements that might use this class
                String importPattern = "import\\s+.*\\." + Pattern.quote(oldClassName) + "\\s*;?";
                content = content.replaceAll(importPattern, 
                    "import " + getPackageFromImport(original, oldClassName) + "." + newClassName + 
                    (original.contains(";") ? ";" : ""));
                
                if (!content.equals(original)) {
                    if (!dryRun) {
                        writeFile(file, content);
                    }
                    result.addReferencingFile(file.getPath());
                    
                    int diff = countDifferences(original, content);
                    result.addReplacements(diff);
                }
            } catch (IOException e) {
                result.addError("Error updating references in " + file.getPath() + ": " + e.getMessage());
            }
        }
        
        return result;
    }
    
    /**
     * Batch refactoring from configuration file
     * Config format: operation:old:new
     * operations: package, import, class, method, variable
     */
    public BatchRefactorResult batchRefactor(String configFilePath, String projectRoot) 
            throws IOException {
        BatchRefactorResult result = new BatchRefactorResult();
        
        List<RefactorConfig> configs = loadConfig(configFilePath);
        
        for (RefactorConfig config : configs) {
            System.out.println("\nExecuting: " + config);
            
            switch(config.getOperation()) {
                case "package":
                    PackageRenameResult pkgResult = renamePackage(projectRoot, 
                        config.getOldValue(), config.getNewValue());
                    result.addPackageResult(pkgResult);
                    break;
                    
                case "import":
                    ImportUpdateResult importResult = updateImports(projectRoot,
                        config.getOldValue(), config.getNewValue());
                    result.addImportResult(importResult);
                    break;
                    
                case "class":
                    ClassRenameResult classResult = renameClass(projectRoot,
                        config.getOldValue(), config.getNewValue());
                    result.addClassResult(classResult);
                    break;
                    
                case "method":
                    // Method rename implementation
                    MethodRenameResult methodResult = renameMethod(projectRoot,
                        config.getOldValue(), config.getNewValue());
                    result.addMethodResult(methodResult);
                    break;
                    
                case "variable":
                    // Variable rename implementation
                    VariableRenameResult varResult = renameVariable(projectRoot,
                        config.getOldValue(), config.getNewValue());
                    result.addVariableResult(varResult);
                    break;
            }
        }
        
        return result;
    }
    
    /**
     * Rename method across the project
     */
    private MethodRenameResult renameMethod(String projectRoot, String oldMethod, String newMethod) {
        MethodRenameResult result = new MethodRenameResult(oldMethod, newMethod);
        
        List<File> files = findAllFiles(new File(projectRoot), new String[]{".java", ".kt"});
        
        for (File file : files) {
            try {
                String content = readFile(file);
                String original = content;
                
                // Method call pattern
                String methodCallPattern = "\\b" + Pattern.quote(oldMethod) + "\\s*\\(";
                content = content.replaceAll(methodCallPattern, newMethod + "(");
                
                // Method declaration pattern (simplified)
                String methodDeclPattern = "(public|private|protected|static|final|abstract)?\\s+" +
                    "[\\w<>\\[\\]]+\\s+" + Pattern.quote(oldMethod) + "\\s*\\(";
                content = content.replaceAll(methodDeclPattern, "$1 " + newMethod + "(");
                
                if (!content.equals(original)) {
                    if (!dryRun) {
                        writeFile(file, content);
                    }
                    result.addUpdatedFile(file.getPath());
                    
                    int diff = countDifferences(original, content);
                    result.addReplacements(diff);
                }
            } catch (IOException e) {
                result.addError("Error processing " + file.getPath() + ": " + e.getMessage());
            }
        }
        
        return result;
    }
    
    /**
     * Rename variable across the project (with scope awareness)
     */
    private VariableRenameResult renameVariable(String projectRoot, String oldVar, String newVar) {
        VariableRenameResult result = new VariableRenameResult(oldVar, newVar);
        
        List<File> files = findAllFiles(new File(projectRoot), new String[]{".java", ".kt"});
        
        for (File file : files) {
            try {
                String content = readFile(file);
                String original = content;
                
                // This is simplified - proper variable renaming needs scope analysis
                String varPattern = "\\b" + Pattern.quote(oldVar) + "\\b";
                
                // Don't replace in strings and comments
                String[] lines = content.split(LINE_SEPARATOR);
                StringBuilder sb = new StringBuilder();
                boolean inComment = false;
                
                for (String line : lines) {
                    String trimmed = line.trim();
                    
                    if (trimmed.startsWith("/*")) inComment = true;
                    if (inComment) {
                        sb.append(line).append(LINE_SEPARATOR);
                        if (trimmed.endsWith("*/")) inComment = false;
                        continue;
                    }
                    
                    if (trimmed.startsWith("//")) {
                        sb.append(line).append(LINE_SEPARATOR);
                        continue;
                    }
                    
                    // Simple replacement - in production, use proper parsing
                    line = line.replaceAll(varPattern, newVar);
                    sb.append(line).append(LINE_SEPARATOR);
                }
                
                content = sb.toString();
                
                if (!content.equals(original)) {
                    if (!dryRun) {
                        writeFile(file, content);
                    }
                    result.addUpdatedFile(file.getPath());
                    
                    int diff = countDifferences(original, content);
                    result.addReplacements(diff);
                }
            } catch (IOException e) {
                result.addError("Error processing " + file.getPath() + ": " + e.getMessage());
            }
        }
        
        return result;
    }
    
    /**
     * Find all files with given extensions
     */
    private List<File> findAllFiles(File dir, String[] extensions) {
        List<File> files = new ArrayList<>();
        
        if (!dir.exists() || !dir.isDirectory()) {
            return files;
        }
        
        File[] listFiles = dir.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    files.addAll(findAllFiles(file, extensions));
                } else {
                    for (String ext : extensions) {
                        if (file.getName().endsWith(ext)) {
                            files.add(file);
                            break;
                        }
                    }
                }
            }
        }
        
        return files;
    }
    
    /**
     * Find directories containing a specific path segment
     */
    private List<File> findDirectoriesContaining(File root, String pathSegment) {
        List<File> result = new ArrayList<>();
        findDirectoriesContainingRecursive(root, pathSegment, result);
        return result;
    }
    
    private void findDirectoriesContainingRecursive(File dir, String pathSegment, List<File> result) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (file.getAbsolutePath().contains(pathSegment)) {
                        result.add(file);
                    }
                    findDirectoriesContainingRecursive(file, pathSegment, result);
                }
            }
        }
    }
    
    /**
     * Find class file by name
     */
    private File findClassFile(String projectRoot, String className) {
        List<File> files = findAllFiles(new File(projectRoot), new String[]{".java", ".kt"});
        for (File file : files) {
            String fileName = file.getName();
            String nameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
            if (nameWithoutExt.equals(className)) {
                return file;
            }
        }
        return null;
    }
    
    /**
     * Get file extension
     */
    private String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return lastDot > 0 ? fileName.substring(lastDot) : "";
    }
    
    /**
     * Extract package from import statement
     */
    private String getPackageFromImport(String content, String className) {
        Pattern pattern = Pattern.compile("import\\s+([\\w.]+)\\.?" + 
            Pattern.quote(className) + "\\s*;");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
    
    /**
     * Read file content
     */
    private String readFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(LINE_SEPARATOR);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        
        return content.toString();
    }
    
    /**
     * Write file content
     */
    private void writeFile(File file, String content) throws IOException {
        BufferedWriter writer = null;
        
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            writer.write(content);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    /**
     * Count differences between two strings (simplified)
     */
    private int countDifferences(String original, String modified) {
        if (original.equals(modified)) return 0;
        
        String[] originalLines = original.split(LINE_SEPARATOR);
        String[] modifiedLines = modified.split(LINE_SEPARATOR);
        
        int diffCount = 0;
        int minLines = Math.min(originalLines.length, modifiedLines.length);
        
        for (int i = 0; i < minLines; i++) {
            if (!originalLines[i].equals(modifiedLines[i])) {
                diffCount++;
            }
        }
        
        return diffCount;
    }
    
    /**
     * Load batch configuration
     */
    private List<RefactorConfig> loadConfig(String configFilePath) throws IOException {
        List<RefactorConfig> configs = new ArrayList<>();
        File file = new File(configFilePath);
        
        if (!file.exists()) {
            throw new IOException("Config file not found: " + configFilePath);
        }
        
        String content = readFile(file);
        String[] lines = content.split(LINE_SEPARATOR);
        
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            
            String[] parts = line.split(":");
            if (parts.length >= 3) {
                String operation = parts[0].trim();
                String oldValue = parts[1].trim();
                String newValue = parts[2].trim();
                configs.add(new RefactorConfig(operation, oldValue, newValue));
            }
        }
        
        return configs;
    }
    
    // ==================== Result Classes ====================
    
    public static class PackageRenameResult {
        private String oldPackage;
        private String newPackage;
        private List<String> updatedFiles;
        private List<String> movedDirectories;
        private List<String> errors;
        private int importUpdates;
        
        public PackageRenameResult(String oldPackage, String newPackage) {
            this.oldPackage = oldPackage;
            this.newPackage = newPackage;
            this.updatedFiles = new ArrayList<>();
            this.movedDirectories = new ArrayList<>();
            this.errors = new ArrayList<>();
            this.importUpdates = 0;
        }
        
        public void addUpdatedFile(String file, String type) {
            updatedFiles.add(file + " (" + type + ")");
        }
        
        public void addMovedDirectory(String oldPath, String newPath) {
            movedDirectories.add(oldPath + " -> " + newPath);
        }
        
        public void addError(String error) {
            errors.add(error);
        }
        
        public void incrementImportUpdates() {
            importUpdates++;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n=== Package Rename Result ===\n");
            sb.append("Package: ").append(oldPackage).append(" -> ").append(newPackage).append("\n");
            sb.append("Files updated: ").append(updatedFiles.size()).append("\n");
            sb.append("Directories moved: ").append(movedDirectories.size()).append("\n");
            sb.append("Import updates: ").append(importUpdates).append("\n");
            
            if (!errors.isEmpty()) {
                sb.append("Errors:\n");
                for (String error : errors) {
                    sb.append("  - ").append(error).append("\n");
                }
            }
            
            return sb.toString();
        }
    }
    
    public static class ImportUpdateResult {
        private String oldImport;
        private String newImport;
        private List<String> updatedFiles;
        private List<String> errors;
        private int totalReplacements;
        
        public ImportUpdateResult(String oldImport, String newImport) {
            this.oldImport = oldImport;
            this.newImport = newImport;
            this.updatedFiles = new ArrayList<>();
            this.errors = new ArrayList<>();
            this.totalReplacements = 0;
        }
        
        public void addUpdatedFile(String file) {
            updatedFiles.add(file);
        }
        
        public void addError(String error) {
            errors.add(error);
        }
        
        public void addReplacements(int count) {
            totalReplacements += count;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n=== Import Update Result ===\n");
            sb.append("Import: ").append(oldImport).append(" -> ").append(newImport).append("\n");
            sb.append("Files updated: ").append(updatedFiles.size()).append("\n");
            sb.append("Total replacements: ").append(totalReplacements).append("\n");
            
            if (!errors.isEmpty()) {
                sb.append("Errors:\n");
                for (String error : errors) {
                    sb.append("  - ").append(error).append("\n");
                }
            }
            
            return sb.toString();
        }
    }
    
    public static class ClassRenameResult {
        private String oldClass;
        private String newClass;
        private boolean classFileUpdated;
        private String newFilePath;
        private List<String> referencingFiles;
        private List<String> errors;
        private int totalReplacements;
        
        public ClassRenameResult(String oldClass, String newClass) {
            this.oldClass = oldClass;
            this.newClass = newClass;
            this.referencingFiles = new ArrayList<>();
            this.errors = new ArrayList<>();
            this.totalReplacements = 0;
        }
        
        public void setClassFileUpdated(boolean updated) {
            this.classFileUpdated = updated;
        }
        
        public void setNewFilePath(String path) {
            this.newFilePath = path;
        }
        
        public void addReferencingFile(String file) {
            referencingFiles.add(file);
        }
        
        public void addError(String error) {
            errors.add(error);
        }
        
        public void addReplacements(int count) {
            totalReplacements += count;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n=== Class Rename Result ===\n");
            sb.append("Class: ").append(oldClass).append(" -> ").append(newClass).append("\n");
            sb.append("Class file updated: ").append(classFileUpdated).append("\n");
            if (newFilePath != null) {
                sb.append("New file path: ").append(newFilePath).append("\n");
            }
            sb.append("Referencing files: ").append(referencingFiles.size()).append("\n");
            sb.append("Total replacements: ").append(totalReplacements).append("\n");
            
            if (!errors.isEmpty()) {
                sb.append("Errors:\n");
                for (String error : errors) {
                    sb.append("  - ").append(error).append("\n");
                }
            }
            
            return sb.toString();
        }
    }
    
    public static class MethodRenameResult {
        private String oldMethod;
        private String newMethod;
        private List<String> updatedFiles;
        private List<String> errors;
        private int totalReplacements;
        
        public MethodRenameResult(String oldMethod, String newMethod) {
            this.oldMethod = oldMethod;
            this.newMethod = newMethod;
            this.updatedFiles = new ArrayList<>();
            this.errors = new ArrayList<>();
            this.totalReplacements = 0;
        }
        
        public void addUpdatedFile(String file) {
            updatedFiles.add(file);
        }
        
        public void addError(String error) {
            errors.add(error);
        }
        
        public void addReplacements(int count) {
            totalReplacements += count;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n=== Method Rename Result ===\n");
            sb.append("Method: ").append(oldMethod).append(" -> ").append(newMethod).append("\n");
            sb.append("Files updated: ").append(updatedFiles.size()).append("\n");
            sb.append("Total replacements: ").append(totalReplacements).append("\n");
            
            if (!errors.isEmpty()) {
                sb.append("Errors:\n");
                for (String error : errors) {
                    sb.append("  - ").append(error).append("\n");
                }
            }
            
            return sb.toString();
        }
    }
    
    public static class VariableRenameResult {
        private String oldVar;
        private String newVar;
        private List<String> updatedFiles;
        private List<String> errors;
        private int totalReplacements;
        
        public VariableRenameResult(String oldVar, String newVar) {
            this.oldVar = oldVar;
            this.newVar = newVar;
            this.updatedFiles = new ArrayList<>();
            this.errors = new ArrayList<>();
            this.totalReplacements = 0;
        }
        
        public void addUpdatedFile(String file) {
            updatedFiles.add(file);
        }
        
        public void addError(String error) {
            errors.add(error);
        }
        
        public void addReplacements(int count) {
            totalReplacements += count;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n=== Variable Rename Result ===\n");
            sb.append("Variable: ").append(oldVar).append(" -> ").append(newVar).append("\n");
            sb.append("Files updated: ").append(updatedFiles.size()).append("\n");
            sb.append("Total replacements: ").append(totalReplacements).append("\n");
            
            if (!errors.isEmpty()) {
                sb.append("Errors:\n");
                for (String error : errors) {
                    sb.append("  - ").append(error).append("\n");
                }
            }
            
            return sb.toString();
        }
    }
    
    public static class BatchRefactorResult {
        private List<PackageRenameResult> packageResults;
        private List<ImportUpdateResult> importResults;
        private List<ClassRenameResult> classResults;
        private List<MethodRenameResult> methodResults;
        private List<VariableRenameResult> variableResults;
        
        public BatchRefactorResult() {
            this.packageResults = new ArrayList<>();
            this.importResults = new ArrayList<>();
            this.classResults = new ArrayList<>();
            this.methodResults = new ArrayList<>();
            this.variableResults = new ArrayList<>();
        }
        
        public void addPackageResult(PackageRenameResult result) {
            packageResults.add(result);
        }
        
        public void addImportResult(ImportUpdateResult result) {
            importResults.add(result);
        }
        
        public void addClassResult(ClassRenameResult result) {
            classResults.add(result);
        }
        
        public void addMethodResult(MethodRenameResult result) {
            methodResults.add(result);
        }
        
        public void addVariableResult(VariableRenameResult result) {
            variableResults.add(result);
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n========================================\n");
            sb.append("        BATCH REFACTOR RESULTS          \n");
            sb.append("========================================\n");
            
            sb.append("\nPackage Renames: ").append(packageResults.size());
            for (PackageRenameResult r : packageResults) {
                sb.append(r.toString());
            }
            
            sb.append("\nImport Updates: ").append(importResults.size());
            for (ImportUpdateResult r : importResults) {
                sb.append(r.toString());
            }
            
            sb.append("\nClass Renames: ").append(classResults.size());
            for (ClassRenameResult r : classResults) {
                sb.append(r.toString());
            }
            
            sb.append("\nMethod Renames: ").append(methodResults.size());
            for (MethodRenameResult r : methodResults) {
                sb.append(r.toString());
            }
            
            sb.append("\nVariable Renames: ").append(variableResults.size());
            for (VariableRenameResult r : variableResults) {
                sb.append(r.toString());
            }
            
            return sb.toString();
        }
    }
    
    public static class RefactorConfig {
        private String operation;
        private String oldValue;
        private String newValue;
        
        public RefactorConfig(String operation, String oldValue, String newValue) {
            this.operation = operation;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }
        
        public String getOperation() { return operation; }
        public String getOldValue() { return oldValue; }
        public String getNewValue() { return newValue; }
        
        @Override
        public String toString() {
            return operation + ": " + oldValue + " -> " + newValue;
        }
    }
    
    public static class RefactorStats {
        private int filesProcessed;
        private int replacements;
        
        public RefactorStats() {
            this.filesProcessed = 0;
            this.replacements = 0;
        }
        
        public void incrementFiles() { filesProcessed++; }
        public void addReplacements(int count) { replacements += count; }
        
        public int getFilesProcessed() { return filesProcessed; }
        public int getReplacements() { return replacements; }
    }
}