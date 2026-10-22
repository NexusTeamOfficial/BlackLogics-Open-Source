/* Decompiler 156ms, total 1861ms, lines 819 */
package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import com.nexusteam.internal.beans.BlockBean;
import java.util.ArrayList;
import java.util.Iterator;

public class fm extends fo {
   public String a;
   private TextView al = null;
   public String b;
   public ArrayList<View> c;
   protected int d = 30;
   protected int e = 50;
   protected int f = 90;
   protected int g = 90;
   protected int h = 4;
   public boolean i = false;
   public boolean j = false;
   public boolean k = false;
   public int l = -1;
   public int m = -1;
   public int n = -1;
   public ArrayList<View> o = new ArrayList();
   public ArrayList<String> p = new ArrayList();
   public int q = 0;
   protected int r = 0;
   public fq s = null;
   public String t;
   public String u;

   public fm(Context var1, int var2, String var3, String var4, String var5) {
      super(var1, var4, false);
      this.setTag(var2);
      this.a = var3;
      this.b = var5;
      this.a();
   }

   public fm(Context var1, int var2, String var3, String var4, String var5, String var6) {
      super(var1, var4, var5, false);
      this.setTag(var2);
      this.a = var3;
      this.b = var6;
      this.a();
   }

   private int a(TextView var1) {
      Rect var3 = new Rect();
      TextPaint var2 = var1.getPaint();
      var2.getTextBounds(var1.getText().toString(), 0, var1.getText().length(), var3);
      return var3.width();
   }

   private TextView a(String var1) {
      String var2;
      TextView var3;
      label15: {
         var3 = new TextView(this.v);
         if (!this.b.equals("getVar")) {
            var2 = var1;
            if (!this.b.equals("getArg")) {
               break label15;
            }
         }

         var2 = var1;
         if (this.x != null) {
            var2 = var1;
            if (this.x.length() > 0) {
               StringBuilder var5 = new StringBuilder();
               var5.append(this.x);
               var5.append(" : ");
               var5.append(var1);
               var2 = var5.toString();
            }
         }
      }

      var3.setText(var2);
      var3.setTextSize(10.0F);
      var3.setPadding(0, 0, 0, 0);
      var3.setGravity(16);
      var3.setTextColor(-1);
      var3.setTypeface((Typeface)null, 1);
      LayoutParams var4 = new LayoutParams(-2, this.ae);
      var4.setMargins(0, 0, 0, 0);
      var3.setLayoutParams(var4);
      return var3;
   }

   private void a(String var1, int var2) {
      ArrayList var4 = kx.d(var1);
      this.o = new ArrayList();
      this.p = new ArrayList();

      for(int var3 = 0; var3 < var4.size(); ++var3) {
         View var5 = this.b((String)var4.get(var3), var2);
         if (var5 instanceof fo) {
            ((fo)var5).ac = this;
         }

         this.o.add(var5);
         var1 = "icon";
         if (var5 instanceof fn) {
            var1 = (String)var4.get(var3);
         }

         if (var5 instanceof TextView) {
            var1 = "label";
         }

         this.p.add(var1);
      }

   }

   private View b(String var1, int var2) {
      if (var1.length() >= 2 && var1.charAt(0) == '%') {
         char var4 = var1.charAt(1);
         if (var4 == 'b') {
            return new fn(this.v, "b", "");
         }

         if (var4 == 'd') {
            return new fn(this.v, "d", "");
         }

         if (var4 == 'm') {
            return new fn(this.v, "m", var1.substring(3));
         }

         if (var4 == 's') {
            Context var3 = this.v;
            if (var1.length() > 2) {
               var1 = var1.substring(3);
            } else {
               var1 = "";
            }

            return new fn(var3, "s", var1);
         }
      }

      return this.a(kx.c(var1));
   }

   private void g(fm var1) {
      if (this.i() && -1 == this.m) {
         this.d(var1);
      } else {
         fm var2 = this.d();
         var2.l = (Integer)var1.getTag();
         var1.ac = var2;
      }

   }

   private void o() {
      if (this.al != null) {
         this.al.bringToFront();
         this.al.setX((float)this.R);
         this.al.setY((float)(this.l() - this.I));
      }

   }

   private void p() {
      this.c = new ArrayList();

      for(int var1 = 0; var1 < this.o.size(); ++var1) {
         View var2 = (View)this.o.get(var1);
         if (var2 instanceof fm || var2 instanceof fn) {
            this.c.add(var2);
         }
      }

   }

