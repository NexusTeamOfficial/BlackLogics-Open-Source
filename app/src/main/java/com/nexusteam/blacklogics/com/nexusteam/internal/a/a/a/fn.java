/* Decompiler 32ms, total 1022ms, lines 191 */
package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class fn extends fo {
   private Context a;
   private Object b = "";
   private TextView c;
   private TextView d;
   private int e = 20;
   private int f = 4;
   private int g = 2;
   private int h = 0;
   private int i = 0;

   public fn(Context var1, String var2, String var3) {
      super(var1, var2, var3, true);
      this.a = var1;
      this.a(var1);
   }

   private TextView a(String var1) {
      TextView var2 = new TextView(this.a);
      var2.setText(var1);
      var2.setTextSize(9.0F);
      LayoutParams var3 = new LayoutParams(this.e, this.ae);
      var3.setMargins(this.h, 0, this.i, 0);
      var2.setPadding(0, 0, 0, 0);
      var2.setLayoutParams(var3);
      var2.setBackgroundColor(0);
      var2.setSingleLine();
      var2.setGravity(17);
      if (!this.w.equals("m")) {
         var2.setTextColor(-268435456);
      } else {
         var2.setTextColor(-251658241);
      }

      return var2;
   }

   private void a(Context var1) {
      String var3;
      byte var4;
      label62: {
         var3 = this.w;
         int var2 = var3.hashCode();
         if (var2 != 98) {
            if (var2 != 100) {
               if (var2 != 115) {
                  switch(var2) {
                  case 109:
                     if (var3.equals("m")) {
                        var4 = 4;
                        break label62;
                     }
                     break;
                  case 110:
                     if (var3.equals("n")) {
                        var4 = 2;
                        break label62;
                     }
                  }
               } else if (var3.equals("s")) {
                  var4 = 3;
                  break label62;
               }
            } else if (var3.equals("d")) {
               var4 = 1;
               break label62;
            }
         } else if (var3.equals("b")) {
            var4 = 0;
            break label62;
         }

         var4 = -1;
      }

      switch(var4) {
      case 0:
         this.z = 1342177280;
         this.e = 25;
         break;
      case 1:
         this.z = -657931;
         break;
      case 2:
         this.z = -3155748;
         break;
      case 3:
         this.z = -1;
         break;
      case 4:
         this.z = 805306368;
      }

      this.e = (int)((float)this.e * this.ab);
      this.f = (int)((float)this.f * this.ab);
      this.g = (int)((float)this.g * this.ab);
      this.h = this.g;
      if (this.w.equals("m") && this.c(this.x).length() >= 0) {
         this.d = this.b(this.x);
         this.addView(this.d);
         this.h = this.getDropdownTypeWidth();
      }

      label47: {
         if (!this.w.equals("m") && !this.w.equals("d")) {
            var3 = this.w;
            if (!var3.equals("n") && !this.w.equals("s")) {
               break label47;
            }
         }

         this.c = this.a("");
         this.addView(this.c);
      }

      this.a((float)(this.e + this.h), (float)this.ae, false);
   }

   private TextView b(String var1) {
      TextView var2 = new TextView(this.a);
      var2.setText(this.c(var1));
      var2.setTextSize(8.0F);
      var2.setTypeface((Typeface)null, 1);
      LayoutParams var3 = new LayoutParams(-2, this.ae);
      var3.setMargins(this.g, 0, this.g, 0);
      var2.setPadding(0, 0, 0, 0);
      var2.setLayoutParams(var3);
      var2.setBackgroundColor(0);
      var2.setSingleLine();
      var2.setGravity(17);
      var2.setTextColor(-1);
      return var2;
   }

   private String c(String var1) {
      String var2 = et.a(var1);
      var1 = var2;
      if (var2.length() > 0) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var2);
         var3.append(" : ");
         var1 = var3.toString();
      }

      return var1;
   }

   private int getDropdownTypeWidth() {
      Rect var1 = new Rect();
      TextPaint var2 = this.d.getPaint();
      String var3 = this.c(this.x);
      var2.getTextBounds(var3, 0, var3.length(), var1);
      return var1.width() + this.g * 2;
   }

   private int getLabelWidth() {
      Rect var1 = new Rect();
      TextPaint var2 = this.c.getPaint();
      var2.getTextBounds(this.c.getText().toString(), 0, this.c.getText().length(), var1);
      return var1.width() + this.f;
   }

   public Object getArgValue() {
      return !this.w.equals("d") && !this.w.equals("m") && !this.w.equals("s") ? this.b : this.c.getText();
   }

   public String getMenuName() {
      return this.x;
   }

   public void setArgValue(Object var1) {
      this.b = var1;
      if (this.w.equals("d") || this.w.equals("m") || this.w.equals("s")) {
         this.c.setText(var1.toString());
         int var2 = Math.max(this.e, this.getLabelWidth());
         this.c.getLayoutParams().width = var2;
         this.a((float)(var2 + this.h), (float)this.ae, true);
      }

   }
}
