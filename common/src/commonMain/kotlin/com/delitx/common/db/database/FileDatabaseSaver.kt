package com.delitx.common.db.database

import com.delitx.common.file.File
import com.delitx.common.json
import kotlinx.serialization.encodeToString

class FileDatabaseSaver : DatabaseSaver<File> {
    override fun saveDatabase(database: Database, saveTo: File) {
        val encoded = json.encodeToString(database)
        saveTo.setContent(encoded)
    }
}
