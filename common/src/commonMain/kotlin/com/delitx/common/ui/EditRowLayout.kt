package com.delitx.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.delitx.common.Date
import com.delitx.common.DateParseException
import com.delitx.common.db.row.Row
import com.delitx.common.db.table.Attribute
import com.delitx.common.db.type.*
import com.delitx.common.utils.updateValue

@Composable
fun EditRowLayout(
    row: Row,
    attributes: List<Attribute>,
    modifier: Modifier = Modifier,
    onRowChangeSave: (Row) -> Unit
) {
    require(row.values.size == attributes.size)
    ChangeRowLayout(
        row.values.map { TypeEditState.Correct(it) },
        attributes,
        modifier = modifier,
        onRowSave = onRowChangeSave
    )
}

@Composable
fun CreateRowLayout(
    attributes: List<Attribute>,
    modifier: Modifier = Modifier,
    onRowSave: (Row) -> Unit
) {
    ChangeRowLayout(
        attributes.map { TypeEditState.Error("") },
        attributes,
        modifier = modifier,
        onRowSave = onRowSave
    )
}

@Composable
private fun ChangeRowLayout(
    values: List<TypeEditState>,
    attributes: List<Attribute>,
    modifier: Modifier = Modifier,
    onRowSave: (Row) -> Unit
) {
    var states by rememberSaveable { mutableStateOf(values) }
    LazyColumn(modifier = modifier) {
        items(states.size) { index ->
            val state = states[index]
            val attribute = attributes[index]
            EditTypeLayout(state, attribute, modifier) { state ->
                states = states.updateValue(index) { state }
            }
        }
        item {
            val types = states.filterIsInstance<TypeEditState.Correct>().map { it.type }
            Button(
                enabled = types.size == states.size,
                onClick = {
                    onRowSave(Row.create(types))
                },
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
private fun EditTypeLayout(
    state: TypeEditState,
    attribute: Attribute,
    modifier: Modifier = Modifier,
    onTypeUpdate: (TypeEditState) -> Unit = {}
) {
    Column(modifier = modifier) {
        Text(attribute.name)
        Text(attribute.type.name)
        TextField(
            state.value,
            isError = when (state) {
                is TypeEditState.Correct -> false
                is TypeEditState.Error -> true
            },
            onValueChange = { value ->
                onTypeUpdate(value.toTypeEditState(attribute))
            }
        )
    }
}

private sealed class TypeEditState {
    abstract val value: String

    data class Correct(val type: Type, override val value: String = type.toString()) :
        TypeEditState()

    data class Error(override val value: String) : TypeEditState()
}

private fun String.toTypeEditState(attribute: Attribute): TypeEditState = when (attribute.type) {
    is TypeChar -> {
        if (length == 1) {
            TypeEditState.Correct(TypeChar(get(0)))
        } else {
            TypeEditState.Error(this)
        }
    }

    is TypeDate -> {
        try {
            TypeEditState.Correct(TypeDate(Date(this)), this)
        } catch (e: DateParseException) {
            TypeEditState.Error(this)
        }
    }

    is TypeDateInvl -> {
        try {
            val splitted = split("-")
            if (splitted.size != 2) {
                TypeEditState.Error(this)
            } else {
                TypeEditState.Correct(
                    TypeDateInvl(
                        TypeDate(Date(splitted[0].trim())),
                        TypeDate(Date(splitted[1].trim()))
                    ),
                    this
                )
            }
        } catch (e: DateParseException) {
            TypeEditState.Error(this)
        }
    }

    is TypeDouble -> {
        val double = toDoubleOrNull()
        if (double == null) {
            TypeEditState.Error(
                this
            )
        } else {
            TypeEditState.Correct(TypeDouble(double))
        }
    }

    is TypeInt -> {
        val int = toIntOrNull()
        if (int == null) {
            TypeEditState.Error(this)
        } else {
            TypeEditState.Correct(TypeInt(int))
        }
    }

    is TypeString -> {
        TypeEditState.Correct(TypeString(this))
    }
}
