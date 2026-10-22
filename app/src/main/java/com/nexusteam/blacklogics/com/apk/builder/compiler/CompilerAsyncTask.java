package com.tyron.compiler;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.ColorStateList;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Environment;

import com.nexusteam.blacklogics.manager.ProjectSettingsManager;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;

import com.android.sdklib.build.ApkBuilder;
import com.apk.builder.apksigner.ApkSigner;
import com.apk.builder.FileUtil;
import com.apk.builder.model.BuildSettings;
import com.apk.builder.model.BuildSettings.DexCompilerType;
import com.apk.builder.model.Library;
import com.apk.builder.model.Project;
import com.nexusteam.blacklogics.DesignActivity;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.development.ProjectBuilder;
import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.file.FileCopyUtil;
import com.nexusteam.blacklogics.interfaces.CompilerLogListener;
import com.nexusteam.blacklogics.parser.JsonParser;
import com.nexusteam.blacklogics.parser.ProjectParser;
import com.nexusteam.blacklogics.project.encryption.*;
import com.nexusteam.blacklogics.project.encryption.dex.ApkProtector;
import com.nexusteam.blacklogics.project.encryption.dex.DexProtectorUtil;
import com.nexusteam.nexatask.NexaTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.elfilibustero.quizboard.QuizBoard;
import com.tyron.compiler.exception.CompilerException;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class CompilerAsyncTask extends NexaTask<Project, String, CompilerResult> {
	
	private final WeakReference<Context> mContext;
	private String jsonPath; 
	private String projectJsonPath;
	private String sc_id;
	private TextView progress;
	private FrameLayout quizContainer;
	private ImageView imageview1;
	private CompilerBottomSheetDialog dialog;
	private RecyclerView a;
	private long startTime;
	private Project project;
	private String url;
	public Context context;
	private CompilerLogListener logListener;
	
	private final Set<String> addedResourceEntries = new HashSet<String>();
	
	private ApkProtector apkProtector;
	
	private ProjectSettingsManager projectManager;
	
	private ProjectParser parser;
	private Complex complex;
	
	public CompilerAsyncTask(Context context) {
		this.context = context;
		mContext = new WeakReference<Context>(context);
		apkProtector = new ApkProtector(context);
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
		this.projectManager = new ProjectSettingsManager(sc_id);
		this.projectManager.load(context);
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
			dialog = new CompilerBottomSheetDialog(context);
			dialog.setCompilerTask(this);
			dialog.show();
			((CompilerBottomSheetDialog) dialog).updateProgress("Starting build...");
		}
	}
	
	@Override
	public CompilerResult doInBackground(Project... params) {
		project = params[0];
		try {
			project.getLogger().d("CompilerAsyncTask", "Starting build process");
			
			if (sc_id == null || sc_id.trim().isEmpty()) {
				project.getLogger().e("CompilerAsyncTask", "Invalid sc_id");
				return new CompilerResult("Invalid sc_id", true);
			}
			
			try {
				generateProjectSource();
			} catch (Exception e) {
				project.getLogger().e("ProjectBuilder", "Failed to generate source: " + e.getMessage());
				return new CompilerResult("Failed to generate source: " + e.getMessage(), true);
			}
			
			if (!hasStoragePermission()) {
				project.getLogger().e("CompilerAsyncTask", "Storage permission not granted");
				return new CompilerResult("Storage permission not granted", true);
			}
			
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
			
			publishProgress("Compiling resources with AAPT2...");
			Compiler aapt2Compiler = new AAPT2Compiler(project);
			aapt2Compiler.setProject(project);
			aapt2Compiler.setProgressListener(new Compiler.OnProgressUpdateListener() {
				@Override
				public void onProgressUpdate(String... update) {
					publishProgress(update);
				}
			});
			if (complex.getAndroidXEnable()) {
				aapt2Compiler.enableAndroidX();
			}
			aapt2Compiler.prepare();
			if (isCancelled()) return null;
			aapt2Compiler.run();
			
			publishProgress("Compiling Java sources with ECJ...");
			Compiler ecjCompiler = new ECJCompiler(project);
			ecjCompiler.setProject(project);
			ecjCompiler.setProgressListener(new Compiler.OnProgressUpdateListener() {
				@Override
				public void onProgressUpdate(String... update) {
					publishProgress(update);
				}
			});
			ecjCompiler.prepare();
			if (isCancelled()) return null;
			ecjCompiler.run();
			
			BuildSettings.DexCompilerType dexCompilerType = project.getBuildSettings().getDexCompilerType();
			
			boolean shouldRunProguard = false;
			try {
				com.nexusteam.sdk.project.proguard.ProguardHandler proguardHandler =
				new com.nexusteam.sdk.project.proguard.ProguardHandler(sc_id);
				shouldRunProguard = proguardHandler.isProguardEnabled();
			} catch (Throwable t) {
				try {
					if (projectManager != null && projectManager.isObfuscateCode()) {
						shouldRunProguard = true;
					}
				} catch (Throwable ignored) {}
			}
			
			if (shouldRunProguard) {
				publishProgress("Running ProGuard...");
				project.getLogger().d("CompilerAsyncTask", "ProGuard enabled → running ProguardCompiler (compiler=" + dexCompilerType + ")");
				
				try {
					Compiler proguardCompiler = new ProguardCompiler(project);
					proguardCompiler.setProject(project);
					proguardCompiler.setProgressListener(new Compiler.OnProgressUpdateListener() {
						@Override
						public void onProgressUpdate(String... update) {
							publishProgress(update);
						}
					});
					proguardCompiler.prepare();
					if (isCancelled()) return null;
					proguardCompiler.run();
					project.getLogger().d("CompilerAsyncTask", "ProGuard completed successfully — classes_proguard ready");
				} catch (CompilerException e) {
					project.getLogger().e("CompilerAsyncTask", "ProGuard failed: " + e.getMessage());
					return new CompilerResult("ProGuard error: " + e.getMessage(), true);
				} catch (Throwable t) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					t.printStackTrace(pw);
					String msg = "ProGuard unexpected error: " + t.getMessage() + "\n" + sw.toString();
					project.getLogger().e("CompilerAsyncTask", msg);
					return new CompilerResult(msg, true);
				}
			} else {
				project.getLogger().d("CompilerAsyncTask", "ProGuard not enabled → skipping ProGuard step");
			}
			
			publishProgress("Generating DEX files...");
			Compiler dexCompiler = null;
			
			if (dexCompilerType == BuildSettings.DexCompilerType.R8) {
				publishProgress("Using R8 Compiler...");
				project.getLogger().d("CompilerAsyncTask", "Running R8 compiler");
				dexCompiler = new R8Compiler(project);
				
			} else if (dexCompilerType == BuildSettings.DexCompilerType.DX) {
				publishProgress("Using DX Compiler...");
				project.getLogger().d("CompilerAsyncTask", "Running DX compiler (legacy)");
				Compiler dxCompiler = new DXCompiler(project);
				dxCompiler.setProject(project);
				dxCompiler.setProgressListener(new Compiler.OnProgressUpdateListener() {
					@Override
					public void onProgressUpdate(String... update) {
						publishProgress(update);
					}
				});
				dxCompiler.prepare();
				if (isCancelled()) return null;
				try {
					dxCompiler.run();
				} catch (CompilerException e) {
					project.getLogger().e("CompilerAsyncTask", "DX compiler failed: " + e.getMessage());
					return new CompilerResult("DX compiler error: " + e.getMessage(), true);
				}
				dexCompiler = null;
				
			} else if (dexCompilerType == BuildSettings.DexCompilerType.D8) {
				publishProgress("Using D8 Compiler...");
				project.getLogger().d("CompilerAsyncTask", "Running D8 compiler");
				dexCompiler = new D8Compiler(project);
				
			} else {
				project.getLogger().w("CompilerAsyncTask", "Unknown compiler type, using D8 as default");
				publishProgress("Using D8 Compiler (fallback)...");
				dexCompiler = new D8Compiler(project);
			}
			
			if (dexCompiler != null) {
				dexCompiler.setProject(project);
				dexCompiler.setProgressListener(new Compiler.OnProgressUpdateListener() {
					@Override
					public void onProgressUpdate(String... update) {
						publishProgress(update);
					}
				});
				dexCompiler.prepare();
				if (isCancelled()) return null;
				try {
					dexCompiler.run();
				} catch (CompilerException e) {
					String errorMsg = e.getMessage();
					project.getLogger().e("DEX Compiler", "DEX compilation failed: " + errorMsg);
					
					if (errorMsg == null || errorMsg.isEmpty()) {
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						errorMsg = "DEX compilation failed with no message. Stack trace: " + sw.toString();
					}
					
					return new CompilerResult("DEX compilation error: " + errorMsg, true);
				}
			}
			
			try {
				com.nexusteam.sdk.project.stringfog.StringfogHandler sfHandler =
				new com.nexusteam.sdk.project.stringfog.StringfogHandler(sc_id);
				if (sfHandler.isStringfogEnabled()) {
					publishProgress("Running StringFog...");
					project.getLogger().d("CompilerAsyncTask", "StringFog ON");
					Compiler stringFogCompiler = new StringFogCompiler(project);
					stringFogCompiler.setProject(project);
					stringFogCompiler.setProgressListener(new Compiler.OnProgressUpdateListener() {
						@Override
						public void onProgressUpdate(String... update) {
							publishProgress(update);
						}
					});
					stringFogCompiler.prepare();
					if (isCancelled()) return null;
					stringFogCompiler.run();
					project.getLogger().d("CompilerAsyncTask", "StringFog done");
				} else {
					project.getLogger().d("CompilerAsyncTask", "StringFog OFF");
				}
			} catch (Throwable sfEx) {
				project.getLogger().e("CompilerAsyncTask",
				"StringFog error (build continues): " + sfEx.getMessage());
			}
			
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
			
			List<File> dexFiles = new ArrayList<File>();
			
			if (dexCompiler instanceof D8Compiler) {
				dexFiles = ((D8Compiler) dexCompiler).getDexFiles();
				project.getLogger().d("APK Builder", "Using D8 dex files: " + dexFiles.size());
				
			} else if (dexCompiler instanceof R8Compiler) {
				dexFiles = ((R8Compiler) dexCompiler).getDexFiles();
				project.getLogger().d("APK Builder", "Using R8 dex files: " + dexFiles.size());
				for (File f : dexFiles) {
					project.getLogger().d("APK Builder", "✅ Found R8 dex: " + f.getName() + " (" + f.length() + " bytes)");
				}
				
				if (dexFiles.isEmpty()) {
					project.getLogger().e("APK Builder", "❌ No dex files found in: " + dexDir.getAbsolutePath());
					return new CompilerResult("No DEX files found in: " + dexDir.getAbsolutePath(), true);
				}
				
			} else if (dexCompiler instanceof DXCompiler) {
				dexFiles = ((DXCompiler) dexCompiler).getDexFiles();
				project.getLogger().d("APK Builder", "Using DX dex files: " + dexFiles.size());
				
			} else {
				File[] dexArr = dexDir.listFiles();
				if (dexArr != null) {
					for (File f : dexArr) {
						if (f.getName().endsWith(".dex")) {
							dexFiles.add(f);
							project.getLogger().d("APK Builder", "Found dex: " + f.getName());
						}
					}
				}
			}
			
			java.util.Collections.sort(dexFiles, new java.util.Comparator<File>() {
				@Override
				public int compare(File a, File b) {
					String nameA = a.getName();
					String nameB = b.getName();
					if ("classes.dex".equals(nameA)) return -1;
					if ("classes.dex".equals(nameB)) return 1;
					return nameA.compareTo(nameB);
				}
			});
			
			if (dexFiles.isEmpty()) {
				return new CompilerResult("No DEX files found in: " + dexDir.getAbsolutePath(), true);
			}
			
			project.getLogger().d("APK Builder", "📦 Total dex files: " + dexFiles.size());
			for (File f : dexFiles) {
				project.getLogger().d("APK Builder", "  - " + f.getName() + " (" + f.length() + " bytes)");
			}
			
			ApkBuilder builder = new ApkBuilder(apkPath, resPath, dexFiles.get(0), null, null);
			for (int i = 1; i < dexFiles.size(); i++) {
				File extra = dexFiles.get(i);
				if ("classes.dex".equals(extra.getName())) {
					project.getLogger().w("APK Builder", "Skipping duplicate classes.dex entry");
					continue;
				}
				try {
					builder.addFile(extra, extra.getName());
					project.getLogger().d("APK Builder", "Added dex file: " + extra.getName());
				} catch (Exception e) {
					return new CompilerResult("Error adding dex file: " + e.getMessage(), true);
				}
			}
			
			addedResourceEntries.clear();
			for (Library library : project.getLibraries()) {
				File classesJar = new File(library.getPath(), "classes.jar");
				if (classesJar.exists()) {
					try {
						addLibraryResourcesDeduped(builder, classesJar);
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
			
			if (projectManager.isResourceEncrypt()) {
				encryptResources();
			}
			if (projectManager.isObfuscateCode()) {
				publishProgress("Obfuscating Code...");
				project.getLogger().d("DexProtection", "Starting Dex protection");
				
				try {
					String unsignedApkPath = project.getOutputFile() + "/bin/gen.apk";
					
					
					String protectedApkPath = apkProtector.protectWithDexEncryption(unsignedApkPath);
					
					
					File originalApk = new File(unsignedApkPath);
					File protectedApk = new File(protectedApkPath);
					
					if (protectedApk.exists()) {
						if (originalApk.exists()) {
							originalApk.delete();
						}
						
						protectedApk.renameTo(originalApk);
					}
				} catch (Exception e) {
					project.getLogger().e("DexProtection", "Dex protection failed: " + e.getMessage());
					
				}
			}
			
			
			publishProgress("Signing APK...");
			project.getLogger().d("APK Signer", "Signing APK");
			String unsignedApk = project.getOutputFile() + "/bin/gen.apk";
			String signedApk = project.getOutputFile() + "/bin/" + project.getProjectName() + ".apk";
			File binDir1 = new File(project.getOutputFile(), "bin");
			
			try {
				new ApkSigner(project, apkPath.getAbsolutePath(), binDir1 + "/" + project.getProjectName() + ".apk", ApkSigner.Mode.TEST).sign();
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
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return new CompilerResult("Compilation error: " + e.getMessage() + "\nStack trace: " + sw.toString(), true);
		} catch (Exception e) {
			project.getLogger().e("CompilerAsyncTask", "Unexpected error: " + e.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return new CompilerResult("Unexpected error: " + e.getMessage() + "\nStack trace: " + sw.toString(), true);
		}
	}
	
	private boolean hasStoragePermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			boolean granted = Environment.isExternalStorageManager();
			if (!granted) {
				project.getLogger().e(
				"CompilerAsyncTask",
				"MANAGE_EXTERNAL_STORAGE not granted"
				);
			}
			return granted;
		}
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			boolean writeGranted =
			context.checkSelfPermission(
			android.Manifest.permission.WRITE_EXTERNAL_STORAGE
			) == PackageManager.PERMISSION_GRANTED;
			return writeGranted;
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
		if (!isCancelled() && dialog instanceof CompilerBottomSheetDialog) {
			((CompilerBottomSheetDialog) dialog).updateProgress(update[0]);
		}
	}
	
	@Override
	public void onPostExecute(CompilerResult result) {
		if (a != null) {
			a.setVisibility(View.GONE);
		}
		if (dialog != null && dialog.isShowing()) {
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
	
	private void addLibraryResourcesDeduped(ApkBuilder builder, File jarFile) throws Exception {
		ZipFile zipFile = new ZipFile(jarFile);
		File tempJar = File.createTempFile("lib_res_", ".jar", jarFile.getParentFile());
		boolean hasNewEntries = false;
		
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tempJar));
			try {
				java.util.Enumeration<? extends ZipEntry> entries = zipFile.entries();
				while (entries.hasMoreElements()) {
					ZipEntry entry = entries.nextElement();
					String name = entry.getName();
					
					if (name.endsWith(".class")) {
						continue;
					}
					
					if (!addedResourceEntries.add(name)) {
						project.getLogger().d("APK Builder", "Skipping duplicate resource entry: "
						+ name + " (from " + jarFile.getParentFile().getName() + ")");
						continue;
					}
					
					hasNewEntries = true;
					zos.putNextEntry(new ZipEntry(name));
					if (!entry.isDirectory()) {
						InputStream is = zipFile.getInputStream(entry);
						try {
							byte[] buf = new byte[8192];
							int len;
							while ((len = is.read(buf)) > 0) {
								zos.write(buf, 0, len);
							}
						} finally {
							is.close();
						}
					}
					zos.closeEntry();
				}
			} finally {
				zos.close();
			}
		} finally {
			zipFile.close();
		}
		
		if (hasNewEntries) {
			builder.addResourcesFromJar(tempJar);
		}
		tempJar.delete();
	}
	
	private void encryptResources() throws Exception {
		publishProgress("Encrypting resources...");
		project.getLogger().d("ResourceEncryption", "Starting resource obfuscation");
		
		String unsignedApk = project.getOutputFile() + "/bin/gen.apk";
		new EncryptResourceTask(unsignedApk, unsignedApk);
		
		project.getLogger().d("ResourceEncryption", "Resource obfuscation completed on gen.apk");
	}
	
	public void generateProjectSource() throws Exception {
		publishProgress("Generating source code...");
		
		Context context = mContext.get();
		if (context == null) {
			throw new Exception("Context is null");
		}
		
		Complex complex = new Complex();
		complex.setId(sc_id);
		
		AndroidManifestGenerator freshManifest = new AndroidManifestGenerator();
		freshManifest.load(context, sc_id);
		String freshPackageName = freshManifest.getPackageName();
		complex.setPkgName(freshPackageName != null && !freshPackageName.trim().isEmpty()
		? freshPackageName
		: project.getPackageName());
		
		ProjectBuilder builder = new ProjectBuilder(
		context, 
		complex, 
		FileUtil.getExternalStorageDir(), 
		sc_id
		);
		builder.setContext(context);
		builder.buildProject();
		
		project.getLogger().d("ProjectBuilder", "Source code generated successfully");
	}
	
	public class CompilerBottomSheetDialog extends BottomSheetDialog {
		
		private TextView progress;
		private FrameLayout quizContainer;
		private ImageView imageview1;
		private String url;
		private CompilerAsyncTask task;
		
		public CompilerBottomSheetDialog(@NonNull Context context) {
			super(context, com.google.android.material.R.style.Theme_Material3_Light_BottomSheetDialog);
			initialize();
		}
		
		public void setCompilerTask(CompilerAsyncTask task) {
			this.task = task;
		}
		
		private void initialize() {
			setContentView(R.layout.build_dialog);
			
			setCancelable(false);
			setCanceledOnTouchOutside(false);
			
			progress = findViewById(R.id.buildText);
			quizContainer = findViewById(R.id.quizContainer);
			imageview1 = findViewById(R.id.imageview1);
			
			QuizBoard mQuizBoard = new QuizBoard(getContext());
			mQuizBoard.setTimer(15000);
			quizContainer.addView(mQuizBoard);
			
			android.graphics.drawable.GradientDrawable gd2 = new android.graphics.drawable.GradientDrawable();
			gd2.setColor(android.R.color.transparent);
			gd2.setCornerRadius(360);
			imageview1.setClipToOutline(true);
			imageview1.setBackground(gd2);
			
			url = "android.resource://" + getContext().getPackageName() + "/raw/loading";
			Glide.with(getContext())
			.load(Uri.parse(url))
			.diskCacheStrategy(DiskCacheStrategy.NONE)   // disk cache skip
			.skipMemoryCache(true)                        // optional, memory cache skip
			.into(imageview1);
			
			setOnKeyListener(new DialogInterface.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialogInterface, int keyCode, android.view.KeyEvent event) {
					if (keyCode == android.view.KeyEvent.KEYCODE_BACK
					&& event.getAction() == android.view.KeyEvent.ACTION_UP) {
						
						if (progress != null) {
							progress.setText("Cancelling building...");
						}
						if (task != null) {
							task.cancel(true);
						}
						return true;
					}
					return false;
				}
			});
		}
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}
		
		@Override
		public void show() {
			super.show();
			if (getWindow() != null) {
				getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
		}
		
		@Override
		public void dismiss() {
			if (getWindow() != null) {
				getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
			super.dismiss();
		}
		
		public void updateProgress(String message) {
			if (progress != null) {
				progress.setText(message);
			}
		}
		
		private int getAttributeColor(Context context, int attrRes) {
			android.content.res.TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attrRes});
			int color = a.getColor(0, 0xFF000000);
			a.recycle();
			return color;
		}
	}
	
}
