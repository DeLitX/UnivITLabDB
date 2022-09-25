package com.delitx.common.db.database

interface DatabaseSaver<T> {
    fun saveDatabase(database: Database, saveTo: T)
}
