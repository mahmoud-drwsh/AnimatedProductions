package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.getImageUrl
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderingUseCase
import com.mahmoudmohamaddarwish.animatedproductions.ui.components.CenteredLoadingMessageWithIndicator
import com.mahmoudmohamaddarwish.animatedproductions.ui.components.CenteredText
import com.mahmoudmohamaddarwish.animatedproductions.ui.components.CoilImage
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.AnimatedProductionsTheme
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

    sealed class Tab(val label: String) {
        object Movies : Tab("Movies")
        object Shows : Tab("TV Shows")
    }

}


@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val moviesFlow by viewModel.moviesFlow.collectAsState(initial = Resource.Loading)
    val showsFlow by viewModel.showsFlow.collectAsState(initial = Resource.Loading)

    AnimatedProductionsTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Popular Movies & Shows", Modifier.fillMaxWidth())
                    },
                    actions = {
                        DropDownMenu(viewModel.orderingUseCase)
                    }
                )
            }
        ) {
            Box(Modifier.padding(it)) {
                HomeScreenTabLayout(moviesFlow, showsFlow)
            }
        }
    }
}


@Composable
fun DropDownMenu(repo: OrderingUseCase) {
    var shown by remember { mutableStateOf(false) }

    val order by repo.order.collectAsState(initial = Order.default)

    IconButton(onClick = { shown = true }) {
        Icon(Icons.Default.Sort, contentDescription = "Localized description")
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
                                .height(56.dp)
                                .selectable(
                                    selected = (orderProperty == order.property),
                                    onClick = {
                                        repo.setOrderProperty(orderProperty)
                                        shown = false
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (orderProperty == order.property),
                                onClick = null // null recommended for accessibility with screenreaders
                            )
                            Text(
                                text = orderProperty.label,
                                style = MaterialTheme.typography.body1.merge(),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }

                    Divider()

                    typeRadioOptions.forEach { orderProperty ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (orderProperty == order.type),
                                    onClick = {
                                        repo.setOrderType(orderProperty)
                                        shown = false
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (orderProperty == order.type),
                                onClick = null // null recommended for accessibility with screenreaders
                            )
                            Text(
                                text = orderProperty.label,
                                style = MaterialTheme.typography.body1.merge(),
                                modifier = Modifier.padding(start = 16.dp)
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

    Column(Modifier.fillMaxSize()) {
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
                    text = { Text(tab.label) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        rememberCoroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesTabContent(resource: Resource<List<Production>>) {
    when (resource) {
        is Resource.Error -> {
            CenteredText(text = resource.message)
        }
        is Resource.Loading -> CenteredLoadingMessageWithIndicator()
        is Resource.Success -> LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(4.dp),
        ) {
            items(resource.data) {
                CoilImage(url = getImageUrl(it.posterPath),
                    imageDescription = "",
                    modifier = Modifier.padding(4.dp))
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowsTabContent(resource: Resource<List<Production>>) {
    when (resource) {
        is Resource.Error -> {
            CenteredText(text = resource.message)
        }

        is Resource.Loading -> CenteredLoadingMessageWithIndicator()

        is Resource.Success -> LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(4.dp),
        ) {
            items(resource.data) {
                CoilImage(url = getImageUrl(it.posterPath),
                    imageDescription = "",
                    modifier = Modifier.padding(4.dp))
            }
        }

    }
}
