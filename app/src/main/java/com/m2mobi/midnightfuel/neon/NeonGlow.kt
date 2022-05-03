package com.m2mobi.midnightfuel.neon

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

@Composable
internal fun rememberNeonGlow(initialRadius: Dp, targetRadius: Dp) = rememberInfiniteTransition().animateFloat(
    initialValue = initialRadius.value,
    targetValue = targetRadius.value,
    animationSpec = InfiniteRepeatableSpec(
        animation = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
        repeatMode = RepeatMode.Reverse,
    ),
)