   protected void a() {
      byte var1;
      String var3;
      label80: {
         var1 = 0;
         this.setDrawingCacheEnabled(false);
         this.d = (int)((float)this.d * this.ab);
         this.e = (int)((float)this.e * this.ab);
         this.f = (int)((float)this.f * this.ab);
         this.g = (int)((float)this.g * this.ab);
         this.h = (int)((float)this.h * this.ab);
         var3 = this.w;
         int var2 = var3.hashCode();
         if (var2 != 32) {
            if (var2 != 104) {
               if (var2 != 108) {
                  if (var2 != 112) {
                     if (var2 != 115) {
                        if (var2 != 118) {
                           switch(var2) {
                           case 97:
                              if (var3.equals("a")) {
                                 var1 = 7;
                                 break label80;
                              }
                              break;
                           case 98:
                              if (var3.equals("b")) {
                                 var1 = 1;
                                 break label80;
                              }
                              break;
                           case 99:
                              if (var3.equals("c")) {
                                 var1 = 8;
                                 break label80;
                              }
                              break;
                           case 100:
                              if (var3.equals("d")) {
                                 var1 = 3;
                                 break label80;
                              }
                              break;
                           case 101:
                              if (var3.equals("e")) {
                                 var1 = 9;
                                 break label80;
                              }
                              break;
                           case 102:
                              if (var3.equals("f")) {
                                 var1 = 10;
                                 break label80;
                              }
                           }
                        } else if (var3.equals("v")) {
                           var1 = 4;
                           break label80;
                        }
                     } else if (var3.equals("s")) {
                        var1 = 2;
                        break label80;
                     }
                  } else if (var3.equals("p")) {
                     var1 = 5;
                     break label80;
                  }
               } else if (var3.equals("l")) {
                  var1 = 6;
                  break label80;
               }
            } else if (var3.equals("h")) {
               var1 = 11;
               break label80;
            }
         } else if (var3.equals(" ")) {
            break label80;
         }

         var1 = -1;
      }

      switch(var1) {
      case 0:
      case 8:
      case 9:
      default:
         break;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
         this.j = true;
         break;
      case 10:
         this.k = true;
         break;
      case 11:
         this.i = true;
      }

      if (!this.i) {
         var3 = this.b;
         if (!var3.equals("definedFunc")) {
            var3 = this.b;
            if (!var3.equals("getVar")) {
               var3 = this.b;
               if (!var3.equals("getArg")) {
                  this.a = kq.a().a(this.getContext(), this.b);
               }
            }
         }
      }

      this.setSpec(this.a);
      this.z = et.a(this.b, this.w);
   }

   public void a(fm var1) {
      View var2 = this.s.findViewWithTag(this.l);
      if (var2 != null) {
         ((fm)var2).ac = null;
      }

      var1.ac = this;
      this.l = (Integer)var1.getTag();
      if (var2 != null) {
         var1.g((fm)var2);
      }

   }

   public void a(fo var1, fm var2) {
      int var3 = this.o.indexOf(var1);
      if (var3 >= 0) {
         boolean var4 = var1 instanceof fm;
         if (var4) {
            fm var5 = (fm)var1;
            var2.t = var5.t;
            var2.u = var5.u;
         } else if (var1 instanceof fn) {
            var2.t = var1.w;
            var2.u = var1.x;
         }

         if (!var4) {
            this.removeView(var1);
         }

         this.o.set(var3, var2);
         var2.ac = this;
         this.p();
         this.e();
         if (var1 != var2 && var4) {
            var1.ac = null;
            var1.setX(this.getX() + (float)this.getWidthSum() + 10.0F);
            var1.setY(this.getY() + 5.0F);
            ((fm)var1).b();
         }

      }
   }

