/* Decompiler 1565ms, total 4939ms, lines 96 */
package com.nexusteam.internal;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipOutputStream;

public class lc {
    public static void a(android.content.Context r4, java.lang.String r5, java.lang.String r6) {
        
        byte[] buffer = new byte[1024];
        

        if (!r6.endsWith(java.io.File.separator)) {
            r6 = r6 + java.io.File.separator;
        }
        
        java.io.File dir = new java.io.File(r6);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                dir.mkdir();
            }
        }
        
        java.io.BufferedInputStream bis = null;
        java.util.zip.ZipInputStream zis = null;
        
        try {
            android.content.res.AssetManager am = r4.getAssets();
            bis = new java.io.BufferedInputStream(am.open(r5));
            zis = new java.util.zip.ZipInputStream(bis);
            
            java.util.zip.ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                
                java.io.File file = new java.io.File(r6 + entry.getName());
                
                if (entry.isDirectory()) {
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                } else {
                    
                    java.io.File parent = file.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    
                    java.io.FileOutputStream fos = null;
                    try {
                        fos = new java.io.FileOutputStream(file, false);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.flush();
                    } finally {
                        if (fos != null) fos.close();
                    }
                }
                zis.closeEntry();
            }
            
        } catch (Exception e) {
            android.util.Log.e("DEBUG", e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (zis != null) zis.close();
                if (bis != null) bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public int a(String var1, File var2, ZipOutputStream var3, ArrayList<String> var4) {
        File[] var10 = var2.listFiles();
        if (var10 == null) {
            return 0;
        } else {
            if (var10.length == 0) {
                String var14 = var2.getAbsolutePath();
                var14 = var14.substring(var1.length(), var14.length());
                this.a(var1, var14, var3);
            }
            
            int var9 = var10.length;
            int var5 = 0;
            
            int var6;
            int var7;
            for(var6 = 0; var5 < var9; var6 = var7) {
                var2 = var10[var5];
                if (var2.isDirectory()) {
                    this.a(var1, var2, var3, var4);
                }
                
                var7 = var6;
                if (var2.isFile()) {
                    String var12 = var2.getAbsolutePath();
                    String var13 = var12.substring(var1.length(), var12.length());
                    Iterator var15 = var4.iterator();
                    
                    boolean var8;
                    while(true) {
                        if (var15.hasNext()) {
                            String var11 = (String)var15.next();
                            if (!var12.contains(var11)) {
                                continue;
                            }
                            
                            var8 = true;
                            break;
                        }
                        
                        var8 = false;
                        break;
                    }
                    
                    var7 = var6;
                    if (!var8) {
                        var7 = var6;
                        if (this.a(var1, var13, var3)) {
                            var7 = var6 + 1;
                        }
                    }
                }
                
                ++var5;
            }
            
            return var6;
        }
    }
    
    public void a(java.io.InputStream r6, java.lang.String r7) {
        
        byte[] buffer = new byte[1024];
        

        if (!r7.endsWith(java.io.File.separator)) {
            r7 = r7 + java.io.File.separator;
        }
        
        java.io.File dir = new java.io.File(r7);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        java.util.zip.ZipInputStream zis = null;
        java.io.BufferedInputStream bis = null;
        
        try {
            bis = new java.io.BufferedInputStream(r6);
            zis = new java.util.zip.ZipInputStream(bis);
            
            java.util.zip.ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                
                java.io.File file = new java.io.File(r7 + entry.getName());
                
                if (entry.isDirectory()) {
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                } else {
                    
                    java.io.File parent = file.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    
                    java.io.FileOutputStream fos = null;
                    try {
                        fos = new java.io.FileOutputStream(file);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.flush();
                    } finally {
                        if (fos != null) fos.close();
                    }
                }
                
                zis.closeEntry();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (zis != null) zis.close();
                if (bis != null) bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public void a(String var1, String var2) {
        try {
            this.a((InputStream)(new FileInputStream(var1)), var2);
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }
    
    public void a(java.lang.String r4,
    java.util.ArrayList<java.lang.String> r5,
    java.util.ArrayList<java.lang.String> r6) {
        
        java.io.FileOutputStream fos = null;
        java.util.zip.ZipOutputStream zos = null;
        
        try {
            fos = new java.io.FileOutputStream(r4);
            zos = new java.util.zip.ZipOutputStream(fos);
            
            java.util.Iterator<java.lang.String> it = r5.iterator();
            while (it.hasNext()) {
                String path = it.next();
                java.io.File file = new java.io.File(path);
                


                a(path, file, zos, r6);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (zos != null) zos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public boolean a(java.lang.String r4,
    java.lang.String r5,
    java.util.zip.ZipOutputStream r6) {
        
        java.io.FileInputStream fis = null;
        java.io.BufferedInputStream bis = null;
        
        try {

            java.io.File file = new java.io.File(r4 + r5);
            
            if (!file.isFile()) {
                if (r6 != null) {
                    r6.closeEntry();
                }
                return false;
            }
            
            fis = new java.io.FileInputStream(file);
            bis = new java.io.BufferedInputStream(fis);
            
            java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry(r5);
            r6.putNextEntry(entry);
            
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) > 0) {
                r6.write(buffer, 0, len);
            }
            
            r6.closeEntry();
            return true;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (bis != null) bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fis != null) fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public byte[] a(java.lang.String r5) {
        
        java.io.ByteArrayOutputStream baos = null;
        java.util.zip.ZipOutputStream zos = null;
        
        try {
            baos = new java.io.ByteArrayOutputStream();
            zos = new java.util.zip.ZipOutputStream(baos);
            
            java.io.File file = new java.io.File(r5);
            java.util.ArrayList<java.lang.String> ignoreList =
            new java.util.ArrayList<>();
            
            int count = a(r5, file, zos, ignoreList);
            
            if (count > 0) {
                zos.close();
            }
            
            return baos.toByteArray();
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (baos != null) baos.close();
            } catch (Exception ignored) {}
        }
    }
    
}
