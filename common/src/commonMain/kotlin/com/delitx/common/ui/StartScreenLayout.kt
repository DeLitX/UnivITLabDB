package com.delitx.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun StartScreenLayout(
    onDatabaseCreate: () -> Unit,
    onDatabaseLoad: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Column {
            Button(onClick = onDatabaseCreate, modifier = Modifier.fillMaxWidth(1f)) {
                Text("Create new database")
            }
            Button(onClick = onDatabaseLoad, modifier = Modifier.fillMaxWidth(1f)) {
                Text("Load database")
            }
        }
    }
}
