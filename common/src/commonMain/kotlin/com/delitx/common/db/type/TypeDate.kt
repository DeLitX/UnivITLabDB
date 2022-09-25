package com.delitx.common.db.type

import com.delitx.common.Date
import kotlinx.serialization.Serializable

@Serializable
data class TypeDate(val value: Date) : Type {
    override val name: String
        get() = "Date"

    override fun toString(): String = value.toString()
}
