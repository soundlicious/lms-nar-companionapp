plugins {
    alias(libs.plugins.kotlinJvm)
    id(libs.plugins.sqldelight.gradle.plugin.get().pluginId) version libs.versions.sqldelight
    alias(libs.plugins.kotlin.serialization) apply false
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("lms.nar.backend.data")
            dependency(project(":commonDB"))
            dialect(libs.sqldelight.sqlite.dialect)
        }
    }
}

dependencies {
    api(project(":commonDB"))
    implementation(libs.sqldelight.runtime.jvm)
    implementation(libs.sqldelight.primitive.adapters)
    implementation(libs.sqldelight.coroutine)
}