/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in wr express or implied.
 * See the License for the specific language governing iting, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, eitherpermissions and
 * limitations under the License.
 */
plugins {
    id("android.library")
    id("android.library.compose")
}

android {
    namespace = "com.zxj.ui"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    implementation(libs.androidx.core.ktx)
    // compose 相关
    androidTestImplementation(libs.compose.ui.test.junit4)
    api(libs.compose.hilt.navigation)
    api(libs.compose.coil)
    api(libs.compose.activity)
    api(libs.compose.lifecycle.viewmodel)
    api(libs.compose.material)
    api(libs.compose.material3)
    api(libs.compose.ui)
    api(libs.compose.ui.tooling.preview)
    api(libs.compose.navigation)
    debugApi(libs.compose.ui.tooling)
    // accompanist
    api(libs.accompanist.webview)
    api(libs.accompanist.pager)
    api(libs.accompanist.pager.indicators)
    api(libs.accompanist.swiperefresh)
    api(libs.accompanist.systemuicontroller)
    api(libs.accompanist.navigation.animation)
    api(libs.accompanist.flowlayout)
    // 约束布局
    api(libs.constraintlayout.compose)
}
