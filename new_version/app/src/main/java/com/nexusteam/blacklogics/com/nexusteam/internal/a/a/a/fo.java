/* Decompiler 264ms, total 1679ms, lines 875 */
package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;

public class fo extends RelativeLayout {
   public Paint A;
   public boolean B;
   public int C;
   public int D;
   public int E;
   public int F;
   public int G;
   public int H;
   public int I;
   public int J;
   public int K;
   public int L;
   public int M;
   protected int N;
   protected int O;
   protected int P;
   protected int Q;
   protected int R;
   protected int S;
   protected int T;
   protected int U;
   protected int V;
   protected int W;
   private boolean a;
   protected int aa;
   protected float ab;
   public fm ac;
   public int ad;
   public int ae;
   public int af;
   public int ag;
   public int ah;
   public Paint ai;
   public Paint aj;
   public Paint ak;
   private boolean b;
   private int c;
   private int d;
   private int e;
   private hc f;
   protected Context v;
   public String w;
   public String x;
   public int y;
   public int z;

   public fo(Context var1, String var2, String var3, boolean var4) {
      super(var1);
      this.C = 3;
      this.D = 12;
      this.E = 15;
      this.F = 3;
      this.G = 2;
      this.H = 15;
      this.I = 15;
      this.J = 15;
      this.K = this.J + this.C;
      this.L = this.K + 10;
      this.M = this.L + this.C;
      this.N = 6;
      this.O = 60;
      this.P = 2;
      this.Q = 2;
      this.R = 3;
      this.S = 0;
      this.T = 2;
      this.W = this.D;
      this.aa = this.D;
      this.ac = null;
      this.ad = 100;
      this.ae = 14;
      this.af = 15;
      this.ag = 6;
      this.ah = 4;
      this.a = false;
      this.b = false;
      this.c = 1;
      this.d = 1;
      this.e = 805306368;
      this.f = null;
      this.v = var1;
      this.w = var2;
      if (var3 != null && var3.indexOf(".") > 0) {
         this.x = var3.substring(0, var3.indexOf("."));
      } else {
         this.x = var3;
      }

      byte var6;
      label86: {
         this.h();
         var2 = this.w;
         int var5 = var2.hashCode();
         if (var5 != 32) {
            if (var5 != 104) {
               if (var5 != 112) {
                  if (var5 != 115) {
                     if (var5 != 118) {
                        switch(var5) {
                        case 97:
                           if (var2.equals("a")) {
                              var6 = 13;
                              break label86;
                           }
                           break;
                        case 98:
                           if (var2.equals("b")) {
                              var6 = 1;
                              break label86;
                           }
                           break;
                        case 99:
                           if (var2.equals("c")) {
                              var6 = 4;
                              break label86;
                           }
                           break;
                        case 100:
                           if (var2.equals("d")) {
                              var6 = 2;
                              break label86;
                           }
                           break;
                        case 101:
                           if (var2.equals("e")) {
                              var6 = 5;
                              break label86;
                           }
                           break;
                        case 102:
                           if (var2.equals("f")) {
                              var6 = 6;
                              break label86;
                           }
                           break;
                        default:
                           switch(var5) {
                           case 108:
                              if (var2.equals("l")) {
                                 var6 = 12;
                                 break label86;
                              }
                              break;
                           case 109:
                              if (var2.equals("m")) {
                                 var6 = 8;
                                 break label86;
                              }
                              break;
                           case 110:
                              if (var2.equals("n")) {
                                 var6 = 3;
                                 break label86;
                              }
                           }
                        }
                     } else if (var2.equals("v")) {
                        var6 = 10;
                        break label86;
                     }
                  } else if (var2.equals("s")) {
                     var6 = 9;
                     break label86;
                  }
               } else if (var2.equals("p")) {
                  var6 = 11;
                  break label86;
               }
            } else if (var2.equals("h")) {
               var6 = 7;
               break label86;
            }
         } else if (var2.equals(" ")) {
            var6 = 0;
            break label86;
         }

         var6 = -1;
      }

      switch(var6) {
      case 0:
         this.P = 4;
         this.y = 4;
         break;
      case 1:
         this.R = 8;
         this.S = 5;
         this.y = 2;
         break;
      case 2:
         this.y = 3;
         this.R = 4;
         break;
      case 3:
         this.y = 3;
         break;
      case 4:
         this.P = 4;
         this.y = 10;
         break;
      case 5:
         this.P = 4;
         this.y = 12;
         break;
      case 6:
         this.P = 4;
         this.y = 5;
         break;
      case 7:
         this.P = 8;
         this.y = 7;
         break;
      case 8:
         this.y = 9;
         break;
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
         this.y = 1;
      }

      this.z = this.e;
      this.B = var4;
      this.setWillNotDraw(false);
      this.a(var1);
   }

