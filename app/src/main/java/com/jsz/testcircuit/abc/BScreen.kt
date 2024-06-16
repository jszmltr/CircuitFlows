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
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object BScreen : Screen {

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
fun BScreenUi(
    state: BScreen.State,
    modifier: Modifier = Modifier
) {
    Scaffold(
        title = state.title,
        onBackClick = { state.eventSink(BScreen.Event.Back) },
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
            ButtonPrimary(text = "Next", onClick = { state.eventSink(BScreen.Event.Next) })
            Spacer(Modifier.height(16.dp))
        }
    }
}

class BPresenter(
    private val navigator: Navigator,
) : Presenter<BScreen.State> {
    @Composable
    override fun present() = BScreen.State(
        title = "B",
        message = "This is B Screen. Halfway through.",
        eventSink = { event ->
            when (event) {
                BScreen.Event.Back -> navigator.pop()
                BScreen.Event.Next -> navigator.goTo(CScreen)
            }
        }
    )
}
