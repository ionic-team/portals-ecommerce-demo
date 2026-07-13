plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.ionic.demo.ecommerce"
    compileSdk = 36

    defaultConfig {
        applicationId = "io.ionic.demo.ecommerce"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
    }
}

dependencies {
    // Ionic Portals and Capacitor dependencies
    implementation("io.ionic:portals:0.14.0")
    implementation(project(":live-update-provider-mock"))
    implementation("com.capacitorjs:camera:8.2.0")

    implementation("com.google.code.gson:gson:2.9.0")

    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.14.2")
    // Real org.json implementation, needed because Android's unit-test stub JSONObject
    // throws "not mocked" for any real method call (e.g. from JSObject.put/fromJSONObject).
    testImplementation("org.json:json:20080701")
}

val copyWebAssets by tasks.registering(Copy::class) {
    val webBuildFolder = layout.projectDirectory.dir("../../../portals/shop/build")
    from(webBuildFolder)
    into(layout.projectDirectory.dir("src/main/assets/webapp"))

    doFirst {
        if (!webBuildFolder.asFile.exists()) {
            throw GradleException("Portal bundle not found at ${webBuildFolder.asFile}. Run `npm run build:shop` from the repo root before building Android.")
        }
    }
}

tasks.named("preBuild") {
    dependsOn(copyWebAssets)
}
