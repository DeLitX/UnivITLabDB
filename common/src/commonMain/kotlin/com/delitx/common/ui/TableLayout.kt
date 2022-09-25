package com.delitx.common.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.delitx.common.db.row.Row
import com.delitx.common.db.table.Table

@Composable
fun TableLayout(
    table: Table,
    modifier: Modifier = Modifier,
    onRowDelete: (Row) -> Unit = {},
    onRowEdit: (Row) -> Unit = {},
    onRowAdd: () -> Unit = {}
) {
    LazyColumn(modifier = modifier, horizontalAlignment = Alignment.End) {
        item {
            Text("Name = ${table.name}", modifier = Modifier.fillParentMaxWidth(1f))
            Button(onClick = onRowAdd, modifier = Modifier.fillParentMaxWidth(1f)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Add Row")
                }
            }
        }
        item {
            Row(modifier = Modifier.fillParentMaxWidth(0.6f)) {
                for ((index, i) in table.attributes.withIndex()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(1 / (table.attributes.size - index).toFloat())
                            .border(1.dp, Color.Black),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(i.name)
                        Text(i.type.name)
                    }
                }
            }
        }
        items(table.rows.size) { index ->
            val row = table.rows[index]
            Row(horizontalArrangement = Arrangement.End) {
                Button(onClick = {
                    onRowDelete(row)
                }) {
                    Text("Delete")
                }
                Button(onClick = {
                    onRowEdit(row)
                }) {
                    Text("Edit")
                }
                RowLayout(
                    row,
                    modifier = Modifier.fillParentMaxWidth(0.6f)
                )
            }
        }
    }
}
