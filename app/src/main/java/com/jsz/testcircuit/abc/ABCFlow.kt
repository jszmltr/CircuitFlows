package com.jsz.testcircuit.abc

import android.util.Log
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.Color
import com.jsz.testcircuit.common.BottomSheetOverlay
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.overlay.OverlayHost
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.PopResult
import kotlinx.parcelize.Parcelize

@OptIn(ExperimentalMaterial3Api::class)
suspend fun OverlayHost.startABCFlow(
    parentNavigator: Navigator? = null,
): Unit = show(
    BottomSheetOverlay(
        model = Unit,
        tonalElevation = BottomSheetDefaults.Elevation,
        scrimColor = Color.Unspecified,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        skipPartiallyExpanded = true,
        onDismiss = { },
    ) { _, overlayNavigator ->
        val backStack = rememberSaveableBackStack(AScreen)
        val navigator = rememberCircuitNavigator(
            backStack,
            onRootPop = {
                Log.e("!!!", "Yessh ${it}")

            }
        )
        NavigableCircuitContent(navigator, backStack)
    },
)

sealed interface ABCResult : PopResult {
    @Parcelize
    data object Success : ABCResult

    @Parcelize
    data object Cancel : ABCResult
}