   public fo(Context var1, String var2, boolean var3) {
      this(var1, var2, "", var3);
   }

   private void a(Context var1) {
      this.ab = kp.a(var1, 1.0F);
      this.C = (int)((float)this.C * this.ab);
      this.D = (int)((float)this.D * this.ab);
      this.E = (int)((float)this.E * this.ab);
      this.H = (int)((float)this.H * this.ab);
      this.I = (int)((float)this.I * this.ab);
      this.F = (int)((float)this.F * this.ab);
      this.G = (int)((float)this.G * this.ab);
      this.J = (int)((float)this.J * this.ab);
      this.K = (int)((float)this.K * this.ab);
      this.L = (int)((float)this.L * this.ab);
      this.M = (int)((float)this.M * this.ab);
      this.N = (int)((float)this.N * this.ab);
      this.O = (int)((float)this.O * this.ab);
      this.W = (int)((float)this.W * this.ab);
      this.aa = (int)((float)this.aa * this.ab);
      this.R = (int)((float)this.R * this.ab);
      this.P = (int)((float)this.P * this.ab);
      this.S = (int)((float)this.S * this.ab);
      this.Q = (int)((float)this.Q * this.ab);
      this.T = (int)((float)this.T * this.ab);
      this.ad = (int)((float)this.ad * this.ab);
      this.ae = (int)((float)this.ae * this.ab);
      this.ag = (int)((float)this.ag * this.ab);
      this.ah = (int)((float)this.ah * this.ab);
      this.af = (int)((float)this.af * this.ab);
      this.c = (int)((float)this.c * this.ab);
      this.d = (int)((float)this.d * this.ab);
      if (this.c < 2) {
         this.c = 2;
      }

      if (this.d < 2) {
         this.d = 2;
      }

      this.A = new Paint();
      if (!this.B) {
         this.a = true;
         this.b = true;
      }

      this.ai = new Paint();
      this.ai.setColor(-536870912);
      this.ai.setStrokeWidth((float)this.c);
      this.aj = new Paint();
      this.aj.setColor(-1610612736);
      this.aj.setStyle(Style.STROKE);
      this.aj.setStrokeWidth((float)this.c);
      this.ak = new Paint();
      this.ak.setColor(-1593835521);
      this.ak.setStyle(Style.STROKE);
      this.ak.setStrokeWidth((float)this.d);
      this.setLayerType(1, (Paint)null);
      this.a((float)this.ad, (float)(this.ae + this.P + this.Q), false);
   }

   private void a(Canvas var1) {
      var1.drawRect(new Rect(0, 0, this.U, this.V), this.A);
      if (this.a) {
         var1.drawLines(this.getRectShadows(), this.aj);
      }

      if (this.b) {
         var1.drawLines(this.getRectReflections(), this.ak);
      }

   }

   private void a(Path var1) {
      var1.moveTo(0.0F, (float)this.F);
      var1.lineTo((float)this.F, 0.0F);
      var1.lineTo((float)this.J, 0.0F);
      var1.lineTo((float)this.K, (float)this.C);
      var1.lineTo((float)this.L, (float)this.C);
      var1.lineTo((float)this.M, 0.0F);
      var1.lineTo((float)(this.U - this.F), 0.0F);
      var1.lineTo((float)this.U, (float)this.F);
   }

   private void a(Path var1, int var2) {
      var1.lineTo((float)this.E, (float)(var2 - this.G));
      float var3 = (float)(this.E + this.G);
      float var4 = (float)var2;
      var1.lineTo(var3, var4);
      var1.lineTo((float)(this.U - this.F), var4);
      var1.lineTo((float)this.U, (float)(var2 + this.F));
   }

