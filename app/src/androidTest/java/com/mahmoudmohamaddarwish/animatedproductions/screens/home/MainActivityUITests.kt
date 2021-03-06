package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.NightModeUseCase
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.BottomNavigationDestinationModel.Companion.DESTINATION_MODELS
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@ExperimentalCoroutinesApi
@OptIn(FlowPreview::class)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityUITests {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var nightModeUseCase: NightModeUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun appShowsBottomNavBar() {
        composeTestRule.run {
            onNodeWithTag(MainActivityTestTags.NAV_BAR.name).assertIsDisplayed()
        }
    }

    @Test
    fun appShowsUIModeTopBarIcon() {
        composeTestRule.run {
            onNodeWithTag(MainActivityTestTags.UI_MODE_ICON_BUTTON.name).assertIsDisplayed()
        }
    }

    @Test
    fun appShowsTopBar() {
        composeTestRule.run {
            onNodeWithTag(MainActivityTestTags.MAIN_TOP_APP_BAR.name).assertIsDisplayed()
        }
    }

    @Test
    fun appShowsBottomNavDestinations() {
        DESTINATION_MODELS.forEach { dest ->
            composeTestRule.run {
                onNodeWithTag(dest.navBarItemTestTag).assertIsDisplayed()
            }
        }
    }

    @Test
    fun clickingUIModeTogglingIconChangesMode(): Unit = runBlocking {
        val millisToWaitForDataToBeSaved = 512L

        val beforeToggling = nightModeUseCase.isNightModeEnabled.first()

        composeTestRule.run {
            onNodeWithTag(MainActivityTestTags.UI_MODE_ICON_BUTTON.name)
                .assertHasClickAction()
                .assertIsDisplayed()
                .performClick()
        }

        delay(millisToWaitForDataToBeSaved)

        val afterToggling = nightModeUseCase.isNightModeEnabled.first()

        assert(beforeToggling != afterToggling)
    }
}

