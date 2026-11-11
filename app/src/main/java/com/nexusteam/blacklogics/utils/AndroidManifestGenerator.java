package com.besome.blacklogics.util;


public class AndroidManifestGenerator {

    /**
     * Generate AndroidManifest.xml content as String.
     *
     * @param packageName The package name for the app
     * @param appName     The app label name
     * @param launcherActivity The main launcher activity class name (e.g., .MainActivity)
     * @return Manifest content as String
     */
    public static String generateManifest(String packageName, String appName, String launcherActivity) {
        StringBuilder manifest = new StringBuilder();

        manifest.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        manifest.append("<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n");
        manifest.append("    package=\"").append(packageName).append("\">\n\n");


        manifest.append("    <uses-permission android:name=\"android.permission.INTERNET\" />\n");
        manifest.append("    <uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />\n");
        manifest.append("    <uses-permission android:name=\"android.permission.WRITE_EXTERNAL_STORAGE\" />\n\n");


        manifest.append("    <application\n");
        manifest.append("        android:allowBackup=\"true\"\n");
        manifest.append("        android:icon=\"@mipmap/ic_launcher\"\n");
        manifest.append("        android:label=\"").append(appName).append("\"\n");
        manifest.append("        android:name=\".BlackApplication\"\n");
        manifest.append("        android:requestLegacyExternalStorage=\"true\"\n");
        manifest.append("        android:usesCleartextTraffic=\"true\"\n");
        manifest.append("        android:theme=\"@style/AppTheme\">\n\n");


        manifest.append("        <activity\n");
        manifest.append("            android:name=\"").append(launcherActivity).append("\"\n");
        manifest.append("            android:configChanges=\"orientation|screenSize|keyboardHidden|smallestScreenSize|screenLayout\"\n");
        manifest.append("            android:hardwareAccelerated=\"true\"\n");
        manifest.append("            android:supportsPictureInPicture=\"true\"\n");
        manifest.append("            android:screenOrientation=\"portrait\"\n");
        manifest.append("            android:exported=\"true\">\n");
        manifest.append("            <intent-filter>\n");
        manifest.append("                <action android:name=\"android.intent.action.MAIN\" />\n");
        manifest.append("                <category android:name=\"android.intent.category.LAUNCHER\" />\n");
        manifest.append("            </intent-filter>\n");
        manifest.append("        </activity>\n\n");


        manifest.append("        <activity\n");
        manifest.append("            android:name=\".DebugActivity\"\n");
        manifest.append("            android:screenOrientation=\"portrait\"\n");
        manifest.append("            android:theme=\"@style/AppTheme.DebugActivity\" />\n\n");


        manifest.append("        <uses-library\n");
        manifest.append("            android:name=\"org.apache.http.legacy\"\n");
        manifest.append("            android:required=\"false\" />\n");

        manifest.append("    </application>\n");
        manifest.append("</manifest>");

        return manifest.toString();
    }


    public static void main(String[] args) {
        String manifestContent = generateManifest(
                "com.my.newproject2",
                "MyApp",
                ".MainActivity"
        );
        System.out.println(manifestContent);
    }
}
