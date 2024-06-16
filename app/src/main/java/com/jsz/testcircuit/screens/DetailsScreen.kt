package com.jsz.testcircuit.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jsz.testcircuit.common.Scaffold
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object DetailsScreen : Screen {
    data class State(
        val title: String,
        val message: String,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data object Back : Event
    }
}

@Composable
fun DetailsScreenUi(
    state: DetailsScreen.State,
    modifier: Modifier = Modifier
) {
    Scaffold(
        title = state.title,
        onBackClick = { state.eventSink(DetailsScreen.Event.Back) },
        modifier = modifier,
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Text(text = state.message)
        }
    }
}
