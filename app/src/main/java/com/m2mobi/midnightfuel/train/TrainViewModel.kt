package com.m2mobi.midnightfuel.train

import androidx.lifecycle.ViewModel
import com.m2mobi.midnightfuel.train.model.TrainingUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrainViewModel : ViewModel() {

    private val _trainings = MutableStateFlow<List<TrainingUIModel>>(emptyList())
    val trainings: StateFlow<List<TrainingUIModel>> by lazy {
        fetchTrainings()
        _trainings.asStateFlow()
    }

    val year = MutableStateFlow("2022").asStateFlow()

    fun onFavorite(training: TrainingUIModel) {
        _trainings.value = trainings.value.map {
            if (it.id == training.id) it.copy(isFavorite = !training.isFavorite) else it
        }
    }

    fun onTraining(training: TrainingUIModel) {
        // Do things
    }

    private fun fetchTrainings() {
        _trainings.value = listOf(
            TrainingUIModel(
                id = 0,
                imageUrl = "https://support.wwf.org.uk/sites/default/files/background-images/koala_0.jpg",
                title = "Leg day",
                subtitle = "Today",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 1,
                imageUrl = "https://scitechdaily.com/images/Two-Koalas-in-a-Tree-Crop.jpg",
                title = "City run",
                subtitle = "Tuesday",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 2,
                imageUrl = "https://media.radiocms.net/uploads/2019/11/24154139/PA-42584780.jpg",
                title = "Core training",
                subtitle = "Wednesday",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 3,
                imageUrl = "https://essaussie.org/wp-content/uploads/2019/08/koala_7660.jpg",
                title = "Leg day",
                subtitle = "Saturday",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 4,
                imageUrl = "https://support.wwf.org.uk/sites/default/files/background-images/koala_0.jpg",
                title = "Leg day",
                subtitle = "Today",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 5,
                imageUrl = "https://scitechdaily.com/images/Two-Koalas-in-a-Tree-Crop.jpg",
                title = "City run",
                subtitle = "Tuesday",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 6,
                imageUrl = "https://media.radiocms.net/uploads/2019/11/24154139/PA-42584780.jpg",
                title = "Core training",
                subtitle = "Wednesday",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 7,
                imageUrl = "https://essaussie.org/wp-content/uploads/2019/08/koala_7660.jpg",
                title = "Leg day",
                subtitle = "Saturday",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 8,
                imageUrl = "https://support.wwf.org.uk/sites/default/files/background-images/koala_0.jpg",
                title = "Leg day",
                subtitle = "Today",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 9,
                imageUrl = "https://scitechdaily.com/images/Two-Koalas-in-a-Tree-Crop.jpg",
                title = "City run",
                subtitle = "Tuesday",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 10,
                imageUrl = "https://media.radiocms.net/uploads/2019/11/24154139/PA-42584780.jpg",
                title = "Core training",
                subtitle = "Wednesday",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 11,
                imageUrl = "https://essaussie.org/wp-content/uploads/2019/08/koala_7660.jpg",
                title = "Leg day",
                subtitle = "Saturday",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 12,
                imageUrl = "https://support.wwf.org.uk/sites/default/files/background-images/koala_0.jpg",
                title = "Leg day",
                subtitle = "Today",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 13,
                imageUrl = "https://scitechdaily.com/images/Two-Koalas-in-a-Tree-Crop.jpg",
                title = "City run",
                subtitle = "Tuesday",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 14,
                imageUrl = "https://media.radiocms.net/uploads/2019/11/24154139/PA-42584780.jpg",
                title = "Core training",
                subtitle = "Wednesday",
                isFavorite = false,
            ),
            TrainingUIModel(
                id = 15,
                imageUrl = "https://essaussie.org/wp-content/uploads/2019/08/koala_7660.jpg",
                title = "Leg day",
                subtitle = "Saturday",
                isFavorite = false,
            ),
        )
    }
}
