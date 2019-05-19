plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android{
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.belimov.FocusNewsApp"
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.recyclerview:recyclerview:1.1.0-alpha04")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.work:work-runtime:2.0.1")
    implementation("com.google.android.material:material:1.0.0")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0-alpha01")
    annotationProcessor("androidx.lifecycle:lifecycle-compiler:2.2.0-alpha01")

    implementation("com.facebook.stetho:stetho:1.5.0")

    implementation("androidx.room:room-runtime:2.1.0-alpha07")
    annotationProcessor("androidx.room:room-compiler:2.1.0-alpha07")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.0")
}
