# -------------------------
# General ProGuard Settings
# -------------------------
-repackageclasses
-ignorewarnings
-dontwarn
-dontnote

# -------------------------
# Kotlin Runtime Rules
# -------------------------
-keep class kotlin.** { *; }
-keep class kotlin.jvm.internal.** { *; }
-dontwarn kotlin.**

# -------------------------
# AndroidX / Multidex Rules
# -------------------------
-keep class androidx.multidex.** { *; }
-dontwarn androidx.multidex.**

# -------------------------
# Keep BlackLogics Core Classes
# -------------------------
-keep class com.besome.blacklogics.** { *; }
-keepclassmembers class com.besome.blacklogics.** { *; }
-dontwarn com.besome.blacklogics.**

# -------------------------
# Reflection & Dynamic Loading
# -------------------------
-keepclassmembers class * {
    public <init>(...);
}
-keepclassmembers class * {
    *;
}

# -------------------------
# Keep UI Components
# -------------------------
-keepclassmembers class * extends android.view.View {
    <init>(android.content.Context);
    <init>(android.content.Context, android.util.AttributeSet);
    <init>(android.content.Context, android.util.AttributeSet, int);
}

# -------------------------
# StringFog Rules
# -------------------------
-keepclassmembers class * {
    java.lang.String stringFog*(...);
}
-keep class com.besome.blacklogics.stringfog.** { *; }

# -------------------------
# Serialization & Resources
# -------------------------
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object readResolve();
}
-keepclassmembers class * implements android.os.Parcelable {
    static final android.os.Parcelable$Creator CREATOR;
}

# -------------------------
# Keep All Annotations
# -------------------------
-keepattributes *Annotation*

# -------------------------
# Optimization Settings
# -------------------------
-optimizations !code/simplification/arithmetic
