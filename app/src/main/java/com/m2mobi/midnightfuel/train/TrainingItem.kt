 package com.m2mobi.midnightfuel.train

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.m2mobi.midnightfuel.extension.rippleClickable
import com.m2mobi.midnightfuel.extension.shimmer
import com.m2mobi.midnightfuel.theme.AccentColor
import com.m2mobi.midnightfuel.theme.Shapes
import com.m2mobi.midnightfuel.train.model.TrainingUIModel
import com.m2mobi.midnightfuel.util.imageRequest

@Composable
internal fun TrainingItem(
    training: TrainingUIModel,
    onSelected: (TrainingUIModel) -> Unit,
    onFavorited: (TrainingUIModel) -> Unit,
) = TrainingItem(
    imageUrl = training.imageUrl,
    title = training.title,
    subtitle = training.subtitle,
    isFavorite = training.isFavorite,
    onSelected = { onSelected(training) },
    onFavorited = { onFavorited(training) },
)

@Composable
private fun TrainingItem(
    imageUrl: String,
    title: String,
    subtitle: String,
    isFavorite: Boolean,
    onSelected: () -> Unit,
    onFavorited: () -> Unit,
) {
    Row(
        modifier = Modifier
            .rippleClickable(onSelected)
            .padding(vertical = 8.dp)
            .padding(start = 16.dp, end = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ContentImage(imageUrl)
        Column(
            modifier = Modifier
                .weight(1F)
                .padding(start = 16.dp, end = 4.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold),
            )
            Text(
                modifier = Modifier.alpha(0.5F),
                text = subtitle,
                style = TextStyle(fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold),
            )
        }
        FavoriteIcon(isFavorite, onFavorited)
    }
}

@Composable
private fun ContentImage(url: String) {
    var showPlaceholder by remember { mutableStateOf(true) }
    Surface(elevation = 0.dp, color = Color.Transparent, shape = Shapes.medium) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .shimmer(
                    visible = showPlaceholder,
                    shape = Shapes.medium,
                ),
            model = imageRequest(url),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            onSuccess = { showPlaceholder = false },
            onError = { showPlaceholder = false },
            onLoading = { showPlaceholder = true },
        )
    }
}

@Composable
private fun FavoriteIcon(isFavorite: Boolean, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.padding(12.dp),
            contentDescription = "",
            imageVector = Icons.Filled.Favorite,
            tint = if (isFavorite) AccentColor else Color.White,
        )
    }
}