   private void a(Path var1, int var2, boolean var3, int var4) {
      var1.lineTo((float)this.U, (float)(var2 - this.F));
      float var5 = (float)(this.U - this.F);
      float var6 = (float)var2;
      var1.lineTo(var5, var6);
      if (var3) {
         var1.lineTo((float)(this.M + var4), var6);
         var1.lineTo((float)(this.L + var4), (float)(this.C + var2));
         var1.lineTo((float)(this.K + var4), (float)(this.C + var2));
         var1.lineTo((float)(this.J + var4), var6);
      }

      if (var4 > 0) {
         var1.lineTo((float)(this.G + var4), var6);
         var5 = (float)var4;
         var1.lineTo(var5, (float)(var2 + this.G));
      } else {
         var1.lineTo((float)(var4 + this.F), var6);
         var1.lineTo(0.0F, (float)(var2 - this.F));
      }

   }

   private float[] a(int var1) {
      float var10 = (float)(this.c / 2 + 0);
      float var20 = (float)(var1 - this.F);
      float var2 = (float)(this.c / 2 + 0);
      float var9 = (float)this.F;
      float var18 = (float)(this.c / 2 + 0);
      float var8 = (float)this.F;
      float var22 = (float)this.F;
      float var12 = (float)(this.c / 2 + 0);
      float var4 = (float)this.F;
      float var3 = (float)(this.c / 2 + 0);
      float var21 = (float)this.J;
      float var17 = (float)(this.c / 2 + 0);
      float var15 = (float)this.K;
      float var14 = (float)(this.C + this.c / 2);
      float var5 = (float)this.L;
      float var6 = (float)(this.C + this.c / 2);
      float var16 = (float)this.L;
      float var23 = (float)(this.C + this.c / 2);
      float var19 = (float)this.M;
      float var7 = (float)(this.c / 2 + 0);
      float var13 = (float)this.M;
      float var24 = (float)(this.c / 2 + 0);
      float var11 = (float)(this.U - this.F);
      return new float[]{var10, var20, var2, var9, var18, var8, var22, var12, var4, var3, var21, var17, var15, var14, var5, var6, var16, var23, var19, var7, var13, var24, var11, (float)(this.c / 2 + 0)};
   }

   private float[] a(int var1, int var2) {
      float var3 = (float)(this.E + this.G);
      float var4 = (float)(var1 - this.c / 2);
      float var8 = (float)(this.E - this.c / 2);
      float var9 = (float)(this.G + var1);
      float var6 = (float)(this.E - this.c / 2);
      float var7 = (float)(var1 + this.G);
      float var5 = (float)(this.E - this.c / 2);
      return new float[]{var3, var4, var8, var9, var6, var7, var5, (float)(var2 - this.G)};
   }

   private float[] a(int var1, boolean var2, int var3) {
      float[] var4;
      if (var2) {
         var4 = new float[24];
      } else {
         var4 = new float[8];
      }

      var4[0] = (float)this.U;
      var4[1] = (float)(var1 - this.F - this.c / 2);
      var4[2] = (float)(this.U - this.F);
      var4[3] = (float)(var1 - this.c / 2);
      if (var2) {
         var4[4] = (float)(this.U - this.F);
         var4[5] = (float)(var1 - this.c / 2);
         var4[6] = (float)(this.M + var3);
         var4[7] = (float)(var1 - this.c / 2);
         var4[8] = (float)(this.M + var3);
         var4[9] = (float)(var1 - this.c / 2);
         var4[10] = (float)(this.L + var3);
         var4[11] = (float)(this.C + var1 - this.c / 2);
         var4[12] = (float)(this.L + var3);
         var4[13] = (float)(this.C + var1 - this.c / 2);
         var4[14] = (float)(this.K + var3);
         var4[15] = (float)(this.C + var1 - this.c / 2);
         var4[16] = (float)(this.K + var3);
         var4[17] = (float)(this.C + var1 - this.c / 2);
         var4[18] = (float)(this.J + var3);
         var4[19] = (float)(var1 - this.c / 2);
         if (var3 > 0) {
            var4[20] = (float)(this.J + var3);
            var4[21] = (float)(var1 - this.c / 2);
            var4[22] = (float)(var3 + this.G);
            var4[23] = (float)(var1 - this.c / 2);
         } else {
            var4[20] = (float)(this.J + var3);
            var4[21] = (float)(var1 - this.c / 2);
            var4[22] = (float)(var3 + this.F);
            var4[23] = (float)(var1 - this.c / 2);
         }
      } else if (var3 > 0) {
         var4[4] = (float)(this.U - this.F);
         var4[5] = (float)(var1 - this.c / 2);
         var4[6] = (float)(var3 + this.G);
         var4[7] = (float)(var1 - this.c / 2);
      } else {
         var4[4] = (float)(this.U - this.F);
         var4[5] = (float)(var1 - this.c / 2);
         var4[6] = (float)(var3 + this.F);
         var4[7] = (float)(var1 - this.c / 2);
      }

      return var4;
   }

