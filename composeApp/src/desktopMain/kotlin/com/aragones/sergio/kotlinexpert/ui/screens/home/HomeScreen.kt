package com.aragones.sergio.kotlinexpert.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aragones.sergio.kotlinexpert.data.Note
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun HomeScreen() = with(HomeState) {

    val state = state.collectAsState().value

    LaunchedEffect(true) {
        loadNotes()
    }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopBar(::onFilterClicked)
            }
        ) { padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {

                if (state.loading) {
                    CircularProgressIndicator()
                }

                state.filteredNotes?.let {
                    NoteList(it)
                }
            }
        }
    }
}

@Composable
fun TopBar(onFilterClicked: (Filter) -> Unit) {

    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("My notes") },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter"
                )
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {

                    val items = listOf(
                        Filter.All to "All",
                        Filter.ByType(Note.Type.TEXT) to "Text",
                        Filter.ByType(Note.Type.AUDIO) to "Audio"
                    )
                    for ((filter, text) in items) {

                        DropdownMenuItem(onClick = {
                            expanded = false
                            onFilterClicked(filter)
                        }) {
                            Text(text)
                        }
                    }
                }
            }
        }
    )
}