import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
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
    }
    signingConfigs {
        val file = rootProject.file("keystore.properties")
        val keyStoreProperties = Properties()
        keyStoreProperties.load(FileInputStream(file))
        create("debug_sign") {
            keyAlias = keyStoreProperties["debugKeyAlias"] as String
            keyPassword = keyStoreProperties["debugKeyPassword"]  as String
            storeFile = rootProject.file(keyStoreProperties["debugStoreFile"]  as String)
            storePassword = keyStoreProperties["debugStorePassword"]  as String
        }

        create("release_sign") {
            keyAlias = keyStoreProperties["releaseKeyAlias"] as String
            keyPassword = keyStoreProperties["releaseKeyPassword"]  as String
            storeFile = rootProject.file(keyStoreProperties["releaseStoreFile"]  as String)
            storePassword = keyStoreProperties["releaseStorePassword"]  as String
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
    annotationProcessor(libs.androidx.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}