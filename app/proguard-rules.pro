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

# io.coil - START
# https://github.com/coil-kt/coil#r8--proguard
# io.coilはアプリ開発者側で追加で設定は不要.
# io.coil - END

# dagger Hilt - START
# https://github.com/google/dagger/issues/2291#issuecomment-764518175
-keepnames class dagger.**
# dagger Hilt - END