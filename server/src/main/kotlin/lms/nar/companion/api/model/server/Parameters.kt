package lms.nar.companion.api.model.server

import io.ktor.http.Parameters

val Parameters.lastUpdate: String?
    get() = get("last_update")

val Parameters.appVersion: String?
    get() = get("app_version")

val Parameters.offset: Long
    get() = get("offset")?.toULongOrNull()?.toLong()?:0L

val Parameters.limit: Long
    get() = get("limit")?.toULongOrNull()?.toLong()?:10L