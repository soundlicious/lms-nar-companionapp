package lms.nar.companion.api.model.server

import io.ktor.http.parsing.ParseException
import kotlinx.serialization.Serializable
import java.util.MissingFormatArgumentException
import java.util.MissingResourceException

@Serializable
data class Version(
    val major: Int,
    val minor: Int,
    val bugfix: Int
) {
    val versionName: String
        get() = "$major.$minor.$bugfix"

    companion object {
        fun parse(version: String?): Result<Version> {
            val versionRegex = Regex("""(\d+)\.(\d+)\.(\d+)""")
            if (version.isNullOrBlank()) {
                return Result.failure(MissingFormatArgumentException("No version provided with format: Major.Minor.Bugfix"))
            }
            return versionRegex.matchEntire(version)?.let { versionResult ->
                val (major, minor, bugfix) = versionResult.destructured
                Result.success(
                    Version(
                        major = major.toInt(),
                        minor = minor.toInt(),
                        bugfix = bugfix.toInt()
                    )
                )
            } ?: Result.failure(ParseException("Wrong version format: Major.Minor.Bugfix"))
        }
    }
}