   public void b() {
      this.bringToFront();
      int var3 = this.R;

      int var4;
      for(int var5 = 0; var5 < this.o.size(); ++var5) {
         View var7 = (View)this.o.get(var5);
         var7.bringToFront();
         boolean var6 = var7 instanceof fm;
         float var1;
         if (var6) {
            var7.setX(this.getX() + (float)var3);
         } else {
            var1 = (float)var3;
            var7.setX(var1);
         }

         if (((String)this.p.get(var5)).equals("label")) {
            var4 = this.a((TextView)var7);
         } else {
            var4 = 0;
         }

         if (var7 instanceof fn) {
            var4 = ((fn)var7).getW();
         }

         if (var6) {
            var4 = ((fm)var7).getWidthSum();
         }

         var3 += var4 + this.h;
         if (var6) {
            var1 = this.getY();
            float var2 = (float)this.P;
            var4 = this.q;
            fm var8 = (fm)var7;
            var7.setY(var1 + var2 + (float)((var4 - var8.q - 1) * this.T));
            var8.b();
         } else {
            var7.setY((float)(this.P + this.q * this.T));
         }
      }

      label81: {
         if (!this.w.equals("b") && !this.w.equals("d") && !this.w.equals("s")) {
            var4 = var3;
            if (!this.w.equals("a")) {
               break label81;
            }
         }

         var4 = Math.max(var3, this.d);
      }

      label74: {
         if (!this.w.equals(" ") && !this.w.equals("")) {
            var3 = var4;
            if (!this.w.equals("f")) {
               break label74;
            }
         }

         var3 = Math.max(var4, this.e);
      }

      label68: {
         if (!this.w.equals("c")) {
            var4 = var3;
            if (!this.w.equals("e")) {
               break label68;
            }
         }

         var4 = Math.max(var3, this.g);
      }

      var3 = var4;
      if (this.w.equals("h")) {
         var3 = Math.max(var4, this.f);
      }

      this.a((float)(var3 + this.S), (float)(this.P + this.ae + this.q * this.T * 2 + this.Q), true);
      fm var9;
      if (this.i()) {
         var3 = this.D;
         if (this.m > -1) {
            var9 = (fm)this.s.findViewWithTag(this.m);
            var9.setX(this.getX() + (float)this.E);
            var9.setY(this.getY() + (float)this.k());
            var9.bringToFront();
            var9.b();
            var3 = var9.getHeightSum();
         }

         this.setSubstack1Height(var3);
         var3 = this.D;
         if (this.n > -1) {
            var9 = (fm)this.s.findViewWithTag(this.n);
            var9.setX(this.getX() + (float)this.E);
            var9.setY(this.getY() + (float)this.l());
            var9.bringToFront();
            var9.b();
            var3 = var9.getHeightSum();
            if (var9.d().k) {
               var3 += this.C;
            }
         }

         this.setSubstack2Height(var3);
         this.o();
      }

      if (this.l > -1) {
         var9 = (fm)this.s.findViewWithTag(this.l);
         var9.setX(this.getX());
         var9.setY(this.getY() + (float)this.m());
         var9.bringToFront();
         var9.b();
      }

   }

   public void b(fm var1) {
      var1.setX(this.getX());
      var1.setY(this.getY() - (float)var1.getHeightSum() + (float)this.C);
      var1.d().a(this);
   }

   public fm c() {
      fm var1;
      for(var1 = this; var1.ac != null; var1 = var1.ac) {
      }

      return var1;
   }

   public void c(fm var1) {
      var1.setX(this.getX() - (float)this.E);
      var1.setY(this.getY() - (float)this.k());
      this.ac = var1;
      var1.m = (Integer)this.getTag();
   }

   public fm d() {
      fm var1;
      for(var1 = this; var1.l != -1; var1 = (fm)this.s.findViewWithTag(var1.l)) {
      }

      return var1;
   }

   public void d(fm var1) {
      View var2 = this.s.findViewWithTag(this.m);
      if (var2 != null) {
         ((fm)var2).ac = null;
      }

      var1.ac = this;
      this.m = (Integer)var1.getTag();
      if (var2 != null) {
         var1.g((fm)var2);
      }

   }

   public void e() {
      for(fm var2 = this; var2 != null; var2 = var2.ac) {
         int var1 = 0;
         Iterator var3 = var2.c.iterator();

         while(var3.hasNext()) {
            View var4 = (View)var3.next();
            if (var4 instanceof fm) {
               fm var5 = (fm)var4;
               var1 = Math.max(var1, var5.q + 1);
            }
         }

         var2.q = var1;
         var2.f();
         if (!var2.j) {
            break;
         }
      }

   }

   public void e(fm var1) {
      View var2 = this.s.findViewWithTag(this.n);
      if (var2 != null) {
         ((fm)var2).ac = null;
      }

      var1.ac = this;
      this.n = (Integer)var1.getTag();
      if (var2 != null) {
         var1.g((fm)var2);
      }

   }

   public void f() {
      int var1 = this.R;

      int var2;
      int var3;
      for(var3 = 0; var3 < this.o.size(); ++var3) {
         View var4 = (View)this.o.get(var3);
         if (((String)this.p.get(var3)).equals("label")) {
            var2 = this.a((TextView)var4);
         } else {
            var2 = 0;
         }

         if (var4 instanceof fn) {
            var2 = ((fn)var4).getW();
         }

         if (var4 instanceof fm) {
            var2 = ((fm)var4).getWidthSum();
         }

         var1 += var2 + this.h;
      }

      label57: {
         if (!this.w.equals("b") && !this.w.equals("d") && !this.w.equals("s")) {
            var2 = var1;
            if (!this.w.equals("a")) {
               break label57;
            }
         }

         var2 = Math.max(var1, this.d);
      }

      label50: {
         if (!this.w.equals(" ") && !this.w.equals("")) {
            var3 = var2;
            if (!this.w.equals("o")) {
               break label50;
            }
         }

         var3 = Math.max(var2, this.e);
      }

      label44: {
         if (!this.w.equals("c")) {
            var1 = var3;
            if (!this.w.equals("e")) {
               break label44;
            }
         }

         var1 = Math.max(var3, this.g);
      }

      var2 = var1;
      if (this.w.equals("h")) {
         var2 = Math.max(var1, this.f);
      }

      var1 = var2;
      if (this.al != null) {
         var1 = Math.max(var2, this.R + this.al.getWidth() + 2);
      }

      this.a((float)(var1 + this.S), (float)(this.P + this.ae + this.q * this.T * 2 + this.Q), false);
   }

