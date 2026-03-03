/* Decompiler 790ms, total 2243ms, lines 989 */
package com.nexusteam.internal;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.Log;
import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.beans.ComponentBean;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ProjectLibraryBean;
import com.nexusteam.internal.beans.SrcCodeBean;
import com.google.gson.Gson;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class fg {
   public String A;
   public String B;
   public String C;
   public String D;
   public String E;
   public String F;
   public String G;
   public String H;
   public String I;
   public ArrayList<String> J;
   kk K;
   Gson L;
   public es M;
   dt N;
   public HashMap<String, Object> a;
   public String b;
   public String c;
   public String d;
   public String e;
   public String f;
   public int g;
   public int h;
   public int i;
   public int j;
   public int k;
   public String l;
   public String m;
   public String n;
   public String o;
   public String p;
   public String q;
   public String r;
   public String s;
   public String t;
   public String u;
   public String v;
   public String w;
   public String x;
   public String y;
   public String z;

   public fg(Context var1, String var2) {
      this(var1, fe.a(var2), mc.a(var2));
   }

   public fg(Context var1, String var2, HashMap<String, Object> var3) {
      this.M = new es();
      this.a = var3;
      this.b = kr.c(var3, "sc_id");
      this.c = var2;
      this.e = kr.c(var3, "my_sc_pkg_name");
      this.d = kr.c(var3, "my_ws_name");
      this.f = kr.c(var3, "my_app_name");
      this.l = kr.c(var3, "sc_ver_code");
      this.m = kr.c(var3, "sc_ver_name");
      this.g = kr.a(var3, "color_accent", var1.getResources().getColor(2131034155));
      this.h = kr.a(var3, "color_primary", var1.getResources().getColor(2131034170));
      this.i = kr.a(var3, "color_primary_dark", var1.getResources().getColor(2131034172));
      this.j = kr.a(var3, "color_control_highlight", var1.getResources().getColor(2131034168));
      this.k = kr.a(var3, "color_control_normal", var1.getResources().getColor(2131034169));
      this.c(var1);
   }

   private void c(Context var1) {
      this.K = new kk(true);
      this.L = new Gson();
      this.N = new dt(var1);
      this.J = new ArrayList();
      this.J.add("SketchApplication.java");
      this.J.add("DebugActivity.java");
      StringBuilder var2;
      if (!this.c.endsWith(File.separator)) {
         var2 = new StringBuilder();
         var2.append(this.c);
         var2.append(File.separator);
         this.c = var2.toString();
      }

      this.n = this.e.replaceAll("\\.", File.separator);
      var2 = new StringBuilder();
      var2.append(this.c);
      var2.append("bin");
      this.t = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.t);
      var2.append(File.separator);
      var2.append("classes");
      this.u = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.c);
      var2.append("gen");
      this.v = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.c);
      var2.append("app");
      var2.append(File.separator);
      var2.append("src");
      var2.append(File.separator);
      var2.append("main");
      this.s = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.s);
      var2.append(File.separator);
      var2.append("java");
      this.y = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.s);
      var2.append(File.separator);
      var2.append("res");
      this.w = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.w);
      var2.append(File.separator);
      var2.append("layout");
      this.x = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.w);
      var2.append(File.separator);
      var2.append("raw");
      this.z = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.s);
      var2.append(File.separator);
      var2.append("assets");
      this.A = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.A);
      var2.append(File.separator);
      var2.append("fonts");
      this.B = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.c);
      var2.append("app");
      var2.append(File.separator);
      var2.append("src");
      var2.append(File.separator);
      var2.append("main");
      var2.append(File.separator);
      var2.append("AndroidManifest.xml");
      this.r = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.t);
      var2.append(File.separator);
      var2.append(this.d);
      var2.append(".apk.res");
      this.C = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.t);
      var2.append(File.separator);
      var2.append("classes.dex");
      this.D = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.t);
      var2.append(File.separator);
      var2.append("_project.dex");
      this.E = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.y);
      var2.append(File.separator);
      var2.append(this.n);
      this.o = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.o);
      var2.append(File.separator);
      var2.append("MainActivity.java");
      this.o = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.e);
      var2.append(".");
      var2.append("MainActivity.java".replaceAll(".java", ""));
      this.p = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.y);
      var2.append(File.separator);
      var2.append(this.n);
      this.q = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.q);
      var2.append(File.separator);
      var2.append("SketchApplication.java");
      this.q = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.t);
      var2.append(File.separator);
      var2.append(this.d);
      var2.append(".apk.unsigned");
      this.F = var2.toString();
      var2 = new StringBuilder();
      var2.append(this.t);
      var2.append(File.separator);
      var2.append(this.d);
      var2.append(".apk");
      this.G = var2.toString();
      var2 = new StringBuilder();
      var2.append(fe.i());
      var2.append(File.separator);
      var2.append(this.d);
      var2.append("_release.apk");
      this.H = var2.toString();
   }

   public ArrayList<SrcCodeBean> a(ly var1, lw var2, lz var3) {
      return this.a(var1, var2, var3, false);
   }

   public ArrayList<SrcCodeBean> a(ly var1, lw var2, lz var3, boolean var4) {
      this.a(var3, var1, var2, var4);
      ArrayList var9 = new ArrayList();
      Iterator var6 = var1.a().iterator();

      ProjectFileBean var7;
      while(var6.hasNext()) {
         var7 = (ProjectFileBean)var6.next();
         hf var5 = new hf(this.M, var7, var2);
         var9.add(new SrcCodeBean(var7.getJavaName(), var5.a()));
      }

      var6 = var1.a().iterator();

      String var10;
      hj var11;
      while(var6.hasNext()) {
         var7 = (ProjectFileBean)var6.next();
         var10 = var7.getXmlName();
         var11 = new hj(this.M, var7);
         var11.a(lw.a(var2.a(var10)), var2.d(var10));
         var9.add(new SrcCodeBean(var10, var11.a()));
      }

      var6 = var1.b().iterator();

      while(var6.hasNext()) {
         var7 = (ProjectFileBean)var6.next();
         var10 = var7.getXmlName();
         var11 = new hj(this.M, var7);
         var11.a(lw.a(var2.a(var10)));
         var9.add(new SrcCodeBean(var10, var11.a()));
      }

      he var8 = new he(this.M, var1.a());
      var9.add(new SrcCodeBean("AndroidManifest.xml", var8.a()));
      return var9;
   }

   public void a() {
      if (!this.N.h()) {
         this.K.a(this.s, false);
      }

   }

   public void a(lz var1, ly var2, lw var3, boolean var4) {
      this.M = new es();
      this.M.f124a = this.e;
      this.M.b = this.f;
      this.M.c = this.l;
      this.M.d = this.m;
      this.M.e = this.N.h();
      this.M.f = var4 ^ true;
      if (var1.c().useYn.equals("Y")) {
         this.M.l = true;
         this.M.k = true;
         this.M.g = true;
         this.M.h = true;
         this.M.a(2);
         this.M.a(8);
      }

      if (var1.d().useYn.equals("Y")) {
         this.M.k = true;
         this.M.g = true;
      }

      if (var1.e().useYn.equals("Y")) {
         this.M.m = true;
         this.M.h = true;
         this.M.a(2);
         this.M.a(8);
         this.M.a(var1.e());
      }

      if (var1.f().useYn.equals("Y")) {
         this.M.o = true;
         this.M.h = true;
         this.M.a(2);
         this.M.a(8);
         this.M.b(var1.f());
      }

      Iterator var11 = var2.a().iterator();

      while(var11.hasNext()) {
         ProjectFileBean var10 = (ProjectFileBean)var11.next();
         if (var10.hasActivityOption(4)) {
            this.M.a(var10.getActivityName()).f125a = true;
         }

         ArrayList var6 = var3.k(var10.getJavaName());
         Iterator var7 = var6.iterator();

         while(var7.hasNext()) {
            ComponentBean var12 = (ComponentBean)var7.next();
            if (var12.type == 15) {
               this.M.g = true;
               this.M.t = true;
               this.M.a(var10.getActivityName(), 16);
               this.M.a(var10.getActivityName(), 32);
               this.M.a(var10.getActivityName(), 64);
            }

            if (var12.type == 16) {
               this.M.a(var10.getActivityName(), 32);
            }

            if (var12.type == 14) {
               this.M.a(var10.getActivityName(), 32);
               this.M.a(var10.getActivityName(), 64);
            }

            if (var12.type == 4) {
               this.M.a(var10.getActivityName(), 4);
            }

            if (var12.type == 12) {
               this.M.a(var10.getActivityName()).b = true;
            }

            if (var12.type == 17) {
               this.M.j = true;
               this.M.n = true;
               this.M.a(var10.getActivityName(), 2);
               this.M.a(var10.getActivityName(), 8);
            }

            if (var12.type == 19) {
               this.M.a(var10.getActivityName(), 128);
            }

            if (var12.type == 20) {
               this.M.a(var10.getActivityName(), 256);
               this.M.a(var10.getActivityName(), 512);
            }

            if (var12.type == 21) {
               Log.d("location", "permission location : activity");
               this.M.a(var10.getActivityName(), 1024);
            }
         }

         HashMap var13 = var3.l(var10.getJavaName());
         Iterator var14 = var13.entrySet().iterator();

         while(var14.hasNext()) {
            Entry var15 = (Entry)var14.next();
            var7 = ((ArrayList)var15.getValue()).iterator();

            while(var7.hasNext()) {
               byte var5;
               BlockBean var8;
               label207: {
                  var8 = (BlockBean)var7.next();
                  String var9 = var8.opCode;
                  switch(var9.hashCode()) {
                  case -2135695280:
                     if (var9.equals("webViewLoadUrl")) {
                        var5 = 33;
                        break label207;
                     }
                     break;
                  case -2055793167:
                     if (var9.equals("fileutillistdir")) {
                        var5 = 3;
                        break label207;
                     }
                     break;
                  case -1834369666:
                     if (var9.equals("setBitmapFileBrightness")) {
                        var5 = 26;
                        break label207;
                     }
                     break;
                  case -1483954587:
                     if (var9.equals("fileutilisdir")) {
                        var5 = 4;
                        break label207;
                     }
                     break;
                  case -1471049951:
                     if (var9.equals("fileutilwrite")) {
                        var5 = 12;
                        break label207;
                     }
                     break;
                  case -1405157727:
                     if (var9.equals("fileutilmakedir")) {
                        var5 = 16;
                        break label207;
                     }
                     break;
                  case -1063598745:
                     if (var9.equals("resizeBitmapFileRetainRatio")) {
                        var5 = 17;
                        break label207;
                     }
                     break;
                  case -917343271:
                     if (var9.equals("getJpegRotate")) {
                        var5 = 9;
                        break label207;
                     }
                     break;
                  case -903177036:
                     if (var9.equals("resizeBitmapFileWithRoundedBorder")) {
                        var5 = 20;
                        break label207;
                     }
                     break;
                  case -733318734:
                     if (var9.equals("strToListMap")) {
                        var5 = 30;
                        break label207;
                     }
                     break;
                  case -602241037:
                     if (var9.equals("fileutilcopy")) {
                        var5 = 13;
                        break label207;
                     }
                     break;
                  case -601942961:
                     if (var9.equals("fileutilmove")) {
                        var5 = 14;
                        break label207;
                     }
                     break;
                  case -601804268:
                     if (var9.equals("fileutilread")) {
                        var5 = 1;
                        break label207;
                     }
                     break;
                  case -149850417:
                     if (var9.equals("fileutilisexist")) {
                        var5 = 2;
                        break label207;
                     }
                     break;
                  case 16308074:
                     if (var9.equals("resizeBitmapFileToCircle")) {
                        var5 = 19;
                        break label207;
                     }
                     break;
                  case 56167279:
                     if (var9.equals("setBitmapFileContrast")) {
                        var5 = 27;
                        break label207;
                     }
                     break;
                  case 163812602:
                     if (var9.equals("cropBitmapFileFromCenter")) {
                        var5 = 21;
                        break label207;
                     }
                     break;
                  case 168740282:
                     if (var9.equals("mapToStr")) {
                        var5 = 29;
                        break label207;
                     }
                     break;
                  case 470160234:
                     if (var9.equals("fileutilGetLastSegmentPath")) {
                        var5 = 11;
                        break label207;
                     }
                     break;
                  case 481850295:
                     if (var9.equals("resizeBitmapFileToSquare")) {
                        var5 = 18;
                        break label207;
                     }
                     break;
                  case 571046965:
                     if (var9.equals("scaleBitmapFile")) {
                        var5 = 23;
                        break label207;
                     }
                     break;
                  case 725249532:
                     if (var9.equals("intentSetAction")) {
                        var5 = 0;
                        break label207;
                     }
                     break;
                  case 950609198:
                     if (var9.equals("setBitmapFileColorFilter")) {
                        var5 = 25;
                        break label207;
                     }
                     break;
                  case 1086207657:
                     if (var9.equals("fileutildelete")) {
                        var5 = 15;
                        break label207;
                     }
                     break;
                  case 1129709718:
                     if (var9.equals("setImageUrl")) {
                        var5 = 32;
                        break label207;
                     }
                     break;
                  case 1156598140:
                     if (var9.equals("fileutilEndsWith")) {
                        var5 = 8;
                        break label207;
                     }
                     break;
                  case 1242107556:
                     if (var9.equals("fileutilisfile")) {
                        var5 = 5;
                        break label207;
                     }
                     break;
                  case 1252547704:
                     if (var9.equals("listMapToStr")) {
                        var5 = 31;
                        break label207;
                     }
                     break;
                  case 1315302372:
                     if (var9.equals("fileutillength")) {
                        var5 = 6;
                        break label207;
                     }
                     break;
                  case 1695890133:
                     if (var9.equals("fileutilStartsWith")) {
                        var5 = 7;
                        break label207;
                     }
                     break;
                  case 1775620400:
                     if (var9.equals("strToMap")) {
                        var5 = 28;
                        break label207;
                     }
                     break;
                  case 1792552710:
                     if (var9.equals("rotateBitmapFile")) {
                        var5 = 22;
                        break label207;
                     }
                     break;
                  case 1974249461:
                     if (var9.equals("skewBitmapFile")) {
                        var5 = 24;
                        break label207;
                     }
                     break;
                  case 1976325370:
                     if (var9.equals("setImageFilePath")) {
                        var5 = 10;
                        break label207;
                     }
                  }

                  var5 = -1;
               }

               switch(var5) {
               case 0:
                  if (((String)var8.parameters.get(1)).equals(fc.c[1])) {
                     this.M.a(var10.getActivityName(), 1);
                  }
                  break;
               case 1:
               case 2:
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
               case 8:
               case 9:
               case 10:
               case 11:
                  this.M.a(var10.getActivityName(), 32);
                  break;
               case 12:
               case 13:
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
               case 22:
               case 23:
               case 24:
               case 25:
               case 26:
               case 27:
                  this.M.a(var10.getActivityName(), 32);
                  this.M.a(var10.getActivityName(), 64);
                  break;
               case 28:
               case 29:
               case 30:
               case 31:
                  this.M.j = true;
                  break;
               case 32:
                  this.M.i = true;
                  this.M.a(2);
                  this.M.a(8);
                  break;
               case 33:
                  this.M.a(2);
                  this.M.a(8);
               }
            }
         }

         this.M.b();
      }

   }

   public void a(Context var1) {
      this.K.c(this.t);
      this.K.c(this.u);
      this.K.c(this.v);
      this.K.c(this.y);
      this.K.c(this.w);
      this.K.c(this.x);
      this.K.c(this.z);
      this.K.c(this.A);
      this.K.c(this.B);
      this.b(var1);
   }

   public void a(Context var1, String var2) {
      try {
         lc.a(var1, var2, this.w);
      } catch (Exception var3) {
         Log.e("ERROR", var3.getMessage(), var3);
      }

   }

   public void a(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(this.w);
      var2.append(File.separator);
      var2.append("drawable-xhdpi");
      var2.append(File.separator);
      var2.append("app_icon.png");
      String var4 = var2.toString();

      try {
         this.K.a(var1, var4);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public void a(String var1, String var2) {
      StringBuilder var3;
      kk var4;
      if (var1.endsWith("java")) {
         var4 = this.K;
         var3 = new StringBuilder();
         var3.append(this.y);
         var3.append(File.separator);
         var3.append(this.n);
         var3.append(File.separator);
         var3.append(var1);
         var4.b(var3.toString(), var2);
      } else if (var1.equals("AndroidManifest.xml")) {
         this.K.b(this.r, var2);
      } else {
         kk var6;
         StringBuilder var7;
         if (!var1.equals("colors.xml") && !var1.equals("styles.xml") && !var1.equals("strings.xml")) {
            if (var1.equals("styles_v21.xml")) {
               kk var5 = this.K;
               var3 = new StringBuilder();
               var3.append(this.w);
               var3.append(File.separator);
               var3.append("values-v21");
               var3.append(File.separator);
               var3.append("styles.xml");
               var5.b(var3.toString(), var2);
            } else if (var1.equals("provider_paths.xml")) {
               var6 = this.K;
               var7 = new StringBuilder();
               var7.append(this.w);
               var7.append(File.separator);
               var7.append("xml");
               var7.append(File.separator);
               var7.append(var1);
               var6.b(var7.toString(), var2);
            } else {
               var4 = this.K;
               var3 = new StringBuilder();
               var3.append(this.x);
               var3.append(File.separator);
               var3.append(var1);
               var4.b(var3.toString(), var2);
            }
         } else {
            var6 = this.K;
            var7 = new StringBuilder();
            var7.append(this.w);
            var7.append(File.separator);
            var7.append("values");
            var7.append(File.separator);
            var7.append(var1);
            var6.b(var7.toString(), var2);
         }
      }

   }

   public void b() {
      this.K.a(this.t, false);
      this.K.a(this.u, false);
      this.K.a(this.v, false);
      this.K.a(this.y, false);
      this.K.a(this.w, false);
      this.K.a(this.z, false);
      this.K.a(this.A, false);
      this.K.a(this.B, false);
   }

   public void b(ly var1, lw var2, lz var3) {
      this.b(var1, var2, var3, false);
   }

   public void b(ly var1, lw var2, lz var3, boolean var4) {
      ArrayList var6 = this.a(var1, var2, var3, var4);
      hh var9;
      if (this.M.k) {
         var9 = new hh();
         var9.b("colorPrimary", String.format("#%06X", this.h & 16777215));
         var9.b("colorPrimaryDark", String.format("#%06X", this.i & 16777215));
         var9.b("colorAccent", String.format("#%06X", this.g & 16777215));
         var9.b("colorControlHighlight", String.format("#%06X", this.j & 16777215));
         var9.b("colorControlNormal", String.format("#%06X", 16777215 & this.k));
         var6.add(new SrcCodeBean("colors.xml", var9.a()));
         var9 = new hh();
         var9.c("AppTheme", "Theme.AppCompat.Light.NoActionBar");
         var9.a("AppTheme", "colorPrimary", "@color/colorPrimary");
         var9.a("AppTheme", "colorPrimaryDark", "@color/colorPrimaryDark");
         var9.a("AppTheme", "colorAccent", "@color/colorAccent");
         var9.a("AppTheme", "colorControlHighlight", "@color/colorControlHighlight");
         var9.a("AppTheme", "colorControlNormal", "@color/colorControlNormal");
         var9.c("AppTheme.FullScreen", "AppTheme");
         var9.a("AppTheme.FullScreen", "android:windowFullscreen", "true");
         var9.a("AppTheme.FullScreen", "android:windowContentOverlay", "@null");
         var9.c("AppTheme.AppBarOverlay", "ThemeOverlay.AppCompat.Dark.ActionBar");
         var9.c("AppTheme.PopupOverlay", "ThemeOverlay.AppCompat.Light");
         var6.add(new SrcCodeBean("styles.xml", var9.a()));
      } else {
         var9 = new hh();
         var9.c("AppTheme", "android:style/Theme.Holo.Light.DarkActionBar");
         var9.c("NoActionBar", "android:style/Theme.Holo.Light.NoActionBar");
         var9.c("FullScreen", "android:style/Theme.Holo.Light.NoActionBar.Fullscreen");
         var9.c("NoStatusBar", "AppTheme");
         var9.a("NoStatusBar", "android:windowFullscreen", "true");
         var6.add(new SrcCodeBean("styles.xml", var9.a()));
         var9 = new hh();
         var9.c("AppTheme", "@android:style/Theme.Material.Light.DarkActionBar");
         var9.a("AppTheme", "android:colorPrimary", "@color/colorPrimary");
         var9.a("AppTheme", "android:colorPrimaryDark", "@color/colorPrimaryDark");
         var9.a("AppTheme", "android:colorAccent", "@color/colorAccent");
         var9.a("AppTheme", "android:colorControlHighlight", "@color/colorControlHighlight");
         var9.a("AppTheme", "android:colorControlNormal", "@color/colorControlNormal");
         var9.c("FullScreen", "@android:style/Theme.Material.Light.NoActionBar.Fullscreen");
         var9.a("FullScreen", "android:colorPrimary", "@color/colorPrimary");
         var9.a("FullScreen", "android:colorPrimaryDark", "@color/colorPrimaryDark");
         var9.a("FullScreen", "android:colorAccent", "@color/colorAccent");
         var9.a("FullScreen", "android:colorControlHighlight", "@color/colorControlHighlight");
         var9.a("FullScreen", "android:colorControlNormal", "@color/colorControlNormal");
         var9.c("NoActionBar", "@android:style/Theme.Material.Light.NoActionBar");
         var9.a("NoActionBar", "android:colorPrimary", "@color/colorPrimary");
         var9.a("NoActionBar", "android:colorPrimaryDark", "@color/colorPrimaryDark");
         var9.a("NoActionBar", "android:colorAccent", "@color/colorAccent");
         var9.a("NoActionBar", "android:colorControlHighlight", "@color/colorControlHighlight");
         var9.a("NoActionBar", "android:colorControlNormal", "@color/colorControlNormal");
         var9.c("NoStatusBar", "AppTheme");
         var9.a("NoStatusBar", "android:windowFullscreen", "true");
         var6.add(new SrcCodeBean("styles_v21.xml", var9.a()));
         var9 = new hh();
         var9.b("colorPrimary", String.format("#%06X", this.h & 16777215));
         var9.b("colorPrimaryDark", String.format("#%06X", this.i & 16777215));
         var9.b("colorAccent", String.format("#%06X", this.g & 16777215));
         var9.b("colorControlHighlight", String.format("#%06X", this.j & 16777215));
         var9.b("colorControlNormal", String.format("#%06X", 16777215 & this.k));
         var6.add(new SrcCodeBean("colors.xml", var9.a()));
      }

      if (this.M.t) {
         hi var11 = new hi("paths");
         var11.a("xmlns", "android", "http://schemas.android.com/apk/res/android");
         hi var5 = new hi("external-path");
         var5.a("", "name", "external_files");
         var5.a("", "path", ".");
         var11.a(var5);
         var6.add(new SrcCodeBean("provider_paths.xml", var11.a()));
      }

      var9 = new hh();
      var9.a("app_name", this.f);
      var6.add(new SrcCodeBean("strings.xml", var9.a()));
      var6.add(new SrcCodeBean("SketchwareUtil.java", hg.c(this.e)));
      var6.add(new SrcCodeBean("FileUtil.java", hg.d(this.e)));
      var6.add(new SrcCodeBean("RequestNetwork.java", hg.e(this.e)));
      var6.add(new SrcCodeBean("RequestNetworkController.java", hg.f(this.e)));
      var6.add(new SrcCodeBean("BluetoothConnect.java", hg.g(this.e)));
      var6.add(new SrcCodeBean("BluetoothController.java", hg.h(this.e)));
      var6.add(new SrcCodeBean("GoogleMapController.java", hg.i(this.e)));
      Iterator var12 = var6.iterator();

      while(var12.hasNext()) {
         SrcCodeBean var7 = (SrcCodeBean)var12.next();
         this.a(var7.srcFileName, var7.source);
      }

      if (this.M.h) {
         ProjectLibraryBean var14 = var3.c();
         hh var8 = new hh();
         var8.a("google_play_services_version", 12451000);
         StringBuilder var13;
         if (this.M.l) {
            var13 = new StringBuilder();
            var13.append("https://");
            var13.append(var14.data);
            var13.append(".firebaseio.com");
            var8.a("firebase_database_url", var13.toString(), false);
            var8.a("project_id", var14.data, false);
            var8.a("google_app_id", var14.reserved1, false);
            if (var14.reserved2 != null && var14.reserved2.length() > 0) {
               var8.a("google_api_key", var14.reserved2, false);
            }

            if (var14.reserved3 != null && var14.reserved3.length() > 0) {
               var8.a("google_storage_bucket", var14.reserved3, false);
            }
         }

         if (this.M.o) {
            ProjectLibraryBean var15 = var3.f();
            var8.a("google_maps_key", var15.data, false);
         }

         kk var10 = this.K;
         var13 = new StringBuilder();
         var13.append(this.w);
         var13.append(File.separator);
         var13.append("values");
         var13.append(File.separator);
         var13.append("secrets.xml");
         var10.b(var13.toString(), var8.a());
      }

      this.d();
   }

   public void b(Context var1) {
      kk var3 = this.K;
      StringBuilder var2 = new StringBuilder();
      var2.append("debug");
      var2.append(File.separator);
      var2.append("DebugActivity.java");
      String var7 = var3.b(var1, var2.toString());
      var7 = var7.replaceAll("\\<\\?package_name\\?\\>", this.e);
      var3 = this.K;
      StringBuilder var4 = new StringBuilder();
      var4.append(this.y);
      var4.append(File.separator);
      var4.append(this.n);
      var4.append(File.separator);
      var4.append("DebugActivity.java");
      var3.b(var4.toString(), var7);
      var3 = this.K;
      var2 = new StringBuilder();
      var2.append("debug");
      var2.append(File.separator);
      var2.append("SketchApplication.java");
      String var5 = var3.b(var1, var2.toString());
      String var8 = var5.replaceAll("\\<\\?package_name\\?\\>", this.e);
      kk var6 = this.K;
      var2 = new StringBuilder();
      var2.append(this.y);
      var2.append(File.separator);
      var2.append(this.n);
      var2.append(File.separator);
      var2.append("SketchApplication.java");
      var6.b(var2.toString(), var8);
   }

   public void c() {
      if (VERSION.SDK_INT <= 19) {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.w);
         var1.append(File.separator);
         var1.append("values-v21");
         String var2 = var1.toString();
         File var3 = new File(var2);
         if (var3.exists()) {
            this.K.a(var3);
         }
      }

   }

   public void d() {
      String var2 = hg.a(27, 19, 27, this.M);
      kk var1 = this.K;
      StringBuilder var3 = new StringBuilder();
      var3.append(this.c);
      var3.append(File.separator);
      var3.append("app");
      var3.append(File.separator);
      var3.append("build.gradle");
      var1.b(var3.toString(), var2);
      String var5 = hg.a();
      var1 = this.K;
      StringBuilder var4 = new StringBuilder();
      var4.append(this.c);
      var4.append(File.separator);
      var4.append("settings.gradle");
      var1.b(var4.toString(), var5);
      var2 = hg.c("3.1.0", "3.3.0");
      var1 = this.K;
      var3 = new StringBuilder();
      var3.append(this.c);
      var3.append(File.separator);
      var3.append("build.gradle");
      var1.b(var3.toString(), var2);
   }

   public void e() {
      this.K.d(this.t);
      this.K.d(this.v);
   }

   public void f() {
      this.K.c(this.t);
      this.K.c(this.u);
      this.K.c(this.v);
   }

   public boolean g() {
      return (new File(this.H)).exists();
   }

   public void h() {
      if (this.g()) {
         (new File(this.H)).delete();
      }

   }
}
