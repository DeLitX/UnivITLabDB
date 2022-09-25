package com.delitx.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.delitx.common.db.table.Table

@Composable
fun TableMiniLayout(table: Table, modifier: Modifier = Modifier, onDeleteClick: () -> Unit) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("Name: ${table.name}")
        Button(onClick = onDeleteClick) {
            Text(text = "Delete table")
        }
    }
}
