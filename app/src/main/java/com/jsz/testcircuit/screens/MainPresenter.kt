package com.jsz.testcircuit.screens

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter

class MainPresenter(
    private val navigator: Navigator,
) : Presenter<MainScreen.State> {
    @Composable
    override fun present() = MainScreen.State(
        title = "Main Screen",
        message = "Welcome in the app ! Tap to see details",
        eventSink = { event ->
            when (event) {
                MainScreen.Event.Details -> navigator.goTo(DetailsScreen)
            }
        }
    )
}
