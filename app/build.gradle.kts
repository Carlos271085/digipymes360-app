plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.app"
        minSdk = 28
        targetSdk = 36
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

    dependencies {
        // Compose + Material 3 + Navigation
        implementation("androidx.activity:activity-compose:1.9.3")
        implementation("androidx.compose.ui:ui:1.7.5")
        implementation("androidx.compose.ui:ui-tooling-preview:1.7.5")
        implementation("androidx.compose.material3:material3:1.3.0")
        implementation("androidx.navigation:navigation-compose:2.8.3")
        implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

        // Retrofit + Gson
        implementation("com.squareup.retrofit2:retrofit:2.11.0")
        implementation("com.squareup.retrofit2:converter-gson:2.11.0")
        implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.14")

        // ViewModels y coroutines
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
        implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

        // Imágenes
        implementation("io.coil-kt:coil-compose:2.7.0")

        //Huella dactilar
        implementation("androidx.biometric:biometric:1.2.0-alpha05")

        //Data Store
        implementation("androidx.datastore:datastore-preferences:1.1.1")

        // Íconos extendidos de Material (necesario para Fingerprint)
        implementation("androidx.compose.material:material-icons-extended:1.7.5")

    }


}
