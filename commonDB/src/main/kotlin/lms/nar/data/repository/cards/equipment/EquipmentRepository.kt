package lms.nar.data.repository.cards.equipment

import app.cash.sqldelight.TransactionWithReturn
import arrow.core.Either
import arrow.core.right
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lms.nar.data.CommonDB
import lms.nar.data.model.equipment.Armor
import lms.nar.data.model.equipment.ArmorTranslation

data class DBUpdate(
    val armors: List<Armor>? = null,
    val armorTranslation: List<ArmorTranslation>? = null
)

class EquipmentRepository(
    private val commonDB: CommonDB,
    armorRepository: ArmorRepository,
    formulaeRepository: FormulaeRepository
) : ArmorRepository by armorRepository,
    FormulaeRepository by formulaeRepository {

    suspend fun updateDBTransaction(updates: DBUpdate) {
        return withContext(Dispatchers.IO) {
            commonDB.transactionWithResult<Either<String,Unit>> {
                Unit.right()
            }
        }
    }

    private fun <T> TransactionWithReturn<T>.insertOrRollback(message: T, body: (()-> List<Either<Long, Long>>?)): T? {
        val result = body()
        return if (result?.any { it is Either.Left<Long> } == true) {
            rollback(message)
        } else null
    }
}

