# Add project specific ProGuard rules here.
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver

# Keep Compose related classes
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**
