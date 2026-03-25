/* Decompiler 442ms, total 3525ms, lines 1518 */
package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import java.util.ArrayList;
import java.util.Iterator;

public class ev {
   public static hc a(String var0, String var1) {
      int var2;
      byte var3;
      byte var5;
      label100: {
         var2 = var0.hashCode();
         var3 = 1;
         if (var2 != 100) {
            if (var2 != 112) {
               if (var2 != 115) {
                  if (var2 != 118) {
                     switch(var2) {
                     case 97:
                        if (var0.equals("a")) {
                           var5 = 4;
                           break label100;
                        }
                        break;
                     case 98:
                        if (var0.equals("b")) {
                           var5 = 0;
                           break label100;
                        }
                        break;
                     default:
                        switch(var2) {
                        case 108:
                           if (var0.equals("l")) {
                              var5 = 5;
                              break label100;
                           }
                           break;
                        case 109:
                           if (var0.equals("m")) {
                              var5 = 8;
                              break label100;
                           }
                           break;
                        case 110:
                           if (var0.equals("n")) {
                              var5 = 2;
                              break label100;
                           }
                        }
                     }
                  } else if (var0.equals("v")) {
                     var5 = 6;
                     break label100;
                  }
               } else if (var0.equals("s")) {
                  var5 = 3;
                  break label100;
               }
            } else if (var0.equals("p")) {
               var5 = 7;
               break label100;
            }
         } else if (var0.equals("d")) {
            var5 = 1;
            break label100;
         }

         var5 = -1;
      }

      hc var4 = null;
      switch(var5) {
      case 0:
         var4 = new hc("boolean");
         break;
      case 1:
      case 2:
         var4 = new hc("double");
         break;
      case 3:
         if (var1 != null && var1.length() > 0) {
            if (var1.toLowerCase().equals("inputonly")) {
               var4 = new hc("Input");
            } else {
               var4 = new hc("String");
            }
         } else {
            var4 = new hc("String");
         }
         break;
      case 4:
         var4 = new hc("Map");
         break;
      case 5:
         label72: {
            var2 = var1.hashCode();
            if (var2 != 995088651) {
               if (var2 != 1137466835) {
                  if (var2 == 1408716506 && var1.equals("List Map")) {
                     var5 = 2;
                     break label72;
                  }
               } else if (var1.equals("List String")) {
                  var5 = var3;
                  break label72;
               }
            } else if (var1.equals("List Number")) {
               var5 = 0;
               break label72;
            }

            var5 = -1;
         }

         switch(var5) {
         case 0:
            var4 = new hc("ListInt");
            return var4;
         case 1:
            var4 = new hc("ListString");
            return var4;
         case 2:
            var4 = new hc("ListMap");
            return var4;
         default:
            return var4;
         }
      case 6:
         var4 = new hc(var1);
         break;
      case 7:
      case 8:
         var4 = new hc(a(var1));
      }

      return var4;
   }

   public static String a(int var0) {
      switch(var0) {
      case 1:
         return "Intent";
      case 2:
         return "SharedPreferences";
      case 3:
         return "Calendar";
      case 4:
         return "Vibrator";
      case 5:
         return "Timer";
      case 6:
         return "FirebaseDB";
      case 7:
         return "Dialog";
      case 8:
         return "MediaPlayer";
      case 9:
         return "SoundPool";
      case 10:
         return "ObjectAnimator";
      case 11:
         return "Gyroscope";
      case 12:
         return "FirebaseAuth";
      case 13:
         return "InterstitialAd";
      case 14:
         return "FirebaseStorage";
      case 15:
         return "Camera";
      case 16:
         return "FilePicker";
      case 17:
         return "RequestNetwork";
      case 18:
         return "TextToSpeech";
      case 19:
         return "SpeechToText";
      case 20:
         return "BluetoothConnect";
      case 21:
         return "LocationManager";
      default:
         return "";
      }
   }

