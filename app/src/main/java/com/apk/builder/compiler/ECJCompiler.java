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

		String sourceTarget = "17";
		try {
			if (mProject.getBuildSettings() != null
					&& mProject.getBuildSettings().getDexCompilerType()
						== com.apk.builder.model.BuildSettings.DexCompilerType.DX) {
				sourceTarget = "1.7";
				mProject.getLogger().d(TAG, "DX selected → compiling with -source/-target 1.7 (no invokedynamic)");
			} else {
				mProject.getLogger().d(TAG, "D8/R8/default → compiling with -source/-target 17");
			}
		} catch (Throwable t) {
			mProject.getLogger().w(TAG, "Could not read DexCompilerType, defaulting to 17: " + t.getMessage());
		}

		args.add("-source");
		args.add(sourceTarget);

		args.add("-target");
		args.add(sourceTarget);

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

		for (File javaDir : mProject.getJavaFiles()) {
			if (javaDir.exists() && javaDir.isDirectory()) {
				for (File javaFile : getJavaFiles(javaDir)) {
					args.add(javaFile.getAbsolutePath());
				}
			}
		}

		for (File resourceFile : getJavaFiles(new File(mProject.getOutputFile() + "/gen"))) {
			args.add(resourceFile.getAbsolutePath());
		}

		Main main = new Main(outWriter, errWriter, false, null, null);
		boolean compileSucceeded;
		try {
			compileSucceeded = main.compile(args.toArray(new String[0]));
		} catch (Exception e) {
			errWriter.flush();
			String errorMessage = errorOutputStream.buffer.toString();
			if (errorMessage.isEmpty()) {
				errorMessage = "Compilation failed due to exception: " + e.getMessage();
			} else {
				errorMessage = errorMessage + "\nException: " + e.getMessage();
			}
			throw new CompilerException(errorMessage);
		}

		errWriter.flush();

		if (main.globalErrorsCount > 0) {
			String errorMessage = errorOutputStream.buffer.toString();
			if (errorMessage.isEmpty()) {
				errorMessage = "Compilation failed: No error details captured by compiler.";
			}
			throw new CompilerException(errorMessage);
		}

		if (!compileSucceeded) {
			String errorMessage = errorOutputStream.buffer.toString();
			if (errorMessage.isEmpty()) {
				errorMessage = "ECJ compile() returned false with no error details - " +
					"check compiler arguments and that Java source files were found.";
			}
			throw new CompilerException(errorMessage);
		}

		File classesDir = new File(mProject.getOutputFile() + "/bin/classes/");
		File[] classFiles = classesDir.listFiles();
		if (classFiles == null || classFiles.length == 0) {
			throw new CompilerException("ECJ reported success but bin/classes is empty - " +
				"likely no Java source files were found or all were skipped. " +
				"Check mProject.getJavaFiles() and gen/ folder.");
		}

		mProject.getLogger().d(TAG, "Compilation successful, " + classFiles.length + " entries in bin/classes");
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
			buffer.append((char) b);
			if (b == '\n') {
				mProject.getLogger().d(TAG, buffer.toString());
			}
		}

		@Override
		public void flush() {
			if (buffer.length() > 0) {
				mProject.getLogger().d(TAG, buffer.toString());
			}
		}
	}
}