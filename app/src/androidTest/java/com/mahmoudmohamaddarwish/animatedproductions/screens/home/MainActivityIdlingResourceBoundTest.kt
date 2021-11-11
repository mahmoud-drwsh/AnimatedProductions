package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import androidx.activity.viewModels
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.FavoritesListUseCase
import com.mahmoudmohamaddarwish.animatedproductions.repo.FavoritesRepoTest.Companion.addDummyFavorites
import com.mahmoudmohamaddarwish.animatedproductions.screens.details.DetailsActivityTestTags
import com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels.NightModeViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
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
class MainActivityIdlingResourceBoundTest {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()


    @Inject
    lateinit var favoritesListUseCase: FavoritesListUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun appSuccessfullyLoadsMovies() {
        composeTestRule.onNodeWithTag(MainActivityTestTags.MOVIES_NAV_ITEM.name)
            .assertHasClickAction()
            .assertIsDisplayed()
            .performClick()

        composeTestRule.registerIdlingResource(MoviesIdlingResource)

        waitForIdleState()

        composeTestRule.onNodeWithTag(MainActivityTestTags.MOVIES_LIST.name).assertIsDisplayed()
    }

    @Test
    fun appSuccessfullyLoadsShows() {
        composeTestRule.onNodeWithTag(MainActivityTestTags.SHOWS_NAV_ITEM.name)
            .assertHasClickAction()
            .assertIsDisplayed()
            .performClick()

        composeTestRule.registerIdlingResource(ShowsIdlingResource)

        waitForIdleState()

        composeTestRule.onNodeWithTag(MainActivityTestTags.SHOWS_LIST.name).assertIsDisplayed()
    }

    @Test
    fun appSuccessfullyLoadsFavoriteMovies() {
        favoritesListUseCase.addDummyFavorites()
        composeTestRule.run {
            onNodeWithTag(MainActivityTestTags.FAVORITE_MOVIES_NAV_ITEM.name)
                .assertHasClickAction()
                .assertIsDisplayed()
                .performClick()

            registerIdlingResource(FavoriteMoviesIdlingResource)

            waitForIdleState()

            onNodeWithTag(MainActivityTestTags.FAVORITE_MOVIES_LIST.name).assertIsDisplayed()
        }
    }

    @Test
    fun appSuccessfullyLoadsFavoriteShows() {

        favoritesListUseCase.addDummyFavorites()

        composeTestRule.run {
            onNodeWithTag(MainActivityTestTags.FAVORITE_SHOWS_NAV_ITEM.name)
                .assertHasClickAction()
                .assertIsDisplayed()
                .performClick()

            registerIdlingResource(FavoriteShowsIdlingResource)

            waitForIdleState()

            onNodeWithTag(MainActivityTestTags.FAVORITE_SHOWS_LIST.name).assertIsDisplayed()
        }
    }

    @Test
    fun appSuccessfullyNavigatesToMovieDetails() {
        composeTestRule.run {
            appSuccessfullyLoadsMovies()

            clickOnFirstImageInTheList()
        }
    }

    @Test
    fun appSuccessfullyNavigatesToShowDetails() {
        composeTestRule.run {
            appSuccessfullyLoadsShows()

            clickOnFirstImageInTheList()
        }
    }

    @Test
    fun appSuccessfullyNavigatesToFavoriteMovieDetails() {
        appSuccessfullyLoadsFavoriteMovies()

        composeTestRule.clickOnFirstImageInTheList()
    }

    @Test
    fun appSuccessfullyNavigatesToFavoriteShowDetails() {
        appSuccessfullyLoadsFavoriteShows()

        composeTestRule.clickOnFirstImageInTheList()
    }

    private fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.clickOnFirstImageInTheList(): SemanticsNodeInteraction {
        // wait for list images to be displayed
        waitForIdleState()

        onAllNodesWithTag(MainActivityTestTags.POSTER_IMAGE.name)
            .onFirst()
            .assertHasClickAction()
            .assertIsDisplayed()
            .performClick()

        // wait for details screen to fully load
        waitForIdleState()

        return onNodeWithTag(DetailsActivityTestTags.ROOT_COMPOSABLE.name).assertIsDisplayed()
    }

    /**
     * Using this ensures that everything has completely loaded after the idlingResource signals
     * that it has loaded.
     * */
    private fun waitForIdleState(l: Long = timeToWaitFor) {
        runBlocking { delay(l) }
    }

    companion object {
        private const val timeToWaitFor = 1000L
    }
}


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

        val vm by composeTestRule.activity.viewModels<NightModeViewModel>()
        nightModeViewModel = vm
    }

    @Test
    fun appSuccessfullyLoadsMovies(): Unit = runBlocking {

    }

    /**
     * Using this ensures that everything has completely loaded after the idlingResource signals
     * that it has loaded.
     * */
    private fun waitForIdleState(l: Long = timeToWaitFor) {
        runBlocking { delay(l) }
    }

    companion object {
        private const val timeToWaitFor = 1000L
    }
}


private const val TAG = "MainActivityIdlingResou"