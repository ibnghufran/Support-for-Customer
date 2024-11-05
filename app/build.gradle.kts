plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services") // Google services plugin for Firebase

    kotlin("kapt") // Add kapt if you’re using any annotation processors

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

    viewBinding {
        enable = true // Use 'enable' instead of 'isEnabled'
    }

}

dependencies {
    implementation(libs.androidx.core.ktx.v190)
    implementation(libs.androidx.appcompat.v151)
    implementation(libs.material.v170)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout.v214)

    // Firebase dependencies with direct version specification
    implementation(libs.firebase.database) // Firebase Realtime Database
    implementation(libs.firebase.auth.v2210) // Firebase Authentication
    implementation (libs.firebase.firestore)
    implementation(libs.androidx.activity)  // For Firestore (optional)
    implementation (libs.firebase.database.ktx) // Check for the latest version
    implementation (libs.firebase.auth.ktx)
    implementation(libs.androidx.wear)
    implementation(libs.androidx.navigation.fragment.ktx) // If you need authentication



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v114)
    androidTestImplementation(libs.androidx.espresso.core.v350)

    implementation (libs.androidx.cardview) // Add this line
    // other dependencies

}
