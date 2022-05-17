package com.m2mobi.midnightfuel.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsetsSides.Companion.Bottom
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.m2mobi.midnightfuel.main.model.BottomBarInsetState
import com.m2mobi.midnightfuel.main.model.rememberBottomBarInsetState
import com.m2mobi.midnightfuel.neon.appendNeonText
import com.m2mobi.midnightfuel.neon.neon
import com.m2mobi.midnightfuel.theme.*
import com.m2mobi.midnightfuel.train.TrainScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent { CreateContent(TrainScreen()) }
    }

    @Composable
    fun CreateContent(screen: Screen) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = false,
                isNavigationBarContrastEnforced = false,
            )
        }

        MidnightFuelTheme {
            val bottomBarInsetState = rememberBottomBarInsetState()
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = { BottomBar(bottomBarInsetState) },
                floatingActionButton = { Fab(bottomBarInsetState) },
                floatingActionButtonPosition = FabPosition.Center,
                isFloatingActionButtonDocked = true,
                backgroundColor = PrimaryColor,
            ) {
                screen { bottomBarInsetState.bottomInset }
            }
        }
    }

    @Composable
    private fun BottomBar(
        bottomBarInsetState: BottomBarInsetState,
    ) {
        val density = LocalDensity.current
        val bottomInset = WindowInsets.navigationBars.only(Bottom).asPaddingValues().calculateBottomPadding()
        val bottomPadding = 16.dp + bottomInset
        Surface(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = bottomPadding)
                .onGloballyPositioned {
                    bottomBarInsetState.bottomBarHeight = density.run { it.size.height.toDp() } + bottomPadding
                },
            color = HighSurfaceColor,
            elevation = 0.dp,
            shape = Shapes.medium,
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                var selectedItem by remember { mutableStateOf(2) }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BottomBarIcon(selected = selectedItem == 1, icon = Icons.Outlined.Home, tabName = "Home") {
                        selectedItem = 1
                    }
                    BottomBarIcon(selected = selectedItem == 2, icon = Icons.Outlined.PlayArrow, tabName = "Train") {
                        selectedItem = 2
                    }
                    BottomNavigationItem(selected = false, enabled = false, onClick = { }, icon = {})
                    BottomBarIcon(selected = selectedItem == 3, icon = Icons.Outlined.Star, tabName = "Goals") {
                        selectedItem = 3
                    }
                    BottomBarIcon(selected = selectedItem == 4, icon = Icons.Outlined.Person, tabName = "Profile") {
                        selectedItem = 4
                    }
                }
            }
        }
    }

    @Composable
    private fun RowScope.BottomBarIcon(selected: Boolean, icon: ImageVector, tabName: String, onClick: () -> Unit) {
        BottomNavigationItem(
            selected = selected,
            enabled = true,
            icon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "$tabName tab",
                )
            },
            label = {
                Text(
                    text = buildAnnotatedString { if (selected) appendNeonText(tabName, 10F) else append(tabName) },
                    style = TextStyle(fontSize = 12.sp),
                )
            },
            selectedContentColor = AccentColor,
            unselectedContentColor = Color.White,
            alwaysShowLabel = true,
            onClick = onClick,
        )
    }

    @Composable
    private fun Fab(bottomBarInsetState: BottomBarInsetState) {
        val density = LocalDensity.current
        CustomFloatingActionButton(
            modifier = Modifier.onGloballyPositioned {
                bottomBarInsetState.fabHeight = density.run { it.size.height.toDp() }
            },
            shape = Shapes.medium,
            onClick = { /* do all the things */ },
        ) {
            Icon(imageVector = Icons.Filled.Home, contentDescription = "Fab icon", tint = Color.White)
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun CustomFloatingActionButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
        backgroundColor: Color = MaterialTheme.colors.secondary,
        contentColor: Color = contentColorFor(backgroundColor),
        content: @Composable () -> Unit
    ) {
        var shadowRadius by remember { mutableStateOf(10.dp) }
        val ripple = rememberRipple()
        Surface(
            modifier = modifier.neon(cornerRadius = { RadiusSmall }, shadowRadius = { shadowRadius }),
            shape = shape,
            color = AccentColor,
            contentColor = contentColor,
            elevation = 0.dp,
            onClick = onClick,
            role = Role.Button,
            interactionSource = interactionSource,
            indication = remember {
                NeonIndication(10.dp, 15.dp, ripple) { shadowRadius = it }
            },
        ) {
            CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
                ProvideTextStyle(MaterialTheme.typography.button) {
                    Box(
                        modifier = Modifier.defaultMinSize(minWidth = 56.dp, minHeight = 56.dp),
                        contentAlignment = Alignment.Center
                    ) { content() }
                }
            }
        }

    }

    private class NeonIndication(
        private val unpressedRadius: Dp,
        private val pressedRadius: Dp,
        private val ripple: Indication,
        private val onRadius: suspend (Dp) -> Unit,
    ) : Indication {

        @Composable
        override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
            val instance = ripple.rememberUpdatedInstance(interactionSource)
            LaunchedEffect(instance, interactionSource) {
                interactionSource.interactions.collect {
                    when (it) {
                        is PressInteraction.Press -> {
                            onRadius(pressedRadius)
                        }
                        else -> {
                            onRadius(unpressedRadius)
                        }
                    }
                }
            }
            return instance
        }
    }
}

