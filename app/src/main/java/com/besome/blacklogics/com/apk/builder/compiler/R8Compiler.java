package com.tyron.compiler;

import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.tyron.compiler.exception.CompilerException;
import com.android.tools.r8.D8;
import com.android.tools.r8.R8;
import com.android.tools.r8.R8Command;
import com.android.tools.r8.OutputMode;
import com.android.tools.r8.origin.Origin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class R8Compiler extends Compiler {
    private static final String TAG = "R8";
    private Project mProject;
    private static final byte[] ENCRYPTION_KEY = "simplekey".getBytes(); // Placeholder key for XOR encryption

    public R8Compiler(Project project) {
        mProject = project;
    }

    @Override
    public void prepare() throws CompilerException {
        onProgressUpdate("R8 > Preparing...");
        mProject.getLogger().d(TAG, "Preparing R8 compilation");

        // Validate output directory
        File outputDir = new File(mProject.getOutputFile(), "bin");
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new CompilerException("Failed to create output directory: " + outputDir.getAbsolutePath());
        }

        // Validate android.jar
        File androidJar = getAndroidJarFile();
        if (!androidJar.exists()) {
            throw new CompilerException("android.jar not found: " + androidJar.getAbsolutePath());
        }

        // Check for D8-generated DEX files
        File dexDir = new File(mProject.getOutputFile(), "bin");
        if (!dexDir.exists() || dexDir.listFiles((dir, name) -> name.endsWith(".dex")).length == 0) {
            throw new CompilerException("No D8-generated DEX files found in: " + dexDir.getAbsolutePath());
        }
    }

    @Override
    public void run() throws CompilerException {
        onProgressUpdate("R8 > Optimizing and encrypting DEX files...");
        mProject.getLogger().d(TAG, "Running R8 compilation on D8-generated DEX files");

        try {
            // Collect D8-generated DEX files
            File dexDir = new File(mProject.getOutputFile(), "bin");
            List<Path> dexFiles = Files.list(dexDir.toPath())
                    .filter(path -> path.toString().endsWith(".dex"))
                    .collect(Collectors.toList());
            if (dexFiles.isEmpty()) {
                throw new CompilerException("No DEX files found in: " + dexDir.getAbsolutePath());
            }
            mProject.getLogger().d(TAG, "Found DEX files: " + dexFiles);

            // Collect library files (android.jar and library DEX/JAR files)
            List<Path> libraryFiles = new ArrayList<>();
            libraryFiles.add(getAndroidJarFile().toPath());

            for (Library library : mProject.getLibraries()) {
                File classJar = library.getClassJarFile();
                if (classJar.exists()) {
                    libraryFiles.add(classJar.toPath());
                    mProject.getLogger().d(TAG, "Added library JAR: " + classJar.getAbsolutePath());
                }

                for (File dexFile : library.getDexFiles()) {
                    if (dexFile.exists()) {
                        libraryFiles.add(dexFile.toPath());
                        mProject.getLogger().d(TAG, "Added library DEX: " + dexFile.getAbsolutePath());
                    }
                }
            }

            // Set up output path for R8
            Path outputPath = Paths.get(mProject.getOutputFile().getAbsolutePath(), "bin", "classes_optimized.dex");
            Files.createDirectories(outputPath.getParent());

            // Configure ProGuard rules (optional)
            List<Path> proguardRules = new ArrayList<>();
            String proguardFilePath = mProject.getProguardFile();
            if (proguardFilePath != null && new File(proguardFilePath).exists()) {
                proguardRules.add(Paths.get(proguardFilePath));
                mProject.getLogger().d(TAG, "Using ProGuard rules: " + proguardFilePath);
            }

            // Build R8 command to process DEX files
            R8Command.Builder builder = R8Command.builder()
                    .addProgramFiles(dexFiles)
                    .addLibraryFiles(libraryFiles)
                    .setMinApiLevel(mProject.getMinSdk())
                    .setOutput(outputPath, OutputMode.DexIndexed)
                    .setMode(com.android.tools.r8.CompilationMode.RELEASE)
                    .setDisableDesugaring(false);

            if (!proguardRules.isEmpty()) {
                builder.addProguardConfigurationFiles(proguardRules);
            }

            // Run R8
            R8.run(builder.build());
            mProject.getLogger().d(TAG, "R8 optimization completed successfully. Output: " + outputPath);

            // Encrypt the output DEX file
            Path encryptedOutputPath = Paths.get(mProject.getOutputFile().getAbsolutePath(), "bin", "classes_encrypted.dex");
            encryptDexFile(outputPath, encryptedOutputPath);
            mProject.getLogger().d(TAG, "DEX file encrypted successfully: " + encryptedOutputPath);

        } catch (Exception e) {
            String errorMsg = "R8 compilation or encryption failed: " + e.getMessage();
            mProject.getLogger().e(TAG, errorMsg);
            throw new CompilerException(errorMsg);
        }
    }

    // Simple XOR-based encryption for the DEX file
    private void encryptDexFile(Path inputPath, Path outputPath) throws IOException {
        mProject.getLogger().d(TAG, "Encrypting DEX file: " + inputPath + " to " + outputPath);
        try (FileInputStream fis = new FileInputStream(inputPath.toFile());
             FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            int keyIndex = 0;
            while ((bytesRead = fis.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    buffer[i] ^= ENCRYPTION_KEY[keyIndex % ENCRYPTION_KEY.length];
                    keyIndex++;
                }
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

    private List<File> getClassFiles(File dir) {
        List<File> files = new ArrayList<>();
        if (!dir.exists() || !dir.isDirectory()) {
            mProject.getLogger().w(TAG, "Class directory not found: " + dir.getAbsolutePath());
            return files;
        }

        File[] fileArr = dir.listFiles();
        if (fileArr == null) {
            mProject.getLogger().w(TAG, "No files found in class directory: " + dir.getAbsolutePath());
            return files;
        }

        for (File file : fileArr) {
            if (file.isDirectory()) {
                files.addAll(getClassFiles(file));
            } else if (file.getName().endsWith(".class")) {
                files.add(file);
            }
        }
        return files;
    }

    private void dexLibrary(Library library) throws CompilerException {
        mProject.getLogger().d(TAG, "Library " + library.getName() + " does not have a dex file, generating one");

        try {
            File classJar = library.getClassJarFile();
            if (!classJar.exists()) {
                throw new CompilerException("Library JAR not found: " + classJar.getAbsolutePath());
            }

            Path outputPath = Paths.get(library.getPath().getAbsolutePath(), "classes.dex");

            // Use D8 to generate DEX file for the library
            List<String> args = new ArrayList<>();
            args.add("--release");
            args.add("--min-api");
            args.add(String.valueOf(mProject.getMinSdk()));
            args.add("--lib");
            args.add(getAndroidJarFile().getAbsolutePath());
            args.add("--output");
            args.add(outputPath.getParent().toString());
            args.add(classJar.getAbsolutePath());

            D8.main(args.toArray(new String[0]));

            File dexFile = outputPath.toFile();
            if (dexFile.exists()) {
                library.getDexFiles().add(dexFile);
                mProject.getLogger().d(TAG, "Generated DEX file for library: " + dexFile.getAbsolutePath());
            } else {
                throw new CompilerException("Failed to generate DEX file for library: " + library.getName());
            }
        } catch (Exception e) {
            throw new CompilerException("Failed to dex library " + library.getName() + ": " + e.getMessage());
        }
    }
}