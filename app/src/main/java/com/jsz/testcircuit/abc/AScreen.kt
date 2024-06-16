package com.jsz.testcircuit.abc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsz.testcircuit.common.ButtonPrimary
import com.jsz.testcircuit.common.Scaffold
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.popRoot
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object AScreen : Screen {

    data class State(
        val title: String,
        val message: String,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data object Back : Event
        data object Next : Event
    }
}

@Composable
fun AScreenUi(
    state: AScreen.State,
    modifier: Modifier = Modifier
) {
    Scaffold(
        title = state.title,
        onBackClick = { state.eventSink(AScreen.Event.Back) },
        modifier = modifier,
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)

        ) {
            Spacer(Modifier.weight(0.5f))
            Text(text = state.message)
            Spacer(Modifier.weight(0.5f))
            ButtonPrimary(text = "Next", onClick = { state.eventSink(AScreen.Event.Next) })
            Spacer(Modifier.height(16.dp))
        }
    }
}

class APresenter(
    private val navigator: Navigator,
) : Presenter<AScreen.State> {
    @Composable
    override fun present() = AScreen.State(
        title = "A",
        message = "Hello World! First Screen",
        eventSink = { event ->
            when (event) {
                AScreen.Event.Back -> navigator.popRoot(ABCResult.Cancel)
                AScreen.Event.Next -> navigator.goTo(BScreen)
            }
        }
    )
}
