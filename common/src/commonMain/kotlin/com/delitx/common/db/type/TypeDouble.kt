package com.delitx.common.db.type

import kotlinx.serialization.Serializable

@Serializable
data class TypeDouble(val value: Double) : Type {
    override val name: String
        get() = "Double"

    override fun toString(): String = value.toString()
}
