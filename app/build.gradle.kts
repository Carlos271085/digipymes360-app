import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}



val localProps = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

val mpPublicKey: String = localProps.getProperty("MP_PUBLIC_KEY") ?: ""

android {
    namespace = "com.example.app"
    compileSdk = 36

    packaging {
        jniLibs {
            useLegacyPackaging = false
        }
    }

    defaultConfig {
        applicationId = "com.example.app"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // ───────────────────────────────
        //  BuildConfig - PUBLIC KEY
        // ───────────────────────────────
        buildConfigField(
            "String",
            "MP_PUBLIC_KEY",
            "\"$mpPublicKey\""
        )
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    // --- COMPOSE (BOM) ---
    implementation(platform("androidx.compose:compose-bom:2024.10.01"))

    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.8.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

    // Retrofit + Gson
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.14")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Huella
    implementation("androidx.biometric:biometric:1.2.0-alpha05")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Icons
    implementation("androidx.compose.material:material-icons-extended")

    // Location
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // --- HILT ---
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // --- MERCADO PAGO ---
    implementation(platform("com.mercadopago.android.sdk:sdk-android-bom:0.1.3"))
    implementation("com.mercadopago.android.sdk:core-methods")

    // ─────────────────────────────
    // UNIT TESTS
    // ─────────────────────────────
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("com.google.truth:truth:1.1.3")

    // ─────────────────────────────
    // ANDROID / COMPOSE UI TESTS
    // ─────────────────────────────
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.10.01"))

    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Compose Testing (sin versión → usa BOM)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Necesario para evitar errores de manifiesto en tests UI
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Navigation testing
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.0")
    androidTestImplementation("io.mockk:mockk-android:1.13.8")
}
