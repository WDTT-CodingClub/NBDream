import kr.co.wdtt.convention.Local

plugins {
    alias(libs.plugins.nbdream.android.library)
    alias(libs.plugins.nbdream.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

ksp {
    arg("room.generateKotlin", "true")
}

android {
    namespace = "kr.co.nbdream.core.local"

    defaultConfig {
        buildConfigField("String", "DATASTORE_NAME", "\"data_store\"")
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.common)
    implementation(libs.kotlinx.serialization.json)

    Local()
}