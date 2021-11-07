package com.mahmoudmohamaddarwish.animatedproductions.screens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.FavoriteMoviesTabContent
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.FavoriteShowsTabContent
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MoviesTabContent
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.ShowsTabContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNav() {
    val destinations: List<BottomNavigationDestination> = listOf(
        BottomNavigationDestination.Movies,
        BottomNavigationDestination.Shows,
        BottomNavigationDestination.FavoriteMovies,
        BottomNavigationDestination.FavoriteShows,
    )

    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(initial = null)

    Scaffold(
        bottomBar = {
            NavigationBar {
                destinations.forEach { destination ->
                    NavigationBarItem(
                        selected = currentBackStackEntry?.destination?.route == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(destination.icon, contentDescription = null) },
                        label = destination.title
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController,
            BottomNavigationDestination.Movies.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            destinations.forEach { destination ->
                composable(
                    route = destination.route,
                    content = destination.content,
                )
            }
        }
    }
}

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
}