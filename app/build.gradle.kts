@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    kotlin("kapt")
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt.android)
}

android {
    namespace = "com.dezdeqness.tmdb"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.dezdeqness.tmdb"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "BASE_API_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField("String", "APIKEY", "\"ef093f135a41bd8b9427ca7261e547cf\"")
        buildConfigField("String", "BASE_IMAGE_URL", "\"https://image.tmdb.org/\"")

    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }

        release {
            isMinifyEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }

}

dependencies {

    implementation(libs.core.ktx)

    implementation(libs.lifecycle.runtime.ktx)

    implementation(libs.activity.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material)

    implementation(libs.coil)

    implementation(libs.corutines)
    implementation(libs.corutines.android)

    implementation(libs.compose.navigation)

    implementation(libs.retrofit)
    implementation(libs.retrofitMoshi)

    implementation(libs.okhttp)
    implementation(libs.okhttpLogInterceptor)

    implementation(libs.room)
    kapt(libs.roomCompiler)
    implementation(libs.roomKtx)

    implementation(libs.hiltAndroid)
    kapt(libs.hiltCompiler)
    implementation(libs.hilt.navigation.compose)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    testImplementation(libs.junitCore)
    testImplementation(libs.junitApi)
    testImplementation(libs.junitLauncher)
    testRuntimeOnly(libs.junitEngine)
    testImplementation(libs.junitParams)
    testImplementation(libs.coroutinesTest)

    testImplementation(libs.mockitoJunit)
    testImplementation(libs.mockitoInline)
    testImplementation(libs.mockitoKotlin)
}