package com.delitx.common.db.database

import com.delitx.common.db.table.Table

interface Database {
    val tables: List<Table>

    fun removeTable(index: Int): Database

    fun addTable(table: Table): Database

    fun updateTable(index: Int, update: (Table) -> Table): Database

    companion object {
        fun create(tables: List<Table>): Database = DatabaseImpl(tables)
    }
}
