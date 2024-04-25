package lms.nar.data.result

import app.cash.sqldelight.Query

fun <T : Any> Query<T>.executeAsOneResult(logError: (throwable: Throwable) -> Unit): Result<T?> {
    return runCatching {
        executeAsOne()
    }.fold(
        onSuccess = { item ->
            Result.success(item)
        },
        onFailure = { error ->
            if (error !is NullPointerException) {
                logError(error)
                Result.failure(error)
            } else {
                Result.success(null)
            }
        }
    )
}