   private void b(Canvas var1) {
      var1.drawRect(new Rect(0, 0, this.U, this.V), this.A);
      Path var2 = new Path();
      var2.moveTo((float)(this.U - this.ah), (float)this.ah);
      var2.lineTo((float)(this.U - this.ah - this.ag / 2), (float)(this.ah + this.ag));
      var2.lineTo((float)(this.U - this.ah - this.ag), (float)this.ah);
      var1.drawPath(var2, this.ai);
   }

   private float[] b(int var1, int var2) {
      float var5 = (float)(this.U - this.c / 2);
      float var3 = (float)(var1 + this.F);
      float var4 = (float)(this.U - this.c / 2);
      return new float[]{var5, var3, var4, (float)(var2 - this.F)};
   }

   private void c(Canvas var1) {
      Path var4 = new Path();
      int var3 = this.V / 2;
      float var2 = (float)var3;
      var4.moveTo(var2, (float)this.V);
      var4.lineTo(0.0F, var2);
      var4.lineTo(var2, 0.0F);
      var4.lineTo((float)(this.U - var3), 0.0F);
      var4.lineTo((float)this.U, var2);
      var4.lineTo((float)(this.U - var3), (float)this.V);
      var1.drawPath(var4, this.A);
      if (this.a) {
         var1.drawLines(this.getBooleanShadows(), this.aj);
      }

      if (this.b) {
         var1.drawLines(this.getBooleanReflections(), this.ak);
      }

   }

   private float[] c(int var1, int var2) {
      float var3 = (float)(var2 + this.G);
      float var5 = (float)(this.c / 2 + var1);
      float var4 = (float)(this.U - this.F);
      return new float[]{var3, var5, var4, (float)(var1 + this.c / 2)};
   }

   private void d(Canvas var1) {
      Path var4 = new Path();
      int var3 = this.V / 2;
      float var2 = (float)var3;
      var4.moveTo(var2, (float)this.V);
      var4.arcTo(new RectF(0.0F, 0.0F, (float)this.V, (float)this.V), 90.0F, 180.0F);
      var4.lineTo((float)(this.U - var3), 0.0F);
      var4.arcTo(new RectF((float)(this.U - this.V), 0.0F, (float)this.U, (float)this.V), 270.0F, 180.0F);
      var1.drawPath(var4, this.A);
      if (this.a) {
         var1.drawArc(new RectF((float)(this.U - this.V), 0.0F, (float)(this.U - this.c / 2), (float)(this.V - this.c / 2)), 330.0F, 120.0F, false, this.aj);
         var1.drawLines(this.getNumberBottomShadows(), this.aj);
         var1.drawArc(new RectF((float)(this.c / 2 + 0), 0.0F, (float)this.V, (float)(this.V - this.c / 2)), 90.0F, 30.0F, false, this.aj);
      }

      if (this.b) {
         var1.drawArc(new RectF((float)(this.c / 2 + 0), (float)(this.c / 2 + 0), (float)this.V, (float)this.V), 150.0F, 120.0F, false, this.ak);
         var1.drawLines(this.getNumberTopReflections(), this.ak);
         var1.drawArc(new RectF((float)(this.U - this.V), (float)(this.c / 2 + 0), (float)(this.U - this.c / 2), (float)this.V), 270.0F, 30.0F, false, this.ak);
      }

   }

   private void e(Canvas var1) {
      Path var6 = new Path();
      this.a(var6);
      int var2 = this.V;
      int var3 = this.y;
      boolean var5 = true;
      boolean var4;
      if (var3 != 5) {
         var4 = true;
      } else {
         var4 = false;
      }

      this.a(var6, var2, var4, 0);
      var1.drawPath(var6, this.A);
      if (this.a) {
         var1.drawLines(this.b(0, this.V), this.aj);
         var2 = this.V;
         if (this.y != 5) {
            var4 = var5;
         } else {
            var4 = false;
         }

         var1.drawLines(this.a(var2, var4, 0), this.aj);
      }

      if (this.b) {
         var1.drawLines(this.a(this.V), this.ak);
      }

   }

