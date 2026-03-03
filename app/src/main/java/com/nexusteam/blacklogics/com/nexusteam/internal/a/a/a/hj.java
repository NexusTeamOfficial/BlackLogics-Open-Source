/* Decompiler 168ms, total 9395ms, lines 992 */
package com.nexusteam.internal;

import com.nexusteam.internal.beans.ImageBean;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.TextBean;
import com.nexusteam.internal.beans.ViewBean;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Iterator;

public class hj {
   private es a;
   private ProjectFileBean b;
   private ViewBean c;
   private ArrayList<ViewBean> d;
   private hi e = null;

   public hj(es var1, ProjectFileBean var2) {
      this.a = var1;
      this.b = var2;
   }

   private String a(String var1) {
      CharBuffer var3 = CharBuffer.wrap(var1);
      var1 = "";

      while(var3.hasRemaining()) {
         char var2 = var3.get();
         StringBuilder var4;
         if (var2 == '?') {
            var4 = new StringBuilder();
            var4.append(var1);
            var4.append("\\?");
            var1 = var4.toString();
         } else if (var2 == '@') {
            var4 = new StringBuilder();
            var4.append(var1);
            var4.append("\\@");
            var1 = var4.toString();
         } else if (var2 == '"') {
            var4 = new StringBuilder();
            var4.append(var1);
            var4.append("&quot;");
            var1 = var4.toString();
         } else if (var2 == '&') {
            var4 = new StringBuilder();
            var4.append(var1);
            var4.append("&amp;");
            var1 = var4.toString();
         } else if (var2 == '<') {
            var4 = new StringBuilder();
            var4.append(var1);
            var4.append("&lt;");
            var1 = var4.toString();
         } else if (var2 == '>') {
            var4 = new StringBuilder();
            var4.append(var1);
            var4.append("&gt;");
            var1 = var4.toString();
         } else if (var2 == '\n') {
            var4 = new StringBuilder();
            var4.append(var1);
            var4.append("\\n");
            var1 = var4.toString();
         } else {
            var4 = new StringBuilder();
            var4.append(var1);
            var4.append(var2);
            var1 = var4.toString();
         }
      }

      return var1;
   }

   private void b() {
      hi var1 = new hi("LinearLayout");
      var1.a("android", "layout_width", "match_parent");
      var1.a("android", "layout_height", "match_parent");
      var1.a("android", "orientation", "vertical");
      Iterator var2 = this.d.iterator();

      while(true) {
         ViewBean var3;
         do {
            if (!var2.hasNext()) {
               if (this.a.k) {
                  if (this.b.fileType == 0) {
                     if (this.b.hasActivityOption(1)) {
                        var1.a("app", "layout_behavior", "@string/appbar_scrolling_view_behavior");
                     }

                     hi var5;
                     if (this.b.hasActivityOption(1) || this.b.hasActivityOption(8)) {
                        var5 = new hi("android.support.design.widget.CoordinatorLayout");
                        var5.a("android", "layout_width", "match_parent");
                        var5.a("android", "layout_height", "match_parent");
                        this.e = var5;
                     }

                     hi var7;
                     if (this.b.hasActivityOption(1)) {
                        var5 = new hi("android.support.v7.widget.Toolbar");
                        var5.a("android", "id", "@+id/_toolbar");
                        var5.a("android", "layout_width", "match_parent");
                        var5.a("android", "layout_height", "?attr/actionBarSize");
                        var5.a("android", "background", "?attr/colorPrimary");
                        var5.a("app", "popupTheme", "@style/AppTheme.PopupOverlay");
                        var7 = new hi("android.support.design.widget.AppBarLayout");
                        var7.a("android", "layout_width", "match_parent");
                        var7.a("android", "layout_height", "wrap_content");
                        var7.a("android", "theme", "@style/AppTheme.AppBarOverlay");
                        var7.a(var5);
                        this.e.a(var7);
                        this.e.a(var1);
                     } else if (this.e == null) {
                        this.e = var1;
                     } else {
                        this.e.a(var1);
                     }

                     if (this.b.hasActivityOption(8)) {
                        this.a(this.e, this.c);
                     }

                     if (this.b.hasActivityOption(4)) {
                        var7 = new hi("android.support.v4.widget.DrawerLayout");
                        var7.a("android", "id", "@+id/_drawer");
                        var7.a("android", "layout_width", "match_parent");
                        var7.a("android", "layout_height", "match_parent");
                        var7.a("tools", "openDrawer", "start");
                        var7.a(this.e);
                        var1 = new hi("LinearLayout");
                        var1.a("android", "id", "@+id/_nav_view");
                        var1.a("android", "layout_width", "320dp");
                        var1.a("android", "layout_height", "match_parent");
                        var1.a("android", "layout_gravity", "start");
                        var1.a("android", "background", "#EEEEEE");
                        hi var4 = new hi("include", true);
                        StringBuilder var6 = new StringBuilder();
                        var6.append("@layout/_drawer_");
                        var6.append(this.b.fileName);
                        var4.a("", "layout", var6.toString());
                        var1.a(var4);
                        var7.a(var1);
                        this.e = var7;
                     }
                  } else {
                     this.e = var1;
                  }
               } else {
                  this.e = var1;
               }

               this.e.a(0, "xmlns", "tools", "http://schemas.android.com/tools");
               this.e.a(0, "xmlns", "app", "http://schemas.android.com/apk/res-auto");
               this.e.a(0, "xmlns", "android", "http://schemas.android.com/apk/res/android");
               return;
            }

            var3 = (ViewBean)var2.next();
         } while(var3.parent != null && var3.parent.length() > 0 && !var3.parent.equals("root"));

         this.b(var1, var3);
      }
   }

