package lms.nar.companion.api.config

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.server.application.*

fun Application.documentationConfig() {
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "apidoc"
            forwardRoot = true
        }
        info {
            title = "Nar Companion App Api"
        }
        server {
            url = "http://localhost:8080"
            description = "Dev server"
        }
    }
}