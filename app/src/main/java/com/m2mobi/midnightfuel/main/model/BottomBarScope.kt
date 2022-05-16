package com.m2mobi.midnightfuel.main.model

import androidx.compose.ui.unit.Dp

class BottomBarScope(private val _bottomBarInset: () -> Dp) {

    val bottomBarInset
        get() = _bottomBarInset()
}
