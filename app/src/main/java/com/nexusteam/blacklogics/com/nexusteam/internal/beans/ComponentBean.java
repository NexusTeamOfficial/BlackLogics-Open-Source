package com.nexusteam.internal.beans;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.hc;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

public class ComponentBean extends CollapsibleBean implements Parcelable {
    public static final int COMPONENT_TYPE_BLUETOOTH_CONNECT = 20;
    public static final int COMPONENT_TYPE_CALENDAR = 3;
    public static final int COMPONENT_TYPE_CAMERA = 15;
    public static final int COMPONENT_TYPE_DIALOG = 7;
    public static final int COMPONENT_TYPE_FILE_PICKER = 16;
    public static final int COMPONENT_TYPE_FIREBASE = 6;
    public static final int COMPONENT_TYPE_FIREBASE_AUTH = 12;
    public static final int COMPONENT_TYPE_FIREBASE_STORAGE = 14;
    public static final int COMPONENT_TYPE_GYROSCOPE = 11;
    public static final int COMPONENT_TYPE_INTENT = 1;
    public static final int COMPONENT_TYPE_INTERSTITIAL_AD = 13;
    public static final int COMPONENT_TYPE_LOCATION_MANAGER = 21;
    public static final int COMPONENT_TYPE_MEDIAPLAYER = 8;
    public static final int COMPONENT_TYPE_OBJECTANIMATOR = 10;
    public static final int COMPONENT_TYPE_REQUEST_NETWORK = 17;
    public static final int COMPONENT_TYPE_SHAREDPREF = 2;
    public static final int COMPONENT_TYPE_SOUNDPOOL = 9;
    public static final int COMPONENT_TYPE_SPEECH_TO_TEXT = 19;
    public static final int COMPONENT_TYPE_TEXT_TO_SPEECH = 18;
    public static final int COMPONENT_TYPE_TIMERTASK = 5;
    public static final int COMPONENT_TYPE_VIBRATOR = 4;
    public static final Parcelable.Creator<ComponentBean> CREATOR = new Parcelable.Creator<ComponentBean>() {
        public ComponentBean createFromParcel(Parcel parcel) {
            return new ComponentBean(parcel);
        }
        
        public ComponentBean[] newArray(int i) {
            return new ComponentBean[i];
        }
    };
    private hc classInfo;
    @Expose
    public String componentId;
    @Expose
    public String param1;
    @Expose
    public String param2;
    @Expose
    public String param3;
    @Expose
    public int type;
    
    public static String getComponentDocsUrlByTypeName(int i) {
        switch (i) {
            case 1:
            return "https://docs.sketchware.io/docs/component-intent.html";
            case 2:
            return "https://docs.sketchware.io/docs/component-shared-preference.html";
            case 3:
            return "https://docs.sketchware.io/docs/component-calendar.html";
            case 4:
            return "https://docs.sketchware.io/docs/component-vibrator.html";
            case 5:
            return "https://docs.sketchware.io/docs/component-timer.html";
            case 6:
            return "https://docs.sketchware.io/docs/component-firebase-database.html";
            case 7:
            return "https://docs.sketchware.io/docs/component-dialog.html";
            case 8:
            return "https://docs.sketchware.io/docs/component-mediaplayer.html";
            case 9:
            return "https://docs.sketchware.io/docs/component-soundpool.html";
            case 10:
            return "https://docs.sketchware.io/docs/component-object-animator.html";
            case 11:
            return "https://docs.sketchware.io/docs/component-gyroscope.html";
            case 12:
            return "https://docs.sketchware.io/docs/component-firebase-auth.html";
            case 13:
            return "";
            case 14:
            return "https://docs.sketchware.io/docs/component-firebase-storage.html";
            case 15:
            return "https://docs.sketchware.io/docs/component-camera.html";
            case 16:
            return "https://docs.sketchware.io/docs/component-filepicker.html";
            case 17:
            return "";
            case 18:
            return "";
            case 19:
            return "";
            case 20:
            return "";
            case 21:
            return "";
            default:
            return "";
        }
    }
    
    public static String getComponentName(Context context, int i) {
        switch (i) {
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
            return "Firebase DB";
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
            return "Firebase Auth";
            case 13:
            return "Interstitial Ad";
            case 14:
            return "Firebase Storage";
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
            return "Module";
        }
    }
    
