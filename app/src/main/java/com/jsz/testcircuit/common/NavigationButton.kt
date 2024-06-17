package com.jsz.testcircuit.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import com.jsz.testcircuit.R

@Composable
fun NavigationButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    visible: Boolean = true,
    onClick: () -> Unit,
) {
    if (!visible) return
    IconButton(modifier = modifier, enabled = enabled, onClick = onClick) {
        Icon(
            painter = rememberVectorPainter(image = Icons.AutoMirrored.Filled.ArrowBack),
            contentDescription = stringResource(R.string.top_bar_back),
        )
    }
}
