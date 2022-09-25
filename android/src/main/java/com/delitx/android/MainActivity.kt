package com.delitx.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import com.delitx.common.App
import com.delitx.common.AppViewModel
import com.delitx.common.navigation.Navigator

class MainActivity : AppCompatActivity() {
    companion object {
        val viewModel = AppViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                App(viewModel)
            }
        }
    }

    override fun onBackPressed() {
        Navigator.navigateBack()
    }
}
