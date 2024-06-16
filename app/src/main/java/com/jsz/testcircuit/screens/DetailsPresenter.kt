package com.jsz.testcircuit.screens

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter

class DetailsPresenter(
    private val navigator: Navigator,
) : Presenter<DetailsScreen.State> {
    @Composable
    override fun present() = DetailsScreen.State(
        title = "Details Screen",
        message = "This is details screen",
        eventSink = { event ->
            when (event) {
                DetailsScreen.Event.Back -> navigator.pop()
            }
        }
    )
}
