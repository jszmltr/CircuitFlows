package com.jsz.testcircuit.abc

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

/**
 * Why we are doing this ? Because I think nested presenters are not a solution here.
 * Nested presenters are good when you want to have a presenter that builds some part of the state
 * (sub presenter that produces sub state)
 *
 * Further more we need flows to be happening _inside_ of the bottom sheet.
 * If we were to navigate from one bottom sheet to the other bottom sheet they would show either on on top of the other OR
 * you would be able to see entire sheet going left to right which is not what we want.
 *
 *
 * There is no way to pass things from here to screen. The only way to pass things is to
 * use the Screen constructor when navigating so we either need to pass things between screens
 * even when we don't need them or pop with result and go to next which I think it doesn't make sense
 * TODO : Implement this , find the good design pattern / naming for this
 *
 * Next things to check and try: opening a bottom sheet with a result mid flow
 * opening flow within a flow and passing result back
 * TODO
 *
 * Logging / Analytics
 * TODO
 *
 * Starting a flow happens in the UI.
 * We could in theory call this from the presenter , but then we'd need overlay host which is kind of a UI concern.
 * What is lesser evil ? Navigating from UI or having a Ui'ish bit in the presenter
 */
@OptIn(ExperimentalMaterial3Api::class)
suspend fun OverlayHost.startABCFlow(
    parentNavigator: Navigator? = null,
): ABCResult = show(
    BottomSheetOverlay<Unit, ABCResult>(
        model = Unit,
        tonalElevation = BottomSheetDefaults.Elevation,
        scrimColor = Color.Unspecified,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        skipPartiallyExpanded = true,
        onDismiss = { ABCResult.Cancel },
    ) { _, overlayNavigator ->
        val backStack = rememberSaveableBackStack(AScreen)
        val navigator = rememberCircuitNavigator(
            backStack,
            onRootPop = {
                overlayNavigator.finish(it as? ABCResult ?: ABCResult.Cancel)
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