   public void f(fm var1) {
      if (this.l == (Integer)var1.getTag()) {
         this.l = -1;
      }

      if (this.m == (Integer)var1.getTag()) {
         this.m = -1;
      }

      if (this.n == (Integer)var1.getTag()) {
         this.n = -1;
      }

      if (var1.j) {
         int var2 = this.o.indexOf(var1);
         if (var2 < 0) {
            return;
         }

         var1.t = "";
         var1.u = "";
         View var3 = this.b((String)this.p.get(var2), this.z);
         if (var3 instanceof fo) {
            ((fo)var3).ac = this;
         }

         this.o.set(var2, var3);
         this.addView(var3);
         this.p();
         this.e();
      }

      this.c().b();
   }

   public void g() {
      fm var1 = this;

      while(true) {
         var1.f();
         if (var1.ac == null) {
            return;
         }

         var1 = var1.ac;
      }
   }

   public ArrayList<fm> getAllChildren() {
      ArrayList var2 = new ArrayList();
      fm var1 = this;

      while(true) {
         var2.add(var1);
         Iterator var3 = var1.o.iterator();

         while(var3.hasNext()) {
            View var4 = (View)var3.next();
            if (var4 instanceof fm) {
               var2.addAll(((fm)var4).getAllChildren());
            }
         }

         fm var5;
         if (var1.i() && var1.m != -1) {
            var5 = (fm)this.s.findViewWithTag(var1.m);
            var2.addAll(var5.getAllChildren());
         }

         if (var1.j() && var1.n != -1) {
            var5 = (fm)this.s.findViewWithTag(var1.n);
            var2.addAll(var5.getAllChildren());
         }

         if (var1.l == -1) {
            return var2;
         }

         var1 = (fm)this.s.findViewWithTag(var1.l);
      }
   }

   public BlockBean getBean() {
      BlockBean var5 = new BlockBean(this.getTag().toString(), this.a, this.w, this.x, this.b);
      var5.color = this.z;
      Iterator var3 = this.c.iterator();

      while(var3.hasNext()) {
         View var2 = (View)var3.next();
         if (var2 instanceof fn) {
            var5.parameters.add(((fn)var2).getArgValue().toString());
         } else if (var2 instanceof fm) {
            ArrayList var1 = var5.parameters;
            StringBuilder var4 = new StringBuilder();
            var4.append("@");
            var4.append(var2.getTag().toString());
            var1.add(var4.toString());
         }
      }

      var5.subStack1 = this.m;
      var5.subStack2 = this.n;
      var5.nextBlock = this.l;
      return var5;
   }

   public int getBlockType() {
      return this.r;
   }

   public int getDepth() {
      fm var2 = this;

      int var1;
      for(var1 = 0; var2.ac != null; ++var1) {
         var2 = var2.ac;
      }

      return var1;
   }

   public int getHeightSum() {
      int var1 = 0;
      fm var3 = this;

      while(true) {
         int var2 = var1;
         if (var1 > 0) {
            var2 = var1 - this.C;
         }

         var1 = var2 + var3.getTotalHeight();
         if (var3.l == -1) {
            return var1;
         }

         var3 = (fm)this.s.findViewWithTag(var3.l);
      }
   }

   public int getWidthSum() {
      int var2 = 0;
      fm var3 = this;

      while(true) {
         var2 = Math.max(var2, var3.getW());
         int var1 = var2;
         if (var3.i()) {
            var1 = var2;
            if (var3.m != -1) {
               var1 = Math.max(var2, this.E + ((fm)this.s.findViewWithTag(var3.m)).getWidthSum());
            }
         }

         var2 = var1;
         if (var3.j()) {
            var2 = var1;
            if (var3.n != -1) {
               var2 = Math.max(var1, this.E + ((fm)this.s.findViewWithTag(var3.n)).getWidthSum());
            }
         }

         if (var3.l == -1) {
            return var2;
         }

         var3 = (fm)this.s.findViewWithTag(var3.l);
      }
   }

   public void setBlockType(int var1) {
      this.r = var1;
   }

   public void setSpec(String var1) {
      this.a = var1;
      this.removeAllViews();
      this.a(this.a, this.z);
      Iterator var2 = this.o.iterator();

      while(var2.hasNext()) {
         View var3 = (View)var2.next();
         this.addView(var3);
      }

      this.p();
      if (this.w.equals("e")) {
         this.al = this.a(kq.a().a(this.getContext(), "else"));
         this.addView(this.al);
      }

      this.b();
   }
}
