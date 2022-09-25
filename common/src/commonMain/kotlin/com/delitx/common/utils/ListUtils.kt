package com.delitx.common.utils

fun <T> List<T>.updateValue(index: Int, update: (T) -> T) =
    subList(0, index) + update(get(index)) + subList(index + 1, size)
