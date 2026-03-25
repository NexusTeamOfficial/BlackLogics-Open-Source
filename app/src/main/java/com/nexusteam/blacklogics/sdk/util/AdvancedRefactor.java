package com.nexusteam.sdk.util;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * AdvancedRefactor.java - Professional Refactoring Tool like Android Studio
 * Uses JavaParser 3.15.21 for safe and accurate refactoring
 */
public class AdvancedRefactor {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final boolean dryRun;
    private final List<String> processedFiles;
    private final Map<String, RefactorStats> stats;
    private final JavaParser javaParser;

    public AdvancedRefactor() {
        this(false);
    }

    public AdvancedRefactor(boolean dryRun) {
        this.dryRun = dryRun;
        this.processedFiles = new ArrayList<>();
        this.stats = new HashMap<>();
        this.javaParser = new JavaParser();
    }

    /**
     * Rename package completely - updates all files and directory structure
     */
    public PackageRenameResult renamePackage(String projectRoot, String oldPackage, String newPackage) {
        System.out.println("Renaming package: " + oldPackage + " -> " + newPackage);
        PackageRenameResult result = new PackageRenameResult(oldPackage, newPackage);

        File root = new File(projectRoot);
        if (!root.exists()) {
            result.addError("Project root does not exist: " + projectRoot);
            return result;
        }

        String oldPath = oldPackage.replace('.', File.separatorChar);
        String newPath = newPackage.replace('.', File.separatorChar);

        List<File> allFiles = findAllFiles(root, new String[]{".java", ".kt"});

        // First pass: Update package declarations
        for (File file : allFiles) {
            try {
                updatePackageInFile(file, oldPackage, newPackage, result);
            } catch (IOException e) {
                result.addError("Error processing " + file.getPath() + ": " + e.getMessage());
            }
        }

        // Second pass: Move directories
        movePackageDirectories(root, oldPath, newPath, result);

        // Third pass: Update imports in all files
        for (File file : findAllFiles(root, new String[]{".java", ".kt", ".xml", ".gradle"})) {
            try {
                if (file.getName().endsWith(".java")) {
                    updateImportsInJavaFile(file, oldPackage, newPackage, result);
                } else {
                    updateImportsInFile(file, oldPackage, newPackage, result);
                }
            } catch (IOException e) {
                result.addError("Error updating imports in " + file.getPath() + ": " + e.getMessage());
            }
        }

        return result;
    }

    /**
     * Update package declaration using JavaParser
     */
    private void updatePackageInFile(File file, String oldPackage, String newPackage, PackageRenameResult result)
            throws IOException {
        if (!file.getName().endsWith(".java")) return;

        ParseResult<CompilationUnit> parseResult = javaParser.parse(file);
        if (!parseResult.isSuccessful() || !parseResult.getResult().isPresent()) {
            return;
        }

        CompilationUnit cu = parseResult.getResult().get();
        Optional<PackageDeclaration> pkgDecl = cu.getPackageDeclaration();
        
        if (pkgDecl.isPresent()) {
            String currentPkg = pkgDecl.get().getName().toString();
            if (currentPkg.equals(oldPackage)) {
                pkgDecl.get().setName(newPackage);

                if (!dryRun) {
                    writeCompilationUnit(file, cu);
                }
                result.addUpdatedFile(file.getPath(), "package");
                processedFiles.add(file.getPath());
            }
        }
    }

    /**
     * Update imports in Java file using JavaParser
     */
    private void updateImportsInJavaFile(File file, String oldPackage, String newPackage, PackageRenameResult result)
            throws IOException {
        ParseResult<CompilationUnit> parseResult = javaParser.parse(file);
        if (!parseResult.isSuccessful() || !parseResult.getResult().isPresent()) {
            return;
        }

        CompilationUnit cu = parseResult.getResult().get();
        boolean modified = false;
        List<ImportDeclaration> imports = cu.getImports();
        
        for (ImportDeclaration imp : imports) {
            String importName = imp.getName().toString();
            if (importName.startsWith(oldPackage + ".")) {
                String newImport = importName.replaceFirst(Pattern.quote(oldPackage), newPackage);
                imp.setName(newImport);
                modified = true;
                result.incrementImportUpdates();
            }
        }

        if (modified && !dryRun) {
            writeCompilationUnit(file, cu);
            result.addUpdatedFile(file.getPath(), "imports");
        }
    }

