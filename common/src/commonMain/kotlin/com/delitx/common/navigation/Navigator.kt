package com.delitx.common.navigation

import com.delitx.common.db.row.Row
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

object Navigator {

    private var stack = MutableStateFlow(listOf<Screen>())

    val currentScreen = stack.map { it.last() }

    fun navigateTo(screen: Screen) {
        if (stack.value.isEmpty() || stack.value.last().canNavigateTo(screen)) {
            stack.update { it + screen }
        }
    }

    fun navigateBack() {
        if (stack.value.size > 1) {
            stack.update { it.subList(0, it.size - 1) }
        }
    }
}

sealed class Screen {
    abstract fun canNavigateTo(screen: Screen): Boolean

    object StartScreen : Screen() {
        override fun canNavigateTo(screen: Screen): Boolean = screen is DatabaseScreen
    }

    object DatabaseScreen : Screen() {
        override fun canNavigateTo(screen: Screen) = screen is TableScreen ||
            screen is TableCreateScreen
    }

    object TableScreen : Screen() {
        override fun canNavigateTo(screen: Screen) =
            screen is RowEditScreen || screen is RowCreateScreen
    }

    object TableCreateScreen : Screen() {
        override fun canNavigateTo(screen: Screen) = false
    }

    data class RowEditScreen(val row: Row) : Screen() {
        override fun canNavigateTo(screen: Screen): Boolean = false
    }

    object RowCreateScreen : Screen() {
        override fun canNavigateTo(screen: Screen): Boolean = false
    }
}
