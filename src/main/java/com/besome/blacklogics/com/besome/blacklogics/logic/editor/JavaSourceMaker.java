package com.besome.blacklogics.logic.editor;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JavaSourceMaker {
	public static final String ACTIVITY_TEMPLATE_PATH = "source/activity_template";
	private Context context;
	private String filename;
	private String BLOCK_PATH = "/storage/emulated/0/.blacklogics/resources/block/My Block/block.json";
	private Map<String, BlockBean> mapBlocks = null;
	private String[] needBracket = new String[]{"+", "-", "*", "/", "%", ">", "=", "<", "&&", "||"};
	private String[] needBracketOwner = new String[]{"repeat", "+", "-", "*", "/", "%", ">", "=", "<", "&&", "||", "not"};
	private String scId;
	private String source;
	private Map<String, String> customBlockSpecs = new HashMap<>();
	//    private ArrayList<ViewBean> views;
	//private Workspace workspace;
	
	public JavaSourceMaker(Context var1, String var2/*, Workspace var3*/) {
		this.context = var1;
		this.scId = var2;
		//  this.workspace = var3;
		this.readTemplate();
		this.loadCustomBlockSpecs(); 
		//   this.setPackageName(this.workspace.pkgName);
	}
	
	private String getParamSource(String var1, boolean var2, String var3) {
		if(var1.length() > 0 && var1.charAt(0) == 64) {
			var1 = this.getSourceFromBean(0, (String)var1.substring(1), var3);
		} else if(var2) {
			return "\"" + this.replaceSpecialChar(var1) + "\"";
		}
		
		return var1;
	}
	
	private String getSourceFromBean(int var1, BlockBean var2, String var3) {
		String var4 = "";
		
		for(int var5 = 0; var5 < var1; ++var5) {
			var4 = var4 + "\t";
		}
		
		ArrayList var6 = new ArrayList();
		
		for(int var7 = 0; var7 < var2.parameters.size(); ++var7) {
			String var208 = (String)var2.parameters.get(var7);
			boolean var209 = ((String)var2.paramTypes.get(var7)).equals("s");
			boolean var210 = false;
			if(var209) {
				var210 = true;
			}
			
			var6.add(this.getParamSource(var208, var210, var2.opCode));
			Log.d("param", "params : " + var6.toString());
		}
		
		String var8 = var2.opCode;
		byte var9 = -1;
		switch(var8.hashCode()) {
			case -2020761366:
			if(var8.equals("fileRemoveData")) {
				var9 = 69;
			}
			break;
			case 96432: // Hash code for "asd"
			if(var8.equals("asd")) {
				var9 = 92; // Assign a unique byte value
			}
			break;
			case -1998407506:
			if(var8.equals("listSetData")) {
				var9 = 81;
			}
			break;
			case -1920517885:
			if(var8.equals("setVarBoolean")) {
				var9 = 3;
			}
			break;
			case -1919300188:
			if(var8.equals("toNumber")) {
				var9 = 45;
			}
			break;
			case -1776922004:
			if(var8.equals("toString")) {
				var9 = 46;
			}
			break;
			case -1684072208:
			if(var8.equals("intentSetData")) {
				var9 = 60;
			}
			break;
			case -1573371685:
			if(var8.equals("stringJoin")) {
				var9 = 41;
			}
			break;
			case -1530840255:
			if(var8.equals("stringIndex")) {
				var9 = 42;
			}
			break;
			case -1528850031:
			if(var8.equals("startActivity")) {
				var9 = 64;
			}
			break;
			case -1526161572:
			if(var8.equals("setBgColor")) {
				var9 = 55;
			}
			break;
			case -1512519571:
			if(var8.equals("definedFunc")) {
				var9 = 0;
			}
			break;
			case -1440042085:
			if(var8.equals("spnSetSelection")) {
				var9 = 89;
			}
			break;
			case -1384861688:
			if(var8.equals("getAtListInt")) {
				var9 = 10;
			}
			break;
			case -1384851894:
			if(var8.equals("getAtListStr")) {
				var9 = 15;
			}
			break;
			case -1377080719:
			if(var8.equals("decreaseInt")) {
				var9 = 6;
			}
			break;
			case -1271141237:
			if(var8.equals("clearList")) {
				var9 = 20;
			}
			break;
			case -1249367264:
			if(var8.equals("getArg")) {
				var9 = 1;
			}
			break;
			case -1249347599:
			if(var8.equals("getVar")) {
				var9 = 2;
			}
			break;
			case -1192544266:
			if(var8.equals("ifElse")) {
				var9 = 24;
			}
			break;
			case -1137582698:
			if(var8.equals("toLowerCase")) {
				var9 = 50;
			}
			break;
			case -941420147:
			if(var8.equals("fileSetFileName")) {
				var9 = 66;
			}
			break;
			case -938285885:
			if(var8.equals("random")) {
				var9 = 39;
			}
			break;
			case -934531685:
			if(var8.equals("repeat")) {
				var9 = 22;
			}
			break;
			case -918173448:
			if(var8.equals("listGetCheckedPosition")) {
				var9 = 84;
			}
			break;
			case -869293886:
			if(var8.equals("finishActivity")) {
				var9 = 65;
			}
			break;
			case -854558288:
			if(var8.equals("setVisible")) {
				var9 = 75;
			}
			break;
			case -677662361:
			if(var8.equals("forever")) {
				var9 = 21;
			}
			break;
			case -578987803:
			if(var8.equals("setChecked")) {
				var9 = 79;
			}
			break;
			case -509946902:
			if(var8.equals("spnRefresh")) {
				var9 = 88;
			}
			break;
			case -425293664:
			if(var8.equals("setClickable")) {
				var9 = 76;
			}
			break;
			case -411705840:
			if(var8.equals("fileSetData")) {
				var9 = 68;
			}
			break;
			case -399551817:
			if(var8.equals("toUpperCase")) {
				var9 = 49;
			}
			break;
			case -353129373:
			if(var8.equals("calendarDiff")) {
				var9 = 74;
			}
			break;
			case -329562760:
			if(var8.equals("insertListInt")) {
				var9 = 9;
			}
			break;
			case -329552966:
			if(var8.equals("insertListStr")) {
				var9 = 14;
			}
			break;
			case -322651344:
			if(var8.equals("stringEquals")) {
				var9 = 44;
			}
			break;
			case -283328259:
			if(var8.equals("intentPutExtra")) {
				var9 = 62;
			}
			break;
			case -189292433:
			if(var8.equals("stringSub")) {
				var9 = 43;
			}
			break;
			case -133532073:
			if(var8.equals("stringLength")) {
				var9 = 40;
			}
			break;
			case -96313603:
			if(var8.equals("containListInt")) {
				var9 = 12;
			}
			break;
			case -96303809:
			if(var8.equals("containListStr")) {
				var9 = 17;
			}
			break;
			case -75125341:
			if(var8.equals("getText")) {
				var9 = 54;
			}
			break;
			case -60494417:
			if(var8.equals("vibratorAction")) {
				var9 = 91;
			}
			break;
			case 37:
			if(var8.equals("%")) {
				var9 = 33;
			}
			break;
			case 42:
			if(var8.equals("*")) {
				var9 = 31;
			}
			break;
			case 43:
			if(var8.equals("+")) {
				var9 = 29;
			}
			break;
			case 45:
			if(var8.equals("-")) {
				var9 = 30;
			}
			break;
			case 47:
			if(var8.equals("/")) {
				var9 = 32;
			}
			break;
			case 60:
			if(var8.equals("<")) {
				var9 = 35;
			}
			break;
			case 61:
			if(var8.equals("=")) {
				var9 = 36;
			}
			break;
			case 62:
			if(var8.equals(">")) {
				var9 = 34;
			}
			break;
			case 1216:
			if(var8.equals("&&")) {
				var9 = 37;
			}
			break;
			case 3357:
			if(var8.equals("if")) {
				var9 = 23;
			}
			break;
			case 3968:
			if(var8.equals("||")) {
				var9 = 38;
			}
			break;
			case 109267:
			if(var8.equals("not")) {
				var9 = 28;
			}
			break;
			case 3568674:
			if(var8.equals("trim")) {
				var9 = 48;
			}
			break;
			case 3569038:
			if(var8.equals("true")) {
				var9 = 26;
			}
			break;
			case 8255701:
			if(var8.equals("calendarFormat")) {
				var9 = 73;
			}
			break;
			case 27679870:
			if(var8.equals("calendarGetNow")) {
				var9 = 70;
			}
			break;
			case 94001407:
			if(var8.equals("break")) {
				var9 = 25;
			}
			break;
			case 97196323:
			if(var8.equals("false")) {
				var9 = 27;
			}
			break;
			case 182549637:
			if(var8.equals("setEnable")) {
				var9 = 51;
			}
			break;
			case 389111867:
			if(var8.equals("spnSetData")) {
				var9 = 87;
			}
			break;
			case 397166713:
			if(var8.equals("getEnable")) {
				var9 = 52;
			}
			break;
			case 404247683:
			if(var8.equals("calendarAdd")) {
				var9 = 71;
			}
			break;
			case 404265028:
			if(var8.equals("calendarSet")) {
				var9 = 72;
			}
			break;
			case 475815924:
			if(var8.equals("setTextColor")) {
				var9 = 56;
			}
			break;
			case 556217437:
			if(var8.equals("setRotate")) {
				var9 = 77;
			}
			break;
			case 573295520:
			if(var8.equals("listGetCheckedCount")) {
				var9 = 86;
			}
			break;
			case 601235430:
			if(var8.equals("currentTime")) {
				var9 = 47;
			}
			break;
			case 657721930:
			if(var8.equals("setVarInt")) {
				var9 = 4;
			}
			break;
			case 725249532:
			if(var8.equals("intentSetAction")) {
				var9 = 59;
			}
			break;
			case 754442829:
			if(var8.equals("increaseInt")) {
				var9 = 5;
			}
			break;
			case 762282303:
			if(var8.equals("indexListInt")) {
				var9 = 11;
			}
			break;
			case 762292097:
			if(var8.equals("indexListStr")) {
				var9 = 16;
			}
			break;
			case 770834513:
			if(var8.equals("getRotate")) {
				var9 = 78;
			}
			break;
			case 845089750:
			if(var8.equals("setVarString")) {
				var9 = 7;
			}
			break;
			case 1160674468:
			if(var8.equals("lengthList")) {
				var9 = 19;
			}
			break;
			case 1240510514:
			if(var8.equals("intentSetScreen")) {
				var9 = 61;
			}
			break;
			case 1305932583:
			if(var8.equals("spnGetSelection")) {
				var9 = 90;
			}
			break;
			case 1343794064:
			if(var8.equals("listSetItemChecked")) {
				var9 = 83;
			}
			break;
			case 1395026457:
			if(var8.equals("setImage")) {
				var9 = 57;
			}
			break;
			case 1397501021:
			if(var8.equals("listRefresh")) {
				var9 = 82;
			}
			break;
			case 1470831563:
			if(var8.equals("intentGetString")) {
				var9 = 63;
			}
			break;
			case 1601394299:
			if(var8.equals("listGetCheckedPositions")) {
				var9 = 85;
			}
			break;
			case 1764351209:
			if(var8.equals("deleteList")) {
				var9 = 18;
			}
			break;
			case 1779174257:
			if(var8.equals("getChecked")) {
				var9 = 80;
			}
			break;
			case 1814870108:
			if(var8.equals("doToast")) {
				var9 = 58;
			}
			break;
			case 1823151876:
			if(var8.equals("fileGetData")) {
				var9 = 67;
			}
			break;
			case 1984984239:
			if(var8.equals("setText")) {
				var9 = 53;
			}
			break;
			case 2090179216:
			if(var8.equals("addListInt")) {
				var9 = 8;
			}
			break;
			case 2090189010:
			if(var8.equals("addListStr")) {
				var9 = 13;
			}
		}
		
		String var14;
		switch(var9) {
			case 0:
			if(var2.parameters.size() <= 0) {
				int var207 = var2.spec.indexOf(" ");
				if(var207 < 0) {
					var14 = var4 + var2.spec + "();";
				} else {
					var14 = var4 + var2.spec.substring(0, var207) + "();";
				}
			} else {
				int var200 = var2.spec.indexOf(" ");
				String var201 = var2.spec.substring(0, var200);
				String var202 = var4 + var201 + "(";
				boolean var203 = true;
				
				for(int var204 = 0; var204 < var6.size(); var203 = false) {
					if(!var203) {
						var202 = var202 + ", ";
					}
					
					String var205 = (String)var6.get(var204);
					String var206 = (String)var2.paramTypes.get(var204);
					if(var205.length() <= 0) {
						if(var206.equals("b")) {
							var202 = var202 + "true";
						} else if(var206.equals("d")) {
							var202 = var202 + "0";
						}
					} else {
						var202 = var202 + var205;
					}
					
					++var204;
				}
				
				var14 = var202 + ");";
			}
			break;
			case 1:
			var14 = "_" + var2.spec;
			break;
			case 2:
			var14 = var2.spec;
			break;
			case 3:
			String var198 = (String)var6.get(0);
			String var199 = (String)var6.get(1);
			if(var199.length() <= 0) {
				var199 = "false";
			}
			
			if(var198.length() <= 0) {
				var14 = "";
			} else {
				var14 = var4 + String.format("%s = %s;", new Object[]{var198, var199});
			}
			break;
			case 4:
			String var196 = (String)var6.get(0);
			String var197 = (String)var6.get(1);
			if(var197.length() <= 0) {
				var197 = "0";
			}
			
			if(var196.length() <= 0) {
				var14 = "";
			} else {
				var14 = var4 + String.format("%s = %s;", new Object[]{var196, var197});
			}
			break;
			case 5:
			String var195 = (String)var6.get(0);
			if(var195.length() <= 0) {
				var14 = "";
			} else {
				var14 = var4 + String.format("%s++;", new Object[]{var195});
			}
			break;
			case 6:
			String var194 = (String)var6.get(0);
			if(var194.length() <= 0) {
				var14 = "";
			} else {
				var14 = var4 + String.format("%s--;", new Object[]{var194});
			}
			break;
			case 7:
			String var191 = (String)var6.get(0);
			String var192 = (String)var6.get(1);
			if(var192.length() <= 0) {
				var192 = "\"\"";
			}
			
			if(var191.length() <= 0) {
				var14 = "";
			} else {
				var14 = var4 + String.format("%s = %s;", new Object[]{var191, var192});
			}
			
			Log.d("param", "setvar : " + var14);
			break;
			case 8:
			String var189 = (String)var6.get(0);
			String var190 = (String)var6.get(1);
			if(var189.length() > 0 && var190.length() > 0) {
				var14 = var4 + String.format("%s.add(%s);", new Object[]{var190, var189});
			} else {
				var14 = "";
			}
			break;
			case 9:
			String var186 = (String)var6.get(0);
			String var187 = (String)var6.get(1);
			String var188 = (String)var6.get(2);
			if(var186.length() > 0 && var187.length() > 0 && var188.length() > 0) {
				var14 = var4 + String.format("%s.add(%s, %s);", new Object[]{var188, var187, var186});
			} else {
				var14 = "";
			}
			break;
			case 10:
			String var184 = (String)var6.get(0);
			String var185 = (String)var6.get(1);
			if(var184.length() > 0 && var185.length() > 0) {
				var14 = String.format("%s.get(%s)", new Object[]{var185, var184});
			} else {
				var14 = "";
			}
			break;
			case 11:
			String var182 = (String)var6.get(0);
			String var183 = (String)var6.get(1);
			if(var182.length() > 0 && var183.length() > 0) {
				var14 = String.format("%s.indexOf(%s)", new Object[]{var183, var182});
			} else {
				var14 = "";
			}
			break;
			case 12:
			String var180 = (String)var6.get(0);
			String var181 = (String)var6.get(1);
			if(var180.length() > 0 && var181.length() > 0) {
				var14 = String.format("%s.contains(%s)", new Object[]{var180, var181});
			} else {
				var14 = "";
			}
			break;
			case 13:
			String var178 = (String)var6.get(0);
			String var179 = (String)var6.get(1);
			if(var178.length() > 0 && var179.length() > 0) {
				var14 = var4 + String.format("%s.add(%s);", new Object[]{var179, var178});
			} else {
				var14 = "";
			}
			break;
			case 14:
			String var175 = (String)var6.get(0);
			String var176 = (String)var6.get(1);
			String var177 = (String)var6.get(2);
			if(var175.length() > 0 && var176.length() > 0 && var177.length() > 0) {
				var14 = var4 + String.format("%s.add(%s, %s);", new Object[]{var177, var176, var175});
			} else {
				var14 = "";
			}
			break;
			case 15:
			String var173 = (String)var6.get(0);
			String var174 = (String)var6.get(1);
			if(var173.length() > 0 && var174.length() > 0) {
				var14 = String.format("%s.get(%s)", new Object[]{var174, var173});
			} else {
				var14 = "";
			}
			break;
			case 16:
			String var171 = (String)var6.get(0);
			String var172 = (String)var6.get(1);
			if(var171.length() > 0 && var172.length() > 0) {
				var14 = String.format("%s.indexOf(%s)", new Object[]{var172, var171});
			} else {
				var14 = "";
			}
			break;
			case 17:
			String var169 = (String)var6.get(0);
			String var170 = (String)var6.get(1);
			if(var169.length() > 0 && var170.length() > 0) {
				var14 = String.format("%s.contains(%s)", new Object[]{var169, var170});
			} else {
				var14 = "";
			}
			break;
			case 18:
			String var167 = (String)var6.get(0);
			String var168 = (String)var6.get(1);
			if(var167.length() > 0 && var168.length() > 0) {
				var14 = var4 + String.format("%s.remove(%s);", new Object[]{var168, var167});
			} else {
				var14 = "";
			}
			break;
			case 19:
			String var166 = (String)var6.get(0);
			if(var166.length() <= 0) {
				var14 = "";
			} else {
				var14 = String.format("%s.size()", new Object[]{var166});
			}
			break;
			case 20:
			String var165 = (String)var6.get(0);
			if(var165.length() <= 0) {
				var14 = "";
			} else {
				var14 = var4 + String.format("%s.clear();", new Object[]{var165});
			}
			break;
			case 21:
			String var164 = "";
			if(var2.subStack1 >= 0) {
				var164 = this.getSourceFromBean(var1 + 1, String.valueOf(var2.subStack1), "");
			}
			
			var14 = var4 + String.format("while(true) {\r\n%s\r\n%s}", new Object[]{var164, var4});
			break;
			case 22:
			String var161 = (String)var6.get(0);
			String var162 = "";
			if(var2.subStack1 >= 0) {
				var162 = this.getSourceFromBean(var1 + 1, String.valueOf(var2.subStack1), "");
			}
			
			if(var161.length() <= 0) {
				var161 = "0";
			}
			
			String var163 = "_repeat" + var2.id;
			var14 = var4 + String.format("for(int %s=0; %s<%s; %s++) {\r\n%s\r\n%s}", new Object[]{var163, var163, var161, var163, var162, var4});
			break;
			case 23:
			String var159 = (String)var6.get(0);
			String var160 = "";
			if(var2.subStack1 >= 0) {
				var160 = this.getSourceFromBean(var1 + 1, String.valueOf(var2.subStack1), "");
			}
			
			if(var159.length() <= 0) {
				var159 = "true";
			}
			
			var14 = var4 + String.format("if (%s) {\r\n%s\r\n%s}", new Object[]{var159, var160, var4});
			break;
			case 24:
			String var156 = (String)var6.get(0);
			String var157 = "";
			if(var2.subStack1 >= 0) {
				var157 = this.getSourceFromBean(var1 + 1, String.valueOf(var2.subStack1), "");
			}
			
			String var158 = "";
			if(var2.subStack2 >= 0) {
				var158 = this.getSourceFromBean(var1 + 1, String.valueOf(var2.subStack2), "");
			}
			
			if(var156.length() <= 0) {
				var156 = "true";
			}
			
			var14 = var4 + String.format("if (%s) {\r\n%s\r\n%s}\r\n%selse {\r\n%s\r\n%s}", new Object[]{var156, var157, var4, var4, var158, var4});
			break;
			case 25:
			var14 = var4 + "break;";
			break;
			case 26:
			case 27:
			var14 = var2.opCode;
			break;
			case 28:
			String var155 = "";
			if(var2.parameters.size() > 0 && ((String)var2.parameters.get(0)).length() > 0) {
				var155 = this.getParamSource((String)var2.parameters.get(0), false, var2.opCode);
			}
			
			if(var155.length() <= 0) {
				var14 = "";
			} else {
				var14 = "!" + var155;
			}
			break;
			case 29:
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
			String var152 = (String)var6.get(0);
			String var153 = (String)var6.get(1);
			if(var152.length() <= 0) {
				var152 = "0";
			}
			
			if(var153.length() <= 0) {
				var153 = "0";
			}
			
			Object[] var154 = new Object[]{var152, var2.opCode, var153};
			var14 = String.format("%s%s%s", var154);
			break;
			case 36:
			String var150 = (String)var6.get(0);
			String var151 = (String)var6.get(1);
			if(var150.length() <= 0) {
				var150 = "0";
			}
			
			if(var151.length() <= 0) {
				var151 = "0";
			}
			
			var14 = String.format("%s==%s", new Object[]{var150, var151});
			break;
			case 37:
			case 38:
			String var147 = (String)var6.get(0);
			String var148 = (String)var6.get(1);
			if(var147.length() <= 0) {
				var147 = "true";
			}
			
			if(var148.length() <= 0) {
				var148 = "true";
			}
			
			Object[] var149 = new Object[]{var147, var2.opCode, var148};
			var14 = String.format("%s%s%s", var149);
			break;
			case 39:
			String var145 = (String)var6.get(0);
			String var146 = (String)var6.get(1);
			if(var145.length() <= 0) {
				var145 = "0";
			}
			
			if(var146.length() <= 0) {
				var146 = "0";
			}
			
			var14 = String.format("getRandom(%s, %s)", new Object[]{var145, var146});
			break;
			case 40:
			String var144 = (String)var6.get(0);
			if(var144.length() <= 0) {
				var14 = "";
			} else {
				var14 = String.format("%s.length()", new Object[]{var144});
			}
			break;
			case 41:
			String var142 = (String)var6.get(0);
			String var143 = (String)var6.get(1);
			if(var142.length() <= 0) {
				var142 = "\"\"";
			}
			
			if(var143.length() <= 0) {
				var143 = "\"\"";
			}
			
			var14 = String.format("%s.concat(%s)", new Object[]{var142, var143});
			break;
			case 42:
			String var140 = (String)var6.get(0);
			String var141 = (String)var6.get(1);
			if(var140.length() <= 0) {
				var140 = "\"\"";
			}
			
			if(var141.length() <= 0) {
				var141 = "\"\"";
			}
			
			var14 = String.format("%s.indexOf(%s)", new Object[]{var141, var140});
			break;
			case 43:
			String var137 = (String)var6.get(0);
			String var138 = (String)var6.get(1);
			String var139 = (String)var6.get(2);
			if(var138.length() <= 0) {
				var138 = "0";
			}
			
			if(var139.length() <= 0) {
				var139 = "0";
			}
			
			var14 = String.format("%s.substring(%s, %s)", new Object[]{var137, var138, var139});
			break;
			case 44:
			String var135 = (String)var6.get(0);
			String var136 = (String)var6.get(1);
			if(var135.length() <= 0) {
				var135 = "\"\"";
			}
			
			if(var136.length() <= 0) {
				var136 = "\"\"";
			}
			
			var14 = String.format("%s.equals(%s)", new Object[]{var135, var136});
			break;
			case 45:
			String var134 = (String)var6.get(0);
			if(var134.length() <= 0) {
				var134 = "\"0\"";
			}
			
			var14 = String.format("Integer.valueOf(%s)", new Object[]{var134});
			break;
			case 46:
			String var133 = (String)var6.get(0);
			if(var133.length() <= 0) {
				var133 = "0";
			}
			
			var14 = String.format("String.valueOf(%s)", new Object[]{var133});
			break;
			case 47:
			var14 = "(int)System.currentTimeMillis()";
			break;
			case 48:
			String var132 = (String)var6.get(0);
			if(var132.length() <= 0) {
				var132 = "\"\"";
			}
			
			var14 = String.format("%s.trim()", new Object[]{var132});
			break;
			case 49:
			String var131 = (String)var6.get(0);
			if(var131.length() <= 0) {
				var131 = "\"\"";
			}
			
			var14 = String.format("%s.toUpperCase()", new Object[]{var131});
			break;
			case 50:
			String var130 = (String)var6.get(0);
			if(var130.length() <= 0) {
				var130 = "\"\"";
			}
			
			var14 = String.format("%s.toLowerCase()", new Object[]{var130});
			break;
			case 51:
			String var126 = (String)var6.get(0);
			String var127 = (String)var6.get(1);
			if(var127.length() <= 0) {
				var127 = "true";
			}
			
			if(var126.length() <= 0) {
				var14 = "";
			} else {
				StringBuilder var128 = (new StringBuilder()).append(var4);
				Object[] var129 = new Object[]{SourceUtil.getVariableNameFromId(var126), var127};
				var14 = var128.append(String.format("%s.setEnabled(%s);", var129)).toString();
			}
			break;
			case 52:
			String var124 = (String)var6.get(0);
			if(var124.length() <= 0) {
				var14 = "";
			} else {
				Object[] var125 = new Object[]{SourceUtil.getVariableNameFromId(var124)};
				var14 = String.format("%s.isEnabled()", var125);
			}
			break;
			case 53:
			String var120 = (String)var6.get(0);
			String var121 = (String)var6.get(1);
			if(var121.length() <= 0) {
				var121 = "\"\"";
			}
			
			if(var120.length() <= 0) {
				var14 = "";
			} else {
				StringBuilder var122 = (new StringBuilder()).append(var4);
				Object[] var123 = new Object[]{SourceUtil.getVariableNameFromId(var120), var121};
				var14 = var122.append(String.format("%s.setText(%s);", var123)).toString();
			}
			break;
			case 54:
			String var118 = (String)var6.get(0);
			if(var118.length() <= 0) {
				var14 = "";
			} else {
				Object[] var119 = new Object[]{SourceUtil.getVariableNameFromId(var118)};
				var14 = String.format("%s.getText().toString()", var119);
			}
			break;
			case 55:
			String var114 = (String)var6.get(0);
			String var115 = (String)var6.get(1);
			if(var115.length() <= 0) {
				var115 = "0xFF000000";
			}
			
			if(var114.length() > 0 && var115.length() > 0) {
				StringBuilder var116 = (new StringBuilder()).append(var4);
				Object[] var117 = new Object[]{SourceUtil.getVariableNameFromId(var114), var115};
				var14 = var116.append(String.format("%s.setBackgroundColor(%s);", var117)).toString();
			} else {
				var14 = "";
			}
			break;
			case 56:
			String var110 = (String)var6.get(0);
			String var111 = (String)var6.get(1);
			if(var111.length() <= 0) {
				var111 = "0xFF000000";
			}
			
			if(var110.length() <= 0) {
				var14 = "";
			} else {
				StringBuilder var112 = (new StringBuilder()).append(var4);
				Object[] var113 = new Object[]{SourceUtil.getVariableNameFromId(var110), var111};
				var14 = var112.append(String.format("%s.setTextColor(%s);", var113)).toString();
			}
			break;
			case 57:
			String var106 = (String)var6.get(0);
			String var107 = (String)var6.get(1);
			if(var106.length() > 0 && var107.length() > 0) {
				StringBuilder var108 = (new StringBuilder()).append(var4);
				Object[] var109 = new Object[]{SourceUtil.getVariableNameFromId(var106), var107};
				var14 = var108.append(String.format("%s.setImageResource(pdrawable.%s);", var109)).toString();
			} else {
				var14 = "";
			}
			break;
			case 58:
			String var105 = (String)var6.get(0);
			if(var105.length() <= 0) {
				var105 = "\"\"";
			}
			
			var14 = var4 + String.format("showMessage(%s);", new Object[]{var105});
			break;
			case 59:
			String var102 = (String)var6.get(0);
			String var103 = (String)var6.get(1);
			String var104;
			if(var103.length() <= 0) {
				var104 = "\"\"";
			} else {
				var104 = "Intent." + var103;
			}
			
			if(var102.length() <= 0) {
				var14 = "";
			} else {
				var14 = var4 + String.format("%s.setAction(%s);", new Object[]{var102, var104});
			}
			break;
			case 60:
			String var100 = (String)var6.get(0);
			String var101 = (String)var6.get(1);
			if(var101.length() <= 0) {
				var101 = "\"\"";
			}
			
			if(var100.length() <= 0) {
				var14 = "";
			} else {
				var14 = var4 + String.format("%s.setData(Uri.parse(%s));", new Object[]{var100, var101});
			}
			break;
			case 61:
			String var98 = (String)var6.get(0);
			String var99 = (String)var6.get(1);
			if(var99.length() > 0 && var98.length() > 0) {
				var14 = var4 + String.format("%s.setClass(getApplicationContext(), %s.class);", new Object[]{var98, var99});
			} else {
				var14 = "";
			}
			break;
			case 62:
			String var95 = (String)var6.get(0);
			String var96 = (String)var6.get(1);
			String var97 = (String)var6.get(2);
			if(var97.length() <= 0) {
				var97 = "\"\"";
			}
			
			if(var96.length() <= 0) {
				var96 = "\"\"";
			}
			
			if(var95.length() <= 0) {
				var14 = "";
			} else {
				var14 = var4 + String.format("%s.putExtra(%s, %s);", new Object[]{var95, var96, var97});
			}
			break;
			case 63:
			String var94 = (String)var6.get(0);
			if(var94.length() <= 0) {
				var94 = "\"\"";
			}
			
			var14 = String.format("getIntent().getStringExtra(%s)", new Object[]{var94});
			break;
			case 64:
			String var93 = (String)var6.get(0);
			if(var93.length() <= 0) {
				var14 = "";
			} else {
				var14 = var4 + String.format("startActivity(%s);", new Object[]{var93});
			}
			break;
			case 65:
			var14 = var4 + "finish();";
			break;
			case 66:
			String var91 = (String)var6.get(0);
			String var92 = (String)var6.get(1);
			if(var91.length() > 0 && var92.length() > 0) {
				var14 = var4 + String.format("%s = getApplicationContext().getSharedPreferences(%s, Activity.MODE_PRIVATE);", new Object[]{var91, var92});
			} else {
				var14 = "";
			}
			break;
			case 67:
			String var89 = (String)var6.get(0);
			String var90 = (String)var6.get(1);
			if(var90.length() <= 0) {
				var90 = "\"\"";
			}
			
			if(var89.length() <= 0) {
				var14 = "";
			} else {
				var14 = String.format("%s.getString(%s, \"\")", new Object[]{var89, var90});
			}
			break;
			case 68:
			String var86 = (String)var6.get(0);
			String var87 = (String)var6.get(1);
			String var88 = (String)var6.get(2);
			if(var88.length() <= 0) {
				var88 = "\"\"";
			}
			
			if(var87.length() <= 0) {
				var87 = "\"\"";
			}
			
			if(var86.length() <= 0) {
				var14 = "";
			} else {
				var14 = var4 + String.format("%s.edit().putString(%s, %s).commit();", new Object[]{var86, var87, var88});
			}
			break;
			case 69:
			String var84 = (String)var6.get(0);
			String var85 = (String)var6.get(1);
			if(var85.length() <= 0) {
				var85 = "\"\"";
			}
			
			if(var84.length() <= 0) {
				var14 = "";
			} else {
				var14 = var4 + String.format("%s.edit().remove(%s).commit();", new Object[]{var84, var85});
			}
			break;
			case 70:
			String var83 = (String)var6.get(0);
			if(var83.length() <= 0) {
				var14 = "";
			} else {
				var14 = var4 + String.format("%s = Calendar.getInstance();", new Object[]{var83});
			}
			break;
			case 71:
			String var80 = (String)var6.get(0);
			String var81 = (String)var6.get(1);
			String var82 = (String)var6.get(2);
			if(var82.length() <= 0) {
				var82 = "0";
			}
			
			if(var80.length() > 0 && var81.length() > 0) {
				var14 = var4 + String.format("%s.add(Calendar.%s, %s);", new Object[]{var80, var81, var82});
			} else {
				var14 = "";
			}
			break;
			case 72:
			String var77 = (String)var6.get(0);
			String var78 = (String)var6.get(1);
			String var79 = (String)var6.get(2);
			if(var79.length() <= 0) {
				var79 = "0";
			}
			
			if(var77.length() > 0 && var78.length() > 0) {
				var14 = var4 + String.format("%s.set(Calendar.%s, %s);", new Object[]{var77, var78, var79});
			} else {
				var14 = "";
			}
			break;
			case 73:
			String var75 = (String)var6.get(0);
			String var76 = (String)var6.get(1);
			if(var76.length() <= 0) {
				var76 = "yyyy/MM/dd hh:mm:ss";
			}
			
			if(var75.length() <= 0) {
				var14 = "";
			} else {
				var14 = String.format("new SimpleDateFormat(%s).format(%s.getTime())", new Object[]{var76, var75});
			}
			break;
			case 74:
			String var73 = (String)var6.get(0);
			String var74 = (String)var6.get(1);
			if(var73.length() > 0 && var74.length() > 0) {
				var14 = var4 + String.format("(int)(%s.getTimeInMillis() - %s.getTimeInMillis())", new Object[]{var73, var74});
			} else {
				var14 = "";
			}
			break;
			case 75:
			String var69 = (String)var6.get(0);
			String var70 = (String)var6.get(1);
			if(var70.length() <= 0) {
				var70 = "VISIBLE";
			}
			
			if(var69.length() <= 0) {
				var14 = "";
			} else {
				StringBuilder var71 = (new StringBuilder()).append(var4);
				Object[] var72 = new Object[]{SourceUtil.getVariableNameFromId(var69), var70};
				var14 = var71.append(String.format("%s.setVisibility(View.%s);", var72)).toString();
			}
			break;
			case 76:
			String var65 = (String)var6.get(0);
			String var66 = (String)var6.get(1);
			if(var66.length() <= 0) {
				var66 = "true";
			}
			
			if(var65.length() <= 0) {
				var14 = "";
			} else {
				StringBuilder var67 = (new StringBuilder()).append(var4);
				Object[] var68 = new Object[]{SourceUtil.getVariableNameFromId(var65), var66};
				var14 = var67.append(String.format("%s.setClickable(%s);", var68)).toString();
			}
			break;
			case 77:
			String var61 = (String)var6.get(0);
			String var62 = (String)var6.get(1);
			if(var62.length() <= 0) {
				var62 = "0";
			}
			
			if(var61.length() <= 0) {
				var14 = "";
			} else {
				StringBuilder var63 = (new StringBuilder()).append(var4);
				Object[] var64 = new Object[]{SourceUtil.getVariableNameFromId(var61), var62};
				var14 = var63.append(String.format("%s.setRotation(%s);", var64)).toString();
			}
			break;
			case 78:
			String var59 = (String)var6.get(0);
			if(var59.length() <= 0) {
				var14 = "";
			} else {
				Object[] var60 = new Object[]{SourceUtil.getVariableNameFromId(var59)};
				var14 = String.format("(int)%s.getRotation()", var60);
			}
			break;
			case 79:
			String var55 = (String)var6.get(0);
			String var56 = (String)var6.get(1);
			if(var56.length() <= 0) {
				var56 = "false";
			}
			
			if(var55.length() <= 0) {
				var14 = "";
			} else {
				StringBuilder var57 = (new StringBuilder()).append(var4);
				Object[] var58 = new Object[]{SourceUtil.getVariableNameFromId(var55), var56};
				var14 = var57.append(String.format("%s.setChecked(%s);", var58)).toString();
			}
			break;
			case 80:
			String var53 = (String)var6.get(0);
			if(var53.length() <= 0) {
				var14 = "";
			} else {
				Object[] var54 = new Object[]{SourceUtil.getVariableNameFromId(var53)};
				var14 = String.format("%s.isChecked()", var54);
			}
			break;
			case 81:
			String var44 = (String)var6.get(0);
			String var45 = (String)var6.get(1);
			if(var44.length() > 0 && var45.length() > 0) {
				int var46 = 0;
				
				/*ViewBean var48;
				while(true) {
				int var47 = this.views.size();
				var48 = null;
				if(var46 >= var47) {
				break;
				}
				
				ViewBean var52 = (ViewBean)this.views.get(var46);
				if(var44.equals(var2.id)) {
				var48 = var52;
				break;
				}
				
				++var46;
				}*/
				
				
				
				String var49 = "android.R.layout.simple_list_item_1";
				/* if(var48 != null) {
				switch(var48.choiceMode) {
				case 0:
				var49 = "android.R.layout.simple_list_item_1";
				break;
				case 1:
				var49 = "android.R.layout.simple_list_item_single_choice";
				break;
				case 2:
				var49 = "android.R.layout.simple_list_item_multiple_choice";
				}
				}*/
				
				StringBuilder var50 = (new StringBuilder()).append(var4);
				Object[] var51 = new Object[]{SourceUtil.getVariableNameFromId(var44), var49, var45};
				var14 = var50.append(String.format("%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), %s, %s));", var51)).toString();
			} else {
				var14 = "";
			}
			break;
			case 82:
			String var41 = (String)var6.get(0);
			if(var41.length() <= 0) {
				var14 = "";
			} else {
				StringBuilder var42 = (new StringBuilder()).append(var4);
				Object[] var43 = new Object[]{SourceUtil.getVariableNameFromId(var41)};
				var14 = var42.append(String.format("((ArrayAdapter)%s.getAdapter()).notifyDataSetChanged();", var43)).toString();
			}
			break;
			case 83:
			String var36 = (String)var6.get(0);
			String var37 = (String)var6.get(1);
			String var38 = (String)var6.get(2);
			if(var37.length() <= 0) {
				var37 = "0";
			}
			
			if(var38.length() <= 0) {
				var38 = "false";
			}
			
			if(var36.length() <= 0) {
				var14 = "";
			} else {
				StringBuilder var39 = (new StringBuilder()).append(var4);
				Object[] var40 = new Object[]{SourceUtil.getVariableNameFromId(var36), var37, var38};
				var14 = var39.append(String.format("%s.setItemChecked(%s, %s);", var40)).toString();
			}
			break;
			case 84:
			String var34 = (String)var6.get(0);
			if(var34.length() <= 0) {
				var14 = "";
			} else {
				Object[] var35 = new Object[]{SourceUtil.getVariableNameFromId(var34)};
				var14 = String.format("%s.getCheckedItemPosition()", var35);
			}
			break;
			case 85:
			String var30 = (String)var6.get(0);
			String var31 = (String)var6.get(1);
			if(var30.length() > 0 && var31.length() > 0) {
				StringBuilder var32 = (new StringBuilder()).append(var4);
				Object[] var33 = new Object[]{var31, SourceUtil.getVariableNameFromId(var30)};
				var14 = var32.append(String.format("%s = getCheckedItemPositionsToArray(%s);", var33)).toString();
			} else {
				var14 = "";
			}
			break;
			case 86:
			String var28 = (String)var6.get(0);
			if(var28.length() <= 0) {
				var14 = "";
			} else {
				Object[] var29 = new Object[]{SourceUtil.getVariableNameFromId(var28)};
				var14 = String.format("%s.getCheckedItemCount()", var29);
			}
			break;
			case 87:
			String var24 = (String)var6.get(0);
			String var25 = (String)var6.get(1);
			if(var24.length() > 0 && var25.length() > 0) {
				StringBuilder var26 = (new StringBuilder()).append(var4);
				Object[] var27 = new Object[]{SourceUtil.getVariableNameFromId(var24), var25};
				var14 = var26.append(String.format("%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, %s));", var27)).toString();
			} else {
				var14 = "";
			}
			break;
			case 88:
			String var21 = (String)var6.get(0);
			if(var21.length() <= 0) {
				var14 = "";
			} else {
				StringBuilder var22 = (new StringBuilder()).append(var4);
				Object[] var23 = new Object[]{SourceUtil.getVariableNameFromId(var21)};
				var14 = var22.append(String.format("((ArrayAdapter)%s.getAdapter()).notifyDataSetChanged();", var23)).toString();
			}
			break;
			case 89:
			String var17 = (String)var6.get(0);
			String var18 = (String)var6.get(1);
			if(var18.length() <= 0) {
				var18 = "0";
			}
			
			if(var17.length() <= 0) {
				var14 = "";
			} else {
				StringBuilder var19 = (new StringBuilder()).append(var4);
				Object[] var20 = new Object[]{SourceUtil.getVariableNameFromId(var17), var18};
				var14 = var19.append(String.format("%s.setSelection(%s);", var20)).toString();
			}
			break;
			case 90:
			String var15 = (String)var6.get(0);
			if(var15.length() <= 0) {
				var14 = "";
			} else {
				Object[] var16 = new Object[]{SourceUtil.getVariableNameFromId(var15)};
				var14 = String.format("%s.getSelectedItemPosition()", var16);
			}
			break;
			case 91:
			String var10 = (String)var6.get(0);
			String var11 = (String)var6.get(1);
			if(var11.length() <= 0) {
				var11 = "0";
			}
			
			if(var10.length() <= 0) {
				var14 = "";
			} else {
				StringBuilder var12 = (new StringBuilder()).append(var4);
				Object[] var13 = new Object[]{SourceUtil.getVariableNameFromId(var10), var11};
				var14 = var12.append(String.format("%s.vibrate(%s);", var13)).toString();
			}
			break;
			case 92:
			String var1007 = (String)var6.get(0);
			String var1368 = (String)var6.get(1);
			if(var1368.length() <= 0) {
				var1368 = "0";
			}
			
			if(var1007.length() <= 0) {
				var14 = "";
			} else {
				StringBuilder var19 = (new StringBuilder()).append(var4);
				Object[] var20 = new Object[]{SourceUtil.getVariableNameFromId(var1007), var1368};
				var14 = var19.append(String.format("%s", var20)).toString();
			}
			break;
			default:
			if (customBlockSpecs.containsKey(var8)) {
				try {
					String formatSpec = customBlockSpecs.get(var8);
					// Prepare parameters, replacing substack placeholders with generated code
					ArrayList<String> processedParams = new ArrayList<>();
					for (int i = 0; i < var6.size(); i++) {
						processedParams.add(String.valueOf(var6.get(i)));
					}
					// Handle subStack1
					if (var2.subStack1 >= 0) {
						String subStackCode = this.getSourceFromBean(var1 + 1, String.valueOf(var2.subStack1), "");
						processedParams.add(subStackCode); // Add substack code as a parameter
					} else {
						processedParams.add(""); // Empty string if no substack
					}
					// Handle subStack2 (for if-else or try-catch blocks)
					if (var2.subStack2 >= 0) {
						String subStackCode = this.getSourceFromBean(var1 + 1, String.valueOf(var2.subStack2), "");
						processedParams.add(subStackCode);
					} else {
						processedParams.add("");
					}
					var14 = var4 + String.format(formatSpec, processedParams.toArray());
				} catch (Exception e) {
					var14 = var4 + "// Error generating custom block: " + var8;
					Log.e("JavaSourceMaker", "Error formatting block: " + var8, e);
				}
			} else {
				var14 = var4 + "// Unknown block: " + var8;
			}
			
		}
		
		if(this.isNeedBracket(var2.opCode, var3)) {
			var14 = "(" + var14 + ")";
		}
		
		if(var2.nextBlock >= 0) {
			var14 = var14 + "\r\n" + this.getSourceFromBean(var1, String.valueOf(var2.nextBlock), "");
		}
		
		return var14;
	}
	
	private String getSourceFromBean(int var1, String var2, String var3) {
		return !this.mapBlocks.containsKey(var2)?"":this.getSourceFromBean(var1, (BlockBean)this.mapBlocks.get(var2), var3);
	}
	
	private boolean isNeedBracket(String var1, String var2) {
		String[] var3 = this.needBracketOwner;
		int var4 = var3.length;
		int var5 = 0;
		
		boolean var6;
		while(true) {
			var6 = false;
			if(var5 >= var4) {
				break;
			}
			
			if(var3[var5].equals(var2)) {
				var6 = true;
				break;
			}
			
			++var5;
		}
		
		String[] var7 = this.needBracket;
		int var8 = var7.length;
		int var9 = 0;
		
		boolean var10;
		while(true) {
			var10 = false;
			if(var9 >= var8) {
				break;
			}
			
			if(var7[var9].equals(var1)) {
				var10 = true;
				break;
			}
			
			++var9;
		}
		
		boolean var11 = false;
		if(var6) {
			var11 = false;
			if(var10) {
				var11 = true;
			}
		}
		
		return var11;
	}
	
	private String replaceSpecialChar(String var1) {
		String var2 = "";
		CharBuffer var3 = CharBuffer.wrap(var1);
		
		for(int var4 = 0; var4 < var3.length(); ++var4) {
			char var5 = var3.get(var4);
			if(var5 == 34) {
				var2 = var2 + "\\\\\"";
			} else if(var5 == 92) {
				if(var4 < -1 + var3.length()) {
					char var6 = var3.get(var4 + 1);
					if(var6 != 110 && var6 != 116) {
						var2 = var2 + "\\\\\\\\";
					} else {
						var2 = var2 + "\\\\" + var6;
						++var4;
					}
				} else {
					var2 = var2 + "\\\\\\\\";
				}
			} else {
				var2 = var2 + var5;
			}
		}
		
		return var2;
	}
	
	private void setPackageName(String var1) {
		this.source = this.source.replaceAll("<\\?package\\?>", "package " + var1 + ";");
	}
	
	private void setResourceName(String var1) {
		this.source = this.source.replaceAll("<\\?resource_name\\?>", "R.layout." + var1.toLowerCase());
	}
	
	
	
	
	public String getSource(int i, ArrayList<BlockBean> arrayList) {
		this.mapBlocks = new HashMap<>();
		if (arrayList == null || arrayList.size() <= 0) {
			return "";
		}
		Iterator it = arrayList.iterator();
		while (it.hasNext()) {
			BlockBean blockBean = (BlockBean) it.next();
			this.mapBlocks.put(blockBean.id, blockBean);
		}
		return "" + getSourceFromBean(i, arrayList.get(0), "");
	}
	
	
	
	
	public void readTemplate() {
		this.source = ""; // (new FileUtil()).readFileFromAsset(this.context, "source/activity_template");
	}
	
	/* public void saveSource(String var1) {
	try {
	this.workspace.saveFile(var1, this.source);
	} catch (Exception var3) {
	var3.printStackTrace();
	}
	}*/
	
	public void setActivityName(String var1) {
		this.filename = var1;
		String var2 = var1.substring(0, var1.indexOf(".java"));
		this.source = this.source.replaceAll("<\\?activity_name\\?>", var2);
		this.setResourceName(var1.substring(0, var1.indexOf("Activity.java")));
	}
	
	public void setDefinedFunctions(String var1) {
		String var2 = "" + "\t// created automatically\r\n";
		String var3 = var2 + SourceUtil.getShowMessageSrc() + "\r\n";
		String var4 = var3 + SourceUtil.getRandomSrc();
		this.source = this.source.replaceAll("<\\?defined_function\\?>", var4);
	}
	
	public void setFunctions(ArrayList var1, Map var2) {
		String var3 = "";
		
		Pair var9;
		for(Iterator var4 = var1.iterator(); var4.hasNext(); var3 = var3 + SourceUtil.getFunctionName((String)var9.first, (String)var9.second) + "\r\n") {
			var9 = (Pair)var4.next();
		}
		
		this.source = this.source.replaceAll("<\\?function\\?>", var3);
		
		String var7;
		String var8;
		for(Iterator var5 = var1.iterator(); var5.hasNext(); this.source = this.source.replaceAll("<\\?" + var7 + "\\?>", var8)) {
			Pair var6 = (Pair)var5.next();
			var7 = (String)var6.first + "_" + "moreBlock";
			var8 = "";
			if(var2.containsKey(var7)) {
				var8 = this.getSource(2, (ArrayList)var2.get(var7));
			}
		}
		
	}
	
	/*public void setInitComponents(ArrayList var1) {
	String var2 = "";
	
	ComponentBean var4;
	for(Iterator var3 = var1.iterator(); var3.hasNext(); var2 = var2 + SourceUtil.makeInitComponentSrc(var4) + "\r\n") {
	var4 = (ComponentBean)var3.next();
	var4.print();
	}
	
	this.source = this.source.replaceAll("<\\?init_component\\?>", var2);
	}*/
	
	/*public void setInitEvents(ArrayList var1, Map var2) {
	String var3 = "";
	
	EventBean var9;
	for(Iterator var4 = var1.iterator(); var4.hasNext(); var3 = var3 + SourceUtil.makeEventSrc(var9) + "\r\n") {
	var9 = (EventBean)var4.next();
	}
	
	this.source = this.source.replaceAll("<\\?init_events\\?>", var3);
	
	String var7;
	String var8;
	for(Iterator var5 = var1.iterator(); var5.hasNext(); this.source = this.source.replaceAll("<\\?" + var7 + "\\?>", var8)) {
	EventBean var6 = (EventBean)var5.next();
	var7 = var6.targetId + "_" + var6.eventName;
	var8 = "";
	if(var2.containsKey(var7)) {
	var8 = this.getSource(4, (ArrayList)var2.get(var7));
	}
	
	if(var6.eventName.equals("onTextChanged")) {
	var8 = var8.replaceAll("_text", "_text.toString()");
	}
	}
	
	}*/
	
	public void setInitLogic(ArrayList var1) {
		String var2 = this.getSource(2, var1);
		this.source = this.source.replaceAll("<\\?init_logic\\?>", var2);
	}
	
	/* public void setInitViews(ArrayList var1) {
	this.views = var1;
	String var2 = "";
	
	ViewBean var4;
	for(Iterator var3 = var1.iterator(); var3.hasNext(); var2 = var2 + SourceUtil.makeInitSrc(var4) + "\r\n") {
	var4 = (ViewBean)var3.next();
	}
	
	this.source = this.source.replaceAll("<\\?init_view\\?>", var2);
	}*/
	
	public void setListsForLogics(ArrayList var1) {
		String var2 = "";
		
		Pair var4;
		for(Iterator var3 = var1.iterator(); var3.hasNext(); var2 = var2 + SourceUtil.getListName(((Integer)var4.first).intValue(), (String)var4.second) + "\r\n") {
			var4 = (Pair)var3.next();
		}
		
		this.source = this.source.replaceAll("<\\?list_logic\\?>", var2);
	}
	
	/*  public void setVariableForComponents(ArrayList var1) {
	String var2 = "";
	
	ComponentBean var4;
	for(Iterator var3 = var1.iterator(); var3.hasNext(); var2 = var2 + SourceUtil.makeVariableSrc(var4) + "\r\n") {
	var4 = (ComponentBean)var3.next();
	}
	
	this.source = this.source.replaceAll("<\\?variable_component\\?>", var2);
	}*/
	
	public void setVariablesForLogics(ArrayList var1) {
		String var2 = "";
		
		Pair var4;
		for(Iterator var3 = var1.iterator(); var3.hasNext(); var2 = var2 + SourceUtil.getVariableName(((Integer)var4.first).intValue(), (String)var4.second) + "\r\n") {
			var4 = (Pair)var3.next();
		}
		
		this.source = this.source.replaceAll("<\\?variable_logic\\?>", var2);
	}
	
	
	
	private void loadCustomBlockSpecs() {
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
				String spec = obj.getString("spec");
				/*if (!customBlockSpecs.containsKey(code)) {
				customBlockSpecs.put(code, spec);
				}*/
				if  (!customBlockSpecs.containsKey(code)) {
					customBlockSpecs.put(code, code);
				}
			}
		} catch (Exception e) {
			Log.e("CustomBlockLoad", "Failed to load custom blocks", e);
		}
	}
	
	
	/*  public void setVariablesForViews(ArrayList var1) {
	String var2 = "";
	
	ViewBean var4;
	for(Iterator var3 = var1.iterator(); var3.hasNext(); var2 = var2 + SourceUtil.makeVariableSrc(var4) + "\r\n") {
	var4 = (ViewBean)var3.next();
	}
	
	this.source = this.source.replaceAll("<\\?variable_view\\?>", var2);
	}*/
}
