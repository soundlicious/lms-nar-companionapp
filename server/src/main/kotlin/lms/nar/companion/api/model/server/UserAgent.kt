package lms.nar.companion.api.model.server

import io.ktor.http.Headers
import io.ktor.http.parsing.ParseException
import io.ktor.server.plugins.NotFoundException
import kotlinx.serialization.Serializable

@Serializable
data class UserAgent(
    val appName: String,
    val appVersion: Version,
    val os: String,
    val osVersion: Version
) {
    companion object {
        fun parse(headers: Headers): Result<UserAgent> {
            val UserAgentRegexp = "(\\w+)/([0-9._-]+):(\\w+)+/([0-9._-]+)"

            return headers["User-Agent"]?.let { userAgent ->
                val userAgentRegex = Regex(UserAgentRegexp)

                userAgentRegex.matchEntire(userAgent)?.let { userAgentResult ->
                    val (appName, appVersionString, os, osVersionString) = userAgentResult.destructured
                    try {
                        val osVersion = Version.parse(osVersionString).getOrThrow()
                        val appVersion = Version.parse(appVersionString).getOrThrow()
                        Result.success(
                            UserAgent(
                                appName = appName,
                                appVersion = appVersion,
                                os = os,
                                osVersion = osVersion
                            )
                        )
                    } catch (e: ParseException) {
                        Result.failure(e)
                    } catch (e: NumberFormatException) {
                        Result.failure(e)
                    }
                }
                    ?: Result.failure(ParseException("Wrong User-Agent format: {AppName}/{AppVersion}:{Os}/{OsVersion}"))

            } ?: Result.failure(NotFoundException("User-Agent Not Found: {AppName}/{AppVersion}:{Os}/{OsVersion}"))
        }
    }
}
