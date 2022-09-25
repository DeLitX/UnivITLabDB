package com.delitx.common.db.type

import kotlinx.serialization.Serializable

@Serializable
data class TypeString(val value: String) : Type {
    override val name: String
        get() = "String"

    override fun toString(): String = value
}
