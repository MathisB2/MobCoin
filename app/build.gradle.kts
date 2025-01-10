plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.mobcoin.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mobcoin.app"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "1.0.0"


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildFeatures {
            viewBinding = true
            buildConfig = true
        }
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

    flavorDimensions += "environment"
    productFlavors {
        create("develop") {
            dimension = "environment"
            versionNameSuffix = "-dev"
            buildConfigField("String", "API_KEY", "\"CG-soMb1tvkPUXUcqmJXvd3He5g\"")
        }
        create("preproduction") {
            dimension = "environment"
            versionNameSuffix = "-preprod"
            buildConfigField("String", "API_KEY", "\"CG-soMb1tvkPUXUcqmJXvd3He5g\"")
        }
        create("production") {
            dimension = "environment"
            versionNameSuffix = "-prod"
            buildConfigField("String", "API_KEY", "\"CG-soMb1tvkPUXUcqmJXvd3He5g\"")
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
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    //ROOM
    val room_version = "2.6.0"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt ("androidx.room:room-compiler:$room_version")

    // To use Kotlin Symbol Processing (KSP)
    //ksp("androidx.room:room-compiler:$room_version")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$room_version")

    // RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$room_version")

    // Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$room_version")

    // Test helpers
    testImplementation("androidx.room:room-testing:$room_version")

    // Paging 3 Integration
    implementation("androidx.room:room-paging:$room_version")

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


    //chart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}