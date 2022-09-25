// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.delitx.common.App
import com.delitx.common.AppViewModel
import com.delitx.common.navigation.Navigator

val viewModel = AppViewModel()
fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Column {
            Button(onClick = { Navigator.navigateBack() }) {
                Text("Back")
            }
            App(viewModel)
        }
    }
}
