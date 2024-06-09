plugins {
    alias(libs.plugins.nbdream.android.library)
    alias(libs.plugins.nbdream.android.library.compose)
}

android {
    namespace = "kr.co.nbdream.core.ui"
}

dependencies {
    implementation(projects.core.common)

    api(libs.bundles.composes)
    api(libs.bundles.paging)

    api(libs.coil)
    api(libs.coil.compose)
    api(libs.coil.gif)
    api(libs.coil.video)
}