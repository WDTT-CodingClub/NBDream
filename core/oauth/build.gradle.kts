import kr.co.wdtt.convention.Social

plugins {
    alias(libs.plugins.nbdream.android.library)
    alias(libs.plugins.nbdream.android.hilt)
    alias(libs.plugins.sgp)
}

android {
    namespace = "kr.co.nbdream.core.oauth"

    buildFeatures {
        buildConfig = true
    }

    flavorDimensions.add("environment")
    productFlavors {
        create("dev") {
            dimension = "environment"
            buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"0b14005c847047e9a5cbb49c55ad231b\"")
            manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = "0b14005c847047e9a5cbb49c55ad231b"
        }

        create("prod") {
            dimension = "environment"
            buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"4379ea24dc9518852cfd141b48a9ff5e\"")
            manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = "4379ea24dc9518852cfd141b48a9ff5e"
        }
    }

}

secrets {
    propertiesFileName = "local.properties"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.common)

    implementation(files("libs/oauth-5.9.1.arr"))

    Social()
}