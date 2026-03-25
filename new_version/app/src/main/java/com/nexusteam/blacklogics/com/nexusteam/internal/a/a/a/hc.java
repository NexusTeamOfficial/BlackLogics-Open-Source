package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

public class hc {
    
    /* renamed from: a  reason: collision with root package name */
    private String f251a;
    private String[] b = null;
    
    public hc(String str) {
        this.f251a = str;
        e();
    }
    
    private void e() {
        String v0 = "";
        String v1 = this.f251a;
        switch (v1) {
            case "boolean":
            v0 = "Var.boolean";
            break;
            case "double":
            v0 = "Var.double";
            break;
            case "String":
            v0 = "Var.String";
            break;
            case "Map":
            v0 = "Var.Map";
            break;
            case "ListInt":
            v0 = "List.ListInt";
            break;
            case "ListString":
            v0 = "List.ListString";
            break;
            case "ListMap":
            v0 = "List.ListMap";
            break;
            case "List":
            v0 = "List";
            break;
            case "View":
            v0 = "View";
            break;
            case "TextView":
            v0 = "View.Clickable.TextView";
            break;
            case "Button":
            v0 = "View.Clickable.TextView.Button";
            break;
            case "EditText":
            v0 = "View.Clickable.TextView.EditText";
            break;
            case "ImageView":
            v0 = "View.Clickable.ImageView";
            break;
            case "CheckBox":
            v0 = "View.Clickable.TextView.Button.CompoundButton.CheckBox";
            break;
            case "Spinner":
            v0 = "View.AdapterView.AbsSpinner.Spinner";
            break;
            case "ListView":
            v0 = "View.AdapterView.AbsListView.ListView";
            break;
            case "WebView":
            v0 = "View.AbsoluteLayout.WebView";
            break;
            case "Switch":
            v0 = "View.Clickable.TextView.Button.CompoundButton.Switch";
            break;
            case "SeekBar":
            v0 = "View.SeekBar";
            break;
            case "CalendarView":
            v0 = "View.FrameLayout.CalendarView";
            break;
            case "AdView":
            v0 = "View.AdView";
            break;
            case "MapView":
            v0 = "View.MapView";
            break;
            case "FloatingActionButton":
            v0 = "View.Clickable.FloatingActionButton";
            break;
            case "LinearLayout":
            v0 = "View.Clickable.ViewGroup.LinearLayout";
            break;
            case "ScrollView":
            v0 = "View.ViewGroup.FrameLayout.ScrollView";
            break;
            case "HorizontalScrollView":
            v0 = "View.ViewGroup.FrameLayout.HorizontalScrollView";
            break;
            case "Intent":
            v0 = "Component.Intent";
            break;
            case "SharedPreferences":
            v0 = "Component.SharedPreferences";
            break;
            case "Calendar":
            v0 = "Component.Calendar";
            break;
            case "Vibrator":
            v0 = "Component.Vibrator";
            break;
            case "Timer":
            v0 = "Component.Timer";
            break;
            case "Dialog":
            v0 = "Component.Dialog";
            break;
            case "MediaPlayer":
            v0 = "Component.MediaPlayer";
            break;
            case "SoundPool":
            v0 = "Component.SoundPool";
            break;
            case "ObjectAnimator":
            v0 = "Component.ObjectAnimator";
            break;
            case "FirebaseDB":
            v0 = "Component.FirebaseDB";
            break;
            case "FirebaseAuth":
            v0 = "Component.FirebaseAuth";
            break;
            case "Gyroscope":
            v0 = "Component.Gyroscope";
            break;
            case "FirebaseStorage":
            v0 = "Component.FirebaseStorage";
            break;
            case "Camera":
            v0 = "Component.Camera";
            break;
            case "FilePicker":
            v0 = "Component.FilePicker";
            break;
            case "RequestNetwork":
            v0 = "Component.RequestNetwork";
            break;
            case "ProgressBar":
            v0 = "View.SeekBar.ProgressBar";
            break;
            case "TextToSpeech":
            v0 = "Component.TextToSpeech";
            break;
            case "SpeechToText":
            v0 = "Component.SpeechToText";
            break;
            case "BluetoothConnect":
            v0 = "Component.BluetoothConnect";
            break;
            case "LocationManager":
            v0 = "Component.LocationManager";
            break;
            default:
            v0 = "";
            break;
        }
        
        this.b = v0.split("\\.");
    }
    
    
    public String a() {
        return this.f251a;
    }
    
    public boolean a(hc hcVar) {
        return a(hcVar.f251a);
    }
    
    public boolean a(String str) {
        if (str.equals(this.f251a)) {
            return true;
        }
        for (String equals : this.b) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean b(String str) {
        return this.f251a.equals(str);
    }
    
    public boolean b() {
        return a("Var");
    }
    
    public boolean c() {
        return a("List");
    }
    
    public boolean d() {
        return a("View");
    }
}
