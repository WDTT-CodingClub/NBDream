/*
 * Copyright 2023 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package kr.co.wdtt.convention

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

internal val Project.libs get() = the<LibrariesForLibs>()

fun Project.App(){
    project.dependencies {
        implementations(
            libs.androidx.core.splashscreen,
            libs.compose.activity,
            libs.compose.lifecycle.runtime,
            libs.compose.navigation,
            libs.compose.hilt.navigation,
            libs.coil,
            libs.coil.compose,
            libs.timber,
        )

        testImplementations(
            libs.junit
        )

        androidTestImplementations(
            libs.androidx.junit,
            libs.androidx.espresso.core
        )
    }
}

fun Project.Local() {
    project.dependencies {
        implementations(
            libs.bundles.rooms,
            libs.datastore.preferences
        )

        ksp(libs.room.compiler)

        testImplementations(
            libs.junit,
            libs.mockito,
            libs.mockk,
            libs.kotlinx.coroutines.test,
        )
    }
}

fun Project.Remote() {
    project.dependencies {
        implementations(
            libs.bundles.ktors
        )

        testImplementations(
            libs.junit,
            libs.mockito,
            libs.mockk,
            libs.kotlinx.coroutines.test,
        )
    }
}

fun Project.Social() {
    project.dependencies {
        implementations(
            libs.bundles.social
        )
    }
}

