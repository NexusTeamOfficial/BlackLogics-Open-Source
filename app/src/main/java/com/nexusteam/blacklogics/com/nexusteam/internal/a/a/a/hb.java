package com.nexusteam.internal;

import android.util.Pair;
import com.nexusteam.internal.beans.BlockBean;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class hb {
   private String[] a = new String[]{"repeat", "+", "-", "*", "/", "%", ">", "=", "<", "&&", "||", "not"};
   private String[] b = new String[]{"+", "-", "*", "/", "%", ">", "=", "<", "&&", "||"};
   private String c;
   private String d;
   private es e;
   private ArrayList<BlockBean> f;
   private Map<String, BlockBean> g = null;

   public hb(String var1, es var2, String var3, ArrayList<BlockBean> var4) {
      this.c = var1;
      this.e = var2;
      this.d = var3;
      this.f = var4;
   }

   private String a(BlockBean var1, String var2) {
      ArrayList var10 = new ArrayList();
      int var4 = 0;

      while(true) {
         int var5 = var1.parameters.size();
         byte var3 = 3;
         String var7;
         if (var4 >= var5) {
            short var13;
            String var16;
            label3329: {
               var16 = var1.opCode;
               switch(var16.hashCode()) {
               case -2135695280:
                  if (var16.equals("webViewLoadUrl")) {
                     var13 = 171;
                     break label3329;
                  }
                  break;
               case -2120571577:
                  if (var16.equals("mapIsEmpty")) {
                     var13 = 15;
                     break label3329;
                  }
                  break;
               case -2114384168:
                  if (var16.equals("firebasestorageDownloadFile")) {
                     var13 = 262;
                     break label3329;
                  }
                  break;
               case -2055793167:
                  if (var16.equals("fileutillistdir")) {
                     var13 = 271;
                     break label3329;
                  }
                  break;
               case -2037144358:
                  if (var16.equals("bluetoothConnectStartConnectionToUuid")) {
                     var13 = 315;
                     break label3329;
                  }
                  break;
               case -2027093331:
                  if (var16.equals("calendarViewSetDate")) {
                     var13 = 184;
                     break label3329;
                  }
                  break;
               case -2020761366:
                  if (var16.equals("fileRemoveData")) {
                     var13 = 133;
                     break label3329;
                  }
                  break;
               case -1998407506:
                  if (var16.equals("listSetData")) {
                     var13 = 159;
                     break label3329;
                  }
                  break;
               case -1989678633:
                  if (var16.equals("mapViewSetMarkerVisible")) {
                     var13 = 198;
                     break label3329;
                  }
                  break;
               case -1979147952:
                  if (var16.equals("stringContains")) {
                     var13 = 63;
                     break label3329;
                  }
                  break;
               case -1975568730:
                  if (var16.equals("copyToClipboard")) {
                     var13 = 120;
                     break label3329;
                  }
                  break;
               case -1966668787:
                  if (var16.equals("firebaseauthSignOutUser")) {
                     var13 = 215;
                     break label3329;
                  }
                  break;
               case -1937348542:
                  if (var16.equals("firebaseStartListen")) {
                     var13 = 216;
                     break label3329;
                  }
                  break;
               case -1922362317:
                  if (var16.equals("getExternalStorageDir")) {
                     var13 = 278;
                     break label3329;
                  }
                  break;
               case -1920517885:
                  if (var16.equals("setVarBoolean")) {
                     var13 = 3;
                     break label3329;
                  }
                  break;
               case -1919300188:
                  if (var16.equals("toNumber")) {
                     var13 = 67;
                     break label3329;
                  }
                  break;
               case -1910071024:
                  if (var16.equals("objectanimatorSetDuration")) {
                     var13 = 251;
                     break label3329;
                  }
                  break;
               case -1886802639:
                  if (var16.equals("soundpoolLoad")) {
                     var13 = 238;
                     break label3329;
                  }
                  break;
               case -1834369666:
                  if (var16.equals("setBitmapFileBrightness")) {
                     var13 = 290;
                     break label3329;
                  }
                  break;
               case -1812313351:
                  if (var16.equals("setColorFilter")) {
                     var13 = 117;
                     break label3329;
                  }
                  break;
               case -1778201036:
                  if (var16.equals("listSmoothScrollTo")) {
                     var13 = 166;
                     break label3329;
                  }
                  break;
               case -1776922004:
                  if (var16.equals("toString")) {
                     var13 = 72;
                     break label3329;
                  }
                  break;
               case -1749698255:
                  if (var16.equals("mediaplayerPause")) {
                     var13 = 228;
                     break label3329;
                  }
                  break;
               case -1747734390:
                  if (var16.equals("mediaplayerReset")) {
                     var13 = 232;
                     break label3329;
                  }
                  break;
               case -1746380899:
                  if (var16.equals("mediaplayerStart")) {
                     var13 = 227;
                     break label3329;
                  }
                  break;
               case -1718917155:
                  if (var16.equals("mediaplayerSeek")) {
                     var13 = 229;
                     break label3329;
                  }
                  break;
               case -1699631195:
                  if (var16.equals("isDrawerOpen")) {
                     var13 = 105;
                     break label3329;
                  }
                  break;
               case -1699349926:
                  if (var16.equals("objectanimatorSetRepeatMode")) {
                     var13 = 252;
                     break label3329;
                  }
                  break;
               case -1684072208:
                  if (var16.equals("intentSetData")) {
                     var13 = 123;
                     break label3329;
                  }
                  break;
               case -1679834825:
                  if (var16.equals("setTrackResource")) {
                     var13 = 242;
                     break label3329;
                  }
                  break;
               case -1666623936:
                  if (var16.equals("speechToTextShutdown")) {
                     var13 = 311;
                     break label3329;
                  }
                  break;
               case -1573371685:
                  if (var16.equals("stringJoin")) {
                     var13 = 58;
                     break label3329;
                  }
                  break;
               case -1541653284:
                  if (var16.equals("objectanimatorStart")) {
                     var13 = 255;
                     break label3329;
                  }
                  break;
               case -1530840255:
                  if (var16.equals("stringIndex")) {
                     var13 = 59;
                     break label3329;
                  }
                  break;
               case -1528850031:
                  if (var16.equals("startActivity")) {
                     var13 = 128;
                     break label3329;
                  }
                  break;
               case -1526161572:
                  if (var16.equals("setBgColor")) {
                     var13 = 113;
                     break label3329;
                  }
                  break;
               case -1513446476:
                  if (var16.equals("dialogCancelButton")) {
                     var13 = 224;
                     break label3329;
                  }
                  break;
               case -1512519571:
                  if (var16.equals("definedFunc")) {
                     var13 = 0;
                     break label3329;
                  }
                  break;
               case -1483954587:
                  if (var16.equals("fileutilisdir")) {
                     var13 = 272;
                     break label3329;
                  }
                  break;
               case -1477942289:
                  if (var16.equals("mediaplayerIsLooping")) {
                     var13 = 236;
                     break label3329;
                  }
                  break;
               case -1471049951:
                  if (var16.equals("fileutilwrite")) {
                     var13 = 265;
                     break label3329;
                  }
                  break;
               case -1440042085:
                  if (var16.equals("spnSetSelection")) {
                     var13 = 169;
                     break label3329;
                  }
                  break;
               case -1438040951:
                  if (var16.equals("seekBarGetMax")) {
                     var13 = 246;
                     break label3329;
                  }
                  break;
               case -1422112391:
                  if (var16.equals("bluetoothConnectIsBluetoothEnabled")) {
                     var13 = 318;
                     break label3329;
                  }
                  break;
               case -1405157727:
                  if (var16.equals("fileutilmakedir")) {
                     var13 = 270;
                     break label3329;
                  }
                  break;
               case -1385076635:
                  if (var16.equals("dialogShow")) {
                     var13 = 222;
                     break label3329;
                  }
                  break;
               case -1384861688:
                  if (var16.equals("getAtListInt")) {
                     var13 = 19;
                     break label3329;
                  }
                  break;
               case -1384858251:
                  if (var16.equals("getAtListMap")) {
                     var13 = 29;
                     break label3329;
                  }
                  break;
               case -1384851894:
                  if (var16.equals("getAtListStr")) {
                     var13 = 24;
                     break label3329;
                  }
                  break;
               case -1377080719:
                  if (var16.equals("decreaseInt")) {
                     var13 = 6;
                     break label3329;
                  }
                  break;
               case -1376608975:
                  if (var16.equals("calendarSetTime")) {
                     var13 = 140;
                     break label3329;
                  }
                  break;
               case -1361468284:
                  if (var16.equals("viewOnClick")) {
                     var13 = 104;
                     break label3329;
                  }
                  break;
               case -1348085287:
                  if (var16.equals("mapViewZoomIn")) {
                     var13 = 191;
                     break label3329;
                  }
                  break;
               case -1348084945:
                  if (var16.equals("mapViewZoomTo")) {
                     var13 = 190;
                     break label3329;
                  }
                  break;
               case -1304067438:
                  if (var16.equals("firebaseDelete")) {
                     var13 = 206;
                     break label3329;
                  }
                  break;
               case -1272546178:
                  if (var16.equals("dialogSetTitle")) {
                     var13 = 220;
                     break label3329;
                  }
                  break;
               case -1271141237:
                  if (var16.equals("clearList")) {
                     var13 = 37;
                     break label3329;
                  }
                  break;
               case -1249367264:
                  if (var16.equals("getArg")) {
                     var13 = 1;
                     break label3329;
                  }
                  break;
               case -1249347599:
                  if (var16.equals("getVar")) {
                     var13 = 2;
                     break label3329;
                  }
                  break;
               case -1217704075:
                  if (var16.equals("objectanimatorSetValue")) {
                     var13 = 249;
                     break label3329;
                  }
                  break;
               case -1206794099:
                  if (var16.equals("getLocationX")) {
                     var13 = 155;
                     break label3329;
                  }
                  break;
               case -1206794098:
                  if (var16.equals("getLocationY")) {
                     var13 = 156;
                     break label3329;
                  }
                  break;
               case -1195899442:
                  if (var16.equals("bluetoothConnectSendData")) {
                     var13 = 317;
                     break label3329;
                  }
                  break;
               case -1192544266:
                  if (var16.equals("ifElse")) {
                     var13 = 41;
                     break label3329;
                  }
                  break;
               case -1185284274:
                  if (var16.equals("gyroscopeStopListen")) {
                     var13 = 219;
                     break label3329;
                  }
                  break;
               case -1182878167:
                  if (var16.equals("firebaseauthGetUid")) {
                     var13 = 213;
                     break label3329;
                  }
                  break;
               case -1160374245:
                  if (var16.equals("bluetoothConnectReadyConnectionToUuid")) {
                     var13 = 313;
                     break label3329;
                  }
                  break;
               case -1149848189:
                  if (var16.equals("toStringFormat")) {
                     var13 = 74;
                     break label3329;
                  }
                  break;
               case -1149458632:
                  if (var16.equals("objectanimatorSetRepeatCount")) {
                     var13 = 253;
                     break label3329;
                  }
                  break;
               case -1143684675:
                  if (var16.equals("firebaseauthGetCurrentUser")) {
                     var13 = 212;
                     break label3329;
                  }
                  break;
               case -1139353316:
                  if (var16.equals("setListMap")) {
                     var13 = 30;
                     break label3329;
                  }
                  break;
               case -1137582698:
                  if (var16.equals("toLowerCase")) {
                     var13 = 71;
                     break label3329;
                  }
                  break;
               case -1123431291:
                  if (var16.equals("calnedarViewSetMaxDate")) {
                     var13 = 186;
                     break label3329;
                  }
                  break;
               case -1107376988:
                  if (var16.equals("webViewGoForward")) {
                     var13 = 177;
                     break label3329;
                  }
                  break;
               case -1106141754:
                  if (var16.equals("webViewCanGoBack")) {
                     var13 = 174;
                     break label3329;
                  }
                  break;
               case -1094491139:
                  if (var16.equals("seekBarSetMax")) {
                     var13 = 245;
                     break label3329;
                  }
                  break;
               case -1081400230:
                  if (var16.equals("mapGet")) {
                     var13 = 10;
                     break label3329;
                  }
                  break;
               case -1081391085:
                  if (var16.equals("mapPut")) {
                     var13 = 9;
                     break label3329;
                  }
                  break;
               case -1081250015:
                  if (var16.equals("mathPi")) {
                     var13 = 83;
                     break label3329;
                  }
                  break;
               case -1063598745:
                  if (var16.equals("resizeBitmapFileRetainRatio")) {
                     var13 = 281;
                     break label3329;
                  }
                  break;
               case -1043233275:
                  if (var16.equals("mediaplayerGetDuration")) {
                     var13 = 231;
                     break label3329;
                  }
                  break;
               case -1033658254:
                  if (var16.equals("mathGetDisplayWidth")) {
                     var13 = 81;
                     break label3329;
                  }
                  break;
               case -1021852352:
                  if (var16.equals("objectanimatorCancel")) {
                     var13 = 256;
                     break label3329;
                  }
                  break;
               case -1007787615:
                  if (var16.equals("mediaplayerSetLooping")) {
                     var13 = 235;
                     break label3329;
                  }
                  break;
               case -996870276:
                  if (var16.equals("insertMapToList")) {
                     var13 = 33;
                     break label3329;
                  }
                  break;
               case -995908985:
                  if (var16.equals("soundpoolCreate")) {
                     var13 = 237;
                     break label3329;
                  }
                  break;
               case -941420147:
                  if (var16.equals("fileSetFileName")) {
                     var13 = 130;
                     break label3329;
                  }
                  break;
               case -938285885:
                  if (var16.equals("random")) {
                     var13 = 56;
                     break label3329;
                  }
                  break;
               case -934531685:
                  if (var16.equals("repeat")) {
                     var13 = 39;
                     break label3329;
                  }
                  break;
               case -918173448:
                  if (var16.equals("listGetCheckedPosition")) {
                     var13 = 163;
                     break label3329;
                  }
                  break;
               case -917343271:
                  if (var16.equals("getJpegRotate")) {
                     var13 = 292;
                     break label3329;
                  }
                  break;
               case -911199919:
                  if (var16.equals("objectanimatorSetProperty")) {
                     var13 = 248;
                     break label3329;
                  }
                  break;
               case -903177036:
                  if (var16.equals("resizeBitmapFileWithRoundedBorder")) {
                     var13 = 284;
                     break label3329;
                  }
                  break;
               case -883988307:
                  if (var16.equals("dialogSetMessage")) {
                     var13 = 221;
                     break label3329;
                  }
                  break;
               case -869293886:
                  if (var16.equals("finishActivity")) {
                     var13 = 129;
                     break label3329;
                  }
                  break;
               case -854558288:
                  if (var16.equals("setVisible")) {
                     var13 = 141;
                     break label3329;
                  }
                  break;
               case -853550561:
                  if (var16.equals("timerCancel")) {
                     var13 = 202;
                     break label3329;
                  }
                  break;
               case -831887360:
                  if (var16.equals("textToSpeechShutdown")) {
                     var13 = 308;
                     break label3329;
                  }
                  break;
               case -733318734:
                  if (var16.equals("strToListMap")) {
                     var13 = 78;
                     break label3329;
                  }
                  break;
               case -697616870:
                  if (var16.equals("camerastarttakepicture")) {
                     var13 = 294;
                     break label3329;
                  }
                  break;
               case -677662361:
                  if (var16.equals("forever")) {
                     var13 = 38;
                     break label3329;
                  }
                  break;
               case -668992194:
                  if (var16.equals("stringReplaceAll")) {
                     var13 = 66;
                     break label3329;
                  }
                  break;
               case -664474111:
                  if (var16.equals("intentSetFlags")) {
                     var13 = 126;
                     break label3329;
                  }
                  break;
               case -649691581:
                  if (var16.equals("objectanimatorSetInterpolator")) {
                     var13 = 254;
                     break label3329;
                  }
                  break;
               case -636363854:
                  if (var16.equals("webViewGetUrl")) {
                     var13 = 172;
                     break label3329;
                  }
                  break;
               case -628607128:
                  if (var16.equals("webViewGoBack")) {
                     var13 = 176;
                     break label3329;
                  }
                  break;
               case -621198621:
                  if (var16.equals("speechToTextStartListening")) {
                     var13 = 309;
                     break label3329;
                  }
                  break;
               case -602241037:
                  if (var16.equals("fileutilcopy")) {
                     var13 = 266;
                     break label3329;
                  }
                  break;
               case -601942961:
                  if (var16.equals("fileutilmove")) {
                     var13 = 267;
                     break label3329;
                  }
                  break;
               case -601804268:
                  if (var16.equals("fileutilread")) {
                     var13 = 264;
                     break label3329;
                  }
                  break;
               case -578987803:
                  if (var16.equals("setChecked")) {
                     var13 = 157;
                     break label3329;
                  }
                  break;
               case -509946902:
                  if (var16.equals("spnRefresh")) {
                     var13 = 168;
                     break label3329;
                  }
                  break;
               case -439342016:
                  if (var16.equals("webViewClearHistory")) {
                     var13 = 179;
                     break label3329;
                  }
                  break;
               case -437272040:
                  if (var16.equals("bluetoothConnectGetRandomUuid")) {
                     var13 = 322;
                     break label3329;
                  }
                  break;
               case -425293664:
                  if (var16.equals("setClickable")) {
                     var13 = 142;
                     break label3329;
                  }
                  break;
               case -418212114:
                  if (var16.equals("firebaseGetChildren")) {
                     var13 = 207;
                     break label3329;
                  }
                  break;
               case -411705840:
                  if (var16.equals("fileSetData")) {
                     var13 = 132;
                     break label3329;
                  }
                  break;
               case -399551817:
                  if (var16.equals("toUpperCase")) {
                     var13 = 70;
                     break label3329;
                  }
                  break;
               case -390304998:
                  if (var16.equals("mapViewAddMarker")) {
                     var13 = 193;
                     break label3329;
                  }
                  break;
               case -356866884:
                  if (var16.equals("webViewSetCacheMode")) {
                     var13 = 173;
                     break label3329;
                  }
                  break;
               case -353129373:
                  if (var16.equals("calendarDiff")) {
                     var13 = 138;
                     break label3329;
                  }
                  break;
               case -329562760:
                  if (var16.equals("insertListInt")) {
                     var13 = 18;
                     break label3329;
                  }
                  break;
               case -329559323:
                  if (var16.equals("insertListMap")) {
                     var13 = 28;
                     break label3329;
                  }
                  break;
               case -329552966:
                  if (var16.equals("insertListStr")) {
                     var13 = 23;
                     break label3329;
                  }
                  break;
               case -322651344:
                  if (var16.equals("stringEquals")) {
                     var13 = 62;
                     break label3329;
                  }
                  break;
               case -283328259:
                  if (var16.equals("intentPutExtra")) {
                     var13 = 125;
                     break label3329;
                  }
                  break;
               case -258774775:
                  if (var16.equals("closeDrawer")) {
                     var13 = 107;
                     break label3329;
                  }
                  break;
               case -247015294:
                  if (var16.equals("mediaplayerRelease")) {
                     var13 = 233;
                     break label3329;
                  }
                  break;
               case -208762465:
                  if (var16.equals("toStringWithDecimal")) {
                     var13 = 73;
                     break label3329;
                  }
                  break;
               case -189292433:
                  if (var16.equals("stringSub")) {
                     var13 = 61;
                     break label3329;
                  }
                  break;
               case -152473824:
                  if (var16.equals("firebaseauthIsLoggedIn")) {
                     var13 = 211;
                     break label3329;
                  }
                  break;
               case -149850417:
                  if (var16.equals("fileutilisexist")) {
                     var13 = 269;
                     break label3329;
                  }
                  break;
               case -133532073:
                  if (var16.equals("stringLength")) {
                     var13 = 57;
                     break label3329;
                  }
                  break;
               case -96313603:
                  if (var16.equals("containListInt")) {
                     var13 = 21;
                     break label3329;
                  }
                  break;
               case -96310166:
                  if (var16.equals("containListMap")) {
                     var13 = 31;
                     break label3329;
                  }
                  break;
               case -96303809:
                  if (var16.equals("containListStr")) {
                     var13 = 26;
                     break label3329;
                  }
                  break;
               case -83301935:
                  if (var16.equals("webViewZoomIn")) {
                     var13 = 181;
                     break label3329;
                  }
                  break;
               case -83186725:
                  if (var16.equals("openDrawer")) {
                     var13 = 106;
                     break label3329;
                  }
                  break;
               case -75125341:
                  if (var16.equals("getText")) {
                     var13 = 112;
                     break label3329;
                  }
                  break;
               case -60494417:
                  if (var16.equals("vibratorAction")) {
                     var13 = 199;
                     break label3329;
                  }
                  break;
               case -60048101:
                  if (var16.equals("firebaseauthResetPassword")) {
                     var13 = 214;
                     break label3329;
                  }
                  break;
               case -24451690:
                  if (var16.equals("dialogOkButton")) {
                     var13 = 223;
                     break label3329;
                  }
                  break;
               case -14362103:
                  if (var16.equals("bluetoothConnectIsBluetoothActivated")) {
                     var13 = 319;
                     break label3329;
                  }
                  break;
               case -10599306:
                  if (var16.equals("firebaseauthCreateUser")) {
                     var13 = 208;
                     break label3329;
                  }
                  break;
               case -9742826:
                  if (var16.equals("firebaseGetPushKey")) {
                     var13 = 205;
                     break label3329;
                  }
                  break;
               case 37:
                  if (var16.equals("%")) {
                     var13 = 50;
                     break label3329;
                  }
                  break;
               case 42:
                  if (var16.equals("*")) {
                     var13 = 48;
                     break label3329;
                  }
                  break;
               case 43:
                  if (var16.equals("+")) {
                     var13 = 46;
                     break label3329;
                  }
                  break;
               case 45:
                  if (var16.equals("-")) {
                     var13 = 47;
                     break label3329;
                  }
                  break;
               case 47:
                  if (var16.equals("/")) {
                     var13 = 49;
                     break label3329;
                  }
                  break;
               case 60:
                  if (var16.equals("<")) {
                     var13 = 52;
                     break label3329;
                  }
                  break;
               case 61:
                  if (var16.equals("=")) {
                     var13 = 53;
                     break label3329;
                  }
                  break;
               case 62:
                  if (var16.equals(">")) {
                     var13 = 51;
                     break label3329;
                  }
                  break;
               case 1216:
                  if (var16.equals("&&")) {
                     var13 = 54;
                     break label3329;
                  }
                  break;
               case 3357:
                  if (var16.equals("if")) {
                     var13 = 40;
                     break label3329;
                  }
                  break;
               case 3968:
                  if (var16.equals("||")) {
                     var13 = 55;
                     break label3329;
                  }
                  break;
               case 109267:
                  if (var16.equals("not")) {
                     var13 = 45;
                     break label3329;
                  }
                  break;
               case 3568674:
                  if (var16.equals("trim")) {
                     var13 = 69;
                     break label3329;
                  }
                  break;
               case 3569038:
                  if (var16.equals("true")) {
                     var13 = 43;
                     break label3329;
                  }
                  break;
               case 8255701:
                  if (var16.equals("calendarFormat")) {
                     var13 = 137;
                     break label3329;
                  }
                  break;
               case 16308074:
                  if (var16.equals("resizeBitmapFileToCircle")) {
                     var13 = 283;
                     break label3329;
                  }
                  break;
               case 25469951:
                  if (var16.equals("bluetoothConnectActivateBluetooth")) {
                     var13 = 320;
                     break label3329;
                  }
                  break;
               case 27679870:
                  if (var16.equals("calendarGetNow")) {
                     var13 = 134;
                     break label3329;
                  }
                  break;
               case 56167279:
                  if (var16.equals("setBitmapFileContrast")) {
                     var13 = 291;
                     break label3329;
                  }
                  break;
               case 61585857:
                  if (var16.equals("firebasePush")) {
                     var13 = 204;
                     break label3329;
                  }
                  break;
               case 94001407:
                  if (var16.equals("break")) {
                     var13 = 42;
                     break label3329;
                  }
                  break;
               case 97196323:
                  if (var16.equals("false")) {
                     var13 = 44;
                     break label3329;
                  }
                  break;
               case 103668285:
                  if (var16.equals("mathE")) {
                     var13 = 84;
                     break label3329;
                  }
                  break;
               case 125431087:
                  if (var16.equals("speechToTextStopListening")) {
                     var13 = 310;
                     break label3329;
                  }
                  break;
               case 134874756:
                  if (var16.equals("listSetCustomViewData")) {
                     var13 = 160;
                     break label3329;
                  }
                  break;
               case 152967761:
                  if (var16.equals("mapClear")) {
                     var13 = 14;
                     break label3329;
                  }
                  break;
               case 163812602:
                  if (var16.equals("cropBitmapFileFromCenter")) {
                     var13 = 285;
                     break label3329;
                  }
                  break;
               case 168740282:
                  if (var16.equals("mapToStr")) {
                     var13 = 77;
                     break label3329;
                  }
                  break;
               case 182549637:
                  if (var16.equals("setEnable")) {
                     var13 = 108;
                     break label3329;
                  }
                  break;
               case 207764385:
                  if (var16.equals("calendarViewGetDate")) {
                     var13 = 183;
                     break label3329;
                  }
                  break;
               case 255417137:
                  if (var16.equals("adViewLoadAd")) {
                     var13 = 187;
                     break label3329;
                  }
                  break;
               case 262073061:
                  if (var16.equals("bluetoothConnectReadyConnection")) {
                     var13 = 312;
                     break label3329;
                  }
                  break;
               case 276674391:
                  if (var16.equals("mapViewMoveCamera")) {
                     var13 = 189;
                     break label3329;
                  }
                  break;
               case 297379706:
                  if (var16.equals("textToSpeechSetSpeechRate")) {
                     var13 = 304;
                     break label3329;
                  }
                  break;
               case 300372142:
                  if (var16.equals("mathAcos")) {
                     var13 = 97;
                     break label3329;
                  }
                  break;
               case 300387327:
                  if (var16.equals("mathAsin")) {
                     var13 = 96;
                     break label3329;
                  }
                  break;
               case 300388040:
                  if (var16.equals("mathAtan")) {
                     var13 = 98;
                     break label3329;
                  }
                  break;
               case 300433453:
                  if (var16.equals("mathCeil")) {
                     var13 = 91;
                     break label3329;
                  }
                  break;
               case 300921928:
                  if (var16.equals("mathSqrt")) {
                     var13 = 88;
                     break label3329;
                  }
                  break;
               case 317453636:
                  if (var16.equals("textToSpeechIsSpeaking")) {
                     var13 = 306;
                     break label3329;
                  }
                  break;
               case 342026220:
                  if (var16.equals("interstitialadShow")) {
                     var13 = 260;
                     break label3329;
                  }
                  break;
               case 348377823:
                  if (var16.equals("soundpoolStreamPlay")) {
                     var13 = 239;
                     break label3329;
                  }
                  break;
               case 348475309:
                  if (var16.equals("soundpoolStreamStop")) {
                     var13 = 240;
                     break label3329;
                  }
                  break;
               case 389111867:
                  if (var16.equals("spnSetData")) {
                     var13 = 167;
                     break label3329;
                  }
                  break;
               case 397166713:
                  if (var16.equals("getEnable")) {
                     var13 = 109;
                     break label3329;
                  }
                  break;
               case 401012285:
                  if (var16.equals("getTranslationX")) {
                     var13 = 148;
                     break label3329;
                  }
                  break;
               case 401012286:
                  if (var16.equals("getTranslationY")) {
                     var13 = 150;
                     break label3329;
                  }
                  break;
               case 404247683:
                  if (var16.equals("calendarAdd")) {
                     var13 = 135;
                     break label3329;
                  }
                  break;
               case 404265028:
                  if (var16.equals("calendarSet")) {
                     var13 = 136;
                     break label3329;
                  }
                  break;
               case 442768763:
                  if (var16.equals("mapGetAllKeys")) {
                     var13 = 16;
                     break label3329;
                  }
                  break;
               case 463560551:
                  if (var16.equals("mapContainKey")) {
                     var13 = 11;
                     break label3329;
                  }
                  break;
               case 463594049:
                  if (var16.equals("objectanimatorSetFromTo")) {
                     var13 = 250;
                     break label3329;
                  }
                  break;
               case 470160234:
                  if (var16.equals("fileutilGetLastSegmentPath")) {
                     var13 = 277;
                     break label3329;
                  }
                  break;
               case 475815924:
                  if (var16.equals("setTextColor")) {
                     var13 = 115;
                     break label3329;
                  }
                  break;
               case 481850295:
                  if (var16.equals("resizeBitmapFileToSquare")) {
                     var13 = 282;
                     break label3329;
                  }
                  break;
               case 490702942:
                  if (var16.equals("filepickerstartpickfiles")) {
                     var13 = 293;
                     break label3329;
                  }
                  break;
               case 501171279:
                  if (var16.equals("mathToDegree")) {
                     var13 = 103;
                     break label3329;
                  }
                  break;
               case 530759231:
                  if (var16.equals("progressBarSetIndeterminate")) {
                     var13 = 302;
                     break label3329;
                  }
                  break;
               case 548860462:
                  if (var16.equals("webViewClearCache")) {
                     var13 = 178;
                     break label3329;
                  }
                  break;
               case 556217437:
                  if (var16.equals("setRotate")) {
                     var13 = 143;
                     break label3329;
                  }
                  break;
               case 571046965:
                  if (var16.equals("scaleBitmapFile")) {
                     var13 = 287;
                     break label3329;
                  }
                  break;
               case 573208400:
                  if (var16.equals("setScaleX")) {
                     var13 = 151;
                     break label3329;
                  }
                  break;
               case 573208401:
                  if (var16.equals("setScaleY")) {
                     var13 = 153;
                     break label3329;
                  }
                  break;
               case 573295520:
                  if (var16.equals("listGetCheckedCount")) {
                     var13 = 165;
                     break label3329;
                  }
                  break;
               case 601235430:
                  if (var16.equals("currentTime")) {
                     var13 = 68;
                     break label3329;
                  }
                  break;
               case 610313513:
                  if (var16.equals("getMapInList")) {
                     var13 = 34;
                     break label3329;
                  }
                  break;
               case 615286641:
                  if (var16.equals("dialogNeutralButton")) {
                     var13 = 225;
                     break label3329;
                  }
                  break;
               case 657721930:
                  if (var16.equals("setVarInt")) {
                     var13 = 4;
                     break label3329;
                  }
                  break;
               case 683193060:
                  if (var16.equals("bluetoothConnectStartConnection")) {
                     var13 = 314;
                     break label3329;
                  }
                  break;
               case 725249532:
                  if (var16.equals("intentSetAction")) {
                     var13 = 122;
                     break label3329;
                  }
                  break;
               case 726487524:
                  if (var16.equals("mathFloor")) {
                     var13 = 92;
                     break label3329;
                  }
                  break;
               case 726877492:
                  if (var16.equals("mapViewSetMarkerIcon")) {
                     var13 = 197;
                     break label3329;
                  }
                  break;
               case 726887785:
                  if (var16.equals("mapViewSetMarkerInfo")) {
                     var13 = 194;
                     break label3329;
                  }
                  break;
               case 732108347:
                  if (var16.equals("mathLog10")) {
                     var13 = 101;
                     break label3329;
                  }
                  break;
               case 737664870:
                  if (var16.equals("mathRound")) {
                     var13 = 90;
                     break label3329;
                  }
                  break;
               case 738846120:
                  if (var16.equals("textToSpeechSetPitch")) {
                     var13 = 303;
                     break label3329;
                  }
                  break;
               case 747168008:
                  if (var16.equals("mapCreateNew")) {
                     var13 = 8;
                     break label3329;
                  }
                  break;
               case 754442829:
                  if (var16.equals("increaseInt")) {
                     var13 = 5;
                     break label3329;
                  }
                  break;
               case 762282303:
                  if (var16.equals("indexListInt")) {
                     var13 = 20;
                     break label3329;
                  }
                  break;
               case 762292097:
                  if (var16.equals("indexListStr")) {
                     var13 = 25;
                     break label3329;
                  }
                  break;
               case 770834513:
                  if (var16.equals("getRotate")) {
                     var13 = 144;
                     break label3329;
                  }
                  break;
               case 787825476:
                  if (var16.equals("getScaleX")) {
                     var13 = 152;
                     break label3329;
                  }
                  break;
               case 787825477:
                  if (var16.equals("getScaleY")) {
                     var13 = 154;
                     break label3329;
                  }
                  break;
               case 797861524:
                  if (var16.equals("addMapToList")) {
                     var13 = 32;
                     break label3329;
                  }
                  break;
               case 836692861:
                  if (var16.equals("mapSize")) {
                     var13 = 13;
                     break label3329;
                  }
                  break;
               case 840973386:
                  if (var16.equals("mathAbs")) {
                     var13 = 89;
                     break label3329;
                  }
                  break;
               case 840975711:
                  if (var16.equals("mathCos")) {
                     var13 = 94;
                     break label3329;
                  }
                  break;
               case 840977909:
                  if (var16.equals("mathExp")) {
                     var13 = 99;
                     break label3329;
                  }
                  break;
               case 840984348:
                  if (var16.equals("mathLog")) {
                     var13 = 100;
                     break label3329;
                  }
                  break;
               case 840984892:
                  if (var16.equals("mathMax")) {
                     var13 = 87;
                     break label3329;
                  }
                  break;
               case 840985130:
                  if (var16.equals("mathMin")) {
                     var13 = 86;
                     break label3329;
                  }
                  break;
               case 840988208:
                  if (var16.equals("mathPow")) {
                     var13 = 85;
                     break label3329;
                  }
                  break;
               case 840990896:
                  if (var16.equals("mathSin")) {
                     var13 = 93;
                     break label3329;
                  }
                  break;
               case 840991609:
                  if (var16.equals("mathTan")) {
                     var13 = 95;
                     break label3329;
                  }
                  break;
               case 845089750:
                  if (var16.equals("setVarString")) {
                     var13 = 7;
                     break label3329;
                  }
                  break;
               case 848786445:
                  if (var16.equals("objectanimatorSetTarget")) {
                     var13 = 247;
                     break label3329;
                  }
                  break;
               case 858248741:
                  if (var16.equals("calendarGetTime")) {
                     var13 = 139;
                     break label3329;
                  }
                  break;
               case 898187172:
                  if (var16.equals("mathToRadian")) {
                     var13 = 102;
                     break label3329;
                  }
                  break;
               case 932259189:
                  if (var16.equals("setBgResource")) {
                     var13 = 114;
                     break label3329;
                  }
                  break;
               case 937017988:
                  if (var16.equals("gyroscopeStartListen")) {
                     var13 = 218;
                     break label3329;
                  }
                  break;
               case 948234497:
                  if (var16.equals("webViewStopLoading")) {
                     var13 = 180;
                     break label3329;
                  }
                  break;
               case 950609198:
                  if (var16.equals("setBitmapFileColorFilter")) {
                     var13 = 289;
                     break label3329;
                  }
                  break;
               case 1053179400:
                  if (var16.equals("mapViewSetMarkerColor")) {
                     var13 = 196;
                     break label3329;
                  }
                  break;
               case 1068548733:
                  if (var16.equals("mathGetDip")) {
                     var13 = 80;
                     break label3329;
                  }
                  break;
               case 1086207657:
                  if (var16.equals("fileutildelete")) {
                     var13 = 268;
                     break label3329;
                  }
                  break;
               case 1088879149:
                  if (var16.equals("setHintTextColor")) {
                     var13 = 298;
                     break label3329;
                  }
                  break;
               case 1090517587:
                  if (var16.equals("getPackageDataDir")) {
                     var13 = 279;
                     break label3329;
                  }
                  break;
               case 1102670563:
                  if (var16.equals("requestnetworkSetHeaders")) {
                     var13 = 300;
                     break label3329;
                  }
                  break;
               case 1129709718:
                  if (var16.equals("setImageUrl")) {
                     var13 = 296;
                     break label3329;
                  }
                  break;
               case 1142897724:
                  if (var16.equals("firebaseauthSignInUser")) {
                     var13 = 209;
                     break label3329;
                  }
                  break;
               case 1156598140:
                  if (var16.equals("fileutilEndsWith")) {
                     var13 = 276;
                     break label3329;
                  }
                  break;
               case 1159035162:
                  if (var16.equals("mapViewZoomOut")) {
                     var13 = 192;
                     break label3329;
                  }
                  break;
               case 1160674468:
                  if (var16.equals("lengthList")) {
                     var13 = 36;
                     break label3329;
                  }
                  break;
               case 1162069698:
                  if (var16.equals("setThumbResource")) {
                     var13 = 241;
                     break label3329;
                  }
                  break;
               case 1179719371:
                  if (var16.equals("stringLastIndex")) {
                     var13 = 60;
                     break label3329;
                  }
                  break;
               case 1187505507:
                  if (var16.equals("stringReplace")) {
                     var13 = 64;
                     break label3329;
                  }
                  break;
               case 1216249183:
                  if (var16.equals("firebasestorageDelete")) {
                     var13 = 263;
                     break label3329;
                  }
                  break;
               case 1219071185:
                  if (var16.equals("firebasestorageUploadFile")) {
                     var13 = 261;
                     break label3329;
                  }
                  break;
               case 1219299503:
                  if (var16.equals("objectanimatorIsRunning")) {
                     var13 = 257;
                     break label3329;
                  }
                  break;
               case 1220078450:
                  if (var16.equals("addSourceDirectly")) {
                     var13 = 75;
                     break label3329;
                  }
                  break;
               case 1236956449:
                  if (var16.equals("mediaplayerCreate")) {
                     var13 = 226;
                     break label3329;
                  }
                  break;
               case 1240510514:
                  if (var16.equals("intentSetScreen")) {
                     var13 = 124;
                     break label3329;
                  }
                  break;
               case 1242107556:
                  if (var16.equals("fileutilisfile")) {
                     var13 = 273;
                     break label3329;
                  }
                  break;
               case 1252547704:
                  if (var16.equals("listMapToStr")) {
                     var13 = 79;
                     break label3329;
                  }
                  break;
               case 1280029577:
                  if (var16.equals("requestFocus")) {
                     var13 = 118;
                     break label3329;
                  }
                  break;
               case 1303367340:
                  if (var16.equals("textToSpeechStop")) {
                     var13 = 307;
                     break label3329;
                  }
                  break;
               case 1305932583:
                  if (var16.equals("spnGetSelection")) {
                     var13 = 170;
                     break label3329;
                  }
                  break;
               case 1311764809:
                  if (var16.equals("setTranslationX")) {
                     var13 = 147;
                     break label3329;
                  }
                  break;
               case 1311764810:
                  if (var16.equals("setTranslationY")) {
                     var13 = 149;
                     break label3329;
                  }
                  break;
               case 1313527577:
                  if (var16.equals("setTypeface")) {
                     var13 = 111;
                     break label3329;
                  }
                  break;
               case 1315302372:
                  if (var16.equals("fileutillength")) {
                     var13 = 274;
                     break label3329;
                  }
                  break;
               case 1330354473:
                  if (var16.equals("firebaseauthSignInAnonymously")) {
                     var13 = 210;
                     break label3329;
                  }
                  break;
               case 1343794064:
                  if (var16.equals("listSetItemChecked")) {
                     var13 = 162;
                     break label3329;
                  }
                  break;
               case 1348133645:
                  if (var16.equals("stringReplaceFirst")) {
                     var13 = 65;
                     break label3329;
                  }
                  break;
               case 1387622940:
                  if (var16.equals("setAlpha")) {
                     var13 = 145;
                     break label3329;
                  }
                  break;
               case 1395026457:
                  if (var16.equals("setImage")) {
                     var13 = 116;
                     break label3329;
                  }
                  break;
               case 1397501021:
                  if (var16.equals("listRefresh")) {
                     var13 = 161;
                     break label3329;
                  }
                  break;
               case 1405084438:
                  if (var16.equals("setTitle")) {
                     var13 = 121;
                     break label3329;
                  }
                  break;
               case 1410284340:
                  if (var16.equals("seekBarSetProgress")) {
                     var13 = 243;
                     break label3329;
                  }
                  break;
               case 1431171391:
                  if (var16.equals("mapRemoveKey")) {
                     var13 = 12;
                     break label3329;
                  }
                  break;
               case 1437288110:
                  if (var16.equals("getPublicDir")) {
                     var13 = 280;
                     break label3329;
                  }
                  break;
               case 1470831563:
                  if (var16.equals("intentGetString")) {
                     var13 = 127;
                     break label3329;
                  }
                  break;
               case 1498864168:
                  if (var16.equals("seekBarGetProgress")) {
                     var13 = 244;
                     break label3329;
                  }
                  break;
               case 1601394299:
                  if (var16.equals("listGetCheckedPositions")) {
                     var13 = 164;
                     break label3329;
                  }
                  break;
               case 1633341847:
                  if (var16.equals("timerAfter")) {
                     var13 = 200;
                     break label3329;
                  }
                  break;
               case 1635356258:
                  if (var16.equals("requestnetworkStartRequestNetwork")) {
                     var13 = 301;
                     break label3329;
                  }
                  break;
               case 1637498582:
                  if (var16.equals("timerEvery")) {
                     var13 = 201;
                     break label3329;
                  }
                  break;
               case 1695890133:
                  if (var16.equals("fileutilStartsWith")) {
                     var13 = 275;
                     break label3329;
                  }
                  break;
               case 1712613410:
                  if (var16.equals("webViewZoomOut")) {
                     var13 = 182;
                     break label3329;
                  }
                  break;
               case 1749552744:
                  if (var16.equals("textToSpeechSpeak")) {
                     var13 = 305;
                     break label3329;
                  }
                  break;
               case 1764351209:
                  if (var16.equals("deleteList")) {
                     var13 = 35;
                     break label3329;
                  }
                  break;
               case 1775620400:
                  if (var16.equals("strToMap")) {
                     var13 = 76;
                     break label3329;
                  }
                  break;
               case 1779174257:
                  if (var16.equals("getChecked")) {
                     var13 = 158;
                     break label3329;
                  }
                  break;
               case 1792552710:
                  if (var16.equals("rotateBitmapFile")) {
                     var13 = 286;
                     break label3329;
                  }
                  break;
               case 1814870108:
                  if (var16.equals("doToast")) {
                     var13 = 119;
                     break label3329;
                  }
                  break;
               case 1820536363:
                  if (var16.equals("interstitialadCreate")) {
                     var13 = 258;
                     break label3329;
                  }
                  break;
               case 1823151876:
                  if (var16.equals("fileGetData")) {
                     var13 = 131;
                     break label3329;
                  }
                  break;
               case 1848365301:
                  if (var16.equals("mapViewSetMapType")) {
                     var13 = 188;
                     break label3329;
                  }
                  break;
               case 1873103950:
                  if (var16.equals("locationManagerRemoveUpdates")) {
                     var13 = 324;
                     break label3329;
                  }
                  break;
               case 1883337723:
                  if (var16.equals("mathGetDisplayHeight")) {
                     var13 = 82;
                     break label3329;
                  }
                  break;
               case 1885231494:
                  if (var16.equals("webViewCanGoForward")) {
                     var13 = 175;
                     break label3329;
                  }
                  break;
               case 1908132964:
                  if (var16.equals("mapViewSetMarkerPosition")) {
                     var13 = 195;
                     break label3329;
                  }
                  break;
               case 1908582864:
                  if (var16.equals("firebaseStopListen")) {
                     var13 = 217;
                     break label3329;
                  }
                  break;
               case 1923980937:
                  if (var16.equals("requestnetworkSetParams")) {
                     var13 = 299;
                     break label3329;
                  }
                  break;
               case 1941634330:
                  if (var16.equals("firebaseAdd")) {
                     var13 = 203;
                     break label3329;
                  }
                  break;
               case 1948735400:
                  if (var16.equals("getAlpha")) {
                     var13 = 146;
                     break label3329;
                  }
                  break;
               case 1964823036:
                  if (var16.equals("bluetoothConnectStopConnection")) {
                     var13 = 316;
                     break label3329;
                  }
                  break;
               case 1973523807:
                  if (var16.equals("mediaplayerIsPlaying")) {
                     var13 = 234;
                     break label3329;
                  }
                  break;
               case 1974249461:
                  if (var16.equals("skewBitmapFile")) {
                     var13 = 288;
                     break label3329;
                  }
                  break;
               case 1976325370:
                  if (var16.equals("setImageFilePath")) {
                     var13 = 295;
                     break label3329;
                  }
                  break;
               case 1984630281:
                  if (var16.equals("setHint")) {
                     var13 = 297;
                     break label3329;
                  }
                  break;
               case 1984984239:
                  if (var16.equals("setText")) {
                     var13 = 110;
                     break label3329;
                  }
                  break;
               case 2017929665:
                  if (var16.equals("calendarViewSetMinDate")) {
                     var13 = 185;
                     break label3329;
                  }
                  break;
               case 2075310296:
                  if (var16.equals("interstitialadLoadAd")) {
                     var13 = 259;
                     break label3329;
                  }
                  break;
               case 2090179216:
                  if (var16.equals("addListInt")) {
                     var13 = 17;
                     break label3329;
                  }
                  break;
               case 2090182653:
                  if (var16.equals("addListMap")) {
                     var13 = 27;
                     break label3329;
                  }
                  break;
               case 2090189010:
                  if (var16.equals("addListStr")) {
                     var13 = 22;
                     break label3329;
                  }
                  break;
               case 2127377128:
                  if (var16.equals("mediaplayerGetCurrent")) {
                     var13 = 230;
                     break label3329;
                  }
                  break;
               case 2130649194:
                  if (var16.equals("bluetoothConnectGetPairedDevices")) {
                     var13 = 321;
                     break label3329;
                  }
                  break;
               case 2138225950:
                  if (var16.equals("locationManagerRequestLocationUpdates")) {
                     var13 = 323;
                     break label3329;
                  }
               }

               var13 = -1;
            }

            String var8;
            String var9;
            String var11;
            String var17;
            StringBuilder var22;
            StringBuilder var23;
            StringBuilder var24;
            int var27;
            StringBuilder var28;
            switch(var13) {
            case 0:
               if (var1.parameters.size() <= 0) {
                  var27 = var1.spec.indexOf(" ");
                  if (var27 < 0) {
                     var28 = new StringBuilder();
                     var28.append("_");
                     var28.append(var1.spec);
                     var28.append("();");
                     var16 = var28.toString();
                  } else {
                     var28 = new StringBuilder();
                     var28.append("_");
                     var28.append(var1.spec.substring(0, var27));
                     var28.append("();");
                     var16 = var28.toString();
                  }
               } else {
                  var27 = var1.spec.indexOf(" ");
                  var7 = var1.spec.substring(0, var27);
                  var28 = new StringBuilder();
                  var28.append("_");
                  var28.append(var7);
                  var28.append("(");
                  var16 = var28.toString();
                  var27 = 0;
                  boolean var14 = true;

                  boolean var15;
                  for(var15 = false; var27 < var10.size(); var14 = false) {
                     var7 = var16;
                     if (!var14) {
                        var24 = new StringBuilder();
                        var24.append(var16);
                        var24.append(", ");
                        var7 = var24.toString();
                     }

                     var16 = (String)var10.get(var27);
                     if (var16.length() <= 0) {
                        hc var26 = (hc)var1.getParamClassInfo().get(var27);
                        if (var26.b("boolean")) {
                           var28 = new StringBuilder();
                           var28.append(var7);
                           var28.append("true");
                           var16 = var28.toString();
                        } else if (var26.b("double")) {
                           var28 = new StringBuilder();
                           var28.append(var7);
                           var28.append("0");
                           var16 = var28.toString();
                        } else {
                           var16 = var7;
                           if (var26.b("String")) {
                              var15 = true;
                              var16 = var7;
                           }
                        }
                     } else {
                        var23 = new StringBuilder();
                        var23.append(var7);
                        var23.append(var16);
                        var16 = var23.toString();
                     }

                     ++var27;
                  }

                  var24 = new StringBuilder();
                  var24.append(var16);
                  var24.append(");");
                  var16 = var24.toString();
                  if (var15) {
                     var16 = "";
                  }
               }
               break;
            case 1:
               var16 = var1.spec;
               var24 = new StringBuilder();
               var24.append("_");
               var24.append(var16);
               var16 = var24.toString();
               break;
            case 2:
               var16 = var1.spec;
               break;
            case 3:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "false";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s = %s;", var8, var16);
               }
               break;
            case 4:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s = %s;", var8, var16);
               }
               break;
            case 5:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s++;", var16);
               }
               break;
            case 6:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s--;", var16);
               }
               break;
            case 7:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s = %s;", var16, var7);
               }
               break;
            case 8:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s = new HashMap<>();", var16);
               }
               break;
            case 9:
               var9 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "";
               }

               if (var9.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.put(%s, %s);", var9, var16, var7);
               }
               break;
            case 10:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.get(%s).toString()", var8, var16);
               }
               break;
            case 11:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.containsKey(%s)", var8, var16);
               }
               break;
            case 12:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.remove(%s);", var8, var16);
               }
               break;
            case 13:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.size()", var16);
               }
               break;
            case 14:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.clear();", var16);
               }
               break;
            case 15:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.isEmpty()", var16);
               }
               break;
            case 16:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("SketchwareUtil.getAllKeysFromMap(%s, %s);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 17:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.add(Double.valueOf(%s));", var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 18:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               if (var16.length() > 0 && var7.length() > 0 && var8.length() > 0) {
                  var16 = String.format("%s.add((int)(%s), Double.valueOf(%s));", var8, var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 19:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.get((int)(%s)).doubleValue()", var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 20:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.indexOf(%s)", var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 21:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.contains(%s)", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 22:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.add(%s);", var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 23:
               var7 = (String)var10.get(0);
               var16 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               if (var7.length() > 0 && var16.length() > 0 && var8.length() > 0) {
                  var16 = String.format("%s.add((int)(%s), %s);", var8, var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 24:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.get((int)(%s))", var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 25:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.indexOf(%s)", var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 26:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.contains(%s)", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 27:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var9 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "";
               }

               if (var9.length() <= 0) {
                  var16 = "";
               } else {
                  var23 = new StringBuilder();
                  var23.append("{\r\n");
                  var23.append(String.format("HashMap<String, Object> _item = new HashMap<>();"));
                  var23.append("\r\n");
                  var23.append(String.format("_item.put(%s, %s);", var16, var7));
                  var23.append("\r\n");
                  var23.append(String.format("%s.add(_item);", var9));
                  var23.append("\r\n");
                  var23.append("}");
                  var23.append("\r\n");
                  var16 = var23.toString();
               }
               break;
            case 28:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var9 = (String)var10.get(2);
               var17 = (String)var10.get(3);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "";
               }

               var8 = var9;
               if (var9.length() <= 0) {
                  var8 = "0";
               }

               if (var17.length() <= 0) {
                  var16 = "";
               } else {
                  var22 = new StringBuilder();
                  var22.append("{\r\n");
                  var22.append(String.format("HashMap<String, Object> _item = new HashMap<>();"));
                  var22.append("\r\n");
                  var22.append(String.format("_item.put(%s, %s);", var16, var7));
                  var22.append("\r\n");
                  var22.append(String.format("%s.add((int)%s, _item);", var17, var8));
                  var22.append("\r\n");
                  var22.append("}");
                  var22.append("\r\n");
                  var16 = var22.toString();
               }
               break;
            case 29:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var9 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "";
               }

               if (var9.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.get((int)%s).get(%s).toString()", var9, var16, var7);
               }
               break;
            case 30:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var9 = (String)var10.get(2);
               var17 = (String)var10.get(3);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "";
               }

               var8 = var9;
               if (var9.length() <= 0) {
                  var8 = "0";
               }

               if (var17.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.get((int)%s).put(%s, %s);", var17, var8, var16, var7);
               }
               break;
            case 31:
               var9 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "";
               }

               if (var9.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.get((int)%s).containsKey(%s)", var9, var16, var7);
               }
               break;
            case 32:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.add(%s);", var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 33:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var9 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() > 0 && var9.length() > 0) {
                  var16 = String.format("%s.add((int)%s, %s);", var9, var16, var8);
               } else {
                  var16 = "";
               }
               break;
            case 34:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var9 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() > 0 && var9.length() > 0) {
                  var16 = String.format("%s = %s.get((int)%s);", var9, var8, var16);
               } else {
                  var16 = "";
               }
               break;
            case 35:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.remove((int)(%s));", var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 36:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.size()", var16);
               }
               break;
            case 37:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.clear();", var16);
               }
               break;
            case 38:
               var16 = "";
               if (var1.subStack1 >= 0) {
                  var16 = this.a(String.valueOf(var1.subStack1), "");
               }

               var16 = String.format("while(true) {\r\n%s\r\n}", var16);
               break;
            case 39:
               var8 = (String)var10.get(0);
               var16 = "";
               if (var1.subStack1 >= 0) {
                  var16 = this.a(String.valueOf(var1.subStack1), "");
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               var23 = new StringBuilder();
               var23.append("_repeat");
               var23.append(var1.id);
               var8 = var23.toString();
               var16 = String.format("for(int %s = 0; %s < (int)(%s); %s++) {\r\n%s\r\n}", var8, var8, var7, var8, var16);
               break;
            case 40:
               var8 = (String)var10.get(0);
               var16 = "";
               if (var1.subStack1 >= 0) {
                  var16 = this.a(String.valueOf(var1.subStack1), "");
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "true";
               }

               var16 = String.format("if (%s) {\r\n%s\r\n}", var7, var16);
               break;
            case 41:
               var9 = (String)var10.get(0);
               var16 = "";
               if (var1.subStack1 >= 0) {
                  var16 = this.a(String.valueOf(var1.subStack1), "");
               }

               var7 = "";
               if (var1.subStack2 >= 0) {
                  var7 = this.a(String.valueOf(var1.subStack2), "");
               }

               var8 = var9;
               if (var9.length() <= 0) {
                  var8 = "true";
               }

               var16 = String.format("if (%s) {\r\n%s\r\n}\r\nelse {\r\n%s\r\n}", var8, var16, var7);
               break;
            case 42:
               var16 = "break;";
               break;
            case 43:
            case 44:
               var16 = var1.opCode;
               break;
            case 45:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("!%s", var16);
               }
               break;
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               var16 = String.format("%s %s %s", var16, var1.opCode, var7);
               break;
            case 53:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               var16 = String.format("%s == %s", var16, var7);
               break;
            case 54:
            case 55:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "true";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "true";
               }

               var16 = String.format("%s %s %s", var16, var1.opCode, var7);
               break;
            case 56:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               var16 = String.format("SketchwareUtil.getRandom((int)(%s), (int)(%s))", var16, var7);
               break;
            case 57:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.length()", var16);
               }
               break;
            case 58:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = String.format("%s.concat(%s)", var16, var7);
               break;
            case 59:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = String.format("%s.indexOf(%s)", var7, var16);
               break;
            case 60:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = String.format("%s.lastIndexOf(%s)", var7, var16);
               break;
            case 61:
               var9 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               var16 = String.format("%s.substring((int)(%s), (int)(%s))", var9, var16, var7);
               break;
            case 62:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = String.format("%s.equals(%s)", var16, var7);
               break;
            case 63:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = String.format("%s.contains(%s)", var16, var7);
               break;
            case 64:
               var7 = (String)var10.get(0);
               var16 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               var16 = String.format("%s.replace(%s, %s)", var7, var16, var8);
               break;
            case 65:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               var16 = String.format("%s.replaceFirst(%s, %s)", var16, var7, var8);
               break;
            case 66:
               var7 = (String)var10.get(0);
               var16 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               var16 = String.format("%s.replaceAll(%s, %s)", var7, var16, var8);
               break;
            case 67:
               label2642: {
                  var7 = (String)var10.get(0);
                  if (var7.length() > 0) {
                     var16 = var7;
                     if (!var7.equals("\"\"")) {
                        break label2642;
                     }
                  }

                  var16 = "\"0\"";
               }

               var16 = String.format("Double.parseDouble(%s)", var16);
               break;
            case 68:
               var16 = "System.currentTimeMillis()";
               break;
            case 69:
               var16 = (String)var10.get(0);
               var16 = String.format("%s.trim()", var16);
               break;
            case 70:
               var16 = (String)var10.get(0);
               var16 = String.format("%s.toUpperCase()", var16);
               break;
            case 71:
               var16 = (String)var10.get(0);
               var16 = String.format("%s.toLowerCase()", var16);
               break;
            case 72:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("String.valueOf((long)(%s))", var16);
               break;
            case 73:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("String.valueOf(%s)", var16);
               break;
            case 74:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               var16 = String.format("new DecimalFormat(%s).format(%s)", var16, var7);
               break;
            case 75:
               var7 = (String)var1.parameters.get(0);
               if (var7 != null) {
                  var16 = var7;
                  if (var7.length() > 0) {
                     break;
                  }
               }

               var16 = "";
               break;
            case 76:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s = new Gson().fromJson(%s, new TypeToken<HashMap<String, Object>>(){}.getType());", var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 77:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("new Gson().toJson(%s)", var16);
               }
               break;
            case 78:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s = new Gson().fromJson(%s, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());", var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 79:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("new Gson().toJson(%s)", var16);
               }
               break;
            case 80:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("SketchwareUtil.getDip(getApplicationContext(), (int)(%s))", var16);
               break;
            case 81:
               var16 = "SketchwareUtil.getDisplayWidthPixels(getApplicationContext())";
               break;
            case 82:
               var16 = "SketchwareUtil.getDisplayHeightPixels(getApplicationContext())";
               break;
            case 83:
               var16 = "Math.PI";
               break;
            case 84:
               var16 = "Math.E";
               break;
            case 85:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               var16 = String.format("Math.pow(%s, %s)", var7, var16);
               break;
            case 86:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               var16 = String.format("Math.min(%s, %s)", var7, var16);
               break;
            case 87:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               var16 = String.format("Math.max(%s, %s)", var7, var16);
               break;
            case 88:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "1";
               }

               var16 = String.format("Math.sqrt(%s)", var16);
               break;
            case 89:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.abs(%s)", var16);
               break;
            case 90:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.round(%s)", var16);
               break;
            case 91:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.ceil(%s)", var16);
               break;
            case 92:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.floor(%s)", var16);
               break;
            case 93:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.sin(%s)", var16);
               break;
            case 94:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.cos(%s)", var16);
               break;
            case 95:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.tan(%s)", var16);
               break;
            case 96:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.asin(%s)", var16);
               break;
            case 97:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.acos(%s)", var16);
               break;
            case 98:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.atan(%s)", var16);
               break;
            case 99:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.exp(%s)", var16);
               break;
            case 100:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.log(%s)", var16);
               break;
            case 101:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.log10(%s)", var16);
               break;
            case 102:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.toRadians(%s)", var16);
               break;
            case 103:
               var7 = (String)var10.get(0);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var16 = String.format("Math.toDegrees(%s)", var16);
               break;
            case 104:
               var7 = (String)var10.get(0);
               var16 = "";
               if (var1.subStack1 >= 0) {
                  var16 = this.a(String.valueOf(var1.subStack1), "");
               }

               if (var7.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setOnClickListener(new View.OnClickListener() {\n@Override\npublic void onClick(View _view) {\n%s\n}\n});", var7, var16);
               }
               break;
            case 105:
               if (!this.e.a(this.c).f125a) {
                  var16 = "false";
               } else {
                  var16 = "_drawer.isDrawerOpen(GravityCompat.START)";
               }
               break;
            case 106:
               if (!this.e.a(this.c).f125a) {
                  var16 = "";
               } else {
                  var16 = "_drawer.openDrawer(GravityCompat.START);";
               }
               break;
            case 107:
               if (!this.e.a(this.c).f125a) {
                  var16 = "";
               } else {
                  var16 = "_drawer.closeDrawer(GravityCompat.START);";
               }
               break;
            case 108:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "true";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setEnabled(%s);", var8, var16);
               }
               break;
            case 109:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.isEnabled()", var16);
               }
               break;
            case 110:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setText(%s);", var16, var7);
               }
               break;
            case 111:
               var8 = (String)var10.get(0);
               var9 = (String)var10.get(1);
               var16 = (String)var10.get(2);
               if (var16.length() <= 0) {
                  var7 = "0";
               } else {
                  Pair[] var19 = fa.a("property_text_style");
                  var4 = var19.length;
                  var27 = 0;

                  while(true) {
                     var7 = var16;
                     if (var27 >= var4) {
                        break;
                     }

                     Pair var21 = var19[var27];
                     var7 = var16;
                     if (var21.second.equals(var16)) {
                        var28 = new StringBuilder();
                        var28.append(var21.first);
                        var28.append("");
                        var7 = var28.toString();
                     }

                     ++var27;
                     var16 = var7;
                  }
               }

               if (var9.length() <= 0) {
                  var16 = "";
               } else if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setTypeface(Typeface.createFromAsset(getAssets(),\"fonts/%s.ttf\"), %s);", var8, var9, var7);
               }
               break;
            case 112:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getText().toString()", var16);
               }
               break;
            case 113:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0xFF000000";
               }

               if (var8.length() > 0 && var16.length() > 0) {
                  var16 = String.format("%s.setBackgroundColor(%s);", var8, var16);
               } else {
                  var16 = "";
               }
               break;
            case 114:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var8.length() > 0 && var7.length() > 0) {
                  if (var7.equals("NONE")) {
                     var16 = "0";
                  } else {
                     var22 = new StringBuilder();
                     var22.append("R.drawable.");
                     var16 = var7;
                     if (var7.endsWith(".9")) {
                        var16 = var7.replaceAll("\\.9", "");
                     }

                     var22.append(var16);
                     var16 = var22.toString();
                  }

                  var16 = String.format("%s.setBackgroundResource(%s);", var8, var16);
               } else {
                  var16 = "";
               }
               break;
            case 115:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0xFF000000";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setTextColor(%s);", var8, var16);
               }
               break;
            case 116:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.endsWith(".9")) {
                  var16 = var7.replaceAll("\\.9", "");
               }

               if (var8.length() > 0 && var16.length() > 0) {
                  var16 = String.format("%s.setImageResource(R.drawable.%s);", var8, var16.toLowerCase());
               } else {
                  var16 = "";
               }
               break;
            case 117:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0x00000000";
               }

               if (var8.equals("\"\"")) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setColorFilter(%s, PorterDuff.Mode.MULTIPLY);", var8, var16);
               }
               break;
            case 118:
               var16 = (String)var10.get(0);
               var16.equals("\"\"");
               var16 = String.format("%s.requestFocus();", var16);
               break;
            case 119:
               var16 = (String)var10.get(0);
               var16 = String.format("SketchwareUtil.showMessage(getApplicationContext(), %s);", var16);
               break;
            case 120:
               var16 = (String)var10.get(0);
               var16 = String.format("((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(\"clipboard\", %s));", var16);
               break;
            case 121:
               var16 = (String)var10.get(0);
               var16 = String.format("setTitle(%s);", var16);
               break;
            case 122:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               if (var8.length() > 0 && !var8.equals("\"\"")) {
                  var28 = new StringBuilder();
                  var28.append("Intent.");
                  var28.append(var8);
                  var16 = var28.toString();
               } else {
                  var16 = "\"\"";
               }

               if (var7.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setAction(%s);", var7, var16);
               }
               break;
            case 123:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setData(Uri.parse(%s));", var16, var7);
               }
               break;
            case 124:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var7.length() > 0 && var16.length() > 0) {
                  var16 = String.format("%s.setClass(getApplicationContext(), %s.class);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 125:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.putExtra(%s, %s);", var16, var7, var8);
               }
               break;
            case 126:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var28 = new StringBuilder();
                  var28.append("Intent.FLAG_ACTIVITY_");
                  var28.append(var8);
                  var16 = var28.toString();
               }

               if (var7.length() > 0 && var16.length() > 0) {
                  var16 = String.format("%s.setFlags(%s);", var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 127:
               var16 = (String)var10.get(0);
               var16 = String.format("getIntent().getStringExtra(%s)", var16);
               break;
            case 128:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("startActivity(%s);", var16);
               }
               break;
            case 129:
               var16 = "finish();";
               break;
            case 130:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s = getApplicationContext().getSharedPreferences(%s, Activity.MODE_PRIVATE);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 131:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getString(%s, \"\")", var16, var7);
               }
               break;
            case 132:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.edit().putString(%s, %s).commit();", var16, var7, var8);
               }
               break;
            case 133:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.edit().remove(%s).commit();", var16, var7);
               }
               break;
            case 134:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s = Calendar.getInstance();", var16);
               }
               break;
            case 135:
               var9 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var9.length() > 0 && var8.length() > 0) {
                  var16 = String.format("%s.add(Calendar.%s, (int)(%s));", var9, var8, var16);
               } else {
                  var16 = "";
               }
               break;
            case 136:
               var9 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var9.length() > 0 && var8.length() > 0) {
                  var16 = String.format("%s.set(Calendar.%s, (int)(%s));", var9, var8, var16);
               } else {
                  var16 = "";
               }
               break;
            case 137:
               label2553: {
                  var8 = (String)var10.get(0);
                  var7 = (String)var10.get(1);
                  if (var7.length() > 0) {
                     var16 = var7;
                     if (!var7.equals("\"\"")) {
                        break label2553;
                     }
                  }

                  var16 = "\"yyyy/MM/dd hh:mm:ss\"";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("new SimpleDateFormat(%s).format(%s.getTime())", var16, var8);
               }
               break;
            case 138:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("(long)(%s.getTimeInMillis() - %s.getTimeInMillis())", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 139:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getTimeInMillis()", var16);
               }
               break;
            case 140:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.setTimeInMillis((long)(%s));", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 141:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "VISIBLE";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setVisibility(View.%s);", var8, var16);
               }
               break;
            case 142:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "true";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setClickable(%s);", var8, var16);
               }
               break;
            case 143:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setRotation((float)(%s));", var8, var16);
               }
               break;
            case 144:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getRotation()", var16);
               }
               break;
            case 145:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setAlpha((float)(%s));", var8, var16);
               }
               break;
            case 146:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getAlpha()", var16);
               }
               break;
            case 147:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setTranslationX((float)(%s));", var8, var16);
               }
               break;
            case 148:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getTranslationX()", var16);
               }
               break;
            case 149:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setTranslationY((float)(%s));", var8, var16);
               }
               break;
            case 150:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getTranslationY()", var16);
               }
               break;
            case 151:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setScaleX((float)(%s));", var8, var16);
               }
               break;
            case 152:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getScaleX()", var16);
               }
               break;
            case 153:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setScaleY((float)(%s));", var8, var16);
               }
               break;
            case 154:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getScaleY()", var16);
               }
               break;
            case 155:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("SketchwareUtil.getLocationX(%s)", var16);
               }
               break;
            case 156:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("SketchwareUtil.getLocationY(%s)", var16);
               }
               break;
            case 157:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "false";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setChecked(%s);", var8, var16);
               }
               break;
            case 158:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.isChecked()", var16);
               }
               break;
            case 159:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), %s, %s));", var16, "android.R.layout.simple_list_item_1", var7);
               } else {
                  var16 = "";
               }
               break;
            case 160:
               var16 = (String)var10.get(0);
               var9 = (String)var10.get(1);
               if (var16.length() > 0 && var9.length() > 0) {
                  var8 = hg.b(var16);
                  var24 = new StringBuilder();
                  var24.append("%s.setAdapter(new ");
                  var24.append(var8);
                  var24.append("(%s));");
                  var16 = String.format(var24.toString(), var16, var9);
               } else {
                  var16 = "";
               }
               break;
            case 161:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("((BaseAdapter)%s.getAdapter()).notifyDataSetChanged();", var16);
               }
               break;
            case 162:
               var9 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "false";
               }

               if (var9.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setItemChecked((int)(%s), %s);", var9, var16, var7);
               }
               break;
            case 163:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getCheckedItemPosition()", var16);
               }
               break;
            case 164:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s = SketchwareUtil.getCheckedItemPositionsToArray(%s);", var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 165:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getCheckedItemCount()", var16);
               }
               break;
            case 166:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               if (var7.length() <= 0) {
               }

               var16 = "";
               if (var8.length() > 0) {
                  var16 = String.format("%s.smoothScrollToPosition((int)(%s));", var7, var8);
               }
               break;
            case 167:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, %s));", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 168:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("((ArrayAdapter)%s.getAdapter()).notifyDataSetChanged();", var16);
               }
               break;
            case 169:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setSelection((int)(%s));", var8, var16);
               }
               break;
            case 170:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getSelectedItemPosition()", var16);
               }
               break;
            case 171:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.loadUrl(%s);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 172:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getUrl()", var16);
               }
               break;
            case 173:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "LOAD_DEFAULT";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getSettings().setCacheMode(WebSettings.%s);", var8, var16);
               }
               break;
            case 174:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.canGoBack()", var16);
               }
               break;
            case 175:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.canGoForward()", var16);
               }
               break;
            case 176:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.goBack();", var16);
               }
               break;
            case 177:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.goForward();", var16);
               }
               break;
            case 178:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.clearCache(true);", var16);
               }
               break;
            case 179:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.clearHistory();", var16);
               }
               break;
            case 180:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.stopLoading();", var16);
               }
               break;
            case 181:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.zoomIn();", var16);
               }
               break;
            case 182:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.zoomOut();", var16);
               }
               break;
            case 183:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getDate()", var16);
               }
               break;
            case 184:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.setDate((long)(%s), true, true);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 185:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.setMinDate((long)(%s));", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 186:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.setMaxDate((long)(%s));", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 187:
               var9 = (String)var10.get(0);
               var16 = "";
               var7 = var16;
               if (this.e.s.size() > 0) {
                  Iterator var25 = this.e.s.iterator();

                  while(true) {
                     var7 = var16;
                     if (!var25.hasNext()) {
                        break;
                     }

                     var7 = (String)var25.next();
                     StringBuilder var18 = new StringBuilder();
                     var18.append(var16);
                     var18.append(".addTestDevice(\"");
                     var18.append(var7);
                     var18.append("\")\n");
                     var16 = var18.toString();
                  }
               }

               if (var9.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.loadAd(new AdRequest.Builder()%s.build());", var9, var7);
               }
               break;
            case 188:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = fc.q[0];
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("_%s_controller.setMapType(GoogleMap.%s);", var8, var16);
               }
               break;
            case 189:
               var9 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var8 = (String)var10.get(2);
               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               if (var9.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("_%s_controller.moveCamera(%s, %s);", var9, var16, var7);
               }
               break;
            case 190:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("_%s_controller.zoomTo(%s);", var8, var16);
               }
               break;
            case 191:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("_%s_controller.zoomIn();", var16);
               }
               break;
            case 192:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("_%s_controller.zoomOut();", var16);
               }
               break;
            case 193:
               var11 = (String)var10.get(0);
               var9 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var8 = (String)var10.get(3);
               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               if (var11.length() > 0 && var9.length() > 0) {
                  var16 = String.format("_%s_controller.addMarker(%s, %s, %s);", var11, var9, var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 194:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = (String)var10.get(2);
               var9 = (String)var10.get(3);
               if (var8.length() > 0 && var7.length() > 0) {
                  var16 = String.format("_%s_controller.setMarkerInfo(%s, %s, %s);", var8, var7, var16, var9);
               } else {
                  var16 = "";
               }
               break;
            case 195:
               var9 = (String)var10.get(0);
               var11 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var8 = (String)var10.get(3);
               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               if (var9.length() > 0 && var11.length() > 0) {
                  var16 = String.format("_%s_controller.setMarkerPosition(%s, %s, %s);", var9, var11, var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 196:
               var9 = (String)var10.get(0);
               var11 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = fc.r[0];
               }

               var8 = (String)var10.get(3);
               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "1";
               }

               if (var9.length() > 0 && var11.length() > 0) {
                  var16 = String.format("_%s_controller.setMarkerColor(%s, BitmapDescriptorFactory.%s, %s);", var9, var11, var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 197:
               var8 = (String)var10.get(0);
               var9 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.endsWith(".9")) {
                  var16 = var7.replaceAll("\\.9", "");
               }

               if (var8.length() > 0 && var9.length() > 0 && var16.length() > 0) {
                  var16 = String.format("_%s_controller.setMarkerIcon(%s, R.drawable.%s);", var8, var9, var16.toLowerCase());
               } else {
                  var16 = "";
               }
               break;
            case 198:
               var9 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "true";
               }

               if (var9.length() > 0 && var8.length() > 0) {
                  var16 = String.format("_%s_controller.setMarkerVisible(%s, %s);", var9, var8, var16);
               } else {
                  var16 = "";
               }
               break;
            case 199:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.vibrate((long)(%s));", var8, var16);
               }
               break;
            case 200:
               var9 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var16 = "";
               if (var1.subStack1 >= 0) {
                  var16 = this.a(String.valueOf(var1.subStack1), "");
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               if (var9.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s = new TimerTask() {\n@Override\npublic void run() {\nrunOnUiThread(new Runnable() {\n@Override\npublic void run() {\n%s\n}\n});\n}\n};\n_timer.schedule(%s, (int)(%s));", var9, var16, var9, var7);
               }
               break;
            case 201:
               var11 = (String)var10.get(0);
               var9 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               var16 = "";
               if (var1.subStack1 >= 0) {
                  var16 = this.a(String.valueOf(var1.subStack1), "");
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               var8 = var9;
               if (var9.length() <= 0) {
                  var8 = "0";
               }

               if (var11.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s = new TimerTask() {\n@Override\npublic void run() {\nrunOnUiThread(new Runnable() {\n@Override\npublic void run() {\n%s\n}\n});\n}\n};\n_timer.scheduleAtFixedRate(%s, (int)(%s), (int)(%s));", var11, var16, var11, var8, var7);
               }
               break;
            case 202:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.cancel();", var16);
               }
               break;
            case 203:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var9 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "";
               }

               if (var8.length() > 0 && var9.length() > 0) {
                  var16 = String.format("%s.child(%s).updateChildren(%s);", var8, var16, var9);
               } else {
                  var16 = "";
               }
               break;
            case 204:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.push().updateChildren(%s);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 205:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.push().getKey()", var16);
               }
               break;
            case 206:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.child(%s).removeValue();", var8, var16);
               }
               break;
            case 207:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var16 = "";
               if (var1.subStack1 >= 0) {
                  var16 = this.a(String.valueOf(var1.subStack1), "");
               }

               if (var8.length() > 0 && var7.length() > 0) {
                  var22 = new StringBuilder();
                  var22.append(String.format("%s.addListenerForSingleValueEvent(new ValueEventListener() {", var7));
                  var22.append("\n@Override\npublic void onDataChange(DataSnapshot _dataSnapshot) {\n");
                  var22.append(String.format("%s = new ArrayList<>();", var8));
                  var22.append("\ntry {\nGenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};\nfor (DataSnapshot _data : _dataSnapshot.getChildren()) {\nHashMap<String, Object> _map = _data.getValue(_ind);\n");
                  var22.append(String.format("%s.add(_map);", var8));
                  var22.append("\n}\n}\ncatch (Exception _e) {\n_e.printStackTrace();\n}\n");
                  var22.append(var16);
                  var22.append("\n}\n@Override\npublic void onCancelled(DatabaseError _databaseError) {\n}\n});");
                  var16 = var22.toString();
               } else {
                  var16 = "";
               }
               break;
            case 208:
               var7 = (String)var10.get(0);
               var16 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               if (var7.length() > 0 && !var16.equals("\"\"") && !var8.equals("\"\"")) {
                  var22 = new StringBuilder();
                  var22.append("_");
                  var22.append(var7);
                  var22.append("_create_user_listener");
                  var9 = var22.toString();
                  var16 = String.format("%s.createUserWithEmailAndPassword(%s, %s).addOnCompleteListener(%s.this, %s);", var7, var16, var8, this.c, var9);
               } else {
                  var16 = "";
               }
               break;
            case 209:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               if (var16.length() > 0 && !var7.equals("\"\"") && !var8.equals("\"\"")) {
                  var22 = new StringBuilder();
                  var22.append("_");
                  var22.append(var16);
                  var22.append("_sign_in_listener");
                  var9 = var22.toString();
                  var16 = String.format("%s.signInWithEmailAndPassword(%s, %s).addOnCompleteListener(%s.this, %s);", var16, var7, var8, this.c, var9);
               } else {
                  var16 = "";
               }
               break;
            case 210:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var24 = new StringBuilder();
                  var24.append("_");
                  var24.append(var16);
                  var24.append("_sign_in_listener");
                  var7 = var24.toString();
                  var16 = String.format("%s.signInAnonymously().addOnCompleteListener(%s.this, %s);", var16, this.c, var7);
               }
               break;
            case 211:
               if (this.e.a(this.c).b) {
                  var16 = String.format("(FirebaseAuth.getInstance().getCurrentUser() != null)");
               } else {
                  var16 = "false";
               }
               break;
            case 212:
               if (this.e.a(this.c).b) {
                  var16 = String.format("FirebaseAuth.getInstance().getCurrentUser().getEmail()");
               } else {
                  var16 = "";
               }
               break;
            case 213:
               if (this.e.a(this.c).b) {
                  var16 = String.format("FirebaseAuth.getInstance().getCurrentUser().getUid()");
               } else {
                  var16 = "";
               }
               break;
            case 214:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && !var7.equals("\"\"")) {
                  var23 = new StringBuilder();
                  var23.append("_");
                  var23.append(var16);
                  var23.append("_reset_password_listener");
                  var8 = var23.toString();
                  var16 = String.format("%s.sendPasswordResetEmail(%s).addOnCompleteListener(%s);", var16, var7, var8);
               } else {
                  var16 = "";
               }
               break;
            case 215:
               if (this.e.a(this.c).b) {
                  var16 = String.format("FirebaseAuth.getInstance().signOut();");
               } else {
                  var16 = "";
               }
               break;
            case 216:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.addChildEventListener(_%s_child_listener);", var16, var16);
               }
               break;
            case 217:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.removeEventListener(_%s_child_listener);", var16, var16);
               }
               break;
            case 218:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.registerListener(_%s_sensor_listener, %s.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);", var16, var16, var16);
               }
               break;
            case 219:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.unregisterListener(_%s_sensor_listener);", var16, var16);
               }
               break;
            case 220:
               label2540: {
                  var8 = (String)var10.get(0);
                  var7 = (String)var10.get(1);
                  if (var7.length() > 0) {
                     var16 = var7;
                     if (!var7.equals("\"\"")) {
                        break label2540;
                     }
                  }

                  var16 = "\"Title\"";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setTitle(%s);", var8, var16);
               }
               break;
            case 221:
               label2535: {
                  var8 = (String)var10.get(0);
                  var7 = (String)var10.get(1);
                  if (var7.length() > 0) {
                     var16 = var7;
                     if (!var7.equals("\"\"")) {
                        break label2535;
                     }
                  }

                  var16 = "\"Message\"";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setMessage(%s);", var8, var16);
               }
               break;
            case 222:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.create().show();", var16);
               }
               break;
            case 223:
               var9 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var16 = "";
               if (var1.subStack1 >= 0) {
                  var16 = this.a(String.valueOf(var1.subStack1), "");
               }

               label2529: {
                  if (var8.length() > 0) {
                     var7 = var8;
                     if (!var8.equals("\"\"")) {
                        break label2529;
                     }
                  }

                  var7 = "\"OK\"";
               }

               if (var9.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setPositiveButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", var9, var7, var16);
               }
               break;
            case 224:
               var9 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var16 = "";
               if (var1.subStack1 >= 0) {
                  var16 = this.a(String.valueOf(var1.subStack1), "");
               }

               label2523: {
                  if (var8.length() > 0) {
                     var7 = var8;
                     if (!var8.equals("\"\"")) {
                        break label2523;
                     }
                  }

                  var7 = "\"Cancel\"";
               }

               if (var9.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setNegativeButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", var9, var7, var16);
               }
               break;
            case 225:
               var9 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var16 = "";
               if (var1.subStack1 >= 0) {
                  var16 = this.a(String.valueOf(var1.subStack1), "");
               }

               label2517: {
                  if (var8.length() > 0) {
                     var7 = var8;
                     if (!var8.equals("\"\"")) {
                        break label2517;
                     }
                  }

                  var7 = "\"Neutral\"";
               }

               if (var9.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setNeutralButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", var9, var7, var16);
               }
               break;
            case 226:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s = MediaPlayer.create(getApplicationContext(), R.raw.%s);", var16, var7.toLowerCase());
               } else {
                  var16 = "";
               }
               break;
            case 227:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.start();", var16);
               }
               break;
            case 228:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.pause();", var16);
               }
               break;
            case 229:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.seekTo((int)(%s));", var8, var16);
               }
               break;
            case 230:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getCurrentPosition()", var16);
               }
               break;
            case 231:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getDuration()", var16);
               }
               break;
            case 232:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.reset();", var16);
               }
               break;
            case 233:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.release();", var16);
               }
               break;
            case 234:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.isPlaying()", var16);
               }
               break;
            case 235:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "false";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setLooping(%s);", var8, var16);
               }
               break;
            case 236:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.isLooping()", var16);
               }
               break;
            case 237:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "1";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s = new SoundPool((int)(%s), AudioManager.STREAM_MUSIC, 0);", var8, var16);
               }
               break;
            case 238:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var7.length() > 0 && var16.length() > 0) {
                  var16 = String.format("%s.load(getApplicationContext(), R.raw.%s, 1)", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 239:
               var8 = (String)var10.get(0);
               var9 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var9.length() > 0 && var8.length() > 0) {
                  var16 = String.format("%s.play((int)(%s), 1.0f, 1.0f, 1, (int)(%s), 1.0f)", var8, var9, var16);
               } else {
                  var16 = "";
               }
               break;
            case 240:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var7.length() > 0 && var16.length() > 0) {
                  var16 = String.format("%s.stop((int)(%s));", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 241:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.endsWith(".9")) {
                  var16 = var7.replaceAll("\\.9", "");
               }

               if (var8.length() > 0 && var16.length() > 0) {
                  var16 = String.format("%s.setThumbResource(R.drawable.%s);", var8, var16.toLowerCase());
               } else {
                  var16 = "";
               }
               break;
            case 242:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.endsWith(".9")) {
                  var16 = var7.replaceAll("\\.9", "");
               }

               if (var8.length() > 0 && var16.length() > 0) {
                  var16 = String.format("%s.setTrackResource(R.drawable.%s);", var8, var16.toLowerCase());
               } else {
                  var16 = "";
               }
               break;
            case 243:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setProgress((int)%s);", var8, var16);
               }
               break;
            case 244:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getProgress()", var16);
               }
               break;
            case 245:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setMax((int)%s);", var8, var16);
               }
               break;
            case 246:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getMax()", var16);
               }
               break;
            case 247:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.setTarget(%s);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 248:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.setPropertyName(\"%s\");", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 249:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setFloatValues((float)(%s));", var8, var16);
               }
               break;
            case 250:
               var9 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               if (var9.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setFloatValues((float)(%s), (float)(%s));", var9, var7, var16);
               }
               break;
            case 251:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setDuration((int)(%s));", var8, var16);
               }
               break;
            case 252:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.setRepeatMode(ValueAnimator.%s);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 253:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setRepeatCount((int)(%s));", var8, var16);
               }
               break;
            case 254:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var16 = "new LinearInterpolator()";
               if (var8.equals("Accelerate")) {
                  var16 = "new AccelerateInterpolator()";
               }

               if (var8.equals("Decelerate")) {
                  var16 = "new DecelerateInterpolator()";
               }

               if (var8.equals("AccelerateDeccelerate")) {
                  var16 = "new AccelerateDecelerateInterpolator()";
               }

               if (var8.equals("Bounce")) {
                  var16 = "new BounceInterpolator()";
               }

               if (var7.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setInterpolator(%s);", var7, var16);
               }
               break;
            case 255:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.start();", var16);
               }
               break;
            case 256:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.cancel();", var16);
               }
               break;
            case 257:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.isRunning()", var16);
               }
               break;
            case 258:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s = new InterstitialAd(getApplicationContext());\n%s.setAdListener(_%s_ad_listener);", var16, var16, var16);
               }
               break;
            case 259:
               var8 = (String)var10.get(0);
               var16 = "";
               var7 = var16;
               if (this.e.s.size() > 0) {
                  Iterator var20 = this.e.s.iterator();

                  while(true) {
                     var7 = var16;
                     if (!var20.hasNext()) {
                        break;
                     }

                     var17 = (String)var20.next();
                     var24 = new StringBuilder();
                     var24.append(var16);
                     var24.append(".addTestDevice(\"");
                     var24.append(var17);
                     var24.append("\")\n");
                     var16 = var24.toString();
                  }
               }

               var16 = this.e.r;
               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setAdUnitId(\"%s\");\n%s.loadAd(new AdRequest.Builder()%s.build());", var8, var16, var8, var7);
               }
               break;
            case 260:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.show();", var16);
               }
               break;
            case 261:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               if (var16.length() > 0 && !var7.equals("\"\"") && !var8.equals("\"\"")) {
                  var16 = String.format("%s.child(%s).putFile(Uri.fromFile(new File(%s))).addOnSuccessListener(_%s_upload_success_listener).addOnFailureListener(_%s_failure_listener).addOnProgressListener(_%s_upload_progress_listener);", var16, var8, var7, var16, var16, var16);
               } else {
                  var16 = "";
               }
               break;
            case 262:
               var7 = (String)var10.get(0);
               var16 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               if (var7.length() > 0 && !var16.equals("\"\"") && !var8.equals("\"\"")) {
                  var16 = String.format("_firebase_storage.getReferenceFromUrl(%s).getFile(new File(%s)).addOnSuccessListener(_%s_download_success_listener).addOnFailureListener(_%s_failure_listener).addOnProgressListener(_%s_download_progress_listener);", var16, var8, var7, var7, var7);
               } else {
                  var16 = "";
               }
               break;
            case 263:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && !var7.equals("\"\"")) {
                  var16 = String.format("_firebase_storage.getReferenceFromUrl(%s).delete().addOnSuccessListener(_%s_delete_success_listener).addOnFailureListener(_%s_failure_listener);", var7, var16, var16);
               } else {
                  var16 = "";
               }
               break;
            case 264:
               var16 = (String)var10.get(0);
               if (var16.equals("\"\"")) {
                  var16 = "";
               } else {
                  var16 = String.format("FileUtil.readFile(%s)", var16);
               }
               break;
            case 265:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var7.equals("\"\"")) {
                  var16 = "";
               } else {
                  var16 = String.format("FileUtil.writeFile(%s, %s);", var7, var16);
               }
               break;
            case 266:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (!var16.equals("\"\"") && !var7.equals("\"\"")) {
                  var16 = String.format("FileUtil.copyFile(%s, %s);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 267:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (!var16.equals("\"\"") && !var7.equals("\"\"")) {
                  var16 = String.format("FileUtil.moveFile(%s, %s);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 268:
               var16 = (String)var10.get(0);
               if (var16.equals("\"\"")) {
                  var16 = "";
               } else {
                  var16 = String.format("FileUtil.deleteFile(%s);", var16);
               }
               break;
            case 269:
               var16 = (String)var10.get(0);
               if (var16.equals("\"\"")) {
                  var16 = "";
               } else {
                  var16 = String.format("FileUtil.isExistFile(%s)", var16);
               }
               break;
            case 270:
               var16 = (String)var10.get(0);
               if (var16.equals("\"\"")) {
                  var16 = "";
               } else {
                  var16 = String.format("FileUtil.makeDir(%s);", var16);
               }
               break;
            case 271:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (!var16.equals("\"\"") && var7.length() > 0) {
                  var16 = String.format("FileUtil.listDir(%s, %s);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 272:
               var16 = (String)var10.get(0);
               if (var16.equals("\"\"")) {
                  var16 = "";
               } else {
                  var16 = String.format("FileUtil.isDirectory(%s)", var16);
               }
               break;
            case 273:
               var16 = (String)var10.get(0);
               if (var16.equals("\"\"")) {
                  var16 = "";
               } else {
                  var16 = String.format("FileUtil.isFile(%s)", var16);
               }
               break;
            case 274:
               var16 = (String)var10.get(0);
               if (var16.equals("\"\"")) {
                  var16 = "";
               } else {
                  var16 = String.format("FileUtil.getFileLength(%s)", var16);
               }
               break;
            case 275:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (!var16.equals("\"\"") && var7.length() > 0) {
                  var16 = String.format("%s.startsWith(%s)", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 276:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (!var16.equals("\"\"") && var7.length() > 0) {
                  var16 = String.format("%s.endsWith(%s)", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 277:
               var16 = (String)var10.get(0);
               if (var16.equals("\"\"")) {
                  var16 = "";
               } else {
                  var16 = String.format("Uri.parse(%s).getLastPathSegment()", var16);
               }
               break;
            case 278:
               var16 = "FileUtil.getExternalStorageDir()";
               break;
            case 279:
               var16 = "FileUtil.getPackageDataDir(getApplicationContext())";
               break;
            case 280:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("FileUtil.getPublicDir(Environment.%s)", var16);
               }
               break;
            case 281:
               var8 = (String)var10.get(0);
               var9 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "1024";
               }

               if (!var8.equals("\"\"") && !var9.equals("\"\"")) {
                  var16 = String.format("FileUtil.resizeBitmapFileRetainRatio(%s, %s, %s);", var8, var9, var16);
               } else {
                  var16 = "";
               }
               break;
            case 282:
               var9 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "1024";
               }

               if (!var9.equals("\"\"") && !var8.equals("\"\"")) {
                  var16 = String.format("FileUtil.resizeBitmapFileToSquare(%s, %s, %s);", var9, var8, var16);
               } else {
                  var16 = "";
               }
               break;
            case 283:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (!var16.equals("\"\"") && !var7.equals("\"\"")) {
                  var16 = String.format("FileUtil.resizeBitmapFileToCircle(%s, %s);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 284:
               var9 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (!var9.equals("\"\"") && !var8.equals("\"\"")) {
                  var16 = String.format("FileUtil.resizeBitmapFileWithRoundedBorder(%s, %s, %s);", var9, var8, var16);
               } else {
                  var16 = "";
               }
               break;
            case 285:
               var9 = (String)var10.get(0);
               var11 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var8 = (String)var10.get(3);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "1024";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "1024";
               }

               if (!var9.equals("\"\"") && !var11.equals("\"\"")) {
                  var16 = String.format("FileUtil.cropBitmapFileFromCenter(%s, %s, %s, %s);", var9, var11, var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 286:
               var9 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (!var9.equals("\"\"") && !var8.equals("\"\"")) {
                  var16 = String.format("FileUtil.rotateBitmapFile(%s, %s, %s);", var9, var8, var16);
               } else {
                  var16 = "";
               }
               break;
            case 287:
               var11 = (String)var10.get(0);
               var9 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var8 = (String)var10.get(3);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "1";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "1";
               }

               if (!var11.equals("\"\"") && !var9.equals("\"\"")) {
                  var16 = String.format("FileUtil.scaleBitmapFile(%s, %s, %s, %s);", var11, var9, var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 288:
               var11 = (String)var10.get(0);
               var9 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var8 = (String)var10.get(3);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "0";
               }

               if (!var11.equals("\"\"") && !var9.equals("\"\"")) {
                  var16 = String.format("FileUtil.skewBitmapFile(%s, %s, %s, %s);", var11, var9, var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 289:
               var8 = (String)var10.get(0);
               var9 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0x00000000";
               }

               if (!var8.equals("\"\"") && !var9.equals("\"\"")) {
                  var16 = String.format("FileUtil.setBitmapFileColorFilter(%s, %s, %s);", var8, var9, var16);
               } else {
                  var16 = "";
               }
               break;
            case 290:
               var9 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (!var9.equals("\"\"") && !var8.equals("\"\"")) {
                  var16 = String.format("FileUtil.setBitmapFileBrightness(%s, %s, %s);", var9, var8, var16);
               } else {
                  var16 = "";
               }
               break;
            case 291:
               var9 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var7 = (String)var10.get(2);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "0";
               }

               if (!var9.equals("\"\"") && !var8.equals("\"\"")) {
                  var16 = String.format("FileUtil.setBitmapFileContrast(%s, %s, %s);", var9, var8, var16);
               } else {
                  var16 = "";
               }
               break;
            case 292:
               var16 = (String)var10.get(0);
               if (var16.equals("\"\"")) {
                  var16 = "";
               } else {
                  var16 = String.format("FileUtil.getJpegRotate(%s)", var16);
               }
               break;
            case 293:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("startActivityForResult(%s, REQ_CD_%s);", var16, var16.toUpperCase());
               }
               break;
            case 294:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("startActivityForResult(%s, REQ_CD_%s);", var16, var16.toUpperCase());
               }
               break;
            case 295:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && !var7.equals("\"\"")) {
                  var16 = String.format("%s.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(%s, 1024, 1024));", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 296:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && !var7.equals("\"\"")) {
                  var16 = String.format("Glide.with(getApplicationContext()).load(Uri.parse(%s)).into(%s);", var7, var16);
               } else {
                  var16 = "";
               }
               break;
            case 297:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && !var7.equals("\"\"")) {
                  var16 = String.format("%s.setHint(%s);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 298:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && !var7.equals("\"\"")) {
                  var16 = String.format("%s.setHintTextColor(%s);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 299:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               if (var16.length() > 0 && var7.length() > 0 && var8.length() > 0) {
                  var16 = String.format("%s.setParams(%s, RequestNetworkController.%s);", var16, var7, var8);
               } else {
                  var16 = "";
               }
               break;
            case 300:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.setHeaders(%s);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 301:
               var7 = (String)var10.get(0);
               var16 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               var9 = (String)var10.get(3);
               if (var7.length() > 0 && var16.length() > 0 && !var8.equals("\"\"") && var9.length() > 0) {
                  var16 = String.format("%s.startRequestNetwork(RequestNetworkController.%s, %s, %s, _%s_request_listener);", var7, var16, var8, var9, var7);
               } else {
                  var16 = "";
               }
               break;
            case 302:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "false";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setIndeterminate(%s);", var8, var16);
               }
               break;
            case 303:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "1";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setPitch((float)%s);", var8, var16);
               }
               break;
            case 304:
               var8 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = "1";
               }

               if (var8.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.setSpeechRate((float)%s);", var8, var16);
               }
               break;
            case 305:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.speak(%s, TextToSpeech.QUEUE_ADD, null);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 306:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.isSpeaking()", var16);
               }
               break;
            case 307:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.stop();", var16);
               }
               break;
            case 308:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.shutdown();", var16);
               }
               break;
            case 309:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("Intent _intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);\n_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());\n_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);\n_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());\n%s.startListening(_intent);", var16);
               }
               break;
            case 310:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.stopListening();", var16);
               }
               break;
            case 311:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.cancel();\n%s.destroy();", var16, var16);
               }
               break;
            case 312:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.readyConnection(_%s_bluetooth_connection_listener, %s);", var16, var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 313:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               if (var16.length() > 0 && var7.length() > 0 && var8.length() > 0) {
                  var16 = String.format("%s.readyConnection(_%s_bluetooth_connection_listener, %s, %s);", var16, var16, var7, var8);
               } else {
                  var16 = "";
               }
               break;
            case 314:
               var7 = (String)var10.get(0);
               var16 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               if (var7.length() > 0 && var16.length() > 0 && var8.length() > 0) {
                  var16 = String.format("%s.startConnection(_%s_bluetooth_connection_listener, %s, %s);", var7, var7, var16, var8);
               } else {
                  var16 = "";
               }
               break;
            case 315:
               var7 = (String)var10.get(0);
               var8 = (String)var10.get(1);
               var16 = (String)var10.get(2);
               var9 = (String)var10.get(3);
               if (var7.length() > 0 && var8.length() > 0 && var16.length() > 0 && var9.length() > 0) {
                  var16 = String.format("%s.startConnection(_%s_bluetooth_connection_listener, %s, %s, %s);", var7, var7, var8, var16, var9);
               } else {
                  var16 = "";
               }
               break;
            case 316:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.stopConnection(_%s_bluetooth_connection_listener, %s);", var16, var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 317:
               var7 = (String)var10.get(0);
               var16 = (String)var10.get(1);
               var8 = (String)var10.get(2);
               if (var7.length() > 0 && var16.length() > 0 && var8.length() > 0) {
                  var16 = String.format("%s.sendData(_%s_bluetooth_connection_listener, %s, %s);", var7, var7, var16, var8);
               } else {
                  var16 = "";
               }
               break;
            case 318:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.isBluetoothEnabled()", var16);
               }
               break;
            case 319:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.isBluetoothActivated()", var16);
               }
               break;
            case 320:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.activateBluetooth();", var16);
               }
               break;
            case 321:
               var16 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               if (var16.length() > 0 && var7.length() > 0) {
                  var16 = String.format("%s.getPairedDevices(%s);", var16, var7);
               } else {
                  var16 = "";
               }
               break;
            case 322:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.getRandomUUID()", var16);
               }
               break;
            case 323:
               var11 = (String)var10.get(0);
               var7 = (String)var10.get(1);
               var16 = var7;
               if (var7.length() <= 0) {
                  var16 = fc.p[0];
               }

               var8 = (String)var10.get(2);
               var7 = var8;
               if (var8.length() <= 0) {
                  var7 = "1000";
               }

               var9 = (String)var10.get(3);
               var8 = var9;
               if (var9.length() <= 0) {
                  var8 = "0";
               }

               if (var11.length() <= 0) {
                  var16 = "";
               } else if (this.e.k) {
                  var9 = this.c;
                  var16 = String.format("if (ContextCompat.checkSelfPermission(%s.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {\n%s.requestLocationUpdates(LocationManager.%s, %s, %s, _%s_location_listener);\n}", var9, var11, var16, var7, var8, var11);
               } else {
                  var16 = String.format("if (Build.VERSION.SDK_INT >= 23) {if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {\n%s.requestLocationUpdates(LocationManager.%s, %s, %s, _%s_location_listener);\n}\n}\nelse {\n%s.requestLocationUpdates(LocationManager.%s, %s, %s, _%s_location_listener);\n}", var11, var16, var7, var8, var11, var11, var16, var7, var8, var11);
               }
               break;
            case 324:
               var16 = (String)var10.get(0);
               if (var16.length() <= 0) {
                  var16 = "";
               } else {
                  var16 = String.format("%s.removeUpdates(_%s_location_listener);", var16, var16);
               }
               break;
            default:
               var16 = "";
            }

            var7 = var16;
            StringBuilder var12;
            if (this.b(var1.opCode, var2)) {
               var12 = new StringBuilder();
               var12.append("(");
               var12.append(var16);
               var12.append(")");
               var7 = var12.toString();
            }

            var2 = var7;
            if (var1.nextBlock >= 0) {
               var12 = new StringBuilder();
               var12.append(var7);
               var12.append("\r\n");
               var12.append(this.a(String.valueOf(var1.nextBlock), ""));
               var2 = var12.toString();
            }

            return var2;
         }

         var7 = (String)var1.parameters.get(var4);
         hc var6 = (hc)var1.getParamClassInfo().get(var4);
         if (var6.b("boolean")) {
            var3 = 0;
         } else if (var6.b("double")) {
            var3 = 1;
         } else if (var6.b("String")) {
            var3 = 2;
         }

         var10.add(this.a(var7, var3, var1.opCode));
         ++var4;
      }
   }

   private String a(String var1) {
      StringBuilder var5 = new StringBuilder(4096);
      CharBuffer var7 = CharBuffer.wrap(var1);

      for(int var3 = 0; var3 < var7.length(); ++var3) {
         char var2 = var7.get(var3);
         if (var2 == '"') {
            var5.append("\\\"");
         } else if (var2 == '\\') {
            if (var3 < var7.length() - 1) {
               int var4 = var3 + 1;
               var2 = var7.get(var4);
               if (var2 != 'n' && var2 != 't') {
                  var5.append("\\\\");
               } else {
                  StringBuilder var6 = new StringBuilder();
                  var6.append("\\");
                  var6.append(var2);
                  var5.append(var6.toString());
                  var3 = var4;
               }
            } else {
               var5.append("\\\\");
            }
         } else if (var2 == '\n') {
            var5.append("\\n");
         } else {
            var5.append(var2);
         }
      }

      return var5.toString();
   }

   private String a(String var1, int var2, String var3) {
      if (var1.length() > 0 && var1.charAt(0) == '@') {
         var3 = this.a(var1.substring(1), var3);
         var1 = var3;
         if (var2 == 2) {
            var1 = var3;
            if (var3.length() <= 0) {
               var1 = "\"\"";
            }
         }

         return var1;
      } else {
         StringBuilder var6;
         if (var2 == 2) {
            var6 = new StringBuilder();
            var6.append("\"");
            var6.append(this.a(var1));
            var6.append("\"");
            return var6.toString();
         } else {
            if (var2 == 1) {
               try {
                  Integer.parseInt(var1);
                  return var1;
               } catch (NumberFormatException var5) {
                  try {
                     Double.parseDouble(var1);
                     var6 = new StringBuilder();
                     var6.append(var1);
                     var6.append("d");
                     var3 = var6.toString();
                     return var3;
                  } catch (NumberFormatException var4) {
                  }
               }
            }

            return var1;
         }
      }
   }

   private String a(String var1, String var2) {
      if (!this.g.containsKey(var1)) {
         return "";
      } else {
         BlockBean var3 = (BlockBean)this.g.get(var1);
         return this.a(var3, var2);
      }
   }

   private boolean b(String var1, String var2) {
      String[] var8 = this.a;
      int var4 = var8.length;
      boolean var7 = false;
      int var3 = 0;

      boolean var11;
      while(true) {
         if (var3 >= var4) {
            var11 = false;
            break;
         }

         String var9 = var8[var3];
         if (var9.equals(var2)) {
            var11 = true;
            break;
         }

         ++var3;
      }

      String[] var10 = this.b;
      int var5 = var10.length;
      var4 = 0;

      boolean var12;
      while(true) {
         if (var4 >= var5) {
            var12 = false;
            break;
         }

         String var13 = var10[var4];
         if (var13.equals(var1)) {
            var12 = true;
            break;
         }

         ++var4;
      }

      boolean var6 = var7;
      if (var11) {
         var6 = var7;
         if (var12) {
            var6 = true;
         }
      }

      return var6;
   }

   public String a() {
      this.g = new HashMap();
      if (this.f != null && this.f.size() > 0) {
         Iterator var1 = this.f.iterator();

         while(var1.hasNext()) {
            BlockBean var2 = (BlockBean)var1.next();
            this.g.put(var2.id, var2);
         }

         StringBuilder var3 = new StringBuilder(4096);
         var3.append(this.a((BlockBean)this.f.get(0), ""));
         return var3.toString();
      } else {
         return "";
      }
   }
}