   public String a() {
      return this.e.a();
   }

   protected void a(hi var1, ViewBean var2) {
      hi var3 = new hi("android.support.design.widget.FloatingActionButton");
      StringBuilder var4 = new StringBuilder();
      var4.append("@+id/");
      var4.append(var2.id);
      var3.a("android", "id", var4.toString());
      var3.a("android", "layout_width", "wrap_content");
      var3.a("android", "layout_height", "wrap_content");
      this.c(var3, var2);
      this.k(var3, var2);
      if (var2.image.resName != null && var2.image.resName.length() > 0 && !var2.image.resName.equals("NONE")) {
         var4 = new StringBuilder();
         var4.append("@drawable/");
         var4.append(var2.image.resName.toLowerCase());
         var3.a("app", "srcCompat", var4.toString());
      }

      this.g(var3, var2);
      var1.a(var3);
   }

   public void a(ArrayList<ViewBean> var1) {
      this.a((ArrayList)var1, (ViewBean)null);
   }

   public void a(ArrayList<ViewBean> var1, ViewBean var2) {
      this.c = var2;
      this.d = var1;
      this.b();
   }

   protected void b(hi var1, ViewBean var2) {
      byte var3;
      label179: {
         String var4 = var2.getClassInfo().a();
         switch(var4.hashCode()) {
         case -1805606060:
            if (var4.equals("Switch")) {
               var3 = 11;
               break label179;
            }
            break;
         case -1793532415:
            if (var4.equals("MapView")) {
               var3 = 16;
               break label179;
            }
            break;
         case -1495589242:
            if (var4.equals("ProgressBar")) {
               var3 = 15;
               break label179;
            }
            break;
         case -1406842887:
            if (var4.equals("WebView")) {
               var3 = 10;
               break label179;
            }
            break;
         case -1125439882:
            if (var4.equals("HorizontalScrollView")) {
               var3 = 8;
               break label179;
            }
            break;
         case -938935918:
            if (var4.equals("TextView")) {
               var3 = 2;
               break label179;
            }
            break;
         case -658531749:
            if (var4.equals("SeekBar")) {
               var3 = 12;
               break label179;
            }
            break;
         case -339785223:
            if (var4.equals("Spinner")) {
               var3 = 6;
               break label179;
            }
            break;
         case -188272861:
            if (var4.equals("CalendarView")) {
               var3 = 13;
               break label179;
            }
            break;
         case 1125864064:
            if (var4.equals("ImageView")) {
               var3 = 4;
               break label179;
            }
            break;
         case 1127291599:
            if (var4.equals("LinearLayout")) {
               var3 = 0;
               break label179;
            }
            break;
         case 1410352259:
            if (var4.equals("ListView")) {
               var3 = 7;
               break label179;
            }
            break;
         case 1601505219:
            if (var4.equals("CheckBox")) {
               var3 = 5;
               break label179;
            }
            break;
         case 1666676343:
            if (var4.equals("EditText")) {
               var3 = 3;
               break label179;
            }
            break;
         case 1955913096:
            if (var4.equals("AdView")) {
               var3 = 14;
               break label179;
            }
            break;
         case 2001146706:
            if (var4.equals("Button")) {
               var3 = 1;
               break label179;
            }
            break;
         case 2059813682:
            if (var4.equals("ScrollView")) {
               var3 = 9;
               break label179;
            }
         }

         var3 = -1;
      }

      hi var7;
      switch(var3) {
      case 0:
         var7 = new hi("LinearLayout");
         break;
      case 1:
         var7 = new hi("Button");
         break;
      case 2:
         var7 = new hi("TextView");
         break;
      case 3:
         var7 = new hi("EditText");
         break;
      case 4:
         var7 = new hi("ImageView");
         break;
      case 5:
         var7 = new hi("CheckBox");
         break;
      case 6:
         var7 = new hi("Spinner");
         break;
      case 7:
         var7 = new hi("ListView");
         break;
      case 8:
         var7 = new hi("HorizontalScrollView");
         break;
      case 9:
         var7 = new hi("ScrollView");
         break;
      case 10:
         var7 = new hi("WebView");
         break;
      case 11:
         var7 = new hi("Switch");
         break;
      case 12:
         var7 = new hi("SeekBar");
         break;
      case 13:
         var7 = new hi("CalendarView");
         break;
      case 14:
         var7 = new hi("com.google.android.gms.ads.AdView");
         break;
      case 15:
         var7 = new hi("ProgressBar");
         break;
      case 16:
         var7 = new hi("com.google.android.gms.maps.MapView");
         break;
      default:
         var7 = null;
      }

      if (var7 != null) {
         StringBuilder var5 = new StringBuilder();
         var5.append("@+id/");
         var5.append(var2.id);
         var7.a("android", "id", var5.toString());
         if (this.b.fileType == 1 && (var2.type == 4 || var2.type == 5 || var2.type == 3 || var2.type == 6 || var2.type == 11 || var2.type == 13 || var2.type == 14 || var2.type == 15 || var2.type == 8)) {
            var7.a("android", "focusable", "false");
         }

         if (var2.layout.width == -1) {
            var7.a("android", "layout_width", "match_parent");
         } else if (var2.layout.width == -2) {
            var7.a("android", "layout_width", "wrap_content");
         } else {
            var5 = new StringBuilder();
            var5.append(var2.layout.width);
            var5.append("dp");
            var7.a("android", "layout_width", var5.toString());
         }

         if (var2.layout.height == -1) {
            var7.a("android", "layout_height", "match_parent");
         } else if (var2.layout.height == -2) {
            var7.a("android", "layout_height", "wrap_content");
         } else {
            var5 = new StringBuilder();
            var5.append(var2.layout.height);
            var5.append("dp");
            var7.a("android", "layout_height", var5.toString());
         }

         this.c(var7, var2);
         this.d(var7, var2);
         this.m(var7, var2);
         if (var2.getClassInfo().a("ViewGroup")) {
            this.j(var7, var2);
         }

         if (var2.getClassInfo().b("LinearLayout")) {
            this.l(var7, var2);
            this.i(var7, var2);
         }

         if (var2.getClassInfo().a("TextView")) {
            this.j(var7, var2);
            this.e(var7, var2);
         }

         if (var2.getClassInfo().a("ImageView")) {
            this.f(var7, var2);
         }

         if (var2.getClassInfo().b("SeekBar")) {
            this.j(var7, var2);
         }

         if (var2.getClassInfo().b("ProgressBar")) {
            this.j(var7, var2);
         }

         this.g(var7, var2);
         int var9 = var2.parentType;
         if (var9 != 0) {
            if (var9 == 2 || var9 == 12) {
               this.k(var7, var2);
            }
         } else {
            this.k(var7, var2);
            this.h(var7, var2);
         }

         if (var2.getClassInfo().a("ViewGroup")) {
            Iterator var8 = this.d.iterator();

            while(var8.hasNext()) {
               ViewBean var6 = (ViewBean)var8.next();
               if (var6.parent != null && var6.parent.equals(var2.id)) {
                  this.b(var7, var6);
               }
            }
         }

         var1.a(var7);
      }
   }

