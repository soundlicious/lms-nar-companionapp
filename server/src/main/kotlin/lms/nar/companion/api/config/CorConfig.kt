package lms.nar.companion.api.config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.corsConfig() {
    install(CORS) {
        allowMethod(HttpMethod.Get)
        anyHost() //TODO remove
    }
}