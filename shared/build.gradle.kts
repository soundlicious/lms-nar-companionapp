plugins {
    id("java-library")
    alias(libs.plugins.kotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":core"))
    implementation(libs.logging.kermit)
    implementation(libs.logging.kermit.testing)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}