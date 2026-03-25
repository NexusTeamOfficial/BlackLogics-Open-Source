package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import java.io.BufferedReader;

import android.util.Log;
import com.bumptech.glide.load.Key;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class kk {
    
    /* renamed from: a  reason: collision wi//th root package name */
    boolean f334a;
    
    public kk() {
        this(false);
    }
    
    public kk(boolean z) {
        this.f334a = false;
        this.f334a = z;
    }
    
    public boolean a(String str) {
        return new File(str).exists();
    }
    
    public void b(String str) {
        d(str);
        c(str);
    }
    
    public boolean c(String str) {
        if (!a(str)) {
            return new File(str).mkdirs();
        }
        return false;
    }
    
    public void a(Context context, String assetName, String destPath) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        
        try {

            int lastIndex = destPath.lastIndexOf(File.separator);
            if (lastIndex > 0) {
                String dirPath = destPath.substring(0, lastIndex);
                c(dirPath); // call original method c() to create directories
            }
            
            inputStream = context.getAssets().open(assetName);
            outputStream = new FileOutputStream(destPath, false);
            
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            
            if (f334a) {
                Log.d(getClass().getSimpleName(),
                "assetFile =>" + destPath + " copy success.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try { inputStream.close(); } catch (IOException e) { e.printStackTrace(); }
            }
            if (outputStream != null) {
                try { outputStream.close(); } catch (IOException e) { e.printStackTrace(); }
            }
        }
    }
    
    public void a(File file, File file2) {
        if (!file.isDirectory()) {
            a(file.getAbsolutePath(), file2.getAbsolutePath());
        } else if (file2.exists() || file2.mkdirs()) {
            String[] list = file.list();
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    a(new File(file, list[i]), new File(file2, list[i]));
                }
            }
        } else {

        }
    }
    
    public void a(String srcPath, String destPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcPath);
            fos = new FileOutputStream(destPath, false); // overwrite, not append
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            
            if (this.f334a) { // assuming 'a' is a boolean field in the same class
                String tag = this.getClass().getSimpleName();
                String msg = "src=" + srcPath + ", dest=" + destPath;
                android.util.Log.d(tag, msg);
            }
            
            while ((bytesRead = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, bytesRead);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public void d(String str) {
        a(str, true);
    }
    
    public void a(File file) {
        a(file, true);
    }
    
    public void a(File file, boolean z) {
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.isDirectory()) {
                        a(file2);
                    }
                    if (file2.isFile()) {
                        if (file2.delete()) {
                            if (this.f334a) {
                                Log.d(getClass().getSimpleName(), "Delete file success." + file2.getAbsolutePath());
                            }
                        } else if (this.f334a) {
                            Log.d(getClass().getSimpleName(), "Delete file failed." + file2.getAbsolutePath());
                        }
                    }
                }
            }
            if (z) {
                file.delete();
            }
        }
    }
    
    public void a(String str, boolean z) {
        a(new File(str), z);
    }
    
    public void e(String str) {
        b(new File(str));
    }
    
    public void b(File file) {
        file.delete();
    }
    
    public void b(String str, String str2) {
        int lastIndexOf = str.lastIndexOf(File.separator);
        if (lastIndexOf > 0) {
            c(str.substring(0, lastIndexOf));
        }
        File file = new File(str);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(Exception e) {}
        }
        FileWriter fileWriter = null;
        try {
            FileWriter fileWriter2 = new FileWriter(new File(str), false);
            try {
                fileWriter2.write(str2);
                fileWriter2.flush();
                if (this.f334a) {
                    String simpleName = getClass().getSimpleName();
                    Log.d(simpleName, str + " saved");
                }
                if (fileWriter2 != null) {
                    try {
                        fileWriter2.close();
                    } catch (IOException unused) {
                    }
                }
            } catch (IOException e) {

                fileWriter = fileWriter2;
                try {

                } catch (Throwable th) {

                    fileWriter2 = fileWriter;
                }
            } catch (Throwable th2) {

                if (fileWriter2 != null) {
                    try {
                        fileWriter2.close();
                    } catch (IOException unused2) {
                    }
                }

            }
        } catch (IOException e2) {


        }
    }
    
    public void a(String str, byte[] bArr) {
        int lastIndexOf = str.lastIndexOf(File.separator);
        if (lastIndexOf > 0) {
            c(str.substring(0, lastIndexOf));
        }
        File file = new File(str);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(Exception e) {}
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str));
            try {
                fileOutputStream2.write(bArr);
                fileOutputStream2.flush();
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (IOException unused) {
                    }
                }
            } catch (IOException e) {

                fileOutputStream = fileOutputStream2;
                try {

                } catch (Throwable th) {

                }
            } catch (Throwable th2) {

                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException unused2) {
                    }
                }

            }
        } catch (IOException e2) {


        }
    }
    
    public byte[] f(String str) {
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(new File(str));
            try {
                int available = fileInputStream2.available();
                if (available > 0) {
                    byte[] bArr = new byte[available];
                    fileInputStream2.read(bArr);
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (Exception unused) {
                        }
                    }
                    return bArr;
                }
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception unused2) {
                    }
                }
                return null;
            } catch (IOException e) {

                fileInputStream = fileInputStream2;
                try {

                } catch (Throwable th) {

                }
            } catch (Throwable th2) {

                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception unused3) {
                    }
                }

            }
        } catch (IOException e2) {


            return null;
        }
        return null;
    }
    
    public byte[] a(byte[] bArr) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytes = "sketchwaresecure".getBytes();
            instance.init(1, new SecretKeySpec(bytes, "AES"), new IvParameterSpec(bytes));
            return instance.doFinal(bArr);
        } catch(Exception e) { return null;}
    }
    
    public byte[] g(String str) {
        try {
            return a(str.getBytes(Key.STRING_CHARSET_NAME));
        } catch(Exception e) { return null;}
    }
    
    public String b(byte[] bArr) {
        try {
            return new String(c(bArr), Key.STRING_CHARSET_NAME);
        } catch(Exception e) { return null;}
    }
    
    public byte[] c(byte[] bArr) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytes = "sketchwaresecure".getBytes();
            instance.init(2, new SecretKeySpec(bytes, "AES"), new IvParameterSpec(bytes));
            return instance.doFinal(bArr);
        } catch(Exception e) { return null;}
    }
    
    public String h(String str) {
        return c(new File(str));
    }
    
    public String c(File file) {
        StringBuilder sb = new StringBuilder();
        FileReader fileReader = null;
        try {
            FileReader fileReader2 = new FileReader(file);
            try {
                char[] cArr = new char[1024];
                while (true) {
                    int read = fileReader2.read(cArr);
                    if (read <= 0) {
                        break;
                    }
                    sb.append(new String(cArr, 0, read));
                }
                if (fileReader2 != null) {
                    try {
                        fileReader2.close();
                    } catch (Exception unused) {
                    }
                }
                return sb.toString();
            } catch (IOException e) {

                fileReader = fileReader2;
                try {

                } catch (Throwable th) {

                    fileReader2 = fileReader;
                }
            } catch (Throwable th2) {

                if (fileReader2 != null) {
                    try {
                        fileReader2.close();
                    } catch (Exception unused2) {
                    }
                }

            }
        } catch (IOException e2) {


        }
        return null;
    }
    
    
    public long a(Context context, String assetName) {
        long size = -1;
        InputStream is = null;
        
        try {
            is = context.getAssets().open(assetName);
            size = (long) is.available();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return size;
    }
    
    public String b(Context context, String assetName) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        
        try {

            InputStream is = context.getAssets().open(assetName.trim());
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return sb.toString();
    }
    
}
