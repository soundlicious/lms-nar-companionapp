package lms.nar.companion.api

import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.netty.*
import lms.nar.companion.api.config.contentNegotiationConfig
import lms.nar.companion.api.config.corsConfig
import lms.nar.companion.api.config.diConfig
import lms.nar.companion.api.config.documentationConfig
import org.koin.ktor.ext.inject

fun Application.mainModule() {
    diConfig()
    corsConfig()
    documentationConfig()
    contentNegotiationConfig()
    onServerReady {
        val datasource by inject<HikariDataSource>()
        println("onServerReady, datasource is running: ${datasource.isRunning}")
    }
    api()
}

fun main(args: Array<String>): Unit = EngineMain.main(args)

