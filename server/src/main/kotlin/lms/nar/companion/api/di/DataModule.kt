package lms.nar.companion.api.di

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.zaxxer.hikari.HikariDataSource
import lms.nar.analytic.Logger
import lms.nar.companion.api.model.DBConfig
import lms.nar.data.CommonDB
import lms.nar.data.repository.cards.equipment.ArmorRepository
import lms.nar.data.repository.cards.equipment.CommonArmorRepository
import lms.nar.data.repository.cards.equipment.CommonFormulaeRepository
import lms.nar.backend.data.Database as BackendDB
import lms.nar.data.repository.cards.equipment.EquipmentRepository
import lms.nar.data.repository.cards.equipment.FormulaeRepository
import org.koin.dsl.module

val dbModule = module(createdAtStart = true) {
    single<HikariDataSource> {
        val dbConfig = get<DBConfig>()
        HikariDataSource()
            .apply {
                jdbcUrl = dbConfig.url
                driverClassName = dbConfig.driverClassName
                username = dbConfig.username
                password = dbConfig.password
                connection
            }
    }
    single<JdbcDriver> {
//        get<HikariDataSource>().asJdbcDriver()
        JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    }

    single<BackendDB> {
        val driver = get<JdbcDriver>()
        BackendDB.Schema.create(driver)
        BackendDB(driver)
    }

    single<CommonDB> {
        get<BackendDB>()
    }
}

val commonDataModule = module {
    single<FormulaeRepository> {
        CommonFormulaeRepository(
            commonDB = get<CommonDB>(),
            logger = get<Logger>()
        )
    }

    single<ArmorRepository> {
        CommonArmorRepository(
            commonDB = get<CommonDB>(),
            logger = get<Logger>()
        )
    }

    single<FormulaeRepository> {
        CommonFormulaeRepository(
            commonDB = get<CommonDB>(),
            logger = get<Logger>()
        )
    }

    factory<EquipmentRepository> {
        EquipmentRepository(
            armorRepository = get(),
            formulaeRepository = get(),
            commonDB = get()
        )
    }
}