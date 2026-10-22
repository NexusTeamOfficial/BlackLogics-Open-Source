package com.nexusteam.blacklogics;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.*;
import androidx.constraintlayout.widget.*;
import androidx.core.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.*;
import androidx.customview.*;
import androidx.draganddrop.*;
import androidx.dynamicanimation.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.*;
import com.airbnb.viewmodeladapter.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.flexbox.*;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.*;
import com.google.android.material.button.*;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import com.nexusteam.filepicker.elitefileselector.*;
import com.nexusteam.nexatask.*;
import com.squareup.leakcanary.*;
import de.hdodenhof.circleimageview.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import io.github.rosemoe.sora.widget.CodeEditor;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.json.*;
import com.nexusteam.blacklogics.utils.Helper;
import java.io.*;
import dalvik.system.*;
import com.android.tools.r8.D8;
import com.hs.minijavaide.JarPackager;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;

public class JavaToSmaliActivity extends AppCompatActivity {
	
	private AsyncTask task_;
	private ProgressDialog pr;
	
	private ArrayList<String> opt = new ArrayList<>();
	
	private LinearLayout linear_main;
	private Button button1;
	private CodeEditor editor;
	private LinearLayout linear3;
	private MaterialButton button_convert;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.java_to_smali);
		initialize(_savedInstanceState);
		
		MobileAds.initialize(this);
		
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear_main = findViewById(R.id.linear_main);
		button1 = findViewById(R.id.button1);
		editor = findViewById(R.id.editor);
		linear3 = findViewById(R.id.linear3);
		button_convert = findViewById(R.id.button_convert);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_backsmali();
			}
		});
		
		button_convert.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				new AsyncTask<String, String, String>() {
					long ecjTime, dxTime;
					@Override
					protected void onPreExecute() {
						pr = new ProgressDialog(JavaToSmaliActivity.this);
						
						pr.setMessage("Running...");
						
						pr.setCancelable(false);
						
						pr.show();
					}
					@Override
					protected String doInBackground(String... params) {
						String _param = params[0];
						
						//code that prepares the files
						FileUtil.deleteFile(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/bin/"));
						FileUtil.makeDir(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/bin/"));
						FileUtil.writeFile(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/bin/Main.java"), editor.getText().toString());
						//code that copies cp.jar from assets to temp folder (if not exists)
						if (!FileUtil.isExistFile(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/bin/cp.jar"))) {
							try (InputStream input = getAssets().open("cp.jar");
							OutputStream output = new FileOutputStream(
							FileUtil.getPackageDataDir(getApplicationContext()).concat("/bin/cp.jar")
							)) {
								byte[] buffer = new byte[input.available()];
								int length;
								while ((length = input.read(buffer)) != -1) {
									output.write(buffer, 0, length);
								}
								
							} catch (FileNotFoundException e) {
								
							} catch (IOException e) {
								
							}
							
						}
						//code that runs ecj
						long time = System.currentTimeMillis();
						publishProgress("Compiling Java...");
						opt.clear();
						opt.add("-1.8");
						opt.add("-nowarn");
						opt.add("-deprecation");
						opt.add("-d");
						opt.add(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/bin/classes"));
						opt.add("-cp");
						opt.add(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/bin/cp.jar"));
						opt.add("-proc:none");
						opt.add("-sourcepath");
						opt.add("ignore");
						opt.add(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/bin/Main.java"));
						PrintWriter printWriter = new PrintWriter(new OutputStream() {
							@Override
							public void write(int p1) throws IOException
							{
								//do nothing
							}
						});
						
						final StringBuilder errs = new StringBuilder();
						
						PrintWriter printWriter2 = new PrintWriter(new OutputStream() {
							@Override
							public void write(int p1) throws IOException
							{
								errs.append((char)p1);
							}
						});
						
						org.eclipse.jdt.internal.compiler.batch.Main main = new org.eclipse.jdt.internal.compiler.batch.Main(printWriter2, printWriter2, false, null, null);
						
						main.compile(opt.toArray(new String[0]));
						
						if(main.globalErrorsCount > 0) {
							return errs.toString();
						}
						ecjTime = System.currentTimeMillis() - time;
						//code that packages classes to a JAR
						publishProgress("Packaging JAR...");
						try{
							new JarPackager(
							
							FileUtil.getPackageDataDir(getApplicationContext()).concat("/bin/classes/"),
							
							FileUtil.getPackageDataDir(getApplicationContext()).concat("/bin/classes.jar")
							
							).create();
						}catch(Exception e){
							return "Packaging JAR failed: " + e.toString();
						}
						//code that runs d8 //dx
						time = System.currentTimeMillis();
						try{
							publishProgress("Dexing with D8...");
							/*
com.android.dx.command.dexer.Main.dexOutputArrays = new ArrayList();

com.android.dx.command.dexer.Main.dexOutputFutures = new ArrayList();
opt.clear();
opt.add("--debug");
opt.add("--verbose");
opt.add("--output=".concat(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/bin/classes.dex")));
opt.add(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/bin/classes.jar"));
com.android.dx.command.dexer.Main.main(opt.toArray(new String[0]));
*/
							opt.clear();
							opt.add("--output");
							opt.add(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/bin/"));
							opt.add("--lib");
							opt.add(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/bin/cp.jar"));
							opt.add(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/bin/classes.jar"));
							D8.main(opt.toArray(new String[0]));
						}catch(Exception e){
							return "Dex failed: " + e.toString();
						}
						dxTime = System.currentTimeMillis() - time;
						
						return "";
					}
					@Override
					protected void onProgressUpdate(String... values) {
						pr.setMessage(values[0]);
					}
					@Override
					protected void onPostExecute(String _result) {
						pr.dismiss();
						if (TextUtils.isEmpty(_result)) {
							final TextView tx = new TextView(JavaToSmaliActivity.this);
							tx.setLayoutParams(new LinearLayout.LayoutParams(-2,-2));
							tx.setTextSize(15);
							tx.setTextColor(Color.BLACK);
							tx.setPadding(30,30,30,30);
							tx.setTextIsSelectable(true);
							
							final ScrollView sc = new ScrollView(JavaToSmaliActivity.this);
							sc.setLayoutParams(new LinearLayout.LayoutParams(-1,-2));
							sc.addView(tx);
							//final 
							//code that starts listening to system prints
							OutputStream _outstream = new OutputStream() {
								@Override
								public void write(int b) {
									tx.append(String.valueOf((char)b));
								}
								
								@Override
								public String toString() {
									return tx.getText().toString();
								}
								
							};
							
							System.setOut(new PrintStream(_outstream));
							System.setErr(new PrintStream(_outstream));
							//code that loads the final dex
							try {
								String optimizedDir = getApplicationContext().getDir("odex", MODE_PRIVATE).getAbsolutePath();
								
								DexClassLoader dcl = new DexClassLoader( 
								FileUtil.getPackageDataDir(getApplicationContext()).concat("/bin/classes.dex")
								, optimizedDir, null, getClassLoader() ); 
								
								Class calledClass = dcl.loadClass("Main");
								
								java.lang.reflect.Method method = calledClass.getDeclaredMethod("main", String[].class); 
								
								String[] param = {};
								
								Object result = method.invoke(null, new Object[] {param});
							} catch(java.lang.reflect.InvocationTargetException i) {
								dialog("Failed..", "Runtime error: " + i.getCause().toString());
								return;
							} catch(Exception e) {
								dialog("Failed..", "Couldn't execute the dex: " + e.toString() + "\n\nSystem logs:\n" + _outstream.toString() );
								return;
							}
							//code that shows the dialog
							button1.performClick();
							/*
new AlertDialog.Builder(JavaToSmaliActivity.this)
.setTitle("Output (ecj:" + ecjTime + "ms | d8:" + dxTime + "ms)")
.setView(sc)
.setPositiveButton("OK", null)
.setNegativeButton("Cancel", null)
.setOnDismissListener(new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialogInterface) {
		    
		}
})
.create().show();
*/
						} else {
							dialog("Failed..", _result);
						}
					}
					public void dialog(String title, String message) {
						AlertDialog.Builder dialog =	new AlertDialog.Builder(JavaToSmaliActivity.this)
						.setTitle(title)
						.setMessage(message)
						.setPositiveButton("OK", null)
						.setNegativeButton("Cancel", null);
						
						AlertDialog dlg = dialog.create();
						dlg.show();
						
						View messageView = dlg.findViewById(android.R.id.message);
						if (messageView instanceof TextView) {
							((TextView) messageView).setTextIsSelectable(true);
						}
					}
				}.execute("");
			}
		});
	}
	
	private void initializeLogic() {
		ImageView back = findViewById(R.id.ig_toolbar_back);
		TextView title = findViewById(R.id.tx_toolbar_title);
		ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);
		
		Helper.applyRippleToToolbarView(back);
		back.setOnClickListener(Helper.getBackPressedClickListener(this));
		title.setText("Java to Smali");
		loadFile.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		loadFile.setImageResource(R.drawable.ic_more_vert_black);
		loadFile.setVisibility(View.GONE);
		Helper.applyRippleToToolbarView(loadFile);
		editor.setTypefaceText(Typeface.MONOSPACE);
		
		//editor.setOverScrollEnabled(true);
		
		//editor.setEdgeEnabled(false);
		
		editor.setEditorLanguage(new JavaLanguage());
		
		editor.setColorScheme(new SchemeDarcula());
		
		editor.setTextSize(12);
		if (FileUtil.isExistFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/bin/Main.java"))) {
			editor.setText(FileUtil.readFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/bin/Main.java")));
		} else {
			editor.setText("/*\n@author Hey! Studios DEV\n@notes Do not add your package.\n       Do not change the Main class' name and\n       Do not remove its main method.\n       \n@Edited By Smart India Gaming       \n*/\n\nimport java.util.*;\n\npublic class Main {\n\n	public static void main(String[] args) {\n		\n		System.out.print(\"Hello, World!\");\n		\n	}\n	\n}");
		}
	}
	public static void a(StringBuilder var0, int var1) {
		for (int var2 = 0; var2 < var1; ++var2)
		{
			var0.append('\t');
		}
		
	}
	
	public static String j(String var0) {
		StringBuilder var1 = new StringBuilder(4096);
		char[] var2 = var0.toCharArray();
		int var3 = var2.length;
		int var4 = 0;
		boolean var5 = false;
		boolean var6 = false;
		boolean var7 = false;
		int var8 = 0;
		boolean var9 = false;
		
		int var19;
		for (boolean var10 = false; var4 < var3; var4 = var19)
		{
			int var13;
			boolean var14;
			boolean var15;
			boolean var16;
			boolean var17;
			int var18;
			label82:
			{
				char var11 = var2[var4];
				if (var5)
				{
					if (var11 == 10)
					{
						var1.append(var11);
						a(var1, var8);
						var5 = false;
					}
					else
					{
						var1.append(var11);
					}
				}
				else if (var6)
				{
					label79:
					{
						if (var11 == 42)
						{
							int var40 = var4 + 1;
							char var41 = var2[var40];
							if (var41 == 47)
							{
								var1.append(var11);
								var1.append(var41);
								var4 = var40;
								var6 = false;
								break label79;
							}
						}
						
						var1.append(var11);
					}
				}
				else if (var7)
				{
					var1.append(var11);
					var7 = false;
				}
				else if (var11 == 92)
				{
					var1.append(var11);
					var7 = true;
				}
				else if (var9)
				{
					if (var11 == 39)
					{
						var1.append(var11);
						var9 = false;
					}
					else
					{
						var1.append(var11);
					}
				}
				else if (var10)
				{
					if (var11 == 34)
					{
						var1.append(var11);
						var10 = false;
					}
					else
					{
						var1.append(var11);
					}
				}
				else
				{
					label88:
					{
						if (var11 == 47)
						{
							int var27 = var4 + 1;
							char var28 = var2[var27];
							if (var28 == 47)
							{
								var1.append(var11);
								var1.append(var28);
								var5 = true;
								var4 = var27;
								break label88;
							}
							
							if (var28 == 42)
							{
								var1.append(var11);
								var1.append(var28);
								var6 = true;
								var4 = var27;
								break label88;
							}
						}
						
						if (var11 != 10)
						{
							boolean var20;
							if (var11 == 39)
							{
								var20 = true;
							}
							else
							{
								var20 = var9;
							}
							
							boolean var21;
							if (var11 == 34)
							{
								var21 = true;
							}
							else
							{
								var21 = var10;
							}
							
							int var22;
							if (var11 == 123)
							{
								var22 = var8 + 1;
							}
							else
							{
								var22 = var8;
							}
							
							if (var11 == 125)
							{
								--var22;
								if (var1.charAt(-1 + var1.length()) == 9)
								{
									var1.deleteCharAt(-1 + var1.length());
								}
							}
							
							var1.append(var11);
							var18 = var22;
							var10 = var21;
							var13 = var4;
							var14 = var5;
							var15 = var6;
							var16 = var7;
							var17 = var20;
							break label82;
						}
						
						var1.append(var11);
						a(var1, var8);
					}
				}
				
				var13 = var4;
				var14 = var5;
				var15 = var6;
				var16 = var7;
				var17 = var9;
				var18 = var8;
			}
			
			var19 = var13 + 1;
			var8 = var18;
			var9 = var17;
			var7 = var16;
			var6 = var15;
			var5 = var14;
		}
		
		return var1.toString();
	}
	{
	}
	
	public void _backsmali() {
		new AsyncTask<String, String, String>() {
			ProgressDialog prog;
			@Override
			protected void onPreExecute() {
				prog = new ProgressDialog(JavaToSmaliActivity.this);
				
				prog.setMessage("Running baksmali...");
				
				prog.setCancelable(false);
				
				prog.show();
			}
			@Override
			protected String doInBackground(String... params) {
				String _param = params[0];
				
				String[] str = new String[] {
					"-f", "-o",
					
					FileUtil.getPackageDataDir(getApplicationContext()).concat("/bin/smali/"),
					FileUtil.getPackageDataDir(getApplicationContext()).concat("/bin/classes.dex")
				};
				com.googlecode.d2j.smali.BaksmaliCmd.main(str);
				
				return "";
			}
			@Override
			protected void onPostExecute(String _result) {
				prog.dismiss();
				CodeEditor edi = new CodeEditor(JavaToSmaliActivity.this);
				
				edi.setTypefaceText(Typeface.MONOSPACE);
				/*
edi.setOverScrollEnabled(true);

edi.setEdgeEnabled(false);
*/
				edi.setEditorLanguage(new JavaLanguage());
				
				edi.setColorScheme(new SchemeDarcula());
				
				edi.setTextSize(10);
				
				edi.setText( formatSmali(
				FileUtil.readFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/bin/smali/Main.smali"))
				) );
				
				new AlertDialog.Builder(JavaToSmaliActivity.this)
				.setView(edi)
				.create()
				.show();
			}
		}.execute("");
	}
	private String formatSmali(String in) {
		
		List<String> lines = new ArrayList<>(Arrays.asList(in.split("\n")));
		
		boolean insideMethod = false;
		
		for(int i = 0; i < lines.size(); i++) {
			
			String line = lines.get(i);
			
			if(line.startsWith(".method")) {
				insideMethod = true;
			}
			
			if(line.startsWith(".end method")) {
				insideMethod = false;
			}
			
			if(insideMethod && !shouldSkip(line)) {
				lines.set(i, line + "\n");
			}
		}
		
		StringBuilder result = new StringBuilder();
		
		for(int i = 0; i < lines.size(); i++) {
			
			if(i != 0) {
				result.append("\n");
			}
			
			result.append(lines.get(i));
		}
		
		return result.toString();
		
	}
	
	private boolean shouldSkip(String smaliLine) {
		
		String[] ops = {
			".line",
			":",
			".prologue"
		};
		
		for(String op : ops) {
			
			if(smaliLine.trim().startsWith(op)) {
				return true;
			}
			
		}
		
		return false;
	}
	{
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}