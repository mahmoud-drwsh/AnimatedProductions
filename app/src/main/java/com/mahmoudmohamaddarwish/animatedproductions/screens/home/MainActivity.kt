package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.repos.OrderRepo
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.getImageUrl
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.DiscoverMoviesResponse
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.DiscoverTVResponse
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.ListMoviesAndShowsUseCase
import com.mahmoudmohamaddarwish.animatedproductions.ui.components.CenteredLoadingMessageWithIndicator
import com.mahmoudmohamaddarwish.animatedproductions.ui.components.CenteredText
import com.mahmoudmohamaddarwish.animatedproductions.ui.components.CoilImage
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.AnimatedProductionsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var orderRepo: OrderRepo

    @Inject
    lateinit var listMoviesAndShowsUseCase: ListMoviesAndShowsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AnimatedProductionsTheme {

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Popular Movies & Shows", Modifier.fillMaxWidth())
                            },
                            actions = {
                                DropDownMenu()
                            }
                        )
                    }
                ) {
                    Box(Modifier.padding(it)) {
                        HomeScreenTabLayout()
                    }
                }

            }
        }
    }

    sealed class Tab(val label: String) {
        object Movies : Tab("Movies")
        object Shows : Tab("TV Shows")
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun MoviesTabContent() {
        val collectAsState by
        listMoviesAndShowsUseCase.moviesFlow.collectAsState(initial = Resource.Loading)
        when (collectAsState) {
            is Resource.Error -> {
                val error = collectAsState as Resource.Error
                CenteredText(text = error.message)
            }
            Resource.Loading -> CenteredLoadingMessageWithIndicator()
            is Resource.Success -> LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(4.dp),
            ) {
                val success = collectAsState as Resource.Success<DiscoverMoviesResponse>

                items(success.data.discoverMovieItems) {
                    CoilImage(url = getImageUrl(it.posterPath),
                        imageDescription = "",
                        modifier = Modifier.padding(4.dp))
                }
            }

        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ShowsTabContent() {
        val collectAsState by
        listMoviesAndShowsUseCase.showsFlow.collectAsState(initial = Resource.Loading)

        when (collectAsState) {
            is Resource.Error -> {
                val error = collectAsState as Resource.Error
                CenteredText(text = error.message)
            }
            Resource.Loading -> CenteredLoadingMessageWithIndicator()

            is Resource.Success -> LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(4.dp),
            ) {
                val success = collectAsState as Resource.Success<DiscoverTVResponse>

                items(success.data.discoverTVItems) {
                    CoilImage(url = getImageUrl(it.posterPath),
                        imageDescription = "",
                        modifier = Modifier.padding(4.dp))
                }
            }

        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun HomeScreenTabLayout() {
        val rememberCoroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState()

        val tabs = listOf(Tab.Movies, Tab.Shows)

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
                    Tab.Movies -> MoviesTabContent()
                    Tab.Shows -> ShowsTabContent()
                }
            }
        }
    }

    @Composable
    fun DropDownMenu() {
        var expanded by remember { mutableStateOf(false) }

        val performActionAndDismiss = { action: () -> Unit ->
            expanded = false
            action()
        }

        Column {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.Sort, contentDescription = "Localized description")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    performActionAndDismiss { orderRepo.setOrderProperty(Order.Property.Name) }
                }) {
                    Text("Name")
                }
                DropdownMenuItem(onClick = {
                    performActionAndDismiss { orderRepo.setOrderProperty(Order.Property.RELEASE_DATE) }
                }) {
                    Text("Release Date")
                }

                Divider()

                DropdownMenuItem(onClick = {
                    performActionAndDismiss { orderRepo.setOrderType(Order.Type.ASCENDING) }
                },
                    Modifier.requiredWidth(128.dp)) {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "")
                    Text("Ascending")
                }


                DropdownMenuItem(onClick = {
                    performActionAndDismiss { orderRepo.setOrderType(Order.Type.DESCENDING) }
                }) {
                    Icon(Icons.Default.KeyboardArrowDown,
                        contentDescription = "",
                        Modifier.background(
                            Color.Transparent))
                    Text("Descending")
                }
            }
        }
    }
}
