# Keep the public picker API and serializable file model when consumer shrinking is enabled.
-keep public class com.nexusteam.filepicker.elitefileselector.** { public *; }
-keepclassmembers class com.nexusteam.filepicker.elitefileselector.** implements java.io.Serializable { *; }
