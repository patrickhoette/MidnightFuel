package com.m2mobi.midnightfuel.neon

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp

class NeonIndication(
    private val unpressedShadowRadius: Dp,
    private val pressedShadowRadius: Dp,
    private val shadowColor: Color,
    private val cornerSize: CornerSize,
) : Indication {

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val isPressed by interactionSource.collectIsPressedAsState()
        val instance = remember(interactionSource, isPressed) {
            NeonIndicationInstance(
                shadowRadius = if (isPressed) pressedShadowRadius else unpressedShadowRadius,
                shadowColor = shadowColor,
                cornerSize = cornerSize,
            )
        }
        return instance
    }
}

class NeonIndicationInstance(
    private val shadowRadius: Dp,
    private val shadowColor: Color,
    private val cornerSize: CornerSize,
) : IndicationInstance {

    private val paint = Paint()

    override fun ContentDrawScope.drawIndication() {
        drawIntoCanvas {
            paint.asFrameworkPaint().setupNeonPaint(shadowRadius.toPx(), shadowColor.toArgb())
            it.drawRoundRect(
                0F,
                0F,
                size.width,
                size.height,
                cornerSize.toPx(size, this),
                cornerSize.toPx(size, this),
                paint,
            )
        }
    }
}
