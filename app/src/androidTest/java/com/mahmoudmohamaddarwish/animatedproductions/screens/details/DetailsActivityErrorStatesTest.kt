package com.mahmoudmohamaddarwish.animatedproductions.screens.details

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
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
class DetailsActivityErrorStatesTest {

    @get:Rule
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            DetailsScreen(detailsUIState = Resource.Error(TEST_ERROR_MESSAGE)
            ) {}
        }
    }

    @Test
    fun app_displays_error_message() {
        composeTestRule.run {
            val errorMessageComposable = onNodeWithTag(DETAILS_ERROR_MESSAGE_TEST_TAG)

            errorMessageComposable.assertIsDisplayed()

            // the error message is displayed in a descendant of the root error message composable
            errorMessageComposable.assert(hasAnyDescendant(hasText(TEST_ERROR_MESSAGE)))
        }
    }

    companion object {
        const val TEST_ERROR_MESSAGE: String = "TEST_ERROR_MESSAGE"
    }
}