   public void c(hi var1, ViewBean var2) {
      StringBuilder var3;
      if (var2.layout.marginLeft == var2.layout.marginRight && var2.layout.marginTop == var2.layout.marginBottom && var2.layout.marginLeft == var2.layout.marginTop && var2.layout.marginLeft > 0) {
         var3 = new StringBuilder();
         var3.append(String.valueOf(var2.layout.marginLeft));
         var3.append("dp");
         var1.a("android", "layout_margin", var3.toString());
      } else {
         if (var2.layout.marginLeft > 0) {
            var3 = new StringBuilder();
            var3.append(String.valueOf(var2.layout.marginLeft));
            var3.append("dp");
            var1.a("android", "layout_marginLeft", var3.toString());
         }

         if (var2.layout.marginTop > 0) {
            var3 = new StringBuilder();
            var3.append(String.valueOf(var2.layout.marginTop));
            var3.append("dp");
            var1.a("android", "layout_marginTop", var3.toString());
         }

         if (var2.layout.marginRight > 0) {
            var3 = new StringBuilder();
            var3.append(String.valueOf(var2.layout.marginRight));
            var3.append("dp");
            var1.a("android", "layout_marginRight", var3.toString());
         }

         if (var2.layout.marginBottom > 0) {
            var3 = new StringBuilder();
            var3.append(String.valueOf(var2.layout.marginBottom));
            var3.append("dp");
            var1.a("android", "layout_marginBottom", var3.toString());
         }

      }
   }

