plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services") // Google services plugin for Firebase
}

android {
    namespace = "com.ibnghufran.supportforcustomer"
    compileSdk = 35 // Updated compile SDK version to 35

    defaultConfig {
        applicationId = "com.ibnghufran.supportforcustomer"
        minSdk = 24
        targetSdk = 35 // Updated target SDK version to 35
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
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.activity:activity-ktx:1.9.3")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Firebase dependencies with direct version specification
    implementation("com.google.firebase:firebase-database:21.0.0") // Firebase Realtime Database
    implementation("com.google.firebase:firebase-auth:22.1.0") // Firebase Authentication

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
}
