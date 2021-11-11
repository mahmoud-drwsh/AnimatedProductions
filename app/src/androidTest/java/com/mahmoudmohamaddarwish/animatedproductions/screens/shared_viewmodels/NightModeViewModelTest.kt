package com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels

import androidx.activity.viewModels
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.DEFAULT_WAITING_TIME
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MainActivity
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

@ExperimentalCoroutinesApi
@OptIn(FlowPreview::class)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class NightModeViewModelTest {


    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()


    private lateinit var nightModeViewModel: NightModeViewModel


    @Before
    fun setup() {
        hiltRule.inject()

        nightModeViewModel = composeTestRule.activity.viewModels<NightModeViewModel>().value
    }

    /**
     * Will try toggling the UI mode twice and checking that the value changes after toggling
     * */
    @Test
    fun theUIModeValueEmittedByTheFlowChangesAfterToggling(): Unit = runBlocking {
        val nightModeEnabledFlow = nightModeViewModel.isNightModeEnabled

        repeat(2) {
            val first = nightModeEnabledFlow.first()

            nightModeViewModel.toggleNightMode()

            // to ensure data has been saved to the disk
            delay(DEFAULT_WAITING_TIME)

            val second = nightModeEnabledFlow.first()

            assert(first != second)
        }
    }
}