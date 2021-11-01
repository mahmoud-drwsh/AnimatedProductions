package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.IdlingResource
import androidx.compose.ui.window.Dialog
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MainActivity.Companion.MAIN_ACTIVITY_POSTER_IMAGE_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MainActivity.Companion.MOVIES_AND_SHOWS_TAB_LAYOUT_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MainActivity.Companion.MOVIES_LIST_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MainActivity.Companion.MOVIES_LOADING_INDICATOR_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MainActivity.Companion.MOVIES_TAB_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MainActivity.Companion.SHOWS_LIST_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MainActivity.Companion.SHOWS_LOADING_INDICATOR_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MainActivity.Companion.SHOWS_TAB_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails.ProductionDetailsActivity.Companion.navigateToDetails
import com.mahmoudmohamaddarwish.animatedproductions.ui.components.CenteredLoadingMessageWithIndicator
import com.mahmoudmohamaddarwish.animatedproductions.ui.components.CenteredText
import com.mahmoudmohamaddarwish.animatedproductions.ui.components.CoilImage
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeScreen(viewModel)
        }
    }

    sealed class Tab {
        object Movies : Tab()
        object Shows : Tab()
    }

    companion object {
        const val MOVIES_AND_SHOWS_TAB_LAYOUT_TEST_TAG = "movies_and_shows_tag"

        const val MOVIES_LIST_TEST_TAG = "movies list test tag"

        const val SHOWS_LIST_TEST_TAG = "shows_list_test_tag"

        const val MOVIES_TAB_TEST_TAG = "movies tab test tag"

        const val SHOWS_TAB_TEST_TAG = "shows_tab_test_tag"

        const val MAIN_ACTIVITY_POSTER_IMAGE_TEST_TAG = "main activity poster image test tag"

        const val SHOWS_LOADING_INDICATOR_TEST_TAG = "shows loading indicator test tag"

        const val MOVIES_LOADING_INDICATOR_TEST_TAG = "movies loading indicator test tag"
    }
}


@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val moviesResource by viewModel.orderedMoviesFlow.collectAsState(initial = Resource.Loading)
    val showsResource by viewModel.orderedShowsFlow.collectAsState(initial = Resource.Loading)

    updateIdlingResourceStatus(moviesResource, showsResource)

    AnimatedProductionsTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.home_screen_title),
                            Modifier.fillMaxWidth())
                    },
                    actions = {
                        SortDialog(viewModel)
                    }
                )
            }
        ) {
            Box(Modifier.padding(it)) {
                HomeScreenTabLayout(moviesResource, showsResource)
            }
        }
    }
}

fun updateIdlingResourceStatus(
    moviesResource: Resource<List<Production>>,
    showsResource: Resource<List<Production>>,
) {
    homeIdlingResource.loading =
        moviesResource is Resource.Success && showsResource is Resource.Success
}


@Composable
fun SortDialog(viewModel: HomeViewModel) {
    var shown by remember { mutableStateOf(false) }

    val order by viewModel.order.collectAsState(initial = Order.default)

    IconButton(onClick = { shown = true }) {
        Icon(Icons.Default.Sort,
            contentDescription = stringResource(R.string.sort_productions_icon_description))
    }

    if (shown)
        Dialog(onDismissRequest = { shown = false }) {
            Card {
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
                        }
                    }
                }
            }
        }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenTabLayout(
    moviesFlow: Resource<List<Production>>,
    showsFlow: Resource<List<Production>>,
) {
    val rememberCoroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    val tabs = listOf(MainActivity.Tab.Movies, MainActivity.Tab.Shows)

    Column(Modifier
        .fillMaxSize()
        .testTag(MOVIES_AND_SHOWS_TAB_LAYOUT_TEST_TAG)) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = { Text(getLabelForTab(tab)) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        rememberCoroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    modifier = Modifier.testTag(getTestTagForTab(tab))
                )
            }
        }

        HorizontalPager(
            count = tabs.size,
            state = pagerState,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxSize()
        ) { tabIndex ->
            when (tabs[tabIndex]) {
                MainActivity.Tab.Movies -> MoviesTabContent(moviesFlow)
                MainActivity.Tab.Shows -> ShowsTabContent(showsFlow)
            }
        }
    }
}

@Composable
private fun getTestTagForTab(tab: MainActivity.Tab) = when (tab) {
    MainActivity.Tab.Movies -> MOVIES_TAB_TEST_TAG
    MainActivity.Tab.Shows -> SHOWS_TAB_TEST_TAG
}

@Composable
fun getLabelForTab(tab: MainActivity.Tab): String = when (tab) {
    MainActivity.Tab.Movies -> stringResource(id = R.string.movies_tab_label)
    MainActivity.Tab.Shows -> stringResource(id = R.string.shows_tab_label)
}

@Composable
fun MoviesTabContent(resource: Resource<List<Production>>) = when (resource) {
    is Resource.Error -> CenteredText(text = resource.message)
    is Resource.Loading -> CenteredLoadingMessageWithIndicator(Modifier.testTag(
        MOVIES_LOADING_INDICATOR_TEST_TAG))
    is Resource.Success -> ProductionsGridList(resource = resource,
        testTag = MOVIES_LIST_TEST_TAG)
}

@Composable
fun ShowsTabContent(resource: Resource<List<Production>>) = when (resource) {
    is Resource.Error -> CenteredText(text = resource.message)
    is Resource.Loading -> CenteredLoadingMessageWithIndicator(Modifier.testTag(
        SHOWS_LOADING_INDICATOR_TEST_TAG))
    is Resource.Success -> ProductionsGridList(resource,
        SHOWS_LIST_TEST_TAG)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProductionsGridList(resource: Resource.Success<List<Production>>, testTag: String) {
    val context = LocalContext.current

    LazyVerticalGrid(
        cells = GridCells.Fixed(PRODUCTIONS_GRID_CELLS_NUMBER),
        contentPadding = PaddingValues(PRODUCTIONS_GRID_CONTENT_PADDING),
        modifier = Modifier.testTag(testTag)
    ) {
        items(resource.data) { production ->
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
                        .testTag(MAIN_ACTIVITY_POSTER_IMAGE_TEST_TAG))
            }
        }
    }
}


object homeIdlingResource : IdlingResource {
    var loading = true

    override val isIdleNow: Boolean
        get() = !loading
}