package com.nexusteam.internal;

import android.util.Pair;
import com.nexusteam.internal.beans.BlockBean;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import android.util.Log;

public class hb {
	private String[] a = new String[]{"repeat", "+", "-", "*", "/", "%", ">", "=", "<", "&&", "||", "not"};
	private String[] b = new String[]{"+", "-", "*", "/", "%", ">", "=", "<", "&&", "||"};
	private String c;
	private String d;
	private es e;
	private ArrayList<BlockBean> f;
	private Map<String, BlockBean> g = null;
	private Map<String, String> customBlockSpecs = new HashMap<>();
	private String BLOCK_PATH = "/storage/emulated/0/.blacklogics/resources/block/My Block/block.json";
	
	public hb(String var1, es var2, String var3, ArrayList<BlockBean> var4) {
		this.c = var1;
		this.e = var2;
		this.d = var3;
		this.f = var4;
		this.loadCustomBlockSpecs();
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
				var16 = var1.opCode;
				var13 = resolveOpIndex(var16);
				
				
				var16 = generateOpCode(var1, var10, var13, var16);
				
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
	



	private short resolveOpIndex(String opcode) {
		short r;
		r = resolveOpIndex0(opcode);
		if (r >= 0) return r;
		r = resolveOpIndex1(opcode);
		if (r >= 0) return r;
		r = resolveOpIndex2(opcode);
		if (r >= 0) return r;
		r = resolveOpIndex3(opcode);
		if (r >= 0) return r;
		return -1;
	}

	private short resolveOpIndex0(String opcode) {
		switch(opcode.hashCode()) {
						case -2135695280:
						if (opcode.equals("webViewLoadUrl")) {
							return 171;
						}
						break;
						case -2120571577:
						if (opcode.equals("mapIsEmpty")) {
							return 15;
						}
						break;
						case -2114384168:
						if (opcode.equals("firebasestorageDownloadFile")) {
							return 262;
						}
						break;
						case -2055793167:
						if (opcode.equals("fileutillistdir")) {
							return 271;
						}
						break;
						case -2037144358:
						if (opcode.equals("bluetoothConnectStartConnectionToUuid")) {
							return 315;
						}
						break;
						case -2027093331:
						if (opcode.equals("calendarViewSetDate")) {
							return 184;
						}
						break;
						case -2020761366:
						if (opcode.equals("fileRemoveData")) {
							return 133;
						}
						break;
						case -1998407506:
						if (opcode.equals("listSetData")) {
							return 159;
						}
						break;
						case -1989678633:
						if (opcode.equals("mapViewSetMarkerVisible")) {
							return 198;
						}
						break;
						case -1979147952:
						if (opcode.equals("stringContains")) {
							return 63;
						}
						break;
						case -1975568730:
						if (opcode.equals("copyToClipboard")) {
							return 120;
						}
						break;
						case -1966668787:
						if (opcode.equals("firebaseauthSignOutUser")) {
							return 215;
						}
						break;
						case -1937348542:
						if (opcode.equals("firebaseStartListen")) {
							return 216;
						}
						break;
						case -1922362317:
						if (opcode.equals("getExternalStorageDir")) {
							return 278;
						}
						break;
						case -1920517885:
						if (opcode.equals("setVarBoolean")) {
							return 3;
						}
						break;
						case -1919300188:
						if (opcode.equals("toNumber")) {
							return 67;
						}
						break;
						case -1910071024:
						if (opcode.equals("objectanimatorSetDuration")) {
							return 251;
						}
						break;
						case -1886802639:
						if (opcode.equals("soundpoolLoad")) {
							return 238;
						}
						break;
						case -1834369666:
						if (opcode.equals("setBitmapFileBrightness")) {
							return 290;
						}
						break;
						case -1812313351:
						if (opcode.equals("setColorFilter")) {
							return 117;
						}
						break;
						case -1778201036:
						if (opcode.equals("listSmoothScrollTo")) {
							return 166;
						}
						break;
						case -1776922004:
						if (opcode.equals("toString")) {
							return 72;
						}
						break;
						case -1749698255:
						if (opcode.equals("mediaplayerPause")) {
							return 228;
						}
						break;
						case -1747734390:
						if (opcode.equals("mediaplayerReset")) {
							return 232;
						}
						break;
						case -1746380899:
						if (opcode.equals("mediaplayerStart")) {
							return 227;
						}
						break;
						case -1718917155:
						if (opcode.equals("mediaplayerSeek")) {
							return 229;
						}
						break;
						case -1699631195:
						if (opcode.equals("isDrawerOpen")) {
							return 105;
						}
						break;
						case -1699349926:
						if (opcode.equals("objectanimatorSetRepeatMode")) {
							return 252;
						}
						break;
						case -1684072208:
						if (opcode.equals("intentSetData")) {
							return 123;
						}
						break;
						case -1679834825:
						if (opcode.equals("setTrackResource")) {
							return 242;
						}
						break;
						case -1666623936:
						if (opcode.equals("speechToTextShutdown")) {
							return 311;
						}
						break;
						case -1573371685:
						if (opcode.equals("stringJoin")) {
							return 58;
						}
						break;
						case -1541653284:
						if (opcode.equals("objectanimatorStart")) {
							return 255;
						}
						break;
						case -1530840255:
						if (opcode.equals("stringIndex")) {
							return 59;
						}
						break;
						case -1528850031:
						if (opcode.equals("startActivity")) {
							return 128;
						}
						break;
						case -1526161572:
						if (opcode.equals("setBgColor")) {
							return 113;
						}
						break;
						case -1513446476:
						if (opcode.equals("dialogCancelButton")) {
							return 224;
						}
						break;
						case -1512519571:
						if (opcode.equals("definedFunc")) {
							return 0;
						}
						break;
						case -1483954587:
						if (opcode.equals("fileutilisdir")) {
							return 272;
						}
						break;
						case -1477942289:
						if (opcode.equals("mediaplayerIsLooping")) {
							return 236;
						}
						break;
						case -1471049951:
						if (opcode.equals("fileutilwrite")) {
							return 265;
						}
						break;
						case -1440042085:
						if (opcode.equals("spnSetSelection")) {
							return 169;
						}
						break;
						case -1438040951:
						if (opcode.equals("seekBarGetMax")) {
							return 246;
						}
						break;
						case -1422112391:
						if (opcode.equals("bluetoothConnectIsBluetoothEnabled")) {
							return 318;
						}
						break;
						case -1405157727:
						if (opcode.equals("fileutilmakedir")) {
							return 270;
						}
						break;
						case -1385076635:
						if (opcode.equals("dialogShow")) {
							return 222;
						}
						break;
						case -1384861688:
						if (opcode.equals("getAtListInt")) {
							return 19;
						}
						break;
						case -1384858251:
						if (opcode.equals("getAtListMap")) {
							return 29;
						}
						break;
						case -1384851894:
						if (opcode.equals("getAtListStr")) {
							return 24;
						}
						break;
						case -1377080719:
						if (opcode.equals("decreaseInt")) {
							return 6;
						}
						break;
						case -1376608975:
						if (opcode.equals("calendarSetTime")) {
							return 140;
						}
						break;
						case -1361468284:
						if (opcode.equals("viewOnClick")) {
							return 104;
						}
						break;
						case -1348085287:
						if (opcode.equals("mapViewZoomIn")) {
							return 191;
						}
						break;
						case -1348084945:
						if (opcode.equals("mapViewZoomTo")) {
							return 190;
						}
						break;
						case -1304067438:
						if (opcode.equals("firebaseDelete")) {
							return 206;
						}
						break;
						case -1272546178:
						if (opcode.equals("dialogSetTitle")) {
							return 220;
						}
						break;
						case -1271141237:
						if (opcode.equals("clearList")) {
							return 37;
						}
						break;
						case -1249367264:
						if (opcode.equals("getArg")) {
							return 1;
						}
						break;
						case -1249347599:
						if (opcode.equals("getVar")) {
							return 2;
						}
						break;
						case -1217704075:
						if (opcode.equals("objectanimatorSetValue")) {
							return 249;
						}
						break;
						case -1206794099:
						if (opcode.equals("getLocationX")) {
							return 155;
						}
						break;
						case -1206794098:
						if (opcode.equals("getLocationY")) {
							return 156;
						}
						break;
						case -1195899442:
						if (opcode.equals("bluetoothConnectSendData")) {
							return 317;
						}
						break;
						case -1192544266:
						if (opcode.equals("ifElse")) {
							return 41;
						}
						break;
						case -1185284274:
						if (opcode.equals("gyroscopeStopListen")) {
							return 219;
						}
						break;
						case -1182878167:
						if (opcode.equals("firebaseauthGetUid")) {
							return 213;
						}
						break;
						case -1160374245:
						if (opcode.equals("bluetoothConnectReadyConnectionToUuid")) {
							return 313;
						}
						break;
						case -1149848189:
						if (opcode.equals("toStringFormat")) {
							return 74;
						}
						break;
						case -1149458632:
						if (opcode.equals("objectanimatorSetRepeatCount")) {
							return 253;
						}
						break;
						case -1143684675:
						if (opcode.equals("firebaseauthGetCurrentUser")) {
							return 212;
						}
						break;
						case -1139353316:
						if (opcode.equals("setListMap")) {
							return 30;
						}
						break;
						case -1137582698:
						if (opcode.equals("toLowerCase")) {
							return 71;
						}
						break;
						case -1123431291:
						if (opcode.equals("calnedarViewSetMaxDate")) {
							return 186;
						}
						break;
						case -1107376988:
						if (opcode.equals("webViewGoForward")) {
							return 177;
						}
						break;
						case -1106141754:
						if (opcode.equals("webViewCanGoBack")) {
							return 174;
						}
						break;
						case -1094491139:
						if (opcode.equals("seekBarSetMax")) {
							return 245;
						}
						break;
						case -1081400230:
						if (opcode.equals("mapGet")) {
							return 10;
						}
						break;
						case -1081391085:
						if (opcode.equals("mapPut")) {
							return 9;
						}
						break;
						case -1081250015:
						if (opcode.equals("mathPi")) {
							return 83;
						}
						break;
						case -1063598745:
						if (opcode.equals("resizeBitmapFileRetainRatio")) {
							return 281;
						}
						break;
						case -1043233275:
						if (opcode.equals("mediaplayerGetDuration")) {
							return 231;
						}
						break;
						case -1033658254:
						if (opcode.equals("mathGetDisplayWidth")) {
							return 81;
						}
						break;
						case -1021852352:
						if (opcode.equals("objectanimatorCancel")) {
							return 256;
						}
						break;

		}
		return -1;
	}


	private short resolveOpIndex1(String opcode) {
		switch(opcode.hashCode()) {
						case -1007787615:
						if (opcode.equals("mediaplayerSetLooping")) {
							return 235;
						}
						break;
						case -996870276:
						if (opcode.equals("insertMapToList")) {
							return 33;
						}
						break;
						case -995908985:
						if (opcode.equals("soundpoolCreate")) {
							return 237;
						}
						break;
						case -941420147:
						if (opcode.equals("fileSetFileName")) {
							return 130;
						}
						break;
						case -938285885:
						if (opcode.equals("random")) {
							return 56;
						}
						break;
						case -934531685:
						if (opcode.equals("repeat")) {
							return 39;
						}
						break;
						case -918173448:
						if (opcode.equals("listGetCheckedPosition")) {
							return 163;
						}
						break;
						case -917343271:
						if (opcode.equals("getJpegRotate")) {
							return 292;
						}
						break;
						case -911199919:
						if (opcode.equals("objectanimatorSetProperty")) {
							return 248;
						}
						break;
						case -903177036:
						if (opcode.equals("resizeBitmapFileWithRoundedBorder")) {
							return 284;
						}
						break;
						case -883988307:
						if (opcode.equals("dialogSetMessage")) {
							return 221;
						}
						break;
						case -869293886:
						if (opcode.equals("finishActivity")) {
							return 129;
						}
						break;
						case -854558288:
						if (opcode.equals("setVisible")) {
							return 141;
						}
						break;
						case -853550561:
						if (opcode.equals("timerCancel")) {
							return 202;
						}
						break;
						case -831887360:
						if (opcode.equals("textToSpeechShutdown")) {
							return 308;
						}
						break;
						case -733318734:
						if (opcode.equals("strToListMap")) {
							return 78;
						}
						break;
						case -697616870:
						if (opcode.equals("camerastarttakepicture")) {
							return 294;
						}
						break;
						case -677662361:
						if (opcode.equals("forever")) {
							return 38;
						}
						break;
						case -668992194:
						if (opcode.equals("stringReplaceAll")) {
							return 66;
						}
						break;
						case -664474111:
						if (opcode.equals("intentSetFlags")) {
							return 126;
						}
						break;
						case -649691581:
						if (opcode.equals("objectanimatorSetInterpolator")) {
							return 254;
						}
						break;
						case -636363854:
						if (opcode.equals("webViewGetUrl")) {
							return 172;
						}
						break;
						case -628607128:
						if (opcode.equals("webViewGoBack")) {
							return 176;
						}
						break;
						case -621198621:
						if (opcode.equals("speechToTextStartListening")) {
							return 309;
						}
						break;
						case -602241037:
						if (opcode.equals("fileutilcopy")) {
							return 266;
						}
						break;
						case -601942961:
						if (opcode.equals("fileutilmove")) {
							return 267;
						}
						break;
						case -601804268:
						if (opcode.equals("fileutilread")) {
							return 264;
						}
						break;
						case -578987803:
						if (opcode.equals("setChecked")) {
							return 157;
						}
						break;
						case -509946902:
						if (opcode.equals("spnRefresh")) {
							return 168;
						}
						break;
						case -439342016:
						if (opcode.equals("webViewClearHistory")) {
							return 179;
						}
						break;
						case -437272040:
						if (opcode.equals("bluetoothConnectGetRandomUuid")) {
							return 322;
						}
						break;
						case -425293664:
						if (opcode.equals("setClickable")) {
							return 142;
						}
						break;
						case -418212114:
						if (opcode.equals("firebaseGetChildren")) {
							return 207;
						}
						break;
						case -411705840:
						if (opcode.equals("fileSetData")) {
							return 132;
						}
						break;
						case -399551817:
						if (opcode.equals("toUpperCase")) {
							return 70;
						}
						break;
						case -390304998:
						if (opcode.equals("mapViewAddMarker")) {
							return 193;
						}
						break;
						case -356866884:
						if (opcode.equals("webViewSetCacheMode")) {
							return 173;
						}
						break;
						case -353129373:
						if (opcode.equals("calendarDiff")) {
							return 138;
						}
						break;
						case -329562760:
						if (opcode.equals("insertListInt")) {
							return 18;
						}
						break;
						case -329559323:
						if (opcode.equals("insertListMap")) {
							return 28;
						}
						break;
						case -329552966:
						if (opcode.equals("insertListStr")) {
							return 23;
						}
						break;
						case -322651344:
						if (opcode.equals("stringEquals")) {
							return 62;
						}
						break;
						case -283328259:
						if (opcode.equals("intentPutExtra")) {
							return 125;
						}
						break;
						case -258774775:
						if (opcode.equals("closeDrawer")) {
							return 107;
						}
						break;
						case -247015294:
						if (opcode.equals("mediaplayerRelease")) {
							return 233;
						}
						break;
						case -208762465:
						if (opcode.equals("toStringWithDecimal")) {
							return 73;
						}
						break;
						case -189292433:
						if (opcode.equals("stringSub")) {
							return 61;
						}
						break;
						case -152473824:
						if (opcode.equals("firebaseauthIsLoggedIn")) {
							return 211;
						}
						break;
						case -149850417:
						if (opcode.equals("fileutilisexist")) {
							return 269;
						}
						break;
						case -133532073:
						if (opcode.equals("stringLength")) {
							return 57;
						}
						break;
						case -96313603:
						if (opcode.equals("containListInt")) {
							return 21;
						}
						break;
						case -96310166:
						if (opcode.equals("containListMap")) {
							return 31;
						}
						break;
						case -96303809:
						if (opcode.equals("containListStr")) {
							return 26;
						}
						break;
						case -83301935:
						if (opcode.equals("webViewZoomIn")) {
							return 181;
						}
						break;
						case -83186725:
						if (opcode.equals("openDrawer")) {
							return 106;
						}
						break;
						case -75125341:
						if (opcode.equals("getText")) {
							return 112;
						}
						break;
						case -60494417:
						if (opcode.equals("vibratorAction")) {
							return 199;
						}
						break;
						case -60048101:
						if (opcode.equals("firebaseauthResetPassword")) {
							return 214;
						}
						break;
						case -24451690:
						if (opcode.equals("dialogOkButton")) {
							return 223;
						}
						break;
						case -14362103:
						if (opcode.equals("bluetoothConnectIsBluetoothActivated")) {
							return 319;
						}
						break;
						case -10599306:
						if (opcode.equals("firebaseauthCreateUser")) {
							return 208;
						}
						break;
						case -9742826:
						if (opcode.equals("firebaseGetPushKey")) {
							return 205;
						}
						break;
						case 37:
						if (opcode.equals("%")) {
							return 50;
						}
						break;
						case 42:
						if (opcode.equals("*")) {
							return 48;
						}
						break;
						case 43:
						if (opcode.equals("+")) {
							return 46;
						}
						break;
						case 45:
						if (opcode.equals("-")) {
							return 47;
						}
						break;
						case 47:
						if (opcode.equals("/")) {
							return 49;
						}
						break;
						case 60:
						if (opcode.equals("<")) {
							return 52;
						}
						break;
						case 61:
						if (opcode.equals("=")) {
							return 53;
						}
						break;
						case 62:
						if (opcode.equals(">")) {
							return 51;
						}
						break;
						case 1216:
						if (opcode.equals("&&")) {
							return 54;
						}
						break;
						case 3357:
						if (opcode.equals("if")) {
							return 40;
						}
						break;
						case 3968:
						if (opcode.equals("||")) {
							return 55;
						}
						break;
						case 109267:
						if (opcode.equals("not")) {
							return 45;
						}
						break;
						case 3568674:
						if (opcode.equals("trim")) {
							return 69;
						}
						break;
						case 1192854929:
						if (opcode.equals("stringReverse")) {
							return 325;
						}
						break;
						case 127650107:
						if (opcode.equals("faReturnFragment")) {
							return 326;
						}
						break;
						case -55076019:
						if (opcode.equals("faReturnTitle")) {
							return 327;
						}
						break;
						case -70596092:
						if (opcode.equals("faReturnCount")) {
							return 328;
						}
						break;
						case 3569038:
						if (opcode.equals("true")) {
							return 43;
						}
						break;
						case 8255701:
						if (opcode.equals("calendarFormat")) {
							return 137;
						}
						break;
						case 16308074:
						if (opcode.equals("resizeBitmapFileToCircle")) {
							return 283;
						}
						break;
						case 25469951:
						if (opcode.equals("bluetoothConnectActivateBluetooth")) {
							return 320;
						}
						break;

		}
		return -1;
	}


	private short resolveOpIndex2(String opcode) {
		switch(opcode.hashCode()) {
						case 27679870:
						if (opcode.equals("calendarGetNow")) {
							return 134;
						}
						break;
						case 56167279:
						if (opcode.equals("setBitmapFileContrast")) {
							return 291;
						}
						break;
						case 61585857:
						if (opcode.equals("firebasePush")) {
							return 204;
						}
						break;
						case 94001407:
						if (opcode.equals("break")) {
							return 42;
						}
						break;
						case 97196323:
						if (opcode.equals("false")) {
							return 44;
						}
						break;
						case 103668285:
						if (opcode.equals("mathE")) {
							return 84;
						}
						break;
						case 125431087:
						if (opcode.equals("speechToTextStopListening")) {
							return 310;
						}
						break;
						case 134874756:
						if (opcode.equals("listSetCustomViewData")) {
							return 160;
						}
						break;
						case 152967761:
						if (opcode.equals("mapClear")) {
							return 14;
						}
						break;
						case 163812602:
						if (opcode.equals("cropBitmapFileFromCenter")) {
							return 285;
						}
						break;
						case 168740282:
						if (opcode.equals("mapToStr")) {
							return 77;
						}
						break;
						case 182549637:
						if (opcode.equals("setEnable")) {
							return 108;
						}
						break;
						case 207764385:
						if (opcode.equals("calendarViewGetDate")) {
							return 183;
						}
						break;
						case 255417137:
						if (opcode.equals("adViewLoadAd")) {
							return 187;
						}
						break;
						case 262073061:
						if (opcode.equals("bluetoothConnectReadyConnection")) {
							return 312;
						}
						break;
						case 276674391:
						if (opcode.equals("mapViewMoveCamera")) {
							return 189;
						}
						break;
						case 297379706:
						if (opcode.equals("textToSpeechSetSpeechRate")) {
							return 304;
						}
						break;
						case 300372142:
						if (opcode.equals("mathAcos")) {
							return 97;
						}
						break;
						case 300387327:
						if (opcode.equals("mathAsin")) {
							return 96;
						}
						break;
						case 300388040:
						if (opcode.equals("mathAtan")) {
							return 98;
						}
						break;
						case 300433453:
						if (opcode.equals("mathCeil")) {
							return 91;
						}
						break;
						case 300921928:
						if (opcode.equals("mathSqrt")) {
							return 88;
						}
						break;
						case 317453636:
						if (opcode.equals("textToSpeechIsSpeaking")) {
							return 306;
						}
						break;
						case 342026220:
						if (opcode.equals("interstitialadShow")) {
							return 260;
						}
						break;
						case 348377823:
						if (opcode.equals("soundpoolStreamPlay")) {
							return 239;
						}
						break;
						case 348475309:
						if (opcode.equals("soundpoolStreamStop")) {
							return 240;
						}
						break;
						case 389111867:
						if (opcode.equals("spnSetData")) {
							return 167;
						}
						break;
						case 397166713:
						if (opcode.equals("getEnable")) {
							return 109;
						}
						break;
						case 401012285:
						if (opcode.equals("getTranslationX")) {
							return 148;
						}
						break;
						case 401012286:
						if (opcode.equals("getTranslationY")) {
							return 150;
						}
						break;
						case 404247683:
						if (opcode.equals("calendarAdd")) {
							return 135;
						}
						break;
						case 404265028:
						if (opcode.equals("calendarSet")) {
							return 136;
						}
						break;
						case 442768763:
						if (opcode.equals("mapGetAllKeys")) {
							return 16;
						}
						break;
						case 463560551:
						if (opcode.equals("mapContainKey")) {
							return 11;
						}
						break;
						case 463594049:
						if (opcode.equals("objectanimatorSetFromTo")) {
							return 250;
						}
						break;
						case 470160234:
						if (opcode.equals("fileutilGetLastSegmentPath")) {
							return 277;
						}
						break;
						case 475815924:
						if (opcode.equals("setTextColor")) {
							return 115;
						}
						break;
						case 481850295:
						if (opcode.equals("resizeBitmapFileToSquare")) {
							return 282;
						}
						break;
						case 490702942:
						if (opcode.equals("filepickerstartpickfiles")) {
							return 293;
						}
						break;
						case 501171279:
						if (opcode.equals("mathToDegree")) {
							return 103;
						}
						break;
						case 530759231:
						if (opcode.equals("progressBarSetIndeterminate")) {
							return 302;
						}
						break;
						case 548860462:
						if (opcode.equals("webViewClearCache")) {
							return 178;
						}
						break;
						case 556217437:
						if (opcode.equals("setRotate")) {
							return 143;
						}
						break;
						case 571046965:
						if (opcode.equals("scaleBitmapFile")) {
							return 287;
						}
						break;
						case 573208400:
						if (opcode.equals("setScaleX")) {
							return 151;
						}
						break;
						case 573208401:
						if (opcode.equals("setScaleY")) {
							return 153;
						}
						break;
						case 573295520:
						if (opcode.equals("listGetCheckedCount")) {
							return 165;
						}
						break;
						case 601235430:
						if (opcode.equals("currentTime")) {
							return 68;
						}
						break;
						case 610313513:
						if (opcode.equals("getMapInList")) {
							return 34;
						}
						break;
						case 615286641:
						if (opcode.equals("dialogNeutralButton")) {
							return 225;
						}
						break;
						case 657721930:
						if (opcode.equals("setVarInt")) {
							return 4;
						}
						break;
						case 683193060:
						if (opcode.equals("bluetoothConnectStartConnection")) {
							return 314;
						}
						break;
						case 725249532:
						if (opcode.equals("intentSetAction")) {
							return 122;
						}
						break;
						case 726487524:
						if (opcode.equals("mathFloor")) {
							return 92;
						}
						break;
						case 726877492:
						if (opcode.equals("mapViewSetMarkerIcon")) {
							return 197;
						}
						break;
						case 726887785:
						if (opcode.equals("mapViewSetMarkerInfo")) {
							return 194;
						}
						break;
						case 732108347:
						if (opcode.equals("mathLog10")) {
							return 101;
						}
						break;
						case 737664870:
						if (opcode.equals("mathRound")) {
							return 90;
						}
						break;
						case 738846120:
						if (opcode.equals("textToSpeechSetPitch")) {
							return 303;
						}
						break;
						case 747168008:
						if (opcode.equals("mapCreateNew")) {
							return 8;
						}
						break;
						case 754442829:
						if (opcode.equals("increaseInt")) {
							return 5;
						}
						break;
						case 762282303:
						if (opcode.equals("indexListInt")) {
							return 20;
						}
						break;
						case 762292097:
						if (opcode.equals("indexListStr")) {
							return 25;
						}
						break;
						case 770834513:
						if (opcode.equals("getRotate")) {
							return 144;
						}
						break;
						case 787825476:
						if (opcode.equals("getScaleX")) {
							return 152;
						}
						break;
						case 787825477:
						if (opcode.equals("getScaleY")) {
							return 154;
						}
						break;
						case 797861524:
						if (opcode.equals("addMapToList")) {
							return 32;
						}
						break;
						case 836692861:
						if (opcode.equals("mapSize")) {
							return 13;
						}
						break;
						case 840973386:
						if (opcode.equals("mathAbs")) {
							return 89;
						}
						break;
						case 840975711:
						if (opcode.equals("mathCos")) {
							return 94;
						}
						break;
						case 840977909:
						if (opcode.equals("mathExp")) {
							return 99;
						}
						break;
						case 840984348:
						if (opcode.equals("mathLog")) {
							return 100;
						}
						break;
						case 840984892:
						if (opcode.equals("mathMax")) {
							return 87;
						}
						break;
						case 840985130:
						if (opcode.equals("mathMin")) {
							return 86;
						}
						break;
						case 840988208:
						if (opcode.equals("mathPow")) {
							return 85;
						}
						break;
						case 840990896:
						if (opcode.equals("mathSin")) {
							return 93;
						}
						break;
						case 840991609:
						if (opcode.equals("mathTan")) {
							return 95;
						}
						break;
						case 845089750:
						if (opcode.equals("setVarString")) {
							return 7;
						}
						break;
						case 848786445:
						if (opcode.equals("objectanimatorSetTarget")) {
							return 247;
						}
						break;
						case 858248741:
						if (opcode.equals("calendarGetTime")) {
							return 139;
						}
						break;
						case 898187172:
						if (opcode.equals("mathToRadian")) {
							return 102;
						}
						break;
						case 932259189:
						if (opcode.equals("setBgResource")) {
							return 114;
						}
						break;
						case 937017988:
						if (opcode.equals("gyroscopeStartListen")) {
							return 218;
						}
						break;

		}
		return -1;
	}


	private short resolveOpIndex3(String opcode) {
		switch(opcode.hashCode()) {
						case 948234497:
						if (opcode.equals("webViewStopLoading")) {
							return 180;
						}
						break;
						case 950609198:
						if (opcode.equals("setBitmapFileColorFilter")) {
							return 289;
						}
						break;
						case 1053179400:
						if (opcode.equals("mapViewSetMarkerColor")) {
							return 196;
						}
						break;
						case 1068548733:
						if (opcode.equals("mathGetDip")) {
							return 80;
						}
						break;
						case 1086207657:
						if (opcode.equals("fileutildelete")) {
							return 268;
						}
						break;
						case 1088879149:
						if (opcode.equals("setHintTextColor")) {
							return 298;
						}
						break;
						case 1090517587:
						if (opcode.equals("getPackageDataDir")) {
							return 279;
						}
						break;
						case 1102670563:
						if (opcode.equals("requestnetworkSetHeaders")) {
							return 300;
						}
						break;
						case 1129709718:
						if (opcode.equals("setImageUrl")) {
							return 296;
						}
						break;
						case 1142897724:
						if (opcode.equals("firebaseauthSignInUser")) {
							return 209;
						}
						break;
						case 1156598140:
						if (opcode.equals("fileutilEndsWith")) {
							return 276;
						}
						break;
						case 1159035162:
						if (opcode.equals("mapViewZoomOut")) {
							return 192;
						}
						break;
						case 1160674468:
						if (opcode.equals("lengthList")) {
							return 36;
						}
						break;
						case 1162069698:
						if (opcode.equals("setThumbResource")) {
							return 241;
						}
						break;
						case 1179719371:
						if (opcode.equals("stringLastIndex")) {
							return 60;
						}
						break;
						case 1187505507:
						if (opcode.equals("stringReplace")) {
							return 64;
						}
						break;
						case 1216249183:
						if (opcode.equals("firebasestorageDelete")) {
							return 263;
						}
						break;
						case 1219071185:
						if (opcode.equals("firebasestorageUploadFile")) {
							return 261;
						}
						break;
						case 1219299503:
						if (opcode.equals("objectanimatorIsRunning")) {
							return 257;
						}
						break;
						case 1220078450:
						if (opcode.equals("addSourceDirectly")) {
							return 75;
						}
						break;
						case 1236956449:
						if (opcode.equals("mediaplayerCreate")) {
							return 226;
						}
						break;
						case 1240510514:
						if (opcode.equals("intentSetScreen")) {
							return 124;
						}
						break;
						case 1242107556:
						if (opcode.equals("fileutilisfile")) {
							return 273;
						}
						break;
						case 1252547704:
						if (opcode.equals("listMapToStr")) {
							return 79;
						}
						break;
						case 1280029577:
						if (opcode.equals("requestFocus")) {
							return 118;
						}
						break;
						case 1303367340:
						if (opcode.equals("textToSpeechStop")) {
							return 307;
						}
						break;
						case 1305932583:
						if (opcode.equals("spnGetSelection")) {
							return 170;
						}
						break;
						case 1311764809:
						if (opcode.equals("setTranslationX")) {
							return 147;
						}
						break;
						case 1311764810:
						if (opcode.equals("setTranslationY")) {
							return 149;
						}
						break;
						case 1313527577:
						if (opcode.equals("setTypeface")) {
							return 111;
						}
						break;
						case 1315302372:
						if (opcode.equals("fileutillength")) {
							return 274;
						}
						break;
						case 1330354473:
						if (opcode.equals("firebaseauthSignInAnonymously")) {
							return 210;
						}
						break;
						case 1343794064:
						if (opcode.equals("listSetItemChecked")) {
							return 162;
						}
						break;
						case 1348133645:
						if (opcode.equals("stringReplaceFirst")) {
							return 65;
						}
						break;
						case 1387622940:
						if (opcode.equals("setAlpha")) {
							return 145;
						}
						break;
						case 1395026457:
						if (opcode.equals("setImage")) {
							return 116;
						}
						break;
						case 1397501021:
						if (opcode.equals("listRefresh")) {
							return 161;
						}
						break;
						case 1405084438:
						if (opcode.equals("setTitle")) {
							return 121;
						}
						break;
						case 1410284340:
						if (opcode.equals("seekBarSetProgress")) {
							return 243;
						}
						break;
						case 1431171391:
						if (opcode.equals("mapRemoveKey")) {
							return 12;
						}
						break;
						case 1437288110:
						if (opcode.equals("getPublicDir")) {
							return 280;
						}
						break;
						case 1470831563:
						if (opcode.equals("intentGetString")) {
							return 127;
						}
						break;
						case 1498864168:
						if (opcode.equals("seekBarGetProgress")) {
							return 244;
						}
						break;
						case 1601394299:
						if (opcode.equals("listGetCheckedPositions")) {
							return 164;
						}
						break;
						case 1633341847:
						if (opcode.equals("timerAfter")) {
							return 200;
						}
						break;
						case 1635356258:
						if (opcode.equals("requestnetworkStartRequestNetwork")) {
							return 301;
						}
						break;
						case 1637498582:
						if (opcode.equals("timerEvery")) {
							return 201;
						}
						break;
						case 1695890133:
						if (opcode.equals("fileutilStartsWith")) {
							return 275;
						}
						break;
						case 1712613410:
						if (opcode.equals("webViewZoomOut")) {
							return 182;
						}
						break;
						case 1749552744:
						if (opcode.equals("textToSpeechSpeak")) {
							return 305;
						}
						break;
						case 1764351209:
						if (opcode.equals("deleteList")) {
							return 35;
						}
						break;
						case 1775620400:
						if (opcode.equals("strToMap")) {
							return 76;
						}
						break;
						case 1779174257:
						if (opcode.equals("getChecked")) {
							return 158;
						}
						break;
						case 1792552710:
						if (opcode.equals("rotateBitmapFile")) {
							return 286;
						}
						break;
						case 1814870108:
						if (opcode.equals("doToast")) {
							return 119;
						}
						break;
						case 1820536363:
						if (opcode.equals("interstitialadCreate")) {
							return 258;
						}
						break;
						case 1823151876:
						if (opcode.equals("fileGetData")) {
							return 131;
						}
						break;
						case 1848365301:
						if (opcode.equals("mapViewSetMapType")) {
							return 188;
						}
						break;
						case 1873103950:
						if (opcode.equals("locationManagerRemoveUpdates")) {
							return 324;
						}
						break;
						case 1883337723:
						if (opcode.equals("mathGetDisplayHeight")) {
							return 82;
						}
						break;
						case 1885231494:
						if (opcode.equals("webViewCanGoForward")) {
							return 175;
						}
						break;
						case 1908132964:
						if (opcode.equals("mapViewSetMarkerPosition")) {
							return 195;
						}
						break;
						case 1908582864:
						if (opcode.equals("firebaseStopListen")) {
							return 217;
						}
						break;
						case 1923980937:
						if (opcode.equals("requestnetworkSetParams")) {
							return 299;
						}
						break;
						case 1941634330:
						if (opcode.equals("firebaseAdd")) {
							return 203;
						}
						break;
						case 1948735400:
						if (opcode.equals("getAlpha")) {
							return 146;
						}
						break;
						case 1964823036:
						if (opcode.equals("bluetoothConnectStopConnection")) {
							return 316;
						}
						break;
						case 1973523807:
						if (opcode.equals("mediaplayerIsPlaying")) {
							return 234;
						}
						break;
						case 1974249461:
						if (opcode.equals("skewBitmapFile")) {
							return 288;
						}
						break;
						case 1976325370:
						if (opcode.equals("setImageFilePath")) {
							return 295;
						}
						break;
						case 1984630281:
						if (opcode.equals("setHint")) {
							return 297;
						}
						break;
						case 1984984239:
						if (opcode.equals("setText")) {
							return 110;
						}
						break;
						case 2017929665:
						if (opcode.equals("calendarViewSetMinDate")) {
							return 185;
						}
						break;
						case 2075310296:
						if (opcode.equals("interstitialadLoadAd")) {
							return 259;
						}
						break;
						case 2090179216:
						if (opcode.equals("addListInt")) {
							return 17;
						}
						break;
						case 2090182653:
						if (opcode.equals("addListMap")) {
							return 27;
						}
						break;
						case 2090189010:
						if (opcode.equals("addListStr")) {
							return 22;
						}
						break;
						case 2127377128:
						if (opcode.equals("mediaplayerGetCurrent")) {
							return 230;
						}
						break;
						case 2130649194:
						if (opcode.equals("bluetoothConnectGetPairedDevices")) {
							return 321;
						}
						break;
						case 2138225950:
						if (opcode.equals("locationManagerRequestLocationUpdates")) {
							return 323;
						}
						break;
		}
		return -1;
	}

	private String generateOpCode(BlockBean var1, ArrayList var10, short var13, String opcode) {
		if (var13 < 0) {
			return genCode_319_def(var1, var10, var13, opcode);
		} else if (var13 <= 39) {
			return genCode_0_39(var1, var10, var13, opcode);
		} else if (var13 <= 78) {
			return genCode_40_78(var1, var10, var13, opcode);
		} else if (var13 <= 118) {
			return genCode_79_118(var1, var10, var13, opcode);
		} else if (var13 <= 158) {
			return genCode_119_158(var1, var10, var13, opcode);
		} else if (var13 <= 198) {
			return genCode_159_198(var1, var10, var13, opcode);
		} else if (var13 <= 238) {
			return genCode_199_238(var1, var10, var13, opcode);
		} else if (var13 <= 278) {
			return genCode_239_278(var1, var10, var13, opcode);
		} else if (var13 <= 318) {
			return genCode_279_318(var1, var10, var13, opcode);
		} else {
			return genCode_319_def(var1, var10, var13, opcode);
		}
	}

	private String genCode_0_39(BlockBean var1, ArrayList var10, short var13, String opcode) {
		String var16 = opcode;
		String var7;
		String var8;
		String var9;
		String var11;
		String var17;
		StringBuilder var22;
		StringBuilder var23;
		StringBuilder var24;
		int var27;
		StringBuilder var28;
		int var4;

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
						var16 = String.format("BlackLogicsUtil.getAllKeysFromMap(%s, %s);", var16, var7);
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

		}
		return var16;
	}


