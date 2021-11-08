package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Order
import com.mahmoudmohamaddarwish.animatedproductions.screens.details.DETAILS_ROOT_COMPOSABLE_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.viewmodels.ProductionsViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivitySuccessStatesTest {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun viewModelCreationTest() {
        composeTestRule.setContent {
            val m: ProductionsViewModel = hiltViewModel()
            runBlocking {
                assert(m.orderedMoviesFlow.first() is Resource.Loading)
            }
        }
    }

    @Test
    fun app_displays_movies_and_shows_tab_layout() {
        composeTestRule.run {
            onNodeWithTag(MAIN_ACTIVITY_MOVIES_AND_SHOWS_TAB_LAYOUT_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun app_displays_movies_tab() {
        composeTestRule.run {
            onNodeWithTag(MAIN_ACTIVITY_MOVIES_TAB_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun app_displays_tv_shows_tab() {
        composeTestRule.run {
            onNodeWithTag(MAIN_ACTIVITY_SHOWS_TAB_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun app_shows_movies_list_after_success_state() {
        composeTestRule.run {
            registerIdlingResource(HomeIdlingResource)

            // click on the movies tab to show the movies list
            onNodeWithTag(MAIN_ACTIVITY_MOVIES_TAB_TEST_TAG).performClick()

            onNodeWithTag(MAIN_ACTIVITY_MOVIES_LIST_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun app_shows_tv_shows_list_after_success_state() {
        composeTestRule.run {
            registerIdlingResource(HomeIdlingResource)

            // click on the Shows tab to show the TV shows list
            onNodeWithTag(MAIN_ACTIVITY_SHOWS_TAB_TEST_TAG).performClick()

            onNodeWithTag(MAIN_ACTIVITY_SHOWS_LIST_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun app_navigates_to_tv_show_details_after_success_state() {
        composeTestRule.run {
            registerIdlingResource(HomeIdlingResource)

            // click on the shows tab
            onNodeWithTag(MAIN_ACTIVITY_SHOWS_TAB_TEST_TAG).performClick()

            // ensure the list of shows is shown
            onNodeWithTag(MAIN_ACTIVITY_SHOWS_LIST_TEST_TAG).assertIsDisplayed()

            // click on the first show in the list
            onAllNodesWithTag(MAIN_ACTIVITY_POSTER_IMAGE_TEST_TAG)
                .onFirst()
                .performClick()

            // ensure the details screen is displayed
            onNodeWithTag(DETAILS_ROOT_COMPOSABLE_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun app_navigates_to_movie_details_after_success_state() {
        composeTestRule.run {
            registerIdlingResource(HomeIdlingResource)

            onNodeWithTag(MAIN_ACTIVITY_MOVIES_TAB_TEST_TAG).performClick()

            // ensure the list of movies is shown
            onNodeWithTag(MAIN_ACTIVITY_MOVIES_LIST_TEST_TAG).assertIsDisplayed()

            // click on the first movie in the list
            onAllNodesWithTag(MAIN_ACTIVITY_POSTER_IMAGE_TEST_TAG)
                .onFirst()
                .performClick()

            // ensure the details screen is displayed
            onNodeWithTag(DETAILS_ROOT_COMPOSABLE_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun ensure_sorting_icon_button_is_displayed() {
        composeTestRule.run {
            val sortingIconButton =
                onNodeWithTag(MAIN_ACTIVITY_SORTING_ICON_BUTTON_TEST_TAG)

            sortingIconButton.assertIsDisplayed()
        }
    }

    @Test
    fun ensure_all_sorting_option_are_displayed() {
        composeTestRule.run {
            val sortingIconButton =
                onNodeWithTag(MAIN_ACTIVITY_SORTING_ICON_BUTTON_TEST_TAG)

            sortingIconButton.assertIsDisplayed()

            // show the options
            sortingIconButton.performClick()


            onNodeWithText(Order.Property.Name.label).assertIsDisplayed()
            onNodeWithText(Order.Property.RELEASE_DATE.label).assertIsDisplayed()
            onNodeWithText(Order.Type.ASCENDING.label).assertIsDisplayed()
            onNodeWithText(Order.Type.DESCENDING.label).assertIsDisplayed()
        }
    }

}

