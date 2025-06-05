package com.tyron.compiler;

import android.os.AsyncTask;
import android.os.Build;
import android.content.Context;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.net.Uri;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import com.android.sdklib.build.ApkBuilder;
import com.apk.builder.FileUtil;
import com.besome.blacklogics.interfaces.CompilerLogListener;
import com.besome.blacklogics.R;
import com.besome.blacklogics.DesignActivity;
import com.besome.blacklogics.parser.JsonParser;
import com.besome.blacklogics.parser.ProjectParser;

import com.besome.blacklogics.file.FileCopyUtil;

import com.bumptech.glide.Glide;
import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.apk.builder.model.BuildSettings;
import com.apk.builder.model.BuildSettings.DexCompilerType;
import com.elfilibustero.quizboard.QuizBoard;
import com.tyron.compiler.exception.CompilerException;
import org.json.JSONException;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class CompilerAsyncTask extends AsyncTask<Project, String, CompilerResult> {
	
	private final WeakReference<Context> mContext;
	private String jsonPath; // Library JSON path
	private String projectJsonPath; // Project JSON file path
	private String sc_id;
	private TextView progress;
	private FrameLayout quizContainer;
	private ImageView imageview1;
	private Dialog dialog;
	private RecyclerView a;
	private long startTime;
	private Project project;
	private String url;
	public Context context;
	private CompilerLogListener logListener;
	
	private ProjectParser parser;
	
	public CompilerAsyncTask(Context context) {
		this.context = context;
		mContext = new WeakReference<>(context);
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public void setRecycler(RecyclerView a) {
		this.a = a;
	}
	
	public void setLibraryJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}
	
	public void setProjectJson(String projectJsonPath) {
		this.projectJsonPath = projectJsonPath;
	}
	
	public void setScId(String sc_id) {
		this.sc_id = sc_id;
	}
	
	public void setLogListener(CompilerLogListener listener) {
		this.logListener = listener;
	}
	
	@Override
	public void onPreExecute() {
		Context context = mContext.get();
		startTime = System.currentTimeMillis();
		
		if (context != null) {
			dialog = new Dialog(context);
			dialog.setContentView(R.layout.build_dialog);
			dialog.setCancelable(false);
			dialog.getWindow().setBackgroundDrawableResource(R.color.white);
			progress = dialog.findViewById(R.id.buildText);
			quizContainer = dialog.findViewById(R.id.quizContainer);
			QuizBoard mQuizBoard = new QuizBoard(context);
			mQuizBoard.setTimer(15000);
			quizContainer.addView(mQuizBoard);
			imageview1 = dialog.findViewById(R.id.imageview1);
			android.graphics.drawable.GradientDrawable gd2 = new android.graphics.drawable.GradientDrawable();
			gd2.setColor(android.R.color.transparent);
			gd2.setCornerRadius(360);
			imageview1.setClipToOutline(true);
			imageview1.setBackground(gd2);
			url = "android.resource://" + context.getPackageName() + "/raw/loading";
			Glide.with(context).load(Uri.parse(url)).into(imageview1);
			
			dialog.setOnKeyListener((dialogInterface, keyCode, event) -> {
				if (keyCode == android.view.KeyEvent.KEYCODE_BACK && event.getAction() == android.view.KeyEvent.ACTION_UP) {
					progress.setText("Cancelling building...");
					cancel(true);
					return true;
				}
				return false;
			});
			
			dialog.show();
		}
	}
	
	@Override
	public CompilerResult doInBackground(Project... params) {
		project = params[0];
		try {
			project.getLogger().d("CompilerAsyncTask", "Starting build process");
			
			// Validate sc_id
			if (sc_id == null || sc_id.trim().isEmpty()) {
				project.getLogger().e("CompilerAsyncTask", "Invalid sc_id");
				return new CompilerResult("Invalid sc_id", true);
			}
			
			// Check storage permissions
			if (!hasStoragePermission()) {
				project.getLogger().e("CompilerAsyncTask", "Storage permission not granted");
				return new CompilerResult("Storage permission not granted", true);
			}
			
			// Parse library JSON
			if (jsonPath != null && !jsonPath.trim().isEmpty()) {
				try {
					String jsonInput = readJsonFromFile(jsonPath);
					
					if (jsonInput == null || jsonInput.trim().isEmpty()) {
						project.getLogger().e("JsonParser", "Library JSON file is empty or invalid: " + jsonPath);
						return new CompilerResult("Library JSON file is empty or invalid: " + jsonPath, true);
					}
					
					try {
						List<Library> libraries = JsonParser.parseLibrariesFromJson(jsonInput);
						/*
						if (libraries == null || libraries.isEmpty()) {
						project.getLogger().e("JsonParser", "No libraries found in JSON file: " + jsonPath);
						return new CompilerResult("No libraries found in JSON file: " + jsonPath, true);
						}
						*/
						project.setLibraries(libraries);
					} catch (JSONException e) {
						project.getLogger().e("JsonParser", "Failed to parse library JSON: " + e.getMessage());
						return new CompilerResult("Failed to parse JSON libraries: " + e.getMessage(), true);
					}
				} catch (Exception e) {
					project.getLogger().e("JsonParser", "Unexpected error processing JSON file: " + e.getMessage());
					return new CompilerResult("Unexpected error processing JSON file: " + e.getMessage(), true);
				}
			}/*else {
			project.getLogger().e("JsonParser", "JSON path is null or empty.");
			return new CompilerResult("JSON path is null or empty.", true);
			}*/
			
			
			if (FileUtil.isExistFile(FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/icon.png")) {
				String source = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/icon.png";
				String destination = FileUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/app/src/main/res/mipmap-xhdpi/icon.png";
				
				FileUtil.copyFile(source, destination);
			}
			
			if (FileUtil.isExistFile(FileUtil.getExternalStorageDir() + "/.blacklogics/resources/images/" + sc_id + "/")) {
				File req = new File(FileUtil.getExternalStorageDir() + "/.blacklogics/resources/images/" + sc_id + "/");
				File urj = new File(FileUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/app/src/main/res/drawable-xhdpi");
				
				FileUtil.copyDirectory(req, urj);
			}
			
			
			// AAPT2 compilation
			Compiler aapt2Compiler = new AAPT2Compiler(project);
			aapt2Compiler.setProject(project);
			aapt2Compiler.setProgressListener(this::publishProgress);
			if (DesignActivity.complex.getAndroidXEnable()) {
				aapt2Compiler.enableAndroidX();
			}
			aapt2Compiler.prepare();
			if (isCancelled()) return null;
			aapt2Compiler.run();
			
			// ECJ compilation
			Compiler ecjCompiler = new ECJCompiler(project);
			ecjCompiler.setProgressListener(this::publishProgress);
			ecjCompiler.prepare();
			if (isCancelled()) return null;
			ecjCompiler.run();
			
			// DEX compilation (Dynamic selection of D8 or R8)
			Compiler dexCompiler;
			DexCompilerType dexCompilerType = project.getBuildSettings().getDexCompilerType();
			
			// Always run D8Compiler first to generate DEX files
			project.getLogger().d("CompilerAsyncTask", "Using D8 Compiler to generate DEX files");
			dexCompiler = new D8Compiler(project);
			dexCompiler.setProgressListener(this::publishProgress);
			dexCompiler.prepare();
			if (isCancelled()) return null;
			dexCompiler.run();
			
			// If R8 is selected, run R8Compiler to optimize and encrypt DEX files
			if (DexCompilerType.R8.equals(dexCompilerType)) {
				project.getLogger().d("CompilerAsyncTask", "Using R8 Compiler for optimization and encryption");
				Compiler r8Compiler = new R8Compiler(project);
				r8Compiler.setProgressListener(this::publishProgress);
				r8Compiler.prepare();
				if (isCancelled()) return null;
				r8Compiler.run();
			}
			
			
			// APK packaging
			publishProgress("Packaging APK...");
			project.getLogger().d("APK Builder", "Packaging APK");
			File binDir = new File(project.getOutputFile(), "bin");
			File apkPath = new File(binDir, "gen.apk");
			try {
				if (!binDir.exists() && !binDir.mkdirs()) {
					return new CompilerResult("Failed to create bin directory: " + binDir.getAbsolutePath(), true);
				}
				if (!apkPath.exists() && !apkPath.createNewFile()) {
					return new CompilerResult("Failed to create APK file: " + apkPath.getAbsolutePath(), true);
				}
			} catch (IOException e) {
				project.getLogger().e("APK Builder", "IO error during file creation: " + e.getMessage());
				return new CompilerResult("IO error during file creation: " + e.getMessage(), true);
			}
			
			File resPath = new File(binDir, "generated.apk.res");
			if (!resPath.exists()) {
				return new CompilerResult("Resource file not found: " + resPath.getAbsolutePath(), true);
			}
			File dexFile = new File(binDir, "classes.dex");
			if (!dexFile.exists()) {
				return new CompilerResult("Dex file not found: " + dexFile.getAbsolutePath(), true);
			}
			
			ApkBuilder builder = new ApkBuilder(apkPath, resPath, dexFile, null, null);
			File[] binFiles = binDir.listFiles();
			if (binFiles != null) {
				for (File file : binFiles) {
					if (!file.getName().equals("classes.dex") && file.getName().endsWith(".dex")) {
						try {
							builder.addFile(file, Uri.parse(file.getAbsolutePath()).getLastPathSegment());
							project.getLogger().d("APK Builder", "Added dex file: " + file.getName());
						} catch (Exception e) {
							project.getLogger().e("APK Builder", "Error adding dex file: " + e.getMessage());
							return new CompilerResult("Error adding dex file: " + e.getMessage(), true);
						}
					}
				}
			}
			
			for (Library library : project.getLibraries()) {
				File classesJar = new File(library.getPath(), "classes.jar");
				if (classesJar.exists()) {
					try {
						builder.addResourcesFromJar(classesJar);
						project.getLogger().d("APK Builder", "Added resources from library: " + library.getName());
					} catch (Exception e) {
						project.getLogger().e("APK Builder", "Error adding library resources: " + e.getMessage());
						return new CompilerResult("Error adding library resources: " + e.getMessage(), true);
					}
				}
			}
			builder.setDebugMode(false);
			if (isCancelled()) return null;
			try {
				builder.sealApk();
			} catch (Exception e) {
				project.getLogger().e("APK Builder", "APK packaging error: " + e.getMessage());
				return new CompilerResult("APK packaging error: " + e.getMessage(), true);
			}
			
			// Signing APK
			publishProgress("Signing APK...");
			project.getLogger().d("APK Signer", "Signing APK");
			String unsignedApk = apkPath.getAbsolutePath();
			String signedApk = project.getOutputFile() + "/bin/" + project.getProjectName() + ".apk";
			try {
				apksigner.Main.sign(new File(unsignedApk), signedApk);
			} catch (Exception e) {
				project.getLogger().e("APK Signer", "Signing error: " + e.getMessage());
				return new CompilerResult("Signing error: " + e.getMessage(), true);
			}
			
			File unsignedApkFile = new File(unsignedApk);
			if (unsignedApkFile.exists()) {
				if (!unsignedApkFile.delete()) {
					project.getLogger().w("APK Signer", "Failed to delete temporary unsigned APK: " + unsignedApk);
				}
			}
			
			long time = System.currentTimeMillis() - startTime;
			project.getLogger().d("CompilerAsyncTask", "Build completed successfully in " + time + "ms");
			return new CompilerResult("Success", false);
			
		} catch (CompilerException e) {
			project.getLogger().e("CompilerAsyncTask", "Compilation error: " + e.getMessage());
			return new CompilerResult("Compilation error: " + e.getMessage(), true);
		} catch (Exception e) {
			project.getLogger().e("CompilerAsyncTask", "Unexpected error: " + e.getMessage());
			return new CompilerResult("Unexpected error: " + e.getMessage(), true);
		}
	}
	
	private boolean hasStoragePermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			boolean readGranted = context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED;
			boolean writeGranted = context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED;
			if (!readGranted || !writeGranted) {
				project.getLogger().e("CompilerAsyncTask", "Missing permissions: READ_EXTERNAL_STORAGE=" + readGranted + ", WRITE_EXTERNAL_STORAGE=" + writeGranted);
			}
			return readGranted && writeGranted;
		}
		return true;
	}
	
	private String readJsonFromFile(String filePath) {
		if (filePath == null || filePath.trim().isEmpty()) {
			project.getLogger().w("JsonParser", "File path is null or empty");
			return null;
		}
		
		File file = new File(filePath);
		if (!file.exists()) {
			project.getLogger().w("JsonParser", "JSON file not found: " + filePath);
			return null;
		}
		
		if (file.length() <= 0) {
			project.getLogger().w("JsonParser", "JSON file is empty: " + filePath);
			return null;
		}
		
		try {
			return FileUtil.readFile(filePath);
		} catch (Exception e) {
			project.getLogger().w("JsonParser", "Error reading JSON file: " + filePath);
			return null;
		}
	}
	
	
	@Override
	public void onProgressUpdate(String... update) {
		if (!isCancelled()) {
			progress.setText(update[0]);
		}
	}
	
	@Override
	public void onPostExecute(CompilerResult result) {
		if (a != null) {
			a.setVisibility(View.GONE);
		}
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		if (result != null && result.isError()) {
			if (logListener != null) {
				logListener.onErrorLog(result.getMessage());
			}
		} else {
			if (logListener != null) {
				logListener.onLog("[SUCCESS] Build completed");
			}
			installApk(project.getOutputFile() + "/bin/" + project.getProjectName() + ".apk");
		}
	}
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
		if (a != null) {
			a.setVisibility(View.GONE);
		}
		if (dialog != null && dialog.isShowing()) {
			Toast.makeText(mContext.get(), "Cancel success", Toast.LENGTH_SHORT).show();
			dialog.dismiss();
		}
	}
	
	public void installApk(final String apk) {
		String PATH = apk;
		java.io.File file = new java.io.File(PATH);
		if (file.exists()) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(uriFromFile(context, new java.io.File(PATH)), "application/vnd.android.package-archive");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			try {
				context.startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("TAG", "Error in opening the file!");
			}
		} else {
			Toast.makeText(context, "APK not found", Toast.LENGTH_LONG).show();
		}
	}
	
	Uri uriFromFile(Context context, java.io.File file) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			return androidx.core.content.FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
		} else {
			return Uri.fromFile(file);
		}
	}
    
    public void initlizeLibs() {
        
    }
}
