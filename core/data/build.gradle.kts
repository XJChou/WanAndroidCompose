plugins {
    id("android.library")
    id("android.hilt")
//    id("kotlinx-serialization")
}

android {
    namespace = "com.zxj.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))

    // page3
    testImplementation(libs.paging.tests)
    api(libs.paging)
    api(libs.paging.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
//    implementation(libs.kotlinx.serialization.json)


}