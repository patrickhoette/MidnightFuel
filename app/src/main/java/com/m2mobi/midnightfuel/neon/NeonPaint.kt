package com.m2mobi.midnightfuel.neon

import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.toArgb
import com.m2mobi.midnightfuel.theme.AccentColor

fun NativePaint.setupNeonPaint(shadowRadius: Float, shadowColor: Int = AccentColor.toArgb()): NativePaint = apply {
    setShadowLayer(shadowRadius, 0F, 0F, shadowColor)
}
