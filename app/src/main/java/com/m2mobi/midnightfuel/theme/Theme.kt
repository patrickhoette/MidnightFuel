package com.m2mobi.midnightfuel.theme

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val Palette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColor,
    secondary = AccentColor,
    background = PrimaryColor,
    surface = SurfaceColor,
    onBackground = TextPrimaryColor,
)

@Composable
fun MidnightFuelTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = Palette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
