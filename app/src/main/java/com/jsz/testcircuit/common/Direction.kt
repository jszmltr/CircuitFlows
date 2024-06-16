package com.jsz.testcircuit.common

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.ui.graphics.vector.ImageVector
import com.jsz.testcircuit.R

enum class Direction(val icon: ImageVector, @StringRes val descriptionResId: Int) {
    LEFT(Icons.AutoMirrored.Filled.ArrowBack, R.string.top_bar_back),
    RIGHT(Icons.AutoMirrored.Filled.ArrowForward, R.string.top_bar_forward),
}
