package com.jsz.testcircuit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jsz.testcircuit.abc.APresenter
import com.jsz.testcircuit.abc.AScreen
import com.jsz.testcircuit.abc.AScreenUi
import com.jsz.testcircuit.abc.BPresenter
import com.jsz.testcircuit.abc.BScreen
import com.jsz.testcircuit.abc.BScreenUi
import com.jsz.testcircuit.abc.CPresenter
import com.jsz.testcircuit.abc.CScreen
import com.jsz.testcircuit.abc.CScreenUi
import com.jsz.testcircuit.screens.DetailsPresenter
import com.jsz.testcircuit.screens.DetailsScreen
import com.jsz.testcircuit.screens.DetailsScreenUi
import com.jsz.testcircuit.screens.MainPresenter
import com.jsz.testcircuit.screens.MainScreen
import com.jsz.testcircuit.screens.MainScreenUi
import com.jsz.testcircuit.theme.CircuitTheme
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.runtime.ui.ui

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val circuit: Circuit = Circuit.Builder()
            .addPresenterFactory { screen, navigator, context ->
                when (screen) {
                    is MainScreen -> MainPresenter(navigator)
                    is DetailsScreen -> DetailsPresenter(navigator)
                    is AScreen -> APresenter(navigator)
                    is BScreen -> BPresenter(navigator)
                    is CScreen -> CPresenter(navigator)
                    else -> null
                }
            }
            .addUiFactory { screen, context ->
                when (screen) {
                    is MainScreen -> ui<MainScreen.State> { state, modifier ->
                        MainScreenUi(state, modifier)
                    }

                    is DetailsScreen -> ui<DetailsScreen.State> { state, modifier ->
                        DetailsScreenUi(state, modifier)
                    }

                    is AScreen -> ui<AScreen.State> { state, modifier ->
                        AScreenUi(state, modifier)
                    }

                    is BScreen -> ui<BScreen.State> { state, modifier ->
                        BScreenUi(state, modifier)
                    }

                    is CScreen -> ui<CScreen.State> { state, modifier ->
                        CScreenUi(state, modifier)
                    }


                    else -> null
                }
            }
            .build()

        setContent {
            val backStack = rememberSaveableBackStack(MainScreen)
            val navigator = rememberCircuitNavigator(backStack)
            CircuitTheme {
                CircuitCompositionLocals(circuit) {
                    ContentWithOverlays {
                        NavigableCircuitContent(navigator, backStack)
                    }
                }
            }
        }
    }
}
