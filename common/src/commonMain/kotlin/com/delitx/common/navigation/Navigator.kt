package com.delitx.common.navigation

import com.delitx.common.db.row.Row
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

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
    abstract val screensCanNavigateTo: List<KClass<Screen>>

    fun canNavigateTo(screen: Screen): Boolean = screensCanNavigateTo.any { it.isInstance(screen) }

    object StartScreen : Screen() {
        override val screensCanNavigateTo: List<KClass<Screen>> = listOf(
            DatabaseScreen::class
        ) as List<KClass<Screen>>
    }

    object DatabaseScreen : Screen() {
        override val screensCanNavigateTo: List<KClass<Screen>> = listOf(
            TableScreen::class,
            TableCreateScreen::class,
            MergeTablesScreen::class
        ) as List<KClass<Screen>>
    }

    object TableScreen : Screen() {
        override val screensCanNavigateTo: List<KClass<Screen>> = listOf(
            RowEditScreen::class,
            RowCreateScreen::class
        ) as List<KClass<Screen>>
    }

    object TableCreateScreen : Screen() {
        override val screensCanNavigateTo: List<KClass<Screen>> = listOf()
    }

    object MergeTablesScreen : Screen() {
        override val screensCanNavigateTo: List<KClass<Screen>> = listOf()
    }

    data class RowEditScreen(val row: Row) : Screen() {
        override val screensCanNavigateTo: List<KClass<Screen>> = listOf()
    }

    object RowCreateScreen : Screen() {
        override val screensCanNavigateTo: List<KClass<Screen>> = listOf()
    }
}
