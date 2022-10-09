package com.delitx.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import com.delitx.common.App
import com.delitx.common.AppViewModel
import com.delitx.common.file.*
import com.delitx.common.navigation.Navigator
import com.delitx.common.file.contentResolver as fileContentResolver

class MainActivity : AppCompatActivity() {
    companion object {
        val viewModel = AppViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fileSelector = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            if (it != null) {
                onFileSelected(it)
            } else {
                onFileNotSelected()
            }
        }
        fileSaveSelector = registerForActivityResult(ActivityResultContracts.CreateDocument("text/plain")) {
            if (it != null) {
                onFileSaveSelected(it)
            } else {
                onFileSaveNotSelected()
            }
        }
        fileContentResolver = contentResolver
        setContent {
            MaterialTheme {
                App(viewModel)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        fileSelector = null
        fileSaveSelector = null
        fileContentResolver = null
    }

    override fun onBackPressed() {
        Navigator.navigateBack()
    }
}
