plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.hilt.android)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "kr.co.wdtt.nbdream"
    compileSdk = 34

    defaultConfig {
        applicationId = "kr.co.wdtt.nbdream"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //kotlin
    implementation(libs.jetbrains.kotlin.reflect)

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    //retrofit converter 협의 필요
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // kotlinx serialization
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.converter)

    // tikxml (retrofit xml converter)
//    implementation(libs.tikxml.annotation)
//    implementation(libs.tikxml.core)
//    implementation(libs.tikxml.processor)
//    implementation(libs.tikxml.retrofit.converter)

    //google
    implementation(libs.play.services.auth)

    //kakao
    implementation (libs.v2.all) // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation (libs.v2.user) // 카카오 로그인 API 모듈
    implementation (libs.v2.cert) // 카카오톡 인증 서비스 API 모듈

    //composeNav
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.fragment)

    //Compose ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // coil
    implementation(libs.coil)
    implementation(libs.coil.compose)
}