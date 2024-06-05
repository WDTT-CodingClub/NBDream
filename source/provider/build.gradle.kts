plugins {
    alias(libs.plugins.nbdream.android.library)
    alias(libs.plugins.nbdream.android.hilt)
}

android {
    namespace = "kr.co.nbdream.source.provider"
}

dependencies {
    implementation(projects.core.domain)
}