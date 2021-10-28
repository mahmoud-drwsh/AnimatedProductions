package com.mahmoudmohamaddarwish.animatedproductions.screens.details

import android.content.res.Resources
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails.DetailsScreen
import com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails.ProductionDetailsActivity
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
class DetailsActivitySuccessStatesTest {

    @get:Rule(order = 1)
    var hiltTestRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    @Inject
    lateinit var resources: Resources

    @Before
    fun setup() {
        hiltTestRule.inject()

        composeTestRule.setContent {
            DetailsScreen(detailsUIState = Resource.Success(Production.dummy)) {}
        }
    }

    @Test
    fun app_displays_backdrop_image() {
        composeTestRule.run {
            onNodeWithTag(ProductionDetailsActivity.BACKDROP_IMAGE_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun app_displays_poster_image() {
        composeTestRule.run {
            onNodeWithTag(ProductionDetailsActivity.DETAILS_POSTER_IMAGE_TEST_TAG).assertIsDisplayed()
        }
    }
}