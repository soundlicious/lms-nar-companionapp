package lms.nar.data.model.equipment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Formulae(
    val id: Long,
    @SerialName("card_ref")
    val cardRef: String,
    val wood: UInt = 0u,
    val leather: UInt = 0u,
    val belladona: UInt = 0u,
    val mandrake: UInt = 0u,
    @SerialName("philosopher_stone")
    val philosopherStone: UInt = 0u,
    val realgar: UInt = 0u,
    val sulfur: UInt = 0u,
    val mercury: UInt = 0u,
    val metal: UInt = 0u,
    val silver: UInt = 0u,
    val gold: UInt = 0u,
    val salt: UInt = 0u,
    val cost: UInt = 0u,
    val updated: String
) {
    companion object {
        val mapper = {
                id: Long,
                card_ref: String,
                wood: Long,
                leather: Long,
                belladona: Long,
                mandrake: Long,
                philosopher_stone: Long,
                realgar: Long,
                sulfur: Long,
                mercury: Long,
                metal: Long,
                silver: Long,
                gold: Long,
                salt: Long,
                cost: Long,
                updated: String,
            ->
            Formulae(
                id = id!!,
                cardRef = card_ref,
                wood = wood.toUInt(),
                leather = leather.toUInt(),
                belladona = belladona.toUInt(),
                mandrake = mandrake.toUInt(),
                philosopherStone = philosopher_stone.toUInt(),
                realgar = realgar.toUInt(),
                sulfur = sulfur.toUInt(),
                mercury = mercury.toUInt(),
                metal = metal.toUInt(),
                silver = silver.toUInt(),
                gold = gold.toUInt(),
                salt = salt.toUInt(),
                cost = cost.toUInt(),
                updated = updated
            )
        }
    }
}
