package com.delitx.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.delitx.common.db.database.Database
import com.delitx.common.db.table.Table

@Composable
fun DatabaseLayout(
    database: Database,
    modifier: Modifier = Modifier,
    onTableClick: (Table) -> Unit = {},
    onTableDeleteClick: (Table) -> Unit = {},
    onCreateNewTableClick: () -> Unit = {},
    onMergeTablesClick: () -> Unit = {},
    onDatabaseSave: () -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        item {
            if (database.tables.size >= 2) {
                Button(onClick = onMergeTablesClick, modifier = Modifier.fillParentMaxWidth(1f)) {
                    Text("Merge tables")
                }
            }
        }
        items(database.tables.size) { index ->
            val table = database.tables[index]
            TableMiniLayout(
                table,
                modifier = Modifier.fillParentMaxWidth(1f)
                    .clickable {
                        onTableClick(table)
                    },
                onDeleteClick = { onTableDeleteClick(table) }
            )
        }
        item {
            Button(onClick = onCreateNewTableClick, modifier = Modifier.fillParentMaxWidth(1f)) {
                Text("Create table")
            }
            Button(onClick = onDatabaseSave, modifier = Modifier.fillParentMaxWidth(1f)) {
                Text("Save database to file")
            }
        }
    }
}
