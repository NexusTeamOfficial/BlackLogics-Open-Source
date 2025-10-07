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
import com.besome.blacklogics.development.Complex;
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
	private Complex complex;
	
	public CompilerAsyncTask(Context context) {
		this.context = context;
		mContext = new WeakReference<>(context);
		complex = new Complex();
		complex.setC(context);
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
		if (complex != null) {
			complex.setId(sc_id);
		}
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
			if (dialog.getWindow() != null) {
				dialog.getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
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
					String jsonInput = FileUtil.readFile(jsonPath);
					if (jsonInput == null || jsonInput.trim().isEmpty()) {
						project.getLogger().e("JsonParser", "Library JSON file is empty or invalid: " + jsonPath);
						return new CompilerResult("Library JSON file is empty or invalid: " + jsonPath, true);
					}
					
					try {
						List<Library> libraries = JsonParser.parseLibrariesFromJson(jsonInput);
						if (libraries == null || libraries.isEmpty()) {
							project.getLogger().w("JsonParser", "No libraries found in JSON file: " + jsonPath);
						}
						project.setLibraries(libraries);
						project.getLogger().d("JsonParser", "Loaded " + libraries.size() + " libraries from JSON");
					} catch (JSONException e) {
						project.getLogger().e("JsonParser", "Failed to parse library JSON: " + e.getMessage());
						return new CompilerResult("Failed to parse JSON libraries: " + e.getMessage(), true);
					}
				} catch (Exception e) {
					project.getLogger().e("JsonParser", "Failed to read JSON file: " + e.getMessage());
					return new CompilerResult("Failed to read JSON file: " + e.getMessage(), true);
				}
			}
			
			// Copy icon.png
			String iconSource = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/icon.png";
			String iconDest = FileUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/app/src/main/res/mipmap-xhdpi/icon.png";
			if (FileUtil.isExistFile(iconSource)) {
				try {
					FileUtil.makeDir(new File(iconDest).getParent());
					FileUtil.copyFile(iconSource, iconDest);
					project.getLogger().d("CompilerAsyncTask", "Copied icon.png to " + iconDest);
				} catch (Exception e) {
					project.getLogger().e("CompilerAsyncTask", "Failed to copy icon.png: " + e.getMessage());
					return new CompilerResult("Failed to copy icon.png: " + e.getMessage(), true);
				}
			}
			
			// Copy drawable resources
			String drawableSource = FileUtil.getExternalStorageDir() + "/.blacklogics/resources/images/" + sc_id + "/";
			String drawableDest = FileUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/app/src/main/res/drawable-xhdpi";
			if (FileUtil.isExistFile(drawableSource)) {
				try {
					FileUtil.makeDir(drawableDest);
					FileUtil.copyDirectory(new File(drawableSource), new File(drawableDest));
					project.getLogger().d("CompilerAsyncTask", "Copied drawable resources to " + drawableDest);
				} catch (Exception e) {
					project.getLogger().e("CompilerAsyncTask", "Failed to copy drawable resources: " + e.getMessage());
					return new CompilerResult("Failed to copy drawable resources: " + e.getMessage(), true);
				}
			}
			
			// AAPT2 compilation
			publishProgress("Compiling resources with AAPT2...");
			Compiler aapt2Compiler = new AAPT2Compiler(project);
			aapt2Compiler.setProject(project);
			aapt2Compiler.setProgressListener(this::publishProgress);
			if (complex.getAndroidXEnable()) {
				aapt2Compiler.enableAndroidX();
			}
			aapt2Compiler.prepare();
			if (isCancelled()) return null;
			aapt2Compiler.run();
			
			// ECJ compilation
			publishProgress("Compiling Java sources with ECJ...");
			Compiler ecjCompiler = new ECJCompiler(project);
			ecjCompiler.setProject(project);
			ecjCompiler.setProgressListener(this::publishProgress);
			ecjCompiler.prepare();
			if (isCancelled()) return null;
			ecjCompiler.run();
			
			// ProGuard compilation (before R8)
			// ProGuard compilation (before R8)
			BuildSettings.DexCompilerType dexCompilerType = project.getBuildSettings().getDexCompilerType();
			if (dexCompilerType == BuildSettings.DexCompilerType.R8) {
				publishProgress("Optimizing with ProGuard...");
				project.getLogger().d("CompilerAsyncTask", "Running ProGuard before R8");
				Compiler proguardCompiler = new ProguardCompiler(project);
				proguardCompiler.setProject(project);
				proguardCompiler.setProgressListener(this::publishProgress);
				proguardCompiler.prepare();
				if (isCancelled()) return null;
				try {
					proguardCompiler.run();
				} catch (CompilerException e) {
					project.getLogger().e("CompilerAsyncTask", "ProGuard failed: " + e.getMessage());
					return new CompilerResult("ProGuard error: " + e.getMessage(), true);
				}
			} else if (dexCompilerType == BuildSettings.DexCompilerType.DX) {
				publishProgress("Using DX Compiler...");
				project.getLogger().d("CompilerAsyncTask", "Running DX compiler (legacy)");
				Compiler dxCompiler = new DXCompiler(project);
				dxCompiler.setProject(project);
				dxCompiler.setProgressListener(this::publishProgress);
				dxCompiler.prepare();
				if (isCancelled()) return null;
				try {
					dxCompiler.run();
				} catch (CompilerException e) {
					project.getLogger().e("CompilerAsyncTask", "DX compiler failed: " + e.getMessage());
					return new CompilerResult("DX compiler error: " + e.getMessage(), true);
				}
			}
			
			// DEX compilation (D8 or R8)
			publishProgress("Generating DEX files...");
			Compiler dexCompiler = null;
			if (dexCompilerType == BuildSettings.DexCompilerType.R8) {
				dexCompiler = new R8Compiler(project);
			} else if (dexCompilerType == BuildSettings.DexCompilerType.D8) {
				dexCompiler = new D8Compiler(project);
			}
		
			if (dexCompiler != null) {
				dexCompiler.setProject(project);
				dexCompiler.setProgressListener(this::publishProgress);
				dexCompiler.prepare();
				if (isCancelled()) return null;
				dexCompiler.run();
			}
			
			
			// APK packaging
			publishProgress("Packaging APK...");
			project.getLogger().d("APK Builder", "Packaging APK");
			
			File binDir = new File(project.getOutputFile(), "bin");
			File dexDir = new File(binDir, "dex");
			File apkPath = new File(binDir, "gen.apk");
			
			try {
				if (!binDir.exists() && !binDir.mkdirs()) {
					return new CompilerResult("Failed to create bin directory: " + binDir.getAbsolutePath(), true);
				}
				if (!apkPath.exists() && !apkPath.createNewFile()) {
					return new CompilerResult("Failed to create APK file: " + apkPath.getAbsolutePath(), true);
				}
			} catch (IOException e) {
				return new CompilerResult("IO error during file creation: " + e.getMessage(), true);
			}
			
			File resPath = new File(binDir, "generated.apk.res");
			if (!resPath.exists()) {
				return new CompilerResult("Resource file not found: " + resPath.getAbsolutePath(), true);
			}
			
			List<File> dexFiles = dexCompiler instanceof D8Compiler
			? ((D8Compiler) dexCompiler).getDexFiles()
			: new ArrayList<>();
			if (dexFiles.isEmpty()) {
				File r8Dex = new File(binDir, "classes.dex");
				if (r8Dex.exists()) {
					dexFiles.add(r8Dex);
				} else {
					return new CompilerResult("DEX files not found in: " + dexDir.getAbsolutePath(), true);
				}
			}
			
			ApkBuilder builder = new ApkBuilder(apkPath, resPath, dexFiles.get(0), null, null);
			for (int i = 1; i < dexFiles.size(); i++) {
				File extra = dexFiles.get(i);
				try {
					builder.addFile(extra, extra.getName());
					project.getLogger().d("APK Builder", "Added dex file: " + extra.getName());
				} catch (Exception e) {
					return new CompilerResult("Error adding dex file: " + e.getMessage(), true);
				}
			}
			
			for (Library library : project.getLibraries()) {
				File classesJar = new File(library.getPath(), "classes.jar");
				if (classesJar.exists()) {
					try {
						builder.addResourcesFromJar(classesJar);
					} catch (Exception e) {
						return new CompilerResult("Error adding library resources: " + e.getMessage(), true);
					}
				}
			}
			
			builder.setDebugMode(false);
			if (isCancelled()) return null;
			try {
				builder.sealApk();
			} catch (Exception e) {
				return new CompilerResult("APK packaging error: " + e.getMessage(), true);
			}
			
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
			
			new File(unsignedApk).delete();
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
			// ⚡ Remove keep screen on flag
			if (dialog.getWindow() != null) {
				dialog.getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
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
			// ⚡ Remove keep screen on flag
			if (dialog.getWindow() != null) {
				dialog.getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
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
