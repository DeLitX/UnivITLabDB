package com.delitx.common.db.database

import com.delitx.common.file.File
import com.delitx.common.json
import kotlinx.serialization.decodeFromString

class FileDatabaseLoader : DatabaseLoader<File> {
    override fun load(source: File): Database {
        val content = source.getContent()
        return json.decodeFromString(content)
    }
}
