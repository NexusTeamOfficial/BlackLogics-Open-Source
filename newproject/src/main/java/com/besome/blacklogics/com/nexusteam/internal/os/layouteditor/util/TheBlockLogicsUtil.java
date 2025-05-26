package com.nexusteam.internal.os.layouteditor.util;

import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.*;
import android.os.*;
import android.util.*;
import android.widget.*;
import com.nexusteam.internal.os.layouteditor.util.*;
import java.io.*;
import org.json.*;
import android.view.*;

public class TheBlockLogicsUtil
{
	public static final String dir = "/storage/emulated/0/.blacklogics";
	public static final String projects = dir + "/data/";
	public static final String baseRes = dir + "/resources/";
	public static final File binFile; 
	public static final File projectsFile1; 
	
	private static Object appFile;
	private static Context context;
	private static File r11;
	
	// Corrected static block
	static {
		projectsFile1 = new File(new StringBuffer().append((Object) appFile).append("/data").toString());
		binFile = new File("/data/data/com.besome.blacklogics/files/bin");
	}
	
	public static void vibrate(Context context)
	{((Vibrator)context.getSystemService("vibrator")).vibrate((long)100); 
	}
	public static void vibrate(Context context, int v)
	{((Vibrator)context.getSystemService("vibrator")).vibrate((long)v); 
	}
	public static final File projectsFile = new File(new StringBuffer().append(appFile).append("/projects").toString());
	
	public static int getDisplayWidthPixels(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}
	
	public static int getDisplayHeightPixels(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
	
	public static float getDimensionPixel(Context context, float f) {
		return TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
	}
	
	public static File getIconFile(File file) {
		return new File(new StringBuffer().append(file).append("/icon.png").toString());
	}
	
	public static File getConfigFile(File file) {
		return new File(new StringBuffer().append(file).append("/config").toString());
	}
	
	public static JSONObject getProjectConfig(File file) {
		try {
			return new JSONObject(readFile(file.getAbsolutePath()));
		} catch (JSONException e) {
			return (JSONObject) null;
		} 
	}
	
	public static void writeFile(String filePath, String text)
	{
		try { 
			File file = new File(filePath).getParentFile(); 
			if (!file.exists()) { 
				file.mkdirs(); 
			} 
			FileWriter fileWriter = new FileWriter(filePath); 
			fileWriter.append((CharSequence)text); 
			fileWriter.close(); 
			return; 
		} 
		catch (IOException iOException) { 
			return; 
		} 
	}
	public static boolean deleteFileProject(String str) {
		File file = r11;
		File file2 = new File(str);
		File file3 = file;
		if (!file3.exists()) {
			return false;
		}
		if (file3.isFile()) {
			return file3.delete();
		}
		File[] listFiles = file3.listFiles();
		if (listFiles != null) {
			File[] fileArr = listFiles;
			for (File file4 : fileArr) {
				boolean deleteFile;
				if (file4.isDirectory()) { 
					deleteFile = deleteFileProject(file4.getAbsolutePath());
				}
				if (file4.isFile()) {
					deleteFile = file4.delete();
				}
			}
		}
		return file3.delete();
	}
	
	public static void showToast(Context context, String string2) { 
		int n = TheBlockLogicsUtil.getDip(context, 15); 
		TextView textView = new TextView(context); 
		textView.setTextSize(1, (float)14); 
		textView.setPadding(n * 1, n, n * 1, n); 
		textView.setText((CharSequence)string2); 
		int n2 = (int)TypedValue.applyDimension((int)1, (float)1.5f, (DisplayMetrics)context.getResources().getDisplayMetrics());
		android.graphics.drawable.GradientDrawable EBFGGDJ = new android.graphics.drawable.GradientDrawable();
		EBFGGDJ.setColor(Color.parseColor("#FFFFFFFF"));
		EBFGGDJ.setCornerRadius(20);
		EBFGGDJ.setStroke(2, Color.parseColor("#FF00C853"));
		textView.setBackground(EBFGGDJ);
		float f = TypedValue.applyDimension((int)1, (float)0.15f, (DisplayMetrics)context.getResources().getDisplayMetrics()); 
		Toast toast = new Toast(context); 
		toast.setGravity(49, 0, 0); 
		toast.setMargin((float)0, f); 
		toast.setView((View)textView); 
		toast.show(); 
	}
	public static String encodeToBase64(String string2) { 
		return new String(Base64.encode((byte[])string2.getBytes(), (int)0)); 
	} 
	public static String readFile(String path)
	{
		StringBuilder sb = new StringBuilder();
		FileReader fr = null;
		try
		{
			fr = new FileReader(new File(path));
			
			char[] buff = new char[1024];
			int length = 0;
			
			while ((length = fr.read(buff)) > 0)
			{
				sb.append(new String(buff, 0, length));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (fr != null)
			{
				try
				{
					fr.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return sb.toString();
	}
	
	public static String readAssetFile(Context ctx, String path)
	{
		String str = "";
		try
		{
			BufferedReader myReader = new BufferedReader(new InputStreamReader(ctx.getAssets().open(path)));
			String aDataRow = "";
			while ((aDataRow = myReader.readLine()) != null)
			{
				str += aDataRow + "\n";
			}
			myReader.close();
		}
		catch (IOException e)
		{
			
		}
		return str;
	}
	
	public static boolean saveImage(String str, Drawable drawable)
	{
		try
		{
			((BitmapDrawable) drawable).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(str));
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public static Drawable getRippleEffect() {
		RoundRectShape roundrectShape = new RoundRectShape((float[])null, (RectF)null, (float[])null);
		ShapeDrawable shapeDrawable = new ShapeDrawable((Shape)roundrectShape);
		ColorStateList colorStateList = ColorStateList.valueOf((int)Color.parseColor((String)"#AABBCCDD"));
		RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, (Drawable)shapeDrawable, (Drawable)null);
		shapeDrawable.getPaint().setColor(-1);
		return rippleDrawable;
	} 
	public static Drawable getRippleEffectButton() {
		RoundRectShape roundrectShape = new RoundRectShape((float[])null, (RectF)null, (float[])null);
		ShapeDrawable shapeDrawable = new ShapeDrawable((Shape)roundrectShape);
		ColorStateList colorStateList = ColorStateList.valueOf((int)Color.parseColor((String)"#13B41A"));
		RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, (Drawable)shapeDrawable, (Drawable)null);
		shapeDrawable.getPaint().setColor(-1);
		return rippleDrawable;
	} 
	public static int getDip(Context context, int n){
		return (int)TypedValue.applyDimension((int)1, (float)n, (DisplayMetrics)context.getResources().getDisplayMetrics());
	}
	public static GradientDrawable getGradient(Context context, int p1, int parseColor, int p3)
	{
		GradientDrawable gradientDrawable = new GradientDrawable();
		gradientDrawable.setStroke(TheBlockLogicsUtil.getDip(context,p1),parseColor);
		gradientDrawable.setColor(p3);
		return gradientDrawable;
	}
	public static void dialog() {
		
	}
	public static boolean copyFile(File sourceFile, File destFile) {
		try {
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			
			FileInputStream in = new FileInputStream(sourceFile);
			FileOutputStream out = new FileOutputStream(destFile);
			
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			
			in.close();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String decodeBase64(String str) {
		return new String(Base64.decode(str.getBytes(), 0));
	}
	
}
