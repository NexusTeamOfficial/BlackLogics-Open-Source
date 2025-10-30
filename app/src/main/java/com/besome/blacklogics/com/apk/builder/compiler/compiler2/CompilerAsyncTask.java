package com.apk.builder.compiler.compiler2;

import android.os.AsyncTask;
import android.content.Context;
import android.app.Dialog;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.net.Uri;

import com.android.sdklib.build.ApkBuilder;
import com.android.sdklib.build.ApkCreationException;
import com.android.sdklib.build.DuplicateFileException;
import com.android.sdklib.build.SealedApkException;
import com.apk.builder.SketchwareUtil;
import com.apk.builder.FileUtil;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;

import com.besome.blacklogics.R;
import com.apk.builder.model.*;
import com.apk.builder.compiler.*;
import com.tyron.compiler.exception.*;
import com.tyron.compiler.Compiler;
import com.tyron.compiler.*;

import java.lang.ref.WeakReference;
import java.io.IOException;
import java.io.File;

public class CompilerAsyncTask extends AsyncTask<Project, String, CompilerResult> {
	
	private final WeakReference<Context> mContext;
	
	private TextView progress;
	private Dialog dialog;
	
	private long startTime;
	private AsyncTask sign_apk_file;
	private Project project;
	
	public CompilerAsyncTask(Context context) {
		mContext = new WeakReference<>(context);
	}
	
	@Override
	public void onPreExecute() {
		Context context = mContext.get();
		
		startTime = System.currentTimeMillis();
	}
	
	@Override
	public CompilerResult doInBackground(Project... params) {
	
	    project = params[0];
	    try {
			
	        Compiler aapt2Compiler = new AAPT2Compiler(project);
			aapt2Compiler.setProgressListener(args -> {
				publishProgress(args);
			});
	        aapt2Compiler.prepare();
	        aapt2Compiler.run();
			
			Compiler ecjCompiler = new ECJCompiler(project);
			ecjCompiler.setProgressListener(args -> {
				publishProgress(args);
			});
			ecjCompiler.prepare();
			ecjCompiler.run();
			
			Compiler d8Compiler = new D8Compiler(project);
			d8Compiler.setProgressListener(args -> {
				publishProgress(args);
			});
			d8Compiler.prepare();
			d8Compiler.run();
		    
		    publishProgress("Packaging APK...");
			project.getLogger().d("APK Builder", "Packaging APK");
			
		    File binDir = new File(project.getOutputFile(), "bin");
			File dexDir = new File(binDir, "dex");
			File apkPath = new File(binDir, "gen.apk");
		    apkPath.createNewFile();
		    
		    File resPath = new File(binDir, "generated.apk.res");
		    
		    File dexFile = new File(binDir, "dex/classes.dex");
		    ApkBuilder builder = new ApkBuilder(apkPath, resPath, dexFile, null, null);
			
			File[] binFiles = binDir.listFiles();
			for (File file : binFiles) {
			    if (!file.getName().equals("classes.dex") && file.getName().endsWith(".dex")) {
			        builder.addFile(file, Uri.parse(file.getAbsolutePath()).getLastPathSegment());
					project.getLogger().d("APK Builder", "Adding dex file " + file.getName() + " to APK.");
			    }
			}
			for (Library library : project.getLibraries()) {
			    builder.addResourcesFromJar(new File(library.getPath(), "classes.jar"));
				
				project.getLogger().d("APK Builder", "Adding resources of " + library.getName() + " to the APK");
			}
			builder.setDebugMode(false);
			builder.sealApk();
			
			long time = System.currentTimeMillis() - startTime;
			
			project.getLogger().d("APK Signer", "Signing Apk");
			String path_ = project.getOutputFile() + "/bin/gen.apk";
			signFile(new java.io.File(path_));
			
	    } catch (Exception e) {
		      return new CompilerResult(e.getMessage(), true);
		}
	    return new CompilerResult("Success", false);
	}
	
	@Override
	public void onProgressUpdate(String... update) {
	 //   progress.setText(update[0]);
	}
	
	@Override
	public void onPostExecute(CompilerResult result) {
		
		if (result.isError()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext.get());
			builder.setTitle("Compilation error");
			builder.setMessage(result.getMessage());
			builder.setPositiveButton("CLOSE", null);
			builder.create().show();
		} else {
			installApk(project.getOutputFile() + "/bin/generated.apk");
        }
	}

    private void signFile(final File file){
        class sign_apk_file extends AsyncTask<Void, Void, Void> { 
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                String outFile =file.getAbsolutePath();
                String out = project.getOutputFile() + "/bin/generated.apk";
                apksigner.Main.sign(file,out);
            } catch (Exception e) {
                long time = System.currentTimeMillis() - startTime;
                project.getLogger().d("APK Signer", "Sign error");
            }
        return null;
         }

        protected void onPreExecute() {

        return ;
        }

        protected void onPostExecute(Void result) {
            long time = System.currentTimeMillis() - startTime;
            project.getLogger().d("APK Builder", "Build success, took " + time + "ms");
            try {
                FileUtil.deleteFile(project.getOutputFile() + "/bin/gen.apk");
            } catch (Exception e) {
	 
            }
        return ;
        }
        }
        new sign_apk_file().execute();
    }
    
    public void installApk(final String apk) {
		String PATH = apk;
        Context context = mContext.get();
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
}
