package com.mahmoudmohamaddarwish.animatedproductions.screens.home

/**
 * This object should be implementing the Compose IdlingResource interface, but due to a problem
 * with the library, I had to implement the IdlingResource interface under the androidTest folder
 * and link that implementation with this object here.
 */
object MainActivityLoadingState {
    var successfullyLoadedMoviesAndShows = false

    val completedLoading: Boolean
        get() = successfullyLoadedMoviesAndShows
}