package com.delitx.common.file

import android.content.ContentResolver
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.core.net.toUri
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.InputStreamReader
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

var contentResolver: ContentResolver? = null
var fileSelector: ActivityResultLauncher<Array<String>>? = null
var fileSaveSelector: ActivityResultLauncher<String?>? = null
var onFileSelected: (Uri) -> Unit = {}
var onFileNotSelected: () -> Unit = {}
var onFileSaveSelected: (Uri) -> Unit = {}
var onFileSaveNotSelected: () -> Unit = {}

actual class File actual constructor(actual val path: String) {

    private val uri: Uri
        get() = path.toUri()

    actual fun getContent(): String =
        readFile(uri)

    actual fun setContent(content: String) {
        writeFile(uri, content)
    }
}

private fun writeFile(uri: Uri, content: String) {
    contentResolver?.openFileDescriptor(uri, "w")
        ?.use { descriptor ->
            FileOutputStream(descriptor.fileDescriptor).use { inputStream ->
                inputStream.write(content.toByteArray())
            }
        }
}

private fun readFile(uri: Uri): String {
    val stringBuilder = StringBuilder()
    contentResolver?.openInputStream(uri)?.use { inputStream ->
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            var line: String? = reader.readLine()
            while (line != null) {
                stringBuilder.append(line)
                stringBuilder.append('\n')
                line = reader.readLine()
            }
        }
    }
    return stringBuilder.toString()
}

actual suspend fun selectFolderPath(): String? = suspendCoroutine { continuation ->
    onFileSaveSelected = {
        continuation.resume(it.toString())
    }
    onFileSaveNotSelected = {
        continuation.resume(null)
    }
    if (fileSaveSelector == null) {
        continuation.resume(null)
    } else {
        fileSaveSelector?.launch("database.txt")
    }
}

actual suspend fun selectFilePath(): String? = suspendCoroutine { continuation ->
    onFileSelected = {
        continuation.resume(it.toString())
    }
    onFileNotSelected = {
        continuation.resume(null)
    }
    if (fileSelector == null) {
        continuation.resume(null)
    } else {
        fileSelector?.launch(arrayOf("text/plain"))
    }
}
