@file:OptIn(ExperimentalMaterialApi::class)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.m2mobi.midnightfuel.train

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.BackdropValue.Revealed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.m2mobi.midnightfuel.R
import com.m2mobi.midnightfuel.extension.retrieveAsEffect
import com.m2mobi.midnightfuel.main.Screen
import com.m2mobi.midnightfuel.neon.NeonText
import com.m2mobi.midnightfuel.neon.NeonTextState
import com.m2mobi.midnightfuel.neon.rememberNeonGlow
import com.m2mobi.midnightfuel.neon.rememberNeonTextState
import com.m2mobi.midnightfuel.theme.*
import com.m2mobi.midnightfuel.train.model.TrainEvents.ShowSnackBar
import com.m2mobi.midnightfuel.train.model.TrainingUIModel

class TrainScreen : Screen {

    @Composable
    override operator fun invoke(bottomBarInset: () -> Dp) {
        val viewModel: TrainViewModel = viewModel()
        val year by viewModel.year.collectAsState()
        val trainings by viewModel.trainings.collectAsState()

        val snackbarHostState = remember { SnackbarHostState() }

        viewModel.events.retrieveAsEffect {
            when (it) {
                is ShowSnackBar -> snackbarHostState.showSnackbar(it.message)
            }
        }

        Screen(
            snackbarHostState = snackbarHostState,
            bottomBarInset = bottomBarInset,
            year = year,
            trainings = trainings,
            onTraining = viewModel::onTraining,
            onFavorite = viewModel::onFavorite,
        )
    }
}

@Composable
private fun Screen(
    snackbarHostState: SnackbarHostState,
    bottomBarInset: () -> Dp,
    year: String,
    trainings: List<TrainingUIModel>,
    onTraining: (TrainingUIModel) -> Unit,
    onFavorite: (TrainingUIModel) -> Unit,
) {
    val density = LocalDensity.current
    val scaffoldState = rememberBackdropScaffoldState(initialValue = Revealed, snackbarHostState = snackbarHostState)
    var topBarHeight by remember { mutableStateOf(0.dp) }
    val pulseAnimation = rememberNeonGlow(
        initialRadius = density.run { 2.dp.toPx() },
        targetRadius = density.run { 15.dp.toPx() },
    )
    val neonTextState = rememberNeonTextState(text = "Physical", textSize = 46.sp) { pulseAnimation.value }

    BackdropScaffold(
        appBar = {
            TopBar(year) {
                density.run { topBarHeight = it.toDp() }
            }
        },
        backLayerContent = { Header(year, neonTextState) },
        frontLayerContent = { MainContent(trainings, bottomBarInset, onFavorite, onTraining) },
        frontLayerShape = Shapes.large.copy(bottomStart = CornerSize(0), bottomEnd = CornerSize(0)),
        scaffoldState = scaffoldState,
        frontLayerScrimColor = Color.Unspecified,
        persistentAppBar = false,
        peekHeight = topBarHeight,
    ) { hostState ->
        SnackbarHost(hostState = hostState) {
            Snackbar(
                modifier = Modifier.padding(bottom = bottomBarInset() + 16.dp),
                snackbarData = it,
                backgroundColor = HighSurfaceColor,
                contentColor = ContentColor,
            )
        }
    }
}

@Composable
private fun TopBar(year: String, onHeight: (Int) -> Unit) {
    val topInset = LocalDensity.current.run { WindowInsets.statusBars.only(WindowInsetsSides.Top).getTop(this).toDp() }
    TopAppBar(
        modifier = Modifier.onGloballyPositioned { onHeight(it.size.height) },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = topInset)
    ) {
        Text(
            text = stringResource(R.string.marathon, year),
            style = Typography.h6
        )
    }
}

@Composable
private fun Header(
    year: String,
    state: NeonTextState,
) {
    val density = LocalDensity.current
    var letsGetBaseline by remember { mutableStateOf(0F) }
    var letsGetHeight by remember { mutableStateOf(0) }
    val baselinePadding by derivedStateOf {
        density.run { (state.size.height - state.baseline - letsGetHeight + letsGetBaseline).toDp() }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp, start = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        Column {
            MarathonText(year)
            LetsGetText(Modifier.padding(bottom = baselinePadding)) {
                letsGetBaseline = it.lastBaseline
                letsGetHeight = it.size.height
            }
        }
        NeonText(state)
    }
}

@Composable
private fun MarathonText(year: String) {
    Text(
        modifier = Modifier.alpha(0.5F),
        text = stringResource(R.string.marathon, year),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        ),
    )
}

@Composable
private fun LetsGetText(modifier: Modifier = Modifier, onTextLayout: (TextLayoutResult) -> Unit) {
    Text(
        modifier = modifier,
        text = "Let's get ",
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
        ),
        onTextLayout = onTextLayout,
    )
}

@Composable
private fun MainContent(
    trainings: List<TrainingUIModel>,
    bottomBarInset: () -> Dp,
    onItemFavorited: (TrainingUIModel) -> Unit,
    onItemSelected: (TrainingUIModel) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PullTab()
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = bottomBarInset() + 16.dp)
        ) {
            item { TrainingsHeader(trainings.size) }
            items(trainings) {
                TrainingItem(training = it, onSelected = onItemSelected, onFavorited = onItemFavorited)
            }
        }
    }
}

@Composable
private fun PullTab() = Box(
    modifier = Modifier
        .padding(top = 8.dp, bottom = 8.dp)
        .size(width = 50.dp, height = 5.dp)
        .background(color = HighSurfaceColor),
)

@Composable
private fun TrainingsHeader(amountTrainings: Int) = Text(
    modifier = Modifier
        .padding(top = 8.dp, bottom = 8.dp)
        .alpha(0.45F),
    text = stringResource(id = R.string.trainings, amountTrainings),
    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
)

@Composable
@Preview
fun ScreenPreview() {
    MidnightFuelTheme {
        Screen(
            snackbarHostState = remember { SnackbarHostState() },
            bottomBarInset = { 0.dp },
            year = "2022",
            trainings = List(20) { TrainingUIModel(it, "", "Item $it", "Subtitle $it", it % 2 == 0) },
            onTraining = {},
            onFavorite = {},
        )
    }
}
