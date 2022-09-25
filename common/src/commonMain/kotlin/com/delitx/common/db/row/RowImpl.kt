package com.delitx.common.db.row

import com.delitx.common.db.type.Type
import kotlinx.serialization.Serializable

@Serializable
data class RowImpl(override val values: List<Type>) : Row {
    override fun copy(update: (List<Type>) -> List<Type>): Row =
        RowImpl(update(values))
}
