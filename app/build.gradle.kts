plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.lapoushko.android_app_audioguide"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.lapoushko.android_app_audioguide"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(project(":common:ui"))
    implementation(libs.androidx.navigation.compose)
    implementation(project(":navigation"))
    implementation(project(":feature:search"))
    implementation(project(":feature:detail-excursion"))
    implementation(project(":feature:profile:setting"))
    implementation(project(":feature:category"))
    implementation(project(":feature:favourite"))
    implementation(project(":feature:guide:audio"))
    implementation(project(":feature:guide:map"))
    implementation(project(":feature:guide"))
    implementation(project(":feature:profile:save"))
    implementation(project(":feature:profile"))
    implementation(project(":domain"))
    implementation(project(":feature"))
    implementation(project(":data"))
    implementation(project(":data:network"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.koin.androidx.compose)

    //firebase
    implementation(platform(libs.firebase.bom))

    //media3
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.common)
    implementation(libs.androidx.media3.session)
}