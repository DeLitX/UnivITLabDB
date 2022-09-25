package com.delitx.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.delitx.common.db.table.Attribute
import com.delitx.common.db.table.Table
import com.delitx.common.db.type.Type
import com.delitx.common.utils.updateValue

@Composable
fun TableCreateLayout(modifier: Modifier = Modifier, onTableCreate: (Table) -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var attributes by rememberSaveable { mutableStateOf(listOf<Attribute>()) }
    LazyColumn(modifier = modifier) {
        item {
            Row {
                Text("Name:")
                TextField(name, onValueChange = { name = it })
            }
        }
        items(attributes.size) { index ->
            val attribute = attributes[index]
            Column(modifier = Modifier.padding(top = 10.dp)) {
                Row {
                    Text("Attribute name:")
                    TextField(
                        attribute.name,
                        onValueChange = { newName ->
                            attributes = attributes.updateValue(index) { attributeValue ->
                                Attribute.create(newName, attributeValue.type::class)
                            }
                        }
                    )
                }
                AttributeTypeLayout(
                    attribute.type,
                    onTypeChange = { attributeValue ->
                        attributes = attributes.updateValue(index) {
                            Attribute.create(
                                attribute.name,
                                attributeValue::class
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }
        }
        item {
            Button(onClick = {
                attributes =
                    attributes + Attribute.create(name = "", Type.getSubclasses()[0]::class)
            }, modifier = Modifier.fillMaxWidth(1f)) {
                Text("Add new attribute")
            }
            Button(onClick = {
                onTableCreate(Table.create(name, attributes, listOf()))
            }, modifier = Modifier.fillMaxWidth(1f)) {
                Text("Save table")
            }
        }
    }
}

@Composable
private fun AttributeTypeLayout(
    type: Type,
    onTypeChange: (Type) -> Unit,
    modifier: Modifier = Modifier
) {
    val sealedSubclasses = Type.getSubclasses()
    LazyRow(modifier = modifier) {
        items(sealedSubclasses.size) { index ->
            val subclass = sealedSubclasses[index]
            Column(
                modifier = Modifier
                    .fillParentMaxWidth(1 / sealedSubclasses.size.toFloat()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(subclass.name)
                RadioButton(
                    selected = subclass::class.isInstance(type),
                    onClick = { onTypeChange(subclass) }
                )
            }
        }
    }
}
