package com.mahmoudmohamaddarwish.animatedproductions.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.data.repos.NightModeRepo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class NightModeRepoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var nightModeRepo: NightModeRepo

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun modeFlowReactsToToggling(): Unit = runBlocking {
        val millisToWaitForDataToBeSaved = 512L

        val first = nightModeRepo.isNightModeEnabled.first()

        nightModeRepo.toggleNightMode()

        delay(millisToWaitForDataToBeSaved) // the delay is for ensuring the new value is stored to the disk

        val second = nightModeRepo.isNightModeEnabled.first()

        assert(first != second)

        nightModeRepo.toggleNightMode()

        delay(millisToWaitForDataToBeSaved) // the delay is for ensuring the new value is stored to the disk

        val third = nightModeRepo.isNightModeEnabled.first()

        assert(second != third)
    }
}