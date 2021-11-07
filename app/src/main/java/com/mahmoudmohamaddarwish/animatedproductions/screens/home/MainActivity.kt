package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.CenteredLoadingMessageWithIndicator
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.CenteredText
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.ProductionsGridList
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.component.MainAppBar
import com.mahmoudmohamaddarwish.animatedproductions.screens.navigation.BottomNavigationDestination
import com.mahmoudmohamaddarwish.animatedproductions.screens.navigation.MainNav
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { MainNav() }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowsTabContent(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val resource by homeViewModel.orderedShowsFlow.collectAsState(initial = Resource.Loading)

    Scaffold(
        topBar = {
            MainAppBar(BottomNavigationDestination.Shows.title)
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            when (resource) {
                is Resource.Error -> {
                    val error = resource as Resource.Error
                    CenteredText(text = error.message)
                }

                is Resource.Loading -> CenteredLoadingMessageWithIndicator(
                    Modifier.testTag(MAIN_ACTIVITY_SHOWS_LOADING_INDICATOR_TEST_TAG)
                )

                is Resource.Success -> {
                    val success = resource as Resource.Success<List<Production>>
                    ProductionsGridList(success, MAIN_ACTIVITY_SHOWS_LIST_TEST_TAG)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesTabContent(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val resource by homeViewModel.orderedMoviesFlow.collectAsState(initial = Resource.Loading)

    Scaffold(
        topBar = {
            MainAppBar(BottomNavigationDestination.Movies.title)
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            when (resource) {
                is Resource.Error -> {
                    val error = resource as Resource.Error
                    CenteredText(text = error.message)
                }

                is Resource.Loading -> CenteredLoadingMessageWithIndicator(
                    Modifier.testTag(MAIN_ACTIVITY_MOVIES_LOADING_INDICATOR_TEST_TAG)
                )

                is Resource.Success -> {
                    val success = resource as Resource.Success<List<Production>>
                    ProductionsGridList(resource = success,
                        testTag = MAIN_ACTIVITY_MOVIES_LIST_TEST_TAG)
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteMoviesTabContent(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val resource by homeViewModel.orderedMoviesFlow.collectAsState(initial = Resource.Loading)

    Scaffold(
        topBar = {
            MainAppBar(BottomNavigationDestination.FavoriteMovies.title)
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            when (resource) {
                is Resource.Error -> {
                    val error = resource as Resource.Error
                    CenteredText(text = error.message)
                }

                is Resource.Loading -> CenteredLoadingMessageWithIndicator(
                    Modifier.testTag(MAIN_ACTIVITY_MOVIES_LOADING_INDICATOR_TEST_TAG)
                )

                is Resource.Success -> {
                    val success = resource as Resource.Success<List<Production>>
                    ProductionsGridList(resource = success,
                        testTag = MAIN_ACTIVITY_MOVIES_LIST_TEST_TAG)
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteShowsTabContent(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val resource by homeViewModel.orderedShowsFlow.collectAsState(initial = Resource.Loading)

    Scaffold(
        topBar = {
            MainAppBar(BottomNavigationDestination.FavoriteMovies.title)
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            when (resource) {
                is Resource.Error -> {
                    val error = resource as Resource.Error
                    CenteredText(text = error.message)
                }

                is Resource.Loading -> CenteredLoadingMessageWithIndicator(
                    Modifier.testTag(MAIN_ACTIVITY_MOVIES_LOADING_INDICATOR_TEST_TAG)
                )

                is Resource.Success -> {
                    val success = resource as Resource.Success<List<Production>>
                    ProductionsGridList(resource = success,
                        testTag = MAIN_ACTIVITY_MOVIES_LIST_TEST_TAG)
                }
            }

        }
    }
}

