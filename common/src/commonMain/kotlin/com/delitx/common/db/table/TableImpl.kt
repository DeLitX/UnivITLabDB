package com.delitx.common.db.table

import com.delitx.common.db.row.Row
import kotlinx.serialization.Serializable

@Serializable
data class TableImpl(
    override val name: String,
    override val attributes: List<Attribute>,
    override val rows: List<Row>
) : Table {

    init {
        for (row in rows) {
            require(isRowFits(row))
        }
    }

    override fun deleteRow(index: Int): Table =
        copy(
            rows = rows.subList(0, index) + rows.subList(index + 1, rows.size)
        )

    override fun addRow(row: Row): Table {
        require(isRowFits(row))
        return copy(rows = rows + row)
    }

    override fun updateRow(index: Int, update: (Row) -> Row): Table =
        copy(
            rows = rows.subList(0, index) +
                update(rows[index]) +
                rows.subList(index + 1, rows.size)
        )

    private fun isRowFits(row: Row): Boolean {
        if (row.values.size != attributes.size) {
            return false
        }
        for (i in attributes.indices) {
            if (!attributes[i].isThisType(row.values[i])) {
                return false
            }
        }
        return true
    }
}
