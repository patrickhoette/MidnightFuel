package com.m2mobi.midnightfuel.neon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.m2mobi.midnightfuel.theme.AccentColor

private const val DEFAULT_FONT_SHADOW_RADIUS = 20F

internal fun AnnotatedString.Builder.addNeonStyle(
    start: Int,
    end: Int,
    shadowRadius: Float = DEFAULT_FONT_SHADOW_RADIUS
) {
    addStyle(createNeonStyleSpan(shadowRadius), start = start, end = end)
}

internal fun <R : Any> AnnotatedString.Builder.withNeonStyle(
    shadowRadius: Float = DEFAULT_FONT_SHADOW_RADIUS,
    block: AnnotatedString.Builder.() -> R
): R {
    return withStyle(createNeonStyleSpan(shadowRadius), block)
}

internal fun AnnotatedString.Builder.appendNeonText(text: String, shadowRadius: Float = DEFAULT_FONT_SHADOW_RADIUS) {
    withNeonStyle(shadowRadius) { append(text) }
}

internal fun createNeonStyleSpan(shadowRadius: Float = DEFAULT_FONT_SHADOW_RADIUS) =
    SpanStyle(shadow = neonShadow(shadowRadius))

private fun neonShadow(shadowRadius: Float = DEFAULT_FONT_SHADOW_RADIUS): Shadow {
    return Shadow(color = AccentColor, blurRadius = shadowRadius)
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF232428)
fun TextPreview() {
    Column(modifier = Modifier.padding(5.dp)) {
        Text(
            text = buildAnnotatedString {
                withNeonStyle { append("Testing Text") }
            },
            color = Color.White,
        )
    }
}
