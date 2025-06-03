plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.lapoushko.network"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(project(":domain"))
    testImplementation(libs.junit)

    //Firebase-bom
    implementation(platform(libs.firebase.bom))

    //Firebase-firestore
    implementation(libs.firebase.firestore)

    //Firebase-auth
    implementation(libs.androidx.credentials)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.okhttp)

    //MoShi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.retrofit2converter.moshi)
}