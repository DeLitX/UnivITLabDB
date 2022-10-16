package com.delitx.common

import com.delitx.common.db.database.Database
import com.delitx.common.db.row.Row
import com.delitx.common.db.table.Attribute
import com.delitx.common.db.table.Table
import com.delitx.common.db.table.mergeTablesByField
import com.delitx.common.db.type.TypeDate
import com.delitx.common.db.type.TypeInt
import com.delitx.common.db.type.TypeString
import kotlin.test.Test
import kotlin.test.assertEquals

class Test {

    @Test
    fun addTableTest() {
        val startDatabase = Database.create(listOf())
        val table = Table.create(
            name = "testTable",
            attributes = listOf(
                Attribute.create("testAttribute1", TypeString::class),
                Attribute.create("testAttribute2", TypeInt::class)
            ),
            rows = listOf(
                Row.create(listOf(TypeString("testRow1"), TypeInt(1))),
                Row.create(listOf(TypeString("testRow2"), TypeInt(2)))
            )
        )
        val expectDatabase = Database.create(listOf(table))
        val actualDatabase = startDatabase.addTable(table)
        assertEquals(expectDatabase, actualDatabase)
    }

    @Test
    fun deleteTableTest() {
        val table = Table.create(
            name = "testTable",
            attributes = listOf(
                Attribute.create("testAttribute1", TypeString::class),
                Attribute.create("testAttribute2", TypeInt::class)
            ),
            rows = listOf(
                Row.create(listOf(TypeString("testRow1"), TypeInt(1))),
                Row.create(listOf(TypeString("testRow2"), TypeInt(2)))
            )
        )
        val startDatabase = Database.create(listOf(table))
        val expectDatabase = Database.create(listOf())
        val actualDatabase = startDatabase.removeTable(0)
        assertEquals(expectDatabase, actualDatabase)
    }

    @Test
    fun addColumnTest() {
        val startTable = Table.create(
            name = "testTable",
            attributes = listOf(
                Attribute.create("testAttribute1", TypeString::class),
                Attribute.create("testAttribute2", TypeInt::class)
            ),
            rows = listOf(
                Row.create(listOf(TypeString("testRow1"), TypeInt(1))),
                Row.create(listOf(TypeString("testRow2"), TypeInt(2)))
            )
        )
        val row = Row.create(listOf(TypeString("testRow3"), TypeInt(3)))
        val expectTable = Table.create(
            name = "testTable",
            attributes = listOf(
                Attribute.create("testAttribute1", TypeString::class),
                Attribute.create("testAttribute2", TypeInt::class)
            ),
            rows = listOf(
                Row.create(listOf(TypeString("testRow1"), TypeInt(1))),
                Row.create(listOf(TypeString("testRow2"), TypeInt(2))),
                Row.create(listOf(TypeString("testRow3"), TypeInt(3)))
            )
        )
        val actualTable = startTable.addRow(row)
        assertEquals(expectTable, actualTable)
    }

    @Test
    fun deleteColumnTest() {
        val startTable = Table.create(
            name = "testTable",
            attributes = listOf(
                Attribute.create("testAttribute1", TypeString::class),
                Attribute.create("testAttribute2", TypeInt::class)
            ),
            rows = listOf(
                Row.create(listOf(TypeString("testRow1"), TypeInt(1))),
                Row.create(listOf(TypeString("testRow2"), TypeInt(2)))
            )
        )
        val expectTable = Table.create(
            name = "testTable",
            attributes = listOf(
                Attribute.create("testAttribute1", TypeString::class),
                Attribute.create("testAttribute2", TypeInt::class)
            ),
            rows = listOf(
                Row.create(listOf(TypeString("testRow1"), TypeInt(1)))
            )
        )
        val actualTable = startTable.deleteRow(1)
        assertEquals(expectTable, actualTable)
    }

    @Test
    fun mergeTablesTest() {
        val table1 = Table.create(
            name = "testTable1",
            attributes = listOf(
                Attribute.create("testAttribute1", TypeString::class),
                Attribute.create("testAttribute2", TypeInt::class)
            ),
            rows = listOf(
                Row.create(listOf(TypeString("testRow1"), TypeInt(1))),
                Row.create(listOf(TypeString("testRow2"), TypeInt(2))),
                Row.create(listOf(TypeString("testRow3"), TypeInt(3)))
            )
        )
        val table2 = Table.create(
            name = "testTable2",
            attributes = listOf(
                Attribute.create("testAttribute3", TypeDate::class),
                Attribute.create("testAttribute4", TypeInt::class)
            ),
            rows = listOf(
                Row.create(listOf(TypeDate(Date("1.1.1")), TypeInt(1))),
                Row.create(listOf(TypeDate(Date("2.2.2")), TypeInt(2))),
                Row.create(listOf(TypeDate(Date("4.4.4")), TypeInt(4)))
            )
        )
        val newTableName = "testTable3"
        val newAttributeName = "testAttribute5"
        val expectTable = Table.create(
            name = newTableName,
            attributes = listOf(
                Attribute.create("testAttribute1", TypeString::class),
                Attribute.create("testAttribute3", TypeDate::class),
                Attribute.create(newAttributeName, TypeInt::class)
            ),
            rows = listOf(
                Row.create(listOf(TypeString("testRow1"), TypeDate(Date("1.1.1")), TypeInt(1))),
                Row.create(listOf(TypeString("testRow2"), TypeDate(Date("2.2.2")), TypeInt(2)))
            )
        )
        val actualTable = mergeTablesByField(table1, table2, 1, 1, newAttributeName, newTableName)
        assertEquals(expectTable, actualTable)
    }
}
