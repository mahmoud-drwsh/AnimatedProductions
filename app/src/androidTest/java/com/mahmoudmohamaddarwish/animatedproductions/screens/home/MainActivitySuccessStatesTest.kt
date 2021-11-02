package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import android.content.res.Resources
import androidx.activity.viewModels
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import com.mahmoudmohamaddarwish.animatedproductions.screens.IDLING_RESOURCE_TIMEOUT
import com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails.DETAILS_BACKDROP_IMAGE_TEST_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    fun app_shows_movies_list_after_success_state() {
        composeTestRule.run {
            waitUntil(IDLING_RESOURCE_TIMEOUT) { MainActivityIdlingResource.isIdle }

            onNodeWithTag(MAIN_ACTIVITY_MOVIES_LIST_TEST_TAG).assertIsDisplayed()
        }
    }


    @Test
    fun app_shows_tv_shows_list_after_success_state() {
        composeTestRule.run {
            waitUntil(IDLING_RESOURCE_TIMEOUT) { MainActivityIdlingResource.isIdle }

            onNodeWithText(resources.getString(R.string.shows_tab_label)).performClick()

            onNodeWithTag(MAIN_ACTIVITY_SHOWS_LIST_TEST_TAG).assertIsDisplayed()
        }
    }


    @Test
    fun app_navigates_to_tv_show_details_after_success_state() {
        composeTestRule.run {
            waitUntil(IDLING_RESOURCE_TIMEOUT) { MainActivityIdlingResource.isIdle }

            onNodeWithText(resources.getString(R.string.shows_tab_label)).performClick()

            onNodeWithTag(MAIN_ACTIVITY_SHOWS_LIST_TEST_TAG).assertIsDisplayed()


            onAllNodesWithTag(MAIN_ACTIVITY_POSTER_IMAGE_TEST_TAG)
                .onFirst()
                .performClick()

            onNodeWithTag(DETAILS_BACKDROP_IMAGE_TEST_TAG).assertIsDisplayed()
        }
    }


}