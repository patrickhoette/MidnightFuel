package com.m2mobi.midnightfuel.theme

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val Palette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColorVariant,
    onPrimary = ContentColor,
    secondary = AccentColor,
    secondaryVariant = AccentColorVariant,
    onSecondary = ContentColor,
    background = PrimaryColor,
    onBackground = ContentColor,
    surface = SurfaceColor,
    onSurface = ContentColor,
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
