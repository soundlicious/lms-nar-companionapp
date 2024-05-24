package lms.nar.companion.api.config

import io.ktor.server.application.*
import lms.nar.companion.api.di.commonDataModule
import lms.nar.companion.api.di.dbModule
import lms.nar.shared.analyticsModule
import org.koin.ktor.plugin.Koin

fun Application.diConfig() {
    install(Koin) {
        modules(
            dbModule + commonDataModule + analyticsModule
        )
    }
}