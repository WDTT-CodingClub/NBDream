import kr.co.wdtt.convention.App
import kr.co.wdtt.convention.Secrets

plugins {
    alias(libs.plugins.nbdream.android.application)
    alias(libs.plugins.nbdream.android.application.compose)
    alias(libs.plugins.nbdream.android.hilt)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "kr.co.wdtt.nbdream"
    compileSdk = 34

    defaultConfig {
        applicationId = "kr.co.wdtt.nbdream"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions.add("environment")
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"

            Secrets.DEV.toBuildConfig()
                .onEach { (name, value) ->
                    buildConfigField("String", name, value)
                }
            addManifestPlaceholders(Secrets.DEV.toManifestPlaceholders())
        }

        create("prod") {
            dimension = "environment"

            Secrets.PROD.toBuildConfig()
                .onEach { (name, value) ->
                    buildConfigField("String", name, value)
                }
            addManifestPlaceholders(Secrets.PROD.toManifestPlaceholders())
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.ui)
    implementation(projects.core.common)
    implementation(projects.core.impl)

    implementation(projects.feature.main)
    implementation(projects.feature.onboard)

    implementation(projects.source.local)
    implementation(projects.source.remote)
    implementation(projects.source.provider)

    implementation(libs.androidx.core.ktx)

    App()
}