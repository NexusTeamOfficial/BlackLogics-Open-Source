/* Decompiler 668ms, total 1696ms, lines 1234 */
package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class kq {
    private static kq a;
    private HashMap<String, String> b;
    private HashMap<String, String> c;
    private String d = fe.g();
    private boolean e;
    private final String f = "block";
    private final String g = "root_spec";
    
    private kq() {
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        if (this.c == null) {
            this.c = new HashMap();
        }
        
    }
    
    public static kq a() {
        if (a == null) {
            synchronized(kq.class){}
            
            Throwable var10000;
            boolean var10001;
            label191: {
                label190: {
                    try {
                        if (a != null) {
                            break label190;
                        }
                    } catch (Throwable var20) {
                        var10000 = var20;
                        var10001 = false;
                        break label191;
                    }
                    
                    try {
                        kq var0 = new kq();
                        a = var0;
                    } catch (Throwable var19) {
                        var10000 = var19;
                        var10001 = false;
                        break label191;
                    }
                }
                
                label184:
                try {
                    return a;
                } catch (Throwable var18) {
                    var10000 = var18;
                    var10001 = false;
                    break label184;
                }
            }
            
            while(true) {
                Throwable var21 = var10000;
                
                try {
                    throw var21;
                } catch (Throwable var17) {
                    var10000 = var17;
                    var10001 = false;
                    continue;
                }
            }
        } else {
            return a;
        }
    }
    
    private HashMap<String, String> b(String path) {
        HashMap<String, String> map = new HashMap<>();
        InputStreamReader reader = null;
        FileInputStream fis = null;
        try {
            File file = new File(path);
            if (!file.exists()) return map;
            
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            fis = new FileInputStream(file);
            reader = new InputStreamReader(fis, "UTF-8");
            parser.setInput(reader);
            
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "string".equals(parser.getName())) {
                    String key = parser.getAttributeValue(null, "name");
                    String value = parser.nextText();
                    map.put(key, value);
                }
                eventType = parser.next();
            }
            e = true; // <-- Boolean
            if (fis != null) fis.close();
            if (reader != null) reader.close();
        } catch (Exception ex) {
            e = false; // <-- Boolean
            try {
                if (fis != null) fis.close();
            } catch (Exception ignored) {}
            try {
                if (reader != null) reader.close();
            } catch (Exception ignored) {}
        } catch (Throwable t) {
            try {
                if (fis != null) fis.close();
            } catch (Exception ignored) {}
            try {
                if (reader != null) reader.close();
            } catch (Exception ignored) {}
            throw t;
        }
        return map;
    }
    
    public String a(Context var1, int var2) {
        return this.a(var1.getResources(), var2);
    }
    
    public String a(Context var1, int var2, Object... var3) {
        return this.a(var1.getResources(), var2, var3);
    }
    
    public String a(Context var1, String var2) {
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        if (this.b.isEmpty()) {
            this.e = false;
            this.b = this.a(this.d);
        }
        
        if (this.c == null) {
            this.c = new HashMap();
        }
        
        if (this.c.isEmpty()) {
            this.b(var1);
        }
        
        var2 = (String)this.c.get(var2);
        String var3 = var2;
        if (var2 == null) {
            var3 = "";
        }
        
        return var3;
    }
    
    public String a(Context var1, String var2, String var3) {
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        boolean var5 = this.b.isEmpty();
        byte var4 = 0;
        if (var5) {
            this.e = false;
            this.b = this.a(this.d);
        }
        
        if (this.c == null) {
            this.c = new HashMap();
        }
        
        if (this.c.isEmpty()) {
            this.b(var1);
        }
        
        label159: {
            switch(var3.hashCode()) {
                case -2117913147:
                if (var3.equals("onStartTrackingTouch")) {
                    var4 = 9;
                    break label159;
                }
                break;
                case -2067423513:
                if (var3.equals("onSpeechError")) {
                    var4 = 33;
                    break label159;
                }
                break;
                case -1865337024:
                if (var3.equals("onResponse")) {
                    var4 = 30;
                    break label159;
                }
                break;
                case -1809154262:
                if (var3.equals("onDataReceived")) {
                    var4 = 35;
                    break label159;
                }
                break;
                case -1779618840:
                if (var3.equals("onProgressChanged")) {
                    var4 = 8;
                    break label159;
                }
                break;
                case -1358405466:
                if (var3.equals("onMapReady")) {
                    var4 = 39;
                    break label159;
                }
                break;
                case -1351902487:
                if (var3.equals("onClick")) {
                    break label159;
                }
                break;
                case -1215328199:
                if (var3.equals("onDeleteSuccess")) {
                    var4 = 24;
                    break label159;
                }
                break;
                case -1153785290:
                if (var3.equals("onAnimationEnd")) {
                    var4 = 12;
                    break label159;
                }
                break;
                case -891988931:
                if (var3.equals("onDateChange")) {
                    var4 = 15;
                    break label159;
                }
                break;
                case -837428873:
                if (var3.equals("onChildChanged")) {
                    var4 = 17;
                    break label159;
                }
                break;
                case -821066400:
                if (var3.equals("onLocationChanged")) {
                    var4 = 41;
                    break label159;
                }
                break;
                case -749253875:
                if (var3.equals("onUploadProgress")) {
                    var4 = 20;
                    break label159;
                }
                break;
                case -732782352:
                if (var3.equals("onConnectionStopped")) {
                    var4 = 38;
                    break label159;
                }
                break;
                case -719893013:
                if (var3.equals("onConnectionError")) {
                    var4 = 37;
                    break label159;
                }
                break;
                case -672992515:
                if (var3.equals("onAnimationStart")) {
                    var4 = 11;
                    break label159;
                }
                break;
                case -609996822:
                if (var3.equals("onConnected")) {
                    var4 = 34;
                    break label159;
                }
                break;
                case -584901992:
                if (var3.equals("onCheckedChange")) {
                    var4 = 1;
                    break label159;
                }
                break;
                case -507667891:
                if (var3.equals("onItemSelected")) {
                    var4 = 2;
                    break label159;
                }
                break;
                case -505277536:
                if (var3.equals("onPageFinished")) {
                    var4 = 7;
                    break label159;
                }
                break;
                case -484536541:
                if (var3.equals("onChildRemoved")) {
                    var4 = 18;
                    break label159;
                }
                break;
                case -376002870:
                if (var3.equals("onErrorResponse")) {
                    var4 = 31;
                    break label159;
                }
                break;
                case 80616227:
                if (var3.equals("onUploadSuccess")) {
                    var4 = 22;
                    break label159;
                }
                break;
                case 136827711:
                if (var3.equals("onAnimationCancel")) {
                    var4 = 13;
                    break label159;
                }
                break;
                case 162093458:
                if (var3.equals("onBindCustomView")) {
                    var4 = 14;
                    break label159;
                }
                break;
                case 249705131:
                if (var3.equals("onFailure")) {
                    var4 = 25;
                    break label159;
                }
                break;
                case 264008033:
                if (var3.equals("onDataSent")) {
                    var4 = 36;
                    break label159;
                }
                break;
                case 372583555:
                if (var3.equals("onChildAdded")) {
                    var4 = 16;
                    break label159;
                }
                break;
                case 378110312:
                if (var3.equals("onTextChanged")) {
                    var4 = 5;
                    break label159;
                }
                break;
                case 445802034:
                if (var3.equals("onCancelled")) {
                    var4 = 19;
                    break label159;
                }
                break;
                case 694589214:
                if (var3.equals("onSpeechResult")) {
                    var4 = 32;
                    break label159;
                }
                break;
                case 805710389:
                if (var3.equals("onItemClicked")) {
                    var4 = 3;
                    break label159;
                }
                break;
                case 1170737640:
                if (var3.equals("onPictureTaken")) {
                    var4 = 26;
                    break label159;
                }
                break;
                case 1348442836:
                if (var3.equals("onDownloadProgress")) {
                    var4 = 21;
                    break label159;
                }
                break;
                case 1348605570:
                if (var3.equals("onPictureTakenCancel")) {
                    var4 = 27;
                    break label159;
                }
                break;
                case 1395209852:
                if (var3.equals("onDownloadSuccess")) {
                    var4 = 23;
                    break label159;
                }
                break;
                case 1586033095:
                if (var3.equals("onStopTrackingTouch")) {
                    var4 = 10;
                    break label159;
                }
                break;
                case 1710477203:
                if (var3.equals("onPageStarted")) {
                    var4 = 6;
                    break label159;
                }
                break;
                case 1757061906:
                if (var3.equals("onFilesPickedCancel")) {
                    var4 = 29;
                    break label159;
                }
                break;
                case 1803231982:
                if (var3.equals("onMarkerClicked")) {
                    var4 = 40;
                    break label159;
                }
                break;
                case 1979400473:
                if (var3.equals("onItemLongClicked")) {
                    var4 = 4;
                    break label159;
                }
                break;
                case 2087273080:
                if (var3.equals("onFilesPicked")) {
                    var4 = 28;
                    break label159;
                }
            }
            
            var4 = -1;
        }
        
        switch(var4) {
            case 0:
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
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            String var6 = (String)this.c.get(var3);
            var3 = var6;
            if (var6 == null) {
                var3 = "";
            }
            
            StringBuilder var8 = new StringBuilder();
            var8.append(this.a(var1, R.string.root_spec_common_when));
            var8.append(" ");
            var8.append(var2);
            var8.append(" ");
            var8.append(var3);
            return var8.toString();
            default:
            var2 = (String)this.c.get(var3);
            String var7 = var2;
            if (var2 == null) {
                var7 = "";
            }
            
            return var7;
        }
    }
    
    public String a(Context var1, String var2, ArrayList<String> var3) {
        int var4 = var3.size();
        boolean var8 = true;
        byte var7 = 0;
        if (var4 > 1) {
            var4 = var3.size() - 1;
        } else {
            var4 = 0;
        }
        
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        if (this.b.isEmpty()) {
            this.e = false;
            this.b = this.a(this.d);
        }
        
        boolean var5;
        int var6;
        StringBuilder var9;
        StringBuilder var11;
        String var12;
        String var17;
        String var18;
        label99: {
            var11 = new StringBuilder(1024);
            var9 = new StringBuilder();
            var9.append(var2);
            var9.append("_head");
            var17 = var9.toString();
            StringBuilder var10 = new StringBuilder();
            var10.append(var2);
            var10.append("_tail");
            var12 = var10.toString();
            if (this.b != null && this.b.containsKey(var17) && this.b.containsKey(var12)) {
                var6 = 0;
                
                do {
                    var5 = var8;
                    if (var6 >= var4) {
                        break label99;
                    }
                    
                    var10 = new StringBuilder();
                    var10.append(var2);
                    var10.append("_body_");
                    ++var6;
                    var10.append(var6);
                    var18 = var10.toString();
                } while(this.b.containsKey(var18));
            }
            
            var5 = false;
        }
        
        if (var5) {
            var17 = (String)this.b.get(var17);
        } else {
            try {
                var17 = var1.getResources().getString(var1.getResources().getIdentifier(var17, "string", var1.getPackageName()));
            } catch (Exception var15) {
                var17 = "";
            }
        }
        
        var18 = var17;
        if (var17 == null) {
            var18 = "";
        }
        
        var11.append(var18);
        var6 = var7;
        if (var3.size() > 0) {
            if (var11.length() > 0) {
                var11.append(" ");
            }
            
            var11.append((String)var3.get(0));
            var6 = var7;
        }
        
        for(; var6 < var4; var11.append((String)var3.get(var6))) {
            var9 = new StringBuilder();
            var9.append(var2);
            var9.append("_body_");
            ++var6;
            var9.append(var6);
            var17 = var9.toString();
            if (var5) {
                var17 = (String)this.b.get(var17);
            } else {
                try {
                    var17 = var1.getResources().getString(var1.getResources().getIdentifier(var17, "string", var1.getPackageName()));
                } catch (Exception var14) {
                    var17 = "";
                }
            }
            
            var18 = var17;
            if (var17 == null) {
                var18 = "";
            }
            
            if (var18.length() > 0) {
                var11.append(" ");
            }
            
            var11.append(var18);
            if (var11.length() > 0) {
                var11.append(" ");
            }
        }
        
        String var16;
        if (var5) {
            var16 = (String)this.b.get(var12);
        } else {
            try {
                var16 = var1.getResources().getString(var1.getResources().getIdentifier(var12, "string", var1.getPackageName()));
            } catch (Exception var13) {
                var16 = "";
            }
        }
        
        var2 = var16;
        if (var16 == null) {
            var2 = "";
        }
        
        if (var11.length() > 0 && var2.length() > 0) {
            var11.append(" ");
        }
        
        var11.append(var2);
        return var11.toString();
    }
    
    public String a(Resources var1, int var2) {
        String var3 = var1.getResourceEntryName(var2);
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        try {
            if (this.b.isEmpty()) {
                this.e = false;
                this.b = this.a(this.d);
            }
            
            if (this.b.containsKey(var3) && this.b.get(var3) != null && ((String)this.b.get(var3)).length() > 0) {
                var3 = ((String)this.b.get(var3)).replaceAll("\\\\\\'", "'").replaceAll("\\\\\\\"", "\"").replaceAll("\\\\n", "\\\n");
                return var3;
            }
        } catch (Exception var4) {
            return var1.getString(var2);
        }
        
        return var1.getString(var2);
    }
    
    public String a(Resources var1, int var2, Object... var3) {
        String var12 = var1.getResourceEntryName(var2);
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        boolean var8 = this.b.isEmpty();
        int var4 = 0;
        if (var8) {
            this.e = false;
            this.b = this.a(this.d);
        }
        
        String var9;
        label111: {
            Exception var10000;
            label99: {
                boolean var10001;
                try {
                    if (!this.b.containsKey(var12) || this.b.get(var12) == null || ((String)this.b.get(var12)).length() <= 0) {
                        return var1.getString(var2, var3);
                    }
                } catch (Exception var20) {
                    var10000 = var20;
                    var10001 = false;
                    break label99;
                }
                
                try {
                    var9 = ((String)this.b.get(var12)).replaceAll("\\\\\\'", "'").replaceAll("\\\\\\\"", "\"").replaceAll("\\\\n", "\\\n");
                } catch (Exception var19) {
                    var10000 = var19;
                    var10001 = false;
                    break label99;
                }
                
                int var7;
                try {
                    var7 = var3.length;
                } catch (Exception var18) {
                    var10000 = var18;
                    var10001 = false;
                    break label99;
                }
                
                int var5;
                String var10;
                for(var5 = 0; var4 < var7; var9 = var10) {
                    Object var11 = var3[var4];
                    int var6 = var5;
                    var10 = var9;
                    
                    label102: {
                        try {
                            if (!var9.contains("%")) {
                                break label102;
                            }
                        } catch (Exception var17) {
                            var10000 = var17;
                            var10001 = false;
                            break label99;
                        }
                        
                        String var22;
                        try {
                            var22 = var11.toString();
                        } catch (Exception var15) {
                            var10000 = var15;
                            var10001 = false;
                            break label99;
                        }
                        
                        var10 = var22;
                        
                        label71: {
                            try {
                                if (!var22.equals("\\n")) {
                                    break label71;
                                }
                            } catch (Exception var16) {
                                var10000 = var16;
                                var10001 = false;
                                break label99;
                            }
                            
                            var10 = "\\\\n";
                        }
                        
                        try {
                            var10 = var9.replaceFirst("%s", var10);
                        } catch (Exception var14) {
                            var10000 = var14;
                            var10001 = false;
                            break label99;
                        }
                        
                        var6 = var5 + 1;
                    }
                    
                    ++var4;
                    var5 = var6;
                }
                
                try {
                    if (var5 != var3.length) {
                        return var1.getString(var2, var3);
                    }
                    
                    var8 = var9.contains("%");
                    break label111;
                } catch (Exception var13) {
                    var10000 = var13;
                    var10001 = false;
                }
            }
            
            Exception var23 = var10000;
            StringBuilder var21 = new StringBuilder();
            var21.append("Faild to load (");
            var21.append(var12);
            var21.append(")");
            Log.e("ERROR", var21.toString(), var23);
            return var1.getString(var2, var3);
        }
        
        if (!var8) {
            return var9;
        } else {
            return var1.getString(var2, var3);
        }
    }
    
    public HashMap<String, String> a(String var1) {
        HashMap var2 = new HashMap();
        kk var4 = new kk();
        lb var3 = new lb();
        if (!(new File(var1)).exists()) {
            return var2;
        } else {
            HashMap var8;
            HashMap var10;
            try {
                byte[] var11 = var4.f(var1);
                byte[] var9 = var3.b(var11);
                var10 = this.a(var9);
            } catch (Exception var7) {
                var8 = var2;
                return var8;
            }
            
            
            var8 = var10;
            return var8;
        }
    }
    
    public HashMap<String, String> a(byte[] bytes) {
        HashMap<String, String> map = new HashMap<>();
        InputStreamReader reader = null;
        ByteArrayInputStream bais = null;
        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            bais = new ByteArrayInputStream(bytes);
            reader = new InputStreamReader(bais, "UTF-8");
            parser.setInput(reader);
            
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "string".equals(parser.getName())) {
                    String key = parser.getAttributeValue(null, "name");
                    String value = parser.nextText();
                    map.put(key, value);
                }
                eventType = parser.next();
            }
            e = true; // <-- changed from 1 to true
            if (bais != null) bais.close();
            if (reader != null) reader.close();
        } catch (Exception ex) {
            e = false; // <-- changed from 0 to false
            try {
                if (bais != null) bais.close();
            } catch (Exception ignored) {}
            try {
                if (reader != null) reader.close();
            } catch (Exception ignored) {}
        } catch (Throwable t) {
            try {
                if (bais != null) bais.close();
            } catch (Exception ignored) {}
            try {
                if (reader != null) reader.close();
            } catch (Exception ignored) {}
            throw t;
        }
        return map;
    }
    
    
    public boolean a(Context var1) {
        if (this.b == null) {
            this.b = new HashMap();
        }
        
        if (this.b != null) {
            this.b.clear();
        }
        
        this.b = this.a(this.d);
        this.b(var1);
        boolean var2;
        if (!this.b.isEmpty() && this.e) {
            var2 = true;
        } else {
            var2 = false;
        }
        
        return var2;
    }
    
    public void b() {
        if (this.b != null) {
            this.b.clear();
            this.b = null;
        }
        
        if (this.c != null) {
            this.c.clear();
            this.c = null;
        }
        
    }
    
    protected void b(Context var1) {
        this.c = new HashMap();
        this.c(var1, "initializeLogic");
        this.c(var1, "onBackPressed");
        this.c(var1, "onPostCreate");
        this.c(var1, "onStart");
        this.c(var1, "onStop");
        this.c(var1, "onDestroy");
        this.c(var1, "onResume");
        this.c(var1, "onPause");
        this.c(var1, "moreBlock");
        this.c(var1, "onClick");
        this.c(var1, "onCheckedChange");
        this.c(var1, "onItemSelected");
        this.c(var1, "onItemClicked");
        this.c(var1, "onItemLongClicked");
        this.c(var1, "onTextChanged");
        this.c(var1, "onPageStarted");
        this.c(var1, "onPageFinished");
        this.c(var1, "onProgressChanged");
        this.c(var1, "onStartTrackingTouch");
        this.c(var1, "onStopTrackingTouch");
        this.c(var1, "onAnimationStart");
        this.c(var1, "onAnimationEnd");
        this.c(var1, "onAnimationCancel");
        this.c(var1, "onBindCustomView");
        this.c(var1, "onDateChange");
        this.c(var1, "onChildAdded");
        this.c(var1, "onChildChanged");
        this.c(var1, "onChildRemoved");
        this.c(var1, "onCancelled");
        this.c(var1, "onSensorChanged");
        this.c(var1, "onCreateUserComplete");
        this.c(var1, "onSignInUserComplete");
        this.c(var1, "onResetPasswordEmailSent");
        this.c(var1, "onUploadProgress");
        this.c(var1, "onDownloadProgress");
        this.c(var1, "onUploadSuccess");
        this.c(var1, "onDownloadSuccess");
        this.c(var1, "onDeleteSuccess");
        this.c(var1, "onFailure");
        this.c(var1, "onPictureTaken");
        this.c(var1, "onPictureTakenCancel");
        this.c(var1, "onFilesPicked");
        this.c(var1, "onFilesPickedCancel");
        this.c(var1, "onAdLoaded");
        this.c(var1, "onAdFailedToLoad");
        this.c(var1, "onAdOpened");
        this.c(var1, "onAdClosed");
        this.c(var1, "onResponse");
        this.c(var1, "onErrorResponse");
        this.c(var1, "onSpeechResult");
        this.c(var1, "onSpeechError");
        this.c(var1, "onConnected");
        this.c(var1, "onDataReceived");
        this.c(var1, "onDataSent");
        this.c(var1, "onConnectionError");
        this.c(var1, "onConnectionStopped");
        this.c(var1, "onMapReady");
        this.c(var1, "onMarkerClicked");
        this.c(var1, "onLocationChanged");
        this.b(var1, "viewOnClick");
        this.b(var1, "setVarBoolean");
        this.b(var1, "setVarInt");
        this.b(var1, "increaseInt");
        this.b(var1, "decreaseInt");
        this.b(var1, "setVarString");
        this.b(var1, "mapCreateNew");
        this.b(var1, "mapPut");
        this.b(var1, "mapGet");
        this.b(var1, "mapContainKey");
        this.b(var1, "mapRemoveKey");
        this.b(var1, "mapSize");
        this.b(var1, "mapClear");
        this.b(var1, "mapIsEmpty");
        this.b(var1, "mapGetAllKeys");
        this.b(var1, "addListInt");
        this.b(var1, "insertListInt");
        this.b(var1, "deleteList");
        this.b(var1, "getAtListInt");
        this.b(var1, "indexListInt");
        this.b(var1, "lengthList");
        this.b(var1, "containListInt");
        this.b(var1, "clearList");
        this.b(var1, "addListStr");
        this.b(var1, "insertListStr");
        this.b(var1, "getAtListStr");
        this.b(var1, "indexListStr");
        this.b(var1, "containListStr");
        this.b(var1, "addListMap");
        this.b(var1, "insertListMap");
        this.b(var1, "getAtListMap");
        this.b(var1, "setListMap");
        this.b(var1, "containListMap");
        this.b(var1, "addMapToList");
        this.b(var1, "insertMapToList");
        this.b(var1, "getMapInList");
        this.b(var1, "repeat");
        this.b(var1, "forever");
        this.b(var1, "break");
        this.b(var1, "if");
        this.b(var1, "ifElse");
        this.b(var1, "else");
        this.b(var1, "true");
        this.b(var1, "false");
        this.b(var1, "<");
        this.b(var1, "=");
        this.b(var1, ">");
        this.b(var1, "&&");
        this.b(var1, "||");
        this.b(var1, "not");
        this.b(var1, "+");
        this.b(var1, "-");
        this.b(var1, "*");
        this.b(var1, "/");
        this.b(var1, "%");
        this.b(var1, "random");
        this.b(var1, "stringLength");
        this.b(var1, "stringJoin");
        this.b(var1, "stringIndex");
        this.b(var1, "stringLastIndex");
        this.b(var1, "stringSub");
        this.b(var1, "stringEquals");
        this.b(var1, "stringContains");
        this.b(var1, "stringReplace");
        this.b(var1, "stringReplaceFirst");
        this.b(var1, "stringReplaceAll");
        this.b(var1, "toNumber");
        this.b(var1, "trim");
        this.b(var1, "toUpperCase");
        this.b(var1, "toLowerCase");
        this.b(var1, "toString");
        this.b(var1, "toStringWithDecimal");
        this.b(var1, "toStringFormat");
        this.b(var1, "addSourceDirectly");
        this.b(var1, "mapToStr");
        this.b(var1, "strToMap");
        this.b(var1, "listMapToStr");
        this.b(var1, "strToListMap");
        this.b(var1, "mathGetDip");
        this.b(var1, "mathGetDisplayWidth");
        this.b(var1, "mathGetDisplayHeight");
        this.b(var1, "mathPi");
        this.b(var1, "mathE");
        this.b(var1, "mathPow");
        this.b(var1, "mathMin");
        this.b(var1, "mathMax");
        this.b(var1, "mathSqrt");
        this.b(var1, "mathAbs");
        this.b(var1, "mathRound");
        this.b(var1, "mathCeil");
        this.b(var1, "mathFloor");
        this.b(var1, "mathSin");
        this.b(var1, "mathCos");
        this.b(var1, "mathTan");
        this.b(var1, "mathAsin");
        this.b(var1, "mathAcos");
        this.b(var1, "mathAtan");
        this.b(var1, "mathExp");
        this.b(var1, "mathLog");
        this.b(var1, "mathLog10");
        this.b(var1, "mathToRadian");
        this.b(var1, "mathToDegree");
        this.b(var1, "isDrawerOpen");
        this.b(var1, "openDrawer");
        this.b(var1, "closeDrawer");
        this.b(var1, "viewOnClick");
        this.b(var1, "setEnable");
        this.b(var1, "getEnable");
        this.b(var1, "setVisible");
        this.b(var1, "setClickable");
        this.b(var1, "setText");
        this.b(var1, "setTypeface");
        this.b(var1, "getText");
        this.b(var1, "setBgColor");
        this.b(var1, "setBgResource");
        this.b(var1, "setTextColor");
        this.b(var1, "setHint");
        this.b(var1, "setHintTextColor");
        this.b(var1, "setImage");
        this.b(var1, "setColorFilter");
        this.b(var1, "requestFocus");
        this.b(var1, "setRotate");
        this.b(var1, "getRotate");
        this.b(var1, "setAlpha");
        this.b(var1, "getAlpha");
        this.b(var1, "setTranslationX");
        this.b(var1, "getTranslationX");
        this.b(var1, "setTranslationY");
        this.b(var1, "getTranslationY");
        this.b(var1, "setScaleX");
        this.b(var1, "getScaleX");
        this.b(var1, "setScaleY");
        this.b(var1, "getScaleY");
        this.b(var1, "getLocationX");
        this.b(var1, "getLocationY");
        this.b(var1, "setChecked");
        this.b(var1, "getChecked");
        this.b(var1, "setThumbResource");
        this.b(var1, "setTrackResource");
        this.b(var1, "listSetData");
        this.b(var1, "listSetCustomViewData");
        this.b(var1, "listRefresh");
        this.b(var1, "listSetItemChecked");
        this.b(var1, "listGetCheckedPosition");
        this.b(var1, "listGetCheckedPositions");
        this.b(var1, "listGetCheckedCount");
        this.b(var1, "listSmoothScrollTo");
        this.b(var1, "spnSetData");
        this.b(var1, "spnRefresh");
        this.b(var1, "spnSetSelection");
        this.b(var1, "spnGetSelection");
        this.b(var1, "webViewLoadUrl");
        this.b(var1, "webViewGetUrl");
        this.b(var1, "webViewSetCacheMode");
        this.b(var1, "webViewCanGoBack");
        this.b(var1, "webViewCanGoForward");
        this.b(var1, "webViewGoBack");
        this.b(var1, "webViewGoForward");
        this.b(var1, "webViewClearCache");
        this.b(var1, "webViewClearHistory");
        this.b(var1, "webViewStopLoading");
        this.b(var1, "webViewZoomIn");
        this.b(var1, "webViewZoomOut");
        this.b(var1, "calendarViewGetDate");
        this.b(var1, "calendarViewSetDate");
        this.b(var1, "calendarViewSetMinDate");
        this.b(var1, "calnedarViewSetMaxDate");
        this.b(var1, "adViewLoadAd");
        this.b(var1, "mapViewSetMapType");
        this.b(var1, "mapViewMoveCamera");
        this.b(var1, "mapViewZoomTo");
        this.b(var1, "mapViewZoomIn");
        this.b(var1, "mapViewZoomOut");
        this.b(var1, "mapViewAddMarker");
        this.b(var1, "mapViewSetMarkerInfo");
        this.b(var1, "mapViewSetMarkerPosition");
        this.b(var1, "mapViewSetMarkerColor");
        this.b(var1, "mapViewSetMarkerIcon");
        this.b(var1, "mapViewSetMarkerVisible");
        this.b(var1, "intentSetAction");
        this.b(var1, "intentSetData");
        this.b(var1, "intentSetScreen");
        this.b(var1, "intentPutExtra");
        this.b(var1, "intentSetFlags");
        this.b(var1, "startActivity");
        this.b(var1, "intentGetString");
        this.b(var1, "finishActivity");
        this.b(var1, "fileGetData");
        this.b(var1, "fileSetData");
        this.b(var1, "fileRemoveData");
        this.b(var1, "calendarGetNow");
        this.b(var1, "calendarAdd");
        this.b(var1, "calendarSet");
        this.b(var1, "calendarFormat");
        this.b(var1, "calendarDiff");
        this.b(var1, "calendarGetTime");
        this.b(var1, "calendarSetTime");
        this.b(var1, "vibratorAction");
        this.b(var1, "timerAfter");
        this.b(var1, "timerEvery");
        this.b(var1, "timerCancel");
        this.b(var1, "dialogSetTitle");
        this.b(var1, "dialogSetMessage");
        this.b(var1, "dialogOkButton");
        this.b(var1, "dialogCancelButton");
        this.b(var1, "dialogNeutralButton");
        this.b(var1, "dialogShow");
        this.b(var1, "dialogDismiss");
        this.b(var1, "mediaplayerCreate");
        this.b(var1, "mediaplayerStart");
        this.b(var1, "mediaplayerPause");
        this.b(var1, "mediaplayerSeek");
        this.b(var1, "mediaplayerGetCurrent");
        this.b(var1, "mediaplayerGetDuration");
        this.b(var1, "mediaplayerIsPlaying");
        this.b(var1, "mediaplayerSetLooping");
        this.b(var1, "mediaplayerIsLooping");
        this.b(var1, "mediaplayerReset");
        this.b(var1, "mediaplayerRelease");
        this.b(var1, "soundpoolCreate");
        this.b(var1, "soundpoolLoad");
        this.b(var1, "soundpoolStreamPlay");
        this.b(var1, "soundpoolStreamStop");
        this.b(var1, "objectanimatorSetTarget");
        this.b(var1, "objectanimatorSetProperty");
        this.b(var1, "objectanimatorSetValue");
        this.b(var1, "objectanimatorSetFromTo");
        this.b(var1, "objectanimatorSetDuration");
        this.b(var1, "objectanimatorSetRepeatMode");
        this.b(var1, "objectanimatorSetRepeatCount");
        this.b(var1, "objectanimatorSetInterpolator");
        this.b(var1, "objectanimatorStart");
        this.b(var1, "objectanimatorCancel");
        this.b(var1, "objectanimatorIsRunning");
        this.b(var1, "firebaseAdd");
        this.b(var1, "firebasePush");
        this.b(var1, "firebaseGetPushKey");
        this.b(var1, "firebaseDelete");
        this.b(var1, "firebaseGetChildren");
        this.b(var1, "firebaseauthCreateUser");
        this.b(var1, "firebaseauthSignInUser");
        this.b(var1, "firebaseauthSignInAnonymously");
        this.b(var1, "firebaseauthIsLoggedIn");
        this.b(var1, "firebaseauthGetCurrentUser");
        this.b(var1, "firebaseauthGetUid");
        this.b(var1, "firebaseauthResetPassword");
        this.b(var1, "firebaseauthSignOutUser");
        this.b(var1, "firebaseStartListen");
        this.b(var1, "firebaseStopListen");
        this.b(var1, "gyroscopeStartListen");
        this.b(var1, "gyroscopeStopListen");
        this.b(var1, "interstitialadCreate");
        this.b(var1, "interstitialadLoadAd");
        this.b(var1, "interstitialadShow");
        this.b(var1, "firebasestorageUploadFile");
        this.b(var1, "firebasestorageDownloadFile");
        this.b(var1, "firebasestorageDelete");
        this.b(var1, "camerastarttakepicture");
        this.b(var1, "filepickerstartpickfiles");
        this.b(var1, "requestnetworkSetParams");
        this.b(var1, "requestnetworkSetHeaders");
        this.b(var1, "requestnetworkStartRequestNetwork");
        this.b(var1, "fileutildelete");
        this.b(var1, "fileutilcopy");
        this.b(var1, "fileutilwrite");
        this.b(var1, "fileutilread");
        this.b(var1, "fileutilmove");
        this.b(var1, "fileutilisexist");
        this.b(var1, "fileutilmakedir");
        this.b(var1, "fileutillistdir");
        this.b(var1, "fileutilisdir");
        this.b(var1, "fileutilisfile");
        this.b(var1, "fileutillength");
        this.b(var1, "fileutilStartsWith");
        this.b(var1, "fileutilEndsWith");
        this.b(var1, "fileutilGetLastSegmentPath");
        this.b(var1, "doToast");
        this.b(var1, "copyToClipboard");
        this.b(var1, "setTitle");
        this.b(var1, "seekBarGetMax");
        this.b(var1, "seekBarGetProgress");
        this.b(var1, "seekBarSetMax");
        this.b(var1, "seekBarSetProgress");
        this.b(var1, "getExternalStorageDir");
        this.b(var1, "getPackageDataDir");
        this.b(var1, "getPublicDir");
        this.b(var1, "resizeBitmapFileRetainRatio");
        this.b(var1, "resizeBitmapFileToSquare");
        this.b(var1, "resizeBitmapFileToCircle");
        this.b(var1, "resizeBitmapFileWithRoundedBorder");
        this.b(var1, "cropBitmapFileFromCenter");
        this.b(var1, "rotateBitmapFile");
        this.b(var1, "scaleBitmapFile");
        this.b(var1, "skewBitmapFile");
        this.b(var1, "setBitmapFileColorFilter");
        this.b(var1, "setBitmapFileBrightness");
        this.b(var1, "setBitmapFileContrast");
        this.b(var1, "setImageFilePath");
        this.b(var1, "setImageUrl");
        this.b(var1, "getJpegRotate");
        this.b(var1, "progressBarSetIndeterminate");
        this.b(var1, "textToSpeechSetPitch");
        this.b(var1, "textToSpeechSetSpeechRate");
        this.b(var1, "textToSpeechSpeak");
        this.b(var1, "textToSpeechIsSpeaking");
        this.b(var1, "textToSpeechStop");
        this.b(var1, "textToSpeechShutdown");
        this.b(var1, "speechToTextStartListening");
        this.b(var1, "speechToTextStopListening");
        this.b(var1, "speechToTextShutdown");
        this.b(var1, "bluetoothConnectReadyConnection");
        this.b(var1, "bluetoothConnectReadyConnectionToUuid");
        this.b(var1, "bluetoothConnectStartConnection");
        this.b(var1, "bluetoothConnectStartConnectionToUuid");
        this.b(var1, "bluetoothConnectStopConnection");
        this.b(var1, "bluetoothConnectSendData");
        this.b(var1, "bluetoothConnectIsBluetoothEnabled");
        this.b(var1, "bluetoothConnectIsBluetoothActivated");
        this.b(var1, "bluetoothConnectActivateBluetooth");
        this.b(var1, "bluetoothConnectGetPairedDevices");
        this.b(var1, "bluetoothConnectGetRandomUuid");
        this.b(var1, "locationManagerRequestLocationUpdates");
        this.b(var1, "locationManagerRemoveUpdates");
    }
    
    protected void b(Context var1, String var2) {
        StringBuilder var3 = new StringBuilder();
        var3.append("block_");
        var3.append(eu.b(var2));
        String var5 = var3.toString();
        ArrayList var4 = eu.d(var2);
        this.c.put(var2, this.a(var1, var5, var4));
    }
    
    protected void c(Context var1, String var2) {
        StringBuilder var3 = new StringBuilder();
        var3.append("root_spec_");
        var3.append(eu.a(var2));
        String var5 = var3.toString();
        ArrayList var4 = eu.c(var2);
        this.c.put(var2, this.a(var1, var5, var4));
    }
}
