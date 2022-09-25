package com.delitx.common.db.type

import kotlinx.serialization.Serializable

@Serializable
data class TypeInt(val value: Int) : Type {
    override val name: String
        get() = "Int"

    override fun toString(): String = value.toString()
}
