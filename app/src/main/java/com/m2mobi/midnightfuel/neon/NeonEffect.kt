package com.m2mobi.midnightfuel.neon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.m2mobi.midnightfuel.theme.*

internal val DefaultShadowRadius = 10.dp
internal fun Modifier.neon(
    cornerRadius: () -> Dp = { 0.dp },
    shadowRadius: () -> Dp = { DefaultShadowRadius },
): Modifier = drawBehind { drawNeon(cornerRadius, shadowRadius) }

internal fun DrawScope.drawNeon(
    radius: () -> Dp = { 0.dp },
    shadowRadius: () -> Dp = { DefaultShadowRadius },
) = drawIntoCanvas {
    it.drawRoundRect(
        0F,
        0F,
        size.width,
        size.height,
        radius().toPx(),
        radius().toPx(),
        createNeonPaint(this, shadowRadius()),
    )
}

private fun createNeonPaint(density: Density, shadowRadius: Dp = DefaultShadowRadius): Paint = Paint().also {
    val radiusPx = density.run { shadowRadius.toPx() }
    it.asFrameworkPaint().setupNeonPaint(radiusPx).apply { color = Color.Transparent.toArgb() }
}

@Preview
@Composable
fun NeonSquarePreview() {
    NeonSquare(RadiusNone)
}

@Preview
@Composable
fun NeonSquareRoundedSmallPreview() {
    NeonSquare(RadiusSmall)
}

@Preview
@Composable
fun NeonSquareRoundedMediumPreview() {
    NeonSquare(RadiusMedium)
}

@Preview
@Composable
fun NeonSquareRoundedLargePreview() {
    NeonSquare(RadiusLarge)
}

@Composable
fun NeonSquare(cornerRadius: Dp) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .background(color = PrimaryColor),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .size(100.dp)
                .neon(cornerRadius = { cornerRadius }),
            color = AccentColor,
            shape = RoundedCornerShape(cornerRadius),
        ) {}
    }
}

@Preview
@Composable
fun NeonLinePreview() {
    NeonLine(RadiusNone)
}

@Preview
@Composable
fun NeonLineRoundedSmallPreview() {
    NeonLine(RadiusSmall)
}

@Preview
@Composable
fun NeonLineRoundedMediumPreview() {
    NeonLine(RadiusMedium)
}

@Composable
fun NeonLine(cornerRadius: Dp) {
    Box(
        modifier = Modifier
            .size(150.dp, 70.dp)
            .background(color = PrimaryColor),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .size(width = 100.dp, height = 20.dp)
                .neon(cornerRadius = { cornerRadius }),
            color = AccentColor,
            shape = RoundedCornerShape(cornerRadius),
        ) {}
    }
}
