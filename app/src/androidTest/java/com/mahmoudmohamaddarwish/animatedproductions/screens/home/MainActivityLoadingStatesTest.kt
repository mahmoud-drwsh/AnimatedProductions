package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import android.content.res.Resources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.Resource
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
class MainActivityLoadingStatesTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createComposeRule()

    @Inject
    lateinit var resources: Resources

    @Before
    fun setup() {
        hiltTestRule.inject()

        composeTestRule.setContent {
            HomeScreenTabLayout(moviesFlow = Resource.Loading, showsFlow = Resource.Loading)
        }
    }

    @Test
    fun app_displays_movies_error_message() {
        composeTestRule.run {
            onNodeWithText(resources.getString(R.string.movies_tab_label)).assertIsDisplayed()

            onNodeWithTag(MainActivity.MOVIES_LOADING_INDICATOR_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun app_displays_tv_shows_error_message() {
        composeTestRule.run {
            onNodeWithText(resources.getString(R.string.shows_tab_label)).performClick()

            onNodeWithTag(MainActivity.SHOWS_LOADING_INDICATOR_TEST_TAG).assertIsDisplayed()
        }
    }
}