package com.delitx.common.db.table

import com.delitx.common.db.row.Row

interface Table {
    val name: String
    val attributes: List<Attribute>
    val rows: List<Row>

    fun deleteRow(index: Int): Table

    fun addRow(row: Row): Table

    fun updateRow(index: Int, update: (Row) -> Row): Table

    companion object {
        fun create(name: String, attributes: List<Attribute>, rows: List<Row>): Table =
            TableImpl(name, attributes, rows)
    }
}
