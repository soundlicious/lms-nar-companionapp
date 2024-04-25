package lms.nar.companion.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Pagination<T>(
    val elements: List<T>,
    val nextOffset: Long?
)
