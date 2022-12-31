# retrace setting. -START
-keepattributes LineNumberTable,SourceFile
# retrace setting. -END

# Logを削除する - START
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}
# Logを削除する - END

# Parcelable - START
# https://github.com/krschultz/android-proguard-snippets/blob/master/libraries/proguard-parceler.pro
# Parceler rules
# Source: https://github.com/johncarl81/parceler#configuring-proguard
-keep class * implements android.os.Parcelable {
#noinspection ShrinkerUnresolvedReference
    public static final android.os.Parcelable$Creator *;
}
# Parcelable - END

# io.ktor -START
# https://github.com/ktorio/ktor/issues/379#issuecomment-453728961
-keep class io.ktor.** { *; }
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.atomicfu.**
-dontwarn io.netty.**
-dontwarn com.typesafe.**
-dontwarn org.slf4j.**
# io.ktor -END

# io.coil - START
# https://github.com/coil-kt/coil#r8--proguard
# io.coilはアプリ開発者側で追加で設定は不要.
# io.coil - END

# dagger Hilt - START
# https://github.com/google/dagger/issues/2291#issuecomment-764518175
-keepnames class dagger.**
# dagger Hilt - END

# kotlinx-serialization - START
# https://github.com/Kotlin/kotlinx.serialization#android
# Keep `Companion` object fields of serializable classes.
# This avoids serializer lookup through `getDeclaredClasses` as done for named companion objects.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Keep `serializer()` on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# @Serializable and @Polymorphic are used at runtime for polymorphic serialization.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

# Serializer for classes with named companion objects are retrieved using `getDeclaredClasses`.
# If you have any, uncomment and replace classes with those containing named companion objects.
#-keepattributes InnerClasses # Needed for `getDeclaredClasses`.
#-if @kotlinx.serialization.Serializable class
#com.example.myapplication.HasNamedCompanion, # <-- List serializable classes with named companions.
#com.example.myapplication.HasNamedCompanion2
#{
#    static **$* *;
#}
#-keepnames class <1>$$serializer { # -keepnames suffices; class is kept when serializer() is kept.
#    static <1>$$serializer INSTANCE;
#}
# kotlinx-serialization - END