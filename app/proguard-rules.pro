
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-dontnote okhttp3.**
-dontwarn javax.annotation.**

-keep class com.dezdeqness.tmdb.data.model.remote.** { *; }
-keep class com.dezdeqness.tmdb.data.service.** { *; }