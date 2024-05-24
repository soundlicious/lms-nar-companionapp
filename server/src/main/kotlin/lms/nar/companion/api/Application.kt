package lms.nar.companion.api

import io.ktor.server.application.*
import io.ktor.server.netty.*
import lms.nar.companion.api.config.contentNegotiationConfig
import lms.nar.companion.api.config.corsConfig
import lms.nar.companion.api.config.diConfig
import lms.nar.companion.api.config.documentationConfig

fun Application.mainModule() {
    diConfig()
    corsConfig()
    documentationConfig()
    contentNegotiationConfig()
    api()
}

fun main(args: Array<String>): Unit = EngineMain.main(args)

