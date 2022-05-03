package com.m2mobi.midnightfuel.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

internal fun Modifier.rippleClickable(onClick: () -> Unit) = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(),
        onClick = onClick,
    )
}

internal fun Modifier.shimmer(visible: Boolean, shape: Shape = RectangleShape): Modifier = placeholder(
    visible = visible,
    shape = shape,
    highlight = PlaceholderHighlight.shimmer(Color.White.copy(alpha = 0.8F)),
    color = Color.White.copy(alpha = 0.5F),
)
