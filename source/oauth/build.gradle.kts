import kr.co.wdtt.convention.Social

plugins {
    alias(libs.plugins.nbdream.android.library)
    alias(libs.plugins.nbdream.android.hilt)
    alias(libs.plugins.sgp)
}

android {
    namespace = "kr.co.nbdream.source.oauth"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.common)

    Social()
}