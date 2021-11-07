package com.mahmoudmohamaddarwish.animatedproductions.screens.home.destinations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import com.mahmoudmohamaddarwish.animatedproductions.R

sealed class BottomNavigationDestination(
    val route: String,
    val icon: ImageVector,
    val content: @Composable (NavBackStackEntry) -> Unit,
    val title: @Composable () -> Unit,
) {
    object Movies : BottomNavigationDestination(
        "movies",
        Icons.Default.Movie,
        content = @Composable {
            MoviesTabContent()
        },
        title = { Text(stringResource(id = R.string.movies_tab_label)) }
    )

    object Shows : BottomNavigationDestination(
        "shows",
        Icons.Filled.Tv,
        content = @Composable {
            ShowsTabContent()
        },
        title = { Text(stringResource(id = R.string.shows_tab_label)) }
    )

    object FavoriteMovies :
        BottomNavigationDestination(
            "favoriteMovies",
            Icons.Default.Favorite,
            content = @Composable {
                FavoriteMoviesTabContent()
            },
            title = { Text(stringResource(R.string.favorite_movies)) }
        )

    object FavoriteShows :
        BottomNavigationDestination(
            "favoriteShows",
            Icons.Default.Favorite,
            content = @Composable {
                FavoriteShowsTabContent()
            },
            title = { Text(stringResource(R.string.favorite_shows)) }
        )

    companion object {
        val destinations: List<BottomNavigationDestination> = listOf(
            BottomNavigationDestination.Movies,
            BottomNavigationDestination.Shows,
            BottomNavigationDestination.FavoriteMovies,
            BottomNavigationDestination.FavoriteShows,
        )
    }
}