package com.delitx.common.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.delitx.common.db.row.Row

@Composable
fun RowLayout(row: Row, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        for ((index, i) in row.values.withIndex()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(1 / (row.values.size - index).toFloat())
                    .border(1.dp, Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(i.toString())
            }
        }
    }
}
