package lms.nar.analytic

interface Logger {
    fun e(message: String, throwable: Throwable?, tag: String? = null)
}