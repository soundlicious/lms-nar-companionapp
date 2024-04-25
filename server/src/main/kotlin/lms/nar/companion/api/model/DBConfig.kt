package lms.nar.companion.api.model

data class DBConfig(
    val url: String,
    val driverClassName: String,
    val username: String,
    val password: String
)