package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.CenteredLoadingMessageWithIndicator
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.CenteredText
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.ProductionsGridList
import com.mahmoudmohamaddarwish.animatedproductions.screens.favorites.FavoritesViewModel
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.BottomNavigationDestinationModel.Companion.DESTINATION_MODELS
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.viewmodels.ProductionsOrderViewModel
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.viewmodels.ProductionsViewModel
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.SORT_MENU_OPTION_HEIGHT
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.SORT_MENU_OPTION_HORIZONTAL_PADDING
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.SORT_MENU_OPTION_TEXT_START_PADDING
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme3.AppTheme
import dagger.hilt.android.AndroidEntryPoint
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

@Composable
fun ProductionsResourceFlowGrid(orderedMoviesFlow: Flow<Resource<List<Production>>>) {
    val resource by orderedMoviesFlow.collectAsState(initial = Resource.Loading)

    when (resource) {
        is Resource.Error -> {
            val error = resource as Resource.Error
            CenteredText(text = error.message)
        }

        is Resource.Loading -> CenteredLoadingMessageWithIndicator(
            modifier = Modifier.testTag(MAIN_ACTIVITY_MOVIES_LOADING_INDICATOR_TEST_TAG)
        )

        is Resource.Success -> {
            ProductionsGridList(
                resource = resource as Resource.Success<List<Production>>,
                testTag = MAIN_ACTIVITY_MOVIES_LIST_TEST_TAG
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
                val propertyRadioOptions: List<Order.Property> = listOf(Order.Property.Name,
                    Order.Property.RELEASE_DATE)

                val typeRadioOptions: List<Order.Type> = listOf(Order.Type.ASCENDING,
                    Order.Type.DESCENDING)

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
                                    Order.Property.Name -> Icons.Default.SortByAlpha
                                    Order.Property.RELEASE_DATE -> Icons.Default.Schedule
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
            ProductionsResourceFlowGrid(productionsViewModel.orderedMoviesFlow)
        }
    )

    object Shows : BottomNavigationDestinationModel(
        route = "shows",
        icon = Icons.Filled.Tv,
        labelComposableLambda = { Text(stringResource(id = R.string.shows_tab_label)) },
        content = @Composable {
            val productionsViewModel: ProductionsViewModel = hiltViewModel()
            ProductionsResourceFlowGrid(productionsViewModel.orderedShowsFlow)
        }
    )

    object FavoriteMovies : BottomNavigationDestinationModel(
        route = "favoriteMovies",
        icon = Icons.Default.Favorite,
        labelComposableLambda = { Text(stringResource(R.string.favorite_movies)) },
        content = @Composable {
            val favoritesViewModel: FavoritesViewModel = hiltViewModel()
            ProductionsResourceFlowGrid(favoritesViewModel.movies)
        }
    )

    object FavoriteShows : BottomNavigationDestinationModel(
        route = "favoriteShows",
        icon = Icons.Default.Favorite,
        labelComposableLambda = { Text(stringResource(R.string.favorite_shows)) },
        content = @Composable {
            val favoritesViewModel: FavoritesViewModel = hiltViewModel()
            ProductionsResourceFlowGrid(favoritesViewModel.shows)

        }
    )

    companion object {
        val DESTINATION_MODELS = listOf(Movies, Shows, FavoriteMovies, FavoriteShows)
    }
}
