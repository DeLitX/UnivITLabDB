package com.delitx.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

actual open class ViewModel actual constructor(){
    actual val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
}
