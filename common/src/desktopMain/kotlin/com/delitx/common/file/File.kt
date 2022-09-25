package com.delitx.common.file

import javax.swing.JFileChooser

actual class File actual constructor(actual val path: String) {

    private val file = java.io.File(path)

    actual fun getContent(): String =
        file.readLines().joinToString("")

    actual fun setContent(content: String) {
        file.writeText(content)
    }
}

actual suspend fun selectFolderPath(): String? {
    val chooser = JFileChooser()
    return when (chooser.showOpenDialog(null)) {
        JFileChooser.APPROVE_OPTION -> chooser.selectedFile.path
        else -> null
    }
}

actual suspend fun selectFilePath(): String? {
    val chooser = JFileChooser()
    return when (chooser.showSaveDialog(null)) {
        JFileChooser.APPROVE_OPTION -> chooser.selectedFile.path
        else -> null
    }
}
