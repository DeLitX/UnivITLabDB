package com.delitx.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.delitx.common.db.database.Database
import com.delitx.common.file.File
import com.delitx.common.file.selectFilePath
import com.delitx.common.file.selectFolderPath
import com.delitx.common.navigation.Navigator
import com.delitx.common.navigation.Screen
import com.delitx.common.ui.*
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

@Composable
fun App(viewModel: AppViewModel) {
    val database by viewModel.database.collectAsState()
    val selectedTable by viewModel.selectedTable.collectAsState(null)
    LaunchedEffect(Unit) {
        Navigator.navigateTo(Screen.StartScreen)
    }
    val scope = rememberCoroutineScope()
    val currentScreen = Navigator.currentScreen.collectAsState(Screen.StartScreen)
    when (val screen = currentScreen.value) {
        Screen.StartScreen -> {
            StartScreenLayout(
                onDatabaseCreate = {
                    viewModel.createDatabase(Database.create(listOf()))
                    Navigator.navigateTo(Screen.DatabaseScreen)
                },
                onDatabaseLoad = {
                    scope.launch {
                        val path = selectFilePath()
                        if (path != null) {
                            val file = File(path)
                            val db = json.decodeFromString<Database>(file.getContent())
                            viewModel.createDatabase(db)
                        }
                        Navigator.navigateTo(Screen.DatabaseScreen)
                    }
                },
                modifier = Modifier.fillMaxSize(1f)
            )
        }

        is Screen.DatabaseScreen -> {
            DatabaseLayout(
                database = database!!,
                modifier = Modifier.fillMaxSize(1f).padding(horizontal = 20.dp),
                onTableClick = { table ->
                    viewModel.selectTable(table)
                    Navigator.navigateTo(Screen.TableScreen)
                },
                onCreateNewTableClick = {
                    Navigator.navigateTo(Screen.TableCreateScreen)
                },
                onDatabaseSave = {
                    scope.launch {
                        val pathToSave = selectFolderPath()
                        val db = database
                        if (pathToSave != null && db != null) {
                            val file = File(pathToSave)
                            file.setContent(json.encodeToString(db))
                        }
                    }
                },
                onMergeTablesClick = {
                    Navigator.navigateTo(Screen.MergeTablesScreen)
                },
                onTableDeleteClick = { table ->
                    viewModel.deleteTable(table)
                }
            )
        }

        is Screen.TableScreen -> {
            TableLayout(
                selectedTable!!,
                modifier = Modifier.fillMaxSize(1f).padding(horizontal = 20.dp),
                onRowAdd = {
                    Navigator.navigateTo(Screen.RowCreateScreen)
                },
                onRowEdit = { row ->
                    Navigator.navigateTo(Screen.RowEditScreen(row))
                },
                onRowDelete = { row ->
                    viewModel.deleteRow(selectedTable!!, row)
                }
            )
        }

        is Screen.RowCreateScreen -> {
            CreateRowLayout(
                selectedTable!!.attributes,
                modifier = Modifier.fillMaxSize(1f).padding(horizontal = 20.dp),
                onRowSave = { row ->
                    viewModel.addRow(selectedTable!!, row)
                    Navigator.navigateBack()
                }
            )
        }

        is Screen.RowEditScreen -> {
            EditRowLayout(
                screen.row,
                selectedTable!!.attributes,
                modifier = Modifier.fillMaxSize(1f).padding(horizontal = 20.dp),
                onRowChangeSave = { row ->
                    viewModel.updateRow(selectedTable!!, screen.row, row)
                    Navigator.navigateBack()
                }
            )
        }

        Screen.TableCreateScreen -> {
            TableCreateLayout(
                modifier = Modifier.fillMaxSize(1f).padding(horizontal = 20.dp),
                onTableCreate = { table ->
                    viewModel.addTable(table)
                    Navigator.navigateBack()
                }
            )
        }

        Screen.MergeTablesScreen -> {
            MergeTablesLayout(
                database?.tables!!,
                onMergeCompleted = { table ->
                    viewModel.addTable(table)
                    Navigator.navigateBack()
                },
                modifier = Modifier.fillMaxSize(1f).padding(horizontal = 20.dp)
            )
        }
    }
}