   private void f(Canvas var1) {
      Path var2 = new Path();
      var2.moveTo(0.0F, (float)this.N);
      var2.arcTo(new RectF(0.0F, 0.0F, (float)this.O, (float)(this.N * 2)), 180.0F, 180.0F);
      var2.lineTo((float)(this.U - this.F), (float)this.N);
      var2.lineTo((float)this.U, (float)(this.N + this.F));
      this.a(var2, this.V, true, 0);
      var1.drawPath(var2, this.A);
      if (this.a) {
         var1.drawLines(this.b(this.N, this.V), this.aj);
         var1.drawLines(this.a(this.V, true, 0), this.aj);
      }

   }

   private void g(Canvas var1) {
      Path var7 = new Path();
      int var2 = this.V + this.W - this.C;
      this.a(var7);
      int var3 = this.V;
      int var4 = this.E;
      boolean var6 = true;
      this.a(var7, var3, true, var4);
      this.a(var7, var2);
      var3 = this.H;
      boolean var5;
      if (this.y == 10) {
         var5 = true;
      } else {
         var5 = false;
      }

      this.a(var7, var3 + var2, var5, 0);
      var1.drawPath(var7, this.A);
      if (this.a) {
         var1.drawLines(this.b(0, this.V), this.aj);
         var1.drawLines(this.a(this.V, true, this.E), this.aj);
         var1.drawLines(this.a(this.V, var2), this.aj);
         var1.drawLines(this.b(var2, this.H + var2), this.aj);
         var3 = this.H;
         if (this.y == 10) {
            var5 = var6;
         } else {
            var5 = false;
         }

         var1.drawLines(this.a(var3 + var2, var5, 0), this.aj);
      }

      if (this.b) {
         var1.drawLines(this.a(this.H + var2), this.ak);
         var1.drawLines(this.c(var2, this.E), this.ak);
      }

   }

   private float[] getBooleanReflections() {
      int var6 = this.V / 2;
      float var5 = (float)(this.c / 2 + 0);
      float var4 = (float)var6;
      float var1 = (float)(this.c / 2 + 0);
      float var2 = (float)(this.c / 2 + 0);
      float var3 = (float)(this.U - var6);
      return new float[]{var5, var4, var4, var1, var4, var2, var3, (float)(this.c / 2 + 0)};
   }

   private float[] getBooleanShadows() {
      int var7 = this.V / 2;
      float var6 = (float)(this.U - this.c / 2);
      float var1 = (float)var7;
      float var3 = (float)(this.U - var7);
      float var5 = (float)(this.V - this.c / 2);
      float var4 = (float)(this.U - var7);
      float var2 = (float)(this.V - this.c / 2);
      return new float[]{var6, var1, var3, var5, var4, var2, var1, (float)(this.V - this.c / 2)};
   }

   private float[] getNumberBottomShadows() {
      int var4 = this.V / 2;
      float var1 = (float)(this.U - var4);
      float var3 = (float)(this.V - this.c / 2);
      float var2 = (float)var4;
      return new float[]{var1, var3, var2, (float)(this.V - this.c / 2)};
   }

   private float[] getNumberTopReflections() {
      int var4 = this.V / 2;
      float var2 = (float)var4;
      float var3 = (float)(this.c / 2 + 0);
      float var1 = (float)(this.U - var4);
      return new float[]{var2, var3, var1, (float)(this.c / 2 + 0)};
   }

   private float[] getRectReflections() {
      float var5 = (float)(this.c / 2 + 0);
      float var3 = (float)(this.U - this.c / 2);
      float var4 = (float)(this.c / 2 + 0);
      float var2 = (float)(this.c / 2 + 0);
      float var1 = (float)(this.c / 2 + 0);
      return new float[]{0.0F, var5, var3, var4, var2, 0.0F, var1, (float)(this.V - this.c / 2)};
   }

   private float[] getRectShadows() {
      float var2 = (float)(this.U - this.c / 2);
      float var4 = (float)(this.U - this.c / 2);
      float var5 = (float)(this.V - this.c / 2);
      float var1 = (float)(this.U - this.c / 2);
      float var3 = (float)(this.V - this.c / 2);
      return new float[]{var2, 0.0F, var4, var5, var1, var3, 0.0F, (float)(this.V - this.c / 2)};
   }

