package lms.nar.companion.api.config

import io.ktor.server.application.*
import lms.nar.companion.api.di.commonDataModule
import lms.nar.companion.api.di.dbModule
import lms.nar.companion.api.model.DBConfig
import lms.nar.shared.analyticsModule
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.diConfig() {
    install(Koin) {
        modules(
            dbModule + commonDataModule + analyticsModule +
                    module {
                        factory<DBConfig> {
                            val dbConfig = environment.config.config("jdbc")
                            DBConfig(
                                dbConfig.property("url").getString(),
                                dbConfig.property("driverClassName").getString(),
                                dbConfig.property("username").getString(),
                                dbConfig.property("password").getString()
                            )
                        }
                    })
    }
}