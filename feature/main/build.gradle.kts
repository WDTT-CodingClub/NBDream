plugins {
    alias(libs.plugins.nbdream.android.feature)
    alias(libs.plugins.nbdream.android.library.compose)
}

android {
    namespace = "kr.co.main"

    buildFeatures {
        buildConfig = true
    }
}
