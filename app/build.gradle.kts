plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.ordy"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.ordy"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    // Firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:34.9.0"))

    // Firebase Database и Analytics без указания версий
    // Вместо firebase-database-ktx
    implementation("com.google.firebase:firebase-database:20.3.0")

    implementation("com.google.firebase:firebase-analytics")

    // AndroidX и остальное
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
