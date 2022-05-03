package com.m2mobi.midnightfuel.train

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.m2mobi.midnightfuel.main.Screen
import com.m2mobi.midnightfuel.neon.NeonText
import com.m2mobi.midnightfuel.neon.rememberNeonGlow
import com.m2mobi.midnightfuel.theme.PullTab
import com.m2mobi.midnightfuel.theme.Shapes
import com.m2mobi.midnightfuel.theme.SurfaceColor
import com.m2mobi.midnightfuel.theme.TextPrimaryColor
import com.m2mobi.midnightfuel.train.model.TrainingUIModel

class TrainScreen : Screen {

    @Composable
    override operator fun invoke(bottomBarPadding: Dp) {
        val viewModel: TrainViewModel = viewModel()
        val year by viewModel.year.collectAsState()
        val trainings by viewModel.trainings.collectAsState()
        Column {
            Header(year)
            MainContent(
                trainings = trainings,
                bottomBarPadding = bottomBarPadding,
                onItemSelected = viewModel::onTraining,
                onItemFavorited = viewModel::onFavorite,
            )
        }
    }

    @Composable
    private fun MainContent(
        trainings: List<TrainingUIModel>,
        bottomBarPadding: Dp,
        onItemFavorited: (TrainingUIModel) -> Unit,
        onItemSelected: (TrainingUIModel) -> Unit,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = SurfaceColor,
                    shape = Shapes.large.copy(bottomStart = CornerSize(0.dp), bottomEnd = CornerSize(0.dp))
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PullTab()
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = bottomBarPadding + 16.dp)
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
            .background(color = PullTab),
    )

    @Composable
    private fun TrainingsHeader(amountTrainings: Int) = Text(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .alpha(0.45F),
        text = "$amountTrainings trainings",
        style = TextStyle(fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold),
    )

    @Composable
    private fun Header(year: String) {
        var letsGetBaseline by remember { mutableStateOf(0F) }
        var letsGetHeight by remember { mutableStateOf(0) }
        var physicalBaseline by remember { mutableStateOf(0F) }
        var physicalHeight by remember { mutableStateOf(0) }
        val baselinePadding = rememberBaselinePadding(
            letsGetBaseline = letsGetBaseline,
            letsGetHeight = letsGetHeight,
            physicalBaseline = physicalBaseline,
            physicalHeight = physicalHeight,
        )
        val animation = rememberNeonGlow(initialRadius = 2.dp, targetRadius = 15.dp)
        Row(
            modifier = Modifier.padding(top = 100.dp, start = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            Column {
                MarathonText(year)
                LetsGetText(Modifier.padding(bottom = baselinePadding)) {
                    letsGetBaseline = it.lastBaseline
                    letsGetHeight = it.size.height
                }
            }
            NeonText(
                modifier = Modifier.onGloballyPositioned { physicalHeight = it.size.height },
                text = "Physical",
                textSize = 46.sp,
                shadowRadius = Dp(animation.value),
                onBaseline = { physicalBaseline = it }
            )
        }
    }

    @Composable
    private fun LetsGetText(modifier: Modifier = Modifier, onTextLayout: (TextLayoutResult) -> Unit) {
        Text(
            modifier = modifier,
            text = "Let's get ",
            color = TextPrimaryColor,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = TextPrimaryColor,
            ),
            onTextLayout = onTextLayout,
        )
    }

    @Composable
    private fun MarathonText(year: String) {
        Text(
            modifier = Modifier.alpha(0.5F),
            text = "Marathon $year",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = TextPrimaryColor,
            ),
        )
    }

    @Composable
    private fun rememberBaselinePadding(
        letsGetBaseline: Float,
        letsGetHeight: Int,
        physicalBaseline: Float,
        physicalHeight: Int,
    ): Dp {
        return LocalDensity.current.run { (physicalHeight - physicalBaseline - letsGetHeight + letsGetBaseline).toDp() }
    }
}

@Composable
@Preview(backgroundColor = 0xFF232428, showBackground = true)
fun ScreenPreview() {
    TrainScreen().invoke(0.dp)
}
