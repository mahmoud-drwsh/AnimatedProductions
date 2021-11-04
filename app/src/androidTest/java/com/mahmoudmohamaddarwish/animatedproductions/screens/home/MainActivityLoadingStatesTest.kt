package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityLoadingStatesTest {

    @get:Rule
    var composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            HomeScreenTabLayout(moviesResource = Resource.Loading, showsResource = Resource.Loading)
        }
    }

    @Test
    fun app_displays_movies_error_message() {
        composeTestRule.run {
            onNodeWithTag(MAIN_ACTIVITY_MOVIES_TAB_TEST_TAG).assertIsDisplayed()

            onNodeWithTag(MAIN_ACTIVITY_MOVIES_LOADING_INDICATOR_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun app_displays_tv_shows_error_message() {
        composeTestRule.run {
            onNodeWithTag(MAIN_ACTIVITY_SHOWS_TAB_TEST_TAG).performClick()

            onNodeWithTag(MAIN_ACTIVITY_SHOWS_LOADING_INDICATOR_TEST_TAG).assertIsDisplayed()
        }
    }
}