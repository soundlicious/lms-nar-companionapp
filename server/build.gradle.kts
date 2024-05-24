plugins {
//    alias(libs.plugins.nar.ktor.library)
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    application
}

group = "lms.nar.companion.api"
version = "1.0.0"
application {
    mainClass.set("lms.nar.companion.api.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

ktor {
    fatJar {
        archiveFileName.set("narApi.jar")
    }
    docker {
        jreVersion.set(JavaVersion.VERSION_17)
        localImageName.set("lms-nar-companion-api")
        imageTag.set("0.0.1")
        portMappings.set(listOf(
            io.ktor.plugin.features.DockerPortMapping(
                80,
                8080,
                io.ktor.plugin.features.DockerPortMappingProtocol.TCP
            )
        ))
    }
}

dependencies {
    implementation(project(":backendDB"))
    implementation(project(":commonDB"))
    implementation(project(":core"))
    implementation(project(":shared"))
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.ktor)
    implementation(libs.sqldelight.driver.sqlite)
    implementation(libs.sqldelight.coroutine)
    implementation(libs.ktor.server.content.negociation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.cors.jvm)
    implementation(libs.ktor.server.logging)
    implementation(libs.ktor.swagger.ui)
}