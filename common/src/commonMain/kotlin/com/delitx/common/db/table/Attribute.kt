package com.delitx.common.db.table

import com.delitx.common.Date
import com.delitx.common.db.type.*
import kotlin.reflect.KClass

interface Attribute {
    val name: String
    val type: Type

    fun isThisType(type: Type): Boolean

    companion object {
        fun <T : Type> create(name: String, type: KClass<T>): Attribute =
            AttributeImpl(
                name,
                when (type) {
                    TypeInt::class -> TypeInt(0)
                    TypeChar::class -> TypeChar(Char(0))
                    TypeDate::class -> TypeDate(Date(0L))
                    TypeDateInvl::class -> TypeDateInvl(TypeDate(Date(0L)), TypeDate(Date(0L)))
                    TypeString::class -> TypeString("")
                    TypeDouble::class -> TypeDouble(0.0)
                    else -> error("invalid type class")
                }
            )
    }
}
