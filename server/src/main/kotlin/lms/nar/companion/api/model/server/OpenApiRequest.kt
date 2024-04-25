package lms.nar.companion.api.model.server

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRequest

val OpenApiRequest.lastUpdateQueryParam
    get() = queryParameter<String>("last_update") {
        required = true
        allowEmptyValue = false
        example = "1987-02-17 00:00:00"
        description =
            "Date(yyyy-mm-dd hh:mm:ss) of the last successful client database's update."
    }

val OpenApiRequest.paginationLimitQueryParam
    get() = queryParameter<Int>("limit") {
        required = false
        example = 10
        allowEmptyValue = false
        description = "Number of items per pages. Default: 10"
    }

val OpenApiRequest.paginationOffsetQueryParam
    get() = queryParameter<Int>("offset") {
        required = false
        example = 10
        allowEmptyValue = false
        description = "Number of items to skip. Default: 0"
    }