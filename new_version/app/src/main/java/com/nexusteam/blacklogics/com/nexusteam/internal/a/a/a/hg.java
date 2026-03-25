/* Decompiler 670ms, total 4787ms, lines 2802 */
package com.nexusteam.internal;

import com.nexusteam.internal.beans.ViewBean;
import java.util.ArrayList;
import java.util.Iterator;

public class hg {
   public static String a() {
      StringBuilder var0 = new StringBuilder();
      var0.append("include ':app'");
      return var0.toString();
   }

   public static String a(int var0, int var1, int var2, es var3) {
      StringBuilder var4 = new StringBuilder();
      var4.append("apply plugin: 'com.android.application'\r\n\r\nandroid {\r\nuseLibrary 'org.apache.http.legacy'\r\ncompileSdkVersion ");
      var4.append(var0);
      var4.append("\r\n");
      var4.append("\r\n");
      var4.append("defaultConfig {");
      var4.append("\r\n");
      var4.append("applicationId \"");
      var4.append(var3.f124a);
      var4.append("\"");
      var4.append("\r\n");
      var4.append("minSdkVersion ");
      var4.append(var1);
      var4.append("\r\n");
      var4.append("targetSdkVersion ");
      var4.append(var2);
      var4.append("\r\n");
      var4.append("versionCode ");
      var4.append(var3.c);
      var4.append("\r\n");
      var4.append("versionName \"");
      var4.append(var3.d);
      var4.append("\"");
      var4.append("\r\n");
      var4.append("}");
      var4.append("\r\n");
      var4.append("buildTypes {");
      var4.append("\r\n");
      var4.append("release {");
      var4.append("\r\n");
      var4.append("minifyEnabled false");
      var4.append("\r\n");
      var4.append("proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'");
      var4.append("\r\n");
      var4.append("}");
      var4.append("\r\n");
      var4.append("}");
      var4.append("\r\n");
      var4.append("}");
      var4.append("\r\n");
      var4.append("\r\n");
      var4.append("dependencies {");
      var4.append("\r\n");
      var4.append("compile fileTree(dir: 'libs', include: ['*.jar'])");
      var4.append("\r\n");
      String var5 = var4.toString();
      String var7 = var5;
      StringBuilder var9;
      if (var3.k) {
         var4 = new StringBuilder();
         var4.append(var5);
         var4.append("implementation 'com.android.support:appcompat-v7:27.0.2'\r\n");
         var7 = var4.toString();
         var9 = new StringBuilder();
         var9.append(var7);
         var9.append("implementation 'com.android.support:design:27.0.2'\r\n");
         var7 = var9.toString();
      }

      var5 = var7;
      if (var3.g) {
         var9 = new StringBuilder();
         var9.append(var7);
         var9.append("implementation 'com.android.support:support-v4:27.0.2\r\n");
         var5 = var9.toString();
      }

      var7 = var5;
      if (var3.l) {
         var4 = new StringBuilder();
         var4.append(var5);
         var4.append("implementation 'com.google.firebase:firebase-core:15.0.2'\r\n");
         var5 = var4.toString();
         var4 = new StringBuilder();
         var4.append(var5);
         var4.append("implementation 'com.google.firebase:firebase-auth:15.0.0'\r\n");
         var7 = var4.toString();
         var9 = new StringBuilder();
         var9.append(var7);
         var9.append("implementation 'com.google.firebase:firebase-database:15.0.1'\r\n");
         var7 = var9.toString();
         var9 = new StringBuilder();
         var9.append(var7);
         var9.append("implementation 'com.google.firebase:firebase-storage:15.0.0'\r\n");
         var7 = var9.toString();
      }

      var5 = var7;
      if (var3.m) {
         var9 = new StringBuilder();
         var9.append(var7);
         var9.append("implementation 'com.android.support:customtabs:27.0.2'\r\n");
         var5 = var9.toString();
         var4 = new StringBuilder();
         var4.append(var5);
         var4.append("implementation 'com.google.android.gms:play-services-ads:15.0.1'\r\n");
         var5 = var4.toString();
      }

      var7 = var5;
      StringBuilder var6;
      if (var3.o) {
         var6 = new StringBuilder();
         var6.append(var5);
         var6.append("implementation 'com.google.android.gms:play-services-maps:15.0.1'\r\n");
         var7 = var6.toString();
      }

      var6 = new StringBuilder();
      var6.append(var7);
      var6.append("implementation 'com.google.code.gson:gson:2.8.0'\r\n");
      var7 = var6.toString();
      var6 = new StringBuilder();
      var6.append(var7);
      var6.append("implementation 'com.github.bumptech.glide:glide:3.7.0'\r\n");
      var7 = var6.toString();
      var6 = new StringBuilder();
      var6.append(var7);
      var6.append("implementation 'com.squareup.okhttp3:okhttp:3.9.1'\r\n");
      String var8 = var6.toString();
      var4 = new StringBuilder();
      var4.append(var8);
      var4.append("}\r\n");
      var8 = var4.toString();
      var4 = new StringBuilder();
      var4.append(var8);
      var4.append("apply plugin: 'com.google.gms.google-services'\r\n");
      var8 = var4.toString();
      return j(var8);
   }

   public static String a(int var0, String var1, String var2, String var3) {
      String var4 = "";
      if (var0 == 16) {
         var4 = "ArrayList<String> _filePath = new ArrayList<>();\r\nif (_data != null) {\r\nif (_data.getClipData() != null) {\r\nfor (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {\r\nClipData.Item _item = _data.getClipData().getItemAt(_index);\r\n_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));\r\n}\r\n}\r\nelse {\r\n_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));\r\n}\r\n}";
      } else if (var0 == 15) {
         StringBuilder var6 = new StringBuilder();
         var6.append(" String _filePath = _file_");
         var6.append(var1);
         var6.append(".getAbsolutePath();");
         var6.append("\r\n");
         var4 = var6.toString();
      }

      StringBuilder var5 = new StringBuilder();
      var5.append("case REQ_CD_");
      var5.append(var1.toUpperCase());
      var5.append(":");
      var5.append("\r\n");
      var5.append("if (_resultCode == Activity.RESULT_OK) {");
      var5.append("\r\n");
      var5.append(var4);
      var5.append("\r\n");
      var5.append("%s");
      var5.append("\r\n");
      var5.append("}");
      var5.append("\r\n");
      var5.append("else {");
      var5.append("\r\n");
      var5.append("%s");
      var5.append("\r\n");
      var5.append("}");
      var5.append("\r\n");
      var5.append("break;");
      var1 = var5.toString();
      return String.format(var1, var2, var3);
   }

   public static String a(ViewBean var0) {
      String var2 = "";
      String var3 = var0.id;
      int var1 = var0.type;
      String var4;
      StringBuilder var5;
      if (var1 != 0) {
         if (var1 != 8) {
            if (var1 != 11) {
               switch(var1) {
               case 3:
                  var5 = new StringBuilder();
                  var5.append("final Button ");
                  var5.append(var3);
                  var5.append(" = (Button) _v.findViewById(R.id.");
                  var5.append(var0.id);
                  var5.append(");");
                  var4 = var5.toString();
                  break;
               case 4:
                  var5 = new StringBuilder();
                  var5.append("final TextView ");
                  var5.append(var3);
                  var5.append(" = (TextView) _v.findViewById(R.id.");
                  var5.append(var0.id);
                  var5.append(");");
                  var4 = var5.toString();
                  break;
               case 5:
                  var5 = new StringBuilder();
                  var5.append("final EditText ");
                  var5.append(var3);
                  var5.append(" = (EditText) _v.findViewById(R.id.");
                  var5.append(var0.id);
                  var5.append(");");
                  var4 = var5.toString();
                  break;
               case 6:
                  var5 = new StringBuilder();
                  var5.append("final ImageView ");
                  var5.append(var3);
                  var5.append(" = (ImageView) _v.findViewById(R.id.");
                  var5.append(var0.id);
                  var5.append(");");
                  var4 = var5.toString();
                  break;
               default:
                  switch(var1) {
                  case 13:
                     var5 = new StringBuilder();
                     var5.append("final Switch ");
                     var5.append(var3);
                     var5.append(" = (Switch) _v.findViewById(R.id.");
                     var5.append(var0.id);
                     var5.append(");");
                     var4 = var5.toString();
                     break;
                  case 14:
                     var5 = new StringBuilder();
                     var5.append("final SeekBar ");
                     var5.append(var3);
                     var5.append(" = (SeekBar) _v.findViewById(R.id.");
                     var5.append(var0.id);
                     var5.append(");");
                     var4 = var5.toString();
                     break;
                  case 15:
                     var5 = new StringBuilder();
                     var5.append("final CalendarView ");
                     var5.append(var3);
                     var5.append(" = (CalendarView) _v.findViewById(R.id.");
                     var5.append(var0.id);
                     var5.append(");");
                     var4 = var5.toString();
                     break;
                  default:
                     var4 = var2;
                  }
               }
            } else {
               var5 = new StringBuilder();
               var5.append("final CheckBox ");
               var5.append(var3);
               var5.append(" = (CheckBox) _v.findViewById(R.id.");
               var5.append(var0.id);
               var5.append(");");
               var4 = var5.toString();
            }
         } else {
            var5 = new StringBuilder();
            var5.append("final ProgressBar ");
            var5.append(var3);
            var5.append(" = (ProgressBar) _v.findViewById(R.id.");
            var5.append(var0.id);
            var5.append(");");
            var4 = var5.toString();
         }
      } else {
         var5 = new StringBuilder();
         var5.append("final LinearLayout ");
         var5.append(var3);
         var5.append(" = (LinearLayout) _v.findViewById(R.id.");
         var5.append(var0.id);
         var5.append(");");
         var4 = var5.toString();
      }

      return var4;
   }

   public static String a(String var0) {
      byte var2;
      label28: {
         int var1 = var0.hashCode();
         if (var1 != -1908172204) {
            if (var1 != 80811813) {
               if (var1 == 1779003621 && var0.equals("FirebaseDB")) {
                  var2 = 1;
                  break label28;
               }
            } else if (var0.equals("Timer")) {
               var2 = 0;
               break label28;
            }
         } else if (var0.equals("FirebaseStorage")) {
            var2 = 2;
            break label28;
         }

         var2 = -1;
      }

      switch(var2) {
      case 0:
         return "private Timer _timer = new Timer();";
      case 1:
         return "private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();";
      case 2:
         return "private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();";
      default:
         return "";
      }
   }

   public static String a(String var0, int var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("public final int REQ_CD_");
      var2.append(var0.toUpperCase());
      var2.append(" = ");
      var2.append(String.valueOf(var1));
      var2.append(";");
      return var2.toString();
   }

   public static String a(String var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var1);
      var2.append(" = (");
      var2.append(ev.c(var0));
      var2.append(") findViewById(R.id.");
      var2.append(var1);
      var2.append(");");
      String var3 = var2.toString();
      String var5 = var3;
      if (var0.equals("WebView")) {
         var2 = new StringBuilder();
         var2.append(var3);
         var2.append("\r\n");
         var3 = var2.toString();
         var2 = new StringBuilder();
         var2.append(var3);
         var2.append(var1);
         var2.append(".getSettings().setJavaScriptEnabled(true);");
         var2.append("\r\n");
         var2.append(var1);
         var2.append(".getSettings().setSupportZoom(true);");
         var5 = var2.toString();
      }

      var3 = var5;
      if (var0.equals("MapView")) {
         StringBuilder var4 = new StringBuilder();
         var4.append(var5);
         var4.append("\r\n");
         var5 = var4.toString();
         var4 = new StringBuilder();
         var4.append(var5);
         var4.append(var1);
         var4.append(".onCreate(_savedInstanceState);");
         var4.append("\r\n");
         var3 = var4.toString();
      }

