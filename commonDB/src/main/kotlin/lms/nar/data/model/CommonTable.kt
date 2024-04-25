package lms.nar.data.model

enum class CommonTable {
    CARD,
    REWARD,
    FORMULAE,

    //region EQUIPMENT
    ARMOR,
    ARMOR_TRANSLATION,
    ARTIFACT,
    ARTIFACT_TRANSLATION,
    CARD_SET,
    CARD_SET_TRANSLATION,
    HELMET,
    HELMET_TRANSLATION,
    ITEM,
    ITEM_TRANSLATION,
    OIL,
    OIL_TRANSLATION,
    POTION,
    POTION_TRANSLATION,
    SHIELD,
    SHIELD_TRANSLATION,
    SPELL,
    SPELL_TRANSLATION,
    WEAPON,
    WEAPON_TRANSLATION,
    //endregion

    //region MISSION
    MISSION,
    MISSION_TRANSLATION,
    MISSION_CARD,
    MISSION_CHOICE,
    //endregion

    //region LOCATION
    CITY,
    CITY_DISTANCE,
    CITY_TRANSLATION,
    //endregion

    //region HERO
    HERO,
    HERO_TRANSLATION,
    HERO_CLASS,
    HERO_CLASS_TRANSLATION,
    SKILL,
    SKILL_TRANSLATION,
    INJURY,
    INJURY_TRANSLATION;
    //endregion

    companion object {
        fun parse(tableName: String): CommonTable? = runCatching {
            CommonTable.valueOf(tableName)
        }.onFailure {
            //TODO : Logging
        }.getOrNull()
    }
}