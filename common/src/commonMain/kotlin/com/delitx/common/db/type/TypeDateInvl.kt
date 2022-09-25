package com.delitx.common.db.type

import com.delitx.common.DateParseException
import kotlinx.serialization.Serializable

@Serializable
data class TypeDateInvl(val start: TypeDate, val end: TypeDate) : Type {
    override val name: String
        get() = "DateInvl"

    init {
        if (start.value.value > end.value.value) {
            throw DateParseException()
        }
    }

    override fun toString(): String = "$start - $end"
}
