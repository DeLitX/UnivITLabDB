package com.delitx.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.delitx.common.db.table.Attribute
import com.delitx.common.db.table.Table
import com.delitx.common.db.table.mergeTablesByField

@Composable
fun MergeTablesLayout(
    tables: List<Table>,
    onMergeCompleted: (Table) -> Unit,
    modifier: Modifier = Modifier
) {
    var resultTableName by rememberSaveable { mutableStateOf("") }
    var resultFieldName by rememberSaveable { mutableStateOf("") }
    var firstTableIndex by rememberSaveable { mutableStateOf(0) }
    var firstAttributeIndex by rememberSaveable { mutableStateOf(0) }
    var secondTableIndex by rememberSaveable { mutableStateOf(1) }
    var secondAttributeIndex by rememberSaveable { mutableStateOf(0) }
    LazyColumn(modifier = modifier) {
        item {
            Row {
                Text("Result table name:")
                TextField(resultTableName, onValueChange = { resultTableName = it })
            }
            Row {
                Text("Result field name:")
                TextField(resultFieldName, onValueChange = { resultFieldName = it })
            }
            Text("First table")
            TableSelector(
                tables,
                firstTableIndex,
                onIndexSelected = { index ->
                    if (secondTableIndex != index) {
                        firstTableIndex = index
                        firstAttributeIndex = 0
                    }
                },
                modifier = Modifier.fillParentMaxWidth(1f)
            )
            Text("First attribute")
            AttributeSelector(
                tables[firstTableIndex].attributes,
                firstAttributeIndex,
                onIndexSelected = { index ->
                    firstAttributeIndex = index
                },
                modifier = Modifier.fillParentMaxWidth(1f)
            )
            Text("Second table")
            TableSelector(
                tables,
                secondTableIndex,
                onIndexSelected = { index ->
                    if (firstTableIndex != index) {
                        secondTableIndex = index
                        secondAttributeIndex = 0
                    }
                },
                modifier = Modifier.fillParentMaxWidth(1f)
            )
            Text("Second attribute")
            AttributeSelector(
                tables[secondTableIndex].attributes,
                secondAttributeIndex,
                onIndexSelected = { index ->
                    secondAttributeIndex = index
                },
                modifier = Modifier.fillParentMaxWidth(1f)
            )
            val firstTable = tables[firstTableIndex]
            val secondTable = tables[secondTableIndex]
            val firstAttribute = firstTable.attributes[firstAttributeIndex]
            val secondAttribute = secondTable.attributes[secondAttributeIndex]
            val canMergeTables = firstAttribute.isThisType(secondAttribute.type)
            if (!canMergeTables) {
                Text("Attributes must have same types", color = Color.Red)
            }
            Button(
                onClick = {
                    val resultTable = mergeTablesByField(
                        firstTable,
                        secondTable,
                        firstAttributeIndex,
                        secondAttributeIndex,
                        resultFieldName,
                        resultTableName
                    )
                    onMergeCompleted(resultTable)
                },
                enabled = canMergeTables,
                modifier = Modifier.fillParentMaxWidth(1f)
            ) {
                Text("Merge tables")
            }
        }
    }
}

@Composable
private fun AttributeSelector(
    attributes: List<Attribute>,
    selectedAttributeIndex: Int,
    onIndexSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(attributes.size) { index ->
            val attribute = attributes[index]
            Card(
                backgroundColor = if (index == selectedAttributeIndex) {
                    Color.Green
                } else {
                    MaterialTheme.colors.surface
                },
                modifier = Modifier.clickable {
                    onIndexSelected(index)
                }
            ) {
                Column(modifier = Modifier.padding(6.dp)) {
                    Text(attribute.name)
                    Text(attribute.type.name)
                }
            }
        }
    }
}

@Composable
private fun TableSelector(
    tables: List<Table>,
    selectedTableIndex: Int,
    onIndexSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(tables.size) { index ->
            val table = tables[index]
            Card(
                backgroundColor = if (index == selectedTableIndex) {
                    Color.Green
                } else {
                    MaterialTheme.colors.surface
                },
                modifier = Modifier.clickable {
                    onIndexSelected(index)
                }
            ) {
                Text(table.name, modifier = Modifier.padding(6.dp))
            }
        }
    }
}
