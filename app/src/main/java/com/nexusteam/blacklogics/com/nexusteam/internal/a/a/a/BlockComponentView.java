/* Decompiler 58ms, total 1451ms, lines 230 */
package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import com.nexusteam.internal.beans.BlockBean;
import java.util.ArrayList;


public class BlockComponentView extends fm {
   private ArrayList<BlockBean> al;
   private TextView am;

   public BlockComponentView(Context var1, String var2, String var3, String var4, String var5, ArrayList<BlockBean> var6) {
      super(var1, -1, var5, var2, var3, var4);
      this.al = var6;
      this.r = 2;
   }

   private TextView a(String var1) {
      TextView var3 = new TextView(this.v);
      String var2 = var1;
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

   private int[] a(TextView var1) {
      Rect var2 = new Rect();
      TextPaint var3 = var1.getPaint();
      var3.getTextBounds(var1.getText().toString(), 0, var1.getText().length(), var2);
      return new int[]{var2.width(), var2.height()};
   }

   protected void a() {
      byte var1;
      label69: {
         var1 = 0;
         this.setDrawingCacheEnabled(false);
         this.d = (int)((float)this.d * this.ab);
         this.e = (int)((float)this.e * this.ab);
         this.f = (int)((float)this.f * this.ab);
         this.g = (int)((float)this.g * this.ab);
         this.h = (int)((float)this.h * this.ab);
         String var3 = this.w;
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
                                 break label69;
                              }
                              break;
                           case 98:
                              if (var3.equals("b")) {
                                 var1 = 1;
                                 break label69;
                              }
                              break;
                           case 99:
                              if (var3.equals("c")) {
                                 var1 = 8;
                                 break label69;
                              }
                              break;
                           case 100:
                              if (var3.equals("d")) {
                                 var1 = 3;
                                 break label69;
                              }
                              break;
                           case 101:
                              if (var3.equals("e")) {
                                 var1 = 9;
                                 break label69;
                              }
                              break;
                           case 102:
                              if (var3.equals("f")) {
                                 var1 = 10;
                                 break label69;
                              }
                           }
                        } else if (var3.equals("v")) {
                           var1 = 4;
                           break label69;
                        }
                     } else if (var3.equals("s")) {
                        var1 = 2;
                        break label69;
                     }
                  } else if (var3.equals("p")) {
                     var1 = 5;
                     break label69;
                  }
               } else if (var3.equals("l")) {
                  var1 = 6;
                  break label69;
               }
            } else if (var3.equals("h")) {
               var1 = 11;
               break label69;
            }
         } else if (var3.equals(" ")) {
            break label69;
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

      this.am = this.a(this.a);
      this.addView(this.am);
      this.z = this.getResources().getColor(com.nexusteam.blacklogics.R.color.scolor_red_02);
      this.b();
   }

   public void b() {
      int var4 = this.R;
      int var3 = this.P;
      this.am.setX((float)var4);
      this.am.setY((float)var3);
      int[] var9 = this.a(this.am);
      var3 = var9[0];
      int var5 = var9[1];
      var4 = this.R + var3 + this.S;
      int var6 = this.P;
      int var8 = this.ae;
      int var7 = this.Q;
      var3 = var4;
      float var1;
      if (this.x != null) {
         var3 = var4;
         if (this.x.length() > 0) {
            var1 = (float)var4;
            var3 = (int)(var1 + this.ab * 8.0F);
         }
      }

      label38: {
         if (!this.w.equals("b") && !this.w.equals("d") && !this.w.equals("s")) {
            var4 = var3;
            if (!this.w.equals("a")) {
               break label38;
            }
         }

         var4 = Math.max(var3, this.d);
      }

      label31: {
         if (!this.w.equals(" ") && !this.w.equals("")) {
            var3 = var4;
            if (!this.w.equals("o")) {
               break label31;
            }
         }

         var3 = Math.max(var4, this.e);
      }

      label25: {
         if (!this.w.equals("c")) {
            var4 = var3;
            if (!this.w.equals("e")) {
               break label25;
            }
         }

         var4 = Math.max(var3, this.g);
      }

      var3 = Math.max(var6 + var8 + var7, this.P + var5 + this.Q);
      float var2 = (float)var4;
      var1 = (float)var3;
      this.a(var2, var1, true);
   }

   public ArrayList<BlockBean> getData() {
      return this.al;
   }
}
