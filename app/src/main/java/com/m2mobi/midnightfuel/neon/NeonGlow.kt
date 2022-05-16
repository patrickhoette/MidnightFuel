package com.m2mobi.midnightfuel.neon

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable

@Composable
internal fun rememberNeonGlow(initialRadius: Float, targetRadius: Float) = rememberInfiniteTransition().animateFloat(
    initialValue = initialRadius,
    targetValue = targetRadius,
    animationSpec = InfiniteRepeatableSpec(
        animation = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
        repeatMode = RepeatMode.Reverse,
    ),
)
