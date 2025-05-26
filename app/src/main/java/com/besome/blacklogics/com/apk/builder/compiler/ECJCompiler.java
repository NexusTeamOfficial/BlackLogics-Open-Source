package com.tyron.compiler;

import com.tyron.compiler.exception.CompilerException;
import com.apk.builder.FileUtil;
import com.apk.builder.util.Decompress;
import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.apk.builder.ApplicationLoader;
import org.eclipse.jdt.internal.compiler.batch.Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

public class ECJCompiler extends Compiler {
    private static final String TAG = "ECJ";
    private Project mProject;

    public ECJCompiler(Project project) {
        mProject = project;
    }

    @Override
    public void prepare() {
    }

    @Override
    public void run() throws CompilerException {
        onProgressUpdate("Compiling java files...");
        mProject.getLogger().d(TAG, "Running...");

        CompilerOutputStream errorOutputStream = new CompilerOutputStream(new StringBuffer());
        PrintWriter errWriter = new PrintWriter(errorOutputStream);

        CompilerOutputStream outputStream = new CompilerOutputStream(new StringBuffer());
        PrintWriter outWriter = new PrintWriter(outputStream);

        ArrayList<String> args = new ArrayList<>();
        args.add("-1.8");
        args.add("-proc:none");
        args.add("-nowarn");
        args.add("-d");
        File file = new File(mProject.getOutputFile() + "/bin/classes/");
        file.mkdir();
        args.add(file.getAbsolutePath());

        args.add("-cp");
        StringBuilder libraryString = new StringBuilder();
        libraryString.append(getAndroidJarFile().getAbsolutePath()).append(":");
        for (Library library : mProject.getLibraries()) {
            File classFile = library.getClassJarFile();
            if (classFile.exists()) {
                libraryString.append(classFile.getAbsolutePath()).append(":");
            }
        }
        libraryString.append(getLambdaFactoryFile().getAbsolutePath());
        args.add(libraryString.toString());

        args.add("-sourcepath");
        args.add(" ");

        // Add all Java source directories
        for (File javaDir : mProject.getJavaFiles()) {
            if (javaDir.exists() && javaDir.isDirectory()) {
                for (File javaFile : getJavaFiles(javaDir)) {
                    args.add(javaFile.getAbsolutePath());
                }
            }
        }

        // Add generated Java files
        for (File resourceFile : getJavaFiles(new File(mProject.getOutputFile() + "/gen"))) {
            args.add(resourceFile.getAbsolutePath());
        }

        Main main = new Main(outWriter, errWriter, false, null, null);
        try {
            main.compile(args.toArray(new String[0]));
        } catch (Exception e) {
            errWriter.flush(); // Ensure error output is captured
            String errorMessage = errorOutputStream.buffer.toString();
            if (errorMessage.isEmpty()) {
                errorMessage = "Compilation failed due to exception: " + e.getMessage();
            } else {
                errorMessage = errorMessage + "\nException: " + e.getMessage();
            }
            throw new CompilerException(errorMessage);
        }

        errWriter.flush(); // Ensure any remaining error output is captured
        if (main.globalErrorsCount > 0) {
            String errorMessage = errorOutputStream.buffer.toString();
            if (errorMessage.isEmpty()) {
                // Rare case: no output captured, provide a minimal fallback
                errorMessage = "Compilation failed: No error details captured by compiler.";
            }
            throw new CompilerException(errorMessage);
        }
    }

    private File getLambdaFactoryFile() {
        File check = new File(ApplicationLoader.applicationContext.getFilesDir() + "/temp/core-lambda-stubs.jar");
        if (check.exists()) {
            return check;
        }
        Decompress.unzipFromAssets(ApplicationLoader.applicationContext, "core-lambda-stubs.zip", check.getParentFile().getAbsolutePath());
        return check;
    }

    private List<File> getJavaFiles(File dir) {
        List<File> files = new ArrayList<>();
        File[] childs = dir.listFiles();
        if (childs != null) {
            for (File child : childs) {
                if (child.isDirectory()) {
                    files.addAll(getJavaFiles(child));
                    continue;
                }
                if (child.getName().endsWith(".java")) {
                    files.add(child);
                }
            }
        }
        return files;
    }

    private class CompilerOutputStream extends OutputStream {
        public StringBuffer buffer;

        public CompilerOutputStream(StringBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void write(int b) {
            buffer.append((char) b); // Always append to buffer
            if (b == '\n') {
                mProject.getLogger().d(TAG, buffer.toString());
                // Do not clear buffer, keep it for final error message
            }
        }

        @Override
        public void flush() {
            if (buffer.length() > 0) {
                mProject.getLogger().d(TAG, buffer.toString());
                // Do not clear buffer, keep it for final error message
            }
        }
    }
}