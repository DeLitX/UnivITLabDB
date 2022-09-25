package com.delitx.common.file

actual class File actual constructor(actual val path: String) {

    private val file = java.io.File(path)

    actual fun getContent(): String =
        file.readLines().joinToString("")

    actual fun setContent(content: String) {
        file.writeText(content)
    }
}

actual suspend fun selectFolderPath(): String? {
    return null
}

actual suspend fun selectFilePath(): String? {
    return null
}
