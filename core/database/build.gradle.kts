plugins {
    id("android.library")
    id("android.hilt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.zxj.database"
}

dependencies {
    implementation(project(":core:model"))
    // room
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    // 协程
    implementation(libs.kotlinx.coroutines.android)
}