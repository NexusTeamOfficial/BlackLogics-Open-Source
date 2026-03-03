/* Decompiler 427ms, total 1335ms, lines 1031 */
package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.RelativeLayout;
import com.nexusteam.internal.beans.BlockBean;
import java.util.ArrayList;
import java.util.Iterator;

public class fq extends RelativeLayout {
   public int a = 10;
   float b = kp.a(this.getContext(), 1.0F);
   private Context c;
   private int[] d = new int[2];
   private fo e;
   private fm f;
   private ArrayList<Object[]> g = new ArrayList();
   private Object[] h = null;

   public fq(Context var1) {
      super(var1);
      this.a(var1);
   }

   private void a(fm var1, String var2) {
      while(true) {
         if (var1 != null) {
            if (!var1.i) {
               for(int var3 = 0; var3 < var1.c.size(); ++var3) {
                  View var5 = (View)var1.c.get(var3);
                  boolean var4 = var5 instanceof fm;
                  if ((var4 || var5 instanceof fn) && (!var4 || !var5.getTag().toString().equals(var2))) {
                     int[] var6 = new int[2];
                     var5.getLocationOnScreen(var6);
                     this.a(var6, var5, 0);
                     if (var4) {
                        this.a((fm)var5, var2);
                     }
                  }
               }
            }

            if (var1.m != -1) {
               this.a((fm)this.findViewWithTag(var1.m), var2);
            }

            if (var1.n != -1) {
               this.a((fm)this.findViewWithTag(var1.n), var2);
            }

            if (var1.l != -1) {
               var1 = (fm)this.findViewWithTag(var1.l);
               continue;
            }
         }

         return;
      }
   }

   private void a(fm var1, boolean var2) {
      while(true) {
         if (var1.getVisibility() != 8) {
            int[] var3;
            if (!var1.k && (!var2 || -1 == var1.l)) {
               var3 = new int[2];
               var1.getLocationOnScreen(var3);
               var3[1] += var1.m();
               this.a(var3, var1, 0);
            }

            if (var1.i() && (!var2 || var1.m == -1)) {
               var3 = new int[2];
               var1.getLocationOnScreen(var3);
               var3[0] += var1.E;
               var3[1] += var1.k();
               this.a(var3, var1, 2);
            }

            if (var1.j() && (!var2 || var1.n == -1)) {
               var3 = new int[2];
               var1.getLocationOnScreen(var3);
               var3[0] += var1.E;
               var3[1] += var1.l();
               this.a(var3, var1, 3);
            }

            if (var1.m != -1) {
               this.a((fm)this.findViewWithTag(var1.m), var2);
            }

            if (var1.n != -1) {
               this.a((fm)this.findViewWithTag(var1.n), var2);
            }

            if (var1.l != -1) {
               var1 = (fm)this.findViewWithTag(var1.l);
               continue;
            }
         }

         return;
      }
   }

   private void a(Context var1) {
      this.c = var1;
      this.d();
   }

   private void a(int[] var1, View var2, int var3) {
      this.g.add(new Object[]{var1, var2, var3});
   }