   public static String a(String var0) {
      byte var1;
      label252: {
         switch(var0.hashCode()) {
         case -2099895620:
            if (var0.equals("Intent")) {
               var1 = 0;
               break label252;
            }
            break;
         case -2004438503:
            if (var0.equals("spinner")) {
               var1 = 32;
               break label252;
            }
            break;
         case -1965257499:
            if (var0.equals("Gyroscope")) {
               var1 = 13;
               break label252;
            }
            break;
         case -1811660373:
            if (var0.equals("soundpool")) {
               var1 = 44;
               break label252;
            }
            break;
         case -1677313857:
            if (var0.equals("requestnetwork")) {
               var1 = 54;
               break label252;
            }
            break;
         case -1673662198:
            if (var0.equals("filepicker")) {
               var1 = 53;
               break label252;
            }
            break;
         case -1632719839:
            if (var0.equals("Firebase Auth")) {
               var1 = 12;
               break label252;
            }
            break;
         case -1587760963:
            if (var0.equals("compoundButton")) {
               var1 = 27;
               break label252;
            }
            break;
         case -1558241498:
            if (var0.equals("objectanimator")) {
               var1 = 45;
               break label252;
            }
            break;
         case -1421968056:
            if (var0.equals("adview")) {
               var1 = 35;
               break label252;
            }
            break;
         case -1367751899:
            if (var0.equals("camera")) {
               var1 = 52;
               break label252;
            }
            break;
         case -1332085432:
            if (var0.equals("dialog")) {
               var1 = 42;
               break label252;
            }
            break;
         case -1197746358:
            if (var0.equals("texttospeech")) {
               var1 = 56;
               break label252;
            }
            break;
         case -1183762788:
            if (var0.equals("intent")) {
               var1 = 37;
               break label252;
            }
            break;
         case -1002626734:
            if (var0.equals("textview")) {
               var1 = 24;
               break label252;
            }
            break;
         case -961721768:
            if (var0.equals("locationmanager")) {
               var1 = 59;
               break label252;
            }
            break;
         case -889473228:
            if (var0.equals("switch")) {
               var1 = 29;
               break label252;
            }
            break;
         case -877150592:
            if (var0.equals("imageview")) {
               var1 = 26;
               break label252;
            }
            break;
         case -823676088:
            if (var0.equals("varInt")) {
               var1 = 16;
               break label252;
            }
            break;
         case -823672651:
            if (var0.equals("varMap")) {
               var1 = 18;
               break label252;
            }
            break;
         case -823666294:
            if (var0.equals("varStr")) {
               var1 = 17;
               break label252;
            }
            break;
         case -685497065:
            if (var0.equals("Firebase DB")) {
               var1 = 10;
               break label252;
            }
            break;
         case -563351033:
            if (var0.equals("firebase")) {
               var1 = 46;
               break label252;
            }
            break;
         case -498706905:
            if (var0.equals("Firebase")) {
               var1 = 11;
               break label252;
            }
            break;
         case -351639837:
            if (var0.equals("calendarview")) {
               var1 = 34;
               break label252;
            }
            break;
         case -290065014:
            if (var0.equals("speechtotext")) {
               var1 = 57;
               break label252;
            }
            break;
         case -178324674:
            if (var0.equals("calendar")) {
               var1 = 39;
               break label252;
            }
            break;
         case -113680546:
            if (var0.equals("Calendar")) {
               var1 = 3;
               break label252;
            }
            break;
         case 2189724:
            if (var0.equals("File")) {
               var1 = 2;
               break label252;
            }
            break;
         case 3143036:
            if (var0.equals("file")) {
               var1 = 38;
               break label252;
            }
            break;
         case 3322014:
            if (var0.equals("list")) {
               var1 = 22;
               break label252;
            }
            break;
         case 3619493:
            if (var0.equals("view")) {
               var1 = 23;
               break label252;
            }
            break;
         case 62092335:
            if (var0.equals("firebaseauth")) {
               var1 = 47;
               break label252;
            }
            break;
         case 80811813:
            if (var0.equals("Timer")) {
               var1 = 5;
               break label252;
            }
            break;
         case 110364485:
            if (var0.equals("timer")) {
               var1 = 41;
               break label252;
            }
            break;
         case 181944945:
            if (var0.equals("listInt")) {
               var1 = 19;
               break label252;
            }
            break;
         case 181948382:
            if (var0.equals("listMap")) {
               var1 = 21;
               break label252;
            }
            break;
         case 181954611:
            if (var0.equals("listSpn")) {
               var1 = 30;
               break label252;
            }
            break;
         case 181954739:
            if (var0.equals("listStr")) {
               var1 = 20;
               break label252;
            }
            break;
         case 191354283:
            if (var0.equals("SoundPool")) {
               var1 = 8;
               break label252;
            }
            break;
         case 235637425:
            if (var0.equals("varBool")) {
               var1 = 15;
               break label252;
            }
            break;
         case 320151695:
            if (var0.equals("InterstitialAd")) {
               var1 = 14;
               break label252;
            }
            break;
         case 325741829:
            if (var0.equals("gyroscope")) {
               var1 = 49;
               break label252;
            }
            break;
         case 400845544:
            if (var0.equals("File (Shared Preferences)")) {
               var1 = 1;
               break label252;
            }
            break;
         case 485199813:
            if (var0.equals("mediaplayer")) {
               var1 = 43;
               break label252;
            }
            break;
         case 690484860:
            if (var0.equals("bluetoothconnect")) {
               var1 = 58;
               break label252;
            }
            break;
         case 837734913:
            if (var0.equals("mapview")) {
               var1 = 36;
               break label252;
            }
            break;
         case 1105738265:
            if (var0.equals("vibrator")) {
               var1 = 40;
               break label252;
            }
            break;
         case 1131540166:
            if (var0.equals("progressbar")) {
               var1 = 55;
               break label252;
            }
            break;
         case 1170382393:
            if (var0.equals("Vibrator")) {
               var1 = 4;
               break label252;
            }
            break;
         case 1224424441:
            if (var0.equals("webview")) {
               var1 = 33;
               break label252;
            }
            break;
         case 1236935621:
            if (var0.equals("MediaPlayer")) {
               var1 = 7;
               break label252;
            }
            break;
         case 1322145871:
            if (var0.equals("interstitialad")) {
               var1 = 50;
               break label252;
            }
            break;
         case 1346661443:
            if (var0.equals("listview")) {
               var1 = 31;
               break label252;
            }
            break;
         case 1536891843:
            if (var0.equals("checkbox")) {
               var1 = 28;
               break label252;
            }
            break;
         case 1602985527:
            if (var0.equals("edittext")) {
               var1 = 25;
               break label252;
            }
            break;
         case 1719159444:
            if (var0.equals("firebasestorage")) {
               var1 = 51;
               break label252;
            }
            break;
         case 1799376742:
            if (var0.equals("ObjectAnimator")) {
               var1 = 9;
               break label252;
            }
            break;
         case 1971813019:
            if (var0.equals("seekbar")) {
               var1 = 48;
               break label252;
            }
            break;
         case 2046749032:
            if (var0.equals("Dialog")) {
               var1 = 6;
               break label252;
            }
         }

         var1 = -1;
      }

      switch(var1) {
      case 0:
         return "Intent";
      case 1:
      case 2:
         return "SharedPreferences";
      case 3:
         return "Calendar";
      case 4:
         return "Vibrator";
      case 5:
         return "Timer";
      case 6:
         return "Dialog";
      case 7:
         return "MediaPlayer";
      case 8:
         return "SoundPool";
      case 9:
         return "ObjectAnimator";
      case 10:
      case 11:
         return "FirebaseDB";
      case 12:
         return "FirebaseAuth";
      case 13:
         return "Gyroscope";
      case 14:
         return "InterstitialAd";
      case 15:
         return "boolean.SelectBoolean";
      case 16:
         return "double.SelectDouble";
      case 17:
         return "String.SelectString";
      case 18:
         return "Map";
      case 19:
         return "ListInt";
      case 20:
         return "ListString";
      case 21:
         return "ListMap";
      case 22:
         return "List";
      case 23:
         return "View";
      case 24:
         return "TextView";
      case 25:
         return "EditText";
      case 26:
         return "ImageView";
      case 27:
         return "CompoundButton";
      case 28:
         return "CheckBox";
      case 29:
         return "Switch";
      case 30:
         return "AdapterView";
      case 31:
         return "ListView";
      case 32:
         return "Spinner";
      case 33:
         return "WebView";
      case 34:
         return "CalendarView";
      case 35:
         return "AdView";
      case 36:
         return "MapView";
      case 37:
         return "Intent";
      case 38:
         return "SharedPreferences";
      case 39:
         return "Calendar";
      case 40:
         return "Vibrator";
      case 41:
         return "Timer";
      case 42:
         return "Dialog";
      case 43:
         return "MediaPlayer";
      case 44:
         return "SoundPool";
      case 45:
         return "ObjectAnimator";
      case 46:
         return "FirebaseDB";
      case 47:
         return "FirebaseAuth";
      case 48:
         return "SeekBar";
      case 49:
         return "Gyroscope";
      case 50:
         return "InterstitialAd";
      case 51:
         return "FirebaseStorage";
      case 52:
         return "Camera";
      case 53:
         return "FilePicker";
      case 54:
         return "RequestNetwork";
      case 55:
         return "ProgressBar";
      case 56:
         return "TextToSpeech";
      case 57:
         return "SpeechToText";
      case 58:
         return "BluetoothConnect";
      case 59:
         return "LocationManager";
      default:
         return var0;
      }
   }

