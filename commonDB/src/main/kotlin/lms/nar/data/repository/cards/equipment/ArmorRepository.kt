package lms.nar.data.repository.cards.equipment

import app.cash.sqldelight.coroutines.asFlow
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import lms.nar.analytic.Logger
import lms.nar.common.data.Database
import lms.nar.data.model.equipment.Armor
import lms.nar.data.model.equipment.ArmorCard
import lms.nar.data.model.equipment.ArmorTranslation
import lms.nar.data.result.executeAsOneResult

interface ArmorRepository {
    fun selectLastUpdatedArmor(
        date: String,
        offset: Long,
        limit: Long = 10
    ): Flow<Result<List<Armor>>>

    fun selectLastUpdatedArmorTranslation(
        date: String,
        offset: Long,
        limit: Long = 10
    ): Flow<Result<List<ArmorTranslation>>>

    fun selectArmorCardPage(
        languageCode: String,
        limit: Long,
        offset: Long
    ): Flow<Result<List<ArmorCard>>>

    fun selectArmorCardById(
        id: Long,
        languageCode: String,
    ): Flow<Result<ArmorCard?>>

    fun insertArmors(armors: List<Armor>): Flow<Either<Long, Long>>
}

class CommonArmorRepository(
    commonDB: Database,
    private val logger: Logger,
    private val loggerTag: String = "ArmorRepository"
) : ArmorRepository {

    private val query = commonDB.armorQueries

    //region Armor
    override fun selectLastUpdatedArmor(
        date: String,
        offset: Long,
        limit: Long
    ): Flow<Result<List<Armor>>> {
        return query.selectAllArmorPaginated(
            date = date,
            limit = limit,
            offset = offset,
            mapper = Armor.mapper
        ).asFlow().map {
            runCatching {
                it.executeAsList()
            }.onFailure { error ->
                logger.e(
                    tag = loggerTag,
                    throwable = error,
                    message = "Error while selecting Armor Page"
                )
            }
        }
    }

    override fun selectLastUpdatedArmorTranslation(
        date: String,
        offset: Long,
        limit: Long,
    ): Flow<Result<List<ArmorTranslation>>> {
        return query.selectAllArmorTranslationPaginated(
            date = date,
            limit = limit,
            offset = offset,
            mapper = ArmorTranslation.mapper
        ).asFlow().mapNotNull {
            runCatching {
                it.executeAsList()
            }.onFailure { error ->
                logger.e(
                    tag = loggerTag,
                    throwable = error,
                    message = "Error while selecting Armor translation Page"
                )
            }
        }
    }

    override fun selectArmorCardPage(
        languageCode: String,
        limit: Long,
        offset: Long
    ): Flow<Result<List<ArmorCard>>> {
        return query.selectAllArmorCardPaginated(
            language_code = "",
            limit = 0,
            offset = 0,
            mapper = ArmorCard.mapper
        ).asFlow().mapNotNull {
            runCatching {
                it.executeAsList()
            }.onFailure { error ->
                logger.e(
                    tag = loggerTag,
                    throwable = error,
                    message = "Error while selecting Armor Card Page"
                )
            }
        }
    }

    override fun selectArmorCardById(
        id: Long,
        languageCode: String,
    ): Flow<Result<ArmorCard?>> {
        return query.selectArmorCardById(
            language_code = languageCode,
            id = id,
            mapper = ArmorCard.mapper
        ).asFlow().map {
            it.executeAsOneResult { error ->
                logger.e(
                    tag = loggerTag,
                    throwable = error,
                    message = "Error retrieving armor card: $id"
                )
            }
        }
    }

    override fun insertArmors(armors: List<Armor>): Flow<Either<Long, Long>> {
        return armors.asFlow().map { armor ->
            runCatching {
                with(armor) {
                    query.insertArmor(
                        id = id,
                        card_ref = cardRef,
                        reward_id = rewardId?.toLong(),
                        slot_type = slotType,
                        slot_weight = slotWeight.toLong()
                    )
                }
            }.fold(
                onSuccess = { armor.id.right() },
                onFailure = { error ->
                    logger.e(
                        message = "Error while inserting armor: $armor",
                        tag = loggerTag,
                        throwable = error
                    )
                    armor.id.left()
                }
            )
        }
    }
//endregion

}