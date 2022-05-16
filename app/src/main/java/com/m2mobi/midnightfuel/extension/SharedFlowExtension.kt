package com.m2mobi.midnightfuel.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> SharedFlow<T>.collectAsEffectWhenStarted(onEmit: suspend (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(onEmit, lifecycleOwner) {
        collectLatest {
            lifecycleOwner.whenStarted { onEmit(it) }
        }
    }
}

@Composable
fun <T> SharedFlow<T>.collectAsEffect(onEmit: suspend (T) -> Unit) {
    LaunchedEffect(onEmit) { collectLatest(onEmit::invoke) }
}
