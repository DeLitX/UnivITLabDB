package com.delitx.common.db.table

import com.delitx.common.db.type.Type
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttributeImpl(
    override val name: String,
    @SerialName("attribute_type") override val type: Type,
) : Attribute {
    override fun isThisType(type: Type): Boolean = this.type::class.isInstance(type)
}
