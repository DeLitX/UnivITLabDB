package com.delitx.common.db.type

import kotlinx.serialization.Serializable

@Serializable
data class TypeChar(val value: Char) : Type {
    override val name: String
        get() = "Char"

    override fun toString(): String = value.toString()
}
