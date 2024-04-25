@file:Suppress("LocalVariableName")

package lms.nar.data.model.equipment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Armor(
    val id: Long,
    @SerialName("card_ref")
    val cardRef: String,
    @SerialName("slot_weight")
    val slotWeight: UInt,
    @SerialName("slot_type")
    val slotType: String, //ENUM?
    @SerialName("reward_id")
    val rewardId: Int?,
    val updated: String,
) {
    companion object {
        val mapper = { id: Long?,
                       card_ref: String,
                       slot_weight: Long,
                       slot_type: String,
                       reward_id: Long?,
                       updated: String ->
            Armor(
                id = id!!,
                cardRef = card_ref,
                slotWeight = slot_weight.toUInt(),
                slotType = slot_type,
                rewardId = reward_id?.toInt(),
                updated = updated,
            )
        }
    }
}

@Serializable
data class ArmorTranslation(
    val id: Long,
    @SerialName("armor_id")
    val armorId: Long,
    var name: String,
    @SerialName("language_code")
    var languageCode: String,
    var description: String?,
    var traits: String?,
    var updated: String,
) {
    companion object {
        val mapper =
            { id: Long?, armor_id: Long, name: String, description: String?, traits: String?, updated: String, language_code: String ->
                ArmorTranslation(
                    id = id!!,
                    armorId = armor_id,
                    name = name,
                    languageCode = language_code,
                    description = description,
                    traits = traits,
                    updated = updated
                )
            }
    }
}

data class ArmorCard(
    val id: Long,
    @SerialName("card_ref")
    val cardRef: String,
    var name: String,
    var description: String?,
    var traits: String?,
    @SerialName("slot_weight")
    val slotWeight: UInt,
    @SerialName("slot_type")
    val slotType: String, //ENUM?
    @SerialName("reward_id")
    val rewardId: Int?,
) {
    companion object {
        val mapper = { id: Long?,
                       card_ref: String,
                       reward_id: Long?,
                       slot_type: String,
                       slot_weight: Long,
                       translated_name: String?,
                       translated_description: String?,
                       translated_traits: String? ->

            ArmorCard(
                id = id!!,
                cardRef = card_ref,
                name = translated_name!!,
                description = translated_description,
                traits = translated_traits,
                slotWeight = slot_weight.toUInt(),
                slotType = slot_type,
                rewardId = reward_id?.toInt()
            )

        }
    }
}