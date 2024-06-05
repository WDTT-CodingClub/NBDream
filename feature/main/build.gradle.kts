plugins {
    alias(libs.plugins.nbdream.android.feature)
    alias(libs.plugins.nbdream.android.library.compose)
}

android {
    namespace = "kr.co.main"
}

dependencies {
    implementation(projects.core.common)
}