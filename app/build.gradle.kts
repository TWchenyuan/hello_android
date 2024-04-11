import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleDaggerHiltAndroid)
    alias(libs.plugins.jlleitschuhGradleKtlint)
    kotlin("kapt")
}

android {
    namespace = "com.thoughtworks.androidtrain"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thoughtworks.androidtrain"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        val file = rootProject.file("keystore.properties")
        val keyStoreProperties = Properties()
        keyStoreProperties.load(FileInputStream(file))
        create("debug_sign") {
            keyAlias = keyStoreProperties["debugKeyAlias"] as String
            keyPassword = keyStoreProperties["debugKeyPassword"] as String
            storeFile = rootProject.file(keyStoreProperties["debugStoreFile"] as String)
            storePassword = keyStoreProperties["debugStorePassword"] as String
        }

        create("release_sign") {
            keyAlias = keyStoreProperties["releaseKeyAlias"] as String
            keyPassword = keyStoreProperties["releaseKeyPassword"] as String
            storeFile = rootProject.file(keyStoreProperties["releaseStoreFile"] as String)
            storePassword = keyStoreProperties["releaseStorePassword"] as String
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs["debug_sign"]
            isMinifyEnabled = false
            isDebuggable = true
        }

        getByName("release") {
            signingConfig = signingConfigs["release_sign"]
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "$rootDir/app/proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "version"
    productFlavors {
        create("dev") {
            dimension = "version"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }

        create("prod") {
            dimension = "version"
            applicationIdSuffix = ".prod"
            versionNameSuffix = "-prod"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.gson)
    implementation(libs.coil.kt.coil)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.hilt.android)

    // compose dependency
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.coil.compose)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.hilt.navigation.compose)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    kapt(libs.hilt.android.compiler)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.truth)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.rules)
    testImplementation(libs.androidx.core.testing)
}

kapt {
    correctErrorTypes = true
}
ktlint {
    android = true
}
