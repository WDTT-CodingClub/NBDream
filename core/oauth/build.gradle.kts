import kr.co.wdtt.convention.Social

plugins {
    alias(libs.plugins.nbdream.android.library)
    alias(libs.plugins.nbdream.android.hilt)
    alias(libs.plugins.sgp)
}

android {
    namespace = "kr.co.nbdream.core.oauth"

    defaultConfig {
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = project.findProperty("KAKAO_NATIVE_APP_KEY") ?: ""
    }

    buildFeatures {
        buildConfig = true
    }
}

secrets {
    propertiesFileName = "local.properties"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.common)

    implementation(libs.naver.auth)

    implementation(files("libs/oauth-5.9.1.arr"))

    Social()
}