    public static String getComponentTypeName(int i) {
        switch (i) {
            case 1:
            return "Intent";
            case 2:
            return "File";
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
    
    public static int getDescStrResource(int i) {
        switch (i) {
            case 1:
            return R.string.component_description_intent;
            case 2:
            return R.string.component_description_file;
            case 3:
            return R.string.component_description_calendar;
            case 4:
            return R.string.component_description_vibrator;
            case 5:
            return R.string.component_description_timer;
            case 6:
            return R.string.design_library_firebase_description_about_firebase;
            case 7:
            return R.string.component_description_dialog;
            case 8:
            return R.string.component_description_mediaplayer;
            case 9:
            return R.string.component_description_soundpool;
            case 10:
            return R.string.component_description_objectanimator;
            case 11:
            return R.string.component_description_gyrosope;
            case 12:
            return R.string.component_description_firebase_auth;
            case 13:
            return R.string.component_description_interstitial_ad;
            case 14:
            return R.string.component_description_firebase_storage;
            case 15:
            return R.string.component_description_camera;
            case 16:
            return R.string.component_description_file_picker;
            case 17:
            return R.string.component_description_request_network;
            case 18:
            return R.string.component_description_text_to_speech;
            case 19:
            return R.string.component_description_speech_to_text;
            case 20:
            return R.string.component_description_bluetooth_connect;
            case 21:
            return R.string.component_description_location_manager;
            default:
            return 0;
        }
    }
    
    public static int getIconResource(int i) {
        switch (i) {
            case 1:
            return R.drawable.widget_intent;
            case 2:
            return R.drawable.widget_shared_preference;
            case 3:
            return R.drawable.widget_calendar;
            case 4:
            return R.drawable.widget_vibrator;
            case 5:
            return R.drawable.widget_timer;
            case 6:
            return R.drawable.widget_firebase;
            case 7:
            return R.drawable.widget_alertdialog;
            case 8:
            return R.drawable.widget_mediaplayer;
            case 9:
            return R.drawable.widget_soundpool;
            case 10:
            return R.drawable.widget_objectanimator;
            case 11:
            return R.drawable.widget_gyroscope;
            case 12:
            return R.drawable.widget_firebase;
            case 13:
            return R.drawable.widget_admob;
            case 14:
            return R.drawable.widget_firebase;
            case 15:
            return R.drawable.widget_camera;
            case 16:
            return R.drawable.widget_file;
            case 17:
            return R.drawable.widget_network_request;
            case 18:
            return R.drawable.widget_text_to_speech;
            case 19:
            return R.drawable.widget_speech_to_text;
            case 20:
            return R.drawable.widget_bluetooth;
            case 21:
            return R.drawable.widget_location;
            default:
            return R.drawable.widget_module;
        }
    }
    
    public int describeContents() {
        return 0;
    }
    
    public void print() {
    }
    
    public ComponentBean(int i) {
        this(i, "");
    }
    
    public ComponentBean(int i, String str) {
        this(i, str, "", "");
    }
    
    public ComponentBean(int i, String str, String str2) {
        this(i, str, str2, "", "");
    }
    
    public ComponentBean(int i, String str, String str2, String str3) {
        this(i, str, str2, str3, "");
    }
    
    public ComponentBean(int i, String str, String str2, String str3, String str4) {
        this.param1 = "";
        this.param2 = "";
        this.param3 = "";
        this.type = i;
        this.componentId = str;
        this.param1 = str2;
        this.param2 = str3;
        this.param3 = str4;
    }
    
    public ComponentBean(Parcel parcel) {
        this.param1 = "";
        this.param2 = "";
        this.param3 = "";
        this.type = parcel.readInt();
        this.componentId = parcel.readString();
        this.param1 = parcel.readString();
        this.param2 = parcel.readString();
        this.param3 = parcel.readString();
    }
    
    public void copy(ComponentBean componentBean) {
        this.type = componentBean.type;
        this.componentId = componentBean.componentId;
        this.param1 = componentBean.param1;
        this.param2 = componentBean.param2;
        this.param3 = componentBean.param3;
    }
    
    public void clearClassInfo() {
        this.classInfo = null;
    }
    
    /* access modifiers changed from: protected */
    public void buildClassInfo() {
        String str = "";
        switch (this.type) {
            case 1:
            str = "Intent";
            break;
            case 2:
            str = "SharedPreferences";
            break;
            case 3:
            str = "Calendar";
            break;
            case 4:
            str = "Vibrator";
            break;
            case 5:
            str = "Timer";
            break;
            case 6:
            str = "FirebaseDB";
            break;
            case 7:
            str = "Dialog";
            break;
            case 8:
            str = "MediaPlayer";
            break;
            case 9:
            str = "SoundPool";
            break;
            case 10:
            str = "ObjectAnimator";
            break;
            case 11:
            str = "Gyroscope";
            break;
            case 12:
            str = "FirebaseAuth";
            break;
            case 13:
            str = "InterstitialAd";
            break;
            case 14:
            str = "FirebaseStorage";
            break;
            case 15:
            str = "Camera";
            break;
            case 16:
            str = "FilePicker";
            break;
            case 17:
            str = "RequestNetwork";
            break;
            case 18:
            str = "TextToSpeech";
            break;
            case 19:
            str = "SpeechToText";
            break;
            case 20:
            str = "BluetoothConnect";
            break;
            case 21:
            str = "LocationManager";
            break;
        }
        this.classInfo = new hc(str);
    }
    
    public hc getClassInfo() {
        if (this.classInfo == null) {
            buildClassInfo();
        }
        return this.classInfo;
    }
    
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.type);
        parcel.writeString(this.componentId);
        parcel.writeString(this.param1);
        parcel.writeString(this.param2);
        parcel.writeString(this.param3);
    }
    
