package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import android.content.res.Resources
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.HomeScreen
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.HomeViewModel
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MainActivity
import com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails.ProductionDetailsActivity.Companion.BACKDROP_IMAGE_TEST_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivitySuccessStatesTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var resources: Resources

    @Before
    fun setup() {
        hiltTestRule.inject()

        composeTestRule.setContent {
            homeViewModel = composeTestRule.activity.viewModels<HomeViewModel>().value

            HomeScreen(homeViewModel)
        }
    }

    @Test
    fun app_displays_movies_tab() {
        composeTestRule.run {
            onNodeWithText(resources.getString(R.string.movies_tab_label)).assertIsDisplayed()
        }
    }

    @Test
    fun app_displays_tv_shows_tab() {
        composeTestRule.run {
            onNodeWithText(resources.getString(R.string.shows_tab_label)).assertIsDisplayed()
        }
    }

    @Test
    fun app_sorting_icon_button() {
        composeTestRule.run {
            val onNodeWithContentDescription =
                onNodeWithContentDescription(resources.getString(R.string.sort_productions_icon_description))
            onNodeWithContentDescription.assertIsDisplayed()
            onNodeWithContentDescription.performClick()

            onNodeWithText(Order.Property.Name.label).assertIsDisplayed()
            onNodeWithText(Order.Property.RELEASE_DATE.label).assertIsDisplayed()
            onNodeWithText(Order.Type.ASCENDING.label).assertIsDisplayed()
            onNodeWithText(Order.Type.DESCENDING.label).assertIsDisplayed()
        }
    }


    @Test
    fun app_shows_movies_and_shows_tabs_after_success_state() {
        runBlocking {
            // this will force the test to wait for the success state to be emitted
            homeViewModel.orderedMoviesFlow.dropWhile { it !is Resource.Success }.first()
        }

        composeTestRule.run {
            onNodeWithTag("movies_and_shows_tag").assertIsDisplayed()
        }
    }


    @Test
    fun app_shows_movies_list_after_success_state() {
        runBlocking {
            // this will force the test to wait for the success state to be emitted
            homeViewModel.orderedMoviesFlow.dropWhile { it !is Resource.Success }.first()
        }

        composeTestRule.run {
            onNodeWithTag(MainActivity.MOVIES_LIST_TEST_TAG).assertIsDisplayed()
        }
    }


    @Test
    fun app_shows_tv_shows_list_after_success_state() {
        runBlocking {
            // this will force the test to wait for the success state to be emitted
            homeViewModel.orderedShowsFlow.dropWhile { it !is Resource.Success }.first()
        }

        composeTestRule.run {
            onNodeWithText(resources.getString(R.string.shows_tab_label)).performClick()

            onNodeWithTag(MainActivity.SHOWS_LIST_TEST_TAG).assertIsDisplayed()
        }
    }


    @Test
    fun app_navigates_to_tv_show_details_after_success_state() {
        runBlocking {
            // this will force the test to wait for the success state to be emitted
            homeViewModel.orderedShowsFlow.dropWhile { it !is Resource.Success }.first()
        }

        composeTestRule.run {
            mainClock.advanceTimeBy(3000L)

            onNodeWithText(resources.getString(R.string.shows_tab_label)).performClick()

            onNodeWithTag(MainActivity.SHOWS_LIST_TEST_TAG).assertIsDisplayed()


            onAllNodesWithTag(MainActivity.MAIN_ACTIVITY_POSTER_IMAGE_TEST_TAG)
                .onFirst()
                .performClick()

            onNodeWithTag(BACKDROP_IMAGE_TEST_TAG).assertIsDisplayed()
        }
    }


}