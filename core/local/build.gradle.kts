import kr.co.wdtt.convention.Local

plugins {
    alias(libs.plugins.nbdream.android.library)
    alias(libs.plugins.nbdream.android.hilt)
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

    Local()
}