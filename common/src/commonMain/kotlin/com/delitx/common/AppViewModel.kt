package com.delitx.common

import com.delitx.common.db.database.Database
import com.delitx.common.db.row.Row
import com.delitx.common.db.table.Table
import kotlinx.coroutines.flow.*

class AppViewModel : ViewModel() {
    private val _database = MutableStateFlow<Database?>(Database.create(listOf()))
    val database = _database.asStateFlow()

    val tables = database.map {
        it?.tables
    }

    private val _selectedIndex = MutableStateFlow(-1)
    val selectedIndex = _selectedIndex.asStateFlow()

    val selectedTable = selectedIndex.combine(tables) { index, tables ->
        tables?.getOrNull(index)
    }

    fun selectTable(table: Table) {
        _selectedIndex.update { database.value?.tables?.indexOf(table) ?: -1 }
    }

    fun unselectTable() {
        _selectedIndex.update { -1 }
    }

    fun createDatabase(database: Database) {
        _database.update { database }
    }

    fun addTable(table: Table) {
        _database.update { database ->
            database?.addTable(table) ?: Database.create(listOf(table))
        }
    }

    fun addRow(table: Table, row: Row) {
        _database.update { database ->
            database!!.updateTable(database.tables.indexOf(table)) { table ->
                table.addRow(row)
            }
        }
    }

    fun updateTable(oldTable: Table, newTable: Table) {
        _database.update { database ->
            database!!.updateTable(database.tables.indexOf(oldTable)) { newTable }
        }
    }

    fun updateRow(table: Table, oldRow: Row, newRow: Row) {
        _database.update { database ->
            database!!.updateTable(database.tables.indexOf(table)) {
                it.updateRow(
                    it.rows.indexOf(
                        oldRow
                    )
                ) { newRow }
            }
        }
    }

    fun deleteRow(table: Table, row: Row) {
        _database.update { database ->
            database!!.updateTable(database.tables.indexOf(table)) {
                it.deleteRow(it.rows.indexOf(row))
            }
        }
    }
}
