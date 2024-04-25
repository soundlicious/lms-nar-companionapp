package lms.nar.shared

import lms.nar.analytic.Logger
import org.koin.dsl.module
import co.touchlab.kermit.Logger as KermitLogger


val analyticsModule = module {
    single<Logger> {
        NarLogger(KermitLogger)
    }
}

class NarLogger(private val logger: KermitLogger) : Logger {

    override fun e(message: String, error: Throwable?, tag: String?) {
        if (tag != null) {
            logger.e(messageString = message, throwable = error, tag = tag)
        } else {
            logger.e(messageString = message, throwable = error)
        }
    }
}