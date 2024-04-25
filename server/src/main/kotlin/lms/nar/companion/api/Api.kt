package lms.nar.companion.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import isValidDateFormat
import lms.nar.companion.api.model.server.lastUpdate
import lms.nar.companion.api.route.equipment
import lms.nar.companion.api.route.status

fun Application.api() {
    routing {
        route("/api") {
            lastUpdateValidation()
            equipment()
            status()
        }
    }
}

fun Route.lastUpdateValidation() {
    intercept(ApplicationCallPipeline.Call) {
        val lastUpdate = call.parameters.lastUpdate
        val strictMode = !call.request.uri.contains("/api/update_status")

        if (strictMode && lastUpdate.isNullOrBlank()) {
            call.respondText(
                text = "last_update is required : yyyy-mm-dd hh:mm:ss",
                status = HttpStatusCode.BadRequest,
                contentType = ContentType.Text.Plain
            )
            return@intercept finish()
        }
        lastUpdate?.let {
            if (!isValidDateFormat(it)) {
                call.respondText(
                    text = "last_update format invalid. Format should be : yyyy-mm-dd hh:mm:ss",
                    status = HttpStatusCode.BadRequest,
                    contentType = ContentType.Text.Plain
                )
                return@intercept finish()
            }
        }
    }
}