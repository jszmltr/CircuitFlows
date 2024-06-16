package com.jsz.testcircuit.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold(
    title: String,
    onBackClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    content: @Composable (contentPadding: PaddingValues) -> Unit
) {
    androidx.compose.material3.Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title) },
                modifier = Modifier,
                navigationIcon = {
                    if (onBackClick != null) NavigationButton(onClick = onBackClick)
                },
            )
        },
    ) { content(it) }

}
