plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.mobcoin.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mobcoin.app"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "API_KEY", "\"toto>\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig=true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Timber
    implementation ("com.jakewharton.timber:timber:5.0.1")

    // coroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    // gson converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    androidTestImplementation(libs.androidx.espresso.core)

    // icon
    implementation ("com.squareup.picasso:picasso:2.8")

    // FNG progress bar
    implementation("app.futured.donut:donut:2.2.4")


    implementation("com.patrykandpatrick.vico:compose:1.15.0")
    implementation("com.patrykandpatrick.vico:compose-m2:1.15.0")
    implementation("com.patrykandpatrick.vico:compose-m3:1.15.0")
    implementation("com.patrykandpatrick.vico:core:1.15.0")
    implementation("com.patrykandpatrick.vico:views:1.15.0")
}