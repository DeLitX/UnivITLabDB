package com.delitx.common.file

expect class File(path: String) {
    val path: String
    fun getContent(): String
    fun setContent(content: String)
}

expect suspend fun selectFilePath(): String?
expect suspend fun selectFolderPath(): String?
