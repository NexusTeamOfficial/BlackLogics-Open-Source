package com.nexusteam.internal.beans;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;
import com.nexusteam.internal.manager.ComponentManager;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.hc;

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

    @Expose
    public String name;

    @Expose
    public String description;

    @Expose
    public String imports;

    @Expose
    public String additionalVar;

    @Expose
    public String icon;

    @Expose
    public String typeName;

    @Expose
    public String className;

    @Expose
    public String buildClass;

    @Expose
    public String url;

    @Expose
    public String defineAdditionalVar;

    public ComponentBean() {
        this.param1 = "";
        this.param2 = "";
        this.param3 = "";
    }

    public static String getComponentDocsUrlByTypeName(int i) {
        switch (i) {
            case 1: return "https://docs.sketchware.io/docs/component-intent.html";
            case 2: return "https://docs.sketchware.io/docs/component-shared-preference.html";
            case 3: return "https://docs.sketchware.io/docs/component-calendar.html";
            case 4: return "https://docs.sketchware.io/docs/component-vibrator.html";
            case 5: return "https://docs.sketchware.io/docs/component-timer.html";
            case 6: return "https://docs.sketchware.io/docs/component-firebase-database.html";
            case 7: return "https://docs.sketchware.io/docs/component-dialog.html";
            case 8: return "https://docs.sketchware.io/docs/component-mediaplayer.html";
            case 9: return "https://docs.sketchware.io/docs/component-soundpool.html";
            case 10: return "https://docs.sketchware.io/docs/component-object-animator.html";
            case 11: return "https://docs.sketchware.io/docs/component-gyroscope.html";
            case 12: return "https://docs.sketchware.io/docs/component-firebase-auth.html";
            case 13: return "";
            case 14: return "https://docs.sketchware.io/docs/component-firebase-storage.html";
            case 15: return "https://docs.sketchware.io/docs/component-camera.html";
            case 16: return "https://docs.sketchware.io/docs/component-filepicker.html";
            default: return "";
        }
    }

    public static String getComponentName(Context context, int i) {
        switch (i) {
            case 1: return "Intent";
            case 2: return "SharedPreferences";
            case 3: return "Calendar";
            case 4: return "Vibrator";
            case 5: return "Timer";
            case 6: return "Firebase DB";
            case 7: return "Dialog";
            case 8: return "MediaPlayer";
            case 9: return "SoundPool";
            case 10: return "ObjectAnimator";
            case 11: return "Gyroscope";
            case 12: return "Firebase Auth";
            case 13: return "Interstitial Ad";
            case 14: return "Firebase Storage";
            case 15: return "Camera";
            case 16: return "FilePicker";
            case 17: return "RequestNetwork";
            case 18: return "TextToSpeech";
            case 19: return "SpeechToText";
            case 20: return "BluetoothConnect";
            case 21: return "LocationManager";
            default:
                ComponentBean customComp = ComponentManager.getInstance().getComponentByType(i);
                if (customComp != null && customComp.name != null && !customComp.name.isEmpty()) {
                    return customComp.name;
                }
                return "Module";
        }
    }

    public static String getComponentTypeName(int i) {
        switch (i) {
            case 1: return "Intent";
            case 2: return "File";
            case 3: return "Calendar";
            case 4: return "Vibrator";
            case 5: return "Timer";
            case 6: return "FirebaseDB";
            case 7: return "Dialog";
            case 8: return "MediaPlayer";
            case 9: return "SoundPool";
            case 10: return "ObjectAnimator";
            case 11: return "Gyroscope";
            case 12: return "FirebaseAuth";
            case 13: return "InterstitialAd";
            case 14: return "FirebaseStorage";
            case 15: return "Camera";
            case 16: return "FilePicker";
            case 17: return "RequestNetwork";
            case 18: return "TextToSpeech";
            case 19: return "SpeechToText";
            case 20: return "BluetoothConnect";
            case 21: return "LocationManager";
            default:
                ComponentBean customBean = ComponentManager.getInstance().getComponentByType(i);
                if (customBean != null) {
                    if (customBean.typeName != null && !customBean.typeName.isEmpty()) {
                        return customBean.typeName;
                    } else if (customBean.name != null && !customBean.name.isEmpty()) {
                        return customBean.name;
                    }
                }
                return "CustomComponent_" + i;
        }
    }

    public static int getDescStrResource(int i) {
        switch (i) {
            case 1: return R.string.component_description_intent;
            case 2: return R.string.component_description_file;
            case 3: return R.string.component_description_calendar;
            case 4: return R.string.component_description_vibrator;
            case 5: return R.string.component_description_timer;
            case 6: return R.string.design_library_firebase_description_about_firebase;
            case 7: return R.string.component_description_dialog;
            case 8: return R.string.component_description_mediaplayer;
            case 9: return R.string.component_description_soundpool;
            case 10: return R.string.component_description_objectanimator;
            case 11: return R.string.component_description_gyrosope;
            case 12: return R.string.component_description_firebase_auth;
            case 13: return R.string.component_description_interstitial_ad;
            case 14: return R.string.component_description_firebase_storage;
            case 15: return R.string.component_description_camera;
            case 16: return R.string.component_description_file_picker;
            case 17: return R.string.component_description_request_network;
            case 18: return R.string.component_description_text_to_speech;
            case 19: return R.string.component_description_speech_to_text;
            case 20: return R.string.component_description_bluetooth_connect;
            case 21: return R.string.component_description_location_manager;
            default: return 0;
        }
    }

    public static int getIconResource(int i) {
        switch (i) {
            case 1: return R.drawable.widget_intent;
            case 2: return R.drawable.widget_shared_preference;
            case 3: return R.drawable.widget_calendar;
            case 4: return R.drawable.widget_vibrator;
            case 5: return R.drawable.widget_timer;
            case 6: return R.drawable.widget_firebase;
            case 7: return R.drawable.widget_alertdialog;
            case 8: return R.drawable.widget_mediaplayer;
            case 9: return R.drawable.widget_soundpool;
            case 10: return R.drawable.widget_objectanimator;
            case 11: return R.drawable.widget_gyroscope;
            case 12: return R.drawable.widget_firebase;
            case 13: return R.drawable.widget_admob;
            case 14: return R.drawable.widget_firebase;
            case 15: return R.drawable.widget_camera;
            case 16: return R.drawable.widget_file;
            case 17: return R.drawable.widget_network_request;
            case 18: return R.drawable.widget_text_to_speech;
            case 19: return R.drawable.widget_speech_to_text;
            case 20: return R.drawable.widget_bluetooth;
            case 21: return R.drawable.widget_location;
            default: return R.drawable.widget_module;
        }
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

    // ✅ FIX 3: Parcel constructor mein saari fields read karo
    // Pehle sirf 5 fields thi — baaki sab Intent ke through null aata tha
    public ComponentBean(Parcel parcel) {
        this.param1 = "";
        this.param2 = "";
        this.param3 = "";
        this.type = parcel.readInt();
        this.componentId = parcel.readString();
        this.param1 = parcel.readString();
        this.param2 = parcel.readString();
        this.param3 = parcel.readString();
        // ✅ NAYE FIELDS — writeToParcel se same order mein
        this.name = parcel.readString();
        this.description = parcel.readString();
        this.imports = parcel.readString();
        this.additionalVar = parcel.readString();
        this.defineAdditionalVar = parcel.readString();
        this.icon = parcel.readString();
        this.typeName = parcel.readString();
        this.className = parcel.readString();
        this.buildClass = parcel.readString();
        this.url = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    // ✅ FIX 3: writeToParcel mein bhi saari fields write karo
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.type);
        parcel.writeString(this.componentId);
        parcel.writeString(this.param1);
        parcel.writeString(this.param2);
        parcel.writeString(this.param3);
        // ✅ NAYE FIELDS — Parcel constructor se same order mein
        parcel.writeString(this.name != null ? this.name : "");
        parcel.writeString(this.description != null ? this.description : "");
        parcel.writeString(this.imports != null ? this.imports : "");
        parcel.writeString(this.additionalVar != null ? this.additionalVar : "");
        parcel.writeString(this.defineAdditionalVar != null ? this.defineAdditionalVar : "");
        parcel.writeString(this.icon != null ? this.icon : "");
        parcel.writeString(this.typeName != null ? this.typeName : "");
        parcel.writeString(this.className != null ? this.className : "");
        parcel.writeString(this.buildClass != null ? this.buildClass : "");
        parcel.writeString(this.url != null ? this.url : "");
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

    protected void buildClassInfo() {
        String str = "";
        switch (this.type) {
            case 1: str = "Intent"; break;
            case 2: str = "SharedPreferences"; break;
            case 3: str = "Calendar"; break;
            case 4: str = "Vibrator"; break;
            case 5: str = "Timer"; break;
            case 6: str = "FirebaseDB"; break;
            case 7: str = "Dialog"; break;
            case 8: str = "MediaPlayer"; break;
            case 9: str = "SoundPool"; break;
            case 10: str = "ObjectAnimator"; break;
            case 11: str = "Gyroscope"; break;
            case 12: str = "FirebaseAuth"; break;
            case 13: str = "InterstitialAd"; break;
            case 14: str = "FirebaseStorage"; break;
            case 15: str = "Camera"; break;
            case 16: str = "FilePicker"; break;
            case 17: str = "RequestNetwork"; break;
            case 18: str = "TextToSpeech"; break;
            case 19: str = "SpeechToText"; break;
            case 20: str = "BluetoothConnect"; break;
            case 21: str = "LocationManager"; break;
            default:
                if (this.typeName != null && !this.typeName.isEmpty()) {
                    str = this.typeName;
                } else if (this.className != null && !this.className.isEmpty()) {
                    str = this.className;
                } else if (this.name != null && !this.name.isEmpty()) {
                    str = this.name;
                }
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

    public static int getComponentTypeByTypeName(String componentName) {
        int hashCode = componentName.hashCode();
        int caseValue = -1;

        if (hashCode == -2099895620 && componentName.equals("Intent")) {
            caseValue = 1;
        } else if (hashCode == -1965257499 && componentName.equals("Gyroscope")) {
            caseValue = 11;
        } else if (hashCode == -1908172204 && componentName.equals("FirebaseStorage")) {
            caseValue = 13;
        } else if (hashCode == -1884914774 && componentName.equals("TextToSpeech")) {
            caseValue = 17;
        } else if (hashCode == -1042830870 && componentName.equals("SpeechToText")) {
            caseValue = 18;
        } else if (hashCode == -1014653761 && componentName.equals("RequestNetwork")) {
            caseValue = 16;
        } else if (hashCode == -596330166 && componentName.equals("FilePicker")) {
            caseValue = 15;
        } else if (hashCode == -498706905 && componentName.equals("Firebase")) {
            caseValue = 9;
        } else if (hashCode == -294086120 && componentName.equals("LocationManager")) {
            caseValue = 20;
        } else if (hashCode == -113680546 && componentName.equals("Calendar")) {
            caseValue = 2;
        } else if (hashCode == 2189724 && componentName.equals("File")) {
            caseValue = 0;
        } else if (hashCode == 80811813 && componentName.equals("Timer")) {
            caseValue = 6;
        } else if (hashCode == 191354283 && componentName.equals("SoundPool")) {
            caseValue = 5;
        } else if (hashCode == 225459311 && componentName.equals("FirebaseAuth")) {
            caseValue = 10;
        } else if (hashCode == 320151695 && componentName.equals("InterstitialAd")) {
            caseValue = 12;
        } else if (hashCode == 1170382393 && componentName.equals("Vibrator")) {
            caseValue = 7;
        } else if (hashCode == 1236935621 && componentName.equals("MediaPlayer")) {
            caseValue = 4;
        } else if (hashCode == 1512362620 && componentName.equals("BluetoothConnect")) {
            caseValue = 19;
        } else if (hashCode == 1799376742 && componentName.equals("ObjectAnimator")) {
            caseValue = 8;
        } else if (hashCode == 2011082565 && componentName.equals("Camera")) {
            caseValue = 14;
        } else if (hashCode == 2046749032 && componentName.equals("Dialog")) {
            caseValue = 3;
        }

        switch (caseValue) {
            case 0: return 2;
            case 1: return 1;
            case 2: return 3;
            case 3: return 7;
            case 4: return 8;
            case 5: return 9;
            case 6: return 5;
            case 7: return 4;
            case 8: return 10;
            case 9: return 6;
            case 10: return 12;
            case 11: return 11;
            case 12: return 13;
            case 13: return 14;
            case 14: return 15;
            case 15: return 16;
            case 16: return 17;
            case 17: return 18;
            case 18: return 19;
            case 19: return 20;
            case 20: return 21;
            default: return -1;
        }
    }

    public static Parcelable.Creator<ComponentBean> getCreator() {
        return CREATOR;
    }
}
