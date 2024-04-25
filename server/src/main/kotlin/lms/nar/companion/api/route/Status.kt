package lms.nar.companion.api.route

import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lms.nar.backend.data.Database
import lms.nar.companion.api.model.server.Version
import lms.nar.companion.api.model.server.appVersion
import lms.nar.companion.api.model.server.lastUpdate
import lms.nar.data.model.status.DeletedRow
import lms.nar.data.model.status.TableUpdate
import lms.nar.data.model.status.UpdateStatus
import org.koin.ktor.ext.inject

fun Route.status() {
    val db by inject<Database>()

    get("update_status", {
        description = "Provide all the tables that needs update and all the deleted row"
        request {
            queryParameter<String>("app_version") {
                required = true
                allowEmptyValue = false
                example = "1.0.0"
                description =
                    "Client information used as a fallback when last_update is not provided to determined the date of the last possible update."
            }
            queryParameter<String>("last_update") {
                required = false
                allowEmptyValue = false
                example = "1987-02-17 00:00:00"
                description =
                    """Date(yyyy-mm-dd hh:mm:ss) of the last successful client database's update.
                    |When not provided the app_version will provide the date of last update instead""".trimMargin()
            }
        }
        response {
            HttpStatusCode.OK to {
                body<UpdateStatus> {
                    description =
                        "List of all the tables needing an update as well as all the deletion needed"
                }
            }
        }
    }) {
        val lastUpdate = call.parameters.lastUpdate
        val appVersion = call.parameters.appVersion
        val appVersionQueries = db.appVersionQueries
        val tableUpdateQueries = db.tableOperationQueries

        val date = lastUpdate ?: Version.parse(appVersion).map { version ->
            appVersionQueries.getAppVersionCreationDate(version.versionName)
                .executeAsOneOrNull()
        }.onFailure {
            return@get call.respondText(
                text = it.message!!,
                contentType = ContentType.Text.Plain,
                status = HttpStatusCode.BadRequest
            )
        }.getOrNull()

        val json = date?.let { date ->
            val updates =
                tableUpdateQueries.selectUpdateAfter(date, TableUpdate.mapper).executeAsList()
            val deletions =
                tableUpdateQueries.selectDeletionAfter(date, DeletedRow.mapper).executeAsList()
            Json.encodeToString(
                UpdateStatus(
                    update = updates,
                    deletion = deletions
                )
            )
        } ?: "[]"

        call.respondText(
            text = json,
            contentType = ContentType.Application.Json,
            status = HttpStatusCode.OK
        )
    }
}