   public void d(hi var1, ViewBean var2) {
      StringBuilder var3;
      if (var2.layout.paddingLeft == var2.layout.paddingRight && var2.layout.paddingTop == var2.layout.paddingBottom && var2.layout.paddingLeft == var2.layout.paddingTop && var2.layout.paddingLeft > 0) {
         var3 = new StringBuilder();
         var3.append(String.valueOf(var2.layout.paddingLeft));
         var3.append("dp");
         var1.a("android", "padding", var3.toString());
      } else {
         if (var2.layout.paddingLeft > 0) {
            var3 = new StringBuilder();
            var3.append(String.valueOf(var2.layout.paddingLeft));
            var3.append("dp");
            var1.a("android", "paddingLeft", var3.toString());
         }

         if (var2.layout.paddingTop > 0) {
            var3 = new StringBuilder();
            var3.append(String.valueOf(var2.layout.paddingTop));
            var3.append("dp");
            var1.a("android", "paddingTop", var3.toString());
         }

         if (var2.layout.paddingRight > 0) {
            var3 = new StringBuilder();
            var3.append(String.valueOf(var2.layout.paddingRight));
            var3.append("dp");
            var1.a("android", "paddingRight", var3.toString());
         }

         if (var2.layout.paddingBottom > 0) {
            var3 = new StringBuilder();
            var3.append(String.valueOf(var2.layout.paddingBottom));
            var3.append("dp");
            var1.a("android", "paddingBottom", var3.toString());
         }

      }
   }

