package com.mahmoudmohamaddarwish.animatedproductions.screens.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
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
class DetailsActivitySuccessStatesTest {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<ProductionDetailsActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            DetailsScreen(
                detailsUIState = Resource.Success(Production.movieDummy),
                navigateBack = {}
            )
        }
    }

    @Test
    fun app_displays_backdrop_image() {
        composeTestRule.run {
            onNodeWithTag(DETAILS_BACKDROP_IMAGE_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun app_displays_poster_image() {
        composeTestRule.run {
            onNodeWithTag(DETAILS_POSTER_IMAGE_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun app_displays_title_text() {
        composeTestRule.run {
            onNodeWithTag(DETAILS_TITLE_TEXT_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun app_displays_overview_text() {
        composeTestRule.run {
            onNodeWithTag(DETAILS_OVERVIEW_TEXT_TEST_TAG).assertIsDisplayed()
        }
    }
}