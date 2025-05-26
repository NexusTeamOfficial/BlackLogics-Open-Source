package com.tyron.compiler;

import android.util.Log;

import com.tyron.compiler.exception.CompilerException;

import com.apk.builder.FileUtil;
import com.apk.builder.BinaryExecutor;
import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.apk.builder.ApplicationLoader;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class AAPT2Compiler extends Compiler {

    private static final String TAG = "AAPT2";

    private Project mProject;
    private final File mFilesDir;
    private List<Library> mLibraries;
    private List<Library> mLibrariesCopy;

    private File binDir;
    private File genDir;
    private File outputPath;
    private File resPath;

    private BinaryExecutor executor;

    public AAPT2Compiler(Project project) {
        mProject = project;
        mFilesDir = ApplicationLoader.applicationContext.getFilesDir();
    }

    @Override
    public void prepare() throws CompilerException {
        mProject.getLogger().d(TAG, "Preparing");
        onProgressUpdate("Preparing AAPT2...");

        mLibraries = new ArrayList<>();
        mLibraries.addAll(mProject.getLibraries());
        mLibrariesCopy = new ArrayList<>(mLibraries);

        // Log library details
        for (Library library : mLibraries) {
            mProject.getLogger().d(TAG, "Library: " + library.getName() + ", Resource Path: " + library.getResourcesFile().getAbsolutePath());
            if (!library.getResourcesFile().exists()) {
                mProject.getLogger().w(TAG, "Library resource directory missing: " + library.getResourcesFile().getAbsolutePath());
            }
        }

        binDir = new File(mProject.getOutputFile(), "bin");
        genDir = new File(mProject.getOutputFile(), "gen");
        genDir.delete();

        // Delete existing library .zip files to force recompilation
        File resDir = new File(binDir, "res");
        if (resDir.exists()) {
            File[] files = resDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".zip")) {
                        file.delete();
                        mProject.getLogger().d(TAG, "Deleted cached resource: " + file.getName());
                    }
                }
            }
        }

        File[] childs = binDir.listFiles();
        if (childs != null) {
            for (File child : childs) {
                if (child.getName().equals("res")) {
                    continue;
                }
                child.delete();
            }
        }

        FileUtil.makeDir(binDir.getPath());
        FileUtil.makeDir(genDir.getPath());
    }

    @Override
    public void run() throws CompilerException, IOException {
        ArrayList<String> args = new ArrayList<>();
        executor = new BinaryExecutor();

        // Compile resources
        onProgressUpdate("AAPT2 > Compiling resources");
        mProject.getLogger().d(TAG, "Compiling project resources");

        resPath = new File(binDir, "res");
        resPath.mkdir();

        // Compile each resources directory
        if (mProject.getResourcesFiles() != null) {
            for (File resourcesDir : mProject.getResourcesFiles()) {
                if (!resourcesDir.exists()) {
                    mProject.getLogger().w(TAG, "Resources directory does not exist: " + resourcesDir.getAbsolutePath());
                    continue;
                }

                mProject.getLogger().d(TAG, "Compiling resource directory: " + resourcesDir.getAbsolutePath());
                args.clear();
                args.add(getAAPT2File().getAbsolutePath());
                args.add("compile");
                args.add("--dir");
                args.add(resourcesDir.getAbsolutePath());
                args.add("-o");

                String outputFileName = "project_" + resourcesDir.getName() + ".zip";
                outputPath = createNewFile(resPath, outputFileName);

                args.add(outputPath.getAbsolutePath());
                mProject.getLogger().d(TAG, "Compile command: " + args);

                executor.setCommands(args);
                String result = executor.execute();
                mProject.getLogger().d(TAG, "Compile output: " + result);
                if (!result.isEmpty()) {
                    mProject.getLogger().e(TAG, "Resource compilation failed: " + result);
                    throw new CompilerException(result);
                }
            }
        }

        onProgressUpdate("AAPT2 > Compiling libraries");
        compileLibraries();

        args.clear();

        // Link resources
        onProgressUpdate("AAPT2 > Linking resources");
        mProject.getLogger().d(TAG, "Linking resources");

        args.add(getAAPT2File().getAbsolutePath());
        args.add("link");
        args.add("--allow-reserved-package-id");
        args.add("--no-version-vectors");
        args.add("--no-version-transitions");
        args.add("--auto-add-overlay");
        args.add("--min-sdk-version");
        args.add(String.valueOf(mProject.getMinSdk()));
        args.add("--target-sdk-version");
        args.add(String.valueOf(mProject.getTargetSdk()));
        args.add("--version-code");
        args.add(String.valueOf(mProject.getVersionCode()));
        args.add("--version-name");
        args.add(String.valueOf(mProject.getVersionName()));
        args.add("-I");
        args.add(getAndroidJarFile().getAbsolutePath());

        if (mProject.getAssetsFile() != null) {
            args.add("-A");
            args.add(mProject.getAssetsFile().getAbsolutePath());
        }

        File[] resources = resPath.listFiles();
        if (resources != null) {
            for (File file : resources) {
                if (file.isDirectory()) {
                    continue;
                }
                if (file.getName().endsWith(".zip")) {
                    args.add("-R");
                    args.add(file.getAbsolutePath());
                }
            }
        }

        File projectZip = new File(resPath, "project.zip");
        if (projectZip.exists()) {
            args.add("-R");
            args.add(projectZip.getAbsolutePath());
        }

        args.add("--java");
        args.add(genDir.getAbsolutePath() + "/");

        args.add("--manifest");
        args.add(mProject.getManifestFile().getAbsolutePath());

        StringBuilder sb = new StringBuilder();
        for (Library library : mLibrariesCopy) {
            if (library.requiresResourceFile()) {
                mProject.getLogger().d(TAG, "Adding extra package: " + library.getPackageName());
                sb.append(library.getPackageName());
                sb.append(":");
            }
        }

        if (!sb.toString().isEmpty()) {
            args.add("--extra-packages");
            args.add(sb.toString().substring(0, sb.toString().length() - 1));
        }

        args.add("-o");
        args.add(createNewFile(binDir, "generated.apk.res").getAbsolutePath());
        mProject.getLogger().d(TAG, "Link command: " + args);

        executor.setCommands(args);
        String result = executor.execute();
        mProject.getLogger().d(TAG, "Link output: " + result);
        if (!result.isEmpty()) {
            mProject.getLogger().e(TAG, "Resource linking failed: " + result);
            throw new CompilerException(result);
        }
    }

    private void compileLibraries() throws CompilerException, IOException {
        ArrayList<String> args = new ArrayList<>();

        for (Library library : mLibraries) {
            File resFile = library.getResourcesFile();
            if (!resFile.exists()) {
                mProject.getLogger().w(TAG, "Resource folder doesn't exist at path: " + resFile.getAbsolutePath());
                continue;
            }

            mProject.getLogger().d(TAG, "Compiling library: " + library.getName() + " at " + resFile.getAbsolutePath());
            args.clear();
            args.add(getAAPT2File().getAbsolutePath());
            args.add("compile");
            args.add("--dir");
            args.add(resFile.getAbsolutePath());
            args.add("-o");
            File output = createNewFile(resPath, library.getName() + ".zip");
            args.add(output.getAbsolutePath());
            mProject.getLogger().d(TAG, "Library compile command: " + args);

            executor.setCommands(args);
            String result = executor.execute();
            mProject.getLogger().d(TAG, "Library compile output: " + result);
            if (!result.isEmpty()) {
                mProject.getLogger().e(TAG, "Failed to compile library " + library.getName() + ": " + result);
                throw new CompilerException("AAPT2: " + result);
            }
        }
    }

    private File createNewFile(File parent, String name) throws IOException {
        File createdFile = new File(parent, name);
        parent.mkdirs();
        createdFile.createNewFile();
        return createdFile;
    }

    private File getAAPT2File() throws CompilerException, IOException {
        File nativeLibrary = new File(ApplicationLoader.applicationContext.getApplicationInfo().nativeLibraryDir + "/libaapt2.so");

        if (!nativeLibrary.exists()) {
            throw new CompilerException("AAPT2 binary not found");
        }

        mProject.getLogger().d(TAG, "AAPT2 binary path: " + nativeLibrary.getAbsolutePath());
        return nativeLibrary;
    }
}