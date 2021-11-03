package com.mahmoudmohamaddarwish.animatedproductions.screens.home

object MainActivityLoadingState {
    var doneLoadingMoviesAndShows = false

    val completedLoading: Boolean
        get() = doneLoadingMoviesAndShows
}