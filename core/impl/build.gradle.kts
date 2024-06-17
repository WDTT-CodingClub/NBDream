plugins {
    alias(libs.plugins.nbdream.android.library)
    alias(libs.plugins.nbdream.android.hilt)
}

android {
    namespace = "kr.co.nbdream.core.impl"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.common)
}