package com.jsz.testcircuit.common

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource

@Composable
fun NavigationButton(
    modifier: Modifier = Modifier,
    direction: Direction = Direction.LEFT,
    enabled: Boolean = true,
    visible: Boolean = true,
    onClick: () -> Unit,
) {
    if (!visible) return

    IconButton(modifier = modifier, enabled = enabled, onClick = onClick) {
        Icon(
            painter = rememberVectorPainter(image = direction.icon),
            contentDescription = stringResource(direction.descriptionResId),
        )
    }
}
