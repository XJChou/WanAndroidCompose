/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    `kotlin-dsl`
}

group = "com.zxj.convention.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        // 应用compose注入
        println(register("androidApplicationCompose") {
            id = "android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        })
        // 应用注入
        register("androidApplication") {
            id = "android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        // 库compose注入
        register("androidLibraryCompose") {
            id = "android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        // 库注入
        register("androidLibrary") {
            id = "android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        // 功能模块注入
        register("androidFeature") {
            id = "android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        // hilt 注入
        register("androidHilt") {
            id = "android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
    }
}