	private String genCode_40_78(BlockBean var1, ArrayList var10, short var13, String opcode) {
		String var16 = opcode;
		String var7;
		String var8;
		String var9;
		String var11;
		String var17;
		StringBuilder var22;
		StringBuilder var23;
		StringBuilder var24;
		int var27;
		StringBuilder var28;
		int var4;

		switch(var13) {
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
					
					var16 = String.format("BlackLogicsUtil.getRandom((int)(%s), (int)(%s))", var16, var7);
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
					case 325:
					var16 = (String)var10.get(0);
					var16 = String.format("new StringBuilder(%s).reverse().toString()", var16);
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

		}
		return var16;
	}


	private String genCode_79_118(BlockBean var1, ArrayList var10, short var13, String opcode) {
		String var16 = opcode;
		String var7;
		String var8;
		String var9;
		String var11;
		String var17;
		StringBuilder var22;
		StringBuilder var23;
		StringBuilder var24;
		int var27;
		StringBuilder var28;
		int var4;

		switch(var13) {
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
					
					var16 = String.format("BlackLogicsUtil.getDip(getApplicationContext(), (int)(%s))", var16);
					break;
					case 81:
					var16 = "BlackLogicsUtil.getDisplayWidthPixels(getApplicationContext())";
					break;
					case 82:
					var16 = "BlackLogicsUtil.getDisplayHeightPixels(getApplicationContext())";
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

		}
		return var16;
	}


