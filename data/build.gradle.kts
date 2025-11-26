import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

// Read the local.properties file to get the API key
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

android {
    namespace = "com.brickmanager.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 28

        // Make the API key available in the BuildConfig file
        buildConfigField("String", "REBRICKABLE_API_KEY", "\"${localProperties.getProperty("rebrickable.api.key")}\"")
    }

    buildFeatures {
        buildConfig = true
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
    // Dependencia al módulo de negocio (obligatorio)
    implementation(project(":domain"))

    // --- Room (DB Local) ---
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx) // Para coroutines/Flow
    ksp(libs.androidx.room.compiler) // Procesador de anotaciones

    // --- Retrofit (APIs Externas - BrickLink, etc.) ---
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // --- Hilt (Para inyectar Repositorio y Data Sources) ---
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // --- Pruebas ---
    testImplementation(libs.junit)
    testImplementation(libs.mockk) 
    testImplementation(libs.kotlinx.coroutines.test)
}
