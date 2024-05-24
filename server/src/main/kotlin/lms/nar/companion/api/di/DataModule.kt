package lms.nar.companion.api.di

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import lms.nar.analytic.Logger
import lms.nar.data.CommonDB
import lms.nar.data.repository.cards.equipment.ArmorRepository
import lms.nar.data.repository.cards.equipment.CommonArmorRepository
import lms.nar.data.repository.cards.equipment.CommonFormulaeRepository
import lms.nar.backend.data.Database as BackendDB
import lms.nar.data.repository.cards.equipment.EquipmentRepository
import lms.nar.data.repository.cards.equipment.FormulaeRepository
import org.koin.dsl.module
import java.io.File

val dbModule = module(createdAtStart = true) {
    single<JdbcDriver> {
        JdbcSqliteDriver(url = "jdbc:sqlite:lmsnar.db"/*JdbcSqliteDriver.IN_MEMORY*/)
    }

    single<BackendDB> {
        val driver = get<JdbcDriver>()
        if (!File("lmsnar.db").exists()) {
            BackendDB.Schema.create(driver)
        }
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