	private String genCode_119_158(BlockBean var1, ArrayList var10, short var13, String opcode) {
		String var16 = opcode;
		String var7;
		String var8;
		String var9;
		String var11;
		String var17;
		StringBuilder var22;
		StringBuilder var23;
		StringBuilder var24;
		int var27;
		StringBuilder var28;
		int var4;

		switch(var13) {
					case 119:
					var16 = (String)var10.get(0);
					var16 = String.format("BlackLogicsUtil.showMessage(getApplicationContext(), %s);", var16);
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
						var16 = String.format("BlackLogicsUtil.getLocationX(%s)", var16);
					}
					break;
					case 156:
					var16 = (String)var10.get(0);
					if (var16.length() <= 0) {
						var16 = "";
					} else {
						var16 = String.format("BlackLogicsUtil.getLocationY(%s)", var16);
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

		}
		return var16;
	}


	private String genCode_159_198(BlockBean var1, ArrayList var10, short var13, String opcode) {
		String var16 = opcode;
		String var7;
		String var8;
		String var9;
		String var11;
		String var17;
		StringBuilder var22;
		StringBuilder var23;
		StringBuilder var24;
		int var27;
		StringBuilder var28;
		int var4;

		switch(var13) {
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
						var16 = String.format("%s = BlackLogicsUtil.getCheckedItemPositionsToArray(%s);", var7, var16);
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

		}
		return var16;
	}