   private void h(Canvas var1) {
      Path var4 = new Path();
      int var3 = this.V + this.W - this.C;
      int var2 = this.I + var3 + this.aa - this.C;
      this.a(var4);
      this.a(var4, this.V, true, this.E);
      this.a(var4, var3);
      this.a(var4, this.I + var3, true, this.E);
      this.a(var4, var2);
      this.a(var4, this.H + var2, true, 0);
      var1.drawPath(var4, this.A);
      if (this.a) {
         var1.drawLines(this.b(0, this.V), this.aj);
         var1.drawLines(this.a(this.V, true, this.E), this.aj);
         var1.drawLines(this.a(this.V, var3), this.aj);
         var1.drawLines(this.b(var3, this.I + var3), this.aj);
         var1.drawLines(this.a(this.I + var3, true, this.E), this.aj);
         var1.drawLines(this.a(this.I + var3, var2), this.aj);
         var1.drawLines(this.b(var2, this.H + var2), this.aj);
         var1.drawLines(this.a(this.H + var2, true, 0), this.aj);
      }

      if (this.b) {
         var1.drawLines(this.a(this.H + var2), this.ak);
         var1.drawLines(this.c(var3, this.E), this.ak);
         var1.drawLines(this.c(var2, this.E), this.ak);
      }

   }

   public void a(float var1, float var2, boolean var3) {
      int var4;
      if (this.y == 9) {
         var4 = (int)var1;
         this.U = var4 + this.af;
      } else {
         var4 = (int)var1;
         this.U = var4;
      }

      var4 = (int)var2;
      this.V = var4;
      if (var3) {
         this.n();
      }

   }

   public void a(fo var1, boolean var2, boolean var3, int var4) {
      this.z = -16777216;
      this.y = var1.y;
      this.U = var1.U;
      this.V = var1.V;
      this.W = var1.W;
      this.aa = var1.aa;
      if (!var2) {
         if (var3) {
            this.y = 4;
            this.V = (int)(this.ab * 6.0F);
         } else if (var4 > 0) {
            this.W = var4 - this.C;
         }
      }

      this.n();
   }

   public hc getClassInfo() {
      if (this.f == null) {
         this.h();
      }

      return this.f;
   }

   public int getTopH() {
      return this.V;
   }

   public int getTotalHeight() {
      int var2 = this.V;
      int var1 = var2;
      if (this.i()) {
         var1 = var2 + (this.I + this.W - this.C);
      }

      var2 = var1;
      if (this.j()) {
         var2 = var1 + (this.H + this.aa - this.C);
      }

      if (this.y != 4 && this.y != 7 && this.y != 10) {
         var1 = var2;
         if (this.y != 12) {
            return var1;
         }
      }

      var1 = var2 + this.C;
      return var1;
   }

   public int getTotalWidth() {
      return this.U;
   }

   public int getW() {
      return this.U;
   }

   protected void h() {
      this.f = ev.a(this.w, this.x);
   }

   public boolean i() {
      boolean var1;
      if (this.y >= 10) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean j() {
      boolean var1;
      if (this.y == 12) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int k() {
      return this.V;
   }

   public int l() {
      return this.V + this.W + this.I - this.C;
   }

   public int m() {
      return this.getTotalHeight() - this.C;
   }

   public void n() {
      this.requestLayout();
   }

   protected void onDraw(Canvas var1) {
      this.A.setColor(this.z);
      switch(this.y) {
      case 1:
         this.a(var1);
         break;
      case 2:
         this.c(var1);
         break;
      case 3:
         this.d(var1);
         break;
      case 4:
      case 5:
         this.e(var1);
      case 6:
      case 8:
      default:
         break;
      case 7:
         this.f(var1);
         break;
      case 9:
         this.b(var1);
         break;
      case 10:
      case 11:
         this.g(var1);
         break;
      case 12:
         this.h(var1);
      }

      super.onDraw(var1);
   }

   protected void onMeasure(int var1, int var2) {
      var2 = MeasureSpec.makeMeasureSpec(this.getTotalWidth(), 1073741824);
      var1 = MeasureSpec.makeMeasureSpec(this.getTotalHeight(), 1073741824);
      super.onMeasure(var2, var1);
   }

   public void setSubstack1Height(int var1) {
      var1 = Math.max(var1, this.D);
      if (var1 != this.W) {
         this.W = var1;
      }

   }

   public void setSubstack2Height(int var1) {
      var1 = Math.max(var1, this.D);
      if (var1 != this.aa) {
         this.aa = var1;
      }

   }
}
