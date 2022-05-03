package com.m2mobi.midnightfuel.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest

@Composable
internal fun imageRequest(url: String) = ImageRequest.Builder(LocalContext.current)
    .crossfade(true)
    .data(url)
    .build()
