package com.mahmoudmohamaddarwish.animatedproductions.screens.details

import android.content.res.Resources
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails.DETAILS_LOADING_INDICATOR_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails.DetailsScreen
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
class DetailsActivityLoadingStatesTest {

    @get:Rule
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            DetailsScreen(detailsUIState = Resource.Loading) {}
        }
    }

    @Test
    fun app_displays_loading_indicator() {
        composeTestRule.run {
            onNodeWithTag(DETAILS_LOADING_INDICATOR_TEST_TAG).assertIsDisplayed()
        }
    }
}