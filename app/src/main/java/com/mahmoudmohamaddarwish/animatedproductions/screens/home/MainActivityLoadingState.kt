package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import java.util.concurrent.atomic.AtomicBoolean

/**
 * This object should be implementing the Compose IdlingResource interface, but due to a problem
 * with the library, I had to implement the IdlingResource interface under the androidTest folder
 * and link that implementation with this object here.
 */
object MainActivityLoadingState {
    var successfullyLoadedMovies = AtomicBoolean(false)
    var successfullyLoadedShows = AtomicBoolean(false)
    var successfullyLoadedFavoriteMovies = AtomicBoolean(false)
    var successfullyLoadedFavoriteShows = AtomicBoolean(false)
}