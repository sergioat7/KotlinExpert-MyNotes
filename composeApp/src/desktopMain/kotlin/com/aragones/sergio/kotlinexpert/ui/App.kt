package com.aragones.sergio.kotlinexpert.ui

import androidx.compose.runtime.*
import com.aragones.sergio.kotlinexpert.ui.screens.detail.DetailScreen
import com.aragones.sergio.kotlinexpert.ui.screens.detail.DetailViewModel
import com.aragones.sergio.kotlinexpert.ui.screens.home.HomeScreen
import com.aragones.sergio.kotlinexpert.ui.screens.home.HomeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed interface Route {
    data object Home : Route
    data class Detail(val id: Long) : Route
}

@Composable
@Preview
fun App() {

    var route: Route by remember { mutableStateOf(Route.Home) }
    val scope = rememberCoroutineScope()

    route.let {
        when (it) {
            Route.Home -> HomeScreen(
                viewModel = HomeViewModel(scope),
                onNoteClick = { noteId -> route = Route.Detail(noteId) }
            )

            is Route.Detail -> DetailScreen(
                viewModel = DetailViewModel(scope, it.id),
                onClose = { route = Route.Home }
            )
        }
    }
}

