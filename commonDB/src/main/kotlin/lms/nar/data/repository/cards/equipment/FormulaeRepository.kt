package lms.nar.data.repository.cards.equipment

import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import lms.nar.analytic.Logger
import lms.nar.data.CommonDB
import lms.nar.data.model.equipment.Formulae

interface FormulaeRepository {
    fun selectLastUpdatedFormulae(
        date: String,
        offset: Long,
        limit: Long
    ): Flow<Result<List<Formulae>>>

    fun insertFormulae(formulaes: List<Formulae>): Flow<Result<Long>>
    fun selectFormulaeForCardRef(cardRef: String): Flow<Result<Formulae>>
}

class CommonFormulaeRepository(
    commonDB: CommonDB,
    private val logger: Logger,
    private val loggerTag: String = "FormulaeRepository"
) : FormulaeRepository {

    private val query = commonDB.formulaeQueries
    override fun selectLastUpdatedFormulae(
        date: String,
        offset: Long,
        limit: Long,
    ): Flow<Result<List<Formulae>>> {
        return query.selectPaginatedFormulae(
            limit = limit,
            offset = offset,
            date = date,
            mapper = Formulae.mapper
        ).asFlow().map {
            runCatching {
                it.executeAsList()
            }.onFailure { error ->
                logger.e(
                    message = "Error while retrieving Formulae",
                    tag = loggerTag,
                    throwable = error
                )
            }
        }
    }

    override fun insertFormulae(formulaes: List<Formulae>): Flow<Result<Long>> {
        return formulaes.asFlow().map { formulae ->
            with(formulae) {
                runCatching {
                    query.insertOrReplaceFormulae(
                        id = id,
                        card_ref = cardRef,
                        wood = wood.toLong(),
                        leather = leather.toLong(),
                        belladona = belladona.toLong(),
                        mandrake = mandrake.toLong(),
                        philosopher_stone = philosopherStone.toLong(),
                        realgar = realgar.toLong(),
                        sulfur = sulfur.toLong(),
                        mercury = mercury.toLong(),
                        metal = metal.toLong(),
                        silver = silver.toLong(),
                        gold = gold.toLong(),
                        salt = salt.toLong(),
                        cost = cost.toLong()
                    )
                }.map { id }.onFailure { error ->
                    logger.e(
                        message = "Error inserting formulae: $id",
                        tag = loggerTag,
                        throwable = error
                    )
                }
            }
        }
    }

    override fun selectFormulaeForCardRef(cardRef: String): Flow<Result<Formulae>> {
        return query.selectFormulaeByCardRef(card_ref = cardRef, mapper = Formulae.mapper).asFlow()
            .map {
                runCatching {
                    it.executeAsOne()
                }
            }
    }
}