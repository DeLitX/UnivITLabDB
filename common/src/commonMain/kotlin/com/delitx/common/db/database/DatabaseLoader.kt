package com.delitx.common.db.database

interface DatabaseLoader<T> {
    fun load(source: T): Database
}