    public static int getComponentTypeByTypeName(String componentName) {
        int hashCode = componentName.hashCode();
        int caseValue = -1;
        
        switch (hashCode) {
            case -2099895620:
            if (componentName.equals("Intent")) {
                caseValue = 1;
            }
            break;
            case -1965257499:
            if (componentName.equals("Gyroscope")) {
                caseValue = 11;
            }
            break;
            case -1908172204:
            if (componentName.equals("FirebaseStorage")) {
                caseValue = 13;
            }
            break;
            case -1884914774:
            if (componentName.equals("TextToSpeech")) {
                caseValue = 17;
            }
            break;
            case -1042830870:
            if (componentName.equals("SpeechToText")) {
                caseValue = 18;
            }
            break;
            case -1014653761:
            if (componentName.equals("RequestNetwork")) {
                caseValue = 16;
            }
            break;
            case -596330166:
            if (componentName.equals("FilePicker")) {
                caseValue = 15;
            }
            break;
            case -498706905:
            if (componentName.equals("Firebase")) {
                caseValue = 9;
            }
            break;
            case -294086120:
            if (componentName.equals("LocationManager")) {
                caseValue = 20;
            }
            break;
            case -113680546:
            if (componentName.equals("Calendar")) {
                caseValue = 2;
            }
            break;
            case 2189724:
            if (componentName.equals("File")) {
                caseValue = 0;
            }
            break;
            case 80811813:
            if (componentName.equals("Timer")) {
                caseValue = 6;
            }
            break;
            case 191354283:
            if (componentName.equals("SoundPool")) {
                caseValue = 5;
            }
            break;
            case 225459311:
            if (componentName.equals("FirebaseAuth")) {
                caseValue = 10;
            }
            break;
            case 320151695:
            if (componentName.equals("InterstitialAd")) {
                caseValue = 12;
            }
            break;
            case 1170382393:
            if (componentName.equals("Vibrator")) {
                caseValue = 7;
            }
            break;
            case 1236935621:
            if (componentName.equals("MediaPlayer")) {
                caseValue = 4;
            }
            break;
            case 1512362620:
            if (componentName.equals("BluetoothConnect")) {
                caseValue = 19;
            }
            break;
            case 1799376742:
            if (componentName.equals("ObjectAnimator")) {
                caseValue = 8;
            }
            break;
            case 2011082565:
            if (componentName.equals("Camera")) {
                caseValue = 14;
            }
            break;
            case 2046749032:
            if (componentName.equals("Dialog")) {
                caseValue = 3;
            }
            break;
        }
        

        switch (caseValue) {
            case 0: return 2;   // File
            case 1: return 1;   // Intent
            case 2: return 3;   // Calendar
            case 3: return 7;   // Dialog
            case 4: return 8;   // MediaPlayer
            case 5: return 9;   // SoundPool
            case 6: return 5;   // Timer
            case 7: return 4;   // Vibrator
            case 8: return 10;  // ObjectAnimator
            case 9: return 6;   // Firebase
            case 10: return 12; // FirebaseAuth
            case 11: return 11; // Gyroscope
            case 12: return 13; // InterstitialAd
            case 13: return 14; // FirebaseStorage
            case 14: return 15; // Camera
            case 15: return 16; // FilePicker
            case 16: return 17; // RequestNetwork
            case 17: return 18; // TextToSpeech
            case 18: return 19; // SpeechToText
            case 19: return 20; // BluetoothConnect
            case 20: return 21; // LocationManager
            default: return -1;
        }
    }
    public static Parcelable.Creator<ComponentBean> getCreator() {
        return CREATOR;
    }
}
