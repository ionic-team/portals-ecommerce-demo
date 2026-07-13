pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(":app")

// Portals apps link the mock provider module directly (no Capacitor plugin adapter).
// The mock ships no Maven artifact, so the module is consumed from source.
include(":live-update-provider-mock")
project(":live-update-provider-mock").projectDir = file("../../../live-update-provider-mock/android/live-update-provider-mock")

rootProject.name = "Portals Ecommerce"
