package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityFailureStatesTest {

    @get:Rule
    var composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            HomeScreenTabLayout(moviesResource = Resource.Error(MOVIES_ERROR_MESSAGE),
                showsResource = Resource.Error(SHOWS_ERROR_MESSAGE))
        }
    }

    @Test
    fun app_displays_movies_error_message() {
        composeTestRule.run {
            onNodeWithTag(MAIN_ACTIVITY_MOVIES_TAB_TEST_TAG).performClick()

            onNodeWithText(MOVIES_ERROR_MESSAGE).assertIsDisplayed()
        }
    }

    @Test
    fun app_displays_tv_shows_error_message() {
        composeTestRule.run {
            onNodeWithTag(MAIN_ACTIVITY_SHOWS_TAB_TEST_TAG).performClick()

            onNodeWithText(SHOWS_ERROR_MESSAGE).assertIsDisplayed()
        }
    }

    companion object {
        private const val MOVIES_ERROR_MESSAGE: String = "MOVIES ERROR"
        private const val SHOWS_ERROR_MESSAGE: String = "SHOWS ERROR"
    }
}