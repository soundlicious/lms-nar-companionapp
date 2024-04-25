import io.ktor.http.Parameters

fun isValidDateFormat(date: String): Boolean {
    val dateRegexp = """\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}"""
    val dateRegex = Regex(dateRegexp)

    return dateRegex.matches(date)
}