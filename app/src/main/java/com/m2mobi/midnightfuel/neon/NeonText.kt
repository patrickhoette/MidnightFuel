package com.m2mobi.midnightfuel.neon

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.m2mobi.midnightfuel.theme.AccentColor
import androidx.compose.ui.graphics.Paint as ComposePaint

@Composable
fun NeonText(
    text: String,
    textSize: TextUnit,
    modifier: Modifier = Modifier,
    shadowRadius: Dp = DefaultShadowRadius,
) {
    val density = LocalDensity.current
    NeonText(
        state = rememberNeonTextState(text = text, textSize = textSize) {
            density.run { shadowRadius.toPx() }
        },
        modifier = modifier,
    )
}

@Composable
fun NeonText(
    state: NeonTextState,
    modifier: Modifier = Modifier,
) {
    val measurePolicy by derivedStateOf {
        MeasurePolicy { _, _ -> layout(state.size.width, state.size.height) {} }
    }
    Layout(
        content = {},
        modifier = Modifier.drawWithCache {
            onDrawBehind {
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        state.text,
                        0F,
                        state.baseline,
                        state.paint,
                    )
                }
            }
        }.then(modifier),
        measurePolicy = measurePolicy,
    )
}

@Stable
class NeonTextState(
    initialText: String,
    initialTextSize: Float,
    private val shadowRadius: () -> Float,
) {

    var text by mutableStateOf(initialText)

    var textSize by mutableStateOf(initialTextSize)

    private val basePaint = ComposePaint().asFrameworkPaint().setupPaint()
    private val paintWithSize by derivedStateOf {
        NativePaint(basePaint).apply { textSize = this@NeonTextState.textSize }
    }
    val paint by derivedStateOf {
        NativePaint(paintWithSize).setupNeonPaint(shadowRadius())
    }

    val size by derivedStateOf {
        val textWidth = paintWithSize.measureText(text)
        val textHeight = paintWithSize.fontMetrics.run { bottom - top + leading }
        IntSize(textWidth.toInt(), textHeight.toInt())
    }

    val baseline by derivedStateOf { size.height - paintWithSize.fontMetrics.descent }

    private fun NativePaint.setupPaint(): NativePaint = apply {
        isAntiAlias = true
        isSubpixelText = true
        strokeWidth = 6F
        strokeCap = Paint.Cap.BUTT
        strokeJoin = Paint.Join.BEVEL
        style = Paint.Style.STROKE
        color = AccentColor.toArgb()
        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        textAlign = Paint.Align.LEFT
    }
}

@Composable
fun rememberNeonTextState(
    text: String,
    textSize: TextUnit,
    shadowRadius: () -> Float,
): NeonTextState {
    val density = LocalDensity.current
    return remember(text, textSize) {
        NeonTextState(
            initialText = text,
            initialTextSize = density.run { textSize.toPx() },
            shadowRadius = shadowRadius,
        )
    }
}

@Composable
@Preview(backgroundColor = 0xFF232428, showBackground = true)
fun NeonTextPreview() {
    NeonText("Neon Text is Awesome", 30.sp)
}