   private boolean a(fm var1, View var2) {
      if (!var1.j) {
         return true;
      } else {
         if (var2 instanceof fo) {
            hc var4 = var1.getClassInfo();
            if (var4 == null) {
               return false;
            }

            hc var3 = ((fo)var2).getClassInfo();
            if (var3 == null) {
               return false;
            }

            if (var4.a(var3)) {
               return true;
            }

            if (var2 instanceof fm) {
               String var5 = ((fm)var2).u;
               if (var4.a(ev.a(var5))) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private void d() {
      if (this.e == null) {
         this.e = new fo(this.c, " ", true);
      }

      this.e.a(10.0F, 10.0F, false);
      this.addView(this.e);
      this.a();
   }

   public fm a(int var1) {
      return (fm)this.findViewWithTag(var1);
   }

   public fm a(fm var1, int var2, int var3, boolean var4) {
      fm var5;
      if (!var4) {
         var5 = this.c(var1, var2, var3);
      } else {
         var1.setX((float)(var2 - this.d[0] - this.getPaddingLeft()));
         var1.setY((float)(var3 - this.d[1] - this.getPaddingTop()));
         var5 = var1;
      }

      if (this.h == null) {
         var5.c().b();
         this.c();
         return var5;
      } else {
         if (var1.j) {
            ((fo)this.h[1]).ac.a((fo)this.h[1], var5);
         } else {
            var1 = (fm)this.h[1];
            switch((Integer)this.h[2]) {
            case 0:
               var1.a(var5);
               break;
            case 1:
               var1.b(var5);
               break;
            case 2:
               var1.d(var5);
               break;
            case 3:
               var1.e(var5);
               break;
            case 4:
               var1.c(var5);
            }
         }

         var5.c().b();
         this.c();
         return var5;
      }
   }

   public void a() {
      this.e.setVisibility(8);
   }

   public void a(fm var1) {
      boolean var3 = var1.d().k;
      boolean var2;
      if (var1.i() && -1 == var1.m) {
         var2 = true;
      } else {
         var2 = false;
      }

      boolean var4 = var1.j;
      this.a(var1.getTag().toString(), var3, var2, var4, var1.getHeight(), var1.k());
      this.h = null;
   }

   public void a(fm var1, int var2) {
      while(true) {
         if (var1 != null) {
            var1.setVisibility(var2);
            Iterator var3 = var1.c.iterator();

            while(var3.hasNext()) {
               View var4 = (View)var3.next();
               if (var4 instanceof fm) {
                  this.a((fm)var4, var2);
               }
            }

            if (var1.i() && var1.m != -1) {
               this.a((fm)this.findViewWithTag(var1.m), var2);
            }

            if (var1.j() && var1.n != -1) {
               this.a((fm)this.findViewWithTag(var1.n), var2);
            }

            if (var1.l != -1) {
               var1 = (fm)this.findViewWithTag(var1.l);
               continue;
            }
         }

         return;
      }
   }

   public void a(fm var1, int var2, int var3) {
      this.getLocationOnScreen(this.d);
      this.h = this.b(var1, var2, var3);
      boolean var5 = var1.i();
      boolean var4 = true;
      if (var5 && -1 == var1.m && this.h != null) {
         fm var6 = (fm)this.h[1];
         var2 = (Integer)this.h[2];
         if (var2 != 0) {
            switch(var2) {
            case 2:
               var6 = (fm)this.findViewWithTag(var6.m);
               break;
            case 3:
               var6 = (fm)this.findViewWithTag(var6.n);
            case 4:
            }
         } else {
            var6 = (fm)this.findViewWithTag(var6.l);
         }
      }

      if (this.h != null) {
         int[] var7 = (int[])this.h[0];
         View var8 = (View)this.h[1];
         this.e.setX((float)(var7[0] - this.d[0]));
         this.e.setY((float)(var7[1] - this.d[1]));
         this.e.bringToFront();
         this.e.setVisibility(0);
         if (var1.j) {
            if (var8 instanceof fm) {
               this.e.a((fm)var8, true, false, 0);
            }

            if (var8 instanceof fn) {
               this.e.a((fn)var8, true, false, 0);
            }
         } else {
            var3 = (Integer)this.h[2];
            if (var3 == 4) {
               var2 = ((fm)var8).getHeightSum();
            } else {
               var2 = 0;
            }

            if (var3 == 1 || var3 == 4) {
               var4 = false;
            }

            this.e.a(var1, false, var4, var2);
         }
      } else {
         this.a();
      }

   }

   public void a(fm var1, ArrayList<BlockBean> var2) {
      if (var2.size() <= 0) {
         this.a(var1);
      } else {
         BlockBean var7;
         BlockBean var9;
         byte var10;
         label64: {
            var7 = (BlockBean)var2.get(0);
            var9 = (BlockBean)var2.get(var2.size() - 1);
            String var8 = var7.type;
            int var3 = var8.hashCode();
            if (var3 != 100) {
               if (var3 != 108) {
                  if (var3 != 112) {
                     if (var3 != 115) {
                        if (var3 != 118) {
                           switch(var3) {
                           case 97:
                              if (var8.equals("a")) {
                                 var10 = 6;
                                 break label64;
                              }
                              break;
                           case 98:
                              if (var8.equals("b")) {
                                 var10 = 0;
                                 break label64;
                              }
                           }
                        } else if (var8.equals("v")) {
                           var10 = 3;
                           break label64;
                        }
                     } else if (var8.equals("s")) {
                        var10 = 1;
                        break label64;
                     }
                  } else if (var8.equals("p")) {
                     var10 = 4;
                     break label64;
                  }
               } else if (var8.equals("l")) {
                  var10 = 5;
                  break label64;
               }
            } else if (var8.equals("d")) {
               var10 = 2;
               break label64;
            }

            var10 = -1;
         }

         boolean var4;
         switch(var10) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
            var4 = true;
            break;
         default:
            var4 = false;
         }

         boolean var6 = var9.type.equals("f");
         boolean var5;
         if ((var7.type.equals("c") || var7.type.equals("e")) && var7.subStack1 <= 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         this.a(var7.id, var6, var5, var4, var1.getHeight(), var1.k());
         this.h = null;
      }
   }

   public void a(BlockBean var1, boolean var2) {
      if (var1.id != null && !var1.id.equals("") && !var1.id.equals("0")) {
         fm var3 = (fm)this.findViewWithTag(Integer.valueOf(var1.id));
         if (var3 != null) {
            fm var4 = var3.ac;
            if (var3 != var4) {
               this.b(var3);
               this.removeView(var3);
            } else {
               this.removeView(var3);
            }

            if (var2 && var4 != null) {
               var4.c().b();
            }

         }
      }
   }

   public void a(String var1, String var2) {
      this.f = new fm(this.getContext(), 0, var1, "h", var2);
      this.f.s = this;
      this.addView(this.f);
      float var3 = kp.a(this.getContext(), 1.0F);
      fm var4 = this.f;
      var3 *= 8.0F;
      var4.setX(var3);
      this.f.setY(var3);
   }

   public void a(String var1, boolean var2, boolean var3, boolean var4, int var5, int var6) {
      this.g = new ArrayList();
      int var8 = (int)(this.b * 3.0F);

      for(int var7 = 0; var7 < this.getChildCount(); ++var7) {
         View var10 = this.getChildAt(var7);
         if (var10 instanceof fm) {
            fm var12 = (fm)var10;
            if (var12.getVisibility() != 8 && var12.ac == null) {
               if (var4) {
                  this.a(var12, var1);
               } else if (!var12.j) {
                  boolean var9 = true;
                  int[] var11;
                  if (!var2 && !var12.i) {
                     var11 = new int[2];
                     var12.getLocationOnScreen(var11);
                     var11[1] -= var5 - var8;
                     this.a(var11, var12, 1);
                  }

                  if (var3 && !var12.i) {
                     var11 = new int[2];
                     var12.getLocationOnScreen(var11);
                     var11[0] -= var12.E;
                     var11[1] -= var6 - var8;
                     this.a(var11, var12, 4);
                  }

                  if (!var2 || var3) {
                     var9 = false;
                  }

                  this.a(var12, var9);
               }
            }
         }
      }

   }

   public boolean a(String var1) {
      int var4 = this.getChildCount();

      for(int var3 = 0; var3 < var4; ++var3) {
         View var5 = this.getChildAt(var3);
         if (var5 instanceof fm) {
            byte var2;
            BlockBean var6;
            label97: {
               var6 = ((fm)var5).getBean();
               String var7 = var6.opCode;
               switch(var7.hashCode()) {
               case -2120571577:
                  if (var7.equals("mapIsEmpty")) {
                     var2 = 13;
                     break label97;
                  }
                  break;
               case -1920517885:
                  if (var7.equals("setVarBoolean")) {
                     var2 = 1;
                     break label97;
                  }
                  break;
               case -1384858251:
                  if (var7.equals("getAtListMap")) {
                     var2 = 19;
                     break label97;
                  }
                  break;
               case -1377080719:
                  if (var7.equals("decreaseInt")) {
                     var2 = 5;
                     break label97;
                  }
                  break;
               case -1249347599:
                  if (var7.equals("getVar")) {
                     var2 = 0;
                     break label97;
                  }
                  break;
               case -1081400230:
                  if (var7.equals("mapGet")) {
                     var2 = 8;
                     break label97;
                  }
                  break;
               case -1081391085:
                  if (var7.equals("mapPut")) {
                     var2 = 7;
                     break label97;
                  }
                  break;
               case -329559323:
                  if (var7.equals("insertListMap")) {
                     var2 = 16;
                     break label97;
                  }
                  break;
               case 152967761:
                  if (var7.equals("mapClear")) {
                     var2 = 12;
                     break label97;
                  }
                  break;
               case 168740282:
                  if (var7.equals("mapToStr")) {
                     var2 = 17;
                     break label97;
                  }
                  break;
               case 442768763:
                  if (var7.equals("mapGetAllKeys")) {
                     var2 = 14;
                     break label97;
                  }
                  break;
               case 463560551:
                  if (var7.equals("mapContainKey")) {
                     var2 = 9;
                     break label97;
                  }
                  break;
               case 657721930:
                  if (var7.equals("setVarInt")) {
                     var2 = 2;
                     break label97;
                  }
                  break;
               case 747168008:
                  if (var7.equals("mapCreateNew")) {
                     var2 = 6;
                     break label97;
                  }
                  break;
               case 754442829:
                  if (var7.equals("increaseInt")) {
                     var2 = 4;
                     break label97;
                  }
                  break;
               case 836692861:
                  if (var7.equals("mapSize")) {
                     var2 = 11;
                     break label97;
                  }
                  break;
               case 845089750:
                  if (var7.equals("setVarString")) {
                     var2 = 3;
                     break label97;
                  }
                  break;
               case 1431171391:
                  if (var7.equals("mapRemoveKey")) {
                     var2 = 10;
                     break label97;
                  }
                  break;
               case 1775620400:
                  if (var7.equals("strToMap")) {
                     var2 = 18;
                     break label97;
                  }
                  break;
               case 2090182653:
                  if (var7.equals("addListMap")) {
                     var2 = 15;
                     break label97;
                  }
               }

               var2 = -1;
            }

            switch(var2) {
            case 0:
               if (var6.spec.equals(var1)) {
                  return true;
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
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
               if (((String)var6.parameters.get(0)).equals(var1)) {
                  return true;
               }
               break;
            case 18:
               if (((String)var6.parameters.get(1)).equals(var1)) {
                  return true;
               }
               break;
            case 19:
               if (((String)var6.parameters.get(2)).equals(var1)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public void b() {
      this.a();
      this.g = new ArrayList();
      this.h = null;
   }

   public void b(fm var1) {
      if (var1.ac != null) {
         fm var2 = var1.ac;
         if (var2 != null) {
            var2.f(var1);
            var1.ac = null;
         }

      }
   }

   public boolean b(String var1) {
      int var4 = this.getChildCount();

      for(int var3 = 0; var3 < var4; ++var3) {
         View var5 = this.getChildAt(var3);
         if (var5 instanceof fm) {
            byte var2;
            BlockBean var6;
            label112: {
               var6 = ((fm)var5).getBean();
               String var7 = var6.opCode;
               switch(var7.hashCode()) {
               case -1998407506:
                  if (var7.equals("listSetData")) {
                     var2 = 15;
                     break label112;
                  }
                  break;
               case -1384861688:
                  if (var7.equals("getAtListInt")) {
                     var2 = 9;
                     break label112;
                  }
                  break;
               case -1384858251:
                  if (var7.equals("getAtListMap")) {
                     var2 = 21;
                     break label112;
                  }
                  break;
               case -1384851894:
                  if (var7.equals("getAtListStr")) {
                     var2 = 10;
                     break label112;
                  }
                  break;
               case -1271141237:
                  if (var7.equals("clearList")) {
                     var2 = 5;
                     break label112;
                  }
                  break;
               case -1249347599:
                  if (var7.equals("getVar")) {
                     var2 = 0;
                     break label112;
                  }
                  break;
               case -1139353316:
                  if (var7.equals("setListMap")) {
                     var2 = 23;
                     break label112;
                  }
                  break;
               case -733318734:
                  if (var7.equals("strToListMap")) {
                     var2 = 17;
                     break label112;
                  }
                  break;
               case -329562760:
                  if (var7.equals("insertListInt")) {
                     var2 = 18;
                     break label112;
                  }
                  break;
               case -329559323:
                  if (var7.equals("insertListMap")) {
                     var2 = 22;
                     break label112;
                  }
                  break;
               case -329552966:
                  if (var7.equals("insertListStr")) {
                     var2 = 19;
                     break label112;
                  }
                  break;
               case -96313603:
                  if (var7.equals("containListInt")) {
                     var2 = 2;
                     break label112;
                  }
                  break;
               case -96310166:
                  if (var7.equals("containListMap")) {
                     var2 = 4;
                     break label112;
                  }
                  break;
               case -96303809:
                  if (var7.equals("containListStr")) {
                     var2 = 3;
                     break label112;
                  }
                  break;
               case 134874756:
                  if (var7.equals("listSetCustomViewData")) {
                     var2 = 16;
                     break label112;
                  }
                  break;
               case 389111867:
                  if (var7.equals("spnSetData")) {
                     var2 = 14;
                     break label112;
                  }
                  break;
               case 762282303:
                  if (var7.equals("indexListInt")) {
                     var2 = 11;
                     break label112;
                  }
                  break;
               case 762292097:
                  if (var7.equals("indexListStr")) {
                     var2 = 12;
                     break label112;
                  }
                  break;
               case 1160674468:
                  if (var7.equals("lengthList")) {
                     var2 = 1;
                     break label112;
                  }
                  break;
               case 1252547704:
                  if (var7.equals("listMapToStr")) {
                     var2 = 6;
                     break label112;
                  }
                  break;
               case 1764351209:
                  if (var7.equals("deleteList")) {
                     var2 = 13;
                     break label112;
                  }
                  break;
               case 2090179216:
                  if (var7.equals("addListInt")) {
                     var2 = 7;
                     break label112;
                  }
                  break;
               case 2090182653:
                  if (var7.equals("addListMap")) {
                     var2 = 20;
                     break label112;
                  }
                  break;
               case 2090189010:
                  if (var7.equals("addListStr")) {
                     var2 = 8;
                     break label112;
                  }
               }

               var2 = -1;
            }

            switch(var2) {
            case 0:
               if (var6.spec.equals(var1)) {
                  return true;
               }
               break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
               if (((String)var6.parameters.get(0)).equals(var1)) {
                  return true;
               }
               break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
               if (((String)var6.parameters.get(1)).equals(var1)) {
                  return true;
               }
               break;
            case 18:
            case 19:
            case 20:
            case 21:
               if (((String)var6.parameters.get(2)).equals(var1)) {
                  return true;
               }
               break;
            case 22:
            case 23:
               if (((String)var6.parameters.get(3)).equals(var1)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public Object[] b(fm var1, int var2, int var3) {
      byte var4;
      if (var1.j) {
         var4 = 40;
      } else {
         var4 = 60;
      }

      int var5 = 100000;
      Object[] var7 = null;
      Point var10 = new Point(var2, var3);

      Object[] var12;
      for(var2 = 0; var2 < this.g.size(); var7 = var12) {
         Object[] var9 = (Object[])this.g.get(var2);
         int[] var8 = (int[])var9[0];
         Point var11 = new Point(var10.x - var8[0], var10.y - var8[1]);
         int var6 = Math.abs(var11.x / 2) + Math.abs(var11.y);
         var3 = var5;
         var12 = var7;
         if (var6 < var5) {
            var3 = var5;
            var12 = var7;
            if (var6 < var4) {
               var3 = var5;
               var12 = var7;
               if (this.a(var1, (View)var9[1])) {
                  var12 = var9;
                  var3 = var6;
               }
            }
         }

         ++var2;
         var5 = var3;
      }

      return var7;
   }

   public fm c(fm var1, int var2, int var3) {
      this.getLocationOnScreen(this.d);
      fm var5 = var1;
      if (var1.getBlockType() == 1) {
         Context var6 = this.getContext();
         int var4 = this.a++;
         var5 = new fm(var6, var4, var1.a, var1.w, var1.x, var1.b);
      }

      var5.s = this;
      this.addView(var5);
      var5.setX((float)(var2 - this.d[0] - this.getPaddingLeft()));
      var5.setY((float)(var3 - this.d[1] - this.getPaddingTop()));
      return var5;
   }

   public fm c(String var1) {
      return (fm)this.findViewWithTag(Integer.valueOf(var1));
   }

   public void c() {
      int var7 = this.getChildCount();
      int var6 = this.getLayoutParams().width;
      int var3 = this.getLayoutParams().width;

      int var4;
      for(int var2 = 0; var2 < var7; var3 = var4) {
         View var8 = this.getChildAt(var2);
         int var5 = var6;
         var4 = var3;
         if (var8 instanceof fm) {
            float var1 = var8.getX();
            fm var9 = (fm)var8;
            var5 = Math.max((int)(var1 + (float)var9.getWidthSum()) + 150, var6);
            var4 = Math.max((int)(var8.getY() + (float)var9.getHeightSum()) + 150, var3);
         }

         ++var2;
         var6 = var5;
      }

      this.getLayoutParams().width = var6;
      this.getLayoutParams().height = var3;
   }

   public void c(fm var1) {
      this.b(var1);
      ArrayList var3 = var1.getAllChildren();
      Iterator var2 = var3.iterator();

      while(var2.hasNext()) {
         var1 = (fm)var2.next();
         this.removeView(var1);
      }

   }

   public int getAddTargetId() {
      Object[] var4 = this.getNearestTarget();
      byte var2 = -1;
      int var1 = var2;
      if (var4 != null) {
         var1 = var2;
         if (var4[2] != null) {
            int var3 = (Integer)var4[2];
            if (var3 != 0 && var3 != 2 && var3 != 3) {
               var1 = var2;
               if (var3 != 5) {
                  return var1;
               }
            }

            var1 = var2;
            if (var4[1] != null) {
               View var6 = (View)var4[1];
               var1 = var2;
               if (var6 instanceof fm) {
                  fm var5 = (fm)var6;
                  if (var5.j) {
                     var1 = (Integer)var5.ac.getTag();
                  } else {
                     var1 = (Integer)((fm)var4[1]).getTag();
                  }
               }

               if (var6 instanceof fn) {
                  fn var7 = (fn)var6;
                  var1 = (Integer)var7.ac.getTag();
               }
            }
         }
      }

      return var1;
   }

   public ArrayList<BlockBean> getBlocks() {
      ArrayList var1 = new ArrayList();
      fm var2 = (fm)this.findViewWithTag(this.f.l);
      if (var2 != null) {
         ArrayList var4 = var2.getAllChildren();
         Iterator var3 = var4.iterator();

         while(var3.hasNext()) {
            var2 = (fm)var3.next();
            var1.add(var2.getBean());
         }
      }

      return var1;
   }

   public Object[] getNearestTarget() {
      return this.h;
   }

   public fm getRoot() {
      return this.f;
   }
}