	private String genCode_199_238(BlockBean var1, ArrayList var10, short var13, String opcode) {
		String var16 = opcode;
		String var7;
		String var8;
		String var9;
		String var11;
		String var17;
		StringBuilder var22;
		StringBuilder var23;
		StringBuilder var24;
		int var27;
		StringBuilder var28;
		int var4;

		switch(var13) {
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

		}
		return var16;
	}


	private String genCode_239_278(BlockBean var1, ArrayList var10, short var13, String opcode) {
		String var16 = opcode;
		String var7;
		String var8;
		String var9;
		String var11;
		String var17;
		StringBuilder var22;
		StringBuilder var23;
		StringBuilder var24;
		int var27;
		StringBuilder var28;
		int var4;

		switch(var13) {
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

		}
		return var16;
	}


	private String genCode_279_318(BlockBean var1, ArrayList var10, short var13, String opcode) {
		String var16 = opcode;
		String var7;
		String var8;
		String var9;
		String var11;
		String var17;
		StringBuilder var22;
		StringBuilder var23;
		StringBuilder var24;
		int var27;
		StringBuilder var28;
		int var4;

		switch(var13) {
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

		}
		return var16;
	}


	private String genCode_319_def(BlockBean var1, ArrayList var10, short var13, String opcode) {
		String var16 = opcode;
		String var7;
		String var8;
		String var9;
		String var11;
		String var17;
		StringBuilder var22;
		StringBuilder var23;
		StringBuilder var24;
		int var27;
		StringBuilder var28;
		int var4;

		switch(var13) {
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
					case 326:
					var16 = (String)var10.get(0);
					if (var16 == null || var16.length() <= 0) {
						var16 = "null";
					}
					var16 = String.format("return %s();", var16);
					break;
					
					case 327:
					var16 = (String)var10.get(0);
					if (var16 == null || var16.length() <= 0) {
						var16 = "\"\"";
					}
					var16 = String.format("return %s;", var16);
					break;
					
					case 328:
					var16 = (String)var10.get(0);
					if (var16 == null || var16.length() <= 0) {
						var16 = "0";
					}
					var16 = String.format("return (int)(%s);", var16);
					break;
					default:
					if (customBlockSpecs.containsKey(var16)) {
						try {
							String formatSpec = customBlockSpecs.get(var16);
							
							ArrayList<String> processedParams = new ArrayList<>();
							for (int i = 0; i < var10.size(); i++) {
								processedParams.add(String.valueOf(var10.get(i)));
							}
							
							if (var1.subStack1 >= 0) {
								String subStackCode = this.a(String.valueOf(var1.subStack1), "");
								processedParams.add(subStackCode);
							} else {
								processedParams.add("");
							}
							
							if (var1.subStack2 >= 0) {
								String subStackCode = this.a(String.valueOf(var1.subStack2), "");
								processedParams.add(subStackCode);
							} else {
								processedParams.add("");
							}
							
							// Support %s and %1$s style templates
							if (formatSpec.contains("%1$") || formatSpec.contains("%2$") || formatSpec.contains("%3$")) {
								var16 = formatWithPositionalParams(formatSpec, processedParams);
							} else {
								var16 = String.format(formatSpec, processedParams.toArray());
							}
							
						} catch (Exception e) {
							var16 = "// Error generating custom block: " + var16;
							Log.e("hb", "Error formatting block: " + var16, e);
						}
					} else {
						var16 = "// Unknown block: " + var16;
					}
					break;
					
					

		}
		return var16;
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
	private void loadCustomBlockSpecs() {
		// Built-in RecyclerView / extra widget codegen (opcode → Java format, %s = params)
		registerBuiltInBlockSpecs();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(BLOCK_PATH));
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			reader.close();
			
			JSONArray blocks = new JSONArray(builder.toString());
			for (int i = 0; i < blocks.length(); i++) {
				JSONObject obj = blocks.getJSONObject(i);
				String code = obj.getString("code").trim();
				// Prefer explicit opcode field if present
				String opcode = obj.optString("opcode", "").trim();
				if (opcode.isEmpty()) {
					opcode = obj.optString("name", "").trim();
				}
				if (opcode.isEmpty()) {
					opcode = code; // legacy: code used as key
				}
				if (!customBlockSpecs.containsKey(opcode)) {
					customBlockSpecs.put(opcode, code);
				}
				// Also map raw code string for older drops
				if (!customBlockSpecs.containsKey(code)) {
					customBlockSpecs.put(code, code);
				}
			}
			
			Log.d("hb", "Loaded " + customBlockSpecs.size() + " custom/built-in blocks");
			
		} catch (Exception e) {
			Log.e("hb", "Failed to load custom blocks", e);
		}
	}
	
	/** BlackLogics built-in blocks — RecyclerView, Grid, Video, Tab, etc. */
	private void registerBuiltInBlockSpecs() {
		// RecyclerView
		putSpec("recyclerSetLayoutManagerLinear",
		"%s.setLayoutManager(new LinearLayoutManager(this));");
		putSpec("recyclerSetLayoutManagerGrid",
		"%s.setLayoutManager(new GridLayoutManager(this, (int)(%s)));");
		putSpec("recyclerSetHasFixedSize",
		"%s.setHasFixedSize(%s);");
		putSpec("faReturnFragment", "return new %1$s();\n");
		putSpec("faReturnTitle", "return %1$s;\n");
		putSpec("faReturnCount", "return %1$s;\n");
		putSpec("listBindGetData", "_data");
		putSpec("listBindGetPosition", "_position");
		putSpec("recyclerSetCustomViewData",
		"%s.setAdapter(%s);");
		putSpec("recyclerNotifyDataSetChanged",
		"if (%1$s.getAdapter() != null) %1$s.getAdapter().notifyDataSetChanged();");
		putSpec("recyclerNotifyItemInserted",
		"if (%1$s.getAdapter() != null) %1$s.getAdapter().notifyItemInserted((int)(%2$s));");
		putSpec("recyclerNotifyItemRemoved",
		"if (%1$s.getAdapter() != null) %1$s.getAdapter().notifyItemRemoved((int)(%2$s));");
		putSpec("recyclerNotifyItemChanged",
		"if (%1$s.getAdapter() != null) %1$s.getAdapter().notifyItemChanged((int)(%2$s));");
		putSpec("recyclerScrollToPosition",
		"%s.scrollToPosition((int)(%s));");
		putSpec("recyclerSmoothScrollToPosition",
		"%s.smoothScrollToPosition((int)(%s));");
		
		// List / Grid extras
		putSpec("listSetSelection",
		"%s.setSelection((int)(%s));");
		putSpec("gridSetNumColumns",
		"%s.setNumColumns((int)(%s));");
		putSpec("expandableExpandGroup",
		"%s.expandGroup((int)(%s));");
		putSpec("expandableCollapseGroup",
		"%s.collapseGroup((int)(%s));");
		
		// More widgets
		putSpec("ratingBarSetRating",
		"%s.setRating((float)(%s));");
		putSpec("ratingBarGetRating",
		"%s.getRating()");
		putSpec("videoViewSetPath",
		"%s.setVideoPath(%s);");
		putSpec("videoViewStart",
		"%s.start();");
		putSpec("videoViewPause",
		"%s.pause();");
		putSpec("videoViewStop",
		"%s.stopPlayback();");
		putSpec("tabLayoutAddTab",
		"%1$s.addTab(%1$s.newTab().setText(%2$s));");
		putSpec("tabLayoutGetSelected",
		"%s.getSelectedTabPosition()");
		putSpec("viewPagerSetCurrentItem",
		"%s.setCurrentItem((int)(%s));");
		putSpec("viewPagerGetCurrentItem",
		"%s.getCurrentItem()");
		putSpec("searchViewSetQuery",
		"%s.setQuery(%s, %s);");
		putSpec("searchViewGetQuery",
		"String.valueOf(%s.getQuery())");
		putSpec("linearLayoutSetOrientation", "%s.setOrientation(%s);");
		putSpec("gridLayoutSetColumnCount", "%s.setColumnCount((int)(%s));");
		putSpec("gridLayoutSetRowCount", "%s.setRowCount((int)(%s));");
		putSpec("nestedScrollScrollTo", "%s.scrollTo((int)(%s), (int)(%s));");
		putSpec("materialCardSetRadius", "%s.setRadius((float)(%s));");
		putSpec("materialCardSetElevation", "%s.setCardElevation((float)(%s));");
		putSpec("materialCardSetBackgroundColor", "%s.setCardBackgroundColor(%s);");
		putSpec("materialSwitchSetChecked", "%s.setChecked(%s);");
		putSpec("materialSwitchGetChecked", "%s.isChecked()");
		putSpec("collapsingToolbarSetTitle", "%s.setTitle(%s);");
		putSpec("collapsingToolbarSetExpanded", "%s.setExpanded(%s);");
		putSpec("collapsingToolbarSetContentScrimColor", "%s.setContentScrimColor(%s);");
		putSpec("motionLayoutTransitionToStart", "%s.transitionToStart();");
		putSpec("motionLayoutTransitionToEnd", "%s.transitionToEnd();");
		putSpec("motionLayoutSetProgress", "%s.setProgress((float)(%s));");
		putSpec("motionLayoutGetProgress", "%s.getProgress()");
		putSpec("spinnerSetSelection", "%s.setSelection((int)(%s));");
		putSpec("spinnerGetSelectedItemPosition", "%s.getSelectedItemPosition()");
		putSpec("spinnerGetSelectedItem", "%s.getSelectedItem().toString()");
		putSpec("videoViewGetDuration", "%s.getDuration()");
		putSpec("videoViewGetCurrentPosition", "%s.getCurrentPosition()");
		putSpec("chipGroupGetCheckedChipId", "%s.getCheckedChipId()");
		putSpec("materialButtonSetIcon", "%s.setIconResource(R.drawable.%s);");
		putSpec("ratingBarSetIsIndicator", "%s.setIsIndicator(%s);");
		putSpec("textInputLayoutSetHelperText", "%s.setHelperText(%s);");
		putSpec("textInputLayoutGetEditText", "%s.getEditText()");
		putSpec("tabLayoutSetupWithViewPager", "%s.setupWithViewPager(%s);");
		// --- NEW SK putSpec (old untouched) ---
		putSpec("setTextSize", "%1$s.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, (float)(%2$s));");
		putSpec("setElevation", "%1$s.setElevation((float)(%2$s));");
		putSpec("getHeight", "%1$s.getHeight()");
		putSpec("getWidth", "%1$s.getWidth()");
		putSpec("checkViewVisibility", "(%1$s.getVisibility() == android.view.View.VISIBLE)");
		putSpec("removeView", "%1$s.removeView(%2$s);");
		putSpec("removeViews", "%1$s.removeAllViews();");
		putSpec("addView", "%1$s.addView(%2$s);");
		putSpec("addViews", "%1$s.addView(%2$s);");
		putSpec("viewGetChildAt", "%1$s.getChildAt((int)(%2$s))");
		putSpec("setGravity", "%1$s.setGravity(%2$s);");
		putSpec("setColorFilterView", "if (%1$s.getBackground() != null) %1$s.getBackground().setColorFilter(%2$s, android.graphics.PorterDuff.Mode.SRC_IN);");
		putSpec("setBgDrawable", "%1$s.setBackground(%2$s);");
		putSpec("setStrokeView", "{ android.graphics.drawable.GradientDrawable _gd = new android.graphics.drawable.GradientDrawable(); _gd.setColor(android.graphics.Color.TRANSPARENT); _gd.setStroke((int)(%2$s), %3$s); %1$s.setBackground(_gd); }");
		putSpec("setCornerRadiusView", "{ android.graphics.drawable.GradientDrawable _gd = new android.graphics.drawable.GradientDrawable(); _gd.setCornerRadius((float)(%2$s)); %1$s.setBackground(_gd); }");
		putSpec("setGradientBackground", "{ android.graphics.drawable.GradientDrawable _gd = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, new int[]{%2$s, %3$s}); %1$s.setBackground(_gd); }");
		putSpec("setRadiusAndStrokeView", "{ android.graphics.drawable.GradientDrawable _gd = new android.graphics.drawable.GradientDrawable(); _gd.setCornerRadius((float)(%2$s)); _gd.setStroke((int)(%3$s), %4$s); %1$s.setBackground(_gd); }");
		putSpec("performClick", "%1$s.performClick();");
		putSpec("viewOnLongClick", "%1$s.setOnLongClickListener(new android.view.View.OnLongClickListener() {\n@Override\npublic boolean onLongClick(android.view.View _view) {\n%2$s\nreturn true;\n}\n});");
		putSpec("viewOnTouch", "%1$s.setOnTouchListener(new android.view.View.OnTouchListener() {\n@Override\npublic boolean onTouch(android.view.View _view, android.view.MotionEvent _event) {\n%2$s\nreturn true;\n}\n});");
		putSpec("showSnackbar", "com.google.android.material.snackbar.Snackbar.make(%1$s, %2$s, (int)(%3$s)).show();");
		putSpec("EditTextdiableSuggestion", "%1$s.setInputType(%1$s.getInputType() | android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);");
		putSpec("EditTextLines", "%1$s.setLines((int)(%2$s));");
		putSpec("EditTextSingleLine", "%1$s.setSingleLine(%2$s);");
		putSpec("EditTextShowError", "%1$s.setError(%2$s);");
		putSpec("EditTextSelectAll", "%1$s.selectAll();");
		putSpec("EditTextSetSelection", "%1$s.setSelection((int)(%2$s), (int)(%3$s));");
		putSpec("EditTextSetMaxLines", "%1$s.setMaxLines((int)(%2$s));");
		putSpec("EdittextGetselectionStart", "%1$s.getSelectionStart()");
		putSpec("EdittextGetselectionEnd", "%1$s.getSelectionEnd()");
		putSpec("autoComSetData", "%1$s.setAdapter(new android.widget.ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, %2$s));");
		putSpec("multiAutoComSetData", "%1$s.setAdapter(new android.widget.ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, %2$s));");
		putSpec("setThreshold", "%1$s.setThreshold((int)(%2$s));");
		putSpec("setTokenizer", "%1$s.setTokenizer(new android.widget.MultiAutoCompleteTextView.CommaTokenizer());");
		putSpec("setImageCustomRes", "%1$s.setImageResource((int)(%2$s));");
		putSpec("getRating", "%1$s.getRating()");
		putSpec("setRating", "%1$s.setRating((float)(%2$s));");
		putSpec("setNumStars", "%1$s.setNumStars((int)(%2$s));");
		putSpec("setStepSize", "%1$s.setStepSize((float)(%2$s));");
		putSpec("videoviewSetVideoUri", "%1$s.setVideoURI(android.net.Uri.parse(%2$s));");
		putSpec("videoviewStart", "%1$s.start();");
		putSpec("videoviewPause", "%1$s.pause();");
		putSpec("videoviewStop", "%1$s.stopPlayback();");
		putSpec("videoviewIsPlaying", "%1$s.isPlaying()");
		putSpec("videoviewCanPause", "%1$s.canPause()");
		putSpec("videoviewCanSeekForward", "%1$s.canSeekForward()");
		putSpec("videoviewCanSeekBackward", "%1$s.canSeekBackward()");
		putSpec("videoviewGetCurrentPosition", "%1$s.getCurrentPosition()");
		putSpec("videoviewGetDuration", "%1$s.getDuration()");
		putSpec("webviewGetProgress", "%1$s.getProgress()");
		putSpec("spnSetCustomViewData", "%1$s.setAdapter(new android.widget.ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, %2$s));");
		putSpec("listSetData", "%1$s.setAdapter(new android.widget.ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, %2$s));");
		putSpec("listSetCustomViewData", "%1$s.setAdapter(new android.widget.ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, %2$s));");
		putSpec("listRefresh", "if (%1$s.getAdapter() != null && %1$s.getAdapter() instanceof android.widget.BaseAdapter) ((android.widget.BaseAdapter)%1$s.getAdapter()).notifyDataSetChanged();");
		putSpec("refreshingList", "if (%1$s.getAdapter() != null && %1$s.getAdapter() instanceof android.widget.BaseAdapter) ((android.widget.BaseAdapter)%1$s.getAdapter()).notifyDataSetChanged();");
		putSpec("listSmoothScrollTo", "%1$s.smoothScrollToPosition((int)(%2$s));");
		putSpec("listViewSetSelection", "%1$s.setSelection((int)(%2$s));");
		putSpec("listSetTranscriptMode", "%1$s.setTranscriptMode((int)(%2$s));");
		putSpec("listSetStackFromBottom", "%1$s.setStackFromBottom(%2$s);");
		putSpec("ListViewAddHeader", "%1$s.addHeaderView(%2$s);");
		putSpec("listViewRemoveHeader", "if (%1$s.getHeaderViewsCount() > 0) %1$s.removeHeaderView(%1$s.getChildAt(0));");
		putSpec("ListViewAddFooter", "%1$s.addFooterView(%2$s);");
		putSpec("listViewRemoveFooter", "if (%1$s.getFooterViewsCount() > 0) %1$s.removeFooterView(%1$s.getChildAt(%1$s.getChildCount()-1));");
		putSpec("listGetCheckedPosition", "%1$s.getCheckedItemPosition()");
		putSpec("listGetCheckedPositions", "%1$s.getCheckedItemPositions()");
		putSpec("listGetCheckedCount", "%1$s.getCheckedItemCount()");
		putSpec("listSetItemChecked", "%1$s.setItemChecked((int)(%2$s), %3$s);");
		putSpec("listAddCustom", "if (%1$s.getAdapter() != null && %1$s.getAdapter() instanceof android.widget.ArrayAdapter) ((android.widget.ArrayAdapter)%1$s.getAdapter()).add(%2$s);");
		putSpec("recyclerSetLayoutManager", "%1$s.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getApplicationContext()));");
		putSpec("recyclerSetLayoutManagerHorizontal", "%1$s.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getApplicationContext(), androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false));");
		putSpec("recyclerScrollToPositionWithOffset", "if (%1$s.getLayoutManager() instanceof androidx.recyclerview.widget.LinearLayoutManager) ((androidx.recyclerview.widget.LinearLayoutManager)%1$s.getLayoutManager()).scrollToPositionWithOffset((int)(%2$s), (int)(%3$s));");
		putSpec("setRecyclerViewLayoutParams", "%1$s.setLayoutParams(new androidx.recyclerview.widget.RecyclerView.LayoutParams((int)(%2$s), (int)(%3$s)));");
		putSpec("gridSetCustomViewData", "%1$s.setAdapter(new android.widget.ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, %2$s));");
		putSpec("gridSetColumnWidth", "%1$s.setColumnWidth((int)(%2$s));");
		putSpec("gridSetVerticalSpacing", "%1$s.setVerticalSpacing((int)(%2$s));");
		putSpec("gridSetHorizontalSpacing", "%1$s.setHorizontalSpacing((int)(%2$s));");
		putSpec("gridSetStretchMode", "%1$s.setStretchMode((int)(%2$s));");
		putSpec("pagerSetCustomViewData", "%1$s.setAdapter(%2$s);");
		putSpec("pagerSetFragmentAdapter", "%1$s.setAdapter(%2$s);");
		putSpec("pagerGetOffscreenPageLimit", "%1$s.getOffscreenPageLimit()");
		putSpec("pagerSetOffscreenPageLimit", "%1$s.setOffscreenPageLimit((int)(%2$s));");
		putSpec("pagerGetCurrentItem", "%1$s.getCurrentItem()");
		putSpec("pagerSetCurrentItem", "%1$s.setCurrentItem((int)(%2$s));");
		putSpec("ViewPagerNotifyOnDtatChange", "if (%1$s.getAdapter() != null) %1$s.getAdapter().notifyDataSetChanged();");
		putSpec("fabIcon", "%1$s.setImageResource((int)(%2$s));");
		putSpec("fabSize", "%1$s.setSize((int)(%2$s));");
		putSpec("fabVisibility", "%1$s.setVisibility(%2$s ? android.view.View.VISIBLE : android.view.View.GONE);");
		putSpec("bottomMenuAddItem", "%1$s.getMenu().add(0, (int)(%2$s), 0, %3$s).setIcon((int)(%4$s));");
		putSpec("onSwipeRefreshLayout", "%1$s.setOnRefreshListener(new androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener() {\n@Override\npublic void onRefresh() {\n%2$s\n}\n});");
		putSpec("setRefreshing", "%1$s.setRefreshing(%2$s);");
		putSpec("setCardBackgroundColor", "%1$s.setCardBackgroundColor(%2$s);");
		putSpec("setCardRadius", "%1$s.setRadius((float)(%2$s));");
		putSpec("setCardElevation", "%1$s.setCardElevation((float)(%2$s));");
		putSpec("setPreventCornerOverlap", "%1$s.setPreventCornerOverlap(%2$s);");
		putSpec("setUseCompatPadding", "%1$s.setUseCompatPadding(%2$s);");
		putSpec("addTab", "%1$s.addTab(%1$s.newTab().setText(%2$s));");
		putSpec("setupWithViewPager", "%1$s.setupWithViewPager(%2$s);");
		putSpec("setInlineLabel", "%1$s.setInlineLabel(%2$s);");
		putSpec("setTabTextColors", "%1$s.setTabTextColors(%2$s, %3$s);");
		putSpec("setTabRippleColor", "%1$s.setTabRippleColor(android.content.res.ColorStateList.valueOf(%2$s));");
		putSpec("setSelectedTabIndicatorColor", "%1$s.setSelectedTabIndicatorColor(%2$s);");
		putSpec("setSelectedTabIndicatorHeight", "%1$s.setSelectedTabIndicatorHeight((int)(%2$s));");
		putSpec("tilSetBoxBgColor", "%1$s.setBoxBackgroundColor(%2$s);");
		putSpec("tilSetBoxStrokeColor", "%1$s.setBoxStrokeColor(%2$s);");
		putSpec("tilSetBoxBgMode", "%1$s.setBoxBackgroundMode((int)(%2$s));");
		putSpec("tilSetBoxCornerRadii", "%1$s.setBoxCornerRadii((float)(%2$s), (float)(%3$s), (float)(%4$s), (float)(%5$s));");
		putSpec("tilSetError", "%1$s.setError(%2$s);");
		putSpec("tilSetErrorEnabled", "%1$s.setErrorEnabled(%2$s);");
		putSpec("tilSetCounterEnabled", "%1$s.setCounterEnabled(%2$s);");
		putSpec("tilSetCounterMaxLength", "%1$s.setCounterMaxLength((int)(%2$s));");
		putSpec("tilGetCounterMaxLength", "%1$s.getCounterMaxLength()");
		putSpec("otpViewSetFieldCount", "%1$s.setFieldCount((int)(%2$s));");
		putSpec("otpViewSetOTPText", "%1$s.setOTP(%2$s);");
		putSpec("otpViewGetOTPText", "%1$s.getOTP()");
		putSpec("otpViewSetOTPListener", "%1$s.setOTPListener(new in.aabhasjindal.otptextview.OTPListener() {\n@Override\npublic void onInteractionListener() {}\n@Override\npublic void onOTPComplete(String otp) {\n%2$s\n}\n});");
		putSpec("setCustomLetter", "%1$s.setCustomLetter(%2$s);");
		putSpec("getBadgeCount", "%1$s.getBadgeNumber()");
		putSpec("setBadgeNumber", "%1$s.setBadgeNumber((int)(%2$s));");
		putSpec("setBadgeString", "%1$s.setBadgeText(%2$s);");
		putSpec("setBadgeBackground", "%1$s.setBadgeBackgroundColor(%2$s);");
		putSpec("setBadgeTextColor", "%1$s.setBadgeTextColor(%2$s);");
		putSpec("setBadgeTextSize", "%1$s.setBadgeTextSize((float)(%2$s));");
		putSpec("setBubbleColor", "%1$s.setBubbleColor(%2$s);");
		putSpec("setBubbleStrokeColor", "%1$s.setBubbleStrokeColor(%2$s);");
		putSpec("setBubbleStrokeWidth", "%1$s.setBubbleStrokeWidth((float)(%2$s));");
		putSpec("setBubbleCornerRadius", "%1$s.setBubbleCornerRadius((float)(%2$s));");
		putSpec("setBubbleArrowHeight", "%1$s.setArrowHeight((float)(%2$s));");
		putSpec("setBubbleArrowWidth", "%1$s.setArrowWidth((float)(%2$s));");
		putSpec("setBubbleArrowPosition", "%1$s.setArrowPosition((int)(%2$s));");
		putSpec("patternToString", "com.andrognito.patternlockview.utils.PatternLockUtils.patternToString(%1$s, %1$s.getPattern())");
		putSpec("patternToMD5", "com.andrognito.patternlockview.utils.PatternLockUtils.patternToMD5(%1$s, %1$s.getPattern())");
		putSpec("patternToSha1", "com.andrognito.patternlockview.utils.PatternLockUtils.patternToSha1(%1$s, %1$s.getPattern())");
		putSpec("patternSetDotCount", "%1$s.setDotCount((int)(%2$s));");
		putSpec("patternSetNormalStateColor", "%1$s.setNormalStateColor(%2$s);");
		putSpec("patternSetCorrectStateColor", "%1$s.setCorrectStateColor(%2$s);");
		putSpec("patternSetWrongStateColor", "%1$s.setWrongStateColor(%2$s);");
		putSpec("patternSetViewMode", "%1$s.setViewMode((int)(%2$s));");
		putSpec("patternLockClear", "%1$s.clearPattern();");
		putSpec("codeviewSetCode", "%1$s.setCode(%2$s);");
		putSpec("codeviewSetLanguage", "%1$s.setLanguage(%2$s);");
		putSpec("codeviewSetTheme", "%1$s.setTheme(%2$s);");
		putSpec("codeviewApply", "%1$s.apply();");
		putSpec("lottieSetAnimationFromAsset", "%1$s.setAnimation(%2$s);");
		putSpec("lottieSetAnimationFromJson", "%1$s.setAnimationFromJson(%2$s, null);");
		putSpec("lottieSetAnimationFromUrl", "%1$s.setAnimationFromUrl(%2$s);");
		putSpec("signInButtonSetColorScheme", "%1$s.setColorScheme((int)(%2$s));");
		putSpec("signInButtonSetSize", "%1$s.setSize((int)(%2$s));");
		putSpec("YTPVLifecycle", "%1$s.addYouTubePlayerListener(new com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener() {});");
		putSpec("YTPVSetListener", "%1$s.addYouTubePlayerListener(new com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener() {\n@Override\npublic void onReady(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {\n%2$s\n}\n});");
		putSpec("bannerAdViewLoadAd", "%1$s.loadAd(new com.google.android.gms.ads.AdRequest.Builder().build());");
		putSpec("interstitialAdLoad", "if (mInterstitialAd != null) { /* already loaded */ } else { com.google.android.gms.ads.interstitial.InterstitialAd.load(getApplicationContext(), /* adUnitId */, new com.google.android.gms.ads.AdRequest.Builder().build(), new com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback() {\n@Override\npublic void onAdLoaded(com.google.android.gms.ads.interstitial.InterstitialAd ad) { mInterstitialAd = ad; }\n}); }");
		putSpec("interstitialAdShow", "if (mInterstitialAd != null) { mInterstitialAd.show(this); }");
		putSpec("interstitialAdIsLoaded", "(mInterstitialAd != null)");
		putSpec("rewardedVideoAdLoad", "/* RewardedAd.load(...) */");
		putSpec("rewardedVideoAdShow", "/* rewardedAd.show(...) */");
		putSpec("timepickerSetHour", "%1$s.setHour((int)(%2$s));");
		putSpec("timepickerSetMinute", "%1$s.setMinute((int)(%2$s));");
		putSpec("timepickerSetCurrentHour", "%1$s.setCurrentHour((int)(%2$s));");
		putSpec("timepickerSetCurrentMinute", "%1$s.setCurrentMinute((int)(%2$s));");
		putSpec("timepickerSetIs24Hour", "%1$s.setIs24HourView(%2$s);");
		putSpec("changeStatebarColour", "if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) { getWindow().setStatusBarColor(%1$s); }");
		putSpec("LightStatusBar", "if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { android.view.View _decor = getWindow().getDecorView(); if (%1$s) { _decor.setSystemUiVisibility(_decor.getSystemUiVisibility() | android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); } else { _decor.setSystemUiVisibility(_decor.getSystemUiVisibility() & ~android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); } }");
		putSpec("showKeyboard", "{ android.view.inputmethod.InputMethodManager _imm = (android.view.inputmethod.InputMethodManager) getSystemService(android.content.Context.INPUT_METHOD_SERVICE); _imm.showSoftInput(%1$s, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT); }");
		putSpec("hideKeyboard", "{ android.view.inputmethod.InputMethodManager _imm = (android.view.inputmethod.InputMethodManager) getSystemService(android.content.Context.INPUT_METHOD_SERVICE); android.view.View _v = getCurrentFocus(); if (_v != null) _imm.hideSoftInputFromWindow(_v.getWindowToken(), 0); }");
		putSpec("intentHasExtra", "%1$s.hasExtra(%2$s)");
		putSpec("finishAffinity", "if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) { finishAffinity(); } else { finish(); }");
		putSpec("intentSetType", "%1$s.setType(%2$s);");
		putSpec("launchApp", "{ android.content.Intent _launch = getPackageManager().getLaunchIntentForPackage(%1$s); if (_launch != null) startActivity(_launch); }");
		putSpec("intentRemoveExtra", "%1$s.removeExtra(%2$s);");
		putSpec("startActivityWithChooser", "startActivity(android.content.Intent.createChooser(%1$s, %2$s));");
		putSpec("sendBroadcast", "sendBroadcast(%1$s);");
		putSpec("startService", "startService(%1$s);");
		putSpec("stopService", "stopService(%1$s);");
		putSpec("fileContainsData", "%1$s.contains(%2$s)");
		putSpec("datePickerDialogShow", "%1$s.show();");
		putSpec("timePickerDialogShow", "%1$s.show();");
		putSpec("AsyncTaskExecute", "%1$s.execute();");
		putSpec("AsyncTaskPublishProgress", "%1$s.publishProgress((int)(%2$s));");
		putSpec("Dialog SetIcon", "%1$s.setIcon((int)(%2$s));");
		putSpec("isConnected", "((android.net.ConnectivityManager) getSystemService(android.content.Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null && ((android.net.ConnectivityManager) getSystemService(android.content.Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().isConnected()");
		putSpec("imageCrop", "/* Use UCrop or similar: UCrop.of(Uri.parse(%1$s), ...).start(this); */");
		putSpec("getAssetFile", "FileUtil.getAssetFile(getApplicationContext(), %1$s)");
		putSpec("copyAssetFile", "FileUtil.copyAssetFile(getApplicationContext(), %1$s, %2$s);");
		putSpec("fileutilcopydir", "FileUtil.copyDir(%1$s, %2$s);");
		putSpec("renameFile", "FileUtil.renameFile(%1$s, %2$s);");
		putSpec("variableAddNew", "/* handled by editor */");
		putSpec("customToast", "BlackLogicsUtil.CustomToast(getApplicationContext(), %1$s, (int)(%2$s), (int)(%3$s), (int)(%4$s), (int)(%5$s), (int)(%6$s));");
		putSpec("customToastWithIcon", "BlackLogicsUtil.CustomToastWithIcon(getApplicationContext(), %1$s, (int)(%2$s), (int)(%3$s), (int)(%4$s), (int)(%5$s), (int)(%6$s), (int)(%7$s));");
		putSpec("CommandBlockJava",
				"/*@BLCMD\n" +
				"type=java\n" +
				"cmd=%5$s\n" +
				"ref=%1$s\n" +
				"dist=%2$s\n" +
				"front=%3$s\n" +
				"back=%4$s\n" +
				"---\n" +
				"%6$s\n" +
				"@BLCMD*/");
		putSpec("CommandBlockXML",
				"/*@BLCMD\n" +
				"type=xml\n" +
				"cmd=%5$s\n" +
				"ref=%1$s\n" +
				"dist=%2$s\n" +
				"front=%3$s\n" +
				"back=%4$s\n" +
				"---\n" +
				"%6$s\n" +
				"@BLCMD*/");
		putSpec("addPermission", "/* Manifest permission: %1$s */");
		putSpec("removePermission", "/* remove permission: %1$s */");
		putSpec("addCustomVariable", "/* custom variable */");
		putSpec("addInitializer", "%1$s");
		putSpec("customImport", "import %1$s;");
		putSpec("customImport2", "import %1$s;");
		putSpec("openResourcesEditor", "/* open resources editor */");
		putSpec("progressdialogCreate", "%1$s = new android.app.ProgressDialog(this);");
		putSpec("progressdialogSetTitle", "%1$s.setTitle(%2$s);");
		putSpec("progressdialogSetMessage", "%1$s.setMessage(%2$s);");
		putSpec("progressdialogSetMax", "%1$s.setMax((int)(%2$s));");
		putSpec("progressdialogSetProgress", "%1$s.setProgress((int)(%2$s));");
		putSpec("progressdialogSetCancelable", "%1$s.setCancelable(%2$s);");
		putSpec("progressdialogSetCanceledOutside", "%1$s.setCanceledOnTouchOutside(%2$s);");
		putSpec("progressdialogSetStyle", "%1$s.setProgressStyle((int)(%2$s));");
		putSpec("progressdialogShow", "if (%1$s != null && !%1$s.isShowing()) %1$s.show();");
		putSpec("progressdialogDismiss", "if (%1$s != null && %1$s.isShowing()) %1$s.dismiss();");
	
	}
	
	private void putSpec(String opcode, String format) {
		if (opcode != null && format != null && !customBlockSpecs.containsKey(opcode)) {
			customBlockSpecs.put(opcode, format);
		}
	}
	
	private String formatWithPositionalParams(String template, ArrayList<String> params) {
		String result = template;
		for (int i = 0; i < params.size(); i++) {
			String placeholder = "%" + (i + 1) + "$s";
			String value = (params.get(i) != null) ? params.get(i) : "";
			result = result.replace(placeholder, value);
		}
		// Backward compatibility for simple %s
		for (String p : params) {
			result = result.replaceFirst("%s", p);
		}
		return result;
	}
	
	public String a() {
		this.g = new HashMap();
		if (this.f != null && this.f.size() > 0) {
			Iterator var1 = this.f.iterator();
			
			while(var1.hasNext()) {
				BlockBean var2 = (BlockBean)var1.next();
				this.g.put(var2.id, var2);
			}
			
			// Generate code from first block
			String generatedCode = this.a((BlockBean)this.f.get(0), "");
			
			// Sirf spacing add karo, blocks mat hatao
			return addProperSpacing(generatedCode);
		} else {
			return "";
		}
	}
	
	// Sirf spacing add karne ka method
	private String addProperSpacing(String code) {
		if (code == null || code.isEmpty()) {
			return "";
		}
		
		String[] lines = code.split("\n");
		StringBuilder result = new StringBuilder();
		int indentLevel = 0;
		boolean inBlock = false;
		
		for (String line : lines) {
			String trimmed = line.trim();
			
			// Skip empty lines initially
			if (trimmed.isEmpty()) {
				continue;
			}
			
			// Handle closing braces first (decrease indent)
			if (trimmed.startsWith("}")) {
				indentLevel = Math.max(0, indentLevel - 1);
			}
			
			// Add proper indentation
			for (int i = 0; i < indentLevel; i++) {
				result.append("    "); // 4 spaces
			}
			result.append(trimmed).append("\n");
			
			// Handle opening braces (increase indent for next lines)
			if (trimmed.endsWith("{")) {
				indentLevel++;
			}
		}
		
		return result.toString();
	}
	
	/*
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
}*/	
}
