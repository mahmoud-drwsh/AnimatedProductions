package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import androidx.activity.viewModels
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.repo.OrderRepoTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Since this viewModel depends on Hilt, it was placed here.
 * */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HomeViewModelTest {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        // since the compose is used, the viewModel is injected as it would be in a compose activity
        val viewModel by composeTestRule.activity.viewModels<HomeViewModel>()
        homeViewModel = viewModel
    }

    @Test
    fun ensureOrderCanBeSavedAndRetrievedSuccessfully() = runBlocking {
        OrderRepoTest.orderObjects.forEach {
            homeViewModel.setSortProperty(it.property)
            homeViewModel.setSortType(it.type)

            // this delay is for ensuring that the data has been saved to the disk
            delay(WAITING_DELAY)

            assert(homeViewModel.order.first() == it)
        }
    }

    @Test
    fun ensureMoviesFlowEmitsLoadingFirst() = runBlocking {
        val resource = homeViewModel.orderedMoviesFlow.first()
        assert(resource is Resource.Loading)
    }

    @Test
    fun ensureShowsFlowEmitsLoadingFirst() = runBlocking {
        val resource = homeViewModel.orderedShowsFlow.first()
        assert(resource is Resource.Loading)
    }

    @Test
    fun ensureShowsFlowEmitsSuccessSecond() = runBlocking {
        val resource = homeViewModel.orderedShowsFlow.drop(1).first()
        assert(resource is Resource.Success)
    }

    @Test
    fun ensureMoviesFlowEmitsSuccessSecond() = runBlocking {
        val resource = homeViewModel.orderedMoviesFlow.drop(1).first()
        assert(resource is Resource.Success)
    }

    @Test
    fun ensureMoviesFlowEmitsNonEmptyLists() = runBlocking {
        val success =
            homeViewModel.orderedMoviesFlow.first { it is Resource.Success } as Resource.Success

        assert(success.data.isNotEmpty())
    }

    @Test
    fun ensureShowsFlowEmitsNonEmptyLists() = runBlocking {
        val success =
            homeViewModel.orderedShowsFlow.first { it is Resource.Success } as Resource.Success

        assert(success.data.isNotEmpty())
    }

    companion object {
        private const val WAITING_DELAY = 1000L
    }
}