    /**
     * Update imports in non-Java files (Kotlin, XML, Gradle)
     */
    private void updateImportsInFile(File file, String oldPackage, String newPackage, PackageRenameResult result)
            throws IOException {
        String content = readFile(file);
        String original = content;

        String oldPkgPattern = Pattern.quote(oldPackage);
        content = content.replaceAll("import\\s+" + oldPkgPattern + "\\.([^;]+);",
                "import " + newPackage + ".$1;");
        content = content.replaceAll("import\\s+static\\s+" + oldPkgPattern + "\\.([^;]+);",
                "import static " + newPackage + ".$1;");
        content = content.replaceAll("import\\s+" + oldPkgPattern + "\\.\\*;",
                "import " + newPackage + ".*;");
        content = content.replaceAll("import\\s+(" + oldPkgPattern + "\\.[^\\n]+)",
                "import " + newPackage + ".$1");

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
        List<File> packageDirs = findDirectoriesContaining(root, oldPath);

        for (File dir : packageDirs) {
            String dirPath = dir.getAbsolutePath();
            if (dirPath.contains(oldPath)) {
                String newDirPath = dirPath.replace(oldPath, newPath);
                File newDir = new File(newDirPath);

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
     * Rename class with full refactoring - updates all references
     */
    public ClassRenameResult renameClass(String projectRoot, String oldClassName, String newClassName) {
        ClassRenameResult result = new ClassRenameResult(oldClassName, newClassName);

        List<File> files = findAllFiles(new File(projectRoot), new String[]{".java", ".kt", ".xml", ".gradle"});

        // First pass: Find and update the class file
        File classFile = findClassFile(projectRoot, oldClassName);
        if (classFile != null) {
            try {
                if (classFile.getName().endsWith(".java")) {
                    updateClassInJavaFile(classFile, oldClassName, newClassName, result);
                } else {
                    updateClassInKotlinFile(classFile, oldClassName, newClassName, result);
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

        // Second pass: Update all references
        for (File file : files) {
            if (file.equals(classFile)) continue;

            try {
                if (file.getName().endsWith(".java")) {
                    updateClassReferencesInJavaFile(file, oldClassName, newClassName, result);
                } else {
                    updateClassReferencesInFile(file, oldClassName, newClassName, result);
                }
            } catch (IOException e) {
                result.addError("Error updating references in " + file.getPath() + ": " + e.getMessage());
            }
        }

        return result;
    }

    /**
     * Update class in Java file using JavaParser
     */
    private void updateClassInJavaFile(File file, String oldClassName, String newClassName, ClassRenameResult result)
            throws IOException {
        ParseResult<CompilationUnit> parseResult = javaParser.parse(file);
        if (!parseResult.isSuccessful() || !parseResult.getResult().isPresent()) {
            return;
        }

        CompilationUnit cu = parseResult.getResult().get();
        List<ClassOrInterfaceDeclaration> classes = cu.getNodesByType(ClassOrInterfaceDeclaration.class);
        
        for (ClassOrInterfaceDeclaration c : classes) {
            if (c.getName().toString().equals(oldClassName)) {
                c.setName(newClassName);
                result.setClassFileUpdated(true);

                if (!dryRun) {
                    writeCompilationUnit(file, cu);
                }
                break;
            }
        }
    }

    /**
     * Update class references in Java file using JavaParser
     */
    private void updateClassReferencesInJavaFile(File file, String oldClassName, String newClassName,
                                                 ClassRenameResult result) throws IOException {
        ParseResult<CompilationUnit> parseResult = javaParser.parse(file);
        if (!parseResult.isSuccessful() || !parseResult.getResult().isPresent()) {
            return;
        }

        CompilationUnit cu = parseResult.getResult().get();
        ClassReferenceVisitor visitor = new ClassReferenceVisitor(oldClassName, newClassName);
        visitor.visit(cu, null);

        if (visitor.isModified() && !dryRun) {
            writeCompilationUnit(file, cu);
            result.addReferencingFile(file.getPath());
            result.addReplacements(visitor.getReplacements());
        }
    }

    /**
     * Visitor to find and replace class references
     */
    private static class ClassReferenceVisitor extends VoidVisitorAdapter<Void> {
        private final String oldClassName;
        private final String newClassName;
        private boolean modified = false;
        private int replacements = 0;

        public ClassReferenceVisitor(String oldClassName, String newClassName) {
            this.oldClassName = oldClassName;
            this.newClassName = newClassName;
        }

        @Override
        public void visit(NameExpr n, Void arg) {
            if (n.getName().toString().equals(oldClassName)) {
                n.setName(newClassName);
                modified = true;
                replacements++;
            }
            super.visit(n, arg);
        }

        @Override
        public void visit(ImportDeclaration n, Void arg) {
            String importName = n.getName().toString();
            if (importName.endsWith("." + oldClassName)) {
                String newImport = importName.replaceFirst(Pattern.quote("." + oldClassName) + "$", "." + newClassName);
                n.setName(newImport);
                modified = true;
                replacements++;
            }
            super.visit(n, arg);
        }

        public boolean isModified() { return modified; }
        public int getReplacements() { return replacements; }
    }

    /**
     * Rename method across the project
     */
    public MethodRenameResult renameMethod(String projectRoot, String oldMethod, String newMethod) {
        MethodRenameResult result = new MethodRenameResult(oldMethod, newMethod);

        List<File> files = findAllFiles(new File(projectRoot), new String[]{".java", ".kt"});

        for (File file : files) {
            try {
                if (file.getName().endsWith(".java")) {
                    renameMethodInJavaFile(file, oldMethod, newMethod, result);
                } else {
                    renameMethodInKotlinFile(file, oldMethod, newMethod, result);
                }
            } catch (IOException e) {
                result.addError("Error processing " + file.getPath() + ": " + e.getMessage());
            }
        }

        return result;
    }

    /**
     * Rename method in Java file using JavaParser
     */
    private void renameMethodInJavaFile(File file, String oldMethod, String newMethod, MethodRenameResult result)
            throws IOException {
        ParseResult<CompilationUnit> parseResult = javaParser.parse(file);
        if (!parseResult.isSuccessful() || !parseResult.getResult().isPresent()) {
            return;
        }

        CompilationUnit cu = parseResult.getResult().get();
        MethodRenameVisitor visitor = new MethodRenameVisitor(oldMethod, newMethod);
        visitor.visit(cu, null);

        if (visitor.isModified() && !dryRun) {
            writeCompilationUnit(file, cu);
            result.addUpdatedFile(file.getPath());
            result.addReplacements(visitor.getReplacements());
        }
    }

    /**
     * Visitor to rename methods
     */
    private static class MethodRenameVisitor extends VoidVisitorAdapter<Void> {
        private final String oldMethod;
        private final String newMethod;
        private boolean modified = false;
        private int replacements = 0;

        public MethodRenameVisitor(String oldMethod, String newMethod) {
            this.oldMethod = oldMethod;
            this.newMethod = newMethod;
        }

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            if (n.getName().toString().equals(oldMethod)) {
                n.setName(newMethod);
                modified = true;
                replacements++;
            }
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodCallExpr n, Void arg) {
            if (n.getName().toString().equals(oldMethod)) {
                n.setName(newMethod);
                modified = true;
                replacements++;
            }
            super.visit(n, arg);
        }

        public boolean isModified() { return modified; }
        public int getReplacements() { return replacements; }
    }

    /**
     * Rename variable across the project
     */
    public VariableRenameResult renameVariable(String projectRoot, String oldVar, String newVar) {
        VariableRenameResult result = new VariableRenameResult(oldVar, newVar);

        List<File> files = findAllFiles(new File(projectRoot), new String[]{".java", ".kt"});

        for (File file : files) {
            try {
                if (file.getName().endsWith(".java")) {
                    renameVariableInJavaFile(file, oldVar, newVar, result);
                } else {
                    renameVariableInKotlinFile(file, oldVar, newVar, result);
                }
            } catch (IOException e) {
                result.addError("Error processing " + file.getPath() + ": " + e.getMessage());
            }
        }

        return result;
    }

    /**
     * Rename variable in Java file using JavaParser
     */
    private void renameVariableInJavaFile(File file, String oldVar, String newVar, VariableRenameResult result)
            throws IOException {
        ParseResult<CompilationUnit> parseResult = javaParser.parse(file);
        if (!parseResult.isSuccessful() || !parseResult.getResult().isPresent()) {
            return;
        }

        CompilationUnit cu = parseResult.getResult().get();
        VariableRenameVisitor visitor = new VariableRenameVisitor(oldVar, newVar);
        visitor.visit(cu, null);

        if (visitor.isModified() && !dryRun) {
            writeCompilationUnit(file, cu);
            result.addUpdatedFile(file.getPath());
            result.addReplacements(visitor.getReplacements());
        }
    }

    /**
     * Visitor to rename variables
     */
    private static class VariableRenameVisitor extends VoidVisitorAdapter<Void> {
        private final String oldVar;
        private final String newVar;
        private boolean modified = false;
        private int replacements = 0;

        public VariableRenameVisitor(String oldVar, String newVar) {
            this.oldVar = oldVar;
            this.newVar = newVar;
        }

        @Override
        public void visit(VariableDeclarator n, Void arg) {
            if (n.getName().toString().equals(oldVar)) {
                n.setName(newVar);
                modified = true;
                replacements++;
            }
            super.visit(n, arg);
        }

        @Override
        public void visit(NameExpr n, Void arg) {
            if (n.getName().toString().equals(oldVar)) {
                n.setName(newVar);
                modified = true;
                replacements++;
            }
            super.visit(n, arg);
        }

        public boolean isModified() { return modified; }
        public int getReplacements() { return replacements; }
    }

    /**
     * Batch refactoring from configuration file
     */
    public BatchRefactorResult batchRefactor(String configFilePath, String projectRoot)
            throws IOException {
        BatchRefactorResult result = new BatchRefactorResult();

        List<RefactorConfig> configs = loadConfig(configFilePath);

        for (RefactorConfig config : configs) {
            System.out.println("\nExecuting: " + config);

            switch (config.getOperation()) {
                case "package":
                    PackageRenameResult pkgResult = renamePackage(projectRoot,
                            config.getOldValue(), config.getNewValue());
                    result.addPackageResult(pkgResult);
                    break;

                case "class":
                    ClassRenameResult classResult = renameClass(projectRoot,
                            config.getOldValue(), config.getNewValue());
                    result.addClassResult(classResult);
                    break;

                case "method":
                    MethodRenameResult methodResult = renameMethod(projectRoot,
                            config.getOldValue(), config.getNewValue());
                    result.addMethodResult(methodResult);
                    break;

                case "variable":
                    VariableRenameResult varResult = renameVariable(projectRoot,
                            config.getOldValue(), config.getNewValue());
                    result.addVariableResult(varResult);
                    break;
            }
        }

        return result;
    }

    /**
     * Write compilation unit back to file
     */
    private void writeCompilationUnit(File file, CompilationUnit cu) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
             BufferedWriter writer = new BufferedWriter(osw)) {
            writer.write(cu.toString());
        }
    }

    // ==================== Kotlin placeholders (simplified) ====================

    private void updateClassInKotlinFile(File file, String oldClass, String newClass, ClassRenameResult result)
            throws IOException {
        String content = readFile(file);
        String original = content;

        content = content.replaceAll("class\\s+" + Pattern.quote(oldClass) + "\\b",
                "class " + newClass);
        content = content.replaceAll("interface\\s+" + Pattern.quote(oldClass) + "\\b",
                "interface " + newClass);

        if (!content.equals(original)) {
            if (!dryRun) {
                writeFile(file, content);
            }
            result.setClassFileUpdated(true);
        }
    }

    private void updateClassReferencesInFile(File file, String oldClass, String newClass, ClassRenameResult result)
            throws IOException {
        String content = readFile(file);
        String original = content;

        content = content.replaceAll("\\b" + Pattern.quote(oldClass) + "\\b", newClass);

        if (!content.equals(original)) {
            if (!dryRun) {
                writeFile(file, content);
            }
            result.addReferencingFile(file.getPath());
        }
    }

    private void renameMethodInKotlinFile(File file, String oldMethod, String newMethod, MethodRenameResult result)
            throws IOException {
        String content = readFile(file);
        String original = content;

        content = content.replaceAll("fun\\s+" + Pattern.quote(oldMethod) + "\\s*\\(",
                "fun " + newMethod + "(");
        content = content.replaceAll("\\b" + Pattern.quote(oldMethod) + "\\s*\\(", newMethod + "(");

        if (!content.equals(original)) {
            if (!dryRun) {
                writeFile(file, content);
            }
            result.addUpdatedFile(file.getPath());
            int diff = countDifferences(original, content);
            result.addReplacements(diff);
        }
    }

    private void renameVariableInKotlinFile(File file, String oldVar, String newVar, VariableRenameResult result)
            throws IOException {
        String content = readFile(file);
        String original = content;

        content = content.replaceAll("var\\s+" + Pattern.quote(oldVar) + "\\b",
                "var " + newVar);
        content = content.replaceAll("val\\s+" + Pattern.quote(oldVar) + "\\b",
                "val " + newVar);
        content = content.replaceAll("\\b" + Pattern.quote(oldVar) + "\\b", newVar);

        if (!content.equals(original)) {
            if (!dryRun) {
                writeFile(file, content);
            }
            result.addUpdatedFile(file.getPath());
            int diff = countDifferences(original, content);
            result.addReplacements(diff);
        }
    }

    // ==================== Utility methods ====================

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

    private File findClassFile(String projectRoot, String className) {
        List<File> files = findAllFiles(new File(projectRoot), new String[]{".java", ".kt"});
        for (File file : files) {
            String fileName = file.getName();
            int lastDot = fileName.lastIndexOf('.');
            if (lastDot > 0) {
                String nameWithoutExt = fileName.substring(0, lastDot);
                if (nameWithoutExt.equals(className)) {
                    return file;
                }
            }
        }
        return null;
    }

    private String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return lastDot > 0 ? fileName.substring(lastDot) : "";
    }

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
        return diffCount + Math.abs(originalLines.length - modifiedLines.length);
    }

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
        private final String oldPackage;
        private final String newPackage;
        private final List<String> updatedFiles;
        private final List<String> movedDirectories;
        private final List<String> errors;
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

    public static class ClassRenameResult {
        private final String oldClass;
        private final String newClass;
        private boolean classFileUpdated;
        private String newFilePath;
        private final List<String> referencingFiles;
        private final List<String> errors;
        private int totalReplacements;

        public ClassRenameResult(String oldClass, String newClass) {
            this.oldClass = oldClass;
            this.newClass = newClass;
            this.referencingFiles = new ArrayList<>();
            this.errors = new ArrayList<>();
            this.totalReplacements = 0;
        }

        public void setClassFileUpdated(boolean updated) { this.classFileUpdated = updated; }
        public void setNewFilePath(String path) { this.newFilePath = path; }
        public void addReferencingFile(String file) { referencingFiles.add(file); }
        public void addError(String error) { errors.add(error); }
        public void addReplacements(int count) { totalReplacements += count; }

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
        private final String oldMethod;
        private final String newMethod;
        private final List<String> updatedFiles;
        private final List<String> errors;
        private int totalReplacements;

        public MethodRenameResult(String oldMethod, String newMethod) {
            this.oldMethod = oldMethod;
            this.newMethod = newMethod;
            this.updatedFiles = new ArrayList<>();
            this.errors = new ArrayList<>();
            this.totalReplacements = 0;
        }

        public void addUpdatedFile(String file) { updatedFiles.add(file); }
        public void addError(String error) { errors.add(error); }
        public void addReplacements(int count) { totalReplacements += count; }

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
        private final String oldVar;
        private final String newVar;
        private final List<String> updatedFiles;
        private final List<String> errors;
        private int totalReplacements;

        public VariableRenameResult(String oldVar, String newVar) {
            this.oldVar = oldVar;
            this.newVar = newVar;
            this.updatedFiles = new ArrayList<>();
            this.errors = new ArrayList<>();
            this.totalReplacements = 0;
        }

        public void addUpdatedFile(String file) { updatedFiles.add(file); }
        public void addError(String error) { errors.add(error); }
        public void addReplacements(int count) { totalReplacements += count; }

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
        private final List<PackageRenameResult> packageResults;
        private final List<ClassRenameResult> classResults;
        private final List<MethodRenameResult> methodResults;
        private final List<VariableRenameResult> variableResults;

        public BatchRefactorResult() {
            this.packageResults = new ArrayList<>();
            this.classResults = new ArrayList<>();
            this.methodResults = new ArrayList<>();
            this.variableResults = new ArrayList<>();
        }

        public void addPackageResult(PackageRenameResult result) { packageResults.add(result); }
        public void addClassResult(ClassRenameResult result) { classResults.add(result); }
        public void addMethodResult(MethodRenameResult result) { methodResults.add(result); }
        public void addVariableResult(VariableRenameResult result) { variableResults.add(result); }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n========================================\n");
            sb.append("        BATCH REFACTOR RESULTS          \n");
            sb.append("========================================\n");

            sb.append("\nPackage Renames: ").append(packageResults.size());
            for (PackageRenameResult r : packageResults) sb.append(r.toString());

            sb.append("\nClass Renames: ").append(classResults.size());
            for (ClassRenameResult r : classResults) sb.append(r.toString());

            sb.append("\nMethod Renames: ").append(methodResults.size());
            for (MethodRenameResult r : methodResults) sb.append(r.toString());

            sb.append("\nVariable Renames: ").append(variableResults.size());
            for (VariableRenameResult r : variableResults) sb.append(r.toString());

            return sb.toString();
        }
    }

    public static class RefactorConfig {
        private final String operation;
        private final String oldValue;
        private final String newValue;

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