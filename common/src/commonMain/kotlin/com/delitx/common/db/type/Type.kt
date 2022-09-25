package com.delitx.common.db.type

import com.delitx.common.Date

sealed interface Type {
    val name: String

    companion object {
        fun getSubclasses(): List<Type> = listOf(
            TypeDate(Date(0L)),
            TypeDateInvl(TypeDate(Date(0L)), TypeDate(Date(0L))),
            TypeInt(0),
            TypeString(""),
            TypeDouble(0.0),
            TypeChar(' ')
        )
    }
}
