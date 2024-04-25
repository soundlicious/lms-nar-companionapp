@file:Suppress("LocalVariableName")

package lms.nar.data.model.status

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeletedRow(
    val id: Long,
    @SerialName("deletion_date")
    val deletionDate: String,
    @SerialName("row_id")
    val rowId: Long,
    @SerialName("table_name")
    val tableName: String
) {
    companion object {
        val mapper = { id: Long, deletion_date: String, row_id: Long, table_name: String? ->
            DeletedRow(
                id = id,
                deletionDate = deletion_date,
                rowId = row_id,
                tableName = table_name!!
            )
        }
    }
}

@Serializable
data class TableUpdate(
    @SerialName("table_name")
    val tableName: String,
    @SerialName("update_date")
    val updateDate: String
) {
    companion object {
        val mapper = { table_name: String, update_date: String ->
            TableUpdate(
                tableName = table_name,
                updateDate = update_date
            )
        }
    }
}

@Serializable
data class UpdateStatus(
    val update: List<TableUpdate>,
    val deletion: List<DeletedRow>
)