   public void e(hi var1, ViewBean var2) {
      if (var2.text.text != null && var2.text.text.length() > 0) {
         var1.a("android", "text", this.a(var2.text.text));
      }

      if (var2.text.textSize > 0) {
         StringBuilder var3 = new StringBuilder();
         var3.append(String.valueOf(var2.text.textSize));
         var3.append("sp");
         var1.a("android", "textSize", var3.toString());
      }

      if (var2.text.textType == TextBean.TEXT_TYPE_BOLD) {
         var1.a("android", "textStyle", "bold");
      } else if (var2.text.textType == TextBean.TEXT_TYPE_ITALIC) {
         var1.a("android", "textStyle", "italic");
      } else if (var2.text.textType == TextBean.TEXT_TYPE_BOLDITALIC) {
         var1.a("android", "textStyle", "bold|italic");
      }

      if (var2.text.textColor != 0) {
         var1.a("android", "textColor", String.format("#%06X", var2.text.textColor & 16777215));
      }

      if (var2.type == 5) {
         if (var2.text.hint != null && var2.text.hint.length() > 0) {
            var1.a("android", "hint", this.a(var2.text.hint));
         }

         if (var2.text.hintColor != 0) {
            var1.a("android", "textColorHint", String.format("#%06X", 16777215 & var2.text.hintColor));
         }

         if (var2.text.singleLine != 0) {
            var1.a("android", "singleLine", "true");
         }

         if (var2.text.line > 0) {
            var1.a("android", "lines", String.valueOf(var2.text.line));
         }

         if (var2.text.inputType != TextBean.INPUT_TYPE_TEXT) {
            var1.a("android", "inputType", fa.a("property_input_type", var2.text.inputType));
         }

         if (var2.text.imeOption != TextBean.IME_OPTION_NORMAL) {
            if (var2.text.imeOption == TextBean.IME_OPTION_NONE) {
               var1.a("android", "imeOptions", "actionNone");
            } else if (var2.text.imeOption == TextBean.IME_OPTION_GO) {
               var1.a("android", "imeOptions", "actionGo");
            } else if (var2.text.imeOption == TextBean.IME_OPTION_SEARCH) {
               var1.a("android", "imeOptions", "actionSearch");
            } else if (var2.text.imeOption == TextBean.IME_OPTION_SEND) {
               var1.a("android", "imeOptions", "actionSend");
            } else if (var2.text.imeOption == TextBean.IME_OPTION_NEXT) {
               var1.a("android", "imeOptions", "actionNext");
            } else if (var2.text.imeOption == TextBean.IME_OPTION_DONE) {
               var1.a("android", "imeOptions", "actionDone");
            }
         }
      }

      if (var2.type == 4) {
         if (var2.text.singleLine != 0) {
            var1.a("android", "singleLine", "true");
         }

         if (var2.text.line > 0) {
            var1.a("android", "lines", String.valueOf(var2.text.line));
         }
      }

   }

