plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.compose")
    id("ru.practicum.android.diploma.plugins.developproperties")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
}

android {
    namespace = "ru.practicum.android.diploma"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.practicum.android.diploma"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(type = "String", name = "API_ACCESS_TOKEN", value = "\"${developProperties.apiAccessToken}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        languageVersion = "1.9"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {

    // ---------- COMPOSE + MATERIAL3 ----------
    // BOM — один раз, версии сам подтянет
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // Базовый Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // Material3 для Compose
    implementation(libs.androidx.compose.material3)

    // Coil — загрузка картинок в Compose
    implementation(libs.coil.compose)

    // Activity + lifecycle integration
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Инструменты для превью/тестов Compose
    debugImplementation(libs.androidx.compose.ui.tooling)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // базовые
    implementation(libs.androidX.core)
    implementation(libs.androidX.appCompat)

    // UI layer libraries (XML)
    implementation(libs.ui.material)
    implementation(libs.ui.constraintLayout)

    // Navigation (Compose)
    implementation(libs.navigation.compose)

    // Retrofit + OkHttp
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // Koin
    implementation(libs.koin)
    implementation(libs.koinCompose)

    // Coroutines
    implementation(libs.coroutines)

    // region Unit tests
    testImplementation(libs.unitTests.junit)
    // endregion

    // region UI tests
    androidTestImplementation(libs.uiTests.junitExt)
    androidTestImplementation(libs.uiTests.espressoCore)

    // paging
    implementation(libs.bundles.paging)

    // ------------------ 🔥 ABOUT KOTLINX.SERIALIZATION ------------------
    // JSON сериализация (альтернативный вариант EPIC 4.1)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    // endregion
}

/**
 * 🔥 Обязательно для генерации сериализаторов
 */
kotlin {
    sourceSets.all {
        languageSettings.optIn("kotlinx.serialization.ExperimentalSerializationApi")
    }
}
