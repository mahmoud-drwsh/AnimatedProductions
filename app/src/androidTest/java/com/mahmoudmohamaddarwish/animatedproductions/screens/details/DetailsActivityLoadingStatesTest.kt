package com.mahmoudmohamaddarwish.animatedproductions.screens.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
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
class DetailsActivityLoadingStatesTest {

    @get:Rule
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            DetailsScreen(detailsUIState = Resource.Loading
            ) {}
        }
    }

    @Test
    fun app_displays_loading_indicator() {
        composeTestRule.run {
            onNodeWithTag(DetailsActivityTestTags.LOADING_INDICATOR.name).assertIsDisplayed()
        }
    }
}