package com.mahmoudmohamaddarwish.animatedproductions;

import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.HomeScreen
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.HomeViewModel
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ViewModelTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()

        composeTestRule.setContent {
            val viewModel = composeTestRule.activity.viewModels<HomeViewModel>().value

            HomeScreen(viewModel)
        }
    }

    @Test
    fun app_displays_movies_tab() {
        composeTestRule.onNodeWithText("Movies").assertIsDisplayed()
    }

    @Test
    fun app_displays_tv_shows_tab() {
        composeTestRule.onNodeWithText("TV Shows").assertIsDisplayed()

        composeTestRule.onNodeWithText("TV Shows").performClick()

        composeTestRule.waitForIdle()
    }
}