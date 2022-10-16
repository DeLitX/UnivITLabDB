package com.delitx.common

import java.text.ParseException
import java.text.SimpleDateFormat
import kotlinx.serialization.Serializable

@Serializable(with = DateSerializer::class)
actual data class Date actual constructor(actual val value: Long) {
    actual constructor(value: String) : this(
        kotlin.run {
            try {
                val parsed = SimpleDateFormat(DateFormat).parse(value)
                parsed?.time ?: throw DateParseException()
            } catch (e: ParseException) {
                throw DateParseException()
            }
        }
    )

    actual override fun toString(): String =
        SimpleDateFormat(DateFormat).format(java.util.Date(value))
}