   public void f(hi var1, ViewBean var2) {
      if (var2.image.resName.length() > 0 && !"NONE".equals(var2.image.resName)) {
         StringBuilder var3 = new StringBuilder();
         var3.append("@drawable/");
         var3.append(var2.image.resName.toLowerCase());
         var1.a("android", "src", var3.toString());
      }

      if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER)) {
         var1.a("android", "scaleType", "center");
      } else if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_XY)) {
         var1.a("android", "scaleType", "fitXY");
      } else if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_START)) {
         var1.a("android", "scaleType", "fitStart");
      } else if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_END)) {
         var1.a("android", "scaleType", "fitEnd");
      } else if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_CENTER)) {
         var1.a("android", "scaleType", "fitCenter");
      } else if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER_CROP)) {
         var1.a("android", "scaleType", "centerCrop");
      } else if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER_INSIDE)) {
         var1.a("android", "scaleType", "centerInside");
      }

   }

   public void g(hi var1, ViewBean var2) {
      if (var2.enabled == 0) {
         var1.a("android", "enabled", "false");
      }

      if (var2.clickable == 0) {
         var1.a("android", "clickable", "false");
      }

      if (var2.image.rotate != 0) {
         var1.a("android", "rotation", String.valueOf(var2.image.rotate));
      }

      if (1.0F != var2.alpha) {
         var1.a("android", "alpha", String.valueOf(var2.alpha));
      }

      StringBuilder var3;
      if (0.0F != var2.translationX) {
         var3 = new StringBuilder();
         var3.append(String.valueOf(var2.translationX));
         var3.append("dp");
         var1.a("android", "translationX", var3.toString());
      }

      if (0.0F != var2.translationY) {
         var3 = new StringBuilder();
         var3.append(String.valueOf(var2.translationY));
         var3.append("dp");
         var1.a("android", "translationY", var3.toString());
      }

      if (1.0F != var2.scaleX) {
         var1.a("android", "scaleX", String.valueOf(var2.scaleX));
      }

      if (1.0F != var2.scaleY) {
         var1.a("android", "scaleY", String.valueOf(var2.scaleY));
      }

      if ((var2.type == 11 || var2.type == 13) && 1 == var2.checked) {
         var1.a("android", "checked", "true");
      }

      if (var2.type == 14) {
         if (var2.progress > 0) {
            var1.a("android", "progress", String.valueOf(var2.progress));
         }

         if (100 != var2.max) {
            var1.a("android", "max", String.valueOf(var2.max));
         }
      }

      if (var2.type == 15 && var2.firstDayOfWeek != 1) {
         var1.a("android", "firstDayOfWeek", String.valueOf(var2.firstDayOfWeek));
      }

      if (var2.type == 10) {
         if (var2.spinnerMode == 1) {
            var1.a("android", "spinnerMode", "dropdown");
         } else if (var2.spinnerMode == 0) {
            var1.a("android", "spinnerMode", "dialog");
         }
      }

      if (var2.type == 9) {
         if (1 != var2.dividerHeight) {
            var3 = new StringBuilder();
            var3.append(var2.dividerHeight);
            var3.append("dp");
            var1.a("android", "dividerHeight", var3.toString());
         }

         if (var2.dividerHeight == 0) {
            var1.a("android", "divider", "@null");
         }

         if (var2.choiceMode == 0) {
            var1.a("android", "choiceMode", "none");
         } else if (var2.choiceMode == 1) {
            var1.a("android", "choiceMode", "singleChoice");
         } else if (var2.choiceMode == 2) {
            var1.a("android", "choiceMode", "multipleChoice");
         }
      }

      if (var2.type == 17) {
         if (var2.adSize != null && var2.adSize.length() > 0) {
            var1.a("app", "adSize", var2.adSize);
         } else {
            var1.a("app", "adSize", "SMART_BANNER");
         }

         var1.a("app", "adUnitId", this.a.q);
      }

      if (var2.type == 8) {
         if (var2.progress > 0) {
            var1.a("android", "progress", String.valueOf(var2.progress));
         }

         if (100 != var2.max) {
            var1.a("android", "max", String.valueOf(var2.max));
         }

         if (var2.indeterminate != null && var2.indeterminate.length() > 0) {
            var1.a("android", "indeterminate", var2.indeterminate);
         }

         if (var2.progressStyle != null && var2.progressStyle.length() > 0) {
            var1.a((String)null, "style", var2.progressStyle);
         }
      }

   }

   public void h(hi var1, ViewBean var2) {
      if (var2.layout.weight > 0) {
         var1.a("android", "layout_weight", String.valueOf(var2.layout.weight));
      }

   }

   public void i(hi var1, ViewBean var2) {
      if (var2.layout.weightSum > 0) {
         var1.a("android", "weightSum", String.valueOf(var2.layout.weightSum));
      }

   }

   public void j(hi var1, ViewBean var2) {
      if (var2.layout.gravity != 0) {
         String var5 = "";
         int var4 = var2.layout.gravity;
         int var3 = var4 & 112;
         var4 &= 7;
         StringBuilder var6;
         String var7;
         StringBuilder var8;
         if (var4 == 1) {
            var6 = new StringBuilder();
            var6.append("");
            var6.append("center_horizontal");
            var7 = var6.toString();
         } else {
            var7 = var5;
            if ((var4 & 3) == 3) {
               if ("".length() > 0) {
                  var6 = new StringBuilder();
                  var6.append("");
                  var6.append("|");
                  var5 = var6.toString();
               }

               var6 = new StringBuilder();
               var6.append(var5);
               var6.append("left");
               var7 = var6.toString();
            }

            if ((var4 & 5) == 5) {
               var5 = var7;
               if (var7.length() > 0) {
                  var8 = new StringBuilder();
                  var8.append(var7);
                  var8.append("|");
                  var5 = var8.toString();
               }

               var6 = new StringBuilder();
               var6.append(var5);
               var6.append("right");
               var7 = var6.toString();
            }
         }

         if (var3 == 16) {
            var5 = var7;
            if (var7.length() > 0) {
               var8 = new StringBuilder();
               var8.append(var7);
               var8.append("|");
               var5 = var8.toString();
            }

            var6 = new StringBuilder();
            var6.append(var5);
            var6.append("center_vertical");
            var7 = var6.toString();
         } else {
            var5 = var7;
            if ((var3 & 48) == 48) {
               var5 = var7;
               if (var7.length() > 0) {
                  var8 = new StringBuilder();
                  var8.append(var7);
                  var8.append("|");
                  var5 = var8.toString();
               }

               var6 = new StringBuilder();
               var6.append(var5);
               var6.append("top");
               var5 = var6.toString();
            }

            var7 = var5;
            if ((var3 & 80) == 80) {
               var7 = var5;
               if (var5.length() > 0) {
                  var6 = new StringBuilder();
                  var6.append(var5);
                  var6.append("|");
                  var7 = var6.toString();
               }

               var8 = new StringBuilder();
               var8.append(var7);
               var8.append("bottom");
               var7 = var8.toString();
            }
         }

         var1.a("android", "gravity", var7);
      }

   }

   public void k(hi var1, ViewBean var2) {
      if (var2.layout.layoutGravity != 0) {
         String var5 = "";
         int var4 = var2.layout.layoutGravity;
         int var3 = var4 & 112;
         var4 &= 7;
         StringBuilder var6;
         String var7;
         StringBuilder var8;
         if (var4 == 1) {
            var6 = new StringBuilder();
            var6.append("");
            var6.append("center_horizontal");
            var7 = var6.toString();
         } else {
            var7 = var5;
            if ((var4 & 3) == 3) {
               if ("".length() > 0) {
                  var6 = new StringBuilder();
                  var6.append("");
                  var6.append("|");
                  var5 = var6.toString();
               }

               var6 = new StringBuilder();
               var6.append(var5);
               var6.append("left");
               var7 = var6.toString();
            }

            if ((var4 & 5) == 5) {
               var5 = var7;
               if (var7.length() > 0) {
                  var8 = new StringBuilder();
                  var8.append(var7);
                  var8.append("|");
                  var5 = var8.toString();
               }

               var6 = new StringBuilder();
               var6.append(var5);
               var6.append("right");
               var7 = var6.toString();
            }
         }

         if (var3 == 16) {
            var5 = var7;
            if (var7.length() > 0) {
               var8 = new StringBuilder();
               var8.append(var7);
               var8.append("|");
               var5 = var8.toString();
            }

            var6 = new StringBuilder();
            var6.append(var5);
            var6.append("center_vertical");
            var7 = var6.toString();
         } else {
            var5 = var7;
            if ((var3 & 48) == 48) {
               var5 = var7;
               if (var7.length() > 0) {
                  var8 = new StringBuilder();
                  var8.append(var7);
                  var8.append("|");
                  var5 = var8.toString();
               }

               var6 = new StringBuilder();
               var6.append(var5);
               var6.append("top");
               var5 = var6.toString();
            }

            var7 = var5;
            if ((var3 & 80) == 80) {
               var7 = var5;
               if (var5.length() > 0) {
                  var6 = new StringBuilder();
                  var6.append(var5);
                  var6.append("|");
                  var7 = var6.toString();
               }

               var8 = new StringBuilder();
               var8.append(var7);
               var8.append("bottom");
               var7 = var8.toString();
            }
         }

         var1.a("android", "layout_gravity", var7);
      }

   }

   public void l(hi var1, ViewBean var2) {
      if (var2.layout.orientation == 0) {
         var1.a("android", "orientation", "horizontal");
      } else if (var2.layout.orientation == 1) {
         var1.a("android", "orientation", "vertical");
      }

   }

   public void m(hi var1, ViewBean var2) {
      if (var2.layout.backgroundResource != null && !"NONE".toLowerCase().equals(var2.layout.backgroundResource.toLowerCase())) {
         String var3;
         if (var2.layout.backgroundResource.endsWith(".9")) {
            var3 = var2.layout.backgroundResource;
            var3 = var3.replaceAll("\\.9", "");
         } else {
            var3 = var2.layout.backgroundResource;
         }

         var1.a("android", "background", String.format("@drawable/%s", var3));
      } else if (var2.layout.backgroundColor != 16777215) {
         if (var2.layout.backgroundColor == 0) {
            var1.a("android", "background", "@android:color/transparent");
         } else {
            var1.a("android", "background", String.format("#%06X", var2.layout.backgroundColor & 16777215));
         }

      }
   }
}
