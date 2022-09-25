package com.delitx.common

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual open class ViewModel actual constructor() : androidx.lifecycle.ViewModel() {
    actual val scope: CoroutineScope = viewModelScope
}
