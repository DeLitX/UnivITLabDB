package com.delitx.common.db.database

import com.delitx.common.db.table.Table
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseImpl(override val tables: List<Table>) : Database {
    override fun removeTable(index: Int): Database =
        DatabaseImpl(
            tables.subList(0, index) +
                tables.subList(index + 1, tables.size)
        )

    override fun addTable(table: Table): Database =
        DatabaseImpl(tables + table)

    override fun updateTable(index: Int, update: (Table) -> Table): Database =
        DatabaseImpl(
            tables.subList(0, index) +
                update(tables[index]) +
                tables.subList(index + 1, tables.size)
        )
}