      return var3;
   }

   public static String a(String var0, String var1, hg.a var2, String... var3) {
      String var4 = "";
      String var6;
      switch(var2) {
      case a:
         var6 = "private";
         break;
      case b:
         var6 = "protected";
         break;
      case c:
         var6 = "public";
         break;
      default:
         var6 = var4;
      }

      var4 = a(var0, var1, var3);
      StringBuilder var7;
      String var8;
      if (var4.length() <= 0) {
         var7 = new StringBuilder();
         var7.append(var6);
         var7.append(" ");
         var7.append(ev.c(var0));
         var7.append(" ");
         var7.append(var1);
         var7.append(";");
         var8 = var7.toString();
      } else {
         var7 = new StringBuilder();
         var7.append(var6);
         var7.append(" ");
         var7.append(ev.c(var0));
         var7.append(" ");
         var7.append(var1);
         var7.append(" = ");
         var7.append(var4);
         var7.append(";");
         var8 = var7.toString();
      }

      StringBuilder var9;
      if (var0.equals("FirebaseDB")) {
         var9 = new StringBuilder();
         var9.append(var8);
         var9.append("\r\nprivate ChildEventListener _");
         var9.append(var1);
         var9.append("_child_listener;");
         var6 = var9.toString();
      } else if (var0.equals("Gyroscope")) {
         var9 = new StringBuilder();
         var9.append(var8);
         var9.append("\r\nprivate SensorEventListener _");
         var9.append(var1);
         var9.append("_sensor_listener;");
         var6 = var9.toString();
      } else if (var0.equals("FirebaseAuth")) {
         var9 = new StringBuilder();
         var9.append(var8);
         var9.append("\r\nprivate OnCompleteListener<AuthResult> _");
         var9.append(var1);
         var9.append("_create_user_listener;");
         var9.append("\r\n");
         var9.append("private OnCompleteListener<AuthResult> _");
         var9.append(var1);
         var9.append("_sign_in_listener;");
         var9.append("\r\n");
         var9.append("private OnCompleteListener<Void> _");
         var9.append(var1);
         var9.append("_reset_password_listener;");
         var6 = var9.toString();
      } else if (var0.equals("InterstitialAd")) {
         var9 = new StringBuilder();
         var9.append(var8);
         var9.append("\r\nprivate AdListener _");
         var9.append(var1);
         var9.append("_ad_listener;");
         var6 = var9.toString();
      } else if (var0.equals("FirebaseStorage")) {
         var9 = new StringBuilder();
         var9.append(var8);
         var9.append("\r\nprivate OnSuccessListener<UploadTask.TaskSnapshot> _");
         var9.append(var1);
         var9.append("_upload_success_listener;");
         var9.append("\r\n");
         var9.append("private OnSuccessListener<FileDownloadTask.TaskSnapshot> _");
         var9.append(var1);
         var9.append("_download_success_listener;");
         var9.append("\r\n");
         var9.append("private OnSuccessListener _");
         var9.append(var1);
         var9.append("_delete_success_listener;");
         var9.append("\r\n");
         var9.append("private OnProgressListener _");
         var9.append(var1);
         var9.append("_upload_progress_listener;");
         var9.append("\r\n");
         var9.append("private OnProgressListener _");
         var9.append(var1);
         var9.append("_download_progress_listener;");
         var9.append("\r\n");
         var9.append("private OnFailureListener _");
         var9.append(var1);
         var9.append("_failure_listener;");
         var6 = var9.toString();
      } else if (var0.equals("RequestNetwork")) {
         var9 = new StringBuilder();
         var9.append(var8);
         var9.append("\r\nprivate RequestNetwork.RequestListener _");
         var9.append(var1);
         var9.append("_request_listener;");
         var6 = var9.toString();
      } else if (var0.equals("BluetoothConnect")) {
         var9 = new StringBuilder();
         var9.append(var8);
         var9.append("\r\nprivate BluetoothConnect.BluetoothConnectionListener _");
         var9.append(var1);
         var9.append("_bluetooth_connection_listener;");
         var6 = var9.toString();
      } else {
         var6 = var8;
         if (var0.equals("LocationManager")) {
            var9 = new StringBuilder();
            var9.append(var8);
            var9.append("\r\nprivate LocationListener _");
            var9.append(var1);
            var9.append("_location_listener;");
            var6 = var9.toString();
         }
      }

      var8 = var6;
      if (var0.equals("Camera")) {
         var7 = new StringBuilder();
         var7.append(var6);
         var7.append("\r\nprivate File _file_");
         var7.append(var1);
         var7.append(";");
         var8 = var7.toString();
      }

      var6 = var8;
      if (var0.equals("MapView")) {
         StringBuilder var5 = new StringBuilder();
         var5.append(var8);
         var5.append("\r\nprivate GoogleMapController _");
         var5.append(var1);
         var5.append("_controller;");
         var6 = var5.toString();
      }

      return var6;
   }

   public static String a(String var0, String var1, String var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append("_drawer_");
      var3.append(var1);
      var3.append(" = (");
      var3.append(ev.c(var0));
      var3.append(") ");
      var3.append(var2);
      var3.append(".findViewById(R.id.");
      var3.append(var1);
      var3.append(");");
      return var3.toString();
   }

   public static String a(String var0, String var1, ArrayList<ViewBean> var2, String var3) {
      String var4 = b(var0);
      var0 = "";

      StringBuilder var6;
      for(Iterator var7 = var2.iterator(); var7.hasNext(); var0 = var6.toString()) {
         ViewBean var5 = (ViewBean)var7.next();
         var6 = new StringBuilder();
         var6.append(var0);
         var6.append(a(var5));
         var6.append("\r\n");
      }

      StringBuilder var8 = new StringBuilder();
      var8.append("public class ");
      var8.append(var4);
      var8.append(" extends BaseAdapter {");
      var8.append("\r\n");
      var8.append("ArrayList<HashMap<String, Object>> _data;");
      var8.append("\r\n");
      var8.append("public ");
      var8.append(var4);
      var8.append("(ArrayList<HashMap<String, Object>> _arr) {");
      var8.append("\r\n");
      var8.append("_data = _arr;");
      var8.append("\r\n");
      var8.append("}");
      var8.append("\r\n");
      var8.append("\r\n");
      var8.append("@Override");
      var8.append("\r\n");
      var8.append("public int getCount() {");
      var8.append("\r\n");
      var8.append("return _data.size();");
      var8.append("\r\n");
      var8.append("}");
      var8.append("\r\n");
      var8.append("\r\n");
      var8.append("@Override");
      var8.append("\r\n");
      var8.append("public HashMap<String, Object> getItem(int _index) {");
      var8.append("\r\n");
      var8.append("return _data.get(_index);");
      var8.append("\r\n");
      var8.append("}");
      var8.append("\r\n");
      var8.append("\r\n");
      var8.append("@Override");
      var8.append("\r\n");
      var8.append("public long getItemId(int _index) {");
      var8.append("\r\n");
      var8.append("return _index;");
      var8.append("\r\n");
      var8.append("}");
      var8.append("\r\n");
      var8.append("@Override");
      var8.append("\r\n");
      var8.append("public View getView(final int _position, View _view, ViewGroup _viewGroup) {");
      var8.append("\r\n");
      var8.append("LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);");
      var8.append("\r\n");
      var8.append("View _v = _view;");
      var8.append("\r\n");
      var8.append("if (_v == null) {");
      var8.append("\r\n");
      var8.append("_v = _inflater.inflate(R.layout.");
      var8.append(var1);
      var8.append(", null);");
      var8.append("\r\n");
      var8.append("}");
      var8.append("\r\n");
      var8.append("\r\n");
      var8.append(var0);
      var8.append("\r\n");
      var8.append(var3);
      var8.append("\r\n");
      var8.append("\r\n");
      var8.append("return _v;");
      var8.append("\r\n");
      var8.append("}");
      var8.append("\r\n");
      var8.append("}");
      return var8.toString();
   }

   public static String a(String var0, String var1, String... var2) {
      byte var3;
      label80: {
         switch(var0.hashCode()) {
         case -2099895620:
            if (var0.equals("Intent")) {
               var3 = 7;
               break label80;
            }
            break;
         case -1936496017:
            if (var0.equals("ListString")) {
               var3 = 5;
               break label80;
            }
            break;
         case -1908172204:
            if (var0.equals("FirebaseStorage")) {
               var3 = 11;
               break label80;
            }
            break;
         case -1808118735:
            if (var0.equals("String")) {
               var3 = 2;
               break label80;
            }
            break;
         case -1325958191:
            if (var0.equals("double")) {
               var3 = 1;
               break label80;
            }
            break;
         case -596330166:
            if (var0.equals("FilePicker")) {
               var3 = 13;
               break label80;
            }
            break;
         case -113680546:
            if (var0.equals("Calendar")) {
               var3 = 8;
               break label80;
            }
            break;
         case 77116:
            if (var0.equals("Map")) {
               var3 = 3;
               break label80;
            }
            break;
         case 64711720:
            if (var0.equals("boolean")) {
               var3 = 0;
               break label80;
            }
            break;
         case 1779003621:
            if (var0.equals("FirebaseDB")) {
               var3 = 9;
               break label80;
            }
            break;
         case 1799376742:
            if (var0.equals("ObjectAnimator")) {
               var3 = 10;
               break label80;
            }
            break;
         case 1846598225:
            if (var0.equals("ListInt")) {
               var3 = 4;
               break label80;
            }
            break;
         case 1846601662:
            if (var0.equals("ListMap")) {
               var3 = 6;
               break label80;
            }
            break;
         case 2011082565:
            if (var0.equals("Camera")) {
               var3 = 12;
               break label80;
            }
         }

         var3 = -1;
      }

      StringBuilder var4;
      switch(var3) {
      case 0:
         return "false";
      case 1:
         return "0";
      case 2:
         return "\"\"";
      case 3:
         return "new HashMap<>()";
      case 4:
      case 5:
      case 6:
         return "new ArrayList<>()";
      case 7:
         return "new Intent()";
      case 8:
         return "Calendar.getInstance()";
      case 9:
         var1 = "";
         var0 = var1;
         if (var2[0] != null) {
            var0 = var1;
            if (!var2[0].isEmpty()) {
               var0 = var2[0].replace(";", "");
            }
         }

         var4 = new StringBuilder();
         var4.append("_firebase.getReference(\"");
         var4.append(var0);
         var4.append("\")");
         return var4.toString();
      case 10:
         return "new ObjectAnimator()";
      case 11:
         var1 = "";
         var0 = var1;
         if (var2[0] != null) {
            var0 = var1;
            if (!var2[0].isEmpty()) {
               var0 = var2[0].replace(";", "");
            }
         }

         var4 = new StringBuilder();
         var4.append("_firebase_storage.getReference(\"");
         var4.append(var0);
         var4.append("\")");
         return var4.toString();
      case 12:
         return "new Intent(MediaStore.ACTION_IMAGE_CAPTURE)";
      case 13:
         return "new Intent(Intent.ACTION_GET_CONTENT)";
      default:
         return "";
      }
   }

   public static String a(boolean var0, int var1) {
      ArrayList var7 = new ArrayList();
      ArrayList var6 = new ArrayList();
      byte var3 = 0;
      byte var2 = 0;
      String var4;
      String var5;
      String var8;
      StringBuilder var9;
      StringBuilder var10;
      String var11;
      if (var0) {
         if ((var1 & 1) == 1) {
            var7.add("ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED");
            var6.add("Manifest.permission.CALL_PHONE");
         }

         if ((var1 & 16) == 16) {
            var7.add("ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED");
            var6.add("Manifest.permission.CAMERA");
         }

         if ((var1 & 32) == 32) {
            var7.add("ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED");
            var6.add("Manifest.permission.READ_EXTERNAL_STORAGE");
         }

         if ((var1 & 64) == 64) {
            var7.add("ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED");
            var6.add("Manifest.permission.WRITE_EXTERNAL_STORAGE");
         }

         if ((var1 & 128) == 128) {
            var7.add("ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED");
            var6.add("Manifest.permission.RECORD_AUDIO");
         }

         if ((var1 & 1024) == 1024) {
            var7.add("ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED");
            var6.add("Manifest.permission.ACCESS_FINE_LOCATION");
         }

         var4 = "if (";

         for(var1 = 0; var1 < var7.size(); ++var1) {
            var8 = (String)var7.get(var1);
            var5 = var4;
            if (var1 != 0) {
               var10 = new StringBuilder();
               var10.append(var4);
               var10.append("\r\n|| ");
               var5 = var10.toString();
            }

            var9 = new StringBuilder();
            var9.append(var5);
            var9.append(var8);
            var4 = var9.toString();
         }

         var10 = new StringBuilder();
         var10.append(var4);
         var10.append(") {\r\n");
         var11 = var10.toString();
         var4 = "ActivityCompat.requestPermissions(this, new String[] {";

         for(var1 = var2; var1 < var6.size(); ++var1) {
            var8 = (String)var6.get(var1);
            var5 = var4;
            if (var1 != 0) {
               var10 = new StringBuilder();
               var10.append(var4);
               var10.append(", ");
               var5 = var10.toString();
            }

            var9 = new StringBuilder();
            var9.append(var5);
            var9.append(var8);
            var4 = var9.toString();
         }

         var10 = new StringBuilder();
         var10.append(var4);
         var10.append("}, 1000);");
         var5 = var10.toString();
         var9 = new StringBuilder();
         var9.append(var11);
         var9.append(var5);
         var9.append("\r\n");
         var5 = var9.toString();
         var9 = new StringBuilder();
         var9.append(var5);
         var9.append("}\r\n");
         var4 = var9.toString();
         var10 = new StringBuilder();
         var10.append(var4);
         var10.append("else {\r\n");
         var5 = var10.toString();
         var9 = new StringBuilder();
         var9.append(var5);
         var9.append("initializeLogic();\r\n");
         var4 = var9.toString();
         var10 = new StringBuilder();
         var10.append(var4);
         var10.append("}\r\n");
         var4 = var10.toString();
      } else {
         if ((var1 & 1) == 1) {
            var7.add("checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED");
            var6.add("Manifest.permission.CALL_PHONE");
         }

         if ((var1 & 16) == 16) {
            var7.add("checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED");
            var6.add("Manifest.permission.CAMERA");
         }

         if ((var1 & 32) == 32) {
            var7.add("checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED");
            var6.add("Manifest.permission.READ_EXTERNAL_STORAGE");
         }

         if ((var1 & 64) == 64) {
            var7.add("checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED");
            var6.add("Manifest.permission.WRITE_EXTERNAL_STORAGE");
         }

         if ((var1 & 128) == 128) {
            var7.add("checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED");
            var6.add("Manifest.permission.RECORD_AUDIO");
         }

         if ((var1 & 1024) == 1024) {
            var7.add("checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED");
            var6.add("Manifest.permission.ACCESS_FINE_LOCATION");
         }

         var9 = new StringBuilder();
         var9.append("if (Build.VERSION.SDK_INT >= 23) {\r\n");
         var9.append("if (");
         var4 = var9.toString();

         for(var1 = 0; var1 < var7.size(); ++var1) {
            var8 = (String)var7.get(var1);
            var5 = var4;
            if (var1 != 0) {
               var10 = new StringBuilder();
               var10.append(var4);
               var10.append("\r\n|| ");
               var5 = var10.toString();
            }

            var9 = new StringBuilder();
            var9.append(var5);
            var9.append(var8);
            var4 = var9.toString();
         }

         var10 = new StringBuilder();
         var10.append(var4);
         var10.append(") {\r\n");
         var11 = var10.toString();
         var4 = "requestPermissions(new String[] {";

         for(var1 = var3; var1 < var6.size(); ++var1) {
            var8 = (String)var6.get(var1);
            var5 = var4;
            if (var1 != 0) {
               var10 = new StringBuilder();
               var10.append(var4);
               var10.append(", ");
               var5 = var10.toString();
            }

            var9 = new StringBuilder();
            var9.append(var5);
            var9.append(var8);
            var4 = var9.toString();
         }

         var10 = new StringBuilder();
         var10.append(var4);
         var10.append("}, 1000);");
         var4 = var10.toString();
         var10 = new StringBuilder();
         var10.append(var11);
         var10.append(var4);
         var10.append("\r\n");
         var4 = var10.toString();
         var10 = new StringBuilder();
         var10.append(var4);
         var10.append("}\r\n");
         var4 = var10.toString();
         var10 = new StringBuilder();
         var10.append(var4);
         var10.append("else {\r\n");
         var5 = var10.toString();
         var9 = new StringBuilder();
         var9.append(var5);
         var9.append("initializeLogic();\r\n");
         var4 = var9.toString();
         var10 = new StringBuilder();
         var10.append(var4);
         var10.append("}\r\n");
         var5 = var10.toString();
         var9 = new StringBuilder();
         var9.append(var5);
         var9.append("}\r\n");
         var5 = var9.toString();
         var9 = new StringBuilder();
         var9.append(var5);
         var9.append("else {\r\n");
         var5 = var9.toString();
         var9 = new StringBuilder();
         var9.append(var5);
         var9.append("initializeLogic();\r\n");
         var5 = var9.toString();
         var9 = new StringBuilder();
         var9.append(var5);
         var9.append("}\r\n");
         var4 = var9.toString();
      }

      return var4;
   }

   public static void a(StringBuilder var0, int var1) {
      for(int var2 = 0; var2 < var1; ++var2) {
         var0.append('\t');
      }

   }

   public static String b(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append(var0.substring(0, 1).toUpperCase());
      var1.append(var0.substring(1));
      var1.append("Adapter");
      return var1.toString();
   }

   public static String b(String var0, String var1) {
      byte var2;
      label248: {
         switch(var0.hashCode()) {
         case -2117913147:
            if (var0.equals("onStartTrackingTouch")) {
               var2 = 17;
               break label248;
            }
            break;
         case -2107467445:
            if (var0.equals("afterTextChanged")) {
               var2 = 14;
               break label248;
            }
            break;
         case -2067423513:
            if (var0.equals("onSpeechError")) {
               var2 = 49;
               break label248;
            }
            break;
         case -1865337024:
            if (var0.equals("onResponse")) {
               var2 = 46;
               break label248;
            }
            break;
         case -1809154262:
            if (var0.equals("onDataReceived")) {
               var2 = 51;
               break label248;
            }
            break;
         case -1779618840:
            if (var0.equals("onProgressChanged")) {
               var2 = 16;
               break label248;
            }
            break;
         case -1708629179:
            if (var0.equals("onSignInUserComplete")) {
               var2 = 34;
               break label248;
            }
            break;
         case -1401315045:
            if (var0.equals("onDestroy")) {
               var2 = 5;
               break label248;
            }
            break;
         case -1384106084:
            if (var0.equals("onAccuracyChanged")) {
               var2 = 32;
               break label248;
            }
            break;
         case -1358405466:
            if (var0.equals("onMapReady")) {
               var2 = 55;
               break label248;
            }
            break;
         case -1351902487:
            if (var0.equals("onClick")) {
               var2 = 0;
               break label248;
            }
            break;
         case -1340212393:
            if (var0.equals("onPause")) {
               var2 = 7;
               break label248;
            }
            break;
         case -1336895037:
            if (var0.equals("onStart")) {
               var2 = 3;
               break label248;
            }
            break;
         case -1215328199:
            if (var0.equals("onDeleteSuccess")) {
               var2 = 44;
               break label248;
            }
            break;
         case -1153785290:
            if (var0.equals("onAnimationEnd")) {
               var2 = 24;
               break label248;
            }
            break;
         case -1111243300:
            if (var0.equals("onBackPressed")) {
               var2 = 1;
               break label248;
            }
            break;
         case -1012956543:
            if (var0.equals("onStop")) {
               var2 = 4;
               break label248;
            }
            break;
         case -891988931:
            if (var0.equals("onDateChange")) {
               var2 = 19;
               break label248;
            }
            break;
         case -837428873:
            if (var0.equals("onChildChanged")) {
               var2 = 27;
               break label248;
            }
            break;
         case -821066400:
            if (var0.equals("onLocationChanged")) {
               var2 = 57;
               break label248;
            }
            break;
         case -749253875:
            if (var0.equals("onUploadProgress")) {
               var2 = 40;
               break label248;
            }
            break;
         case -732782352:
            if (var0.equals("onConnectionStopped")) {
               var2 = 54;
               break label248;
            }
            break;
         case -719893013:
            if (var0.equals("onConnectionError")) {
               var2 = 53;
               break label248;
            }
            break;
         case -672992515:
            if (var0.equals("onAnimationStart")) {
               var2 = 22;
               break label248;
            }
            break;
         case -609996822:
            if (var0.equals("onConnected")) {
               var2 = 50;
               break label248;
            }
            break;
         case -584901992:
            if (var0.equals("onCheckedChange")) {
               var2 = 8;
               break label248;
            }
            break;
         case -536246231:
            if (var0.equals("onResetPasswordEmailSent")) {
               var2 = 35;
               break label248;
            }
            break;
         case -507667891:
            if (var0.equals("onItemSelected")) {
               var2 = 9;
               break label248;
            }
            break;
         case -505277536:
            if (var0.equals("onPageFinished")) {
               var2 = 21;
               break label248;
            }
            break;
         case -484536541:
            if (var0.equals("onChildRemoved")) {
               var2 = 28;
               break label248;
            }
            break;
         case -376002870:
            if (var0.equals("onErrorResponse")) {
               var2 = 47;
               break label248;
            }
            break;
         case 80616227:
            if (var0.equals("onUploadSuccess")) {
               var2 = 42;
               break label248;
            }
            break;
         case 136827711:
            if (var0.equals("onAnimationCancel")) {
               var2 = 23;
               break label248;
            }
            break;
         case 204442875:
            if (var0.equals("onPostCreate")) {
               var2 = 2;
               break label248;
            }
            break;
         case 249705131:
            if (var0.equals("onFailure")) {
               var2 = 45;
               break label248;
            }
            break;
         case 264008033:
            if (var0.equals("onDataSent")) {
               var2 = 52;
               break label248;
            }
            break;
         case 372583555:
            if (var0.equals("onChildAdded")) {
               var2 = 26;
               break label248;
            }
            break;
         case 378110312:
            if (var0.equals("onTextChanged")) {
               var2 = 15;
               break label248;
            }
            break;
         case 384010806:
            if (var0.equals("onChildMoved")) {
               var2 = 29;
               break label248;
            }
            break;
         case 445802034:
            if (var0.equals("onCancelled")) {
               var2 = 30;
               break label248;
            }
            break;
         case 570020448:
            if (var0.equals("onAnimationRepeat")) {
               var2 = 25;
               break label248;
            }
            break;
         case 601233006:
            if (var0.equals("onAdClosed")) {
               var2 = 39;
               break label248;
            }
            break;
         case 694589214:
            if (var0.equals("onSpeechResult")) {
               var2 = 48;
               break label248;
            }
            break;
         case 805710389:
            if (var0.equals("onItemClicked")) {
               var2 = 11;
               break label248;
            }
            break;
         case 861234439:
            if (var0.equals("onAdLoaded")) {
               var2 = 36;
               break label248;
            }
            break;
         case 863618555:
            if (var0.equals("onSensorChanged")) {
               var2 = 31;
               break label248;
            }
            break;
         case 948174187:
            if (var0.equals("onAdOpened")) {
               var2 = 38;
               break label248;
            }
            break;
         case 956173256:
            if (var0.equals("beforeTextChanged")) {
               var2 = 13;
               break label248;
            }
            break;
         case 1348442836:
            if (var0.equals("onDownloadProgress")) {
               var2 = 41;
               break label248;
            }
            break;
         case 1395209852:
            if (var0.equals("onDownloadSuccess")) {
               var2 = 43;
               break label248;
            }
            break;
         case 1463983852:
            if (var0.equals("onResume")) {
               var2 = 6;
               break label248;
            }
            break;
         case 1586033095:
            if (var0.equals("onStopTrackingTouch")) {
               var2 = 18;
               break label248;
            }
            break;
         case 1633718655:
            if (var0.equals("onCreateUserComplete")) {
               var2 = 33;
               break label248;
            }
            break;
         case 1705537961:
            if (var0.equals("onNothingSelected")) {
               var2 = 10;
               break label248;
            }
            break;
         case 1710477203:
            if (var0.equals("onPageStarted")) {
               var2 = 20;
               break label248;
            }
            break;
         case 1803231982:
            if (var0.equals("onMarkerClicked")) {
               var2 = 56;
               break label248;
            }
            break;
         case 1855724576:
            if (var0.equals("onAdFailedToLoad")) {
               var2 = 37;
               break label248;
            }
            break;
         case 1979400473:
            if (var0.equals("onItemLongClicked")) {
               var2 = 12;
               break label248;
            }
         }

         var2 = -1;
      }

      var0 = var1;
      switch(var2) {
      case 0:
         var0 = String.format("@Override\r\npublic void onClick(View _view) {\r\n%s\r\n}", var1);
         break;
      case 1:
         var0 = String.format("@Override\r\npublic void onBackPressed() {\r\n%s\r\n}", var1);
         break;
      case 2:
         var0 = String.format("@Override\r\nprotected void onPostCreate(Bundle _savedInstanceState) {\r\nsuper.onPostCreate(_savedInstanceState);\r\n%s\r\n}", var1);
         break;
      case 3:
         var0 = String.format("@Override\r\npublic void onStart() {\r\nsuper.onStart();\r\n%s\r\n}", var1);
         break;
      case 4:
         var0 = String.format("@Override\r\npublic void onStop() {\r\nsuper.onStop();\r\n%s\r\n}", var1);
         break;
      case 5:
         var0 = String.format("@Override\r\npublic void onDestroy() {\r\nsuper.onDestroy();\r\n%s\r\n}", var1);
         break;
      case 6:
         var0 = String.format("@Override\r\npublic void onResume() {\r\nsuper.onResume();\r\n%s\r\n}", var1);
         break;
      case 7:
         var0 = String.format("@Override\r\npublic void onPause() {\r\nsuper.onPause();\r\n%s\r\n}", var1);
         break;
      case 8:
         var0 = String.format("@Override\r\npublic void onCheckedChanged(CompoundButton _param1, boolean _param2)  {\r\nfinal boolean _isChecked = _param2;\r\n%s\r\n}", var1);
         break;
      case 9:
         var0 = String.format("@Override\r\npublic void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {\r\nfinal int _position = _param3;\r\n%s\r\n}", var1);
         break;
      case 10:
         var0 = String.format("@Override\r\npublic void onNothingSelected(AdapterView<?> _param1) {\r\n%s\r\n}", var1);
         break;
      case 11:
         var0 = String.format("@Override\r\npublic void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {\r\nfinal int _position = _param3;\r\n%s\r\n}", var1);
         break;
      case 12:
         var0 = String.format("@Override\r\npublic boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {\r\nfinal int _position = _param3;\r\n%s\r\nreturn true;\r\n}", var1);
         break;
      case 13:
         var0 = String.format("@Override\r\npublic void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {\r\n%s\r\n}", var1);
         break;
      case 14:
         var0 = String.format("@Override\r\npublic void afterTextChanged(Editable _param1) {\r\n%s\r\n}", var1);
         break;
      case 15:
         var0 = String.format("@Override\r\npublic void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {\r\nfinal String _charSeq = _param1.toString();\r\n%s\r\n}", var1);
         break;
      case 16:
         var0 = String.format("@Override\r\npublic void onProgressChanged (SeekBar _param1, int _param2, boolean _param3) {\r\nfinal int _progressValue = _param2;\r\n%s\r\n}", var1);
         break;
      case 17:
         var0 = String.format("@Override\r\npublic void onStartTrackingTouch(SeekBar _param1) {\r\n%s\r\n}", var1);
         break;
      case 18:
         var0 = String.format("@Override\r\npublic void onStopTrackingTouch(SeekBar _param2) {\r\n%s\r\n}", var1);
         break;
      case 19:
         var0 = String.format("@Override\r\npublic void onSelectedDayChange(CalendarView _param1, int _param2, int _param3, int _param4) {\r\nfinal int _year = _param2;\r\nfinal int _month = _param3;\r\nfinal int _day = _param4;\r\n%s\r\n}", var1);
         break;
      case 20:
         var0 = String.format("@Override\r\npublic void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {\r\nfinal String _url = _param2;\r\n%s\r\nsuper.onPageStarted(_param1, _param2, _param3);\r\n}", var1);
         break;
      case 21:
         var0 = String.format("@Override\r\npublic void onPageFinished(WebView _param1, String _param2) {\r\nfinal String _url = _param2;\r\n%s\r\nsuper.onPageFinished(_param1, _param2);\r\n}", var1);
         break;
      case 22:
         var0 = String.format("@Override\r\npublic void onAnimationStart(Animator _param1) {\r\n%s\r\n}", var1);
         break;
      case 23:
         var0 = String.format("@Override\r\npublic void onAnimationCancel(Animator _param1) {\r\n%s\r\n}", var1);
         break;
      case 24:
         var0 = String.format("@Override\r\npublic void onAnimationEnd(Animator _param1) {\r\n%s\r\n}", var1);
         break;
      case 25:
         var0 = String.format("@Override\r\npublic void onAnimationRepeat(Animator _param1) {\r\n%s\r\n}", var1);
         break;
      case 26:
         var0 = String.format("@Override\r\npublic void onChildAdded(DataSnapshot _param1, String _param2) {\r\nGenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};\r\nfinal String _childKey = _param1.getKey();\r\nfinal HashMap<String, Object> _childValue = _param1.getValue(_ind);\r\n%s\r\n}", var1);
         break;
      case 27:
         var0 = String.format("@Override\r\npublic void onChildChanged(DataSnapshot _param1, String _param2) {\r\nGenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};\r\nfinal String _childKey = _param1.getKey();\r\nfinal HashMap<String, Object> _childValue = _param1.getValue(_ind);\r\n%s\r\n}", var1);
         break;
      case 28:
         var0 = String.format("@Override\r\npublic void onChildRemoved(DataSnapshot _param1) {\r\nGenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};\r\nfinal String _childKey = _param1.getKey();\r\nfinal HashMap<String, Object> _childValue = _param1.getValue(_ind);\r\n%s\r\n}", var1);
         break;
      case 29:
         var0 = String.format("@Override\r\npublic void onChildMoved(DataSnapshot _param1, String _param2) {\r\n%s\r\n}", var1);
         break;
      case 30:
         var0 = String.format("@Override\r\npublic void onCancelled(DatabaseError _param1) {\r\nfinal int _errorCode = _param1.getCode();\r\nfinal String _errorMessage = _param1.getMessage();\r\n%s\r\n}", var1);
         break;
      case 31:
         var0 = String.format("@Override\r\npublic void onSensorChanged(SensorEvent _param1) {\r\nfloat[] _rotationMatrix = new float[16];\r\nSensorManager.getRotationMatrixFromVector(_rotationMatrix, _param1.values);\r\nfloat[] _remappedRotationMatrix = new float[16];\r\nSensorManager.remapCoordinateSystem(_rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, _remappedRotationMatrix);\r\nfloat[] _orientations = new float[3];\r\nSensorManager.getOrientation(_remappedRotationMatrix, _orientations);\r\nfor(int _i = 0; _i < 3; _i++) {\r\n_orientations[_i] = (float)(Math.toDegrees(_orientations[_i]));\r\n}\r\nfinal double _x = _orientations[0];\r\nfinal double _y = _orientations[1];\r\nfinal double _z = _orientations[2];\r\n%s\r\n}", var1);
         break;
      case 32:
         var0 = String.format("@Override\r\npublic void onAccuracyChanged(Sensor _param1, int _param2) {\r\n%s\r\n}", var1);
         break;
      case 33:
      case 34:
         var0 = String.format("@Override\r\npublic void onComplete(Task<AuthResult> _param1) {\r\nfinal boolean _success = _param1.isSuccessful();\r\nfinal String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : \"\";\r\n%s\r\n}", var1);
         break;
      case 35:
         var0 = String.format("@Override\r\npublic void onComplete(Task<Void> _param1) {\r\nfinal boolean _success = _param1.isSuccessful();\r\n%s\r\n}", var1);
         break;
      case 36:
         var0 = String.format("@Override\r\npublic void onAdLoaded() {\r\n%s\r\n}", var1);
         break;
      case 37:
         var0 = String.format("@Override\r\npublic void onAdFailedToLoad(int _param1) {\r\nfinal int _errorCode = _param1;\r\n%s\r\n}", var1);
         break;
      case 38:
         var0 = String.format("@Override\r\npublic void onAdOpened() {\r\n%s\r\n}", var1);
         break;
      case 39:
         var0 = String.format("@Override\r\npublic void onAdClosed() {\r\n%s\r\n}", var1);
         break;
      case 40:
         var0 = String.format("@Override\r\npublic void onProgress(UploadTask.TaskSnapshot _param1) {\r\ndouble _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();\r\n%s\r\n}", var1);
         break;
      case 41:
         var0 = String.format("@Override\r\npublic void onProgress(FileDownloadTask.TaskSnapshot _param1) {\r\ndouble _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();\r\n%s\r\n}", var1);
         break;
      case 42:
         var0 = String.format("@Override\r\npublic void onSuccess(UploadTask.TaskSnapshot _param1) {\r\nfinal String _downloadUrl = _param1.getDownloadUrl().toString();\r\n%s\r\n}", var1);
         break;
      case 43:
         var0 = String.format("@Override\r\npublic void onSuccess(FileDownloadTask.TaskSnapshot _param1) {\r\nfinal long _totalByteCount = _param1.getTotalByteCount();\r\n%s\r\n}", var1);
         break;
      case 44:
         var0 = String.format("@Override\r\npublic void onSuccess(Object _param1) {\r\n%s\r\n}", var1);
         break;
      case 45:
         var0 = String.format("@Override\r\npublic void onFailure(Exception _param1) {\r\nfinal String _message = _param1.getMessage();\r\n%s\r\n}", var1);
         break;
      case 46:
         var0 = String.format("@Override\r\npublic void onResponse(String _param1, String _param2) {\r\nfinal String _tag = _param1;\r\nfinal String _response = _param2;\r\n%s\r\n}", var1);
         break;
      case 47:
         var0 = String.format("@Override\r\npublic void onErrorResponse(String _param1, String _param2) {\r\nfinal String _tag = _param1;\r\nfinal String _message = _param2;\r\n%s\r\n}", var1);
         break;
      case 48:
         var0 = String.format("@Override\r\npublic void onResults(Bundle _param1) {\r\nfinal ArrayList<String> _results = _param1.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);\r\nfinal String _result = _results.get(0);\r\n%s\r\n}", var1);
         break;
      case 49:
         var0 = String.format("@Override\r\npublic void onError(int _param1) {\r\nfinal String _errorMessage;\r\nswitch (_param1) {\r\ncase SpeechRecognizer.ERROR_AUDIO:\r\n_errorMessage = \"audio error\";\r\nbreak;\r\ncase SpeechRecognizer.ERROR_SPEECH_TIMEOUT:\r\n_errorMessage = \"speech timeout\";\r\nbreak;\r\ncase SpeechRecognizer.ERROR_NO_MATCH:\r\n_errorMessage = \"speech no match\";\r\nbreak;\r\ncase SpeechRecognizer.ERROR_RECOGNIZER_BUSY:\r\n_errorMessage = \"recognizer busy\";\r\nbreak;\r\ncase SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:\r\n_errorMessage = \"recognizer insufficient permissions\";\r\nbreak;\r\ndefault:\r\n_errorMessage = \"recognizer other error\";\r\nbreak;\r\n}\r\n%s\r\n}", var1);
         break;
      case 50:
         var0 = String.format("@Override\r\npublic void onConnected(String _param1, HashMap<String, Object> _param2) {\r\nfinal String _tag = _param1;\r\nfinal HashMap<String, Object> _deviceData = _param2;\r\n%s\r\n}", var1);
         break;
      case 51:
         var0 = String.format("@Override\r\npublic void onDataReceived(String _param1, byte[] _param2, int _param3) {\r\nfinal String _tag = _param1;\r\nfinal String _data = new String(_param2, 0, _param3);\r\n%s\r\n}", var1);
         break;
      case 52:
         var0 = String.format("@Override\r\npublic void onDataSent(String _param1, byte[] _param2) {\r\nfinal String _tag = _param1;\r\nfinal String _data = new String(_param2);\r\n%s\r\n}", var1);
         break;
      case 53:
         var0 = String.format("@Override\r\npublic void onConnectionError(String _param1, String _param2, String _param3) {\r\nfinal String _tag = _param1;\r\nfinal String _connectionState = _param2;\r\nfinal String _errorMessage = _param3;\r\n%s\r\n}", var1);
         break;
      case 54:
         var0 = String.format("@Override\r\npublic void onConnectionStopped(String _param1) {\r\nfinal String _tag = _param1;\r\n%s\r\n}", var1);
      case 55:
         break;
      case 56:
         var0 = String.format("@Override\r\npublic boolean onMarkerClick(Marker _param1) {\r\nfinal String _id = _param1.getTag().toString();\r\n%s\r\nreturn false;\r\n}", var1);
         break;
      case 57:
         var0 = String.format("@Override\r\npublic void onLocationChanged(Location _param1) {\r\nfinal double _lat = _param1.getLatitude();\r\nfinal double _lng = _param1.getLongitude();\r\nfinal double _acc = _param1.getAccuracy();\r\n%s\r\n}", var1);
         break;
      default:
         var0 = "";
      }

      return var0;
   }

   public static String b(String var0, String var1, String var2) {
      byte var3;
      String var4;
      label128: {
         var4 = "";
         switch(var0.hashCode()) {
         case -2054042947:
            if (var0.equals("onClickListener")) {
               var3 = 0;
               break label128;
            }
            break;
         case -2020041772:
            if (var0.equals("sensorEventListener")) {
               var3 = 10;
               break label128;
            }
            break;
         case -1990409668:
            if (var0.equals("onTextChangedListener")) {
               var3 = 5;
               break label128;
            }
            break;
         case -1907134451:
            if (var0.equals("onDeleteSuccessListener")) {
               var3 = 20;
               break label128;
            }
            break;
         case -1362091184:
            if (var0.equals("onDownloadSuccessListener")) {
               var3 = 19;
               break label128;
            }
            break;
         case -1353514613:
            if (var0.equals("recognitionListener")) {
               var3 = 23;
               break label128;
            }
            break;
         case -933396353:
            if (var0.equals("onFailureListener")) {
               var3 = 21;
               break label128;
            }
            break;
         case -924274776:
            if (var0.equals("onDownloadProgressListener")) {
               var3 = 18;
               break label128;
            }
            break;
         case -829278715:
            if (var0.equals("onMapMarkerClickListener")) {
               var3 = 26;
               break label128;
            }
            break;
         case -827956745:
            if (var0.equals("adListener")) {
               var3 = 15;
               break label128;
            }
            break;
         case -803249912:
            if (var0.equals("authCreateUserComplete")) {
               var3 = 12;
               break label128;
            }
            break;
         case -744728252:
            if (var0.equals("webViewClient")) {
               var3 = 11;
               break label128;
            }
            break;
         case -359785373:
            if (var0.equals("requestListener")) {
               var3 = 22;
               break label128;
            }
            break;
         case -332388831:
            if (var0.equals("onItemSelectedListener")) {
               var3 = 2;
               break label128;
            }
            break;
         case -291578101:
            if (var0.equals("onMapReadyCallback")) {
               var3 = 25;
               break label128;
            }
            break;
         case -291013445:
            if (var0.equals("animatorListener")) {
               var3 = 8;
               break label128;
            }
            break;
         case -80069142:
            if (var0.equals("onItemClickListener")) {
               var3 = 3;
               break label128;
            }
            break;
         case 149369550:
            if (var0.equals("authSignInUserComplete")) {
               var3 = 13;
               break label128;
            }
            break;
         case 165600064:
            if (var0.equals("onSeekBarChangeListener")) {
               var3 = 6;
               break label128;
            }
            break;
         case 462657998:
            if (var0.equals("onItemLongClickListener")) {
               var3 = 4;
               break label128;
            }
            break;
         case 670396663:
            if (var0.equals("onUploadSuccessListener")) {
               var3 = 16;
               break label128;
            }
            break;
         case 1118236689:
            if (var0.equals("onDateChangeListener")) {
               var3 = 7;
               break label128;
            }
            break;
         case 1280727296:
            if (var0.equals("bluetoothConnectionListener")) {
               var3 = 24;
               break label128;
            }
            break;
         case 1538933641:
            if (var0.equals("locationListener")) {
               var3 = 27;
               break label128;
            }
            break;
         case 1560580237:
            if (var0.equals("authResetEmailSent")) {
               var3 = 14;
               break label128;
            }
            break;
         case 1842370015:
            if (var0.equals("onCheckChangedListener")) {
               var3 = 1;
               break label128;
            }
            break;
         case 1953306337:
            if (var0.equals("onUploadProgressListener")) {
               var3 = 17;
               break label128;
            }
            break;
         case 2139736498:
            if (var0.equals("childEventListener")) {
               var3 = 9;
               break label128;
            }
         }

         var3 = -1;
      }

      StringBuilder var5;
      switch(var3) {
      case 0:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(".setOnClickListener(new View.OnClickListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("});");
         var0 = String.format(var5.toString(), var2);
         break;
      case 1:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(".setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("});");
         var0 = String.format(var5.toString(), var2);
         break;
      case 2:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(".setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("});");
         var0 = String.format(var5.toString(), var2);
         break;
      case 3:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(".setOnItemClickListener(new AdapterView.OnItemClickListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("});");
         var0 = String.format(var5.toString(), var2);
         break;
      case 4:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(".setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("});");
         var0 = String.format(var5.toString(), var2);
         break;
      case 5:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(".addTextChangedListener(new TextWatcher() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("});");
         var0 = String.format(var5.toString(), var2);
         break;
      case 6:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(".setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("});");
         var0 = String.format(var5.toString(), var2);
         break;
      case 7:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(".setOnDateChangeListener(new CalendarView.OnDateChangeListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("});");
         var0 = String.format(var5.toString(), var2);
         break;
      case 8:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(".addListener(new Animator.AnimatorListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("});");
         var0 = String.format(var5.toString(), var2);
         break;
      case 9:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_child_listener = new ChildEventListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var5.append("\r\n");
         var5.append(var1);
         var5.append(".addChildEventListener(_");
         var5.append(var1);
         var5.append("_child_listener);");
         var0 = String.format(var5.toString(), var2);
         break;
      case 10:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_sensor_listener = new SensorEventListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var5.append("\r\n");
         var5.append(var1);
         var5.append(".registerListener(_");
         var5.append(var1);
         var5.append("_sensor_listener, ");
         var5.append(var1);
         var5.append(".getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);");
         var0 = String.format(var5.toString(), var2);
         break;
      case 11:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(".setWebViewClient(new WebViewClient() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("});");
         var0 = String.format(var5.toString(), var2);
         break;
      case 12:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_create_user_listener = new OnCompleteListener<AuthResult>() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var0 = String.format(var5.toString(), var2);
         break;
      case 13:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_sign_in_listener = new OnCompleteListener<AuthResult>() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var0 = String.format(var5.toString(), var2);
         break;
      case 14:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_reset_password_listener = new OnCompleteListener<Void>() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var0 = String.format(var5.toString(), var2);
         break;
      case 15:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_ad_listener = new AdListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var0 = String.format(var5.toString(), var2);
         break;
      case 16:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_upload_success_listener = new OnSuccessListener<UploadTask.TaskSnapshot>() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var0 = String.format(var5.toString(), var2);
         break;
      case 17:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var0 = String.format(var5.toString(), var2);
         break;
      case 18:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var0 = String.format(var5.toString(), var2);
         break;
      case 19:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var0 = String.format(var5.toString(), var2);
         break;
      case 20:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_delete_success_listener = new OnSuccessListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var0 = String.format(var5.toString(), var2);
         break;
      case 21:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_failure_listener = new OnFailureListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var0 = String.format(var5.toString(), var2);
         break;
      case 22:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_request_listener = new RequestNetwork.RequestListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var0 = String.format(var5.toString(), var2);
         break;
      case 23:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(".setRecognitionListener(new RecognitionListener() {");
         var5.append("\r\n");
         var5.append("@Override");
         var5.append("\r\n");
         var5.append("public void onReadyForSpeech(Bundle _param1) {");
         var5.append("\r\n");
         var5.append("}");
         var5.append("\r\n");
         var5.append("@Override");
         var5.append("\r\n");
         var5.append("public void onBeginningOfSpeech() {");
         var5.append("\r\n");
         var5.append("}");
         var5.append("\r\n");
         var5.append("@Override");
         var5.append("\r\n");
         var5.append("public void onRmsChanged(float _param1) {");
         var5.append("\r\n");
         var5.append("}");
         var5.append("\r\n");
         var5.append("@Override");
         var5.append("\r\n");
         var5.append("public void onBufferReceived(byte[] _param1) {");
         var5.append("\r\n");
         var5.append("}");
         var5.append("\r\n");
         var5.append("@Override");
         var5.append("\r\n");
         var5.append("public void onEndOfSpeech() {");
         var5.append("\r\n");
         var5.append("}");
         var5.append("\r\n");
         var5.append("@Override");
         var5.append("\r\n");
         var5.append("public void onPartialResults(Bundle _param1) {");
         var5.append("\r\n");
         var5.append("}");
         var5.append("\r\n");
         var5.append("@Override");
         var5.append("\r\n");
         var5.append("public void onEvent(int _param1, Bundle _param2) {");
         var5.append("\r\n");
         var5.append("}");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("});");
         var0 = String.format(var5.toString(), var2);
         break;
      case 24:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_bluetooth_connection_listener = new BluetoothConnect.BluetoothConnectionListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("};");
         var0 = String.format(var5.toString(), var2);
         break;
      case 25:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_controller = new GoogleMapController(");
         var5.append(var1);
         var5.append(", new OnMapReadyCallback() {");
         var5.append("\r\n");
         var5.append("@Override");
         var5.append("\r\n");
         var5.append("public void onMapReady(GoogleMap _googleMap) {");
         var5.append("\r\n");
         var5.append("_");
         var5.append(var1);
         var5.append("_controller.setGoogleMap(_googleMap);");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("}");
         var5.append("\r\n");
         var5.append("});");
         var0 = String.format(var5.toString(), var2);
         break;
      case 26:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_controller.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("});");
         var0 = String.format(var5.toString(), var2);
         break;
      case 27:
         var5 = new StringBuilder();
         var5.append("_");
         var5.append(var1);
         var5.append("_location_listener = new LocationListener() {");
         var5.append("\r\n");
         var5.append("%s");
         var5.append("\r\n");
         var5.append("@Override\npublic void onStatusChanged(String provider, int status, Bundle extras) {}\n@Override\npublic void onProviderEnabled(String provider) {}\n@Override\npublic void onProviderDisabled(String provider) {}\n};");
         var0 = String.format(var5.toString(), var2);
         break;
      default:
         var0 = var4;
      }

      return var0;
   }

   public static String b(String var0, String var1, String... var2) {
      byte var3;
      String var4;
      label76: {
         var4 = "";
         switch(var0.hashCode()) {
         case -1965257499:
            if (var0.equals("Gyroscope")) {
               var3 = 3;
               break label76;
            }
            break;
         case -1884914774:
            if (var0.equals("TextToSpeech")) {
               var3 = 8;
               break label76;
            }
            break;
         case -1042830870:
            if (var0.equals("SpeechToText")) {
               var3 = 9;
               break label76;
            }
            break;
         case -1014653761:
            if (var0.equals("RequestNetwork")) {
               var3 = 7;
               break label76;
            }
            break;
         case -596330166:
            if (var0.equals("FilePicker")) {
               var3 = 5;
               break label76;
            }
            break;
         case -294086120:
            if (var0.equals("LocationManager")) {
               var3 = 11;
               break label76;
            }
            break;
         case 225459311:
            if (var0.equals("FirebaseAuth")) {
               var3 = 4;
               break label76;
            }
            break;
         case 1170382393:
            if (var0.equals("Vibrator")) {
               var3 = 1;
               break label76;
            }
            break;
         case 1512362620:
            if (var0.equals("BluetoothConnect")) {
               var3 = 10;
               break label76;
            }
            break;
         case 1616304435:
            if (var0.equals("SharedPreferences")) {
               var3 = 0;
               break label76;
            }
            break;
         case 2011082565:
            if (var0.equals("Camera")) {
               var3 = 6;
               break label76;
            }
            break;
         case 2046749032:
            if (var0.equals("Dialog")) {
               var3 = 2;
               break label76;
            }
         }

         var3 = -1;
      }

      StringBuilder var5;
      StringBuilder var6;
      switch(var3) {
      case 0:
         var4 = "";
         var0 = var4;
         if (var2[0] != null) {
            var0 = var4;
            if (!var2[0].isEmpty()) {
               var0 = var2[0].replace(";", "");
            }
         }

         var6 = new StringBuilder();
         var6.append(var1);
         var6.append(" = getSharedPreferences(\"");
         var6.append(var0);
         var6.append("\", Activity.MODE_PRIVATE);");
         var0 = var6.toString();
         break;
      case 1:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(" = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);");
         var0 = var5.toString();
         break;
      case 2:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(" = new AlertDialog.Builder(this);");
         var0 = var5.toString();
         break;
      case 3:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(" = (SensorManager) getSystemService(Context.SENSOR_SERVICE);");
         var5.append("\r\n");
         var5.append("if (");
         var5.append(var1);
         var5.append(".getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null) {");
         var5.append("\r\n");
         var5.append("SketchwareUtil.showMessage(getApplicationContext(), \"Gyroscope is not supported on this device\");");
         var5.append("\r\n");
         var5.append("}");
         var0 = var5.toString();
         break;
      case 4:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(" = FirebaseAuth.getInstance();");
         var0 = var5.toString();
         break;
      case 5:
         var4 = "*/*";
         var0 = var4;
         if (var2[0] != null) {
            var0 = var4;
            if (var2[0].length() > 0) {
               var0 = var2[0].replace(";", "");
            }
         }

         var6 = new StringBuilder();
         var6.append(var1);
         var6.append(".setType(\"");
         var6.append(var0);
         var6.append("\");");
         var6.append("\r\n");
         var6.append(var1);
         var6.append(".putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);");
         var0 = var6.toString();
         break;
      case 6:
         var5 = new StringBuilder();
         var5.append("_file_");
         var5.append(var1);
         var5.append(" = FileUtil.createNewPictureFile(getApplicationContext());");
         var5.append("\r\n");
         var5.append("Uri _uri_");
         var5.append(var1);
         var5.append(" = null;");
         var5.append("\r\n");
         var5.append("if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {");
         var5.append("\r\n");
         var5.append("_uri_");
         var5.append(var1);
         var5.append("= FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + \".provider\", _file_");
         var5.append(var1);
         var5.append(");");
         var5.append("\r\n");
         var5.append("}");
         var5.append("\r\n");
         var5.append("else {");
         var5.append("\r\n");
         var5.append("_uri_");
         var5.append(var1);
         var5.append(" = Uri.fromFile(_file_");
         var5.append(var1);
         var5.append(");");
         var5.append("\r\n");
         var5.append("}");
         var5.append("\r\n");
         var5.append(var1);
         var5.append(".putExtra(MediaStore.EXTRA_OUTPUT, _uri_");
         var5.append(var1);
         var5.append(");");
         var5.append("\r\n");
         var5.append(var1);
         var5.append(".addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);");
         var0 = var5.toString();
         break;
      case 7:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(" = new RequestNetwork(this);");
         var0 = var5.toString();
         break;
      case 8:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(" = new TextToSpeech(getApplicationContext(), null);");
         var0 = var5.toString();
         break;
      case 9:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(" = SpeechRecognizer.createSpeechRecognizer(this);");
         var0 = var5.toString();
         break;
      case 10:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(" = new BluetoothConnect(this);");
         var0 = var5.toString();
         break;
      case 11:
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append(" = (LocationManager) getSystemService(Context.LOCATION_SERVICE);");
         var0 = var5.toString();
         break;
      default:
         var0 = var4;
      }

      return var0;
   }

   public static String c(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("package ");
      var1.append(var0);
      var1.append(";");
      var1.append("\r\n");
      var1.append("\nimport android.content.Context;\nimport android.util.SparseBooleanArray;\nimport android.util.TypedValue;\nimport android.view.View;\nimport android.widget.ListView;\nimport android.widget.Toast;\n\nimport java.util.ArrayList;\nimport java.util.Iterator;\nimport java.util.Map;\nimport java.util.Random;\n\npublic class SketchwareUtil {\npublic static void showMessage(Context _context, String _s) {\nToast.makeText(_context, _s, Toast.LENGTH_SHORT).show();\n}\n\npublic static int getLocationX(View _v) {\nint _location[] = new int[2];\n_v.getLocationInWindow(_location);\nreturn _location[0];\n}\n\npublic static int getLocationY(View _v) {\nint _location[] = new int[2];\n_v.getLocationInWindow(_location);\nreturn _location[1];\n}\n\npublic static int getRandom(int _min, int _max) {\nRandom random = new Random();\nreturn random.nextInt(_max - _min + 1) + _min;\n}\n\npublic static ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {\nArrayList<Double> _result = new ArrayList<Double>();\nSparseBooleanArray _arr = _list.getCheckedItemPositions();\nfor (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {\nif (_arr.valueAt(_iIdx))\n_result.add((double) _arr.keyAt(_iIdx));\n}\nreturn _result;\n}\n\npublic static float getDip(Context _context, int _input) {\nreturn TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, _context.getResources().getDisplayMetrics());\n}\n\npublic static int getDisplayWidthPixels(Context _context) {\nreturn _context.getResources().getDisplayMetrics().widthPixels;\n}\n\npublic static int getDisplayHeightPixels(Context _context) {\nreturn _context.getResources().getDisplayMetrics().heightPixels;\n}\n\npublic static void getAllKeysFromMap(Map<String, Object> map, ArrayList<String> output) {\nif (output == null) return;\noutput.clear();\n\nif (map == null || map.size() <= 0) return;\n\nIterator itr = map.entrySet().iterator();\nwhile (itr.hasNext()) {\nMap.Entry<String, String> entry = (Map.Entry) itr.next();\noutput.add(entry.getKey());\n}\n}\n}");
      return var1.toString();
   }

   public static String c(String var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("// Top-level build file where you can add configuration options common to all sub-projects/modules.\r\n\r\nbuildscript {\r\nrepositories {\r\njcenter()\r\ngoogle()\r\n}\r\ndependencies {\r\nclasspath 'com.android.tools.build:gradle:");
      var2.append(var0);
      var2.append("'");
      var2.append("\r\n");
      var2.append("classpath 'com.google.gms:google-services:");
      var2.append(var1);
      var2.append("'");
      var2.append("\r\n");
      var2.append("// NOTE: Do not place your application dependencies here; they belong");
      var2.append("\r\n");
      var2.append("// in the individual module build.gradle files");
      var2.append("\r\n");
      var2.append("}");
      var2.append("\r\n");
      var2.append("}");
      var2.append("\r\n");
      var2.append("\r\n");
      var2.append("allprojects {");
      var2.append("\r\n");
      var2.append("repositories {");
      var2.append("\r\n");
      var2.append("jcenter()");
      var2.append("\r\n");
      var2.append("google()");
      var2.append("\r\n");
      var2.append("}");
      var2.append("\r\n");
      var2.append("}");
      var2.append("\r\n");
      var0 = var2.toString();
      return j(var0);
   }

   public static String c(String var0, String var1, String var2) {
      byte var3;
      String var4;
      label52: {
         var4 = "";
         switch(var0.hashCode()) {
         case -1401315045:
            if (var0.equals("onDestroy")) {
               var3 = 5;
               break label52;
            }
            break;
         case -1340212393:
            if (var0.equals("onPause")) {
               var3 = 3;
               break label52;
            }
            break;
         case -1336895037:
            if (var0.equals("onStart")) {
               var3 = 1;
               break label52;
            }
            break;
         case -1111243300:
            if (var0.equals("onBackPressed")) {
               var3 = 0;
               break label52;
            }
            break;
         case -1012956543:
            if (var0.equals("onStop")) {
               var3 = 4;
               break label52;
            }
            break;
         case 1463983852:
            if (var0.equals("onResume")) {
               var3 = 2;
               break label52;
            }
         }

         var3 = -1;
      }

      StringBuilder var5;
      switch(var3) {
      case 0:
         var0 = var4;
         if (var1.equals("DrawerLayout")) {
            var5 = new StringBuilder();
            var5.append("if (");
            var5.append(var2);
            var5.append(".isDrawerOpen(GravityCompat.START)) {");
            var5.append("\r\n");
            var5.append(var2);
            var5.append(".closeDrawer(GravityCompat.START);");
            var5.append("\r\n");
            var5.append("}");
            var5.append("\r\n");
            var5.append("else {");
            var5.append("\r\n");
            var5.append("super.onBackPressed();");
            var5.append("\r\n");
            var5.append("}");
            var0 = var5.toString();
         }
         break;
      case 1:
         var0 = var4;
         if (var1.equals("MapView")) {
            var5 = new StringBuilder();
            var5.append(var2);
            var5.append(".onStart();");
            var0 = var5.toString();
         }
         break;
      case 2:
         var0 = var4;
         if (var1.equals("MapView")) {
            var5 = new StringBuilder();
            var5.append(var2);
            var5.append(".onResume();");
            var0 = var5.toString();
         }
         break;
      case 3:
         var0 = var4;
         if (var1.equals("MapView")) {
            var5 = new StringBuilder();
            var5.append(var2);
            var5.append(".onPause();");
            var0 = var5.toString();
         }
         break;
      case 4:
         var0 = var4;
         if (var1.equals("MapView")) {
            var5 = new StringBuilder();
            var5.append(var2);
            var5.append(".onStop();");
            var0 = var5.toString();
         }
         break;
      case 5:
         var0 = var4;
         if (var1.equals("MapView")) {
            var5 = new StringBuilder();
            var5.append(var2);
            var5.append(".onDestroy();");
            var0 = var5.toString();
         }
         break;
      default:
         var0 = var4;
      }

      return var0;
   }

   public static String d(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("package ");
      var1.append(var0);
      var1.append(";\n\nimport android.content.ContentResolver;\nimport android.content.ContentUris;\nimport android.content.Context;\nimport android.database.Cursor;\nimport android.graphics.Bitmap;\nimport android.graphics.BitmapFactory;\nimport android.graphics.Canvas;\nimport android.graphics.ColorFilter;\nimport android.graphics.ColorMatrix;\nimport android.graphics.ColorMatrixColorFilter;\nimport android.graphics.LightingColorFilter;\nimport android.graphics.Matrix;\nimport android.graphics.Paint;\nimport android.graphics.PorterDuff;\nimport android.graphics.PorterDuffXfermode;\nimport android.graphics.Rect;\nimport android.graphics.RectF;\nimport android.media.ExifInterface;\nimport android.net.Uri;\nimport android.os.Environment;\nimport android.provider.DocumentsContract;\nimport android.provider.MediaStore;\nimport android.text.TextUtils;\n\nimport java.io.File;\nimport java.io.FileInputStream;\nimport java.io.FileOutputStream;\nimport java.io.FileReader;\nimport java.io.FileWriter;\nimport java.io.IOException;\nimport java.net.URLDecoder;\nimport java.text.SimpleDateFormat;\nimport java.util.ArrayList;\nimport java.util.Date;\n\npublic class FileUtil {\n\nprivate static void createNewFile(String path) {\nint lastSep = path.lastIndexOf(File.separator);\nif (lastSep > 0) {\nString dirPath = path.substring(0, lastSep);\nmakeDir(dirPath);\n}\n\nFile file = new File(path);\n\ntry {\nif (!file.exists())\nfile.createNewFile();\n} catch (IOException e) {\ne.printStackTrace();\n}\n}\n\npublic static String readFile(String path) {\ncreateNewFile(path);\n\nStringBuilder sb = new StringBuilder();\nFileReader fr = null;\ntry {\nfr = new FileReader(new File(path));\n\nchar[] buff = new char[1024];\nint length = 0;\n\nwhile ((length = fr.read(buff)) > 0) {\nsb.append(new String(buff, 0, length));\n}\n} catch (IOException e) {\ne.printStackTrace();\n} finally {\nif (fr != null) {\ntry {\nfr.close();\n} catch (Exception e) {\ne.printStackTrace();\n}\n}\n}\n\nreturn sb.toString();\n}\n\npublic static void writeFile(String path, String str) {\ncreateNewFile(path);\nFileWriter fileWriter = null;\n\ntry {\nfileWriter = new FileWriter(new File(path), false);\nfileWriter.write(str);\nfileWriter.flush();\n} catch (IOException e) {\ne.printStackTrace();\n} finally {\ntry {\nif (fileWriter != null)\nfileWriter.close();\n} catch (IOException e) {\ne.printStackTrace();\n}\n}\n}\n\npublic static void copyFile(String sourcePath, String destPath) {\nif (!isExistFile(sourcePath)) return;\ncreateNewFile(destPath);\n\nFileInputStream fis = null;\nFileOutputStream fos = null;\n\ntry {\nfis = new FileInputStream(sourcePath);\nfos = new FileOutputStream(destPath, false);\n\nbyte[] buff = new byte[1024];\nint length = 0;\n\nwhile ((length = fis.read(buff)) > 0) {\nfos.write(buff, 0, length);\n}\n} catch (IOException e) {\ne.printStackTrace();\n} finally {\nif (fis != null) {\ntry {\nfis.close();\n} catch (IOException e) {\ne.printStackTrace();\n}\n}\nif (fos != null) {\ntry {\nfos.close();\n} catch (IOException e) {\ne.printStackTrace();\n}\n}\n}\n}\n\npublic static void moveFile(String sourcePath, String destPath) {\ncopyFile(sourcePath, destPath);\ndeleteFile(sourcePath);\n}\n\npublic static void deleteFile(String path) {\nFile file = new File(path);\n\nif (!file.exists()) return;\n\nif (file.isFile()) {\nfile.delete();\nreturn;\n}\n\nFile[] fileArr = file.listFiles();\n\nif (fileArr != null) {\nfor (File subFile : fileArr) {\nif (subFile.isDirectory()) {\ndeleteFile(subFile.getAbsolutePath());\n}\n\nif (subFile.isFile()) {\nsubFile.delete();\n}\n}\n}\n\nfile.delete();\n}\n\npublic static boolean isExistFile(String path) {\nFile file = new File(path);\nreturn file.exists();\n}\n\npublic static void makeDir(String path) {\nif (!isExistFile(path)) {\nFile file = new File(path);\nfile.mkdirs();\n}\n}\n\npublic static void listDir(String path, ArrayList<String> list) {\nFile dir = new File(path);\nif (!dir.exists() || dir.isFile()) return;\n\nFile[] listFiles = dir.listFiles();\nif (listFiles == null || listFiles.length <= 0) return;\n\nif (list == null) return;\nlist.clear();\nfor (File file : listFiles) {\nlist.add(file.getAbsolutePath());\n}\n}\n\npublic static boolean isDirectory(String path) {\nif (!isExistFile(path)) return false;\nreturn new File(path).isDirectory();\n}\n\npublic static boolean isFile(String path) {\nif (!isExistFile(path)) return false;\nreturn new File(path).isFile();\n}\n\npublic static long getFileLength(String path) {\nif (!isExistFile(path)) return 0;\nreturn new File(path).length();\n}\n\npublic static String getExternalStorageDir() {\nreturn Environment.getExternalStorageDirectory().getAbsolutePath();\n}\n\npublic static String getPackageDataDir(Context context) {\nreturn context.getExternalFilesDir(null).getAbsolutePath();\n}\n\npublic static String getPublicDir(String type) {\nreturn Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();\n}\n\npublic static String convertUriToFilePath(final Context context, final Uri uri) {\nString path = null;\nif (DocumentsContract.isDocumentUri(context, uri)) {\nif (isExternalStorageDocument(uri)) {\nfinal String docId = DocumentsContract.getDocumentId(uri);\nfinal String[] split = docId.split(\":\");\nfinal String type = split[0];\n\nif (\"primary\".equalsIgnoreCase(type)) {\npath = Environment.getExternalStorageDirectory() + \"/\" + split[1];\n}\n} else if (isDownloadsDocument(uri)) {\nfinal String id = DocumentsContract.getDocumentId(uri);\n\nif (!TextUtils.isEmpty(id)) {\nif (id.startsWith(\"raw:\")) {\nreturn id.replaceFirst(\"raw:\", \"\");\n}\n}\n\nfinal Uri contentUri = ContentUris\n.withAppendedId(Uri.parse(\"content://downloads/public_downloads\"), Long.valueOf(id));\n\npath = getDataColumn(context, contentUri, null, null);\n} else if (isMediaDocument(uri)) {\nfinal String docId = DocumentsContract.getDocumentId(uri);\nfinal String[] split = docId.split(\":\");\nfinal String type = split[0];\n\nUri contentUri = null;\nif (\"image\".equals(type)) {\ncontentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;\n} else if (\"video\".equals(type)) {\ncontentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;\n} else if (\"audio\".equals(type)) {\ncontentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;\n}\n\nfinal String selection = MediaStore.Audio.Media._ID + \"=?\";\nfinal String[] selectionArgs = new String[]{\nsplit[1]\n};\n\npath = getDataColumn(context, contentUri, selection, selectionArgs);\n}\n} else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {\npath = getDataColumn(context, uri, null, null);\n} else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {\npath = uri.getPath();\n}\n\nif (path != null) {\ntry {\nreturn URLDecoder.decode(path, \"UTF-8\");\n}catch(Exception e){\nreturn null;\n}\n}\nreturn null;\n}\n\nprivate static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {\nCursor cursor = null;\n\nfinal String column = MediaStore.Images.Media.DATA;\nfinal String[] projection = {\ncolumn\n};\n\ntry {\ncursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);\nif (cursor != null && cursor.moveToFirst()) {\nfinal int column_index = cursor.getColumnIndexOrThrow(column);\nreturn cursor.getString(column_index);\n}\n} catch (Exception e) {\n\n} finally {\nif (cursor != null) {\ncursor.close();\n}\n}\nreturn null;\n}\n\n\nprivate static boolean isExternalStorageDocument(Uri uri) {\nreturn \"com.android.externalstorage.documents\".equals(uri.getAuthority());\n}\n\nprivate static boolean isDownloadsDocument(Uri uri) {\nreturn \"com.android.providers.downloads.documents\".equals(uri.getAuthority());\n}\n\nprivate static boolean isMediaDocument(Uri uri) {\nreturn \"com.android.providers.media.documents\".equals(uri.getAuthority());\n}\n\nprivate static void saveBitmap(Bitmap bitmap, String destPath) {\nFileOutputStream out = null;\nFileUtil.createNewFile(destPath);\ntry {\nout = new FileOutputStream(new File(destPath));\nbitmap.compress(Bitmap.CompressFormat.PNG, 100, out);\n} catch (Exception e) {\ne.printStackTrace();\n} finally {\ntry {\nif (out != null) {\nout.close();\n}\n} catch (IOException e) {\ne.printStackTrace();\n}\n}\n}\n\npublic static Bitmap getScaledBitmap(String path, int max) {\nBitmap src = BitmapFactory.decodeFile(path);\n\nint width = src.getWidth();\nint height = src.getHeight();\nfloat rate = 0.0f;\n\nif (width > height) {\nrate = max / (float) width;\nheight = (int) (height * rate);\nwidth = max;\n} else {\nrate = max / (float) height;\nwidth = (int) (width * rate);\nheight = max;\n}\n\nreturn Bitmap.createScaledBitmap(src, width, height, true);\n}\n\npublic static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {\nfinal int width = options.outWidth;\nfinal int height = options.outHeight;\nint inSampleSize = 1;\n\nif (height > reqHeight || width > reqWidth) {\nfinal int halfHeight = height / 2;\nfinal int halfWidth = width / 2;\n\nwhile ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {\ninSampleSize *= 2;\n}\n}\n\nreturn inSampleSize;\n}\n\npublic static Bitmap decodeSampleBitmapFromPath(String path, int reqWidth, int reqHeight) {\nfinal BitmapFactory.Options options = new BitmapFactory.Options();\noptions.inJustDecodeBounds = true;\nBitmapFactory.decodeFile(path, options);\n\noptions.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);\n\noptions.inJustDecodeBounds = false;\nreturn BitmapFactory.decodeFile(path, options);\n}\n\npublic static void resizeBitmapFileRetainRatio(String fromPath, String destPath, int max) {\nif (!isExistFile(fromPath)) return;\nBitmap bitmap = getScaledBitmap(fromPath, max);\nsaveBitmap(bitmap, destPath);\n}\n\npublic static void resizeBitmapFileToSquare(String fromPath, String destPath, int max) {\nif (!isExistFile(fromPath)) return;\nBitmap src = BitmapFactory.decodeFile(fromPath);\nBitmap bitmap = Bitmap.createScaledBitmap(src, max, max, true);\nsaveBitmap(bitmap, destPath);\n}\n\npublic static void resizeBitmapFileToCircle(String fromPath, String destPath) {\nif (!isExistFile(fromPath)) return;\nBitmap src = BitmapFactory.decodeFile(fromPath);\nBitmap bitmap = Bitmap.createBitmap(src.getWidth(),\nsrc.getHeight(), Bitmap.Config.ARGB_8888);\nCanvas canvas = new Canvas(bitmap);\n\nfinal int color = 0xff424242;\nfinal Paint paint = new Paint();\nfinal Rect rect = new Rect(0, 0, src.getWidth(), src.getHeight());\n\npaint.setAntiAlias(true);\ncanvas.drawARGB(0, 0, 0, 0);\npaint.setColor(color);\ncanvas.drawCircle(src.getWidth() / 2, src.getHeight() / 2,\nsrc.getWidth() / 2, paint);\npaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));\ncanvas.drawBitmap(src, rect, rect, paint);\n\nsaveBitmap(bitmap, destPath);\n}\n\npublic static void resizeBitmapFileWithRoundedBorder(String fromPath, String destPath, int pixels) {\nif (!isExistFile(fromPath)) return;\nBitmap src = BitmapFactory.decodeFile(fromPath);\nBitmap bitmap = Bitmap.createBitmap(src.getWidth(), src\n.getHeight(), Bitmap.Config.ARGB_8888);\nCanvas canvas = new Canvas(bitmap);\n\nfinal int color = 0xff424242;\nfinal Paint paint = new Paint();\nfinal Rect rect = new Rect(0, 0, src.getWidth(), src.getHeight());\nfinal RectF rectF = new RectF(rect);\nfinal float roundPx = pixels;\n\npaint.setAntiAlias(true);\ncanvas.drawARGB(0, 0, 0, 0);\npaint.setColor(color);\ncanvas.drawRoundRect(rectF, roundPx, roundPx, paint);\n\npaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));\ncanvas.drawBitmap(src, rect, rect, paint);\n\nsaveBitmap(bitmap, destPath);\n}\n\npublic static void cropBitmapFileFromCenter(String fromPath, String destPath, int w, int h) {\nif (!isExistFile(fromPath)) return;\nBitmap src = BitmapFactory.decodeFile(fromPath);\n\nint width = src.getWidth();\nint height = src.getHeight();\n\nif (width < w && height < h)\nreturn;\n\nint x = 0;\nint y = 0;\n\nif (width > w)\nx = (width - w) / 2;\n\nif (height > h)\ny = (height - h) / 2;\n\nint cw = w;\nint ch = h;\n\nif (w > width)\ncw = width;\n\nif (h > height)\nch = height;\n\nBitmap bitmap = Bitmap.createBitmap(src, x, y, cw, ch);\nsaveBitmap(bitmap, destPath);\n}\n\npublic static void rotateBitmapFile(String fromPath, String destPath, float angle) {\nif (!isExistFile(fromPath)) return;\nBitmap src = BitmapFactory.decodeFile(fromPath);\nMatrix matrix = new Matrix();\nmatrix.postRotate(angle);\nBitmap bitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);\nsaveBitmap(bitmap, destPath);\n}\n\npublic static void scaleBitmapFile(String fromPath, String destPath, float x, float y) {\nif (!isExistFile(fromPath)) return;\nBitmap src = BitmapFactory.decodeFile(fromPath);\nMatrix matrix = new Matrix();\nmatrix.postScale(x, y);\n\nint w = src.getWidth();\nint h = src.getHeight();\n\nBitmap bitmap = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);\nsaveBitmap(bitmap, destPath);\n}\n\npublic static void skewBitmapFile(String fromPath, String destPath, float x, float y) {\nif (!isExistFile(fromPath)) return;\nBitmap src = BitmapFactory.decodeFile(fromPath);\nMatrix matrix = new Matrix();\nmatrix.postSkew(x, y);\n\nint w = src.getWidth();\nint h = src.getHeight();\n\nBitmap bitmap = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);\nsaveBitmap(bitmap, destPath);\n}\n\npublic static void setBitmapFileColorFilter(String fromPath, String destPath, int color) {\nif (!isExistFile(fromPath)) return;\nBitmap src = BitmapFactory.decodeFile(fromPath);\nBitmap bitmap = Bitmap.createBitmap(src, 0, 0,\nsrc.getWidth() - 1, src.getHeight() - 1);\nPaint p = new Paint();\nColorFilter filter = new LightingColorFilter(color, 1);\np.setColorFilter(filter);\nCanvas canvas = new Canvas(bitmap);\ncanvas.drawBitmap(bitmap, 0, 0, p);\nsaveBitmap(bitmap, destPath);\n}\n\npublic static void setBitmapFileBrightness(String fromPath, String destPath, float brightness) {\nif (!isExistFile(fromPath)) return;\nBitmap src = BitmapFactory.decodeFile(fromPath);\nColorMatrix cm = new ColorMatrix(new float[]\n{\n1, 0, 0, 0, brightness,\n0, 1, 0, 0, brightness,\n0, 0, 1, 0, brightness,\n0, 0, 0, 1, 0\n});\n\nBitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());\nCanvas canvas = new Canvas(bitmap);\nPaint paint = new Paint();\npaint.setColorFilter(new ColorMatrixColorFilter(cm));\ncanvas.drawBitmap(src, 0, 0, paint);\nsaveBitmap(bitmap, destPath);\n}\n\npublic static void setBitmapFileContrast(String fromPath, String destPath, float contrast) {\nif (!isExistFile(fromPath)) return;\nBitmap src = BitmapFactory.decodeFile(fromPath);\nColorMatrix cm = new ColorMatrix(new float[]\n{\ncontrast, 0, 0, 0, 0,\n0, contrast, 0, 0, 0,\n0, 0, contrast, 0, 0,\n0, 0, 0, 1, 0\n});\n\nBitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());\nCanvas canvas = new Canvas(bitmap);\nPaint paint = new Paint();\npaint.setColorFilter(new ColorMatrixColorFilter(cm));\ncanvas.drawBitmap(src, 0, 0, paint);\n\nsaveBitmap(bitmap, destPath);\n}\n\npublic static int getJpegRotate(String filePath) {\nint rotate = 0;\ntry {\nExifInterface exif = new ExifInterface(filePath);\nint iOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);\n\nswitch (iOrientation) {\ncase ExifInterface.ORIENTATION_ROTATE_90:\nrotate = 90;\nbreak;\ncase ExifInterface.ORIENTATION_ROTATE_180:\nrotate = 180;\nbreak;\ncase ExifInterface.ORIENTATION_ROTATE_270:\nrotate = 270;\nbreak;\ndefault:\nrotate = 0;\nbreak;\n}\n}\ncatch (IOException e) {\nreturn 0;\n}\n\nreturn rotate;\n}\npublic static File createNewPictureFile(Context context) {\nSimpleDateFormat date = new SimpleDateFormat(\"yyyyMMdd_HHmmss\");\nString fileName = date.format(new Date()) + \".jpg\";\nFile file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + fileName);\nreturn file;\n}\n}");
      return var1.toString();
   }

   public static String d(String var0, String var1, String var2) {
      StringBuilder var6 = new StringBuilder();
      var6.append("private void _");
      var6.append(var0);
      var6.append(" (");
      var0 = var6.toString();
      ArrayList var11 = kx.d(var1);
      int var3 = 0;

      boolean var5;
      StringBuilder var10;
      for(boolean var4 = true; var3 < var11.size(); var4 = var5) {
         String var7 = (String)var11.get(var3);
         var1 = var0;
         var5 = var4;
         if (var7.charAt(0) == '%') {
            label51: {
               StringBuilder var9;
               if (var7.charAt(1) == 'b') {
                  var1 = var0;
                  if (!var4) {
                     var10 = new StringBuilder();
                     var10.append(var0);
                     var10.append(", ");
                     var1 = var10.toString();
                  }

                  var9 = new StringBuilder();
                  var9.append(var1);
                  var9.append("final boolean _");
                  var9.append(var7.substring(3));
                  var0 = var9.toString();
               } else if (var7.charAt(1) == 'd') {
                  var1 = var0;
                  if (!var4) {
                     var10 = new StringBuilder();
                     var10.append(var0);
                     var10.append(", ");
                     var1 = var10.toString();
                  }

                  var9 = new StringBuilder();
                  var9.append(var1);
                  var9.append("final double _");
                  var9.append(var7.substring(3));
                  var0 = var9.toString();
               } else if (var7.charAt(1) == 's') {
                  var1 = var0;
                  if (!var4) {
                     var10 = new StringBuilder();
                     var10.append(var0);
                     var10.append(", ");
                     var1 = var10.toString();
                  }

                  var9 = new StringBuilder();
                  var9.append(var1);
                  var9.append("final String _");
                  var9.append(var7.substring(3));
                  var0 = var9.toString();
               } else {
                  var1 = var0;
                  var5 = var4;
                  if (var7.charAt(1) != 'm') {
                     break label51;
                  }

                  var1 = var0;
                  if (!var4) {
                     var10 = new StringBuilder();
                     var10.append(var0);
                     var10.append(", ");
                     var1 = var10.toString();
                  }

                  var0 = var7.substring(3, var7.lastIndexOf("."));
                  var7 = var7.substring(var7.lastIndexOf(".") + 1);
                  StringBuilder var8 = new StringBuilder();
                  var8.append(var1);
                  var8.append("final ");
                  var8.append(ev.c(ev.a(var0)));
                  var8.append(" _");
                  var8.append(var7);
                  var0 = var8.toString();
               }

               var5 = false;
               var1 = var0;
            }
         }

         ++var3;
         var0 = var1;
      }

      var10 = new StringBuilder();
      var10.append(var0);
      var10.append(") {\r\n");
      var0 = var10.toString();
      var10 = new StringBuilder();
      var10.append(var0);
      var10.append(var2);
      var10.append("\r\n");
      var0 = var10.toString();
      var10 = new StringBuilder();
      var10.append(var0);
      var10.append("}\r\n");
      return var10.toString();
   }

   public static String e(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("package ");
      var1.append(var0);
      var1.append(";");
      var1.append("\r\n");
      var1.append("\nimport android.app.Activity;\n\nimport java.util.HashMap;\n\npublic class RequestNetwork {\nprivate HashMap<String, Object> params = new HashMap<>();\nprivate HashMap<String, Object> headers = new HashMap<>();\n\nprivate Activity activity;\n\nprivate int requestType = 0;\n\npublic RequestNetwork(Activity activity) {\nthis.activity = activity;\n}\n\npublic void setHeaders(HashMap<String, Object> headers) {\nthis.headers = headers;\n}\n\npublic void setParams(HashMap<String, Object> params, int requestType) {\nthis.params = params;\nthis.requestType = requestType;\n}\n\npublic HashMap<String, Object> getParams() {\nreturn params;\n}\n\npublic HashMap<String, Object> getHeaders() {\nreturn headers;\n}\n\npublic Activity getActivity() {\nreturn activity;\n}\n\npublic int getRequestType() {\nreturn requestType;\n}\n\npublic void startRequestNetwork(String method, String url, String tag, RequestListener requestListener) {\nRequestNetworkController.getInstance().execute(this, method, url, tag, requestListener);\n}\n\npublic interface RequestListener {\npublic void onResponse(String tag, String response);\npublic void onErrorResponse(String tag, String message);\n}\n}\n");
      return var1.toString();
   }

   public static String f(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("package ");
      var1.append(var0);
      var1.append(";\n\nimport com.google.gson.Gson;\n\nimport java.io.IOException;\nimport java.security.cert.CertificateException;\nimport java.util.HashMap;\nimport java.util.concurrent.TimeUnit;\n\nimport javax.net.ssl.HostnameVerifier;\nimport javax.net.ssl.SSLContext;\nimport javax.net.ssl.SSLSession;\nimport javax.net.ssl.SSLSocketFactory;\nimport javax.net.ssl.TrustManager;\nimport javax.net.ssl.X509TrustManager;\n\nimport okhttp3.Call;\nimport okhttp3.Callback;\nimport okhttp3.FormBody;\nimport okhttp3.Headers;\nimport okhttp3.HttpUrl;\nimport okhttp3.OkHttpClient;\nimport okhttp3.Request;\nimport okhttp3.RequestBody;\nimport okhttp3.Response;\n\npublic class RequestNetworkController {\npublic static final String GET      = \"GET\";\npublic static final String POST     = \"POST\";\npublic static final String PUT      = \"PUT\";\npublic static final String DELETE   = \"DELETE\";\n\npublic static final int REQUEST_PARAM = 0;\npublic static final int REQUEST_BODY  = 1;\n\nprivate static final int SOCKET_TIMEOUT = 15000;\nprivate static final int READ_TIMEOUT   = 25000;\n\nprotected OkHttpClient client;\n\nprivate static RequestNetworkController mInstance;\n\npublic static synchronized RequestNetworkController getInstance() {\nif(mInstance == null) {\nmInstance = new RequestNetworkController();\n}\nreturn mInstance;\n}\n\nprivate OkHttpClient getClient() {\nif (client == null) {\nOkHttpClient.Builder builder = new OkHttpClient.Builder();\n\ntry {\nfinal TrustManager[] trustAllCerts = new TrustManager[]{\nnew X509TrustManager() {\n@Override\npublic void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)\nthrows CertificateException {\n}\n\n@Override\npublic void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)\nthrows CertificateException {\n}\n\n@Override\npublic java.security.cert.X509Certificate[] getAcceptedIssuers() {\nreturn new java.security.cert.X509Certificate[]{};\n}\n}\n};\n\nfinal SSLContext sslContext = SSLContext.getInstance(\"TLS\");\nsslContext.init(null, trustAllCerts, new java.security.SecureRandom());\nfinal SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();\nbuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);\nbuilder.connectTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);\nbuilder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);\nbuilder.writeTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);\nbuilder.hostnameVerifier(new HostnameVerifier() {\n@Override\npublic boolean verify(String hostname, SSLSession session) {\nreturn true;\n}\n});\n} catch (Exception e) {\n}\n\nclient = builder.build();\n}\n\nreturn client;\n}\n\npublic void execute(final RequestNetwork requestNetwork, String method, String url, final String tag, final RequestNetwork.RequestListener requestListener) {\nRequest.Builder reqBuilder = new Request.Builder();\nHeaders.Builder headerBuilder = new Headers.Builder();\n\nif(requestNetwork.getHeaders().size() > 0) {\nHashMap<String, Object> headers = requestNetwork.getHeaders();\n\nfor(HashMap.Entry<String, Object> header : headers.entrySet()) {\nheaderBuilder.add(header.getKey(), String.valueOf(header.getValue()));\n}\n}\n\ntry {\nif (requestNetwork.getRequestType() == REQUEST_PARAM) {\nif (method.equals(GET)) {\nHttpUrl.Builder httpBuilder;\n\ntry {\nhttpBuilder = HttpUrl.parse(url).newBuilder();\n} catch (NullPointerException ne) {\nthrow new NullPointerException(\"unexpected url: \" + url);\n}\n\nif (requestNetwork.getParams().size() > 0) {\nHashMap<String, Object> params = requestNetwork.getParams();\n\nfor (HashMap.Entry<String, Object> param : params.entrySet()) {\nhttpBuilder.addQueryParameter(param.getKey(), String.valueOf(param.getValue()));\n}\n}\n\nreqBuilder.url(httpBuilder.build()).headers(headerBuilder.build()).get();\n} else {\nFormBody.Builder formBuilder = new FormBody.Builder();\nif (requestNetwork.getParams().size() > 0) {\nHashMap<String, Object> params = requestNetwork.getParams();\n\nfor (HashMap.Entry<String, Object> param : params.entrySet()) {\nformBuilder.add(param.getKey(), String.valueOf(param.getValue()));\n}\n}\n\nRequestBody reqBody = formBuilder.build();\n\nreqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody);\n}\n} else {\nRequestBody reqBody = RequestBody.create(okhttp3.MediaType.parse(\"application/json\"), new Gson().toJson(requestNetwork.getParams()));\n\nif (method.equals(GET)) {\nreqBuilder.url(url).headers(headerBuilder.build()).get();\n} else {\nreqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody);\n}\n}\n\nRequest req = reqBuilder.build();\n\ngetClient().newCall(req).enqueue(new Callback() {\n@Override\npublic void onFailure(Call call, final IOException e) {\nrequestNetwork.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nrequestListener.onErrorResponse(tag, e.getMessage());\n}\n});\n}\n\n@Override\npublic void onResponse(Call call, final Response response) throws IOException {\nfinal String responseBody = response.body().string().trim();\nrequestNetwork.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nrequestListener.onResponse(tag, responseBody);\n}\n});\n}\n});\n} catch (Exception e) {\nrequestListener.onErrorResponse(tag, e.getMessage());\n}\n}\n}");
      return var1.toString();
   }

   public static String g(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("package ");
      var1.append(var0);
      var1.append(";");
      var1.append("\r\n");
      var1.append("\nimport android.app.Activity;\nimport android.bluetooth.BluetoothAdapter;\nimport android.bluetooth.BluetoothDevice;\nimport android.content.Intent;\n\nimport java.util.ArrayList;\nimport java.util.HashMap;\nimport java.util.Set;\nimport java.util.UUID;\n\npublic class BluetoothConnect {\nprivate static final String DEFAULT_UUID = \"00001101-0000-1000-8000-00805F9B34FB\";\n\nprivate Activity activity;\n\nprivate BluetoothAdapter bluetoothAdapter;\n\npublic BluetoothConnect(Activity activity) {\nthis.activity = activity;\nthis.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();\n}\n\npublic boolean isBluetoothEnabled() {\nif(bluetoothAdapter != null) return true;\n\nreturn false;\n}\n\npublic boolean isBluetoothActivated() {\nif(bluetoothAdapter == null) return false;\n\nreturn bluetoothAdapter.isEnabled();\n}\n\npublic void activateBluetooth() {\nIntent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);\nactivity.startActivity(intent);\n}\n\npublic String getRandomUUID() {\nreturn String.valueOf(UUID.randomUUID());\n}\n\npublic void getPairedDevices(ArrayList<HashMap<String, Object>> results) {\nSet<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();\n\nif(pairedDevices.size() > 0) {\nfor(BluetoothDevice device : pairedDevices) {\nHashMap<String, Object> result = new HashMap<>();\nresult.put(\"name\", device.getName());\nresult.put(\"address\", device.getAddress());\n\nresults.add(result);\n}\n}\n}\n\npublic void readyConnection(BluetoothConnectionListener listener, String tag) {\nif(BluetoothController.getInstance().getState().equals(BluetoothController.STATE_NONE)) {\nBluetoothController.getInstance().start(this, listener, tag, UUID.fromString(DEFAULT_UUID), bluetoothAdapter);\n}\n}\n\npublic void readyConnection(BluetoothConnectionListener listener, String uuid, String tag) {\nif(BluetoothController.getInstance().getState().equals(BluetoothController.STATE_NONE)) {\nBluetoothController.getInstance().start(this, listener, tag, UUID.fromString(uuid), bluetoothAdapter);\n}\n}\n\n\npublic void startConnection(BluetoothConnectionListener listener, String address, String tag) {\nBluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);\n\nBluetoothController.getInstance().connect(device, this, listener, tag, UUID.fromString(DEFAULT_UUID), bluetoothAdapter);\n}\n\npublic void startConnection(BluetoothConnectionListener listener, String uuid, String address, String tag) {\nBluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);\n\nBluetoothController.getInstance().connect(device, this, listener, tag, UUID.fromString(uuid), bluetoothAdapter);\n}\n\npublic void stopConnection(BluetoothConnectionListener listener, String tag) {\nBluetoothController.getInstance().stop(this, listener, tag);\n}\n\npublic void sendData(BluetoothConnectionListener listener, String data, String tag) {\nString state = BluetoothController.getInstance().getState();\n\nif(!state.equals(BluetoothController.STATE_CONNECTED)) {\nlistener.onConnectionError(tag, state, \"Bluetooth is not connected yet\");\nreturn;\n}\n\nBluetoothController.getInstance().write(data.getBytes());\n}\n\npublic Activity getActivity() {\nreturn activity;\n}\n\npublic interface BluetoothConnectionListener {\nvoid onConnected(String tag, HashMap<String, Object> deviceData);\nvoid onDataReceived(String tag, byte[] data, int bytes);\nvoid onDataSent(String tag, byte[] data);\nvoid onConnectionError(String tag, String connectionState, String message);\nvoid onConnectionStopped(String tag);\n}\n}");
      return var1.toString();
   }

   public static String h(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("package ");
      var1.append(var0);
      var1.append(";");
      var1.append("\r\n");
      var1.append("\nimport android.bluetooth.BluetoothAdapter;\nimport android.bluetooth.BluetoothDevice;\nimport android.bluetooth.BluetoothServerSocket;\nimport android.bluetooth.BluetoothSocket;\n\nimport java.io.InputStream;\nimport java.io.OutputStream;\nimport java.util.HashMap;\nimport java.util.UUID;\n\npublic class BluetoothController {\npublic static final String STATE_NONE       = \"none\";\npublic static final String STATE_LISTEN     = \"listen\";\npublic static final String STATE_CONNECTING = \"connecting\";\npublic static final String STATE_CONNECTED  = \"connected\";\n\nprivate AcceptThread acceptThread;\nprivate ConnectThread connectThread;\nprivate ConnectedThread connectedThread;\n\nprivate String state = STATE_NONE;\n\nprivate static BluetoothController instance;\n\npublic static synchronized BluetoothController getInstance() {\nif(instance == null) {\ninstance = new BluetoothController();\n}\n\nreturn instance;\n}\n\npublic synchronized void start(BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\nif (connectThread != null) {\nconnectThread.cancel();\nconnectThread = null;\n}\n\nif (connectedThread != null) {\nconnectedThread.cancel();\nconnectedThread = null;\n}\n\nif (acceptThread != null) {\nacceptThread.cancel();\nacceptThread = null;\n}\n\nacceptThread = new AcceptThread(bluetoothConnect, listener, tag, uuid, bluetoothAdapter);\nacceptThread.start();}\n\npublic synchronized void connect(BluetoothDevice device, BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\nif (state.equals(STATE_CONNECTING)) {\nif (connectThread != null) {\nconnectThread.cancel();\nconnectThread = null;\n}\n}\n\nif (connectedThread != null) {\nconnectedThread.cancel();\nconnectedThread = null;\n}\n\nconnectThread = new ConnectThread(device, bluetoothConnect, listener, tag, uuid, bluetoothAdapter);\nconnectThread.start();\n}\n\npublic synchronized void connected(BluetoothSocket socket, final BluetoothDevice device, BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag) {\nif (connectThread != null) {\nconnectThread.cancel();\nconnectThread = null;\n}\n\nif (connectedThread != null) {\nconnectedThread.cancel();\nconnectedThread = null;\n}\n\nif (acceptThread != null) {\nacceptThread.cancel();\nacceptThread = null;\n}\n\nconnectedThread = new ConnectedThread(socket, bluetoothConnect, listener, tag);\nconnectedThread.start();\n\nbluetoothConnect.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nHashMap<String, Object> deviceMap = new HashMap<>();\ndeviceMap.put(\"name\", device.getName());\ndeviceMap.put(\"address\", device.getAddress());\n\nlistener.onConnected(tag, deviceMap);\n}\n});\n}\n\npublic synchronized void stop(BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag) {\nif (connectThread != null) {\nconnectThread.cancel();\nconnectThread = null;\n}\n\nif (connectedThread != null) {\nconnectedThread.cancel();\nconnectedThread = null;\n}\n\nif (acceptThread != null) {\nacceptThread.cancel();\nacceptThread = null;\n}\n\nstate = STATE_NONE;\n\nbluetoothConnect.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nlistener.onConnectionStopped(tag);\n}\n});\n}\n\npublic void write(byte[] out) {\nConnectedThread r;\n\nsynchronized (this) {\nif (!state.equals(STATE_CONNECTED)) return;\nr = connectedThread;\n}\n\nr.write(out);\n}\n\npublic void connectionFailed(BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag, final String message) {\nstate = STATE_NONE;\n\nbluetoothConnect.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nlistener.onConnectionError(tag, state, message);\n}\n});\n}\n\npublic void connectionLost(BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag) {\nstate = STATE_NONE;\n\nbluetoothConnect.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nlistener.onConnectionError(tag, state, \"Bluetooth connection is disconnected\");\n}\n});\n}\n\npublic String getState() {\nreturn state;\n}\n\nprivate class AcceptThread extends Thread {\nprivate BluetoothServerSocket serverSocket;\n\nprivate BluetoothConnect bluetoothConnect;\nprivate BluetoothConnect.BluetoothConnectionListener listener;\nprivate String tag;\n\npublic AcceptThread(BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\nthis.bluetoothConnect = bluetoothConnect;\nthis.listener = listener;\nthis.tag = tag;\n\ntry {\nserverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(tag, uuid);\n} catch (Exception e) {\ne.printStackTrace();\n}\n\nstate = STATE_LISTEN;\n}\n\n@Override\npublic void run() {\nBluetoothSocket bluetoothSocket;\n\nwhile (!state.equals(STATE_CONNECTED)) {\ntry {\nbluetoothSocket = serverSocket.accept();\n} catch (Exception e) {\ne.printStackTrace();\nbreak;\n}\n\nif (bluetoothSocket != null) {\nsynchronized (BluetoothController.this) {\nswitch (state) {\ncase STATE_LISTEN:\ncase STATE_CONNECTING:\nconnected(bluetoothSocket, bluetoothSocket.getRemoteDevice(), bluetoothConnect, listener, tag);\nbreak;\ncase STATE_NONE:\ncase STATE_CONNECTED:\ntry {\nbluetoothSocket.close();\n} catch (Exception e) {\ne.printStackTrace();\n}\nbreak;\n}\n}\n}\n}\n}\n\npublic void cancel() {\ntry {\nserverSocket.close();\n} catch (Exception e) {\ne.printStackTrace();\n}\n}\n}\n\nprivate class ConnectThread extends Thread {\nprivate BluetoothDevice device;\nprivate BluetoothSocket socket;\n\nprivate BluetoothConnect bluetoothConnect;\nprivate BluetoothConnect.BluetoothConnectionListener listener;\nprivate String tag;\nprivate BluetoothAdapter bluetoothAdapter;\n\npublic ConnectThread(BluetoothDevice device, BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\nthis.device = device;\nthis.bluetoothConnect = bluetoothConnect;\nthis.listener = listener;\nthis.tag = tag;\nthis.bluetoothAdapter = bluetoothAdapter;\n\ntry {\nsocket = device.createRfcommSocketToServiceRecord(uuid);\n} catch (Exception e) {\ne.printStackTrace();\n}\n\nstate = STATE_CONNECTING;\n}\n\n@Override\npublic void run() {\nbluetoothAdapter.cancelDiscovery();\n\ntry {\nsocket.connect();\n} catch (Exception e) {\ntry {\nsocket.close();\n} catch (Exception e2) {\ne2.printStackTrace();\n}\nconnectionFailed(bluetoothConnect, listener, tag, e.getMessage());\nreturn;\n}\n\nsynchronized (BluetoothController.this) {\nconnectThread = null;\n}\n\nconnected(socket, device, bluetoothConnect, listener, tag);\n}\n\npublic void cancel() {\ntry {\nsocket.close();\n} catch (Exception e) {\ne.printStackTrace();\n}\n}\n}\n\nprivate class ConnectedThread extends Thread {\nprivate BluetoothSocket socket;\nprivate InputStream inputStream;\nprivate OutputStream outputStream;\n\nprivate BluetoothConnect bluetoothConnect;\nprivate BluetoothConnect.BluetoothConnectionListener listener;\nprivate String tag;\n\npublic ConnectedThread(BluetoothSocket socket, BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag) {\nthis.bluetoothConnect = bluetoothConnect;\nthis.listener = listener;\nthis.tag = tag;\n\nthis.socket = socket;\n\ntry {\ninputStream = socket.getInputStream();\noutputStream = socket.getOutputStream();\n} catch (Exception e) {\ne.printStackTrace();\n}\n\nstate = STATE_CONNECTED;\n}\n\npublic void run() {\nwhile (state.equals(STATE_CONNECTED)) {\ntry {\nfinal byte[] buffer = new byte[1024];\nfinal int bytes = inputStream.read(buffer);\n\nbluetoothConnect.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nlistener.onDataReceived(tag, buffer, bytes);\n}\n});\n} catch (Exception e) {\ne.printStackTrace();\nconnectionLost(bluetoothConnect, listener, tag);\nbreak;\n}\n}\n}\n\npublic void write(final byte[] buffer) {\ntry {\noutputStream.write(buffer);\n\nbluetoothConnect.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nlistener.onDataSent(tag, buffer);\n}\n});\n} catch (Exception e) {\ne.printStackTrace();\n}\n}\n\npublic void cancel() {\ntry {\nsocket.close();\n} catch (Exception e) {\ne.printStackTrace();\n}\n}\n}\n}");
      return var1.toString();
   }

   public static String i(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("package ");
      var1.append(var0);
      var1.append(";");
      var1.append("\r\n");
      var1.append("\nimport com.google.android.gms.maps.CameraUpdateFactory;\nimport com.google.android.gms.maps.GoogleMap;\nimport com.google.android.gms.maps.MapView;\nimport com.google.android.gms.maps.OnMapReadyCallback;\nimport com.google.android.gms.maps.model.BitmapDescriptorFactory;\nimport com.google.android.gms.maps.model.LatLng;\nimport com.google.android.gms.maps.model.Marker;\nimport com.google.android.gms.maps.model.MarkerOptions;\n\nimport java.util.HashMap;\n\npublic class GoogleMapController {\n\nprivate GoogleMap googleMap;\nprivate MapView mapView;\nprivate HashMap<String, Marker> mapMarker;\nprivate GoogleMap.OnMarkerClickListener onMarkerClickListener;\n\npublic GoogleMapController(MapView mapView, OnMapReadyCallback onMapReadyCallback) {\nthis.mapView = mapView;\nmapMarker = new HashMap<>();\n\nthis.mapView.getMapAsync(onMapReadyCallback);\n}\n\npublic void setGoogleMap(GoogleMap googleMap) {\nthis.googleMap = googleMap;\n\nif (onMarkerClickListener != null) {\nthis.googleMap.setOnMarkerClickListener(onMarkerClickListener);\n}\n}\n\npublic GoogleMap getGoogleMap() {\nreturn googleMap;\n}\n\npublic void setMapType(int _mapType) {\nif (googleMap == null) return;\n\ngoogleMap.setMapType(_mapType);\n}\n\npublic void setOnMarkerClickListener(GoogleMap.OnMarkerClickListener onMarkerClickListener) {\nthis.onMarkerClickListener = onMarkerClickListener;\n\nif (googleMap != null) {\nthis.googleMap.setOnMarkerClickListener(onMarkerClickListener);\n}\n}\n\npublic void addMarker(String id, double lat, double lng) {\nif (googleMap == null) return;\n\nMarkerOptions markerOptions = new MarkerOptions();\nmarkerOptions.position(new LatLng(lat, lng));\nMarker marker = googleMap.addMarker(markerOptions);\nmarker.setTag(id);\nmapMarker.put(id, marker);\n}\n\npublic Marker getMarker(String id) {\nreturn mapMarker.get(id);\n}\n\npublic void setMarkerInfo(String id, String title, String snippet) {\nMarker marker = mapMarker.get(id);\nif (marker == null) return;\n\nmarker.setTitle(title);\nmarker.setSnippet(snippet);\n}\n\npublic void setMarkerPosition(String id, double lat, double lng) {\nMarker marker = mapMarker.get(id);\nif (marker == null) return;\n\nmarker.setPosition(new LatLng(lat, lng));\n}\n\npublic void setMarkerColor(String id, float color, double alpha) {\nMarker marker = mapMarker.get(id);\nif (marker == null) return;\n\nmarker.setAlpha((float) alpha);\nmarker.setIcon(BitmapDescriptorFactory.defaultMarker(color));\n}\n\npublic void setMarkerIcon(String id, int resIcon) {\nMarker marker = mapMarker.get(id);\nif (marker == null) return;\n\nmarker.setIcon(BitmapDescriptorFactory.fromResource(resIcon));\n}\n\npublic void setMarkerVisible(String id, boolean visible) {\nMarker marker = mapMarker.get(id);\nif (marker == null) return;\n\nmarker.setVisible(visible);\n}\n\n\npublic void moveCamera(double lat, double lng) {\nif (googleMap == null) return;\n\ngoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));\n}\n\npublic void zoomTo(double zoom) {\nif (googleMap == null) return;\n\ngoogleMap.moveCamera(CameraUpdateFactory.zoomTo((float) zoom));\n}\n\npublic void zoomIn() {\nif (googleMap == null) return;\n\ngoogleMap.moveCamera(CameraUpdateFactory.zoomIn());\n}\n\npublic void zoomOut() {\nif (googleMap == null) return;\n\ngoogleMap.moveCamera(CameraUpdateFactory.zoomOut());\n}\n}");
      return var1.toString();
   }

   public static String j(String var0) {
      StringBuilder var12 = new StringBuilder(4096);
      char[] var13 = var0.toCharArray();
      int var11 = var13.length;
      int var3 = 0;
      boolean var6 = false;
      boolean var7 = false;
      int var9 = 0;
      boolean var8 = false;
      boolean var5 = false;

      for(boolean var4 = false; var3 < var11; ++var3) {
         char var1 = var13[var3];
         if (var6) {
            if (var1 == '\n') {
               var12.append(var1);
               a(var12, var9);
               var6 = false;
            } else {
               var12.append(var1);
            }
         } else {
            char var2;
            int var10;
            if (var7) {
               if (var1 == '*') {
                  var10 = var3 + 1;
                  var2 = var13[var10];
                  if (var2 == '/') {
                     var12.append(var1);
                     var12.append(var2);
                     var3 = var10;
                     var7 = false;
                     continue;
                  }
               }

               var12.append(var1);
            } else if (var8) {
               var12.append(var1);
               var8 = false;
            } else if (var1 == '\\') {
               var12.append(var1);
               var8 = true;
            } else if (var5) {
               if (var1 == '\'') {
                  var12.append(var1);
                  var5 = false;
               } else {
                  var12.append(var1);
               }
            } else if (var4) {
               if (var1 == '"') {
                  var12.append(var1);
                  var4 = false;
               } else {
                  var12.append(var1);
               }
            } else {
               if (var1 == '/') {
                  var10 = var3 + 1;
                  var2 = var13[var10];
                  if (var2 == '/') {
                     var12.append(var1);
                     var12.append(var2);
                     var3 = var10;
                     var6 = true;
                     continue;
                  }

                  if (var2 == '*') {
                     var12.append(var1);
                     var12.append(var2);
                     var3 = var10;
                     var7 = true;
                     continue;
                  }
               }

               if (var1 == '\n') {
                  var12.append(var1);
                  a(var12, var9);
               } else {
                  if (var1 == '\'') {
                     var5 = true;
                  }

                  if (var1 == '"') {
                     var4 = true;
                  }

                  var10 = var9;
                  if (var1 == '{') {
                     ++var9;
                     var10 = var9;
                     if (!"".contains("(")) {
                        "".contains("$");
                        var10 = var9;
                     }
                  }

                  if (var1 == '}') {
                     --var10;
                     var9 = var10;
                     if (var12.charAt(var12.length() - 1) == '\t') {
                        var12.deleteCharAt(var12.length() - 1);
                        var9 = var10;
                     }
                  } else {
                     var9 = var10;
                  }

                  var12.append(var1);
               }
            }
         }
      }

      return var12.toString();
   }

   static enum a {
      a,
      b,
      c;

      private static final hg.a[] d = new hg.a[]{a, b, c};
   }
}
