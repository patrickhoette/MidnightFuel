package com.m2mobi.midnightfuel.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

interface Screen {

    @Composable
    operator fun invoke(bottomBarPadding: Dp)
}
