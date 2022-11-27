plugins {
    id("android.library")
    id("android.library.compose")
}

android {
    namespace = "com.zxj.designsystem"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    // compose 相关
    api(libs.compose.material)
    api(libs.compose.material3)
    api(libs.compose.ui)
    api(libs.compose.ui.tooling.preview)
    debugApi(libs.compose.ui.tooling)
//    api(libs.androidx.compose.ui.util)
//    api(libs.androidx.compose.runtime)
}