   public static String b(int var0) {
      switch(var0) {
      case 0:
         return "boolean";
      case 1:
         return "double";
      case 2:
         return "String";
      case 3:
         return "Map";
      default:
         return "";
      }
   }

   public static ArrayList<hc> b(String var0) {
      ArrayList var2 = new ArrayList();
      ArrayList var5 = kx.d(var0);
      if (var5.size() <= 0) {
         return var2;
      } else {
         Iterator var3 = var5.iterator();

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            if (var4.charAt(0) == '%' && var4.length() >= 2) {
               char var1 = var4.charAt(1);
               var0 = "";
               if (var4.length() > 3) {
                  var0 = var4.substring(3);
               }

               var2.add(a(String.valueOf(var1), var0));
            }
         }

         return var2;
      }
   }

   public static String c(int var0) {
      switch(var0) {
      case 1:
         return "ListInt";
      case 2:
         return "ListString";
      case 3:
         return "ListMap";
      default:
         return "";
      }
   }

   public static String c(String var0) {
      byte var1;
      label72: {
         switch(var0.hashCode()) {
         case -1965257499:
            if (var0.equals("Gyroscope")) {
               var1 = 7;
               break label72;
            }
            break;
         case -1936496017:
            if (var0.equals("ListString")) {
               var1 = 4;
               break label72;
            }
            break;
         case -1908172204:
            if (var0.equals("FirebaseStorage")) {
               var1 = 11;
               break label72;
            }
            break;
         case -1325958191:
            if (var0.equals("double")) {
               var1 = 0;
               break label72;
            }
            break;
         case -1042830870:
            if (var0.equals("SpeechToText")) {
               var1 = 14;
               break label72;
            }
            break;
         case -596330166:
            if (var0.equals("FilePicker")) {
               var1 = 13;
               break label72;
            }
            break;
         case 77116:
            if (var0.equals("Map")) {
               var1 = 2;
               break label72;
            }
            break;
         case 80811813:
            if (var0.equals("Timer")) {
               var1 = 6;
               break label72;
            }
            break;
         case 320151695:
            if (var0.equals("InterstitialAd")) {
               var1 = 10;
               break label72;
            }
            break;
         case 326594314:
            if (var0.equals("double.SelectDouble")) {
               var1 = 1;
               break label72;
            }
            break;
         case 1779003621:
            if (var0.equals("FirebaseDB")) {
               var1 = 9;
               break label72;
            }
            break;
         case 1846598225:
            if (var0.equals("ListInt")) {
               var1 = 3;
               break label72;
            }
            break;
         case 1846601662:
            if (var0.equals("ListMap")) {
               var1 = 5;
               break label72;
            }
            break;
         case 2011082565:
            if (var0.equals("Camera")) {
               var1 = 12;
               break label72;
            }
            break;
         case 2046749032:
            if (var0.equals("Dialog")) {
               var1 = 8;
               break label72;
            }
         }

         var1 = -1;
      }

      switch(var1) {
      case 0:
      case 1:
         return "double";
      case 2:
         return "HashMap<String, Object>";
      case 3:
         return "ArrayList<Double>";
      case 4:
         return "ArrayList<String>";
      case 5:
         return "ArrayList<HashMap<String, Object>>";
      case 6:
         return "TimerTask";
      case 7:
         return "SensorManager";
      case 8:
         return "AlertDialog.Builder";
      case 9:
         return "DatabaseReference";
      case 10:
         return "InterstitialAd";
      case 11:
         return "StorageReference";
      case 12:
      case 13:
         return "Intent";
      case 14:
         return "SpeechRecognizer";
      default:
         return var0;
      }
   }

   public static ArrayList<String> d(String var0) {
      byte var1;
      ArrayList var2;
      label194: {
         var2 = new ArrayList();
         switch(var0.hashCode()) {
         case -2099895620:
            if (var0.equals("Intent")) {
               var1 = 22;
               break label194;
            }
            break;
         case -1965257499:
            if (var0.equals("Gyroscope")) {
               var1 = 33;
               break label194;
            }
            break;
         case -1936496017:
            if (var0.equals("ListString")) {
               var1 = 2;
               break label194;
            }
            break;
         case -1908172204:
            if (var0.equals("FirebaseStorage")) {
               var1 = 38;
               break label194;
            }
            break;
         case -1884914774:
            if (var0.equals("TextToSpeech")) {
               var1 = 41;
               break label194;
            }
            break;
         case -1805606060:
            if (var0.equals("Switch")) {
               var1 = 16;
               break label194;
            }
            break;
         case -1793532415:
            if (var0.equals("MapView")) {
               var1 = 21;
               break label194;
            }
            break;
         case -1495589242:
            if (var0.equals("ProgressBar")) {
               var1 = 20;
               break label194;
            }
            break;
         case -1406842887:
            if (var0.equals("WebView")) {
               var1 = 15;
               break label194;
            }
            break;
         case -1125439882:
            if (var0.equals("HorizontalScrollView")) {
               var1 = 6;
               break label194;
            }
            break;
         case -1042830870:
            if (var0.equals("SpeechToText")) {
               var1 = 42;
               break label194;
            }
            break;
         case -938935918:
            if (var0.equals("TextView")) {
               var1 = 7;
               break label194;
            }
            break;
         case -658531749:
            if (var0.equals("SeekBar")) {
               var1 = 17;
               break label194;
            }
            break;
         case -596330166:
            if (var0.equals("FilePicker")) {
               var1 = 40;
               break label194;
            }
            break;
         case -431490149:
            if (var0.equals("DrawerLayout")) {
               var1 = 36;
               break label194;
            }
            break;
         case -339785223:
            if (var0.equals("Spinner")) {
               var1 = 13;
               break label194;
            }
            break;
         case -294086120:
            if (var0.equals("LocationManager")) {
               var1 = 43;
               break label194;
            }
            break;
         case -188272861:
            if (var0.equals("CalendarView")) {
               var1 = 18;
               break label194;
            }
            break;
         case -113680546:
            if (var0.equals("Calendar")) {
               var1 = 24;
               break label194;
            }
            break;
         case 77116:
            if (var0.equals("Map")) {
               var1 = 0;
               break label194;
            }
            break;
         case 80811813:
            if (var0.equals("Timer")) {
               var1 = 26;
               break label194;
            }
            break;
         case 191354283:
            if (var0.equals("SoundPool")) {
               var1 = 29;
               break label194;
            }
            break;
         case 225459311:
            if (var0.equals("FirebaseAuth")) {
               var1 = 32;
               break label194;
            }
            break;
         case 320151695:
            if (var0.equals("InterstitialAd")) {
               var1 = 37;
               break label194;
            }
            break;
         case 524559195:
            if (var0.equals("Toolbar")) {
               var1 = 35;
               break label194;
            }
            break;
         case 1100433486:
            if (var0.equals("FloatingActionButton")) {
               var1 = 34;
               break label194;
            }
            break;
         case 1125864064:
            if (var0.equals("ImageView")) {
               var1 = 9;
               break label194;
            }
            break;
         case 1127291599:
            if (var0.equals("LinearLayout")) {
               var1 = 4;
               break label194;
            }
            break;
         case 1170382393:
            if (var0.equals("Vibrator")) {
               var1 = 25;
               break label194;
            }
            break;
         case 1236935621:
            if (var0.equals("MediaPlayer")) {
               var1 = 28;
               break label194;
            }
            break;
         case 1410352259:
            if (var0.equals("ListView")) {
               var1 = 14;
               break label194;
            }
            break;
         case 1601505219:
            if (var0.equals("CheckBox")) {
               var1 = 12;
               break label194;
            }
            break;
         case 1616304435:
            if (var0.equals("SharedPreferences")) {
               var1 = 23;
               break label194;
            }
            break;
         case 1666676343:
            if (var0.equals("EditText")) {
               var1 = 10;
               break label194;
            }
            break;
         case 1705213149:
            if (var0.equals("CompoundButton")) {
               var1 = 11;
               break label194;
            }
            break;
         case 1779003621:
            if (var0.equals("FirebaseDB")) {
               var1 = 31;
               break label194;
            }
            break;
         case 1799376742:
            if (var0.equals("ObjectAnimator")) {
               var1 = 30;
               break label194;
            }
            break;
         case 1846598225:
            if (var0.equals("ListInt")) {
               var1 = 1;
               break label194;
            }
            break;
         case 1846601662:
            if (var0.equals("ListMap")) {
               var1 = 3;
               break label194;
            }
            break;
         case 1955913096:
            if (var0.equals("AdView")) {
               var1 = 19;
               break label194;
            }
            break;
         case 2001146706:
            if (var0.equals("Button")) {
               var1 = 8;
               break label194;
            }
            break;
         case 2011082565:
            if (var0.equals("Camera")) {
               var1 = 39;
               break label194;
            }
            break;
         case 2046749032:
            if (var0.equals("Dialog")) {
               var1 = 27;
               break label194;
            }
            break;
         case 2059813682:
            if (var0.equals("ScrollView")) {
               var1 = 5;
               break label194;
            }
         }

         var1 = -1;
      }

      switch(var1) {
      case 0:
         var2.add("java.util.HashMap");
         break;
      case 1:
         var2.add("java.util.ArrayList");
         break;
      case 2:
         var2.add("java.util.ArrayList");
         break;
      case 3:
         var2.add("java.util.ArrayList");
         var2.add("java.util.HashMap");
         break;
      case 4:
         var2.add("android.widget.LinearLayout");
         break;
      case 5:
         var2.add("android.widget.ScrollView");
         break;
      case 6:
         var2.add("android.widget.HorizontalScrollView");
         break;
      case 7:
         var2.add("android.widget.TextView");
         break;
      case 8:
         var2.add("android.widget.Button");
         break;
      case 9:
         var2.add("android.widget.ImageView");
         break;
      case 10:
         var2.add("android.widget.EditText");
         break;
      case 11:
         var2.add("android.widget.CompoundButton");
         break;
      case 12:
         var2.add("android.widget.CheckBox");
         break;
      case 13:
         var2.add("android.widget.Spinner");
         var2.add("android.widget.ArrayAdapter");
         break;
      case 14:
         var2.add("android.widget.ListView");
         var2.add("android.widget.ArrayAdapter");
         var2.add("android.widget.BaseAdapter");
         break;
      case 15:
         var2.add("android.webkit.WebView");
         var2.add("android.webkit.WebSettings");
         break;
      case 16:
         var2.add("android.widget.Switch");
         break;
      case 17:
         var2.add("android.widget.SeekBar");
         break;
      case 18:
         var2.add("android.widget.CalendarView");
         break;
      case 19:
         var2.add("com.google.android.gms.ads.AdView");
         var2.add("com.google.android.gms.ads.AdRequest");
         break;
      case 20:
         var2.add("android.widget.ProgressBar");
         break;
      case 21:
         var2.add("com.google.android.gms.maps.MapView");
         var2.add("com.google.android.gms.maps.GoogleMap");
         var2.add("com.google.android.gms.maps.OnMapReadyCallback");
         var2.add("com.google.android.gms.maps.model.Marker");
         var2.add("com.google.android.gms.maps.model.BitmapDescriptorFactory");
         break;
      case 22:
         var2.add("android.content.Intent");
         var2.add("android.net.Uri");
         break;
      case 23:
         var2.add("android.app.Activity");
         var2.add("android.content.SharedPreferences");
         break;
      case 24:
         var2.add("java.util.Calendar");
         var2.add("java.text.SimpleDateFormat");
         break;
      case 25:
         var2.add("android.content.Context");
         var2.add("android.os.Vibrator");
         break;
      case 26:
         var2.add("java.util.Timer");
         var2.add("java.util.TimerTask");
         break;
      case 27:
         var2.add("android.app.AlertDialog");
         var2.add("android.content.DialogInterface");
         break;
      case 28:
         var2.add("android.media.MediaPlayer");
         break;
      case 29:
         var2.add("android.media.SoundPool");
         break;
      case 30:
         var2.add("android.animation.ObjectAnimator");
         var2.add("android.view.animation.LinearInterpolator");
         var2.add("android.view.animation.AccelerateInterpolator");
         var2.add("android.view.animation.DecelerateInterpolator");
         var2.add("android.view.animation.AccelerateDecelerateInterpolator");
         var2.add("android.view.animation.BounceInterpolator");
         break;
      case 31:
         var2.add("com.google.firebase.database.FirebaseDatabase");
         var2.add("com.google.firebase.database.DatabaseReference");
         var2.add("com.google.firebase.database.ValueEventListener");
         var2.add("com.google.firebase.database.DataSnapshot");
         var2.add("com.google.firebase.database.DatabaseError");
         var2.add("com.google.firebase.database.GenericTypeIndicator");
         var2.add("com.google.firebase.database.ChildEventListener");
         var2.add("java.util.HashMap");
         break;
      case 32:
         var2.add("com.google.firebase.auth.AuthResult");
         var2.add("com.google.firebase.auth.FirebaseAuth");
         var2.add("com.google.android.gms.tasks.OnCompleteListener");
         var2.add("com.google.android.gms.tasks.Task");
         break;
      case 33:
         var2.add("android.content.Context");
         var2.add("android.hardware.Sensor");
         var2.add("android.hardware.SensorManager");
         var2.add("android.hardware.SensorEvent");
         var2.add("android.hardware.SensorEventListener");
         break;
      case 34:
         var2.add("android.support.design.widget.FloatingActionButton");
         break;
      case 35:
         var2.add("android.support.v7.widget.Toolbar");
         break;
      case 36:
         var2.add("android.support.v4.view.GravityCompat");
         var2.add("android.support.v4.widget.DrawerLayout");
         var2.add("android.support.v7.app.ActionBarDrawerToggle");
         break;
      case 37:
         var2.add("com.google.android.gms.ads.AdRequest");
         var2.add("com.google.android.gms.ads.InterstitialAd");
         var2.add("com.google.android.gms.ads.AdListener");
         break;
      case 38:
         var2.add("com.google.firebase.storage.FileDownloadTask");
         var2.add("com.google.firebase.storage.FirebaseStorage");
         var2.add("com.google.firebase.storage.StorageReference");
         var2.add("com.google.firebase.storage.UploadTask");
         var2.add("com.google.firebase.storage.OnProgressListener");
         var2.add("com.google.firebase.storage.FileDownloadTask");
         var2.add("com.google.android.gms.tasks.OnSuccessListener");
         var2.add("com.google.android.gms.tasks.OnFailureListener");
         var2.add("android.net.Uri");
         var2.add("java.io.File");
         break;
      case 39:
         var2.add("android.content.Intent");
         var2.add("android.net.Uri");
         var2.add("android.provider.MediaStore");
         var2.add("android.os.Build");
         var2.add("android.support.v4.content.FileProvider");
         var2.add("java.io.File");
         break;
      case 40:
         var2.add("android.content.Intent");
         var2.add("android.content.ClipData");
         break;
      case 41:
         var2.add("android.speech.tts.TextToSpeech");
         break;
      case 42:
         var2.add("android.speech.SpeechRecognizer");
         var2.add("android.speech.RecognizerIntent");
         var2.add("android.speech.RecognitionListener");
         break;
      case 43:
         var2.add("android.location.Location");
         var2.add("android.location.LocationManager");
         var2.add("android.location.LocationListener");
      }

      return var2;
   }

   public static ArrayList<String> e(String var0) {
      byte var1;
      ArrayList var2;
      label91: {
         var2 = new ArrayList();
         switch(var0.hashCode()) {
         case -2054042947:
            if (var0.equals("onClickListener")) {
               var1 = 0;
               break label91;
            }
            break;
         case -1990409668:
            if (var0.equals("onTextChangedListener")) {
               var1 = 1;
               break label91;
            }
            break;
         case -1907134451:
            if (var0.equals("onDeleteSuccessListener")) {
               var1 = 12;
               break label91;
            }
            break;
         case -1362091184:
            if (var0.equals("onDownloadSuccessListener")) {
               var1 = 11;
               break label91;
            }
            break;
         case -1353514613:
            if (var0.equals("recognitionListener")) {
               var1 = 16;
               break label91;
            }
            break;
         case -933396353:
            if (var0.equals("onFailureListener")) {
               var1 = 13;
               break label91;
            }
            break;
         case -924274776:
            if (var0.equals("onDownloadProgressListener")) {
               var1 = 15;
               break label91;
            }
            break;
         case -829278715:
            if (var0.equals("onMapMarkerClickListener")) {
               var1 = 18;
               break label91;
            }
            break;
         case -744728252:
            if (var0.equals("webViewClient")) {
               var1 = 8;
               break label91;
            }
            break;
         case -332388831:
            if (var0.equals("onItemSelectedListener")) {
               var1 = 3;
               break label91;
            }
            break;
         case -291578101:
            if (var0.equals("onMapReadyCallback")) {
               var1 = 17;
               break label91;
            }
            break;
         case -291013445:
            if (var0.equals("animatorListener")) {
               var1 = 9;
               break label91;
            }
            break;
         case -80069142:
            if (var0.equals("onItemClickListener")) {
               var1 = 4;
               break label91;
            }
            break;
         case 165600064:
            if (var0.equals("onSeekBarChangeListener")) {
               var1 = 6;
               break label91;
            }
            break;
         case 462657998:
            if (var0.equals("onItemLongClickListener")) {
               var1 = 5;
               break label91;
            }
            break;
         case 670396663:
            if (var0.equals("onUploadSuccessListener")) {
               var1 = 10;
               break label91;
            }
            break;
         case 1118236689:
            if (var0.equals("onDateChangeListener")) {
               var1 = 7;
               break label91;
            }
            break;
         case 1538933641:
            if (var0.equals("locationListener")) {
               var1 = 19;
               break label91;
            }
            break;
         case 1842370015:
            if (var0.equals("onCheckChangedListener")) {
               var1 = 2;
               break label91;
            }
            break;
         case 1953306337:
            if (var0.equals("onUploadProgressListener")) {
               var1 = 14;
               break label91;
            }
         }

         var1 = -1;
      }

      switch(var1) {
      case 0:
         var2.add("android.view.View");
         break;
      case 1:
         var2.add("android.text.Editable");
         var2.add("android.text.TextWatcher");
         break;
      case 2:
         var2.add("android.widget.CompoundButton");
         break;
      case 3:
      case 4:
      case 5:
         var2.add("android.widget.AdapterView");
      case 6:
      case 7:
      default:
         break;
      case 8:
         var2.add("android.webkit.WebViewClient");
         break;
      case 9:
         var2.add("android.animation.Animator");
         break;
      case 10:
      case 11:
      case 12:
         var2.add("com.google.android.gms.tasks.OnSuccessListener");
         break;
      case 13:
         var2.add("com.google.android.gms.tasks.OnFailureListener");
         break;
      case 14:
      case 15:
         var2.add("com.google.firebase.storage.OnProgressListener");
         break;
      case 16:
         var2.add("android.speech.RecognitionListener");
         break;
      case 17:
         var2.add("com.google.android.gms.maps.OnMapReadyCallback");
         break;
      case 18:
         var2.add("com.google.android.gms.maps.GoogleMap");
         break;
      case 19:
         var2.add("android.location.LocationListener");
      }

      return var2;
   }
}
