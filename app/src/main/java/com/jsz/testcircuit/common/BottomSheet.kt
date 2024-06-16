package com.jsz.testcircuit.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.slack.circuit.foundation.internal.BackHandler
import com.slack.circuit.overlay.Overlay
import com.slack.circuit.overlay.OverlayNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class BottomSheetOverlay<Model : Any, Result : Any>(
    private val model: Model,
    private val onDismiss: () -> Result,
    private val tonalElevation: Dp = BottomSheetDefaults.Elevation,
    private val scrimColor: Color = Color.Unspecified,
    private val skipPartiallyExpanded: Boolean = false,
    private val dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    private val content: @Composable (Model, OverlayNavigator<Result>) -> Unit,
) : Overlay<Result> {
    @Composable
    override fun Content(navigator: OverlayNavigator<Result>) {
        val sheetState =
            rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

        val coroutineScope = rememberCoroutineScope()
        BackHandler(enabled = sheetState.isVisible) {
            coroutineScope
                .launch { sheetState.hide() }
                .invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        navigator.finish(onDismiss())
                    }
                }
        }

        val configuration = LocalConfiguration.current
        val bottomSheetMaxHeight = remember(configuration.screenHeightDp) {
            configuration.screenHeightDp.dp - 108.dp
        }

        ModalBottomSheet(
            content = {
                // Delay setting the result until we've finished dismissing
                content(model) { result ->
                    // This is the OverlayNavigator.finish() callback
                    coroutineScope.launch {
                        try {
                            sheetState.hide()
                        } finally {
                            navigator.finish(result)
                        }
                    }
                }
            },
            dragHandle = dragHandle,
            tonalElevation = tonalElevation,
            scrimColor = if (scrimColor.isSpecified) scrimColor else BottomSheetDefaults.ScrimColor,
            sheetState = sheetState,
            onDismissRequest = { navigator.finish(onDismiss()) },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = bottomSheetMaxHeight)
                .navigationBarsPadding(),
        )

        LaunchedEffect(Unit) { sheetState.show() }
    }
}

//fun<Model : Any, Result : Any> bottomSheet (
//    model: Model,
//    modifier: Modifier = Modifier,
//    content: @Composable (Model, OverlayNavigator<Result>) -> Unit,
//) : BottomSheetOverlay<Model, Result>{
//    return BottomSheetOverlay(
//        model = model,
//        sheetContainerColor = Color.Yellow,
//        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
//        dragHandle = {},
//        skipPartiallyExpandedState = true,
//    ) { model, navigator ->
//        Column(
//            modifier = modifier
//                .fillMaxWidth()

//            content = { content(model, navigator) },
//        )
//    }
//}

//@Composable
//private fun BottomSheetTopHandle(
//    modifier: Modifier = Modifier,
//) {
//    Row(
//        horizontalArrangement = Arrangement.Center,
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(top = 8.dp, bottom = 12.dp)
//    ) {
//        Surface(
//            shape = RoundedCornerShape(100.dp),
//            color = Color.Black.copy(alpha = .2f),
//            modifier = Modifier
//                .width(32.dp)
//                .height(6.dp),
//            content = {}
//        )
//    }
//}
