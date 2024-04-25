package lms.nar.companion.api.route

import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lms.nar.companion.api.model.Pagination
import lms.nar.companion.api.model.server.lastUpdate
import lms.nar.companion.api.model.server.lastUpdateQueryParam
import lms.nar.companion.api.model.server.limit
import lms.nar.companion.api.model.server.offset
import lms.nar.companion.api.model.server.paginationLimitQueryParam
import lms.nar.companion.api.model.server.paginationOffsetQueryParam
import lms.nar.data.model.status.UpdateStatus
import lms.nar.data.repository.cards.equipment.EquipmentRepository
import org.koin.ktor.ext.inject


fun Route.equipment() {
    val repo by inject<EquipmentRepository>()

    get("armor", {
        description = "Provide last updated armors"
        request {
            lastUpdateQueryParam
            paginationLimitQueryParam
            paginationOffsetQueryParam
        }
        response {
            HttpStatusCode.OK to {
                body<UpdateStatus> {
                    description =
                        "All the new armors since last_update"
                }
            }
        }
    }) {
        val lastUpdate = call.parameters.lastUpdate!!
        val limit = call.parameters.limit
        val offset = call.parameters.offset

        val armors = repo.selectLastUpdatedArmor(
            date = lastUpdate,
            limit = limit,
            offset = offset
        ).firstOrNull()?.getOrNull() ?: emptyList()

        val pagination = Pagination(
            elements = armors,
            nextOffset = if (armors.size < limit) null else offset + limit
        )

        call.respondText(
            text = Json.encodeToString(pagination),
            contentType = ContentType.Application.Json,
            status = HttpStatusCode.OK
        )
    }

    get("armor_translation") {

    }
}