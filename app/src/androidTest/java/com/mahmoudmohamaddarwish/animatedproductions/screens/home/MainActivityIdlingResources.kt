/**
 * Due to a problem with the IdlingResource Compose library, I was able to implement the
 * IdlingResource interface only under the androidTest folder, thus, due to that, I have implemented
 * it for each asynchronous operation that needs to be waited for before the test continues
 */
package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import androidx.compose.ui.test.IdlingResource


object MoviesIdlingResource : IdlingResource {
    override val isIdleNow: Boolean
        get() = MainActivityLoadingState.successfullyLoadedMovies.get()
}

object ShowsIdlingResource : IdlingResource {
    override val isIdleNow: Boolean
        get() = MainActivityLoadingState.successfullyLoadedShows.get()
}

object FavoriteShowsIdlingResource : IdlingResource {
    override val isIdleNow: Boolean
        get() = MainActivityLoadingState.successfullyLoadedFavoriteShows.get()
}

object FavoriteMoviesIdlingResource : IdlingResource {
    override val isIdleNow: Boolean
        get() = MainActivityLoadingState.successfullyLoadedFavoriteMovies.get()
}