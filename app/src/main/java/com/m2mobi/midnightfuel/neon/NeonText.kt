package com.m2mobi.midnightfuel.neon

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.m2mobi.midnightfuel.theme.AccentColor

@Composable
internal fun NeonText(
    text: String,
    textSize: TextUnit,
    modifier: Modifier = Modifier,
    shadowRadius: Dp = DefaultShadowRadius,
    onBaseline: ((Float) -> Unit)? = null,
) {
    val paint = rememberPaint(shadowRadius = shadowRadius, textSize = textSize)
    val intrinsicSize = rememberIntrinsicSize(text = text, paint = paint)
    Canvas(
        modifier = Modifier
            .size(intrinsicSize)
            .then(modifier),
    ) {
        drawIntoCanvas {
            val baseline = size.height - paint.fontMetrics.descent
            onBaseline?.invoke(baseline)
            it.nativeCanvas.drawText(
                text,
                0F,
                baseline,
                paint,
            )
        }
    }
}

@Composable
private fun rememberPaint(shadowRadius: Dp, textSize: TextUnit): NativePaint {
    val density = LocalDensity.current
    return createNeonPaint(density = density, shadowRadius = shadowRadius).asFrameworkPaint().apply {
        isAntiAlias = true
        isSubpixelText = true
        this.textSize = density.run { textSize.toPx() }
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
private fun rememberIntrinsicSize(text: String, paint: NativePaint): DpSize {
    val density = LocalDensity.current
    val textWidth = paint.measureText(text)
    val textHeight =  paint.fontMetrics.run { bottom - top + leading }
    return density.run { Size(textWidth, textHeight).toDpSize() }
}

@Composable
@Preview(backgroundColor = 0xFF232428, showBackground = true)
fun NeonTextPreview() {
    NeonText("Neon Text is Awesome", 30.sp)
}
