plugins {
    alias(libs.plugins.kotlinJvm)
    id(libs.plugins.sqldelight.gradle.plugin.get().pluginId) version libs.versions.sqldelight
    alias(libs.plugins.kotlin.serialization)
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("lms.nar.common.data")
            dialect(libs.sqldelight.sqlite.dialect)
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(platform(libs.arrow.bom))
    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)
    implementation(libs.sqldelight.primitive.adapters)
    implementation(libs.sqldelight.coroutine)
    implementation(libs.kotlin.serialization)
}