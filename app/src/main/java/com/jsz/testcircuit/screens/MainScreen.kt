package com.jsz.testcircuit.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsz.testcircuit.abc.startABCFlow
import com.jsz.testcircuit.common.ButtonPrimary
import com.jsz.testcircuit.common.Scaffold
import com.slack.circuit.overlay.LocalOverlayHost
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data object MainScreen : Screen {
    data class State(
        val title: String,
        val message: String,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data object Details : Event
    }
}

@Composable
fun MainScreenUi(
    state: MainScreen.State,
    modifier: Modifier = Modifier
) {
    val overlayHost = LocalOverlayHost.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        title = state.title,
        onBackClick = null,
        modifier = modifier,
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Spacer(Modifier.weight(0.5f))
            Text(text = state.message)

            Spacer(Modifier.weight(0.5f))
            ButtonPrimary(text = "Details", onClick = { state.eventSink(MainScreen.Event.Details) })
            Spacer(Modifier.height(8.dp))
            ButtonPrimary(text = "Flow", onClick = {
                coroutineScope.launch {
                    overlayHost.startABCFlow()
                }
            })
            Spacer(Modifier.height(16.dp))
        }
    }
}
