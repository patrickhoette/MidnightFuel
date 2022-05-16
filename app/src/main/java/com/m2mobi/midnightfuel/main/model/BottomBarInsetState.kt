package com.m2mobi.midnightfuel.main.model

import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Stable
class BottomBarInsetState {

    var bottomBarHeight by mutableStateOf(0.dp)

    var fabHeight by mutableStateOf(0.dp)

    val bottomInset by derivedStateOf { bottomBarHeight + fabHeight / 2 }
}

@Composable
fun rememberBottomBarInsetState(): BottomBarInsetState = remember { BottomBarInsetState() }
