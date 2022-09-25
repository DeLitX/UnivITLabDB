package com.delitx.common

import kotlinx.coroutines.CoroutineScope

expect open class ViewModel() {
    val scope: CoroutineScope
}
