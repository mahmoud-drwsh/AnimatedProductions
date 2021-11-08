package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Order
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.CenteredContent
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.CoilImage
import com.mahmoudmohamaddarwish.animatedproductions.screens.details.ProductionDetailsActivity.Companion.navigateToDetails
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.BottomNavigationDestinationModel.Companion.DESTINATION_MODELS
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.viewmodels.ProductionsOrderViewModel
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.viewmodels.ProductionsViewModel
import com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels.FavoritesViewModel
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.*
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme3.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { AppTheme { HomeScreen() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val currentBackStackEntry by navController.currentBackStackEntryFlow
        .collectAsState(initial = null)

    Scaffold(
        bottomBar = { MainNavigationBottomBar(currentBackStackEntry, navController) }
    ) { paddingValues ->
        MainNavHost(navController, paddingValues)
    }
}

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
    NavigationBar {
        DESTINATION_MODELS.forEach { destination ->
            NavigationBarItem(
                selected = currentBackStackEntry?.destination?.route == destination.route,
                onClick = {
                    navController.navigate(route = destination.route) { launchSingleTop = true }
                },
                icon = { Icon(imageVector = destination.icon, contentDescription = null) },
                label = destination.labelComposableLambda
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductionsPagedGrid(productionsFlow: Flow<PagingData<Production>>) {
    val context = LocalContext.current
    val lazyPagingItems = productionsFlow.collectAsLazyPagingItems()

    Column {
        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }

        if (lazyPagingItems.itemCount == 0) {
            CenteredContent {
                Text(text = "Empty list")
                Button(onClick = { lazyPagingItems.retry() }) {
                    Text(text = "Retry")
                }

            }
        } else {
            LazyVerticalGrid(
                cells = GridCells.Fixed(PRODUCTIONS_GRID_CELLS_NUMBER),
                contentPadding = PaddingValues(top = 0.dp, start = 4.dp, end = 4.dp, bottom = 4.dp),
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
                            )
                        }
                    }
                }
            }
        }

        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            Text(
                text = "Waiting for items to load from the backend",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }

}

@Composable
fun SortDialog(viewModel: ProductionsOrderViewModel = hiltViewModel()) {
    var shown by remember { mutableStateOf(false) }

    val order by viewModel.order.collectAsState(initial = Order.default)

    IconButton(
        onClick = { shown = true },
        modifier = Modifier.testTag(MAIN_ACTIVITY_SORTING_ICON_BUTTON_TEST_TAG),
    ) {
        Icon(
            imageVector = Icons.Default.Sort,
            contentDescription = stringResource(R.string.sort_productions_icon_description)
        )
    }

    if (shown)
        Dialog(onDismissRequest = { shown = false }) {
            Surface(
                shape = RoundedCornerShape(8.dp)
            ) {
                val propertyRadioOptions: Array<Order.Property> = Order.Property.values()

                val typeRadioOptions: Array<Order.Type> = Order.Type.values()

                Column(Modifier.selectableGroup()) {
                    propertyRadioOptions.forEach { orderProperty ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(SORT_MENU_OPTION_HEIGHT)
                                .selectable(
                                    selected = (orderProperty == order.property),
                                    onClick = {
                                        viewModel.setSortProperty(orderProperty)
                                        shown = false
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = SORT_MENU_OPTION_HORIZONTAL_PADDING),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (orderProperty == order.property),
                                onClick = null // null recommended for accessibility with screenreaders
                            )
                            Text(
                                text = orderProperty.label,
                                style = MaterialTheme.typography.body1.merge(),
                                modifier = Modifier.padding(start = SORT_MENU_OPTION_HORIZONTAL_PADDING)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = when (orderProperty) {
                                    Order.Property.VOTE_AVERAGE -> Icons.Default.SortByAlpha
                                    Order.Property.RELEASE_DATE -> Icons.Default.Schedule
                                    Order.Property.POPULARITY -> Icons.Default.Favorite
                                },
                                contentDescription = null,
                            )
                        }
                    }

                    Divider()

                    typeRadioOptions.forEach { orderProperty ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(SORT_MENU_OPTION_HEIGHT)
                                .selectable(
                                    selected = (orderProperty == order.type),
                                    onClick = {
                                        viewModel.setSortType(orderProperty)
                                        shown = false
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = SORT_MENU_OPTION_HORIZONTAL_PADDING),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (orderProperty == order.type),
                                onClick = null // null recommended for accessibility with screenreaders
                            )
                            Text(
                                text = orderProperty.label,
                                style = MaterialTheme.typography.body1.merge(),
                                modifier = Modifier.padding(start = SORT_MENU_OPTION_TEXT_START_PADDING)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = when (orderProperty) {
                                    Order.Type.ASCENDING -> Icons.Default.ArrowDownward
                                    Order.Type.DESCENDING -> Icons.Default.ArrowUpward
                                },
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
}

@Composable
fun MainAppBar(title: @Composable () -> Unit) {
    CenterAlignedTopAppBar(
        title = title,
        actions = {
            SortDialog()
        },
        modifier = Modifier
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(8.dp))
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
    val content: @Composable () -> Unit,
) {
    object Movies : BottomNavigationDestinationModel(
        route = "movies",
        icon = Icons.Default.Movie,
        labelComposableLambda = { Text(stringResource(id = R.string.movies_tab_label)) },
        content = @Composable {
            val productionsViewModel: ProductionsViewModel = hiltViewModel()
            ProductionsPagedGrid(productionsViewModel.moviesDataSourceFlow)
        }
    )

    object Shows : BottomNavigationDestinationModel(
        route = "shows",
        icon = Icons.Filled.Tv,
        labelComposableLambda = { Text(stringResource(id = R.string.shows_tab_label)) },
        content = @Composable {
            val productionsViewModel: ProductionsViewModel = hiltViewModel()
            ProductionsPagedGrid(productionsViewModel.showsDataSourceFlow)
        }
    )

    object FavoriteMovies : BottomNavigationDestinationModel(
        route = "favoriteMovies",
        icon = Icons.Default.Favorite,
        labelComposableLambda = { Text(stringResource(R.string.favorite_movies)) },
        content = @Composable {
            val favoritesViewModel: FavoritesViewModel = hiltViewModel()
            ProductionsPagedGrid(favoritesViewModel.moviesPaged)
        }
    )

    object FavoriteShows : BottomNavigationDestinationModel(
        route = "favoriteShows",
        icon = Icons.Default.Favorite,
        labelComposableLambda = { Text(stringResource(R.string.favorite_shows)) },
        content = @Composable {
            val favoritesViewModel: FavoritesViewModel = hiltViewModel()
            ProductionsPagedGrid(favoritesViewModel.showsPaged)
        }
    )

    companion object {
        val DESTINATION_MODELS = listOf(Movies, Shows, FavoriteMovies, FavoriteShows)
    }
}
