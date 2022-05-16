package com.m2mobi.midnightfuel.train.model

sealed class TrainEvents {

    data class ShowSnackBar(val message: String) : TrainEvents()
}
