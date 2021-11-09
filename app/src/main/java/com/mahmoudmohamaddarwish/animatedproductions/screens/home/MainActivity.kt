package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.CenteredText
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.CoilImage
import com.mahmoudmohamaddarwish.animatedproductions.screens.details.ProductionDetailsActivity.Companion.navigateToDetails
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.BottomNavigationDestinationModel.Companion.DESTINATION_MODELS
import com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels.FavoritesViewModel
import com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels.NightModeViewModel
import com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels.ProductionsViewModel
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme3.POSTER_IMAGE_HEIGHT
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme3.PRODUCTION_POSTER_IMAGE_PADDING
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme3.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@FlowPreview
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent { AppTheme { HomeScreen() } }
    }
}


@FlowPreview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val currentBackStackEntry by navController.currentBackStackEntryFlow
        .collectAsState(initial = null)

    Scaffold(
        bottomBar = { MainNavigationBottomBar(currentBackStackEntry, navController) },
        modifier = Modifier.testTag(MainActivityTestTags.ROOT_COMPOSABLE.name)
    ) { paddingValues ->
        MainNavHost(navController, paddingValues)
    }
}

@FlowPreview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationDestinationModel.Movies.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        DESTINATION_MODELS.forEach { destination ->
            composable(route = destination.route) {
                Scaffold(topBar = { MainAppBar(destination.labelComposableLambda) }) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) { destination.content() }
                }
            }
        }
    }
}

@FlowPreview
@Composable
private fun MainNavigationBottomBar(
    currentBackStackEntry: NavBackStackEntry?,
    navController: NavHostController,
) {
    NavigationBar(
        Modifier.testTag(MainActivityTestTags.NAV_BAR.name)
    ) {
        DESTINATION_MODELS.forEach { destination ->
            NavigationBarItem(
                selected = currentBackStackEntry?.destination?.route == destination.route,
                onClick = {
                    navController.navigate(route = destination.route) { launchSingleTop = true }
                },
                icon = { Icon(imageVector = destination.icon, contentDescription = null) },
                label = destination.labelComposableLambda,
                modifier = Modifier.testTag(destination.navBarItemTestTag)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductionsPagedGrid(
    productionsFlow: Flow<PagingData<Production>>,
    testTag: String,
    onLoaded: () -> Unit,
) {
    val context = LocalContext.current
    val lazyPagingItems = productionsFlow.collectAsLazyPagingItems()

    Column {
        if (lazyPagingItems.loadState.refresh == LoadState.Loading
            || lazyPagingItems.loadState.append == LoadState.Loading
        )
            Text(
                text = stringResource(R.string.paging_loading_message),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

        if (lazyPagingItems.loadState.refresh is LoadState.NotLoading
            && lazyPagingItems.itemCount == 0
        ) {
            CenteredText(text = stringResource(R.string.paging_empty_list_message))
        } else if (
            lazyPagingItems.loadState.refresh is LoadState.NotLoading
            && lazyPagingItems.itemCount > 0
        ) {
            onLoaded()
        }

        LoadedProductionsGird(
            lazyPagingItems = lazyPagingItems,
            testTag = testTag,
            context = context
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LoadedProductionsGird(
    lazyPagingItems: LazyPagingItems<Production>,
    testTag: String,
    context: Context,
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(PRODUCTIONS_GRID_CELLS_NUMBER),
        contentPadding = PaddingValues(top = 0.dp,
            start = 4.dp,
            end = 4.dp,
            bottom = 4.dp),
        modifier = Modifier.testTag(testTag)
    ) {

        items(lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]
            item?.let { production ->
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CoilImage(
                        url = production.posterPath,
                        imageDescription = stringResource(R.string.poster_image_description),
                        modifier = Modifier
                            .padding(PRODUCTION_POSTER_IMAGE_PADDING)
                            .height(POSTER_IMAGE_HEIGHT)
                            .fillMaxWidth()
                            .clickable { context.navigateToDetails(production) }
                            .testTag(MainActivityTestTags.POSTER_IMAGE.name)
                    )
                }
            }
        }
    }
}

@Composable
fun MainAppBar(
    title: @Composable () -> Unit,
    nightModeViewModel: NightModeViewModel = hiltViewModel(),
) {
    val nightModeEnabled by nightModeViewModel.isNightModeEnabled.collectAsState(initial = false)

    CenterAlignedTopAppBar(
        title = title,
        actions = {
            IconButton(onClick = { nightModeViewModel.toggleNightMode() },
                modifier = Modifier.testTag(MainActivityTestTags.UI_MODE_ICON_BUTTON.name)) {
                Icon(if (nightModeEnabled) Icons.Default.LightMode else Icons.Default.ModeNight,
                    null)
            }
        },
        modifier = Modifier
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(8.dp))
            .testTag(MainActivityTestTags.MAIN_TOP_APP_BAR.name)
    )
}

/**
 * This sealed class represents a screen content for each corresponding bottom navigation item
 *
 * The items are:
 *      1. Movies
 *      2. Shows
 *      3. Favorite Movies
 *      4. Favorite Shows
 * */
@OptIn(ExperimentalCoroutinesApi::class)
@FlowPreview
sealed class BottomNavigationDestinationModel(
    val route: String,
    val icon: ImageVector,
    val labelComposableLambda: @Composable () -> Unit,
    val navBarItemTestTag: String,
    val content: @Composable () -> Unit,
) {
    object Movies : BottomNavigationDestinationModel(
        route = "movies",
        icon = Icons.Default.Movie,
        labelComposableLambda = { Text(stringResource(id = R.string.movies_tab_label)) },
        navBarItemTestTag = MainActivityTestTags.MOVIES_NAV_ITEM.name,
        content = @Composable {
            val productionsViewModel: ProductionsViewModel = hiltViewModel()
            ProductionsPagedGrid(
                productionsViewModel.moviesDataSourceFlow,
                MainActivityTestTags.MOVIES_LIST.name
            ) {
                MainActivityLoadingState.successfullyLoadedMovies.set(true)
            }
        }
    )

    object Shows : BottomNavigationDestinationModel(
        route = "shows",
        icon = Icons.Filled.Tv,
        labelComposableLambda = { Text(stringResource(id = R.string.shows_tab_label)) },
        navBarItemTestTag = MainActivityTestTags.SHOWS_NAV_ITEM.name,
        content = @Composable {
            val productionsViewModel: ProductionsViewModel = hiltViewModel()
            ProductionsPagedGrid(
                productionsViewModel.showsDataSourceFlow,
                MainActivityTestTags.SHOWS_LIST.name
            ) {
                MainActivityLoadingState.successfullyLoadedShows.set(true)
            }
        }
    )

    object FavoriteMovies : BottomNavigationDestinationModel(
        route = "favoriteMovies",
        icon = Icons.Default.Favorite,
        labelComposableLambda = { Text(stringResource(R.string.favorite_movies)) },
        navBarItemTestTag = MainActivityTestTags.FAVORITE_MOVIES_NAV_ITEM.name,
        content = @Composable {
            val favoritesViewModel: FavoritesViewModel = hiltViewModel()
            ProductionsPagedGrid(
                favoritesViewModel.moviesPaged,
                MainActivityTestTags.FAVORITE_MOVIES_LIST.name
            ) {
                MainActivityLoadingState.successfullyLoadedFavoriteMovies.set(true)
            }
        }
    )

    object FavoriteShows : BottomNavigationDestinationModel(
        route = "favoriteShows",
        icon = Icons.Default.Favorite,
        labelComposableLambda = { Text(stringResource(R.string.favorite_shows)) },
        navBarItemTestTag = MainActivityTestTags.FAVORITE_SHOWS_NAV_ITEM.name,
        content = @Composable {
            val favoritesViewModel: FavoritesViewModel = hiltViewModel()
            ProductionsPagedGrid(
                favoritesViewModel.showsPaged,
                MainActivityTestTags.FAVORITE_SHOWS_LIST.name) {
                MainActivityLoadingState.successfullyLoadedFavoriteShows.set(true)
            }
        }
    )

    companion object {
        val DESTINATION_MODELS = listOf(Movies, Shows, FavoriteMovies, FavoriteShows)
    }
}
