-dontwarn org.slf4j.impl.StaticLoggerBinder
-dontwarn org.slf4j.impl.StaticMDCBinder

-keep class com.kakao.sdk.**.model.* { *; }
-keep class com.kakao.** { *; }
-keep class net.daum.mf.map.api.** { *; }
-keep class net.daum.mf.map.n.api.** { *; }


-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscry