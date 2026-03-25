package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;

public class ex {
    
    /* renamed from: a  reason: collision with root package name */
    public static final String[] f126a = {"onBackPressed", "onPostCreate", "onStart", "onResume", "onPause", "onStop", "onDestroy"};
    
    public static String[] a() {
        return f126a;
    }
    
    public static String[] a(hc hcVar) {
        ArrayList arrayList = new ArrayList();
        if (hcVar.a("Clickable")) {
            arrayList.add("onClick");
        }
        if (hcVar.a("EditText")) {
            arrayList.add("onTextChanged");
        }
        if (hcVar.a("CompoundButton")) {
            arrayList.add("onCheckedChange");
        }
        if (hcVar.b("SeekBar")) {
            arrayList.add("onProgressChanged");
            arrayList.add("onStartTrackingTouch");
            arrayList.add("onStopTrackingTouch");
        }
        if (hcVar.a("Spinner")) {
            arrayList.add("onItemSelected");
        }
        if (hcVar.a("ListView")) {
            arrayList.add("onItemClicked");
            arrayList.add("onItemLongClicked");
            arrayList.add("onBindCustomView");
        }
        if (hcVar.a("WebView")) {
            arrayList.add("onPageStarted");
            arrayList.add("onPageFinished");
        }
        if (hcVar.a("CalendarView")) {
            arrayList.add("onDateChange");
        }
        if (hcVar.a("MapView")) {
            arrayList.add("onMapReady");
            arrayList.add("onMarkerClicked");
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
    
    public static String[] b(hc hcVar) {
        ArrayList arrayList = new ArrayList();
        if (hcVar.a("ObjectAnimator")) {
            arrayList.add("onAnimationStart");
            arrayList.add("onAnimationEnd");
            arrayList.add("onAnimationCancel");
        }
        if (hcVar.a("FirebaseDB")) {
            arrayList.add("onChildAdded");
            arrayList.add("onChildChanged");
            arrayList.add("onChildRemoved");
            arrayList.add("onCancelled");
        }
        if (hcVar.a("FirebaseAuth")) {
            arrayList.add("onCreateUserComplete");
            arrayList.add("onSignInUserComplete");
            arrayList.add("onResetPasswordEmailSent");
        }
        if (hcVar.a("Gyroscope")) {
            arrayList.add("onSensorChanged");
        }
        if (hcVar.a("InterstitialAd")) {
            arrayList.add("onAdLoaded");
            arrayList.add("onAdFailedToLoad");
            arrayList.add("onAdOpened");
            arrayList.add("onAdClosed");
        }
        if (hcVar.a("FirebaseStorage")) {
            arrayList.add("onUploadProgress");
            arrayList.add("onDownloadProgress");
            arrayList.add("onUploadSuccess");
            arrayList.add("onDownloadSuccess");
            arrayList.add("onDeleteSuccess");
            arrayList.add("onFailure");
        }
        if (hcVar.a("Camera")) {
            arrayList.add("onPictureTaken");
            arrayList.add("onPictureTakenCancel");
        }
        if (hcVar.a("FilePicker")) {
            arrayList.add("onFilesPicked");
            arrayList.add("onFilesPickedCancel");
        }
        if (hcVar.a("RequestNetwork")) {
            arrayList.add("onResponse");
            arrayList.add("onErrorResponse");
        }
        if (hcVar.a("SpeechToText")) {
            arrayList.add("onSpeechResult");
            arrayList.add("onSpeechError");
        }
        if (hcVar.a("BluetoothConnect")) {
            arrayList.add("onConnected");
            arrayList.add("onDataReceived");
            arrayList.add("onDataSent");
            arrayList.add("onConnectionError");
            arrayList.add("onConnectionStopped");
        }
        if (hcVar.a("LocationManager")) {
            arrayList.add("onLocationChanged");
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
    
    public static String[] c(hc hcVar) {
        ArrayList arrayList = new ArrayList();
        if (hcVar.a("Clickable")) {
            arrayList.add("onClickListener");
        }
        if (hcVar.a("EditText")) {
            arrayList.add("onTextChangedListener");
        }
        if (hcVar.a("CompoundButton")) {
            arrayList.add("onCheckChangedListener");
        }
        if (hcVar.b("SeekBar")) {
            arrayList.add("onSeekBarChangeListener");
        }
        if (hcVar.a("Spinner")) {
            arrayList.add("onItemSelectedListener");
        }
        if (hcVar.a("ListView")) {
            arrayList.add("onItemClickListener");
            arrayList.add("onItemLongClickListener");
        }
        if (hcVar.a("WebView")) {
            arrayList.add("webViewClient");
        }
        if (hcVar.a("CalendarView")) {
            arrayList.add("onDateChangeListener");
        }
        if (hcVar.a("MapView")) {
            arrayList.add("onMapReadyCallback");
            arrayList.add("onMapMarkerClickListener");
        }
        if (hcVar.a("ObjectAnimator")) {
            arrayList.add("animatorListener");
        }
        if (hcVar.a("FirebaseDB")) {
            arrayList.add("childEventListener");
        }
        if (hcVar.a("FirebaseAuth")) {
            arrayList.add("authCreateUserComplete");
            arrayList.add("authSignInUserComplete");
            arrayList.add("authResetEmailSent");
        }
        if (hcVar.a("Gyroscope")) {
            arrayList.add("sensorEventListener");
        }
        if (hcVar.a("InterstitialAd")) {
            arrayList.add("adListener");
        }
        if (hcVar.a("FirebaseStorage")) {
            arrayList.add("onUploadProgressListener");
            arrayList.add("onDownloadProgressListener");
            arrayList.add("onUploadSuccessListener");
            arrayList.add("onDownloadSuccessListener");
            arrayList.add("onDeleteSuccessListener");
            arrayList.add("onFailureListener");
        }
        if (hcVar.a("RequestNetwork")) {
            arrayList.add("requestListener");
        }
        if (hcVar.a("SpeechToText")) {
            arrayList.add("recognitionListener");
        }
        if (hcVar.a("BluetoothConnect")) {
            arrayList.add("bluetoothConnectionListener");
        }
        if (hcVar.a("LocationManager")) {
            arrayList.add("locationListener");
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
    
    public static String[] a(String listenerName) {
        List<String> eventMethods = new ArrayList<>();
        
        int p0 = -1; 
        
        switch (listenerName) {
            case "childEventListener": p0 = 0xa; break;
            case "onUploadProgressListener": p0 = 0x10; break;
            case "onCheckChangedListener": p0 = 0x2; break;
            case "authResetEmailSent": p0 = 0xe; break;
            case "locationListener": p0 = 0x1b; break;
            case "bluetoothConnectionListener": p0 = 0x18; break;
            case "onDateChangeListener": p0 = 0x8; break;
            case "onUploadSuccessListener": p0 = 0x12; break;
            case "onItemLongClickListener": p0 = 0x6; break;
            case "onSeekBarChangeListener": p0 = 0x3; break;
            case "authSignInUserComplete": p0 = 0xd; break;
            case "onItemClickListener": p0 = 0x5; break;
            case "animatorListener": p0 = 0x9; break;
            case "onMapReadyCallback": p0 = 0x19; break;
            case "onItemSelectedListener": p0 = 0x4; break;
            case "requestListener": p0 = 0x16; break;
            case "webViewClient": p0 = 0x7; break;
            case "authCreateUserComplete": p0 = 0xc; break;
            case "adListener": p0 = 0xf; break;
            case "onMapMarkerClickListener": p0 = 0x1a; break;
            case "onDownloadProgressListener": p0 = 0x11; break;
            case "onFailureListener": p0 = 0x15; break;
            case "recognitionListener": p0 = 0x17; break;
            case "onDownloadSuccessListener": p0 = 0x13; break;
            case "onDeleteSuccessListener": p0 = 0x14; break;
            case "onTextChangedListener": p0 = 0x1; break;
            case "sensorEventListener": p0 = 0xb; break;
            case "onClickListener": p0 = 0x0; break;
        }
        
        switch (p0) {
            case 0x0: eventMethods.add("onClick"); break;
            case 0x1: 
            eventMethods.add("onTextChanged");
            eventMethods.add("beforeTextChanged");
            eventMethods.add("afterTextChanged");
            break;
            case 0x2: eventMethods.add("onCheckedChange"); break;
            case 0x3: 
            eventMethods.add("onProgressChanged");
            eventMethods.add("onStartTrackingTouch");
            eventMethods.add("onStopTrackingTouch");
            break;
            case 0x4: 
            eventMethods.add("onItemSelected");
            eventMethods.add("onNothingSelected");
            break;
            case 0x5: eventMethods.add("onItemClicked"); break;
            case 0x6: eventMethods.add("onItemLongClicked"); break;
            case 0x7: 
            eventMethods.add("onPageStarted");
            eventMethods.add("onPageFinished");
            break;
            case 0x8: eventMethods.add("onDateChange"); break;
            case 0x9: 
            eventMethods.add("onAnimationStart");
            eventMethods.add("onAnimationEnd");
            eventMethods.add("onAnimationCancel");
            eventMethods.add("onAnimationRepeat");
            break;
            case 0xa: 
            eventMethods.add("onChildAdded");
            eventMethods.add("onChildChanged");
            eventMethods.add("onChildMoved");
            eventMethods.add("onChildRemoved");
            eventMethods.add("onCancelled");
            break;
            case 0xb: 
            eventMethods.add("onSensorChanged");
            eventMethods.add("onAccuracyChanged");
            break;
            case 0xc: eventMethods.add("onCreateUserComplete"); break;
            case 0xd: eventMethods.add("onSignInUserComplete"); break;
            case 0xe: eventMethods.add("onResetPasswordEmailSent"); break;
            case 0xf: 
            eventMethods.add("onAdLoaded");
            eventMethods.add("onAdFailedToLoad");
            eventMethods.add("onAdOpened");
            eventMethods.add("onAdClosed");
            break;
            case 0x10: eventMethods.add("onUploadProgress"); break;
            case 0x11: eventMethods.add("onDownloadProgress"); break;
            case 0x12: eventMethods.add("onUploadSuccess"); break;
            case 0x13: eventMethods.add("onDownloadSuccess"); break;
            case 0x14: eventMethods.add("onDeleteSuccess"); break;
            case 0x15: eventMethods.add("onFailure"); break;
            case 0x16: 
            eventMethods.add("onResponse");
            eventMethods.add("onErrorResponse");
            break;
            case 0x17: 
            eventMethods.add("onSpeechResult");
            eventMethods.add("onSpeechError");
            break;
            case 0x18: 
            eventMethods.add("onConnected");
            eventMethods.add("onDataReceived");
            eventMethods.add("onDataSent");
            eventMethods.add("onConnectionError");
            eventMethods.add("onConnectionStopped");
            break;
            case 0x19: eventMethods.add("onMapReady"); break;
            case 0x1a: eventMethods.add("onMarkerClicked"); break;
            case 0x1b: eventMethods.add("onLocationChanged"); break;
            default: break;
        }
        
        return eventMethods.toArray(new String[0]);
    }
    
    
    public static String a(String paramString, Context paramContext) {
        String str = "";
        int i = paramString.hashCode();
        int index = -1;
        switch (i) {
            case 0x7c694278: // "onFilesPicked"
            if (paramString.equals("onFilesPicked")) index = 0x2d; break;
            case 0x75fb4119: // "onItemLongClicked"
            if (paramString.equals("onItemLongClicked")) index = 0xf; break;
            case 0x6e9c1c20: // "onAdFailedToLoad"
            if (paramString.equals("onAdFailedToLoad")) index = 0x22; break;
            case 0x6b7b22ee: // "onMarkerClicked"
            if (paramString.equals("onMarkerClicked")) index = 0x39; break;
            case 0x6a7a6638: // "moreBlock"
            if (paramString.equals("moreBlock")) index = 0xa; break;
            case 0x68baa312: // "onFilesPickedCancel"
            if (paramString.equals("onFilesPickedCancel")) index = 0x2e; break;
            case 0x65f3cf93: // "onPageStarted"
            if (paramString.equals("onPageStarted")) index = 0x8; break;
            case 0x65a871a9: // "onCreateUserComplete"
            if (paramString.equals("onCreateUserComplete")) index = 0x1e; break;
            case 0x6160917f: // "onStopTrackingTouch"
            if (paramString.equals("onStopTrackingTouch")) index = 0x13; break;
            case 0x5e88f1c7: // "onResume"
            if (paramString.equals("onResume")) index = 6; break;
            case 0x57429eec: // "onDownloadSuccess"
            if (paramString.equals("onDownloadSuccess")) index = 0x28; break;
            case 0x5329367c: // "onPictureTakenCancel"
            if (paramString.equals("onPictureTakenCancel")) index = 0x2c; break;
            case 0x50621682: // "onDownloadProgress"
            if (paramString.equals("onDownloadProgress")) index = 0x26; break;
            case 0x505f9ad4: // "onPictureTaken"
            if (paramString.equals("onPictureTaken")) index = 0x2b; break;
            case 0x45c809e8: // "onAdOpened"
            if (paramString.equals("onAdOpened")) index = 0x23; break;
            case 0x3883fd6b: // "onSensorChanged"
            if (paramString.equals("onSensorChanged")) index = 0x1d; break;
            case 0x3379c5fb: // "onAdLoaded"
            if (paramString.equals("onAdLoaded")) index = 0x21; break;
            case 0x30062a35: // "onItemClicked"
            if (paramString.equals("onItemClicked")) index = 0xe; break;
            case 0x2966971e: // "onSpeechResult"
            if (paramString.equals("onSpeechResult")) index = 0x31; break;
            case 0x23d6166e: // "onAdClosed"
            if (paramString.equals("onAdClosed")) index = 0x24; break;
            case 0x1a926632: // "onCancelled"
            if (paramString.equals("onCancelled")) index = 0x1c; break;
            case 0x16898168: // "onTextChanged"
            if (paramString.equals("onTextChanged")) index = 0x10; break;
            case 0x16352c83: // "onChildAdded"
            if (paramString.equals("onChildAdded")) index = 0x19; break;
            case 0xfbc7161: // "onDataSent"
            if (paramString.equals("onDataSent")) index = 0x35; break;
            case 0xee232ab: // "onFailure"
            if (paramString.equals("onFailure")) index = 0x2a; break;
            case 0xc2f8cfb: // "onPostCreate"
            if (paramString.equals("onPostCreate")) index = 2; break;
            case 0x9a95992: // "onBindCustomView"
            if (paramString.equals("onBindCustomView")) index = 0x17; break;
            case 0x827d33f: // "onAnimationCancel"
            if (paramString.equals("onAnimationCancel")) index = 0x16; break;
            case 0x4ce1b23: // "onUploadSuccess"
            if (paramString.equals("onUploadSuccess")) index = 0x27; break;
            case -0x16695936: // "onErrorResponse"
            if (paramString.equals("onErrorResponse")) index = 0x30; break;
            case -0x17281252: // "initializeLogic"
            if (paramString.equals("initializeLogic")) index = 0; break;
            case -0x1ce170dd: // "onChildRemoved"
            if (paramString.equals("onChildRemoved")) index = 0x1b; break;
            case -0x1e1dec60: // "onPageFinished"
            if (paramString.equals("onPageFinished")) index = 9; break;
            case -0x1e4265b3: // "onItemSelected"
            if (paramString.equals("onItemSelected")) index = 0xd; break;
            case -0x1ff677d7: // "onResetPasswordEmailSent"
            if (paramString.equals("onResetPasswordEmailSent")) index = 0x20; break;
            case -0x22dce568: // "onCheckedChange"
            if (paramString.equals("onCheckedChange")) index = 0xc; break;
            case -0x245bd016: // "onConnected"
            if (paramString.equals("onConnected")) index = 0x33; break;
            case -0x281d0d03: // "onAnimationStart"
            if (paramString.equals("onAnimationStart")) index = 0x14; break;
            case -0x2ae8b215: // "onConnectionError"
            if (paramString.equals("onConnectionError")) index = 0x36; break;
            case -0x2bad5f10: // "onConnectionStopped"
            if (paramString.equals("onConnectionStopped")) index = 0x37; break;
            case -0x2ca8b4f3: // "onUploadProgress"
            if (paramString.equals("onUploadProgress")) index = 0x25; break;
            case -0x30f07aa0: // "onLocationChanged"
            if (paramString.equals("onLocationChanged")) index = 0x3a; break;
            case -0x31ea2689: // "onChildChanged"
            if (paramString.equals("onChildChanged")) index = 0x1a; break;
            case -0x352aabc3: // "onDateChange"
            if (paramString.equals("onDateChange")) index = 0x18; break;
            case -0x3c607d7f: // "onStop"
            if (paramString.equals("onStop")) index = 4; break;
            case -0x423c3a24: // "onBackPressed"
            if (paramString.equals("onBackPressed")) index = 1; break;
            case -0x44c55dca: // "onAnimationEnd"
            if (paramString.equals("onAnimationEnd")) index = 0x15; break;
            case -0x48706fc7: // "onDeleteSuccess"
            if (paramString.equals("onDeleteSuccess")) index = 0x29; break;
            case -0x4faf663d: // "onStart"
            if (paramString.equals("onStart")) index = 3; break;
            case -0x4fe204a9: // "onPause"
            if (paramString.equals("onPause")) index = 7; break;
            case -0x50946517: // "onClick"
            if (paramString.equals("onClick")) index = 0xb; break;
            case -0x50f79f5a: // "onMapReady"
            if (paramString.equals("onMapReady")) index = 0x38; break;
            case -0x53865ee5: // "onDestroy"
            if (paramString.equals("onDestroy")) index = 5; break;
            case -0x65d79cbb: // "onSignInUserComplete"
            if (paramString.equals("onSignInUserComplete")) index = 0x1f; break;
            case -0x6a12d418: // "onProgressChanged"
            if (paramString.equals("onProgressChanged")) index = 0x11; break;
            case -0x6bd580d6: // "onDataReceived"
            if (paramString.equals("onDataReceived")) index = 0x34; break;
            case -0x6f2ec8c0: // "onResponse"
            if (paramString.equals("onResponse")) index = 0x2f; break;
            case -0x7b3a6119: // "onSpeechError"
            if (paramString.equals("onSpeechError")) index = 0x32; break;
            case -0x7d9d66b5: // "onStartTrackingTouch"
            if (paramString.equals("onStartTrackingTouch")) index = 0x12; break;
            case -0x7e3cca3b: // "onLocationChanged" (wait, duplicate? No, check hashes)
        }
        
        Resources localResources = paramContext.getResources();
        switch (index) {
            case 0x3a: return kq.a().a(localResources, R.string.event_on_location_changed);          // on Location Changed
            case 0x39: return kq.a().a(localResources, R.string.event_on_marker_clicked);           // on Marker Clicked
            case 0x38: return kq.a().a(localResources, R.string.event_on_map_ready);               // on Map Ready
            case 0x37: return kq.a().a(localResources, R.string.event_on_connection_stopped);      // on Connection Stopped
            case 0x36: return kq.a().a(localResources, R.string.event_on_connection_error);        // on Connection Error
            case 0x35: return kq.a().a(localResources, R.string.event_on_data_sent);               // on Data Sent
            case 0x34: return kq.a().a(localResources, R.string.event_on_data_received);           // on Data Received
            case 0x33: return kq.a().a(localResources, R.string.event_on_connected);               // on Connected
            case 0x32: return kq.a().a(localResources, R.string.event_on_speech_error);            // on Speech Error
            case 0x31: return kq.a().a(localResources, R.string.event_on_speech_result);           // on Speech Result
            case 0x30: return kq.a().a(localResources, R.string.event_on_error_response);          // on Error Response
            case 0x2f: return kq.a().a(localResources, R.string.event_on_response);                // on Response
            case 0x2e: return kq.a().a(localResources, R.string.event_onfilespickedcancel);        // on FilesPicked Cancel
            case 0x2d: return kq.a().a(localResources, R.string.event_onfilespicked);              // on FilesPicked
            case 0x2c: return kq.a().a(localResources, R.string.event_onpicturetakencancel);       // on PictureTaken Cancel
            case 0x2b: return kq.a().a(localResources, R.string.event_onpicturetaken);             // on PictureTaken
            case 0x2a: return kq.a().a(localResources, R.string.event_onfailure);                  // on Failure
            case 0x29: return kq.a().a(localResources, R.string.event_ondeletesuccess);            // on Delete Success
            case 0x28: return kq.a().a(localResources, R.string.event_ondownloadsuccess);           // on Download Success
            case 0x27: return kq.a().a(localResources, R.string.event_onuploadsuccess);            // on Upload Success
            case 0x26: return kq.a().a(localResources, R.string.event_ondownloadprogress);         // on Download Progress
            case 0x25: return kq.a().a(localResources, R.string.event_onuploadprogress);           // on Upload Progress
            case 0x24: return kq.a().a(localResources, R.string.event_onadclosed);                 // on Ad Closed
            case 0x23: return kq.a().a(localResources, R.string.event_onadopened);                 // on Ad Opened
            case 0x22: return kq.a().a(localResources, R.string.event_onadfailedtoload);           // on Ad Failed To Load
            case 0x21: return kq.a().a(localResources, R.string.event_onadloaded);                 // on Ad Loaded
            case 0x20: return kq.a().a(localResources, R.string.event_onresetpasswordemailsent);   // on Reset Password Email Sent
            case 0x1f: return kq.a().a(localResources, R.string.event_onsigninusercomplete);       // on SignIn User Complete
            case 0x1e: return kq.a().a(localResources, R.string.event_oncreateusercomplete);       // on Create User Complete
            case 0x1d: return kq.a().a(localResources, R.string.event_onsensorchanged);            // on Sensor Changed
            case 0x1c: return kq.a().a(localResources, R.string.event_oncancelled);                // on Cancelled
            case 0x1b: return kq.a().a(localResources, R.string.event_onchildremoved);             // on Child Removed
            case 0x1a: return kq.a().a(localResources, R.string.event_onchildchanged);             // on Child Changed
            case 0x19: return kq.a().a(localResources, R.string.event_onchildadded);               // on Child Added
            case 0x18: return kq.a().a(localResources, R.string.event_ondatechange);               // on Date Change
            case 0x17: return kq.a().a(localResources, R.string.event_onbindcustomview);           // on Bind Custom View
            case 0x16: return kq.a().a(localResources, R.string.event_onanimationcancel);          // on Animation Cancel
            case 0x15: return kq.a().a(localResources, R.string.event_onanimationend);             // on Animation End
            case 0x14: return kq.a().a(localResources, R.string.event_onanimationstart);           // on Animation Start
            case 0x13: return kq.a().a(localResources, R.string.event_onstoptrackingtouch);        // on Stop Tracking Touch
            case 0x12: return kq.a().a(localResources, R.string.event_onstarttrackingtouch);       // on Start Tracking Touch
            case 0x11: return kq.a().a(localResources, R.string.event_onprogresschanged);          // on Progress Changed
            case 0x10: return kq.a().a(localResources, R.string.event_ontextchanged);              // on Text Changed
            case 0xf:  return kq.a().a(localResources, R.string.event_onitemlongclicked);           // on Item Long Clicked
            case 0xe:  return kq.a().a(localResources, R.string.event_onitemclicked);              // on Item Clicked
            case 0xd:  return kq.a().a(localResources, R.string.event_onitemselected);             // on Item Selected
            case 0xc:  return kq.a().a(localResources, R.string.event_oncheckchanged);             // on Check Changed
            case 0xb:  return kq.a().a(localResources, R.string.event_onclick);                    // on Click
            case 0xa:  return kq.a().a(localResources, R.string.event_definefunc);                 // Define Function
            case 9:    return kq.a().a(localResources, R.string.event_onpagefinished);             // on Page Finished
            case 8:    return kq.a().a(localResources, R.string.event_onpagestarted);              // on Page Started
            case 7:    return kq.a().a(localResources, R.string.event_onpause);                    // on Pause
            case 6:    return kq.a().a(localResources, R.string.event_onresume);                   // on Resume
            case 5:    return kq.a().a(localResources, R.string.event_ondestroy);                  // on Destroy
            case 4:    return kq.a().a(localResources, R.string.event_onstop);                     // on Stop
            case 3:    return kq.a().a(localResources, R.string.event_onstart);                    // on Start
            case 2:    return kq.a().a(localResources, R.string.event_onpostcreated);              // on Post Create
            case 1:    return kq.a().a(localResources, R.string.event_onbackpressed);              // on Back Pressed
            case 0:    return kq.a().a(localResources, R.string.event_initialize);                 // Initialize
            default:   return str;
        }
    } 
    public static int b(String eventName) {
        switch (eventName) {

            case "initializeLogic":
            case "onBackPressed":
            case "onPostCreate":
            case "onStart":
            case "onResume":
            case "onPause":
            case "onStop":
            case "onDestroy":
            return R.drawable.event_on_click_48dp; // 0x7f070094
            

            case "onClick":                     return R.drawable.event_on_click_48dp;
            case "onCheckChanged":              return R.drawable.event_on_check_changed_48dp;
            case "onItemSelected":              return R.drawable.event_on_item_selected_48dp;
            case "onItemClicked":               return R.drawable.event_on_item_clicked_48dp;
            case "onItemLongClicked":           return R.drawable.event_on_item_long_clicked_48dp;
            case "onTextChanged":               return R.drawable.event_on_text_changed_48dp;
            case "onStartTrackingTouch":        return R.drawable.event_on_start_tracking_touch_48dp;
            case "onStopTrackingTouch":         return R.drawable.event_on_stop_tracking_touch_48dp;
            case "onProgressChanged":           return R.drawable.event_on_progress_changed_48dp;
            

            case "onPageStarted":               return R.drawable.event_on_page_started_48dp;
            case "onPageFinished":              return R.drawable.event_on_page_finished_48dp;
            

            case "onAnimationStart":            return R.drawable.event_on_animation_start_48dp;
            case "onAnimationEnd":              return R.drawable.event_on_animation_end_48dp;
            case "onAnimationCancel":           return R.drawable.event_on_animation_cancel_48dp;
            case "onAnimationRepeat":           return R.drawable.event_animation_repeat_48dp;
            

            case "onBindCustomView":            return R.drawable.event_on_bind_custom_view_48dp;
            

            case "onDateChange":                return R.drawable.event_on_date_changed_48dp;
            

            case "onChildAdded":                return R.drawable.event_on_child_added_48dp;
            case "onChildChanged":              return R.drawable.event_on_child_changed_48dp;
            case "onChildMoved":                return R.drawable.event_on_child_moved_48dp;
            case "onChildRemoved":              return R.drawable.event_on_child_removed_48dp;
            case "onCancelled":                 return R.drawable.event_on_cancelled_48dp;
            

            case "onCreateUserComplete":        return R.drawable.event_on_create_user_complete_48dp;
            case "onSignInUserComplete":        return R.drawable.event_on_signin_complete_48dp;
            case "onResetPasswordEmailSent":    return R.drawable.event_on_reset_password_email_sent_48dp;
            

            case "onSensorChanged":             return R.drawable.event_on_sensor_changed_48dp;
            case "onAccuracyChanged":           return R.drawable.event_on_accuracy_changed_48dp;
            

            case "onAdLoaded":                  return R.drawable.event_on_ad_loaded;
            case "onAdFailedToLoad":            return R.drawable.event_on_ad_failed_to_load;
            case "onAdOpened":                  return R.drawable.event_on_ad_opened;
            case "onAdClosed":                  return R.drawable.event_on_ad_closed;
            

            case "onUploadProgress":            return R.drawable.event_on_upload_progress_48dp;
            case "onDownloadProgress":          return R.drawable.event_on_download_progress_48dp;
            case "onUploadSuccess":             return R.drawable.event_on_upload_success_48dp;
            case "onDownloadSuccess":           return R.drawable.event_on_download_success_48dp;
            case "onDeleteSuccess":             return R.drawable.event_on_click_48dp;
            case "onFailure":                   return R.drawable.event_on_click_48dp;
            

            case "onPictureTaken":              return R.drawable.event_on_picture_taken_48dp;
            case "onPictureTakenCancel":        return R.drawable.event_on_picture_taken_cancel_48dp;
            case "onFilesPicked":               return R.drawable.event_on_file_picked_48dp;
            case "onFilesPickedCancel":         return R.drawable.event_on_file_picked_cancel_48dp;
            

            case "onResponse":                  return R.drawable.event_on_response_48dp;
            case "onErrorResponse":             return R.drawable.event_on_error_response_48dp;
            

            case "onSpeechResult":              return R.drawable.event_on_speech_result;
            case "onSpeechError":               return R.drawable.event_on_speech_error;
            

            case "onConnected":                 return R.drawable.event_on_connected_96;
            case "onDataReceived":              return R.drawable.event_on_data_received_96;
            case "onDataSent":                  return R.drawable.event_on_data_sent_96;
            case "onConnectionError":           return R.drawable.event_on_connection_error_96;
            case "onConnectionStopped":         return R.drawable.event_on_connection_stopped_96;
            

            case "onLocationChanged":           return R.drawable.event_on_location_changed_96;
            case "onMapReady":                  return R.drawable.event_on_map_ready_96;
            case "onMarkerClicked":             return R.drawable.event_on_marker_clicked_96;
            
            default:
            return R.drawable.event_on_click_48dp; // fallback
        }
    }
}
