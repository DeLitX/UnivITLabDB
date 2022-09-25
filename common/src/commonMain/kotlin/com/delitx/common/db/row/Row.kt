package com.delitx.common.db.row

import com.delitx.common.db.type.Type

interface Row {
    val values: List<Type>

    fun copy(update: (List<Type>) -> List<Type> = { it }): Row

    companion object {
        fun create(values: List<Type>): Row = RowImpl(values)
    }
}
